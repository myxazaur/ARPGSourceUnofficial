//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.dimensions.toxicomania;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class BunkerGenerator {
   public int rotation = 0;
   public short structBaseConfiguration = 0;
   public boolean xplus = false;
   public boolean zplus = false;
   public boolean xminus = false;
   public boolean zminus = false;
   public boolean yplus = false;
   public boolean yminus = false;
   public boolean empty = false;

   public boolean isMediumSize() {
      return (this.structBaseConfiguration & 64) != 0;
   }

   public boolean isBigSize() {
      return (this.structBaseConfiguration & 128) != 0;
   }

   public boolean isLargeSize() {
      return this.isMediumSize() && this.isBigSize();
   }

   public boolean hasExitInSide(int side) {
      if (side == 0) {
         return this.xplus;
      } else if (side == 1) {
         return this.zplus;
      } else if (side == 2) {
         return this.xminus;
      } else if (side == 3) {
         return this.zminus;
      } else if (side == 4) {
         return this.yplus;
      } else {
         return side == 5 ? this.yminus : false;
      }
   }

   public int getXInSide(int basex, int side, int multiplier) {
      if (side == 0) {
         return basex + multiplier;
      } else {
         return side == 2 ? basex - multiplier : basex;
      }
   }

   public int getZInSide(int basez, int side, int multiplier) {
      if (side == 1) {
         return basez + multiplier;
      } else {
         return side == 3 ? basez - multiplier : basez;
      }
   }

   public int getYInSide(int basey, int side, int multiplier) {
      if (side == 4) {
         return basey + multiplier;
      } else {
         return side == 5 ? basey - multiplier : basey;
      }
   }

   public boolean isNonStairs() {
      return (this.structBaseConfiguration & 16) == 0 && (this.structBaseConfiguration & 32) == 0;
   }

   public void initSides() {
      this.xplus = (this.structBaseConfiguration & 1) != 0;
      this.zplus = (this.structBaseConfiguration & 2) != 0;
      this.xminus = (this.structBaseConfiguration & 4) != 0;
      this.zminus = (this.structBaseConfiguration & 8) != 0;
      this.yplus = (this.structBaseConfiguration & 16) != 0;
      this.yminus = (this.structBaseConfiguration & 32) != 0;
   }

   public void rotateTo(int rotation) {
      this.rotation = rotation;
      if (rotation != 0) {
         if (rotation == 1) {
            boolean xpls = this.xplus;
            this.xplus = this.zminus;
            this.zminus = this.xminus;
            this.xminus = this.zplus;
            this.zplus = xpls;
         }

         if (rotation == 2) {
            boolean xpls = this.xplus;
            this.xplus = this.xminus;
            this.xminus = xpls;
            boolean zpls = this.zplus;
            this.zplus = this.zminus;
            this.zminus = zpls;
         }

         if (rotation == 3) {
            boolean xpls = this.xplus;
            this.xplus = this.zplus;
            this.zplus = this.xminus;
            this.xminus = this.zminus;
            this.zminus = xpls;
         }
      }
   }

   public static void generateBunker(World world, BlockPos pos, int arraySizeXZ, int arraySizeY, int bunkerSize, Random rand) {
      BunkerGenerator[][][] mainArray = new BunkerGenerator[arraySizeXZ][arraySizeY][arraySizeXZ];
      BunkerGenerator start = new BunkerGenerator();
      start.structBaseConfiguration = 258;
      start.initSides();
      mainArray[arraySizeXZ / 2][arraySizeY - 1][arraySizeXZ / 2] = start;
      generateGroup(world, mainArray, arraySizeXZ, arraySizeY, bunkerSize, rand, true);
      replaceAddMedium(world, mainArray, arraySizeXZ, arraySizeY, rand);
      placeStructures(world, pos, mainArray, arraySizeXZ, arraySizeY, rand);
   }

   public static void generateGroup(
      World world, BunkerGenerator[][][] baseArray, int arraySizeXZ, int arraySizeY, int bunkerSize, Random rand, boolean needexit
   ) {
      for (int bs = 0; bs < bunkerSize; bs++) {
         for (int x = 0; x < arraySizeXZ; x++) {
            for (int y = 0; y < arraySizeY; y++) {
               for (int z = 0; z < arraySizeXZ; z++) {
                  if (baseArray[x][y][z] != null) {
                     BunkerGenerator gen = baseArray[x][y][z];
                     if (gen.structBaseConfiguration != 0 && !gen.empty) {
                        for (int i = 0; i < 4; i++) {
                           if (gen.hasExitInSide(i)) {
                              boolean genLarge = rand.nextFloat() < 0.1F;
                              boolean genLargeFailed = true;
                              if (genLarge) {
                                 int xx = gen.getXInSide(x, i, 2);
                                 int zz = gen.getZInSide(z, i, 2);
                                 if (xx >= 1
                                    && zz >= 1
                                    && xx < arraySizeXZ - 1
                                    && zz < arraySizeXZ - 1
                                    && (
                                       y == arraySizeY - 1
                                          || baseArray[xx][y + 1][zz] == null
                                             && baseArray[xx + 1][y + 1][zz] == null
                                             && baseArray[xx][y + 1][zz + 1] == null
                                             && baseArray[xx - 1][y + 1][zz] == null
                                             && baseArray[xx][y + 1][zz - 1] == null
                                             && baseArray[xx + 1][y + 1][zz + 1] == null
                                             && baseArray[xx - 1][y + 1][zz - 1] == null
                                             && baseArray[xx - 1][y + 1][zz + 1] == null
                                             && baseArray[xx + 1][y + 1][zz - 1] == null
                                    )
                                    && (baseArray[xx][y][zz] == null || baseArray[xx][y][zz].isNonStairs())
                                    && (baseArray[xx + 1][y][zz] == null || baseArray[xx + 1][y][zz].isNonStairs())
                                    && (baseArray[xx][y][zz + 1] == null || baseArray[xx][y][zz + 1].isNonStairs())
                                    && (baseArray[xx - 1][y][zz] == null || baseArray[xx - 1][y][zz].isNonStairs())
                                    && (baseArray[xx][y][zz - 1] == null || baseArray[xx][y][zz - 1].isNonStairs())
                                    && baseArray[xx + 1][y][zz + 1] == null
                                    && baseArray[xx - 1][y][zz - 1] == null
                                    && baseArray[xx - 1][y][zz + 1] == null
                                    && baseArray[xx + 1][y][zz - 1] == null) {
                                    BunkerGenerator newgenerator = new BunkerGenerator();
                                    newgenerator.structBaseConfiguration = configurationLarge(rand);
                                    newgenerator.initSides();
                                    newgenerator.rotateTo(rand.nextInt(4));
                                    baseArray[xx][y][zz] = newgenerator;
                                    BunkerGenerator newgeneratorNone = new BunkerGenerator();
                                    newgeneratorNone.empty = true;
                                    BunkerGenerator newgeneratorXp = new BunkerGenerator();
                                    newgeneratorXp.structBaseConfiguration = 1;
                                    newgeneratorXp.xplus = true;
                                    BunkerGenerator newgeneratorXm = new BunkerGenerator();
                                    newgeneratorXm.structBaseConfiguration = 4;
                                    newgeneratorXm.xminus = true;
                                    BunkerGenerator newgeneratorZp = new BunkerGenerator();
                                    newgeneratorZp.structBaseConfiguration = 2;
                                    newgeneratorZp.zplus = true;
                                    BunkerGenerator newgeneratorZm = new BunkerGenerator();
                                    newgeneratorZm.structBaseConfiguration = 8;
                                    newgeneratorZm.zminus = true;
                                    baseArray[xx + 1][y][zz + 1] = newgeneratorNone;
                                    baseArray[xx - 1][y][zz - 1] = newgeneratorNone;
                                    baseArray[xx - 1][y][zz + 1] = newgeneratorNone;
                                    baseArray[xx + 1][y][zz - 1] = newgeneratorNone;
                                    baseArray[xx + 1][y][zz] = newgeneratorXp;
                                    baseArray[xx - 1][y][zz] = newgeneratorXm;
                                    baseArray[xx][y][zz + 1] = newgeneratorZp;
                                    baseArray[xx][y][zz - 1] = newgeneratorZm;
                                    if (y < arraySizeY - 1) {
                                       baseArray[xx + 1][y + 1][zz + 1] = newgeneratorNone;
                                       baseArray[xx - 1][y + 1][zz - 1] = newgeneratorNone;
                                       baseArray[xx - 1][y + 1][zz + 1] = newgeneratorNone;
                                       baseArray[xx + 1][y + 1][zz - 1] = newgeneratorNone;
                                       baseArray[xx + 1][y + 1][zz] = newgeneratorNone;
                                       baseArray[xx - 1][y + 1][zz] = newgeneratorNone;
                                       baseArray[xx][y + 1][zz + 1] = newgeneratorNone;
                                       baseArray[xx][y + 1][zz - 1] = newgeneratorNone;
                                       baseArray[xx][y + 1][zz] = newgeneratorNone;
                                    }

                                    genLargeFailed = false;
                                 }
                              }

                              if (!genLarge || genLargeFailed) {
                                 int xx = gen.getXInSide(x, i, 1);
                                 int zz = gen.getZInSide(z, i, 1);
                                 if (xx >= 0 && zz >= 0 && xx < arraySizeXZ && zz < arraySizeXZ && baseArray[xx][y][zz] == null) {
                                    BunkerGenerator newgenerator = new BunkerGenerator();
                                    if (rand.nextFloat() < 0.25F && y - 1 >= 0 && baseArray[xx][y - 1][zz] == null) {
                                       newgenerator.structBaseConfiguration = topStairs(rand);
                                       newgenerator.initSides();
                                       boolean succ = false;
                                       int nside = GetMOP.next(i, 2, 4);
                                       if (!newgenerator.hasExitInSide(nside)) {
                                          for (int r = 1; r < 4; r++) {
                                             newgenerator.initSides();
                                             newgenerator.rotateTo(r);
                                             if (newgenerator.hasExitInSide(nside)) {
                                                succ = true;
                                                break;
                                             }
                                          }
                                       } else {
                                          succ = true;
                                       }

                                       if (succ) {
                                          baseArray[xx][y][zz] = newgenerator;
                                          BunkerGenerator newgenerator2 = new BunkerGenerator();
                                          newgenerator2.structBaseConfiguration = downStairs(rand);
                                          newgenerator2.initSides();
                                          newgenerator2.rotateTo(newgenerator.rotation);
                                          baseArray[xx][y - 1][zz] = newgenerator2;
                                       }
                                    } else {
                                       newgenerator.structBaseConfiguration = newStructBaseConfiguration(rand);
                                       newgenerator.initSides();
                                       boolean succx = false;
                                       int nsidex = GetMOP.next(i, 2, 4);
                                       if (!newgenerator.hasExitInSide(nsidex)) {
                                          for (int rx = 1; rx < 4; rx++) {
                                             newgenerator.initSides();
                                             newgenerator.rotateTo(rx);
                                             if (newgenerator.hasExitInSide(nsidex)) {
                                                succx = true;
                                                break;
                                             }
                                          }
                                       } else {
                                          succx = true;
                                       }

                                       if (succx) {
                                          baseArray[xx][y][zz] = newgenerator;
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static void placeStructures(World world, BlockPos basePos, BunkerGenerator[][][] array, int arraySizeXZ, int arraySizeY, Random rand) {
      int ymult = 7;

      for (int x = 0; x < arraySizeXZ; x++) {
         for (int y = 0; y < arraySizeY; y++) {
            for (int z = 0; z < arraySizeXZ; z++) {
               if (array[x][y][z] != null) {
                  BunkerGenerator gen = array[x][y][z];
                  if (gen.structBaseConfiguration != 0 && !gen.empty) {
                     if (gen.structBaseConfiguration == 15) {
                        placeStruct(world, basePos.add(x * 11, y * ymult, z * 11), rand, ":bunker_small_" + (rand.nextInt(3) + 1), 5, 0, gen.rotation);
                     } else if (gen.structBaseConfiguration == 10) {
                        placeStruct(
                           world,
                           basePos.add(x * 11, y * ymult, z * 11),
                           rand,
                           rand.nextFloat() < 0.5 ? ":bunker_small_4" : ":bunker_small_0",
                           5,
                           0,
                           gen.rotation
                        );
                     } else if (gen.structBaseConfiguration == 8) {
                        placeStruct(world, basePos.add(x * 11, y * ymult, z * 11), rand, ":bunker_small_5", 5, 0, gen.rotation);
                     } else if (gen.structBaseConfiguration == 40) {
                        placeStruct(world, basePos.add(x * 11, y * ymult, z * 11), rand, ":bunker_small_6", 5, 0, gen.rotation);
                     } else if (gen.structBaseConfiguration == 37) {
                        placeStruct(world, basePos.add(x * 11, y * ymult, z * 11), rand, ":bunker_small_7", 5, 0, gen.rotation);
                     } else if (gen.structBaseConfiguration == 29) {
                        placeStruct(world, basePos.add(x * 11, y * ymult, z * 11), rand, ":bunker_small_8", 5, 0, gen.rotation);
                     } else if (gen.structBaseConfiguration == 18) {
                        placeStruct(world, basePos.add(x * 11, y * ymult, z * 11), rand, ":bunker_small_9", 5, 0, gen.rotation);
                     } else if (gen.structBaseConfiguration == 79) {
                        placeStruct(
                           world,
                           basePos.add(x * 11 + 6, y * ymult, z * 11 + 6),
                           rand,
                           ":bunker_medium_" + (rand.nextInt(2) + 1),
                           11,
                           0,
                           gen.rotation
                        );
                     } else if (gen.structBaseConfiguration == 139) {
                        placeStruct(world, basePos.add(x * 11 + 6, y * ymult, z * 11 + 6), rand, ":bunker_big_1", 11, 0, gen.rotation);
                     } else if (gen.structBaseConfiguration == 207) {
                        placeStructLarge(
                           world, basePos.add(x * 11, y * ymult, z * 11), rand, ":bunker_large_" + (rand.nextInt(2) + 1), 15, 0, gen.rotation
                        );
                     } else if (gen.structBaseConfiguration == 258) {
                        BlockPos poss = basePos.add(x * 11, y * ymult, z * 11);
                        placeStruct(world, poss, rand, ":bunker_entry", 5, 0, gen.rotation);
                        poss = poss.up(7);

                        while (true) {
                           placeStruct(world, poss, rand, ":bunker_tube", 5, 0, gen.rotation);
                           poss = poss.up(8);
                           if (world.isAirBlock(poss.add(0, 4, -8))) {
                              placeStruct(world, poss, rand, ":bunker_door", 8, 0, 0);
                              break;
                           }

                           if (world.isAirBlock(poss.add(8, 4, 0))) {
                              placeStruct(world, poss, rand, ":bunker_door", 8, 0, 1);
                              break;
                           }

                           if (world.isAirBlock(poss.add(0, 4, 8))) {
                              placeStruct(world, poss, rand, ":bunker_door", 8, 0, 2);
                              break;
                           }

                           if (world.isAirBlock(poss.add(-8, 4, 0))) {
                              placeStruct(world, poss, rand, ":bunker_door", 8, 0, 3);
                              break;
                           }

                           if (world.isOutsideBuildHeight(poss)) {
                              break;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static void replaceAddMedium(World world, BunkerGenerator[][][] array, int arraySizeXZ, int arraySizeY, Random rand) {
      int exitsNeedOnSide = 1;

      for (int x = 0; x < arraySizeXZ - 1; x++) {
         for (int y = 0; y < arraySizeY - 1; y++) {
            for (int z = 0; z < arraySizeXZ - 1; z++) {
               if (array[x][y][z] == null && array[x + 1][y][z] == null && array[x][y][z + 1] == null && array[x + 1][y][z + 1] == null) {
                  int xplusm = 0;
                  int zplusm = 0;
                  int xminusm = 0;
                  int zminusm = 0;
                  BunkerGenerator gen = array[x][y][z];
                  boolean b1 = false;
                  if (x - 1 >= 0 && array[x - 1][y][z] != null && array[x - 1][y][z].xplus) {
                     xminusm++;
                  }

                  if (z - 1 >= 0 && array[x][y][z - 1] != null && array[x][y][z - 1].zplus) {
                     zminusm++;
                  }

                  if (x + 2 < arraySizeXZ && array[x + 2][y][z] != null && array[x + 2][y][z].xminus) {
                     xplusm++;
                  }

                  if (z + 2 < arraySizeXZ && array[x][y][z + 2] != null && array[x][y][z + 2].zminus) {
                     zplusm++;
                  }

                  if (x - 1 >= 0 && z + 1 < arraySizeXZ && array[x - 1][y][z + 1] != null && array[x - 1][y][z + 1].xplus) {
                     xminusm++;
                  }

                  if (z - 1 >= 0 && x + 1 < arraySizeXZ && array[x + 1][y][z - 1] != null && array[x + 1][y][z - 1].zplus) {
                     zminusm++;
                  }

                  if (x + 2 < arraySizeXZ && z + 1 < arraySizeXZ && array[x + 2][y][z + 1] != null && array[x + 2][y][z + 1].xminus) {
                     xplusm++;
                  }

                  if (z + 2 < arraySizeXZ && x + 1 < arraySizeXZ && array[x + 1][y][z + 2] != null && array[x + 1][y][z + 2].zminus) {
                     zplusm++;
                  }

                  BunkerGenerator newgenerator = new BunkerGenerator();
                  boolean big = y == arraySizeY - 1
                     || array[x][y + 1][z] == null && array[x + 1][y + 1][z] == null && array[x][y + 1][z + 1] == null && array[x + 1][y + 1][z + 1] == null;
                  newgenerator.structBaseConfiguration = big ? configurationBig(rand) : configurationMedium(rand);
                  newgenerator.initSides();
                  boolean succ = false;
                  if ((!newgenerator.xminus || xminusm < exitsNeedOnSide)
                     && (!newgenerator.xplus || xplusm < exitsNeedOnSide)
                     && (!newgenerator.zminus || zminusm < exitsNeedOnSide)
                     && (!newgenerator.zplus || zplusm < exitsNeedOnSide)) {
                     for (int r = 1; r < 4; r++) {
                        newgenerator.initSides();
                        newgenerator.rotateTo(r);
                        if (newgenerator.xminus && xminusm >= exitsNeedOnSide
                           || newgenerator.xplus && xplusm >= exitsNeedOnSide
                           || newgenerator.zminus && zminusm >= exitsNeedOnSide
                           || newgenerator.zplus && zplusm >= exitsNeedOnSide) {
                           succ = true;
                           break;
                        }
                     }
                  } else {
                     succ = true;
                  }

                  if (succ) {
                     array[x][y][z] = newgenerator;
                     BunkerGenerator newgenerator2 = new BunkerGenerator();
                     newgenerator2.empty = true;
                     array[x + 1][y][z] = newgenerator2;
                     array[x + 1][y][z + 1] = newgenerator2;
                     array[x][y][z + 1] = newgenerator2;
                     if (big && y < arraySizeY - 1) {
                        array[x][y + 1][z] = newgenerator2;
                        array[x + 1][y + 1][z] = newgenerator2;
                        array[x + 1][y + 1][z + 1] = newgenerator2;
                        array[x][y + 1][z + 1] = newgenerator2;
                     }
                  }
               }
            }
         }
      }
   }

   public static short newStructBaseConfiguration(Random rand) {
      int randint = rand.nextInt(5) + 1;
      if (randint < 4) {
         return 15;
      } else if (randint == 4) {
         return 10;
      } else {
         return (short)(randint == 5 ? 8 : 0);
      }
   }

   public static short topStairs(Random rand) {
      int randint = rand.nextInt(2) + 1;
      if (randint == 1) {
         return 40;
      } else {
         return (short)(randint == 2 ? 37 : 0);
      }
   }

   public static short downStairs(Random rand) {
      int randint = rand.nextInt(2) + 1;
      if (randint == 1) {
         return 29;
      } else {
         return (short)(randint == 2 ? 18 : 0);
      }
   }

   public static short configurationMedium(Random rand) {
      return 79;
   }

   public static short configurationBig(Random rand) {
      return 139;
   }

   public static short configurationLarge(Random rand) {
      return 207;
   }

   public static BlockPos digTonnel(EnumFacing direction, World world, BlockPos start, int length) {
      boolean b = direction.getAxis() == Axis.X;
      IBlockState air = Blocks.AIR.getDefaultState();
      IBlockState walls = Blocks.STONE.getStateFromMeta(6);
      IBlockState column = BlocksRegister.RUSTMETALL.getDefaultState();

      for (int i = 0; i < length; i++) {
         for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
               for (int z = -2; z <= 2; z++) {
                  boolean yb = y == -2 || y == 2;
                  if (!b || z != -2 && z != 2 && !yb) {
                     if (!b && (x == -2 || x == 2 || yb)) {
                        if (z == 0) {
                           world.setBlockState(start.add(x, y, z), column);
                        } else {
                           world.setBlockState(start.add(x, y, z), walls);
                        }
                     } else {
                        world.setBlockState(start.add(x, y, z), air);
                     }
                  } else if (x == 0) {
                     world.setBlockState(start.add(x, y, z), column);
                  } else {
                     world.setBlockState(start.add(x, y, z), walls);
                  }
               }
            }
         }

         start = start.offset(direction, 5);
      }

      return start;
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

      template.addBlocksToWorld(worldIn, pos.add(sx * displace, Yoffset, sz * displace), ChestReplacerToxic.instanceReplBunker, settings, 2);
   }

   public static void placeStructLarge(World worldIn, BlockPos pos, Random rand, String structure, int displace, int Yoffset, int rotation) {
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

      template.addBlocksToWorld(worldIn, pos.add(sx * displace, Yoffset, sz * displace), ChestReplacerToxic.instanceReplBunker, settings, 2);
      boolean rand1 = rand.nextFloat() < 0.7;
      int darkMinimal = rand.nextInt(10);
      int darkMaximum = rand.nextInt(4) + darkMinimal;
      int rustMinimal = rand.nextInt(11);
      int rustMaximum = rand.nextInt(3) + rustMinimal;
      IBlockState column = rand.nextFloat() < 0.5F
         ? BlocksRegister.RUSTMETALL.getDefaultState()
         : (rand.nextFloat() < 0.5F ? BlocksRegister.DARKRUSTMETALL.getDefaultState() : Blocks.STONE.getStateFromMeta(6));
      IBlockState slab = Blocks.DOUBLE_STONE_SLAB.getStateFromMeta(8);
      BlockPos pos2 = pos.add(-16, 0, -16);

      for (int x = 0; x < 33; x++) {
         for (int z = 0; z < 33; z++) {
            if (x == 0 || x == 32 || z == 0 || z == 32) {
               for (int y = 0; y < 14; y++) {
                  IBlockState state = null;
                  if (x != 11 && x != 12 && x != 20 && x != 21 && z != 11 && z != 12 && z != 20 && z != 21) {
                     if (y <= 0 || y > 3 || (x < 15 || x > 17) && (z < 15 || z > 17)) {
                        if (rand1 && y < 2) {
                           state = BlocksRegister.RUSTMETALL.getDefaultState();
                        } else if (y >= darkMinimal && y <= darkMaximum) {
                           state = BlocksRegister.DARKRUSTMETALL.getDefaultState();
                        } else if (y >= rustMinimal && y <= rustMaximum) {
                           state = BlocksRegister.RUSTMETALL.getDefaultState();
                        } else {
                           state = slab;
                        }
                     } else {
                        state = Blocks.AIR.getDefaultState();
                     }
                  } else {
                     state = column;
                  }

                  worldIn.setBlockState(pos2.add(x, y, z), state);
               }
            }
         }
      }
   }
}
