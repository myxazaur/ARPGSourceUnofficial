//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.elements.ChainMace;
import com.Vivern.Arpg.main.ItemsRegister;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityChainMace extends Entity implements IEntitySynchronize, IProjectile {
   public final ItemStack weaponstack;
   public ChainMace mace;
   public EntityPlayer thrower;
   public String throwerName;
   public float gravity = 0.0F;
   public int returnTime = 0;
   public byte itemSendId = 0;
   public float collisionBorderSize = 0.0F;
   public float toHorizontal = 0.0F;
   public float toVertical = 0.0F;
   public boolean spinned = false;
   public boolean spinDirection = false;
   public int soundCooldown = 0;
   public int var1 = 0;

   public EntityChainMace(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.CHAINMACEIRON);
      this.ignoreFrustumCheck = true;
   }

   public EntityChainMace(World world, EntityPlayer thrower) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.CHAINMACEIRON);
      this.thrower = thrower;
      this.ignoreFrustumCheck = true;
   }

   public EntityChainMace(World world, double x, double y, double z) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.CHAINMACEIRON);
      this.setPosition(x, y, z);
      this.ignoreFrustumCheck = true;
   }

   public EntityChainMace(World world, EntityPlayer thrower, ItemStack itemstack, ChainMace mace) {
      super(world);
      this.weaponstack = itemstack;
      this.thrower = thrower;
      this.mace = mace;
      this.ignoreFrustumCheck = true;
   }

   public void setSize(float width, float height) {
      if (width != this.width || height != this.height) {
         float f = this.width;
         this.width = width;
         this.height = height;
         if (this.width < f) {
            double d0 = width / 2.0;
            this.setEntityBoundingBox(
               new AxisAlignedBB(
                  this.posX - d0,
                  this.posY,
                  this.posZ - d0,
                  this.posX + d0,
                  this.posY + this.height,
                  this.posZ + d0
               )
            );
            return;
         }

         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
         this.setEntityBoundingBox(
            new AxisAlignedBB(
               axisalignedbb.minX,
               axisalignedbb.minY,
               axisalignedbb.minZ,
               axisalignedbb.minX + this.width,
               axisalignedbb.minY + this.height,
               axisalignedbb.minZ + this.width
            )
         );
         if (this.width > f && !this.firstUpdate && !this.world.isRemote) {
            this.move(MoverType.SELF, f - this.width, 0.0, f - this.width);
         }
      }
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX;
      this.motionZ = this.motionZ + entityThrower.motionZ;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.75;
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

   protected float getGravityVelocity() {
      return this.gravity;
   }

   @Nullable
   public AxisAlignedBB getCollisionBox(Entity entityIn) {
      return null;
   }

   @Nullable
   public AxisAlignedBB getCollisionBoundingBox() {
      return !this.noClip && this.ticksExisted >= 3 ? this.getEntityBoundingBox() : null;
   }

   public float getCollisionBorderSize() {
      return this.collisionBorderSize;
   }

   public Vec3d getChainBindingPoint(float partialTicks, boolean forThirdPerson) {
      double playerPosX = this.thrower.prevPosX + (this.thrower.posX - this.thrower.prevPosX) * partialTicks;
      double playerPosY = this.thrower.prevPosY + (this.thrower.posY - this.thrower.prevPosY) * partialTicks;
      double playerPosZ = this.thrower.prevPosZ + (this.thrower.posZ - this.thrower.prevPosZ) * partialTicks;
      float pitch = this.thrower.prevRotationPitch + (this.thrower.rotationPitch - this.thrower.prevRotationPitch) * partialTicks;
      float yaw = this.thrower.prevRotationYawHead + (this.thrower.rotationYawHead - this.thrower.prevRotationYawHead) * partialTicks;
      float f = MathHelper.cos(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f1 = MathHelper.sin(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f2 = -MathHelper.cos(-pitch * (float) (Math.PI / 180.0));
      float f3 = MathHelper.sin(-pitch * (float) (Math.PI / 180.0));
      Vec3d look = new Vec3d(f1 * f2, f3, f * f2);
      if (forThirdPerson) {
         yaw = this.thrower.prevRenderYawOffset + (this.thrower.renderYawOffset - this.thrower.prevRenderYawOffset) * partialTicks;
      }

      if (this.thrower.getPrimaryHand() == EnumHandSide.RIGHT) {
         yaw += 90.0F;
      } else {
         yaw -= 90.0F;
      }

      float ff = MathHelper.cos(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      float ff1 = MathHelper.sin(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      Vec3d offset = new Vec3d(-ff1, 0.0, -ff);
      return !this.spinned
         ? new Vec3d(
            look.x * 0.6 + offset.x * 0.35 + playerPosX,
            look.y * 0.6 + playerPosY + (this.thrower.isSneaking() ? 0.9F : 1.25F),
            look.z * 0.6 + offset.z * 0.35 + playerPosZ
         )
         : new Vec3d(
            look.x * 0.25 + offset.x * 0.4 + playerPosX,
            look.y * 0.25 + playerPosY + 2.0,
            look.z * 0.25 + offset.z * 0.4 + playerPosZ
         );
   }

   public Vec3d getRotatPitchYawAndDist(float partialTicks, boolean forThirdPerson) {
      Vec3d chainBind = this.getChainBindingPoint(partialTicks, forThirdPerson);
      float mx = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - chainBind.x);
      float mz = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - chainBind.z);
      float my = (float)(chainBind.y - (this.prevPosY + (this.posY - this.prevPosY) * partialTicks + this.height / 2.0F));
      float moti_zx = (float)Math.sqrt(mx * mx + mz * mz);
      float moti_zy = (float)Math.sqrt(my * my + mz * mz);
      float cosangle_Yaw = mz / moti_zx;
      float cosangle_Pitch = mz / moti_zy;
      float angle_Yaw = (float)Math.toDegrees(Math.acos(cosangle_Yaw));
      float angle_Pitch = (float)Math.toDegrees(Math.acos(cosangle_Pitch));
      float tanangle = my / moti_zx;
      float angle = (float)Math.toDegrees(Math.atan(tanangle));
      if (mx > 0.0F) {
         angle_Yaw = -angle_Yaw;
      }

      if (my < 0.0F) {
         angle_Pitch = -angle_Pitch;
      }

      float my2 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks + this.height / 2.0F - chainBind.y);
      double distance = MathHelper.sqrt(mx * mx + my2 * my2 + mz * mz);
      return new Vec3d(-angle, angle_Yaw + 180.0F, distance);
   }

   public void onUpdate() {
      this.lastTickPosX = this.posX;
      this.lastTickPosY = this.posY;
      this.lastTickPosZ = this.posZ;
      super.onUpdate();
      if (this.soundCooldown > 0) {
         this.soundCooldown--;
      }

      if (!this.noClip) {
         this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0, this.posZ);
      }

      this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
      float f = 0.98F;
      if (this.onGround) {
         BlockPos underPos = new BlockPos(
            MathHelper.floor(this.posX),
            MathHelper.floor(this.getEntityBoundingBox().minY) - 1,
            MathHelper.floor(this.posZ)
         );
         IBlockState underState = this.world.getBlockState(underPos);
         f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.91F;
      }

      this.motionX *= f;
      this.motionY *= 0.91;
      this.motionZ *= f;
      if (this.onGround) {
         this.motionY *= -0.9F;
      }

      if (!this.world.isRemote) {
         if (this.thrower != null) {
            if (this.ticksExisted < 3) {
               IEntitySynchronize.sendSynchronize(this, 48.0, this.thrower.getEntityId(), 0.0, 0.0, 0.0, 0.0, 0.0);
            }

            if (this.thrower.isDead && this.returnTime <= 0) {
               this.returnTime = 1;
            }
         } else {
            this.setDead();
         }

         if (this.ticksExisted < 3 || this.ticksExisted % 20 == 0) {
            this.world.setEntityState(this, this.itemSendId);
         }
      }

      if (this.mace != null) {
         this.mace.onTickMace(this);
         Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
         Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
         RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d, vec3d1);
         vec3d = new Vec3d(this.posX, this.posY, this.posZ);
         vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
         if (raytraceresult != null) {
            vec3d1 = new Vec3d(
               raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z
            );
         }

         Entity entity = null;
         List<Entity> list = this.world
            .getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0));
         double d0 = 0.0;

         for (int i = 0; i < list.size(); i++) {
            Entity entity1 = list.get(i);
            if (entity1.canBeCollidedWith()) {
               AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(0.3F + this.getCollisionBorderSize());
               if (vec3d.x >= axisalignedbb.minX
                  && vec3d.x <= axisalignedbb.maxX
                  && vec3d.y >= axisalignedbb.minY
                  && vec3d.y <= axisalignedbb.maxY
                  && vec3d.z >= axisalignedbb.minZ
                  && vec3d.z <= axisalignedbb.maxZ) {
                  float collBS = this.getCollisionBorderSize() + 0.1F;
                  Vec3d decrVector = new Vec3d(
                        vec3d.x - vec3d1.x, vec3d.y - vec3d1.y, vec3d.z - vec3d1.z
                     )
                     .normalize();
                  vec3d = new Vec3d(
                     vec3d.x + decrVector.x * collBS,
                     vec3d.y + decrVector.y * collBS,
                     vec3d.z + decrVector.z * collBS
                  );
               }

               RayTraceResult raytraceresult1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);
               if (raytraceresult1 != null) {
                  double d1 = vec3d.squareDistanceTo(raytraceresult1.hitVec);
                  if (d1 < d0 || d0 == 0.0) {
                     entity = entity1;
                     d0 = d1;
                  }
               }
            }
         }

         if (entity != null) {
            raytraceresult = new RayTraceResult(entity);
         }

         if (raytraceresult != null) {
            if (raytraceresult.typeOfHit == Type.BLOCK
               && this.world.getBlockState(raytraceresult.getBlockPos()).getBlock() == Blocks.PORTAL) {
               this.setPortal(raytraceresult.getBlockPos());
            } else if (!ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
               this.mace.onImpactMace(this, raytraceresult);
            }
         }
      }

      if (!this.hasNoGravity()) {
         this.motionY = this.motionY - this.getGravityVelocity();
      }

      this.setPosition(this.posX, this.posY, this.posZ);
   }

   @Override
   public void onClient(double x, double y, double z, double a, double b, double c) {
      Entity en = this.world.getEntityByID((int)x);
      if (en instanceof EntityPlayer) {
         this.thrower = (EntityPlayer)en;
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == -1) {
         this.spinned = true;
         this.spinDirection = true;
      }

      if (id == -2) {
         this.spinned = true;
         this.spinDirection = false;
      }

      if (id == -3) {
         this.spinned = false;
         this.spinDirection = false;
      }

      if (id == -4) {
         this.spinned = false;
         this.spinDirection = true;
      }

      if (id == 1) {
         this.mace = (ChainMace)ItemsRegister.CHAINMACEIRON;
         this.setSize(this.mace.entitySize, this.mace.entitySize);
      }

      if (id == 2) {
         this.mace = (ChainMace)ItemsRegister.CHAINMACEDIAMOND;
         this.setSize(this.mace.entitySize, this.mace.entitySize);
      }

      if (id == 3) {
         this.mace = (ChainMace)ItemsRegister.CHAINMACEMOLTEN;
         this.setSize(this.mace.entitySize, this.mace.entitySize);
      }

      if (id == 4) {
         this.mace = (ChainMace)ItemsRegister.ICEBREAKER;
         this.setSize(this.mace.entitySize, this.mace.entitySize);
      }

      if (id == 5) {
         this.mace = (ChainMace)ItemsRegister.ECHINUS;
         this.setSize(this.mace.entitySize, this.mace.entitySize);
      }
   }

   protected void entityInit() {
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      if (this.throwerName == null || this.throwerName.isEmpty()) {
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
   public EntityPlayer getThrower() {
      if (this.thrower == null && this.throwerName != null && !this.throwerName.isEmpty()) {
         this.thrower = this.world.getPlayerEntityByName(this.throwerName);
         if (this.thrower == null && this.world instanceof WorldServer) {
            try {
               Entity entity = ((WorldServer)this.world).getEntityFromUuid(UUID.fromString(this.throwerName));
               if (entity instanceof EntityPlayer) {
                  this.thrower = (EntityPlayer)entity;
               }
            } catch (Throwable var21) {
               this.thrower = null;
            }
         }
      }

      return this.thrower;
   }
}
