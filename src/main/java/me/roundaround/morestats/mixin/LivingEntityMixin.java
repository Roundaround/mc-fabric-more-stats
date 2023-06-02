package me.roundaround.morestats.mixin;

import me.roundaround.morestats.MoreStats;
import me.roundaround.morestats.util.Memory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
  @Inject(
      method = "tryUseTotem", at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/advancement/criterion/UsedTotemCriterion;trigger(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/item/ItemStack;)V"
  )
  )
  public void onTotemPop(DamageSource source, CallbackInfoReturnable<Boolean> info) {
    ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
    player.incrementStat(MoreStats.TOTEM_POP);

    Entity attacker = source.getAttacker();
    if (attacker != null) {
      player.incrementStat(MoreStats.TOTEMS_POPPED_BY.getOrCreateStat(attacker.getType()));
      return;
    }

    DamageSources damageSources = player.getWorld().getDamageSources();

    if (source == damageSources.flyIntoWall()) {
      player.incrementStat(MoreStats.CRUNCH_TOTEM_POP);
    } else if (source == damageSources.fall()) {
      UUID uuid = player.getUuid();
      if (Memory.LATEST_TOTEM_FROM_PEARL.contains(uuid)) {
        player.incrementStat(MoreStats.ENDER_PEARL_TOTEM_POP);
      } else {
        player.incrementStat(MoreStats.FALL_TOTEM_POP);
      }
      Memory.LATEST_TOTEM_FROM_PEARL.remove(uuid);
    } else if (source == damageSources.drown()) {
      player.incrementStat(MoreStats.DROWN_TOTEM_POP);
    } else if (source == damageSources.starve()) {
      player.incrementStat(MoreStats.STARVE_TOTEM_POP);
    } else if (source == damageSources.inFire() || source == damageSources.lava() ||
        source == damageSources.onFire() || source == damageSources.hotFloor()) {
      player.incrementStat(MoreStats.FIRE_TOTEM_POP);
    } else if (source == damageSources.freeze()) {
      player.incrementStat(MoreStats.POWDER_SNOW_TOTEM_POP);
    }
  }
}
