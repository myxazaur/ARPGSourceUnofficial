package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityArrowFish extends AbstractArrow {
   public EntityLivingBase target;
   public boolean watered = false;
   public float starterSpeed = 0.0F;

   public EntityArrowFish(World worldIn) {
      super(worldIn);
      this.livetimeAir = 600;
   }

   public EntityArrowFish(World worldIn, double x, double y, double z) {
      super(worldIn, x, y, z);
      this.livetimeAir = 600;
   }

   public EntityArrowFish(World worldIn, EntityLivingBase shooter) {
      super(worldIn, shooter);
      this.livetimeAir = 600;
   }

   public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
      this.starterSpeed = velocity * 0.7F;
      super.shoot(x, y, z, velocity, inaccuracy);
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      this.watered = this.isInsideOfMaterial(Material.WATER);
      if (this.watered && !this.world.isRemote) {
         double speed = GetMOP.getSpeed(this);
         this.motionX = this.motionX / speed * this.starterSpeed;
         this.motionY = this.motionY / speed * this.starterSpeed;
         this.motionZ = this.motionZ / speed * this.starterSpeed;
         if (this.target != null) {
            this.motionX *= 0.6;
            this.motionY *= 0.6;
            this.motionZ *= 0.6;
            SuperKnockback.applyMove(
               this,
               this.target.isPotionActive(MobEffects.GLOWING) ? -0.3F : -0.13F,
               this.target.posX,
               this.target.posY + this.target.height / 2.0F,
               this.target.posZ
            );
            this.velocityChanged = true;
            if (!this.target.isEntityAlive()) {
               this.target = null;
            }
         } else if (this.rand.nextFloat() < 0.5F) {
            List<EntityLivingBase> list = GetMOP.getHostilesInAABBto(
               this.world,
               this.getPositionVector().add(new Vec3d(this.motionX, this.motionY, this.motionZ).normalize().scale(5.0)),
               5.0,
               5.0,
               (EntityLivingBase)this.shootingEntity,
               this.shootingEntity
            );
            if (!list.isEmpty()) {
               List<EntityLivingBase> list2 = new ArrayList<>();

               for (EntityLivingBase entity : list) {
                  if (entity.isPotionActive(MobEffects.GLOWING)) {
                     list2.add(entity);
                  }
               }

               if (list2.isEmpty()) {
                  if (this.rand.nextFloat() < 0.5F) {
                     this.target = list.get(this.rand.nextInt(list.size()));
                  }
               } else {
                  this.target = list2.get(this.rand.nextInt(list2.size()));
               }

               this.world.setEntityState(this, (byte)9);
            }
         }

         if (this.rand.nextFloat() < 0.02F || this.ticksExisted == 1) {
            this.world.setEntityState(this, (byte)9);
         }
      }
   }

   public boolean hasNoGravity() {
      return super.hasNoGravity() || this.watered;
   }

   @Override
   public SoundEvent getHitSound() {
      return Sounds.arrow_fish;
   }

   protected ItemStack getArrowStack() {
      return new ItemStack(ItemsRegister.ARROWFISH);
   }

   public boolean handleWaterMovement() {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 9) {
         this.arrowShake = 8;
      } else {
         super.handleStatusUpdate(id);
      }
   }
}
