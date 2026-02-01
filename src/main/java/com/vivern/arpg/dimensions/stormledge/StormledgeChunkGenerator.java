package com.vivern.arpg.dimensions.stormledge;

import com.vivern.arpg.dimensions.aquatica.ChunkGenSettingsAqua;
import com.vivern.arpg.dimensions.generationutils.ChunkBuilder;
import com.vivern.arpg.dimensions.generationutils.CustomOreGenerator;
import com.vivern.arpg.dimensions.generationutils.GenerationHelper;
import com.vivern.arpg.dimensions.generationutils.WorldGenCrystalIsland;
import com.vivern.arpg.main.BiomesRegister;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.GetMOP;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextOverworld;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType;

public class StormledgeChunkGenerator implements IChunkGenerator {
   public static final IBlockState STONE = Blocks.STONE.getDefaultState();
   public static final IBlockState GRASS = BlocksRegister.FULMINIFLORA.getDefaultState();
   public static final IBlockState WATER = Blocks.WATER.getDefaultState();
   public int averageIslandY = 128;
   public int largeIslandY = 80;
   private final Random rand;
   private NoiseGeneratorOctaves minLimitPerlinNoise;
   private NoiseGeneratorOctaves maxLimitPerlinNoise;
   private NoiseGeneratorOctaves mainPerlinNoise;
   public NoiseGeneratorPerlin surfaceNoise;
   public NoiseGeneratorPerlin downNoise;
   public NoiseGeneratorPerlin islandsCentersNoise;
   public NoiseGeneratorPerlin mNoise;
   public NoiseGeneratorPerlin sNoise;
   public NoiseGeneratorPerlin decorNoise1;
   public NoiseGeneratorPerlin decorNoise2;
   public NoiseGeneratorOctaves scaleNoise;
   public NoiseGeneratorOctaves depthNoise;
   public NoiseGeneratorOctaves forestNoise;
   private final World world;
   private final boolean mapFeaturesEnabled;
   private final WorldType terrainType;
   private final double[] heightMap;
   private final float[] biomeWeights;
   private ChunkGenSettingsAqua settings;
   private double[] depthBuffer = new double[256];
   private Biome[] biomesForGeneration;
   double[] mainNoiseRegion;
   double[] minLimitRegion;
   double[] maxLimitRegion;
   double[] depthRegion;
   public WorldGenCrystalIsland worldGenCrystalIsland = new WorldGenCrystalIsland();
   public CustomOreGenerator stormsteelOreGen;
   public static HashMap<Double, Double> sqrtMap = new HashMap<>();

