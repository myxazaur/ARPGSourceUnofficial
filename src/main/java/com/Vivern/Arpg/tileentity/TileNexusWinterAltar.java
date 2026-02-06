package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.dimensions.ethernalfrost.DimensionEthernalFrost;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.OreDicHelper;
import com.Vivern.Arpg.main.ParticleFastSummon;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.mobs.AbstractMob;
import com.Vivern.Arpg.mobs.EverfrostMobsPack;
import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.renders.IMagicVision;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;

public class TileNexusWinterAltar extends TileNexus implements IVialElementsAccepter, IMagicVision, ITileEntitySynchronize {
   public static ResourceLocation circle_aurora = new ResourceLocation("arpg:textures/circle_aurora.png");
   public static float maxElementCount = 16.0F;
   public float elementCollected;
   public int resourceComplete;
   public byte resourceItem;
   public static int circlesForIngot = 1;
   public static int circlesForSphere = 2;

   public TileNexusWinterAltar() {
      this.FINAL_WAVE = 6;
      this.ZONEhollow = 32.0;
      this.ZONEradius = 52.0;
      this.ZONEheight = 24.0;
      this.maxLandingYdecrease = 0;
      this.maxCalmTime = 200;
      this.MAX_HEALTH = 420;
      this.nexusId = 4;
   }

   @Override
   public void onClient(double... args) {
      if (args.length == 1) {
         if (args[0] == 0.0) {
            this.invasionStarted = false;
         }

         if (args[0] == 1.0) {
            this.invasionStarted = true;
         }

         if (args[0] == 2.0) {
         }
      }
   }

   @Override
   public double getCooledSpawnRate(boolean forWaveStart) {
      return forWaveStart ? 5.0E-5F : 3.0E-4F;
   }

