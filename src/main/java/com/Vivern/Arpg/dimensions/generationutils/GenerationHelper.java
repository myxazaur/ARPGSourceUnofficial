package com.Vivern.Arpg.dimensions.generationutils;

import com.Vivern.Arpg.blocks.BlockSpeleothem;
import com.Vivern.Arpg.dimensions.ethernalfrost.ChestReplacersFrozen;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.tileentity.ChestLock;
import com.Vivern.Arpg.tileentity.EnumChest;
import com.Vivern.Arpg.tileentity.TileARPGChest;
import com.google.common.base.Predicate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration.Type;

public class GenerationHelper {
   static List<EnumFacing> HORIZONTALS = Arrays.asList(EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.EAST);

   public static Random newRandomFromCoords(long x, long z) {
      return new Random(x * 231896917442L + z * 542813726771L);
   }

   public static boolean isReplaceable(World world, BlockPos pos) {
      return world.getBlockState(pos).getBlock().isReplaceable(world, pos);
   }

   public static void placeStructMound(World worldIn, BlockPos pos, Random rand, String structure, int displace, int Yoffset, int rotation) {
      placeStruct(worldIn, pos, rand, structure, displace, Yoffset, rotation, ChestReplacersFrozen.replacerMound);
   }

   public static void placeStruct(
      World worldIn, BlockPos pos, Random rand, String structure, int displace, int Yoffset, int rotation, @Nullable ITemplateProcessor templateProcessor
   ) {
      WorldServer worldServer = (WorldServer)worldIn;
      MinecraftServer minecraftServer = worldIn.getMinecraftServer();
      TemplateManager templateManager = worldServer.getStructureTemplateManager();
      Template template = templateManager.get(minecraftServer, new ResourceLocation("arpg" + structure));
      PlacementSettings settings = new PlacementSettings();
      int sx = -1;
      int sz = -1;
      if (rotation == 0) {
         settings.setRotation(Rotation.CLOCKWISE_180);
         sx = 1;
         sz = 1;
      }

      if (rotation == 1) {
         settings.setRotation(Rotation.CLOCKWISE_90);
         sx = 1;
         sz = -1;
      }

      if (rotation == 2) {
         settings.setRotation(Rotation.NONE);
      }

      if (rotation == 3) {
         settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
         sx = -1;
         sz = 1;
      }

      template.addBlocksToWorld(worldIn, pos.add(sx * displace, Yoffset, sz * displace), templateProcessor, settings, 2);
   }

   public static void placeStruct(
      WorldServer worldServer, WorldGenAdvanced worldIn, BlockPos pos, Random rand, String structure, int displace, int Yoffset, int rotation
   ) {
      MinecraftServer minecraftServer = worldServer.getMinecraftServer();
      TemplateManager templateManager = worldServer.getStructureTemplateManager();
      Template template = templateManager.get(minecraftServer, new ResourceLocation("arpg" + structure));
      PlacementSettings settings = new PlacementSettings();
      int sx = -1;
      int sz = -1;
      if (rotation == 0) {
         settings.setRotation(Rotation.CLOCKWISE_180);
         sx = 1;
         sz = 1;
      }

      if (rotation == 1) {
         settings.setRotation(Rotation.CLOCKWISE_90);
         sx = 1;
         sz = -1;
      }

      if (rotation == 2) {
         settings.setRotation(Rotation.NONE);
      }

      if (rotation == 3) {
         settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
         sx = -1;
         sz = 1;
      }

      Template2 template2 = new Template2(template);
      template2.addBlocksToWorldGenAdvanced(worldIn, pos.add(sx * displace, Yoffset, sz * displace), settings, 2);
   }

   public static int isDungeonAtChunkPos(int x, int z, long worldseed, int dungeonProbablity) {
      Random rand = new Random(worldseed);
      long k = rand.nextLong() / 2L * 2L + 1L;
      long l = rand.nextLong() / 2L * 2L + 1L;
      rand.setSeed(x * k + z * l ^ worldseed);
      int priority = rand.nextInt();
      return rand.nextInt(dungeonProbablity) == 0 ? priority : -1;
   }

