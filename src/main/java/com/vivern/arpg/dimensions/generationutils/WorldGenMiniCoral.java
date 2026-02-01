package com.vivern.arpg.dimensions.generationutils;

import com.vivern.arpg.blocks.Corallimorpha;
import com.vivern.arpg.blocks.MiniCoral;
import com.vivern.arpg.main.GetMOP;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenMiniCoral extends WorldGenerator {
   public Block flower;
   public IBlockState state;
   public boolean corallimorpha = false;
   public int samples = 64;
   public int sizeXZ = 8;
   public int sizeY = 4;

   public WorldGenMiniCoral(Block flower) {
      this.setGeneratedBlock(flower);
   }

   public WorldGenMiniCoral(Block flower, int samples, int sizeXZ, int sizeY) {
      this.setGeneratedBlock(flower);
      this.samples = samples;
      this.sizeXZ = Math.min(8, sizeXZ);
      this.sizeY = sizeY;
   }

   public WorldGenMiniCoral(Block flower, int meta, int samples, int sizeXZ, int sizeY) {
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

   public WorldGenMiniCoral setcorallimorpha() {
      this.corallimorpha = true;
      return this;
   }

   public boolean generate(World worldIn, Random rand, BlockPos position) {
      if (this.corallimorpha) {
         for (int i = 0; i < this.samples; i++) {
            BlockPos blockpos = position.add(
               rand.nextInt(this.sizeXZ) - rand.nextInt(this.sizeXZ),
               this.sizeY == 0 ? 0 : rand.nextInt(this.sizeY) - rand.nextInt(this.sizeY),
               rand.nextInt(this.sizeXZ) - rand.nextInt(this.sizeXZ)
            );
            EnumFacing facing = this.getRandomFacing(worldIn, rand, blockpos);
            if (facing != null
               && worldIn.getBlockState(blockpos).getBlock() == Blocks.WATER
               && blockpos.getY() > 0
               && this.flower.canPlaceBlockOnSide(worldIn, blockpos, facing)) {
               worldIn.setBlockState(blockpos, this.state.withProperty(Corallimorpha.FACING, facing).withProperty(Corallimorpha.WET, true), 2);
            }
         }

         return true;
      } else {
         for (int ix = 0; ix < this.samples; ix++) {
            BlockPos blockpos = position.add(
               rand.nextInt(this.sizeXZ) - rand.nextInt(this.sizeXZ),
               this.sizeY == 0 ? 0 : rand.nextInt(this.sizeY) - rand.nextInt(this.sizeY),
               rand.nextInt(this.sizeXZ) - rand.nextInt(this.sizeXZ)
            );
            EnumFacing facing = this.getRandomFacing(worldIn, rand, blockpos);
            if (facing != null
               && worldIn.getBlockState(blockpos).getBlock() == Blocks.WATER
               && blockpos.getY() > 0
               && this.flower.canPlaceBlockOnSide(worldIn, blockpos, facing)) {
               worldIn.setBlockState(blockpos, this.state.withProperty(MiniCoral.FACING, facing).withProperty(MiniCoral.WET, true), 2);
            }
         }

         return true;
      }
   }

   public EnumFacing getRandomFacing(World worldIn, Random rand, BlockPos position) {
      int f = rand.nextInt(6);

      for (int i = 0; i < 6; i++) {
         f = GetMOP.next(f, 1, 6);
         EnumFacing facing = EnumFacing.byIndex(f);
         if (worldIn.getBlockState(position.offset(facing)).isOpaqueCube()) {
            return facing.getOpposite();
         }
      }

      return null;
   }
}
