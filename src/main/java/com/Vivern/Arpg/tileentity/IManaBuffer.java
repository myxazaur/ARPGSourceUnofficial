package com.Vivern.Arpg.tileentity;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLogic;

public interface IManaBuffer {
   Material MAGIC_BLOCK = new MaterialLogic(MapColor.GOLD);

   ManaBuffer getManaBuffer();

   default boolean canProvideMana() {
      return true;
   }
}
