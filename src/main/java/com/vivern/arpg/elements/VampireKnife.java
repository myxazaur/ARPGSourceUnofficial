package com.vivern.arpg.elements;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class VampireKnife extends Item {
   public VampireKnife() {
      this.setRegistryName("vampire_knife");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("vampire_knife");
   }
}
