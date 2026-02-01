package com.vivern.arpg.tileentity;

import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.mobs.AbstractMob;
import com.vivern.arpg.mobs.EverfrostMobsPack;
import com.vivern.arpg.mobs.NexusCap;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;

public class TileNexusNiveolite extends TileNexus {
   public int slidersGoal;
   public boolean summonSlider;
   public float clientAnimation;
   public float clientAnimationPrev;
   public int clientUprise;
   public int clientUprisePrev;

   public TileNexusNiveolite() {
      this.FINAL_WAVE = 5;
      this.ZONEhollow = 2.0;
      this.ZONEradius = 26.0;
      this.ZONEheight = 9.0;
      this.maxCalmTime = 60;
      this.MAX_HEALTH = 1000;
      this.nexusId = 3;
   }

   public void onGoal() {
      this.slidersGoal++;
      this.waveCompleteRate += 25.0F;
      this.sendPacket();
   }

   @Override
   public void spawnNexusCap(String team) {
      NexusCap cap = new NexusCap(this.getWorld(), this, this.MAX_HEALTH, 0.5F, 0.5F);
      cap.setPosition(
         this.pos.getX() + 0.5,
         this.pos.getY() + 0.5 - cap.height / 2.0,
         this.pos.getZ() + 0.5
      );
      this.world.spawnEntity(cap);
      cap.onInitialSpawn();
      cap.team = team;
      cap.nexusPos = this.getPos();
      cap.setEntityInvulnerable(true);
   }

   @Override
   public void update() {
      super.update();
      if (this.world.isRemote) {
         this.clientAnimationPrev = this.clientAnimation;
         this.clientUprisePrev = this.clientUprise;
         this.clientAnimation = this.clientAnimation + this.clientUprise / 40.0F;
         EntityPlayer entityplayer = this.world
            .getClosestPlayer(
               this.pos.getX() + 0.5F, this.pos.getY() + 0.5F, this.pos.getZ() + 0.5F, 4.0, false
            );
         if (entityplayer != null) {
            if (this.clientUprise < 40) {
               this.clientUprise++;
            }
         } else if (this.clientUprise > 0) {
            this.clientUprise--;
         }
      }

      if (!this.world.isRemote && this.invasionStarted && this.invasionTime % 20 == 0) {
         AxisAlignedBB aabb = new AxisAlignedBB(this.pos).grow(this.ZONEradius + 6.0, this.ZONEheight, this.ZONEradius + 6.0);
         List<EverfrostMobsPack.NiveousSlider> sliders = this.world.getEntitiesWithinAABB(EverfrostMobsPack.NiveousSlider.class, aabb);
         float chanceToNew = 0.3F - GetMOP.getfromto((float)sliders.size(), 0.0F, 4.0F) * 0.3F;
         chanceToNew = chanceToNew * chanceToNew * 15.0F;
         if (this.rand.nextFloat() < chanceToNew) {
            this.summonSlider = true;
            Vec3d randpos = this.getRandomPosInZone();
            this.landMobGroup(randpos.x, randpos.y, randpos.z);
         }

         List<EntityPlayer> players = this.world.getEntitiesWithinAABB(EntityPlayer.class, aabb);
         boolean noempty = false;

         for (EntityPlayer player : players) {
            if (player.isEntityAlive() && !player.isSpectator()) {
               noempty = true;
               break;
            }
         }

         if (!noempty) {
            AxisAlignedBB aabb2 = new AxisAlignedBB(this.getPos().add(-1, -1, -1), this.getPos().add(1, 1, 1));
            List<NexusCap> list = this.world.getEntitiesWithinAABB(NexusCap.class, aabb2);
            if (!list.isEmpty()) {
               for (NexusCap cap : list) {
                  cap.attackEntityFrom(DamageSource.OUT_OF_WORLD, 20.0F);
               }
            }
         }
      }
   }

   @Override
   public void landMobGroup(double x, double y, double z) {
      PooledMutableBlockPos blockpos$pooledmutableblockpos = PooledMutableBlockPos.retain();
      int minn = this.getPos().getY() - this.maxLandingYdecrease;

      while (y > minn) {
         IBlockState state = this.world.getBlockState(blockpos$pooledmutableblockpos.setPos(x, y, z));
         if (state.isSideSolid(this.world, blockpos$pooledmutableblockpos, EnumFacing.UP)
            && state.getBlock() != BlocksRegister.NIVEOUSHOLE
            && this.world.isAirBlock(blockpos$pooledmutableblockpos.setPos(x, y + 1.0, z))) {
            blockpos$pooledmutableblockpos.release();
            this.spawnMobGroup(x, y + 1.0, z);
            return;
         }

         y--;
      }

      blockpos$pooledmutableblockpos.release();
   }

   @Override
   public int getMaxGroupsCountOnWave(int wave) {
      return wave == 5 ? 12 : 8;
   }

   @Override
   public double getCooledSpawnRate(boolean forWaveStart) {
      return forWaveStart ? 5.0E-5F : 0.0025F;
   }

