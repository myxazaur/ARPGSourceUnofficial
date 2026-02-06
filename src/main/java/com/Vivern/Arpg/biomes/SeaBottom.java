package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.dimensions.aquatica.AquaticaChunkGenerator;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.chunk.ChunkPrimer;

public class SeaBottom extends Biome {
   public SeaBottom() {
      super(
         new BiomeProperties("Sea bottom")
            .setBaseHeight(-3.6F)
            .setHeightVariation(0.2F)
            .setTemperature(0.9F)
            .setWaterColor(11921407)
            .setRainfall(0.0F)
            .setRainDisabled()
      );
      this.topBlock = Blocks.GRAVEL.getDefaultState();
      this.fillerBlock = Blocks.STONE.getDefaultState();
      this.decorator = new SeaBottomDecorator();
   }

   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
      AquaticaChunkGenerator.generateBiomeTerrain(this, worldIn, rand, chunkPrimerIn, x, z, noiseVal);
   }
}
