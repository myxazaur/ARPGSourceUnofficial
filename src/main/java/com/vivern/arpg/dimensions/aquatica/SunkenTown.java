package com.vivern.arpg.dimensions.aquatica;

import com.vivern.arpg.blocks.BlockSeaweed;
import com.vivern.arpg.blocks.Pilaster;
import com.vivern.arpg.dimensions.generationutils.GenerationHelper;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.mobs.AbstractMob;
import com.vivern.arpg.mobs.AquaticaMobsPack;
import com.vivern.arpg.tileentity.TileMonsterSpawner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;

public class SunkenTown {
   public World world;
   public Random rand;
   public ArrayList<BlockPos> usedPoses = new ArrayList<>();
   public ArrayList<BlockPos> freeRoofPoses = new ArrayList<>();
   public ArrayList<int[]> rememberedMobs = new ArrayList<>();
   public int corridorTypesCount = 3;
   public int samples = 120;
   public int distBound = 64;
   public BlockPos center;
   public float columnRadius = 2.5F;
   public int minimumPlatforms = 4;

   public SunkenTown(World world, Random rand, BlockPos center) {
      this.world = world;
      this.rand = rand;
      this.center = center;
   }

   public void rememberMobAt(BlockPos pos, int entityType, int spawnRange, int spawnHeight) {
      this.rememberedMobs.add(new int[]{pos.getX(), pos.getY(), pos.getZ(), entityType, spawnRange, spawnHeight});
   }

   public void generateSunkenTown() {
      this.generateSunkenTown(this.center);
   }

   public void generateSunkenTown(BlockPos pos) {
      BlockSeaweed.canSeaweedFall = false;
      this.setHall(pos);
      this.samples--;

      for (int i = 0; i < 4; i++) {
         EnumFacing face = EnumFacing.byHorizontalIndex(i);
         this.generateUnderground(pos.offset(face, 17), face, 0);
      }

      Collections.shuffle(this.freeRoofPoses);

      for (BlockPos posroof : this.freeRoofPoses) {
         if ((this.minimumPlatforms > 0 || this.rand.nextFloat() < 0.25F)
            && this.isPosFree(posroof.up(22))
            && !this.world.getBlockState(posroof.up(12)).getMaterial().blocksMovement()) {
            this.setPlatform(posroof.up(10));
         }
      }

      for (int[] ints : this.rememberedMobs) {
         this.spawnMobAt(new BlockPos(ints[0], ints[1], ints[2]), ints[3], ints[4], ints[5]);
      }

      BlockSeaweed.canSeaweedFall = true;
   }

   public boolean isPosFree(BlockPos pos) {
      for (BlockPos poss : this.usedPoses) {
         if (poss.equals(pos)) {
            return false;
         }
      }

      return true;
   }

   public boolean isPosGrounded(BlockPos pos) {
      GetMOP.BlockTraceResult res = GetMOP.blockTrace(this.world, pos.down(), EnumFacing.DOWN, 30, GetMOP.SOLID_BLOCKS);
      return res != null;
   }

   public int getDistToCenter(BlockPos pos) {
      return Math.max(Math.abs(pos.getX() - this.center.getX()), Math.abs(pos.getZ() - this.center.getZ()));
   }

