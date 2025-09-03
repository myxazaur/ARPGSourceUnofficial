//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.dimensions.generationutils;

import com.Vivern.Arpg.main.GetMOP;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenAncientRuins extends WorldGenerator {
   public IBlockState[] bricks;
   public IBlockState column;
   public IBlockState columnBorder;
   public IBlockState slab;
   public IBlockState cobble;
   public int sizelast;
   public int ruinsSizeMin;
   public int ruinsSizeMax;
   public int lengthMin;
   public int lengthMax;
   public int bricksHeightMin;
   public int bricksHeightMax;
   public int columnHeightMin;
   public int columnHeightMax;

   public WorldGenAncientRuins(
      IBlockState[] bricks,
      @Nullable IBlockState column,
      IBlockState columnBorder,
      IBlockState slab,
      IBlockState cobble,
      int ruinsSizeMin,
      int ruinsSizeMax,
      int lengthMin,
      int lengthMax,
      int bricksHeightMin,
      int bricksHeightMax,
      int columnHeightMin,
      int columnHeightMax
   ) {
      this.bricks = bricks;
      this.column = column;
      this.columnBorder = columnBorder;
      this.slab = slab;
      this.cobble = cobble;
      this.ruinsSizeMin = ruinsSizeMin;
      this.ruinsSizeMax = ruinsSizeMax;
      this.lengthMin = lengthMin;
      this.lengthMax = lengthMax;
      this.bricksHeightMin = bricksHeightMin;
      this.bricksHeightMax = bricksHeightMax;
      this.columnHeightMin = columnHeightMin;
      this.columnHeightMax = columnHeightMax;
   }

   public boolean generate(World world, Random rand, BlockPos position) {
      this.sizelast = this.ruinsSizeMin + rand.nextInt(this.ruinsSizeMax - this.ruinsSizeMin + 1);
      int bricksHeight = this.bricksHeightMin + rand.nextInt(this.bricksHeightMax - this.bricksHeightMin + 1);
      int columnHeight = this.columnHeightMin + rand.nextInt(this.columnHeightMax - this.columnHeightMin + 1);
      if (position.getY() - bricksHeight <= 0) {
         bricksHeight = position.getY() - 1;
      }

      if (columnHeight + position.getY() >= 255) {
         columnHeight = 254 - position.getY();
      }

      this.next(
         world,
         rand,
         position,
         EnumFacing.HORIZONTALS[rand.nextInt(4)],
         this.lengthMin + rand.nextInt(this.lengthMax - this.lengthMin + 1),
         bricksHeight,
         columnHeight,
         this.sizelast
      );
      return true;
   }

   public void next(World world, Random rand, BlockPos position, EnumFacing prevface, int length, int bricksHeight, int columnHeight, int attempts) {
      if (attempts > 0 && this.sizelast > 0) {
         this.sizelast--;
         EnumFacing face = EnumFacing.HORIZONTALS[rand.nextInt(4)];
         if (face == prevface) {
            face = face.getOpposite();
         }

         EnumFacing faceR = face.rotateY();
         EnumFacing faceL = face.rotateYCCW();
         BlockPos pos = position.offset(face, length + 1);
         if (!this.hasSolidBlocks(world, pos, bricksHeight)) {
            attempts -= 2;
         }

         boolean prevSlabPlaced = false;
         if (this.placeColumn(world, rand, pos.up(), columnHeight) && rand.nextFloat() < 0.8F) {
            BlockPos posupClmn = pos.up(columnHeight + 2);
            world.setBlockState(posupClmn, this.slab, 2);
            if (rand.nextFloat() < 0.8F) {
               world.setBlockState(posupClmn.offset(prevface.getOpposite()), this.slab, 2);
               prevSlabPlaced = true;
            }

            if (rand.nextFloat() < 0.8F) {
               world.setBlockState(posupClmn.offset(face.getOpposite()), this.slab, 2);
            } else {
               prevSlabPlaced = false;
            }
         }

         int startSlabindex = 2 + (length <= 3 ? 0 : rand.nextInt(length - 3));
         if (length > 2) {
            for (int l = 2; l < length; l++) {
               BlockPos posf = position.offset(face, l);
               this.placeBricksDown(world, rand, posf, bricksHeight);
               this.placeBricksDown(world, rand, posf.offset(faceL), bricksHeight);
               this.placeBricksDown(world, rand, posf.offset(faceR), bricksHeight);
               if (prevSlabPlaced && l >= startSlabindex) {
                  world.setBlockState(posf.up(columnHeight + 2), this.slab, 2);
               }
            }
         }

         for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
               this.placeBricksDown(world, rand, pos.add(x, 0, z), bricksHeight);
            }
         }

         this.next(world, rand, pos, face.getOpposite(), length, bricksHeight, columnHeight, attempts - 1);
         if (rand.nextFloat() < 0.25F) {
            this.next(world, rand, pos, face.getOpposite(), length, bricksHeight, columnHeight, rand.nextInt(Math.max(this.sizelast, 1)));
         }
      }
   }

   public boolean hasSolidBlocks(World world, BlockPos position, int height) {
      for (int y = 1; y < height; y++) {
         if (world.getBlockState(position.down(y)).isTopSolid()) {
            return true;
         }
      }

      return false;
   }

   public boolean placeColumn(World world, Random rand, BlockPos position, int height) {
      world.setBlockState(position, this.columnBorder, 2);
      int dest = rand.nextInt(height * 2);

      for (int y = 1; y < height; y++) {
         if (y >= dest) {
            if (rand.nextFloat() < 0.8) {
               if (rand.nextFloat() < 0.8) {
                  for (int i = 0; i < Math.max(rand.nextInt(height - y), 1); i++) {
                     if (this.column == null) {
                        this.setBrick(
                           world, rand, GetMOP.getTrueHeight(world, position.add(rand.nextGaussian(), y, rand.nextGaussian())).up(), 0.0F
                        );
                     } else {
                        world.setBlockState(
                           GetMOP.getTrueHeight(world, position.add(rand.nextGaussian(), y, rand.nextGaussian())).up(), this.column, 2
                        );
                     }
                  }
               }

               world.setBlockState(
                  GetMOP.getTrueHeight(world, position.add(rand.nextGaussian(), y, rand.nextGaussian())).up(), this.columnBorder, 2
               );
            }

            return false;
         }

         if (this.column == null) {
            this.setBrick(world, rand, position.up(y), 0.0F);
         } else {
            world.setBlockState(position.up(y), this.column, 2);
         }
      }

      world.setBlockState(position.up(height), this.columnBorder, 2);
      return true;
   }

   public void placeBricksDown(World world, Random rand, BlockPos position, int height) {
      for (int y = 0; y < height; y++) {
         float destr = (float)y / height;
         if (rand.nextFloat() < destr / 2.0F) {
            break;
         }

         this.setBrick(world, rand, position.down(y), destr);
      }
   }

   public void setBrick(World world, Random rand, BlockPos position, float destroying) {
      if (rand.nextFloat() < destroying) {
         world.setBlockState(position, this.cobble, 2);
      } else {
         int indx = rand.nextInt(this.bricks.length);
         world.setBlockState(position, this.bricks[indx], 2);
      }
   }
}
