package com.Vivern.Arpg.dimensions.generationutils;

import com.Vivern.Arpg.blocks.ElectrofernLeaves;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenElectrofern extends WorldGenerator {
   public Block leaf = BlocksRegister.ELECTROFERNLEAVES;
   public Block stem = BlocksRegister.ELECTROFERNSTEM;
   public EnumFacing generateDirection = EnumFacing.UP;
   public boolean generateRotated;
   public int maxLength;
   int maxFernRootMultiples = 0;

   public boolean generate(World world, Random rand, BlockPos position) {
      this.maxFernRootMultiples = 0;
      this.generateBranch(world, rand, position, this.maxLength, this.generateDirection, this.generateRotated);
      return true;
   }

   public void genColumnDown(World world, Random rand, BlockPos position, int maxBlocks, Block block, boolean recursion) {
      for (int i = 0; i < maxBlocks && GenerationHelper.isReplaceable(world, position); i++) {
         world.setBlockState(position, block.getDefaultState(), 2);
         position = position.down();
         if (recursion) {
            for (EnumFacing face : EnumFacing.HORIZONTALS) {
               if (rand.nextFloat() < 0.075F) {
                  this.genColumnDown(world, rand, position.offset(face), maxBlocks, block, false);
               }
            }
         }
      }
   }

   public void genFernRoot(World world, Random rand, BlockPos position, int maxBlocks, Block block, EnumFacing down, EnumFacing[] otherDirections) {
      if (maxBlocks > 0) {
         world.setBlockState(position, block.getDefaultState(), 2);
         boolean posChanged = false;
         if (rand.nextFloat() < 0.15F) {
            EnumFacing face = otherDirections[rand.nextInt(4)];
            BlockPos newposition = position.offset(face);
            if (GenerationHelper.isReplaceable(world, newposition) && !GetMOP.collidesWithBlockExcept(world, newposition, block, face.getOpposite())) {
               position = newposition;
               posChanged = true;
            }
         }

         if (!posChanged) {
            BlockPos newposition = position.down();
            if (GenerationHelper.isReplaceable(world, newposition)) {
               position = newposition;
               posChanged = true;
            } else {
               for (EnumFacing face : otherDirections) {
                  newposition = position.offset(face);
                  if (GenerationHelper.isReplaceable(world, newposition) && !GetMOP.collidesWithBlockExcept(world, newposition, block, face.getOpposite())) {
                     position = newposition;
                     posChanged = true;
                     break;
                  }
               }

               if (!posChanged) {
                  for (EnumFacing facex : otherDirections) {
                     newposition = position.offset(facex);
                     if (GenerationHelper.isReplaceable(world, newposition)) {
                        position = newposition;
                        posChanged = true;
                        break;
                     }
                  }
               }
            }
         }

         if (posChanged) {
            this.genFernRoot(world, rand, position, maxBlocks - 1, block, down, otherDirections);
            if (this.maxFernRootMultiples > 0 && rand.nextFloat() < 0.1F) {
               BlockPos newposition = position.offset(otherDirections[rand.nextInt(4)]);
               if (GenerationHelper.isReplaceable(world, newposition)) {
                  this.maxFernRootMultiples--;
                  this.genFernRoot(world, rand, newposition, maxBlocks - 1, block, down, otherDirections);
               }
            }
         }
      }
   }

   public void generateOldFernTree(World world, Random rand, BlockPos position, int fernHeight, int branchLength, Block stone, Block wood) {
      int stoneHeight = 2 + rand.nextInt(2 + fernHeight / 5);
      BlockPos pos = position;

      for (int i = 0; i < stoneHeight; i++) {
         world.setBlockState(pos, stone.getDefaultState(), 2);

         for (EnumFacing face : EnumFacing.HORIZONTALS) {
            if (rand.nextFloat() < 0.5F) {
               this.genColumnDown(world, rand, pos.offset(face), stoneHeight, wood, true);
            }
         }

         pos = pos.up();
      }

      for (EnumFacing facex : EnumFacing.HORIZONTALS) {
         if (rand.nextFloat() < 0.55F) {
            this.maxFernRootMultiples = 3;
            this.genFernRoot(world, rand, pos.offset(facex), stoneHeight * 2, this.stem, EnumFacing.DOWN, EnumFacing.HORIZONTALS);
         }
      }

      EnumFacing bend = EnumFacing.HORIZONTALS[rand.nextInt(4)];

      for (int i = 0; i < fernHeight; i++) {
         if (rand.nextFloat() < 0.15F) {
            world.setBlockState(pos, this.stem.getDefaultState(), 2);
            pos = pos.offset(bend);
         } else {
            world.setBlockState(pos, this.stem.getDefaultState(), 2);

            for (EnumFacing facexx : EnumFacing.HORIZONTALS) {
               if (rand.nextFloat() < 0.05F) {
                  BlockPos leafPos = pos.offset(facexx);
                  if (GenerationHelper.isReplaceable(world, leafPos)) {
                     world.setBlockState(leafPos, this.leafState(facexx, true), 2);
                  }
               }
            }

            pos = pos.up();
         }
      }

      world.setBlockState(pos, this.stem.getDefaultState(), 2);
      int branchLengthRandomness = 1;
      if (rand.nextFloat() < 0.2) {
         branchLengthRandomness = 2 + rand.nextInt(3);
      }

      this.generateFernBush(world, rand, pos, 2 + rand.nextInt(3), branchLength, branchLengthRandomness, EnumFacing.UP);
   }

   public void generateFernBush(World world, Random rand, BlockPos position, int fernHeight, int branchLength, int branchLengthRandomness, EnumFacing facing) {
      EnumFacing[] facingArray;
      if (facing.getAxis() == Axis.Y) {
         facingArray = EnumFacing.HORIZONTALS;
      } else if (facing.getAxis() == Axis.X) {
         facingArray = GetMOP.ZY_TICALS;
      } else {
         facingArray = GetMOP.XY_TICALS;
      }

      int arrayi = 0;
      int generatedBranches = 0;

      for (int i = 1; i <= fernHeight; i++) {
         BlockPos pos = position.offset(facing, i);
         world.setBlockState(pos, this.stem.getDefaultState(), 2);
         boolean shouldRotate = this.shouldRotate(facing, facingArray[arrayi]);
         this.generateBranch(world, rand, pos, branchLength + rand.nextInt(branchLengthRandomness), facingArray[arrayi], shouldRotate);
         this.generateBranch(world, rand, pos, branchLength + rand.nextInt(branchLengthRandomness), facingArray[arrayi].getOpposite(), shouldRotate);
         generatedBranches += 2;
         if (generatedBranches == 4) {
            branchLength = MathHelper.floor(branchLength / 2.0F);
            if (branchLength <= 0) {
               break;
            }
         }

         arrayi = GetMOP.next(arrayi, 1, 4);
      }
   }

   public void generateBranch(World world, Random rand, BlockPos position, int length, EnumFacing facing, boolean rotated) {
      Axis mainAxis;
      if (facing.getAxis() == Axis.Y) {
         if (rotated) {
            mainAxis = Axis.X;
         } else {
            mainAxis = Axis.Z;
         }
      } else if (facing.getAxis() == Axis.X) {
         if (rotated) {
            mainAxis = Axis.Z;
         } else {
            mainAxis = Axis.Y;
         }
      } else if (rotated) {
         mainAxis = Axis.X;
      } else {
         mainAxis = Axis.Y;
      }

      for (int i = 1; i < length; i++) {
         BlockPos posOffset = position.offset(facing, i);
         if (!GenerationHelper.isReplaceable(world, posOffset)) {
            length = i - 1;
            break;
         }

         world.setBlockState(posOffset, this.stem.getDefaultState(), 2);
      }

      if (length > 0) {
         world.setBlockState(position.offset(facing, length), this.leafState(facing, rotated), 2);
         IBlockState rightLeafState = this.rotateFernAround(facing, rotated, mainAxis, false);
         IBlockState leftLeafState = this.rotateFernAround(facing, rotated, mainAxis, true);
         EnumFacing rightDirection = GetMOP.rotate(facing, false, mainAxis);
         EnumFacing leftDirection = GetMOP.rotate(facing, true, mainAxis);
         if (length - 1 > 0) {
            world.setBlockState(position.offset(facing, length - 1).offset(rightDirection), rightLeafState, 2);
         }

         if (length - 2 > 0) {
            world.setBlockState(position.offset(facing, length - 2).offset(leftDirection), leftLeafState, 2);
         }

         if (length - 3 > 0) {
            int tipSize = 1;
            boolean shouldIncrementSize = false;
            IBlockState backState = this.leafState(facing.getOpposite(), rotated);
            IBlockState forwardState = this.leafState(facing, rotated);

            for (int i = length - 3; i > 0; i -= 2) {
               if (shouldIncrementSize) {
                  this.shootTip(
                     world, position.offset(facing, i), leftDirection, tipSize, leftLeafState, facing.getOpposite(), backState, facing, forwardState
                  );
               } else {
                  this.shootTip(
                     world, position.offset(facing, i), rightDirection, tipSize, rightLeafState, facing, forwardState, facing.getOpposite(), backState
                  );
               }

               if (shouldIncrementSize) {
                  tipSize++;
                  shouldIncrementSize = false;
               } else {
                  shouldIncrementSize = true;
               }
            }
         }
      }
   }

   public void shootTip(
      World world,
      BlockPos position,
      EnumFacing direction,
      int length,
      IBlockState tipState,
      EnumFacing leftDirection,
      IBlockState leftState,
      EnumFacing rightDirection,
      IBlockState rightState
   ) {
      boolean left = false;

      for (int i = 1; i <= length; i++) {
         BlockPos offpos = position.offset(direction, i);
         if (!GenerationHelper.isReplaceable(world, offpos)) {
            length = i - 1;
            break;
         }

         world.setBlockState(offpos, this.stem.getDefaultState(), 2);
         if (left) {
            world.setBlockState(offpos.offset(leftDirection), leftState, 2);
         } else {
            world.setBlockState(offpos.offset(rightDirection), rightState, 2);
         }

         left = !left;
      }

      BlockPos offpos2 = position.offset(direction, length + 1);
      if (GenerationHelper.isReplaceable(world, offpos2)) {
         world.setBlockState(offpos2, tipState, 2);
      }
   }

   public IBlockState leafState(EnumFacing facing, boolean rotated) {
      return this.leaf.getDefaultState().withProperty(ElectrofernLeaves.FACING, facing).withProperty(ElectrofernLeaves.ROTATED, rotated);
   }

   public IBlockState rotateFernAround(EnumFacing facing, boolean rotated, Axis axis, boolean clockwise) {
      if (axis == Axis.Y) {
         return facing != EnumFacing.UP && facing != EnumFacing.DOWN
            ? this.leafState(clockwise ? facing.rotateY() : facing.rotateYCCW(), rotated)
            : this.leafState(facing, !rotated);
      } else if (axis == Axis.Z) {
         return facing.getAxis() == axis ? this.leafState(facing, !rotated) : this.leafState(GetMOP.rotateZ(facing, clockwise), !rotated);
      } else if (axis == Axis.X) {
         return facing.getAxis() == axis ? this.leafState(facing, !rotated) : this.leafState(GetMOP.rotateX(facing, clockwise), rotated);
      } else {
         return null;
      }
   }

   public boolean shouldRotate(EnumFacing mainDirection, EnumFacing leafDirection) {
      return mainDirection.getAxis() == Axis.Z && leafDirection.getAxis() == Axis.X ? true : mainDirection.getAxis() == Axis.X;
   }
}
