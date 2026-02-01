package com.vivern.arpg.dimensions.aquatica;

import com.vivern.arpg.biomes.BiomeControlled;
import com.vivern.arpg.blocks.GiantShell;
import com.vivern.arpg.blocks.MiniCoral;
import com.vivern.arpg.dimensions.generationutils.GenerationHelper;
import com.vivern.arpg.dimensions.generationutils.WorldGenCoral;
import com.vivern.arpg.dimensions.generationutils.WorldGenMetallicCoral;
import com.vivern.arpg.dimensions.generationutils.WorldGenMiniCoral;
import com.vivern.arpg.elements.ItemShell;
import com.vivern.arpg.entity.EntityHangingAllSides;
import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.loot.ListLootTable;
import com.vivern.arpg.main.BiomesRegister;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.NoiseGenerator3D;
import com.vivern.arpg.mobs.SpawnerTuners;
import com.vivern.arpg.tileentity.EnumChest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSand.EnumType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextOverworld;

public class AquaticaChunkGenerator implements IChunkGenerator {
   public static final IBlockState STONE = BlocksRegister.SHELLROCK.getDefaultState();
   public static final IBlockState STONE2 = BlocksRegister.CHALKROCK.getDefaultState();
   public static final IBlockState AIR = Blocks.AIR.getDefaultState();
   public static final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
   public static final IBlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
   public static final IBlockState RED_SANDSTONE = Blocks.RED_SANDSTONE.getDefaultState();
   public static final IBlockState SANDSTONE = Blocks.SANDSTONE.getDefaultState();
   public static final IBlockState ICE = Blocks.ICE.getDefaultState();
   public static final IBlockState WATER = Blocks.WATER.getDefaultState();
   public static final IBlockState SAND = Blocks.SAND.getDefaultState();
   public final Random rand;
   public NoiseGeneratorOctaves minLimitPerlinNoise;
   public NoiseGeneratorOctaves maxLimitPerlinNoise;
   public NoiseGeneratorOctaves mainPerlinNoise;
   public NoiseGeneratorPerlin surfaceNoise;
   public NoiseGeneratorOctaves scaleNoise;
   public NoiseGeneratorOctaves depthNoise;
   public NoiseGeneratorOctaves forestNoise;
   public NoiseGeneratorPerlin terrainControlNoise;
   public NoiseGeneratorPerlin caveNoise1;
   public NoiseGeneratorPerlin caveNoise2;
   public NoiseGenerator3D noisegenerator3dbiomes;
   public final World world;
   public final boolean mapFeaturesEnabled;
   public final WorldType terrainType;
   public final double[] heightMap;
   public final float[] biomeWeights;
   public ChunkGenSettingsAqua settings;
   public IBlockState oceanBlock = Blocks.WATER.getDefaultState();
   public double[] depthBuffer = new double[256];
   public MapGenBase caveGenerator = new AquaticaCavesMapGen(false);
   public MapGenBase waterCaveGenerator = new AquaticaCavesMapGen(true);
   public MapGenBase ravineGenerator = new MapGenRavineAquatica();
   public static WorldGenMinable cavegeneratorSeastone = new WorldGenMinable(
      BlocksRegister.SEASTONE.getDefaultState(), 24, BlockMatcher.forBlock(BlocksRegister.SHELLROCK)
   );
   public static WorldGenMinable cavegeneratorChalkrock = new WorldGenMinable(
      BlocksRegister.CHALKROCK.getDefaultState(), 24, BlockMatcher.forBlock(BlocksRegister.SHELLROCK)
   );
   public static WorldGenMinable cavegeneratorSand = new WorldGenMinable(SAND, 33, BlockMatcher.forBlock(BlocksRegister.SHELLROCK));
   public Biome[] biomesForGeneration;
   double[] mainNoiseRegion;
   double[] minLimitRegion;
   double[] maxLimitRegion;
   double[] depthRegion;
   public static Block[] corals = new Block[]{
      BlocksRegister.CORALYELLOW, BlocksRegister.CORALPINK, BlocksRegister.CORALWHITE, BlocksRegister.CORALORANGE, BlocksRegister.CORALRED
   };
   public static Block[] coralsMini = new Block[]{
      BlocksRegister.MINICORALBLUE,
      BlocksRegister.MINICORALBROWN,
      BlocksRegister.MINICORALPURPLE,
      BlocksRegister.MINICORALRED,
      BlocksRegister.MINICORALWHITE,
      BlocksRegister.MINICORALWHITE2,
      BlocksRegister.MINICORALWHITE2
   };
   public static Block[] coralsFavia = new Block[]{
      BlocksRegister.MINICORALFAVIABLUE,
      BlocksRegister.MINICORALFAVIAGREEN,
      BlocksRegister.MINICORALFAVIARED,
      BlocksRegister.MINICORALFAVIAYELLOW,
      BlocksRegister.MINICORALBRAIN
   };
   public static Block[] corallimorphas = new Block[]{
      BlocksRegister.CORALLIMORPHABLUE,
      BlocksRegister.CORALLIMORPHABROWN,
      BlocksRegister.CORALLIMORPHAGREEN,
      BlocksRegister.CORALLIMORPHALILAC,
      BlocksRegister.CORALLIMORPHAPINK,
      BlocksRegister.CORALLIMORPHARED,
      BlocksRegister.CORALLIMORPHAYELLOW
   };
   public static Block[] coralsBig = new Block[]{
      BlocksRegister.MINICORALBROWNBIG,
      BlocksRegister.MINICORALPURPLEBIG,
      BlocksRegister.MINICORALREDBIG,
      BlocksRegister.MINICORALWHITE2BIG,
      BlocksRegister.MINICORALWHITEBIG
   };
   public static Block[] coralsGlow = new Block[]{
      BlocksRegister.ACTINIFORABLUE,
      BlocksRegister.ACTINIFORABLUEBIG,
      BlocksRegister.ACTINIFORARED,
      BlocksRegister.ACTINIFORAREDBIG,
      BlocksRegister.ACTINIFORAYELLOW
   };
   public static int sealvl = 194;
   public WorldGenMetallicCoral coralOreGenerator = new WorldGenMetallicCoral(16, true);
   public static IBlockState CA_STATE = Blocks.STONE.getDefaultState();
   public static boolean[][] ccallrules = new boolean[][]{
      {true, false, true, true, true, false, false, true, false, false, false, true, false, true, false, true, true, true},
      {true, false, true, false, true, false, false, true, false, false, false, true, false, true, false, true, true, true},
      {true, true, true, false, true, false, false, true, false, false, false, true, false, true, false, true, true, false},
      {true, true, true, false, true, false, true, true, false, false, false, true, false, true, false, true, true, false},
      {true, true, true, false, true, false, true, true, false, false, false, true, false, true, false, true, true, true},
      {true, false, true, false, true, false, true, true, false, false, false, true, false, true, false, true, true, false},
      {true, false, true, false, true, false, false, true, false, false, false, true, false, true, false, true, true, false},
      {false, false, false, false, true, false, false, true, false, false, false, true, false, true, false, true, false, false},
      {false, false, false, false, true, false, true, true, false, false, false, true, false, true, false, true, false, false},
      {false, false, false, false, true, false, true, true, false, false, false, true, false, true, false, true, true, false}
   };

