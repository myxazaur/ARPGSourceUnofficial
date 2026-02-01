package com.vivern.arpg.elements;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "arpg"
)
public class ItemGun extends Item {
   public boolean getIsAimed() {
      return false;
   }
}