   public void generateUnderground(BlockPos pos, EnumFacing direction, int corridorPrevious) {
      if (this.samples > 0) {
         if (this.isPosFree(pos) && this.isPosGrounded(pos)) {
            float distModifier = MathHelper.clamp(2.0F - (float)this.getDistToCenter(pos) / this.distBound, 0.0F, 1.0F);
            if (this.rand.nextFloat() < 0.32 * distModifier) {
               if (this.rand.nextFloat() < 0.8) {
                  this.setHall(pos);
                  this.buildColumn(pos.down(), this.columnRadius);
               } else {
                  this.setTower(pos);
                  this.buildColumn(pos.down(), this.columnRadius);
               }

               this.samples--;

               for (int i = 0; i < 4; i++) {
                  if (this.rand.nextFloat() < 0.6F) {
                     EnumFacing face = EnumFacing.byHorizontalIndex(i);
                     if (face != direction.getOpposite()) {
                        this.generateUnderground(pos.offset(face, 17), face, 0);
                     }
                  }
               }
            } else if (this.rand.nextFloat() < 0.55 * distModifier) {
               int corridorType = corridorPrevious == 0 ? this.rand.nextInt(this.corridorTypesCount) + 1 : corridorPrevious;
               this.setCorridor(pos, corridorType, direction.getAxis() == Axis.Z);
               this.buildColumn(pos.down(), this.columnRadius);
               this.samples--;
               this.generateUnderground(pos.offset(direction, 17), direction, corridorType);
            } else if (this.rand.nextFloat() < 0.35 * distModifier) {
               this.setElevator(pos);
               this.buildColumn(pos.down(), this.columnRadius);
               this.samples--;

               for (int ix = 0; ix < 4; ix++) {
                  if (this.rand.nextFloat() < 0.45F) {
                     EnumFacing face = EnumFacing.byHorizontalIndex(ix);
                     if (face != direction.getOpposite()) {
                        this.generateUnderground(pos.offset(face, 17), face, 0);
                     }
                  }
               }

               BlockPos posup = pos.up(22);

               for (int ixx = 0; ixx < 4; ixx++) {
                  if (this.rand.nextFloat() < 0.65F) {
                     EnumFacing face = EnumFacing.byHorizontalIndex(ixx);
                     if (face != direction.getOpposite()) {
                        this.generateUnderground(posup.offset(face, 17), face, 0);
                     }
                  }
               }
            } else {
               this.setEnd(pos, direction);
               this.samples--;
            }
         }
      }
   }

   public void setPlatform(BlockPos pos) {
      this.usedPoses.add(pos);
      int yoffset = 0;
      int type = this.rand.nextInt(7) + 1;
      if (type == 1) {
         yoffset = -1;
      }

      if (type == 2) {
         yoffset = -3;
      }

      if (type == 3) {
         yoffset = -2;
      }

      if (type == 4) {
         yoffset = 0;
      }

      if (type == 5) {
         yoffset = -3;
      }

      if (type == 6) {
         yoffset = 0;
      }

      if (type == 7) {
         yoffset = 0;
      }

      this.minimumPlatforms--;
      GenerationHelper.placeStruct(
         this.world, pos, this.rand, ":sunken_town_platform_" + type, 8, yoffset, this.rand.nextInt(4), ReplacersAquatica.replacerSunkenTown
      );
      this.rememberMobAt(pos.up(), 0, 5, 3);
   }

   public void setHall(BlockPos pos) {
      this.usedPoses.add(pos);
      this.freeRoofPoses.add(pos);
      int yoffset = 0;
      int type = this.rand.nextInt(1) + 1;
      if (type == 1) {
         yoffset = 0;
      }

      GenerationHelper.placeStruct(
         this.world, pos, this.rand, ":sunken_town_hall_" + type, 8, yoffset, this.rand.nextInt(4), ReplacersAquatica.replacerSunkenTown
      );
      this.decorateWalls(pos);
      this.rememberMobAt(pos.up(), 0, 3, 3);
   }

   public void setElevator(BlockPos pos) {
      BlockPos posup = pos.up(22);
      this.usedPoses.add(pos);
      this.usedPoses.add(posup);
      this.freeRoofPoses.add(posup);
      int yoffset = 0;
      int type = 1;
      GenerationHelper.placeStruct(
         this.world, pos, this.rand, ":sunken_town_elevator_" + type, 8, yoffset, this.rand.nextInt(4), ReplacersAquatica.replacerSunkenTown
      );
      this.decorateWalls(pos);
      this.decorateWalls(posup);
   }

