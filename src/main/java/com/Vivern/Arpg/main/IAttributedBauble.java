package com.Vivern.Arpg.main;

import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IAttributedBauble {
   IAttribute getAttribute();

   double value();

   int operation();

   String itemName();

   default Multimap<String, AttributeModifier> getAttributeModifiers(EntityPlayer player, int equipmentSlot, ItemStack itemstack) {
      return null;
   }

   default boolean useMultimap() {
      return false;
   }
}
