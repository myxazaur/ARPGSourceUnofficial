package com.vivern.arpg.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;

public class FrozenStoneBricks extends Block {
   public FrozenStoneBricks() {
      super(Material.ROCK);
      this.setRegistryName("frozen_stone_bricks");
      this.setTranslationKey("frozen_stone_bricks");
      this.blockHardness = 2.0F;
      this.blockResistance = 1.5F;
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
   }

   public boolean isFullCube(IBlockState state) {
      return true;
   }
}
