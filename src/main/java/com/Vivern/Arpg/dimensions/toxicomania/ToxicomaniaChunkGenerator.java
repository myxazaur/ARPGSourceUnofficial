package com.Vivern.Arpg.dimensions.toxicomania;

import com.Vivern.Arpg.biomes.BiomeControlled;
import com.Vivern.Arpg.blocks.BlockSpeleothem;
import com.Vivern.Arpg.blocks.ToxiberryTreeLog;
import com.Vivern.Arpg.dimensions.generationutils.CustomOreGenerator;
import com.Vivern.Arpg.dimensions.generationutils.GenerationHelper;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenPetroleum;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenSpread;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenToxicBarrels;
import com.Vivern.Arpg.loot.ListLootTable;
import com.Vivern.Arpg.main.BiomesRegister;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.mobs.ToxicomaniaMobsPack;
import com.Vivern.Arpg.tileentity.ChestLock;
import com.Vivern.Arpg.tileentity.TileARPGChest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
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
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextOverworld;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType;

public class ToxicomaniaChunkGenerator implements IChunkGenerator {
   public static final IBlockState STONE = BlocksRegister.RADIOSTONE.getDefaultState();
   public static final IBlockState SLUDGE = BlocksRegister.SLUDGE.getDefaultState();
   public static final IBlockState JUNK = BlocksRegister.JUNK.getDefaultState();
   public static final IBlockState SCRAP = BlocksRegister.SCRAP.getDefaultState();
   public static final IBlockState DIRT = BlocksRegister.TOXICDIRT.getDefaultState();
   public static final IBlockState GRASS = BlocksRegister.TOXICGRASS.getDefaultState();
   public static final IBlockState WASTE = BlocksRegister.NUCLEARWASTE.getDefaultState();
   public static final IBlockState COBBLE = BlocksRegister.RADIOCOBBLE.getDefaultState();
   public static final IBlockState SLIME = BlocksRegister.BROWNSLIME.getDefaultState();
   public static final IBlockState WATER = BlocksRegister.FLUIDPOISON.getDefaultState();
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
   private final World world;
   private final WorldType terrainType;
   private final double[] heightMap;
   private final float[] biomeWeights;
   private ChunkGenSettingsToxic settings;
   private IBlockState oceanBlock = BlocksRegister.FLUIDPOISON.getDefaultState();
   private double[] depthBuffer = new double[256];
   private MapGenBase caveGenerator = new ToxicomaniaCavesMapGen();
   private MapGenMineshaft mineshaftGenerator = new MapGenMineshaft();
   private MapGenBase ravineGenerator = new ToxicomaniaRavineGen();
   private Biome[] biomesForGeneration;
   double[] mainNoiseRegion;
   double[] minLimitRegion;
   double[] maxLimitRegion;
   double[] depthRegion;
   public List<Long> dungeons = new ArrayList<>();
   public CustomOreGenerator arsenicOreGen;
   public CustomOreGenerator toxiniumOreGen;
   public CustomOreGenerator wolframOreGen;
   public CustomOreGenerator lepidoliteOreGen;
   public WorldGenSpread slimeblobs = new WorldGenSpread(BlocksRegister.SLIMEBLOB, 18, 5, 4, null, false);
   public WorldGenToxicBarrels barrels = new WorldGenToxicBarrels(20, 5, 3);
   public WorldGenSpread bones = new WorldGenSpread(BlocksRegister.BONESPILE, 15, 3, 2, null, false);
   public WorldGenPetroleum petroleumGenerator = new WorldGenPetroleum(
         BlocksRegister.FLUIDPETROLEUM.getDefaultState(),
         BlocksRegister.RADIOSTONE.getDefaultState(),
         BlocksRegister.FLUIDNATURALGAS.getDefaultState(),
         172,
         WorldGenPetroleum.defaultBlocksToReplace,
         16,
         10,
         22,
         0.3F,
         true
      )
      .setOptions(0.08, 0.04, 4.0, 2.0, 0.4, 0.6, 0.8, 1.2, 0.2, 0.8);
   public WorldGenPetroleum naturalGasGenerator = new WorldGenPetroleum(
         BlocksRegister.FLUIDNATURALGAS.getDefaultState(),
         BlocksRegister.RADIOSTONE.getDefaultState(),
         null,
         172,
         WorldGenPetroleum.defaultBlocksToReplace,
         20,
         9,
         21,
         0.0F,
         true
      )
      .setOptions(0.08, 0.04, 4.0, 2.0, 0.2, 0.5, 1.0, 1.1, 0.14, 0.45);
   public static WorldGenSpread blobs = new WorldGenSpread(BlocksRegister.SLIMEBLOB, 50, 4, 2, null);
   public static Block[] caveJunkPlants = new Block[]{
      BlocksRegister.MUCOPHILLUS, BlocksRegister.MUCOPHILLUSBROWN, BlocksRegister.VISCOSA, BlocksRegister.TOXIBERRYARCANO
   };