   public StormledgeChunkGenerator(World worldIn, long seed) {
      this.world = worldIn;
      this.mapFeaturesEnabled = false;
      this.terrainType = worldIn.getWorldInfo().getTerrainType();
      this.rand = new Random(seed);
      this.minLimitPerlinNoise = new NoiseGeneratorOctaves(this.rand, 16);
      this.maxLimitPerlinNoise = new NoiseGeneratorOctaves(this.rand, 16);
      this.mainPerlinNoise = new NoiseGeneratorOctaves(this.rand, 8);
      this.surfaceNoise = new NoiseGeneratorPerlin(this.rand, 4);
      this.downNoise = new NoiseGeneratorPerlin(new Random(seed + 1L), 4);
      this.islandsCentersNoise = new NoiseGeneratorPerlin(new Random(seed + 2L), 4);
      this.mNoise = new NoiseGeneratorPerlin(new Random(seed + 3L), 4);
      this.sNoise = new NoiseGeneratorPerlin(new Random(seed + 4L), 4);
      this.decorNoise1 = new NoiseGeneratorPerlin(new Random(seed + 5L), 4);
      this.decorNoise2 = new NoiseGeneratorPerlin(new Random(seed + 6L), 4);
      this.stormsteelOreGen = new CustomOreGenerator(
         BlocksRegister.ORESTORMSTEEL.getDefaultState(),
         104,
         state -> state.getBlock() == Blocks.STONE || state.getBlock() == BlocksRegister.DOLERITE,
         4,
         14,
         8,
         48,
         190
      );
      this.scaleNoise = new NoiseGeneratorOctaves(this.rand, 10);
      this.depthNoise = new NoiseGeneratorOctaves(this.rand, 16);
      this.forestNoise = new NoiseGeneratorOctaves(this.rand, 8);
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

   public int getIslandCenterYInChunk(int x, int z) {
      double v = MathHelper.clamp(this.islandsCentersNoise.getValue(x, z) / 1.8, -5.0, 5.0);
      if (v > 4.75) {
         return (int)(this.averageIslandY + (v - 4.75) * 214.0);
      } else {
         return v < -4.75 ? (int)(this.averageIslandY + (v + 4.75) * 214.0) : 0;
      }
   }

   public void genFlyingIslands(ChunkBuilder chunkBuilder, int x, int z, BiomeProvider biomes) {
      Biome biome = biomes.getBiome(new BlockPos(x * 16, 1, z * 16));
      if (biome == BiomesRegister.SKY_ISLANDS || biome == BiomesRegister.ARTHROHELIA_ISLANDS) {
         int y = this.getIslandCenterYInChunk(x, z);
         int valSCALE = (int)((this.sNoise.getValue(x, z) + 10.0) * 3.6 + 8.0);
         if (y > 0) {
            int i = x * 16;
            int j = z * 16;

            for (int rx = -valSCALE; rx <= valSCALE; rx++) {
               for (int rz = -valSCALE; rz <= valSCALE; rz++) {
                  int xx = rx + i;
                  int zz = rz + j;
                  double dist = rx * rx + rz * rz;
                  if (sqrtMap.containsKey(dist)) {
                     dist = sqrtMap.get(dist);
                  } else {
                     dist = Math.sqrt(dist);
                     sqrtMap.put(dist, dist);
                  }

                  double valUP = this.surfaceNoise.getValue(xx / 15.0, zz / 15.0) / 1.3 + y - dist / 3.0;
                  double valDOWN = this.downNoise.getValue(xx / 20.0, zz / 20.0) * 2.0 + y + dist - valSCALE / 2.0;
                  double valSTALACTS = this.mNoise.getValue(xx / 3.0, zz / 3.0);
                  int yip = (int)(valDOWN + valSTALACTS);

                  for (int yy = yip; yy < valUP; yy++) {
                     chunkBuilder.setBlockState(new BlockPos(xx, yy, zz), STONE);
                  }

                  if (valUP > yip) {
                     chunkBuilder.setBlockState(new BlockPos(xx, valUP, zz), GRASS);
                     this.decorateIsland(chunkBuilder, xx, MathHelper.floor(valUP + 1.0), zz);
                  }
               }
            }
         }
      }
   }

   public void decorateIsland(ChunkBuilder chunkBuilder, int x, int y, int z) {
      if (this.rand.nextFloat() < 0.01F) {
         BlockPos pos = new BlockPos(x, y, z);
         if (chunkBuilder.isAirBlock(pos) && chunkBuilder.getBlockState(pos.down()).isOpaqueCube()) {
            chunkBuilder.setBlockState(pos, BlocksRegister.FULMINIORTUMBONNY.getDefaultState(), 2);
         }
      } else if (this.rand.nextFloat() < 0.015F) {
         BlockPos pos = new BlockPos(x, y, z);
         if (chunkBuilder.isAirBlock(pos) && chunkBuilder.getBlockState(pos.down()).isOpaqueCube()) {
            chunkBuilder.setBlockState(pos, BlocksRegister.GLOWBUSH.getDefaultState(), 2);
         }
      } else if (this.rand.nextFloat() < 0.28F) {
         BlockPos pos = new BlockPos(x, y, z);
         if (chunkBuilder.isAirBlock(pos) && chunkBuilder.getBlockState(pos.down()).isOpaqueCube()) {
            chunkBuilder.setBlockState(pos, BlocksRegister.FULMINIHERBA.getDefaultState(), 2);
         }
      } else if (this.rand.nextFloat() < 0.75F && this.decorNoise2.getValue(x / 13.0, z / 13.0) > 4.5) {
         BlockPos pos = new BlockPos(x, y, z);
         if (chunkBuilder.isAirBlock(pos) && chunkBuilder.getBlockState(pos.down()).isOpaqueCube()) {
            chunkBuilder.setBlockState(pos, BlocksRegister.FULMINIORTUMBULB.getDefaultState(), 2);
         }
      }
   }

   public void decorateBigIsland(ChunkPrimer chunkPrimer, int x, int y, int z) {
      if (this.rand.nextFloat() < 0.015F) {
         chunkPrimer.setBlockState(x, y, z, BlocksRegister.GLOWBUSH.getDefaultState());
      } else if (this.rand.nextFloat() < 0.28F) {
         chunkPrimer.setBlockState(x, y, z, BlocksRegister.FULMINIHERBA.getDefaultState());
      } else if (this.rand.nextFloat() < 0.75F && this.decorNoise2.getValue(x / 13.0, z / 13.0) > 5.0) {
         chunkPrimer.setBlockState(x, y, z, BlocksRegister.FULMINIORTUMBULB.getDefaultState());
      }
   }

   public void setBlocksInChunk(int x, int z, ChunkPrimer primer) {
      int i = x * 16;
      int j = z * 16;

      for (int rx = 0; rx < 16; rx++) {
         for (int rz = 0; rz < 16; rz++) {
            int xx = rx + i;
            int zz = rz + j;
            double globalVal = this.sNoise.getValue(xx / 200.0, zz / 200.0);
            if (globalVal > 7.0) {
               double globalAdd = (globalVal - 7.0) * 15.0;
               double mainDownVal = Math.max(this.downNoise.getValue(xx / 7.0, zz / 7.0) * 3.0 - globalAdd + this.largeIslandY, 0.0);
               double mainUpVal = Math.min(this.surfaceNoise.getValue(xx / 17.0, zz / 17.0) * 1.6 + globalAdd + this.largeIslandY, 255.0);

               for (int ry = (int)mainDownVal; ry < mainUpVal; ry++) {
                  primer.setBlockState(rx, ry, rz, STONE);
               }

               if (mainUpVal > mainDownVal) {
                  primer.setBlockState(rx, (int)mainUpVal, rz, GRASS);
                  this.decorateBigIsland(primer, rx, (int)mainUpVal + 1, rz);
               }
            }
         }
      }
   }

   public void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomesIn) {
      if (ForgeEventFactory.onReplaceBiomeBlocks(this, x, z, primer, this.world)) {
         double d0 = 0.03125;
         this.depthBuffer = this.surfaceNoise.getRegion(this.depthBuffer, x * 16, z * 16, 16, 16, 0.0625, 0.0625, 1.0);
      }
   }

