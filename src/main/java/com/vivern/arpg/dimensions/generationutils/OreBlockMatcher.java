package com.vivern.arpg.dimensions.generationutils;

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
