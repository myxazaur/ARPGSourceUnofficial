package com.Vivern.Arpg.dimensions.ethernalfrost;

import com.Vivern.Arpg.dimensions.aquatica.GenLayerFixAquatica;
import com.Vivern.Arpg.dimensions.toxicomania.GenLayerFixToxic;
import com.Vivern.Arpg.main.BiomesRegister;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerRiver;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.gen.layer.IntCache;
import org.apache.commons.lang3.ArrayUtils;

public class GenLayerFixEthernalfrost {
   public static GenLayer[] createWorld(long seed) {
      GenLayer genlayer = new GenLayerBiomeEthernalfrost(1L);
      genlayer = new GenLayerZoom(1000L, genlayer);
      genlayer = new GenLayerFixAquatica.GenLayerRandomPoint(1L, genlayer, null, BiomesRegister.FROZEN_LAKE, 12);
      genlayer = new GenLayerZoom(1001L, genlayer);
      genlayer = new GenLayerZoom(1002L, genlayer);
      genlayer = new GenLayerBiomeFrozenBeach(1L, genlayer);
      genlayer = new GenLayerZoom(1003L, genlayer);

      for (int i = 0; i < BiomesRegister.frozen_mountains_amount - 1; i++) {
         genlayer = new GenLayerMountain(
            2031L + i, genlayer, Biome.getIdForBiome(BiomesRegister.FROZEN_MOUNTAINS[0 + i]), Biome.getIdForBiome(BiomesRegister.FROZEN_MOUNTAINS[1 + i])
         );
      }

      genlayer = new GenLayerZoom(1004L, genlayer);
      GenLayer genlayerriverinit = new GenLayerRiverInit(100L, genlayer);
      genlayerriverinit = new GenLayerZoom(1006L, genlayerriverinit);
      genlayerriverinit = new GenLayerZoom(1007L, genlayerriverinit);
      genlayerriverinit = new GenLayerZoom(1008L, genlayerriverinit);
      genlayerriverinit = new GenLayerZoom(1009L, genlayerriverinit);
      GenLayer genlayerriver = new GenLayerRiver(1L, genlayerriverinit);
      GenLayer riversmooth = new GenLayerSmooth(1000L, genlayerriver);
      Biome[] biomes = new Biome[]{BiomesRegister.FROZEN_FOREST};
      Biome[] rivers = new Biome[]{BiomesRegister.EVERFROST_RIVER};
      Biome[] avoids = new Biome[]{BiomesRegister.FROZEN_LAKE, BiomesRegister.FROZEN_BEACH};
      avoids = (Biome[])ArrayUtils.addAll(avoids, BiomesRegister.FROZEN_MOUNTAINS);
      genlayer = new GenLayerFixToxic.GenLayerRiverMixAdvanced(100L, genlayer, riversmooth, biomes, rivers, avoids);
      genlayer = new GenLayerVoronoiZoom(10L, genlayer);
      GenLayer var19 = new GenLayerVoronoiZoom(10L, genlayer);
      genlayer.initWorldGenSeed(seed);
      var19.initWorldGenSeed(seed);
      return new GenLayer[]{genlayer, var19, genlayer};
   }

   public static class GenLayerBiomeFrozenBeach extends GenLayer {
      public int toId;
      public int neighborId;
      boolean fromnull = false;

      public GenLayerBiomeFrozenBeach(long p_i2128_1_, GenLayer p_i2128_3_) {
         super(p_i2128_1_);
         super.parent = p_i2128_3_;
         this.toId = Biome.getIdForBiome(BiomesRegister.FROZEN_BEACH);
         this.neighborId = Biome.getIdForBiome(BiomesRegister.FROZEN_LAKE);
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
               int k1 = aint[j1 + 1 + (i1 + 0) * k];
               int l1 = aint[j1 + 0 + (i1 + 1) * k];
               int i2 = aint[j1 + 1 + (i1 + 2) * k];
               int j2 = aint[j1 + 2 + (i1 + 1) * k];
               int k2 = aint[j1 + 1 + (i1 + 1) * k];
               this.initChunkSeed(j1 + areaX, i1 + areaY);
               if (k2 != Biome.getIdForBiome(BiomesRegister.FROZEN_MEADOW)
                     && k2 != Biome.getIdForBiome(BiomesRegister.FROZEN_WARM_FOREST)
                     && k2 != Biome.getIdForBiome(BiomesRegister.ICE_HILLS)
                     && k2 != Biome.getIdForBiome(BiomesRegister.FROZEN_FOREST)
                  || k1 != this.neighborId && l1 != this.neighborId && i2 != this.neighborId && j2 != this.neighborId) {
                  aint1[j1 + i1 * areaWidth] = k2;
               } else {
                  aint1[j1 + i1 * areaWidth] = this.toId;
               }
            }
         }

         return aint1;
      }
   }

   public static class GenLayerMountain extends GenLayer {
      public int toId;
      public int fromId;

      public GenLayerMountain(long p_i2128_1_, GenLayer p_i2128_3_, int fromId, int toId) {
         super(p_i2128_1_);
         super.parent = p_i2128_3_;
         this.toId = toId;
         this.fromId = fromId;
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
               int k1 = aint[j1 + 1 + (i1 + 0) * k];
               int l1 = aint[j1 + 0 + (i1 + 1) * k];
               int i2 = aint[j1 + 1 + (i1 + 2) * k];
               int j2 = aint[j1 + 2 + (i1 + 1) * k];
               int k2 = aint[j1 + 1 + (i1 + 1) * k];
               this.initChunkSeed(j1 + areaX, i1 + areaY);
               if (k2 == this.fromId && k1 == this.fromId && l1 == this.fromId && i2 == this.fromId && j2 == this.fromId) {
                  aint1[j1 + i1 * areaWidth] = this.toId;
               } else {
                  aint1[j1 + i1 * areaWidth] = k2;
               }
            }
         }

         return aint1;
      }
   }
}
