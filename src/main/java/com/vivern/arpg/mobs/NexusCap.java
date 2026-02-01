package com.vivern.arpg.mobs;

import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.network.PacketNexusInfoToClients;
import com.vivern.arpg.tileentity.TileNexus;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NexusCap extends AbstractMob {
   public TileNexus nexus = null;
   public BlockPos nexusPos;
   public int winTime = 0;

   public NexusCap(World world) {
      super(world, 2.4F, 2.4F);
      this.defaultteam = "";
      this.setattributes(20.0, 32.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
      this.setNoGravity(true);
      this.noClip = true;
      this.soul = null;
   }

   public NexusCap(World world, TileNexus nexus, float health) {
      super(world, 2.4F, 2.4F);
      this.nexus = nexus;
      this.defaultteam = "";
      this.setattributes(health, 32.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
      this.setNoGravity(true);
      this.noClip = true;
      this.soul = null;
   }

   public NexusCap(World world, TileNexus nexus, float health, float width, float height) {
      super(world, width, height);
      this.nexus = nexus;
      this.defaultteam = "";
      this.setattributes(health, 32.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
      this.setNoGravity(true);
      this.noClip = true;
      this.soul = null;
   }

   protected boolean canDespawn() {
      return false;
   }

   public boolean canBePushed() {
      return false;
   }

   public boolean isPushedByWater() {
      return false;
   }

   public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio) {
   }

   public void removeTrackingPlayer(EntityPlayerMP player) {
      super.removeTrackingPlayer(player);
      PacketNexusInfoToClients packet = new PacketNexusInfoToClients();
      packet.writeargs(0.0F, 0.0F, (short)0, (short)0, false);
      PacketHandler.sendTo(packet, player);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.setInteger("nexusPosX", this.nexus.getPos().getX());
      compound.setInteger("nexusPosY", this.nexus.getPos().getY());
      compound.setInteger("nexusPosZ", this.nexus.getPos().getZ());
      compound.setInteger("winTime", this.winTime);
      return super.writeToNBT(compound);
   }

   public void readFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("nexusPosX") && compound.hasKey("nexusPosY") && compound.hasKey("nexusPosZ")) {
         this.nexusPos = new BlockPos(compound.getInteger("nexusPosX"), compound.getInteger("nexusPosY"), compound.getInteger("nexusPosZ"));
      }

      if (compound.hasKey("winTime")) {
         this.winTime = compound.getInteger("winTime");
      }

      super.readFromNBT(compound);
   }

   @Override
   public boolean attackEntityAsMob(Entity entityIn) {
      return false;
   }

   @Override
   public void onDeath(DamageSource cause) {
      if (this.nexus == null || this.nexus.onKillNexusCap(this, cause)) {
         super.onDeath(cause);
      }
   }

   @Override
   protected void onDeathUpdate() {
      this.deathTime++;
      if (this.deathTime == 80) {
         this.setDead();
      }
   }

   @Override
   public boolean attackEntityFrom(DamageSource source, float amount) {
      if (source == DamageSource.IN_WALL) {
         return false;
      } else if (source == DamageSource.FALL) {
         return false;
      } else {
         boolean a = super.attackEntityFrom(source, amount);
         if (this.nexus != null) {
            this.nexus.onAttackNexus(this, source, amount);
         } else if (!this.world.isRemote) {
            this.setDead();
         }

         return a;
      }
   }

   public void heal(float healAmount) {
      super.heal(healAmount);
      if (this.nexus != null) {
         this.nexus.onHealNexus(this, healAmount);
      }
   }

   protected float getJumpUpwardsMotion() {
      return 0.0F;
   }

   public boolean canBreatheUnderwater() {
      return true;
   }

   @Override
   public void onUpdate() {
      this.motionX = 0.0;
      this.motionY = 0.0;
      this.motionZ = 0.0;
      super.onUpdate();
      if (this.nexusPos != null) {
         if (this.nexus == null) {
            TileEntity tile = this.world.getTileEntity(this.nexusPos);
            if (tile != null && tile instanceof TileNexus) {
               this.nexus = (TileNexus)tile;
            } else if (!this.world.isRemote) {
               this.setDead();
            }
         } else if (!this.world.isRemote && this.ticksExisted % 10 == 0) {
            TileEntity tile = this.world.getTileEntity(this.nexusPos);
            if (tile != this.nexus) {
               this.setDead();
            }
         }
      }

      if (this.winTime > 0) {
         this.winTime++;
         if (this.winTime > 100) {
            this.setDead();
         }
      }
   }
}
