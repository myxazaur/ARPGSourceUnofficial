package com.vivern.arpg.tileentity;

import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.MobReactor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class SpawnerTuner {
   public ArrayList<ClassAndWeight> spawns = new ArrayList<>();
   public int maximumMobTypes;
   public int maxImpossiblePower;
   public int activeRange = 16;
   public float tunerDifficulty = 1.0F;
   public float mobsCountPerSpawnMin = 1.5F;
   public float mobsCountPerSpawnMax = 3.5F;
   public int nearCheckDistance = 20;
   public int initialMaxMobsNearby = 10;

   public SpawnerTuner addCreature(Class<? extends Entity> entity, int weight, int mobPower, MobReactor.GroupSpawnMode groupSpawnMode) {
      this.spawns.add(new ClassAndWeight(entity, weight, mobPower, groupSpawnMode));
      this.maxImpossiblePower += mobPower;
      return this;
   }

   public SpawnerTuner setInitialMaxMobsNearby(int initialMaxMobsNearby) {
      this.initialMaxMobsNearby = initialMaxMobsNearby;
      return this;
   }

   public SpawnerTuner setMaximumMobTypes(int max) {
      this.maximumMobTypes = max;
      return this;
   }

   public SpawnerTuner setActiveRange(int activeRange) {
      this.activeRange = activeRange;
      return this;
   }

   public SpawnerTuner setNearCheckDistance(int nearCheckDistance) {
      this.nearCheckDistance = nearCheckDistance;
      return this;
   }

   public SpawnerTuner setMobsCountPerSpawn(float averageMobsCount) {
      this.mobsCountPerSpawnMin = averageMobsCount - 1.0F;
      this.mobsCountPerSpawnMax = averageMobsCount + 1.0F;
      return this;
   }

   public SpawnerTuner setMobsCountPerSpawn(float mobsCountPerSpawnMin, float mobsCountPerSpawnMax) {
      this.mobsCountPerSpawnMin = mobsCountPerSpawnMin;
      this.mobsCountPerSpawnMax = mobsCountPerSpawnMax;
      return this;
   }

   public void setupSpawner(World world, TileMonsterSpawner spawner, Random rand) {
      Collections.shuffle(this.spawns);
      int types = 0;
      int currentPower = 0;

      for (ClassAndWeight classAndWeight : this.spawns) {
         spawner.addMobToSpawn(classAndWeight.entity, classAndWeight.weight, classAndWeight.spawnMode == MobReactor.GroupSpawnMode.LAND);
         types++;
         currentPower += classAndWeight.mobPower;
         if (types >= this.maximumMobTypes) {
            break;
         }
      }

      float maxPossiblePower = (float)this.maximumMobTypes / this.spawns.size() * this.maxImpossiblePower;
      float currentPowerPercent = currentPower / maxPossiblePower;
      currentPowerPercent = MathHelper.clamp(currentPowerPercent, 0.2F, 4.0F);
      currentPowerPercent /= this.tunerDifficulty;
      int difficulty = world.getDifficulty().getId();
      int maxMobsNearby = this.initialMaxMobsNearby + difficulty * 3;
      int countMIN = Math.max(GetMOP.floatToIntWithChance(this.mobsCountPerSpawnMin / currentPowerPercent, rand), 1);
      int countMAX = Math.max(GetMOP.floatToIntWithChance(this.mobsCountPerSpawnMax / currentPowerPercent, rand), 1);
      if (countMIN > countMAX) {
         countMIN = countMAX;
      }

      float spawnerDelay = (1.0F + rand.nextFloat() * 0.5F) * (300.0F + 70.0F * currentPowerPercent - difficulty * 20) / this.tunerDifficulty;
      spawner.initSpawner(
         (int)spawnerDelay, 1.8F, (0.25F + rand.nextFloat() * 0.1F) * currentPowerPercent, countMIN, countMAX, 4.0F, 3.0F, this.activeRange, maxMobsNearby
      );
      spawner.nearCheckDistance = this.nearCheckDistance;
   }

   public void setupSpawner(World world, BlockPos pos, Random rand) {
      if (world != null) {
         TileEntity tile = world.getTileEntity(pos);
         if (tile != null && tile instanceof TileMonsterSpawner) {
            TileMonsterSpawner spawner = (TileMonsterSpawner)tile;
            this.setupSpawner(world, spawner, rand);
         }
      }
   }

   public class ClassAndWeight {
      public Class<? extends Entity> entity;
      public int weight;
      public int mobPower;
      public MobReactor.GroupSpawnMode spawnMode;

      public ClassAndWeight(Class<? extends Entity> entity, int weight, int mobPower, MobReactor.GroupSpawnMode spawnMode) {
         this.entity = entity;
         this.weight = weight;
         this.mobPower = mobPower;
         this.spawnMode = spawnMode;
      }
   }
}
