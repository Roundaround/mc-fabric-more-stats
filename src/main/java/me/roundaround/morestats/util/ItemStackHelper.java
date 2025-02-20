package me.roundaround.morestats.util;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;

public final class ItemStackHelper {
  private ItemStackHelper() {
  }

  public static boolean hasCustomName(ItemStack stack) {
    return stack.get(DataComponentTypes.CUSTOM_NAME) != null;
  }
}
