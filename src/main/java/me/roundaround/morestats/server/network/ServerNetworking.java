package me.roundaround.morestats.server.network;

import me.roundaround.morestats.MoreStats;
import me.roundaround.morestats.network.Networking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public final class ServerNetworking {
  private ServerNetworking() {
  }

  public static void registerReceivers() {
    ServerPlayNetworking.registerGlobalReceiver(Networking.TogglePerspectiveC2S.ID,
        ServerNetworking::handleTogglePerspective
    );
  }

  public static void handleTogglePerspective(
      Networking.TogglePerspectiveC2S payload, ServerPlayNetworking.Context context
  ) {
    context.player().server.execute(() -> context.player().incrementStat(MoreStats.TOGGLE_PERSPECTIVE));
  }
}
