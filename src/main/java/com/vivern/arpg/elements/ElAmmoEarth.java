package com.vivern.arpg.elements;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ElAmmoEarth extends Item {
   public ElAmmoEarth() {
      this.setRegistryName("earth_focus_ammo");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("earth_focus_ammo");
   }
}