   @Override
   public void update() {
      super.update();
      if (this.world.isRemote && this.invasionStarted) {
         if (this.invasionTime % 20 == 0) {
            ParticleFastSummon.round2(
               this.world,
               circle_aurora,
               new Vec3d(this.pos.getX() + 0.5, this.pos.getY() + 1.05, this.pos.getZ() + 0.5),
               0.2F,
               1.2F,
               20,
               220,
               1.0F,
               1.0F,
               1.0F,
               2
            );
         }

         if (this.rand.nextFloat() < 0.25F) {
            ShardType.COLD
               .spawnNativeParticle(
                  this.world,
                  1.0F,
                  this.pos.getX() + 0.5,
                  this.pos.getY() + 1.05,
                  this.pos.getZ() + 0.5,
                  this.rand.nextGaussian() / 19.0,
                  this.rand.nextGaussian() / 19.0 + 0.07F,
                  this.rand.nextGaussian() / 19.0,
                  true
               );
         }
      }

      if (!this.world.isRemote && this.invasionStarted) {
         if (this.resourceItem > 0) {
            this.resourceComplete++;
            if (this.resourceComplete >= 100) {
               this.world
                  .spawnEntity(
                     new EntityItem(
                        this.world,
                        this.pos.getX() + 0.5,
                        this.pos.getY() + 1.5,
                        this.pos.getZ() + 0.5,
                        new ItemStack(this.resourceItem == 1 ? ItemsRegister.INGOTNORTHERN : ItemsRegister.NORTHERNSPHERE)
                     )
                  );
               this.resourceComplete = 0;
               this.resourceItem = 0;
               this.world
                  .playSound((EntityPlayer)null, this.getPos(), Sounds.magic_k, SoundCategory.BLOCKS, 1.0F, 0.8F + this.rand.nextFloat() * 0.2F);
            }
         }

         if (this.invasionTime % 30 == 0 && this.resourceItem == 0) {
            int circles = 0;
            int ingots = 0;
            int fragments = 0;
            byte can = 0;
            List<Entity> list = GetMOP.getEntitiesInAABBof(this.world, this.pos.up(), 0.5, null);

            for (Entity entity : list) {
               if (entity instanceof EntityItem) {
                  EntityItem entityItem = (EntityItem)entity;
                  Item item = entityItem.getItem().getItem();
                  if (item == ItemsRegister.WEATHERFRAGMENTS) {
                     fragments += entityItem.getItem().getCount();
                  }

                  if (item == ItemsRegister.ICECIRCLEFILLED) {
                     circles += entityItem.getItem().getCount();
                  }

                  if (OreDicHelper.itemStringOredigMatches(entityItem.getItem(), OreDicHelper.SILVER)) {
                     ingots += entityItem.getItem().getCount();
                  }
               }

               if (ingots >= 1 && circles >= circlesForIngot) {
                  can = 1;
                  break;
               }

               if (fragments >= 1 && circles >= circlesForSphere) {
                  can = 2;
                  break;
               }
            }

            if (can > 0) {
               int shouldRemoveIngots = can == 1 ? 1 : 0;
               int shouldRemoveFragments = can == 2 ? 1 : 0;
               int shouldRemoveCircles = can == 1 ? circlesForIngot : circlesForSphere;

               for (Entity entity : list) {
                  if (entity instanceof EntityItem) {
                     EntityItem entityItemx = (EntityItem)entity;
                     Item itemx = entityItemx.getItem().getItem();
                     if (itemx == ItemsRegister.WEATHERFRAGMENTS && shouldRemoveFragments > 0) {
                        int canRemove = Math.min(entityItemx.getItem().getCount(), shouldRemoveFragments);
                        entityItemx.getItem().shrink(canRemove);
                        shouldRemoveFragments -= canRemove;
                     }

                     if (itemx == ItemsRegister.ICECIRCLEFILLED && shouldRemoveCircles > 0) {
                        int canRemove = Math.min(entityItemx.getItem().getCount(), shouldRemoveCircles);
                        entityItemx.getItem().shrink(canRemove);
                        shouldRemoveCircles -= canRemove;
                     }

                     if (OreDicHelper.itemStringOredigMatches(entityItemx.getItem(), OreDicHelper.SILVER) && shouldRemoveIngots > 0) {
                        int canRemove = Math.min(entityItemx.getItem().getCount(), shouldRemoveIngots);
                        entityItemx.getItem().shrink(canRemove);
                        shouldRemoveIngots -= canRemove;
                     }
                  }
               }

               if (shouldRemoveIngots == 0 && shouldRemoveFragments == 0 && shouldRemoveCircles == 0) {
                  this.resourceItem = can;
                  this.world
                     .playSound(
                        (EntityPlayer)null, this.getPos(), Sounds.winter_altar_craft, SoundCategory.BLOCKS, 1.0F, 0.9F + this.rand.nextFloat() * 0.2F
                     );
                  ITileEntitySynchronize.sendSynchronize(this, 64.0, 1.0);
               }
            }
         } else {
            for (Entity entityx : GetMOP.getEntitiesInAABBof(this.world, this.pos.up(), 1.3, null)) {
               if (entityx instanceof EntityItem) {
                  EntityItem entityItemxx = (EntityItem)entityx;
                  Item itemxx = entityItemxx.getItem().getItem();
                  if (itemxx == ItemsRegister.WEATHERFRAGMENTS
                     || itemxx == ItemsRegister.ICECIRCLEFILLED
                     || OreDicHelper.itemStringOredigMatches(entityItemxx.getItem(), OreDicHelper.SILVER)) {
                     SuperKnockback.applyMove(
                        entityItemxx,
                        -0.13F,
                        this.pos.getX() + 0.5,
                        this.pos.getY() + 1.65,
                        this.pos.getZ() + 0.5
                     );
                     entityItemxx.velocityChanged = true;
                  }
               }
            }
         }
      }
   }

