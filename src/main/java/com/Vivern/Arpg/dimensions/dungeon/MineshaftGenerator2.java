package com.Vivern.Arpg.dimensions.dungeon;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class MineshaftGenerator2 {
   public int size;
   public int height;
   public int downlevel;
   public int samples = 0;
   public int genFromX;
   public int genFromZ;
   public int genToX;
   public int genToZ;
   public ChunkPrimer chunk;

   public MineshaftGenerator2(int size, int height, int tunnelLevelsDown, int samples, ChunkPrimer chunk) {
      this.size = size;
      this.height = height;
      this.downlevel = tunnelLevelsDown;
      this.samples = samples;
      this.chunk = chunk;
   }

   public MineshaftGenerator2 setGenRange(int genFromX, int genFromZ, int genToX, int genToZ) {
      this.genFromX = genFromX;
      this.genFromZ = genFromZ;
      this.genToX = genToX;
      this.genToZ = genToZ;
      return this;
   }

   public MineshaftGenerator2 setGenRange(ChunkPos chunk) {
      this.genFromX = chunk.getXStart();
      this.genFromZ = chunk.getZStart();
      this.genToX = chunk.getXEnd();
      this.genToZ = chunk.getZEnd();
      return this;
   }

   public static void onGenerateChunk(World world, int chunkX, int chunkZ, int level, ChunkPrimer chunkprimer) {
      int checkRadius = 20 + Math.min(level / 5, 10);

      for (int xx = -checkRadius; xx <= checkRadius; xx++) {
         for (int zz = -checkRadius; zz <= checkRadius; zz++) {
            int cX = chunkX + xx;
            int cZ = chunkZ + zz;
            Random rand = GetMOP.getBestRandomBasedOnCoordinates(cX, cZ, world.getSeed());
            if (rand.nextFloat() < 2.0F / (level + 30.0F)) {
               int height = 3 + rand.nextInt(2 + rand.nextInt(3));
               MineshaftGenerator2 gen = new MineshaftGenerator2(
                  2 + rand.nextInt(1 + rand.nextInt(1 + rand.nextInt(3))), height, 2 + rand.nextInt(5), 15 + rand.nextInt(45 + level), chunkprimer
               );
               int i = cX * 16 + rand.nextInt(16);
               int j = cZ * 16 + rand.nextInt(16);
               gen.setGenRange(new ChunkPos(chunkX, chunkZ));
               gen.mineshaftGenerate(world, rand, i, 10 + rand.nextInt(244 - height), j);
            }
         }
      }
   }

   public boolean isInGenRange(BlockPos pos) {
      return pos.getX() >= this.genFromX
         && pos.getX() <= this.genToX
         && pos.getZ() >= this.genFromZ
         && pos.getZ() <= this.genToZ;
   }

   public void mineshaftGenerate(World world, Random rand, int x, int y, int z) {
      BlockPos pos = new BlockPos(x, y, z);
      EnumFacing face = EnumFacing.HORIZONTALS[rand.nextInt(4)];
      this.placeCrossroad(world, rand, pos, this.size, this.height, face, 0.95F);
   }

   public void next(World world, Random rand, BlockPos pos, int size, int height, EnumFacing face) {
      this.samples--;
      if (this.samples > 0) {
         if (rand.nextFloat() < 0.3F) {
            this.placeCrossroad(world, rand, pos, size, height, face, 0.5F);
         } else if (rand.nextFloat() < 0.87F) {
            this.placeRoad(world, rand, pos, size, height, face);
         } else {
            this.placeLadder(world, rand, pos, size, height, this.downlevel);
         }
      }
   }

   public void placeRoad(World world, Random rand, BlockPos pos, int size, int height, EnumFacing face) {
      int sizesmall = size - 1;

      for (int x = -sizesmall; x <= sizesmall; x++) {
         for (int z = -sizesmall; z <= sizesmall; z++) {
            BlockPos xzpos = pos.add(x, 0, z);
            if (this.isInGenRange(xzpos)) {
               this.replaceToWoodOrShaft(world, xzpos);
               this.air(world, xzpos, height);
            }
         }
      }

      EnumFacing faceRotY = face.rotateY();

      for (int off = -sizesmall; off <= sizesmall; off++) {
         BlockPos xzpos = pos.offset(face, off);
         BlockPos xzpos1 = xzpos.offset(faceRotY, size);
         BlockPos xzpos2 = xzpos.offset(faceRotY, -size);
         if (this.isInGenRange(xzpos1)) {
            this.air(world, xzpos1, height);
         }

         if (this.isInGenRange(xzpos2)) {
            this.air(world, xzpos2, height);
         }
      }

      BlockPos possOffset = pos.offset(face, size);
      this.shaft(world, possOffset.offset(face.rotateY(), size), height - 1);
      this.shaft(world, possOffset.offset(face.rotateYCCW(), size), height - 1);

      for (int i = -size; i <= size; i++) {
         BlockPos fpos1 = possOffset.offset(faceRotY, i);
         if (this.isInGenRange(fpos1)) {
            this.replaceToWoodOrShaft(world, fpos1);
            this.setBlockState(fpos1.up(height), BlocksRegister.WOODENSHAFT.getDefaultState());
            if (Math.abs(i) < size) {
               this.air(world, fpos1, height - 1);
            }

            if (i == 0) {
               int h2 = height / 2 + (height % 2 == 0 ? 0 : 1);
               int nearDist = this.getNearestDistanceFloorOrTop(world, fpos1.up(h2), h2 + 1);
               if (nearDist > h2 + 1) {
                  for (int n = fpos1.getY() + height + 1; n <= fpos1.getY() + nearDist; n++) {
                     this.replaceToWoodOrShaft(world, new BlockPos(fpos1.getX(), n, fpos1.getZ()));
                  }

                  BlockPos posTOP = possOffset.up(nearDist + 1);

                  for (int ni = -size + 1; ni <= size - 1; ni++) {
                     BlockPos fpos2 = posTOP.offset(faceRotY, ni);
                     if (ni == 0 || this.isInGenRange(fpos2)) {
                        this.replaceToWoodOrShaft(world, fpos2);
                        this.setBlockState(fpos2.up(), BlocksRegister.ROTTENPLANKS.getDefaultState());
                     }
                  }
               }

               if (nearDist < 0) {
                  for (int n = fpos1.getY() - 2; n > fpos1.getY() + nearDist; n--) {
                     this.replaceToWoodOrShaft(world, new BlockPos(fpos1.getX(), n, fpos1.getZ()));
                  }

                  BlockPos posUNDER = possOffset.down();

                  for (int nix = -size + 1; nix <= size - 1; nix++) {
                     BlockPos fpos2 = posUNDER.offset(faceRotY, nix);
                     if (nix == 0 || this.isInGenRange(fpos2)) {
                        this.replaceToWoodOrShaft(world, fpos2);
                     }
                  }

                  this.setBlockState(
                     new BlockPos(fpos1.getX(), fpos1.getY() + nearDist, fpos1.getZ()), BlocksRegister.ROTTENPLANKS.getDefaultState()
                  );
               }
            }
         }
      }

      this.decorate(world, rand, pos, size, height, face);
      if (rand.nextFloat() < 0.87F) {
         this.next(world, rand, pos.offset(face, size * 2), size, height, face);
      }
   }

   public void placeCrossroad(World world, Random rand, BlockPos pos, int size, int height, EnumFacing prevFacing, float continueChance) {
      for (int x = -size; x <= size; x++) {
         for (int z = -size; z <= size; z++) {
            BlockPos xzpos = pos.add(x, 0, z);
            if (this.isInGenRange(xzpos)) {
               this.setBlockState(xzpos, BlocksRegister.ROTTENPLANKS.getDefaultState());
               BlockPos posupp = xzpos.up(height + 1);
               if (!this.getBlockState(posupp).getMaterial().isReplaceable()) {
                  this.setBlockState(posupp, BlocksRegister.ROTTENPLANKS.getDefaultState());
               }

               if (x != -size && x != size && z != -size && z != size) {
                  this.air(world, xzpos, height);
               } else {
                  this.setBlockState(xzpos.up(height), BlocksRegister.WOODENSHAFT.getDefaultState());
                  this.air(world, xzpos, height - 1);
               }
            }
         }
      }

      this.shaft(world, pos.add(size, 0, size), height - 1);
      this.shaft(world, pos.add(-size, 0, size), height - 1);
      this.shaft(world, pos.add(size, 0, -size), height - 1);
      this.shaft(world, pos.add(-size, 0, -size), height - 1);
      this.decorate(world, rand, pos, size, height, null);

      for (EnumFacing facingNext : EnumFacing.HORIZONTALS) {
         if (prevFacing != facingNext.getOpposite() && rand.nextFloat() < continueChance) {
            this.next(world, rand, pos.offset(facingNext, size * 2), size, height, facingNext);
         }
      }
   }

   public void placeLadder(World world, Random rand, BlockPos pos, int size, int height, int downLevels) {
      int downAdd = -(downLevels * 2 + height) + 1;
      if (pos.getY() + downAdd >= 3) {
         EnumFacing facingLadder = EnumFacing.HORIZONTALS[rand.nextInt(4)];
         int smallsize = size - 1;

         for (int x = -size; x <= size; x++) {
            for (int z = -size; z <= size; z++) {
               BlockPos xzpos = pos.add(x, 0, z);
               if (this.isInGenRange(xzpos)) {
                  boolean xb = x == -smallsize || x == smallsize;
                  boolean zb = z == -smallsize || z == smallsize;
                  if (!xb && !zb) {
                     for (int i = 0; i < downLevels; i++) {
                        this.setBlockState(xzpos.down(i * 2), Blocks.AIR.getDefaultState());
                        this.setBlockState(xzpos.down(i * 2 + 1), Blocks.AIR.getDefaultState());
                     }
                  } else {
                     for (int i = 0; i < downLevels; i++) {
                        this.setBlockState(xzpos.down(i * 2), BlocksRegister.WOODENSHAFT.getDefaultState());
                        if (xb && zb) {
                           this.setBlockState(xzpos.down(i * 2 + 1), BlocksRegister.WOODENSHAFT.getDefaultState());
                        } else {
                           this.setBlockState(xzpos.down(i * 2 + 1), Blocks.AIR.getDefaultState());
                        }
                     }
                  }

                  if (x != -size && x != size && z != -size && z != size) {
                     this.air(world, xzpos, height);
                  } else {
                     this.setBlockState(xzpos, BlocksRegister.ROTTENPLANKS.getDefaultState());
                     this.setBlockState(xzpos.up(height), BlocksRegister.WOODENSHAFT.getDefaultState());
                     this.air(world, xzpos, height - 1);
                  }
               }
            }
         }

         this.shaft(world, pos.add(size, 0, size), height - 1);
         this.shaft(world, pos.add(-size, 0, size), height - 1);
         this.shaft(world, pos.add(size, 0, -size), height - 1);
         this.shaft(world, pos.add(-size, 0, -size), height - 1);

         for (int x = -size; x <= size; x++) {
            for (int zx = -size; zx <= size; zx++) {
               BlockPos xzpos = pos.add(x, downAdd, zx);
               if (this.isInGenRange(xzpos)) {
                  this.setBlockState(xzpos, BlocksRegister.ROTTENPLANKS.getDefaultState());
                  if (x != -size && x != size && zx != -size && zx != size) {
                     this.air(world, xzpos, height);
                  } else {
                     this.setBlockState(xzpos.up(height + 1), BlocksRegister.ROTTENPLANKS.getDefaultState());
                     this.setBlockState(xzpos.up(height), BlocksRegister.WOODENSHAFT.getDefaultState());
                     this.air(world, xzpos, height - 1);
                  }
               }
            }
         }

         this.shaft(world, pos.add(size, downAdd, size), height - 1);
         this.shaft(world, pos.add(-size, downAdd, size), height - 1);
         this.shaft(world, pos.add(size, downAdd, -size), height - 1);
         this.shaft(world, pos.add(-size, downAdd, -size), height - 1);
         BlockPos posLadderPlanks = pos.offset(facingLadder, size - 1);
         BlockPos posLadder = pos.offset(facingLadder, size - 2);
         EnumFacing faceLadderOpp = facingLadder.getOpposite();
         if (this.isInGenRange(posLadderPlanks)) {
            for (int ix = 0; ix < downLevels; ix++) {
               this.setBlockState(posLadderPlanks.down(ix * 2), BlocksRegister.ROTTENPLANKS.getDefaultState());
               this.setBlockState(posLadderPlanks.down(ix * 2 + 1), BlocksRegister.ROTTENPLANKS.getDefaultState());
            }

            for (int ix = downLevels * 2; ix < -downAdd; ix++) {
               this.setBlockState(posLadderPlanks.down(ix), BlocksRegister.ROTTENPLANKS.getDefaultState());
            }
         }

         if (this.isInGenRange(posLadder)) {
            for (int ix = 0; ix < downLevels; ix++) {
               this.setBlockState(
                  posLadder.down(ix * 2), Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, faceLadderOpp)
               );
               this.setBlockState(
                  posLadder.down(ix * 2 + 1), Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, faceLadderOpp)
               );
            }

            for (int ix = downLevels * 2; ix < -downAdd; ix++) {
               this.setBlockState(posLadder.down(ix), Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, faceLadderOpp));
            }
         }

         BlockPos downfloorpos = pos.up(downAdd);
         this.decorate(world, rand, downfloorpos, size, height, null);
         EnumFacing facingNext = EnumFacing.HORIZONTALS[rand.nextInt(4)];
         this.next(world, rand, downfloorpos.offset(facingNext, size * 2), size, height, facingNext);
      }
   }

   public int getNearestDistanceFloorOrTop(World world, BlockPos pos, int starter) {
      while (true) {
         BlockPos poss = pos.add(0, starter, 0);
         IBlockState state = this.getBlockState(poss);
         if (!state.isFullBlock() && poss.getY() <= 255) {
            BlockPos poss2 = pos.add(0, -starter, 0);
            IBlockState state2 = this.getBlockState(poss2);
            if (!state2.isFullBlock() && poss2.getY() >= 0) {
               starter++;
               continue;
            }

            return -starter;
         }

         return starter;
      }
   }

   public void shaft(World world, BlockPos pos, int height) {
      if (this.isInGenRange(pos)) {
         for (int y = 1; y <= height; y++) {
            this.setBlockState(pos.up(y), BlocksRegister.WOODENSHAFT.getDefaultState());
         }
      }
   }

   public void air(World world, BlockPos pos, int height) {
      for (int y = 1; y <= height; y++) {
         this.setBlockState(pos.up(y), Blocks.AIR.getDefaultState());
      }
   }

   public void replaceToWoodOrShaft(World world, BlockPos pos) {
      IBlockState state = this.getBlockState(pos);
      if (state.getMaterial().isLiquid()) {
         this.setBlockState(pos, BlocksRegister.ROTTENPLANKS.getDefaultState());
      } else if (state.getMaterial().isReplaceable()) {
         this.setBlockState(pos, BlocksRegister.WOODENSHAFT.getDefaultState());
      }
   }

   public IBlockState getBlockState(BlockPos pos) {
      int i = pos.getX() & 15;
      int k = pos.getZ() & 15;
      return this.chunk.getBlockState(i, MathHelper.clamp(pos.getY(), 0, 255), k);
   }

   public void setBlockState(BlockPos pos, IBlockState state) {
      int i = pos.getX() & 15;
      int k = pos.getZ() & 15;
      this.chunk.setBlockState(i, MathHelper.clamp(pos.getY(), 0, 255), k, state);
   }

   public void decorate(World world, Random rand, BlockPos pos, int size, int height, @Nullable EnumFacing face) {
      int style = rand.nextInt(2);
      if (face != null) {
         EnumFacing faceR = rand.nextFloat() < 0.5F ? face.rotateY() : face.rotateYCCW();
         EnumFacing faceRopp = faceR.getOpposite();
         if (style == 0) {
            BlockPos poss = pos.offset(faceR, size).up();
            int decorAmount = Math.max(5 + rand.nextInt(6 + size * size) + (int)(rand.nextGaussian() * 2.0), 2);
            int styleBlock = rand.nextInt(28);

            for (int i = 0; i < decorAmount; i++) {
               int randOffset = rand.nextInt(size * 2 + 1) - size;
               int randOffset2 = (int)(Math.abs(rand.nextGaussian()) * size / 2.0);
               int randOffset3 = (int)(Math.abs(rand.nextGaussian()) * height / 2.0);
               BlockPos finalPos = new BlockPos(
                  poss.getX() + randOffset * face.getXOffset() + randOffset2 * faceRopp.getXOffset(),
                  poss.getY() + randOffset3,
                  poss.getZ() + randOffset * face.getZOffset() + randOffset2 * faceRopp.getZOffset()
               );
               if (this.isInGenRange(finalPos) && this.getBlockState(finalPos.down()).isTopSolid()) {
                  this.placeDecorBlock(rand, finalPos, styleBlock);
               }
            }
         }

         if (style == 1) {
            BlockPos coordsCR = DimensionDungeon.getCaveRegionCoords(pos);
            int number = DimensionDungeon.getCaveRegionNumberFromCoord(coordsCR.getX(), coordsCR.getZ());
            if (rand.nextFloat() < 0.4F + number * 0.1F) {
               BlockPos poss = pos.offset(faceR, size).up();
               if (this.isInGenRange(poss)) {
                  this.setBlockState(poss, Blocks.ENDER_CHEST.getDefaultState().withProperty(BlockEnderChest.FACING, faceRopp));
               }
            }
         }
      }
   }

   public void placeDecorBlock(Random rand, BlockPos pos, int styleBlock) {
      if (styleBlock >= 23) {
         this.setBlockState(pos, Blocks.WEB.getDefaultState());
      } else if (styleBlock >= 20) {
         this.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
      } else if (styleBlock >= 18) {
         this.setBlockState(pos, BlocksRegister.ROTTENPLANKS.getDefaultState());
      } else if (styleBlock >= 17) {
         this.setBlockState(pos, Blocks.COAL_ORE.getDefaultState());
      } else if (styleBlock >= 16) {
         this.setBlockState(pos, Blocks.IRON_ORE.getDefaultState());
      } else if (styleBlock >= 15) {
         this.setBlockState(pos, Blocks.GOLD_ORE.getDefaultState());
      } else if (styleBlock >= 14) {
         this.setBlockState(pos, Blocks.EMERALD_ORE.getDefaultState());
      } else if (styleBlock >= 13) {
         this.setBlockState(pos, Blocks.DIAMOND_ORE.getDefaultState());
      } else if (styleBlock >= 12) {
         this.setBlockState(pos, Blocks.COAL_BLOCK.getDefaultState());
      } else if (styleBlock >= 11) {
         this.setBlockState(pos, Blocks.GRAVEL.getDefaultState());
      } else if (styleBlock >= 10) {
         this.setBlockState(pos, BlocksRegister.ORERUBY.getDefaultState());
      } else if (styleBlock >= 9) {
         this.setBlockState(pos, BlocksRegister.ORECITRINE.getDefaultState());
      } else if (styleBlock >= 8) {
         this.setBlockState(pos, BlocksRegister.ORESAPPHIRE.getDefaultState());
      } else if (styleBlock >= 7) {
         this.setBlockState(pos, BlocksRegister.ORETOPAZ.getDefaultState());
      } else if (styleBlock >= 6) {
         this.setBlockState(pos, BlocksRegister.ORERHINESTONE.getDefaultState());
      } else if (styleBlock >= 5) {
         this.setBlockState(pos, BlocksRegister.OREAMETHYST.getDefaultState());
      } else if (styleBlock >= 3) {
         this.setBlockState(pos, Blocks.MOSSY_COBBLESTONE.getDefaultState());
      } else {
         this.setBlockState(pos, BlocksRegister.GREENGLOWINGMUSH.getDefaultState());
      }
   }

   public static int wrap(int value, int round) {
      int halfround = round / 2;
      value %= round;
      if (value >= halfround) {
         value -= round;
      }

      if (value < -halfround) {
         value += round;
      }

      return value;
   }
}
