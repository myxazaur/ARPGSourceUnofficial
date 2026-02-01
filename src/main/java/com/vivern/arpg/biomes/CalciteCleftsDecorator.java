package com.vivern.arpg.biomes;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkGeneratorSettings.Factory;

class CalciteCleftsDecorator extends BiomeDecorator {
   public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
      if (this.decorating) {
         throw new RuntimeException("Already decorating");
      } else {
         this.chunkProviderSettings = Factory.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
         this.chunkPos = pos;
         this.decorating = false;
      }
   }
}
