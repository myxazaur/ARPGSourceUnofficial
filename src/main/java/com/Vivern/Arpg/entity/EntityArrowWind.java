//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.MovingSoundEntity;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.ParticleTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityArrowWind extends AbstractArrow {
   public static ResourceLocation texture = new ResourceLocation("arpg:textures/circle_wind.png");
   public static ParticleTracker.TrackerSmoothShowHide ssh = new ParticleTracker.TrackerSmoothShowHide(
      new Vec3d[]{new Vec3d(0.0, 4.0, 0.2), new Vec3d(5.0, 13.0, -0.125)},
      new Vec3d[]{new Vec3d(0.0, 3.0, 0.4), new Vec3d(3.0, 6.0, 0.2), new Vec3d(7.0, 13.0, -0.3)}
   );
   public MovingSoundEntity soundFly;

   public EntityArrowWind(World worldIn) {
      super(worldIn);
   }

   public EntityArrowWind(World worldIn, double x, double y, double z) {
      super(worldIn, x, y, z);
   }

   public EntityArrowWind(World worldIn, EntityLivingBase shooter) {
      super(worldIn, shooter);
   }

   public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
      super.shoot(x, y, z, velocity, inaccuracy / 3.0F);
   }

   public boolean canWind() {
      return Math.abs(this.prevPosX - this.posX) > 0.57
         || Math.abs(this.prevPosY - this.posY) > 0.57
         || Math.abs(this.prevPosZ - this.posZ) > 0.57;
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      boolean continuePlaysound = false;
      if (!this.isInWater() && !this.inGround && this.canWind()) {
         if (!this.world.isRemote) {
            if (this.pickupStatus == PickupStatus.ALLOWED) {
               double speedSq = this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ;
               if (speedSq >= 1.0) {
                  double radius = 3.0;
                  double scale = 1.0;
                  if (speedSq >= 16.0) {
                     radius = 5.0;
                     scale = 1.4;
                  } else if (speedSq >= 4.0) {
                     radius = 4.0;
                     scale = 1.2;
                  }

                  Vec3d vec = this.getPositionVector().add(new Vec3d(this.motionX, this.motionY, this.motionZ).scale(scale));

                  for (Entity entity : GetMOP.getEntitiesInAABBof(this.world, vec, radius, this)) {
                     if (!(entity instanceof EntityArrowWind)) {
                        SuperKnockback.applyKnockback(entity, -0.75F, vec.x, vec.y, vec.z);
                     }
                  }
               }
            }
         } else {
            double speed = GetMOP.getSpeed(this);
            if (speed >= 1.0) {
               continuePlaysound = true;
               float add = (float)Math.min((speed - 1.0) / 2.0, 1.5);
               Vec3d vec = this.getPositionVector().add(new Vec3d(this.motionX, this.motionY, this.motionZ).scale(0.5));
               GUNParticle part = new GUNParticle(
                  texture,
                  add,
                  0.0F,
                  14,
                  -1,
                  this.world,
                  vec.x,
                  vec.y,
                  vec.z,
                  0.0F,
                  0.0F,
                  0.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  true,
                  this.rand.nextInt(360)
               );
               part.alpha = 0.0F;
               part.rotationPitchYaw = new Vec2f(-this.rotationPitch, -this.rotationYaw);
               part.tracker = ssh;
               this.world.spawnEntity(part);
            }
         }
      }

      if (this.world.isRemote) {
         if (continuePlaysound) {
            if (this.soundFly == null) {
               this.soundFly = new MovingSoundEntity(this, Sounds.arrow_wind_fly, this.getSoundCategory(), 1.2F, 1.0F, true);
               Minecraft.getMinecraft().getSoundHandler().playSound(this.soundFly);
            }
         } else if (this.soundFly != null) {
            Minecraft.getMinecraft().getSoundHandler().stopSound(this.soundFly);
            this.soundFly = null;
         }
      }
   }

   @Override
   public SoundEvent getHitSound() {
      return Sounds.arrow_wind;
   }

   protected ItemStack getArrowStack() {
      return new ItemStack(ItemsRegister.ARROWWIND);
   }
}
