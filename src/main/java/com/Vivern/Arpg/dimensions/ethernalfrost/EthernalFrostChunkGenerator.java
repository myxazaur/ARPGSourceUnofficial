package com.Vivern.Arpg.dimensions.ethernalfrost;

import com.Vivern.Arpg.biomes.FrozenMountain;
import com.Vivern.Arpg.blocks.BlockStalactiteBase;
import com.Vivern.Arpg.dimensions.generationutils.CustomOreGenerator;
import com.Vivern.Arpg.dimensions.generationutils.GenerationHelper;
import com.Vivern.Arpg.dimensions.generationutils.ModularStructureGenerator;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenIceSpikesTrap;
import com.Vivern.Arpg.loot.ListLootTable;
import com.Vivern.Arpg.main.BiomesRegister;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.StructPos;
import com.Vivern.Arpg.tileentity.EnumChest;
import com.Vivern.Arpg.tileentity.TileChest;
import com.Vivern.Arpg.tileentity.TilePresentBox;
import com.Vivern.Arpg.tileentity.TilePuzzle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
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
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextOverworld;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType;

public class EthernalFrostChunkGenerator implements IChunkGenerator {
   protected static final IBlockState STONE = BlocksRegister.GLACIER.getDefaultState();
   protected static final IBlockState GRAVEL = BlocksRegister.SNOWICE.getDefaultState();
   protected static final IBlockState WATER = Blocks.WATER.getDefaultState();
   protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
   protected static final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
   protected static final IBlockState DEEP_STONE = BlocksRegister.FROZENSTONE.getDefaultState();
   private final Random rand;
   private NoiseGeneratorOctaves minLimitPerlinNoise;
   private NoiseGeneratorOctaves maxLimitPerlinNoise;
   private NoiseGeneratorOctaves mainPerlinNoise;
   private NoiseGeneratorPerlin surfaceNoise;
   public NoiseGeneratorOctaves scaleNoise;
   public NoiseGeneratorOctaves depthNoise;
   public NoiseGeneratorOctaves forestNoise;
   public NoiseGeneratorPerlin perlin;
   private final World world;
   private final WorldType terrainType;
   private final double[] heightMap;
   private final float[] biomeWeights;
   private ChunkGenSettings settings;
   private double[] depthBuffer = new double[256];
   private Biome[] biomesForGeneration;
   public int[] biomesIdsForGeneration;
   double[] mainNoiseRegion;
   double[] minLimitRegion;
   double[] maxLimitRegion;
   double[] depthRegion;
   private final NoiseGeneratorOctaves noiseGen4;
   private double[] stoneNoise;
   private MapGenBase caveGenerator;
   public CustomOreGenerator snowiceOreGen;
   public CustomOreGenerator frozenstoneOreGen;
   public CustomOreGenerator iceOreGen;
   public CustomOreGenerator frozencobbleOreGen;
   public CustomOreGenerator oreGen;
   public CustomOreGenerator oreGen2;
   public CustomOreGenerator frozenSlimes;
   public CustomOreGenerator frozenDebrisOreGen;
   private NoiseGeneratorPerlin perlinMountains;
   public WorldGenIceSpikesTrap icetrap1 = new WorldGenIceSpikesTrap(false, 6, 8, false);
   public WorldGenIceSpikesTrap icetrap2 = new WorldGenIceSpikesTrap(true, 6, 8, true);
   public List<Long> dungeons = new ArrayList<>();
   public static int moundArrayMax = 0;
   public static int moundLevelValue = 0;

