package me.roundaround.morestats.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.roundaround.morestats.MoreStats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin {
  @Shadow
  private String newItemName;

  @Inject(method = "onTakeOutput", at = @At(value = "HEAD"))
  public void onTakeOutput(PlayerEntity player, ItemStack stack, CallbackInfo info) {
    AnvilScreenHandler self = (AnvilScreenHandler) (Object) this;

    player.increaseStat(MoreStats.ANVIL_XP, self.getLevelCost());
    
    ItemStack input = self.getSlot(0).getStack();
    if (stack.hasCustomName() && !stack.getName().equals(input.getName())) {
      player.increaseStat(MoreStats.ITEM_RENAME, stack.getCount());
    }
  }

  @Inject(method = "onTakeOutput", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"))
  public void onBreakAnvil(PlayerEntity player, ItemStack stack, CallbackInfo info) {
    player.incrementStat(MoreStats.ANVIL_BREAK);
  }
}
