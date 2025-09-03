//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.dimensions.generationutils;

import com.Vivern.Arpg.blocks.BlockRotated;
import com.Vivern.Arpg.main.BlocksRegister;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenArthroheliaTree extends WorldGenerator {
   public EnumFacing growDirection;
   public int maxsamples;
   public int samples;
   public float[] chances;
   public float chanceReduction = 1.0F;
   public float startChanceToContinue = 0.9F;
   public float startChanceToSplit = 0.2F;
   public int genStyle = 0;
   public static float[] chancesBrush = new float[]{0.8F, 0.3F, 0.5F};
   public static float[] chancesTree = new float[]{0.95F, 0.05F, 0.7F};
   public static float[] chancesMini = new float[]{0.95F, 0.1F, 0.1F};

   public boolean generate(World world, Random rand, BlockPos position) {
      this.samples = this.maxsamples;
      world.setBlockState(position, BlocksRegister.ARTHROHELIASTEM.getDefaultState().withProperty(BlockRotated.FACING_FULL, this.growDirection), 2);
      if (this.genStyle == 0) {
         this.recursiveGenerate(world, position, rand, 0, this.growDirection, this.startChanceToContinue, this.startChanceToSplit);
      }

      if (this.genStyle == 1) {
         this.recursiveGenerate2(world, position, rand, 0, this.growDirection, this.startChanceToContinue, this.startChanceToSplit);
      }

      if (this.genStyle == 2) {
         this.recursiveGenerate3(world, position, rand, 0, this.growDirection, this.startChanceToContinue, this.startChanceToSplit);
      }

      return this.samples < this.maxsamples;
   }

   public float getChances(EnumFacing facing) {
      if (facing == this.growDirection) {
         return this.chances[0];
      } else {
         return facing == this.growDirection.getOpposite() ? this.chances[1] : this.chances[2];
      }
   }

   public void recursiveGenerate(
      World world, BlockPos position, Random rand, int currentoffsetFromStart, EnumFacing facing, float chanceToContinue, float chanceToSplit
   ) {
      BlockPos offpos = position.offset(facing);
      if (this.samples > 0) {
         if (rand.nextFloat() < chanceToSplit) {
            offpos = offpos.offset(facing);
            world.setBlockState(offpos, BlocksRegister.LANTERNAMETHYST.getDefaultState(), 2);
            this.setTwo(world, offpos, facing, BlocksRegister.ARTHROHELIASTEM);
            this.samples -= 3;

            for (EnumFacing nextFace : EnumFacing.VALUES) {
               if (nextFace != facing.getOpposite() && rand.nextFloat() < this.getChances(nextFace)) {
                  this.setTwo(world, offpos, nextFace, BlocksRegister.ARTHROHELIASTEM);
                  this.recursiveGenerate(
                     world,
                     offpos.offset(nextFace),
                     rand,
                     currentoffsetFromStart,
                     nextFace,
                     chanceToContinue - 0.05F * this.chanceReduction,
                     chanceToSplit - 0.02F * this.chanceReduction
                  );
               }
            }
         } else if (rand.nextFloat() < chanceToContinue) {
            world.setBlockState(offpos, BlocksRegister.ARTHROHELIASTALK.getDefaultState().withProperty(BlockRotated.FACING_FULL, facing), 2);
            this.samples--;
            this.recursiveGenerate(world, offpos, rand, currentoffsetFromStart, facing, chanceToContinue, chanceToSplit);
         } else {
            world.setBlockState(offpos, BlocksRegister.ARTHROHELIASPIKE.getDefaultState().withProperty(BlockRotated.FACING_FULL, facing), 2);
         }
      } else {
         world.setBlockState(offpos, BlocksRegister.ARTHROHELIASPIKE.getDefaultState().withProperty(BlockRotated.FACING_FULL, facing), 2);
      }
   }

   public void setTwo(World world, BlockPos position, EnumFacing facing, Block block) {
      world.setBlockState(position.offset(facing), block.getDefaultState().withProperty(BlockRotated.FACING_FULL, facing), 2);
      facing = facing.getOpposite();
      world.setBlockState(position.offset(facing), block.getDefaultState().withProperty(BlockRotated.FACING_FULL, facing), 2);
   }

   public void recursiveGenerate2(
      World world, BlockPos position, Random rand, int currentoffsetFromStart, EnumFacing facing, float chanceToContinue, float chanceToSplit
   ) {
      BlockPos offpos = position.offset(facing);
      if (this.samples > 0) {
         if (rand.nextFloat() < chanceToSplit) {
            offpos = offpos.offset(facing);
            world.setBlockState(offpos, BlocksRegister.LANTERNAMETHYST.getDefaultState(), 2);
            this.setTwo(world, offpos, facing, BlocksRegister.ARTHROHELIASTALK);
            this.samples -= 3;

            for (EnumFacing nextFace : EnumFacing.VALUES) {
               if (nextFace != facing.getOpposite() && rand.nextFloat() < this.getChances(nextFace)) {
                  this.setTwo(world, offpos, nextFace, BlocksRegister.ARTHROHELIASTALK);
                  this.recursiveGenerate2(
                     world,
                     offpos.offset(nextFace),
                     rand,
                     currentoffsetFromStart,
                     nextFace,
                     chanceToContinue - 0.05F * this.chanceReduction,
                     chanceToSplit - 0.02F * this.chanceReduction
                  );
               }
            }
         } else if (rand.nextFloat() < chanceToContinue && rand.nextFloat() < chanceToContinue) {
            world.setBlockState(offpos, BlocksRegister.ARTHROHELIASTEM.getDefaultState().withProperty(BlockRotated.FACING_FULL, facing.getOpposite()), 2);
            world.setBlockState(offpos.offset(facing), BlocksRegister.ARTHROHELIASTEM.getDefaultState().withProperty(BlockRotated.FACING_FULL, facing), 2);
            this.samples -= 2;
            this.recursiveGenerate2(world, offpos.offset(facing), rand, currentoffsetFromStart, facing, chanceToContinue, chanceToSplit);
         } else {
            world.setBlockState(offpos, BlocksRegister.ARTHROHELIASPIKE.getDefaultState().withProperty(BlockRotated.FACING_FULL, facing), 2);
         }
      } else {
         world.setBlockState(offpos, BlocksRegister.ARTHROHELIASPIKE.getDefaultState().withProperty(BlockRotated.FACING_FULL, facing), 2);
      }
   }

   public void recursiveGenerate3(
      World world, BlockPos position, Random rand, int currentoffsetFromStart, EnumFacing facing, float chanceToContinue, float chanceToSplit
   ) {
      BlockPos offpos = position.offset(facing);
      if (this.samples > 0) {
         if (rand.nextFloat() < chanceToSplit) {
            world.setBlockState(offpos, BlocksRegister.ARTHROHELIASTALK.getDefaultState().withProperty(BlockRotated.FACING_FULL, facing), 2);
            offpos = offpos.offset(facing, 2);
            world.setBlockState(offpos, BlocksRegister.LANTERNAMETHYST.getDefaultState(), 2);
            this.samples -= 3;

            for (EnumFacing nextFace : EnumFacing.VALUES) {
               if (nextFace != facing.getOpposite() && rand.nextFloat() < this.getChances(nextFace)) {
                  world.setBlockState(
                     offpos.offset(nextFace, 2),
                     BlocksRegister.ARTHROHELIASTALK.getDefaultState().withProperty(BlockRotated.FACING_FULL, nextFace.getOpposite()),
                     2
                  );
                  this.recursiveGenerate3(
                     world,
                     offpos.offset(nextFace, 2),
                     rand,
                     currentoffsetFromStart,
                     nextFace,
                     chanceToContinue - 0.05F * this.chanceReduction,
                     chanceToSplit - 0.02F * this.chanceReduction
                  );
               }
            }
         } else if (rand.nextFloat() < chanceToContinue && rand.nextFloat() < chanceToContinue) {
            world.setBlockState(offpos, BlocksRegister.ARTHROHELIASTEM.getDefaultState().withProperty(BlockRotated.FACING_FULL, facing.getOpposite()), 2);
            world.setBlockState(offpos.offset(facing), BlocksRegister.ARTHROHELIASTEM.getDefaultState().withProperty(BlockRotated.FACING_FULL, facing), 2);
            this.samples -= 2;
            this.recursiveGenerate3(world, offpos.offset(facing), rand, currentoffsetFromStart, facing, chanceToContinue, chanceToSplit);
         } else {
            world.setBlockState(offpos, BlocksRegister.ARTHROHELIASTEM.getDefaultState().withProperty(BlockRotated.FACING_FULL, facing.getOpposite()), 2);
            world.setBlockState(offpos.offset(facing), BlocksRegister.LANTERNAMETHYST.getDefaultState(), 2);
            this.setSpikes(world, offpos.offset(facing), facing, BlocksRegister.ARTHROHELIASPIKE);
         }
      } else {
         world.setBlockState(offpos, BlocksRegister.ARTHROHELIASPIKE.getDefaultState().withProperty(BlockRotated.FACING_FULL, facing), 2);
      }
   }

   public void setSpikes(World world, BlockPos position, EnumFacing facing, Block block) {
      for (EnumFacing nextFace : EnumFacing.VALUES) {
         if (nextFace != facing && nextFace != facing.getOpposite()) {
            world.setBlockState(position.offset(nextFace), block.getDefaultState().withProperty(BlockRotated.FACING_FULL, nextFace), 2);
         }
      }
   }
}
