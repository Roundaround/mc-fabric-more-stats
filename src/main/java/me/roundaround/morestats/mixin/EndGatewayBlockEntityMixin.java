package me.roundaround.morestats.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.roundaround.morestats.MoreStats;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(EndGatewayBlockEntity.class)
public abstract class EndGatewayBlockEntityMixin {
  @Inject(method = "tryTeleportingEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;resetNetherPortalCooldown()V"))
  private static void onTeleport(World world, BlockPos pos, BlockState state, Entity entity, EndGatewayBlockEntity blockEntity, CallbackInfo info) {
    if (!(entity instanceof PlayerEntity)) {
      return;
    }

    ((PlayerEntity) entity).incrementStat(MoreStats.END_GATEWAY);
  }
}
