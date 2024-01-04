package me.roundaround.morestats;

import java.util.Locale;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.StatType;
import net.minecraft.stat.Stats;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MoreStats {
  private static final StatFormatter TICKS_TO_HOURS = (ticks) -> {
    double hours = ticks / 20.0 / 60.0 / 60.0;
    return String.format(Locale.ROOT, "%.3f", hours);
  };

  public static final StatType<EntityType<?>> DAMAGED = registerType("damaged", Registries.ENTITY_TYPE);
  public static final StatType<EntityType<?>> DAMAGED_BY = registerType("damaged_by", Registries.ENTITY_TYPE);
  public static final StatType<EntityType<?>> TOTEMS_POPPED_BY = registerType("totem_popped_by",
      Registries.ENTITY_TYPE);
  public static final Identifier PLAYED_HOURS = register("play_hours", TICKS_TO_HOURS);
  public static final Identifier CRUNCH = register("crunch", StatFormatter.DEFAULT);
  public static final Identifier SHREIKER_TRIGGER = register("shreiker_trigger", StatFormatter.DEFAULT);
  public static final Identifier WARDEN_SUMMON = register("warden_summon", StatFormatter.DEFAULT);
  public static final Identifier TOTEM_POP = register("totem_pop", StatFormatter.DEFAULT);
  public static final Identifier CRUNCH_TOTEM_POP = register("crunch_totem_pop", StatFormatter.DEFAULT);
  public static final Identifier ENDER_PEARL_TOTEM_POP = register("ender_pearl_totem_pop", StatFormatter.DEFAULT);
  public static final Identifier FALL_TOTEM_POP = register("fall_totem_pop", StatFormatter.DEFAULT);
  public static final Identifier DROWN_TOTEM_POP = register("drown_totem_pop", StatFormatter.DEFAULT);
  public static final Identifier STARVE_TOTEM_POP = register("starve_totem_pop", StatFormatter.DEFAULT);
  public static final Identifier FIRE_TOTEM_POP = register("fire_totem_pop", StatFormatter.DEFAULT);
  public static final Identifier POWDER_SNOW_TOTEM_POP = register("powder_snow_totem_pop", StatFormatter.DEFAULT);
  public static final Identifier CLOSE_CALL = register("close_call", StatFormatter.DEFAULT);
  public static final Identifier VERY_CLOSE_CALL = register("very_close_call", StatFormatter.DEFAULT);
  public static final Identifier BEACON_TIME = register("beacon_time", StatFormatter.TIME);
  public static final Identifier FIRE_TIME = register("fire_time", StatFormatter.TIME);
  public static final Identifier FROZEN_TIME = register("frozen_time", StatFormatter.TIME);
  public static final Identifier GLOWING_TIME = register("glowing_time", StatFormatter.TIME);
  public static final Identifier DROWN_TIME = register("drown_time", StatFormatter.TIME);
  public static final Identifier HUNGRY_TIME = register("hungry_time", StatFormatter.TIME);
  public static final Identifier STARVE_TIME = register("starve_time", StatFormatter.TIME);
  public static final Identifier PAUSE_TIME = register("pause_time", StatFormatter.TIME);
  public static final Identifier POWDER_SNOW_TIME = register("powder_snow_time", StatFormatter.TIME);
  public static final Identifier SPYGLASS_TIME = register("spyglass_time", StatFormatter.TIME);
  public static final Identifier FIRE_DAMAGE = register("fire_damage", StatFormatter.DIVIDE_BY_TEN);
  public static final Identifier FALL_DAMAGE = register("fall_damage", StatFormatter.DIVIDE_BY_TEN);
  public static final Identifier CRUNCH_DAMAGE = register("crunch_damage", StatFormatter.DIVIDE_BY_TEN);
  public static final Identifier DROWN_DAMAGE = register("drown_damage", StatFormatter.DIVIDE_BY_TEN);
  public static final Identifier STARVE_DAMAGE = register("starve_damage", StatFormatter.DIVIDE_BY_TEN);
  public static final Identifier POWDER_SNOW_DAMAGE = register("powder_snow_damage", StatFormatter.DIVIDE_BY_TEN);
  public static final Identifier ENDER_PEARL_DAMAGE = register("ender_pearl_damage", StatFormatter.DIVIDE_BY_TEN);
  public static final Identifier ENDER_PEARL_ONE_CM = register("ender_pearl_one_cm", StatFormatter.DISTANCE);
  public static final Identifier ANVIL_XP = register("anvil_xp", StatFormatter.DEFAULT);
  public static final Identifier ENCHANT_XP = register("enchant_xp", StatFormatter.DEFAULT);
  public static final Identifier ANVIL_BREAK = register("anvil_break", StatFormatter.DEFAULT);
  public static final Identifier ITEM_RENAME = register("item_rename", StatFormatter.DEFAULT);
  public static final Identifier MENDING_REPAIR = register("mending_repair", StatFormatter.DEFAULT);
  public static final Identifier TOGGLE_PERSPECTIVE = register("toggle_perspective", StatFormatter.DEFAULT);
  public static final Identifier NETHER_PORTAL = register("nether_portal", StatFormatter.DEFAULT);
  public static final Identifier END_PORTAL = register("end_portal", StatFormatter.DEFAULT);
  public static final Identifier END_GATEWAY = register("end_gateway", StatFormatter.DEFAULT);

  private static Identifier register(String id, StatFormatter formatter) {
    Identifier identifier = new Identifier(MoreStatsMod.MOD_ID, id);
    Registry.register(Registries.CUSTOM_STAT, id, identifier);
    Stats.CUSTOM.getOrCreateStat(identifier, formatter);
    return identifier;
  }

  private static <T> StatType<T> registerType(String id, Registry<T> registry) {
    Identifier identifier = new Identifier(MoreStatsMod.MOD_ID, id);
    MutableText text = Text.translatable("stat_type." + MoreStatsMod.MOD_ID + "." + id);
    return Registry.register(Registries.STAT_TYPE, identifier, new StatType<T>(registry, text));
  }

  public static void load() {
  }
}
