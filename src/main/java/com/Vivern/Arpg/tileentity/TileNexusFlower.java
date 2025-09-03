//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.mobs.AbstractMob;
import com.Vivern.Arpg.mobs.ToxicomaniaMobsPack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class TileNexusFlower extends TileNexus {
   boolean bunkercardSpawned = false;

   public TileNexusFlower() {
      this.FINAL_WAVE = 8;
      this.ZONEhollow = 32.0;
      this.ZONEradius = 50.0;
      this.maxCalmTime = 240;
      this.MAX_HEALTH = 120;
      this.nexusId = 1;
   }

   @Override
   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.setBoolean("bunkercardSpawned", this.bunkercardSpawned);
      return super.writeToNBT(compound);
   }

   @Override
   public void readFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("bunkercardSpawned")) {
         this.bunkercardSpawned = compound.getBoolean("bunkercardSpawned");
      }

      super.readFromNBT(compound);
   }

   @Override
   public int getMaxGroupsCountOnWave(int wave) {
      return 5 + wave * 8;
   }

   @Override
   public void startInvasion(String team) {
      super.startInvasion(team);
      if (this.invasionStarted) {
         this.sendNexusMessageToAllAround("Strange living mucus is trickle around flower");
      }
   }

   @Override
   public void onInvasionEnd(boolean success) {
      if (success) {
         int i = 250;

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
         this.sendNexusMessageToAllAround("The slimes is defeated!");
         this.sendNexusMessageToAllAround(
            "Time: " + mins + " min " + secsadd + "sec" + ", Waves completed: " + this.WAVE + ", Mobs spawned: " + this.MOBS_SPAWNED
         );
      }

      super.onInvasionEnd(success);
      if (this.world.getBlockState(this.getPos()).getBlock() == BlocksRegister.SWEETNECTARFLOWER) {
         this.world.destroyBlock(this.getPos(), false);
      }
   }

   @Override
   public void spawnMobGroup(double x, double y, double z) {
      if (this.WAVE != 8 || this.bunkercardSpawned && !(this.rand.nextFloat() < 0.12)) {
         if (this.WAVE > 1 && this.rand.nextFloat() < 0.3) {
            List<AbstractMob> list = new ArrayList<>();

            for (int i = 0; i < this.rand.nextInt(3); i++) {
               list.add(new ToxicomaniaMobsPack.TestTubeCreature(this.world));
            }

            for (int i = 0; i < this.rand.nextInt(5); i++) {
               list.add(new ToxicomaniaMobsPack.TestTubeSubstance(this.world));
            }

            this.placeMobs(x, y, z, list);
         } else if (this.WAVE == 1 || this.rand.nextFloat() < 0.35) {
            List<AbstractMob> list = new ArrayList<>();

            for (int i = 0; i <= this.rand.nextInt(6); i++) {
               list.add(new ToxicomaniaMobsPack.TestTubeSubstance(this.world));
            }

            this.placeMobs(x, y, z, list);
         } else if (this.WAVE > 2 && this.rand.nextFloat() < 0.28) {
            List<AbstractMob> list = new ArrayList<>();

            for (int i = 0; i < this.rand.nextInt(2); i++) {
               list.add(new ToxicomaniaMobsPack.TestTubeCreature(this.world));
            }

            for (int i = 0; i < this.rand.nextInt(8); i++) {
               list.add(new ToxicomaniaMobsPack.MutantZombie(this.world));
            }

            this.placeMobs(x, y, z, list);
         } else if (this.WAVE > 3 && this.rand.nextFloat() < 0.12) {
            List<AbstractMob> list = new ArrayList<>();
            if (this.rand.nextFloat() < 0.7F) {
               list.add(new ToxicomaniaMobsPack.MutantBeast(this.world));
            }

            for (int i = 0; i <= this.rand.nextInt(8); i++) {
               list.add(new ToxicomaniaMobsPack.MutantZombie(this.world));
            }

            this.placeMobs(x, y, z, list);
         } else if (this.WAVE > 4 && this.rand.nextFloat() < 0.19) {
            List<AbstractMob> list = new ArrayList<>();

            for (int i = 0; i <= this.rand.nextInt(4); i++) {
               list.add(new ToxicomaniaMobsPack.Experiment9(this.world));
            }

            for (int i = 0; i <= this.rand.nextInt(7); i++) {
               list.add(new ToxicomaniaMobsPack.Springer(this.world));
            }

            this.placeMobs(x, y, z, list);
         } else if (this.WAVE > 5 && this.rand.nextFloat() < 0.1) {
            List<AbstractMob> list = new ArrayList<>();
            ToxicomaniaMobsPack.MutantZombie zombie = new ToxicomaniaMobsPack.MutantZombie(this.world);
            ToxicomaniaMobsPack.TestTubeCreature slime = new ToxicomaniaMobsPack.TestTubeCreature(this.world);
            zombie.startRiding(slime);
            list.add(zombie);
            list.add(slime);
            this.placeMobs(x, y, z, list);
         } else if (this.WAVE > 5 && this.rand.nextFloat() < 0.23) {
            List<AbstractMob> list = new ArrayList<>();
            ToxicomaniaMobsPack.MutantZombie zombie = new ToxicomaniaMobsPack.MutantZombie(this.world);
            ToxicomaniaMobsPack.TestTubeSubstance slime = new ToxicomaniaMobsPack.TestTubeSubstance(this.world);
            slime.startRiding(zombie);
            list.add(zombie);
            list.add(slime);
            this.placeMobs(x, y, z, list);
         } else if (this.WAVE <= 6) {
            List<AbstractMob> list = new ArrayList<>();

            for (int i = 0; i <= this.rand.nextInt(3); i++) {
               list.add(new ToxicomaniaMobsPack.TestTubeSubstance(this.world));
            }

            this.placeMobs(x, y, z, list);
         } else {
            List<AbstractMob> list = new ArrayList<>();

            for (int i = 0; i <= this.rand.nextInt(3 + this.WAVE - 6); i++) {
               ToxicomaniaMobsPack.Dron dron = new ToxicomaniaMobsPack.Dron(this.world);
               ToxicomaniaMobsPack.TestTubeSubstance slime = new ToxicomaniaMobsPack.TestTubeSubstance(this.world);
               dron.startRiding(slime);
               list.add(dron);
               list.add(slime);
            }

            this.placeMobs(x, y, z, list);
         }
      } else {
         int spawnedThisW = this.groupsSpawnedThisWave;
         List<AbstractMob> list = new ArrayList<>();
         ToxicomaniaMobsPack.TestTubeSubstance subs = new ToxicomaniaMobsPack.TestTubeSubstance(this.world);
         subs.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(ItemsRegister.BUNKERKEYCARD));
         subs.setGlowing(true);
         list.add(subs);
         this.placeMobs(x, y, z, list);
         if (this.groupsSpawnedThisWave > spawnedThisW) {
            this.bunkercardSpawned = true;
         }
      }
   }

   @Override
   public void onInvaderKilled(AbstractMob entity, DamageSource source) {
      float seriousness = this.getEntitySeriousness(entity);
      this.calmTimeBonus = MathHelper.clamp(this.calmTimeBonus + (int)(seriousness * 3.0F), 0, this.maxCalmTime * 2);
      this.waveCompleteRate += seriousness;
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

   @Override
   public float getEntitySeriousness(AbstractMob mob) {
      if (mob instanceof ToxicomaniaMobsPack.TestTubeCreature) {
         return 5.0F + Math.max(3 - this.WAVE, 0);
      } else if (mob instanceof ToxicomaniaMobsPack.Springer) {
         return 1.0F;
      } else if (mob instanceof ToxicomaniaMobsPack.MutantBeast) {
         return 10.0F;
      } else if (mob instanceof ToxicomaniaMobsPack.Dron) {
         return 3.0F;
      } else if (mob instanceof ToxicomaniaMobsPack.Hose) {
         return 8.0F;
      } else {
         return mob instanceof ToxicomaniaMobsPack.TestTubeSubstance ? 2.0F + Math.max(5 - this.WAVE, 0) : 2.0F + Math.max(4 - this.WAVE, 0);
      }
   }
}
