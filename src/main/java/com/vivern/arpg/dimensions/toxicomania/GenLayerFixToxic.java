package com.vivern.arpg.dimensions.toxicomania;

import com.vivern.arpg.dimensions.aquatica.GenLayerFixAquatica;
import com.vivern.arpg.main.BiomesRegister;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerAddIsland;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerIsland;
import net.minecraft.world.gen.layer.GenLayerRiver;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerFixToxic {
   public static GenLayer[] createWorld(long seed) {
      GenLayer biomes = new GenLayerBiomeToxic(1L);
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

   public static GenLayer[] initializeAllBiomeGenerators(long seed) {
      GenLayer genlayer = new GenLayerIsland(1L);
      genlayer = new GenLayerFuzzyZoom(2000L, genlayer);
      genlayer = new GenLayerFuzzyZoom(2000L, genlayer);
      genlayer = new GenLayerAddIsland(1L, genlayer);
      genlayer = new GenLayerZoom(2001L, genlayer);
      genlayer = new GenLayerAddIsland(1L, genlayer);
      genlayer = new GenLayerZoom(2002L, genlayer);
      genlayer = new GenLayerAddIsland(1L, genlayer);
      genlayer = new GenLayerZoom(2002L, genlayer);
      genlayer = new GenLayerFixAquatica.GenLayerRandomPoint(1L, genlayer, Biomes.OCEAN, BiomesRegister.TOXIC_JUNGLE, 20);
      genlayer = new GenLayerBiomeCustomEdgeRand(
         1L, genlayer, Biomes.OCEAN, BiomesRegister.TOXIC_JUNGLE, BiomesRegister.TOXIC_JUNGLE, 50
      );
      genlayer = new GenLayerBiomeCustomEdgeRand(
         1L, genlayer, Biomes.OCEAN, BiomesRegister.TOXIC_JUNGLE, BiomesRegister.TOXIC_JUNGLE, 20
      );
      genlayer = new GenLayerBiomeCustomEdgeRand(
         1L, genlayer, Biomes.OCEAN, BiomesRegister.TOXIC_JUNGLE, BiomesRegister.TOXIC_FLOWER_ISLAND, 70
      );
      genlayer = new GenLayerFixAquatica.GenLayerRandomArrayPoint(
         1L, genlayer, Biomes.PLAINS, new Biome[]{BiomesRegister.TOXIC_MOUNTAINS, BiomesRegister.TOXIC_SLIME_LAND, BiomesRegister.TOXIC_SWAMP}, 2
      );
      genlayer = new GenLayerBiomeCustomEdgeRand(
         1L, genlayer, Biomes.PLAINS, BiomesRegister.TOXIC_SLIME_LAND, BiomesRegister.TOXIC_SLIME_LAND, 70
      );
      genlayer = new GenLayerBiomeCustomEdgeRand(
         1L, genlayer, Biomes.PLAINS, BiomesRegister.TOXIC_SWAMP, BiomesRegister.TOXIC_SWAMP, 80
      );
      genlayer = new GenLayerZoom(2001L, genlayer);
      genlayer = new GenLayerBiomeCustomEdgeRand(
         1L, genlayer, Biomes.PLAINS, BiomesRegister.TOXIC_MOUNTAINS, BiomesRegister.TOXIC_MOUNTAINS, 40
      );
      genlayer = new GenLayerFuzzyZoom(2000L, genlayer);
      genlayer = new GenLayerReplaceBiome(1L, genlayer, Biomes.PLAINS, BiomesRegister.TOXIC_PLAINS);
      genlayer = new GenLayerReplaceBiome(1L, genlayer, Biomes.OCEAN, BiomesRegister.POISON_OCEAN);
      genlayer = new GenLayerZoom(2001L, genlayer);
      genlayer = new GenLayerFixAquatica.GenLayerRandomPoint(1L, genlayer, BiomesRegister.POISON_OCEAN, BiomesRegister.TOXIC_SWAMP, 15);
      genlayer = new GenLayerZoom(2001L, genlayer);
      genlayer = new GenLayerFixAquatica.GenLayerBiomeCustomEdge(1L, genlayer, BiomesRegister.TOXIC_PLAINS, Biomes.OCEAN, BiomesRegister.DIRTY_BEACH);
      genlayer = new GenLayerZoom(2001L, genlayer);
      GenLayer genlayerriverinit = new GenLayerRiverInit(100L, genlayer);
      GenLayer riverinitmagnifed = GenLayerZoom.magnify(1000L, genlayerriverinit, 4);
      GenLayer genlayerriver = new GenLayerRiver(1L, riverinitmagnifed);
      GenLayer riversmooth = new GenLayerSmooth(1000L, genlayerriver);
      Biome[] biomes = new Biome[]{BiomesRegister.TOXIC_PLAINS, BiomesRegister.TOXIC_SLIME_LAND};
      Biome[] rivers = new Biome[]{BiomesRegister.POISON_RIVER, BiomesRegister.SLIME_RIVER};
      Biome[] avoids = new Biome[]{BiomesRegister.POISON_OCEAN, BiomesRegister.TOXIC_MOUNTAINS};
      genlayer = new GenLayerRiverMixAdvanced(100L, genlayer, riversmooth, biomes, rivers, avoids);
      genlayer = new GenLayerVoronoiZoom(10L, genlayer);
      GenLayer var38 = new GenLayerVoronoiZoom(10L, genlayer);
      genlayer.initWorldGenSeed(seed);
      var38.initWorldGenSeed(seed);
      return new GenLayer[]{genlayer, var38, genlayer};
   }

   public static class GenLayerBiomeCustomEdgeRand extends GenLayer {
      public Biome from;
      public Biome to;
      public Biome neighbor;
      public int toId;
      public int fromId;
      public int neighborId;
      public int percentchance = 1;

      public GenLayerBiomeCustomEdgeRand(long p_i2128_1_, GenLayer p_i2128_3_, Biome from, Biome neighbor, Biome to, int percentchance) {
         super(p_i2128_1_);
         super.parent = p_i2128_3_;
         this.from = from;
         this.to = to;
         this.toId = Biome.getIdForBiome(to);
         this.fromId = Biome.getIdForBiome(from);
         this.neighborId = Biome.getIdForBiome(neighbor);
         this.percentchance = percentchance;
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
               if (k2 == this.fromId
                  && (k1 == this.neighborId || l1 == this.neighborId || i2 == this.neighborId || j2 == this.neighborId)
                  && this.nextInt(100) < this.percentchance) {
                  aint1[j1 + i1 * areaWidth] = this.toId;
               } else {
                  aint1[j1 + i1 * areaWidth] = k2;
               }
            }
         }

         return aint1;
      }
   }

   public static class GenLayerReplaceBiome extends GenLayer {
      public Biome from;
      public Biome to;
      public int toId;
      public int fromId;

      public GenLayerReplaceBiome(long p_i2128_1_, GenLayer p_i2128_3_, Biome from, Biome to) {
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
               int k1 = aint[j1 + 1 + (i1 + 0) * k];
               int l1 = aint[j1 + 0 + (i1 + 1) * k];
               int i2 = aint[j1 + 1 + (i1 + 2) * k];
               int j2 = aint[j1 + 2 + (i1 + 1) * k];
               int k2 = aint[j1 + 1 + (i1 + 1) * k];
               this.initChunkSeed(j1 + areaX, i1 + areaY);
               if (k2 == this.fromId) {
                  aint1[j1 + i1 * areaWidth] = this.toId;
               } else {
                  aint1[j1 + i1 * areaWidth] = k2;
               }
            }
         }

         return aint1;
      }
   }

   public static class GenLayerRiverMixAdvanced extends GenLayer {
      private final GenLayer biomePatternGeneratorChain;
      private final GenLayer riverPatternGeneratorChain;
      public Biome[] from;
      public Biome[] to;
      public Biome[] avoid;

      public GenLayerRiverMixAdvanced(long p_i2129_1_, GenLayer p_i2129_3_, GenLayer p_i2129_4_, Biome[] from, Biome[] to, Biome[] avoid) {
         super(p_i2129_1_);
         this.biomePatternGeneratorChain = p_i2129_3_;
         this.riverPatternGeneratorChain = p_i2129_4_;
         this.from = from;
         this.to = to;
         this.avoid = avoid;
      }

      public void initWorldGenSeed(long seed) {
         this.biomePatternGeneratorChain.initWorldGenSeed(seed);
         this.riverPatternGeneratorChain.initWorldGenSeed(seed);
         super.initWorldGenSeed(seed);
      }

      public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
         int[] aint = this.biomePatternGeneratorChain.getInts(areaX, areaY, areaWidth, areaHeight);
         int[] aint1 = this.riverPatternGeneratorChain.getInts(areaX, areaY, areaWidth, areaHeight);
         int[] aint2 = IntCache.getIntCache(areaWidth * areaHeight);

         for (int i = 0; i < areaWidth * areaHeight; i++) {
            if (this.contains(this.avoid, aint[i]) || aint[i] >= 3000) {
               aint2[i] = aint[i];
            } else if (aint1[i] != Biome.getIdForBiome(Biomes.RIVER)) {
               aint2[i] = aint[i];
            } else {
               boolean finded = false;

               for (int j = 0; j < this.from.length; j++) {
                  if (aint[i] == Biome.getIdForBiome(this.from[j])) {
                     aint2[i] = Biome.getIdForBiome(this.to[j]);
                     finded = true;
                     break;
                  }
               }

               if (!finded) {
                  aint2[i] = Biome.getIdForBiome(this.to[0]);
               }
            }
         }

         return aint2;
      }

      public boolean contains(Biome[] array, int i) {
         for (Biome biome : array) {
            if (Biome.getIdForBiome(biome) == i) {
               return true;
            }
         }

         return false;
      }
   }
}
