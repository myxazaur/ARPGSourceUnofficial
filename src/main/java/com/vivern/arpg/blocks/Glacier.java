package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Glacier extends Block implements IBlockHardBreak {
   public Glacier() {
      super(Material.ROCK);
      this.setRegistryName("glacier");
      this.setTranslationKey("glacier");
      this.blockHardness = BlocksRegister.HR_SNOWICE_GLACIER.HARDNESS;
      this.blockResistance = BlocksRegister.HR_SNOWICE_GLACIER.RESISTANCE;
      this.setHarvestLevel("pickaxe", BlocksRegister.HR_SNOWICE_GLACIER.LVL);
      this.setSoundType(SoundType.GLASS);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
   }

   public boolean isFullCube(IBlockState state) {
      return true;
   }

   public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos, Predicate<IBlockState> target) {
      return true;
   }

   public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state) {
      worldIn.setBlockState(pos, Blocks.FLOWING_WATER.getStateFromMeta(2));
   }

   @Override
   public float getBlockBreakingSpeed(World world, String tool, int toolLevel, IBlockState state, BlockPos pos, float originalSpeed) {
      return BlocksRegister.HR_SNOWICE_GLACIER.getBlockBreakingSpeed(world, tool, toolLevel, state, pos, originalSpeed);
   }
}
