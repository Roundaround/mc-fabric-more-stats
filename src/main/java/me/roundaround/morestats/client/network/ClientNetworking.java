package me.roundaround.morestats.client.network;

import io.netty.buffer.Unpooled;
import me.roundaround.morestats.network.NetworkPackets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;

public class ClientNetworking {
  public static void sendTogglePerspectivePacket() {
    ClientPlayNetworking.send(
        NetworkPackets.TOGGLE_PERSPECTIVE_PACKET,
        new PacketByteBuf(Unpooled.buffer()));
  }
}
