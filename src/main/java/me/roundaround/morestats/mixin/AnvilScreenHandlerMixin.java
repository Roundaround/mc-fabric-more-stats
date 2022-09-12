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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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

  @Inject(method = "method_24922(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"))
  private static void onBreakAnvil(PlayerEntity player, World world, BlockPos blockPos, CallbackInfo info) {
    player.incrementStat(MoreStats.ANVIL_BREAK);
  }
}
