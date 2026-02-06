package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class FrozenStone extends BlockBlockHard {
   public FrozenStone() {
      super(
         Material.ROCK,
         "frozen_stone",
         BlocksRegister.HR_FROZEN_STONE.HARDNESS,
         BlocksRegister.HR_FROZEN_STONE.RESISTANCE,
         BlocksRegister.HR_FROZEN_STONE.SLOW,
         BlocksRegister.HR_FROZEN_STONE.FAST,
         BlocksRegister.HR_FROZEN_STONE.LVL,
         "pickaxe",
         true
      );
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
   }

   @Override
   public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos, Predicate<IBlockState> target) {
      return true;
   }

   @Override
   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      return Item.getItemFromBlock(BlocksRegister.FROZENCOBBLE);
   }

   @Override
   public boolean isFullCube(IBlockState state) {
      return true;
   }
}
