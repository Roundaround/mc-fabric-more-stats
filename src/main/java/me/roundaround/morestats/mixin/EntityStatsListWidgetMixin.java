package me.roundaround.morestats.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import me.roundaround.morestats.MoreStats;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.StatsScreen;
import net.minecraft.entity.EntityType;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatHandler;
import net.minecraft.stat.Stats;

@Mixin(StatsScreen.EntityStatsListWidget.class)
public abstract class EntityStatsListWidgetMixin {
  @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/stat/StatHandler;getStat(Lnet/minecraft/stat/Stat;)I", ordinal = 0))
  private int getStat(StatHandler statHandler, Stat<EntityType<?>> stat) {
    EntityType<?> entityType = stat.getValue();
    List<Stat<EntityType<?>>> allStats = List.of(
        Stats.KILLED.getOrCreateStat(entityType),
        Stats.KILLED_BY.getOrCreateStat(entityType),
        MoreStats.DAMAGED.getOrCreateStat(entityType),
        MoreStats.DAMAGED_BY.getOrCreateStat(entityType),
        MoreStats.TOTEMS_POPPED_BY.getOrCreateStat(entityType));
    return allStats.stream().mapToInt(statHandler::getStat).max().orElse(0);
  }

  @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/AlwaysSelectedEntryListWidget;<init>(Lnet/minecraft/client/MinecraftClient;IIII)V"))
  private static void onSuper(Args args) {
    MinecraftClient client = args.get(0);
    int currentHeight = args.get(4);

    args.set(4, currentHeight + client.textRenderer.fontHeight * 3);
  }
}
