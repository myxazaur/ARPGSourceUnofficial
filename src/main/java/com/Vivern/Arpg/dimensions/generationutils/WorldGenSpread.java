package com.Vivern.Arpg.dimensions.generationutils;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenSpread extends WorldGenerator {
   public Block block;
   public IBlockState state;
   public Block blockGround;
   public int samples = 64;
   public int sizeXZ = 8;
   public int sizeY = 4;
   public boolean nocheckAir = false;

   public WorldGenSpread(Block block, @Nullable Block blockGround) {
      this.setGeneratedBlock(block);
      this.blockGround = blockGround;
   }

   public WorldGenSpread(Block block, int samples, int sizeXZ, int sizeY, @Nullable Block blockGround) {
      this.setGeneratedBlock(block);
      this.samples = samples;
      this.sizeXZ = Math.min(8, sizeXZ);
      this.sizeY = sizeY;
      this.blockGround = blockGround;
   }

   public WorldGenSpread(Block block, int samples, int sizeXZ, int sizeY, @Nullable Block blockGround, boolean nocheckAir) {
      this.setGeneratedBlock(block);
      this.samples = samples;
      this.sizeXZ = Math.min(8, sizeXZ);
      this.sizeY = sizeY;
      this.blockGround = blockGround;
      this.nocheckAir = nocheckAir;
   }

   public void setGeneratedBlock(Block block) {
      this.block = block;
      this.state = block.getDefaultState();
   }

   public boolean generate(World worldIn, Random rand, BlockPos position) {
      for (int i = 0; i < this.samples; i++) {
         BlockPos blockpos = position.add(
            rand.nextInt(this.sizeXZ) - rand.nextInt(this.sizeXZ),
            this.sizeY == 0 ? 0 : rand.nextInt(this.sizeY) - rand.nextInt(this.sizeY),
            rand.nextInt(this.sizeXZ) - rand.nextInt(this.sizeXZ)
         );
         if ((this.nocheckAir || worldIn.isAirBlock(blockpos))
            && blockpos.getY() < 255
            && this.block.canPlaceBlockAt(worldIn, blockpos)
            && (this.blockGround == null || worldIn.getBlockState(blockpos.down()).getBlock() == this.blockGround)) {
            worldIn.setBlockState(blockpos, this.state, 2);
         }
      }

      return true;
   }
}
