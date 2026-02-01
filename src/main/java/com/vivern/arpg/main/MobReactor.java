package com.vivern.arpg.main;

import com.vivern.arpg.arpgamemodes.SurvivorGamestyleWatcher;
import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import com.vivern.arpg.weather.WorldEvent;
import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class MobReactor {
   public EntityEntry entry;
   public Class<? extends EntityLiving> clazz;
   public int pointsCost;
   public int chance;
   public int mobsPerSpawnMin;
   public int mobsPerSpawnAdd;
   public GroupSpawnMode mode;
   public int emptyZone;
   public int xzRange;
   public int yRange;
   public boolean canBeOverHead;
   public int chanceMinimum;
   public int chanceMaximum;
   public int minWorldHeight = 0;
   public int maxWorldHeight = 256;
   public boolean heightIncludesPlayer = false;
   @Nullable
   public Predicate<Biome> biomeLogic;
   public boolean biomeIncludesPlayer = false;
   public GroupSpawnLight darkLightLogic = GroupSpawnLight.ANY;
   public int worldEventAssign = 0;
   public WorldEventsSpawnLogic worldEventsSpawnLogic = WorldEventsSpawnLogic.NOT_AFFECT;
   public int survivorGamestyleLevel;
   public boolean enderLogic = false;

   public MobReactor(
      int pointsCost,
      int chance,
      Class<? extends EntityLiving> clazz,
      int mobsPerSpawnMin,
      int mobsPerSpawnMax,
      GroupSpawnMode mode,
      int emptyZone,
      int xzRange,
      int yRange,
      boolean canBeOverHead
   ) {
      EntityEntry entr = EntityRegistry.getEntry(clazz);
      if (entr != null) {
         this.entry = entr;
      } else {
         this.clazz = clazz;
      }

      this.pointsCost = pointsCost;
      this.chance = chance;
      this.mobsPerSpawnMin = mobsPerSpawnMin;
      this.mobsPerSpawnAdd = mobsPerSpawnMax - mobsPerSpawnMin + 1;
      this.mode = mode;
      this.emptyZone = emptyZone;
      this.xzRange = xzRange;
      this.yRange = yRange;
      this.canBeOverHead = canBeOverHead;
   }

   public MobReactor(
      int pointsCost,
      int chance,
      EntityEntry entry,
      int mobsPerSpawnMin,
      int mobsPerSpawnMax,
      GroupSpawnMode mode,
      int emptyZone,
      int xzRange,
      int yRange,
      boolean canBeOverHead
   ) {
      if (entry == null) {
         throw new NullPointerException("null MobReactor entry");
      } else {
         this.entry = entry;
         this.pointsCost = pointsCost;
         this.chance = chance;
         this.mobsPerSpawnMin = mobsPerSpawnMin;
         this.mobsPerSpawnAdd = mobsPerSpawnMax - mobsPerSpawnMin + 1;
         this.mode = mode;
         this.emptyZone = emptyZone;
         this.xzRange = xzRange;
         this.yRange = yRange;
         this.canBeOverHead = canBeOverHead;
      }
   }

   public boolean involved(EntityPlayer playerSpawnOn, @Nullable AbstractWorldProvider abstractWorldProvider) {
      if (!this.heightIncludesPlayer || !(playerSpawnOn.posY < this.minWorldHeight) && !(playerSpawnOn.posY > this.maxWorldHeight)) {
         if (this.biomeIncludesPlayer
            && this.biomeLogic != null
            && !this.biomeLogic.apply(playerSpawnOn.world.getBiome(playerSpawnOn.getPosition()))) {
            return false;
         } else if (this.survivorGamestyleLevel > 0
            && SurvivorGamestyleWatcher.currentWatcher != null
            && SurvivorGamestyleWatcher.currentWatcher.LEVEL < this.survivorGamestyleLevel) {
            return false;
         } else {
            if (this.worldEventsSpawnLogic != WorldEventsSpawnLogic.NOT_AFFECT) {
               if (this.worldEventsSpawnLogic == WorldEventsSpawnLogic.ONLY_IN_ASSIGNED
                  && (abstractWorldProvider == null || !abstractWorldProvider.isWorldEventStarted(this.worldEventAssign))) {
                  return false;
               }

               if (this.worldEventsSpawnLogic == WorldEventsSpawnLogic.IN_ALL_EVENTS_EXCEPT_ASSIGNED) {
                  if (abstractWorldProvider == null || abstractWorldProvider.getWorldEventsHandler() == null) {
                     return false;
                  }

                  boolean somestarted = false;

                  for (WorldEvent worldEvent : abstractWorldProvider.getWorldEventsHandler().events) {
                     if (worldEvent.isStarted) {
                        if (worldEvent.index == this.worldEventAssign) {
                           return false;
                        }

                        somestarted = true;
                     }
                  }

                  if (!somestarted) {
                     return false;
                  }
               }

               if (this.worldEventsSpawnLogic == WorldEventsSpawnLogic.ANYTIME_EXCEPT_ASSIGNED
                  && abstractWorldProvider != null
                  && abstractWorldProvider.isWorldEventStarted(this.worldEventAssign)) {
                  return false;
               }

               if (this.worldEventsSpawnLogic == WorldEventsSpawnLogic.WHEN_NO_EVENTS
                  && abstractWorldProvider != null
                  && abstractWorldProvider.getWorldEventsHandler() != null) {
                  for (WorldEvent worldEventx : abstractWorldProvider.getWorldEventsHandler().events) {
                     if (worldEventx.isStarted) {
                        return false;
                     }
                  }
               }
            }

            return !this.enderLogic
               || !(playerSpawnOn.posX * playerSpawnOn.posX + playerSpawnOn.posZ * playerSpawnOn.posZ < 1000000.0);
         }
      } else {
         return false;
      }
   }

   public MobReactor setHeightLogic(int minWorldHeight, int maxWorldHeight, boolean includesPlayer) {
      this.minWorldHeight = minWorldHeight;
      this.maxWorldHeight = maxWorldHeight;
      return this;
   }

   public MobReactor setBiomeLogic(Predicate<Biome> biomeLogic) {
      this.biomeLogic = biomeLogic;
      return this;
   }

   public MobReactor setLightLogic(GroupSpawnLight darkLightLogic) {
      this.darkLightLogic = darkLightLogic;
      return this;
   }

   public MobReactor setWorldEventsLogic(WorldEventsSpawnLogic worldEventsSpawnLogic, int worldEventIndex) {
      this.worldEventsSpawnLogic = worldEventsSpawnLogic;
      this.worldEventAssign = worldEventIndex;
      return this;
   }

   public MobReactor setSurvivorLogic(int level) {
      this.survivorGamestyleLevel = level;
      return this;
   }

   public MobReactor setEnderLogic() {
      this.enderLogic = true;
      return this;
   }

   public int doMobGroup(World world, MobSpawn spawn, EntityPlayer player, int difficulty) {
      List<EntityLiving> list = new ArrayList<>();
      int mobscount = spawn.buyMobsAndGetCount(player, this.pointsCost, this.mobsPerSpawnMin + spawn.rand.nextInt(this.mobsPerSpawnAdd));
      if (mobscount > 0) {
         if (this.entry != null) {
            for (int i = 0; i < mobscount; i++) {
               EntityLiving newliving = (EntityLiving)this.entry.newInstance(world);
               if (newliving != null) {
                  list.add(newliving);
               }
            }
         } else {
            for (int ix = 0; ix < mobscount; ix++) {
               EntityLiving newliving = (EntityLiving)EntityList.newEntity(this.clazz, world);
               if (newliving != null) {
                  list.add(newliving);
               }
            }
         }

         if (list.isEmpty()) {
            return 0;
         }

         if (this.mode == GroupSpawnMode.LAND) {
            for (int w = 0; w < 10; w++) {
               BlockPos pos = spawn.createRandomPosNearPlayer(player, this.emptyZone, this.xzRange, this.yRange, this.canBeOverHead);
               if (pos.getY() >= this.minWorldHeight
                  && pos.getY() <= this.maxWorldHeight
                  && this.checkLight(pos, world)
                  && (this.biomeLogic == null || this.biomeLogic.apply(world.getBiome(pos)))
                  && spawn.landMobGroup(world, pos, list)) {
                  int mobsspawned = spawn.lastSpawnedMobs;
                  Mana.addSwarmPoints(player, -spawn.lastSpawnedMobs * this.pointsCost);
                  spawn.lastSpawnedMobs = 0;
                  return mobsspawned;
               }
            }
         } else if (this.mode == GroupSpawnMode.AIRBORN) {
            for (int wx = 0; wx < 10; wx++) {
               BlockPos pos = spawn.createRandomPosNearPlayer(player, this.emptyZone, this.xzRange, this.yRange, this.canBeOverHead);
               if (pos.getY() >= this.minWorldHeight
                  && pos.getY() <= this.maxWorldHeight
                  && this.checkLight(pos, world)
                  && (this.biomeLogic == null || this.biomeLogic.apply(world.getBiome(pos)))
                  && spawn.airborneMobGroup(world, pos, list)) {
                  int mobsspawned = spawn.lastSpawnedMobs;
                  Mana.addSwarmPoints(player, -spawn.lastSpawnedMobs * this.pointsCost);
                  spawn.lastSpawnedMobs = 0;
                  return mobsspawned;
               }
            }
         } else if (this.mode == GroupSpawnMode.UNDERGROUND) {
            for (int wxx = 0; wxx < 10; wxx++) {
               BlockPos pos = spawn.createRandomPosNearPlayer(player, this.emptyZone, this.xzRange, this.yRange, this.canBeOverHead);
               if (pos.getY() >= this.minWorldHeight
                  && pos.getY() <= this.maxWorldHeight
                  && this.checkLight(pos, world)
                  && (this.biomeLogic == null || this.biomeLogic.apply(world.getBiome(pos)))
                  && spawn.undergroundMobGroup(world, pos, list)) {
                  int mobsspawned = spawn.lastSpawnedMobs;
                  Mana.addSwarmPoints(player, -spawn.lastSpawnedMobs * this.pointsCost);
                  spawn.lastSpawnedMobs = 0;
                  return mobsspawned;
               }
            }
         } else if (this.mode == GroupSpawnMode.WATER) {
            for (int wxxx = 0; wxxx < 10; wxxx++) {
               BlockPos pos = spawn.createRandomPosNearPlayer(player, this.emptyZone, this.xzRange, this.yRange, this.canBeOverHead);
               if (pos.getY() >= this.minWorldHeight
                  && pos.getY() <= this.maxWorldHeight
                  && this.checkLight(pos, world)
                  && (this.biomeLogic == null || this.biomeLogic.apply(world.getBiome(pos)))
                  && spawn.waterMobGroup(world, pos, list)) {
                  int mobsspawned = spawn.lastSpawnedMobs;
                  Mana.addSwarmPoints(player, -spawn.lastSpawnedMobs * this.pointsCost);
                  spawn.lastSpawnedMobs = 0;
                  return mobsspawned;
               }
            }
         }
      }

      return 0;
   }

   public boolean checkLight(BlockPos pos, World world) {
      if (this.darkLightLogic == GroupSpawnLight.ANY) {
         return true;
      } else {
         boolean isLight = world.getLightFromNeighbors(pos.up()) >= 2;
         return this.darkLightLogic == GroupSpawnLight.LIGHT ? isLight : !isLight;
      }
   }

   public static enum GroupSpawnLight {
      LIGHT,
      DARK,
      ANY;
   }

   public static enum GroupSpawnMode {
      LAND,
      AIRBORN,
      WATER,
      UNDERGROUND;
   }

   public static enum WorldEventsSpawnLogic {
      NOT_AFFECT,
      ONLY_IN_ASSIGNED,
      IN_ALL_EVENTS_EXCEPT_ASSIGNED,
      ANYTIME_EXCEPT_ASSIGNED,
      WHEN_NO_EVENTS;
   }
}
