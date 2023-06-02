package me.roundaround.morestats.mixin;

import me.roundaround.morestats.MoreStats;
import me.roundaround.morestats.util.Memory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(DamageTracker.class)
public abstract class DamageTrackerMixin {
  @Shadow
  private LivingEntity entity;

  @Inject(method = "onDamage", at = @At(value = "HEAD"))
  public void onDamage(DamageSource source, float originalHealth, float damage, CallbackInfo info) {
    int amount = Math.round(damage * 10f);
    Entity attacker = source.getAttacker();

    if (!(entity instanceof PlayerEntity)) {
      if (attacker instanceof PlayerEntity) {
        ((PlayerEntity) attacker).increaseStat(MoreStats.DAMAGED.getOrCreateStat(entity.getType()),
            amount);
      }
      return;
    }

    PlayerEntity player = (PlayerEntity) entity;

    float remaining = originalHealth - damage;
    if (originalHealth > 4f && remaining <= 4f && remaining > 1f) {
      player.incrementStat(MoreStats.CLOSE_CALL);
      return;
    } else if (originalHealth > 1f && remaining <= 1f && remaining > 0f) {
      player.incrementStat(MoreStats.VERY_CLOSE_CALL);
      return;
    }

    if (attacker != null) {
      player.increaseStat(MoreStats.DAMAGED_BY.getOrCreateStat(attacker.getType()), amount);
      return;
    }

    DamageSources damageSources = player.getWorld().getDamageSources();

    if (source == damageSources.flyIntoWall()) {
      player.incrementStat(MoreStats.CRUNCH);
      player.increaseStat(MoreStats.CRUNCH_DAMAGE, amount);
    } else if (source == damageSources.fall()) {
      UUID uuid = player.getUuid();
      if (Memory.LATEST_FALL_FROM_PEARL.contains(uuid)) {
        player.increaseStat(MoreStats.ENDER_PEARL_DAMAGE, amount);
      } else {
        player.increaseStat(MoreStats.FALL_DAMAGE, amount);
      }
      Memory.LATEST_FALL_FROM_PEARL.remove(uuid);
    } else if (source == damageSources.drown()) {
      player.increaseStat(MoreStats.DROWN_DAMAGE, amount);
    } else if (source == damageSources.starve()) {
      player.increaseStat(MoreStats.STARVE_DAMAGE, amount);
    } else if (source == damageSources.inFire() || source == damageSources.lava() ||
        source == damageSources.onFire() || source == damageSources.hotFloor()) {
      player.increaseStat(MoreStats.FIRE_DAMAGE, amount);
    } else if (source == damageSources.freeze()) {
      player.increaseStat(MoreStats.POWDER_SNOW_DAMAGE, amount);
    }
  }
}
