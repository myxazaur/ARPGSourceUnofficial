//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.dimensions.dungeon;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerFixDungeon {
   public static GenLayer[] createWorld(long seed) {
      GenLayer biomes = new GenLayerBiomeDungeon(1L);
      biomes = new GenLayerZoom(1000L, biomes);
      biomes = new GenLayerZoom(1001L, biomes);
      biomes = new GenLayerZoom(1002L, biomes);
      biomes = new GenLayerZoom(1003L, biomes);
      biomes = new GenLayerZoom(1004L, biomes);
      biomes = new GenLayerZoom(1005L, biomes);
      GenLayer genlayervoronoizoom = new GenLayerVoronoiZoom(10L, biomes);
      biomes.initWorldGenSeed(seed);
      genlayervoronoizoom.initWorldGenSeed(seed);
      return new GenLayer[]{biomes, genlayervoronoizoom};
   }

   public static class GenLayerReplacer extends GenLayer {
      public Biome from;
      public Biome to;
      public int toId;
      public int fromId;

      public GenLayerReplacer(long p_i2128_1_, GenLayer p_i2128_3_, Biome from, Biome to) {
         super(p_i2128_1_);
         super.parent = p_i2128_3_;
         this.from = from;
         this.to = to;
         this.toId = Biome.getIdForBiome(to);
         this.fromId = Biome.getIdForBiome(from);
      }

      public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
         int i = areaX - 1;
         int j = areaY - 1;
         int k = areaWidth + 2;
         int l = areaHeight + 2;
         int[] aint = this.parent.getInts(i, j, k, l);
         int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);

         for (int i1 = 0; i1 < areaHeight; i1++) {
            for (int j1 = 0; j1 < areaWidth; j1++) {
               int k2 = aint[j1 + 1 + (i1 + 1) * k];
               this.initChunkSeed(j1 + areaX, i1 + areaY);
               if (k2 == this.fromId) {
                  aint1[j1 + i1 * areaWidth] = this.toId;
               }
            }
         }

         return aint1;
      }
   }
}
