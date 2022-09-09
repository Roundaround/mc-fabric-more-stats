package me.roundaround.morestats.network;

import me.roundaround.morestats.MoreStatsMod;
import net.minecraft.util.Identifier;

public class NetworkPackets {
  public static final Identifier TOGGLE_PERSPECTIVE_PACKET = new Identifier(
      MoreStatsMod.MOD_ID,
      "toggle_perspective_packet");
}
