package com.vivern.arpg.loot;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class OreDrop {
   public final int min;
   public final int max;
   public Item drop;
   public final int fortuneState;

   public OreDrop(int min, int max, Item drop, int fortuneState) {
      this.min = min;
      this.max = max;
      this.drop = drop;
      this.fortuneState = fortuneState;
   }

   public ItemStack getStackDropped(World worldIn, BlockPos pos, IBlockState state, int fortune, Random rand) {
      int count;
      switch (this.fortuneState) {
         case 0:
            count = rand.nextInt(this.max - this.min + 1 + fortune) + this.min;
            break;
         case 1:
            int newmin = Math.min(this.min + Math.round(fortune * rand.nextFloat()), this.max);
            count = rand.nextInt(this.max - newmin + 1) + newmin;
            break;
         case 2:
            count = MathHelper.ceil((rand.nextInt(this.max - this.min + 1) + this.min) * Math.abs(rand.nextGaussian()) / 3.0 * fortune);
            break;
         case 3:
            count = rand.nextInt(this.max - this.min + 1) + this.min;
            break;
         case 4:
            count = MathHelper.ceil((rand.nextInt(this.max - this.min + 1) + this.min) * Math.abs(rand.nextGaussian()) * fortune);
            break;
         case 5:
            count = rand.nextInt(this.max - this.min + 1) + this.min + (rand.nextFloat() < fortune / 3.75F ? 1 : 0);
            break;
         case 6:
            count = rand.nextInt(this.max - this.min + 1) + this.min + (int)(rand.nextFloat() * (fortune / 3.0F) * this.max / 2.0F);
            break;
         case 7:
            count = rand.nextInt(this.max - this.min + 1)
               + this.min
               + (rand.nextFloat() < fortune / 4.0F ? 1 : 0)
               + (rand.nextFloat() < fortune / 4.0F ? 1 : 0);
            break;
         default:
            count = rand.nextInt(this.max - this.min + 1) + this.min;
      }

      return new ItemStack(this.drop, count);
   }
}
