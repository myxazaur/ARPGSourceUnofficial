package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class Calcite extends Block implements IBlockHardBreak {
   public Calcite() {
      super(Material.ROCK);
      this.setRegistryName("calcite");
      this.setTranslationKey("calcite");
      this.blockHardness = 10.0F;
      this.blockResistance = 17.0F;
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
   }

   @Override
   public BlocksRegister.Hardres getHardres() {
      return BlocksRegister.HR_DUNGEON_STONES;
   }

   public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos, Predicate<IBlockState> target) {
      return true;
   }

   public boolean isFullCube(IBlockState state) {
      return true;
   }
}
