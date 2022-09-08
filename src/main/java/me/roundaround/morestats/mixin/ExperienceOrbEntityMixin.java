package me.roundaround.morestats.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import me.roundaround.morestats.MoreStats;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbEntityMixin {
  @Redirect(method = "onPlayerCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ExperienceOrbEntity;repairPlayerGears(Lnet/minecraft/entity/player/PlayerEntity;I)I"))
  public int repairPlayerGears(ExperienceOrbEntity self, PlayerEntity player, int amount) {
    int remaining = ((ExperienceOrbEntityAccessor) self).invokeRepairPlayerGears(player, amount);
    int diff = amount - remaining;
    if (diff > 0) {
      player.increaseStat(MoreStats.MENDING_REPAIR, diff);
    }
    return remaining;
  }
}
