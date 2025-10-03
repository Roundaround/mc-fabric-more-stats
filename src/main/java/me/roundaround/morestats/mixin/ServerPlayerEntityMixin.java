package me.roundaround.morestats.mixin;

import me.roundaround.morestats.MoreStats;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
  @Inject(
      method = "playerTick", at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/entity/player/HungerManager;update" +
               "(Lnet/minecraft/server/network/ServerPlayerEntity;)V"
  )
  )
  public void tick(CallbackInfo info) {
    PlayerEntity self = (PlayerEntity) (Object) this;

    self.incrementStat(MoreStats.PLAYED_HOURS);

    if (self.isOnFire()) {
      self.incrementStat(MoreStats.FIRE_TIME);
    }

    if (self.isFrozen()) {
      self.incrementStat(MoreStats.FROZEN_TIME);
    }

    if (self.isUsingSpyglass()) {
      self.incrementStat(MoreStats.SPYGLASS_TIME);
    }

    if (self.isGlowing()) {
      self.incrementStat(MoreStats.GLOWING_TIME);
    }

    if (!self.canBreatheInWater() && !StatusEffectUtil.hasWaterBreathing(self) && !self.getAbilities().invulnerable &&
        self.getAir() <= 0) {
      self.incrementStat(MoreStats.DROWN_TIME);
    }

    if (self.getHungerManager().getFoodLevel() <= 0) {
      self.incrementStat(MoreStats.STARVE_TIME);
    }

    if (self.getHungerManager().getFoodLevel() <= 6f) {
      self.incrementStat(MoreStats.HUNGRY_TIME);
    }

    if (self.getActiveStatusEffects().values().stream().anyMatch(StatusEffectInstance::isAmbient)) {
      self.incrementStat(MoreStats.BEACON_TIME);
    }

    if (self.inPowderSnow) {
      self.incrementStat(MoreStats.POWDER_SNOW_TIME);
    }
  }

  @Inject(method = "applyEnchantmentCosts", at = @At(value = "HEAD"))
  public void applyEnchantmentCosts(ItemStack stack, int cost, CallbackInfo info) {
    PlayerEntity self = (PlayerEntity) (Object) this;
    self.increaseStat(MoreStats.ENCHANT_XP, cost);
  }
}
