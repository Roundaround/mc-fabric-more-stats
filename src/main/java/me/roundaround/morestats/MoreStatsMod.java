package me.roundaround.morestats;

import me.roundaround.gradle.api.annotation.Entrypoint;
import me.roundaround.morestats.network.Networking;
import me.roundaround.morestats.server.network.ServerNetworking;
import net.fabricmc.api.ModInitializer;

@Entrypoint(Entrypoint.MAIN)
public final class MoreStatsMod implements ModInitializer {
  @Override
  public void onInitialize() {
    // Force load MoreStats class and all the static register calls
    MoreStats.load();

    Networking.registerC2SPayloads();
    ServerNetworking.registerReceivers();
  }
}
