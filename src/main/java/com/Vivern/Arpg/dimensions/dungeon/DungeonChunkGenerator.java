//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.dimensions.dungeon;

import com.Vivern.Arpg.blocks.BlockSpeleothem;
import com.Vivern.Arpg.blocks.BlueGlowingMushroom;
import com.Vivern.Arpg.blocks.CaveCrystal;
import com.Vivern.Arpg.blocks.GlowingCaveCrystal;
import com.Vivern.Arpg.blocks.SeleniteCrystal;
import com.Vivern.Arpg.dimensions.generationutils.CustomOreGenerator;
import com.Vivern.Arpg.dimensions.generationutils.GenerationHelper;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenAncientRuins;
import com.Vivern.Arpg.loot.ListLootTable;
import com.Vivern.Arpg.main.BiomesRegister;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.NoiseGenerator3D;
import com.Vivern.Arpg.mobs.SpawnerTuners;
import com.Vivern.Arpg.tileentity.ChestLock;
import com.Vivern.Arpg.tileentity.EnumChest;
import com.Vivern.Arpg.tileentity.TileDungeonLadder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
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
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextOverworld;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType;

public class DungeonChunkGenerator implements IChunkGenerator {
   protected static final IBlockState STONE = Blocks.STONE.getDefaultState();
   protected static final IBlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
   protected static final IBlockState WATER = Blocks.WATER.getDefaultState();
   protected static final IBlockState WATER_BORDER = Blocks.CLAY.getDefaultState();
   protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
   protected static final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
   protected static final IBlockState DEEP_STONE = BlocksRegister.DEEPROCK.getDefaultState();
   protected static final IBlockState CAVE_ONYX = BlocksRegister.CAVEONYX.getDefaultState();
   protected static final IBlockState GREEN_ONYX = BlocksRegister.GREENONYX.getDefaultState();
   protected static final IBlockState CALCITE = BlocksRegister.CALCITE.getDefaultState();
   protected static final IBlockState DOLERITE = BlocksRegister.DOLERITE.getDefaultState();
   protected static final IBlockState[] ONYX_CAVES_MATERIALS = new IBlockState[]{STONE, CALCITE, CAVE_ONYX, DEEP_STONE, CAVE_ONYX, CALCITE, GREEN_ONYX};
   protected List<IBlockState> onyx_list = new ArrayList<>();
   private final Random rand;
   private NoiseGeneratorOctaves minLimitPerlinNoise;
   private NoiseGeneratorOctaves maxLimitPerlinNoise;
   private NoiseGeneratorOctaves mainPerlinNoise;
   private NoiseGeneratorPerlin surfaceNoise;
   public NoiseGeneratorOctaves scaleNoise;
   public NoiseGeneratorOctaves depthNoise;
   public NoiseGeneratorOctaves forestNoise;
   public NoiseGeneratorPerlin perlin;
   public NoiseGeneratorPerlin perlin2;
   public NoiseGeneratorPerlin perlin3;
   public NoiseGeneratorPerlin perlin4;
   public NoiseGeneratorPerlin perlin5;
   private final World world;
   private final WorldType terrainType;
   private final double[] heightMap;
   private final float[] biomeWeights;
   private DungeonChunkGenSettings settings;
   private double[] depthBuffer = new double[256];
   private Biome[] biomesForGeneration;
   double[] mainNoiseRegion;
   double[] minLimitRegion;
   double[] maxLimitRegion;
   double[] depthRegion;
   private final NoiseGeneratorOctaves noiseGen4;
   private double[] stoneNoise;
   private MapGenBase caveGenerator;
   private MapGenBase caveSmallGenerator;
   private MapGenBase caveBigGenerator;
   private MapGenBase caveCleftsGenerator;
   private NoiseGenerator3D noisegenerator3d;
   private NoiseGenerator3D noisegenerator3dbiomes;
   public WorldGenAncientRuins wgAncientRuinsStone = new WorldGenAncientRuins(
      new IBlockState[]{
         Blocks.STONEBRICK.getDefaultState(),
         Blocks.STONEBRICK.getDefaultState(),
         Blocks.STONEBRICK.getStateFromMeta(1),
         Blocks.STONEBRICK.getStateFromMeta(2)
      },
      null,
      Blocks.STONEBRICK.getStateFromMeta(3),
      Blocks.STONE_SLAB.getStateFromMeta(5),
      Blocks.COBBLESTONE.getDefaultState(),
      15,
      40,
      1,
      5,
      2,
      6,
      3,
      12
   );
   public WorldGenAncientRuins wgAncientRuinsCobble = new WorldGenAncientRuins(
      new IBlockState[]{
         Blocks.COBBLESTONE.getDefaultState(),
         Blocks.COBBLESTONE.getDefaultState(),
         Blocks.MOSSY_COBBLESTONE.getDefaultState(),
         Blocks.STONE.getStateFromMeta(5),
         Blocks.STONEBRICK.getStateFromMeta(2)
      },
      null,
      Blocks.STONEBRICK.getStateFromMeta(3),
      Blocks.STONE_SLAB.getStateFromMeta(3),
      Blocks.STONE.getDefaultState(),
      15,
      30,
      1,
      4,
      2,
      5,
      2,
      6
   );
   public WorldGenAncientRuins wgAncientRuinsDolerite = new WorldGenAncientRuins(
      new IBlockState[]{BlocksRegister.DOLERITEBRICKS.getDefaultState()},
      BlocksRegister.DOLERITECOLUMN.getDefaultState(),
      BlocksRegister.DOLERITECOLUMN.getDefaultState(),
      BlocksRegister.DOLERITEPILASTER.getDefaultState(),
      BlocksRegister.DOLERITE.getDefaultState(),
      10,
      45,
      2,
      6,
      2,
      6,
      4,
      16
   );
   public CustomOreGenerator mithrilOreGen;
   public CustomOreGenerator adamantiumOreGen;

