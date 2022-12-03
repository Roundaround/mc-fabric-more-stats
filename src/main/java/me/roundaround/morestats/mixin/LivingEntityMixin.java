package me.roundaround.morestats.mixin;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.roundaround.morestats.MoreStats;
import me.roundaround.morestats.util.Memory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
  @Inject(method = "tryUseTotem", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/criterion/UsedTotemCriterion;trigger(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/item/ItemStack;)V"))
  public void onTotemPop(DamageSource source, CallbackInfoReturnable<Boolean> info) {
    ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
    player.incrementStat(MoreStats.TOTEM_POP);
    
    Entity attacker = source.getAttacker();
    if (attacker != null) {
      player.incrementStat(MoreStats.TOTEMS_POPPED_BY.getOrCreateStat(attacker.getType()));
      return;
    }
    
    if (source == DamageSource.FLY_INTO_WALL) {
      player.incrementStat(MoreStats.CRUNCH_TOTEM_POP);
    } else if (source == DamageSource.FALL) {
      UUID uuid = player.getUuid();
      if (Memory.LATEST_TOTEM_FROM_PEARL.contains(uuid)) {
        player.incrementStat(MoreStats.ENDER_PEARL_TOTEM_POP);
      } else {
        player.incrementStat(MoreStats.FALL_TOTEM_POP);
      }
      Memory.LATEST_TOTEM_FROM_PEARL.remove(uuid);
    } else if (source == DamageSource.DROWN) {
      player.incrementStat(MoreStats.DROWN_TOTEM_POP);
    } else if (source == DamageSource.STARVE) {
      player.incrementStat(MoreStats.STARVE_TOTEM_POP);
    } else if (source == DamageSource.IN_FIRE || source == DamageSource.LAVA
        || source == DamageSource.ON_FIRE) {
      player.incrementStat(MoreStats.FIRE_TOTEM_POP);
    } else if (source == DamageSource.FREEZE) {
      player.incrementStat(MoreStats.POWDER_SNOW_TOTEM_POP);
    }
  }
}
