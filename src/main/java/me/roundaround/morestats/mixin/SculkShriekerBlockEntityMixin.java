package me.roundaround.morestats.mixin;

import me.roundaround.morestats.MoreStats;
import net.minecraft.block.entity.SculkShriekerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(SculkShriekerBlockEntity.class)
public abstract class SculkShriekerBlockEntityMixin {
  @Unique
  Optional<PlayerEntity> whoDoneIt = Optional.empty();

  @Inject(
      method = "shriek(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/Entity;)V",
      at = @At(value = "HEAD")
  )
  public void shriek(ServerWorld world, Entity entity, CallbackInfo info) {
    if (entity instanceof PlayerEntity) {
      whoDoneIt = Optional.of((PlayerEntity) entity);
      whoDoneIt.get().incrementStat(MoreStats.SHREIKER_TRIGGER);
    }
  }

  @Inject(method = "trySpawnWarden", at = @At(value = "RETURN"))
  private void trySpawnWarden(ServerWorld world, CallbackInfoReturnable<Boolean> info) {
    if (whoDoneIt.isPresent() && info.getReturnValue()) {
      whoDoneIt.get().incrementStat(MoreStats.WARDEN_SUMMON);
    }
    whoDoneIt = Optional.empty();
  }
}