   public Chunk generateChunk(int x, int z) {
      this.rand.setSeed(x * 341873128712L + z * 132897987541L);
      ChunkPrimer chunkprimer = new ChunkPrimer();
      this.setBlocksInChunk(x, z, chunkprimer);
      this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
      this.replaceBiomeBlocks(x, z, chunkprimer, this.biomesForGeneration);
      Biome biome = this.world.getBiomeProvider().getBiome(new BlockPos(x * 16, 1, z * 16));
      ChunkBuilder chunkBuilder = new ChunkBuilder(chunkprimer, x, z, this.world.getSeed());
      int radiusCheck = 3;

      for (int cx = -radiusCheck; cx <= radiusCheck; cx++) {
         for (int cz = -radiusCheck; cz <= radiusCheck; cz++) {
            this.genFlyingIslands(chunkBuilder, cx + x, cz + z, this.world.getBiomeProvider());
         }
      }

      radiusCheck = 3;
      chunkBuilder.enableAdvancedMode(radiusCheck);

      for (int cx = -radiusCheck; cx <= radiusCheck; cx++) {
         for (int cz = -radiusCheck; cz <= radiusCheck; cz++) {
            int islandChunkX = cx + x;
            int islandChunkZ = cz + z;
            Random islandRand = GenerationHelper.newRandomFromCoords(islandChunkX, islandChunkZ);
            if (islandRand.nextFloat() < 0.01F) {
               Vec3d vecpos = new Vec3d(islandChunkX * 16 + islandRand.nextInt(16), 60 + islandRand.nextInt(100), islandChunkZ * 16 + islandRand.nextInt(16));
               Biome biome2 = this.world.getBiomeProvider().getBiome(new BlockPos(vecpos));
               if (biome2 == BiomesRegister.CRYSTALLIZED_SKY_ISLANDS || biome2 == BiomesRegister.ELECTROFERN_GROVE) {
                  this.worldGenCrystalIsland.setChunkBuilder(chunkBuilder);
                  this.worldGenCrystalIsland.generateCrystalIsland(islandRand, vecpos, 1, 0.6F + islandRand.nextFloat() * 0.25F);
                  chunkBuilder.setBlockState(new BlockPos(vecpos), BlocksRegister.OREADAMANTIUM.getDefaultState());
               }
            }
         }
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
      this.stormsteelOreGen.generate(this.rand, x, z, this.world, this, null);
      float dungeonValue = GenerationHelper.getDungeonValue(x, z, this.world.getSeed(), 7);
      if (dungeonValue >= 0.0F && dungeonValue < 0.1F) {
         BlockPos castlepos = blockpos.add(this.rand.nextInt(16), 50 + this.rand.nextInt(200), this.rand.nextInt(16));
         int centerhallsize = 1 + this.rand.nextInt(4);
         int dungeonsize = 10 + this.rand.nextInt(30);
         ZarpionIntricacyGenerator gen = new ZarpionIntricacyGenerator(dungeonsize, centerhallsize, 7, centerhallsize * (3 + this.rand.nextInt(5)), castlepos);
         gen.generateIntricacy(this.world, this.rand);
      }

      biome.decorate(this.world, this.rand, new BlockPos(i, 0, j));
      if (biome == BiomesRegister.SKY_ISLANDS || biome == BiomesRegister.CRYSTALLIZED_SKY_ISLANDS) {
         double chanceTrees = this.decorNoise2.getValue((x + 3568) / 82.0, (x + 8526) / 82.0);

         for (int ct = 0; ct < (chanceTrees > 0.0 ? 3 : 1); ct++) {
            if (this.rand.nextFloat() < 0.7 + chanceTrees / 40.0) {
               BlockPos pos = GetMOP.getTopBlock(
                     this.world, new BlockPos(i + this.rand.nextInt(16) + 8, 254, j + this.rand.nextInt(16) + 8), BlocksRegister.FULMINIFLORA
                  )
                  .up();
               if (GenerationHelper.isReplaceable(this.world, pos) && this.world.getBlockState(pos.down()).isOpaqueCube()) {
                  boolean pink = this.decorNoise1.getValue(x / 50.0, z / 50.0) < 0.0;
                  if (this.rand.nextFloat() < 0.08) {
                     pink = !pink;
                  }

                  genArthrostelecha(this.world, pos, pink, this.rand);
               }
            }
         }
      }

      if (dungeonValue >= 0.4F && dungeonValue < 0.6F) {
         BlockPos castlepos = blockpos.add(this.rand.nextInt(16), 80 + this.rand.nextInt(100), this.rand.nextInt(16));
         if (!this.world.getBlockState(castlepos).getBlock().isReplaceable(this.world, castlepos)) {
            GetMOP.BlockTraceResult res = GetMOP.blockTrace(this.world, castlepos, EnumFacing.UP, 80, GetMOP.AIR_BLOCKS);
            if (res != null) {
               castlepos = res.pos;
            }
         }

         GenerationHelper.placeStruct(this.world, castlepos, this.rand, ":etherite_altar", 4, 0, 0, null);
      }

      if (TerrainGen.populate(this, this.world, this.rand, x, z, flag, EventType.ANIMALS)) {
         WorldEntitySpawner.performWorldGenSpawning(this.world, biome, i + 8, j + 8, 16, 16, this.rand);
      }

      blockpos = blockpos.add(8, 0, 8);
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

   public static void genArthrostelecha(World world, BlockPos pos, boolean pink, Random rand) {
      int type = rand.nextInt(7) + 1;
      if (pink) {
         if (rand.nextFloat() < 0.5F && (type == 5 || type == 6)) {
            type = rand.nextInt(7) + 1;
         }

         if (type == 1 || type == 5 || type == 6) {
            placeStruct(world, pos, rand, ":pink_tree_" + type, 6, 0, rand.nextInt(4));
         }

         if (type == 2 || type == 3 || type == 4) {
            placeStruct(world, pos, rand, ":pink_tree_" + type, 5, 0, rand.nextInt(4));
         }

         if (type == 7) {
            placeStruct(world, pos, rand, ":pink_tree_" + type, 3, 0, rand.nextInt(4));
         }
      } else {
         if (type == 1 || type == 3) {
            placeStruct(world, pos, rand, ":brass_tree_" + type, 5, 0, rand.nextInt(4));
         }

         if (type == 2) {
            placeStruct(world, pos, rand, ":brass_tree_" + type, 4, 0, rand.nextInt(4));
         }

         if (type == 4) {
            placeStruct(world, pos, rand, ":brass_tree_" + type, 7, 0, rand.nextInt(4));
         }

         if (type == 5) {
            placeStruct(world, pos, rand, ":brass_tree_" + type, 8, 0, rand.nextInt(4));
         }

         if (type == 6 || type == 7) {
            placeStruct(world, pos, rand, ":brass_tree_" + type, 3, 0, rand.nextInt(4));
         }
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
}
