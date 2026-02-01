package com.vivern.arpg.recipes;

import com.vivern.arpg.main.GetMOP;
import java.util.ArrayList;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTurnCollection {
   public ArrayList<BlockShardTypeTurn> list = new ArrayList<>();
   public static int ATTEMPTS_AMOUNT = 8;

   public BlockTurnCollection add(BlockShardTypeTurn turn) {
      this.list.add(turn);
      return this;
   }

   public BlockTurnCollection add(int weight, float energyAmount, Block blockFrom, Block blockTo) {
      this.list.add(new BlockShardTypeTurn.SimpleBlockShardTypeTurn(weight, energyAmount, blockFrom, blockTo));
      return this;
   }

   public void affectBlock(Object dealer, World world, BlockPos blockPos, float elementAmount) {
      int id = byWeight(GetMOP.rand, this.list);
      if (id >= 0 && id < this.list.size()) {
         BlockShardTypeTurn turn = this.list.get(id);
         if (GetMOP.rand.nextFloat() < elementAmount / turn.energyAmount) {
            this.recursiveAffectBlock(turn, dealer, world, blockPos, elementAmount, ATTEMPTS_AMOUNT, null);
         }
      }
   }

   public boolean recursiveAffectBlock(
      BlockShardTypeTurn turn, Object dealer, World world, BlockPos blockPos, float elementAmount, int lastRecursions, @Nullable EnumFacing lastFacing
   ) {
      if (lastRecursions > 0) {
         if (!turn.turn(dealer, world, blockPos, elementAmount)) {
            EnumFacing facing = GetMOP.getRandomFacingExcept(lastFacing);
            return this.recursiveAffectBlock(turn, dealer, world, blockPos.offset(facing), elementAmount, lastRecursions - 1, facing.getOpposite());
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public static int byWeight(Random rand, ArrayList<BlockShardTypeTurn> list) {
      int summ = 0;

      for (int i = 0; i < list.size(); i++) {
         summ += list.get(i).weight;
      }

      return byWeight(summ, rand, list);
   }

   public static int byWeight(int summ, Random rand, ArrayList<BlockShardTypeTurn> list) {
      int r = rand.nextInt(summ);
      int all = 0;

      for (int i = 0; i < list.size(); i++) {
         int weight = list.get(i).weight;
         all += weight;
         if (r < all) {
            return i;
         }
      }

      return -1;
   }
}
