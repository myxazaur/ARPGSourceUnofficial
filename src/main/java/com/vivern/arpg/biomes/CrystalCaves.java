package com.vivern.arpg.biomes;

import com.vivern.arpg.main.BlocksRegister;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class CrystalCaves extends Biome {
   public CrystalCaves() {
      super(new BiomeProperties("Crystal caves").setBaseHeight(0.0F).setHeightVariation(0.0F).setTemperature(0.5F));
      this.topBlock = Blocks.STONE.getDefaultState();
      this.fillerBlock = Blocks.STONE.getDefaultState();
      this.decorator = new CrystalCavesDecorator();
   }

   public static void blockPosCrystalTrace2(World worldIn, Random random, BlockPos pos, int xmin, int xmax, int zmin, int zmax, Block crystalblock) {
      int xx = pos.getX();
      int yy = pos.getY();
      int zz = pos.getZ();
      boolean xxb = random.nextBoolean();
      boolean yyb = random.nextBoolean();
      boolean zzb = random.nextBoolean();
      int xxperc = random.nextInt(10) + 1;
      int yyperc = random.nextInt(10) + 1;
      int zzperc = random.nextInt(10) + 1;
      int size = random.nextInt(5);
      if (random.nextInt(4) == 0) {
         size += random.nextInt(2) + 1;
      }

      int xxrand = random.nextInt(3) - 1;
      int yyrand = random.nextInt(3) - 1;
      int zzrand = random.nextInt(3) - 1;
      if (random.nextInt(11) == 0) {
         double gaussian = random.nextGaussian();
         if (gaussian > 0.0) {
            size = (int)(size + Math.round(gaussian));
         }
      }

      for (int ii = 1; ii < 25; ii++) {
         if (ii % xxperc == 0) {
            xx += xxb ? 1 : -1;
         }

         if (ii % yyperc == 0) {
            yy += yyb ? 1 : -1;
         }

         if (ii % zzperc == 0) {
            zz += zzb ? 1 : -1;
         }

         BlockPos finalpos = new BlockPos(xx, yy, zz);
         if (finalpos.getX() > xmax || finalpos.getZ() > zmax || finalpos.getX() < xmin || finalpos.getZ() < zmin) {
            return;
         }

         if (worldIn.isAirBlock(finalpos) || worldIn.getBlockState(finalpos).getBlock() == crystalblock) {
            worldIn.setBlockState(finalpos, crystalblock.getDefaultState());
            if (size > 5) {
               int radius = size - 5;

               for (int fx = -radius; fx < radius + 1; fx++) {
                  for (int fy = -radius; fy < radius + 1; fy++) {
                     for (int fz = -radius; fz < radius + 1; fz++) {
                        BlockPos final2pos = finalpos.add(fx, fy, fz);
                        if (worldIn.isAirBlock(final2pos)) {
                           worldIn.setBlockState(final2pos, crystalblock.getDefaultState());
                        }
                     }
                  }
               }
            } else {
               if (size > 1 && worldIn.isAirBlock(finalpos.add(xxrand, yyrand, zzrand))) {
                  worldIn.setBlockState(finalpos.add(xxrand, yyrand, zzrand), crystalblock.getDefaultState());
               }

               if (size > 2 && worldIn.isAirBlock(finalpos.add(-xxrand, -yyrand, -zzrand))) {
                  worldIn.setBlockState(finalpos.add(-xxrand, -yyrand, -zzrand), crystalblock.getDefaultState());
               }

               if (size > 3 && worldIn.isAirBlock(finalpos.add(xxrand, -yyrand, -zzrand))) {
                  worldIn.setBlockState(finalpos.add(xxrand, -yyrand, -zzrand), crystalblock.getDefaultState());
               }

               if (size > 4 && worldIn.isAirBlock(finalpos.add(-xxrand, yyrand, zzrand))) {
                  worldIn.setBlockState(finalpos.add(-xxrand, yyrand, zzrand), crystalblock.getDefaultState());
               }
            }
         }
      }
   }

   public static void blockPosTrace(World worldIn, Random random, BlockPos pos) {
      int xx = pos.getX();
      int yy = pos.getY();
      int zz = pos.getZ();
      int xxdisp = random.nextInt(9) + 1;
      int yydisp = random.nextInt(9) + 1;
      int zzdisp = random.nextInt(9) + 1;
      boolean xxb = random.nextBoolean();
      boolean yyb = random.nextBoolean();
      boolean zzb = random.nextBoolean();
      boolean swapxz = random.nextBoolean();
      int xxrand = random.nextInt(3) - 1;
      int yyrand = random.nextInt(3) - 1;
      int zzrand = random.nextInt(3) - 1;
      int size = random.nextInt(7);
      if (random.nextInt(8) == 0) {
         double gaussian = random.nextGaussian();
         if (gaussian > 0.0) {
            size = (int)(size + Math.round(gaussian));
         }
      }

      for (int i = 0; i < 5; i++) {
         for (int ii = 0; ii < xxdisp; ii++) {
            if (ii == 0) {
               if (xxb) {
                  xx++;
               } else {
                  xx--;
               }
            }

            for (int ee = 0; ee < yydisp; ee++) {
               if (ee == 0) {
                  if (yyb) {
                     yy++;
                  } else {
                     yy--;
                  }
               }

               for (int rr = 0; rr < zzdisp; rr++) {
                  if (rr == 0) {
                     if (zzb) {
                        zz++;
                     } else {
                        zz--;
                     }
                  }

                  BlockPos finalpos;
                  if (swapxz) {
                     finalpos = new BlockPos(zz, yy, xx);
                  } else {
                     finalpos = new BlockPos(xx, yy, zz);
                  }

                  if (worldIn.isAirBlock(finalpos) || worldIn.getBlockState(finalpos).getBlock() == BlocksRegister.SELENITE) {
                     worldIn.setBlockState(finalpos, BlocksRegister.SELENITE.getDefaultState());
                     if (size <= 5) {
                        if (size > 1 && worldIn.isAirBlock(finalpos.add(xxrand, yyrand, zzrand))) {
                           worldIn.setBlockState(finalpos.add(xxrand, yyrand, zzrand), BlocksRegister.SELENITE.getDefaultState());
                        }

                        if (size > 2 && worldIn.isAirBlock(finalpos.add(-xxrand, -yyrand, -zzrand))) {
                           worldIn.setBlockState(finalpos.add(-xxrand, -yyrand, -zzrand), BlocksRegister.SELENITE.getDefaultState());
                        }

                        if (size > 3 && worldIn.isAirBlock(finalpos.add(xxrand, -yyrand, -zzrand))) {
                           worldIn.setBlockState(finalpos.add(xxrand, -yyrand, -zzrand), BlocksRegister.SELENITE.getDefaultState());
                        }

                        if (size > 4 && worldIn.isAirBlock(finalpos.add(-xxrand, yyrand, zzrand))) {
                           worldIn.setBlockState(finalpos.add(-xxrand, yyrand, zzrand), BlocksRegister.SELENITE.getDefaultState());
                        }
                     } else {
                        int radius = size - 5;

                        for (int fx = -radius; fx < radius + 1; fx++) {
                           for (int fy = -radius; fy < radius + 1; fy++) {
                              for (int fz = -radius; fz < radius + 1; fz++) {
                                 BlockPos final2pos = finalpos.add(fx, fy, fz);
                                 if (worldIn.isAirBlock(final2pos)) {
                                    worldIn.setBlockState(final2pos, BlocksRegister.SELENITE.getDefaultState());
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
