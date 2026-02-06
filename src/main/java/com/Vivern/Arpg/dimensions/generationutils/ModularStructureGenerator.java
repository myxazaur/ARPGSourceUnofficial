package com.Vivern.Arpg.dimensions.generationutils;

import com.Vivern.Arpg.blocks.Pilaster;
import com.Vivern.Arpg.dimensions.ethernalfrost.DimensionEthernalFrost;
import com.Vivern.Arpg.loot.ListLootTable;
import com.Vivern.Arpg.main.BlocksRegister;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ModularStructureGenerator extends WorldGenAdvanced {
   public static ModularStructureGenerator generator_niveous_hall = new ModularStructureGenerator(
      generator -> {
         int floorThick = 2;
         int wallThick = 1;
         int roofThick = 2;
         ModulePlaceBlock air = new ModulePlaceBlock(generator, Blocks.AIR.getDefaultState());
         ModulePlaceBlockFacing column = new ModulePlaceBlockFacing(generator, BlocksRegister.NIVEOUSCOLUMN, false);
         ModulePlaceBlock bricks = new ModulePlaceBlock(generator, BlocksRegister.NIVEOUSBRICKS.getDefaultState());
         ModulePlaceBlock polished = new ModulePlaceBlock(generator, BlocksRegister.POLISHEDNIVEOUSBLOCK.getDefaultState());
         ModulePlaceBlock packetice = new ModulePlaceBlock(generator, Blocks.PACKED_ICE.getDefaultState());
         ModulePlaceBlock ice = new ModulePlaceBlock(generator, Blocks.ICE.getDefaultState());
         ModulePlaceBlock altar = new ModulePlaceBlock(generator, BlocksRegister.NIVEOLITEGAMEBLOCK.getDefaultState());
         ModulePlaceBlockFacing pilaster = new ModulePlaceBlockFacing(generator, BlocksRegister.NIVEOUSPILASTER, false);
         ModulePlaceBlockFacing pilasterpolished = new ModulePlaceBlockFacing(generator, BlocksRegister.POLISHEDNIVEOUSPILASTER, false);
         ModuleChest chest = new ModuleChest(generator, BlocksRegister.CHESTFROZEN, ListLootTable.CHESTS_NIVEOUS_HALL, 0.04F, true);
         ModulePlaceBlock spawner = new ModulePlaceBlock(generator, BlocksRegister.MOBSPAWNERFROZEN.getDefaultState());
         spawner.interfaceOn = (sourcePos, iblockstate, facing, sourceModule, age) -> DimensionEthernalFrost.setupRandomSpawner(
            null, generator.getAccess().getTileEntity(sourcePos), DimensionEthernalFrost.EnumEverfrostSpawner.NIVEOUS_HALL, generator.rand
         );
         spawner.chance = 0.25F;
         IBlockState holeSt = BlocksRegister.NIVEOUSHOLE.getDefaultState();
         IBlockState iceeSt = Blocks.ICE.getDefaultState();
         IBlockState pilaSt = BlocksRegister.POLISHEDNIVEOUSPILASTER.getDefaultState().withProperty(Pilaster.FACING, EnumFacing.DOWN);
         ModulePlaceMultiblock holes = new ModulePlaceMultiblock(generator, true)
            .add(holeSt, 0, -2, 0)
            .add(holeSt, 1, -2, 0)
            .add(holeSt, 1, -2, 1)
            .add(holeSt, 0, -2, 1)
            .add(pilaSt, 0, 6, 0)
            .add(pilaSt, 1, 6, 0)
            .add(pilaSt, 1, 6, 1)
            .add(pilaSt, 0, 6, 1)
            .add(iceeSt, 0, 7, 0)
            .add(iceeSt, 1, 7, 0)
            .add(iceeSt, 1, 7, 1)
            .add(iceeSt, 0, 7, 1);
         ModuleScatter holesScatter = new ModuleScatter(generator, 26, 26, 1, 0, 3, 4, true);
         holesScatter.onScatter = holes;
         ModuleColumn columngen = new ModuleColumn(generator, 9, 1, column);
         columngen.addBaseLayer(pilaster, false);
         columngen.addTopLayer(pilaster, false);
         columngen.addTopLayer(bricks, false);
         columngen.center = spawner;
         columngen.centerOffset = 2;
         ModuleRectangularRoom chestsPlacer = new ModuleRectangularRoom(generator, 4, 4, 1, 1, 0, 0, false, false);
         chestsPlacer.wallBlockPlacer = chest;
         chestsPlacer.queue = 3;
         chestsPlacer.enableOffsetFromSource = false;
         int[][] doorProfile1 = new int[][]{{1, 1, 3, 3}, {0, 0, 1, 3}, {0, 0, 0, 1}, {0, 0, 0, 1}, {0, 0, 0, 1}};
         ModuleDoor doorSmall = new ModuleDoor(generator, wallThick, floorThick, doorProfile1);
         doorSmall.air = air;
         doorSmall.insideWall = polished;
         doorSmall.doorFraming = pilasterpolished;
         doorSmall.queue = 1;
         int[][] doorProfile2 = new int[][]{
            {1, 1, 1, 1, 4}, {0, 0, 0, 0, 3}, {0, 0, 0, 0, 3}, {0, 0, 0, 0, 3}, {0, 0, 0, 0, 3}, {0, 0, 0, 0, 3}, {0, 0, 0, 0, 3}, {0, 0, 0, 0, 3}
         };
         ModuleDoor doorBig = new ModuleDoor(generator, wallThick, floorThick, doorProfile2);
         doorBig.air = air;
         doorBig.insideWall = polished;
         doorBig.doorFraming = pilasterpolished;
         doorBig.doorFraming4 = polished;
         doorBig.queue = 1;
         ModuleScaffold scaffold = new ModuleScaffold(generator, 100, null);
         scaffold.scaffoldBlockPlacer = packetice;
         scaffold.baseBlockPlacer = bricks;
         ModuleRectangularRoom perimeterFoundation = new ModuleRectangularRoom(generator, 7, 7, 2, 2, 0, 0, false, false);
         perimeterFoundation.wallBlockPlacer = polished;
         perimeterFoundation.cornerBlockPlacer = column;
         perimeterFoundation.underFloorModule = scaffold;
         perimeterFoundation.underfloorModuleRadiusAdd = 1;
         perimeterFoundation.enableOffsetFromSource = false;
         ModuleRectangularRoom mainRoom = new ModuleRectangularRoom(generator, 27, 27, 13, wallThick, floorThick, roofThick, true, true);
         mainRoom.floorBlockPlacer = polished;
         mainRoom.roofBlockPlacer = polished;
         mainRoom.wallBlockPlacer = bricks;
         mainRoom.cornerBlockPlacer = column;
         mainRoom.air = air;
         mainRoom.previousRoom = doorSmall;
         mainRoom.limitValue = 1;
         mainRoom.checkLimit = true;
         mainRoom.addHitboxToCollisionList = true;
         mainRoom.onfloorCenterModule = new ModuleArray(generator, holesScatter, altar);
         mainRoom.underFloorModule = scaffold;
         ModuleRectangularRoom iceRoomOnRoof = new ModuleRectangularRoom(generator, 22, 22, 7, 1, 2, 1, true, true);
         iceRoomOnRoof.floorBlockPlacer = bricks;
         iceRoomOnRoof.roofBlockPlacer = polished;
         iceRoomOnRoof.wallBlockPlacer = air;
         iceRoomOnRoof.cornerBlockPlacer = air;
         iceRoomOnRoof.air = ice;
         iceRoomOnRoof.enableOffsetFromSource = false;
         ModuleRectangularRoom iceRoomOnRoof2 = new ModuleRectangularRoom(generator, 10, 10, 4, 1, 1, 1, true, true);
         iceRoomOnRoof2.floorBlockPlacer = bricks;
         iceRoomOnRoof2.roofBlockPlacer = polished;
         iceRoomOnRoof2.wallBlockPlacer = air;
         iceRoomOnRoof2.cornerBlockPlacer = air;
         iceRoomOnRoof2.air = ice;
         iceRoomOnRoof2.enableOffsetFromSource = false;
         ModuleStructure spire1 = new ModuleStructure(generator, ":niveous_spire_1", 5, 0);
         ModuleStructure spire2 = new ModuleStructure(generator, ":niveous_spire_2", 5, 0);
         mainRoom.onroofCenterModule = iceRoomOnRoof;
         iceRoomOnRoof.onroofCenterModule = iceRoomOnRoof2;
         iceRoomOnRoof2.onroofCenterModule = spire2;
         ModuleRectangularRoom perimeterRoomFirst = new ModuleRectangularRoom(generator, 5, 5, 13, wallThick, floorThick, roofThick, true, true);
         perimeterRoomFirst.floorBlockPlacer = polished;
         perimeterRoomFirst.roofBlockPlacer = polished;
         perimeterRoomFirst.wallBlockPlacer = bricks;
         perimeterRoomFirst.cornerBlockPlacer = column;
         perimeterRoomFirst.air = air;
         perimeterRoomFirst.previousRoom = doorSmall;
         perimeterRoomFirst.onfloorCenterModule = columngen;
         perimeterRoomFirst.limitValue = 1;
         perimeterRoomFirst.checkLimit = true;
         perimeterRoomFirst.addHitboxToCollisionList = true;
         perimeterRoomFirst.collisionCheck = true;
         perimeterRoomFirst.onroofCenterModule = spire2;
         ModuleRectangularRoom perimeterRoomNext = new ModuleRectangularRoom(generator, 5, 5, 13, wallThick, floorThick, roofThick, true, true);
         perimeterRoomNext.floorBlockPlacer = polished;
         perimeterRoomNext.roofBlockPlacer = polished;
         perimeterRoomNext.wallBlockPlacer = bricks;
         perimeterRoomNext.cornerBlockPlacer = column;
         perimeterRoomNext.air = air;
         perimeterRoomNext.previousRoom = doorBig;
         perimeterRoomNext.onfloorCenterModule = new ModuleArray(generator, columngen, chestsPlacer);
         perimeterRoomNext.limitValue = 1;
         perimeterRoomNext.checkLimit = true;
         perimeterRoomNext.addHitboxToCollisionList = true;
         perimeterRoomNext.collisionCheck = true;
         perimeterRoomNext.boundingBoxId = 2;
         perimeterRoomNext.onroofCenterModule = spire1;
         perimeterRoomNext.centerModule = perimeterFoundation;
         ModuleIf.IModuleCondition conditionCollideBrick = (msgenerator, sourcePos, facing, sourceModule, age) -> msgenerator.isBoxHasIdAt(
            sourcePos.offset(facing, 3), 2
         );
         ModuleIf.IModuleCondition conditionEndOfGeneration = (msgenerator, sourcePos, facing, sourceModule, age) -> !msgenerator.isBoxHasIdAt(
               sourcePos.offset(facing, 3), 2
            )
            && age >= 5;
         perimeterRoomNext.nextRoom = new ModuleArray(
            generator, perimeterRoomNext, new ModuleIf(generator, doorBig, conditionCollideBrick), new ModuleIf(generator, doorSmall, conditionEndOfGeneration)
         );
         perimeterRoomNext.nextRoomChance = 0.75F;
         perimeterRoomFirst.nextRoom = perimeterRoomNext;
         perimeterRoomFirst.nextRoomChance = 0.75F;
         mainRoom.nextRoom = perimeterRoomFirst;
         mainRoom.previousRoom = perimeterRoomFirst;
         mainRoom.nextRoomChance = 1.0F;
         return mainRoom;
      },
      35
   );
   public int maxModulesLimit;
   public int modulesLimit;
   public IModuleInit init;
   public Module startModule;
   public Random rand;
   public WorldServer worldserver;
   public boolean debugSigns = false;
   public HashMap<Integer, ModularGenerationList> generationMap;
   public ArrayList<BoundingBoxWithId> collisionList;
   public int keyUsed;

   public ModularStructureGenerator(IModuleInit init, int maxModulesLimit) {
      this.init = init;
      this.maxModulesLimit = maxModulesLimit;
   }

   public void addToGenerationQueue(Module module, BlockPos sourcePos, EnumFacing facing, Module sourceModule, int age) {
      if (module.canBeQueued) {
         int qpos = module.queue + age;
         ModularGenerationInstance instance = new ModularGenerationInstance(
            module, sourcePos, facing, sourceModule, age
         );
         ModularGenerationList generationList;
         if (this.generationMap.containsKey(qpos)) {
            generationList = this.generationMap.get(qpos);
         } else {
            generationList = new ModularGenerationList(qpos);
            this.generationMap.put(qpos, generationList);
         }

         generationList.add(instance);
      } else {
         module.generate(sourcePos, facing, sourceModule, age);
      }
   }

   public boolean generate(World worldIn, Random rand, BlockPos position) {
      this.generationMap = new HashMap<>();
      this.collisionList = new ArrayList<>();
      this.keyUsed = Integer.MIN_VALUE;
      this.setWorld(worldIn);
      if (worldIn instanceof WorldServer) {
         this.worldserver = (WorldServer)worldIn;
      }

      if (this.startModule == null) {
         this.startModule = this.init.getStartModule(this);
      }

      this.rand = rand;
      this.modulesLimit = 0;
      this.startModule.generate(position, EnumFacing.byHorizontalIndex(rand.nextInt(4)), Module.EMPTY, 0);
      int amount = 0;

      while (true) {
         boolean has = false;

         for (int key : this.generationMap.keySet()) {
            if (key > this.keyUsed) {
               this.keyUsed = key;
               has = true;
               break;
            }
         }

         if (!has) {
            return true;
         }

         ModularGenerationList generationList = this.generationMap.get(this.keyUsed);
         generationList.generateAll();
         System.out.println("generate while | amount " + ++amount + " keyUsed " + this.keyUsed);
      }
   }

   public boolean cannotGenerate(Module module, BlockPos sourcePos, EnumFacing facing, Module sourceModule, int age) {
      if (module.checkLimit && this.modulesLimit >= this.maxModulesLimit) {
         return true;
      } else {
         if (module.collisionCheck) {
            AxisAlignedBB aabb = module.getBoundingBox(sourcePos, facing, sourceModule, age);
            if (aabb != null) {
               for (BoundingBoxWithId hit : this.collisionList) {
                  if (aabb.intersects(hit.aabb)) {
                     return true;
                  }
               }
            }
         }

         return module.chance != 1.0F ? this.rand.nextFloat() > module.chance : false;
      }
   }

   public void onEndGenerate(Module module, BlockPos sourcePos, EnumFacing facing, Module sourceModule, int age) {
      this.modulesLimit = this.modulesLimit + module.limitValue;
      if (module.addHitboxToCollisionList) {
         BoundingBoxWithId boxWithId = new BoundingBoxWithId(
            module.getBoundingBox(sourcePos, facing, sourceModule, age), module.boundingBoxId
         );
         this.collisionList.add(boxWithId);
      }

      if (this.debugSigns && module.canDebug) {
         this.placeDebugSign(sourcePos, module.getClass().getSimpleName(), "" + facing + " | age: " + age);
      }
   }

   @Nullable
   public BoundingBoxWithId getFirstBoxAt(BlockPos pos) {
      for (BoundingBoxWithId hit : this.collisionList) {
         if (hit.aabb
            .intersects(
               pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1
            )) {
            return hit;
         }
      }

      return null;
   }

   public boolean isBoxHasIdAt(BlockPos pos, int idTo) {
      for (BoundingBoxWithId hit : this.collisionList) {
         if (hit.aabb
               .intersects(
                  pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1
               )
            && hit.id == idTo) {
            return true;
         }
      }

      return false;
   }

   public void placeDebugSign(BlockPos pos, String text0, String text1) {
      if (this.getAccess() instanceof World) {
         World world = (World)this.getAccess();
         world.setBlockState(pos, Blocks.STANDING_SIGN.getDefaultState(), 2);
         TileEntity tile = world.getTileEntity(pos);
         if (tile instanceof TileEntitySign) {
            TileEntitySign sign = (TileEntitySign)tile;
            sign.signText[0] = new TextComponentString(text0);
            sign.signText[1] = new TextComponentString(text1);
         }
      }
   }

   public static class BoundingBoxWithId {
      public AxisAlignedBB aabb;
      public int id;

      public BoundingBoxWithId(AxisAlignedBB aabb, int id) {
         this.aabb = aabb;
         this.id = id;
      }
   }

   public interface IModuleInit {
      Module getStartModule(ModularStructureGenerator var1);
   }

   public class ModularGenerationInstance {
      public int age;
      public Module module;
      public BlockPos sourcePos;
      public EnumFacing facing;
      public Module sourceModule;

      public ModularGenerationInstance(Module module, BlockPos sourcePos, EnumFacing facing, Module sourceModule, int age) {
         this.age = age;
         this.module = module;
         this.sourcePos = sourcePos;
         this.facing = facing;
         this.sourceModule = sourceModule;
      }
   }

   public class ModularGenerationList implements Comparable<ModularGenerationList> {
      public int queuePosition;
      public ArrayList<ModularGenerationInstance> list;

      public ModularGenerationList(int queuePosition) {
         this.queuePosition = queuePosition;
         this.list = new ArrayList<>();
      }

      public void add(ModularGenerationInstance instance) {
         this.list.add(instance);
      }

      public void generateAll() {
         for (ModularGenerationInstance instance : this.list) {
            instance.module.generate(instance.sourcePos, instance.facing, instance.sourceModule, instance.age);
         }
      }

      public int compareTo(ModularGenerationList other) {
         return other.queuePosition - this.queuePosition;
      }
   }
}
