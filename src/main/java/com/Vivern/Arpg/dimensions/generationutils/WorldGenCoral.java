//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.dimensions.generationutils;

import com.Vivern.Arpg.blocks.MiniCoral;
import com.Vivern.Arpg.dimensions.aquatica.AquaticaChunkGenerator;
import com.Vivern.Arpg.main.BlocksRegister;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCoral extends WorldGenerator {
   public float glowingModifier = 1.0F;
   public float diversityModifier = 1.0F;
   public Block mainCoralPrioriry;

   public WorldGenCoral(float glowingModifier, float diversityModifier, @Nullable Block mainCoralPrioriry) {
      this.glowingModifier = glowingModifier;
      this.diversityModifier = diversityModifier;
      this.mainCoralPrioriry = mainCoralPrioriry;
   }

   public boolean generate(World worldIn, Random rand, BlockPos position) {
      IBlockState mainCoral = this.randomBlock(rand);
      IBlockState mainMiniCoral = this.randomMainMinicoral(rand);
      float diversity = 0.4F * this.diversityModifier;
      worldIn.setBlockState(position, rand.nextFloat() < 0.5 ? BlocksRegister.SEASTONE.getDefaultState() : mainCoral, 2);
      int cycles = 2 + rand.nextInt(3);

      for (int i = 0; i <= cycles; i++) {
         int radius = i + 1;

         for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
               for (int z = -radius; z <= radius; z++) {
                  if (x >= i || x <= -i || y >= i || y <= -i || z >= i || z <= -i) {
                     BlockPos pos = position.add(x, y, z);
                     IBlockState state = worldIn.getBlockState(pos);
                     if (state.getBlock() == mainCoral.getBlock() || state.getBlock() == BlocksRegister.SEASTONE) {
                        if (i < cycles) {
                           for (int f = 0; f < 3; f++) {
                              EnumFacing facing1 = EnumFacing.byIndex(rand.nextInt(6));
                              BlockPos finalpos = pos.offset(facing1);
                              if (rand.nextFloat() < 0.4) {
                                 EnumFacing facing2 = EnumFacing.byIndex(rand.nextInt(6));
                                 if (facing2.getAxis() != facing1.getAxis()) {
                                    finalpos = finalpos.offset(facing2);
                                 }
                              }

                              if (worldIn.getBlockState(finalpos).getBlock().isReplaceable(worldIn, finalpos)) {
                                 worldIn.setBlockState(finalpos, rand.nextFloat() < 0.5 - i / 7.0F ? BlocksRegister.SEASTONE.getDefaultState() : mainCoral, 2);
                              }

                              if (rand.nextFloat() < 0.6) {
                                 break;
                              }
                           }
                        } else {
                           for (EnumFacing facing : EnumFacing.VALUES) {
                              if (rand.nextFloat() < 0.85F) {
                                 BlockPos finalposx = pos.offset(facing);
                                 Block block = worldIn.getBlockState(finalposx).getBlock();
                                 if (block == Blocks.WATER || block == Blocks.FLOWING_WATER) {
                                    worldIn.setBlockState(
                                       finalposx,
                                       rand.nextFloat() < diversity
                                          ? this.randomMinicoral(rand, true, facing)
                                          : mainMiniCoral.withProperty(MiniCoral.WET, true).withProperty(MiniCoral.FACING, facing),
                                       2
                                    );
                                 } else if (worldIn.isAirBlock(finalposx)) {
                                    worldIn.setBlockState(
                                       finalposx,
                                       rand.nextFloat() < diversity
                                          ? this.randomMinicoral(rand, false, facing)
                                          : mainMiniCoral.withProperty(MiniCoral.WET, false).withProperty(MiniCoral.FACING, facing),
                                       2
                                    );
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

      return false;
   }

   public IBlockState randomBlock(Random rand) {
      return this.mainCoralPrioriry != null && rand.nextFloat() > this.diversityModifier
         ? this.mainCoralPrioriry.getDefaultState()
         : AquaticaChunkGenerator.corals[rand.nextInt(5)].getDefaultState();
   }

   public IBlockState randomMainMinicoral(Random rand) {
      if (rand.nextFloat() < 0.04) {
         return AquaticaChunkGenerator.coralsGlow[rand.nextInt(5)].getDefaultState();
      } else if (rand.nextFloat() < 0.03) {
         return AquaticaChunkGenerator.coralsFavia[rand.nextInt(5)].getDefaultState();
      } else {
         return rand.nextFloat() < 0.02
            ? AquaticaChunkGenerator.corallimorphas[rand.nextInt(7)].getDefaultState()
            : AquaticaChunkGenerator.coralsMini[rand.nextInt(7)].getDefaultState();
      }
   }

   public IBlockState randomMinicoral(Random rand, boolean wet, EnumFacing facing) {
      if (rand.nextFloat() < 0.18 * this.glowingModifier) {
         return AquaticaChunkGenerator.coralsGlow[rand.nextInt(5)].getDefaultState().withProperty(MiniCoral.WET, wet).withProperty(MiniCoral.FACING, facing);
      } else if (rand.nextFloat() < 0.15) {
         return AquaticaChunkGenerator.coralsFavia[rand.nextInt(5)].getDefaultState().withProperty(MiniCoral.WET, wet).withProperty(MiniCoral.FACING, facing);
      } else if (rand.nextFloat() < 0.1) {
         return AquaticaChunkGenerator.corallimorphas[rand.nextInt(7)]
            .getDefaultState()
            .withProperty(MiniCoral.WET, wet)
            .withProperty(MiniCoral.FACING, facing);
      } else {
         return rand.nextFloat() < 0.27
            ? AquaticaChunkGenerator.coralsBig[rand.nextInt(5)].getDefaultState().withProperty(MiniCoral.WET, wet).withProperty(MiniCoral.FACING, facing)
            : AquaticaChunkGenerator.coralsMini[rand.nextInt(7)].getDefaultState().withProperty(MiniCoral.WET, wet).withProperty(MiniCoral.FACING, facing);
      }
   }
}
