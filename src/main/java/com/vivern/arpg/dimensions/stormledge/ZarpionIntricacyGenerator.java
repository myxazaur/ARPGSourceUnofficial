package com.vivern.arpg.dimensions.stormledge;

import com.vivern.arpg.blocks.StormRack;
import com.vivern.arpg.dimensions.generationutils.GenerationHelper;
import com.vivern.arpg.loot.ListLootTable;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.mobs.NPCMobsPack;
import com.vivern.arpg.mobs.SpawnerTuners;
import com.vivern.arpg.tileentity.EnumChest;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class ZarpionIntricacyGenerator {
   public int centerHallSize;
   public int dungeonSize;
   public int corridorWidth;
   public BlockPos centerPos;
   public static int width = 19;
   public static int offset = 9;
   public int pyramidalHeight = 10;

   public ZarpionIntricacyGenerator(int dungeonSize, int centerHallSize, int corridorWidth, int pyramidalHeight, BlockPos centerPos) {
      this.centerHallSize = centerHallSize;
      this.dungeonSize = dungeonSize;
      this.centerPos = centerPos;
      this.corridorWidth = corridorWidth;
      this.pyramidalHeight = pyramidalHeight;
      System.out.println("CREATE ZarpionIntricacyGenerator");
   }

   public void generateIntricacy(World world, Random rand) {
      System.out.println("generateIntricacy");
      ArrayList<IntricacyPart> rooms = new ArrayList<>();
      int size = this.centerHallSize;

      for (int xx = -size; xx <= size; xx++) {
         for (int zz = -size; zz <= size; zz++) {
            System.out.println("ins 1");
            IntricacyPart part = new IntricacyPart();
            if (xx == 0) {
               if (zz == size) {
                  part.setEntries(true, false, false, false);
               } else if (zz == -size) {
                  part.setEntries(false, false, true, false);
               }
            } else if (zz == 0) {
               if (xx == size) {
                  part.setEntries(false, false, false, true);
               } else if (xx == -size) {
                  part.setEntries(false, true, false, false);
               }
            } else {
               part.directions = 0;
            }

            part.pos = this.centerPos.add(xx * width, 0, zz * width);
            part.generation = 0;
            rooms.add(part);
         }
      }

      int sizeInBlocks = (this.centerHallSize * 2 + 1) * width;
      int halfSizeInBlocks = sizeInBlocks / 2;
      float arrsize = MathHelper.ceil((this.centerHallSize * 2.0F + 1.0F) * width / this.corridorWidth);
      boolean[][] cells = this.genCellularArray(rand, (int)arrsize);
      float halfarrsize = arrsize / 2.0F;
      ArrayList<BlockPos> centers = new ArrayList<>();
      System.out.println("generate pyramid");

      for (int xx = -halfSizeInBlocks; xx <= halfSizeInBlocks; xx++) {
         for (int zz = -halfSizeInBlocks; zz <= halfSizeInBlocks; zz++) {
            System.out.println("ins 2");
            BlockPos inPos = this.centerPos.add(xx, 2, zz);
            world.setBlockState(inPos, BlocksRegister.STORMPLATE.getDefaultState(), 2);
            world.setBlockState(inPos.up(6), BlocksRegister.STORMMASONRY.getDefaultState(), 2);
            int arrX = MathHelper.clamp((xx + halfSizeInBlocks) / this.corridorWidth, 0, (int)arrsize - 1);
            int arrZ = MathHelper.clamp((zz + halfSizeInBlocks) / this.corridorWidth, 0, (int)arrsize - 1);
            boolean isWall = false;
            if ((
                  Math.abs(xx) > 2 && Math.abs(zz) > 2
                     || Math.abs(xx) < halfSizeInBlocks - this.corridorWidth * 2 && Math.abs(zz) < halfSizeInBlocks - this.corridorWidth * 2
               )
               && (xx == -halfSizeInBlocks || xx == halfSizeInBlocks || zz == -halfSizeInBlocks || zz == halfSizeInBlocks || cells[arrX][arrZ])) {
               world.setBlockState(inPos.up(), BlocksRegister.STORMMASONRY.getDefaultState(), 2);
               world.setBlockState(inPos.up(2), BlocksRegister.STORMPLATE.getDefaultState(), 2);
               world.setBlockState(inPos.up(3), BlocksRegister.STORMMASONRY.getDefaultState(), 2);
               world.setBlockState(inPos.up(4), BlocksRegister.STORMMASONRY.getDefaultState(), 2);
               world.setBlockState(inPos.up(5), BlocksRegister.STORMPLATE.getDefaultState(), 2);
               isWall = true;
            }

            float pyramidRoofHeightRatio = MathHelper.clamp(1.0F - (float)Math.max(Math.abs(xx), Math.abs(zz)) / halfSizeInBlocks, 0.0F, 1.0F);
            int pyramidRoofHeight = Math.round(pyramidRoofHeightRatio * this.pyramidalHeight);
            if (pyramidRoofHeight > 0) {
               this.column(
                  world,
                  inPos.up(7),
                  pyramidRoofHeight,
                  isWall ? BlocksRegister.STORMMASONRY.getDefaultState() : BlocksRegister.STORMPLATE.getDefaultState()
               );
               this.column(
                  world,
                  inPos.down(),
                  -pyramidRoofHeight,
                  isWall ? BlocksRegister.STORMMASONRY.getDefaultState() : BlocksRegister.STORMPLATE.getDefaultState()
               );
            }

            if (!isWall && xx % this.corridorWidth == 0 && zz % this.corridorWidth == 0) {
               int addxz = 0;
               if (this.centerHallSize == 1) {
                  addxz = 3;
               }

               if (this.centerHallSize == 2) {
                  addxz = -2;
               }

               if (this.centerHallSize == 3) {
                  addxz = 0;
               }

               if (this.centerHallSize == 4) {
                  addxz = 2;
               }

               centers.add(inPos.add(addxz, 0, addxz));
            }
         }
      }

      System.out.println("fill pyramid");

      for (BlockPos center : centers) {
         System.out.println("ins 3");
         EnumFacing facing = EnumFacing.HORIZONTALS[rand.nextInt(4)];
         this.placeColumnsAndRacksAtWalls(world, center, rand, facing);
         this.placeColumnsAndRacksAtWalls(world, center, rand, facing.getOpposite());
         if (rand.nextFloat() < 0.1F) {
            this.spawner(world, center.up(2), rand);
         }
      }

      System.out.println("generate outside");

      for (int s = 0; s < this.dungeonSize; s++) {
         System.out.println("ins 4");
         ArrayList<IntricacyPart> temprooms = new ArrayList<>();
         int sLast = s;

         for (IntricacyPart part : rooms) {
            System.out.println("ins 5");

            for (EnumFacing face : EnumFacing.HORIZONTALS) {
               System.out.println("ins 6");
               if (s <= this.dungeonSize && part.hasEntry(face)) {
                  IntricacyPart newpart = new IntricacyPart();
                  newpart.generation = part.generation + 1;
                  newpart.pos = part.pos.offset(face, width);
                  if (!this.hasPos(rooms, newpart.pos)) {
                     if (rand.nextFloat() < 0.55F) {
                        int type = 0;
                        byte var50;
                        if (newpart.generation < 4 && rand.nextFloat() < 0.8F) {
                           var50 = 4;
                        } else if (rand.nextFloat() < 0.6F) {
                           var50 = 1;
                        } else if (rand.nextFloat() < 0.6F) {
                           var50 = 2;
                        } else {
                           var50 = 3;
                        }

                        this.placeStruct(world, newpart.pos, rand, ":intricacy_corridor_" + var50, offset, 0, face.getOpposite().getHorizontalIndex());
                        newpart.directions = 0;
                        newpart.setEntry(face);
                        temprooms.add(newpart);
                        s++;
                        if (rand.nextFloat() < 0.5) {
                           GenerationHelper.setChestWithLoot(world, newpart.pos.up(3), EnumChest.STORM, ListLootTable.CHESTS_ZARPION_INTRICACY, face);
                           this.spawner(world, newpart.pos.up(4), rand);
                        }
                     } else if (rand.nextFloat() < 0.35F) {
                        int typex = 1;
                        this.placeStruct(world, newpart.pos, rand, ":intricacy_crossroad_" + typex, offset, 0, rand.nextInt(4));
                        newpart.directions = 0;
                        newpart.setEntries(true, true, true, true);
                        temprooms.add(newpart);
                        s++;
                        if (rand.nextFloat() < 0.42F) {
                           IntricacyPart newpart2 = new IntricacyPart();
                           newpart2.generation = newpart.generation + 1;
                           newpart2.pos = newpart.pos.down(14);
                           if (!this.hasPos(rooms, newpart2.pos)) {
                              int rotate = rand.nextInt(4);
                              newpart2.setEntry(GetMOP.rotate(EnumFacing.NORTH, rotate));
                              newpart2.setEntry(GetMOP.rotate(EnumFacing.EAST, rotate));
                              newpart2.setEntry(GetMOP.rotate(EnumFacing.SOUTH, rotate));
                              this.placeStruct(world, newpart2.pos, rand, ":intricacy_downhall_1", offset, -2, rotate);
                              temprooms.add(newpart2);
                           }
                        }

                        if (rand.nextFloat() < 0.5) {
                           GenerationHelper.setChestWithLoot(world, newpart.pos.up(3), EnumChest.STORM, ListLootTable.CHESTS_ZARPION_INTRICACY, face);
                           this.spawner(world, newpart.pos.up(4), rand);
                        }
                     } else if (rand.nextFloat() < 0.35F && newpart.pos.getY() >= this.centerPos.getY()) {
                        int typexx = 1;
                        if (rand.nextFloat() < 0.5F) {
                           typexx = 2;
                        }

                        newpart.directions = 0;
                        IntricacyPart newpart2 = new IntricacyPart();
                        newpart2.generation = newpart.generation;
                        newpart2.directions = 0;
                        newpart2.pos = newpart.pos.up(25);
                        if (!this.hasPos(rooms, newpart2.pos)) {
                           this.placeStruct(world, newpart.pos, rand, ":intricacy_tower_" + typexx, offset, 2, face.getOpposite().getHorizontalIndex());
                           temprooms.add(newpart2);
                           temprooms.add(newpart);
                           s++;
                        }

                        if (rand.nextFloat() < 0.85) {
                           GenerationHelper.setChestWithLoot(
                              world, newpart.pos.up(30), EnumChest.STORM, ListLootTable.CHESTS_ZARPION_INTRICACY, face
                           );
                           this.spawner(world, newpart.pos.up(31), rand);
                        }
                     } else if (rand.nextFloat() < 0.42F && newpart.pos.getY() >= this.centerPos.getY()) {
                        int typexxx = 1;
                        if (rand.nextFloat() < 0.5F) {
                           typexxx = 2;
                        }

                        newpart.directions = 0;
                        IntricacyPart newpart2x = new IntricacyPart();
                        newpart2x.generation = newpart.generation;
                        newpart2x.setEntry(face.getOpposite());
                        if (typexxx == 2) {
                           newpart2x.setEntry(face);
                        }

                        newpart2x.pos = newpart.pos.up(25);
                        if (!this.hasPos(rooms, newpart2x.pos)) {
                           this.placeStruct(world, newpart.pos, rand, ":intricacy_elevator_" + typexxx, offset, 2, face.getOpposite().getHorizontalIndex());
                           temprooms.add(newpart2x);
                           temprooms.add(newpart);
                           s++;
                        }
                     } else if (rand.nextFloat() < 0.5F) {
                        int typexxxx = 1;
                        this.placeStruct(world, newpart.pos, rand, ":intricacy_downtreasure_" + typexxxx, offset, -2, face.getOpposite().getHorizontalIndex());
                        newpart.directions = 0;
                        temprooms.add(newpart);
                        s++;
                        if (rand.nextFloat() < 0.5) {
                           GenerationHelper.setChestWithLoot(world, newpart.pos.up(2), EnumChest.STORM, ListLootTable.CHESTS_ZARPION_INTRICACY, face);
                        }
                     } else {
                        int typexxxx = 1;
                        this.placeStruct(world, newpart.pos, rand, ":intricacy_end_" + typexxxx, offset, 0, face.getOpposite().getHorizontalIndex());
                        newpart.directions = 0;
                        temprooms.add(newpart);
                        s++;
                     }
                  }
               }
            }
         }

         rooms.addAll(temprooms);
         if (s == sLast) {
            break;
         }
      }

      System.out.println("generate ends");
      ArrayList<IntricacyPart> temprooms = new ArrayList<>();

      for (IntricacyPart part : rooms) {
         System.out.println("ins 7");

         for (EnumFacing facex : EnumFacing.HORIZONTALS) {
            System.out.println("ins 8");
            if (part.hasEntry(facex)) {
               IntricacyPart newpart = new IntricacyPart();
               newpart.generation = part.generation + 1;
               newpart.pos = part.pos.offset(facex, width);
               if (!this.hasPos(rooms, newpart.pos) && rand.nextFloat() < 0.9F) {
                  int typexxxx = 1;
                  this.placeStruct(world, newpart.pos, rand, ":intricacy_end_" + typexxxx, offset, 0, facex.getOpposite().getHorizontalIndex());
                  newpart.directions = 0;
                  temprooms.add(newpart);
               }
            }
         }
      }

      rooms.addAll(temprooms);
      System.out.println("set TRADER");
      NPCMobsPack.NpcZarpionTrader npc = new NPCMobsPack.NpcZarpionTrader(world);

      for (int i = 0; i <= 20; i++) {
         System.out.println("ins 9");
         if (i == 0) {
            npc.setPosition(this.centerPos.getX() + 0.5, this.centerPos.getY() + 3.1, this.centerPos.getZ() + 0.5);
         } else {
            npc.setPosition(
               this.centerPos.getX() + 0.5 + (rand.nextFloat() - 0.5) * 4.0,
               this.centerPos.getY() + 3.1 + (rand.nextFloat() - 0.5) * 3.0,
               this.centerPos.getZ() + 0.5 + (rand.nextFloat() - 0.5) * 4.0
            );
         }

         if (!world.collidesWithAnyBlock(npc.getEntityBoundingBox())) {
            world.spawnEntity(npc);
            npc.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(npc)), (IEntityLivingData)null);
            npc.enablePersistence();
            world.setBlockState(
               new BlockPos(npc.posX + 2.0, this.centerPos.getY() + 2, npc.posZ + 2.0),
               BlocksRegister.STORMCONDUCTOR.getDefaultState()
            );
            world.setBlockState(
               new BlockPos(npc.posX - 2.0, this.centerPos.getY() + 2, npc.posZ + 2.0),
               BlocksRegister.STORMCONDUCTOR.getDefaultState()
            );
            world.setBlockState(
               new BlockPos(npc.posX - 2.0, this.centerPos.getY() + 2, npc.posZ - 2.0),
               BlocksRegister.STORMCONDUCTOR.getDefaultState()
            );
            world.setBlockState(
               new BlockPos(npc.posX + 2.0, this.centerPos.getY() + 2, npc.posZ - 2.0),
               BlocksRegister.STORMCONDUCTOR.getDefaultState()
            );
            break;
         }
      }

      System.out.println("END");
   }

   public boolean[][] genCellularArray(Random rand, int size) {
      boolean[][] arr1 = new boolean[size][size];

      for (int x = 0; x < size; x++) {
         for (int y = 0; y < size; y++) {
            arr1[x][y] = rand.nextFloat() < 0.4F;
         }
      }

      for (int i = 0; i < 30; i++) {
         boolean[][] arr2 = new boolean[size][size];

         for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
               boolean n = this.getCellOrFalse(arr1, x, y - 1, size);
               boolean s = this.getCellOrFalse(arr1, x, y + 1, size);
               boolean w = this.getCellOrFalse(arr1, x - 1, y, size);
               boolean e = this.getCellOrFalse(arr1, x + 1, y, size);
               boolean nw = this.getCellOrFalse(arr1, x - 1, y - 1, size);
               boolean ne = this.getCellOrFalse(arr1, x + 1, y - 1, size);
               boolean sw = this.getCellOrFalse(arr1, x - 1, y + 1, size);
               boolean se = this.getCellOrFalse(arr1, x + 1, y + 1, size);
               arr2[x][y] = this.getChangedCell(nw, n, ne, w, arr1[x][y], e, sw, s, se);
            }
         }

         arr1 = arr2;
      }

      int hsize = size / 2;
      arr1[hsize][hsize] = false;
      arr1[hsize + 1][hsize + 1] = false;
      arr1[hsize + 1][hsize - 1] = false;
      arr1[hsize - 1][hsize + 1] = false;
      arr1[hsize - 1][hsize - 1] = false;
      arr1[hsize - 1][hsize] = false;
      arr1[hsize][hsize - 1] = false;
      arr1[hsize + 1][hsize] = false;
      arr1[hsize][hsize + 1] = false;
      return arr1;
   }

   public boolean getCellOrFalse(boolean[][] array, int x, int y, int max) {
      if (x < 0 || x >= max) {
         return false;
      } else {
         return y >= 0 && y < max ? array[x][y] : false;
      }
   }

   public boolean getChangedCell(boolean nw, boolean n, boolean ne, boolean w, boolean self, boolean e, boolean sw, boolean s, boolean se) {
      int sum4 = 0;
      int sum42 = 0;
      if (nw) {
         sum42++;
      }

      if (n) {
         sum4++;
      }

      if (ne) {
         sum42++;
      }

      if (w) {
         sum4++;
      }

      if (e) {
         sum4++;
      }

      if (sw) {
         sum42++;
      }

      if (s) {
         sum4++;
      }

      if (se) {
         sum42++;
      }

      int sum8 = sum4 + sum42;
      if (sum8 > 3) {
         return false;
      } else {
         return sum42 == 1 && sum4 != 3 ? true : self;
      }
   }

   public boolean hasPos(ArrayList<IntricacyPart> list, BlockPos pos) {
      for (IntricacyPart part : list) {
         if (part.pos.equals(pos)) {
            return true;
         }
      }

      return false;
   }

   public void placeStruct(World worldIn, BlockPos pos, Random rand, String structure, int displace, int Yoffset, int rotation) {
      WorldServer worldServer = (WorldServer)worldIn;
      MinecraftServer minecraftServer = worldIn.getMinecraftServer();
      TemplateManager templateManager = worldServer.getStructureTemplateManager();
      Template template = templateManager.get(minecraftServer, new ResourceLocation("arpg" + structure));
      PlacementSettings settings = new PlacementSettings();
      int sx = -1;
      int sz = -1;
      if (rotation == 2) {
         settings.setRotation(Rotation.CLOCKWISE_180);
         sx = 1;
         sz = 1;
      }

      if (rotation == 1) {
         settings.setRotation(Rotation.CLOCKWISE_90);
         sx = 1;
         sz = -1;
      }

      if (rotation == 3) {
         settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
         sx = -1;
         sz = 1;
      }

      if (rotation == 0) {
         settings.setRotation(Rotation.NONE);
      }

      template.addBlocksToWorld(worldIn, pos.add(sx * displace, Yoffset, sz * displace), null, settings, 2);
   }

   public void placeColumnsAndRacksAtWalls(World world, BlockPos posm, Random rand, EnumFacing facing) {
      GetMOP.BlockTraceResult result = GetMOP.blockTrace(world, posm.up(), facing, this.corridorWidth / 2 + 1, GetMOP.SOLID_BLOCKS);
      if (result != null) {
         EnumFacing facingR = facing.rotateY();
         EnumFacing facingL = facing.rotateYCCW();

         for (int rl = 0; rl < this.corridorWidth / 2; rl++) {
            IBlockState columnstate = null;
            int randstate = rand.nextInt(7);
            if (randstate == 0 || randstate == 1) {
               columnstate = BlocksRegister.STORMRACK.getDefaultState().withProperty(StormRack.FACING, facing.getOpposite());
            }

            if (randstate == 2 || randstate == 3) {
               columnstate = BlocksRegister.STORMCONDUCTOR.getStateFromMeta(5);
            }

            if (randstate == 4) {
               columnstate = BlocksRegister.STORMMASONRY.getDefaultState();
            }

            if (randstate == 5) {
               columnstate = BlocksRegister.STORMPLATE.getDefaultState();
            }

            if (randstate == 6) {
               columnstate = BlocksRegister.BEAMROCK.getDefaultState();
            }

            BlockPos posRLRight = result.pos.offset(facingR, rl);
            BlockPos posRLLeftt = result.pos.offset(facingL, rl);
            int maxDepth = 2;

            for (int depth = 0; depth <= maxDepth; depth++) {
               BlockPos posDepthedR = posRLRight.offset(facing, depth);
               BlockPos posDepthedL = posRLLeftt.offset(facing, depth);
               if (depth == maxDepth && rand.nextFloat() < 0.5F) {
                  this.column(world, posDepthedR, 4, columnstate);
                  this.column(world, posDepthedL, 4, columnstate);
               } else {
                  this.column(world, posDepthedR, 4, Blocks.AIR.getDefaultState());
                  this.column(world, posDepthedL, 4, Blocks.AIR.getDefaultState());
               }
            }
         }

         if (rand.nextFloat() < 0.2F) {
            this.spawner(world, result.pos.up().offset(facing), rand);
         } else if (rand.nextFloat() < 0.12F) {
            GenerationHelper.setChestWithLoot(
               world, result.pos.offset(facing), EnumChest.STORM, ListLootTable.CHESTS_ZARPION_INTRICACY, facing.getOpposite()
            );
         }
      }
   }

   public void column(World world, BlockPos posm, int height, IBlockState state) {
      if (height > 0) {
         for (int i = 0; i < height; i++) {
            world.setBlockState(posm.up(i), state, 2);
         }
      } else {
         for (int i = 0; i > height; i--) {
            world.setBlockState(posm.up(i), state, 2);
         }
      }
   }

   public void spawner(World world, BlockPos posm, Random rand) {
      world.setBlockState(posm, BlocksRegister.MOBSPAWNERSTORM.getDefaultState(), 2);
      SpawnerTuners.ZARPION_INTRICACY.setupSpawner(world, posm, rand);
   }

   public class IntricacyPart {
      public BlockPos pos;
      public int directions;
      public int generation = 0;

      public boolean hasEntry(EnumFacing direction) {
         return (this.directions & 1 << direction.getHorizontalIndex()) > 0;
      }

      public void setEntry(EnumFacing direction) {
         this.directions = this.directions | 1 << direction.getHorizontalIndex();
      }

      public void setEntries(boolean zplusS, boolean xminusW, boolean zminusN, boolean xplusE) {
         this.directions = 0 | (zplusS ? 1 : 0) | (xminusW ? 2 : 0) | (zminusN ? 4 : 0) | (xplusE ? 8 : 0);
      }
   }
}
