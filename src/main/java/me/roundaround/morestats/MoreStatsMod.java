package me.roundaround.morestats;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.roundaround.morestats.network.ServerNetworking;
import net.fabricmc.api.ModInitializer;

public final class MoreStatsMod implements ModInitializer {
  public static final String MOD_ID = "morestats";
  public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

  @Override
  public void onInitialize() {
    // Force load MoreStats class and all the static register calls
    MoreStats.load();

    ServerNetworking.registerReceivers();
  }
}
