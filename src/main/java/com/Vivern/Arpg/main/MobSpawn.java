//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.main;

import com.Vivern.Arpg.arpgamemodes.SurvivorGamestyleWatcher;
import com.Vivern.Arpg.dimensions.generationutils.AbstractWorldProvider;
import com.Vivern.Arpg.mobs.AbstractMob;
import com.Vivern.Arpg.mobs.MobSpawnAquatica;
import com.Vivern.Arpg.mobs.MobSpawnDungeon;
import com.Vivern.Arpg.mobs.MobSpawnEnder;
import com.Vivern.Arpg.mobs.MobSpawnEverfrost;
import com.Vivern.Arpg.mobs.MobSpawnNether;
import com.Vivern.Arpg.mobs.MobSpawnOverworld;
import com.Vivern.Arpg.mobs.MobSpawnStormledge;
import com.Vivern.Arpg.mobs.MobSpawnToxicomania;
import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@EventBusSubscriber(
   modid = "arpg"
)
public abstract class MobSpawn {
   public static HashMap<Integer, MobSpawn> spawnByDimension = new HashMap<>();
   public static int defaultmaxMobs = 80;
   public static int maxMobsDifficMult = 20;
   public static int frequency = 100;
   public Random rand = new Random();
   public int dimension;
   public int maxLandingYdecrease = 24;
   private int swarmFrequency;
   public boolean enableSwarms = false;
   public List<MobReactor> reactors = new ArrayList<>();
   public int chancesSum = 0;
   @Nullable
   public MobReactor miniBoss;
   public int lastSpawnedMobs = 0;
   public int lowestCost = Integer.MAX_VALUE;
   public int highestCost = 0;
   public boolean inSurvivorMode = false;
   public static Predicate<Block> toxic = new Predicate<Block>() {
      public boolean apply(Block input) {
         return input == BlocksRegister.TOXICDIRT
            || input == BlocksRegister.TOXICGRASS
            || input == BlocksRegister.SLUDGE
            || input == BlocksRegister.RADIOCOBBLE
            || input == BlocksRegister.RADIOSTONE
            || input == BlocksRegister.JUNK
            || input == BlocksRegister.SCRAP
            || input == BlocksRegister.BRICKSHARDS;
      }
   };
   public static Predicate<Block> toxicflower = new Predicate<Block>() {
      public boolean apply(Block input) {
         return input == BlocksRegister.MUTAFLOWERPINK || input == BlocksRegister.MUTAFLOWERRED;
      }
   };
   public static Predicate<Block> slime = new Predicate<Block>() {
      public boolean apply(Block input) {
         return input == BlocksRegister.BROWNSLIME || input == Blocks.SLIME_BLOCK;
      }
   };
   public static Predicate<Block> bunker = new Predicate<Block>() {
      public boolean apply(Block input) {
         return input == BlocksRegister.RUSTMETALL || input == BlocksRegister.RUSTARMATURE || input == BlocksRegister.DARKRUSTMETALL;
      }
   };

   public MobSpawn(int dimension) {
      this.dimension = dimension;
   }

   public String getChatMessage() {
      return "";
   }

   public void setSwarmFrequency(int swarmFrequency) {
      int i1 = swarmFrequency / frequency;
      this.swarmFrequency = i1 * frequency;
      this.enableSwarms = true;
   }

   public int getSwarmFrequency() {
      return this.swarmFrequency;
   }

   public void addReactor(MobReactor reactor) {
      reactor.chanceMinimum = this.chancesSum;
      this.chancesSum = this.chancesSum + reactor.chance;
      reactor.chanceMaximum = this.chancesSum - 1;
      this.reactors.add(reactor);
      if (this.lowestCost > reactor.pointsCost) {
         this.lowestCost = reactor.pointsCost;
      }

      if (this.highestCost < reactor.pointsCost) {
         this.highestCost = reactor.pointsCost;
      }
   }

