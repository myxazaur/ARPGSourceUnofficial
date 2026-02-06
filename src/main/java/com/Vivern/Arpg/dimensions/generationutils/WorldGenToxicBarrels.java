package com.Vivern.Arpg.dimensions.generationutils;

import com.Vivern.Arpg.blocks.ToxicBarrel;
import com.Vivern.Arpg.main.BlocksRegister;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenToxicBarrels extends WorldGenerator {
   public int samples = 64;
   public int sizeXZ = 8;
   public int sizeY = 4;

   public WorldGenToxicBarrels() {
   }

   public WorldGenToxicBarrels(int samples, int sizeXZ, int sizeY) {
      this.samples = samples;
      this.sizeXZ = sizeXZ;
      this.sizeY = sizeY;
   }

   public boolean generate(World worldIn, Random rand, BlockPos position) {
      for (int i = 0; i < this.samples; i++) {
         BlockPos blockpos = position.add(
            rand.nextInt(this.sizeXZ) - rand.nextInt(this.sizeXZ),
            rand.nextInt(this.sizeY) - rand.nextInt(this.sizeY),
            rand.nextInt(this.sizeXZ) - rand.nextInt(this.sizeXZ)
         );
         IBlockState state = worldIn.getBlockState(blockpos.down());
         if (worldIn.isAirBlock(blockpos) && blockpos.getY() < 255 && !state.getMaterial().isLiquid() && state.isFullCube()) {
            worldIn.setBlockState(blockpos, BlocksRegister.TOXICBARREL.getDefaultState().withProperty(ToxicBarrel.VARIANT, rand.nextInt(13)));
         }
      }

      return true;
   }
}
