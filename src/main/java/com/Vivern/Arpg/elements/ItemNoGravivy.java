package com.Vivern.Arpg.elements;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;

public class ItemNoGravivy extends ItemItem {
   public ItemNoGravivy(String name, CreativeTabs tab, int maxdamage, int maxstacksize) {
      super(name, tab, maxdamage, maxstacksize);
   }

   public boolean onEntityItemUpdate(EntityItem entityItem) {
      if (!entityItem.hasNoGravity()) {
         entityItem.setNoGravity(true);
      }

      return super.onEntityItemUpdate(entityItem);
   }
}
