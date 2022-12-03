package me.roundaround.morestats.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import me.roundaround.morestats.MoreStats;
import net.minecraft.entity.EntityType;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatHandler;
import net.minecraft.stat.Stats;

@Mixin(targets = "net/minecraft/client/gui/screen/StatsScreen$EntityStatsListWidget")
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
}
