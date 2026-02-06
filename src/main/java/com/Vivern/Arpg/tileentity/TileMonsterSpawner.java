package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.mobs.AbstractMob;
import java.util.Arrays;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;

public class TileMonsterSpawner extends TileEntity implements ITickable {
   public static Random rand = new Random();
   public NBTTagCompound[] entityDatas;
   public int[] entityChances;
   public boolean[] landOnGround;
   public int spawnDelay = 20;
   public int maximalDelay = 100;
   public float maxDelayMultiplier = 1.5F;
   public float delayMultiplier = 1.0F;
   public float delayKillAdd = 0.1F;
   public int spawnCountMin = 4;
   public int spawnCountMax = 8;
   public float spawnRange = 4.0F;
   public float spawnHeight = 3.0F;
   public float activeRange = 26.0F;
   public boolean isActive = false;
   public int maxRandomIntToChance = 0;
   public int maxMobsNearby = 16;
   public int nearCheckDistance = 48;

   public TileMonsterSpawner initSpawner(
      int baseMaxDelay,
      float maxDelayMultiplier,
      float delayKillAdd,
      int spawnCountMin,
      int spawnCountMax,
      float spawnRange,
      float spawnHeight,
      float activeRange,
      int maxMobsNearby
   ) {
      this.maximalDelay = baseMaxDelay;
      this.maxDelayMultiplier = maxDelayMultiplier;
      this.delayKillAdd = delayKillAdd;
      this.spawnCountMin = spawnCountMin;
      this.spawnCountMax = spawnCountMax;
      this.spawnRange = spawnRange;
      this.spawnHeight = spawnHeight;
      this.activeRange = activeRange;
      this.maxMobsNearby = maxMobsNearby;
      return this;
   }

   public TileMonsterSpawner addMobToSpawn(Class<? extends Entity> entity, int weight, boolean shouldLandOnGround) {
      NBTTagCompound entityTag = new NBTTagCompound();
      String entityid = EntityList.getKey(entity).toString();
      entityTag.setString("id", entityid);
      if (this.entityDatas == null) {
         this.entityDatas = new NBTTagCompound[1];
         this.entityChances = new int[1];
         this.landOnGround = new boolean[1];
      } else {
         this.entityDatas = Arrays.copyOf(this.entityDatas, this.entityDatas.length + 1);
         this.entityChances = Arrays.copyOf(this.entityChances, this.entityChances.length + 1);
         this.landOnGround = Arrays.copyOf(this.landOnGround, this.landOnGround.length + 1);
      }

      this.maxRandomIntToChance += weight;
      int index = this.entityDatas.length - 1;
      this.entityDatas[index] = entityTag;
      this.entityChances[index] = this.maxRandomIntToChance;
      this.landOnGround[index] = shouldLandOnGround;
      return this;
   }

   public void update() {
      this.spawnDelay--;
      if (this.spawnDelay <= 0 && this.isActive) {
         int count = this.spawnCountMin + rand.nextInt(this.spawnCountMax - this.spawnCountMin + 1);
         int mobsSpawned = 0;

         for (Entity entity : this.world.loadedEntityList) {
            if (entity.getDistanceSq(this.pos) < this.nearCheckDistance * this.nearCheckDistance
               && entity instanceof AbstractMob
               && ((AbstractMob)entity).spawner != null) {
               mobsSpawned++;
            }
         }

         if (mobsSpawned + count <= this.maxMobsNearby) {
            this.spawn(count);
            this.spawnDelay = this.maximalDelay;
            this.maximalDelay = (int)(this.maximalDelay * this.delayMultiplier);
            this.delayMultiplier = 1.0F;
         } else {
            this.spawnDelay = 26;
         }
      }

      if (this.spawnDelay % 6 == 0) {
         this.isActive = this.world
            .isAnyPlayerWithinRangeAt(
               this.pos.getX() + 0.5,
               this.pos.getY() + 0.5,
               this.pos.getZ() + 0.5,
               this.activeRange
            );
      }
   }

   public void spawn(int count) {
      if (!this.world.isRemote && this.entityDatas != null && this.landOnGround != null && this.entityChances != null) {
         if (this.maxRandomIntToChance == 0) {
            for (int i = 0; i < count; i++) {
               this.spawnMob(0);
            }
         } else {
            for (int i = 0; i < count; i++) {
               int randMob = rand.nextInt(this.maxRandomIntToChance + 1);
               int index = 0;

               for (int w = 0; w < this.entityChances.length; w++) {
                  int weight = this.entityChances[w];
                  if (randMob <= weight) {
                     index = w;
                     break;
                  }
               }

               this.spawnMob(index);
            }
         }
      }
   }

