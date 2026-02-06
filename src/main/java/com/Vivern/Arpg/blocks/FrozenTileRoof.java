package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FrozenTileRoof extends Block implements IBlockHardBreak {
   public FrozenTileRoof() {
      super(Material.ROCK);
      this.setRegistryName("frozen_tile_roof");
      this.setTranslationKey("frozen_tile_roof");
      this.blockHardness = BlocksRegister.HR_FROZEN_ROOF.HARDNESS;
      this.blockResistance = BlocksRegister.HR_FROZEN_ROOF.RESISTANCE;
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setHarvestLevel("pickaxe", BlocksRegister.HR_FROZEN_ROOF.LVL);
   }

   public boolean isFullCube(IBlockState state) {
      return true;
   }

   @Override
   public float getBlockBreakingSpeed(World world, String tool, int toolLevel, IBlockState state, BlockPos pos, float originalSpeed) {
      return BlocksRegister.HR_FROZEN_ROOF.getBlockBreakingSpeed(world, tool, toolLevel, state, pos, originalSpeed);
   }
}