   public void addMiniBoss(MobReactor reactor) {
      this.miniBoss = reactor;
   }

   public int getPointsPerPlayerPerSecond(EntityPlayer player, int difficulty) {
      return 2;
   }

   public int getPointsPerPlayerOnSwarm(EntityPlayer player, int difficulty) {
      return 300 + 100 * difficulty;
   }

   public int getMaxPointsPerPlayer(EntityPlayer player, int difficulty) {
      return this.getPointsPerPlayerOnSwarm(player, difficulty);
   }

   public void createMobs(World world, EntityPlayer player, int difficulty) {
      AbstractWorldProvider abstractWorldProvider = null;
      if (world.provider instanceof AbstractWorldProvider) {
         abstractWorldProvider = (AbstractWorldProvider)world.provider;
      }

      if (this.chancesSum > 0) {
         if (this.miniBoss != null) {
            int swarmsWithoutMiniboss = Mana.getSwarmsWithoutMiniboss(player);
            if (swarmsWithoutMiniboss < 0) {
               MobReactor reactor = this.miniBoss;
               if (reactor.involved(player, abstractWorldProvider) && reactor.doMobGroup(world, this, player, difficulty) > 0) {
                  Mana.setSwarmsWithoutMiniboss(player, 0);
               }
            }
         }

         for (int i = 0; i < 10 * difficulty; i++) {
            int randint = this.rand.nextInt(this.chancesSum);

            for (MobReactor reactor : this.reactors) {
               if (reactor.chanceMinimum <= randint && reactor.chanceMaximum >= randint) {
                  if (reactor.involved(player, abstractWorldProvider) && reactor.doMobGroup(world, this, player, difficulty) <= 0) {
                     return;
                  }
                  break;
               }
            }
         }
      }
   }

   public static boolean isInBossfightNow(EntityPlayer player) {
      for (Entity entity : GetMOP.getEntitiesInAABBof(player.world, player, 64.0, player)) {
         if (!entity.isNonBoss() && entity instanceof AbstractMob) {
            return true;
         }
      }

      return false;
   }

   public static void chillAfterBoss(EntityPlayer player) {
      Mana.setSwarmTicks(player, 1);
   }

   public static void onPlayerUpdate(World world, EntityPlayer player) {
      int swarmTicks = Mana.addSwarmTicks(player, 1);
      if (swarmTicks % frequency == 0 && !player.isSpectator()) {
         int diff = world.getDifficulty().getId();
         MobSpawn spawn = spawnByDimension.get(player.dimension);
         if (spawn != null) {
            if (!spawn.inSurvivorMode) {
               int max = spawn.getMaxPointsPerPlayer(player, diff);
               int has = Mana.getSwarmPoints(player);
               if (has < max) {
                  Mana.addSwarmPoints(player, spawn.getPointsPerPlayerPerSecond(player, diff) * (frequency / 20));
               }
            }

            if (spawn.dimension == player.world.provider.getDimension()) {
               int playerSwarmPoints = Mana.getSwarmPoints(player);
               if (playerSwarmPoints >= spawn.highestCost) {
                  spawn.createMobs(world, player, diff);
               }
            }

            if (spawn.enableSwarms && swarmTicks % spawn.getSwarmFrequency() == 0) {
               int perSwarm = spawn.getPointsPerPlayerOnSwarm(player, diff);
               int max = spawn.getMaxPointsPerPlayer(player, diff);
               if (isInBossfightNow(player)) {
                  perSwarm /= 4;
               }

               int has = Mana.getSwarmPoints(player);
               if (has < max) {
                  Mana.setSwarmPoints(player, Math.min(has + perSwarm, max));
                  player.sendMessage(new TextComponentString(spawn.getChatMessage()));
                  if (spawn.miniBoss != null) {
                     int swarmsWithoutMiniboss = Mana.getSwarmsWithoutMiniboss(player);
                     float minibossChance = spawn.getMinibossChanceOnSwarm(player, swarmsWithoutMiniboss);
                     if (spawn.rand.nextFloat() < minibossChance) {
                        Mana.setSwarmsWithoutMiniboss(player, -1);
                     } else {
                        Mana.setSwarmsWithoutMiniboss(player, swarmsWithoutMiniboss + 1);
                     }
                  }
               }
            }
         }
      }
   }

