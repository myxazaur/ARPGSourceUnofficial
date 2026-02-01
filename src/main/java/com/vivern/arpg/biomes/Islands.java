package com.vivern.arpg.biomes;

import com.vivern.arpg.dimensions.aquatica.AquaticaChunkGenerator;
import com.vivern.arpg.main.BlocksRegister;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.chunk.ChunkPrimer;

public class Islands extends Biome {
   public Islands() {
      super(
         new BiomeProperties("Islands")
            .setBaseHeight(5.75F)
            .setHeightVariation(0.08F)
            .setTemperature(0.95F)
            .setWaterColor(11921407)
            .setRainfall(0.0F)
            .setRainDisabled()
      );
      this.topBlock = Blocks.SAND.getDefaultState();
      this.fillerBlock = BlocksRegister.CHALKROCK.getDefaultState();
      this.decorator = new IslandsDecorator();
   }

   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
      AquaticaChunkGenerator.generateBiomeTerrain(this, worldIn, rand, chunkPrimerIn, x, z, noiseVal);
   }

   public int getGrassColorAtPos(BlockPos pos) {
      return 11001929;
   }

   public int getFoliageColorAtPos(BlockPos pos) {
      return 11001929;
   }
}
