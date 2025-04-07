package me.roundaround.morestats.network;

import me.roundaround.morestats.generated.Constants;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public final class Networking {
  private Networking() {
  }

  public static final Identifier TOGGLE_PERSPECTIVE_C2S = Identifier.of(Constants.MOD_ID, "toggle_perspective_c2s");

  public static void registerC2SPayloads() {
    PayloadTypeRegistry.playC2S().register(TogglePerspectiveC2S.ID, TogglePerspectiveC2S.CODEC);
  }

  public record TogglePerspectiveC2S() implements CustomPayload {
    public static final CustomPayload.Id<TogglePerspectiveC2S> ID = new CustomPayload.Id<>(TOGGLE_PERSPECTIVE_C2S);
    public static final PacketCodec<RegistryByteBuf, TogglePerspectiveC2S> CODEC =
        PacketCodec.unit(new TogglePerspectiveC2S());

    @Override
    public Id<TogglePerspectiveC2S> getId() {
      return ID;
    }
  }
}
