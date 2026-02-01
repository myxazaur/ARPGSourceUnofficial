package com.vivern.arpg.dimensions.generationutils;

import com.vivern.arpg.blocks.MiniCoral;
import com.vivern.arpg.dimensions.aquatica.AquaticaChunkGenerator;
import com.vivern.arpg.main.BlocksRegister;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenMetallicCoral extends WorldGenerator {
   public int size;
   public boolean canGrowMinicorals;
   public int limit = 7;

   public WorldGenMetallicCoral(int size, boolean canGrowMinicorals) {
      this.size = size;
      this.canGrowMinicorals = canGrowMinicorals;
   }

   public void checkAndSetBlockState(World world, BlockPos pos, IBlockState newState, int flags) {
      if (world.isChunkGeneratedAt(pos.getX() >> 4, pos.getZ() >> 4)) {
         world.setBlockState(pos, newState, flags);
      }
   }

   public boolean generate(World worldIn, Random rand, BlockPos position) {
      IBlockState mainCoral = BlocksRegister.METALLICCORAL.getDefaultState();
      boolean minicorals = this.canGrowMinicorals && rand.nextFloat() < 0.8;
      this.checkAndSetBlockState(worldIn, position, rand.nextFloat() < 0.5 ? BlocksRegister.SEASTONE.getDefaultState() : mainCoral, 2);
      int finalradius = 0;
      int count = 0;

      for (int i = 0; i < this.limit; i++) {
         int radius = i + 1;
         if (finalradius < radius) {
            finalradius = radius;
         }

         for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
               for (int z = -radius; z <= radius; z++) {
                  if (count < this.size) {
                     if (x > i || x < -i || y > i || y < -i || z > i || z < -i) {
                        BlockPos pos = position.add(x, y, z);
                        IBlockState state = worldIn.getBlockState(pos);
                        if (state.getBlock() == BlocksRegister.METALLICCORAL || state.getBlock() == BlocksRegister.SEASTONE) {
                           boolean blockgenerated = false;

                           for (int f = 0; f < 4; f++) {
                              EnumFacing facing1 = EnumFacing.byIndex(rand.nextInt(6));
                              BlockPos finalpos = pos.offset(facing1);
                              if (worldIn.getBlockState(finalpos).getBlock().isReplaceable(worldIn, finalpos)) {
                                 this.checkAndSetBlockState(worldIn, finalpos, rand.nextInt(2) > i ? BlocksRegister.SEASTONE.getDefaultState() : mainCoral, 2);
                                 count++;
                                 blockgenerated = true;
                              }

                              if (blockgenerated && rand.nextFloat() < 0.6 || count >= this.size) {
                                 break;
                              }
                           }
                        }
                     }
                  } else {
                     x = radius;
                     y = radius;
                     z = radius;
                  }
               }
            }
         }

         if (count >= this.size) {
            break;
         }
      }

      if (minicorals) {
         int i = finalradius - 1;

         for (int x = -finalradius; x <= finalradius; x++) {
            for (int y = -finalradius; y <= finalradius; y++) {
               for (int zx = -finalradius; zx <= finalradius; zx++) {
                  if (x >= i || x <= -i || y >= i || y <= -i || zx >= i || zx <= -i) {
                     BlockPos pos = position.add(x, y, zx);
                     IBlockState state = worldIn.getBlockState(pos);
                     if (state.getBlock() == BlocksRegister.METALLICCORAL || state.getBlock() == BlocksRegister.SEASTONE) {
                        for (EnumFacing facing : EnumFacing.VALUES) {
                           if (rand.nextFloat() < 0.35F) {
                              BlockPos finalposx = pos.offset(facing);
                              Block block = worldIn.getBlockState(finalposx).getBlock();
                              if (block == Blocks.WATER || block == Blocks.FLOWING_WATER) {
                                 this.checkAndSetBlockState(worldIn, finalposx, this.randomMinicoral(rand, true, facing), 2);
                              } else if (worldIn.isAirBlock(finalposx)) {
                                 this.checkAndSetBlockState(worldIn, finalposx, this.randomMinicoral(rand, false, facing), 2);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return true;
   }

   public IBlockState randomMinicoral(Random rand, boolean wet, EnumFacing facing) {
      if (rand.nextFloat() < 0.02) {
         return AquaticaChunkGenerator.coralsGlow[rand.nextInt(5)].getDefaultState().withProperty(MiniCoral.WET, wet).withProperty(MiniCoral.FACING, facing);
      } else if (rand.nextFloat() < 0.11) {
         return AquaticaChunkGenerator.coralsFavia[rand.nextInt(5)].getDefaultState().withProperty(MiniCoral.WET, wet).withProperty(MiniCoral.FACING, facing);
      } else if (rand.nextFloat() < 0.13) {
         return AquaticaChunkGenerator.corallimorphas[rand.nextInt(7)]
            .getDefaultState()
            .withProperty(MiniCoral.WET, wet)
            .withProperty(MiniCoral.FACING, facing);
      } else if (rand.nextFloat() < 0.14) {
         return AquaticaChunkGenerator.coralsBig[rand.nextInt(5)].getDefaultState().withProperty(MiniCoral.WET, wet).withProperty(MiniCoral.FACING, facing);
      } else if (rand.nextFloat() < 0.13) {
         return BlocksRegister.MINICORALBROWN.getDefaultState().withProperty(MiniCoral.WET, wet).withProperty(MiniCoral.FACING, facing);
      } else if (rand.nextFloat() < 0.13) {
         return BlocksRegister.MINICORALPURPLE.getDefaultState().withProperty(MiniCoral.WET, wet).withProperty(MiniCoral.FACING, facing);
      } else if (rand.nextFloat() < 0.13) {
         return BlocksRegister.MINICORALWHITE.getDefaultState().withProperty(MiniCoral.WET, wet).withProperty(MiniCoral.FACING, facing);
      } else {
         return rand.nextFloat() < 0.13
            ? BlocksRegister.MINICORALWHITE2.getDefaultState().withProperty(MiniCoral.WET, wet).withProperty(MiniCoral.FACING, facing)
            : AquaticaChunkGenerator.coralsMini[rand.nextInt(7)].getDefaultState().withProperty(MiniCoral.WET, wet).withProperty(MiniCoral.FACING, facing);
      }
   }
}