   public void spawnMob(int indxInArrays) {
      NBTTagCompound nbttagcompound = this.entityDatas[indxInArrays];
      if (nbttagcompound != null) {
         boolean landGround = this.landOnGround[indxInArrays];
         double d0;
         double d1;
         double d2;
         if (nbttagcompound.hasKey("Pos", 6)) {
            NBTTagList nbttaglist = nbttagcompound.getTagList("Pos", 6);
            int j = nbttaglist.tagCount();
            if (j >= 1) {
               d0 = nbttaglist.getDoubleAt(0);
            } else {
               d0 = this.pos.getX()
                  + (this.world.rand.nextDouble() - this.world.rand.nextDouble()) * this.spawnRange
                  + 0.5;
            }

            if (j >= 2) {
               d1 = nbttaglist.getDoubleAt(1);
            } else {
               d1 = this.pos.getY();
            }

            if (j >= 3) {
               d2 = nbttaglist.getDoubleAt(2);
            } else {
               d2 = this.pos.getZ()
                  + (this.world.rand.nextDouble() - this.world.rand.nextDouble()) * this.spawnRange
                  + 0.5;
            }
         } else {
            d0 = this.pos.getX()
               + (this.world.rand.nextDouble() - this.world.rand.nextDouble()) * this.spawnRange
               + 0.5;
            d1 = this.pos.getY();
            d2 = this.pos.getZ()
               + (this.world.rand.nextDouble() - this.world.rand.nextDouble()) * this.spawnRange
               + 0.5;
         }

         if (landGround) {
            d1 = getLandHeight(
               this.world, new BlockPos(d0, d1 + this.world.rand.nextInt((int)this.spawnHeight), d2), (int)this.spawnHeight * 2
            );
         } else {
            d1 += this.world.rand.nextInt((int)this.spawnHeight) - this.world.rand.nextInt((int)this.spawnHeight) + 0.5;
         }

         ResourceLocation resourcelocation = new ResourceLocation(nbttagcompound.getString("id"));
         Entity entity = EntityList.createEntityByIDFromName(resourcelocation, this.world);
         if (entity != null) {
            float yaw = this.world.rand.nextFloat() * 360.0F;
            entity.setLocationAndAngles(d0, d1, d2, yaw, entity.rotationPitch);
            if (!nbttagcompound.hasKey("Pos", 6)) {
               for (int i = 0; i < 16 && this.world.collidesWithAnyBlock(entity.getEntityBoundingBox()); i++) {
                  d0 = this.pos.getX()
                     + (this.world.rand.nextDouble() - this.world.rand.nextDouble()) * this.spawnRange
                     + 0.5;
                  d1 = this.pos.getY();
                  d2 = this.pos.getZ()
                     + (this.world.rand.nextDouble() - this.world.rand.nextDouble()) * this.spawnRange
                     + 0.5;
                  if (landGround) {
                     d1 = getLandHeight(
                        this.world,
                        new BlockPos(d0, d1 + this.world.rand.nextInt((int)this.spawnHeight), d2),
                        (int)this.spawnHeight * 2
                     );
                  } else {
                     d1 += this.world.rand.nextInt((int)this.spawnHeight) - this.world.rand.nextInt((int)this.spawnHeight)
                        + 0.5;
                  }

                  entity.setLocationAndAngles(d0, d1, d2, yaw, 0.0F);
               }
            }

            AnvilChunkLoader.spawnEntity(entity, this.world);
            if (entity instanceof AbstractMob) {
               AbstractMob mob = (AbstractMob)entity;
               mob.onInitialSpawn();
               mob.spawner = this;
               mob.canDropLoot = true;
            }
         }
      }
   }

   public void onMobKilled(AbstractMob mob, DamageSource cause) {
      if (this.isInvalid()) {
         mob.spawner = null;
      }

      this.delayMultiplier = Math.min(this.delayMultiplier + this.delayKillAdd, this.maxDelayMultiplier);
   }

   public static int getLandHeight(World world, BlockPos pos, int range) {
      Chunk chunk = world.getChunk(pos);

      for (int y = pos.getY(); y > pos.getY() - range; y--) {
         IBlockState state = chunk.getBlockState(pos.getX(), y, pos.getZ());
         if (state.isTopSolid() && !chunk.getBlockState(pos.getX(), y + 1, pos.getZ()).getMaterial().blocksMovement()) {
            return y + 1;
         }
      }

      for (int yx = pos.getY(); yx < pos.getY() + range; yx++) {
         if (chunk.getBlockState(pos.getX(), yx, pos.getZ()).isTopSolid()
            && !chunk.getBlockState(pos.getX(), yx + 1, pos.getZ()).getMaterial().blocksMovement()) {
            return yx + 1;
         }
      }

      return pos.getY();
   }

