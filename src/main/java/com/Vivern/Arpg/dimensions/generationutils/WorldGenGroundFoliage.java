package com.Vivern.Arpg.dimensions.generationutils;

import com.Vivern.Arpg.blocks.HealthFlowerLeaves;
import com.Vivern.Arpg.blocks.ManaFlowerLeaves;
import com.Vivern.Arpg.main.BlocksRegister;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenGroundFoliage extends WorldGenerator {
   public Block flower;
   public IBlockState state;
   public int samples = 64;
   public int sizeXZ = 8;
   public int sizeY = 4;

   public WorldGenGroundFoliage(Block flower) {
      this.setGeneratedBlock(flower);
   }

   public WorldGenGroundFoliage(Block flower, int samples, int sizeXZ, int sizeY) {
      this.setGeneratedBlock(flower);
      this.samples = samples;
      this.sizeXZ = Math.min(8, sizeXZ);
      this.sizeY = sizeY;
   }

   public WorldGenGroundFoliage(Block flower, int meta, int samples, int sizeXZ, int sizeY) {
      this.setGeneratedBlock(flower, meta);
      this.samples = samples;
      this.sizeXZ = Math.min(8, sizeXZ);
      this.sizeY = sizeY;
   }

   public void setGeneratedBlock(Block flower) {
      this.flower = flower;
      this.state = flower.getDefaultState();
   }

   public void setGeneratedBlock(Block flower, int meta) {
      this.flower = flower;
      this.state = flower.getStateFromMeta(meta);
   }

   public boolean generate(World worldIn, Random rand, BlockPos position) {
      for (int i = 0; i < this.samples; i++) {
         BlockPos blockpos = position.add(
            rand.nextInt(this.sizeXZ) - rand.nextInt(this.sizeXZ),
            this.sizeY == 0 ? 0 : rand.nextInt(this.sizeY) - rand.nextInt(this.sizeY),
            rand.nextInt(this.sizeXZ) - rand.nextInt(this.sizeXZ)
         );
         if (worldIn.isAirBlock(blockpos) && blockpos.getY() < 255 && this.flower.canPlaceBlockAt(worldIn, blockpos)) {
            worldIn.setBlockState(blockpos, this.state);
         }
      }

      return true;
   }

   public static class WorldGenManaAndHealthFoliage extends WorldGenGroundFoliage {
      public WorldGenManaAndHealthFoliage(Block flower, int samples, int sizeXZ, int sizeY) {
         super(flower, samples, sizeXZ, sizeY);
      }

      @Override
      public boolean generate(World worldIn, Random rand, BlockPos position) {
         for (int i = 0; i < this.samples; i++) {
            BlockPos blockpos = position.add(
               rand.nextInt(this.sizeXZ) - rand.nextInt(this.sizeXZ),
               this.sizeY == 0 ? 0 : rand.nextInt(this.sizeY) - rand.nextInt(this.sizeY),
               rand.nextInt(this.sizeXZ) - rand.nextInt(this.sizeXZ)
            );
            if (worldIn.isAirBlock(blockpos) && blockpos.getY() < 255 && this.flower.canPlaceBlockAt(worldIn, blockpos)) {
               worldIn.setBlockState(blockpos, this.state);
               if (this.state.getBlock() == BlocksRegister.MANAFLOWERLEAVES) {
                  ManaFlowerLeaves.growForGeneration(worldIn, blockpos, rand);
               }

               if (this.state.getBlock() == BlocksRegister.HEALTHFLOWERLEAVES) {
                  HealthFlowerLeaves.growForGeneration(worldIn, blockpos, rand);
               }
            }
         }

         return true;
      }
   }
}
