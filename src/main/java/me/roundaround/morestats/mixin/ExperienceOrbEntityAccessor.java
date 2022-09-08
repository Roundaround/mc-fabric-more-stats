package me.roundaround.morestats.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(ExperienceOrbEntity.class)
public interface ExperienceOrbEntityAccessor {
  @Invoker("repairPlayerGears")
  public int invokeRepairPlayerGears(PlayerEntity player, int amount);
}