   public AquaticaChunkGenerator(World worldIn, long seed) {
      this.caveGenerator = TerrainGen.getModdedMapGen(this.caveGenerator, EventType.CAVE);
      this.ravineGenerator = TerrainGen.getModdedMapGen(this.ravineGenerator, EventType.RAVINE);
      this.world = worldIn;
      this.mapFeaturesEnabled = false;
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
      this.caveNoise1 = new NoiseGeneratorPerlin(new Random(worldIn.getSeed() + 1L), 4);
      this.caveNoise2 = new NoiseGeneratorPerlin(new Random(worldIn.getSeed() + 2L), 4);
      this.noisegenerator3dbiomes = new NoiseGenerator3D(worldIn.getSeed());
      this.heightMap = new double[825];
      this.biomeWeights = new float[25];

      for (int i = -2; i <= 2; i++) {
         for (int j = -2; j <= 2; j++) {
            float f = 10.0F / MathHelper.sqrt(i * i + j * j + 0.2F);
            this.biomeWeights[i + 2 + (j + 2) * 5] = f;
         }
      }

      this.settings = new ChunkGenSettingsAqua.Factory().build();
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

   public void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomesIn) {
      if (ForgeEventFactory.onReplaceBiomeBlocks(this, x, z, primer, this.world)) {
         double d0 = 0.03125;
         this.depthBuffer = this.surfaceNoise.getRegion(this.depthBuffer, x * 16, z * 16, 16, 16, 0.0625, 0.0625, 1.0);

         for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
               Biome biome = biomesIn[j + i * 16];
               if (biome instanceof BiomeControlled) {
                  this.generateAdvBiomeTerrain(this.world, this.rand, primer, x * 16 + i, z * 16 + j, this.depthBuffer[j + i * 16], biome);
               }

               biome.genTerrainBlocks(this.world, this.rand, primer, x * 16 + i, z * 16 + j, this.depthBuffer[j + i * 16]);
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
      if (this.settings.useRavines) {
         this.ravineGenerator.generate(this.world, x, z, chunkprimer);
      }

      if (this.settings.useCaves) {
         this.caveGenerator.generate(this.world, x, z, chunkprimer);
         this.waterCaveGenerator.generate(this.world, x, z, chunkprimer);
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
            double d7 = this.depthRegion[j] / 2000.0;
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
            double stretchY = biome == BiomesRegister.REEF ? 5.0 : this.settings.stretchY;

            for (int l1 = 0; l1 < 33; l1++) {
               double d1 = (l1 - d0) * stretchY * 128.0 / 256.0 / d9;
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
      if (biome != BiomesRegister.ISLANDS
         && biome != BiomesRegister.AQUATICABEACH
         && biome != BiomesRegister.SHALLOW
         && this.settings.useWaterLakes
         && !flag
         && this.rand.nextInt(this.settings.waterLakeChance) == 0
         && TerrainGen.populate(this, this.world, this.rand, x, z, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.LAKE)) {
         int i1 = this.rand.nextInt(16) + 8;
         int j1 = this.rand.nextInt(256);
         int k1 = this.rand.nextInt(16) + 8;
         new WorldGenLakes(Blocks.WATER).generate(this.world, this.rand, blockpos.add(i1, j1, k1));
      }

      if (!flag
         && this.rand.nextInt(this.settings.lavaLakeChance / 10) == 0
         && this.settings.useLavaLakes
         && TerrainGen.populate(this, this.world, this.rand, x, z, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.LAVA)) {
         int i2 = this.rand.nextInt(16) + 8;
         int l2 = this.rand.nextInt(this.rand.nextInt(248) + 8);
         int k3 = this.rand.nextInt(16) + 8;
         if (l2 < this.world.getSeaLevel() || this.rand.nextInt(this.settings.lavaLakeChance / 8) == 0) {
            new WorldGenLakes(Blocks.LAVA).generate(this.world, this.rand, blockpos.add(i2, l2, k3));
         }
      }

      for (int j2 = 0; j2 < 4; j2++) {
         int xx = x * 16 + this.rand.nextInt(16);
         int yy = 30 + this.rand.nextInt(30);
         int zz = z * 16 + this.rand.nextInt(16);
         cavegeneratorSand.generate(this.world, this.rand, new BlockPos(xx, yy, zz));
      }

      for (int j2 = 0; j2 < 6; j2++) {
         int xx = x * 16 + this.rand.nextInt(16);
         int yy = 30 + this.rand.nextInt(60);
         int zz = z * 16 + this.rand.nextInt(16);
         cavegeneratorChalkrock.generate(this.world, this.rand, new BlockPos(xx, yy, zz));
      }

      if (this.settings.useDungeons
         && TerrainGen.populate(this, this.world, this.rand, x, z, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.DUNGEON)) {
         for (int j2 = 0; j2 < this.settings.dungeonChance; j2++) {
            int i3 = this.rand.nextInt(16) + 8;
            int l3 = this.rand.nextInt(256);
            int l1 = this.rand.nextInt(16) + 8;
            new WorldGenDungeons().generate(this.world, this.rand, blockpos.add(i3, l3, l1));
         }
      }

      if (hasJellyfishCaveAtChunk(x, z, this.world)) {
         GetMOP.BlockTraceResult result = GetMOP.blockTrace(
            this.world,
            new BlockPos(i, 0, j).add(this.rand.nextInt(16) + 8, sealvl, this.rand.nextInt(16) + 8),
            EnumFacing.DOWN,
            sealvl - 32,
            GetMOP.SOLID_BLOCKS
         );
         if (result != null) {
            Block cr = corals[this.rand.nextInt(5)];
            WorldGenCoral c = new WorldGenCoral(1.2F, 0.77F, cr);
            WorldGenMiniCoral mc = new WorldGenMiniCoral(randomMinicoral(1.2F, this.rand), 0, 20, 2, 2);
            generateJellyfishCave(
               this.world,
               result.pos,
               this.rand,
               5,
               9,
               74,
               new Vec3d(this.rand.nextFloat() - 0.5F, -0.4F, this.rand.nextFloat() - 0.5F).normalize(),
               c,
               mc,
               cr.getDefaultState()
            );
         }
      }

      if (hasSunkenTownAtChunk(x, z, this.world)) {
         GetMOP.BlockTraceResult result = GetMOP.blockTrace(
            this.world,
            new BlockPos(i, 0, j).add(this.rand.nextInt(16) + 8, sealvl - 16, this.rand.nextInt(16) + 8),
            EnumFacing.DOWN,
            sealvl - 32,
            GetMOP.SOLID_BLOCKS
         );
         if (result != null) {
            SunkenTown town = new SunkenTown(this.world, this.rand, result.prevPos);
            town.generateSunkenTown(result.prevPos);
         }
      }

      if (hasSirenSanctuaryAtChunk(x, z, this.world)) {
         GetMOP.BlockTraceResult result = GetMOP.blockTrace(
            this.world,
            new BlockPos(i, 0, j).add(this.rand.nextInt(16) + 8, 250, this.rand.nextInt(16) + 8),
            EnumFacing.DOWN,
            250 - sealvl + 2,
            state -> state.getMaterial().isLiquid() || GetMOP.SOLID_BLOCKS.apply(state)
         );
         if (result != null) {
            SirenSanctuary town = new SirenSanctuary(this.world, 60, this.rand, 30, 2, 5, 0.1F);
            town.generate(result.prevPos);
         }
      }

      if (!this.world.isRemote) {
         if (this.rand.nextFloat() < 0.001) {
            int ix = this.rand.nextInt(16) + 8;
            int lz = this.rand.nextInt(16) + 8;
            BlockPos fpos = this.world.getTopSolidOrLiquidBlock(blockpos.add(ix, 0, lz));
            int rt = this.rand.nextInt(4);
            BlockPos fposadd;
            if (rt == 0) {
               fposadd = fpos.add(29, 0, 0);
            } else if (rt == 1) {
               fposadd = fpos.add(0, 0, 29);
            } else if (rt == 2) {
               fposadd = fpos.add(-29, 0, 0);
            } else {
               fposadd = fpos.add(0, 0, -29);
            }

            fposadd = this.world.getTopSolidOrLiquidBlock(fposadd);
            if (fposadd.getY() < fpos.getY()) {
               fpos = new BlockPos(fpos.getX(), fposadd.getY(), fpos.getZ());
            } else if (fposadd.getY() > fpos.getY()) {
               fposadd = new BlockPos(fposadd.getX(), fpos.getY(), fposadd.getZ());
            }

            placeStruct(this.world, fpos, this.rand, ":sea_fossil_a1", 14, -8, rt);
            placeStruct(this.world, fposadd, this.rand, ":sea_fossil_b1", 14, -8, rt);
         }

         if (this.rand.nextFloat() < 0.04) {
            int ixx = this.rand.nextInt(16) + 8;
            int lzx = this.rand.nextInt(16) + 8;
            BlockPos fposx = this.world.getTopSolidOrLiquidBlock(blockpos.add(ixx, 0, lzx));
            fposx = new BlockPos(fposx.getX(), Math.max(fposx.getY() - 14, 14) * this.rand.nextDouble() + 14.0, fposx.getZ());
            int rtx = this.rand.nextInt(4);
            BlockPos fposaddx;
            if (rtx == 0) {
               fposaddx = fposx.add(29, 0, 0);
            } else if (rtx == 1) {
               fposaddx = fposx.add(0, 0, 29);
            } else if (rtx == 2) {
               fposaddx = fposx.add(-29, 0, 0);
            } else {
               fposaddx = fposx.add(0, 0, -29);
            }

            placeStruct(this.world, fposx, this.rand, ":sea_fossil_a1", 14, -8, rtx);
            placeStruct(this.world, fposaddx, this.rand, ":sea_fossil_b1", 14, -8, rtx);
         }

         if (this.rand.nextFloat() < 0.005) {
            int ixx = this.rand.nextInt(16) + 8;
            int lzx = this.rand.nextInt(16) + 8;
            BlockPos fposx = this.world.getTopSolidOrLiquidBlock(blockpos.add(ixx, 0, lzx));
            int structN = this.rand.nextInt(4) + 2;
            int offsetXZ = 0;
            int offsetY = 0;
            if (structN == 2) {
               offsetXZ = 8;
               offsetY = -2;
            }

            if (structN == 3) {
               offsetXZ = 10;
               offsetY = -1;
            }

            if (structN == 4) {
               offsetXZ = 11;
               offsetY = -1;
            }

            if (structN == 5) {
               offsetXZ = 4;
               offsetY = 0;
            }

            placeStruct(this.world, fposx, this.rand, ":sea_fossil_" + structN, offsetXZ, offsetY, this.rand.nextInt(4));
         }

         if (this.rand.nextFloat() < 0.2) {
            int ixxx = this.rand.nextInt(16) + 8;
            int lzxx = this.rand.nextInt(16) + 8;
            BlockPos fposxx = this.world.getTopSolidOrLiquidBlock(blockpos.add(ixxx, 0, lzxx));
            fposxx = new BlockPos(fposxx.getX(), Math.max(fposxx.getY() - 14, 14) * this.rand.nextDouble() + 14.0, fposxx.getZ());
            int structNx = this.rand.nextInt(4) + 2;
            int offsetXZx = 0;
            int offsetYx = 0;
            if (structNx == 2) {
               offsetXZx = 8;
               offsetYx = -2;
            }

            if (structNx == 3) {
               offsetXZx = 10;
               offsetYx = -1;
            }

            if (structNx == 4) {
               offsetXZx = 11;
               offsetYx = -1;
            }

            if (structNx == 5) {
               offsetXZx = 4;
               offsetYx = 0;
            }

            placeStruct(this.world, fposxx, this.rand, ":sea_fossil_" + structNx, offsetXZx, offsetYx, this.rand.nextInt(4));
         }
      }

      generateSunkenChest(this.world, new BlockPos(i, 0, j), this.rand, false);
      if (biome == BiomesRegister.CORALS) {
         generateSunkenChest(this.world, new BlockPos(i, 0, j), this.rand, true);
      }

      biome.decorate(this.world, this.rand, new BlockPos(i, 0, j));
      if (biome != BiomesRegister.AQUATICABEACH && biome != BiomesRegister.ISLANDS) {
         GenerationHelper.generateSpeleothemsAtChunk(this.world, new BlockPos(i, 0, j), BlocksRegister.SHELLROCKSPELEOTHEM, this.rand, -1, 0.4F, 0.65F, 0.2F);
      }

      if (TerrainGen.populate(this, this.world, this.rand, x, z, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.ANIMALS)) {
         WorldEntitySpawner.performWorldGenSpawning(this.world, biome, i + 8, j + 8, 16, 16, this.rand);
      }

      blockpos = blockpos.add(8, 0, 8);
      if (TerrainGen.populate(this, this.world, this.rand, x, z, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.ICE)) {
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

               boolean isIslandBiome = biome == BiomesRegister.AQUATICABEACH
                  || biome == BiomesRegister.ISLAND_RIVER
                  || biome == BiomesRegister.ISLANDS
                  || biome == BiomesRegister.ISLANDHILLS;
               int bheight = this.world.getTopSolidOrLiquidBlock(blockpos2).getY() - (isIslandBiome ? 8 : 2);
               int xx = blockpos.getX() + k2;
               int zz = blockpos.getZ() + j3;
               int prevBlocksSolid = 0;
               boolean isGenericColumn = k2 == 0 && j3 == 0;
               boolean isCoralsColumn = this.rand.nextFloat() < 0.67F;
               double noiseCoralsMult = isIslandBiome ? 1.0 : 2.0;

               for (int rr = 7; rr < bheight; rr++) {
                  BlockPos finalpos = new BlockPos(xx, rr, zz);
                  IBlockState fstate = this.world.getBlockState(finalpos);
                  int wetfinalpos = 0;
                  if (fstate.isOpaqueCube()) {
                     if (fstate.getBlock() != Blocks.SAND && fstate.getBlock() != Blocks.GRAVEL) {
                        prevBlocksSolid++;
                     }
                  } else {
                     if (prevBlocksSolid >= 2) {
                        this.world.setBlockState(finalpos.down(), SAND, 2);
                        if ((!isIslandBiome || this.rand.nextFloat() < 0.04) && this.terrainControlNoise.getValue(xx / 7.1, zz / 7.1) > 0.5) {
                           byte var90;
                           if (isInWater(this.world, finalpos)) {
                              var90 = 1;
                           } else {
                              var90 = 2;
                           }

                           if (this.rand.nextFloat() < 0.35
                              && (biome == BiomesRegister.SEAWEED_BAY || this.caveNoise1.getValue(xx / 5.1, zz / 5.1) > 0.65)
                              && var90 == 1) {
                              if (this.rand.nextFloat() < 0.75) {
                                 this.world.setBlockState(finalpos, BlocksRegister.SEAGRASS.getDefaultState(), 2);
                              } else {
                                 genSeaweed(this.world, xx, rr, zz, 6 + this.rand.nextInt(4), BlocksRegister.SEAWEEDBLOCK.getDefaultState());
                              }
                           }
                        } else if (isIslandBiome && this.rand.nextFloat() < 0.17 && finalpos.getY() != sealvl) {
                           if (this.caveNoise2.getValue(xx / 7.1, zz / 7.1) > 0.5) {
                              if (wetfinalpos == 0) {
                                 if (isInWater(this.world, finalpos)) {
                                    wetfinalpos = 1;
                                 } else {
                                    wetfinalpos = 2;
                                 }
                              }

                              this.world
                                 .setBlockState(
                                    finalpos,
                                    BlocksRegister.GIANTSHELL
                                       .getDefaultState()
                                       .withProperty(GiantShell.TYPE, this.rand.nextInt(8))
                                       .withProperty(GiantShell.WET, wetfinalpos == 1),
                                    2
                                 );
                           }
                        } else if (wetfinalpos == 2 && this.rand.nextFloat() < 0.2 && finalpos.getY() != sealvl) {
                           this.world.setBlockState(finalpos, BlocksRegister.BONESPILE.getDefaultState(), 2);
                        }
                     }

                     prevBlocksSolid = 0;
                  }

                  if (isCoralsColumn
                     && (this.noisegenerator3dbiomes.getValue(xx / 25.1, rr / 25.1, zz / 25.1) + 2.0) * noiseCoralsMult
                        > (this.rand.nextFloat() + this.rand.nextFloat()) * 8.0F
                     && fstate.getMaterial() == Material.WATER) {
                     EnumFacing fac = this.getRandomFacing(this.world, this.rand, finalpos);
                     if (fac != null) {
                        if (this.rand.nextFloat() < 0.75) {
                           this.world
                              .setBlockState(
                                 finalpos,
                                 coralsMini[this.rand.nextInt(7)].getDefaultState().withProperty(MiniCoral.FACING, fac).withProperty(MiniCoral.WET, true),
                                 2
                              );
                        } else if (this.rand.nextFloat() < 0.6) {
                           this.world
                              .setBlockState(
                                 finalpos,
                                 coralsBig[this.rand.nextInt(5)].getDefaultState().withProperty(MiniCoral.FACING, fac).withProperty(MiniCoral.WET, true),
                                 2
                              );
                        } else {
                           this.world
                              .setBlockState(
                                 finalpos,
                                 coralsGlow[this.rand.nextInt(5)].getDefaultState().withProperty(MiniCoral.FACING, fac).withProperty(MiniCoral.WET, true),
                                 2
                              );
                        }
                     }
                  }

                  if (isGenericColumn && this.rand.nextInt(3) == bheight % 3 && fstate.isOpaqueCube()) {
                     for (EnumFacing f : EnumFacing.VALUES) {
                        BlockPos offpos = finalpos.offset(f);
                        IBlockState offstate = this.world.getBlockState(offpos);
                        if (offstate.getMaterial() == Material.WATER) {
                           this.coralOreGenerator.size = 8 + this.rand.nextInt(16);
                           this.coralOreGenerator.generate(this.world, this.rand, offpos);
                        }
                     }
                  }
               }
            }
         }
      }

      for (int m = 0; m < 280; m++) {
         BlockPos shellpos = new BlockPos(i + 8 + this.rand.nextInt(16), 30 + this.rand.nextInt(sealvl - 30 + 5), j + 8 + this.rand.nextInt(16));
         placeRandomSeashellOnBlock(this.world, shellpos, this.rand, null);
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

   public static int getNBcount2Dquad(World world, BlockPos pos) {
      int count = 8;
      if (world.isAirBlock(pos.add(1, 0, 0))) {
         count--;
      }

      if (world.isAirBlock(pos.add(1, 0, 1))) {
         count--;
      }

      if (world.isAirBlock(pos.add(0, 0, 1))) {
         count--;
      }

      if (world.isAirBlock(pos.add(-1, 0, 0))) {
         count--;
      }

      if (world.isAirBlock(pos.add(-1, 0, -1))) {
         count--;
      }

      if (world.isAirBlock(pos.add(0, 0, -1))) {
         count--;
      }

      if (world.isAirBlock(pos.add(1, 0, -1))) {
         count--;
      }

      if (world.isAirBlock(pos.add(-1, 0, 1))) {
         count--;
      }

      return count;
   }

   public static void cellularAutom1(World world, int x, int y, int z) {
      int range = 80;
      boolean[][] matrix = new boolean[range][range];

      for (int ix = 0; ix < range; ix++) {
         for (int iz = 0; iz < range; iz++) {
            BlockPos pos = new BlockPos(x + ix, y, z + iz);
            if (world.isAirBlock(pos)) {
               if (getNBcount2Dquad(world, pos) >= 5.0F + Debugger.floats[0]) {
                  matrix[ix][iz] = true;
               } else {
                  matrix[ix][iz] = false;
               }
            } else if (getNBcount2Dquad(world, pos) <= 3.0F + Debugger.floats[1]) {
               matrix[ix][iz] = false;
            } else {
               matrix[ix][iz] = true;
            }
         }
      }

      for (int ix = 0; ix < range; ix++) {
         for (int izx = 0; izx < range; izx++) {
            BlockPos pos = new BlockPos(x + ix, y + 1, z + izx);
            if (matrix[ix][izx]) {
               world.setBlockState(pos, CA_STATE);
            } else {
               world.setBlockToAir(pos);
            }
         }
      }
   }

   public static void fillNoise(World world, int x, int y, int z, Random rand) {
      int range = 80;

      for (int ix = 0; ix < range; ix++) {
         for (int iz = 0; iz < range; iz++) {
            BlockPos pos = new BlockPos(x + ix, y, z + iz);
            if (rand.nextFloat() < 0.45) {
               world.setBlockState(pos, CA_STATE);
            } else {
               world.setBlockToAir(pos);
            }
         }
      }
   }

   public static void setStick(World world, int x, int y, int z, Random rand, int stickType) {
      IBlockState state = Blocks.SANDSTONE.getDefaultState();
      if (stickType == 0) {
         world.setBlockState(new BlockPos(x, y, z), state);
      }

      if (stickType == 1) {
         world.setBlockState(new BlockPos(x, y, z), state);
         world.setBlockState(new BlockPos(x + rand.nextInt(3) - 1, y, z + rand.nextInt(3) - 1), state);
      }

      if (stickType == 2) {
         world.setBlockState(new BlockPos(x, y, z), state);
         world.setBlockState(new BlockPos(x + rand.nextInt(3) - 1, y, z), state);
         world.setBlockState(new BlockPos(x, y, z + rand.nextInt(3) - 1), state);
      }

      if (stickType == 3) {
         int xr = rand.nextInt(3) - 1;
         int zr = rand.nextInt(3) - 1;
         world.setBlockState(new BlockPos(x, y, z), state);
         world.setBlockState(new BlockPos(x + xr, y, z), state);
         world.setBlockState(new BlockPos(x, y, z + zr), state);
         world.setBlockState(new BlockPos(x + xr, y, z + zr), state);
      }

      if (stickType == 4) {
         int xr = rand.nextInt(3) - 1;
         int zr = rand.nextInt(3) - 1;
         world.setBlockState(new BlockPos(x, y, z), state);
         world.setBlockState(new BlockPos(x + xr, y, z), state);
         world.setBlockState(new BlockPos(x, y, z + zr), state);
         world.setBlockState(new BlockPos(x + xr, y, z + zr), state);
         world.setBlockState(new BlockPos(x + rand.nextInt(3) - 1, y, z + rand.nextInt(3) - 1), state);
      }

      if (stickType == 5) {
         int xr = rand.nextInt(3) - 1;
         int zr = rand.nextInt(3) - 1;
         world.setBlockState(new BlockPos(x, y, z), state);
         world.setBlockState(new BlockPos(x + xr, y, z), state);
         world.setBlockState(new BlockPos(x, y, z + zr), state);
         world.setBlockState(new BlockPos(x + xr, y, z + zr), state);
         world.setBlockState(new BlockPos(x + rand.nextInt(3) - 1, y, z + rand.nextInt(3) - 1), state);
         world.setBlockState(new BlockPos(x + rand.nextInt(3) - 1, y, z + rand.nextInt(3) - 1), state);
      }
   }

   public static int[][] getStickPosition(World world, int x, int z, Random rand, int stickType) {
      if (stickType == 0) {
         return new int[][]{{x, z}};
      } else if (stickType == 1) {
         return new int[][]{{x, z}, {x + rand.nextInt(3) - 1, z + rand.nextInt(3) - 1}};
      } else if (stickType == 2) {
         return new int[][]{{x, z}, {x + rand.nextInt(3) - 1, z}, {x, z + rand.nextInt(3) - 1}};
      } else if (stickType == 3) {
         int xr = rand.nextInt(3) - 1;
         int zr = rand.nextInt(3) - 1;
         return new int[][]{{x, z}, {x + xr, z}, {x, z + zr}, {x + xr, z + zr}};
      } else if (stickType == 4) {
         int xr = rand.nextInt(3) - 1;
         int zr = rand.nextInt(3) - 1;
         return new int[][]{{x, z}, {x + xr, z}, {x, z + zr}, {x + xr, z + zr}, {x + rand.nextInt(3) - 1, z + rand.nextInt(3) - 1}};
      } else if (stickType == 5) {
         int xr = rand.nextInt(3) - 1;
         int zr = rand.nextInt(3) - 1;
         return new int[][]{
            {x, z},
            {x + xr, z},
            {x, z + zr},
            {x + xr, z + zr},
            {x + rand.nextInt(3) - 1, z + rand.nextInt(3) - 1},
            {x + rand.nextInt(3) - 1, z + rand.nextInt(3) - 1}
         };
      } else {
         return new int[][]{{x, z}};
      }
   }

   public static void genFlatCoral(
      World world, int x, int y, int z, Random rand, int range, int stickSize, int stickSizeVar, int displace, float noiseFilling, int smooth, int maxLength
   ) {
      IBlockState state = BlocksRegister.SHELLROCK.getDefaultState();
      int displaceX2 = displace * 2 + 1;

      for (int ita = 0; ita < maxLength; ita++) {
         int currentstickSize = 3 + Math.round(rand.nextFloat() * stickSizeVar);
         int sticktype = rand.nextInt(4);
         int sticktype2 = rand.nextInt(4) + 2;
         int[][] poses = getStickPosition(world, x, z, rand, sticktype);
         int[][] poses2 = getStickPosition(world, x, z, rand, sticktype2);

         for (int iy = y; iy < currentstickSize + y; iy++) {
            if (iy != y && iy != currentstickSize + y - 1) {
               for (int[] posss : poses) {
                  world.setBlockState(new BlockPos(posss[0], iy, posss[1]), state, 2);
               }
            } else {
               for (int[] posss : poses2) {
                  world.setBlockState(new BlockPos(posss[0], iy, posss[1]), state, 2);
               }
            }
         }

         y += currentstickSize;
         boolean[][] matrix = new boolean[range][range];

         for (int ix = 0; ix < range; ix++) {
            for (int iz = 0; iz < range; iz++) {
               matrix[ix][iz] = rand.nextFloat() < noiseFilling;
            }
         }

         for (int it = 0; it < smooth; it++) {
            boolean[][] matrix2 = new boolean[range][range];

            for (int ix = 0; ix < range; ix++) {
               for (int iz = 0; iz < range; iz++) {
                  if (!matrix[ix][iz]) {
                     if (getNBcount2Dquad(matrix, ix, iz, range) >= 5) {
                        matrix2[ix][iz] = true;
                     } else {
                        matrix2[ix][iz] = false;
                     }
                  } else if (getNBcount2Dquad(matrix, ix, iz, range) <= 3) {
                     matrix2[ix][iz] = false;
                  } else {
                     matrix2[ix][iz] = true;
                  }
               }
            }

            matrix = matrix2;
         }

         int halfrange = range / 2;

         for (int ix = 0; ix < range; ix++) {
            for (int izx = 0; izx < range; izx++) {
               if (matrix[ix][izx]) {
                  world.setBlockState(new BlockPos(x + ix - halfrange, y, z + izx - halfrange), state, 2);
               }
            }
         }

         int displacerX = rand.nextInt(displaceX2) - displace;
         int displacerZ = rand.nextInt(displaceX2) - displace;
         int displacedX = x + displacerX;
         int displacedZ = z + displacerZ;
         if (world.getBlockState(new BlockPos(displacedX, y, displacedZ)).getBlock() != state.getBlock()) {
            displacedX = x - displacerX;
            displacedZ = z - displacerZ;
            if (world.getBlockState(new BlockPos(displacedX, y, displacedZ)).getBlock() != state.getBlock()) {
               displacedX = x + displacerZ;
               displacedZ = z + displacerX;
               if (world.getBlockState(new BlockPos(displacedX, y, displacedZ)).getBlock() != state.getBlock()) {
                  displacedX = x - displacerZ;
                  displacedZ = z - displacerX;
                  if (world.getBlockState(new BlockPos(displacedX, y, displacedZ)).getBlock() != state.getBlock()) {
                     return;
                  }
               }
            }
         }

         x = displacedX;
         z = displacedZ;
         y++;
      }
   }

   public static int getNBcount2Dquad(boolean[][] matrix, int x, int z, int matrMaxSize) {
      int count = 0;
      if (x + 1 < matrMaxSize && matrix[x + 1][z]) {
         count++;
      }

      if (x + 1 < matrMaxSize && z + 1 < matrMaxSize && matrix[x + 1][z + 1]) {
         count++;
      }

      if (z + 1 < matrMaxSize && matrix[x][z + 1]) {
         count++;
      }

      if (x - 1 >= 0 && matrix[x - 1][z]) {
         count++;
      }

      if (x - 1 >= 0 && z - 1 >= 0 && matrix[x - 1][z - 1]) {
         count++;
      }

      if (z - 1 >= 0 && matrix[x][z - 1]) {
         count++;
      }

      if (x + 1 < matrMaxSize && z - 1 >= 0 && matrix[x + 1][z - 1]) {
         count++;
      }

      if (z + 1 < matrMaxSize && x - 1 >= 0 && matrix[x - 1][z + 1]) {
         count++;
      }

      return count;
   }

   public static void genCellularCoral(World world, int x, int y, int z, Random rand, int range, int height, IBlockState coralBlock) {
      int halfrange = range / 2;
      int heightToReverse = height - height / (3 + rand.nextInt(3));
      int reverseVar = 3 + rand.nextInt(3);
      boolean[][][] matrix = new boolean[height][range][range];
      boolean[] rules = ccallrules[rand.nextInt(10)];

      for (int ix = 0; ix < range; ix++) {
         for (int iz = 0; iz < range; iz++) {
            matrix[0][ix][iz] = true;
         }
      }

      matrix[0][halfrange][halfrange] = false;

      for (int it = 1; it < height; it++) {
         for (int ix = 0; ix < range; ix++) {
            for (int iz = 0; iz < range; iz++) {
               if (it >= heightToReverse) {
                  if (matrix[it - 1][ix][iz] && getNBcount2Dquad(matrix[it - 1], ix, iz, range) < reverseVar) {
                     matrix[it][ix][iz] = false;
                  } else {
                     matrix[it][ix][iz] = matrix[it - 1][ix][iz];
                  }
               } else if (matrix[it - 1][ix][iz]) {
                  matrix[it][ix][iz] = rules[getNBcount2Dquad(matrix[it - 1], ix, iz, range)];
               } else {
                  matrix[it][ix][iz] = rules[getNBcount2Dquad(matrix[it - 1], ix, iz, range) + 9];
               }
            }
         }
      }

      for (int ix = 1; ix < range - 1; ix++) {
         for (int izx = 1; izx < range - 1; izx++) {
            BlockPos posxz = new BlockPos(x + ix - halfrange, y, z + izx - halfrange);

            for (int it = 1; it < height; it++) {
               if (matrix[it][ix][izx]) {
                  world.setBlockState(posxz.add(0, it, 0), coralBlock, 2);
               }
            }
         }
      }
   }

   public static void genRecursiveCoral(World world, int x, int y, int z, Random rand, int maxCount, int branchLength, IBlockState block) {
      int[] storage = new int[]{maxCount};
      genRecursiveCoral(world, x, y, z, rand, storage, branchLength, block, block, 255);
   }

   public static void genRecursiveCoral(
      World world, int x, int y, int z, Random rand, int[] maxCountStorage, int branchLength, IBlockState block, IBlockState blockTop, int yMax
   ) {
      world.setBlockState(new BlockPos(x, y, z), maxCountStorage[0] > 1 && branchLength > 0 ? block : blockTop, 2);
      maxCountStorage[0]--;
      if (maxCountStorage[0] > 0 && branchLength > 0 && y < yMax) {
         if (rand.nextFloat() < 0.4 && branchLength > 1) {
            int offX = rand.nextInt(3) - 1;
            int offZ = rand.nextInt(3) - 1;
            genRecursiveCoral(world, x + offX, y + 1, z + offZ, rand, maxCountStorage, branchLength - (rand.nextFloat() < 0.5 ? 1 : 2), block, blockTop, yMax);
            genRecursiveCoral(world, x - offX, y + 1, z - offZ, rand, maxCountStorage, branchLength - (rand.nextFloat() < 0.5 ? 1 : 2), block, blockTop, yMax);
         } else {
            genRecursiveCoral(world, x, y + 1, z, rand, maxCountStorage, branchLength - 1, block, blockTop, yMax);
         }
      }
   }

   public static void genRecursiveCoral(
      World world, int x, int y, int z, Random rand, int maxCount, int branchLength, IBlockState block, IBlockState blockTop, int yMax
   ) {
      int[] storage = new int[]{maxCount};
      genRecursiveCoral(world, x, y, z, rand, storage, branchLength, block, blockTop, yMax);
   }

   public static void genSeaweed(World world, int x, int y, int z, int maxCount, IBlockState block) {
      if (world.getBlockState(new BlockPos(x, y - 1, z)).getMaterial() == Material.SAND) {
         Material mat = world.getBlockState(new BlockPos(x, y - 2, z)).getMaterial();
         if (mat == Material.SAND) {
            Material mat2 = world.getBlockState(new BlockPos(x, y - 3, z)).getMaterial();
            if (mat2 == Material.SAND || mat2 == Material.AIR || mat2 == Material.WATER) {
               return;
            }
         } else if (mat == Material.AIR || mat == Material.WATER) {
            return;
         }
      }

      int max = Math.min(maxCount + y, 193);

      for (int i = y; i < max; i++) {
         BlockPos ps = new BlockPos(x, i, z);
         if (world.getBlockState(ps).getMaterial() != Material.WATER) {
            return;
         }

         world.setBlockState(ps, block, 2);
      }
   }

   public static boolean isCoral(IBlockState state) {
      Block block = state.getBlock();
      return block == BlocksRegister.CORALORANGE
         || block == BlocksRegister.CORALRED
         || block == BlocksRegister.CORALPINK
         || block == BlocksRegister.CORALWHITE
         || block == BlocksRegister.CORALYELLOW;
   }

   public static void generateBiomeTerrain(Biome biome, World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
      int i = worldIn.getSeaLevel();
      IBlockState iblockstate = biome.topBlock;
      IBlockState iblockstate1 = biome.fillerBlock;
      int j = -1;
      int k = (int)(noiseVal / 3.0 + 3.0 + rand.nextDouble() * 0.25);
      int l = x & 15;
      int i1 = z & 15;
      MutableBlockPos blockpos$mutableblockpos = new MutableBlockPos();

      for (int j1 = 255; j1 >= 0; j1--) {
         if (j1 <= rand.nextInt(5)) {
            chunkPrimerIn.setBlockState(i1, j1, l, BEDROCK);
         } else if (j1 - 9 <= rand.nextInt(6)) {
            chunkPrimerIn.setBlockState(i1, j1, l, Blocks.STONE.getDefaultState());
         } else if (j1 - 25 <= rand.nextInt(7)) {
            chunkPrimerIn.setBlockState(i1, j1, l, STONE2);
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
                     iblockstate = biome.topBlock;
                     iblockstate1 = biome.fillerBlock;
                  }

                  if (j1 < i && (iblockstate == null || iblockstate.getMaterial() == Material.AIR)) {
                     if (biome.getTemperature(blockpos$mutableblockpos.setPos(x, j1, z)) < 0.15F) {
                        iblockstate = ICE;
                     } else {
                        iblockstate = WATER;
                     }
                  }

                  j = k;
                  if (j1 >= i - 1) {
                     chunkPrimerIn.setBlockState(i1, j1, l, iblockstate);
                  } else if (j1 < i - 7 - k) {
                     iblockstate = AIR;
                     iblockstate1 = STONE;
                     chunkPrimerIn.setBlockState(i1, j1, l, GRAVEL);
                  } else {
                     chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
                  }
               } else if (j > 0) {
                  j--;
                  chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
                  if (j == 0 && iblockstate1.getBlock() == Blocks.SAND && k > 1) {
                     j = rand.nextInt(4) + Math.max(0, j1 - 194);
                     iblockstate1 = iblockstate1.getValue(BlockSand.VARIANT) == EnumType.RED_SAND ? RED_SANDSTONE : SANDSTONE;
                  }
               }
            }
         }
      }
   }

