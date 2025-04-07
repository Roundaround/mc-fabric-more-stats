package me.roundaround.morestats.mixin;

import me.roundaround.morestats.MoreStats;
import net.minecraft.block.EndGatewayBlock;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.block.Portal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.PortalManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Entity.class)
public abstract class EntityMixin {
  @Shadow
  public PortalManager portalManager;

  @Inject(
      method = "tickPortalTeleportation", at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/entity/Entity;teleportTo(Lnet/minecraft/world/TeleportTarget;)" +
               "Lnet/minecraft/entity/Entity;"
  )
  )
  private void onTeleport(CallbackInfo ci) {
    this.getCurrentPortal().ifPresent((portal) -> {
      switch (portal) {
        case NetherPortalBlock ignored -> this.incrementStat(MoreStats.NETHER_PORTAL);
        case EndPortalBlock ignored -> this.incrementStat(MoreStats.END_PORTAL);
        case EndGatewayBlock ignored -> this.incrementStat(MoreStats.END_GATEWAY);
        default -> this.incrementStat(MoreStats.OTHER_PORTALS);
      }
    });
  }

  @Unique
  private Optional<Portal> getCurrentPortal() {
    return Optional.ofNullable(this.portalManager)
        .map((portalManager) -> ((PortalManagerAccessor) portalManager).getPortal());
  }

  @Unique
  private void incrementStat(Identifier stat) {
    Entity self = (Entity) (Object) this;
    if (self instanceof PlayerEntity player) {
      player.incrementStat(stat);
      return;
    }
    self.getPassengerList().forEach((entity) -> {
      if (entity instanceof PlayerEntity rider) {
        rider.incrementStat(stat);
      }
    });
  }
}
