package com.Vivern.Arpg.dimensions.ethernalfrost;

import com.Vivern.Arpg.dimensions.generationutils.GenerationHelper;
import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.NamedInt;
import com.Vivern.Arpg.main.StructPos;
import com.Vivern.Arpg.mobs.NPCMobsPack;
import com.Vivern.Arpg.tileentity.TileMonsterSpawner;
import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;

public class BristlingVillage {
   public static Predicate<IBlockState> BLOCKS_TO_COLLIDE = new Predicate<IBlockState>() {
      public boolean apply(IBlockState input) {
         if (input.getBlock() == BlocksRegister.CONIFERPLANKS) {
            return true;
         } else {
            return input.getMaterial() == Material.SNOW
               ? input.isFullBlock()
               : input.getMaterial() != Material.AIR
                  && input.getMaterial() != Material.LEAVES
                  && input.getMaterial() != Material.PLANTS
                  && input.getMaterial() != Material.CACTUS
                  && input.getMaterial() != Material.VINE
                  && input.getMaterial() != Material.WOOD;
         }
      }
   };
   public ArrayList<StructPos> structuresSpawned = new ArrayList<>();
   public ArrayList<int[]> rememberedMobs = new ArrayList<>();
   public int outpathesLast;
   public int recursionLast;
   public int maximumRoadLength;
   public World world;
   public IBlockState[] roadBlocks = new IBlockState[]{
      BlocksRegister.CONIFERPLANKS.getDefaultState(), Blocks.SNOW.getDefaultState(), BlocksRegister.CONIFERPLANKS.getDefaultState()
   };
   public IBlockState[] fluidRoadBlocks = new IBlockState[]{
      BlocksRegister.CONIFERPLANKS.getDefaultState(), BlocksRegister.FROZENSTONE.getDefaultState(), BlocksRegister.FROZENBRICKS.getDefaultState()
   };
   public Random rand;
   public static NamedInt[] structuresHomes = new NamedInt[]{
      new NamedInt(":frozen_home_1", 6),
      new NamedInt(":frozen_home_2", 9),
      new NamedInt(":frozen_home_3", 6),
      new NamedInt(":frozen_home_4", 9),
      new NamedInt(":frozen_home_5", 14),
      new NamedInt(":frozen_home_6", 8),
      new NamedInt(":frozen_home_7", 6)
   };
   public static NamedInt[] structuresRoad = new NamedInt[]{
      new NamedInt(":christmas_tree_1", 4), new NamedInt(":christmas_tree_2", 5), new NamedInt(":bristling_farm_1", 4), new NamedInt(":bristling_farm_2", 4)
   };
   public static NamedInt[] structuresLamps = new NamedInt[]{
      new NamedInt(":bristling_lamp_1", 1), new NamedInt(":bristling_lamp_2", 1), new NamedInt(":bristling_lamp_3", 1), new NamedInt(":bristling_lamp_4", 1)
   };
   public int roadRadius = 2;
   public int noHomesTime;
   public int homesSpawned = 0;
   public BlockPos center;
   public int maxVillageRadius = 64;
   public int lampstyle;

   public BristlingVillage(World world, int maxRecursiveOutpathes, int allLengthBound, int maximumRoadLength, Random rand, int noHomesTime) {
      this.world = world;
      this.outpathesLast = maxRecursiveOutpathes;
      this.recursionLast = allLengthBound;
      this.maximumRoadLength = maximumRoadLength;
      this.rand = rand;
      this.noHomesTime = noHomesTime;
   }

