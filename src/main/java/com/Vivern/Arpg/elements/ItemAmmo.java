package com.Vivern.Arpg.elements;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemAmmo extends Item {
   public ItemAmmo(CreativeTabs tab, String name, int maxStackSize, int maxDamage) {
      this.setRegistryName(name);
      this.setCreativeTab(tab);
      this.setTranslationKey(name);
      this.setMaxDamage(maxDamage);
      this.setMaxStackSize(maxStackSize);
   }
}
