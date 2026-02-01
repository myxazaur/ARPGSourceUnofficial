package com.vivern.arpg.elements;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class FireworkPack extends Item {
   public FireworkPack() {
      this.setRegistryName("firework_pack");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("firework_pack");
   }
}
