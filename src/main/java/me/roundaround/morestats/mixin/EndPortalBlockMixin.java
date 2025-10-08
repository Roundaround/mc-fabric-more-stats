package me.roundaround.morestats.mixin;

import me.roundaround.morestats.MoreStats;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndPortalBlock.class)
public class EndPortalBlockMixin {
  @Inject(
      method = "onEntityCollision", at = @At(
      value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;detachForDimensionChange()V"
  )
  )
  private void onShowCredits(
      BlockState state,
      World world,
      BlockPos pos,
      Entity entity,
      EntityCollisionHandler handler,
      boolean bl,
      CallbackInfo ci
  ) {
    if (!(entity instanceof ServerPlayerEntity player)) {
      return;
    }
    player.incrementStat(MoreStats.END_PORTAL);
  }
}
