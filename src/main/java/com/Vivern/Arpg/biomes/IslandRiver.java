//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.dimensions.aquatica.AquaticaChunkGenerator;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.chunk.ChunkPrimer;

public class IslandRiver extends Biome {
   public IslandRiver() {
      super(
         new BiomeProperties("Island river")
            .setBaseHeight(5.25F)
            .setHeightVariation(0.01F)
            .setTemperature(0.95F)
            .setWaterColor(11921407)
            .setRainfall(0.0F)
            .setRainDisabled()
      );
      this.topBlock = Blocks.SAND.getDefaultState();
      this.fillerBlock = Blocks.SANDSTONE.getDefaultState();
   }

   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
      AquaticaChunkGenerator.generateBiomeTerrain(this, worldIn, rand, chunkPrimerIn, x, z, noiseVal);
   }
}
