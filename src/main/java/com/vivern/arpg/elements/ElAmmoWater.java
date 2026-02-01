package com.vivern.arpg.elements;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ElAmmoWater extends Item {
   public ElAmmoWater() {
      this.setRegistryName("water_focus_ammo");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("water_focus_ammo");
   }
}
