package me.roundaround.morestats.mixin;

import me.roundaround.morestats.MoreStats;
import me.roundaround.morestats.util.ItemStackHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ForgingScreenHandler.class)
public abstract class ForgingScreenHandlerMixin {
  @Unique
  Optional<ItemStack> input = Optional.empty();

  @Inject(method = "quickMove", at = @At(value = "HEAD"))
  public void beforeTakeItem(PlayerEntity player, int index, CallbackInfoReturnable<ItemStack> info) {
    ForgingScreenHandler self = (ForgingScreenHandler) (Object) this;
    if (!(self instanceof AnvilScreenHandler) || index != 2) {
      return;
    }

    input = Optional.of(self.getSlot(0).getStack().copy());
  }

  @Inject(method = "quickMove", at = @At(value = "RETURN"))
  public void onTakeItem(PlayerEntity player, int index, CallbackInfoReturnable<ItemStack> info) {
    ForgingScreenHandler self = (ForgingScreenHandler) (Object) this;
    if (!(self instanceof AnvilScreenHandler) || index != 2) {
      return;
    }

    if (input.isEmpty()) {
      return;
    }

    ItemStack stack = info.getReturnValue();
    if (ItemStackHelper.hasCustomName(stack) && !stack.getName().equals(input.get().getName())) {
      player.increaseStat(MoreStats.ITEM_RENAME, stack.getCount());
    }

    input = Optional.empty();
  }
}