   public void setTower(BlockPos pos) {
      this.usedPoses.add(pos);
      int yoffset = 0;
      int type = this.rand.nextInt(1) + 1;
      if (type == 1) {
         yoffset = 0;
      }

      GenerationHelper.placeStruct(
         this.world, pos, this.rand, ":sunken_town_base_" + type, 8, yoffset, this.rand.nextInt(4), ReplacersAquatica.replacerSunkenTown
      );
      yoffset = 5 + this.rand.nextInt(8);
      type = yoffset * 4 + 30;
      int levels = type / 22;

      for (int i = 1; i <= levels; i++) {
         this.usedPoses.add(pos.up(i * 22));
      }

      for (int i = 0; i < yoffset; i++) {
         BlockPos posup = pos.up(i * 4 + 10);
         int typex = this.rand.nextInt(3) + 1;
         GenerationHelper.placeStruct(
            this.world, posup, this.rand, ":sunken_town_tube_" + typex, 6, 0, this.rand.nextInt(4), ReplacersAquatica.replacerSunkenTown
         );
      }

      BlockPos posup = pos.up(yoffset * 4 + 10);
      int typex = this.rand.nextInt(6) + 1;
      GenerationHelper.placeStruct(
         this.world, posup, this.rand, ":sunken_town_tower_" + typex, 6, 0, this.rand.nextInt(4), ReplacersAquatica.replacerSunkenTown
      );
      this.decorateWalls(pos);
      this.rememberMobAt(posup, 0, 2, 3);
      this.rememberMobAt(pos.up(), 0, 3, 3);
   }

   public void setCorridor(BlockPos pos, int type, boolean axisZ) {
      this.usedPoses.add(pos);
      this.freeRoofPoses.add(pos);
      int yoffset = 0;
      if (type == 1) {
         yoffset = -1;
      }

      if (type == 2) {
         yoffset = 0;
      }

      if (type == 3) {
         yoffset = 0;
      }

      GenerationHelper.placeStruct(
         this.world,
         pos,
         this.rand,
         ":sunken_town_corridor_" + type,
         9,
         yoffset,
         axisZ ? (this.rand.nextFloat() < 0.5F ? 1 : 3) : (this.rand.nextFloat() < 0.5F ? 0 : 2),
         ReplacersAquatica.replacerSunkenTown
      );
      if (this.rand.nextFloat() < 0.3F) {
         this.rememberMobAt(pos.up(), 0, 3, 3);
      }
   }

   public void setEnd(BlockPos pos, EnumFacing facing) {
      this.usedPoses.add(pos);
      int yoffset = 0;
      int type = this.rand.nextInt(2) + 1;
      int rotation = 0;
      if (facing == EnumFacing.EAST) {
         rotation = 2;
      }

      if (facing == EnumFacing.SOUTH) {
         rotation = 1;
      }

      if (facing == EnumFacing.WEST) {
         rotation = 0;
      }

      if (facing == EnumFacing.NORTH) {
         rotation = 3;
      }

      GenerationHelper.placeStruct(this.world, pos, this.rand, ":sunken_town_end_" + type, 9, yoffset, rotation, ReplacersAquatica.replacerSunkenTown);
   }

   public void buildColumn(BlockPos pos, float radius) {
      GetMOP.BlockTraceResult result = GetMOP.blockTrace(this.world, pos, EnumFacing.DOWN, 250, GetMOP.SOLID_BLOCKS);
      int ybound = result.impactState.getBlock() != BlocksRegister.PEARLESCENTBRICKS
            && result.impactState.getBlock() != BlocksRegister.PEARLESCENTPILASTER
            && result.impactState.getBlock() != BlocksRegister.PEARLESCENTSTAIRS
         ? 1
         : result.pos.getY();
      float radiusSq = radius * radius;
      int radiusbig = (int)Math.ceil(radius);

      for (int x = -radiusbig; x <= radiusbig; x++) {
         for (int z = -radiusbig; z <= radiusbig; z++) {
            float distsq = x * x + z * z;
            if (distsq <= radiusSq) {
               for (int y = pos.getY(); y > ybound; y--) {
                  BlockPos poss = new BlockPos(pos.getX() + x, y, pos.getZ() + z);
                  if (y < pos.getY() - 3 && this.world.getBlockState(poss).isFullCube()) {
                     break;
                  }

                  this.world.setBlockState(poss, this.getRandomBrick(distsq > radiusSq - 2.0F), 2);
               }
            }
         }
      }
   }

   public IBlockState getRandomBrick(boolean usecoralBrick) {
      if (this.rand.nextFloat() < 0.9) {
         return BlocksRegister.PEARLESCENTBRICKS.getDefaultState();
      } else if (this.rand.nextFloat() < 0.3) {
         return BlocksRegister.CORALWHITE.getDefaultState();
      } else if (usecoralBrick && this.rand.nextFloat() < 0.4) {
         return BlocksRegister.CORALBRICKS.getDefaultState();
      } else if (this.rand.nextFloat() < 0.4) {
         return BlocksRegister.CHALKROCK.getDefaultState();
      } else if (this.rand.nextFloat() < 0.4) {
         return BlocksRegister.SEASTONE.getDefaultState();
      } else {
         return this.rand.nextFloat() < 0.6
            ? BlocksRegister.SHELLROCK.getDefaultState()
            : BlocksRegister.PEARLESCENTPILASTER.getDefaultState().withProperty(Pilaster.FACING, EnumFacing.byIndex(this.rand.nextInt(6)));
      }
   }

