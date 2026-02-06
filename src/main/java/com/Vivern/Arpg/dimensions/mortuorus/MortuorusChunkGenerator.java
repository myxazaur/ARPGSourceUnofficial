package com.Vivern.Arpg.dimensions.mortuorus;

import com.Vivern.Arpg.biomes.BiomeControlled;
import com.Vivern.Arpg.dimensions.generationutils.CustomOreGenerator;
import com.Vivern.Arpg.dimensions.toxicomania.ChunkGenSettingsToxic;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.NoiseGenerator3D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextOverworld;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType;

public class MortuorusChunkGenerator implements IChunkGenerator {
   public static final IBlockState STONE = Blocks.STONE.getDefaultState();
   public static final IBlockState DIRT = Blocks.GRAVEL.getDefaultState();
   public static final IBlockState GRASS = Blocks.GRAVEL.getDefaultState();
   public static final IBlockState WATER = BlocksRegister.FLUIDDARKNESS.getDefaultState();
   public static final IBlockState AIR = Blocks.AIR.getDefaultState();
   public static final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
   private final Random rand;
   private NoiseGeneratorOctaves minLimitPerlinNoise;
   private NoiseGeneratorOctaves maxLimitPerlinNoise;
   private NoiseGeneratorOctaves mainPerlinNoise;
   private NoiseGeneratorPerlin surfaceNoise;
   public NoiseGeneratorOctaves scaleNoise;
   public NoiseGeneratorOctaves depthNoise;
   public NoiseGeneratorOctaves forestNoise;
   public NoiseGeneratorPerlin terrainControlNoise;
   public NoiseGenerator3D noise3d1;
   private final World world;
   private final WorldType terrainType;
   private final double[] heightMap;
   private final float[] biomeWeights;
   private ChunkGenSettingsToxic settings;
   private double[] depthBuffer = new double[256];
   private Biome[] biomesForGeneration;
   double[] mainNoiseRegion;
   double[] minLimitRegion;
   double[] maxLimitRegion;
   double[] depthRegion;
   public List<Long> dungeons = new ArrayList<>();
   public CustomOreGenerator arsenicOreGen;
   public CustomOreGenerator toxiniumOreGen;

   public MortuorusChunkGenerator(World worldIn, long seed) {
      this.world = worldIn;
      this.terrainType = worldIn.getWorldInfo().getTerrainType();
      this.rand = new Random(seed);
      this.minLimitPerlinNoise = new NoiseGeneratorOctaves(this.rand, 16);
      this.maxLimitPerlinNoise = new NoiseGeneratorOctaves(this.rand, 16);
      this.mainPerlinNoise = new NoiseGeneratorOctaves(this.rand, 8);
      this.surfaceNoise = new NoiseGeneratorPerlin(this.rand, 4);
      this.scaleNoise = new NoiseGeneratorOctaves(this.rand, 10);
      this.depthNoise = new NoiseGeneratorOctaves(this.rand, 16);
      this.forestNoise = new NoiseGeneratorOctaves(this.rand, 8);
      this.terrainControlNoise = new NoiseGeneratorPerlin(this.rand, 4);
      this.noise3d1 = new NoiseGenerator3D(this.world.getSeed());
      this.heightMap = new double[825];
      this.biomeWeights = new float[25];
      this.arsenicOreGen = new CustomOreGenerator(
         BlocksRegister.OREARSENIC.getDefaultState(), 101, BlockMatcher.forBlock(BlocksRegister.RADIOSTONE), 4, 10, 6, 10, 50
      );
      this.toxiniumOreGen = new CustomOreGenerator(
         BlocksRegister.ORETOXINIUM.getDefaultState(), 101, BlockMatcher.forBlock(BlocksRegister.RADIOSTONE), 2, 8, 2, 6, 25
      );

      for (int i = -2; i <= 2; i++) {
         for (int j = -2; j <= 2; j++) {
            float f = 10.0F / MathHelper.sqrt(i * i + j * j + 0.2F);
            this.biomeWeights[i + 2 + (j + 2) * 5] = f;
         }
      }

      this.settings = new ChunkGenSettingsToxic.Factory().build();
      ContextOverworld ctx = new ContextOverworld(
         this.minLimitPerlinNoise, this.maxLimitPerlinNoise, this.mainPerlinNoise, this.surfaceNoise, this.scaleNoise, this.depthNoise, this.forestNoise
      );
      ctx = (ContextOverworld)TerrainGen.getModdedNoiseGenerators(worldIn, this.rand, ctx);
      this.minLimitPerlinNoise = ctx.getLPerlin1();
      this.maxLimitPerlinNoise = ctx.getLPerlin2();
      this.mainPerlinNoise = ctx.getPerlin();
      this.surfaceNoise = ctx.getHeight();
      this.scaleNoise = ctx.getScale();
      this.depthNoise = ctx.getDepth();
      this.forestNoise = ctx.getForest();
   }

