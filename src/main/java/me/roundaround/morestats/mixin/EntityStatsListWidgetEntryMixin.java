package me.roundaround.morestats.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.roundaround.morestats.MoreStats;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.stat.StatHandler;
import net.minecraft.text.Text;

@Mixin(targets = "net.minecraft.client.gui.screen.StatsScreen$EntityStatsListWidget$Entry")
public abstract class EntityStatsListWidgetEntryMixin {
  private static final MinecraftClient CLIENT = MinecraftClient.getInstance();

  @Shadow
  private Text entityTypeName;

  private Text damagedText;
  private boolean damagedAny = false;
  private Text damagedByText;
  private boolean damagedByAny = false;
  private Text totemsPoppedByText;
  private boolean totemsPoppedByAny = false;

  @Inject(method = "<init>", at = @At(value = "RETURN"))
  private void onInit(EntityType<?> entityType, CallbackInfo info) {
    StatHandler statHandler = CLIENT.player.getStatHandler();

    // TODO: Convert texts to translatables

    int damaged = statHandler.getStat(MoreStats.DAMAGED.getOrCreateStat(entityType));
    if (damaged == 0) {
      damagedText = Text.of("You have never damaged " + entityTypeName.getString());
    } else {
      damagedText = Text.of("You dealt " + damaged + " damage to " + entityTypeName.getString());
      damagedAny = true;
    }

    int damagedBy = statHandler.getStat(MoreStats.DAMAGED_BY.getOrCreateStat(entityType));
    if (damagedBy == 0) {
      damagedByText = Text.of("You have never been damaged by " + entityTypeName.getString());
    } else {
      damagedByText = Text.of(entityTypeName.getString() + " dealt " + damagedBy + " damage to you");
      damagedByAny = true;
    }

    int totemsPoppedBy = statHandler.getStat(MoreStats.TOTEMS_POPPED_BY.getOrCreateStat(entityType));
    if (totemsPoppedBy == 0) {
      totemsPoppedByText = Text.of(entityTypeName.getString() + " has never popped one of your totems");
    } else {
      totemsPoppedByText = Text.of(entityTypeName.getString() + " popped " + totemsPoppedBy + " of your totems");
      totemsPoppedByAny = true;
    }
  }

  @Inject(method = "render", at = @At(value = "RETURN"))
  private void onRender(
      MatrixStack matrices,
      int index,
      int y,
      int x,
      int entryWidth,
      int entryHeight,
      int mouseX,
      int mouseY,
      boolean hovered,
      float tickDelta,
      CallbackInfo info) {
    TextRenderer textRenderer = CLIENT.textRenderer;

    DrawableHelper.drawTextWithShadow(matrices,
        textRenderer,
        damagedText, x + 2 + 10,
        y + 1 + textRenderer.fontHeight * 3, damagedAny ? 0x909090 : 0x606060);
    DrawableHelper.drawTextWithShadow(matrices,
        textRenderer,
        damagedByText, x + 2 + 10,
        y + 1 + textRenderer.fontHeight * 4, damagedByAny ? 0x909090 : 0x606060);
    DrawableHelper.drawTextWithShadow(matrices,
        textRenderer,
        totemsPoppedByText, x + 2 + 10,
        y + 1 + textRenderer.fontHeight * 5, totemsPoppedByAny ? 0x909090 : 0x606060);
  }
}
