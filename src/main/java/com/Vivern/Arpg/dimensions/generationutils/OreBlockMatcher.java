//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.dimensions.generationutils;

import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class OreBlockMatcher implements Predicate<IBlockState> {
   public final Block[] blocks;

   public OreBlockMatcher(Block[] blocksType) {
      this.blocks = blocksType;
   }

   public boolean apply(@Nullable IBlockState apply) {
      if (apply != null) {
         for (Block bl : this.blocks) {
            if (bl == apply.getBlock()) {
               return true;
            }
         }
      }

      return false;
   }
}
