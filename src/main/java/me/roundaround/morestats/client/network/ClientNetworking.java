package me.roundaround.morestats.client.network;

import me.roundaround.morestats.network.Networking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public final class ClientNetworking {
  private ClientNetworking() {
  }

  public static void sendTogglePerspective() {
    if (ClientPlayNetworking.canSend(Networking.TogglePerspectiveC2S.ID)) {
      ClientPlayNetworking.send(new Networking.TogglePerspectiveC2S());
    }
  }
}
