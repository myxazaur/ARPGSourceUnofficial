package com.Vivern.Arpg.dimensions.aquatica;

import com.Vivern.Arpg.main.BiomesRegister;
import net.minecraft.init.Biomes;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomeMesa;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerAddIsland;
import net.minecraft.world.gen.layer.GenLayerAddSnow;
import net.minecraft.world.gen.layer.GenLayerEdge;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerIsland;
import net.minecraft.world.gen.layer.GenLayerRemoveTooMuchOcean;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraft.world.gen.layer.GenLayerEdge.Mode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenLayerFixAquatica {
   public static GenLayer[] initializeAllBiomeGenerators(long seed, WorldType worldType, ChunkGeneratorSettings genSettings) {
      GenLayer genlayer = new GenLayerIsland(1L);
      genlayer = new GenLayerFuzzyZoom(2000L, genlayer);
      genlayer = new GenLayerAddIsland(1L, genlayer);
      Biome[] oceans = new Biome[]{Biomes.OCEAN, BiomesRegister.CORALS, BiomesRegister.SEA_BOTTOM, BiomesRegister.SEAWEED_BAY};
      genlayer = new GenLayerRandomArrayPoint(
         1L, genlayer, Biomes.OCEAN, new Biome[]{BiomesRegister.CORALS, BiomesRegister.SEA_BOTTOM, BiomesRegister.SEAWEED_BAY}, 1
      );
      genlayer = new GenLayerZoom(2001L, genlayer);
      genlayer = new GenLayerRandomPoint(1L, genlayer, Biomes.OCEAN, BiomesRegister.REEF, 20);
      genlayer = new GenLayerArrayCustomEdge(1L, genlayer, oceans, new Biome[]{Biomes.PLAINS}, BiomesRegister.SHALLOW);
      genlayer = new GenLayerBiomeCustomEdge(1L, genlayer, Biomes.PLAINS, BiomesRegister.SHALLOW, BiomesRegister.AQUATICABEACH);
      genlayer = new GenLayerBiomeCustomEdge(1L, genlayer, Biomes.PLAINS, BiomesRegister.AQUATICABEACH, BiomesRegister.ISLANDS);
      genlayer = new GenLayerBiomeCustomEdge(1L, genlayer, Biomes.PLAINS, BiomesRegister.ISLANDS, BiomesRegister.ISLANDHILLS);
      genlayer = new GenLayerArrayCustomEdge(1L, genlayer, oceans, new Biome[]{BiomesRegister.SHALLOW}, BiomesRegister.SHALLOW);
      genlayer = new GenLayerArrayCustomEdge(1L, genlayer, oceans, new Biome[]{BiomesRegister.SHALLOW}, BiomesRegister.REEF);
      genlayer = new GenLayerArrayCustomEdge(1L, genlayer, oceans, new Biome[]{BiomesRegister.REEF}, BiomesRegister.REEF);
      genlayer = new GenLayerZoom(2001L, genlayer);
      genlayer = new GenLayerInside(2001L, genlayer, BiomesRegister.ISLANDS, BiomesRegister.ISLANDHILLS, BiomesRegister.AQUATICABEACH);
      genlayer = new GenLayerRandomPoint(1L, genlayer, BiomesRegister.SHALLOW, Biomes.PLAINS, 10);
      genlayer = new GenLayerZoom(2001L, genlayer);
      genlayer = new GenLayerBiomeCustomEdge(1L, genlayer, BiomesRegister.SHALLOW, Biomes.PLAINS, BiomesRegister.AQUATICABEACH);
      genlayer = new GenLayerFuzzyZoom(2000L, genlayer);
      genlayer = new GenLayerBiomeCustomEdge(1L, genlayer, Biomes.PLAINS, BiomesRegister.AQUATICABEACH, BiomesRegister.AQUATICABEACH);
      genlayer = new GenLayerBiomeCustomEdge(1L, genlayer, Biomes.PLAINS, BiomesRegister.AQUATICABEACH, BiomesRegister.SHALLOW);
      GenLayer genlayerriverinit = new GenLayerRiverInit(100L, genlayer);
      GenLayer riverinitmagnifed = GenLayerZoom.magnify(1000L, genlayerriverinit, 3);
      GenLayer genlayerriver = new GenLayerRiver(1L, riverinitmagnifed);
      GenLayer riversmooth = new GenLayerSmooth(1000L, genlayerriver);
      genlayer = new GenLayerRiverMix(100L, genlayer, riversmooth);
      genlayer = new GenLayerVoronoiZoom(10L, genlayer);
      GenLayer var33 = new GenLayerVoronoiZoom(10L, genlayer);
      genlayer.initWorldGenSeed(seed);
      var33.initWorldGenSeed(seed);
      return new GenLayer[]{genlayer, var33, genlayer};
   }

   public static GenLayer[] initializeAllBiomeGenerators(long seed, WorldType worldType, ChunkGeneratorSettings genSettings, boolean def) {
      GenLayer genlayer = new GenLayerIsland(1L);
      genlayer = new GenLayerFuzzyZoom(2000L, genlayer);
      GenLayer genlayeraddisland = new GenLayerAddIsland(1L, genlayer);
      GenLayer genlayerzoom = new GenLayerZoom(2001L, genlayeraddisland);
      GenLayer genlayeraddisland1 = new GenLayerAddIsland(2L, genlayerzoom);
      genlayeraddisland1 = new GenLayerAddIsland(50L, genlayeraddisland1);
      genlayeraddisland1 = new GenLayerAddIsland(70L, genlayeraddisland1);
      GenLayer genlayerremovetoomuchocean = new GenLayerRemoveTooMuchOcean(2L, genlayeraddisland1);
      GenLayer genlayeraddsnow = new GenLayerAddSnow(2L, genlayerremovetoomuchocean);
      GenLayer genlayeraddisland2 = new GenLayerAddIsland(3L, genlayeraddsnow);
      GenLayer genlayeredge = new GenLayerEdge(2L, genlayeraddisland2, Mode.COOL_WARM);
      genlayeredge = new GenLayerEdge(2L, genlayeredge, Mode.HEAT_ICE);
      genlayeredge = new GenLayerEdge(3L, genlayeredge, Mode.SPECIAL);
      GenLayer genlayerzoom1 = new GenLayerZoom(2002L, genlayeredge);
      genlayerzoom1 = new GenLayerZoom(2003L, genlayerzoom1);
      GenLayer genlayeraddisland3 = new GenLayerAddIsland(4L, genlayerzoom1);
      GenLayer genlayeraddmushroomisland = new GenLayerAddMushroomIsland(5L, genlayeraddisland3);
      GenLayer genlayerdeepocean = new GenLayerDeepOcean(4L, genlayeraddmushroomisland);
      GenLayer genlayer4 = GenLayerZoom.magnify(1000L, genlayerdeepocean, 0);
      int biomeSize = 4;
      int riverSize = biomeSize;
      if (genSettings != null) {
         biomeSize = genSettings.biomeSize;
         riverSize = genSettings.riverSize;
      }

      if (worldType == WorldType.LARGE_BIOMES) {
         biomeSize = 6;
      }

      GenLayer lvt_7_1_ = GenLayerZoom.magnify(1000L, genlayer4, 0);
      GenLayer genlayerriverinit = new GenLayerRiverInit(100L, lvt_7_1_);
      GenLayer genlayerbiomeedge = worldType.getBiomeLayer(seed, genlayer4, genSettings);
      GenLayer lvt_9_1_ = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
      GenLayer genlayerhills = new GenLayerHills(1000L, genlayerbiomeedge, lvt_9_1_);
      GenLayer genlayer5 = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
      genlayer5 = GenLayerZoom.magnify(1000L, genlayer5, riverSize);
      GenLayer genlayerriver = new GenLayerRiver(1L, genlayer5);
      GenLayer genlayersmooth = new GenLayerSmooth(1000L, genlayerriver);
      genlayerhills = new GenLayerRareBiome(1001L, genlayerhills);

      for (int k = 0; k < biomeSize; k++) {
         genlayerhills = new GenLayerZoom(1000 + k, genlayerhills);
         if (k == 0) {
            genlayerhills = new GenLayerAddIsland(3L, genlayerhills);
         }

         if (k == 1 || biomeSize == 1) {
            genlayerhills = new GenLayerShore(1000L, genlayerhills);
         }
      }

      GenLayer genlayersmooth1 = new GenLayerSmooth(1000L, genlayerhills);
      GenLayer genlayerrivermix = new GenLayerRiverMix(100L, genlayersmooth1, genlayersmooth);
      GenLayer genlayer3 = new GenLayerVoronoiZoom(10L, genlayerrivermix);
      genlayerrivermix.initWorldGenSeed(seed);
      genlayer3.initWorldGenSeed(seed);
      return new GenLayer[]{genlayerrivermix, genlayer3, genlayerrivermix};
   }

   public static class GenLayerAddMushroomIsland extends GenLayer {
      public GenLayerAddMushroomIsland(long p_i2120_1_, GenLayer p_i2120_3_) {
         super(p_i2120_1_);
         this.parent = p_i2120_3_;
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
               int k1 = aint[j1 + 0 + (i1 + 0) * k];
               int l1 = aint[j1 + 2 + (i1 + 0) * k];
               int i2 = aint[j1 + 0 + (i1 + 2) * k];
               int j2 = aint[j1 + 2 + (i1 + 2) * k];
               int k2 = aint[j1 + 1 + (i1 + 1) * k];
               this.initChunkSeed(j1 + areaX, i1 + areaY);
               if (k2 == 0 && k1 == 0 && l1 == 0 && i2 == 0 && j2 == 0 && this.nextInt(100) == 0) {
                  aint1[j1 + i1 * areaWidth] = Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND);
               } else {
                  aint1[j1 + i1 * areaWidth] = k2;
               }
            }
         }

         return aint1;
      }
   }

   public static class GenLayerArrayCustomEdge extends GenLayer {
      public Biome[] from;
      public Biome to;
      public Biome[] neighbor;
      public int toId;
      public int[] fromId;
      public int[] neighborId;

      public GenLayerArrayCustomEdge(long p_i2128_1_, GenLayer p_i2128_3_, Biome[] from, Biome[] neighbor, Biome to) {
         super(p_i2128_1_);
         super.parent = p_i2128_3_;
         this.from = from;
         this.to = to;
         this.toId = Biome.getIdForBiome(to);
         this.fromId = new int[from.length];
         this.neighborId = new int[neighbor.length];
         int indx = 0;

         for (Biome bm : from) {
            this.fromId[indx] = Biome.getIdForBiome(bm);
            indx++;
         }

         int indx2 = 0;

         for (Biome bm : neighbor) {
            this.neighborId[indx2] = Biome.getIdForBiome(bm);
            indx2++;
         }
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
               boolean nb = false;

               for (int nbId : this.neighborId) {
                  if (k1 == nbId || l1 == nbId || i2 == nbId || j2 == nbId) {
                     nb = true;
                     break;
                  }
               }

               boolean fr = false;

               for (int frId : this.fromId) {
                  if (k2 == frId) {
                     fr = true;
                     break;
                  }
               }

               if (fr && nb) {
                  aint1[j1 + i1 * areaWidth] = this.toId;
               } else {
                  aint1[j1 + i1 * areaWidth] = k2;
               }
            }
         }

         return aint1;
      }
   }

   public static class GenLayerBiomeCustomEdge extends GenLayer {
      public Biome from;
      public Biome to;
      public Biome neighbor;
      public int toId;
      public int fromId;
      public int neighborId;

      public GenLayerBiomeCustomEdge(long p_i2128_1_, GenLayer p_i2128_3_, Biome from, Biome neighbor, Biome to) {
         super(p_i2128_1_);
         super.parent = p_i2128_3_;
         this.from = from;
         this.to = to;
         this.toId = Biome.getIdForBiome(to);
         this.fromId = Biome.getIdForBiome(from);
         this.neighborId = Biome.getIdForBiome(neighbor);
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
               if (k2 != this.fromId || k1 != this.neighborId && l1 != this.neighborId && i2 != this.neighborId && j2 != this.neighborId) {
                  aint1[j1 + i1 * areaWidth] = k2;
               } else {
                  aint1[j1 + i1 * areaWidth] = this.toId;
               }
            }
         }

         return aint1;
      }
   }

   public static class GenLayerDeepOcean extends GenLayer {
      public GenLayerDeepOcean(long p_i45472_1_, GenLayer p_i45472_3_) {
         super(p_i45472_1_);
         this.parent = p_i45472_3_;
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
               int k1 = aint[j1 + 1 + (i1 + 1 - 1) * (areaWidth + 2)];
               int l1 = aint[j1 + 1 + 1 + (i1 + 1) * (areaWidth + 2)];
               int i2 = aint[j1 + 1 - 1 + (i1 + 1) * (areaWidth + 2)];
               int j2 = aint[j1 + 1 + (i1 + 1 + 1) * (areaWidth + 2)];
               int k2 = aint[j1 + 1 + (i1 + 1) * k];
               int l2 = 0;
               if (k1 == 0) {
                  l2++;
               }

               if (l1 == 0) {
                  l2++;
               }

               if (i2 == 0) {
                  l2++;
               }

               if (j2 == 0) {
                  l2++;
               }

               if (k2 == 0 && l2 > 3) {
                  aint1[j1 + i1 * areaWidth] = Biome.getIdForBiome(BiomesRegister.SEA_BOTTOM);
               } else {
                  aint1[j1 + i1 * areaWidth] = k2;
               }
            }
         }

         return aint1;
      }
   }

   public static class GenLayerHills extends GenLayer {
      private static final Logger LOGGER = LogManager.getLogger();
      private final GenLayer riverLayer;

      public GenLayerHills(long p_i45479_1_, GenLayer p_i45479_3_, GenLayer p_i45479_4_) {
         super(p_i45479_1_);
         this.parent = p_i45479_3_;
         this.riverLayer = p_i45479_4_;
      }

      public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
         int[] aint = this.parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
         int[] aint1 = this.riverLayer.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
         int[] aint2 = IntCache.getIntCache(areaWidth * areaHeight);

         for (int i = 0; i < areaHeight; i++) {
            for (int j = 0; j < areaWidth; j++) {
               this.initChunkSeed(j + areaX, i + areaY);
               int k = aint[j + 1 + (i + 1) * (areaWidth + 2)];
               int l = aint1[j + 1 + (i + 1) * (areaWidth + 2)];
               boolean flag = (l - 2) % 29 == 0;
               if (k > 255) {
                  LOGGER.debug("old! {}", k);
               }

               Biome biome = Biome.getBiomeForId(k);
               boolean flag1 = biome != null && biome.isMutation();
               if (k != 0 && l >= 2 && (l - 2) % 29 == 1 && !flag1) {
                  Biome biome3 = Biome.getMutationForBiome(biome);
                  aint2[j + i * areaWidth] = biome3 == null ? k : Biome.getIdForBiome(biome3);
               } else if (this.nextInt(3) != 0 && !flag) {
                  aint2[j + i * areaWidth] = k;
               } else {
                  Biome biome1 = biome;
                  if (biome == Biomes.DESERT) {
                     biome1 = Biomes.DESERT_HILLS;
                  } else if (biome == Biomes.FOREST) {
                     biome1 = Biomes.FOREST_HILLS;
                  } else if (biome == Biomes.BIRCH_FOREST) {
                     biome1 = Biomes.BIRCH_FOREST_HILLS;
                  } else if (biome == Biomes.ROOFED_FOREST) {
                     biome1 = Biomes.PLAINS;
                  } else if (biome == Biomes.TAIGA) {
                     biome1 = BiomesRegister.ISLANDS;
                  } else if (biome == Biomes.REDWOOD_TAIGA) {
                     biome1 = Biomes.REDWOOD_TAIGA_HILLS;
                  } else if (biome == Biomes.COLD_TAIGA) {
                     biome1 = Biomes.COLD_TAIGA_HILLS;
                  } else if (biome == Biomes.PLAINS) {
                     if (this.nextInt(3) == 0) {
                        biome1 = Biomes.FOREST_HILLS;
                     } else {
                        biome1 = Biomes.FOREST;
                     }
                  } else if (biome == Biomes.ICE_PLAINS) {
                     biome1 = Biomes.ICE_MOUNTAINS;
                  } else if (biome == Biomes.JUNGLE) {
                     biome1 = Biomes.JUNGLE_HILLS;
                  } else if (biome == Biomes.OCEAN) {
                     biome1 = BiomesRegister.SEA_BOTTOM;
                  } else if (biome == Biomes.EXTREME_HILLS) {
                     biome1 = Biomes.EXTREME_HILLS_WITH_TREES;
                  } else if (biome == Biomes.SAVANNA) {
                     biome1 = Biomes.SAVANNA_PLATEAU;
                  } else if (biomesEqualOrMesaPlateau(k, Biome.getIdForBiome(Biomes.MESA_ROCK))) {
                     biome1 = Biomes.MESA;
                  } else if (biome == Biomes.DEEP_OCEAN && this.nextInt(3) == 0) {
                     int i1 = this.nextInt(2);
                     if (i1 == 0) {
                        biome1 = Biomes.PLAINS;
                     } else {
                        biome1 = Biomes.FOREST;
                     }
                  }

                  int j2 = Biome.getIdForBiome(biome1);
                  if (flag && j2 != k) {
                     Biome biome2 = Biome.getMutationForBiome(biome1);
                     j2 = biome2 == null ? k : Biome.getIdForBiome(biome2);
                  }

                  if (j2 == k) {
                     aint2[j + i * areaWidth] = k;
                  } else {
                     int k2 = aint[j + 1 + (i + 0) * (areaWidth + 2)];
                     int j1 = aint[j + 2 + (i + 1) * (areaWidth + 2)];
                     int k1 = aint[j + 0 + (i + 1) * (areaWidth + 2)];
                     int l1 = aint[j + 1 + (i + 2) * (areaWidth + 2)];
                     int i2 = 0;
                     if (biomesEqualOrMesaPlateau(k2, k)) {
                        i2++;
                     }

                     if (biomesEqualOrMesaPlateau(j1, k)) {
                        i2++;
                     }

                     if (biomesEqualOrMesaPlateau(k1, k)) {
                        i2++;
                     }

                     if (biomesEqualOrMesaPlateau(l1, k)) {
                        i2++;
                     }

                     if (i2 >= 3) {
                        aint2[j + i * areaWidth] = j2;
                     } else {
                        aint2[j + i * areaWidth] = k;
                     }
                  }
               }
            }
         }

         return aint2;
      }
   }

   public static class GenLayerInside extends GenLayer {
      public Biome from;
      public Biome to;
      public Biome avoid;
      public int toId;
      public int fromId;
      public int avoidId;

      public GenLayerInside(long p_i2128_1_, GenLayer p_i2128_3_, Biome from, Biome to, Biome avoid) {
         super(p_i2128_1_);
         super.parent = p_i2128_3_;
         this.from = from;
         this.to = to;
         this.toId = Biome.getIdForBiome(to);
         this.fromId = Biome.getIdForBiome(from);
         this.avoidId = Biome.getIdForBiome(avoid);
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
               if (k2 == this.fromId && k1 != this.avoidId && l1 != this.avoidId && i2 != this.avoidId && j2 != this.avoidId) {
                  aint1[j1 + i1 * areaWidth] = this.toId;
               } else {
                  aint1[j1 + i1 * areaWidth] = k2;
               }
            }
         }

         return aint1;
      }
   }

   public static class GenLayerRandomArrayPoint extends GenLayer {
      public Biome from;
      public Biome[] to;
      public int[] toId;
      public int fromId;
      public int intchance = 1;
      public int size;

      public GenLayerRandomArrayPoint(long p_i2128_1_, GenLayer p_i2128_3_, Biome from, Biome[] to, int intchance) {
         super(p_i2128_1_);
         super.parent = p_i2128_3_;
         this.from = from;
         this.to = to;
         this.toId = new int[to.length];
         int indx = 0;

         for (Biome bm : to) {
            this.toId[indx] = Biome.getIdForBiome(bm);
            indx++;
         }

         this.fromId = Biome.getIdForBiome(from);
         this.intchance = intchance;
         this.size = to.length;
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
               if (k2 == this.fromId && this.nextInt(this.intchance) == 0) {
                  aint1[j1 + i1 * areaWidth] = this.toId[this.size > 1 ? this.nextInt(this.size) : 0];
               } else {
                  aint1[j1 + i1 * areaWidth] = k2;
               }
            }
         }

         return aint1;
      }
   }

   public static class GenLayerRandomPoint extends GenLayer {
      public Biome from;
      public Biome to;
      public int toId;
      public int fromId;
      public int intchance = 1;

      public GenLayerRandomPoint(long p_i2128_1_, GenLayer p_i2128_3_, Biome from, Biome to, int intchance) {
         super(p_i2128_1_);
         super.parent = p_i2128_3_;
         this.from = from;
         this.to = to;
         this.toId = Biome.getIdForBiome(to);
         this.fromId = Biome.getIdForBiome(from);
         this.intchance = intchance;
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
               if ((this.from == null || k2 == this.fromId) && this.nextInt(this.intchance) == 0) {
                  aint1[j1 + i1 * areaWidth] = this.toId;
               } else {
                  aint1[j1 + i1 * areaWidth] = k2;
               }
            }
         }

         return aint1;
      }
   }

   public static class GenLayerRareBiome extends GenLayer {
      public GenLayerRareBiome(long p_i45478_1_, GenLayer p_i45478_3_) {
         super(p_i45478_1_);
         this.parent = p_i45478_3_;
      }

      public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
         int[] aint = this.parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
         int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);

         for (int i = 0; i < areaHeight; i++) {
            for (int j = 0; j < areaWidth; j++) {
               this.initChunkSeed(j + areaX, i + areaY);
               int k = aint[j + 1 + (i + 1) * (areaWidth + 2)];
               if (this.nextInt(57) == 0) {
                  if (k == Biome.getIdForBiome(Biomes.PLAINS)) {
                     aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.MUTATED_PLAINS);
                  } else {
                     aint1[j + i * areaWidth] = k;
                  }
               } else {
                  aint1[j + i * areaWidth] = k;
               }
            }
         }

         return aint1;
      }
   }

   public class GenLayerReplacerAquatica extends GenLayer {
      public Biome from;
      public Biome to;
      public int toId;
      public int fromId;

      public GenLayerReplacerAquatica(long p_i2128_1_, GenLayer p_i2128_3_, Biome from, Biome to) {
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

   public static class GenLayerRiver extends GenLayer {
      public GenLayerRiver(long p_i2128_1_, GenLayer p_i2128_3_) {
         super(p_i2128_1_);
         super.parent = p_i2128_3_;
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
               int k1 = this.riverFilter(aint[j1 + 0 + (i1 + 1) * k]);
               int l1 = this.riverFilter(aint[j1 + 2 + (i1 + 1) * k]);
               int i2 = this.riverFilter(aint[j1 + 1 + (i1 + 0) * k]);
               int j2 = this.riverFilter(aint[j1 + 1 + (i1 + 2) * k]);
               int k2 = this.riverFilter(aint[j1 + 1 + (i1 + 1) * k]);
               if (k2 == k1 && k2 == i2 && k2 == l1 && k2 == j2) {
                  aint1[j1 + i1 * areaWidth] = -1;
               } else {
                  aint1[j1 + i1 * areaWidth] = Biome.getIdForBiome(BiomesRegister.ISLAND_RIVER);
               }
            }
         }

         return aint1;
      }

      private int riverFilter(int p_151630_1_) {
         return p_151630_1_ >= 2 ? 2 + (p_151630_1_ & 1) : p_151630_1_;
      }
   }

   public static class GenLayerRiverMix extends GenLayer {
      private final GenLayer biomePatternGeneratorChain;
      private final GenLayer riverPatternGeneratorChain;

      public GenLayerRiverMix(long p_i2129_1_, GenLayer p_i2129_3_, GenLayer p_i2129_4_) {
         super(p_i2129_1_);
         this.biomePatternGeneratorChain = p_i2129_3_;
         this.riverPatternGeneratorChain = p_i2129_4_;
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
            if (aint[i] == Biome.getIdForBiome(BiomesRegister.CORALS)
               || aint[i] == Biome.getIdForBiome(BiomesRegister.SEA_BOTTOM)
               || aint[i] == Biome.getIdForBiome(BiomesRegister.SEAWEED_BAY)
               || aint[i] == Biome.getIdForBiome(Biomes.OCEAN)
               || aint[i] == Biome.getIdForBiome(BiomesRegister.SHALLOW)
               || aint[i] == Biome.getIdForBiome(BiomesRegister.REEF)) {
               aint2[i] = aint[i];
            } else if (aint1[i] == Biome.getIdForBiome(BiomesRegister.ISLAND_RIVER)) {
               if (aint[i] == Biome.getIdForBiome(Biomes.ICE_PLAINS)) {
                  aint2[i] = Biome.getIdForBiome(Biomes.FROZEN_RIVER);
               } else if (aint[i] != Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND) && aint[i] != Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND_SHORE)) {
                  aint2[i] = aint1[i] & 0xFF;
               } else {
                  aint2[i] = Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND_SHORE);
               }
            } else {
               aint2[i] = aint[i];
            }
         }

         return aint2;
      }
   }

   public static class GenLayerShore extends GenLayer {
      public GenLayerShore(long p_i2130_1_, GenLayer p_i2130_3_) {
         super(p_i2130_1_);
         this.parent = p_i2130_3_;
      }

      public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
         int[] aint = this.parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
         int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);

         for (int i = 0; i < areaHeight; i++) {
            for (int j = 0; j < areaWidth; j++) {
               this.initChunkSeed(j + areaX, i + areaY);
               int k = aint[j + 1 + (i + 1) * (areaWidth + 2)];
               Biome biome = Biome.getBiome(k);
               if (k == Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND)) {
                  int j2 = aint[j + 1 + (i + 1 - 1) * (areaWidth + 2)];
                  int i3 = aint[j + 1 + 1 + (i + 1) * (areaWidth + 2)];
                  int l3 = aint[j + 1 - 1 + (i + 1) * (areaWidth + 2)];
                  int k4 = aint[j + 1 + (i + 1 + 1) * (areaWidth + 2)];
                  if (j2 != Biome.getIdForBiome(Biomes.OCEAN)
                     && i3 != Biome.getIdForBiome(Biomes.OCEAN)
                     && l3 != Biome.getIdForBiome(Biomes.OCEAN)
                     && k4 != Biome.getIdForBiome(Biomes.OCEAN)) {
                     aint1[j + i * areaWidth] = k;
                  } else {
                     aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND_SHORE);
                  }
               } else if (biome != null && biome == BiomesRegister.ISLANDS) {
                  int i2 = aint[j + 1 + (i + 1 - 1) * (areaWidth + 2)];
                  int l2 = aint[j + 1 + 1 + (i + 1) * (areaWidth + 2)];
                  int k3 = aint[j + 1 - 1 + (i + 1) * (areaWidth + 2)];
                  int j4 = aint[j + 1 + (i + 1 + 1) * (areaWidth + 2)];
                  if (!this.isJungleCompatible(i2) || !this.isJungleCompatible(l2) || !this.isJungleCompatible(k3) || !this.isJungleCompatible(j4)) {
                     aint1[j + i * areaWidth] = Biome.getIdForBiome(BiomesRegister.AQUATICABEACH);
                  } else if (!isBiomeOceanic(i2) && !isBiomeOceanic(l2) && !isBiomeOceanic(k3) && !isBiomeOceanic(j4)) {
                     aint1[j + i * areaWidth] = k;
                  } else {
                     aint1[j + i * areaWidth] = Biome.getIdForBiome(BiomesRegister.AQUATICABEACH);
                  }
               } else if (k == Biome.getIdForBiome(Biomes.EXTREME_HILLS)
                  || k == Biome.getIdForBiome(Biomes.EXTREME_HILLS_WITH_TREES)
                  || k == Biome.getIdForBiome(Biomes.EXTREME_HILLS_EDGE)) {
                  this.replaceIfNeighborOcean(aint, aint1, j, i, areaWidth, k, Biome.getIdForBiome(BiomesRegister.AQUATICABEACH));
               } else if (biome != null && biome.isSnowyBiome()) {
                  this.replaceIfNeighborOcean(aint, aint1, j, i, areaWidth, k, Biome.getIdForBiome(Biomes.COLD_BEACH));
               } else if (k == Biome.getIdForBiome(Biomes.MESA) || k == Biome.getIdForBiome(Biomes.MESA_ROCK)) {
                  int l = aint[j + 1 + (i + 1 - 1) * (areaWidth + 2)];
                  int i1 = aint[j + 1 + 1 + (i + 1) * (areaWidth + 2)];
                  int j1 = aint[j + 1 - 1 + (i + 1) * (areaWidth + 2)];
                  int k1 = aint[j + 1 + (i + 1 + 1) * (areaWidth + 2)];
                  if (isBiomeOceanic(l) || isBiomeOceanic(i1) || isBiomeOceanic(j1) || isBiomeOceanic(k1)) {
                     aint1[j + i * areaWidth] = k;
                  } else if (this.isMesa(l) && this.isMesa(i1) && this.isMesa(j1) && this.isMesa(k1)) {
                     aint1[j + i * areaWidth] = k;
                  } else {
                     aint1[j + i * areaWidth] = Biome.getIdForBiome(BiomesRegister.AQUATICABEACH);
                  }
               } else if (k != Biome.getIdForBiome(Biomes.OCEAN)
                  && k != Biome.getIdForBiome(Biomes.DEEP_OCEAN)
                  && k != Biome.getIdForBiome(Biomes.RIVER)
                  && k != Biome.getIdForBiome(Biomes.SWAMPLAND)) {
                  int l1 = aint[j + 1 + (i + 1 - 1) * (areaWidth + 2)];
                  int k2 = aint[j + 1 + 1 + (i + 1) * (areaWidth + 2)];
                  int j3 = aint[j + 1 - 1 + (i + 1) * (areaWidth + 2)];
                  int i4 = aint[j + 1 + (i + 1 + 1) * (areaWidth + 2)];
                  if (!isBiomeOceanic(l1) && !isBiomeOceanic(k2) && !isBiomeOceanic(j3) && !isBiomeOceanic(i4)) {
                     aint1[j + i * areaWidth] = k;
                  } else {
                     aint1[j + i * areaWidth] = Biome.getIdForBiome(BiomesRegister.AQUATICABEACH);
                  }
               } else {
                  aint1[j + i * areaWidth] = k;
               }
            }
         }

         return aint1;
      }

      private void replaceIfNeighborOcean(
         int[] p_151632_1_, int[] p_151632_2_, int p_151632_3_, int p_151632_4_, int p_151632_5_, int p_151632_6_, int p_151632_7_
      ) {
         if (isBiomeOceanic(p_151632_6_)) {
            p_151632_2_[p_151632_3_ + p_151632_4_ * p_151632_5_] = p_151632_6_;
         } else {
            int i = p_151632_1_[p_151632_3_ + 1 + (p_151632_4_ + 1 - 1) * (p_151632_5_ + 2)];
            int j = p_151632_1_[p_151632_3_ + 1 + 1 + (p_151632_4_ + 1) * (p_151632_5_ + 2)];
            int k = p_151632_1_[p_151632_3_ + 1 - 1 + (p_151632_4_ + 1) * (p_151632_5_ + 2)];
            int l = p_151632_1_[p_151632_3_ + 1 + (p_151632_4_ + 1 + 1) * (p_151632_5_ + 2)];
            if (!isBiomeOceanic(i) && !isBiomeOceanic(j) && !isBiomeOceanic(k) && !isBiomeOceanic(l)) {
               p_151632_2_[p_151632_3_ + p_151632_4_ * p_151632_5_] = p_151632_6_;
            } else {
               p_151632_2_[p_151632_3_ + p_151632_4_ * p_151632_5_] = p_151632_7_;
            }
         }
      }

      private boolean isJungleCompatible(int p_151631_1_) {
         return Biome.getBiome(p_151631_1_) != null && Biome.getBiome(p_151631_1_).getBiomeClass() == BiomeJungle.class
            ? true
            : p_151631_1_ == Biome.getIdForBiome(Biomes.JUNGLE_EDGE)
               || p_151631_1_ == Biome.getIdForBiome(Biomes.JUNGLE)
               || p_151631_1_ == Biome.getIdForBiome(Biomes.JUNGLE_HILLS)
               || p_151631_1_ == Biome.getIdForBiome(Biomes.FOREST)
               || p_151631_1_ == Biome.getIdForBiome(Biomes.TAIGA)
               || isBiomeOceanic(p_151631_1_);
      }

      private boolean isMesa(int p_151633_1_) {
         return Biome.getBiome(p_151633_1_) instanceof BiomeMesa;
      }
   }
}