   public void decorateWalls(BlockPos pos) {
      for (int x = -9; x <= 9; x++) {
         for (int z = -9; z <= 9; z++) {
            if (x == 9 || x == -9 || z == 9 || z == -9) {
               for (int y = 0; y <= 8; y++) {
                  if (this.rand.nextFloat() < 0.65) {
                     BlockPos poss = pos.add(x, y, z);
                     IBlockState state = this.world.getBlockState(poss);
                     if (state.getBlock() == BlocksRegister.SHELLROCK
                        || state.getBlock() == BlocksRegister.CHALKROCK
                        || state.getBlock() == BlocksRegister.STROMATOLITE) {
                        if (poss.getY() > AquaticaChunkGenerator.sealvl) {
                           this.world.setBlockToAir(poss);
                        } else if (this.rand.nextFloat() < 0.5) {
                           this.world.setBlockState(poss, Blocks.WATER.getDefaultState());
                        } else if (this.rand.nextFloat() < 0.65) {
                           this.world.setBlockState(poss, BlocksRegister.SEASTONE.getDefaultState());
                        } else if (this.rand.nextFloat() < 0.6) {
                           this.world.setBlockState(poss, BlocksRegister.CORALPINK.getDefaultState());
                        } else {
                           this.world
                              .setBlockState(
                                 poss,
                                 state.getBlock() == BlocksRegister.STROMATOLITE
                                    ? BlocksRegister.CHALKROCK.getDefaultState()
                                    : BlocksRegister.STROMATOLITE.getDefaultState()
                              );
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void spawnMobAt(BlockPos pos, int entityType, int spawnRange, int spawnHeight) {
      boolean landGround = true;
      double d0 = pos.getX() + (this.world.rand.nextDouble() - this.world.rand.nextDouble()) * spawnRange + 0.5;
      double d1 = pos.getY();
      double d2 = pos.getZ() + (this.world.rand.nextDouble() - this.world.rand.nextDouble()) * spawnRange + 0.5;
      d1 = TileMonsterSpawner.getLandHeight(this.world, new BlockPos(d0, d1 + this.world.rand.nextInt(spawnHeight), d2), spawnHeight * 2);
      EntityLiving entity = null;
      if (entityType == 0) {
         // FIX: Change type of variable `var20` to AbstractMob
         AbstractMob var20 = new AquaticaMobsPack.Mermaid(this.world);
         var20.setPosition(d0, d1, d2);
         if (var20 != null) {
            float yaw = this.world.rand.nextFloat() * 360.0F;
            var20.setLocationAndAngles(var20.posX, var20.posY, var20.posZ, yaw, 0.0F);

            for (int i = 0; i < 16 && this.world.collidesWithAnyBlock(var20.getEntityBoundingBox()); i++) {
               d0 = pos.getX() + (this.world.rand.nextDouble() - this.world.rand.nextDouble()) * spawnRange + 0.5;
               d1 = pos.getY();
               d2 = pos.getZ() + (this.world.rand.nextDouble() - this.world.rand.nextDouble()) * spawnRange + 0.5;
               d1 = TileMonsterSpawner.getLandHeight(this.world, new BlockPos(d0, d1 + this.world.rand.nextInt(spawnHeight), d2), spawnHeight * 2);
               var20.setLocationAndAngles(d0, d1, d2, yaw, 0.0F);
            }

            if (!this.world.collidesWithAnyBlock(var20.getEntityBoundingBox())) {
               AnvilChunkLoader.spawnEntity(var20, this.world);
               if (var20 instanceof AbstractMob) {
                  AbstractMob mob = var20;
                  mob.onInitialSpawn();
                  mob.canDropLoot = true;
               }

               var20.enablePersistence();
            }
         }
      }
   }
}
