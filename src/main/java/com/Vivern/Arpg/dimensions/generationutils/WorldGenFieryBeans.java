package com.Vivern.Arpg.dimensions.generationutils;

import com.Vivern.Arpg.blocks.FieryBeanBlock;
import com.Vivern.Arpg.main.BlocksRegister;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenFieryBeans implements IWorldGenerator {
   public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
      int dim = world.provider.getDimension();
      if ((dim == 0 || dim == 103) && random.nextFloat() < (dim == 0 ? 0.06F : 0.04F)) {
         int x = chunkX * 16 + random.nextInt(16);
         int z = chunkZ * 16 + random.nextInt(16);
         BlockPos poss = new BlockPos(x, 0, z);
         if (BiomeDictionary.hasType(world.getBiome(poss), Type.JUNGLE)) {
            this.runGenerator(6 + random.nextInt(14), world, random, world.getTopSolidOrLiquidBlock(poss), false);
         }
      }
   }

   public void runGenerator(int height, World world, Random rand, BlockPos pos, boolean checkReplace) {
      Block d = world.getBlockState(pos).getBlock();

      int y;
      for (y = 0; y < height; y++) {
         BlockPos poss = pos.up(y);
         if (checkReplace && !world.getBlockState(poss).getBlock().isReplaceable(world, poss)) {
            break;
         }

         world.setBlockState(poss, BlocksRegister.FIERYBEANLOG.getDefaultState());
      }

      GenerationHelper.placeStruct(
         world, pos.up(y), rand, ":fiery_beans_tree_" + (rand.nextInt(4) + 1), 5, 0, rand.nextInt(4), ReplaceOnlyReplaceable.instance
      );

      for (int y2 = y + 2; y2 > 3; y2--) {
         if (rand.nextFloat() < y2 / 25.0F) {
            EnumFacing f = EnumFacing.HORIZONTALS[rand.nextInt(4)];
            BlockPos poss = pos.up(y2).offset(f);
            if (world.getBlockState(poss).getBlock().isReplaceable(world, poss)
               && world.getBlockState(poss.up()).getBlock() != BlocksRegister.FIERYBEANBLOCK) {
               world.setBlockState(
                  poss,
                  BlocksRegister.FIERYBEANBLOCK.getDefaultState().withProperty(FieryBeanBlock.FACING, f).withProperty(FieryBeanBlock.AGE, rand.nextInt(3))
               );
            }
         }
      }
   }
}
