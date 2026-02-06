package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.blocks.BlockSpeleothem;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenSpread;
import com.Vivern.Arpg.main.BlocksRegister;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkGeneratorSettings.Factory;

class TunnelsDecorator extends BiomeDecorator {
   public WorldGenSpread brownmushrooms = new WorldGenSpread(Blocks.BROWN_MUSHROOM, 17, 4, 4, null, false);
   public WorldGenSpread redmushrooms = new WorldGenSpread(Blocks.RED_MUSHROOM, 12, 4, 4, null, false);

   public void decorate(World world, Random rand, Biome biome, BlockPos pos) {
      if (this.decorating) {
         throw new RuntimeException("Already decorating");
      } else {
         Block blockstalact = BlocksRegister.STONESPELEOTHEM;

         for (int iix = 0; iix < 16; iix++) {
            for (int iiz = 0; iiz < 16; iiz++) {
               if (rand.nextFloat() < 0.4) {
                  int px = iix + 8;
                  int pz = iiz + 8;
                  boolean lastblockStone = false;
                  int prevstate = -1;
                  IBlockState stateup = null;

                  for (int yy = 254; yy > 5; yy--) {
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
                              if (rand.nextFloat() < 0.2) {
                                 world.setBlockState(fpos.up(), blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 4), 2);
                                 if (rand.nextFloat() < 0.9) {
                                    if (rand.nextFloat() < 0.6) {
                                       world.setBlockState(fpos.up(2), blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 3), 2);
                                       IBlockState state3 = world.getBlockState(fpos.up(3));
                                       if (rand.nextFloat() < 0.6
                                          && (state3.getBlock() == blockstalact || state3.getBlock() == Blocks.AIR)) {
                                          world.setBlockState(fpos.up(3), blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 2), 2);
                                          IBlockState state4 = world.getBlockState(fpos.up(4));
                                          if (rand.nextFloat() < 0.5
                                             && (state4.getBlock() == blockstalact || state4.getBlock() == Blocks.AIR)) {
                                             world.setBlockState(fpos.up(4), blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 2), 2);
                                             IBlockState state5 = world.getBlockState(fpos.up(5));
                                             if (rand.nextFloat() < 0.5
                                                && (state5.getBlock() == blockstalact || state5.getBlock() == Blocks.AIR)) {
                                                world.setBlockState(
                                                   fpos.up(5), blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 2), 2
                                                );
                                             }
                                          }
                                       }
                                    } else {
                                       world.setBlockState(fpos.up(2), blockstalact.getDefaultState().withProperty(BlockSpeleothem.TYPE, 8), 2);
                                    }
                                 }
                              } else if (rand.nextFloat() < 0.65) {
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

         if (rand.nextFloat() < 0.13F) {
            BlockPos posit = pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8);
            List<BlockPos> listposes = new ArrayList<>();
            int height = 252;

            for (int rr = 10; rr < height; rr++) {
               BlockPos currentpos = posit.add(0, rr, 0);
               if (!world.isAirBlock(currentpos.down()) && world.isAirBlock(currentpos) && world.isBlockFullCube(currentpos.down())) {
                  listposes.add(currentpos);
               }
            }

            if (!listposes.isEmpty()) {
               for (BlockPos finalpos : listposes) {
                  if (rand.nextFloat() < 0.75F && finalpos != null) {
                     (rand.nextFloat() < 0.3F ? this.redmushrooms : this.brownmushrooms).generate(world, rand, finalpos);
                  }
               }
            }
         }

         this.chunkProviderSettings = Factory.jsonToFactory(world.getWorldInfo().getGeneratorOptions()).build();
         this.chunkPos = pos;
         this.decorating = false;
      }
   }
}
