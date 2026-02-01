package com.vivern.arpg.dimensions.toxicomania;

import com.vivern.arpg.blocks.RustLamp;
import com.vivern.arpg.dimensions.generationutils.GenerationHelper;
import com.vivern.arpg.loot.ListLootTable;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.tileentity.TileARPGChest;
import java.util.HashMap;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class LabGenerator {
   public static IBlockState plating = BlocksRegister.LABPLATING.getDefaultState();
   public static IBlockState glass = Blocks.STAINED_GLASS.getStateFromMeta(0);
   public static IBlockState concrete = Blocks.CONCRETE.getStateFromMeta(0);
   public static IBlockState slab = Blocks.STONE_SLAB.getDefaultState();
   public static IBlockState slabUP = Blocks.STONE_SLAB.getStateFromMeta(8);

   public static void generateLab(World world, BlockPos pos, Random rand, float coloredGlassChance) {
      int xx = 8 + rand.nextInt(20);
      int zz = 8 + rand.nextInt(20);
      if (rand.nextFloat() < coloredGlassChance) {
         glass = Blocks.STAINED_GLASS.getStateFromMeta(rand.nextInt(16));
      } else {
         glass = Blocks.STAINED_GLASS.getStateFromMeta(0);
      }

      boolean stairsSideX = false;
      if (xx > zz) {
         stairsSideX = true;
      }

      int height = 4 + rand.nextInt(5);
      int roomcount = 4 + rand.nextInt(4);

      for (int i = 0; i < roomcount; i++) {
         makeBigSurfaceRoom(
            world,
            pos.up(i * height),
            xx,
            zz,
            height,
            2,
            Math.min(3 + rand.nextInt(4), height - 1),
            4 + rand.nextInt(4),
            1 + rand.nextInt(6),
            i == roomcount - 1,
            rand
         );
      }

      for (int i = 0; i < roomcount - 1; i++) {
         if (stairsSideX) {
            int counter = 0;

            for (int z = -zz + 3; z <= zz; z++) {
               counter++;
               BlockPos pos1 = pos.add((xx - 1) * (i % 2 == 0 ? 1 : -1), i * height + counter / 2, z);
               BlockPos pos2 = new BlockPos(pos.getX() + (xx - 2) * (i % 2 == 0 ? 1 : -1), pos1.getY(), pos1.getZ());
               world.setBlockToAir(new BlockPos(pos1.getX(), pos.getY() + (i + 1) * height, pos1.getZ()));
               world.setBlockToAir(new BlockPos(pos2.getX(), pos.getY() + (i + 1) * height, pos2.getZ()));
               if (counter % 2 == 0) {
                  world.setBlockState(pos1, slab);
                  world.setBlockState(pos2, slab);
               } else {
                  world.setBlockState(pos1, plating);
                  world.setBlockState(pos2, plating);
               }

               if (counter / 2 >= height) {
                  break;
               }
            }
         } else {
            int counter = 0;

            for (int x = -xx + 3; x <= xx; x++) {
               counter++;
               BlockPos pos1x = pos.add(x, i * height + counter / 2, (zz - 1) * (i % 2 == 0 ? 1 : -1));
               BlockPos pos2x = new BlockPos(pos1x.getX(), pos1x.getY(), pos.getZ() + (zz - 2) * (i % 2 == 0 ? 1 : -1));
               world.setBlockToAir(new BlockPos(pos1x.getX(), pos.getY() + (i + 1) * height, pos1x.getZ()));
               world.setBlockToAir(new BlockPos(pos2x.getX(), pos.getY() + (i + 1) * height, pos2x.getZ()));
               if (counter % 2 == 0) {
                  world.setBlockState(pos1x, slab);
                  world.setBlockState(pos2x, slab);
               } else {
                  world.setBlockState(pos1x, plating);
                  world.setBlockState(pos2x, plating);
               }

               if (counter / 2 >= height) {
                  break;
               }
            }
         }
      }

      for (EnumFacing facing : EnumFacing.HORIZONTALS) {
         int sizet = 2 + rand.nextInt(5);
         int xoffset = facing.getAxis() == Axis.Z ? rand.nextInt(xx - sizet) - rand.nextInt(xx - sizet) : 0;
         int zoffset = facing.getAxis() == Axis.X ? rand.nextInt(zz - sizet) - rand.nextInt(zz - sizet) : 0;
         int cont = 3 + rand.nextInt(4);
         if (cont % 2 == 0) {
            cont++;
         }

         makeSurfaceTunnel(
            world,
            pos.add(facing.getXOffset() * (xx - 1) + xoffset, 0, facing.getZOffset() * (zz - 1) + zoffset),
            facing,
            16 + rand.nextInt(10),
            2 + rand.nextInt(4),
            Math.min(sizet + 1 + rand.nextInt(3), height + 1),
            3 + rand.nextInt(4),
            rand,
            cont,
            facing.getOpposite()
         );
      }

      BlockPos stairsPos = pos.add(rand.nextInt(4) - rand.nextInt(4), -25, rand.nextInt(4) - rand.nextInt(4));
      placeStruct(world, stairsPos, rand, ":lab_stairs", 4, 0, rand.nextInt(4));
      EnumFacing facing = EnumFacing.HORIZONTALS[rand.nextInt(4)];
      int sizet = 2 + rand.nextInt(5);
      int cont = 5 + rand.nextInt(6);
      if (cont % 2 == 0) {
         cont++;
      }

      makeUndergroundTunnel(
         world,
         stairsPos.add(facing.getXOffset() * 3, 0, facing.getZOffset() * 3),
         facing,
         16 + rand.nextInt(10),
         2 + rand.nextInt(4),
         Math.min(sizet + 1 + rand.nextInt(3), height + 1),
         3 + rand.nextInt(4),
         rand,
         cont,
         facing.getOpposite()
      );
   }

   public static void makeBigSurfaceRoom(
      World world, BlockPos pos, int xsize, int zsize, int ysize, int windowMin, int windowMax, int windowFrequency, int windowSize, boolean roof, Random rand
   ) {
      HashMap<BlockPos, Integer> delayedDecorateFloor = new HashMap<>();

      for (int x = -xsize; x <= xsize; x++) {
         for (int y = 0; y <= ysize; y++) {
            for (int z = -zsize; z <= zsize; z++) {
               BlockPos fpos = pos.add(x, y, z);
               if (x != -xsize && x != xsize) {
                  if (z != -zsize && z != zsize) {
                     if (y == 0) {
                        world.setBlockState(fpos, concrete);
                        if (rand.nextFloat() < 0.01) {
                           int distFromWall = Math.min(Math.min(Math.abs(x - xsize), Math.abs(x + xsize)), Math.min(Math.abs(z - zsize), Math.abs(z + zsize)));
                           delayedDecorateFloor.put(fpos, distFromWall);
                        }
                     } else if (roof && y == ysize) {
                        world.setBlockState(fpos, concrete);
                        decorateRoof(world, fpos);
                     } else {
                        world.setBlockToAir(fpos);
                     }
                  } else if (y >= windowMin && y <= windowMax && (x + xsize) % windowFrequency <= windowSize) {
                     world.setBlockState(fpos, glass);
                  } else {
                     world.setBlockState(fpos, plating);
                  }
               } else if (y >= windowMin && y <= windowMax && (z + zsize) % windowFrequency <= windowSize) {
                  world.setBlockState(fpos, glass);
               } else {
                  world.setBlockState(fpos, plating);
               }
            }
         }
      }

      for (Entry<BlockPos, Integer> entry : delayedDecorateFloor.entrySet()) {
         decorateFloor(world, entry.getKey(), entry.getValue(), ysize, rand);
      }
   }

   public static void decorateRoof(World world, BlockPos pos) {
   }

   public static void decorateFloor(World world, BlockPos position, int distanceFromWall, int roomHeight, Random rand) {
      if (world.getBlockState(position).getBlock() == concrete.getBlock()) {
         position = position.up();
         if (rand.nextFloat() < 0.1 && distanceFromWall >= 5) {
            GenerationHelper.genBlocksHeap(world, position.up(roomHeight - 3), rand, 15, BlocksRegister.SCRAPELECTRONICS.getDefaultState());
         } else if (rand.nextFloat() < 0.08 && distanceFromWall >= 3) {
            world.setBlockState(position.add(2, 0, 2), BlocksRegister.BIOCELL.getDefaultState());
            world.setBlockState(position.add(-2, 0, 2), BlocksRegister.BIOCELL.getDefaultState());
            world.setBlockState(position.add(-2, 0, -2), BlocksRegister.BIOCELL.getDefaultState());
            world.setBlockState(position.add(2, 0, -2), BlocksRegister.BIOCELL.getDefaultState());
         } else if (rand.nextFloat() < 0.45 && distanceFromWall >= 3 && roomHeight >= 4) {
            placeStruct(world, position, rand, ":lab_decor_" + (rand.nextInt(3) + 6), 3, 0, rand.nextInt(4));
         } else if (rand.nextFloat() < 0.15 && distanceFromWall >= 4 && roomHeight >= 5) {
            placeStruct(world, position, rand, ":lab_decor_3", 4, 0, rand.nextInt(4));
         } else if (rand.nextFloat() < 0.15 && distanceFromWall >= 4) {
            placeStruct(world, position, rand, ":lab_decor_4", 4, 0, rand.nextInt(4));
         } else if (rand.nextFloat() < 0.15 && distanceFromWall >= 6 && roomHeight >= 3) {
            placeStruct(world, position, rand, ":lab_decor_5", 5, 0, rand.nextInt(4));
         } else if (rand.nextFloat() < 0.25) {
            IBlockState state = BlocksRegister.CHESTRUSTED.getDefaultState().withRotation(Rotation.values()[rand.nextInt(4)]);
            world.setBlockState(position, state);
            TileEntity tileentity = world.getTileEntity(position);
            if (tileentity instanceof TileARPGChest) {
               ((TileARPGChest)tileentity).setLootTable(ListLootTable.CHESTS_RUSTED_LAB, world.rand.nextLong());
            }
         }
      }
   }

   public static void decorateSurfaceRoomCenter(World world, BlockPos position, int roomRadius, Random rand) {
      if (rand.nextFloat() < 0.15 && roomRadius >= 3) {
         world.setBlockState(position.add(2, 0, 2), BlocksRegister.BIOCELL.getDefaultState());
         world.setBlockState(position.add(-2, 0, 2), BlocksRegister.BIOCELL.getDefaultState());
         world.setBlockState(position.add(-2, 0, -2), BlocksRegister.BIOCELL.getDefaultState());
         world.setBlockState(position.add(2, 0, -2), BlocksRegister.BIOCELL.getDefaultState());
      } else if (rand.nextFloat() < 0.45 && roomRadius >= 3) {
         placeStruct(world, position, rand, ":lab_decor_" + (rand.nextInt(3) + 6), 3, 0, rand.nextInt(4));
      } else if (rand.nextFloat() < 0.3 && roomRadius >= 4) {
         placeStruct(world, position, rand, ":lab_decor_" + (rand.nextInt(2) + 3), 4, 0, rand.nextInt(4));
      } else if (rand.nextFloat() < 0.15 && roomRadius >= 5) {
         placeStruct(world, position, rand, ":lab_decor_5", 5, 0, rand.nextInt(4));
      } else {
         IBlockState state = BlocksRegister.CHESTRUSTED.getDefaultState().withRotation(Rotation.values()[rand.nextInt(4)]);
         world.setBlockState(position, state);
         TileEntity tileentity = world.getTileEntity(position);
         if (tileentity instanceof TileARPGChest) {
            ((TileARPGChest)tileentity).setLootTable(ListLootTable.CHESTS_RUSTED_LAB, world.rand.nextLong());
         }
      }

      int columnRadius = Math.max(roomRadius / 2, 2);
      int xbound = position.getX() - columnRadius;
      int xbound2 = position.getX() + columnRadius;
      int zbound = position.getZ() - columnRadius;
      int zbound2 = position.getZ() + columnRadius;

      for (int x = xbound; x <= xbound2; x++) {
         for (int z = zbound; z <= zbound2; z++) {
            if (x != xbound && x != xbound2 || z != zbound && z != zbound2) {
               int notairblocks = 0;

               for (int y = position.getY() - 1; y > 30; y--) {
                  BlockPos posd = new BlockPos(x, y, z);
                  IBlockState st = world.getBlockState(posd);
                  if (!st.getBlock().isReplaceable(world, posd) && st.getMaterial() != Material.PLANTS) {
                     if (++notairblocks > 6) {
                        break;
                     }
                  } else {
                     notairblocks = 0;
                     world.setBlockState(posd, x != position.getX() && z != position.getZ() ? concrete : plating, 2);
                  }
               }
            }
         }
      }
   }

   public static void makeSurfaceTunnel(
      World world,
      BlockPos position,
      EnumFacing facing,
      int length,
      int size,
      int height,
      int lampFrequency,
      Random rand,
      int continueTunnel,
      EnumFacing disabledFacing
   ) {
      boolean axisX = facing.getAxis() == Axis.X;
      BlockPos pos = position.offset(facing);
      int lem2 = length - 2;

      for (int i = 0; i < length; i++) {
         boolean clmn = i % lampFrequency == 0;
         if (axisX) {
            for (int xz = -size; xz <= size; xz++) {
               for (int y = 0; y <= height; y++) {
                  int sheight = Math.round(height * 0.7F);
                  if (clmn && xz == 0 && y == height - 1) {
                     world.setBlockState(
                        pos.add(0, y, xz),
                        BlocksRegister.RUSTLAMP
                           .getDefaultState()
                           .withProperty(RustLamp.FACING, EnumFacing.DOWN)
                           .withProperty(RustLamp.ON, rand.nextFloat() < 0.25F)
                     );
                  } else if (y > sheight) {
                     int sheightY = y - sheight;
                     int maxsheightY = height - sheight;
                     if (y == height && Math.abs(xz) <= sheightY) {
                        if (clmn) {
                           world.setBlockState(pos.add(0, y, xz), plating);
                        } else {
                           world.setBlockState(pos.add(0, y, xz), glass);
                        }
                     } else if (xz > -size && xz < size && sheightY == Math.min(size - Math.abs(xz), maxsheightY)) {
                        world.setBlockState(pos.add(0, y, xz), glass);
                        if (clmn && y < height) {
                           world.setBlockState(pos.add(0, y + 1, xz), plating);
                        }
                     }
                  } else if (xz != -size && xz != size) {
                     if (y == 0) {
                        world.setBlockState(pos.add(0, y, xz), concrete);
                     } else {
                        world.setBlockToAir(pos.add(0, y, xz));
                     }
                  } else if (clmn && y == 2) {
                     world.setBlockState(pos.add(0, y, xz), glass);
                  } else {
                     world.setBlockState(pos.add(0, y, xz), plating);
                     if (clmn && y == sheight) {
                        world.setBlockState(pos.add(0, y + 1, xz), plating);
                     }
                  }
               }
            }
         } else {
            for (int xz = -size; xz <= size; xz++) {
               for (int yx = 0; yx <= height; yx++) {
                  int sheight = Math.round(height * 0.7F);
                  if (clmn && xz == 0 && yx == height - 1) {
                     world.setBlockState(
                        pos.add(xz, yx, 0),
                        BlocksRegister.RUSTLAMP
                           .getDefaultState()
                           .withProperty(RustLamp.FACING, EnumFacing.DOWN)
                           .withProperty(RustLamp.ON, rand.nextFloat() < 0.25F)
                     );
                  } else if (yx > sheight) {
                     int sheightY = yx - sheight;
                     int maxsheightY = height - sheight;
                     if (yx == height && Math.abs(xz) <= sheightY) {
                        if (clmn) {
                           world.setBlockState(pos.add(xz, yx, 0), plating);
                        } else {
                           world.setBlockState(pos.add(xz, yx, 0), glass);
                        }
                     } else if (xz > -size && xz < size && sheightY == Math.min(size - Math.abs(xz), maxsheightY)) {
                        world.setBlockState(pos.add(xz, yx, 0), glass);
                        if (clmn && yx < height) {
                           world.setBlockState(pos.add(xz, yx + 1, 0), plating);
                        }
                     }
                  } else if (xz != -size && xz != size) {
                     if (yx == 0) {
                        world.setBlockState(pos.add(xz, yx, 0), concrete);
                     } else {
                        world.setBlockToAir(pos.add(xz, yx, 0));
                     }
                  } else if (clmn && yx == 2) {
                     world.setBlockState(pos.add(xz, yx, 0), glass);
                  } else {
                     world.setBlockState(pos.add(xz, yx, 0), plating);
                     if (clmn && yx == sheight) {
                        world.setBlockState(pos.add(xz, yx + 1, 0), plating);
                     }
                  }
               }
            }
         }

         if (continueTunnel > 0 && i == lem2) {
            int rsize = Math.round(size * 1.5F) + rand.nextInt(4);
            int rheig = Math.round(height * 1.5F) + rand.nextInt(4);
            makeSurfaceRoom(world, pos.offset(facing, rsize + 1), rsize, rheig, rand, facing.getOpposite(), continueTunnel - 1, disabledFacing);
         }

         pos = pos.offset(facing);
      }
   }

   public static void makeSurfaceRoom(
      World world, BlockPos position, int size, int height, Random rand, EnumFacing entryFacing, int continueTunnel, EnumFacing disabledFacing
   ) {
      int height2 = height / 2;
      int sizeRound = (int)(size * 0.5F);

      for (int x = -size; x <= size; x++) {
         for (int y = 0; y <= height2; y++) {
            for (int z = -size; z <= size; z++) {
               BlockPos fpos = position.add(x, y, z);
               if (x != -size && x != size) {
                  if (x == -size + 1 || x == size - 1) {
                     int absz = Math.abs(z);
                     if (absz > sizeRound && absz < size - 1) {
                        world.setBlockState(fpos, plating);
                     }
                  } else if (z != -size && z != size) {
                     if (z == -size + 1 || z == size - 1) {
                        int absx = Math.abs(x);
                        if (absx > sizeRound && absx < size - 1) {
                           world.setBlockState(fpos, plating);
                        }
                     }
                  } else if (Math.abs(x) <= sizeRound) {
                     world.setBlockState(fpos, plating);
                  }
               } else if (Math.abs(z) <= sizeRound) {
                  world.setBlockState(fpos, plating);
               }

               if (y == 0) {
                  int absz = Math.abs(z);
                  int absx = Math.abs(x);
                  if (absz <= sizeRound) {
                     if (x > -size && x < size) {
                        world.setBlockState(fpos, concrete);
                     }
                  } else if (absx <= sizeRound) {
                     if (z > -size && z < size) {
                        world.setBlockState(fpos, concrete);
                     }
                  } else if (absz > sizeRound && absz < size - 1) {
                     if (x > -size + 1 && x < size - 1) {
                        world.setBlockState(fpos, concrete);
                     }
                  } else if (absx > sizeRound && absx < size - 1 && z > -size + 1 && z < size - 1) {
                     world.setBlockState(fpos, concrete);
                  }
               } else if (y == height2) {
                  int absz = Math.abs(z);
                  int absx = Math.abs(x);
                  if (absz <= sizeRound) {
                     if (x > -size && x < size) {
                        int dist = (int)Math.round(flatDistance(position, fpos));
                        BlockPos posup = fpos.up(MathHelper.clamp(size - dist, 1, height2));
                        world.setBlockState(posup, glass);
                        int iy = 1;

                        while (true) {
                           BlockPos posdown = posup.down(iy);
                           if (posdown.getY() <= position.getY()) {
                              break;
                           }

                           world.setBlockToAir(posdown);
                           iy++;
                        }
                     }
                  } else if (absx <= sizeRound) {
                     if (z > -size && z < size) {
                        int dist = (int)Math.round(flatDistance(position, fpos));
                        BlockPos posup = fpos.up(MathHelper.clamp(size - dist, 1, height2));
                        world.setBlockState(posup, glass);
                        int iy = 1;

                        while (true) {
                           BlockPos posdown = posup.down(iy);
                           if (posdown.getY() <= position.getY()) {
                              break;
                           }

                           world.setBlockToAir(posdown);
                           iy++;
                        }
                     }
                  } else if (absz > sizeRound && absz < size - 1) {
                     if (x > -size + 1 && x < size - 1) {
                        int dist = (int)Math.round(flatDistance(position, fpos));
                        BlockPos posup = fpos.up(MathHelper.clamp(size - dist, 1, height2));
                        world.setBlockState(posup, glass);
                        int iy = 1;

                        while (true) {
                           BlockPos posdown = posup.down(iy);
                           if (posdown.getY() <= position.getY()) {
                              break;
                           }

                           world.setBlockToAir(posdown);
                           iy++;
                        }
                     }
                  } else if (absx > sizeRound && absx < size - 1 && z > -size + 1 && z < size - 1) {
                     int dist = (int)Math.round(flatDistance(position, fpos));
                     BlockPos posup = fpos.up(MathHelper.clamp(size - dist, 1, height2));
                     world.setBlockState(posup, glass);
                     int iy = 1;

                     while (true) {
                        BlockPos posdown = posup.down(iy);
                        if (posdown.getY() <= position.getY()) {
                           break;
                        }

                        world.setBlockToAir(posdown);
                        iy++;
                     }
                  }
               }
            }
         }
      }

      if (continueTunnel > 0) {
         EnumFacing cfacing = rand.nextFloat() < 0.5 ? entryFacing.rotateY() : entryFacing.rotateYCCW();
         if (cfacing == disabledFacing) {
            cfacing = cfacing.getOpposite();
         }

         int rsize = Math.round(size / 1.5F) + rand.nextInt(2) - rand.nextInt(3);
         int rheig = Math.max(3, Math.round(height / 1.5F) + rand.nextInt(2) - rand.nextInt(3));
         makeSurfaceTunnel(
            world,
            position.offset(cfacing, size - 1),
            cfacing,
            8 + rand.nextInt(8),
            rsize,
            rheig,
            3 + rand.nextInt(4),
            rand,
            continueTunnel - 1,
            disabledFacing
         );
      }

      decorateSurfaceRoomCenter(world, position.up(), size - 1, rand);
   }

   public static void makeUndergroundTunnel(
      World world,
      BlockPos position,
      EnumFacing facing,
      int length,
      int size,
      int height,
      int lampFrequency,
      Random rand,
      int continueTunnel,
      EnumFacing disabledFacing
   ) {
      boolean axisX = facing.getAxis() == Axis.X;
      BlockPos pos = position.offset(facing);
      int lem2 = length - 2;

      for (int i = 0; i < length; i++) {
         boolean clmn = i % lampFrequency == 0;
         if (axisX) {
            for (int xz = -size; xz <= size; xz++) {
               for (int y = 0; y <= height; y++) {
                  int sheight = Math.round(height * 0.7F);
                  if (y > sheight) {
                     int sheightY = y - sheight;
                     int maxsheightY = height - sheight;
                     if (y == height && Math.abs(xz) <= sheightY) {
                        if (clmn) {
                           world.setBlockState(pos.add(0, y, xz), plating);
                           world.setBlockToAir(pos.add(0, y - 1, xz));
                        } else {
                           world.setBlockState(pos.add(0, y, xz), concrete);
                           world.setBlockToAir(pos.add(0, y - 1, xz));
                        }
                     } else if (xz > -size && xz < size && sheightY == Math.min(size - Math.abs(xz), maxsheightY)) {
                        world.setBlockState(pos.add(0, y, xz), concrete);
                        if (clmn && y < height) {
                           world.setBlockState(pos.add(0, y + 1, xz), plating);
                        }
                     }
                  } else if (xz != -size && xz != size) {
                     if (clmn && xz == -size + 1 && y == 3) {
                        world.setBlockState(
                           pos.add(0, y, xz),
                           BlocksRegister.RUSTLAMP
                              .getDefaultState()
                              .withProperty(RustLamp.FACING, EnumFacing.SOUTH)
                              .withProperty(RustLamp.ON, rand.nextFloat() < 0.25F)
                        );
                     } else if (clmn && xz == size - 1 && y == 3) {
                        world.setBlockState(
                           pos.add(0, y, xz),
                           BlocksRegister.RUSTLAMP
                              .getDefaultState()
                              .withProperty(RustLamp.FACING, EnumFacing.NORTH)
                              .withProperty(RustLamp.ON, rand.nextFloat() < 0.25F)
                        );
                     } else if (y == 0) {
                        if (xz == 0) {
                           world.setBlockState(pos, glass);
                           world.setBlockState(pos.down(1), BlocksRegister.RUSTEDPIPE.getDefaultState());
                           world.setBlockState(pos.down(2), glass);
                           if (rand.nextFloat() < 0.07F) {
                              world.setBlockState(pos.add(0, -1, 1), BlocksRegister.RUSTEDPIPE.getDefaultState());
                              world.setBlockState(pos.add(0, -2, 1), glass);
                           } else {
                              world.setBlockState(pos.add(0, -1, 1), glass);
                           }

                           if (rand.nextFloat() < 0.07F) {
                              world.setBlockState(pos.add(0, -1, -1), BlocksRegister.RUSTEDPIPE.getDefaultState());
                              world.setBlockState(pos.add(0, -2, -1), glass);
                           } else {
                              world.setBlockState(pos.add(0, -1, -1), glass);
                           }
                        } else {
                           world.setBlockState(pos.add(0, y, xz), concrete);
                        }
                     } else {
                        world.setBlockToAir(pos.add(0, y, xz));
                     }
                  } else {
                     world.setBlockState(pos.add(0, y, xz), plating);
                     if (clmn && y == sheight) {
                        world.setBlockState(pos.add(0, y + 1, xz), plating);
                     }
                  }
               }
            }
         } else {
            for (int xz = -size; xz <= size; xz++) {
               for (int yx = 0; yx <= height; yx++) {
                  int sheight = Math.round(height * 0.7F);
                  if (yx > sheight) {
                     int sheightY = yx - sheight;
                     int maxsheightY = height - sheight;
                     if (yx == height && Math.abs(xz) <= sheightY) {
                        if (clmn) {
                           world.setBlockState(pos.add(xz, yx, 0), plating);
                           world.setBlockToAir(pos.add(xz, yx - 1, 0));
                        } else {
                           world.setBlockState(pos.add(xz, yx, 0), concrete);
                           world.setBlockToAir(pos.add(xz, yx - 1, 0));
                        }
                     } else if (xz > -size && xz < size && sheightY == Math.min(size - Math.abs(xz), maxsheightY)) {
                        world.setBlockState(pos.add(xz, yx, 0), concrete);
                        if (clmn && yx < height) {
                           world.setBlockState(pos.add(xz, yx + 1, 0), plating);
                        }
                     }
                  } else if (xz != -size && xz != size) {
                     if (clmn && xz == -size + 1 && yx == 3) {
                        world.setBlockState(
                           pos.add(xz, yx, 0),
                           BlocksRegister.RUSTLAMP
                              .getDefaultState()
                              .withProperty(RustLamp.FACING, EnumFacing.EAST)
                              .withProperty(RustLamp.ON, rand.nextFloat() < 0.25F)
                        );
                     } else if (clmn && xz == size - 1 && yx == 3) {
                        world.setBlockState(
                           pos.add(xz, yx, 0),
                           BlocksRegister.RUSTLAMP
                              .getDefaultState()
                              .withProperty(RustLamp.FACING, EnumFacing.WEST)
                              .withProperty(RustLamp.ON, rand.nextFloat() < 0.25F)
                        );
                     } else if (yx == 0) {
                        if (xz == 0) {
                           world.setBlockState(pos, glass);
                           world.setBlockState(pos.down(1), BlocksRegister.RUSTEDPIPE.getDefaultState());
                           world.setBlockState(pos.down(2), glass);
                           if (rand.nextFloat() < 0.07F) {
                              world.setBlockState(pos.add(1, -1, 0), BlocksRegister.RUSTEDPIPE.getDefaultState());
                              world.setBlockState(pos.add(1, -2, 0), glass);
                           } else {
                              world.setBlockState(pos.add(1, -1, 0), glass);
                           }

                           if (rand.nextFloat() < 0.07F) {
                              world.setBlockState(pos.add(-1, -1, 0), BlocksRegister.RUSTEDPIPE.getDefaultState());
                              world.setBlockState(pos.add(-1, -2, 0), glass);
                           } else {
                              world.setBlockState(pos.add(-1, -1, 0), glass);
                           }
                        } else {
                           world.setBlockState(pos.add(xz, yx, 0), concrete);
                        }
                     } else {
                        world.setBlockToAir(pos.add(xz, yx, 0));
                     }
                  } else {
                     world.setBlockState(pos.add(xz, yx, 0), plating);
                     if (clmn && yx == sheight) {
                        world.setBlockState(pos.add(xz, yx + 1, 0), plating);
                     }
                  }
               }
            }
         }

         if (continueTunnel > 0 && i == lem2) {
            int rsize = Math.round(size * 1.5F) + rand.nextInt(4);
            int rheig = Math.round(height * 1.5F) + rand.nextInt(4);
            makeUndergroundRoom(world, pos.offset(facing, rsize + 1), rsize, rheig, rand, facing.getOpposite(), continueTunnel - 1, disabledFacing);
         }

         pos = pos.offset(facing);
      }
   }

   public static void makeUndergroundRoom(
      World world, BlockPos position, int size, int height, Random rand, EnumFacing entryFacing, int continueTunnel, EnumFacing disabledFacing
   ) {
      int height2 = height / 2;
      int sizeRound = (int)(size * 0.5F);

      for (int x = -size; x <= size; x++) {
         for (int y = 0; y <= height2; y++) {
            for (int z = -size; z <= size; z++) {
               BlockPos fpos = position.add(x, y, z);
               if (x != -size && x != size) {
                  if (x == -size + 1 || x == size - 1) {
                     int absz = Math.abs(z);
                     if (absz > sizeRound && absz < size - 1) {
                        world.setBlockState(fpos, plating);
                     }
                  } else if (z != -size && z != size) {
                     if (z == -size + 1 || z == size - 1) {
                        int absx = Math.abs(x);
                        if (absx > sizeRound && absx < size - 1) {
                           world.setBlockState(fpos, plating);
                        }
                     }
                  } else if (Math.abs(x) <= sizeRound) {
                     world.setBlockState(fpos, plating);
                  }
               } else if (Math.abs(z) <= sizeRound) {
                  world.setBlockState(fpos, plating);
               }

               if (y == 0) {
                  int absz = Math.abs(z);
                  int absx = Math.abs(x);
                  if (absz <= sizeRound) {
                     if (x > -size && x < size) {
                        world.setBlockState(fpos, concrete);
                     }
                  } else if (absx <= sizeRound) {
                     if (z > -size && z < size) {
                        world.setBlockState(fpos, concrete);
                     }
                  } else if (absz > sizeRound && absz < size - 1) {
                     if (x > -size + 1 && x < size - 1) {
                        world.setBlockState(fpos, concrete);
                     }
                  } else if (absx > sizeRound && absx < size - 1 && z > -size + 1 && z < size - 1) {
                     world.setBlockState(fpos, concrete);
                  }
               } else if (y == height2) {
                  int absz = Math.abs(z);
                  int absx = Math.abs(x);
                  if (absz <= sizeRound) {
                     if (x > -size && x < size) {
                        int dist = (int)Math.round(flatDistance(position, fpos));
                        BlockPos posup = fpos.up(MathHelper.clamp(size - dist, 1, height2));
                        world.setBlockState(posup, concrete);
                        int iy = 1;

                        while (true) {
                           BlockPos posdown = posup.down(iy);
                           if (posdown.getY() <= position.getY()) {
                              break;
                           }

                           world.setBlockToAir(posdown);
                           iy++;
                        }
                     }
                  } else if (absx <= sizeRound) {
                     if (z > -size && z < size) {
                        int dist = (int)Math.round(flatDistance(position, fpos));
                        BlockPos posup = fpos.up(MathHelper.clamp(size - dist, 1, height2));
                        world.setBlockState(posup, concrete);
                        int iy = 1;

                        while (true) {
                           BlockPos posdown = posup.down(iy);
                           if (posdown.getY() <= position.getY()) {
                              break;
                           }

                           world.setBlockToAir(posdown);
                           iy++;
                        }
                     }
                  } else if (absz > sizeRound && absz < size - 1) {
                     if (x > -size + 1 && x < size - 1) {
                        int dist = (int)Math.round(flatDistance(position, fpos));
                        BlockPos posup = fpos.up(MathHelper.clamp(size - dist, 1, height2));
                        world.setBlockState(posup, concrete);
                        int iy = 1;

                        while (true) {
                           BlockPos posdown = posup.down(iy);
                           if (posdown.getY() <= position.getY()) {
                              break;
                           }

                           world.setBlockToAir(posdown);
                           iy++;
                        }
                     }
                  } else if (absx > sizeRound && absx < size - 1 && z > -size + 1 && z < size - 1) {
                     int dist = (int)Math.round(flatDistance(position, fpos));
                     BlockPos posup = fpos.up(MathHelper.clamp(size - dist, 1, height2));
                     world.setBlockState(posup, concrete);
                     int iy = 1;

                     while (true) {
                        BlockPos posdown = posup.down(iy);
                        if (posdown.getY() <= position.getY()) {
                           break;
                        }

                        world.setBlockToAir(posdown);
                        iy++;
                     }
                  }
               }
            }
         }
      }

      if (rand.nextFloat() < 0.2) {
         placeStruct(world, position, rand, ":lab_decor_1", 4, -4, rand.nextInt(4));
      } else if (height2 >= 5 && rand.nextFloat() < 0.2) {
         placeStruct(world, position, rand, ":lab_decor_2", 4, -4, rand.nextInt(4));
      } else if ((rand.nextFloat() < 0.2 || continueTunnel == 0) && size >= 3) {
         world.setBlockState(position.add(2, 1, 2), BlocksRegister.BIOCELL.getDefaultState());
         world.setBlockState(position.add(-2, 1, 2), BlocksRegister.BIOCELL.getDefaultState());
         world.setBlockState(position.add(-2, 1, -2), BlocksRegister.BIOCELL.getDefaultState());
         world.setBlockState(position.add(2, 1, -2), BlocksRegister.BIOCELL.getDefaultState());
      }

      if (continueTunnel > 0) {
         EnumFacing cfacing = rand.nextFloat() < 0.5 ? entryFacing.rotateY() : entryFacing.rotateYCCW();
         if (cfacing == disabledFacing) {
            cfacing = cfacing.getOpposite();
         }

         int rsize = Math.round(size / 1.5F) + rand.nextInt(2) - rand.nextInt(3);
         int rheig = Math.max(3, Math.round(height / 1.5F) + rand.nextInt(2) - rand.nextInt(3));
         makeUndergroundTunnel(
            world,
            position.offset(cfacing, size - 1),
            cfacing,
            8 + rand.nextInt(8),
            rsize,
            rheig,
            3 + rand.nextInt(4),
            rand,
            continueTunnel - 1,
            disabledFacing
         );
      }
   }

   public static double flatDistance(BlockPos pos1, BlockPos pos2) {
      double d0 = pos1.getX() - pos2.getX();
      double d2 = pos1.getZ() - pos2.getZ();
      return Math.sqrt(d0 * d0 + d2 * d2);
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

      template.addBlocksToWorld(worldIn, pos.add(sx * displace, Yoffset, sz * displace), ChestReplacerToxic.instanceReplLab, settings, 2);
   }
}
