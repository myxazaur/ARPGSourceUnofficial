package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.elements.ItemRocket;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.renders.IRenderOptions;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityLaunchedRocket extends EntityThrowable implements IRenderOptions, IEntitySynchronize {
   public final ItemStack weaponstack;
   public ItemRocket rocket = null;
   public boolean grenade = false;
   public float gravity = 0.0F;
   public boolean gravityChanged = false;
   public float damage = 6.0F;
   public float knockback = 1.0F;
   public float unstabilize = 0.6F;
   public int explodeTicks = 0;

   public EntityLaunchedRocket(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.ROCKETLAUNCHER);
   }

   public EntityLaunchedRocket(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.ROCKETLAUNCHER);
   }

   public EntityLaunchedRocket(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.ROCKETLAUNCHER);
   }

   public EntityLaunchedRocket(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.rocket = ItemRocket.getItemRocketFromString(NBTHelper.GetNBTstring(itemstack, "rocket"));
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      super.writeEntityToNBT(compound);
      compound.setFloat("damage", this.damage);
      compound.setFloat("knockback", this.knockback);
   }

   public void readEntityFromNBT(NBTTagCompound compound) {
      super.readEntityFromNBT(compound);
      if (compound.hasKey("damage")) {
         this.damage = compound.getFloat("damage");
      }

      if (compound.hasKey("knockback")) {
         this.knockback = compound.getFloat("knockback");
      }
   }

   @Override
   public void onClient(double... args) {
      this.gravity = (float)args[0];
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * this.unstabilize;
      this.motionZ = this.motionZ + entityThrower.motionZ * this.unstabilize;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * this.unstabilize;
      }
   }

   protected float getGravityVelocity() {
      return this.grenade ? 0.05F : this.gravity;
   }

   public void onUpdate() {
      if ((this.ticksExisted <= 3 || this.ticksExisted % 10 == 0) && !this.world.isRemote) {
         if (this.grenade) {
            this.world.setEntityState(this, (byte)4);
         }

         if (this.rocket != null) {
            this.world.setEntityState(this, (byte)(-this.rocket.id));
         }
      }

      if (this.gravityChanged && !this.world.isRemote) {
         IEntitySynchronize.sendSynchronize(this, 64.0, this.gravity);
         this.gravityChanged = false;
      }

      super.onUpdate();
      if (this.rocket != null) {
         this.rocket.onProjectileUpdate(this, this.weaponstack);
      }

      if (this.ticksExisted > 50 && !this.world.isRemote && this.explodeTicks <= 0) {
         if (this.grenade) {
            this.expl(null);
         } else {
            this.setDead();
         }
      }

      if (this.explodeTicks > 0) {
         this.explodeTicks++;
      }

      if (this.explodeTicks > 10) {
         this.expl(null);
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 4) {
         this.grenade = true;
      }

      if (id < 0) {
         this.rocket = ItemRocket.getItemRocketFromId(-id);
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (this.grenade) {
         if (result != null && result.sideHit != null) {
            float frictionMultipl = 0.3F;
            if (result.sideHit == EnumFacing.UP || result.sideHit == EnumFacing.DOWN) {
               this.motionY = -this.motionY * frictionMultipl;
               this.motionX *= frictionMultipl;
               this.motionZ *= frictionMultipl;
               this.explodeTicks++;
            }

            if (result.sideHit == EnumFacing.NORTH || result.sideHit == EnumFacing.SOUTH) {
               this.motionZ = -this.motionZ * frictionMultipl;
               this.motionX *= frictionMultipl;
               this.motionY *= frictionMultipl;
               this.explodeTicks++;
            }

            if (result.sideHit == EnumFacing.EAST || result.sideHit == EnumFacing.WEST) {
               this.motionX = -this.motionX * frictionMultipl;
               this.motionZ *= frictionMultipl;
               this.motionY *= frictionMultipl;
               this.explodeTicks++;
            }
         }
      } else if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit)) {
            this.expl(null);
         }
      } else if (this.world
            .getBlockState(result.getBlockPos())
            .getBlock()
            .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
         != null) {
         this.expl(result);
      }
   }

   public void expl(RayTraceResult result) {
      double x;
      double y;
      double z;
      if (result != null && result.hitVec != null) {
         x = result.hitVec.x;
         y = result.hitVec.y;
         z = result.hitVec.z;
      } else {
         x = this.posX;
         y = this.posY;
         z = this.posZ;
      }

      if (this.rocket != null
         && this.rocket.explode(this.world, this.thrower, x, y, z, result, this, this.weaponstack, this.damage, this.knockback)) {
         this.setDead();
      }
   }

   @Override
   public void doOptions() {
      if (this.rocket != null) {
         GlStateManager.color(this.rocket.colorR, this.rocket.colorG, this.rocket.colorB);
      }
   }

   @Override
   public void undoOptions() {
      GlStateManager.color(1.0F, 1.0F, 1.0F);
   }
}
