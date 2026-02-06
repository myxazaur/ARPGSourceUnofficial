package com.Vivern.Arpg.recipes;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockShardTypeTurn {
   public int weight;
   public float energyAmount;

   public BlockShardTypeTurn(int weight, float energyAmount) {
      this.weight = weight;
      this.energyAmount = energyAmount;
   }

   public abstract boolean turn(Object var1, World var2, BlockPos var3, float var4);

   public static class FireBlockShardTypeTurn extends BlockShardTypeTurn {
      public Block blockFrom;
      public Block blockTo;

      public FireBlockShardTypeTurn(int weight, float energyAmount, Block blockFrom, Block blockTo) {
         super(weight, energyAmount);
         this.blockFrom = blockFrom;
         this.blockTo = blockTo;
      }

      @Override
      public boolean turn(Object dealer, World world, BlockPos blockPos, float elementAmount) {
         if (world.getBlockState(blockPos).getBlock() == this.blockFrom) {
            world.setBlockState(blockPos, this.blockTo.getDefaultState());
            return true;
         } else {
            return false;
         }
      }
   }

   public static class SimpleBlockShardTypeTurn extends BlockShardTypeTurn {
      public Block blockFrom;
      public Block blockTo;

      public SimpleBlockShardTypeTurn(int weight, float energyAmount, Block blockFrom, Block blockTo) {
         super(weight, energyAmount);
         this.blockFrom = blockFrom;
         this.blockTo = blockTo;
      }

      @Override
      public boolean turn(Object dealer, World world, BlockPos blockPos, float elementAmount) {
         if (world.getBlockState(blockPos).getBlock() == this.blockFrom) {
            world.setBlockState(blockPos, this.blockTo.getDefaultState());
            return true;
         } else {
            return false;
         }
      }
   }
}
