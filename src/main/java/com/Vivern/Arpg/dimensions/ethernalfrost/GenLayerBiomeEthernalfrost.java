package com.Vivern.Arpg.dimensions.ethernalfrost;

import com.Vivern.Arpg.main.BiomesRegister;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerBiomeEthernalfrost extends GenLayer {
   private Biome[] allowedBiomes = new Biome[]{
      BiomesRegister.FROZEN_WARM_FOREST,
      BiomesRegister.FROZEN_FOREST,
      BiomesRegister.ICE_HILLS,
      BiomesRegister.FROZEN_MEADOW,
      BiomesRegister.FROZEN_MOUNTAINS[0]
   };

   public GenLayerBiomeEthernalfrost(long seed) {
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