   public EthernalFrostChunkGenerator(World worldIn, long seed) {
      this.world = worldIn;
      this.terrainType = worldIn.getWorldInfo().getTerrainType();
      this.rand = new Random(seed);
      this.noiseGen4 = new NoiseGeneratorOctaves(this.rand, 4);
      this.stoneNoise = new double[256];
      this.minLimitPerlinNoise = new NoiseGeneratorOctaves(this.rand, 16);
      this.maxLimitPerlinNoise = new NoiseGeneratorOctaves(this.rand, 16);
      this.mainPerlinNoise = new NoiseGeneratorOctaves(this.rand, 8);
      this.surfaceNoise = new NoiseGeneratorPerlin(this.rand, 4);
      this.scaleNoise = new NoiseGeneratorOctaves(this.rand, 10);
      this.depthNoise = new NoiseGeneratorOctaves(this.rand, 16);
      this.forestNoise = new NoiseGeneratorOctaves(this.rand, 8);
      this.heightMap = new double[825];
      this.biomeWeights = new float[25];
      this.caveGenerator = new EthernalFrostCavesMapGen();
      this.perlin = new NoiseGeneratorPerlin(new Random(worldIn.getSeed()), 3);
      this.perlinMountains = new NoiseGeneratorPerlin(new Random(worldIn.getSeed() + 10L), 4);
      this.oreGen = new CustomOreGenerator(BlocksRegister.OREICEGL.getDefaultState(), 100, BlockMatcher.forBlock(BlocksRegister.GLACIER), 2, 6, 7, 10, 40);
      this.oreGen2 = new CustomOreGenerator(BlocksRegister.OREICESN.getDefaultState(), 100, BlockMatcher.forBlock(BlocksRegister.SNOWICE), 2, 4, 6, 10, 50);
      this.snowiceOreGen = new CustomOreGenerator(
         BlocksRegister.SNOWICE.getDefaultState(), 100, BlockMatcher.forBlock(BlocksRegister.GLACIER), 10, 33, 2, 20, 100
      );
      this.frozenstoneOreGen = new CustomOreGenerator(
         BlocksRegister.FROZENSTONE.getDefaultState(), 100, BlockMatcher.forBlock(BlocksRegister.GLACIER), 10, 20, 2, 20, 80
      );
      this.iceOreGen = new CustomOreGenerator(
         Blocks.ICE.getDefaultState(), 100, BlockMatcher.forBlock(BlocksRegister.GLACIER), 8, 22, 5, 22, 250
      );
      this.frozencobbleOreGen = new CustomOreGenerator(
         BlocksRegister.FROZENCOBBLE.getDefaultState(), 100, BlockMatcher.forBlock(BlocksRegister.GLACIER), 4, 15, 6, 2, 50
      );
      this.frozenSlimes = new CustomOreGenerator(
         BlocksRegister.FROZENSLIME.getDefaultState(), 100, BlockMatcher.forBlock(BlocksRegister.GLACIER), 6, 22, 4, 14, 100
      );
      this.frozenDebrisOreGen = new CustomOreGenerator(
         BlocksRegister.FROZENDEBRIS.getDefaultState(),
         100,
         state -> state.getBlock() == BlocksRegister.FROZENSTONE || state.getBlock() == BlocksRegister.GLACIER,
         1,
         4,
         8,
         2,
         17
      );

      for (int i = -2; i <= 2; i++) {
         for (int j = -2; j <= 2; j++) {
            float f = 10.0F / MathHelper.sqrt(i * i + j * j + 0.2F);
            this.biomeWeights[i + 2 + (j + 2) * 5] = f;
         }
      }

      this.settings = new ChunkGenSettings.Factory().build();
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

   public Chunk generateChunk(int x, int z) {
      this.rand.setSeed(x * 341873128712L + z * 132897987541L);
      ChunkPrimer chunkprimer = new ChunkPrimer();
      this.setBlocksInChunk(x, z, chunkprimer);
      BiomeProvider biomeprovider = this.world.getBiomeProvider();
      this.biomesForGeneration = biomeprovider.getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
      if (biomeprovider instanceof BiomeProviderFrost) {
         this.biomesIdsForGeneration = ((BiomeProviderFrost)biomeprovider).getBiomesRaw(this.biomesIdsForGeneration, x * 16, z * 16, 16, 16);
      }

      this.replaceBiomeBlocks(x, z, chunkprimer, this.biomesForGeneration);
      this.caveGenerator.generate(this.world, x, z, chunkprimer);
      Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
      byte[] abyte = chunk.getBiomeArray();

      for (int i = 0; i < abyte.length; i++) {
         abyte[i] = (byte)Biome.getIdForBiome(this.biomesForGeneration[i]);
      }

      chunk.generateSkylightMap();
      return chunk;
   }

   public void blockAGenerate(int xc, int zc, ChunkPrimer chunkprimer) {
      NoiseGeneratorPerlin perlin = new NoiseGeneratorPerlin(new Random(this.world.getSeed()), 3);

      for (int ii = 0; ii < 16; ii++) {
         for (int ss = 0; ss < 16; ss++) {
            int x = xc * 16 + ii;
            int z = zc * 16 + ss;
            int height = chunkprimer.findGroundBlockIdx(x, z);
            double perl = perlin.getValue(x / 10.5, z / 10.5);
            if (perl > 0.0 && perl < 1.0) {
               height = (int)(height + (8.0 - perl));
            }

            perl = (perl + 3.0) * 1.6;
            int hill = perl < 0.0 ? 1 : 85 + (int)Math.round(Math.pow(1.4, perl) * 1.3);

            for (int rr = height; rr < hill; rr++) {
               chunkprimer.setBlockState(x, rr, z, BlocksRegister.GLACIER.getDefaultState());
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
      ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, x, z, false);
      if (biome != Biomes.DESERT
         && biome != Biomes.DESERT_HILLS
         && this.settings.useWaterLakes
         && this.rand.nextInt(this.settings.waterLakeChance) == 0
         && TerrainGen.populate(this, this.world, this.rand, x, z, false, EventType.LAKE)) {
      }

      if (this.rand.nextInt(this.settings.lavaLakeChance / 10) == 0
         && this.settings.useLavaLakes
         && TerrainGen.populate(this, this.world, this.rand, x, z, false, EventType.LAVA)) {
      }

      this.oreGen.generate(this.rand, x, z, this.world, this, null);
      this.oreGen2.generate(this.rand, x, z, this.world, this, null);
      this.frozencobbleOreGen.generate(this.rand, x, z, this.world, this, null);
      this.frozenstoneOreGen.generate(this.rand, x, z, this.world, this, null);
      this.iceOreGen.generate(this.rand, x, z, this.world, this, null);
      this.snowiceOreGen.generate(this.rand, x, z, this.world, this, null);
      this.frozenSlimes.generate(this.rand, x, z, this.world, this, null);
      this.frozenDebrisOreGen.generate(this.rand, x, z, this.world, this, null);
      float dungeonValue = GenerationHelper.getDungeonValue(x, z, this.world.getSeed(), 7);
      if (dungeonValue >= 0.0F && dungeonValue < 0.1F) {
         BlockPos castlepos = GetMOP.getTopBlocks(
            this.world,
            blockpos.add(this.rand.nextInt(16), 254, this.rand.nextInt(16)),
            Blocks.SNOW,
            Blocks.ICE,
            BlocksRegister.SNOWICE,
            BlocksRegister.GLACIER
         );
         if (castlepos.getY() > 1) {
            IceCastle castle = new IceCastle(this.rand);
            castle.generateIceCastle(this.world, castlepos);
         }
      }

      if (dungeonValue >= 0.1F && dungeonValue < 0.63F) {
         BlockPos posmound = this.world.getTopSolidOrLiquidBlock(blockpos.add(this.rand.nextInt(16), 0, this.rand.nextInt(16)));
         if (!this.world.getBlockState(posmound).getMaterial().isLiquid()) {
            generateMound(this.world, posmound, 40, 32, this.rand);
         }
      }

      if (dungeonValue >= 0.63F && dungeonValue < 0.75F && !(biome instanceof FrozenMountain)) {
         BlockPos poshall = GetMOP.getTopBlocks(
            this.world,
            blockpos.add(this.rand.nextInt(16), 254, this.rand.nextInt(16)),
            Blocks.SNOW,
            Blocks.ICE,
            Blocks.WATER,
            BlocksRegister.SNOWICE,
            BlocksRegister.GLACIER
         );
         if (poshall.getY() > 1) {
            ModularStructureGenerator.generator_niveous_hall.generate(this.world, this.rand, poshall);
         }
      }

      if (this.rand.nextFloat() < 0.4) {
         BlockPos uppos = this.world.getHeight(new BlockPos(i + this.rand.nextInt(16) + 8, 0, j + this.rand.nextInt(16) + 8));
         boolean issolidPrev = false;
         if (this.world.getBiome(uppos) != BiomesRegister.FROZEN_LAKE) {
            for (int rr = 20; rr < uppos.getY() + 1; rr++) {
               BlockPos finalpos = new BlockPos(uppos.getX(), rr, uppos.getZ());
               if (this.world.isAirBlock(finalpos)) {
                  if (issolidPrev && this.rand.nextFloat() < 0.5) {
                     if (this.world.canBlockSeeSky(finalpos)) {
                        this.icetrap2.depth = this.rand.nextInt(4) + 6;
                        this.icetrap2.size = this.rand.nextInt(3) + 5;
                        this.icetrap2.generate(this.world, this.rand, finalpos.down());
                     } else {
                        this.icetrap1.depth = this.rand.nextInt(4) + 6;
                        this.icetrap1.size = this.rand.nextInt(3) + 5;
                        this.icetrap1.generate(this.world, this.rand, finalpos.down());
                     }
                  }

                  issolidPrev = false;
               } else {
                  issolidPrev = true;
               }
            }
         }
      }

      biome.decorate(this.world, this.rand, new BlockPos(i, 0, j));
      this.worldDecorate(this.world, this.rand, new BlockPos(i, 0, j));
      if (dungeonValue >= 0.75F && dungeonValue < 1.0F) {
         int xx = x * 16 + this.rand.nextInt(16);
         int zz = z * 16 + this.rand.nextInt(16);
         BlockPos pos = new BlockPos(xx, 255, zz);
         if (!BiomeDictionary.hasType(this.world.getBiome(pos), Type.OCEAN)) {
            BristlingVillage village = new BristlingVillage(this.world, 36 + this.rand.nextInt(44), 200 + this.rand.nextInt(300), 27, this.rand, 5);
            village.generate(pos);
         }
      }

      if (TerrainGen.populate(this, this.world, this.rand, x, z, false, EventType.ANIMALS)) {
         WorldEntitySpawner.performWorldGenSpawning(this.world, biome, i + 8, j + 8, 16, 16, this.rand);
      }

      blockpos = blockpos.add(8, 0, 8);
      if (TerrainGen.populate(this, this.world, this.rand, x, z, false, EventType.ICE)) {
         for (int k2 = 0; k2 < 16; k2++) {
            for (int j3 = 0; j3 < 16; j3++) {
               BlockPos blockpos1 = this.world.getPrecipitationHeight(blockpos.add(k2, 0, j3));
               BlockPos blockpos2 = blockpos1.down();
               if (this.world.canBlockFreezeWater(blockpos2)) {
                  this.world.setBlockState(blockpos2, Blocks.ICE.getDefaultState(), 2);
               }

               Biome biomee = this.world.getBiome(blockpos1);
               if (biomee == BiomesRegister.FROZEN_BEACH || biomee == BiomesRegister.EVERFROST_RIVER) {
                  this.world.setBlockState(blockpos1, BlocksRegister.LOOSESNOW.getDefaultState(), 2);
               } else if (biomee != BiomesRegister.FROZEN_LAKE) {
                  IBlockState statDown = this.world.getBlockState(blockpos2);
                  if (statDown.isFullCube() && statDown.getBlock() != BlocksRegister.LOOSESNOW && statDown.getBlock() != Blocks.SNOW) {
                     this.world
                        .setBlockState(
                           blockpos1,
                           statDown.getBlock() == Blocks.ICE
                              ? BlocksRegister.LOOSESNOW.getDefaultState()
                              : Blocks.SNOW_LAYER.getDefaultState(),
                           2
                        );
                  }
               }

               if (this.world.getBiome(blockpos2) == BiomesRegister.ICE_HILLS) {
                  NoiseGeneratorPerlin perlin = new NoiseGeneratorPerlin(new Random(this.world.getSeed()), 3);
                  int xx = blockpos.getX() + k2;
                  int zz = blockpos.getZ() + j3;
                  int grandNoise = (int)Math.max(0.0, 14.0 * this.perlinMountains.getValue(xx / 250.0, zz / 250.0));
                  int height = this.world.getHeight(xx, zz);
                  double perl = perlin.getValue(xx / 10.5, zz / 10.5);
                  if (perl > 0.0 && perl < 1.0) {
                     height = (int)(height + (8.0 - perl));
                  }

                  perl = (perl + 3.0) * 1.67;
                  int hill = perl < 0.0 ? 1 : 64 + grandNoise + (int)Math.round(Math.pow(1.36, perl) * 1.3);

                  for (int rrx = height; rrx < hill; rrx++) {
                     if (hill * 0.95 < rrx && rrx > 115.0 + perl * 2.0) {
                        this.world.setBlockState(new BlockPos(xx, rrx, zz), Blocks.SNOW.getDefaultState());
                     } else {
                        this.world.setBlockState(new BlockPos(xx, rrx, zz), BlocksRegister.GLACIER.getDefaultState());
                     }

                     if (rrx + 1 == hill) {
                        this.world.setBlockState(new BlockPos(xx, rrx + 1, zz), Blocks.SNOW_LAYER.getDefaultState());
                     }
                  }
               }
            }
         }
      }

      ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, x, z, false);
      BlockFalling.fallInstantly = false;
   }

   public static void emitSnowfall(World worldIn, Random random, BlockPos pos, int chunkx, int chunkz, IBlockState snow) {
      BlockPos posdown = pos.down();
      BlockPos nextpos = null;
      if (worldIn.isAirBlock(posdown)) {
         nextpos = posdown;
      } else {
         int ne = random.nextInt(4);
         boolean diagonal = random.nextBoolean();

         for (int i = 0; i < 4; i++) {
            ne = GetMOP.next(ne, 1, 4);
            EnumFacing face = EnumFacing.HORIZONTALS[ne];
            BlockPos offpos = diagonal ? posdown.offset(face).offset(face.rotateY()) : posdown.offset(face);
            if (worldIn.isAirBlock(offpos)) {
               nextpos = offpos;
               break;
            }
         }

         if (nextpos == null) {
            int ne0 = random.nextInt(4);
            boolean diagonal0 = random.nextBoolean();

            for (int ix = 0; ix < 4; ix++) {
               ne0 = GetMOP.next(ne0, 1, 4);
               EnumFacing face = EnumFacing.HORIZONTALS[ne0];
               BlockPos offpos = diagonal0 ? pos.offset(face).offset(face.rotateY()) : pos.offset(face);
               if (worldIn.isAirBlock(offpos)) {
                  nextpos = offpos;
                  break;
               }
            }
         }

         if (nextpos == null) {
            int ne2 = random.nextInt(4);

            for (int ixx = 0; ixx < 4; ixx++) {
               ne2 = GetMOP.next(ne2, 1, 4);
               EnumFacing face = EnumFacing.HORIZONTALS[ne2];
               BlockPos offpos = posdown.offset(face, 2);
               if (worldIn.isAirBlock(offpos)) {
                  nextpos = offpos;
                  break;
               }
            }
         }
      }

      if (nextpos != null) {
         worldIn.setBlockState(pos, snow);
         emitSnowfall(worldIn, random, nextpos, chunkx, chunkz, snow);
         worldIn.setBlockState(pos.west(), snow);
         worldIn.setBlockState(pos.east(), snow);
         worldIn.setBlockState(pos.south(), snow);
         worldIn.setBlockState(pos.north(), snow);
      }
   }

   public void worldDecorate(World worldIn, Random random, BlockPos pos) {
      if (random.nextFloat() < 0.8) {
         int height = worldIn.getHeight(pos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8)).getY();

         for (int ii = 0; ii < random.nextInt(3) + 1; ii++) {
            List<BlockPos> listposes = new ArrayList<>();
            BlockPos posit = pos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8);

            for (int rr = 15; rr < height - 8; rr++) {
               BlockPos currentpos = posit.add(0, rr, 0);
               if (!worldIn.isAirBlock(currentpos.down()) && worldIn.isAirBlock(currentpos) && worldIn.isBlockFullCube(currentpos.down())
                  )
                {
                  listposes.add(currentpos);
               }
            }

            if (!listposes.isEmpty()) {
               BlockPos finalpos = listposes.get(random.nextInt(listposes.size()));
               if (finalpos != null) {
                  worldIn.setBlockState(finalpos, BlocksRegister.FROZENBARREL.getDefaultState());
               }
            }
         }
      }

      if (random.nextFloat() < 0.55) {
         int height = worldIn.getHeight(pos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8)).getY();
         List<BlockPos> listposes = new ArrayList<>();
         BlockPos posit = pos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8);

