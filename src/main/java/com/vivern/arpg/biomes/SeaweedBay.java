package com.vivern.arpg.biomes;

import com.vivern.arpg.dimensions.aquatica.AquaticaChunkGenerator;
import com.vivern.arpg.main.BlocksRegister;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.chunk.ChunkPrimer;

public class SeaweedBay extends Biome {
   public SeaweedBay() {
      super(
         new BiomeProperties("SeaweedBay")
            .setBaseHeight(-0.9F)
            .setHeightVariation(0.15F)
            .setTemperature(0.9F)
            .setWaterColor(11921407)
            .setRainfall(0.0F)
            .setRainDisabled()
      );
      this.topBlock = Blocks.SAND.getDefaultState();
      this.fillerBlock = BlocksRegister.SHELLROCK.getDefaultState();
      this.decorator = new SeaweedBayDecorator();
   }

   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
      AquaticaChunkGenerator.generateBiomeTerrain(this, worldIn, rand, chunkPrimerIn, x, z, noiseVal);
   }
}