   public void generate(BlockPos pos) {
      int lamp = this.rand.nextInt(5);
      this.lampstyle = this.rand.nextInt(4);
      if (lamp == 0) {
         ChestReplacersFrozen.replacerPresentBoxLampState = BlocksRegister.LAMPRUBY.getDefaultState();
         ChestReplacersFrozen.replacerPresentBoxLanternState = BlocksRegister.LANTERNRUBY.getDefaultState();
      }

      if (lamp == 1) {
         ChestReplacersFrozen.replacerPresentBoxLampState = BlocksRegister.LAMPRHINESTONE.getDefaultState();
         ChestReplacersFrozen.replacerPresentBoxLanternState = BlocksRegister.LANTERNRHINESTONE.getDefaultState();
      }

      if (lamp == 2) {
         ChestReplacersFrozen.replacerPresentBoxLampState = BlocksRegister.LAMPTOPAZ.getDefaultState();
         ChestReplacersFrozen.replacerPresentBoxLanternState = BlocksRegister.LANTERNTOPAZ.getDefaultState();
      }

      if (lamp == 3) {
         ChestReplacersFrozen.replacerPresentBoxLampState = BlocksRegister.LAMPCITRINE.getDefaultState();
         ChestReplacersFrozen.replacerPresentBoxLanternState = BlocksRegister.LANTERNCITRINE.getDefaultState();
      }

      if (lamp == 4) {
         ChestReplacersFrozen.replacerPresentBoxLampState = BlocksRegister.LAMPDIAMOND.getDefaultState();
         ChestReplacersFrozen.replacerPresentBoxLanternState = BlocksRegister.LANTERNDIAMOND.getDefaultState();
      }

      this.center = this.world.getHeight(pos).add(0, 16, 0);
      int i1 = this.rand.nextInt(360);
      this.recursiveGenerate(this.center, GetMOP.YawToVec3d(i1), this.maximumRoadLength);
      if (this.homesSpawned < 10 && this.recursionLast > 0) {
         Vec3d direction = GetMOP.YawToVec3d(i1 + 180);

         for (int i = 2; i < 20; i += 2) {
            BlockPos offsetPos = this.center.add(direction.x * i, 0.0, direction.z * i);
            if (this.isClear(offsetPos, 1)) {
               this.recursiveGenerate(offsetPos, direction, this.maximumRoadLength);
               break;
            }
         }
      }

      for (int[] ints : this.rememberedMobs) {
         this.spawnMobAt(new BlockPos(ints[0], ints[1], ints[2]), ints[3], ints[4], ints[5]);
      }
   }

   public void recursiveGenerate(BlockPos pos, Vec3d direction, int last) {
      if (Math.abs(this.center.getX() - pos.getX()) <= this.maxVillageRadius
         && Math.abs(this.center.getZ() - pos.getZ()) <= this.maxVillageRadius) {
         last--;
      } else {
         last = 0;
      }

      this.recursionLast--;
      if (this.recursionLast > 0) {
         if (this.rand.nextFloat() < 0.5F) {
            direction = direction.rotateYaw(this.rand.nextFloat() - 0.5F);
         }

         pos = pos.add(direction.x, 0.0, direction.z);
         GetMOP.BlockTraceResult result = GetMOP.blockTrace(this.world, pos, EnumFacing.DOWN, 32, BLOCKS_TO_COLLIDE);
         if (result != null) {
            if ((this.rand.nextFloat() < 0.95F + Debugger.floats[7] || this.noHomesTime > 0) && last > 0) {
               if (this.isClear(result.pos, 0)) {
                  this.placeDirtRound(result.pos, this.roadRadius);
                  if (this.rand.nextFloat() < 0.01F) {
                     this.rememberMobAt(result.pos.up(), 0, 2, 2);
                  }

                  this.recursiveGenerate(pos, direction, last);
                  if (this.rand.nextFloat() < 0.35F + Debugger.floats[4]) {
                     float angle = GetMOP.Vec2dToYaw(direction.x, direction.z);
                     angle += this.rand.nextFloat() < 0.5F ? 90.0F + (this.rand.nextFloat() - 0.5F) * 90.0F : -90.0F - (this.rand.nextFloat() - 0.5F) * 90.0F;
                     Vec3d direction2 = GetMOP.YawToVec3d(angle);
                     if ((this.rand.nextFloat() < 0.8 + Debugger.floats[6] || this.noHomesTime > 0) && this.outpathesLast > 0) {
                        this.outpathesLast--;
                        this.noHomesTime--;
                        this.recursiveGenerate(pos, direction2.scale(this.roadRadius), this.maximumRoadLength);
                     } else {
                        NamedInt structure = this.rand.nextFloat() < 0.5F
                           ? structuresLamps[this.lampstyle]
                           : structuresRoad[this.rand.nextInt(structuresRoad.length)];
                        direction2 = direction2.scale(structure.value + 1 + this.roadRadius);
                        BlockPos offpos = pos.add(direction2.x, 0.0, direction2.z);
                        GetMOP.BlockTraceResult result2 = GetMOP.blockTrace(this.world, offpos, EnumFacing.DOWN, 32, BLOCKS_TO_COLLIDE);
                        if (result2 != null && this.isClear(result2.pos, structure.value)) {
                           int rotation = this.rand.nextInt(4);
                           GenerationHelper.placeStruct(
                              this.world, result2.pos, this.rand, structure.name, structure.value, 0, rotation, ChestReplacersFrozen.replacerPresentBox
                           );
                           this.structuresSpawned.add(new StructPos(result2.pos, rotation, structure.value));
                        }
                     }
                  }
               }
            } else {
               EnumFacing face = EnumFacing.fromAngle(GetMOP.Vec2dToYaw(-direction.x, direction.z));
               int structId = this.rand.nextInt(structuresHomes.length);
               NamedInt structure = structuresHomes[structId];
               BlockPos finalpos = result.pos.offset(face, structure.value);
               if (this.isClear(finalpos, structure.value)) {
                  GenerationHelper.placeStruct(
                     this.world,
                     finalpos,
                     this.rand,
                     structure.name,
                     structure.value,
                     structId == 4 ? -9 : 1,
                     face.getHorizontalIndex(),
                     ChestReplacersFrozen.replacerPresentBox
                  );
                  this.structuresSpawned.add(new StructPos(finalpos, face.getHorizontalIndex(), structure.value));
                  this.homesSpawned++;
                  this.placeFoundation(this.world, finalpos.up(), structure.value);
                  int mobsCount = this.rand.nextFloat() < 0.2F ? 2 : 1;

                  for (int i = 0; i < mobsCount; i++) {
                     this.rememberMobAt(finalpos.up(), 0, 3, 2);
                  }
               } else {
                  this.recursiveGenerate(pos, direction, last);
               }
            }
         }
      }
   }

