package me.roundaround.morestats.mixin;

import me.roundaround.morestats.MoreStats;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.StatsScreen;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.entity.EntityType;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.StatHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.gui.screen.StatsScreen$EntityStatsListWidget$Entry")
public abstract class EntityStatsListWidgetEntryMixin {
  @Unique
  private static final MinecraftClient CLIENT = MinecraftClient.getInstance();

  @Unique
  private Text damagedText;
  @Unique
  private boolean damagedAny = false;
  @Unique
  private Text damagedByText;
  @Unique
  private boolean damagedByAny = false;
  @Unique
  private Text totemsPoppedByText;
  @Unique
  private boolean totemsPoppedByAny = false;

  @Final
  @Shadow
  private Text entityTypeName;

  @Inject(
      method = "<init>(Lnet/minecraft/client/gui/screen/StatsScreen$EntityStatsListWidget;" +
               "Lnet/minecraft/entity/EntityType;)V", at = @At(value = "RETURN")
  )
  private void onInit(
      StatsScreen.EntityStatsListWidget listWidget, EntityType<?> entityType, CallbackInfo info
  ) {
    assert CLIENT.player != null;
    StatHandler statHandler = CLIENT.player.getStatHandler();

    int damaged = statHandler.getStat(MoreStats.DAMAGED.getOrCreateStat(entityType));
    if (damaged == 0) {
      damagedText = Text.translatable("stat_type.morestats.damaged.none", entityTypeName.getString());
    } else {
      damagedText = Text.translatable("stat_type.morestats.damaged",
          StatFormatter.DIVIDE_BY_TEN.format(damaged),
          entityTypeName.getString()
      );
      damagedAny = true;
    }

    int damagedBy = statHandler.getStat(MoreStats.DAMAGED_BY.getOrCreateStat(entityType));
    if (damagedBy == 0) {
      damagedByText = Text.translatable("stat_type.morestats.damaged_by.none", entityTypeName.getString());
    } else {
      damagedByText = Text.translatable("stat_type.morestats.damaged_by",
          entityTypeName.getString(),
          StatFormatter.DIVIDE_BY_TEN.format(damagedBy)
      );
      damagedByAny = true;
    }

    int totemsPoppedBy = statHandler.getStat(MoreStats.TOTEMS_POPPED_BY.getOrCreateStat(entityType));
    if (totemsPoppedBy == 0) {
      totemsPoppedByText = Text.translatable("stat_type.morestats.totems_popped_by.none", entityTypeName.getString());
    } else {
      totemsPoppedByText = Text.translatable("stat_type.morestats.totems_popped_by",
          entityTypeName.getString(),
          totemsPoppedBy
      );
      totemsPoppedByAny = true;
    }
  }

  @Inject(method = "render", at = @At(value = "RETURN"))
  private void onRender(
      DrawContext drawContext,
      int mouseX,
      int mouseY,
      boolean hovered,
      float tickDelta,
      CallbackInfo info
  ) {
    TextRenderer textRenderer = CLIENT.textRenderer;
    int x = self().getContentX();
    int y = self().getContentY();

    drawContext.drawTextWithShadow(textRenderer,
        damagedText,
        x + 2 + 10,
        y + 1 + textRenderer.fontHeight * 3,
        damagedAny ? Colors.ALTERNATE_WHITE : Colors.GRAY
    );
    drawContext.drawTextWithShadow(textRenderer,
        damagedByText,
        x + 2 + 10,
        y + 1 + textRenderer.fontHeight * 4,
        damagedByAny ? Colors.ALTERNATE_WHITE : Colors.GRAY
    );
    drawContext.drawTextWithShadow(textRenderer,
        totemsPoppedByText,
        x + 2 + 10,
        y + 1 + textRenderer.fontHeight * 5,
        totemsPoppedByAny ? Colors.ALTERNATE_WHITE : Colors.GRAY
    );
  }

  @Unique
  private AlwaysSelectedEntryListWidget.Entry<?> self() {
    return (AlwaysSelectedEntryListWidget.Entry<?>) (Object) this;
  }
}
