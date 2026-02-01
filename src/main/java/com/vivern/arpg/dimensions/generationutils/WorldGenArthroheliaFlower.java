package com.vivern.arpg.dimensions.generationutils;

import com.vivern.arpg.blocks.BlockRotated;
import com.vivern.arpg.main.GetMOP;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenArthroheliaFlower extends WorldGenerator {
   public Block spike;
   public Block stalk;
   public Block stem;
   public Block core;
   public int hatType;

   public boolean generate(World world, Random rand, BlockPos position) {
      int radiusX = 4;
      int radiusZ = 4;
      int height = 8;
      world.setBlockState(position.up(), this.spike.getDefaultState().withProperty(BlockRotated.FACING_FULL, EnumFacing.DOWN), 2);
      world.setBlockState(position.up(2), this.stalk.getDefaultState().withProperty(BlockRotated.FACING_FULL, EnumFacing.UP), 2);
      int iterations = 4 + rand.nextInt(2);

      for (int i = 0; i < iterations; i++) {
         for (int x = -radiusX; x <= radiusX; x++) {
            for (int z = -radiusZ; z <= radiusZ; z++) {
               for (int y = 0; y < height; y++) {
                  this.doStep(world, rand, position.add(x, y, z));
               }
            }
         }
      }

      for (int x = -radiusX; x <= radiusX; x++) {
         for (int z = -radiusZ; z <= radiusZ; z++) {
            for (int y = height; y > 0; y--) {
               this.doHat(world, rand, position.add(x, y, z));
            }
         }
      }

      return true;
   }

   public void doStep(World world, Random rand, BlockPos position) {
      IBlockState state = world.getBlockState(position);
      if (state.getBlock() == this.stalk && rand.nextFloat() < 0.3F) {
         EnumFacing facing = this.selectFacing((EnumFacing)state.getValue(BlockRotated.FACING_FULL), rand);
         BlockPos var6 = position.offset(facing);
         world.setBlockState(var6, this.spike.getDefaultState().withProperty(BlockRotated.FACING_FULL, facing.getOpposite()), 2);
         position = var6.offset(facing);
         world.setBlockState(position, this.stalk.getDefaultState().withProperty(BlockRotated.FACING_FULL, facing), 2);
      }

      if (state.getBlock() == this.spike && rand.nextFloat() < 0.15F) {
         EnumFacing facing = this.selectFacing(((EnumFacing)state.getValue(BlockRotated.FACING_FULL)).getOpposite(), rand);
         position = position.offset(facing);
         world.setBlockState(position, this.spike.getDefaultState().withProperty(BlockRotated.FACING_FULL, facing.getOpposite()), 2);
         position = position.offset(facing);
         world.setBlockState(position, this.stalk.getDefaultState().withProperty(BlockRotated.FACING_FULL, facing), 2);
      }
   }

   public void doHat(World world, Random rand, BlockPos position) {
      IBlockState state = world.getBlockState(position);
      if (state.getBlock() == this.stalk && rand.nextFloat() < 0.5F) {
         EnumFacing facing = (EnumFacing)state.getValue(BlockRotated.FACING_FULL);
         if (facing == EnumFacing.UP) {
            if (this.hatType == 1 && world.isAirBlock(position.up()) && world.isAirBlock(position.up(2))) {
               world.setBlockState(position.up(), this.stem.getDefaultState().withProperty(BlockRotated.FACING_FULL, EnumFacing.DOWN), 2);
               world.setBlockState(position.up(2), this.stem.getDefaultState().withProperty(BlockRotated.FACING_FULL, EnumFacing.UP), 2);
            }

            if (this.hatType == 2) {
               for (int i = 1; i <= 3; i++) {
                  if (world.isAirBlock(position.up(i))) {
                     world.setBlockState(position.up(i), this.stalk.getDefaultState().withProperty(BlockRotated.FACING_FULL, EnumFacing.UP), 2);
                  }
               }
            }

            if (this.hatType == 3 && world.isAirBlock(position.up()) && world.isAirBlock(position.up(2))) {
               world.setBlockState(position.up(), this.stem.getDefaultState().withProperty(BlockRotated.FACING_FULL, EnumFacing.DOWN), 2);
               world.setBlockState(position.up(2), this.core.getDefaultState(), 2);
            }
         }
      }
   }

   public EnumFacing selectFacing(EnumFacing last, Random rand) {
      Axis axis = last.getAxis();
      if (axis == Axis.Y) {
         return EnumFacing.HORIZONTALS[rand.nextInt(4)];
      } else {
         return axis == Axis.X ? GetMOP.ZY_TICALS[rand.nextInt(3) + 1] : GetMOP.XY_TICALS[rand.nextInt(3) + 1];
      }
   }
}
