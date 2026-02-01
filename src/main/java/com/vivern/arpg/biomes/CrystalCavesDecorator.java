package com.vivern.arpg.biomes;

import com.vivern.arpg.blocks.BlockSpeleothem;
import com.vivern.arpg.main.BlocksRegister;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkGeneratorSettings.Factory;

class CrystalCavesDecorator extends BiomeDecorator {
   public void setStalact(World world, BlockPos pos, int prop, Block blockstalact, Random rand) {
      world.setBlockState(
         pos, (rand.nextFloat() < 0.15 ? BlocksRegister.SELENITESPELEOTHEM : blockstalact).getDefaultState().withProperty(BlockSpeleothem.TYPE, prop), 2
      );
   }

   public void decorate(World world, Random rand, Biome biome, BlockPos pos) {
      if (this.decorating) {
         throw new RuntimeException("Already decorating");
      } else {
         for (int ss = 0; ss < 160; ss++) {
            BlockPos postogenerate = new BlockPos(pos.getX() + rand.nextInt(16) + 8, rand.nextInt(254) + 1, pos.getZ() + rand.nextInt(16) + 8);
            if (world.getBlockState(postogenerate).getMaterial() == Material.ROCK) {
               CrystalCaves.blockPosCrystalTrace2(
                  world,
                  rand,
                  postogenerate,
                  pos.getX() + 3,
                  pos.getX() + 31,
                  pos.getZ() + 1,
                  pos.getZ() + 29,
                  BlocksRegister.SELENITE
               );
            }
         }

         for (int iix = 0; iix < 16; iix++) {
            for (int iiz = 0; iiz < 16; iiz++) {
               if (rand.nextFloat() < 0.43) {
                  Block blockstalact = rand.nextFloat() < 0.6 ? BlocksRegister.STONESPELEOTHEM : BlocksRegister.SELENITESPELEOTHEM;
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
                              this.setStalact(world, fpos, 0, blockstalact, rand);
                              prevstate = 0;
                           } else if (rand.nextFloat() < 0.2) {
                              this.setStalact(world, fpos, 1, blockstalact, rand);
                              prevstate = 1;
                           } else if (rand.nextFloat() < 0.07) {
                              this.setStalact(world, fpos, 2, blockstalact, rand);
                              prevstate = 2;
                           } else if (rand.nextFloat() < 0.2) {
                              this.setStalact(world, fpos, 7, blockstalact, rand);
                              prevstate = 7;
                           } else if (rand.nextFloat() < 0.8) {
                              this.setStalact(world, fpos, 5, blockstalact, rand);
                              prevstate = 5;
                           }
                        } else if (prevstate >= 0) {
                           if (prevstate == 0) {
                              if (rand.nextFloat() < 0.5) {
                                 this.setStalact(world, fpos, 1, blockstalact, rand);
                                 prevstate = 1;
                              } else {
                                 this.setStalact(world, fpos, 7, blockstalact, rand);
                                 prevstate = 7;
                              }
                           } else if (prevstate == 1) {
                              if (rand.nextFloat() < 0.85) {
                                 this.setStalact(world, fpos, 2, blockstalact, rand);
                                 prevstate = 2;
                              } else {
                                 prevstate = -1;
                              }
                           } else if (prevstate == 2) {
                              if (rand.nextFloat() < 0.5) {
                                 this.setStalact(world, fpos, 2, blockstalact, rand);
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
                           && (stateup.getBlock() instanceof BlockSpeleothem || stateup.getBlock() == Blocks.AIR)) {
                           IBlockState state2 = world.getBlockState(fpos.up(2));
                           boolean b1 = false;
                           if (state2.getBlock() instanceof BlockSpeleothem) {
                              int stat = (Integer)state2.getValue(BlockSpeleothem.TYPE);
                              if (stat == 0 && rand.nextFloat() < 0.3) {
                                 this.setStalact(world, fpos.up(), 4, blockstalact, rand);
                              } else if (stat == 1 && rand.nextFloat() < 0.3) {
                                 this.setStalact(world, fpos.up(), 3, blockstalact, rand);
                              } else {
                                 b1 = true;
                              }
                           } else {
                              b1 = true;
                           }

                           if (b1) {
                              if (rand.nextFloat() < 0.2) {
                                 this.setStalact(world, fpos.up(), 4, blockstalact, rand);
                                 if (rand.nextFloat() < 0.9) {
                                    if (rand.nextFloat() < 0.6) {
                                       this.setStalact(world, fpos.up(2), 3, blockstalact, rand);
                                       IBlockState state3 = world.getBlockState(fpos.up(3));
                                       if (rand.nextFloat() < 0.6
                                          && (state3.getBlock() instanceof BlockSpeleothem || state3.getBlock() == Blocks.AIR)) {
                                          this.setStalact(world, fpos.up(3), 2, blockstalact, rand);
                                          IBlockState state4 = world.getBlockState(fpos.up(4));
                                          if (rand.nextFloat() < 0.5
                                             && (state4.getBlock() instanceof BlockSpeleothem || state4.getBlock() == Blocks.AIR)) {
                                             this.setStalact(world, fpos.up(4), 2, blockstalact, rand);
                                             IBlockState state5 = world.getBlockState(fpos.up(5));
                                             if (rand.nextFloat() < 0.5
                                                && (state5.getBlock() instanceof BlockSpeleothem || state5.getBlock() == Blocks.AIR)) {
                                                this.setStalact(world, fpos.up(5), 2, blockstalact, rand);
                                             }
                                          }
                                       }
                                    } else {
                                       this.setStalact(world, fpos.up(2), 8, blockstalact, rand);
                                    }
                                 }
                              } else if (rand.nextFloat() < 0.4) {
                                 if (rand.nextFloat() < 0.85) {
                                    this.setStalact(world, fpos.up(), 6, blockstalact, rand);
                                 } else {
                                    this.setStalact(world, fpos.up(), 8, blockstalact, rand);
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

         this.chunkProviderSettings = Factory.jsonToFactory(world.getWorldInfo().getGeneratorOptions()).build();
         this.chunkPos = pos;
         this.decorating = false;
      }
   }
}
