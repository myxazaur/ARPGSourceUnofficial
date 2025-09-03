//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

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