   public DungeonChunkGenerator(World worldIn, long seed) {
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
      this.caveGenerator = new DungeonCavesMapGen();
      this.caveSmallGenerator = new DungeonSmallCavesMapGen();
      this.caveBigGenerator = new DungeonBigCavesMapGen();
      this.caveCleftsGenerator = new DungeonCleftsMapGen();
      this.perlin = new NoiseGeneratorPerlin(new Random(worldIn.getSeed()), 3);
      this.perlin2 = new NoiseGeneratorPerlin(new Random(worldIn.getSeed() + 5L), 3);
      this.perlin3 = new NoiseGeneratorPerlin(new Random(worldIn.getSeed() + 11L), 3);
      this.perlin4 = new NoiseGeneratorPerlin(new Random(worldIn.getSeed() - 7L), 3);
      this.perlin5 = new NoiseGeneratorPerlin(new Random(worldIn.getSeed() - 1L), 3);
      this.noisegenerator3d = new NoiseGenerator3D(worldIn.getSeed());
      this.noisegenerator3dbiomes = new NoiseGenerator3D(worldIn.getSeed() + 5L);
      this.adamantiumOreGen = new CustomOreGenerator(
         BlocksRegister.OREADAMANTIUM.getDefaultState(), 102, BlockMatcher.forBlock(Blocks.STONE), 3, 8, 1, 6, 250
      );
      this.mithrilOreGen = new CustomOreGenerator(
         BlocksRegister.OREMITHRIL.getDefaultState(), 102, BlockMatcher.forBlock(Blocks.STONE), 2, 9, 1, 6, 250
      );
      Random onyxrandom = new Random(this.world.getSeed());

      for (int i = 0; i < 40; i++) {
         this.onyx_list.add(ONYX_CAVES_MATERIALS[onyxrandom.nextInt(7)]);
      }

      for (int i = -2; i <= 2; i++) {
         for (int j = -2; j <= 2; j++) {
            float f = 10.0F / MathHelper.sqrt(i * i + j * j + 0.2F);
            this.biomeWeights[i + 2 + (j + 2) * 5] = f;
         }
      }

      this.settings = new DungeonChunkGenSettings.Factory().build();
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
      this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
      this.replaceBiomeBlocks(x, z, chunkprimer, this.biomesForGeneration);
      Biome biome = this.world.getBiomeProvider().getBiome(new BlockPos(x * 16, 1, z * 16));
      if (biome == BiomesRegister.CALCITE_CLEFTS) {
         this.caveCleftsGenerator.generate(this.world, x, z, chunkprimer);
      } else {
         this.caveGenerator.generate(this.world, x, z, chunkprimer);
         this.caveSmallGenerator.generate(this.world, x, z, chunkprimer);
      }

      this.generateDeepRoadsCaves(this.world, this.rand, new BlockPos(x * 16, 0, z * 16), x, z, 0, 0, chunkprimer);
      this.generateDeepRoadsCaves(this.world, this.rand, new BlockPos(x * 16, 0, z * 16), x, z, 4444, 0, chunkprimer);
      int displacee = displacebySeed(this.world.getSeed());
      this.generateDeepRoadsCaves(this.world, this.rand, new BlockPos(x * 16, 0, z * 16), x, z, displacee, displacee, chunkprimer);
      this.generateDeepRoadsCaves(this.world, this.rand, new BlockPos(x * 16, 0, z * 16), x, z, displacee + 3570, displacee, chunkprimer);
      int displacee2 = displacebySeed2(this.world.getSeed());
      this.generateDeepRoadsWaterCaves(this.world, this.rand, new BlockPos(x * 16, 0, z * 16), x, z, displacee2, displacee2, chunkprimer, biome);
      this.generateDeepRoadsWaterCaves(this.world, this.rand, new BlockPos(x * 16, 0, z * 16), x, z, displacee2 + 5130, displacee2, chunkprimer, biome);
      int level = DimensionDungeon.getCaveRegionNumberFromCoord(DimensionDungeon.getCaveRegionCoords(new BlockPos(x * 16, 0, z * 16)));
      MineshaftGenerator2.onGenerateChunk(this.world, x, z, level, chunkprimer);
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

      int level = DimensionDungeon.getCaveRegionNumberFromCoord(DimensionDungeon.getCaveRegionCoords(blockpos));
      if (level > 1) {
         this.adamantiumOreGen.chancesToSpawn = Math.min(level + 6, 14);
         this.mithrilOreGen.chancesToSpawn = Math.min(level + 6, 14);
         this.adamantiumOreGen.generate(this.rand, x, z, this.world, this, null);
         this.mithrilOreGen.generate(this.rand, x, z, this.world, this, null);
      }

      if (this.rand.nextFloat() < 0.2F) {
         BlockPos posit = blockpos.add(this.rand.nextInt(16) + 8, 0, this.rand.nextInt(16) + 8);
         double value = this.perlin5.getValue(posit.getX() / 80.0, posit.getZ() / 80.0);
         if (value > 2.0) {
            List<BlockPos> listposes = new ArrayList<>();
            int height = 252;

            for (int rr = 10; rr < height; rr++) {
               BlockPos currentpos = posit.add(0, rr, 0);
               if (!this.world.isAirBlock(currentpos.down())
                  && this.world.isAirBlock(currentpos)
                  && this.world.isBlockFullCube(currentpos.down())) {
                  listposes.add(currentpos);
               }
            }

            if (!listposes.isEmpty()) {
               BlockPos finalpos = listposes.get(this.rand.nextInt(listposes.size()));
               if (finalpos != null) {
                  if ((biome != BiomesRegister.GLOWING_TUNNELS || !(this.rand.nextFloat() < 0.76)) && (level < 10 || !(this.rand.nextFloat() < 0.25))) {
                     if (this.rand.nextFloat() < 0.35) {
                        this.wgAncientRuinsCobble.generate(this.world, this.rand, finalpos);
                     } else {
                        this.wgAncientRuinsStone.generate(this.world, this.rand, finalpos);
                     }
                  } else {
                     this.wgAncientRuinsDolerite.generate(this.world, this.rand, finalpos);
                  }
               }
            }
         }
      }

      DimensionDungeon.generateDungeonDecor(this.world, new BlockPos(i, 0, j), this.rand, this.perlin5);
      biome.decorate(this.world, this.rand, new BlockPos(i, 0, j));
      BlockPos ladderDown = DimensionDungeon.getDownLadderPosInChunk(x, z, this.world);
      if (ladderDown != null) {
         genDownLadderCave(this.world, ladderDown, this.rand);
         EnumFacing fl = EnumFacing.HORIZONTALS[this.rand.nextInt(4)];
         EnumFacing flo = fl.getOpposite();
         IBlockState laddr = Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, fl);
         this.world.setBlockState(ladderDown, BlocksRegister.DUNGEONTRAVELFLOOR.getDefaultState(), 2);
         this.world.setBlockToAir(ladderDown.up());
         this.world.setBlockToAir(ladderDown.up(2));
         setupLadderTileEntity(this.world, ladderDown, fl, this.rand, false);

         for (int iy = 1; iy < 45; iy++) {
            BlockPos upp = ladderDown.up(iy);
            Block block = this.world.getBlockState(upp).getBlock();
            if (block != AIR.getBlock()) {
               break;
            }

            BlockPos uppoffs = upp.offset(flo);
            if (this.world.isAirBlock(uppoffs)) {
               this.world.setBlockState(uppoffs, BlocksRegister.ROTTENPLANKS.getDefaultState(), 2);
            }

            this.world.setBlockState(upp, laddr, 2);
         }
      }

