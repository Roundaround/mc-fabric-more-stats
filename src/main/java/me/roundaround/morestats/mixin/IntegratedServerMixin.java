package me.roundaround.morestats.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.roundaround.morestats.MoreStats;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.network.ServerPlayerEntity;

@Mixin(IntegratedServer.class)
public abstract class IntegratedServerMixin {
  @Inject(method = "incrementTotalWorldTimeStat", at = @At(value = "HEAD"))
  public void onIncrementTotalWorldTimeStat(CallbackInfo info) {
    try (IntegratedServer self = (IntegratedServer) (Object) this) {
      for (ServerPlayerEntity player : self.getPlayerManager().getPlayerList()) {
        player.incrementStat(MoreStats.PAUSE_TIME);
      }
    }

    // TODO: MinecraftServer.tick increment this stat if MultiplayerServerPause
    // is installed and game is paused via it
  }
}