   public float getMinibossChanceOnSwarm(EntityPlayer player, int swarmsWithoutMiniboss) {
      if (swarmsWithoutMiniboss == 1) {
         return 0.25F;
      } else if (swarmsWithoutMiniboss == 2) {
         return 0.5F;
      } else if (swarmsWithoutMiniboss == 3) {
         return 0.8F;
      } else {
         return swarmsWithoutMiniboss >= 4 ? 0.9F : 0.0F;
      }
   }

   public int buyMobsAndGetCount(EntityPlayer player, int pointsCost, int amountMax) {
      int points = Mana.getSwarmPoints(player);
      return amountMax * pointsCost <= points ? amountMax : points / pointsCost;
   }

   public BlockPos createRandomPosNearPlayer(EntityPlayer player, int emptyZoneRange, int xzRange, int yRange, boolean canBeOverHead) {
      float mult = (float)emptyZoneRange / xzRange;
      int x = this.rand.nextInt(xzRange) - this.rand.nextInt(xzRange);
      int y = this.rand.nextInt(yRange) - this.rand.nextInt(yRange);
      int z = this.rand.nextInt(xzRange) - this.rand.nextInt(xzRange);
      if (!canBeOverHead) {
         if (Math.abs(x) < emptyZoneRange && Math.abs(z) < emptyZoneRange) {
            if (this.rand.nextFloat() < 0.3) {
               x = (int)(x * mult + (x < 0 ? -emptyZoneRange : emptyZoneRange));
               z = (int)(z * mult + (z < 0 ? -emptyZoneRange : emptyZoneRange));
            } else if (this.rand.nextFloat() < 0.5) {
               x = (int)(x * mult + (x < 0 ? -emptyZoneRange : emptyZoneRange));
            } else {
               z = (int)(z * mult + (z < 0 ? -emptyZoneRange : emptyZoneRange));
            }
         }
      } else if (Math.abs(x) < emptyZoneRange && Math.abs(z) < emptyZoneRange && Math.abs(y) < emptyZoneRange) {
         if (this.rand.nextFloat() < 0.15) {
            x = (int)(x * mult + (x < 0 ? -emptyZoneRange : emptyZoneRange));
            z = (int)(z * mult + (z < 0 ? -emptyZoneRange : emptyZoneRange));
            y = (int)(y * mult + (y < 0 ? -emptyZoneRange : emptyZoneRange));
         } else if (this.rand.nextFloat() < 0.3) {
            x = (int)(x * mult + (x < 0 ? -emptyZoneRange : emptyZoneRange));
            z = (int)(z * mult + (z < 0 ? -emptyZoneRange : emptyZoneRange));
         } else if (this.rand.nextFloat() < 0.3333) {
            x = (int)(x * mult + (x < 0 ? -emptyZoneRange : emptyZoneRange));
         } else if (this.rand.nextFloat() < 0.5) {
            z = (int)(z * mult + (z < 0 ? -emptyZoneRange : emptyZoneRange));
         } else {
            y = (int)(y * mult + (y < 0 ? -emptyZoneRange : emptyZoneRange));
         }
      }

      return new BlockPos(player.posX + x, player.posY + y, player.posZ + z);
   }

   public boolean landMobGroup(World world, BlockPos pos, List<EntityLiving> mobs) {
      return this.landMobGroup(world, pos.getY(), pos.getX(), pos.getY(), pos.getZ(), mobs);
   }

