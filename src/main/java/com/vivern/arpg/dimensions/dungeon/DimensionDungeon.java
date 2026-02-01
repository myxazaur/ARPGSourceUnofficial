package com.vivern.arpg.dimensions.dungeon;

import com.vivern.arpg.main.BiomesRegister;
import com.vivern.arpg.main.DimensionsRegister;
import com.vivern.arpg.main.NamedInt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldProvider.WorldSleepResult;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class DimensionDungeon extends WorldProvider {
   public static int larvesLVL = 15;
   public static int darkElvesLVL = 25;
   public static int darkMinesLVL = 35;
   public static HashMap<EnumCaveDecorType, ArrayList<NamedInt>> decorationRegister = new HashMap<>();
   public static DecorReplacerDungeon replacerDungeon = new DecorReplacerDungeon();
   public static int REGION_SIZE = 131072;
   public static int ladderProbablity = 50;

   public static void registerAllDecors() {
      for (EnumCaveDecorType type : EnumCaveDecorType.values()) {
         decorationRegister.put(type, new ArrayList<>());
      }

      registerDecor(":cavedecor_deep_1", 0, EnumCaveDecorType.DEEP);
      registerDecor(":cavedecor_deep_2", 0, EnumCaveDecorType.DEEP);
      registerDecor(":cavedecor_deep_3", -2, EnumCaveDecorType.DEEP);
      registerDecor(":cavedecor_deep_4", 0, EnumCaveDecorType.DEEP);
      registerDecor(":cavedecor_mineshaft_1", -1, EnumCaveDecorType.MINESHAFT);
      registerDecor(":cavedecor_mineshaft_2", -3, EnumCaveDecorType.MINESHAFT);
      registerDecor(":cavedecor_mineshaft_3", -3, EnumCaveDecorType.MINESHAFT);
      registerDecor(":cavedecor_mineshaft_4", -3, EnumCaveDecorType.MINESHAFT);
      registerDecor(":cavedecor_mineshaft_5", -3, EnumCaveDecorType.MINESHAFT);
      registerDecor(":cavedecor_mineshaft_6", -3, EnumCaveDecorType.MINESHAFT);
      registerDecor(":cavedecor_mineshaft_7", -2, EnumCaveDecorType.MINESHAFT);
      registerDecor(":cavedecor_mineshaft_8", -2, EnumCaveDecorType.MINESHAFT);
      registerDecor(":cavedecor_mineshaft_9", 0, EnumCaveDecorType.MINESHAFT);
      registerDecor(":cavedecor_mineshaft_10", 0, EnumCaveDecorType.MINESHAFT);
      registerDecor(":cavedecor_mineshaft_11", -1, EnumCaveDecorType.MINESHAFT);
      registerDecor(":cavedecor_mineshaft_12", 0, EnumCaveDecorType.MINESHAFT);
      registerDecor(":cavedecor_mineshaft_13", -2, EnumCaveDecorType.MINESHAFT);
      registerDecor(":cavedecor_normal_1", -3, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_2", -5, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_3", -3, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_4", -3, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_5", -3, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_6", -1, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_7", -2, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_8", -1, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_9", -1, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_10", -2, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_11", -1, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_12", -2, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_13", -2, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_14", -1, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_15", -1, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_16", -3, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_17", -2, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_18", -2, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_19", -4, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_20", -1, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_21", -1, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_22", -2, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_23", -2, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_24", -3, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_25", -1, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_26", -2, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_27", 0, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_normal_28", -1, EnumCaveDecorType.NORMAL);
      registerDecor(":cavedecor_crystal_1", -3, EnumCaveDecorType.SELENITE);
      registerDecor(":cavedecor_crystal_2", -3, EnumCaveDecorType.SELENITE);
      registerDecor(":cavedecor_crystal_3", -3, EnumCaveDecorType.SELENITE);
      registerDecor(":cavedecor_crystal_4", -3, EnumCaveDecorType.SELENITE);
      registerDecor(":cavedecor_crystal_5", -5, EnumCaveDecorType.SELENITE);
      registerDecor(":cavedecor_crystal_6", 0, EnumCaveDecorType.SELENITE);
      registerDecor(":cavedecor_crystal_7", -1, EnumCaveDecorType.SELENITE);
      registerDecor(":cavedecor_crystal_8", -1, EnumCaveDecorType.SELENITE);
      registerDecor(":cavedecor_crystal_9", -1, EnumCaveDecorType.SELENITE);
      registerDecor(":cavedecor_crystal_10", 0, EnumCaveDecorType.SELENITE);
      registerDecor(":cavedecor_crystal_11", -1, EnumCaveDecorType.SELENITE);
      registerDecor(":cavedecor_crystal_12", 0, EnumCaveDecorType.SELENITE);
      registerDecor(":cavedecor_crystal_13", 0, EnumCaveDecorType.SELENITE);
      registerDecor(":cavedecor_crystal_14", 0, EnumCaveDecorType.SELENITE);
      registerDecor(":cavedecor_crystal_15", -1, EnumCaveDecorType.SELENITE);
      registerDecor(":cavedecor_crystal_16", -1, EnumCaveDecorType.SELENITE);
      registerDecor(":cavedecor_crystal_17", -1, EnumCaveDecorType.SELENITE);
      registerDecor(":cavedecor_crystal_18", 0, EnumCaveDecorType.SELENITE);
   }

   public static void registerDecor(String name, int offsetY, EnumCaveDecorType type) {
      ArrayList<NamedInt> list = decorationRegister.get(type);
      list.add(new NamedInt(name, offsetY));
   }

   public static void generateDungeonDecor(World worldIn, BlockPos pos, Random random, NoiseGeneratorPerlin perlin) {
      double value = perlin.getValue(pos.getX() / 80.0, pos.getZ() / 80.0);
      int val2 = (int)Math.round(value / 3.0);
      if (val2 > 0) {
         int max = random.nextInt(val2) + 1;
         int height = 252;

         for (int ii = 0; ii < max; ii++) {
            List<BlockPos> listposes = new ArrayList<>();
            BlockPos posit = pos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8);

            for (int rr = 10; rr < height; rr++) {
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
                  EnumCaveDecorType structure = EnumCaveDecorType.NORMAL;
                  Biome biome = worldIn.getBiome(finalpos);
                  if (biome == BiomesRegister.GLOWING_TUNNELS && random.nextFloat() < 0.93F) {
                     if (random.nextFloat() < 0.6F) {
                        structure = EnumCaveDecorType.GLOWING;
                     } else {
                        structure = EnumCaveDecorType.DEEP;
                     }
                  } else if (random.nextFloat() < 0.15F) {
                     structure = EnumCaveDecorType.MINESHAFT;
                  } else if (biome == BiomesRegister.MAGIC_CRYSTAL_CAVES && random.nextFloat() < 0.6F) {
                     structure = EnumCaveDecorType.MAGIC;
                  } else if (biome == BiomesRegister.CRYSTAL_CAVES && random.nextFloat() < 0.5F) {
                     structure = EnumCaveDecorType.SELENITE;
                  } else if (biome == BiomesRegister.CALCITE_CLEFTS && random.nextFloat() < 0.6F) {
                     structure = EnumCaveDecorType.CALCITE;
                  }

                  EnumCaveDecorType structureStyle = EnumCaveDecorType.NORMAL;
                  if (biome == BiomesRegister.CALCITE_CLEFTS) {
                     structureStyle = EnumCaveDecorType.CALCITE;
                  }

                  if (biome == BiomesRegister.GLOWING_TUNNELS) {
                     structureStyle = EnumCaveDecorType.GLOWING;
                  }

                  if (biome == BiomesRegister.MAGIC_CRYSTAL_CAVES) {
                     structureStyle = EnumCaveDecorType.MAGIC;
                  }

                  placeDungeonDecor(worldIn, finalpos, structure, structureStyle, random);
               }
            }
         }
      }
   }

   public static void placeDungeonDecor(
      World worldIn, BlockPos pos, EnumCaveDecorType structure, EnumCaveDecorType structureStyle, Random rand
   ) {
      if (structureStyle != null) {
         replacerDungeon.mode = structureStyle;
      }

      ArrayList<NamedInt> list;
      if (structure == EnumCaveDecorType.GLOWING) {
         list = decorationRegister.get(EnumCaveDecorType.SELENITE);
      } else if (structure == EnumCaveDecorType.MAGIC) {
         list = decorationRegister.get(EnumCaveDecorType.SELENITE);
      } else {
         list = decorationRegister.get(structure);
      }

      if (!list.isEmpty()) {
         replacerDungeon.randomizeOre(rand);
         NamedInt struct = list.get(rand.nextInt(list.size()));
         WorldServer worldServer = (WorldServer)worldIn;
         MinecraftServer minecraftServer = worldIn.getMinecraftServer();
         TemplateManager templateManager = worldServer.getStructureTemplateManager();
         Template template = templateManager.get(minecraftServer, new ResourceLocation("arpg" + struct.name));
         int displaceX = template.getSize().getX() / 2;
         int displaceZ = template.getSize().getZ() / 2;
         PlacementSettings settings = new PlacementSettings();
         int sx = -1;
         int sz = -1;
         int swr = rand.nextInt(4);
         if (swr == 0) {
            settings.setRotation(Rotation.CLOCKWISE_180);
            sx = 1;
            sz = 1;
         }

         if (swr == 1) {
            settings.setRotation(Rotation.CLOCKWISE_90);
            sx = 1;
            sz = -1;
         }

         if (swr == 2) {
            settings.setRotation(Rotation.NONE);
         }

         if (swr == 3) {
            settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
            sx = -1;
            sz = 1;
         }

         template.addBlocksToWorld(worldIn, pos.add(sx * displaceX, struct.value, sz * displaceZ), replacerDungeon, settings, 2);
      }
   }

   public DimensionType getDimensionType() {
      return DimensionsRegister.DUNGEON;
   }

   public IChunkGenerator createChunkGenerator() {
      return new DungeonChunkGenerator(this.world, this.world.getSeed());
   }

   protected void init() {
      this.hasSkyLight = true;
      this.biomeProvider = new BiomeProviderDungeon(this.world.getSeed());
   }

   public boolean isSurfaceWorld() {
      return false;
   }

   public boolean canBlockFreeze(BlockPos pos, boolean byWater) {
      return this.canBlockFreezes(this.world, pos, byWater);
   }

   public boolean canBlockFreezes(World world, BlockPos pos, boolean noWaterAdj) {
      Biome biome = world.getBiome(pos);
      float f = biome.getTemperature(pos);
      if (f >= 0.15F) {
         return false;
      } else {
         if (pos.getY() >= 0 && pos.getY() < 256 && world.getLightFor(EnumSkyBlock.BLOCK, pos) < 10) {
            IBlockState iblockstate1 = world.getBlockState(pos);
            Block block = iblockstate1.getBlock();
            if (block == Blocks.WATER || block == Blocks.FLOWING_WATER) {
               if (!noWaterAdj) {
                  return true;
               }

               return true;
            }
         }

         return false;
      }
   }

   public static int getCaveRegionXZCoord(int xorz) {
      return (xorz < 0 ? -1 : 0) + xorz / REGION_SIZE;
   }

   public static BlockPos getCaveRegionCoords(BlockPos pos) {
      int halfSize = REGION_SIZE / 2;
      return new BlockPos(getCaveRegionXZCoord(pos.getX() + halfSize), 0, getCaveRegionXZCoord(pos.getZ() + halfSize));
   }

   public static int getCaveRegionNumberFromCoord(BlockPos coords) {
      return getCaveRegionNumberFromCoord(coords.getX(), coords.getZ());
   }

   public static int getCaveRegionNumberFromCoord(int crX, int crZ) {
      int y = -crX;
      return (int)Math.round(
         Math.pow(Math.abs(Math.abs(crZ) - Math.abs(y)) + Math.abs(crZ) + Math.abs(y), 2.0)
            + Math.abs(crZ + y + 0.1) / (crZ + y + 0.1) * (Math.abs(Math.abs(crZ) - Math.abs(y)) + Math.abs(crZ) + Math.abs(y) + crZ - y)
            + 1.0
      );
   }

   public static BlockPos getCaveRegionCoordFromNumber(int number) {
      EnumFacing rotate = EnumFacing.EAST;
      int stepCount = 1;
      int rotateCount = 0;
      BlockPos coord = BlockPos.ORIGIN;
      int currentNum = 1;

      while (true) {
         coord = coord.offset(rotate, stepCount);
         currentNum += stepCount;
         if (currentNum >= number) {
            if (currentNum == number) {
               return coord;
            }

            return coord.offset(rotate.getOpposite(), currentNum - number);
         }

         rotate = rotate.rotateYCCW();
         if (++rotateCount >= 2) {
            rotateCount = 0;
            stepCount++;
         }
      }
   }

   public static Vec3d getRelativePosInRegion(double x, double y, double z, int crX, int crZ) {
      BlockPos center = getRegionCenterPos(crX, crZ);
      return new Vec3d(x - center.getX(), y - center.getY(), z - center.getZ());
   }

   public static BlockPos getRelativePosInRegion(BlockPos absolutepos, int crX, int crZ) {
      BlockPos center = getRegionCenterPos(crX, crZ);
      return new BlockPos(
         absolutepos.getX() - center.getX(),
         absolutepos.getY() - center.getY(),
         absolutepos.getZ() - center.getZ()
      );
   }

   public static BlockPos getRegionCenterPos(int crX, int crZ) {
      return new BlockPos(crX * REGION_SIZE, 0, crZ * REGION_SIZE);
   }

   @Nullable
   public static BlockPos getDownLadderPosInChunk(int x, int z, World world) {
      Random rand = new Random(world.getSeed());
      long k = rand.nextLong() / 2L * 2L + 1L;
      long l = rand.nextLong() / 2L * 2L + 1L;
      rand.setSeed(x * k + z * l ^ world.getSeed());
      if (rand.nextInt(ladderProbablity) == 0) {
         int i = x * 16;
         int j = z * 16;
         return new BlockPos(i + rand.nextInt(16), 1, j + rand.nextInt(16));
      } else {
         return null;
      }
   }

   public static List<BlockPos> getUpLadderPosesInChunk(int x, int z, World world) {
      List<BlockPos> list = new ArrayList<>();
      int i = x * 16;
      int j = z * 16;
      int halfSize = REGION_SIZE / 2;

      for (int xx = 0; xx < 16; xx++) {
         for (int zz = 0; zz < 16; zz++) {
            int fX = i + xx;
            int fZ = j + zz;
            int CRX = getCaveRegionXZCoord(fX + halfSize);
            int CRZ = getCaveRegionXZCoord(fZ + halfSize);
            int num = getCaveRegionNumberFromCoord(CRX, CRZ);
            if (num > 1) {
               BlockPos crCoordPrev = getCaveRegionCoordFromNumber(num - 1);
               Vec3d relative = getRelativePosInRegion(fX, 1.0, fZ, CRX, CRZ);
               BlockPos centerPrev = getRegionCenterPos(crCoordPrev.getX(), crCoordPrev.getZ());
               BlockPos checkPos = centerPrev.add(relative.x, relative.y, relative.z);
               BlockPos ladderPos = getDownLadderPosInChunk(checkPos.getX() >> 4, checkPos.getZ() >> 4, world);
               if (checkPos.equals(ladderPos)) {
                  list.add(new BlockPos(fX, 254, fZ));
               }
            }
         }
      }

      return list;
   }

   public int getRespawnDimension(EntityPlayerMP player) {
      BlockPos blockpos = player.getBedLocation(102);
      if (blockpos == null) {
         return 0;
      } else {
         BlockPos blockpos1 = EntityPlayer.getBedSpawnLocation(player.world, blockpos, player.isSpawnForced(102));
         return blockpos1 != null && blockpos1.getY() < 254 ? 102 : 0;
      }
   }

   public boolean canRespawnHere() {
      return false;
   }

   public WorldSleepResult canSleepAt(EntityPlayer player, BlockPos pos) {
      return WorldSleepResult.ALLOW;
   }

   public static enum EnumCaveDecorType {
      DEEP,
      NORMAL,
      MINESHAFT,
      WET,
      SELENITE,
      MAGIC,
      GLOWING,
      CALCITE;
   }
}
