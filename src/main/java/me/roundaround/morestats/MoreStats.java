package me.roundaround.morestats;

import java.util.Locale;

import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MoreStats {
  private static final StatFormatter TICKS_TO_HOURS = (ticks) -> {
    double hours = ticks / 20.0 / 60.0 / 60.0;
    return String.format(Locale.ROOT, "%.3f", hours);
  };

  public static final Identifier PLAYED_HOURS = register("play_hours", TICKS_TO_HOURS);
  public static final Identifier CRUNCH = register("crunch", StatFormatter.DEFAULT);
  public static final Identifier SHREIKER_TRIGGER = register("shreiker_trigger", StatFormatter.DEFAULT);
  public static final Identifier BEACON_TIME = register("beacon_time", StatFormatter.TIME);
  public static final Identifier FIRE_TIME = register("fire_time", StatFormatter.TIME);
  public static final Identifier DROWN_TIME = register("drown_time", StatFormatter.TIME);
  public static final Identifier HUNGRY_TIME = register("hungry_time", StatFormatter.TIME);
  public static final Identifier STARVE_TIME = register("starve_time", StatFormatter.TIME);
  public static final Identifier PAUSE_TIME = register("pause_time", StatFormatter.TIME);
  public static final Identifier FIRE_DAMAGE = register("fire_damage", StatFormatter.DIVIDE_BY_TEN);
  public static final Identifier FALL_DAMAGE = register("fall_damage", StatFormatter.DIVIDE_BY_TEN);
  public static final Identifier CRUNCH_DAMAGE = register("crunch_damage", StatFormatter.DIVIDE_BY_TEN);
  public static final Identifier DROWN_DAMAGE = register("drown_damage", StatFormatter.DIVIDE_BY_TEN);
  public static final Identifier STARVE_DAMAGE = register("starve_damage", StatFormatter.DIVIDE_BY_TEN);

  private static Identifier register(String id, StatFormatter formatter) {
    Identifier identifier = new Identifier(MoreStatsMod.MOD_ID, id);
    Registry.register(Registry.CUSTOM_STAT, id, identifier);
    Stats.CUSTOM.getOrCreateStat(identifier, formatter);
    return identifier;
  }

  public static void load() {}
}
