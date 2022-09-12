package me.roundaround.morestats.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.roundaround.morestats.MoreStats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
  @Inject(method = "tryUseTotem", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/criterion/UsedTotemCriterion;trigger(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/item/ItemStack;)V"))
  public void onTotemPop(DamageSource source, CallbackInfoReturnable<Boolean> info) {
    ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
    player.incrementStat(MoreStats.TOTEM_POP);
  }
}
