package com.Vivern.Arpg.blocks;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class Selenite extends Block {
   public Selenite() {
      super(Material.ROCK);
      this.setRegistryName("selenite");
      this.setTranslationKey("selenite");
      this.blockHardness = 2.5F;
      this.blockResistance = 2.0F;
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
   }

   public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos, Predicate<IBlockState> target) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return true;
   }
}
