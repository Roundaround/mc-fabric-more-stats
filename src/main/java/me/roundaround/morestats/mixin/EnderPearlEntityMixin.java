package me.roundaround.morestats.mixin;

import me.roundaround.morestats.MoreStats;
import me.roundaround.morestats.util.Memory;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(EnderPearlEntity.class)
public abstract class EnderPearlEntityMixin {
  @Unique
  Optional<Vec3d> position = Optional.empty();

  @Inject(
      method = "onCollision", at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/entity/projectile/thrown/EnderPearlEntity;getOwner()Lnet/minecraft/entity/Entity;"
  )
  )
  public void onCollisionBeforeTeleport(HitResult hitResult, CallbackInfo info) {
    EnderPearlEntity self = (EnderPearlEntity) (Object) this;

    if (!(self.getOwner() instanceof ServerPlayerEntity player)) {
      return;
    }

    this.position = Optional.of(new Vec3d(player.getX(), player.getY(), player.getZ()));
  }

  @Inject(
      method = "onCollision", at = @At(
      value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;clearCurrentExplosion()V"
  )
  )
  public void onCollisionAfterTeleport(HitResult hitResult, CallbackInfo info) {
    EnderPearlEntity self = (EnderPearlEntity) (Object) this;

    if (!(self.getOwner() instanceof ServerPlayerEntity player) || this.position.isEmpty()) {
      return;
    }

    double dx = player.getX() - this.position.get().getX();
    double dy = player.getY() - this.position.get().getY();
    double dz = player.getZ() - this.position.get().getZ();

    int distance = Math.round((float) Math.sqrt(dx * dx + dy * dy + dz * dz) * 100f);
    if (distance > 0) {
      player.increaseStat(MoreStats.ENDER_PEARL_ONE_CM, distance);
    }

    this.position = Optional.empty();

    Memory.LATEST_FALL_FROM_PEARL.add(player.getUuid());
    Memory.LATEST_TOTEM_FROM_PEARL.add(player.getUuid());
  }
}
