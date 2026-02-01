package com.vivern.arpg.dimensions.generationutils;

import com.vivern.arpg.main.GetMOP;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.EntityEntry;

public class WorldGenNPC implements IWorldGenerator {
   public boolean ishealth;
   public EntityEntry NPCentry;
   public String structureName;
   public int chance;
   public int dimension;
   public boolean underground;

   public WorldGenNPC(EntityEntry NPCentry, String structureName, int chance, int dimension, boolean underground) {
      this.NPCentry = NPCentry;
      this.structureName = structureName;
      this.chance = chance;
      this.dimension = dimension;
      this.underground = underground;
   }

   public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
      int dim = world.provider.getDimension();
      if (dim == this.dimension) {
         if (this.underground) {
            if (random.nextInt(this.chance) == 0) {
               int x = chunkX * 16 + random.nextInt(16);
               int z = chunkZ * 16 + random.nextInt(16);
               GetMOP.BlockTraceResult topresult = GetMOP.blockTrace(world, new BlockPos(x, 255, z), EnumFacing.DOWN, 254, GetMOP.SOLID_NON_PLANTS_BLOCKS);
               if (topresult != null) {
                  BlockPos top = topresult.pos;
                  int start = top.getY() - 6;
                  int end = 11;
                  BlockPos startpos = new BlockPos(x, end, z);
                  if (GetMOP.SOLID_BLOCKS.apply(world.getBlockState(startpos))) {
                     GetMOP.BlockTraceResult result = GetMOP.blockTrace(world, startpos, EnumFacing.UP, start - end, GetMOP.AIR_BLOCKS);
                     if (result != null && !world.getBlockState(result.pos.down()).getMaterial().isLiquid()) {
                        GenerationHelper.placeStruct(world, result.pos, random, this.structureName, 5, -1, random.nextInt(4), null);
                        Entity entity = this.NPCentry.newInstance(world);
                        entity.setPosition(result.pos.getX() + 0.5, result.pos.getY(), result.pos.getZ() + 0.5);
                        world.spawnEntity(entity);
                        if (entity instanceof EntityLiving) {
                           ((EntityLiving)entity).onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity)), (IEntityLivingData)null);
                           ((EntityLiving)entity).enablePersistence();
                        }
                     }
                  }
               }
            }
         } else {
            if (this.dimension == 0
               && GenerationHelper.canGenerateDungeonAtChunkPos(chunkX, chunkZ, world.getSeed(), WorldGenHorribleVillage.probablity, 8)) {
               return;
            }

            if (random.nextInt(this.chance) == 0) {
               int x = chunkX * 16 + random.nextInt(16);
               int z = chunkZ * 16 + random.nextInt(16);
               GetMOP.BlockTraceResult result = GetMOP.blockTrace(world, new BlockPos(x, 255, z), EnumFacing.DOWN, 254, GetMOP.SOLID_NON_PLANTS_BLOCKS);
               if (result != null && !world.getBlockState(result.prevPos).getMaterial().isLiquid()) {
                  GenerationHelper.placeStruct(world, result.pos, random, this.structureName, 5, 0, random.nextInt(4), null);
                  Entity entity = this.NPCentry.newInstance(world);
                  entity.setPosition(result.pos.getX() + 0.5, result.pos.getY() + 1, result.pos.getZ() + 0.5);
                  world.spawnEntity(entity);
                  if (entity instanceof EntityLiving) {
                     ((EntityLiving)entity).onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity)), (IEntityLivingData)null);
                     ((EntityLiving)entity).enablePersistence();
                  }
               }
            }
         }
      }
   }
}