   public boolean isClear(BlockPos pos, int size) {
      for (StructPos structPos : this.structuresSpawned) {
         int bigsize = size + structPos.size;
         if (Math.abs(structPos.getX() - pos.getX()) <= bigsize && Math.abs(structPos.getZ() - pos.getZ()) <= bigsize) {
            return false;
         }
      }

      return true;
   }

   public void placeDirtRound(BlockPos pos, int size) {
      int sizeSq = size * size;

      for (int x = -size; x <= size; x++) {
         for (int z = -size; z <= size; z++) {
            if (x * x + z * z <= sizeSq) {
               BlockPos poss = pos.add(x, 0, z);
               IBlockState hasState = this.world.getBlockState(poss);
               if (hasState.getBlock() != BlocksRegister.CONIFERPLANKS) {
                  if (hasState.getMaterial().isLiquid()) {
                     this.world.setBlockState(poss, this.fluidRoadBlocks[this.rand.nextInt(this.fluidRoadBlocks.length)], 2);
                  } else if (!BLOCKS_TO_COLLIDE.apply(this.world.getBlockState(poss.up())) && BLOCKS_TO_COLLIDE.apply(hasState)) {
                     this.world.setBlockState(poss, this.roadBlocks[this.rand.nextInt(this.roadBlocks.length)], 2);
                  }
               }
            }
         }
      }
   }

   public void rememberMobAt(BlockPos pos, int entityType, int spawnRange, int spawnHeight) {
      this.rememberedMobs.add(new int[]{pos.getX(), pos.getY(), pos.getZ(), entityType, spawnRange, spawnHeight});
   }

   public void spawnMobAt(BlockPos pos, int entityType, int spawnRange, int spawnHeight) {
      boolean landGround = true;
      double d0 = pos.getX() + (this.world.rand.nextDouble() - this.world.rand.nextDouble()) * spawnRange + 0.5;
      double d1 = pos.getY();
      double d2 = pos.getZ() + (this.world.rand.nextDouble() - this.world.rand.nextDouble()) * spawnRange + 0.5;
      d1 = TileMonsterSpawner.getLandHeight(this.world, new BlockPos(d0, d1 + this.world.rand.nextInt(spawnHeight), d2), spawnHeight * 2);
      EntityLiving entity = null;
      if (entityType == 0) {
         EntityLiving var20 = new NPCMobsPack.NpcBristling(this.world);
         var20.setPosition(d0, d1, d2);
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
            var20.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(var20)), (IEntityLivingData)null);
            var20.enablePersistence();
         }
      }
   }

   public void placeFoundation(World world, BlockPos pos, int size) {
      for (int x = pos.getX() - size; x <= pos.getX() + size; x++) {
         for (int z = pos.getZ() - size; z <= pos.getZ() + size; z++) {
            boolean generate = false;
            IBlockState material = world.getBlockState(new BlockPos(x, pos.getY(), z));
            if (material.getBlock() == BlocksRegister.CONIFERLOG
               || material.getBlock() == BlocksRegister.CONIFERPLANKS
               || material.getBlock() == BlocksRegister.FROZENBRICKS
               || material.getBlock() == BlocksRegister.FROZENCOBBLE
               || material.getBlock() == Blocks.PLANKS) {
               generate = true;
            }

            if (generate) {
               for (int y = pos.getY() - 1; y > 1; y--) {
                  BlockPos fpos = new BlockPos(x, y, z);
                  IBlockState getted = world.getBlockState(fpos);
                  Block block = getted.getBlock();
                  if (!block.isReplaceable(world, fpos) && !block.isLeaves(getted, world, fpos) && getted.getMaterial() != Material.WOOD) {
                     break;
                  }

                  world.setBlockState(fpos, material, 2);
               }
            }
         }
      }
   }
}
