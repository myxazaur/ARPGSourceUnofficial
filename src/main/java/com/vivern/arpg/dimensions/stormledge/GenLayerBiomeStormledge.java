package com.vivern.arpg.dimensions.stormledge;

import com.vivern.arpg.main.BiomesRegister;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerBiomeStormledge extends GenLayer {
   private Biome[] allowedBiomes = new Biome[]{
      BiomesRegister.ELECTROFERN_WASTELANDS,
      BiomesRegister.ELECTROFERN_GROVE,
      BiomesRegister.ELECTROFERN_GROVE,
      BiomesRegister.SKY_ISLANDS,
      BiomesRegister.SKY_ISLANDS,
      BiomesRegister.SKY_ISLANDS,
      BiomesRegister.ARTHROHELIA_ISLANDS,
      BiomesRegister.ARTHROHELIA_ISLANDS,
      BiomesRegister.CRYSTALLIZED_SKY_ISLANDS,
      BiomesRegister.CRYSTALLIZED_SKY_ISLANDS
   };

   public GenLayerBiomeStormledge(long seed) {
      super(seed);
   }

   public int[] getInts(int x, int z, int width, int depth) {
      int[] dest = IntCache.getIntCache(width * depth);

      for (int dz = 0; dz < depth; dz++) {
         for (int dx = 0; dx < width; dx++) {
            this.initChunkSeed(dx + x, dz + z);
            dest[dx + dz * width] = Biome.getIdForBiome(this.allowedBiomes[this.nextInt(this.allowedBiomes.length)]);
         }
      }

      return dest;
   }
}
