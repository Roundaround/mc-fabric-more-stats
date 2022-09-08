package me.roundaround.morestats.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.roundaround.morestats.MoreStats;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;

@Mixin(EnderPearlEntity.class)
public abstract class EnderPearlEntityMixin {
  Optional<Vec3d> position = Optional.empty();

  @Inject(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/thrown/EnderPearlEntity;getOwner()Lnet/minecraft/entity/Entity;"))
  public void onCollisionBeforeTeleport(HitResult hitResult, CallbackInfo info) {
    EnderPearlEntity self = (EnderPearlEntity) (Object) this;

    if (!(self.getOwner() instanceof ServerPlayerEntity)) {
      return;
    }

    ServerPlayerEntity player = (ServerPlayerEntity) self.getOwner();
    position = Optional.of(player.getPos());
  }

  @Inject(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
  public void onCollisionAfterTeleport(HitResult hitResult, CallbackInfo info) {
    EnderPearlEntity self = (EnderPearlEntity) (Object) this;

    if (!(self.getOwner() instanceof ServerPlayerEntity) || position.isEmpty()) {
      return;
    }

    ServerPlayerEntity player = (ServerPlayerEntity) self.getOwner();
    double dx = player.getX() - position.get().getX();
    double dy = player.getY() - position.get().getY();
    double dz = player.getZ() - position.get().getZ();

    int distance = Math.round((float) Math.sqrt(dx * dx + dy * dy + dz * dz) * 100f);
    if (distance > 0) {
      player.increaseStat(MoreStats.ENDER_PEARL_ONE_CM, distance);
    }

    position = Optional.empty();
  }
}
