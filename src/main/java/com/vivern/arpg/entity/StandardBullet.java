package com.vivern.arpg.entity;

import com.vivern.arpg.network.IFixedTrackerEntity;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class StandardBullet extends Entity implements IProjectile, IFixedTrackerEntity {
   protected EntityLivingBase thrower;
   private String throwerName;
   public Entity ignoreEntity;
   private int ignoreTime;
   public float airFriction = 0.99F;
   public float waterFriction = 0.99F;
   public float waterBaublesCount = 4.0F;
   private static final DataParameter<Float> RED = EntityDataManager.createKey(StandardBullet.class, DataSerializers.FLOAT);
   private static final DataParameter<Float> GREEN = EntityDataManager.createKey(StandardBullet.class, DataSerializers.FLOAT);
   private static final DataParameter<Float> BLUE = EntityDataManager.createKey(StandardBullet.class, DataSerializers.FLOAT);

   public StandardBullet(World worldIn) {
      super(worldIn);
      this.setSize(0.25F, 0.25F);
   }

   public StandardBullet(World worldIn, double x, double y, double z) {
      this(worldIn);
      this.setPosition(x, y, z);
   }

   public StandardBullet(World worldIn, EntityLivingBase throwerIn) {
      this(worldIn, throwerIn.posX, throwerIn.posY + throwerIn.getEyeHeight() - 0.1F, throwerIn.posZ);
      this.thrower = throwerIn;
   }

   public boolean canCollideWithBlocks() {
      return false;
   }

   @Override
   public boolean canFix() {
      return this.canCollideWithBlocks();
   }

   protected void entityInit() {
      this.dataManager.register(RED, 1.0F);
      this.dataManager.register(GREEN, 1.0F);
      this.dataManager.register(BLUE, 1.0F);
   }

   public void setColor(float r, float g, float b) {
      this.dataManager.set(RED, r);
      this.dataManager.set(GREEN, g);
      this.dataManager.set(BLUE, b);
   }

   public float getRED() {
      return (Float)this.dataManager.get(RED);
   }

   public float getGREEN() {
      return (Float)this.dataManager.get(GREEN);
   }

   public float getBLUE() {
      return (Float)this.dataManager.get(BLUE);
   }

   @SideOnly(Side.CLIENT)
   public boolean isInRangeToRenderDist(double distance) {
      double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0;
      if (Double.isNaN(d0)) {
         d0 = 4.0;
      }

      d0 *= 64.0;
      return distance < d0 * d0;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX;
      this.motionZ = this.motionZ + entityThrower.motionZ;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY;
      }
   }

   public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
      float f = MathHelper.sqrt(x * x + y * y + z * z);
      x /= f;
      y /= f;
      z /= f;
      x += this.rand.nextGaussian() * 0.0075F * inaccuracy;
      y += this.rand.nextGaussian() * 0.0075F * inaccuracy;
      z += this.rand.nextGaussian() * 0.0075F * inaccuracy;
      x *= velocity;
      y *= velocity;
      z *= velocity;
      this.motionX = x;
      this.motionY = y;
      this.motionZ = z;
      float f1 = MathHelper.sqrt(x * x + z * z);
      this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180.0 / Math.PI));
      this.rotationPitch = (float)(MathHelper.atan2(y, f1) * (180.0 / Math.PI));
      this.prevRotationYaw = this.rotationYaw;
      this.prevRotationPitch = this.rotationPitch;
   }

   @SideOnly(Side.CLIENT)
   public void setVelocity(double x, double y, double z) {
      this.motionX = x;
      this.motionY = y;
      this.motionZ = z;
      if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
         float f = MathHelper.sqrt(x * x + z * z);
         this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180.0 / Math.PI));
         this.rotationPitch = (float)(MathHelper.atan2(y, f) * (180.0 / Math.PI));
         this.prevRotationYaw = this.rotationYaw;
         this.prevRotationPitch = this.rotationPitch;
      }
   }

   public void onUpdate() {
      this.lastTickPosX = this.posX;
      this.lastTickPosY = this.posY;
      this.lastTickPosZ = this.posZ;
      super.onUpdate();
      Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
      Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
      RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d, vec3d1);
      if (raytraceresult != null) {
         vec3d1 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
      }

      Entity entity = null;
      List<Entity> list = this.world
         .getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0));
      double d0 = Double.MAX_VALUE;
      RayTraceResult savedResult = null;

      for (int i = 0; i < list.size(); i++) {
         Entity entity1 = list.get(i);
         if (entity1.canBeCollidedWith() && entity1 != this.ignoreEntity && entity1 != this.getThrower()) {
            AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(this.width + entity1.getCollisionBorderSize());
            RayTraceResult raytraceresult1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);
            if (raytraceresult1 != null) {
               double d1 = vec3d.squareDistanceTo(raytraceresult1.hitVec);
               if (d1 < d0) {
                  entity = entity1;
                  savedResult = raytraceresult1;
                  d0 = d1;
               }
            }
         }
      }

      if (entity != null) {
         raytraceresult = new RayTraceResult(entity);
         if (savedResult != null) {
            raytraceresult.hitVec = savedResult.hitVec;
            raytraceresult.sideHit = savedResult.sideHit;
            raytraceresult.subHit = savedResult.subHit;
         }
      }

      if (raytraceresult != null) {
         if (raytraceresult.typeOfHit == Type.BLOCK
            && this.world.getBlockState(raytraceresult.getBlockPos()).getBlock() == Blocks.PORTAL) {
            this.setPortal(raytraceresult.getBlockPos());
         } else if (!ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
            this.onImpact(raytraceresult);
         }
      }

      if (this.canCollideWithBlocks()) {
         this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0, this.posZ);
         this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
      } else {
         this.posX = this.posX + this.motionX;
         this.posY = this.posY + this.motionY;
         this.posZ = this.posZ + this.motionZ;
         this.setPosition(this.posX, this.posY, this.posZ);
      }

      float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
      this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180.0 / Math.PI));
      this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f) * (180.0 / Math.PI));

      while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
         this.prevRotationPitch -= 360.0F;
      }

      while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
         this.prevRotationPitch += 360.0F;
      }

      while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
         this.prevRotationYaw -= 360.0F;
      }

      while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
         this.prevRotationYaw += 360.0F;
      }

      float rotaionTowardSpeed = this.getRotationTowardMovementSpeed();
      this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * rotaionTowardSpeed;
      this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * rotaionTowardSpeed;
      float f1 = this.airFriction;
      float f2 = this.getGravityVelocity();
      if (this.isInWater()) {
         int baublesMax = (int)this.waterBaublesCount;
         float baubleChance = this.waterBaublesCount - baublesMax;
         if (baublesMax > 0) {
            for (int j = 0; j < baublesMax; j++) {
               float f3 = 0.25F;
               this.world
                  .spawnParticle(
                     EnumParticleTypes.WATER_BUBBLE,
                     this.posX - this.motionX * 0.25,
                     this.posY - this.motionY * 0.25,
                     this.posZ - this.motionZ * 0.25,
                     this.motionX,
                     this.motionY,
                     this.motionZ,
                     new int[0]
                  );
            }
         }

         if (this.rand.nextFloat() < baubleChance) {
            float f3 = 0.25F;
            this.world
               .spawnParticle(
                  EnumParticleTypes.WATER_BUBBLE,
                  this.posX - this.motionX * 0.25,
                  this.posY - this.motionY * 0.25,
                  this.posZ - this.motionZ * 0.25,
                  this.motionX,
                  this.motionY,
                  this.motionZ,
                  new int[0]
               );
         }

         f1 = this.waterFriction;
      }

      this.motionX *= f1;
      this.motionY *= f1;
      this.motionZ *= f1;
      if (!this.hasNoGravity()) {
         this.motionY -= f2;
      }
   }

   public float getRotationTowardMovementSpeed() {
      return 0.2F;
   }

   public float getGravityVelocity() {
      return 0.03F;
   }

   public void onImpact(RayTraceResult result) {
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      if ((this.throwerName == null || this.throwerName.isEmpty()) && this.thrower instanceof EntityPlayer) {
         this.throwerName = this.thrower.getName();
      }

      compound.setString("ownerName", this.throwerName == null ? "" : this.throwerName);
   }

   public void readEntityFromNBT(NBTTagCompound compound) {
      this.thrower = null;
      this.throwerName = compound.getString("ownerName");
      if (this.throwerName != null && this.throwerName.isEmpty()) {
         this.throwerName = null;
      }

      this.thrower = this.getThrower();
   }

   @Nullable
   public EntityLivingBase getThrower() {
      if (this.thrower == null && this.throwerName != null && !this.throwerName.isEmpty()) {
         this.thrower = this.world.getPlayerEntityByName(this.throwerName);
         if (this.thrower == null && this.world instanceof WorldServer) {
            try {
               Entity entity = ((WorldServer)this.world).getEntityFromUuid(UUID.fromString(this.throwerName));
               if (entity instanceof EntityLivingBase) {
                  this.thrower = (EntityLivingBase)entity;
               }
            } catch (Throwable var21) {
               this.thrower = null;
            }
         }
      }

      return this.thrower;
   }
}
