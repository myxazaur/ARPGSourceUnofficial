package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.GetMOP;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class AbstractWorm extends AbstractMob {
   public List<Vec3d> poslist = new ArrayList<>();
   public int number;
   public AbstractWorm headEntity;
   public UUID headEntityID;
   public float segmentDistance = 0.6F;

   public AbstractWorm(World world, float sizeWidth, float sizeHeight, boolean isSegment, int number, AbstractWorm headEntity) {
      super(world, sizeWidth, sizeHeight);
      this.setNoGravity(true);
      this.noClip = true;
      this.isSubMob = isSegment;
      this.number = number;
      this.headEntity = headEntity;
      if (headEntity != null) {
         this.headEntityID = headEntity.getUniqueID();
      }
   }

   public abstract int getSegmentsCount();

   public AbstractWorm(World world, float sizeWidth, float sizeHeight) {
      this(world, sizeWidth, sizeHeight, false, 0, null);
   }

   public boolean hasNoGravity() {
      return true;
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound compound) {
      if (this.headEntity != null) {
         compound.setUniqueId("ownerId", this.headEntity.getUniqueID());
      }

      compound.setInteger("segmentnumber", this.number);
      compound.setBoolean("issegment", this.isSubMob);
      super.writeEntityToNBT(compound);
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("ownerIdMost") && compound.hasKey("ownerIdLeast")) {
         this.headEntityID = compound.getUniqueId("ownerId");
      }

      if (compound.hasKey("segmentnumber")) {
         this.number = compound.getInteger("segmentnumber");
      }

      if (compound.hasKey("issegment")) {
         this.isSubMob = compound.getBoolean("issegment");
      }

      super.readEntityFromNBT(compound);
   }

   @Nullable
   public AbstractWorm getOwnerIfSegment() {
      return this.isSubMob ? this.headEntity : null;
   }

   public Vec3d getSegmCoord(int number) {
      return number < this.poslist.size() ? this.poslist.get(number) : this.getPositionVector();
   }

   public float getSegmentMotionPower(Vec3d vec) {
      return 1.0F;
   }

   public void fall(float distance, float damageMultiplier) {
   }

   @Override
   public boolean attackEntityFrom(DamageSource source, float amount) {
      if (source == DamageSource.CRAMMING) {
         return false;
      } else if (source == DamageSource.IN_WALL) {
         return false;
      } else if (!this.isSubMob) {
         return this.isEntityInvulnerable(source) ? false : super.attackEntityFrom(source, amount);
      } else if (this.headEntity != null) {
         this.world.setEntityState(this, (byte)2);
         boolean att = this.headEntity.attackEntityFrom(source, amount);
         if (this.headEntity.getHealth() <= 0.0F) {
            super.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
         }

         return att;
      } else {
         return !this.isEntityInvulnerable(source);
      }
   }

   @Override
   public void handleStatusUpdate(byte id) {
      if (id == 8) {
         this.isSubMob = false;
      }

      if (id == 9) {
         this.isSubMob = true;
      }

      super.handleStatusUpdate(id);
   }

   protected boolean canDespawn() {
      return this.isSubMob ? false : super.canDespawn();
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted < 3 || this.ticksExisted % 20 == 0) {
         this.world.setEntityState(this, (byte)(this.isSubMob ? 9 : 8));
      }

      if (!this.isSubMob) {
         Vec3d pitchYaw = GetMOP.Vec3dToPitchYaw(new Vec3d(-this.motionX, -this.motionY, -this.motionZ));
         this.rotationPitch = (float)MathHelper.wrapDegrees(pitchYaw.x);
         this.rotationYaw = (float)MathHelper.wrapDegrees(pitchYaw.y);
         if (this.poslist != null) {
            Vec3d vec = new Vec3d(this.posX, this.posY, this.posZ);
            if (this.poslist.size() > 0) {
               if (vec != null) {
                  if (this.poslist.get(0) == null) {
                     this.poslist.set(0, vec);
                  } else if (vec.distanceTo(this.poslist.get(0)) > this.segmentDistance) {
                     this.poslist.add(0, vec);
                  }

                  if (this.poslist.size() > this.getSegmentsCount() + 1) {
                     this.poslist.remove(this.getSegmentsCount() + 1);
                  }
               }
            } else {
               this.poslist.add(vec);
            }
         }
      } else {
         if (!this.world.isRemote) {
            if (this.headEntity != null) {
               if (this.headEntity.poslist.size() > this.number + 1) {
                  Vec3d vec1 = this.headEntity.poslist.get(this.number);
                  Vec3d vec2 = this.headEntity.poslist.get(this.number + 1);
                  Vec3d vec = new Vec3d(
                     (vec1.x + vec2.x) / 2.0,
                     (vec1.y + vec2.y) / 2.0,
                     (vec1.z + vec2.z) / 2.0
                  );
                  if (vec != null) {
                     this.motionX /= 2.0;
                     this.motionY /= 2.0;
                     this.motionZ /= 2.0;
                     double power = this.getSegmentMotionPower(vec);
                     double prunex = (vec.x - this.posX) * power;
                     double pruney = (vec.y - this.posY) * power;
                     double prunez = (vec.z - this.posZ) * power;
                     this.motionX += prunex;
                     this.motionY += pruney;
                     this.motionZ += prunez;
                  }
               }

               if (!this.headEntity.isEntityAlive()) {
                  this.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
               }

               if (this.getHealth() != this.headEntity.getHealth()) {
                  this.setHealth(this.headEntity.getHealth());
               }
            } else {
               if (this.headEntityID != null) {
                  Entity entit = getEntityByUUID(this.world, this.headEntityID);
                  if (entit instanceof AbstractWorm) {
                     this.headEntity = (AbstractWorm)entit;
                  }
               }

               if (this.ticksExisted > 20 && this.headEntity == null) {
                  this.setDead();
               }
            }
         }

         if (this.headEntity != null) {
            if (this.headEntity.poslist.size() > this.number) {
               if (this.number > 0) {
                  Vec3d pitchYaw = GetMOP.Vec3dToPitchYaw(this.getPositionVector().subtract(this.headEntity.poslist.get(this.number - 1)));
                  this.rotationPitch = (float)MathHelper.wrapDegrees(pitchYaw.x);
                  this.rotationYaw = (float)MathHelper.wrapDegrees(pitchYaw.y);
               }

               if (this.number == 0) {
                  Vec3d pitchYaw = GetMOP.Vec3dToPitchYaw(this.getPositionVector().subtract(this.headEntity.getPositionVector()));
                  this.rotationPitch = (float)MathHelper.wrapDegrees(pitchYaw.x);
                  this.rotationYaw = (float)MathHelper.wrapDegrees(pitchYaw.y);
               }
            }

            if (this.headEntity.getAttackTarget() != null) {
               this.setAttackTarget(this.headEntity.getAttackTarget());
            }
         }
      }
   }
}
