package com.vivern.arpg.dimensions.ethernalfrost;

import com.vivern.arpg.dimensions.generationutils.GenerationHelper;
import com.vivern.arpg.loot.ListLootTable;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.StructPos;
import com.vivern.arpg.tileentity.ChestLock;
import com.vivern.arpg.tileentity.EnumChest;
import com.vivern.arpg.tileentity.TileARPGChest;
import com.vivern.arpg.tileentity.TilePuzzle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class IceCastle {
   public Random castlerand;

   public IceCastle(Random rand) {
      this.castlerand = rand;
   }

   public void generateIceCastle(World world, BlockPos pos) {
      if (!world.isRemote) {
         ChestReplacersFrozen.posesToSetPuzzle.clear();
         List<BlockPos> list = new ArrayList<>();
         List<BlockPos> roomlist = new ArrayList<>();
         List<BlockPos> environmentlist = new ArrayList<>();
         List<BlockPos> foundationlist = new ArrayList<>();
         this.generateHall(world, this.castlerand, pos.add(0, -22, 0), 20, list, this.castlerand.nextInt(4));
         this.placeWalls(world, this.castlerand, list);
         this.generateRoom(world, this.castlerand, pos.add(0, 18, 0), 20, list, roomlist, foundationlist, this.castlerand.nextInt(4));
         this.placeOutsideRooms(world, this.castlerand, roomlist, list, foundationlist);
         this.generateTowers(world, this.castlerand, roomlist);
         this.generateEnvironment(world, this.castlerand, list, roomlist, environmentlist);
         List<StructPos> wallslist = this.generateCastleWalls(world, this.castlerand, roomlist, environmentlist, pos.getY() + 5);
         this.generateDungeonEntry(world, this.castlerand, pos.add(0, -22, 0), this.castlerand.nextInt(4));

         for (BlockPos poss : foundationlist) {
            if (poss.getX() != pos.getX() || poss.getZ() != pos.getZ()) {
               this.placeFoundation(world, poss, 4, false);
            }
         }

         for (StructPos possx : wallslist) {
            this.placeFoundation(world, possx.blockpos.down(13), 4, true);
         }

         for (BlockPos puzlPos : ChestReplacersFrozen.posesToSetPuzzle) {
            TileEntity tileentity = world.getTileEntity(puzlPos);
            if (tileentity != null && tileentity instanceof TileARPGChest) {
               EnumFacing face = EnumFacing.HORIZONTALS[this.castlerand.nextInt(4)];
               BlockPos posss = puzlPos.offset(face);
               if (world.isAirBlock(posss) && !world.isAirBlock(posss.down())) {
                  world.setBlockState(posss, BlocksRegister.PUZZLE.getDefaultState());
                  TilePuzzle puzzle = (TilePuzzle)world.getTileEntity(posss);
                  puzzle.setupPuzzle(6);
                  puzzle.chestOpened = face.getOpposite();
                  ((TileARPGChest)tileentity).lockOrUnlockWith(ChestLock.PUZZLE, true);
               }
            }
         }
      }
   }

   public void placeFoundation(World world, BlockPos pos, int size, boolean underground) {
      for (int x = pos.getX() - size; x <= pos.getX() + size; x++) {
         for (int z = pos.getZ() - size; z <= pos.getZ() + size; z++) {
            boolean generate = false;
            IBlockState material = world.getBlockState(new BlockPos(x, pos.getY(), z));
            if (material.getBlock() == BlocksRegister.FROZENBRICKS
               || material.getBlock() == Blocks.STONEBRICK
               || material.getBlock() == BlocksRegister.FROZENBRICKPILASTER) {
               generate = true;
            }

            if (material.getBlock() == BlocksRegister.FROZENVASE
               || material.getBlock() == BlocksRegister.FROZENCHANDELIER
               || material.getBlock() == BlocksRegister.FROZENBRICKSTAIRS) {
               material = BlocksRegister.FROZENBRICKS.getDefaultState();
               generate = true;
            }

            if (generate) {
               if (!underground) {
                  for (int y = pos.getY() - 1; y > 1; y--) {
                     BlockPos fpos = new BlockPos(x, y, z);
                     IBlockState getted = world.getBlockState(fpos);
                     Block block = getted.getBlock();
                     if (!block.isReplaceable(world, fpos)
                        && !block.isLeaves(getted, world, fpos)
                        && block != Blocks.ICE
                        && block != Blocks.SNOW
                        && block != Blocks.SNOW_LAYER) {
                        break;
                     }

                     world.setBlockState(fpos, material);
                  }
               } else {
                  int under = this.castlerand.nextInt(7) + 3;

                  for (int y = pos.getY() - 1; y > 1; y--) {
                     BlockPos fpos = new BlockPos(x, y, z);
                     IBlockState getted = world.getBlockState(fpos);
                     Block block = getted.getBlock();
                     if (!block.isReplaceable(world, fpos)
                        && !block.isLeaves(getted, world, fpos)
                        && block != Blocks.ICE
                        && block != Blocks.SNOW
                        && block != Blocks.SNOW_LAYER) {
                        if (under <= 0 || block != BlocksRegister.SNOWICE && block != BlocksRegister.GLACIER && block != BlocksRegister.CLEANICE) {
                           break;
                        }

                        world.setBlockState(fpos, material);
                        under--;
                     } else {
                        world.setBlockState(fpos, material);
                     }
                  }
               }
            }
         }
      }
   }

   public static void placeSpawner(World world, BlockPos pos, boolean parapet, Random random) {
      world.setBlockState(pos, BlocksRegister.MOBSPAWNERFROZEN.getDefaultState());
      DimensionEthernalFrost.setupRandomSpawner(
         world,
         world.getTileEntity(pos),
         parapet ? DimensionEthernalFrost.EnumEverfrostSpawner.ICE_CASTLE_PARAPET : DimensionEthernalFrost.EnumEverfrostSpawner.ICE_CASTLE,
         random
      );
   }

   public static void placeChest(World world, BlockPos pos, Random random, @Nullable IBlockState baseState, boolean rare) {
      if (random.nextFloat() < (rare ? 0.75 : 0.3)) {
         GenerationHelper.setChestWithLoot(
            world,
            pos,
            EnumChest.FROZEN,
            ListLootTable.CHESTS_ICE_CASTLE_RICH,
            baseState == null ? EnumFacing.HORIZONTALS[random.nextInt(4)] : (EnumFacing)baseState.getValue(BlockChest.FACING),
            ChestLock.WINTER_CURSE
         );
         ChestReplacersFrozen.posesToSetPuzzle.add(pos);
      } else {
         GenerationHelper.setChestWithLoot(
            world,
            pos,
            EnumChest.FROZEN,
            ListLootTable.CHESTS_ICE_CASTLE,
            baseState == null ? EnumFacing.HORIZONTALS[random.nextInt(4)] : (EnumFacing)baseState.getValue(BlockChest.FACING),
            ChestLock.WINTER_CURSE
         );
      }
   }

   public void addperimeter(List<BlockPos> originallist, List<BlockPos> addinglist) {
      for (BlockPos position : originallist) {
         BlockPos pos1 = position.add(9, 0, 9);
         BlockPos pos2 = position.add(0, 0, 9);
         BlockPos pos3 = position.add(-9, 0, 9);
         BlockPos pos4 = position.add(-9, 0, 0);
         BlockPos pos5 = position.add(-9, 0, -9);
         BlockPos pos6 = position.add(0, 0, -9);
         BlockPos pos7 = position.add(9, 0, -9);
         BlockPos pos8 = position.add(9, 0, 0);
         if (!this.listPosMatch(originallist, pos1) && !this.listPosMatch(addinglist, pos1)) {
            addinglist.add(pos1);
         }

         if (!this.listPosMatch(originallist, pos2) && !this.listPosMatch(addinglist, pos2)) {
            addinglist.add(pos2);
         }

         if (!this.listPosMatch(originallist, pos3) && !this.listPosMatch(addinglist, pos3)) {
            addinglist.add(pos3);
         }

         if (!this.listPosMatch(originallist, pos4) && !this.listPosMatch(addinglist, pos4)) {
            addinglist.add(pos4);
         }

         if (!this.listPosMatch(originallist, pos5) && !this.listPosMatch(addinglist, pos5)) {
            addinglist.add(pos5);
         }

         if (!this.listPosMatch(originallist, pos6) && !this.listPosMatch(addinglist, pos6)) {
            addinglist.add(pos6);
         }

         if (!this.listPosMatch(originallist, pos7) && !this.listPosMatch(addinglist, pos7)) {
            addinglist.add(pos7);
         }

         if (!this.listPosMatch(originallist, pos8) && !this.listPosMatch(addinglist, pos8)) {
            addinglist.add(pos8);
         }
      }
   }

   public void addperimeter(List<BlockPos> originallist) {
      List<BlockPos> addinglist = new ArrayList<>();

      for (BlockPos position : originallist) {
         BlockPos pos1 = position.add(9, 0, 9);
         BlockPos pos2 = position.add(0, 0, 9);
         BlockPos pos3 = position.add(-9, 0, 9);
         BlockPos pos4 = position.add(-9, 0, 0);
         BlockPos pos5 = position.add(-9, 0, -9);
         BlockPos pos6 = position.add(0, 0, -9);
         BlockPos pos7 = position.add(9, 0, -9);
         BlockPos pos8 = position.add(9, 0, 0);
         if (!this.listPosMatch(originallist, pos1) && !this.listPosMatch(addinglist, pos1)) {
            addinglist.add(pos1);
         }

         if (!this.listPosMatch(originallist, pos2) && !this.listPosMatch(addinglist, pos2)) {
            addinglist.add(pos2);
         }

         if (!this.listPosMatch(originallist, pos3) && !this.listPosMatch(addinglist, pos3)) {
            addinglist.add(pos3);
         }

         if (!this.listPosMatch(originallist, pos4) && !this.listPosMatch(addinglist, pos4)) {
            addinglist.add(pos4);
         }

         if (!this.listPosMatch(originallist, pos5) && !this.listPosMatch(addinglist, pos5)) {
            addinglist.add(pos5);
         }

         if (!this.listPosMatch(originallist, pos6) && !this.listPosMatch(addinglist, pos6)) {
            addinglist.add(pos6);
         }

         if (!this.listPosMatch(originallist, pos7) && !this.listPosMatch(addinglist, pos7)) {
            addinglist.add(pos7);
         }

         if (!this.listPosMatch(originallist, pos8) && !this.listPosMatch(addinglist, pos8)) {
            addinglist.add(pos8);
         }
      }

      for (BlockPos aposition : addinglist) {
         originallist.add(aposition);
      }
   }

   public boolean listPosMatch(List<BlockPos> list, BlockPos position) {
      for (BlockPos pos : list) {
         if (pos.getX() == position.getX()
            && pos.getY() == position.getY()
            && pos.getZ() == position.getZ()) {
            return true;
         }
      }

      return false;
   }

   public boolean listStructPosMatch(List<StructPos> list, BlockPos position) {
      for (StructPos stpos : list) {
         BlockPos pos = stpos.blockpos;
         if (pos.getX() == position.getX()
            && pos.getY() == position.getY()
            && pos.getZ() == position.getZ()) {
            return true;
         }
      }

      return false;
   }

   public void addPerimeterWalls(World worldIn, Random random, List<BlockPos> originallist, List<StructPos> addinglist, int y) {
      for (BlockPos position : originallist) {
         BlockPos pos2 = position.add(0, 0, 9);
         BlockPos pos4 = position.add(-9, 0, 0);
         BlockPos pos6 = position.add(0, 0, -9);
         BlockPos pos8 = position.add(9, 0, 0);
         if (!this.listPosMatch(originallist, pos2) && !this.listStructPosMatch(addinglist, pos2)) {
            addinglist.add(new StructPos(pos2, 3));
         }

         if (!this.listPosMatch(originallist, pos4) && !this.listStructPosMatch(addinglist, pos4)) {
            addinglist.add(new StructPos(pos4, 1));
         }

         if (!this.listPosMatch(originallist, pos6) && !this.listStructPosMatch(addinglist, pos6)) {
            addinglist.add(new StructPos(pos6, 0));
         }

         if (!this.listPosMatch(originallist, pos8) && !this.listStructPosMatch(addinglist, pos8)) {
            addinglist.add(new StructPos(pos8, 2));
         }
      }

      for (BlockPos position : originallist) {
         BlockPos pos1 = position.add(9, 0, 9);
         BlockPos pos3 = position.add(-9, 0, 9);
         BlockPos pos5 = position.add(-9, 0, -9);
         BlockPos pos7 = position.add(9, 0, -9);
         if (!this.listPosMatch(originallist, pos1) && !this.listStructPosMatch(addinglist, pos1)) {
            addinglist.add(new StructPos(pos1, 3));
         }

         if (!this.listPosMatch(originallist, pos3) && !this.listStructPosMatch(addinglist, pos3)) {
            addinglist.add(new StructPos(pos3, 1));
         }

         if (!this.listPosMatch(originallist, pos5) && !this.listStructPosMatch(addinglist, pos5)) {
            addinglist.add(new StructPos(pos5, 0));
         }

         if (!this.listPosMatch(originallist, pos7) && !this.listStructPosMatch(addinglist, pos7)) {
            addinglist.add(new StructPos(pos7, 2));
         }
      }
   }

   public List<StructPos> generateCastleWalls(World worldIn, Random random, List<BlockPos> roomlist, List<BlockPos> envlist, int y) {
      for (BlockPos eposition : envlist) {
         roomlist.add(eposition);
      }

      List<StructPos> wallslist = new ArrayList<>();
      this.addperimeter(roomlist);
      this.addperimeter(roomlist);
      this.addPerimeterWalls(worldIn, random, roomlist, wallslist, y);

      for (StructPos stpos : wallslist) {
         boolean up = false;
         boolean down = false;
         boolean right = false;
         boolean left = false;
         if (this.listStructPosMatch(wallslist, stpos.blockpos.add(0, 0, 9))) {
            up = true;
         }

         if (this.listStructPosMatch(wallslist, stpos.blockpos.add(0, 0, -9))) {
            down = true;
         }

         if (this.listStructPosMatch(wallslist, stpos.blockpos.add(9, 0, 0))) {
            left = true;
         }

         if (this.listStructPosMatch(wallslist, stpos.blockpos.add(-9, 0, 0))) {
            right = true;
         }

         if ((!up || !down || left || right) && (!right || !left || down || up)) {
            ResourceLocation locationw = this.getNearestCollidesCount(worldIn, stpos.blockpos, roomlist) < 2
               ? new ResourceLocation("arpg:ice_castle_wall_tower")
               : new ResourceLocation("arpg:ice_castle_wall_inside");
            if (up && left && !down && !right) {
               this.placeStructure(worldIn, random, new BlockPos(stpos.blockpos.getX(), y, stpos.blockpos.getZ()), locationw, 4, 0);
            } else if (down && left && !up && !right) {
               this.placeStructure(worldIn, random, new BlockPos(stpos.blockpos.getX(), y, stpos.blockpos.getZ()), locationw, 4, 1);
            } else if (up && right && !down && !left) {
               this.placeStructure(worldIn, random, new BlockPos(stpos.blockpos.getX(), y, stpos.blockpos.getZ()), locationw, 4, 2);
            } else if (down && right && !left && !up) {
               this.placeStructure(worldIn, random, new BlockPos(stpos.blockpos.getX(), y, stpos.blockpos.getZ()), locationw, 4, 3);
            } else if (down || right || left || up) {
               this.placeStructure(
                  worldIn,
                  random,
                  new BlockPos(stpos.blockpos.getX(), y, stpos.blockpos.getZ()),
                  new ResourceLocation("arpg:ice_castle_wall_quad"),
                  4,
                  3
               );
            }
         } else {
            this.placeStructure(
               worldIn,
               random,
               new BlockPos(stpos.blockpos.getX(), y, stpos.blockpos.getZ()),
               new ResourceLocation("arpg:ice_castle_wall"),
               4,
               stpos.rotation
            );
         }
      }

      return wallslist;
   }

   public void generateEnvironment(World worldIn, Random random, List<BlockPos> list, List<BlockPos> roomlist, List<BlockPos> envlist) {
      for (BlockPos pos : roomlist) {
         if (random.nextFloat() < 0.93) {
            BlockPos posoffset = pos.add((random.nextInt(9) - 4) * 9, 0, (random.nextInt(9) - 4) * 9);
            if (this.getNearestCollidesCount(worldIn, posoffset, roomlist) == 0
               && this.getNearestCollidesCount(worldIn, posoffset, list) == 0
               && this.getNearestCollidesCount(worldIn, posoffset, envlist) == 0) {
               float fr = random.nextFloat();
               if (fr < 0.4) {
                  BlockPos posdown = GetMOP.getTrueHeight(worldIn, posoffset.add(random.nextInt(7) - 3, -9, random.nextInt(7) - 3));
                  this.generateParapet(worldIn, random, posdown);
                  this.placeFoundation(worldIn, posdown, 6, false);
                  envlist.add(posoffset);
               } else {
                  BlockPos posdown = GetMOP.getTrueHeight(worldIn, posoffset.add(random.nextInt(7) - 3, -9, random.nextInt(7) - 3));
                  this.placeStructure(worldIn, random, posdown, new ResourceLocation("arpg:frozen_fountain_" + (random.nextInt(2) + 1)), 6, -1);
                  this.placeFoundation(worldIn, posdown, 6, false);
                  envlist.add(posoffset);
               }
            }
         }
      }
   }

   public void generateTowers(World worldIn, Random random, List<BlockPos> list) {
      for (BlockPos pos : list) {
         if (random.nextFloat() < 0.7) {
            this.generateTower(worldIn, random, pos.add(0, 9, 0));
         }
      }
   }

   public void placeStructure(World worldIn, Random random, BlockPos position, ResourceLocation templateloc, int displace, int rotation) {
      WorldServer worldServer = (WorldServer)worldIn;
      MinecraftServer minecraftServer = worldIn.getMinecraftServer();
      TemplateManager templateManager = worldServer.getStructureTemplateManager();
      Template template = templateManager.get(minecraftServer, templateloc);
      PlacementSettings settings = new PlacementSettings();
      int sx = -1;
      int sz = -1;
      int swr = rotation == -1 ? random.nextInt(4) : rotation;
      if (swr == 0) {
         settings.setRotation(Rotation.CLOCKWISE_180);
         sx = 1;
         sz = 1;
         BlockPos transfpos = position.add(-1, 0, -1);
      }

      if (swr == 1) {
         settings.setRotation(Rotation.CLOCKWISE_90);
         sx = 1;
         sz = -1;
         BlockPos var16 = position.add(-1, 0, 0);
      }

      if (swr == 2) {
         settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
         sx = -1;
         sz = 1;
         BlockPos var17 = position.add(0, 0, -1);
      }

      if (swr == 3) {
         settings.setRotation(Rotation.NONE);
      }

      template.addBlocksToWorld(worldIn, position.add(sx * displace, 0, sz * displace), ChestReplacersFrozen.replacerIceCastle, settings, 2);
   }

   public void generateParapet(World worldIn, Random random, BlockPos position) {
      WorldServer worldServer = (WorldServer)worldIn;
      MinecraftServer minecraftServer = worldIn.getMinecraftServer();
      TemplateManager templateManager = worldServer.getStructureTemplateManager();
      int updisplace = 0;
      Template templatetop = templateManager.get(minecraftServer, new ResourceLocation("arpg:ice_castle_parapet_1"));
      PlacementSettings settings = new PlacementSettings();
      int sx = -1;
      int sz = -1;
      int swr = random.nextInt(4);
      if (swr == 0) {
         settings.setRotation(Rotation.CLOCKWISE_180);
         sx = 1;
         sz = 1;
         BlockPos transfpos = position.add(-1, 0, -1);
      }

      if (swr == 1) {
         settings.setRotation(Rotation.CLOCKWISE_90);
         sx = 1;
         sz = -1;
         BlockPos var17 = position.add(-1, 0, 0);
      }

      if (swr == 2) {
         settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
         sx = -1;
         sz = 1;
         BlockPos var18 = position.add(0, 0, -1);
      }

      if (swr == 3) {
         settings.setRotation(Rotation.NONE);
      }

      templatetop.addBlocksToWorld(worldIn, position.add(sx * 7, updisplace, sz * 7), ChestReplacersFrozen.replacerIceCastle, settings, 2);
      updisplace += 32;
      int rr = random.nextInt(7);

      for (int i = 0; i < random.nextInt(15) + 4; i++) {
         if (i < rr) {
            worldIn.setBlockState(position.add(0, updisplace, 0), Blocks.COBBLESTONE_WALL.getDefaultState());
            updisplace++;
         } else {
            worldIn.setBlockState(position.add(0, updisplace, 0), Blocks.IRON_BARS.getDefaultState());
            updisplace++;
         }
      }
   }

   public void generateTower(World worldIn, Random random, BlockPos position) {
      WorldServer worldServer = (WorldServer)worldIn;
      MinecraftServer minecraftServer = worldIn.getMinecraftServer();
      TemplateManager templateManager = worldServer.getStructureTemplateManager();
      int updisplace = 0;
      Template templatetop = templateManager.get(minecraftServer, new ResourceLocation("arpg:ice_castle_tower_top_1"));
      Template templatetower1 = templateManager.get(minecraftServer, new ResourceLocation("arpg:ice_castle_tower_small_" + (random.nextInt(3) + 1)));
      Template templatetower2 = templateManager.get(minecraftServer, new ResourceLocation("arpg:ice_castle_tower_small_" + (random.nextInt(3) + 1)));
      Template templatebase;
      byte var20;
      if (random.nextFloat() < 0.4) {
         var20 = 18;
         templatebase = templateManager.get(minecraftServer, new ResourceLocation("arpg:ice_castle_tower_base_" + (random.nextInt(2) + 1)));
      } else {
         var20 = 9;
         templatebase = templateManager.get(minecraftServer, new ResourceLocation("arpg:ice_castle_tower_post_1"));
      }

      PlacementSettings settings = new PlacementSettings();
      int sx = -1;
      int sz = -1;
      int swr = random.nextInt(4);
      if (swr == 0) {
         settings.setRotation(Rotation.CLOCKWISE_180);
         sx = 1;
         sz = 1;
         BlockPos transfpos = position.add(-1, 0, -1);
      }

      if (swr == 1) {
         settings.setRotation(Rotation.CLOCKWISE_90);
         sx = 1;
         sz = -1;
         BlockPos var22 = position.add(-1, 0, 0);
      }

      if (swr == 2) {
         settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
         sx = -1;
         sz = 1;
         BlockPos var23 = position.add(0, 0, -1);
      }

      if (swr == 3) {
         settings.setRotation(Rotation.NONE);
      }

      Rotation rotation1 = settings.getRotation();
      templatebase.addBlocksToWorld(worldIn, position.add(sx * 4, 0, sz * 4), ChestReplacersFrozen.replacerIceCastle, settings, 2);

      for (int i = 0; i < random.nextInt(13) + 2; i++) {
         if (random.nextBoolean()) {
            templatetower1.addBlocksToWorld(worldIn, position.add(sx * 3, var20, sz * 3), ChestReplacersFrozen.replacerIceCastle, settings, 2);
            var20 += 8;
         } else {
            templatetower2.addBlocksToWorld(worldIn, position.add(sx * 3, var20, sz * 3), ChestReplacersFrozen.replacerIceCastle, settings, 2);
            var20 += 8;
         }
      }

      templatetop.addBlocksToWorld(worldIn, position.add(sx * 5, var20, sz * 5), ChestReplacersFrozen.replacerIceCastle, settings, 2);
      var20 += 32;
      int rr = random.nextInt(5);

      for (int ix = 0; ix < random.nextInt(9) + 4; ix++) {
         if (ix < rr) {
            worldIn.setBlockState(position.add(0, var20, 0), Blocks.COBBLESTONE_WALL.getDefaultState());
            var20++;
         } else {
            worldIn.setBlockState(position.add(0, var20, 0), Blocks.IRON_BARS.getDefaultState());
            var20++;
         }
      }
   }

   public void generateRoom(
      World worldIn,
      Random random,
      BlockPos position,
      int samples,
      List<BlockPos> list,
      List<BlockPos> roomlist,
      List<BlockPos> foundationlist,
      int generatorRotation
   ) {
      if (!this.listPosMatch(list, position)) {
         list.add(position);
         roomlist.add(position);
         foundationlist.add(position.down(9));
         WorldServer worldServer = (WorldServer)worldIn;
         MinecraftServer minecraftServer = worldIn.getMinecraftServer();
         TemplateManager templateManager = worldServer.getStructureTemplateManager();
         boolean placedouble = random.nextFloat() < 0.2;
         Template template = templateManager.get(minecraftServer, new ResourceLocation("arpg:ice_castle_inside_room_" + (random.nextInt(2) + 1)));
         Template template2 = templateManager.get(minecraftServer, new ResourceLocation("arpg:ice_castle_inside_room_" + (random.nextInt(2) + 1)));
         Template template3 = null;
         if (placedouble) {
            template3 = templateManager.get(minecraftServer, new ResourceLocation("arpg:ice_castle_room_stairs"));
         }

         PlacementSettings settings = new PlacementSettings();
         int sx = -1;
         int sz = -1;
         int swr = placedouble ? position.getX() % 4 : random.nextInt(4);
         if (swr == 0) {
            settings.setRotation(Rotation.CLOCKWISE_180);
            sx = 1;
            sz = 1;
            BlockPos transfpos = position.add(-1, 0, -1);
         }

         if (swr == 1) {
            settings.setRotation(Rotation.CLOCKWISE_90);
            sx = 1;
            sz = -1;
            BlockPos var25 = position.add(-1, 0, 0);
         }

         if (swr == 2) {
            settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
            sx = -1;
            sz = 1;
            BlockPos var26 = position.add(0, 0, -1);
         }

         if (swr == 3) {
            settings.setRotation(Rotation.NONE);
         }

         if (placedouble) {
            template3.addBlocksToWorld(worldIn, position.add(sx * 4, -9, sz * 4), ChestReplacersFrozen.replacerIceCastle, settings, 2);
         } else {
            template.addBlocksToWorld(worldIn, position.add(sx * 4, 0, sz * 4), ChestReplacersFrozen.replacerIceCastle, settings, 2);
            template2.addBlocksToWorld(worldIn, position.add(sx * 4, -9, sz * 4), ChestReplacersFrozen.replacerIceCastle, settings, 2);
         }
      }

      if (--samples > 0) {
         int rotat = 0;
         if (random.nextFloat() < 0.75) {
            rotat = random.nextInt(4);
         } else {
            rotat = generatorRotation;
         }

         BlockPos nextpos = position;
         switch (rotat) {
            case 0:
               nextpos = position.add(9, 0, 0);
            case 1:
               nextpos = nextpos.add(-9, 0, 0);
            case 2:
               nextpos = nextpos.add(0, 0, 9);
            case 3:
               nextpos = nextpos.add(0, 0, -9);
            default:
               this.generateRoom(worldIn, random, nextpos, samples, list, roomlist, foundationlist, rotat);
         }
      }
   }

   public void placeOutsideRooms(World worldIn, Random random, List<BlockPos> list, List<BlockPos> listprev, List<BlockPos> foundationlist) {
      int firstEntranceIndex = random.nextInt(list.size()) + 1;
      int ii = 0;

      for (BlockPos pos : list) {
         ii++;
         BlockPos offspos1 = pos.add(9, 0, 0);
         BlockPos offspos2 = pos.add(-9, 0, 0);
         BlockPos offspos3 = pos.add(0, 0, 9);
         BlockPos offspos4 = pos.add(0, 0, -9);
         boolean lpm1 = this.listPosMatch(list, offspos1);
         boolean lpm2 = this.listPosMatch(list, offspos2);
         boolean lpm3 = this.listPosMatch(list, offspos3);
         boolean lpm4 = this.listPosMatch(list, offspos4);
         int index = ii == firstEntranceIndex ? random.nextInt(4) : -1;
         if (!lpm1 && worldIn.isAirBlock(pos.add(4, 1, 0))) {
            int placemode = this.getNearestCollidesCount(worldIn, offspos1, list);
            if (index == 0) {
               ii = 10000;
               placemode = -1;
            }

            if (random.nextFloat() < 0.1) {
               placemode = -1;
            }

            this.placeOutsideRoomWithRotation(worldIn, random, offspos1, 2, placemode, listprev, foundationlist);
         }

         if (!lpm2 && worldIn.isAirBlock(pos.add(-4, 1, 0))) {
            int placemodex = this.getNearestCollidesCount(worldIn, offspos2, list);
            if (index == 1) {
               ii = 10000;
               placemodex = -1;
            }

            if (random.nextFloat() < 0.1) {
               placemodex = -1;
            }

            this.placeOutsideRoomWithRotation(worldIn, random, offspos2, 1, placemodex, listprev, foundationlist);
         }

         if (!lpm3 && worldIn.isAirBlock(pos.add(0, 1, 4))) {
            int placemodexx = this.getNearestCollidesCount(worldIn, offspos3, list);
            if (index == 2) {
               ii = 10000;
               placemodexx = -1;
            }

            if (random.nextFloat() < 0.1) {
               placemodexx = -1;
            }

            this.placeOutsideRoomWithRotation(worldIn, random, offspos3, 3, placemodexx, listprev, foundationlist);
         }

         if (!lpm4 && worldIn.isAirBlock(pos.add(0, 1, -4))) {
            int placemodexxx = this.getNearestCollidesCount(worldIn, offspos4, list);
            if (index == 3) {
               ii = 10000;
               placemodexxx = -1;
            }

            if (random.nextFloat() < 0.1) {
               placemodexxx = -1;
            }

            this.placeOutsideRoomWithRotation(worldIn, random, offspos4, 0, placemodexxx, listprev, foundationlist);
         }
      }
   }

   public int getNearestCollidesCount(World worldIn, BlockPos position, List<BlockPos> list) {
      int i = 0;
      if (this.listPosMatch(list, position.add(9, 0, 0))) {
         i++;
      }

      if (this.listPosMatch(list, position.add(-9, 0, 0))) {
         i++;
      }

      if (this.listPosMatch(list, position.add(0, 0, 9))) {
         i++;
      }

      if (this.listPosMatch(list, position.add(0, 0, -9))) {
         i++;
      }

      return i;
   }

   public void placeOutsideRoomWithRotation(
      World worldIn, Random random, BlockPos position, int rotation, int mode, List<BlockPos> list, List<BlockPos> foundationlist
   ) {
      WorldServer worldServer = (WorldServer)worldIn;
      MinecraftServer minecraftServer = worldIn.getMinecraftServer();
      TemplateManager templateManager = worldServer.getStructureTemplateManager();
      Template template2 = null;
      boolean placeOutRoom = true;
      boolean minitower = false;
      list.add(position.add(0, -9, 0));
      list.add(position.add(0, 9, 0));
      list.add(position);
      foundationlist.add(position.down(9));
      Template template3;
      if (random.nextFloat() < 0.8) {
         template3 = templateManager.get(minecraftServer, new ResourceLocation("arpg:ice_castle_colonnade"));
      } else {
         if (random.nextFloat() < 0.45) {
            template3 = templateManager.get(minecraftServer, new ResourceLocation("arpg:ice_castle_room_entrance"));
         } else {
            template3 = templateManager.get(minecraftServer, new ResourceLocation("arpg:ice_castle_outside_minitower"));
            minitower = true;
         }

         placeOutRoom = false;
      }

      Template template;
      if (mode == 1) {
         template = templateManager.get(minecraftServer, new ResourceLocation("arpg:ice_castle_outside_room_" + (random.nextInt(3) + 1)));
         template2 = templateManager.get(minecraftServer, new ResourceLocation("arpg:ice_castle_room_roof_" + (random.nextInt(3) + 1)));
      } else if (mode == 4) {
         template = templateManager.get(minecraftServer, new ResourceLocation("arpg:ice_castle_inside_room_" + (random.nextInt(2) + 1)));
      } else if (mode == -1) {
         template = templateManager.get(minecraftServer, new ResourceLocation("arpg:ice_castle_room_entrance"));
      } else {
         template = templateManager.get(minecraftServer, new ResourceLocation("arpg:ice_castle_balcony_" + (random.nextInt(2) + 1)));
      }

      PlacementSettings settings = new PlacementSettings();
      int sx = -1;
      int sz = -1;
      if (rotation == 0) {
         settings.setRotation(Rotation.CLOCKWISE_180);
         sx = 1;
         sz = 1;
         BlockPos transfpos = position.add(-1, 0, -1);
      }

      if (rotation == 1) {
         settings.setRotation(Rotation.CLOCKWISE_90);
         sx = 1;
         sz = -1;
         BlockPos var21 = position.add(-1, 0, 0);
      }

      if (rotation == 2) {
         settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
         sx = -1;
         sz = 1;
         BlockPos var22 = position.add(0, 0, -1);
      }

      if (rotation == 3) {
         settings.setRotation(Rotation.NONE);
      }

      if (placeOutRoom) {
         template.addBlocksToWorld(worldIn, position.add(sx * 4, 0, sz * 4), ChestReplacersFrozen.replacerIceCastle, settings, 2);
      }

      if (mode == 1 && placeOutRoom) {
         template2.addBlocksToWorld(worldIn, position.add(sx * 4, 9, sz * 4), ChestReplacersFrozen.replacerIceCastle, settings, 2);
      }

      if (mode != 3 && mode != 2) {
         template3.addBlocksToWorld(worldIn, position.add(sx * 4, -9, sz * 4), ChestReplacersFrozen.replacerIceCastle, settings, 2);
         if (minitower) {
            this.placeMiniTower(worldIn, random, position.add(0, 18, 0), settings);
         }
      }
   }

   public void placeMiniTower(World worldIn, Random random, BlockPos position, PlacementSettings settings) {
      WorldServer worldServer = (WorldServer)worldIn;
      MinecraftServer minecraftServer = worldIn.getMinecraftServer();
      TemplateManager templateManager = worldServer.getStructureTemplateManager();
      int updisplace = 0;
      Template templatetower1 = templateManager.get(minecraftServer, new ResourceLocation("arpg:ice_castle_tower_small_" + (random.nextInt(3) + 1)));
      Template templatetop = templateManager.get(minecraftServer, new ResourceLocation("arpg:ice_castle_tower_top_small"));
      int sx = -1;
      int sz = -1;
      int sxx = 0;
      int szz = -1;
      if (settings.getRotation() == Rotation.CLOCKWISE_180) {
         sx = 1;
         sz = 1;
         sxx = 0;
         szz = 1;
      }

      if (settings.getRotation() == Rotation.CLOCKWISE_90) {
         sx = 1;
         sz = -1;
         sxx = 1;
         szz = 0;
      }

      if (settings.getRotation() == Rotation.COUNTERCLOCKWISE_90) {
         sx = -1;
         sz = 1;
         sxx = -1;
         szz = 0;
      }

      for (int i = 0; i < random.nextInt(4); i++) {
         templatetower1.addBlocksToWorld(
            worldIn, position.add(sx * 3 + sxx, updisplace, sz * 3 + szz), ChestReplacersFrozen.replacerIceCastle, settings, 2
         );
         updisplace += 8;
      }

      templatetop.addBlocksToWorld(worldIn, position.add(sx * 4 + sxx, updisplace, sz * 4 + szz), ChestReplacersFrozen.replacerIceCastle, settings, 2);
      updisplace += 9;
      int rr = random.nextInt(5);

      for (int i = 0; i < random.nextInt(9) + 4; i++) {
         if (i < rr) {
            worldIn.setBlockState(position.add(sxx, updisplace, szz), Blocks.COBBLESTONE_WALL.getDefaultState());
            updisplace++;
         } else {
            worldIn.setBlockState(position.add(sxx, updisplace, szz), Blocks.IRON_BARS.getDefaultState());
            updisplace++;
         }
      }
   }

   public void placeWalls(World worldIn, Random random, List<BlockPos> list) {
      for (BlockPos pos : list) {
         if (random.nextFloat() < 0.4) {
            BlockPos offspos = pos.add(9, 0, 0);
            if (!this.listPosMatch(list, offspos) && worldIn.isAirBlock(pos.add(4, 1, 0))) {
               this.placeWallWithRotation(worldIn, random, offspos, 2);
            }
         }

         if (random.nextFloat() < 0.4) {
            BlockPos offspos = pos.add(-9, 0, 0);
            if (!this.listPosMatch(list, offspos) && worldIn.isAirBlock(pos.add(-4, 1, 0))) {
               this.placeWallWithRotation(worldIn, random, offspos, 1);
            }
         }

         if (random.nextFloat() < 0.4) {
            BlockPos offspos = pos.add(0, 0, 9);
            if (!this.listPosMatch(list, offspos) && worldIn.isAirBlock(pos.add(0, 1, 4))) {
               this.placeWallWithRotation(worldIn, random, offspos, 3);
            }
         }

         if (random.nextFloat() < 0.4) {
            BlockPos offspos = pos.add(0, 0, -9);
            if (!this.listPosMatch(list, offspos) && worldIn.isAirBlock(pos.add(0, 1, -4))) {
               this.placeWallWithRotation(worldIn, random, offspos, 0);
            }
         }
      }
   }

   public void placeWallWithRotation(World worldIn, Random random, BlockPos position, int rotation) {
      WorldServer worldServer = (WorldServer)worldIn;
      MinecraftServer minecraftServer = worldIn.getMinecraftServer();
      TemplateManager templateManager = worldServer.getStructureTemplateManager();
      Template template = templateManager.get(minecraftServer, new ResourceLocation("arpg:ice_castle_underground_wall_" + (random.nextInt(3) + 1)));
      PlacementSettings settings = new PlacementSettings();
      int sx = -1;
      int sz = -1;
      if (rotation == 0) {
         settings.setRotation(Rotation.CLOCKWISE_180);
         sx = 1;
         sz = 1;
         BlockPos transfpos = position.add(-1, 0, -1);
      }

      if (rotation == 1) {
         settings.setRotation(Rotation.CLOCKWISE_90);
         sx = 1;
         sz = -1;
         BlockPos var14 = position.add(-1, 0, 0);
      }

      if (rotation == 2) {
         settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
         sx = -1;
         sz = 1;
         BlockPos var15 = position.add(0, 0, -1);
      }

      if (rotation == 3) {
         settings.setRotation(Rotation.NONE);
      }

      template.addBlocksToWorld(worldIn, position.add(sx * 4, 0, sz * 4), ChestReplacersFrozen.replacerIceCastle, settings, 2);
   }

   public void generateDungeonEntry(World worldIn, Random random, BlockPos position, int generatorRotation) {
      WorldServer worldServer = (WorldServer)worldIn;
      MinecraftServer minecraftServer = worldIn.getMinecraftServer();
      TemplateManager templateManager = worldServer.getStructureTemplateManager();
      Template template = templateManager.get(minecraftServer, new ResourceLocation("arpg:ice_castle_dungeon_entry"));
      PlacementSettings settings = new PlacementSettings();
      int sx = -1;
      int sz = -1;
      int swr = position.getX() % 4;
      if (swr == 0) {
         settings.setRotation(Rotation.CLOCKWISE_180);
         sx = 1;
         sz = 1;
      }

      if (swr == 1) {
         settings.setRotation(Rotation.CLOCKWISE_90);
         sx = 1;
         sz = -1;
      }

      if (swr == 2) {
         settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
         sx = -1;
         sz = 1;
      }

      if (swr == 3) {
         settings.setRotation(Rotation.NONE);
      }

      template.addBlocksToWorld(worldIn, position.add(sx * 4, 0, sz * 4), ChestReplacersFrozen.replacerIceCastle, settings, 2);
   }

   public void generateHall(World worldIn, Random random, BlockPos position, int samples, List<BlockPos> list, int generatorRotation) {
      if (!this.listPosMatch(list, position)) {
         list.add(position);
         WorldServer worldServer = (WorldServer)worldIn;
         MinecraftServer minecraftServer = worldIn.getMinecraftServer();
         TemplateManager templateManager = worldServer.getStructureTemplateManager();
         Template template = templateManager.get(minecraftServer, new ResourceLocation("arpg:ice_castle_underground_hall_" + (random.nextInt(3) + 1)));
         PlacementSettings settings = new PlacementSettings();
         int sx = -1;
         int sz = -1;
         int swr = random.nextInt(4);
         if (swr == 0) {
            settings.setRotation(Rotation.CLOCKWISE_180);
            sx = 1;
            sz = 1;
         }

         if (swr == 1) {
            settings.setRotation(Rotation.CLOCKWISE_90);
            sx = 1;
            sz = -1;
         }

         if (swr == 2) {
            settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
            sx = -1;
            sz = 1;
         }

         if (swr == 3) {
            settings.setRotation(Rotation.NONE);
         }

         template.addBlocksToWorld(worldIn, position.add(sx * 4, 0, sz * 4), ChestReplacersFrozen.replacerIceCastle, settings, 2);
      }

      if (--samples > 0) {
         int rotat = 0;
         if (random.nextFloat() < 0.65) {
            rotat = random.nextInt(4);
         } else {
            rotat = generatorRotation;
         }

         BlockPos nextpos = position;
         switch (rotat) {
            case 0:
               nextpos = position.add(9, 0, 0);
            case 1:
               nextpos = nextpos.add(-9, 0, 0);
            case 2:
               nextpos = nextpos.add(0, 0, 9);
            case 3:
               nextpos = nextpos.add(0, 0, -9);
            default:
               if (random.nextFloat() < 0.85) {
                  this.generateHall(worldIn, random, nextpos, samples, list, rotat);
               } else if (random.nextFloat() < 0.7) {
                  this.generateCorridor(worldIn, random, nextpos, samples, list, rotat);
               } else if (!this.generateBigRoom(worldIn, random, nextpos, list, rotat)) {
                  this.generateCorridor(worldIn, random, nextpos, samples, list, rotat);
               } else {
                  this.generateHall(worldIn, random, nextpos, samples, list, rotat);
               }

               if (random.nextFloat() < 0.3 && rotat != generatorRotation) {
                  BlockPos nextpos2 = position;
                  switch (generatorRotation) {
                     case 0:
                        nextpos2 = position.add(9, 0, 0);
                     case 1:
                        nextpos2 = nextpos2.add(-9, 0, 0);
                     case 2:
                        nextpos2 = nextpos2.add(0, 0, 9);
                     case 3:
                        nextpos2 = nextpos2.add(0, 0, -9);
                  }

                  if (random.nextFloat() < 0.4
                     && nextpos2.getY() > 19
                     && !this.listPosMatch(list, nextpos2)
                     && !this.listPosMatch(list, nextpos2.add(0, -9, 0))) {
                     this.generateHallDouble(worldIn, random, nextpos2, list);
                     this.generateHall(worldIn, random, nextpos2.add(0, -9, 0), Math.round(samples / 1.3F), list, random.nextInt(4));
                  } else {
                     this.generateHall(worldIn, random, nextpos2, Math.round((float)(samples / 2)), list, generatorRotation);
                  }
               }
         }
      }
   }

   public void generateHallDouble(World worldIn, Random random, BlockPos position, List<BlockPos> list) {
      if (!this.listPosMatch(list, position) && !this.listPosMatch(list, position.add(0, -9, 0))) {
         list.add(position);
         list.add(position.add(0, -9, 0));
         WorldServer worldServer = (WorldServer)worldIn;
         MinecraftServer minecraftServer = worldIn.getMinecraftServer();
         TemplateManager templateManager = worldServer.getStructureTemplateManager();
         Template template = templateManager.get(
            minecraftServer, new ResourceLocation("arpg:ice_castle_underground_double_" + (random.nextInt(2) + 1))
         );
         PlacementSettings settings = new PlacementSettings();
         int sx = -1;
         int sz = -1;
         int swr = random.nextInt(4);
         if (swr == 0) {
            settings.setRotation(Rotation.CLOCKWISE_180);
            sx = 1;
            sz = 1;
            BlockPos transfpos = position.add(-1, 0, -1);
         }

         if (swr == 1) {
            settings.setRotation(Rotation.CLOCKWISE_90);
            sx = 1;
            sz = -1;
            BlockPos var14 = position.add(-1, 0, 0);
         }

         if (swr == 2) {
            settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
            sx = -1;
            sz = 1;
            BlockPos var15 = position.add(0, 0, -1);
         }

         if (swr == 3) {
            settings.setRotation(Rotation.NONE);
         }

         template.addBlocksToWorld(worldIn, position.add(sx * 4, -9, sz * 4), ChestReplacersFrozen.replacerIceCastle, settings, 2);
      }
   }

   public void generateCorridor(World worldIn, Random random, BlockPos position, int samples, List<BlockPos> list, int generatorRotation) {
      if (!this.listPosMatch(list, position)) {
         list.add(position);
         WorldServer worldServer = (WorldServer)worldIn;
         MinecraftServer minecraftServer = worldIn.getMinecraftServer();
         TemplateManager templateManager = worldServer.getStructureTemplateManager();
         Template template = templateManager.get(
            minecraftServer, new ResourceLocation("arpg:ice_castle_underground_corridor_" + (random.nextInt(2) + 1))
         );
         PlacementSettings settings = new PlacementSettings();
         int sx = -1;
         int sz = -1;
         if (generatorRotation == 0) {
            settings.setRotation(Rotation.CLOCKWISE_180);
            sx = 1;
            sz = 1;
            BlockPos transfpos = position.add(-1, 0, -1);
         }

         if (generatorRotation == 1) {
            settings.setRotation(Rotation.CLOCKWISE_90);
            sx = 1;
            sz = -1;
            BlockPos var18 = position.add(-1, 0, 0);
         }

         if (generatorRotation == 2) {
            settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
            sx = -1;
            sz = 1;
            BlockPos var19 = position.add(0, 0, -1);
         }

         if (generatorRotation == 3) {
            settings.setRotation(Rotation.NONE);
         }

         template.addBlocksToWorld(worldIn, position.add(sx * 4, 0, sz * 4), ChestReplacersFrozen.replacerIceCastle, settings, 2);
      }

      if (--samples > 0) {
         BlockPos nextpos = position;
         switch (generatorRotation) {
            case 0:
               nextpos = position.add(9, 0, 0);
            case 1:
               nextpos = nextpos.add(-9, 0, 0);
            case 2:
               nextpos = nextpos.add(0, 0, 9);
            case 3:
               nextpos = nextpos.add(0, 0, -9);
            default:
               if (random.nextFloat() < 0.17) {
                  this.generateHall(worldIn, random, nextpos, samples, list, generatorRotation);
               } else {
                  this.generateCorridor(worldIn, random, nextpos, samples, list, generatorRotation);
               }
         }
      }
   }

   public boolean generateBigRoom(World worldIn, Random random, BlockPos pos, List<BlockPos> list, int generatorRotation) {
      BlockPos position = pos;
      switch (generatorRotation) {
         case 0:
            position = pos.add(9, 0, 0);
         case 1:
            position = position.add(-9, 0, 0);
         case 2:
            position = position.add(0, 0, 9);
         case 3:
            position = position.add(0, 0, -9);
         default:
            if (!this.listPosMatch(list, position)
               && !this.listPosMatch(list, position.add(9, 0, 0))
               && !this.listPosMatch(list, position.add(-9, 0, 0))
               && !this.listPosMatch(list, position.add(0, 0, 9))
               && !this.listPosMatch(list, position.add(0, 0, -9))
               && !this.listPosMatch(list, position.add(9, 0, 9))
               && !this.listPosMatch(list, position.add(-9, 0, 9))
               && !this.listPosMatch(list, position.add(9, 0, -9))
               && !this.listPosMatch(list, position.add(-9, 0, -9))) {
               list.add(position);
               list.add(position.add(9, 0, 0));
               list.add(position.add(-9, 0, 0));
               list.add(position.add(0, 0, 9));
               list.add(position.add(0, 0, -9));
               list.add(position.add(9, 0, 9));
               list.add(position.add(-9, 0, 9));
               list.add(position.add(9, 0, -9));
               list.add(position.add(-9, 0, -9));
               WorldServer worldServer = (WorldServer)worldIn;
               MinecraftServer minecraftServer = worldIn.getMinecraftServer();
               TemplateManager templateManager = worldServer.getStructureTemplateManager();
               Template template = templateManager.get(minecraftServer, new ResourceLocation("arpg:ice_castle_underground_bigroom_1"));
               Template templatevases = templateManager.get(minecraftServer, new ResourceLocation("arpg:ice_castle_underground_vases_1"));
               PlacementSettings settings = new PlacementSettings();
               int sx = -1;
               int sz = -1;
               int swr = random.nextInt(4);
               if (swr == 0) {
                  settings.setRotation(Rotation.CLOCKWISE_180);
                  sx = 1;
                  sz = 1;
                  BlockPos transfpos = position.add(-1, 0, -1);
               }

               if (swr == 1) {
                  settings.setRotation(Rotation.CLOCKWISE_90);
                  sx = 1;
                  sz = -1;
                  BlockPos var18 = position.add(-1, 0, 0);
               }

               if (swr == 2) {
                  settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
                  sx = -1;
                  sz = 1;
                  BlockPos var19 = position.add(0, 0, -1);
               }

               if (swr == 3) {
                  settings.setRotation(Rotation.NONE);
               }

               template.addBlocksToWorld(worldIn, position.add(sx * 13, 0, sz * 13), ChestReplacersFrozen.replacerIceCastle, settings, 2);
               PlacementSettings settingsvases = new PlacementSettings();
               settingsvases.setIntegrity(random.nextFloat()).setRotation(settings.getRotation());
               templatevases.addBlocksToWorld(worldIn, position.add(sx * 13, 0, sz * 13), ChestReplacersFrozen.replacerIceCastle, settingsvases, 2);
               return true;
            } else {
               return false;
            }
      }
   }
}