   public void readFromNBT(NBTTagCompound compound) {
      super.readFromNBT(compound);
      if (compound.hasKey("spawnDelay")) {
         this.spawnDelay = compound.getInteger("spawnDelay");
      }

      if (compound.hasKey("maximalDelay")) {
         this.maximalDelay = compound.getInteger("maximalDelay");
      }

      if (compound.hasKey("maxDelayMultiplier")) {
         this.maxDelayMultiplier = compound.getFloat("maxDelayMultiplier");
      }

      if (compound.hasKey("delayMultiplier")) {
         this.delayMultiplier = compound.getFloat("delayMultiplier");
      }

      if (compound.hasKey("delayKillAdd")) {
         this.delayKillAdd = compound.getFloat("delayKillAdd");
      }

      if (compound.hasKey("spawnCountMin")) {
         this.spawnCountMin = compound.getInteger("spawnCountMin");
      }

      if (compound.hasKey("spawnCountMax")) {
         this.spawnCountMax = compound.getInteger("spawnCountMax");
      }

      if (compound.hasKey("spawnRange")) {
         this.spawnRange = compound.getFloat("spawnRange");
      }

      if (compound.hasKey("spawnHeight")) {
         this.spawnHeight = compound.getFloat("spawnHeight");
      }

      if (compound.hasKey("activeRange")) {
         this.activeRange = compound.getFloat("activeRange");
      }

      if (compound.hasKey("maxRandomIntToChance")) {
         this.maxRandomIntToChance = compound.getInteger("maxRandomIntToChance");
      }

      if (compound.hasKey("maxMobsNearby")) {
         this.maxMobsNearby = compound.getInteger("maxMobsNearby");
      }

      if (compound.hasKey("nearCheckDistance")) {
         this.nearCheckDistance = compound.getInteger("nearCheckDistance");
      }

      if (compound.hasKey("entityDatas")) {
         NBTTagCompound tags = compound.getCompoundTag("entityDatas");
         int arraySize = 0;
         if (tags.hasKey("entryCount")) {
            arraySize = tags.getInteger("entryCount");
            this.entityDatas = new NBTTagCompound[arraySize];
            this.entityChances = new int[arraySize];
            this.landOnGround = new boolean[arraySize];
         }

         for (int i = 0; tags.hasKey("entry" + i) && i < arraySize; i++) {
            NBTTagCompound tag = tags.getCompoundTag("entry" + i);
            if (tag.hasKey("data") && tag.hasKey("addedChance")) {
               this.entityDatas[i] = tag.getCompoundTag("data");
               this.entityChances[i] = tag.getInteger("addedChance");
               if (tag.hasKey("landOnGround")) {
                  this.landOnGround[i] = tag.getBoolean("landOnGround");
               } else {
                  this.landOnGround[i] = true;
               }
            }
         }
      }
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      super.writeToNBT(compound);
      compound.setInteger("spawnDelay", this.spawnDelay);
      compound.setInteger("maximalDelay", this.maximalDelay);
      compound.setFloat("maxDelayMultiplier", this.maxDelayMultiplier);
      compound.setFloat("delayMultiplier", this.delayMultiplier);
      compound.setFloat("delayKillAdd", this.delayKillAdd);
      compound.setInteger("spawnCountMin", this.spawnCountMin);
      compound.setInteger("spawnCountMax", this.spawnCountMax);
      compound.setFloat("spawnRange", this.spawnRange);
      compound.setFloat("spawnHeight", this.spawnHeight);
      compound.setFloat("activeRange", this.activeRange);
      compound.setInteger("maxRandomIntToChance", this.maxRandomIntToChance);
      compound.setInteger("maxMobsNearby", this.maxMobsNearby);
      compound.setInteger("nearCheckDistance", this.nearCheckDistance);
      NBTTagCompound tags = new NBTTagCompound();
      if (this.entityDatas != null && this.entityChances != null && this.landOnGround != null) {
         int imax = Math.min(Math.min(this.entityDatas.length, this.entityChances.length), this.landOnGround.length);

         for (int i = 0; i < imax; i++) {
            NBTTagCompound entitydata = this.entityDatas[i];
            int chance = this.entityChances[i];
            boolean land = this.landOnGround[i];
            NBTTagCompound tag = new NBTTagCompound();
            if (entitydata != null) {
               tag.setTag("data", entitydata);
            }

            tag.setInteger("addedChance", chance);
            tag.setBoolean("landOnGround", land);
            tags.setTag("entry" + i, tag);
         }

         tags.setInteger("entryCount", imax);
      }

      compound.setTag("entityDatas", tags);
      return compound;
   }
}
