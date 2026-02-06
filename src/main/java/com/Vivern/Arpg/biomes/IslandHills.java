package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.dimensions.aquatica.AquaticaChunkGenerator;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.chunk.ChunkPrimer;

public class IslandHills extends Biome {
   public IslandHills() {
      super(
         new BiomeProperties("Island hills")
            .setBaseHeight(7.0F)
            .setHeightVariation(0.15F)
            .setTemperature(0.95F)
            .setWaterColor(11921407)
            .setRainfall(0.0F)
            .setRainDisabled()
      );
      this.topBlock = Blocks.GRASS.getDefaultState();
      this.fillerBlock = Blocks.SANDSTONE.getDefaultState();
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
