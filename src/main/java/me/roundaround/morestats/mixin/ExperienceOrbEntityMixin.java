package me.roundaround.morestats.mixin;

import me.roundaround.morestats.MoreStats;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbEntityMixin {
  @Inject(method = "repairPlayerGears", at = @At(value = "RETURN"))
  public void repairPlayerGears(ServerPlayerEntity player, int amount, CallbackInfoReturnable<Integer> info) {
    int consumed = amount - info.getReturnValueI();
    if (consumed > 0) {
      player.increaseStat(MoreStats.MENDING_REPAIR, consumed);
    }
  }
}