   public boolean landMobGroup(World world, int baseY, double x, double y, double z, List<EntityLiving> mobs) {
      PooledMutableBlockPos blockpos$pooledmutableblockpos = PooledMutableBlockPos.retain();
      int minn = baseY - this.maxLandingYdecrease;

      while (y > minn) {
         if (world.getBlockState(blockpos$pooledmutableblockpos.setPos(x, y, z)).isSideSolid(world, blockpos$pooledmutableblockpos, EnumFacing.UP)
            && this.isEnableBlockToSpawnIn(world, blockpos$pooledmutableblockpos.setPos(x, y + 1.0, z))) {
            this.placeMobsOnGround(world, x, y, z, mobs);
            return true;
         }

         y--;
      }

      return false;
   }

   public boolean airborneMobGroup(World world, BlockPos pos, List<EntityLiving> mobs) {
      return this.airborneMobGroup(world, pos.getY(), pos.getX(), pos.getY(), pos.getZ(), mobs);
   }

   public boolean airborneMobGroup(World world, int baseY, double x, double y, double z, List<EntityLiving> mobs) {
      PooledMutableBlockPos blockpos$pooledmutableblockpos = PooledMutableBlockPos.retain();
      int minn = baseY - this.maxLandingYdecrease;
      int maxI = this.maxLandingYdecrease / 2;

      for (int i = 0; i <= maxI; i++) {
         if (this.isEnableBlockToSpawnIn(world, blockpos$pooledmutableblockpos.setPos(x, y, z))) {
            this.placeMobsOnAir(world, x, y, z, mobs);
            return true;
         }

         if (y >= 0.0) {
            y = -y - 1.0;
         } else {
            y = -y;
         }
      }

      return false;
   }

   public void placeMobsOnGround(World world, double x, double y, double z, List<EntityLiving> mobs) {
      for (EntityLiving mob : mobs) {
         if (!mob.isRiding()) {
            boolean succ = false;

            for (int i = 0; i < 2; i++) {
               BlockPos pos = new BlockPos(x + this.rand.nextGaussian() * 2.0, y + this.rand.nextDouble() * 6.0, z + this.rand.nextGaussian() * 2.0);
               boolean airblock = this.isEnableBlockToSpawnIn(world, pos);

               for (int iy = 1; iy < 7; iy++) {
                  BlockPos posd = pos.down(iy);
                  boolean solidd = world.getBlockState(posd).isSideSolid(world, posd, EnumFacing.UP);
                  if (airblock && solidd) {
                     mob.setPosition(posd.getX() + 0.5, posd.getY() + 1.0, posd.getZ() + 0.5);
                     if (mob.isNotColliding()) {
                        this.spawnEntityNaturally(world, mob);
                        this.lastSpawnedMobs++;
                        if (this.inSurvivorMode && SurvivorGamestyleWatcher.currentWatcher != null) {
                           SurvivorGamestyleWatcher.currentWatcher.handleSpawnedMob(mob, this);
                        }

                        succ = true;
                        break;
                     }
                  }

                  airblock = !solidd;
               }

               if (succ) {
                  break;
               }
            }

            if (!succ) {
               mob.setDead();
            }
         }
      }

      for (EntityLiving mobx : mobs) {
         if (mobx.isRiding() && mobx.getRidingEntity() != null && mobx.getRidingEntity().isAddedToWorld()) {
            mobx.setPosition(mobx.getRidingEntity().posX, mobx.getRidingEntity().posY, mobx.getRidingEntity().posZ);
            this.spawnEntityNaturally(world, mobx);
            this.lastSpawnedMobs++;
         }
      }
   }

