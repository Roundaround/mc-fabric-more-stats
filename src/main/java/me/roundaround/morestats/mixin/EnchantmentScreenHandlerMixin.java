package me.roundaround.morestats.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import me.roundaround.morestats.MoreStats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.EnchantmentScreenHandler;

@Mixin(EnchantmentScreenHandler.class)
public abstract class EnchantmentScreenHandlerMixin {
  @Redirect(method = "onButtonClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;applyEnchantmentCosts(Lnet/minecraft/item/ItemStack;I)V"))
  public void applyEnchantmentCosts(PlayerEntity player, ItemStack stack, int cost) {
    player.increaseStat(MoreStats.ENCHANT_XP, cost);
    player.applyEnchantmentCosts(stack, cost);
  }
}
