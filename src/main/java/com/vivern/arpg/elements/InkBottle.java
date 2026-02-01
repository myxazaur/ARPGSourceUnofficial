package com.vivern.arpg.elements;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InkBottle extends Item {
   public int burntime = -1;
   public boolean beacon = false;
   public boolean ench = false;

   public InkBottle(String name, int maxdamage) {
      this.setRegistryName(name);
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey(name);
      this.setMaxDamage(maxdamage);
      this.setMaxStackSize(1);
   }

   public boolean isDamageable() {
      return true;
   }

   public InkBottle setEnchantGlow() {
      this.ench = true;
      return this;
   }

   public boolean hasEffect(ItemStack stack) {
      return this.ench ? this.ench : super.hasEffect(stack);
   }
}