   public ToxicomaniaChunkGenerator(World worldIn, long seed) {
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
      this.heightMap = new double[825];
      this.biomeWeights = new float[25];
      this.arsenicOreGen = new CustomOreGenerator(
         BlocksRegister.OREARSENIC.getDefaultState(), 101, BlockMatcher.forBlock(BlocksRegister.RADIOSTONE), 4, 10, 6, 10, 50
      );
      this.toxiniumOreGen = new CustomOreGenerator(
         BlocksRegister.ORETOXINIUM.getDefaultState(), 101, BlockMatcher.forBlock(BlocksRegister.RADIOSTONE), 2, 8, 2, 6, 25
      );
      this.wolframOreGen = new CustomOreGenerator(
         BlocksRegister.OREWOLFRAM.getDefaultState(), 101, BlockMatcher.forBlock(BlocksRegister.RADIOSTONE), 2, 6, 8, 5, 55
      );
      this.lepidoliteOreGen = new CustomOreGenerator(
         BlocksRegister.ORELEPIDOLITE.getDefaultState(), 101, BlockMatcher.forBlock(BlocksRegister.RADIOSTONE), 2, 7, 3, 10, 30
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
                           primer.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, this.oceanBlock);
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
                     chunkPrimerIn.setBlockState(i1, j1, l, SLUDGE);
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
      if (this.settings.useCaves) {
         this.caveGenerator.generate(this.world, x, z, chunkprimer);
      }

      if (this.settings.useRavines) {
         this.ravineGenerator.generate(this.world, x, z, chunkprimer);
      }

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
         new WorldGenLakes(BlocksRegister.FLUIDPOISON).generate(this.world, this.rand, blockpos.add(i1, j1, k1));
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

      this.arsenicOreGen.generate(this.rand, x, z, this.world, this, null);
      this.toxiniumOreGen.generate(this.rand, x, z, this.world, this, null);
      this.wolframOreGen.generate(this.rand, x, z, this.world, this, null);
      this.lepidoliteOreGen.generate(this.rand, x, z, this.world, this, null);
      this.naturalGasGenerator.generate(this.rand, x, z, this.world, this, null);
      this.petroleumGenerator.generate(this.rand, x, z, this.world, this, null);
      if (this.rand.nextInt(15) == 0 && !this.isDungeonNearChunk(this.world, x, z, 10)) {
         this.dungeons.add(0L | x | (long)z << 32);
         int arraySize = 8 + this.rand.nextInt(5);
         int disp = arraySize * 11 / 2;
         BunkerGenerator.generateBunker(
            this.world,
            new BlockPos(i + this.rand.nextInt(16) - disp, 16 + this.rand.nextInt(9), j + this.rand.nextInt(16) - disp),
            arraySize,
            2 + this.rand.nextInt(3),
            this.rand.nextFloat() < 0.3 ? 2 : 3,
            this.rand
         );
      } else if (this.rand.nextInt(10) == 0 && !this.isDungeonNearChunk(this.world, x, z, 10)) {
         BlockPos pos1 = this.world.getTopSolidOrLiquidBlock(blockpos.add(2, 0, 2));
         BlockPos pos2 = this.world.getTopSolidOrLiquidBlock(blockpos.add(14, 0, 14));
         BlockPos pos3 = this.world.getTopSolidOrLiquidBlock(blockpos.add(2, 0, 14));
         BlockPos pos4 = this.world.getTopSolidOrLiquidBlock(blockpos.add(14, 0, 2));
         BlockPos pos5 = this.world.getTopSolidOrLiquidBlock(blockpos.add(8, 0, 8));
         if (!this.world.getBlockState(pos5).getMaterial().isLiquid()
            && Math.abs(pos5.getY() - pos1.getY()) < 4
            && Math.abs(pos5.getY() - pos2.getY()) < 4
            && Math.abs(pos5.getY() - pos3.getY()) < 4
            && Math.abs(pos5.getY() - pos4.getY()) < 4) {
            this.dungeons.add(0L | x | (long)z << 32);
            LabGenerator.generateLab(this.world, pos5, this.rand, 0.7F);
         }
      }

      BlockPos pos = new BlockPos(i, 0, j);

      for (int rt = 0; rt < 3; rt++) {
         BlockPos posit = pos.add(this.rand.nextInt(16) + 8, 0, this.rand.nextInt(16) + 8);
         List<BlockPos> listposes = new ArrayList<>();
         int height = 61;

         for (int rr = 12; rr < height; rr++) {
            BlockPos currentpos = posit.add(0, rr, 0);
            if (!this.world.isAirBlock(currentpos.down())
               && this.world.isAirBlock(currentpos)
               && this.world.isBlockFullCube(currentpos.down())) {
               listposes.add(currentpos);
            }
         }

         if (!listposes.isEmpty()) {
            int maxc = this.rand.nextInt(3) + 1;

            for (int c = 0; c < maxc; c++) {
               BlockPos finalpos = listposes.get(this.rand.nextInt(listposes.size()));
               if (finalpos != null) {
                  if (this.rand.nextFloat() < 0.08F) {
                     GenerationHelper.genBlocksHeap(this.world, finalpos.up(3), this.rand, 17, BlocksRegister.SCRAPELECTRONICS.getDefaultState());
                  } else if (this.rand.nextFloat() < 0.3F
                     && this.world.getBlockState(finalpos.down()).getBlock() == BlocksRegister.RADIOSTONE
                     && this.world.getBlockState(finalpos.up()).getCollisionBoundingBox(this.world, finalpos.up()) == null) {
                     ToxicomaniaMobsPack.PoisonSpitter mob = new ToxicomaniaMobsPack.PoisonSpitter(this.world);
                     mob.setPosition(
                        finalpos.getX() + 0.45 + this.rand.nextFloat() * 0.1,
                        finalpos.getY(),
                        finalpos.getZ() + 0.45 + this.rand.nextFloat() * 0.1
                     );
                     this.world.spawnEntity(mob);
                     mob.onInitialSpawn();
                  } else if (this.rand.nextFloat() < 0.25F) {
                     genSlimePuddle(this.world, finalpos.down(), this.rand);
                  } else if (this.rand.nextFloat() < 0.33F) {
                     boolean b = this.rand.nextFloat() < 0.7F;
                     genPoisonPuddle(this.world, finalpos, this.rand, b ? BlocksRegister.FLUIDPOISON : BlocksRegister.FLUIDTOXIN, b);
                  } else if (this.rand.nextFloat() < 0.45F) {
                     genCaveJunkPlants(this.world, finalpos.up(3), this.rand);
                  } else if (this.rand.nextFloat() < 0.8F) {
                     this.barrels.generate(this.world, this.rand, finalpos);
                  } else {
                     this.bones.generate(this.world, this.rand, finalpos);
                  }
               }
            }
         }
      }

      if (this.rand.nextFloat() < (biome == BiomesRegister.TOXIC_SLIME_LAND ? 0.4F : 0.2F)) {
         BlockPos posit = pos.add(this.rand.nextInt(16) + 8, 0, this.rand.nextInt(16) + 8);
         List<BlockPos> listposes = new ArrayList<>();
         int height = 60;

         for (int rrx = 12; rrx < height; rrx++) {
            BlockPos currentpos = posit.add(0, rrx, 0);
            if (!this.world.isAirBlock(currentpos.down())
               && this.world.isAirBlock(currentpos)
               && this.world.isBlockFullCube(currentpos.down())) {
               listposes.add(currentpos);
            }
         }

         if (!listposes.isEmpty()) {
            for (BlockPos finalpos : listposes) {
               this.slimeblobs.generate(this.world, this.rand, finalpos);
            }
         }
      }

      this.generateSpeleothems(this.world, this.rand, pos);
      biome.decorate(this.world, this.rand, pos);
      int rrVinesMax = biome == BiomesRegister.TOXIC_JUNGLE ? 6 : 4;

      for (int rrxx = 0; rrxx < rrVinesMax; rrxx++) {
         BlockPos posit = pos.add(this.rand.nextInt(16) + 8, 0, this.rand.nextInt(16) + 8);
         genCaveVines(this.world, posit, this.rand);
      }

      if (this.rand.nextFloat() < 0.35) {
         BlockPos posit = pos.add(this.rand.nextInt(16) + 8, 0, this.rand.nextInt(16) + 8);
         int height = Math.min(this.world.getTopSolidOrLiquidBlock(posit).getY() - 5, 62);
         List<BlockPos> listposes = new ArrayList<>();

         for (int rrxx = 15; rrxx < height; rrxx++) {
            BlockPos currentpos = posit.add(0, rrxx, 0);
            if (this.world.isAirBlock(currentpos) && this.world.getBlockState(currentpos.down()).isTopSolid()) {
               listposes.add(currentpos);
            }
         }

         if (!listposes.isEmpty()) {
            BlockPos finalpos = listposes.get(this.rand.nextInt(listposes.size()));
            if (finalpos != null) {
               this.world.setBlockState(finalpos, BlocksRegister.CHESTRUSTED.getDefaultState());
               TileEntity tile = this.world.getTileEntity(finalpos);
               if (tile != null && tile instanceof TileARPGChest) {
                  ((TileARPGChest)tile).setLootTable(ListLootTable.CHESTS_RUSTED_UNDERGROUND, this.rand.nextLong());
                  ((TileARPGChest)tile).lockOrUnlockWith(ChestLock.RUSTED_KEY, true);
               }
            }
         }
      }

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

   public void generateSpeleothems(World world, Random rand, BlockPos pos) {
      Block blockstalact = BlocksRegister.RADIOACTIVESPELEOTHEM;

      for (int iix = 0; iix < 16; iix++) {
         for (int iiz = 0; iiz < 16; iiz++) {
            if (rand.nextFloat() < 0.255) {
               int px = iix + 8;
               int pz = iiz + 8;
               boolean lastblockStone = false;
               int prevstate = -1;
               IBlockState stateup = null;

               for (int yy = 132; yy > 11; yy--) {
                  BlockPos fpos = pos.add(px, yy, pz);
                  IBlockState state = world.getBlockState(fpos);
                  if (state.getBlock() == Blocks.AIR) {
                     if (lastblockStone) {
                        if (rand.nextFloat() < 0.2) {
                           world.setBlockState(fpos, blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 0), 2);
                           prevstate = 0;
                        } else if (rand.nextFloat() < 0.2) {
                           world.setBlockState(fpos, blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 1), 2);
                           prevstate = 1;
                        } else if (rand.nextFloat() < 0.07) {
                           world.setBlockState(fpos, blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 2), 2);
                           prevstate = 2;
                        } else if (rand.nextFloat() < 0.2) {
                           world.setBlockState(fpos, blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 7), 2);
                           prevstate = 7;
                        } else if (rand.nextFloat() < 0.8) {
                           world.setBlockState(fpos, blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 5), 2);
                           prevstate = 5;
                        }
                     } else if (prevstate >= 0) {
                        if (prevstate == 0) {
                           if (rand.nextFloat() < 0.5) {
                              world.setBlockState(fpos, blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 1), 2);
                              prevstate = 1;
                           } else {
                              world.setBlockState(fpos, blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 7), 2);
                              prevstate = 7;
                           }
                        } else if (prevstate == 1) {
                           if (rand.nextFloat() < 0.85) {
                              world.setBlockState(fpos, blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 2), 2);
                              prevstate = 2;
                           } else {
                              prevstate = -1;
                           }
                        } else if (prevstate == 2) {
                           if (rand.nextFloat() < 0.5) {
                              world.setBlockState(fpos, blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 2), 2);
                              prevstate = 2;
                           } else {
                              prevstate = -1;
                           }
                        } else {
                           prevstate = -1;
                        }
                     }

                     lastblockStone = false;
                  } else {
                     if (state.getBlock() == BlocksRegister.RADIOSTONE
                        && stateup != null
                        && (stateup.getBlock() == blockstalact || stateup.getBlock() == Blocks.AIR)
                        && fpos.getY() < 62) {
                        IBlockState state2 = world.getBlockState(fpos.up(2));
                        boolean b1 = false;
                        if (state2.getBlock() == blockstalact) {
                           int stat = (Integer)state2.getValue(BlockSpeleothem.TYPE);
                           if (stat == 0 && rand.nextFloat() < 0.4) {
                              world.setBlockState(fpos.up(), blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 4), 2);
                           } else if (stat == 1 && rand.nextFloat() < 0.4) {
                              world.setBlockState(fpos.up(), blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 3), 2);
                           } else {
                              b1 = true;
                           }
                        } else {
                           b1 = true;
                        }

                        if (b1) {
                           if (rand.nextFloat() < 0.2) {
                              world.setBlockState(fpos.up(), blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 4), 2);
                              if (rand.nextFloat() < 0.9) {
                                 if (rand.nextFloat() < 0.6) {
                                    world.setBlockState(fpos.up(2), blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 3), 2);
                                    IBlockState state3 = world.getBlockState(fpos.up(3));
                                    if (rand.nextFloat() < 0.6 && (state3.getBlock() == blockstalact || state3.getBlock() == Blocks.AIR)) {
                                       world.setBlockState(fpos.up(3), blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 2), 2);
                                       IBlockState state4 = world.getBlockState(fpos.up(4));
                                       if (rand.nextFloat() < 0.5
                                          && (state4.getBlock() == blockstalact || state4.getBlock() == Blocks.AIR)) {
                                          world.setBlockState(fpos.up(4), blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 2), 2);
                                          IBlockState state5 = world.getBlockState(fpos.up(5));
                                          if (rand.nextFloat() < 0.5
                                             && (state5.getBlock() == blockstalact || state5.getBlock() == Blocks.AIR)) {
                                             world.setBlockState(fpos.up(5), blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 2), 2);
                                          }
                                       }
                                    }
                                 } else {
                                    world.setBlockState(fpos.up(2), blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 8), 2);
                                 }
                              }
                           } else if (rand.nextFloat() < 0.65) {
                              if (rand.nextFloat() < 0.85) {
                                 world.setBlockState(fpos.up(), blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 6), 2);
                              } else {
                                 world.setBlockState(fpos.up(), blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 8), 2);
                              }
                           }
                        }
                     }

                     if (state.getBlock() == BlocksRegister.RADIOSTONE) {
                        lastblockStone = true;
                        prevstate = -1;
                     }
                  }

                  stateup = state;
               }
            }
         }
      }
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

   public boolean isDungeonNearChunk(World world, int x, int z, int range) {
      for (int xx = -range; xx <= range; xx++) {
         for (int zz = -range; zz <= range; zz++) {
            for (long l : this.dungeons) {
               if (l == (0L | xx + x | (long)(zz + z) << 32)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   @Nullable
   public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
      return null;
   }

   public void recreateStructures(Chunk chunkIn, int x, int z) {
   }

   public static void genLootBlob(World world, BlockPos pos, Random rand) {
      for (EnumFacing f : EnumFacing.HORIZONTALS) {
         for (int i = 0; i < rand.nextInt(5); i++) {
            world.setBlockState(pos.offset(f).up(i), BlocksRegister.BROWNSLIME.getDefaultState(), 2);
         }
      }

      int add = rand.nextFloat() < 0.6 ? 1 : 0;
      int max = rand.nextInt(6) + 4;

      for (int i = 0; i <= max; i++) {
         world.setBlockState(pos.up(i), BlocksRegister.BROWNSLIME.getDefaultState(), 2);
         if (rand.nextFloat() < 0.05) {
            pos = pos.offset(EnumFacing.byHorizontalIndex(rand.nextInt(4)));
         }

         if (i == max) {
            for (EnumFacing f : EnumFacing.HORIZONTALS) {
               for (int i2 = 1; i2 < rand.nextInt(3) + 1 + add; i2++) {
                  world.setBlockState(pos.offset(f).up(i - i2), BlocksRegister.BROWNSLIME.getDefaultState(), 2);
               }
            }

            world.setBlockState(pos.up(i - 1), BlocksRegister.LOOTBLOB.getDefaultState(), 2);
            if (add > 0 && rand.nextFloat() < 0.2F) {
               world.setBlockState(pos.up(i - 2), BlocksRegister.LOOTBLOB.getDefaultState(), 2);
            }
         }
      }

      blobs.generate(world, rand, pos);
   }

   public static void genNectarFlowerPlace(World world, BlockPos pos, Random rand) {
      for (int i = 0; i <= 25; i++) {
         BlockPos pos2 = pos.add(rand.nextGaussian() * 2.0, 0.0, rand.nextGaussian() * 2.0);
         world.setBlockState(pos2, BlocksRegister.TOXIBERRYLOG.getDefaultState().withProperty(ToxiberryTreeLog.LOG_AXIS, EnumAxis.values()[rand.nextInt(3)]), 2);
      }

      for (int i = 0; i <= 95; i++) {
         BlockPos pos2 = pos.add(rand.nextGaussian() * 4.0, -1.0, rand.nextGaussian() * 4.0);
         if (world.getBlockState(pos2).getBlock().isReplaceable(world, pos2)) {
            world.setBlockState(pos2, BlocksRegister.TOXIBERRYLEAVES.getDefaultState(), 2);
         }
      }

      for (int ix = 0; ix <= 30; ix++) {
         BlockPos pos2 = pos.add(rand.nextGaussian() * 6.5, 6.0, rand.nextGaussian() * 6.5);
         BlockPos pos3 = GetMOP.getTrueHeight(world, pos2);
         BlockPos pos4 = pos3.up();
         if (world.getBlockState(pos4).getBlock().isReplaceable(world, pos4)
            && world.getBlockState(pos3).getBlock() != BlocksRegister.CHLORINEBELCHER
            && world.getBlockState(pos3).getBlock() != BlocksRegister.SWEETNECTARFLOWER) {
            world.setBlockState(pos4, BlocksRegister.CHLORINEBELCHER.getDefaultState(), 2);
         }
      }

      for (int ixx = 0; ixx <= 8; ixx++) {
         BlockPos pos2 = pos.add(rand.nextGaussian() * 2.0, 0.0, rand.nextGaussian() * 2.0);
         if (world.getBlockState(pos2.down()).getBlock() == BlocksRegister.TOXIBERRYLOG) {
            world.setBlockState(pos2, BlocksRegister.MUTAFLOWERPINK.getDefaultState(), 2);
         }
      }

      for (int ixxx = 0; ixxx <= 8; ixxx++) {
         BlockPos pos2 = pos.add(rand.nextGaussian() * 2.0, 0.0, rand.nextGaussian() * 2.0);
         if (world.getBlockState(pos2.down()).getBlock() == BlocksRegister.TOXIBERRYLOG) {
            world.setBlockState(pos2, BlocksRegister.MUTAFLOWERRED.getDefaultState(), 2);
         }
      }

      world.setBlockState(pos.up(), BlocksRegister.SWEETNECTARFLOWER.getDefaultState(), 2);
   }

   public static void genCaveVines(World world, BlockPos pos, Random rand) {
      boolean stone = false;

      for (int y = 64; y > 13; y--) {
         BlockPos posf = new BlockPos(pos.getX(), y, pos.getZ());
         Block block = world.getBlockState(posf).getBlock();
         if (block == BlocksRegister.RADIOSTONE) {
            stone = true;
         } else if (block == Blocks.AIR) {
            if (stone) {
               if (rand.nextFloat() < 0.3F) {
                  stone = false;
                  if (rand.nextFloat() < 0.75F) {
                     world.setBlockState(posf, BlocksRegister.LOPPYTOXIBERRY.getDefaultState());
                  }
               } else {
                  world.setBlockState(
                     posf, rand.nextFloat() < 0.76F ? BlocksRegister.LOPPYTOXISTEM.getDefaultState() : BlocksRegister.LOPPYTOXIBERRY.getDefaultState()
                  );
               }
            }
         } else {
            stone = false;
         }
      }
   }

   public static void genCaveJunkPlants(World world, BlockPos pos, Random rand) {
      for (int i = 0; i < 15; i++) {
         BlockPos fpos = GetMOP.getTrueHeight(world, pos.add(rand.nextGaussian(), 0.0, rand.nextGaussian())).up();
         if (world.getBlockState(fpos).getBlock() == Blocks.AIR) {
            world.setBlockState(fpos, JUNK);
         }
      }

      IBlockState plant = caveJunkPlants[rand.nextInt(caveJunkPlants.length)].getDefaultState();

      for (int ix = 0; ix < 10; ix++) {
         BlockPos fpos = GetMOP.getTrueHeight(world, pos.add(rand.nextGaussian(), 0.0, rand.nextGaussian()));
         if (world.getBlockState(fpos).getBlock() == JUNK.getBlock()
            && world.getBlockState(fpos.up()).getBlock() == Blocks.AIR) {
            world.setBlockState(fpos.up(), plant);
         }
      }
   }

   public static void genSlimePuddle(World world, BlockPos pos, Random rand) {
      int maxi = rand.nextInt(26) + 16;

      for (int i = 0; i < maxi; i++) {
         BlockPos fpos = pos.add(rand.nextGaussian() * 1.4, 0.0, rand.nextGaussian() * 1.4);
         Block vlod = world.getBlockState(fpos).getBlock();
         if (vlod == BlocksRegister.RADIOSTONE && !GetMOP.collidesWithBlockHorizontal(world, fpos, Blocks.AIR)) {
            world.setBlockState(fpos, BlocksRegister.FLUIDSLIME.getDefaultState());
         } else if (vlod == BlocksRegister.FLUIDSLIME) {
            world.setBlockState(fpos, BlocksRegister.BROWNSLIME.getDefaultState());
         } else if (vlod == BlocksRegister.BROWNSLIME && world.getBlockState(fpos.up()).getBlock() == Blocks.AIR) {
            world.setBlockState(fpos.up(), BlocksRegister.SLIMEBLOB.getDefaultState());
         }
      }
   }

   public static void genPoisonPuddle(World world, BlockPos pos, Random rand, Block fluid, boolean lilypad) {
      int maxi = rand.nextInt(26) + 16;

      for (int i = 0; i < maxi; i++) {
         BlockPos fpos = pos.add(rand.nextGaussian() * 1.4, 0.0, rand.nextGaussian() * 1.4);
         BlockPos downpos = GetMOP.getTrueHeight(world, fpos);
         Block vlod = world.getBlockState(downpos).getBlock();
         if (vlod == BlocksRegister.RADIOSTONE
            && world.getBlockState(downpos.down()).isOpaqueCube()
            && !GetMOP.collidesWithBlockHorizontal(world, downpos, Blocks.AIR)) {
            world.setBlockState(downpos, fluid.getDefaultState());
            if (lilypad && downpos.getY() == pos.getY() - 1 && rand.nextFloat() < 0.16F) {
               world.setBlockState(fpos, BlocksRegister.POISONLILY.getDefaultState());
            }
         }
      }
   }
}
