package com.vivern.arpg.elements;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ElAmmoAir extends Item {
   public ElAmmoAir() {
      this.setRegistryName("air_focus_ammo");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("air_focus_ammo");
   }
}
