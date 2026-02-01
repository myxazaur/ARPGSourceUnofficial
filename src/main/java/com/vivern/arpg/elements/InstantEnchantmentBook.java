package com.vivern.arpg.elements;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class InstantEnchantmentBook extends Item {
   public InstantEnchantmentBook() {
      this.setRegistryName("instant_enchantment_book");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("instant_enchantment_book");
      this.setMaxStackSize(1);
   }
}
