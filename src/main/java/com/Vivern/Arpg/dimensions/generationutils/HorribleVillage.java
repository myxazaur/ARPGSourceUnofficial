package com.Vivern.Arpg.dimensions.generationutils;

import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.loot.ListLootTable;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.NamedInt;
import com.Vivern.Arpg.main.StructPos;
import com.Vivern.Arpg.mobs.AbstractMob;
import com.Vivern.Arpg.mobs.OtherMobsPack;
import com.Vivern.Arpg.tileentity.TileMonsterSpawner;
import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.Template.BlockInfo;

public class HorribleVillage {
   public static Predicate<IBlockState> BLOCKS_TO_COLLIDE = new Predicate<IBlockState>() {
      public boolean apply(IBlockState input) {
         return input.getBlock() == Blocks.PLANKS
            ? true
            : input.getMaterial() != Material.AIR
               && input.getMaterial() != Material.LEAVES
               && input.getMaterial() != Material.PLANTS
               && input.getMaterial() != Material.CACTUS
               && input.getMaterial() != Material.VINE
               && input.getMaterial() != Material.SNOW
               && input.getMaterial() != Material.WOOD;
      }
   };
   public static ITemplateProcessor replacerHorribleVillage = new ITemplateProcessor() {
      public BlockInfo processBlock(World world, BlockPos pos, BlockInfo blockInfoIn) {
         if (blockInfoIn.blockState.getBlock() == Blocks.CHEST) {
            IBlockState state = Blocks.CHEST.getStateFromMeta(Blocks.CHEST.getMetaFromState(blockInfoIn.blockState));
            world.setBlockState(pos, state);
            TileEntity tileentity = world.getTileEntity(pos);
            if (tileentity instanceof TileEntityChest) {
               ((TileEntityChest)tileentity).setLootTable(ListLootTable.CHESTS_HORRIBLE_VILLAGE, world.rand.nextLong());
            }

            return null;
         } else {
            return blockInfoIn;
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
      Blocks.DIRT.getStateFromMeta(0),
      Blocks.DIRT.getStateFromMeta(1),
      Blocks.DIRT.getStateFromMeta(2),
      Blocks.GRASS_PATH.getDefaultState(),
      Blocks.GRASS_PATH.getDefaultState(),
      Blocks.GRAVEL.getDefaultState()
   };
   public Random rand;
   public static NamedInt[] structuresHomes = new NamedInt[]{
      new NamedInt(":horrible_village_tower", 4),
      new NamedInt(":horrible_village_scaffold", 4),
      new NamedInt(":horrible_village_home_1", 7),
      new NamedInt(":horrible_village_home_2", 7),
      new NamedInt(":horrible_village_home_3", 7),
      new NamedInt(":horrible_village_horses", 4),
      new NamedInt(":horrible_village_silverfish", 4),
      new NamedInt(":horrible_village_shop", 5)
   };
   public static NamedInt[] structuresRoad = new NamedInt[]{
      new NamedInt(":horrible_village_lantern", 1),
      new NamedInt(":horrible_village_well", 2),
      new NamedInt(":horrible_village_hay", 2),
      new NamedInt(":horrible_village_haysmall", 1),
      new NamedInt(":horrible_village_bones", 2),
      new NamedInt(":horrible_village_coal", 2),
      new NamedInt(":horrible_village_cart", 3)
   };
   public int roadRadius = 2;
   public int noHomesTime;
   public int homesSpawned = 0;
   public BlockPos center;
   public int maxVillageRadius = 64;

   public HorribleVillage(World world, int maxRecursiveOutpathes, int allLengthBound, int maximumRoadLength, Random rand, int noHomesTime) {
      this.world = world;
      this.outpathesLast = maxRecursiveOutpathes;
      this.recursionLast = allLengthBound;
      this.maximumRoadLength = maximumRoadLength;
      this.rand = rand;
      this.noHomesTime = noHomesTime;
   }

   public void generate(BlockPos pos) {
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
                  if (this.rand.nextFloat() < 0.06F) {
                     this.rememberMobAt(result.pos.up(), GetMOP.byWeight(this.rand, 3, 4, 3, 5), 2, 2);
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
                        NamedInt structure = structuresRoad[this.rand.nextInt(structuresRoad.length)];
                        direction2 = direction2.scale(structure.value + 1 + this.roadRadius);
                        BlockPos offpos = pos.add(direction2.x, 0.0, direction2.z);
                        GetMOP.BlockTraceResult result2 = GetMOP.blockTrace(this.world, offpos, EnumFacing.DOWN, 32, BLOCKS_TO_COLLIDE);
                        if (result2 != null && this.isClear(result2.pos, structure.value)) {
                           int rotation = this.rand.nextInt(4);
                           GenerationHelper.placeStruct(
                              this.world, result2.pos, this.rand, structure.name, structure.value, -1, rotation, replacerHorribleVillage
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
                     this.world, finalpos, this.rand, structure.name, structure.value, -1, face.getHorizontalIndex(), replacerHorribleVillage
                  );
                  this.structuresSpawned.add(new StructPos(finalpos, face.getHorizontalIndex(), structure.value));
                  this.homesSpawned++;
                  this.placeFoundation(this.world, finalpos.down(), structure.value);
                  int difficultyAdd = this.world.getDifficulty() == EnumDifficulty.HARD ? 2 : 0;
                  if (structure.value >= 5) {
                     int mobsCount = this.rand.nextInt(4 + difficultyAdd) + 1;

                     for (int i = 0; i < mobsCount; i++) {
                        this.rememberMobAt(finalpos.up(), GetMOP.byWeight(this.rand, 3, 4, 2, 4), 3, 2);
                     }
                  } else if (structId < 2) {
                     int mobsCount = this.rand.nextInt(3 + difficultyAdd) + 1;

                     for (int i = 0; i < mobsCount; i++) {
                        this.rememberMobAt(finalpos.up(structId == 0 ? 7 : 5), GetMOP.byWeight(this.rand, 2, 1, 6, 1), 2, 2);
                     }
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
               if (hasState.getBlock() != Blocks.PLANKS) {
                  if (hasState.getMaterial().isLiquid()) {
                     this.world
                        .setBlockState(poss, this.rand.nextFloat() < 0.15F ? Blocks.DIRT.getStateFromMeta(2) : Blocks.PLANKS.getStateFromMeta(1), 2);
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
         entity = new OtherMobsPack.HorribleEvoker(this.world);
         entity.setPosition(d0, d1, d2);
      } else if (entityType == 1) {
         entity = new OtherMobsPack.HorribleVindicator(this.world);
         entity.setPosition(d0, d1, d2);
      } else if (entityType == 2) {
         entity = new OtherMobsPack.Equestrian(this.world);
         entity.setPosition(d0, d1, d2);
      } else {
         if (entityType != 3) {
            return;
         }

         entity = new EntityVindicator(this.world);
         entity.setPosition(d0, d1, d2);
         if (this.rand.nextFloat() < 0.76F) {
            entity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
         } else {
            entity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
         }
      }

      if (entity != null) {
         float yaw = this.world.rand.nextFloat() * 360.0F;
         entity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, yaw, 0.0F);

         for (int i = 0; i < 16 && this.world.collidesWithAnyBlock(entity.getEntityBoundingBox()); i++) {
            d0 = pos.getX() + (this.world.rand.nextDouble() - this.world.rand.nextDouble()) * spawnRange + 0.5;
            d1 = pos.getY();
            d2 = pos.getZ() + (this.world.rand.nextDouble() - this.world.rand.nextDouble()) * spawnRange + 0.5;
            d1 = TileMonsterSpawner.getLandHeight(this.world, new BlockPos(d0, d1 + this.world.rand.nextInt(spawnHeight), d2), spawnHeight * 2);
            entity.setLocationAndAngles(d0, d1, d2, yaw, 0.0F);
         }

         if (!this.world.collidesWithAnyBlock(entity.getEntityBoundingBox())) {
            AnvilChunkLoader.spawnEntity(entity, this.world);
            if (entity instanceof AbstractMob) {
               AbstractMob mob = (AbstractMob)entity;
               mob.onInitialSpawn();
               mob.canDropLoot = true;
            }

            entity.enablePersistence();
         }
      }
   }

   public void placeFoundation(World world, BlockPos pos, int size) {
      for (int x = pos.getX() - size; x <= pos.getX() + size; x++) {
         for (int z = pos.getZ() - size; z <= pos.getZ() + size; z++) {
            boolean generate = false;
            IBlockState material = world.getBlockState(new BlockPos(x, pos.getY(), z));
            if (material.getBlock() == Blocks.COBBLESTONE) {
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
