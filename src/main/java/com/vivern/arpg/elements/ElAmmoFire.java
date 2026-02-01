package com.vivern.arpg.elements;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ElAmmoFire extends Item {
   public ElAmmoFire() {
      this.setRegistryName("fire_focus_ammo");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("fire_focus_ammo");
   }
}
