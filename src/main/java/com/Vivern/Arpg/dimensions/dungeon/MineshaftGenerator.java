package com.Vivern.Arpg.dimensions.dungeon;

import com.Vivern.Arpg.dimensions.generationutils.FractalGenerator;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class MineshaftGenerator extends FractalGenerator {
   public MineshaftGenerator(int checkRadius, NoiseGeneratorPerlin perlin) {
      super(checkRadius, perlin);
   }

   @Override
   public boolean checkAndGenerate(World world, int chunkX, int chunkZ) {
      for (int ix = -this.checkRadius; ix <= this.checkRadius; ix++) {
         for (int iz = -this.checkRadius; iz <= this.checkRadius; iz++) {
            if (this.perlin.getValue((chunkX + ix) * 10, (chunkZ + iz) * 10) > 4.5) {
               Random rand = new Random(chunkX + ix + chunkZ + iz + world.getSeed());
               new ArrayList();
               mineshaftGenerate(world, rand, chunkX * 16, 100, chunkZ * 16);
            }
         }
      }

      return false;
   }

   public static void mineshaftGenerate(World world, Random rand, int x, int y, int z) {
      int shaft = 0;
      int rot = 0;
      int samples = 400;

      while (true) {
         if (rot == 0 || rot == 2) {
            boolean shgenerated = false;

            for (int zz = -2; zz <= 2; zz++) {
               if (world.isChunkGeneratedAt(x >> 4, z + zz >> 4)) {
                  for (int yy = 0; yy < 4; yy++) {
                     BlockPos pos = new BlockPos(x, y + yy, z + zz);
                     if (world.getBlockState(pos).getBlock() != BlocksRegister.WOODENSHAFT) {
                        if (shaft % 5 == 0 && !isAirUpper(world, x, y, z, rot)) {
                           shgenerated = true;
                           if (yy == 3 || zz == -2 || zz == 2) {
                              world.setBlockState(pos, BlocksRegister.WOODENSHAFT.getDefaultState());
                           } else if (zz == 0 && yy == 0) {
                              world.setBlockState(pos, Blocks.RAIL.getDefaultState());
                           } else {
                              world.setBlockToAir(pos);
                           }
                        } else if (zz == 0 && yy == 0) {
                           world.setBlockState(pos, Blocks.RAIL.getDefaultState());
                        } else {
                           world.setBlockToAir(pos);
                        }
                     }
                  }

                  if (zz > -2 && zz < 2) {
                     BlockPos posdown = new BlockPos(x, y - 1, z + zz);
                     if (world.isAirBlock(posdown)) {
                        world.setBlockState(posdown, BlocksRegister.WOODENSHAFT.getDefaultState());
                        if (zz == 0 && shaft % 5 == 0) {
                           shootSupport(world, x, y, z, rot, rand);
                        }
                     }
                  }
               }
            }

            if (rot == 0) {
               x++;
            }

            if (rot == 2) {
               x--;
            }
         }

         if (rot == 1 || rot == 3) {
            boolean shgenerated = false;

            for (int xx = -2; xx <= 2; xx++) {
               if (world.isChunkGeneratedAt(x + xx >> 4, z >> 4)) {
                  for (int yyx = 0; yyx < 4; yyx++) {
                     BlockPos pos = new BlockPos(x + xx, y + yyx, z);
                     if (world.getBlockState(pos).getBlock() != BlocksRegister.WOODENSHAFT) {
                        if (shaft % 5 == 0 && !isAirUpper(world, x, y, z, rot)) {
                           shgenerated = true;
                           if (yyx == 3 || xx == -2 || xx == 2) {
                              world.setBlockState(pos, BlocksRegister.WOODENSHAFT.getDefaultState());
                           } else if (xx == 0 && yyx == 0) {
                              world.setBlockState(pos, Blocks.RAIL.getDefaultState());
                           } else {
                              world.setBlockToAir(pos);
                           }
                        } else if (xx == 0 && yyx == 0) {
                           world.setBlockState(pos, Blocks.RAIL.getDefaultState());
                        } else {
                           world.setBlockToAir(pos);
                        }
                     }
                  }

                  if (xx > -2 && xx < 2) {
                     BlockPos posdown = new BlockPos(x + xx, y - 1, z);
                     if (world.isAirBlock(posdown)) {
                        world.setBlockState(posdown, BlocksRegister.WOODENSHAFT.getDefaultState());
                        if (xx == 0 && shaft % 5 == 0) {
                           shootSupport(world, x, y, z, rot, rand);
                        }
                     }
                  }
               }
            }

            if (rot == 1) {
               z++;
            }

            if (rot == 3) {
               z--;
            }
         }

         shaft++;
         samples--;
         float randf = rand.nextFloat();
         if (samples < 1) {
            return;
         }

         if (randf > 0.9) {
            int nextrot = rand.nextInt(4);
            if (rot == GetMOP.next(nextrot, 2, 4)) {
               nextrot = GetMOP.next(nextrot, rand.nextInt(3) + 1, 4);
            }

            rot = nextrot;
            if (!isAirUpper(world, x, y, z, nextrot)) {
               for (int zzx = -2; zzx <= 2; zzx++) {
                  for (int xxx = -2; xxx <= 2; xxx++) {
                     if (world.isChunkGeneratedAt(x + xxx >> 4, z + zzx >> 4)) {
                        for (int yyxx = -1; yyxx < 4; yyxx++) {
                           BlockPos pos = new BlockPos(x + xxx, y + yyxx, z + zzx);
                           if (world.getBlockState(pos).getBlock() != Blocks.RAIL) {
                              boolean ab = world.isAirBlock(pos);
                              if ((yyxx != 3 || xxx != 2 && xxx != -2 && zzx != 2 && zzx != -2)
                                 && (yyxx <= -1 || (xxx != -2 || zzx != -2) && (xxx != 2 || zzx != 2) && (xxx != -2 || zzx != 2) && (xxx != 2 || zzx != -2))
                                 && (yyxx != -1 || !ab)) {
                                 if (xxx == 0 && yyxx == 0) {
                                    world.setBlockState(pos, Blocks.RAIL.getDefaultState());
                                 } else if (yyxx > -1) {
                                    world.setBlockToAir(pos);
                                 }
                              } else {
                                 world.setBlockState(pos, BlocksRegister.WOODENSHAFT.getDefaultState());
                              }
                           }
                        }
                     }
                  }
               }
            } else {
               for (int zzx = -1; zzx <= 1; zzx++) {
                  for (int xxxx = -1; xxxx <= 1; xxxx++) {
                     if (world.isChunkGeneratedAt(x + xxxx >> 4, z + zzx >> 4)) {
                        BlockPos pos = new BlockPos(x + xxxx, y - 1, z + zzx);
                        if (world.getBlockState(pos).getBlock() != Blocks.RAIL && world.isAirBlock(pos)) {
                           world.setBlockState(pos, BlocksRegister.WOODENSHAFT.getDefaultState());
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static void shootSupport(World world, int x, int y, int z, int rot, Random rand) {
      Chunk chunk1 = world.getChunk(x >> 4, z >> 4);
      int result = 0;

      for (int i = 0; i < 200; i++) {
         if (chunk1.getBlockState(x, Math.max(y - 2 - i, 1), z).getBlock() != Blocks.AIR) {
            result = -i;
            break;
         }

         if (chunk1.getBlockState(x, Math.min(y + 4 + i, 254), z).getBlock() != Blocks.AIR) {
            result = i;
            break;
         }
      }

      if (result < 0) {
         if (rot == 0 || rot == 2) {
            world.setBlockState(new BlockPos(x, y - 2, z + 1), BlocksRegister.WOODENSHAFT.getDefaultState());
            world.setBlockState(new BlockPos(x, y - 2, z), BlocksRegister.WOODENSHAFT.getDefaultState());
            world.setBlockState(new BlockPos(x, y - 2, z - 1), BlocksRegister.WOODENSHAFT.getDefaultState());
         }

         if (rot == 1 || rot == 3) {
            world.setBlockState(new BlockPos(x + 1, y - 2, z), BlocksRegister.WOODENSHAFT.getDefaultState());
            world.setBlockState(new BlockPos(x, y - 2, z), BlocksRegister.WOODENSHAFT.getDefaultState());
            world.setBlockState(new BlockPos(x - 1, y - 2, z), BlocksRegister.WOODENSHAFT.getDefaultState());
         }

         for (int yy = -2; yy > result - 2; yy--) {
            world.setBlockState(new BlockPos(x, y + yy, z), BlocksRegister.WOODENSHAFT.getDefaultState());
         }
      }

      if (result > 0) {
         if (rot == 0 || rot == 2) {
            for (int yy = 0; yy < result + 4; yy++) {
               BlockPos osn = new BlockPos(x, y + yy, z);
               world.setBlockState(osn.east(), BlocksRegister.WOODENSHAFT.getDefaultState());
               world.setBlockState(osn.west(), BlocksRegister.WOODENSHAFT.getDefaultState());
            }
         }

         if (rot == 1 || rot == 3) {
            int yy = 0;

            while (yy < result + 4) {
               yy++;
            }
         }
      }
   }

   public static boolean isAirUpper(World world, int x, int y, int z, int rot) {
      if (rot == 0 || rot == 2) {
         for (int zz = -2; zz <= 2; zz++) {
            if (!world.isAirBlock(new BlockPos(x, y + 4, z + zz))) {
               return false;
            }
         }
      }

      if (rot == 1 || rot == 3) {
         for (int xx = -2; xx <= 2; xx++) {
            if (!world.isAirBlock(new BlockPos(x + xx, y + 4, z))) {
               return false;
            }
         }
      }

      return true;
   }

   class MineshaftGeneratorPiece {
      final byte type;
      final byte rotation;
      final int x;
      final int y;
      final int z;

      public MineshaftGeneratorPiece(byte type, byte rotation, int x, int y, int z) {
         this.type = type;
         this.rotation = rotation;
         this.x = x;
         this.y = y;
         this.z = z;
      }
   }
}
