//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.blocks.TideBeacon;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.mobs.AbstractMob;
import com.Vivern.Arpg.mobs.AquaticaMobsPack;
import com.Vivern.Arpg.recipes.Seal;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;

public class TileNexusBeacon extends TileNexus {
   public float randAngle1 = 0.0F;
   public float randAngle2 = 0.0F;
   public float randAngle3 = 0.0F;
   public float randAngle4 = 0.0F;
   public int tentacklesCount = 0;
   public int mobWave1 = 0;
   public int mobWave2 = 0;
   public int mobWave3 = 0;
   public int ACTIVATOR = 0;
   public boolean dried = false;
   public static int mobsTypes = 10;
   public int passiveCompletingRule = 0;

   public TileNexusBeacon() {
      this.FINAL_WAVE = 3;
      this.ZONEhollow = 38.0;
      this.ZONEradius = 56.0;
      this.ZONEheight = 20.0;
      this.maxCalmTime = 40;
      this.MAX_HEALTH = 540;
      this.maxLandingYdecrease = 5;
      this.nexusId = 2;
      this.randAngle1 = (this.rand.nextFloat() - 0.5F) * 1.3F;
      this.randAngle2 = (this.rand.nextFloat() - 0.5F) * 0.56F;
      this.randAngle3 = (this.rand.nextFloat() - 0.5F) * 1.3F;
      this.randAngle4 = (this.rand.nextFloat() - 0.5F) * 0.56F;
      this.tentacklesCount = 6 + this.rand.nextInt(5);
      this.hasDespawnFix = true;
   }

   @Override
   public double getCooledSpawnRate(boolean forWaveStart) {
      return forWaveStart ? 5.0E-5F : 5.0E-4F;
   }

   @Override
   public float getPassiveCompleting() {
      int killsNeed = this.ACTIVATOR == 4 ? 6 : 7;
      return this.passiveCompletingRule < killsNeed ? 0.0F : 0.4F - this.WAVE * 0.05F;
   }

   @Override
   public void startNextWave() {
      super.startNextWave();
      this.passiveCompletingRule = 0;
   }