   public void placeMobsOnAir(World world, double x, double y, double z, List<EntityLiving> mobs) {
      for (EntityLiving mob : mobs) {
         if (!mob.isRiding()) {
            boolean succ = false;

            for (int i = 0; i < 2; i++) {
               BlockPos pos = new BlockPos(x + this.rand.nextGaussian() * 2.0, y + this.rand.nextDouble() * 6.0, z + this.rand.nextGaussian() * 2.0);
               if (this.isEnableBlockToSpawnIn(world, pos)) {
                  mob.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                  if (mob.isNotColliding()) {
                     this.spawnEntityNaturally(world, mob);
                     this.lastSpawnedMobs++;
                     succ = true;
                     break;
                  }
               }

               if (succ) {
                  break;
               }
            }

            if (!succ) {
               mob.setDead();
            }
         }
      }

      for (EntityLiving mobx : mobs) {
         if (mobx.isRiding() && mobx.getRidingEntity() != null && mobx.getRidingEntity().isAddedToWorld()) {
            mobx.setPosition(mobx.getRidingEntity().posX, mobx.getRidingEntity().posY, mobx.getRidingEntity().posZ);
            this.spawnEntityNaturally(world, mobx);
            this.lastSpawnedMobs++;
         }
      }
   }

   public boolean undergroundMobGroup(World world, BlockPos pos, List<EntityLiving> mobs) {
      return this.undergroundMobGroup(world, pos.getY(), pos.getX(), pos.getY(), pos.getZ(), mobs);
   }

   public boolean undergroundMobGroup(World world, int baseY, double x, double y, double z, List<EntityLiving> mobs) {
      PooledMutableBlockPos blockpos$pooledmutableblockpos = PooledMutableBlockPos.retain();
      int minn = baseY - this.maxLandingYdecrease;
      int maxI = this.maxLandingYdecrease / 2;

      for (int i = 0; i <= maxI; i++) {
         if (this.isEnableBlockToSpawnInUnderground(world, blockpos$pooledmutableblockpos.setPos(x, y, z))) {
            this.placeMobsUnderground(world, x, y, z, mobs);
            return true;
         }

         if (y >= 0.0) {
            y = -y - 1.0;
         } else {
            y = -y;
         }
      }

      return false;
   }

   public void placeMobsUnderground(World world, double x, double y, double z, List<EntityLiving> mobs) {
      for (EntityLiving mob : mobs) {
         if (!mob.isRiding()) {
            boolean succ = false;

            for (int i = 0; i < 2; i++) {
               BlockPos pos = new BlockPos(x + this.rand.nextGaussian() * 2.0, y + this.rand.nextGaussian() * 2.0, z + this.rand.nextGaussian() * 2.0);
               if (this.isEnableBlockToSpawnInUnderground(world, pos)) {
                  mob.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                  this.spawnEntityNaturally(world, mob);
                  this.lastSpawnedMobs++;
                  succ = true;
                  break;
               }

               if (succ) {
                  break;
               }
            }

            if (!succ) {
               mob.setDead();
            }
         }
      }

      for (EntityLiving mobx : mobs) {
         if (mobx.isRiding() && mobx.getRidingEntity() != null && mobx.getRidingEntity().isAddedToWorld()) {
            mobx.setPosition(mobx.getRidingEntity().posX, mobx.getRidingEntity().posY, mobx.getRidingEntity().posZ);
            this.spawnEntityNaturally(world, mobx);
            this.lastSpawnedMobs++;
         }
      }
   }

   public boolean waterMobGroup(World world, BlockPos pos, List<EntityLiving> mobs) {
      return this.waterMobGroup(world, pos.getY(), pos.getX(), pos.getY(), pos.getZ(), mobs);
   }

   public boolean waterMobGroup(World world, int baseY, double x, double y, double z, List<EntityLiving> mobs) {
      PooledMutableBlockPos blockpos$pooledmutableblockpos = PooledMutableBlockPos.retain();
      int minn = baseY - this.maxLandingYdecrease;
      int maxI = this.maxLandingYdecrease / 2;

      for (int i = 0; i <= maxI; i++) {
         if (this.isEnableBlockToSpawnInWater(world, blockpos$pooledmutableblockpos.setPos(x, y, z))) {
            this.placeMobsWater(world, x, y, z, mobs);
            return true;
         }

         if (y >= 0.0) {
            y = -y - 1.0;
         } else {
            y = -y;
         }
      }

      return false;
   }

