package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.main.Shards;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityShard extends Entity {
   public ShardType shardType;
   public int ticks = 0;
   public float energy = 0.0F;
   public float rotationX;
   public float rotationY;
   public float rotationZ;
   public float prevrotationX;
   public float prevrotationY;
   public float prevrotationZ;
   public float rotationXspeed;
   public float rotationYspeed;
   public float rotationZspeed;
   static float SIZE = 0.1875F;

   public EntityShard(World world) {
      super(world);
      this.shardType = null;
      this.standartInit();
   }

   public EntityShard(World world, ShardType shardType) {
      super(world);
      this.shardType = shardType;
      this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
      this.standartInit();
   }

   public EntityShard(World world, ShardType shardType, Entity dropper) {
      super(world);
      this.setPosition(dropper.posX, dropper.posY + dropper.height / 2.0F, dropper.posZ);
      this.shardType = shardType;
      this.motionX = this.rand.nextGaussian() / 13.0 + dropper.motionX / 2.0;
      this.motionY = this.rand.nextGaussian() / 17.0 + 0.2 + dropper.motionY / 2.0;
      this.motionZ = this.rand.nextGaussian() / 13.0 + dropper.motionZ / 2.0;
      this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
      this.velocityChanged = true;
      this.standartInit();
   }

   public void setStartMotion() {
      this.motionX = this.rand.nextGaussian() / 13.0;
      this.motionY = this.rand.nextGaussian() / 17.0 + 0.26;
      this.motionZ = this.rand.nextGaussian() / 13.0;
      this.velocityChanged = true;
   }

   public void standartInit() {
      this.noClip = false;
      this.setSize(SIZE, SIZE);
      this.rotationXspeed = (float)(this.rand.nextGaussian() * 2.0);
      this.rotationYspeed = (float)(this.rand.nextGaussian() * 2.0);
      this.rotationZspeed = (float)(this.rand.nextGaussian() * 2.0);
      this.ticks = this.rand.nextInt(60);
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   protected void entityInit() {
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == -25) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.shard_drop,
               SoundCategory.PLAYERS,
               0.9F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );
         if (this.world.isRemote) {
            this.energy = 0.5F + this.rand.nextFloat() / 2.0F;
         }
      }

      if (id == 0) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.shard_pickup,
               SoundCategory.PLAYERS,
               0.6F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );
      }

      if (id > 0) {
         this.shardType = ShardType.byId(id);
      }
   }

   public void readEntityFromNBT(NBTTagCompound nbt) {
      if (nbt.hasKey("ticks")) {
         this.ticks = nbt.getInteger("ticks");
      } else {
         this.ticks = 0;
      }

      if (nbt.hasKey("type")) {
         this.shardType = ShardType.byName(nbt.getString("type"));
      } else {
         this.shardType = ShardType.FIRE;
      }
   }

   public void writeEntityToNBT(NBTTagCompound nbt) {
      nbt.setInteger("ticks", this.ticks);
      nbt.setString("type", this.shardType.getName());
   }

   public void onUpdate() {
      super.onUpdate();
      this.ticks++;
      this.prevrotationX = this.rotationX;
      this.prevrotationY = this.rotationY;
      this.prevrotationZ = this.rotationZ;
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      if (!this.onGround) {
         this.rotationX = this.rotationX + this.rotationXspeed;
         this.rotationY = this.rotationY + this.rotationYspeed;
         this.rotationZ = this.rotationZ + this.rotationZspeed;
      } else {
         if ((int)this.rotationZ % 180 != 0) {
            this.rotationZ = ((int)this.rotationZ / 180 + 1) * 180;
         }

         if ((int)this.rotationX % 90 != 0) {
            this.rotationX = ((int)this.rotationX / 90 + 1) * 90;
         }

         this.rotationYspeed = (float)(this.rotationYspeed * 0.9);
         this.rotationY = this.rotationY + this.rotationYspeed;
      }

      if (this.ticksExisted == 2 && !this.world.isRemote) {
         this.world.setEntityState(this, (byte)-25);
         this.setStartMotion();
      }

      if (!this.hasNoGravity()) {
         this.motionY -= 0.03F;
      }

      if (this.world.getBlockState(new BlockPos(this)).getMaterial() == Material.LAVA) {
         this.motionY = 0.2F;
         this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F;
         this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F;
      }

      this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0, this.posZ);
      double d0 = 8.0;
      this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
      float f = 0.98F;
      if (this.onGround) {
         BlockPos underPos = new BlockPos(
            MathHelper.floor(this.posX),
            MathHelper.floor(this.getEntityBoundingBox().minY) - 1,
            MathHelper.floor(this.posZ)
         );
         IBlockState underState = this.world.getBlockState(underPos);
         f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.98F;
      }

      this.motionX *= f;
      this.motionY *= 0.98F;
      this.motionZ *= f;
      if (this.onGround) {
         this.motionY *= -0.9F;
      }

      if (this.shardType != null && this.ticksExisted % 2 == 0) {
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)this.shardType.id);
         }

         double damageRadius = this.shardType.reachDistance;
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         List<EntityPlayer> list = this.world.getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);
         double max = Double.MAX_VALUE;
         EntityPlayer targ = null;
         if (!list.isEmpty()) {
            for (EntityPlayer player : list) {
               if (EntitySelectors.NOT_SPECTATING.apply(player)) {
                  double dist = player.getDistanceSq(this);
                  if (dist < max) {
                     max = dist;
                     targ = player;
                  }
               }
            }

            if (targ != null) {
               SuperKnockback.applyMove(this, -0.15F, targ.posX, targ.posY, targ.posZ);
               this.velocityChanged = true;
            }
         }
      }

      if (!this.world.isRemote) {
         if (this.ticks > 2300) {
            this.setDead();
         }

         if (this.ticksExisted == 30) {
            GetMOP.BlockTraceResult result = GetMOP.blockTrace(
               this.world, this.getPosition(), EnumFacing.DOWN, (int)(this.posY + 2.0), GetMOP.SOLID_BLOCKS
            );
            if (result == null) {
               this.setNoGravity(true);
            }
         }
      }
   }

   public void onCollideWithPlayer(EntityPlayer entityIn) {
      if (this.shardType != null && this.ticksExisted > 15 && !entityIn.world.isRemote) {
         this.world.setEntityState(this, (byte)0);
         Shards.addEnergyToPlayer(entityIn, this.shardType, this.energy);
         this.setDead();
      }
   }

   public boolean canBeAttackedWithItem() {
      return false;
   }

   public boolean handleWaterMovement() {
      return this.world.handleMaterialAcceleration(this.getEntityBoundingBox(), Material.WATER, this);
   }

   public float energyToScale(float basescale) {
      return (this.energy - 0.75F) * basescale;
   }
}