   public void setBlocksInChunk(int x, int z, ChunkPrimer primer) {
      this.biomesForGeneration = this.world.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);
      this.generateHeightmap(x * 4, 0, z * 4);

      for (int i = 0; i < 4; i++) {
         int j = i * 5;
         int k = (i + 1) * 5;

         for (int l = 0; l < 4; l++) {
            int i1 = (j + l) * 33;
            int j1 = (j + l + 1) * 33;
            int k1 = (k + l) * 33;
            int l1 = (k + l + 1) * 33;

            for (int i2 = 0; i2 < 32; i2++) {
               double d0 = 0.125;
               double d1 = this.heightMap[i1 + i2];
               double d2 = this.heightMap[j1 + i2];
               double d3 = this.heightMap[k1 + i2];
               double d4 = this.heightMap[l1 + i2];
               double d5 = (this.heightMap[i1 + i2 + 1] - d1) * 0.125;
               double d6 = (this.heightMap[j1 + i2 + 1] - d2) * 0.125;
               double d7 = (this.heightMap[k1 + i2 + 1] - d3) * 0.125;
               double d8 = (this.heightMap[l1 + i2 + 1] - d4) * 0.125;

               for (int j2 = 0; j2 < 8; j2++) {
                  double d9 = 0.25;
                  double d10 = d1;
                  double d11 = d2;
                  double d12 = (d3 - d1) * 0.25;
                  double d13 = (d4 - d2) * 0.25;

                  for (int k2 = 0; k2 < 4; k2++) {
                     double d14 = 0.25;
                     double d16 = (d11 - d10) * 0.25;
                     double lvt_45_1_ = d10 - d16;

                     for (int l2 = 0; l2 < 4; l2++) {
                        if ((lvt_45_1_ += d16) > 0.0) {
                           primer.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, STONE);
                        } else if (i2 * 8 + j2 < this.settings.seaLevel) {
                           primer.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, WATER);
                        }
                     }

                     d10 += d12;
                     d11 += d13;
                  }

                  d1 += d5;
                  d2 += d6;
                  d3 += d7;
                  d4 += d8;
               }
            }
         }
      }
   }

   private void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomesIn) {
      if (ForgeEventFactory.onReplaceBiomeBlocks(this, x, z, primer, this.world)) {
         double d0 = 0.03125;
         this.depthBuffer = this.surfaceNoise.getRegion(this.depthBuffer, x * 16, z * 16, 16, 16, d0 * 2.0, d0 * 2.0, 1.0);

         for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
               Biome biome = biomesIn[j + i * 16];
               this.generateBiomeTerrain(
                  this.world, this.rand, primer, x * 16 + i, z * 16 + j, this.depthBuffer[j + i * 16], biome, biome instanceof BiomeControlled
               );
            }
         }
      }
   }

   private void generateBiomeTerrain(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal, Biome biome, boolean advancedBiome) {
      int i = worldIn.getSeaLevel();
      IBlockState iblockstate = biome.topBlock;
      IBlockState iblockstate1 = biome.fillerBlock;
      IBlockState mainstate = biome.topBlock;
      IBlockState mainstate1 = biome.fillerBlock;
      if (advancedBiome) {
         double tgc = this.terrainControlNoise.getValue(x / 100.0, z / 100.0);
         IBlockState[] states = ((BiomeControlled)biome).controlSurface(mainstate, mainstate1, noiseVal);
         if (states != null) {
            mainstate = states[0];
            mainstate1 = states[1];
            iblockstate = states[0];
            iblockstate1 = states[1];
         }
      }

      int j = -1;
      int k = (int)(noiseVal / 3.0 + 3.0 + rand.nextDouble() * 0.25);
      int l = x & 15;
      int i1 = z & 15;
      boolean hasNoAir = false;
      new MutableBlockPos();

      for (int j1 = 255; j1 >= 0; j1--) {
         if (j1 == 0) {
            chunkPrimerIn.setBlockState(i1, j1, l, BEDROCK);
         } else {
            IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);
            if (iblockstate2.getMaterial() == Material.AIR) {
               j = -1;
            } else if (iblockstate2.getBlock() == STONE.getBlock()) {
               if (j == -1) {
                  if (k <= 0) {
                     iblockstate = AIR;
                     iblockstate1 = STONE;
                  } else if (j1 >= i - 4 && j1 <= i + 1) {
                     iblockstate = mainstate;
                     iblockstate1 = mainstate1;
                  }

                  if (j1 < i && (iblockstate == null || iblockstate.getMaterial() == Material.AIR)) {
                     iblockstate1 = WATER;
                  }

                  j = k;
                  if (j1 >= i - 1) {
                     chunkPrimerIn.setBlockState(i1, j1, l, iblockstate);
                  } else if (j1 < i - 7 - k) {
                     iblockstate = AIR;
                     iblockstate1 = STONE;
                     chunkPrimerIn.setBlockState(i1, j1, l, DIRT);
                  } else {
                     chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
                  }
               } else if (j > 0) {
                  j--;
                  chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
                  if (j == 0 && iblockstate1.getBlock() == Blocks.SAND) {
                     j = rand.nextInt(4);
                     iblockstate1 = STONE;
                  }
               } else if (hasNoAir) {
                  chunkPrimerIn.setBlockState(i1, j1, l, STONE);
               }

               hasNoAir = true;
            }
         }
      }
   }

   public Chunk generateChunk(int x, int z) {
      this.rand.setSeed(x * 341873128712L + z * 132897987541L);
      ChunkPrimer chunkprimer = new ChunkPrimer();
      this.setBlocksInChunk(x, z, chunkprimer);
      this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
      this.replaceBiomeBlocks(x, z, chunkprimer, this.biomesForGeneration);
      GraveCombGenerator.onGenerateChunk(this.world, x, z, chunkprimer);
      MortuorusCavesGenerator.onGenerateChunk(this.world, x, z, chunkprimer, this.noise3d1);
      Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
      byte[] abyte = chunk.getBiomeArray();

      for (int i = 0; i < abyte.length; i++) {
         abyte[i] = (byte)Biome.getIdForBiome(this.biomesForGeneration[i]);
      }

      chunk.generateSkylightMap();
      return chunk;
   }

   private void generateHeightmap(int x, int y, int z) {
      this.depthRegion = this.depthNoise
         .generateNoiseOctaves(
            this.depthRegion,
            x,
            z,
            5,
            5,
            this.settings.depthNoiseScaleX,
            this.settings.depthNoiseScaleZ,
            this.settings.depthNoiseScaleExponent
         );
      float f = this.settings.coordinateScale;
      float f1 = this.settings.heightScale;
      this.mainNoiseRegion = this.mainPerlinNoise
         .generateNoiseOctaves(
            this.mainNoiseRegion,
            x,
            y,
            z,
            5,
            33,
            5,
            f / this.settings.mainNoiseScaleX,
            f1 / this.settings.mainNoiseScaleY,
            f / this.settings.mainNoiseScaleZ
         );
      this.minLimitRegion = this.minLimitPerlinNoise.generateNoiseOctaves(this.minLimitRegion, x, y, z, 5, 33, 5, f, f1, f);
      this.maxLimitRegion = this.maxLimitPerlinNoise.generateNoiseOctaves(this.maxLimitRegion, x, y, z, 5, 33, 5, f, f1, f);
      int i = 0;
      int j = 0;

      for (int k = 0; k < 5; k++) {
         for (int l = 0; l < 5; l++) {
            float f2 = 0.0F;
            float f3 = 0.0F;
            float f4 = 0.0F;
            int i1 = 2;
            Biome biome = this.biomesForGeneration[k + 2 + (l + 2) * 10];

            for (int j1 = -2; j1 <= 2; j1++) {
               for (int k1 = -2; k1 <= 2; k1++) {
                  Biome biome1 = this.biomesForGeneration[k + j1 + 2 + (l + k1 + 2) * 10];
                  float f5 = this.settings.biomeDepthOffSet + biome1.getBaseHeight() * this.settings.biomeDepthWeight;
                  float f6 = this.settings.biomeScaleOffset + biome1.getHeightVariation() * this.settings.biomeScaleWeight;
                  if (this.terrainType == WorldType.AMPLIFIED && f5 > 0.0F) {
                     f5 = 1.0F + f5 * 2.0F;
                     f6 = 1.0F + f6 * 4.0F;
                  }

                  float f7 = this.biomeWeights[j1 + 2 + (k1 + 2) * 5] / (f5 + 2.0F);
                  if (biome1.getBaseHeight() > biome.getBaseHeight()) {
                     f7 /= 2.0F;
                  }

                  f2 += f6 * f7;
                  f3 += f5 * f7;
                  f4 += f7;
               }
            }

            f2 /= f4;
            f3 /= f4;
            f2 = f2 * 0.9F + 0.1F;
            f3 = (f3 * 4.0F - 1.0F) / 8.0F;
            double d7 = this.depthRegion[j] / 8000.0;
            if (d7 < 0.0) {
               d7 = -d7 * 0.3;
            }

            d7 = d7 * 3.0 - 2.0;
            if (d7 < 0.0) {
               d7 /= 2.0;
               if (d7 < -1.0) {
                  d7 = -1.0;
               }

               d7 /= 1.4;
               d7 /= 2.0;
            } else {
               if (d7 > 1.0) {
                  d7 = 1.0;
               }

               d7 /= 8.0;
            }

            j++;
            double d8 = f3;
            double d9 = f2;
            d8 += d7 * 0.2;
            d8 = d8 * this.settings.baseSize / 8.0;
            double d0 = this.settings.baseSize + d8 * 4.0;

            for (int l1 = 0; l1 < 33; l1++) {
               double d1 = (l1 - d0) * this.settings.stretchY * 128.0 / 256.0 / d9;
               if (d1 < 0.0) {
                  d1 *= 4.0;
               }

               double d2 = this.minLimitRegion[i] / this.settings.lowerLimitScale;
               double d3 = this.maxLimitRegion[i] / this.settings.upperLimitScale;
               double d4 = (this.mainNoiseRegion[i] / 10.0 + 1.0) / 2.0;
               double d5 = MathHelper.clampedLerp(d2, d3, d4) - d1;
               if (l1 > 29) {
                  double d6 = (l1 - 29) / 3.0F;
                  d5 = d5 * (1.0 - d6) + -10.0 * d6;
               }

               this.heightMap[i] = d5;
               i++;
            }
         }
      }
   }

   public void populate(int x, int z) {
      BlockFalling.fallInstantly = true;
      int i = x * 16;
      int j = z * 16;
      BlockPos blockpos = new BlockPos(i, 0, j);
      Biome biome = this.world.getBiome(blockpos.add(16, 0, 16));
      this.rand.setSeed(this.world.getSeed());
      long k = this.rand.nextLong() / 2L * 2L + 1L;
      long l = this.rand.nextLong() / 2L * 2L + 1L;
      this.rand.setSeed(x * k + z * l ^ this.world.getSeed());
      boolean flag = false;
      new ChunkPos(x, z);
      ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, x, z, flag);
      if (this.settings.useWaterLakes && !flag && this.rand.nextInt(this.settings.waterLakeChance) == 0) {
         int i1 = this.rand.nextInt(16) + 8;
         int j1 = this.rand.nextInt(256);
         int k1 = this.rand.nextInt(16) + 8;
         new WorldGenLakes(BlocksRegister.FLUIDDARKNESS).generate(this.world, this.rand, blockpos.add(i1, j1, k1));
      }

      if (!flag
         && this.rand.nextInt(this.settings.lavaLakeChance / 10) == 0
         && this.settings.useLavaLakes
         && TerrainGen.populate(this, this.world, this.rand, x, z, flag, EventType.LAVA)) {
         int i2 = this.rand.nextInt(16) + 8;
         int l2 = this.rand.nextInt(this.rand.nextInt(248) + 8);
         int k3 = this.rand.nextInt(16) + 8;
         if (l2 < this.world.getSeaLevel() || this.rand.nextInt(this.settings.lavaLakeChance / 8) == 0) {
            new WorldGenLakes(Blocks.LAVA).generate(this.world, this.rand, blockpos.add(i2, l2, k3));
         }
      }

      BlockPos pos = new BlockPos(i, 0, j);
      biome.decorate(this.world, this.rand, pos);
      if (TerrainGen.populate(this, this.world, this.rand, x, z, flag, EventType.ANIMALS)) {
         WorldEntitySpawner.performWorldGenSpawning(this.world, biome, i + 8, j + 8, 16, 16, this.rand);
      }

      blockpos = blockpos.add(8, 0, 8);
      if (TerrainGen.populate(this, this.world, this.rand, x, z, flag, EventType.ICE)) {
         for (int k2 = 0; k2 < 16; k2++) {
            for (int j3 = 0; j3 < 16; j3++) {
               BlockPos blockpos1 = this.world.getPrecipitationHeight(blockpos.add(k2, 0, j3));
               BlockPos blockpos2 = blockpos1.down();
               if (this.world.canBlockFreezeWater(blockpos2)) {
                  this.world.setBlockState(blockpos2, Blocks.ICE.getDefaultState(), 2);
               }

               if (this.world.canSnowAt(blockpos1, true)) {
                  this.world.setBlockState(blockpos1, Blocks.SNOW_LAYER.getDefaultState(), 2);
               }
            }
         }
      }

      ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, x, z, flag);
      BlockFalling.fallInstantly = false;
   }

   public boolean generateStructures(Chunk chunkIn, int x, int z) {
      return false;
   }

   public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
      Biome biome = this.world.getBiome(pos);
      return biome.getSpawnableList(creatureType);
   }

   public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
      return false;
   }

   @Nullable
   public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
      return null;
   }

   public void recreateStructures(Chunk chunkIn, int x, int z) {
   }
}