   public void placeMobsWater(World world, double x, double y, double z, List<EntityLiving> mobs) {
      for (EntityLiving mob : mobs) {
         if (!mob.isRiding()) {
            boolean succ = false;

            for (int i = 0; i < 2; i++) {
               BlockPos pos = new BlockPos(x + this.rand.nextGaussian() * 2.0, y + this.rand.nextDouble() * 6.0, z + this.rand.nextGaussian() * 2.0);
               if (this.isEnableBlockToSpawnInWater(world, pos)) {
                  mob.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                  if (world.getCollisionBoxes(mob, mob.getEntityBoundingBox()).isEmpty()) {
                     this.spawnEntityNaturally(world, mob);
                     this.lastSpawnedMobs++;
                     succ = true;
                     break;
                  }
               }

               if (succ) {
                  break;
               }
            }

            if (!succ) {
               mob.setDead();
            }
         }
      }

      for (EntityLiving mobx : mobs) {
         if (mobx.isRiding() && mobx.getRidingEntity() != null && mobx.getRidingEntity().isAddedToWorld()) {
            mobx.setPosition(mobx.getRidingEntity().posX, mobx.getRidingEntity().posY, mobx.getRidingEntity().posZ);
            this.spawnEntityNaturally(world, mobx);
            this.lastSpawnedMobs++;
         }
      }
   }

   public void spawnEntityNaturally(World world, EntityLiving mob) {
      world.spawnEntity(mob);
      mob.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(mob)), (IEntityLivingData)null);
      if (mob instanceof AbstractMob) {
         ((AbstractMob)mob).canDropLoot = true;
      }
   }

   public static void init() {
      spawnByDimension.put(0, new MobSpawnOverworld());
      spawnByDimension.put(-1, new MobSpawnNether());
      spawnByDimension.put(1, new MobSpawnEnder());
      spawnByDimension.put(100, new MobSpawnEverfrost());
      spawnByDimension.put(101, new MobSpawnToxicomania());
      spawnByDimension.put(102, new MobSpawnDungeon());
      spawnByDimension.put(103, new MobSpawnAquatica());
      spawnByDimension.put(104, new MobSpawnStormledge());
   }

   public static EntityLiving newInstance(World world, Class<? extends EntityLiving> entityClass) throws Exception {
      EntityEntry entry = EntityRegistry.getEntry(entityClass);
      return entry != null ? (EntityLiving)entry.newInstance(world) : entityClass.getConstructor(World.class).newInstance(world);
   }

   public static EnumCreatureType getCreatureType(Class<? extends EntityLiving> entityClass) {
      if (EnumCreatureType.MONSTER.getCreatureClass().isAssignableFrom(entityClass)) {
         return EnumCreatureType.MONSTER;
      } else if (EnumCreatureType.CREATURE.getCreatureClass().isAssignableFrom(entityClass)) {
         return EnumCreatureType.CREATURE;
      } else if (EnumCreatureType.AMBIENT.getCreatureClass().isAssignableFrom(entityClass)) {
         return EnumCreatureType.AMBIENT;
      } else {
         return EnumCreatureType.WATER_CREATURE.getCreatureClass().isAssignableFrom(entityClass) ? EnumCreatureType.WATER_CREATURE : null;
      }
   }

   public boolean isEnableBlockToSpawnIn(World world, BlockPos pos) {
      return world.isAirBlock(pos) || world.getBlockState(pos).getCollisionBoundingBox(world, pos) == null;
   }

   public boolean isEnableBlockToSpawnInUnderground(World world, BlockPos pos) {
      return world.getBlockState(pos).isFullCube();
   }

   public boolean isEnableBlockToSpawnInWater(World world, BlockPos pos) {
      return world.getBlockState(pos).getMaterial() == Material.WATER;
   }
}