      List<BlockPos> listUpladder = DimensionDungeon.getUpLadderPosesInChunk(x, z, this.world);
      if (!listUpladder.isEmpty()) {
         label276:
         for (BlockPos lpos : listUpladder) {
            EnumFacing fl = EnumFacing.HORIZONTALS[this.rand.nextInt(4)];
            EnumFacing flo = fl.getOpposite();
            genUpLadderCave(this.world, lpos, this.rand);
            this.world.setBlockState(lpos, BlocksRegister.DUNGEONTRAVELTOP.getDefaultState(), 2);
            this.world.setBlockToAir(lpos.down());
            this.world.setBlockToAir(lpos.down(2));
            setupLadderTileEntity(this.world, lpos, fl, this.rand, true);
            IBlockState laddr = Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, fl);

            for (int iy = 1; iy < 45; iy++) {
               BlockPos dnn = lpos.down(iy);
               if (!this.world.isAirBlock(dnn)) {
                  for (int iy2 = 1; iy2 < 45; iy2++) {
                     BlockPos dnn2 = lpos.down(iy + iy2);
                     if (this.world.isAirBlock(dnn)) {
                        for (int iy3 = 1; iy3 < 65; iy3++) {
                           BlockPos dnn3 = lpos.down(iy + iy3);
                           if (!this.world.isAirBlock(dnn) && iy3 > iy2) {
                              continue label276;
                           }

                           this.world.setBlockState(dnn.offset(flo), BlocksRegister.ROTTENPLANKS.getDefaultState(), 2);
                           this.world.setBlockState(dnn, laddr, 2);
                        }
                        continue label276;
                     }
                  }
                  break;
               }

               this.world.setBlockState(dnn.offset(flo), BlocksRegister.ROTTENPLANKS.getDefaultState(), 2);
               this.world.setBlockState(dnn, laddr, 2);
            }
         }
      }

      this.setDungeonChests(this.world, this.rand, new BlockPos(i, 0, j));
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

               if (this.world.getBiome(blockpos2) == BiomesRegister.ONYX_CAVES) {
                  int xx = blockpos.getX() + k2;
                  int zz = blockpos.getZ() + j3;

                  for (int rrx = 1; rrx < 255; rrx++) {
                     BlockPos finalpos = new BlockPos(xx, rrx, zz);
                     IBlockState statee = this.world.getBlockState(finalpos);
                     if (statee.getMaterial() == Material.ROCK && !statee.getBlock().hasTileEntity(statee)) {
                        double value = this.noisegenerator3dbiomes.getValue(xx / 50.1, rrx / 50.1, zz / 50.1);
                        float min = -0.2F;

                        for (int ll = 0; ll < 40; ll++) {
                           if (min < value && value < min + 0.03F) {
                              this.world.setBlockState(finalpos, this.onyx_list.get(ll));
                           }

                           min += 0.03F;
                        }
                     }
                  }
               }

               if (this.world.getBiome(blockpos2) == BiomesRegister.MAGIC_CRYSTAL_CAVES) {
                  int xx = blockpos.getX() + k2;
                  int zz = blockpos.getZ() + j3;

                  for (int rrxx = 1; rrxx < 255; rrxx++) {
                     if ((this.noisegenerator3dbiomes.getValue(xx / 25.1, rrxx / 25.1, zz / 25.1) + 1.0) * 2.0
                        > (this.rand.nextFloat() + this.rand.nextFloat()) * 8.0F) {
                        BlockPos finalpos = new BlockPos(xx, rrxx, zz);
                        if (this.world.isAirBlock(finalpos) && BlocksRegister.CAVECRYSTALS.canPlaceBlockAt(this.world, finalpos)) {
                           this.world
                              .setBlockState(
                                 finalpos,
                                 BlocksRegister.CAVECRYSTALS
                                    .getActualState(
                                       BlocksRegister.CAVECRYSTALS.getDefaultState().withProperty(CaveCrystal.TYPE, this.rand.nextInt(4)), this.world, finalpos
                                    )
                              );
                        }
                     }
                  }
               }

               if (this.world.getBiome(blockpos2) == BiomesRegister.GLOWING_TUNNELS) {
                  int xx = blockpos.getX() + k2;
                  int zz = blockpos.getZ() + j3;

                  for (int rrxxx = 1; rrxxx < 255; rrxxx++) {
                     BlockPos finalpos = new BlockPos(xx, rrxxx, zz);
                     if (this.world.getBlockState(finalpos).getMaterial() == Material.ROCK
                        && GetMOP.collidesWithBlock(this.world, finalpos, Blocks.AIR)) {
                        double value = this.noisegenerator3dbiomes.getValue(xx / 55.1, rrxxx / 55.1, zz / 55.1);
                        if (-0.01 < value && value < 0.01) {
                           this.world.setBlockState(finalpos, BlocksRegister.GLOWINGVEIN.getDefaultState());
                        }
                     }

                     if ((this.noisegenerator3dbiomes.getValue(xx / 25.1, rrxxx / 25.1, zz / 25.1) + 1.0) * 2.0
                           > (this.rand.nextFloat() + this.rand.nextFloat()) * 9.0F
                        && this.world.isAirBlock(finalpos)
                        && BlocksRegister.GLOWINGCAVECRYSTALS.canPlaceBlockAt(this.world, finalpos)) {
                        this.world
                           .setBlockState(
                              finalpos,
                              BlocksRegister.GLOWINGCAVECRYSTALS
                                 .getActualState(
                                    BlocksRegister.GLOWINGCAVECRYSTALS.getDefaultState().withProperty(GlowingCaveCrystal.TYPE, this.rand.nextInt(4)),
                                    this.world,
                                    finalpos
                                 )
                           );
                     }

                     if ((this.noisegenerator3d.getValue(xx / 20.1, rrxxx / 20.1, zz / 20.1) + 0.8) * 3.0
                           > (this.rand.nextFloat() + this.rand.nextFloat()) * 15.0F
                        && this.world.isAirBlock(finalpos)
                        && BlocksRegister.BLUEGLOWINGMUSH.canPlaceBlockAt(this.world, finalpos)) {
                        this.world.setBlockState(finalpos, BlueGlowingMushroom.getRandomStateWorldgen(this.world, finalpos));
                     }
                  }
               }

               if (this.world.getBiome(blockpos2) == BiomesRegister.CRYSTAL_CAVES) {
                  int xx = blockpos.getX() + k2;
                  int zz = blockpos.getZ() + j3;

                  for (int rrxxx = 1; rrxxx < 255; rrxxx++) {
                     if ((this.noisegenerator3dbiomes.getValue(xx / 25.1, rrxxx / 25.1, zz / 25.1) + 1.0) * 3.0
                        > (this.rand.nextFloat() + this.rand.nextFloat()) * 11.0F) {
                        BlockPos finalposx = new BlockPos(xx, rrxxx, zz);
                        if (this.world.isAirBlock(finalposx) && BlocksRegister.SELENITECRYSTALS.canPlaceBlockAt(this.world, finalposx)) {
                           this.world
                              .setBlockState(
                                 finalposx,
                                 BlocksRegister.SELENITECRYSTALS
                                    .getActualState(
                                       BlocksRegister.SELENITECRYSTALS.getDefaultState().withProperty(SeleniteCrystal.TYPE, this.rand.nextInt(4)),
                                       this.world,
                                       finalposx
                                    )
                              );
                        }
                     }
                  }
               }

               if (this.world.getBiome(blockpos2) == BiomesRegister.UNDERGROUND_LAKE) {
                  int xx = blockpos.getX() + k2;
                  int zz = blockpos.getZ() + j3;
                  int midheight = 100;
                  int waterlvl = -10;

                  for (int rrxxxx = 1; rrxxxx < 255; rrxxxx++) {
                     double raz = Math.abs(rrxxxx - midheight) / 30.0;
                     if (this.noisegenerator3dbiomes.getValue(xx / 40.1, rrxxxx / 45.1, zz / 40.1) + 1.0 > raz) {
                        BlockPos finalposx = new BlockPos(xx, rrxxxx, zz);
                        if (rrxxxx <= midheight + waterlvl) {
                           this.world.setBlockState(finalposx, Blocks.WATER.getDefaultState());
                        } else {
                           this.world.setBlockToAir(finalposx);
                        }
                     }
                  }
               }

               int xx = blockpos.getX() + k2;
               int zz = blockpos.getZ() + j3;
               int midheight = 100;
               int waterlvl = -10;

               for (int rrxxxxx = 1; rrxxxxx < 255; rrxxxxx++) {
                  BlockPos finalposx = new BlockPos(xx, rrxxxxx, zz);
                  if (this.world.getBlockState(finalposx).getBlock() == Blocks.ENDER_CHEST) {
                     GenerationHelper.setChestWithLoot(
                        this.world,
                        finalposx,
                        EnumChest.ROTTEN,
                        ListLootTable.CHESTS_DUNGEON_MINESHAFT,
                        (EnumFacing)this.world.getBlockState(finalposx).getValue(BlockEnderChest.FACING)
                     );
                  }
               }
            }
         }
      }

      ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, x, z, false);
      BlockFalling.fallInstantly = false;
   }

   public void generatePit(World worldIn, Random random, BlockPos pos, int x, int z, ChunkPrimer chunkprimer) {
      double ddd = 80.1;
      double ddd2 = 2.1;

      for (int ii = 0; ii < 16; ii++) {
         for (int ss = 0; ss < 16; ss++) {
            int fx = pos.getX() + ii;
            int fz = pos.getZ() + ss;
            double value = this.surfaceNoise.getValue(fx / ddd, fz / ddd);

            for (int rr = 2; rr < 253; rr++) {
               double finalvalue1 = value + this.perlin.getValue(fx / ddd, rr / ddd) + this.perlin.getValue(fz / ddd, rr / ddd);
               double finalvalue2 = value + this.perlin2.getValue(fx / ddd, rr / ddd) + this.perlin2.getValue(fz / ddd, rr / ddd);
               double finalvalue3 = value + this.perlin3.getValue(fx / ddd, rr / ddd) + this.perlin3.getValue(fz / ddd, rr / ddd);
               double finalvalue = this.noisegenerator3d.getValue(finalvalue1 / ddd2, finalvalue2 / ddd2, finalvalue3 / ddd2);
               if (finalvalue > 0.3) {
                  chunkprimer.setBlockState(ii, MathHelper.clamp(rr, 2, 253), ss, AIR);
               }
            }
         }
      }
   }

   public void generateDeepRoadsCaves(World worldIn, Random random, BlockPos pos, int x, int z, int displace, int displaceheight, ChunkPrimer chunkprimer) {
      for (int ii = 0; ii < 16; ii++) {
         for (int ss = 0; ss < 16; ss++) {
            int fx = pos.getX() + ii + 8 + displace;
            int fz = pos.getZ() + ss + 8 + displace;
            int valuescale = (int)Math.round(this.surfaceNoise.getValue((fx + 1234) / 280.1, (fz - 4321) / 280.1) * 1.5 + 7.5);
            double value2 = MathHelper.clamp(
               (
                     this.perlin2
                           .getValue((pos.getX() + ii + 8 + displaceheight) / 600.1, (pos.getZ() + ss + 8 + displaceheight) / 600.1)
                        + 5.0
                  )
                  * 23.0,
               15.0,
               230.0
            );
            double value = this.perlin.getValue(fx / 160.1, fz / 160.1);
            double value3 = this.perlin2.getValue(fx / 3.1, fz / 3.1);
            double value4stalacts = this.perlin4.getValue(fx / 40.1, fz / 40.1) / 2.5;
            double cutout1 = this.perlin3.getValue((fx + 455) / 160.1, (fz + 455) / 160.1);
            if (cutout1 > 2.0) {
               value += cutout1 - 2.0;
            }

            double valueabs = Math.sqrt(1.0 - (25.0 - valuescale * 1.4) * (value - 0.4) * (value - 0.4));
            int up = 13 + valuescale;
            int down = 4 + valuescale / 3;
            int rr1 = (int)Math.round(value2 - valueabs * down);

            for (int rr = rr1; rr < Math.round(value2 + valueabs * up); rr++) {
               int yabs = (int)Math.round(Math.abs(rr - value2));
               if (!(up - (value3 - (0.7 + valuescale / 9.0 + value4stalacts)) * down < yabs)) {
                  chunkprimer.setBlockState(ii, MathHelper.clamp(rr, 2, 253), ss, AIR);
               }
            }
         }
      }
   }

   public int getWaterLvlInCaveRegion(int x, int z) {
      return MathHelper.clamp(
         (int)Math.round((this.perlin4.getValue(DimensionDungeon.getCaveRegionXZCoord(x), DimensionDungeon.getCaveRegionXZCoord(z)) + 5.0) * 25.0), 2, 254
      );
   }

   public void generateDeepRoadsWaterCaves(
      World worldIn, Random random, BlockPos pos, int x, int z, int displace, int displaceheight, ChunkPrimer chunkprimer, Biome biome
   ) {
      for (int ii = 0; ii < 16; ii++) {
         for (int ss = 0; ss < 16; ss++) {
            int fx = pos.getX() + ii + 8 + displace;
            int fz = pos.getZ() + ss + 8 + displace;
            int waterlvl = this.getWaterLvlInCaveRegion(pos.getX(), pos.getZ());
            int valuescale = (int)Math.round(this.surfaceNoise.getValue((fx + 3217) / 280.1, (fz - 5643) / 280.1) * 1.5 + 7.5);
            double value2 = MathHelper.clamp(
               (
                        this.perlin
                              .getValue((pos.getX() + ii + 8 + displaceheight) / 600.1, (pos.getZ() + ss + 8 + displaceheight) / 600.1)
                           - 1.0
                     )
                     * 3.5
                  + waterlvl,
               15.0,
               230.0
            );
            double value = this.perlin2.getValue(fx / 220.1, fz / 220.1);
            double value3 = this.perlin3.getValue(fx / 3.1, fz / 3.1);
            double value4stalacts = this.perlin.getValue(fx / 40.1, fz / 40.1) / 2.5;
            double cutout1 = this.perlin4.getValue((fx + 555) / 160.1, (fz + 555) / 160.1);
            if (cutout1 > 2.0) {
               value += cutout1 - 2.0;
            }

            double valueabs = Math.sqrt(1.0 - (25.0 - valuescale * 1.4) * (value - 0.4) * (value - 0.4));
            int up = 13 + valuescale;
            int down = 4 + valuescale / 3;
            int rr1 = (int)Math.round(value2 - valueabs * down);
            int rr2 = (int)Math.round(value2 + valueabs * up);
            boolean iswater = false;

            for (int rr = rr1; rr < rr2; rr++) {
               int yabs = (int)Math.round(Math.abs(rr - value2));
               if (!(up - (value3 - (0.7 + valuescale / 9.0 + value4stalacts)) * down < yabs)) {
                  if (rr > waterlvl) {
                     chunkprimer.setBlockState(ii, MathHelper.clamp(rr, 2, 253), ss, AIR);
                  } else {
                     chunkprimer.setBlockState(ii, MathHelper.clamp(rr, 2, 253), ss, WATER);
                     iswater = true;
                  }
               }
            }

            if (iswater) {
               for (int rrx = rr1 - 1; rrx > rr1 - 5; rrx--) {
                  if (chunkprimer.getBlockState(ii, MathHelper.clamp(rrx, 2, 253), ss).getBlock() != Blocks.WATER) {
                     chunkprimer.setBlockState(ii, MathHelper.clamp(rrx, 2, 253), ss, biome.fillerBlock);
                  }
               }
            }

            double valueabs2 = Math.sqrt(2.0 - (25.0 - valuescale * 1.5) * (value - 0.4) * (value - 0.4));
            int up2 = 13 + valuescale;
            int down2 = 4 + valuescale;
            int rr1a = (int)Math.round(value2 - valueabs2 * down2);
            int rr2a = (int)Math.min(Math.round(value2 + valueabs2 * up2), (long)(waterlvl + 1));

            for (int rrxx = rr1a; rrxx < rr2a; rrxx++) {
               if (chunkprimer.getBlockState(ii, MathHelper.clamp(rrxx, 2, 253), ss).getBlock() != Blocks.WATER) {
                  chunkprimer.setBlockState(ii, MathHelper.clamp(rrxx, 2, 253), ss, biome.fillerBlock);
               }
            }
         }
      }
   }

   public static void setStalact(
      World worldIn, Random random, int x, int y, int z, int flatSamples, double size, int height, IBlockState stone, float doublePlaceChance
   ) {
      BlockPos startpos = null;
      List<BlockPos> poses = new ArrayList<>();
      Chunk chunk1 = worldIn.getChunk(x >> 4, z >> 4);

      while (y > 1) {
         if (chunk1.getBlockState(x, y, z).getBlock() == Blocks.AIR
            && chunk1.getBlockState(x, y + 1, z).getMaterial() == Material.ROCK) {
            startpos = new BlockPos(x, y, z);
            break;
         }

         y--;
      }

      if (y > 1) {
         while (y > 1) {
            if (chunk1.getBlockState(x, y, z).getBlock() == Blocks.AIR && chunk1.getBlockState(x, y - 1, z).isTopSolid()) {
               new BlockPos(x, y, z);
               break;
            }

            y--;
         }

         for (int rr = 0; rr < flatSamples; rr++) {
            BlockPos pos = startpos.add(Math.round(random.nextGaussian() * size), 0.0, Math.round(random.nextGaussian() * size));
            boolean ncontains = true;

            for (BlockPos cpos : poses) {
               if (cpos.getX() == pos.getX() && cpos.getY() == pos.getY() && cpos.getZ() == pos.getZ()) {
                  ncontains = false;
               }
            }

            if (ncontains) {
               poses.add(pos);
            }
         }

         for (BlockPos fpos : poses) {
            double dist = fpos.getDistance(startpos.getX(), startpos.getY(), startpos.getZ());
            int down = (int)Math.round(-Math.log(Math.abs(Math.pow(dist, height))));

            for (int ry = 0; ry < down; ry++) {
               BlockPos rypos = fpos.down(ry);
               worldIn.setBlockState(rypos, stone);
            }
         }
      }
   }

   public static void fillWaterCaves(World worldIn, Random random, BlockPos pos, EnumFacing prev, int samples) {
      if (samples > 0) {
         for (EnumFacing face1 : EnumFacing.VALUES) {
            if (face1 != EnumFacing.UP && face1 != prev) {
               BlockPos pos1 = pos.offset(face1);
               if (worldIn.isAirBlock(pos1)) {
                  worldIn.setBlockState(pos1, WATER);
                  fillWaterCaves(worldIn, random, pos1, face1.getOpposite(), --samples);
               }
            }
         }
      }
   }

   public void placeDeepRock(World worldIn, Random random, BlockPos pos) {
      for (int ii = 0; ii < 16; ii++) {
         for (int ss = 0; ss < 16; ss++) {
            for (int rr = 1; rr < 255; rr++) {
               BlockPos uppos = new BlockPos(pos.getX() + ii + 8, rr, pos.getZ() + ss + 8);
               if (this.noisegenerator3d.getValue(uppos.getX() / 40.1, uppos.getY() / 13.1, uppos.getZ() / 40.1) > 0.6) {
                  worldIn.setBlockState(uppos, DEEP_STONE);
               }
            }
         }
      }
   }

   public void setDungeonChests(World worldIn, Random random, BlockPos pos) {
      if (random.nextFloat() < 0.2) {
         int height = worldIn.getHeight(pos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8)).getY();
         List<BlockPos> listposes = new ArrayList<>();
         BlockPos posit = pos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8);

         for (int rr = 13; rr < height - 12; rr++) {
            BlockPos currentpos = posit.add(0, rr, 0);
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
   }

   public void worldDecorate(World worldIn, Random random, BlockPos pos) {
      for (int ii = 0; ii < 16; ii++) {
         for (int ss = 0; ss < 16; ss++) {
            for (int rr = 1; rr < 255; rr++) {
               BlockPos uppos = new BlockPos(pos.getX() + ii + 8, rr, pos.getZ() + ss + 8);
               if (this.noisegenerator3dbiomes.getValue(uppos.getX() / 60.1, uppos.getY() / 60.1, uppos.getZ() / 60.1)
                     > this.rand.nextFloat()
                  && this.world.isAirBlock(uppos)
                  && BlocksRegister.CAVECRYSTALS.canPlaceBlockAt(this.world, uppos)) {
                  this.world
                     .setBlockState(
                        uppos,
                        BlocksRegister.CAVECRYSTALS
                           .getActualState(BlocksRegister.CAVECRYSTALS.getDefaultState().withProperty(CaveCrystal.TYPE, this.rand.nextInt(4)), this.world, uppos)
                     );
               }
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
         new NoiseGeneratorPerlin(new Random(this.world.getSeed()), 3);

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
      new MutableBlockPos();

      for (int j1 = 255; j1 >= 0; j1--) {
         if (j1 != 0 && j1 != 255) {
            IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);
            if (iblockstate2.getMaterial() == Material.AIR) {
               j = -1;
            } else if (iblockstate2.getBlock() == STONE.getBlock()) {
               chunkPrimerIn.setBlockState(i1, j1, l, biome.fillerBlock);
               if (j == -1) {
                  if (k <= 0) {
                     iblockstate = AIR;
                     iblockstate1 = biome.fillerBlock;
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
                     iblockstate1 = biome.fillerBlock;
                     chunkPrimerIn.setBlockState(i1, j1, l, GRAVEL);
                  } else {
                     chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
                  }
               } else if (j > 0) {
                  j--;
                  chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
                  if (j == 0 && iblockstate1.getBlock() == Blocks.SAND) {
                     j = rand.nextInt(4);
                     iblockstate1 = biome.fillerBlock;
                  }
               }
            }
         } else {
            chunkPrimerIn.setBlockState(i1, j1, l, BEDROCK);
         }
      }
   }

   public static int displacebySeed(long seed) {
      return -1000L < seed && seed < 1000L ? (int)seed + 3000 : (int)seed;
   }

   public static int displacebySeed2(long seed) {
      return -1500L < seed && seed < 1500L ? (int)seed + 6500 : (int)seed;
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

   public static void genUpLadderCave(World world, BlockPos position, Random rand) {
      BlockPos pos = position.down(3);
      int length = 12 + rand.nextInt(16);
      int radiusMax = 5;
      int radiusMin = 3;
      Vec3d caveDirection = new Vec3d(0.0, -3.0, 0.0);

      for (int i = 0; i < length; i++) {
         int radius = i == 0 ? 3 : radiusMin + rand.nextInt(radiusMax - radiusMin + 1);
         int radiusSq = radius * radius;

         for (int xr = -radius; xr <= radius; xr++) {
            for (int yr = -radius; yr <= radius; yr++) {
               for (int zr = -radius; zr <= radius; zr++) {
                  int distSq = xr * xr + yr * yr + zr * zr;
                  if (distSq <= radiusSq) {
                     BlockPos fpos = new BlockPos(xr + pos.getX(), yr + pos.getY(), zr + pos.getZ());
                     IBlockState state = world.getBlockState(fpos);
                     if (!state.getMaterial().isLiquid() && canCaveReplaceBlock(state) && fpos.getY() != 254) {
                        world.setBlockState(fpos, AIR, 2);
                     }
                  }
               }
            }
         }

         double scaleFactor = radius / 2.0;
         caveDirection = caveDirection.add(
               rand.nextInt(4) - rand.nextInt(4), rand.nextInt(i > 10 ? 2 : 3) - rand.nextInt(4), rand.nextInt(4) - rand.nextInt(4)
            )
            .normalize()
            .scale(scaleFactor);
         pos = pos.add(caveDirection.x, caveDirection.y, caveDirection.z);
      }
   }

   public static void genDownLadderCave(World world, BlockPos position, Random rand) {
      BlockPos pos = position.up(3);
      int length = 12 + rand.nextInt(14);
      int radiusMax = 6;
      int radiusMin = 4;
      Vec3d caveDirection = new Vec3d(0.0, 4.0, 0.0);

      for (int i = 0; i < length; i++) {
         int radius = i == 0 ? 4 : radiusMin + rand.nextInt(radiusMax - radiusMin + 1);
         int radiusSq = radius * radius;
         int radiusSq2 = (radius - 1) * (radius - 1);

         for (int xr = -radius; xr <= radius; xr++) {
            for (int yr = -radius; yr <= radius; yr++) {
               for (int zr = -radius; zr <= radius; zr++) {
                  double distSq = xr * xr + yr * yr + zr * zr;
                  BlockPos fpos = new BlockPos(xr + pos.getX(), yr + pos.getY(), zr + pos.getZ());
                  IBlockState state = world.getBlockState(fpos);
                  if (distSq < radiusSq2 - 2 && !state.getMaterial().isLiquid() && canCaveReplaceBlock(state) && fpos.getY() != 1) {
                     world.setBlockState(fpos, AIR, 2);
                  }

                  if (distSq <= radiusSq && state.getMaterial() == Material.LAVA) {
                     if (distSq < radiusSq && rand.nextInt((int)Math.max(Math.round(Math.sqrt(Math.max(distSq, 1.0))), 0L)) == 0) {
                        world.setBlockState(fpos, AIR, 2);
                     } else {
                        world.setBlockState(fpos, DEEP_STONE, 2);
                     }
                  }
               }
            }
         }

         double scaleFactor = radius / 2.0;
         caveDirection = caveDirection.add(
               rand.nextInt(4) - rand.nextInt(4), rand.nextInt(4) - rand.nextInt(i > 6 ? 2 : 3), rand.nextInt(4) - rand.nextInt(4)
            )
            .normalize()
            .scale(scaleFactor);
         pos = pos.add(caveDirection.x, caveDirection.y, caveDirection.z);
      }
   }

   public static boolean canCaveReplaceBlock(IBlockState p_175793_1_) {
      if (p_175793_1_.getBlock() == Blocks.STONE) {
         return true;
      } else if (p_175793_1_.getBlock() == Blocks.DIRT) {
         return true;
      } else if (p_175793_1_.getBlock() == BlocksRegister.CALCITE) {
         return true;
      } else if (p_175793_1_.getBlock() == BlocksRegister.DEEPROCK) {
         return true;
      } else if (p_175793_1_.getBlock() == BlocksRegister.DOLERITE) {
         return true;
      } else if (p_175793_1_.getBlock() == BlocksRegister.GLOWINGVEIN) {
         return true;
      } else if (p_175793_1_.getBlock() == BlocksRegister.CAVEONYX) {
         return true;
      } else if (p_175793_1_.getBlock() == BlocksRegister.GREENONYX) {
         return true;
      } else if (p_175793_1_.getBlock() == Blocks.CLAY) {
         return true;
      } else {
         return p_175793_1_.getBlock() == Blocks.GRAVEL ? true : p_175793_1_.getBlock() instanceof BlockSpeleothem;
      }
   }

   public static void setupLadderTileEntity(World world, BlockPos pos, EnumFacing face, Random rand, boolean top) {
      TileEntity tile = world.getTileEntity(pos);
      if (tile != null && tile instanceof TileDungeonLadder) {
         BlockPos coords = DimensionDungeon.getCaveRegionCoords(pos);
         int cavenumber = DimensionDungeon.getCaveRegionNumberFromCoord(coords.getX(), coords.getZ());
         TileDungeonLadder ladder = (TileDungeonLadder)tile;
         ladder.face = face;
         Biome biome = world.getBiome(pos);
         int style = 0;
         if (biome == BiomesRegister.CALCITE_CLEFTS) {
            if (rand.nextInt(cavenumber) == 0) {
               style = 0;
               if (rand.nextFloat() < 0.8) {
                  ladder.laddermaterial = Blocks.LADDER;
               } else {
                  ladder.laddermaterial = Blocks.IRON_BARS;
               }
            } else if (rand.nextFloat() < 0.8 && rand.nextInt(Math.abs(cavenumber - DimensionDungeon.darkMinesLVL) + 1) == 0) {
               style = 2;
               if (rand.nextFloat() < 0.7 && rand.nextInt(cavenumber) == 0) {
                  ladder.laddermaterial = BlocksRegister.ROTTENPLANKS;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = Blocks.STONE;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = Blocks.COBBLESTONE;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = Blocks.OBSIDIAN;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = BlocksRegister.CALCITE;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = BlocksRegister.DOLERITE;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = BlocksRegister.DEEPROCK;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = BlocksRegister.CAVEONYX;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = BlocksRegister.GREENONYX;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = Blocks.HARDENED_CLAY;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = BlocksRegister.MAGICSTONE;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = BlocksRegister.SELENITE;
               } else {
                  ladder.laddermaterial = Blocks.BEDROCK;
               }
            } else {
               style = 1;
               if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = Blocks.STONE;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = Blocks.COBBLESTONE;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = Blocks.OBSIDIAN;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = BlocksRegister.CALCITE;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = BlocksRegister.DOLERITE;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = BlocksRegister.DEEPROCK;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = BlocksRegister.CAVEONYX;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = BlocksRegister.GREENONYX;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = BlocksRegister.MAGICSTONE;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = BlocksRegister.SELENITE;
               } else {
                  ladder.laddermaterial = Blocks.BEDROCK;
               }
            }

            if (rand.nextFloat() < 0.3) {
               ladder.blockmaterial = Blocks.BEDROCK;
            } else if (!(rand.nextFloat() < 0.8) || rand.nextInt(cavenumber) != 0 && rand.nextInt(cavenumber) != 0) {
               ladder.blockmaterial = BlocksRegister.CALCITE;
            } else {
               ladder.blockmaterial = BlocksRegister.ROTTENPLANKS;
            }
         }

         if (biome == BiomesRegister.ONYX_CAVES) {
            if (rand.nextInt(cavenumber) == 0) {
               style = 0;
               if (rand.nextFloat() < 0.9) {
                  ladder.laddermaterial = Blocks.LADDER;
               } else {
                  ladder.laddermaterial = Blocks.IRON_BARS;
               }
            } else if (rand.nextFloat() < 0.8 && rand.nextInt(Math.abs(cavenumber - DimensionDungeon.darkMinesLVL) + 1) == 0) {
               style = 2;
               if (rand.nextFloat() < 0.7 && rand.nextInt(cavenumber) == 0) {
                  ladder.laddermaterial = BlocksRegister.ROTTENPLANKS;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = Blocks.OBSIDIAN;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = BlocksRegister.CALCITE;
               } else if (rand.nextFloat() < 0.3) {
                  ladder.laddermaterial = BlocksRegister.DOLERITE;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = BlocksRegister.DEEPROCK;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = BlocksRegister.CAVEONYX;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = BlocksRegister.GREENONYX;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = BlocksRegister.MAGICSTONE;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = BlocksRegister.SELENITE;
               } else {
                  ladder.laddermaterial = Blocks.BEDROCK;
               }
            } else {
               style = 1;
               if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = Blocks.STONE;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = Blocks.COBBLESTONE;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = Blocks.OBSIDIAN;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = BlocksRegister.CALCITE;
               } else if (rand.nextFloat() < 0.3) {
                  ladder.laddermaterial = BlocksRegister.DOLERITE;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = BlocksRegister.DEEPROCK;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = BlocksRegister.CAVEONYX;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = BlocksRegister.GREENONYX;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = BlocksRegister.MAGICSTONE;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = BlocksRegister.SELENITE;
               } else {
                  ladder.laddermaterial = Blocks.BEDROCK;
               }
            }

            if (rand.nextFloat() < 0.1) {
               ladder.blockmaterial = Blocks.BEDROCK;
            } else if (rand.nextFloat() < 0.8 && rand.nextInt(cavenumber) < 4) {
               ladder.blockmaterial = BlocksRegister.ROTTENPLANKS;
            } else if (rand.nextFloat() < 0.3) {
               ladder.blockmaterial = BlocksRegister.CAVEONYX;
            } else if (rand.nextFloat() < 0.3) {
               ladder.blockmaterial = BlocksRegister.GREENONYX;
            } else if (rand.nextFloat() < 0.3) {
               ladder.blockmaterial = BlocksRegister.DEEPROCK;
            } else {
               ladder.blockmaterial = BlocksRegister.CALCITE;
            }
         }

         if (biome == BiomesRegister.CRYSTAL_CAVES) {
            if (rand.nextInt(cavenumber) == 0) {
               style = 0;
               if (rand.nextFloat() < 0.7) {
                  ladder.laddermaterial = Blocks.LADDER;
               } else {
                  ladder.laddermaterial = Blocks.IRON_BARS;
               }
            } else if (rand.nextFloat() < 0.8 && rand.nextInt(Math.abs(cavenumber - DimensionDungeon.darkMinesLVL) + 1) == 0) {
               style = 2;
               if (rand.nextFloat() < 0.7 && rand.nextInt(cavenumber) == 0) {
                  ladder.laddermaterial = BlocksRegister.ROTTENPLANKS;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = Blocks.OBSIDIAN;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = Blocks.BEDROCK;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = Blocks.STONE;
               } else if (rand.nextFloat() < 0.4) {
                  ladder.laddermaterial = BlocksRegister.SELENITECRYSTALS;
               } else {
                  ladder.laddermaterial = BlocksRegister.SELENITE;
               }
            } else {
               style = 1;
               if (rand.nextFloat() < 0.16) {
                  ladder.laddermaterial = Blocks.STONE;
               } else if (rand.nextFloat() < 0.5) {
                  ladder.laddermaterial = BlocksRegister.SELENITECRYSTALS;
               } else if (rand.nextFloat() < 0.16) {
                  ladder.laddermaterial = Blocks.COBBLESTONE;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = Blocks.OBSIDIAN;
               } else if (rand.nextFloat() < 0.35) {
                  ladder.laddermaterial = BlocksRegister.MAGICSTONE;
               } else if (rand.nextFloat() < 0.6) {
                  ladder.laddermaterial = BlocksRegister.SELENITE;
               } else {
                  ladder.laddermaterial = Blocks.BEDROCK;
               }
            }

            if (rand.nextFloat() < 0.1) {
               ladder.blockmaterial = Blocks.BEDROCK;
            } else if (rand.nextFloat() < 0.7 && rand.nextInt(cavenumber) < 4) {
               ladder.blockmaterial = BlocksRegister.ROTTENPLANKS;
            } else if (rand.nextFloat() < 0.3) {
               ladder.blockmaterial = BlocksRegister.SELENITE;
            } else if (rand.nextFloat() < 0.65) {
               ladder.blockmaterial = Blocks.STONE;
            } else {
               ladder.blockmaterial = BlocksRegister.DEEPROCK;
            }
         }

         if (biome == BiomesRegister.GLOWING_TUNNELS) {
            if (rand.nextInt(cavenumber) == 0) {
               style = 0;
               if (rand.nextFloat() < 0.9) {
                  ladder.laddermaterial = Blocks.LADDER;
               } else {
                  ladder.laddermaterial = Blocks.IRON_BARS;
               }
            } else if (rand.nextFloat() < 0.85 && rand.nextInt(Math.abs(cavenumber - DimensionDungeon.darkMinesLVL) + 1) == 0) {
               style = 2;
               if (rand.nextFloat() < 0.6 && rand.nextInt(cavenumber) == 0) {
                  ladder.laddermaterial = BlocksRegister.ROTTENPLANKS;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = Blocks.OBSIDIAN;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = BlocksRegister.SELENITE;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = BlocksRegister.DEEPROCK;
               } else if (rand.nextFloat() < 0.5) {
                  ladder.laddermaterial = BlocksRegister.DOLERITE;
               } else if (rand.nextFloat() < 0.35) {
                  ladder.laddermaterial = BlocksRegister.GLOWINGCAVECRYSTALS;
               } else {
                  ladder.laddermaterial = Blocks.BEDROCK;
               }
            } else {
               style = 1;
               if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = Blocks.STONE;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = BlocksRegister.SELENITECRYSTALS;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = BlocksRegister.DOLERITE;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = Blocks.OBSIDIAN;
               } else if (rand.nextFloat() < 0.75) {
                  ladder.laddermaterial = BlocksRegister.GLOWINGCAVECRYSTALS;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = BlocksRegister.SELENITE;
               } else {
                  ladder.laddermaterial = Blocks.BEDROCK;
               }
            }

            if (rand.nextFloat() < 0.1) {
               ladder.blockmaterial = Blocks.BEDROCK;
            } else if (rand.nextFloat() < 0.65 && rand.nextInt(cavenumber) < 4) {
               ladder.blockmaterial = BlocksRegister.ROTTENPLANKS;
            } else if (rand.nextFloat() < 0.8) {
               ladder.blockmaterial = BlocksRegister.DOLERITE;
            } else if (rand.nextFloat() < 0.15) {
               ladder.blockmaterial = Blocks.STONE;
            } else {
               ladder.blockmaterial = BlocksRegister.DEEPROCK;
            }
         }

         if (biome == BiomesRegister.TUNNELS || biome == BiomesRegister.UNDERGROUND_LAKE || biome == BiomesRegister.WATER_CAVES) {
            if (rand.nextInt(cavenumber) == 0) {
               style = 0;
               if (rand.nextFloat() < 0.7) {
                  ladder.laddermaterial = Blocks.LADDER;
               } else {
                  ladder.laddermaterial = Blocks.IRON_BARS;
               }
            } else if (rand.nextFloat() < 0.7 && rand.nextInt(Math.abs(cavenumber - DimensionDungeon.darkMinesLVL) + 1) == 0) {
               style = 2;
               if (rand.nextFloat() < 0.8 && rand.nextInt(cavenumber) == 0) {
                  ladder.laddermaterial = BlocksRegister.ROTTENPLANKS;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = Blocks.OBSIDIAN;
               } else if (rand.nextFloat() < 0.4) {
                  ladder.laddermaterial = Blocks.STONEBRICK;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = BlocksRegister.SELENITE;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = BlocksRegister.DEEPROCK;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = BlocksRegister.DOLERITE;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = BlocksRegister.SELENITECRYSTALS;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = BlocksRegister.CAVECRYSTALS;
               } else if (rand.nextFloat() < 0.05) {
                  ladder.laddermaterial = BlocksRegister.GLOWINGCAVECRYSTALS;
               } else {
                  ladder.laddermaterial = Blocks.BEDROCK;
               }
            } else {
               style = 1;
               if (rand.nextFloat() < 0.05) {
                  ladder.laddermaterial = Blocks.STONE;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = BlocksRegister.DOLERITE;
               } else if (rand.nextFloat() < 0.3) {
                  ladder.laddermaterial = BlocksRegister.MAGICSTONE;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = Blocks.OBSIDIAN;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = Blocks.HARDENED_CLAY;
               } else if (rand.nextFloat() < 0.3) {
                  ladder.laddermaterial = BlocksRegister.SELENITECRYSTALS;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = BlocksRegister.CAVECRYSTALS;
               } else if (rand.nextFloat() < 0.05) {
                  ladder.laddermaterial = BlocksRegister.GLOWINGCAVECRYSTALS;
               } else {
                  ladder.laddermaterial = Blocks.BEDROCK;
               }
            }

            if (rand.nextFloat() < 0.25) {
               ladder.blockmaterial = Blocks.BEDROCK;
            } else if (rand.nextFloat() < 0.85 && rand.nextInt(cavenumber) < 4) {
               ladder.blockmaterial = BlocksRegister.ROTTENPLANKS;
            } else if (rand.nextFloat() < 0.8) {
               ladder.blockmaterial = Blocks.STONE;
            } else if (rand.nextFloat() < 0.35) {
               ladder.blockmaterial = cavenumber < 40 ? Blocks.STONEBRICK : Blocks.COBBLESTONE;
            } else {
               ladder.blockmaterial = BlocksRegister.DEEPROCK;
            }
         }

         if (biome == BiomesRegister.MAGIC_CRYSTAL_CAVES) {
            if (rand.nextInt(cavenumber) == 0) {
               style = 0;
               if (rand.nextFloat() < 0.95) {
                  ladder.laddermaterial = Blocks.LADDER;
               } else {
                  ladder.laddermaterial = Blocks.IRON_BARS;
               }
            } else if (rand.nextFloat() < 0.6 && rand.nextInt(Math.abs(cavenumber - DimensionDungeon.darkMinesLVL) + 1) == 0) {
               style = 2;
               if (rand.nextFloat() < 0.8 && rand.nextInt(cavenumber) == 0) {
                  ladder.laddermaterial = BlocksRegister.ROTTENPLANKS;
               } else if (rand.nextFloat() < 0.2) {
                  ladder.laddermaterial = Blocks.OBSIDIAN;
               } else if (rand.nextFloat() < 0.3) {
                  ladder.laddermaterial = Blocks.STONEBRICK;
               } else if (rand.nextFloat() < 0.4) {
                  ladder.laddermaterial = BlocksRegister.MAGICSTONE;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = BlocksRegister.DOLERITE;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = BlocksRegister.SELENITECRYSTALS;
               } else if (rand.nextFloat() < 0.6) {
                  ladder.laddermaterial = BlocksRegister.CAVECRYSTALS;
               } else if (rand.nextFloat() < 0.05) {
                  ladder.laddermaterial = BlocksRegister.GLOWINGCAVECRYSTALS;
               } else {
                  ladder.laddermaterial = Blocks.BEDROCK;
               }
            } else {
               style = 1;
               if (rand.nextFloat() < 0.05) {
                  ladder.laddermaterial = Blocks.STONE;
               } else if (rand.nextFloat() < 0.5) {
                  ladder.laddermaterial = BlocksRegister.MAGICSTONE;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = Blocks.OBSIDIAN;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = BlocksRegister.SELENITECRYSTALS;
               } else if (rand.nextFloat() < 0.5) {
                  ladder.laddermaterial = BlocksRegister.CAVECRYSTALS;
               } else if (rand.nextFloat() < 0.1) {
                  ladder.laddermaterial = BlocksRegister.GLOWINGCAVECRYSTALS;
               } else {
                  ladder.laddermaterial = Blocks.BEDROCK;
               }
            }

            if (rand.nextFloat() < 0.1) {
               ladder.blockmaterial = Blocks.BEDROCK;
            } else if (rand.nextFloat() < 0.85 && rand.nextInt(cavenumber) < 4) {
               ladder.blockmaterial = BlocksRegister.ROTTENPLANKS;
            } else if (rand.nextFloat() < 0.8) {
               ladder.blockmaterial = Blocks.STONE;
            } else if (rand.nextFloat() < 0.35) {
               ladder.blockmaterial = cavenumber < 40 ? Blocks.STONEBRICK : Blocks.COBBLESTONE;
            } else if (rand.nextFloat() < 0.1) {
               ladder.blockmaterial = BlocksRegister.MAGICSTONE;
            } else {
               ladder.blockmaterial = BlocksRegister.DEEPROCK;
            }
         }

         ladder.ladderStyle = top ? style + 3 : style;
      }
   }

   public static void generateChestAndSpawner(World world, BlockPos pos, int size, Random rand) {
      boolean slimes = rand.nextFloat() < 0.36F;
      boolean barrels = rand.nextFloat() < 0.36F;
      world.setBlockState(pos, BlocksRegister.DOLERITEBRICKS.getDefaultState(), 2);

      for (int x = -size; x <= size; x++) {
         for (int z = -size; z <= size; z++) {
            if (rand.nextFloat() < 0.6F) {
               BlockPos poss = pos.add(x, 0, z);
               if (world.getBlockState(poss).isFullCube()) {
                  world.setBlockState(poss, (rand.nextFloat() < 0.1F ? BlocksRegister.DOLERITEPILASTER : BlocksRegister.DOLERITEBRICKS).getDefaultState(), 2);
                  if (slimes && rand.nextFloat() < 0.4F && world.isAirBlock(poss.up())) {
                     world.setBlockState(poss.up(), BlocksRegister.DOLERITECOLUMN.getDefaultState(), 2);
                  }

                  if (barrels && rand.nextFloat() < 0.08F && world.isAirBlock(poss.up())) {
                     world.setBlockState(poss.up(), BlocksRegister.CRYSTALVASE.getDefaultState(), 2);
                  }
               }
            }
         }
      }

      GenerationHelper.setChestWithLoot(
         world,
         pos.up(),
         EnumChest.CRYSTAL,
         ListLootTable.CHESTS_DUNGEON_DOLERITE_TROVE,
         EnumFacing.HORIZONTALS[rand.nextInt(4)],
         ChestLock.DOLERITE
      );
      world.setBlockState(pos.up(2), BlocksRegister.MOBSPAWNERANCIENT.getDefaultState(), 2);
      SpawnerTuners.DUNGEON_STRUCTURES.setupSpawner(world, pos.up(2), rand);
   }
}
