//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleGore extends Entity {
   public float scale;
   public float gravity;
   public int livetime = 1;
   public ResourceLocation texture;
   public float rotateX;
   public float rotateY;
   public float rotateZ;
   public float rotateXspeed;
   public float rotateYspeed;
   public float rotateZspeed;
   public float tickMotionMultiplier = 1.0F;
   public ModelRenderer box;
   public boolean randomRotationOnImpact = true;
   public int light = -1;
   public Entity entityNailer = null;
   public List<ModelRenderer> renderCutList = null;

   public ParticleGore(World worldIn) {
      super(worldIn);
      this.livetime = 1;
   }

   public ParticleGore(
      World world,
      ResourceLocation texture,
      float scale,
      float gravity,
      int livetime,
      ModelRenderer box,
      double x,
      double y,
      double z,
      double speedx,
      double speedy,
      double speedz
   ) {
      super(world);
      this.setPosition(x, y, z);
      this.scale = scale;
      this.texture = texture;
      this.livetime = livetime;
      this.gravity = gravity;
      this.box = box;
      this.setSize(0.25F, 0.25F);
      this.setVelocity(speedx, speedy, speedz);
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   public boolean handleWaterMovement() {
      return this.world.handleMaterialAcceleration(this.getEntityBoundingBox(), Material.WATER, this);
   }

   public boolean canBeAttackedWithItem() {
      return false;
   }

   public void setNailer(Entity entity) {
      if (entity instanceof INailer) {
         this.entityNailer = entity;
      }
   }

   public void onUpdate() {
      super.onUpdate();
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      if (this.entityNailer != null && !this.entityNailer.isDead) {
         this.setPosition(this.entityNailer.posX, this.entityNailer.posY, this.entityNailer.posZ);
      } else {
         if (!this.hasNoGravity()) {
            this.motionY = this.motionY - this.gravity;
         }

         if (this.world.getBlockState(new BlockPos(this)).getMaterial() == Material.LAVA) {
            this.motionY = 0.2F;
            this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F;
            this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F;
            this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
         }

         this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0, this.posZ);
         double d0 = 8.0;
         this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
         float f = 0.98F * this.tickMotionMultiplier;
         if (this.onGround) {
            this.rotateXspeed *= 0.8F;
            this.rotateYspeed *= 0.8F;
            this.rotateZspeed *= 0.8F;
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
      }

      if (this.ticksExisted > this.livetime) {
         this.setDead();
      }

      this.rotateX = this.rotateX + this.rotateXspeed;
      this.rotateY = this.rotateY + this.rotateYspeed;
      this.rotateZ = this.rotateZ + this.rotateZspeed;
   }

   public void onUpdatee() {
      super.onUpdate();
      if (this.ticksExisted > this.livetime) {
         this.setDead();
      }

      if (this.entityNailer != null && !this.entityNailer.isDead) {
         this.setPosition(this.entityNailer.posX, this.entityNailer.posY, this.entityNailer.posZ);
      } else {
         if (!this.hasNoGravity()) {
            this.motionY = this.motionY - this.gravity;
         }

         this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0, this.posZ);
      }

      float frict = this.tickMotionMultiplier;
      if (this.onGround) {
         this.rotateXspeed *= 0.8F;
         this.rotateYspeed *= 0.8F;
         this.rotateZspeed *= 0.8F;
         BlockPos underPos = new BlockPos(
            MathHelper.floor(this.posX),
            MathHelper.floor(this.getEntityBoundingBox().minY) - 1,
            MathHelper.floor(this.posZ)
         );
         IBlockState underState = this.world.getBlockState(underPos);
         frict *= underState.getBlock().getSlipperiness(underState, this.world, underPos, this);
      }

      this.motionX *= frict;
      this.motionY *= frict;
      this.motionZ *= frict;
      if (this.onGround) {
         this.motionY *= -0.9F;
      }

      this.rotateX = this.rotateX + this.rotateXspeed;
      this.rotateY = this.rotateY + this.rotateYspeed;
      this.rotateZ = this.rotateZ + this.rotateZspeed;
   }

   protected void entityInit() {
   }

   public void readEntityFromNBT(NBTTagCompound compound) {
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
   }
}