   public static boolean canGenerateDungeonAtChunkPos(int x, int z, long worldseed, int dungeonProbablity, int checkRadius) {
      int higher = isDungeonAtChunkPos(x, z, worldseed, dungeonProbablity);
      if (higher == -1) {
         return false;
      } else {
         for (int xx = x - checkRadius; xx <= x + checkRadius; xx++) {
            for (int zz = z - checkRadius; zz <= z + checkRadius; zz++) {
               if (isDungeonAtChunkPos(xx, zz, worldseed, dungeonProbablity) > higher) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   @Nullable
   public static BlockPos getSomeNearDungeon(int x, int z, long worldseed, int dungeonProbablity, int generatorCheckRadius, int nearRadius) {
      for (int xx = x - nearRadius; xx <= x + nearRadius; xx++) {
         for (int zz = z - nearRadius; zz <= z + nearRadius; zz++) {
            if (canGenerateDungeonAtChunkPos(xx, zz, worldseed, dungeonProbablity, generatorCheckRadius)) {
               return new BlockPos(xx * 16 + 8, 64, zz * 16 + 8);
            }
         }
      }

      return null;
   }

   public static float getDungeonValue(int chunkx, int chunkz, long seed, int voidRadius) {
      Random thischunkRand = getChunkRandom(chunkx, chunkz, seed);
      float thischunkPriority = thischunkRand.nextFloat();

      for (int xx = chunkx - voidRadius; xx <= chunkx + voidRadius; xx++) {
         for (int zz = chunkz - voidRadius; zz <= chunkz + voidRadius; zz++) {
            if (xx != chunkx || zz != chunkz) {
               Random chunkRand = getChunkRandom(xx, zz, seed);
               float chunkPriority = chunkRand.nextFloat();
               if (chunkPriority > thischunkPriority) {
                  return -1.0F;
               }
            }
         }
      }

      return thischunkRand.nextFloat();
   }

   private static Random getChunkRandom(int chunkx, int chunkz, long seed) {
      Random rand = new Random(seed);
      long k = rand.nextLong() / 2L * 2L + 1L;
      long l = rand.nextLong() / 2L * 2L + 1L;
      rand.setSeed(chunkx * k + chunkz * l ^ seed);
      return rand;
   }

   public static void generateSpeleothemsAtChunk(
      World world, BlockPos pos, Block blockstalact, Random rand, int startheight, float mainchance, float floorSmallThemsChance, float floorBigThemsChance
   ) {
      for (int iix = 0; iix < 16; iix++) {
         for (int iiz = 0; iiz < 16; iiz++) {
            if (rand.nextFloat() < mainchance) {
               int px = iix + 8;
               int pz = iiz + 8;
               boolean lastblockStone = false;
               int prevstate = -1;
               IBlockState stateup = null;
               int startY = startheight < 0 ? world.getTopSolidOrLiquidBlock(pos.add(px, 0, pz)).getY() - 1 : startheight;

               for (int yy = startY; yy > 5; yy--) {
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
                     if (state.isSideSolid(world, fpos, EnumFacing.UP)
                        && stateup != null
                        && (stateup.getBlock() == blockstalact || stateup.getBlock() == Blocks.AIR)) {
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
                           if (rand.nextFloat() < floorBigThemsChance) {
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
                           } else if (rand.nextFloat() < floorSmallThemsChance) {
                              if (rand.nextFloat() < 0.85) {
                                 world.setBlockState(fpos.up(), blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 6), 2);
                              } else {
                                 world.setBlockState(fpos.up(), blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 8), 2);
                              }
                           }
                        }
                     }

                     if (state.isSideSolid(world, fpos, EnumFacing.DOWN)) {
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

   public static void setIfReplaciableOregen(World world, BlockPos pos, IBlockState state, int flags, Predicate<IBlockState> target) {
      IBlockState state2 = world.getBlockState(pos);
      if (state2.getBlock().isReplaceableOreGen(state2, world, pos, target)) {
         world.setBlockState(pos, state, flags);
      }
   }

   public static void setChestWithLoot(World world, BlockPos pos, EnumChest enumChest, ResourceLocation lootTable, EnumFacing rotation, ChestLock... locks) {
      Block block = enumChest.getBlock();
      world.setBlockState(pos, block.getDefaultState());
      TileEntity tileentity = world.getTileEntity(pos);
      if (tileentity instanceof TileARPGChest) {
         TileARPGChest arpgChest = (TileARPGChest)tileentity;
         arpgChest.setChestFacing(rotation);
         Random newrand = GetMOP.getBestRandomBasedOnCoordinates(pos.getX(), pos.getY(), pos.getZ());
         arpgChest.setLootTable(lootTable, newrand.nextLong());
         if (locks.length > 0) {
            for (ChestLock chestLock : locks) {
               arpgChest.lockOrUnlockWith(chestLock, true);
            }
         }
      }
   }

   public static void setChestWithMap(World world, BlockPos pos, EnumChest enumChest, BlockPos mapTreasurePosition, EnumFacing rotation, ChestLock... locks) {
      Block block = enumChest.getBlock();
      world.setBlockState(pos, block.getDefaultState());
      TileEntity tileentity = world.getTileEntity(pos);
      if (tileentity instanceof TileARPGChest) {
         TileARPGChest arpgChest = (TileARPGChest)tileentity;
         arpgChest.setChestFacing(rotation);
         ItemStack itemstack = ItemMap.setupNewMap(world, mapTreasurePosition.getX(), mapTreasurePosition.getZ(), (byte)2, true, true);
         ItemMap.renderBiomePreviewMap(world, itemstack);
         MapData.addTargetDecoration(itemstack, mapTreasurePosition, "x", Type.TARGET_X);
         arpgChest.setInventorySlotContents(13, itemstack);
         if (locks.length > 0) {
            for (ChestLock chestLock : locks) {
               arpgChest.lockOrUnlockWith(chestLock, true);
            }
         }
      }
   }

   public static void setChestWithItemStack(World world, BlockPos pos, EnumChest enumChest, ItemStack stack, EnumFacing rotation, ChestLock... locks) {
      Block block = enumChest.getBlock();
      world.setBlockState(pos, block.getDefaultState());
      TileEntity tileentity = world.getTileEntity(pos);
      if (tileentity instanceof TileARPGChest) {
         TileARPGChest arpgChest = (TileARPGChest)tileentity;
         arpgChest.setChestFacing(rotation);
         arpgChest.setInventorySlotContents(13, stack);
         if (locks.length > 0) {
            for (ChestLock chestLock : locks) {
               arpgChest.lockOrUnlockWith(chestLock, true);
            }
         }
      }
   }

   public static List<EnumFacing> shuffledHORIZONTALS() {
      Collections.shuffle(HORIZONTALS);
      return HORIZONTALS;
   }

   public static void genBlocksHeap(World world, BlockPos pos, Random rand, int attempts, IBlockState blockstate) {
      for (int i = 0; i < attempts; i++) {
         BlockPos fpos = GetMOP.getTrueHeight(world, pos.add(rand.nextGaussian(), 0.0, rand.nextGaussian())).up();
         if (world.getBlockState(fpos).getBlock() == Blocks.AIR) {
            world.setBlockState(fpos, blockstate);
         }
      }
   }
}