   @Override
   public float acceptVialElements(ItemStack vial, ShardType shardType, float count) {
      if (shardType != ShardType.COLD) {
         return count;
      } else if (count > 0.0F) {
         float add = Math.min(maxElementCount - this.elementCollected, count);
         this.elementCollected += add;
         PacketHandler.trySendPacketUpdate(this.world, this.pos, this, 64.0);
         return count - add;
      } else {
         float remove = Math.max(-this.elementCollected, count);
         this.elementCollected += remove;
         PacketHandler.trySendPacketUpdate(this.world, this.pos, this, 64.0);
         return count - remove;
      }
   }

   @Override
   public float getElementCount(ShardType shardType) {
      return shardType != ShardType.COLD ? 0.0F : this.elementCollected;
   }

   @Override
   public float getElementEnergy(ShardType shardType) {
      return shardType != ShardType.COLD ? 0.0F : this.elementCollected;
   }

   @Override
   public float getMana() {
      return 0.0F;
   }

   @Override
   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.setFloat("elementCollected", this.elementCollected);
      compound.setByte("resourceItem", this.resourceItem);
      return super.writeToNBT(compound);
   }

   @Override
   public void readFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("elementCollected")) {
         this.elementCollected = compound.getFloat("elementCollected");
      }

      if (compound.hasKey("resourceItem")) {
         this.resourceItem = compound.getByte("resourceItem");
      }

      super.readFromNBT(compound);
   }

   @Override
   public void startNextWave() {
      if (!DimensionEthernalFrost.isAuroraNow(this.getWorld())) {
         this.sendNexusMessageToAllAround("The aurora time are over");
         this.onInvasionEnd(false);
      }

      super.startNextWave();
   }

   @Override
   public int getMaxGroupsCountOnWave(int wave) {
      if (wave == 1) {
         return 4;
      } else if (wave == 2) {
         return 5;
      } else {
         return wave == 3 ? 5 : 6;
      }
   }

   @Override
   public void startInvasion(String team) {
      super.startInvasion(team);
      if (this.invasionStarted) {
         this.sendNexusMessageToAllAround("The aurora light becomes powerful");
         ITileEntitySynchronize.sendSynchronize(this, 64.0, 1.0);
      }
   }

   @Override
   public void onInvasionEnd(boolean success) {
      if (success) {
         int i = 200;

         while (i > 0) {
            int j = EntityXPOrb.getXPSplit(i);
            i -= j;
            this.world
               .spawnEntity(
                  new EntityXPOrb(
                     this.world,
                     this.getPos().getX() + 0.5,
                     this.getPos().getY() + 0.75,
                     this.getPos().getZ() + 0.5,
                     j
                  )
               );
         }

         int secs = this.invasionTime / 20;
         int mins = secs / 60;
         int secsadd = secs % 60;
         this.sendNexusMessageToAllAround("The lights are fading!");
         this.sendNexusMessageToAllAround(
            "Time: " + mins + " min " + secsadd + "sec" + ", Waves completed: " + this.WAVE + ", Mobs spawned: " + this.MOBS_SPAWNED
         );
      }

      this.resourceItem = 0;
      this.resourceComplete = 0;
      super.onInvasionEnd(success);
      ITileEntitySynchronize.sendSynchronize(this, 64.0, 0.0);
   }

   @Override
   public void spawnMobGroup(double x, double y, double z) {
      if (this.rand.nextFloat() < 0.1) {
         List<AbstractMob> list = new ArrayList<>();
         list.add(new EverfrostMobsPack.Frost(this.world));
         this.placeMobs(x, y, z, list);
      } else if (this.rand.nextFloat() < 0.08) {
         List<AbstractMob> list = new ArrayList<>();
         list.add(new EverfrostMobsPack.Gelum(this.world));
         this.placeMobs(x, y, z, list);
      } else {
         List<AbstractMob> list = new ArrayList<>();

         for (int i = 0; i < 1; i++) {
            list.add(new EverfrostMobsPack.AurorasPhantasm(this.world));
         }

         this.placeMobs(x, y, z, list);
      }
   }

   @Override
   public void onInvaderKilled(AbstractMob entity, DamageSource source) {
      float seriousness = this.getEntitySeriousness(entity);
      this.calmTimeBonus = MathHelper.clamp(this.calmTimeBonus + (int)(seriousness * 3.0F), 0, this.maxCalmTime * 2);
      this.waveCompleteRate += seriousness;
   }

   @Override
   public void initSpawnZones() {
      double posX = this.getPos().getX() + 0.5;
      double posY = this.getPos().getY() + 0.5;
      double posZ = this.getPos().getZ() + 0.5;
      this.MOB_SPAWN_ZONES = new AxisAlignedBB[]{null, null, null, null};
      this.MOB_SPAWN_ZONES[0] = new AxisAlignedBB(
         posX + this.ZONEhollow, posY, posZ - this.ZONEhollow, posX + this.ZONEradius, posY + this.ZONEheight, posZ + this.ZONEradius
      );
      this.MOB_SPAWN_ZONES[1] = new AxisAlignedBB(
         posX - this.ZONEradius, posY, posZ + this.ZONEhollow, posX + this.ZONEhollow, posY + this.ZONEheight, posZ + this.ZONEradius
      );
      this.MOB_SPAWN_ZONES[2] = new AxisAlignedBB(
         posX - this.ZONEradius, posY, posZ - this.ZONEradius, posX - this.ZONEhollow, posY + this.ZONEheight, posZ + this.ZONEhollow
      );
      this.MOB_SPAWN_ZONES[3] = new AxisAlignedBB(
         posX - this.ZONEhollow, posY, posZ - this.ZONEradius, posX + this.ZONEradius, posY + this.ZONEheight, posZ - this.ZONEhollow
      );
   }

   @Override
   public void landMobGroup(double x, double y, double z) {
      PooledMutableBlockPos blockpos$pooledmutableblockpos = PooledMutableBlockPos.retain();
      int minn = this.getPos().getY() - this.maxLandingYdecrease;

      while (y > minn) {
         if (this.world.isAirBlock(blockpos$pooledmutableblockpos.setPos(x, y, z))) {
            blockpos$pooledmutableblockpos.release();
            this.spawnMobGroup(x, y, z);
            return;
         }

         y--;
      }

      blockpos$pooledmutableblockpos.release();
   }

   public void placeMobs(double x, double y, double z, List<AbstractMob> mobs) {
      boolean atleast1mobspawned = false;

      for (AbstractMob mob : mobs) {
         if (!mob.isRiding()) {
            boolean succ = false;

            for (int i = 0; i < 10; i++) {
               BlockPos pos = new BlockPos(x + this.rand.nextGaussian() * 2.0, y + this.rand.nextGaussian() * 2.0, z + this.rand.nextGaussian() * 2.0);
               boolean airblock = this.world.isAirBlock(pos);
               if (airblock) {
                  mob.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                  if (mob.isNotColliding()) {
                     this.world.spawnEntity(mob);
                     mob.onInitialSpawn();
                     mob.isAgressive = true;
                     mob.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(this.ZONEradius);
                     mob.nexus = this;
                     mob.canDropLoot = true;
                     this.MOBS_SPAWNED++;
                     atleast1mobspawned = true;
                     succ = true;
                     break;
                  }
               }
            }

            if (!succ) {
               mob.setDead();
            }
         }
      }

      for (AbstractMob mobx : mobs) {
         if (mobx.isRiding() && mobx.getRidingEntity() != null && mobx.getRidingEntity().isAddedToWorld()) {
            mobx.setPosition(mobx.getRidingEntity().posX, mobx.getRidingEntity().posY, mobx.getRidingEntity().posZ);
            this.world.spawnEntity(mobx);
            mobx.onInitialSpawn();
            mobx.isAgressive = true;
            mobx.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(this.ZONEradius);
            mobx.nexus = this;
            mobx.canDropLoot = true;
            this.MOBS_SPAWNED++;
            atleast1mobspawned = true;
         }
      }

      if (atleast1mobspawned) {
         this.groupsSpawnedThisWave++;
      }
   }

   @Override
   public float getEntitySeriousness(AbstractMob mob) {
      return 20.0F;
   }
}
