package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Dolerite extends Block implements IBlockHardBreak {
   public Dolerite() {
      super(Material.ROCK);
      this.setRegistryName("dolerite");
      this.setTranslationKey("dolerite");
      this.blockHardness = 15.0F;
      this.blockResistance = 27.0F;
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
   }

   public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos, Predicate<IBlockState> target) {
      return true;
   }

   public boolean isFullCube(IBlockState state) {
      return true;
   }

   @Override
   public float getBlockBreakingSpeed(World world, String tool, int toolLevel, IBlockState state, BlockPos pos, float originalSpeed) {
      return BlocksRegister.HR_SNOWICE_GLACIER.getBlockBreakingSpeed(world, tool, toolLevel, state, pos, originalSpeed);
   }
}