         for (int rrx = 25; rrx < height - 12; rrx++) {
            BlockPos currentpos = posit.add(0, rrx, 0);
            if (!worldIn.isAirBlock(currentpos.down()) && worldIn.isAirBlock(currentpos) && worldIn.isBlockFullCube(currentpos.down())) {
               listposes.add(currentpos);
            }
         }

         if (!listposes.isEmpty()) {
            BlockPos finalpos = listposes.get(random.nextInt(listposes.size()));
            if (finalpos != null && (this.world.getBlockState(finalpos.down()).getBlock() != Blocks.SNOW || random.nextFloat() < 0.5F)
               )
             {
               worldIn.setBlockState(finalpos, BlocksRegister.PRESENTBOX.getDefaultState());
               TileEntity tileentity = worldIn.getTileEntity(finalpos);
               if (tileentity instanceof TilePresentBox) {
                  ((TilePresentBox)tileentity).setLootTable(ListLootTable.PRESENT_BOX, this.world.rand.nextLong());
               }
            }
         }
      }

      if (random.nextFloat() < 0.2) {
         int height = worldIn.getHeight(pos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8)).getY();
         List<BlockPos> listposes = new ArrayList<>();
         BlockPos posit = pos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8);

         for (int rrxx = 13; rrxx < height - 12; rrxx++) {
            BlockPos currentpos = posit.add(0, rrxx, 0);
            if (!worldIn.isAirBlock(currentpos.down()) && worldIn.isAirBlock(currentpos) && worldIn.isBlockFullCube(currentpos.down())) {
               listposes.add(currentpos);
            }
         }

         if (!listposes.isEmpty()) {
            BlockPos finalpos = listposes.get(random.nextInt(listposes.size()));
            if (finalpos != null) {
               generateChestAndSpawner(worldIn, finalpos, 2 + random.nextInt(4), random);
            }
         }
      }

      if (random.nextFloat() < 0.15) {
         int height = 50;
         List<BlockPos> listposes = new ArrayList<>();
         BlockPos posit = pos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8);

         for (int rrxxx = 13; rrxxx < height; rrxxx++) {
            BlockPos currentpos = posit.add(0, rrxxx, 0);
            if (!worldIn.isAirBlock(currentpos.down()) && worldIn.isAirBlock(currentpos) && worldIn.isBlockFullCube(currentpos.down())) {
               listposes.add(currentpos);
            }
         }

         if (!listposes.isEmpty()) {
            BlockPos finalpos = listposes.get(random.nextInt(listposes.size()));
            if (finalpos != null) {
               generateWinterAltar(worldIn, finalpos.down(), random);
            }
         }
      }

      if (random.nextFloat() < 0.02) {
         BlockPos position = new BlockPos(pos.getX() + 8, random.nextInt(40) + 10, pos.getZ() + 8);
         WorldServer worldServer = (WorldServer)worldIn;
         MinecraftServer minecraftServer = worldIn.getMinecraftServer();
         TemplateManager templateManager = worldServer.getStructureTemplateManager();
         Template template = templateManager.get(minecraftServer, new ResourceLocation("arpg:dungeon_snow_1"));
         PlacementSettings settings = new PlacementSettings();
         int swr = random.nextInt(4);
         if (swr == 0) {
            settings.setRotation(Rotation.CLOCKWISE_180);
         }

         if (swr == 1) {
            settings.setRotation(Rotation.CLOCKWISE_90);
         }

         if (swr == 2) {
            settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
         }

         if (swr == 3) {
            settings.setRotation(Rotation.NONE);
         }

         template.addBlocksToWorld(worldIn, position, settings);
      }

      for (int ii = 0; ii < 16; ii++) {
         for (int ss = 0; ss < 16; ss++) {
            for (int rrxxxx = 20; rrxxxx < 50; rrxxxx++) {
               BlockPos uppos = new BlockPos(pos.getX() + ii + 8, rrxxxx, pos.getZ() + ss + 8);
               if (worldIn.isAirBlock(uppos.add(0, 1, 0)) && worldIn.getBlockState(uppos).getBlock() == BlocksRegister.GLACIER) {
                  double value = this.perlin.getValue((pos.getX() + ii + 8) / 10.1, (pos.getZ() + ss + 8) / 10.1);
                  if (value > 0.5) {
                     worldIn.setBlockState(uppos, Blocks.ICE.getDefaultState());
                  }
               }
            }
         }
      }

      for (int ii = 0; ii < random.nextInt(28); ii++) {
         BlockPos uppos = worldIn.getHeight(pos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8));
         int blocks = 0;
         boolean created = false;
         boolean setaround = false;
         boolean enstalacts = false;

         for (int ss = uppos.getY() - 2; ss > 5; ss--) {
            BlockPos bpos = new BlockPos(uppos.getX(), ss, uppos.getZ());
            if (worldIn.isAirBlock(bpos)) {
               if (!created) {
                  blocks = random.nextInt(5);
                  created = true;
                  if (random.nextFloat() < 0.3) {
                     setaround = true;
                  }
               }
            } else {
               created = false;
            }

            if (blocks <= 0) {
               if (created && enstalacts) {
                  if (!(worldIn.getBlockState(bpos.up()).getBlock() instanceof BlockStalactiteBase)) {
                     worldIn.setBlockState(bpos, BlocksRegister.STALACTITEFROZEN.getStateFromMeta(random.nextInt(5)));
                     if (random.nextFloat() < 0.3) {
                        enstalacts = false;
                     }
                  } else if (!worldIn.isAirBlock(bpos.down()) && worldIn.isBlockFullCube(bpos.down())) {
                     worldIn.setBlockState(bpos, BlocksRegister.STALAGMITEFROZEN.getStateFromMeta(random.nextInt(5)));
                     enstalacts = false;
                  } else {
                     worldIn.setBlockState(bpos, BlocksRegister.STALACTITEFROZENADD.getStateFromMeta(random.nextInt(5)));
                     if (random.nextFloat() < 0.5) {
                        enstalacts = false;
                     }
                  }
               }
            } else {
               blocks--;
               worldIn.setBlockState(bpos, Blocks.ICE.getDefaultState());
               if (setaround) {
                  for (int aa = 0; aa < random.nextInt(3); aa++) {
                     BlockPos aroundpos = bpos.add(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
                     worldIn.setBlockState(aroundpos, Blocks.ICE.getDefaultState());
                     if (worldIn.isAirBlock(aroundpos.add(0, 1, 0))) {
                        worldIn.setBlockState(aroundpos.add(0, 1, 0), Blocks.ICE.getDefaultState());
                     }

                     if (random.nextFloat() < 0.5) {
                        worldIn.setBlockState(aroundpos.add(0, -1, 0), Blocks.ICE.getDefaultState());
                        if (random.nextFloat() < 0.5) {
                           worldIn.setBlockState(aroundpos.add(0, -2, 0), Blocks.ICE.getDefaultState());
                        }
                     }
                  }

                  setaround = false;
               }

               if (blocks == 0 && random.nextFloat() < 0.85) {
                  enstalacts = true;
               }
            }
         }
      }

      for (int ii = 0; ii < random.nextInt(28); ii++) {
         BlockPos uppos = worldIn.getHeight(pos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8));
         int blocks = 0;
         boolean created = false;
         boolean setaround = false;
         boolean enstalacts = false;

         for (int ss = 0; ss < uppos.getY() - 2; ss++) {
            BlockPos bposx = new BlockPos(uppos.getX(), ss, uppos.getZ());
            if (worldIn.isAirBlock(bposx)) {
               if (!created) {
                  blocks = random.nextInt(4);
                  created = true;
                  if (random.nextFloat() < 0.4) {
                     setaround = true;
                  }
               }
            } else {
               created = false;
            }

            if (blocks <= 0) {
               if (created && enstalacts) {
                  if (!(worldIn.getBlockState(bposx.down()).getBlock() instanceof BlockStalactiteBase)) {
                     int meta = random.nextInt(5);
                     worldIn.setBlockState(bposx, BlocksRegister.STALAGMITEFROZEN.getStateFromMeta(meta));
                     if (random.nextFloat() < 0.3 || meta == 2 || meta == 3) {
                        enstalacts = false;
                     }
                  } else if (!worldIn.isAirBlock(bposx.up()) && worldIn.isBlockFullCube(bposx.up())) {
                     worldIn.setBlockState(bposx, BlocksRegister.STALACTITEFROZEN.getStateFromMeta(random.nextInt(5)));
                     enstalacts = false;
                  } else {
                     worldIn.setBlockState(bposx, BlocksRegister.STALACTITEFROZENADD.getStateFromMeta(random.nextInt(5)));
                     if (random.nextFloat() < 0.5) {
                        enstalacts = false;
                     }
                  }
               }
            } else {
               blocks--;
               worldIn.setBlockState(bposx, Blocks.ICE.getDefaultState());
               if (setaround) {
                  for (int aa = 0; aa < random.nextInt(3); aa++) {
                     BlockPos aroundposx = bposx.add(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
                     worldIn.setBlockState(aroundposx, Blocks.ICE.getDefaultState());
                     if (worldIn.isAirBlock(aroundposx.add(0, -1, 0))) {
                        worldIn.setBlockState(aroundposx.add(0, -1, 0), Blocks.ICE.getDefaultState());
                     }

                     if (random.nextFloat() < 0.5) {
                        worldIn.setBlockState(aroundposx.add(0, 1, 0), Blocks.ICE.getDefaultState());
                        if (random.nextFloat() < 0.5) {
                           worldIn.setBlockState(aroundposx.add(0, 2, 0), Blocks.ICE.getDefaultState());
                        }
                     }
                  }

                  setaround = false;
               }

               if (blocks == 0 && random.nextFloat() < 0.85) {
                  enstalacts = true;
               }
            }
         }
      }

      for (int ii = 0; ii < random.nextInt(100); ii++) {
         BlockPos uppos = worldIn.getHeight(pos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8));
         int blocks = 0;
         boolean created = false;

         for (int ss = uppos.getY() - 2; ss > 5; ss--) {
            BlockPos bposxx = new BlockPos(uppos.getX(), ss, uppos.getZ());
            if (worldIn.isAirBlock(bposxx)) {
               if (!created) {
                  blocks = random.nextInt(4);
                  created = true;
                  if (random.nextFloat() < 0.15) {
                     blocks += random.nextInt(3);
                  }
               }
            } else {
               created = false;
            }

            if (blocks > 0) {
               if (!(worldIn.getBlockState(bposxx.up()).getBlock() instanceof BlockStalactiteBase)) {
                  worldIn.setBlockState(bposxx, BlocksRegister.STALACTITEFROZEN.getStateFromMeta(random.nextInt(5)));
               } else if (!worldIn.isAirBlock(bposxx.down()) && worldIn.isBlockFullCube(bposxx.down())) {
                  worldIn.setBlockState(bposxx, BlocksRegister.STALAGMITEFROZEN.getStateFromMeta(random.nextInt(5)));
                  blocks = 1;
               } else {
                  worldIn.setBlockState(bposxx, BlocksRegister.STALACTITEFROZENADD.getStateFromMeta(random.nextInt(5)));
               }

               blocks--;
            }
         }
      }

      for (int ii = 0; ii < random.nextInt(100); ii++) {
         BlockPos uppos = worldIn.getHeight(pos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8));
         int blocks = 0;
         boolean created = false;

         for (int ss = 0; ss < uppos.getY() - 5; ss++) {
            BlockPos bposxxx = new BlockPos(uppos.getX(), ss, uppos.getZ());
            if (worldIn.isAirBlock(bposxxx)) {
               if (!created) {
                  blocks = random.nextInt(3);
                  created = true;
                  if (random.nextFloat() < 0.15) {
                     blocks += random.nextInt(3);
                  }
               }
            } else {
               created = false;
            }

            if (blocks > 0) {
               if (!(worldIn.getBlockState(bposxxx.down()).getBlock() instanceof BlockStalactiteBase)) {
                  int meta = random.nextInt(5);
                  worldIn.setBlockState(bposxxx, BlocksRegister.STALAGMITEFROZEN.getStateFromMeta(meta));
                  if (meta == 2 || meta == 3) {
                     blocks = 1;
                  }
               } else if (!worldIn.isAirBlock(bposxxx.up()) && worldIn.isBlockFullCube(bposxxx.up())) {
                  worldIn.setBlockState(bposxxx, BlocksRegister.STALACTITEFROZEN.getStateFromMeta(random.nextInt(5)));
                  blocks = 1;
               } else {
                  worldIn.setBlockState(bposxxx, BlocksRegister.STALACTITEFROZENADD.getStateFromMeta(random.nextInt(5)));
               }

               blocks--;
            }
         }
      }
   }

   public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
      Biome biome = this.world.getBiome(pos);
      return biome.getSpawnableList(creatureType);
   }

   public void recreateStructures(Chunk chunkIn, int x, int z) {
   }

   public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
      return false;
   }

   public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
      return null;
   }

   public boolean generateStructures(Chunk chunkIn, int x, int z) {
      return false;
   }

   private void setBlocksInChunk(int x, int z, ChunkPrimer primer) {
      BiomeProvider biomeprovider = this.world.getBiomeProvider();
      this.biomesForGeneration = biomeprovider.getBiomesForGeneration(this.biomesForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);
      if (biomeprovider instanceof BiomeProviderFrost) {
         this.biomesIdsForGeneration = ((BiomeProviderFrost)biomeprovider).getBiomesForGenerationRaw(this.biomesIdsForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);
      }

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
                           int yyy = i2 * 8 + j2;
                           primer.setBlockState(i * 4 + k2, yyy, l * 4 + l2, this.rand.nextInt(8) > yyy - 8 ? DEEP_STONE : STONE);
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
               Biome Biome = biomesIn[j + i * 16];
               this.generateBiomeTerrain(this.world, this.rand, primer, x * 16 + i, z * 16 + j, this.depthBuffer[j + i * 16], Biome);
            }
         }
      }
   }

   private void generateBiomeTerrain(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal, Biome biome) {
      int i = worldIn.getSeaLevel();
      IBlockState iblockstate = biome.topBlock;
      IBlockState iblockstate1 = biome.fillerBlock;
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
               if (rand.nextInt(13) > j1 - 15) {
                  chunkPrimerIn.setBlockState(i1, j1, l, DEEP_STONE);
               }

               if (j == -1) {
                  if (k <= 0) {
                     iblockstate = AIR;
                     iblockstate1 = STONE;
                  } else if (j1 >= i - 4 && j1 <= i + 1) {
                     iblockstate = biome.topBlock;
                     iblockstate1 = biome.fillerBlock;
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
                     chunkPrimerIn.setBlockState(i1, j1, l, GRAVEL);
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

            for (int j1 = -i1; j1 <= i1; j1++) {
               for (int k1 = -i1; k1 <= i1; k1++) {
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

   public static boolean checkFlat(World worldIn, BlockPos pos) {
      BlockPos posd = pos.down();
      return worldIn.getBlockState(posd.west(4)).getBlock() != Blocks.AIR
         && worldIn.getBlockState(posd.east(4)).getBlock() != Blocks.AIR
         && worldIn.getBlockState(posd.south(4)).getBlock() != Blocks.AIR
         && worldIn.getBlockState(posd.north(4)).getBlock() != Blocks.AIR;
   }

   public static boolean isNoCollideStruct(List<StructPos> placedHomes, StructPos strpos) {
      for (StructPos spos : placedHomes) {
         if (Math.abs(spos.getX() - strpos.getX()) < spos.size + strpos.size && Math.abs(spos.getZ() - strpos.getZ()) < spos.size + strpos.size) {
            return false;
         }
      }

      return true;
   }

   public static void generateMound(World world, BlockPos pos, int size, int arraySize, Random rand) {
      ChestReplacersFrozen.posesToSetPuzzle.clear();
      moundArrayMax = arraySize;
      moundLevelValue = 0;
      boolean[][][] fillArray = new boolean[arraySize][3][arraySize];
      GenerationHelper.placeStructMound(world, pos, rand, ":mound_entry", 4, -16, rand.nextInt(4));
      int tubes = Math.max((pos.getY() - 70) / 8, 0);
      if (tubes > 0) {
         for (int t = 1; t <= tubes; t++) {
            GenerationHelper.placeStructMound(world, pos, rand, ":mound_entry_tube", 4, -16 - 8 * t, rand.nextInt(4));
         }
      }

      int ri4 = rand.nextInt(4);
      GenerationHelper.placeStructMound(world, pos, rand, ":mound_entry_down", 3, -23 - 8 * tubes, GetMOP.next(ri4, 2, 4));
      int[] sizeContainer = new int[]{size};
      int ars2 = arraySize / 2;
      fillArray[ars2][0][ars2] = true;
      recursiveGenerateMound(world, pos.add(-ars2 * 7, -23 - 8 * tubes, -ars2 * 7), fillArray, ars2, 0, ars2, ri4, rand, sizeContainer);

      for (BlockPos puzlPos : ChestReplacersFrozen.posesToSetPuzzle) {
         TileEntity tileentity = world.getTileEntity(puzlPos);
         if (tileentity != null && tileentity instanceof TileChest) {
            EnumFacing face = EnumFacing.HORIZONTALS[rand.nextInt(4)];
            BlockPos posss = puzlPos.offset(face);
            if (world.isAirBlock(posss) && !world.isAirBlock(posss.down())) {
               world.setBlockState(posss, BlocksRegister.PUZZLE.getDefaultState());
               TilePuzzle puzzle = (TilePuzzle)world.getTileEntity(posss);
               puzzle.setupPuzzle(4);
               puzzle.chestOpened = face.getOpposite();
               ((TileChest)tileentity).lockWithPuzzle(true);
            }
         }
      }
   }

   public static boolean isOutOfMound(int x, int y, int z) {
      return x >= moundArrayMax || z >= moundArrayMax || x < 0 || z < 0 || y < 0 || y >= 3;
   }

   public static void recursiveGenerateMound(
      World world, BlockPos startpos, boolean[][][] fillArray, int x, int y, int z, int rotate, Random rand, int[] sizeContainer
   ) {
      if (rand.nextFloat() < 0.8) {
         if (rotate == 0) {
            z--;
         }

         if (rotate == 1) {
            x--;
         }

         if (rotate == 2) {
            z++;
         }

         if (rotate == 3) {
            x++;
         }

         if (isOutOfMound(x, y, z)) {
            BlockPos endpos = startpos.add(x * 7, -y * 15, z * 7);
            GenerationHelper.placeStructMound(world, endpos, rand, ":mound_end", 3, 0, rotate);
            return;
         }

         if (fillArray[x][y][z]) {
            return;
         }

         BlockPos finalpos = startpos.add(x * 7, -y * 15, z * 7);
         if (rand.nextFloat() < 0.5) {
            int newr = rand.nextFloat() < 0.3 ? rotate : (rand.nextFloat() < 0.5 ? GetMOP.next(rotate, 1, 4) : GetMOP.next(rotate, 3, 4));
            GenerationHelper.placeStructMound(world, finalpos, rand, ":mound_crossroad", 3, 0, newr);
            fillArray[x][y][z] = true;
            sizeContainer[0]--;
            if (sizeContainer[0] > 0) {
               recursiveGenerateMound(world, startpos, fillArray, x, y, z, newr, rand, sizeContainer);
            }

            for (int rr = 0; rr < 4; rr++) {
               if (rr != rotate && rr != newr) {
                  if (rand.nextFloat() < 0.25) {
                     int rx = x;
                     int rz = z;
                     if (rr == 0) {
                        rz = z - 1;
                     }

                     if (rr == 1) {
                        rx = x - 1;
                     }

                     if (rr == 2) {
                        rz++;
                     }

                     if (rr == 3) {
                        rx++;
                     }

                     if (!isOutOfMound(rx, y, rz) && !fillArray[rx][y][rz]) {
                        if (rand.nextFloat() < 0.5 - moundLevelValue * 0.2 && y < 2 && !fillArray[rx][y + 1][rz]) {
                           moundLevelValue++;
                           BlockPos cfinalpos = startpos.add(rx * 7, -y * 15, rz * 7);
                           GenerationHelper.placeStructMound(world, cfinalpos, rand, ":mound_entry_up", 3, -8, rr);
                           int ri4 = rand.nextInt(4);
                           GenerationHelper.placeStructMound(world, cfinalpos, rand, ":mound_entry_down", 3, -15, GetMOP.next(ri4, 2, 4));
                           fillArray[rx][y][rz] = true;
                           fillArray[rx][y + 1][rz] = true;
                           sizeContainer[0] += 6;
                           recursiveGenerateMound(world, startpos, fillArray, rx, y + 1, rz, ri4, rand, sizeContainer);
                        } else {
                           BlockPos cfinalpos = startpos.add(rx * 7, -y * 15, rz * 7);
                           GenerationHelper.placeStructMound(world, cfinalpos, rand, ":mound_tunnel", 3, 0, rr);
                           fillArray[rx][y][rz] = true;
                           sizeContainer[0]--;
                           if (sizeContainer[0] > 0) {
                              recursiveGenerateMound(world, startpos, fillArray, rx, y, rz, rr, rand, sizeContainer);
                           } else {
                              placeEndMound(world, startpos, fillArray, rx, y, rz, rr, rand);
                           }
                        }
                     }
                  } else {
                     int rxx = x;
                     int rzx = z;
                     if (rr == 0) {
                        rzx = z - 2;
                     }

                     if (rr == 1) {
                        rxx = x - 2;
                     }

                     if (rr == 2) {
                        rzx += 2;
                     }

                     if (rr == 3) {
                        rxx += 2;
                     }

                     if (!isOutOfMound(rxx + 1, y, rzx + 1)
                        && !isOutOfMound(rxx - 1, y, rzx - 1)
                        && !fillArray[rxx][y][rzx + 1]
                        && !fillArray[rxx - 1][y][rzx]
                        && !fillArray[rxx][y][rzx]
                        && !fillArray[rxx + 1][y][rzx]
                        && !fillArray[rxx][y][rzx - 1]) {
                        BlockPos rrpos;
                        if (rr == 0) {
                           rrpos = finalpos.add(0, 0, -12);
                        } else if (rr == 1) {
                           rrpos = finalpos.add(-12, 0, 0);
                        } else if (rr == 2) {
                           rrpos = finalpos.add(0, 0, 12);
                        } else {
                           rrpos = finalpos.add(12, 0, 0);
                        }

                        fillArray[rxx][y][rzx] = true;
                        fillArray[rxx + 1][y][rzx] = true;
                        fillArray[rxx - 1][y][rzx] = true;
                        fillArray[rxx][y][rzx - 1] = true;
                        fillArray[rxx][y][rzx + 1] = true;
                        GenerationHelper.placeStructMound(world, rrpos, rand, ":mound_tomb_" + (rand.nextInt(3) + 1), 8, 0, rr);
                     }
                  }
               }
            }
         } else {
            GenerationHelper.placeStructMound(world, finalpos, rand, ":mound_tunnel", 3, 0, rotate);
            fillArray[x][y][z] = true;
            sizeContainer[0]--;
            if (sizeContainer[0] > 0) {
               recursiveGenerateMound(world, startpos, fillArray, x, y, z, rotate, rand, sizeContainer);
            } else {
               placeEndMound(world, startpos, fillArray, x, y, z, rotate, rand);
            }
         }
      } else {
         if (rotate == 0) {
            z -= 2;
         }

         if (rotate == 1) {
            x -= 2;
         }

         if (rotate == 2) {
            z += 2;
         }

         if (rotate == 3) {
            x += 2;
         }

         if (isOutOfMound(x + 1, y, z + 1) || isOutOfMound(x - 1, y, z - 1)) {
            BlockPos endpos = startpos.add(x * 7, -y * 15, z * 7);
            GenerationHelper.placeStructMound(world, endpos, rand, ":mound_end", 3, 0, rotate);
            if (rotate == 0) {
               z++;
            }

            if (rotate == 1) {
               x++;
            }

            if (rotate == 2) {
               z--;
            }

            if (rotate == 3) {
               x--;
            }

            BlockPos endpos2 = startpos.add(x * 7, -y * 15, z * 7);
            GenerationHelper.placeStructMound(world, endpos2, rand, ":mound_tunnel", 3, 0, rotate);
            return;
         }

         if (fillArray[x - 1][y][z + 1]
            || fillArray[x][y][z + 1]
            || fillArray[x + 1][y][z + 1]
            || fillArray[x - 1][y][z]
            || fillArray[x][y][z]
            || fillArray[x + 1][y][z]
            || fillArray[x - 1][y][z - 1]
            || fillArray[x][y][z - 1]
            || fillArray[x + 1][y][z - 1]) {
            return;
         }

         BlockPos finalpos = startpos.add(x * 7, -y * 15, z * 7);
         GenerationHelper.placeStructMound(
            world, finalpos, rand, ":mound_room_" + (rand.nextFloat() < 0.3 ? rand.nextInt(3) + 3 : rand.nextInt(2) + 1), 10, 0, rotate
         );
         fillArray[x][y][z] = true;
         fillArray[x + 1][y][z] = true;
         fillArray[x - 1][y][z] = true;
         fillArray[x][y][z - 1] = true;
         fillArray[x + 1][y][z - 1] = true;
         fillArray[x - 1][y][z - 1] = true;
         fillArray[x][y][z + 1] = true;
         fillArray[x + 1][y][z + 1] = true;
         fillArray[x - 1][y][z + 1] = true;
         if (rotate == 0) {
            z--;
         }

         if (rotate == 1) {
            x--;
         }

         if (rotate == 2) {
            z++;
         }

         if (rotate == 3) {
            x++;
         }

         sizeContainer[0] -= 3;
         if (sizeContainer[0] > 0) {
            recursiveGenerateMound(world, startpos, fillArray, x, y, z, rotate, rand, sizeContainer);
         } else {
            placeEndMound(world, startpos, fillArray, x, y, z, rotate, rand);
         }
      }
   }

   public static void placeEndMound(World world, BlockPos startpos, boolean[][][] fillArray, int x, int y, int z, int rotate, Random rand) {
      if (rotate == 0) {
         z--;
      }

      if (rotate == 1) {
         x--;
      }

      if (rotate == 2) {
         z++;
      }

      if (rotate == 3) {
         x++;
      }

      if (isOutOfMound(x, y, z)) {
         BlockPos endpos = startpos.add(x * 7, -y * 15, z * 7);
         GenerationHelper.placeStructMound(world, endpos, rand, ":mound_end", 3, 0, rotate);
      } else if (!fillArray[x][y][z]) {
         BlockPos endpos = startpos.add(x * 7, -y * 15, z * 7);
         GenerationHelper.placeStructMound(world, endpos, rand, ":mound_end", 3, 0, rotate);
         fillArray[x][y][z] = true;
      }
   }

   public static void generateChestAndSpawner(World world, BlockPos pos, int size, Random rand) {
      boolean slimes = rand.nextFloat() < 0.3F;
      boolean barrels = rand.nextFloat() < 0.3F;
      world.setBlockState(pos, BlocksRegister.FROZENBRICKS.getDefaultState(), 2);

      for (int x = -size; x <= size; x++) {
         for (int z = -size; z <= size; z++) {
            if (rand.nextFloat() < 0.6F) {
               BlockPos poss = pos.add(x, 0, z);
               if (world.getBlockState(poss).isFullCube()) {
                  world.setBlockState(poss, BlocksRegister.FROZENBRICKS.getDefaultState(), 2);
                  if (slimes && rand.nextFloat() < 0.4F && world.isAirBlock(poss.up())) {
                     world.setBlockState(poss.up(), BlocksRegister.FROZENSLIME.getDefaultState(), 2);
                  }

                  if (barrels && rand.nextFloat() < 0.08F && world.isAirBlock(poss.up())) {
                     world.setBlockState(poss.up(), BlocksRegister.FROZENBARREL.getDefaultState(), 2);
                  }
               }
            }
         }
      }

      GenerationHelper.setChestWithLoot(
         world, pos.up(), EnumChest.FROZEN, ListLootTable.CHESTS_FROZEN_STRUCTURES, EnumFacing.HORIZONTALS[rand.nextInt(4)]
      );
      world.setBlockState(pos.up(2), BlocksRegister.MOBSPAWNERFROZEN.getDefaultState(), 2);
      DimensionEthernalFrost.setupRandomSpawner(world, world.getTileEntity(pos.up(2)), DimensionEthernalFrost.EnumEverfrostSpawner.STRUCTURES, rand);
   }

   public static void generateWinterAltar(World world, BlockPos pos, Random rand) {
      world.setBlockState(pos.up(), BlocksRegister.WINTERALTAR.getDefaultState(), 2);
      world.setBlockState(pos, BlocksRegister.FROZENBRICKS.getDefaultState(), 2);

      for (int x = 0; x <= 30; x++) {
         BlockPos poss = pos.add(rand.nextGaussian() * 1.3, 0.0, rand.nextGaussian() * 1.3);
         if (!world.isAirBlock(poss)) {
            world.setBlockState(poss, BlocksRegister.FROZENBRICKS.getDefaultState(), 2);
         }
      }
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
}
