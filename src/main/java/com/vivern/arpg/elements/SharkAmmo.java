package com.vivern.arpg.elements;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class SharkAmmo extends Item {
   public SharkAmmo() {
      this.setRegistryName("shark_ammo");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("shark_ammo");
   }
}
