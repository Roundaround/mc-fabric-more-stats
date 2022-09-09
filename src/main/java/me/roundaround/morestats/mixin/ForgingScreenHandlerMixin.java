package me.roundaround.morestats.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.roundaround.morestats.MoreStats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;

@Mixin(ForgingScreenHandler.class)
public abstract class ForgingScreenHandlerMixin {
  @Inject(method = "transferSlot", at = @At(value = "RETURN"))
  public void onTakeItem(PlayerEntity player, int index, CallbackInfoReturnable<ItemStack> info) {
    if (!(((Object) this) instanceof AnvilScreenHandler) || index != 2) {
      return;
    }

    player.increaseStat(MoreStats.ITEM_RENAME, info.getReturnValue().getCount());
  }
}
