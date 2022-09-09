package me.roundaround.morestats.network;

import me.roundaround.morestats.MoreStats;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class ServerNetworking {
  public static void registerReceivers() {
    ServerPlayNetworking.registerGlobalReceiver(
        NetworkPackets.TOGGLE_PERSPECTIVE_PACKET,
        ServerNetworking::handleTogglePerspectivePacket);
  }

  public static void handleTogglePerspectivePacket(
      MinecraftServer server,
      ServerPlayerEntity player,
      ServerPlayNetworkHandler handler,
      PacketByteBuf buf,
      PacketSender responseSender) {
    player.incrementStat(MoreStats.TOGGLE_PERSPECTIVE);
  }
}
