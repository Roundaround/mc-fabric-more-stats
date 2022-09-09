package me.roundaround.morestats.mixin;

import java.util.Optional;

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
  Optional<ItemStack> input = Optional.empty();

  @Inject(method = "transferSlot", at = @At(value = "HEAD"))
  public void beforeTakeItem(PlayerEntity player, int index, CallbackInfoReturnable<ItemStack> info) {
    ForgingScreenHandler self = (ForgingScreenHandler) (Object) this;
    if (!(self instanceof AnvilScreenHandler) || index != 2) {
      return;
    }

    input = Optional.of(self.getSlot(0).getStack().copy());
  }

  @Inject(method = "transferSlot", at = @At(value = "RETURN"))
  public void onTakeItem(PlayerEntity player, int index, CallbackInfoReturnable<ItemStack> info) {
    ForgingScreenHandler self = (ForgingScreenHandler) (Object) this;
    if (!(self instanceof AnvilScreenHandler) || index != 2) {
      return;
    }

    if (input.isEmpty()) {
      return;
    }

    ItemStack stack = info.getReturnValue();
    if (stack.hasCustomName() && !stack.getName().equals(input.get().getName())) {
      player.increaseStat(MoreStats.ITEM_RENAME, stack.getCount());
    }

    input = Optional.empty();
  }
}