   @Override
   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.setInteger("mobWave1", this.mobWave1);
      compound.setInteger("mobWave2", this.mobWave2);
      compound.setInteger("mobWave3", this.mobWave3);
      compound.setInteger("activator", this.ACTIVATOR);
      compound.setBoolean("dried", this.dried);
      compound.setInteger("passiveCompletingRule", this.passiveCompletingRule);
      return super.writeToNBT(compound);
   }

   @Override
   public void readFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("mobWave1")) {
         this.mobWave1 = compound.getInteger("mobWave1");
      }

      if (compound.hasKey("mobWave2")) {
         this.mobWave2 = compound.getInteger("mobWave2");
      }

      if (compound.hasKey("mobWave3")) {
         this.mobWave3 = compound.getInteger("mobWave3");
      }

      if (compound.hasKey("activator")) {
         this.ACTIVATOR = compound.getInteger("activator");
      }

      if (compound.hasKey("dried")) {
         this.dried = compound.getBoolean("dried");
      }

      if (compound.hasKey("passiveCompletingRule")) {
         this.passiveCompletingRule = compound.getInteger("passiveCompletingRule");
      }

      super.readFromNBT(compound);
   }

   @Override
   public int getMaxGroupsCountOnWave(int wave) {
      return this.ACTIVATOR == 4 ? 16 : 12;
   }

   @Override
   public void update() {
      super.update();
      if (this.world.isRemote) {
         int lt = 20 + this.rand.nextInt(10);
         float scl = 0.04F + this.rand.nextFloat() * 0.05F;
         GUNParticle water = new GUNParticle(
            Seal.splashes[this.rand.nextInt(3)],
            scl,
            0.023F,
            lt,
            150,
            this.world,
            this.pos.getX() + 0.5,
            this.pos.getY() + 0.51,
            this.pos.getZ() + 0.5,
            (float)this.rand.nextGaussian() / 25.0F,
            (float)this.rand.nextGaussian() / 25.0F + (this.invasionStarted ? 0.2F : 0.1F),
            (float)this.rand.nextGaussian() / 25.0F,
            0.65F - this.rand.nextFloat() * 0.2F,
            1.0F - this.rand.nextFloat() * 0.22F,
            1.0F - this.rand.nextFloat() * 0.2F,
            false,
            this.rand.nextInt(360),
            true,
            1.15F
         );
         water.scaleTickAdding = -scl / lt * 0.7F;
         this.world.spawnEntity(water);
      }
   }

   @Override
   public void startInvasion(String team) {
      if (this.ACTIVATOR == 4) {
         this.ZONEhollow = 25.0;
         this.ZONEradius = 40.0;
         this.ZONEheight = 15.0;
      }

      super.startInvasion(team);
      if (this.invasionStarted) {
         this.sendNexusMessageToAllAround("Monsters from the depths are swimming for light");
         this.mobWave1 = this.rand.nextInt(mobsTypes) + 1;
         this.mobWave2 = this.rand.nextInt(mobsTypes) + 1;
         this.mobWave3 = this.rand.nextInt(mobsTypes) + 1;
      }

      TideBeacon.trySendPacketUpdate(this.world, this.getPos(), this, 64);
   }

   @Override
   public void onInvasionEnd(boolean success) {
      if (success) {
         int i = 300;

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
         this.sendNexusMessageToAllAround("The tide is finished!");
         this.sendNexusMessageToAllAround(
            "Time: " + mins + " min " + secsadd + "sec" + ", Waves completed: " + this.WAVE + ", Mobs spawned: " + this.MOBS_SPAWNED
         );
         Item activ = null;
         if (this.ACTIVATOR == 1) {
            activ = ItemsRegister.TIDEACTIVATOR2;
         }

         if (this.ACTIVATOR == 2) {
            activ = ItemsRegister.TIDEACTIVATOR3;
         }

         if (this.ACTIVATOR == 3) {
            activ = ItemsRegister.TIDEACTIVATOR4;
         }

         if (this.ACTIVATOR == 4) {
            activ = ItemsRegister.TIDEACTIVATOR5;
         }

         if (activ != null) {
            this.world
               .spawnEntity(
                  new EntityItem(
                     this.world,
                     this.getPos().getX() + 0.5,
                     this.getPos().getY() + 0.6,
                     this.getPos().getZ() + 0.5,
                     new ItemStack(activ)
                  )
               );
            if (this.ACTIVATOR == 3 && this.rand.nextFloat() < 0.5F) {
               this.world
                  .spawnEntity(
                     new EntityItem(
                        this.world,
                        this.getPos().getX() + 0.5,
                        this.getPos().getY() + 0.6,
                        this.getPos().getZ() + 0.5,
                        new ItemStack(ItemsRegister.TIDALHEART)
                     )
                  );
            }

            if (this.ACTIVATOR == 4) {
               this.world
                  .spawnEntity(
                     new EntityItem(
                        this.world,
                        this.getPos().getX() + 0.5,
                        this.getPos().getY() + 0.6,
                        this.getPos().getZ() + 0.5,
                        new ItemStack(ItemsRegister.TIDALHEART)
                     )
                  );
               if (this.rand.nextFloat() < 0.5F) {
                  this.world
                     .spawnEntity(
                        new EntityItem(
                           this.world,
                           this.getPos().getX() + 0.5,
                           this.getPos().getY() + 0.6,
                           this.getPos().getZ() + 0.5,
                           new ItemStack(ItemsRegister.TIDALHEART)
                        )
                     );
               }
            }
         }
      } else {
         Item activx = null;
         if (this.ACTIVATOR == 1) {
            activx = ItemsRegister.TIDEACTIVATOR1;
         }

         if (this.ACTIVATOR == 2) {
            activx = ItemsRegister.TIDEACTIVATOR2;
         }

         if (this.ACTIVATOR == 3) {
            activx = ItemsRegister.TIDEACTIVATOR3;
         }

         if (this.ACTIVATOR == 4) {
            activx = ItemsRegister.TIDEACTIVATOR4;
         }

         if (activx != null) {
            this.world
               .spawnEntity(
                  new EntityItem(
                     this.world,
                     this.getPos().getX() + 0.5,
                     this.getPos().getY() + 0.6,
                     this.getPos().getZ() + 0.5,
                     new ItemStack(activx)
                  )
               );
         }
      }

      this.ACTIVATOR = 0;
      super.onInvasionEnd(success);
      this.dried = true;
      TideBeacon.trySendPacketUpdate(this.world, this.getPos(), this, 64);
   }

   @Override
   public void landMobGroup(double x, double y, double z) {
      PooledMutableBlockPos blockpos$pooledmutableblockpos = PooledMutableBlockPos.retain();
      int minn = this.getPos().getY() - this.maxLandingYdecrease;

      while (y > minn) {
         if (this.world
               .getBlockState(blockpos$pooledmutableblockpos.setPos(x, y, z))
               .isSideSolid(this.world, blockpos$pooledmutableblockpos, EnumFacing.UP)
            && this.world.isAirBlock(blockpos$pooledmutableblockpos.setPos(x, y + 1.0, z))) {
            blockpos$pooledmutableblockpos.release();
            this.spawnMobGroup(x, y + 1.0, z);
            return;
         }

         y--;
      }

      blockpos$pooledmutableblockpos.release();
      this.spawnMobGroup(x, y, z);
   }

   @Override
   public void spawnMobGroup(double x, double y, double z) {
      List<AbstractMob> list = new ArrayList<>();
      this.addEntityToList(list);
      this.placeMobs(x, y, z, list);
   }

   @Override
   public void onInvaderKilled(AbstractMob entity, DamageSource source) {
      float seriousness = this.getEntitySeriousness(entity);
      this.calmTimeBonus = MathHelper.clamp(this.calmTimeBonus + (int)seriousness, 0, this.maxCalmTime * 2);
      this.waveCompleteRate += seriousness;
      if (seriousness > 1.0F) {
         this.passiveCompletingRule++;
      }
   }

   public void placeMobs(double x, double y, double z, List<AbstractMob> mobs) {
      boolean atleast1mobspawned = false;

      for (AbstractMob mob : mobs) {
         if (!mob.isRiding()) {
            boolean succ = false;

            for (int i = 0; i < 8; i++) {
               BlockPos pos = new BlockPos(x + this.rand.nextGaussian() * 2.0, y + this.rand.nextGaussian() * 2.0, z + this.rand.nextGaussian() * 2.0);
               boolean waterblock = this.world.getBlockState(pos).getBlock() == Blocks.WATER;
               if (waterblock) {
                  mob.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
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
      if (mob instanceof AquaticaMobsPack.Breed) {
         return 3.0F;
      } else if (mob instanceof AquaticaMobsPack.Polipoid) {
         return 8.5F;
      } else if (mob instanceof AquaticaMobsPack.Needletooth) {
         return 4.5F;
      } else if (mob instanceof AquaticaMobsPack.Blisterfish) {
         return 8.5F;
      } else if (mob instanceof AquaticaMobsPack.Wizardfish) {
         return 10.0F;
      } else if (mob instanceof AquaticaMobsPack.Dartfish) {
         return 9.0F;
      } else if (mob instanceof AquaticaMobsPack.Hydromona) {
         return 8.5F;
      } else if (mob instanceof AquaticaMobsPack.Trachymona) {
         return 9.0F;
      } else if (mob instanceof AquaticaMobsPack.Archelon) {
         return 10.0F;
      } else if (mob instanceof AquaticaMobsPack.OceanSpirit) {
         return 10.0F;
      } else {
         return mob instanceof AquaticaMobsPack.DarkMermaid ? 8.5F : 1.0F;
      }
   }

   public void addEntityToList(List<AbstractMob> list) {
      int entity = (this.ACTIVATOR - 1) * 3 + (this.WAVE - 1);
      switch (entity) {
         case 0:
            for (int i = 0; i < 2 + this.rand.nextInt(4); i++) {
               list.add(new AquaticaMobsPack.Breed(this.world));
            }
            break;
         case 1:
            for (int i = 0; i < 1 + this.rand.nextInt(3); i++) {
               list.add(new AquaticaMobsPack.Needletooth(this.world));
            }
            break;
         case 2:
            for (int i = 0; i < 1 + this.rand.nextInt(2); i++) {
               list.add(new AquaticaMobsPack.Polipoid(this.world));
            }
            break;
         case 3:
            for (int i = 0; i < 1 + this.rand.nextInt(2); i++) {
               list.add(new AquaticaMobsPack.Blisterfish(this.world));
            }
            break;
         case 4:
            for (int i = 0; i < 1 + this.rand.nextInt(2); i++) {
               list.add(new AquaticaMobsPack.Hydromona(this.world));
            }
            break;
         case 5:
            for (int i = 0; i < 1 + this.rand.nextInt(1); i++) {
               list.add(new AquaticaMobsPack.Dartfish(this.world));
            }
            break;
         case 6:
            for (int i = 0; i < 1 + this.rand.nextInt(1); i++) {
               list.add(new AquaticaMobsPack.Archelon(this.world));
            }
            break;
         case 7:
            for (int i = 0; i < 1 + this.rand.nextInt(9); i++) {
               list.add(new AquaticaMobsPack.Breed(this.world));
            }
            break;
         case 8:
            list.add(new AquaticaMobsPack.Wizardfish(this.world));
            break;
         case 9:
            for (int i = 0; i < 1 + this.rand.nextInt(3); i++) {
               list.add(new AquaticaMobsPack.Trachymona(this.world));
            }
            break;
         case 10:
            for (int i = 0; i < 1 + this.rand.nextInt(2); i++) {
               list.add(new AquaticaMobsPack.OceanSpirit(this.world));
            }
            break;
         case 11:
            for (int i = 0; i < 1 + this.rand.nextInt(2); i++) {
               list.add(new AquaticaMobsPack.DarkMermaid(this.world));
            }
      }
   }

   public int getEntityWeight(int entity) {
      switch (entity) {
         case 1:
            return 10;
         case 2:
            return 5;
         case 3:
            return 8;
         case 4:
            return 10;
         case 5:
            return 3;
         case 6:
            return 4;
         case 7:
            return 7;
         case 8:
            return 6;
         case 9:
            return 3;
         case 10:
            return 2;
         default:
            return 1;
      }
   }

   public void write(NBTTagCompound compound) {
      compound.setInteger("activator", this.ACTIVATOR);
      compound.setBoolean("dried", this.dried);
   }

   public void read(NBTTagCompound compound) {
      if (compound.hasKey("activator")) {
         this.ACTIVATOR = compound.getInteger("activator");
      }

      if (compound.hasKey("dried")) {
         this.dried = compound.getBoolean("dried");
      }
   }

   public NBTTagCompound getUpdateTag() {
      NBTTagCompound compound = super.getUpdateTag();
      this.write(compound);
      return compound;
   }

   public void handleUpdateTag(NBTTagCompound compound) {
      this.read(compound);
      super.handleUpdateTag(compound);
   }

   public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
      NBTTagCompound compound = packet.getNbtCompound();
      this.read(compound);
   }

   public SPacketUpdateTileEntity getUpdatePacket() {
      NBTTagCompound compound = new NBTTagCompound();
      this.write(compound);
      return new SPacketUpdateTileEntity(this.pos, 1, compound);
   }
}