   @Override
   public void startInvasion(String team) {
      super.startInvasion(team);
      if (this.invasionStarted) {
         this.sendNexusMessageToAllAround("The frozen mechanism starts working");
      }
   }

   @Override
   public void onInvasionEnd(boolean success) {
      if (success) {
         int i = 180;

         while (i > 0) {
            int j = EntityXPOrb.getXPSplit(i);
            i -= j;
            this.world
               .spawnEntity(
                  new EntityXPOrb(
                     this.world,
                     this.getPos().getX() + 0.5,
                     this.getPos().getY() + 1.15,
                     this.getPos().getZ() + 0.5,
                     j
                  )
               );
         }

         int count = 15 + this.rand.nextInt(6);

         for (int j = 0; j < count; j++) {
            this.world
               .spawnEntity(
                  new EntityItem(
                     this.world,
                     this.getPos().getX() + 0.5,
                     this.getPos().getY() + 1.15,
                     this.getPos().getZ() + 0.5,
                     new ItemStack(ItemsRegister.ICECIRCLE)
                  )
               );
         }

         int secs = this.invasionTime / 20;
         int mins = secs / 60;
         int secsadd = secs % 60;
         this.sendNexusMessageToAllAround("All goals scored!");
         this.sendNexusMessageToAllAround(
            "Time: " + mins + " min " + secsadd + "sec" + ", Waves completed: " + this.WAVE + ", Mobs spawned: " + this.MOBS_SPAWNED
         );
      }

      super.onInvasionEnd(success);
   }

   @Override
   public void spawnMobGroup(double x, double y, double z) {
      if (this.summonSlider) {
         List<AbstractMob> list = new ArrayList<>();
         EverfrostMobsPack.NiveousSlider slider = new EverfrostMobsPack.NiveousSlider(this.world);
         slider.nexusPosition = this.getPos();
         list.add(slider);
         this.placeMobs(x, y, z, list);
         this.summonSlider = false;
      } else {
         if (this.WAVE >= 5 && this.rand.nextFloat() < 0.2) {
            List<AbstractMob> list = new ArrayList<>();
            list.add(new EverfrostMobsPack.IceApparition(this.world));
            this.placeMobs(x, y, z, list);
         } else if (this.WAVE >= 4 && this.rand.nextFloat() < 0.1) {
            List<AbstractMob> list = new ArrayList<>();
            list.add(new EverfrostMobsPack.HarridanOfIce(this.world));
            this.placeMobs(x, y, z, list);
         } else if (this.WAVE >= 3 && this.rand.nextFloat() < 0.35) {
            List<AbstractMob> list = new ArrayList<>();

            for (int i = 0; i < 1 + this.rand.nextInt(3); i++) {
               list.add(new EverfrostMobsPack.IceWarrior(this.world));
            }

            this.placeMobs(x, y, z, list);
         } else if (this.WAVE >= 2 && this.rand.nextFloat() < 0.5) {
            List<AbstractMob> list = new ArrayList<>();

            for (int i = 0; i < 1 + this.rand.nextInt(3); i++) {
               list.add(new EverfrostMobsPack.Fentral(this.world));
            }

            this.placeMobs(x, y, z, list);
         } else if (this.WAVE >= 1) {
            List<AbstractMob> list = new ArrayList<>();

            for (int i = 0; i < 1 + this.rand.nextInt(4); i++) {
               list.add(new EverfrostMobsPack.Snowrover(this.world));
            }

            this.placeMobs(x, y, z, list);
         }
      }
   }

   @Override
   public float getPassiveCompleting() {
      return 0.0F;
   }

   @Override
   public float getPassiveSpawnCompleting() {
      return 0.0F;
   }

   @Override
   public void onInvaderKilled(AbstractMob entity, DamageSource source) {
   }

   public void placeMobs(double x, double y, double z, List<AbstractMob> mobs) {
      boolean atleast1mobspawned = false;

      for (AbstractMob mob : mobs) {
         if (!mob.isRiding()) {
            boolean succ = false;

            for (int i = 0; i < 2; i++) {
               BlockPos pos = new BlockPos(x + this.rand.nextGaussian() * 2.0, y + this.rand.nextDouble() * 6.0, z + this.rand.nextGaussian() * 2.0);
               boolean airblock = this.world.isAirBlock(pos);

               for (int iy = 1; iy < 7; iy++) {
                  BlockPos posd = pos.down(iy);
                  boolean solidd = this.world.getBlockState(posd).isSideSolid(this.getWorld(), posd, EnumFacing.UP);
                  if (airblock && solidd) {
                     mob.setPosition(posd.getX() + 0.5, posd.getY() + 1.0, posd.getZ() + 0.5);
                     if (mob.isNotColliding()) {
                        this.world.spawnEntity(mob);
                        mob.onInitialSpawn();
                        mob.isAgressive = false;
                        mob.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(this.ZONEradius);
                        mob.nexus = this;
                        mob.canDropLoot = true;
                        this.MOBS_SPAWNED++;
                        atleast1mobspawned = true;
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
}