   public void generateAdvBiomeTerrain(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal, Biome biome) {
      int i = worldIn.getSeaLevel();
      IBlockState iblockstate = biome.topBlock;
      IBlockState iblockstate1 = biome.fillerBlock;
      IBlockState mainstate = biome.topBlock;
      IBlockState mainstate1 = biome.fillerBlock;
      double tgc = this.terrainControlNoise.getValue(x / 100.0, z / 100.0);
      IBlockState[] states = ((BiomeControlled)biome).controlSurface(mainstate, mainstate1, noiseVal);
      if (states != null) {
         mainstate = states[0];
         mainstate1 = states[1];
         iblockstate = states[0];
         iblockstate1 = states[1];
      }

      int j = -1;
      int k = (int)(noiseVal / 3.0 + 3.0 + rand.nextDouble() * 0.25);
      int l = x & 15;
      int i1 = z & 15;
      boolean hasNoAir = false;
      new MutableBlockPos();

      for (int j1 = 255; j1 >= 0; j1--) {
         if (j1 <= rand.nextInt(5)) {
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
                     chunkPrimerIn.setBlockState(i1, j1, l, SAND);
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

   public static void generateJellyfishCave(
      World world,
      BlockPos pos,
      Random rand,
      int radiusMin,
      int radiusMax,
      int lenght,
      Vec3d caveDirection,
      WorldGenerator coralGenerator,
      WorldGenerator minicoralGenerator,
      IBlockState wallsCoral
   ) {
      List<BlockPos> listCoralls = new ArrayList<>();
      List<BlockPos> listCenters = new ArrayList<>();

      for (int i = 0; i < lenght; i++) {
         int radius = radiusMin + rand.nextInt(radiusMax - radiusMin + 1);
         int radiusSq = radius * radius;
         int radiusSq2 = radiusSq + radius + radius;

         for (int xr = -radius; xr <= radius; xr++) {
            for (int yr = -radius; yr <= radius; yr++) {
               for (int zr = -radius; zr <= radius; zr++) {
                  int distSq = xr * xr + yr * yr + zr * zr;
                  if (distSq < radiusSq) {
                     world.setBlockState(new BlockPos(xr + pos.getX(), yr + pos.getY(), zr + pos.getZ()), WATER, 2);
                  } else if (distSq == radiusSq) {
                     BlockPos fpos = new BlockPos(xr + pos.getX(), yr + pos.getY(), zr + pos.getZ());
                     if (world.isAirBlock(fpos)) {
                        world.setBlockState(fpos, rand.nextFloat() < 0.6F ? wallsCoral : STONE, 2);
                     } else {
                        listCoralls.add(fpos);
                     }
                  } else if (distSq < radiusSq2) {
                     BlockPos fpos = new BlockPos(xr + pos.getX(), yr + pos.getY(), zr + pos.getZ());
                     IBlockState wallstate = world.getBlockState(fpos);
                     if (wallstate.getMaterial() == Material.AIR) {
                        world.setBlockState(fpos, rand.nextFloat() < 0.6F ? wallsCoral : STONE, 2);
                     } else if (rand.nextFloat() < 0.4F && wallstate.getBlock() == STONE.getBlock()) {
                        world.setBlockState(fpos, wallsCoral, 2);
                     }
                  }
               }
            }
         }

         double scaleFactor = radius / 2.0;
         caveDirection = caveDirection.add(rand.nextInt(4) - rand.nextInt(4), rand.nextInt(3) - rand.nextInt(4), rand.nextInt(4) - rand.nextInt(4))
            .normalize()
            .scale(scaleFactor);
         pos = pos.add(caveDirection.x, caveDirection.y, caveDirection.z);
         if (pos.getY() < 9 + radius) {
            pos = new BlockPos(
               pos.getX() + caveDirection.x * rand.nextFloat(),
               8 + radius + rand.nextInt(3),
               pos.getZ() + caveDirection.z * rand.nextFloat()
            );
         }

         if (i > 3 && rand.nextFloat() < 0.4F) {
            listCenters.add(pos);
         }
      }

      for (BlockPos coralpos : listCoralls) {
         IBlockState wallstate = world.getBlockState(coralpos);
         if (!wallstate.getMaterial().isLiquid()) {
            if (rand.nextFloat() < 0.3F) {
               coralGenerator.generate(world, rand, coralpos);
            } else if (rand.nextFloat() < 0.7F) {
               minicoralGenerator.generate(world, rand, coralpos);
            }
         }
      }

      for (int i = 0; i < listCenters.size(); i++) {
         BlockPos centerpos = listCenters.get(i);
         if (i == listCenters.size() - 1) {
            GenerationHelper.setChestWithLoot(
               world, centerpos.down(4), EnumChest.CORAL, ListLootTable.CHESTS_JELLYFISH_CAVE_FINAL, EnumFacing.HORIZONTALS[rand.nextInt(4)]
            );

            for (int j = 5; j < 24; j++) {
               BlockPos brickPos = centerpos.down(j);
               if (GetMOP.SOLID_BLOCKS.apply(world.getBlockState(brickPos))) {
                  break;
               }

               world.setBlockState(brickPos, BlocksRegister.CORALBRICKS.getDefaultState(), 2);
            }
         } else if (rand.nextFloat() < 0.25F) {
            GenerationHelper.setChestWithLoot(
               world, centerpos.down(5), EnumChest.SUNKEN, ListLootTable.CHESTS_JELLYFISH_CAVE, EnumFacing.HORIZONTALS[rand.nextInt(4)]
            );

            for (int j = 6; j < 24; j++) {
               BlockPos brickPos = centerpos.down(j);
               if (GetMOP.SOLID_BLOCKS.apply(world.getBlockState(brickPos))) {
                  break;
               }

               world.setBlockState(brickPos, BlocksRegister.CORALBRICKS.getDefaultState(), 2);
            }
         } else {
            world.setBlockState(centerpos, BlocksRegister.MOBSPAWNERAQUATIC.getDefaultState(), 2);
            SpawnerTuners.JELLYFISHCAVE.setupSpawner(world, centerpos, rand);

            for (int j = 1; j < 26; j++) {
               BlockPos weedPos = centerpos.up(j);
               if (GetMOP.SOLID_BLOCKS.apply(world.getBlockState(weedPos))) {
                  break;
               }

               world.setBlockState(weedPos, BlocksRegister.SEAWEEDBLOCK.getDefaultState(), 2);
            }
         }
      }
   }

   public static void generateBigSponge(World world, BlockPos pos, Random rand, int radius, IBlockState sponge) {
      int radiusSq = radius * radius;
      int radiusLow = radius / 2 + rand.nextInt(2);
      int radiusLowSq = radiusLow * radiusLow;
      BlockPos posLow = pos.add(0.0, radiusLow + radiusLow * rand.nextFloat(), 0.0);

      for (int xr = -radius; xr <= radius; xr++) {
         for (int yr = -radius; yr <= radius; yr++) {
            for (int zr = -radius; zr <= radius; zr++) {
               if (xr * xr + yr * yr + zr * zr <= radiusSq) {
                  int xB = xr + pos.getX();
                  int yB = yr + pos.getY();
                  int zB = zr + pos.getZ();
                  if (posLow.distanceSq(xB, yB, zB) > radiusLowSq) {
                     BlockPos fpos = new BlockPos(xB, yB, zB);
                     if (world.getBlockState(fpos).getMaterial() == Material.WATER) {
                        world.setBlockState(fpos, sponge, 2);
                     }
                  }
               }
            }
         }
      }
   }

   public static Block randomMinicoral(float glowingModifier, Random rand) {
      if (rand.nextFloat() < 0.18 * glowingModifier) {
         return coralsGlow[rand.nextInt(5)];
      } else if (rand.nextFloat() < 0.15) {
         return coralsFavia[rand.nextInt(5)];
      } else if (rand.nextFloat() < 0.1) {
         return corallimorphas[rand.nextInt(7)];
      } else {
         return rand.nextFloat() < 0.27 ? coralsBig[rand.nextInt(5)] : coralsMini[rand.nextInt(7)];
      }
   }

   public static void placeStruct(World worldIn, BlockPos pos, Random rand, String structure, int displace, int Yoffset, int rotation) {
      WorldServer worldServer = (WorldServer)worldIn;
      MinecraftServer minecraftServer = worldIn.getMinecraftServer();
      TemplateManager templateManager = worldServer.getStructureTemplateManager();
      Template template = templateManager.get(minecraftServer, new ResourceLocation("arpg" + structure));
      PlacementSettings settings = new PlacementSettings();
      int sx = -1;
      int sz = -1;
      if (rotation == 2) {
         settings.setRotation(Rotation.CLOCKWISE_180);
         sx = 1;
         sz = 1;
      }

      if (rotation == 1) {
         settings.setRotation(Rotation.CLOCKWISE_90);
         sx = 1;
         sz = -1;
      }

      if (rotation == 3) {
         settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
         sx = -1;
         sz = 1;
      }

      if (rotation == 0) {
         settings.setRotation(Rotation.NONE);
      }

      template.addBlocksToWorld(worldIn, pos.add(sx * displace, Yoffset, sz * displace), null, settings, 2);
   }

   @Nullable
   public EnumFacing getRandomFacing(World worldIn, Random rand, BlockPos position) {
      int f = rand.nextInt(6);

      for (int i = 0; i < 6; i++) {
         f = GetMOP.next(f, 1, 6);
         EnumFacing facing = EnumFacing.byIndex(f);
         if (worldIn.getBlockState(position.offset(facing)).isOpaqueCube()) {
            return facing.getOpposite();
         }
      }

      return null;
   }

   public static boolean isInWater(World worldIn, BlockPos pos) {
      IBlockState state1 = worldIn.getBlockState(pos.up());
      if (state1.getMaterial() == Material.WATER || state1.isOpaqueCube()) {
         IBlockState state2 = worldIn.getBlockState(pos.east());
         if (state2.getMaterial() == Material.WATER || state2.isOpaqueCube()) {
            IBlockState state3 = worldIn.getBlockState(pos.south());
            if (state3.getMaterial() == Material.WATER || state3.isOpaqueCube()) {
               IBlockState state4 = worldIn.getBlockState(pos.west());
               if (state4.getMaterial() == Material.WATER || state4.isOpaqueCube()) {
                  IBlockState state5 = worldIn.getBlockState(pos.north());
                  if (state5.getMaterial() == Material.WATER || state5.isOpaqueCube()) {
                     IBlockState state6 = worldIn.getBlockState(pos.down());
                     if (state6.getMaterial() == Material.WATER || state6.isOpaqueCube()) {
                        return true;
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   public static void generateSunkenChest(World world, BlockPos pos, Random rand, boolean inStromatoliteBiome) {
      BlockPos chestSet = null;
      Biome biome = world.getBiome(pos);
      if (rand.nextFloat() < (biome == BiomesRegister.SEA_BOTTOM ? 0.05 : 0.2)
         && biome != BiomesRegister.SHALLOW
         && biome != BiomesRegister.AQUATICABEACH
         && biome != BiomesRegister.ISLANDS
         && biome != BiomesRegister.ISLANDHILLS) {
         if (inStromatoliteBiome) {
            BlockPos start = pos.add(rand.nextInt(16) + 8, 80 + rand.nextInt(35), rand.nextInt(16) + 8);
            if (world.getBlockState(start).getMaterial() == Material.WATER) {
               GetMOP.BlockTraceResult result = GetMOP.blockTrace(world, start, EnumFacing.HORIZONTALS[rand.nextInt(4)], 8, GetMOP.SOLID_BLOCKS);
               if (result != null) {
                  world.setBlockState(result.pos.down(), BlocksRegister.STROMATOLITE.getDefaultState(), 2);
                  world.setBlockToAir(result.pos.up());
                  chestSet = result.pos;
               }
            }
         } else {
            GetMOP.BlockTraceResult result = GetMOP.blockTrace(
               world, pos.add(rand.nextInt(16) + 8, sealvl, rand.nextInt(16) + 8), EnumFacing.DOWN, sealvl - 32, GetMOP.SOLID_BLOCKS
            );
            if (result != null) {
               chestSet = result.prevPos;
            }
         }
      }

      if (chestSet != null) {
         if (rand.nextFloat() < 0.25F) {
            ArrayList<BlockPos> poses = new ArrayList<>();
            int cx = chestSet.getX() >> 4;
            int cz = chestSet.getZ() >> 4;

            for (int x = -64; x <= 64; x++) {
               for (int z = -64; z <= 64; z++) {
                  if (hasJellyfishCaveAtChunk(cx + x, cz + z, world)) {
                     BlockPos blockpos = new BlockPos((cx + x) * 16 + 8, 0, (cz + z) * 16 + 8);
                     poses.add(blockpos);
                  }
               }
            }

            if (!poses.isEmpty()) {
               GenerationHelper.setChestWithMap(
                  world, chestSet, EnumChest.SUNKEN, poses.get(rand.nextInt(poses.size())), EnumFacing.HORIZONTALS[rand.nextInt(4)]
               );
            }
         } else {
            GenerationHelper.setChestWithLoot(world, chestSet, EnumChest.SUNKEN, ListLootTable.CHESTS_SUNKEN, EnumFacing.HORIZONTALS[rand.nextInt(4)]);
         }
      }
   }

   public static boolean hasJellyfishCaveAtChunk(int x, int z, World world) {
      int i = x * 16;
      int j = z * 16;
      BlockPos blockpos = new BlockPos(i, 0, j);
      Biome biome = world.getBiome(blockpos);
      if (biome != BiomesRegister.AQUATICABEACH
         && biome != BiomesRegister.ISLANDS
         && biome != BiomesRegister.ISLANDHILLS
         && biome != BiomesRegister.ISLAND_RIVER) {
         float dungeonValue = GenerationHelper.getDungeonValue(x, z, world.getSeed(), 9);
         if (dungeonValue >= 0.0F && dungeonValue < 0.1F) {
            return true;
         }
      }

      return false;
   }

   public static boolean hasSunkenTownAtChunk(int x, int z, World world) {
      int i = x * 16;
      int j = z * 16;
      BlockPos blockpos = new BlockPos(i, 0, j);
      Biome biome = world.getBiome(blockpos);
      if (biome != BiomesRegister.AQUATICABEACH
         && biome != BiomesRegister.ISLANDS
         && biome != BiomesRegister.ISLANDHILLS
         && biome != BiomesRegister.ISLAND_RIVER
         && biome != BiomesRegister.SHALLOW) {
         float dungeonValue = GenerationHelper.getDungeonValue(x, z, world.getSeed(), 7);
         if (dungeonValue >= 0.1F && dungeonValue < 0.2F) {
            return true;
         }
      }

      return false;
   }

   public static boolean hasSirenSanctuaryAtChunk(int x, int z, World world) {
      int i = x * 16;
      int j = z * 16;
      BlockPos blockpos = new BlockPos(i, 0, j);
      Biome biome = world.getBiome(blockpos);
      if (biome == BiomesRegister.AQUATICABEACH || biome == BiomesRegister.SHALLOW || biome == BiomesRegister.ISLAND_RIVER || biome == BiomesRegister.ISLANDS) {
         float dungeonValue = GenerationHelper.getDungeonValue(x, z, world.getSeed(), 7);
         if (dungeonValue >= 0.6F && dungeonValue < 0.7F) {
            return true;
         }
      }

      return false;
   }

   public static boolean placeRandomSeashellOnBlock(World world, BlockPos pos, Random rand, @Nullable EnumFacing placeSide) {
      if (!world.isRemote) {
         if (placeSide == null) {
            placeSide = EnumFacing.VALUES[rand.nextInt(6)];
            if (placeSide == EnumFacing.DOWN && rand.nextFloat() < 0.6F) {
               placeSide = EnumFacing.UP;
            }
         }

         BlockPos posoff = pos.offset(placeSide);
         IBlockState state = world.getBlockState(posoff);
         if (world.isSideSolid(pos, placeSide) && (GetMOP.WATER_BLOCKS.apply(state) || GetMOP.AIR_BLOCKS.apply(state))) {
            EntityHangingAllSides entityHanging = new EntityHangingAllSides(world, posoff, placeSide);
            entityHanging.setDisplayedItem(ItemShell.getRandomSeashell(rand));
            entityHanging.setItemRotation(rand.nextInt(8));
            entityHanging.setType(1);
            world.spawnEntity(entityHanging);
            return true;
         }
      }

      return false;
   }
}
