package me.roundaround.morestats.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.roundaround.morestats.MoreStats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(Entity.class)
public abstract class EntityMixin {
  @Inject(method = "tickPortal", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;moveToWorld(Lnet/minecraft/server/world/ServerWorld;)Lnet/minecraft/entity/Entity;"))
  public void onMoveToWorld(CallbackInfo info) {
    Entity self = (Entity) (Object) this;
    if (!(self instanceof PlayerEntity)) {
      return;
    }

    ((PlayerEntity) self).incrementStat(MoreStats.NETHER_PORTAL);
  }
}
