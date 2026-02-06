package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.dimensions.aquatica.AquaticaChunkGenerator;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.chunk.ChunkPrimer;

public class Corals extends Biome {
   public Corals() {
      super(
         new BiomeProperties("Corals")
            .setBaseHeight(-1.0F)
            .setHeightVariation(0.15F)
            .setTemperature(0.95F)
            .setWaterColor(11921407)
            .setRainfall(0.0F)
            .setRainDisabled()
      );
      this.topBlock = Blocks.SAND.getDefaultState();
      this.fillerBlock = Blocks.SAND.getDefaultState();
      this.decorator = new CoralsDecorator();
   }

   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
      AquaticaChunkGenerator.generateBiomeTerrain(this, worldIn, rand, chunkPrimerIn, x, z, noiseVal);
   }
}
