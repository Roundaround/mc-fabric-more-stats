package me.roundaround.morestats;

import me.roundaround.morestats.network.Networking;
import me.roundaround.morestats.server.network.ServerNetworking;
import net.fabricmc.api.ModInitializer;

public final class MoreStatsMod implements ModInitializer {
  public static final String MOD_ID = "morestats";

  @Override
  public void onInitialize() {
    // Force load MoreStats class and all the static register calls
    MoreStats.load();

    Networking.registerC2SPayloads();
    ServerNetworking.registerReceivers();
  }
}
