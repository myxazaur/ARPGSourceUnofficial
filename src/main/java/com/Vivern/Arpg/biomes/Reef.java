package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.dimensions.aquatica.AquaticaChunkGenerator;
import com.Vivern.Arpg.main.BlocksRegister;
import java.util.Random;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSand.EnumType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.chunk.ChunkPrimer;

public class Reef extends Biome {
   public Reef() {
      super(
         new BiomeProperties("Reef").setBaseHeight(0.05F).setHeightVariation(0.45F).setTemperature(0.95F).setWaterColor(11921407).setRainfall(0.0F).setRainDisabled()
      );
      this.topBlock = BlocksRegister.SEASTONE.getDefaultState();
      this.fillerBlock = BlocksRegister.SHELLROCK.getDefaultState();
      this.decorator = new ReefDecorator();
   }

   public int getGrassColorAtPos(BlockPos pos) {
      return 7395905;
   }

   public int getFoliageColorAtPos(BlockPos pos) {
      return 7395905;
   }

   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
      this.generateReefTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
   }

   public final void generateReefTerrain(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
      int i = worldIn.getSeaLevel();
      IBlockState iblockstate = this.topBlock;
      IBlockState iblockstate1 = this.fillerBlock;
      IBlockState iblockstateGrass = Blocks.GRASS.getDefaultState();
      IBlockState iblockstateDirt = Blocks.DIRT.getDefaultState();
      IBlockState iblockstateSand = Blocks.SAND.getDefaultState();
      int j = -1;
      int k = (int)(noiseVal / 3.0 + 3.0 + rand.nextDouble() * 0.25);
      int l = x & 15;
      int i1 = z & 15;
      MutableBlockPos blockpos$mutableblockpos = new MutableBlockPos();

      for (int j1 = 255; j1 >= 0; j1--) {
         if (j1 <= rand.nextInt(5)) {
            chunkPrimerIn.setBlockState(i1, j1, l, BEDROCK);
         } else {
            IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);
            if (iblockstate2.getMaterial() == Material.AIR) {
               j = -1;
            } else if (iblockstate2.getBlock() == AquaticaChunkGenerator.STONE.getBlock()) {
               if (j == -1) {
                  if (k <= 0) {
                     iblockstate = AIR;
                     iblockstate1 = AquaticaChunkGenerator.STONE;
                  } else if (j1 >= i - 4 && j1 <= i + 1) {
                     iblockstate = this.topBlock;
                     iblockstate1 = this.fillerBlock;
                  }

                  if (j1 < i && (iblockstate == null || iblockstate.getMaterial() == Material.AIR)) {
                     if (this.getTemperature(blockpos$mutableblockpos.setPos(x, j1, z)) < 0.15F) {
                        iblockstate = ICE;
                     } else {
                        iblockstate = WATER;
                     }
                  }

                  j = k;
                  if (j1 >= i - 1) {
                     chunkPrimerIn.setBlockState(i1, j1, l, j1 == 194 ? iblockstateSand : (j1 >= 195 ? iblockstateGrass : iblockstate));
                  } else if (j1 < i - 7 - k) {
                     iblockstate = AIR;
                     iblockstate1 = AquaticaChunkGenerator.STONE;
                     chunkPrimerIn.setBlockState(i1, j1, l, GRAVEL);
                  } else {
                     chunkPrimerIn.setBlockState(i1, j1, l, j1 >= 195 ? iblockstateDirt : iblockstate1);
                  }
               } else if (j > 0) {
                  j--;
                  chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
                  if (j == 0 && iblockstate1.getBlock() == Blocks.SAND && k > 1) {
                     j = rand.nextInt(4) + Math.max(0, j1 - 63);
                     iblockstate1 = iblockstate1.getValue(BlockSand.VARIANT) == EnumType.RED_SAND ? RED_SANDSTONE : SANDSTONE;
                  }
               }
            }
         }
      }
   }
}
