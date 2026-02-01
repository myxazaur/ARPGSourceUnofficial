package com.vivern.arpg.entity;

import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.MovingSoundEntity;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.renders.SparkleSubparticle;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityArrowBengal extends AbstractArrow {
   public static int PARTICLE_BOUND = 10;
   public static int partickling_arrows = 0;
   static ResourceLocation texture = new ResourceLocation("arpg:textures/sparkle2.png");
   static ResourceLocation texture2 = new ResourceLocation("arpg:textures/sparkle3.png");
   public boolean isSparkling = false;
   public int timeSparkling = 0;

   public EntityArrowBengal(World worldIn) {
      super(worldIn);
   }

   public EntityArrowBengal(World worldIn, double x, double y, double z) {
      super(worldIn, x, y, z);
   }

   public EntityArrowBengal(World worldIn, EntityLivingBase shooter) {
      super(worldIn, shooter);
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (!this.world.isRemote) {
         if (this.inGround
            && this.timeInGround > 10
            && !this.isSparkling
            && this.pickupStatus != PickupStatus.CREATIVE_ONLY
            && this.pickupStatus != PickupStatus.DISALLOWED) {
            this.isSparkling = true;
            this.pickupStatus = PickupStatus.DISALLOWED;
            this.world.setEntityState(this, (byte)9);
         }

         if (this.timeSparkling > 200) {
            this.setDead();
         }
      }

      if (this.isSparkling) {
         this.timeSparkling++;
         if (this.world.isRemote) {
            Vec3d pointToLights = this.getPositionVector()
               .subtract(GetMOP.PitchYawToVec3d(-this.rotationPitch, -this.rotationYaw).scale((200 - this.timeSparkling) / 200.0 * 0.7));
            if (this.rand.nextFloat() < 0.5F) {
               GUNParticle part = new GUNParticle(
                  texture,
                  0.06F + this.rand.nextFloat() * 0.04F,
                  0.03F,
                  20 + this.rand.nextInt(10),
                  240,
                  this.world,
                  pointToLights.x,
                  pointToLights.y,
                  pointToLights.z,
                  (float)this.rand.nextGaussian() / 16.0F,
                  (float)this.rand.nextGaussian() / 16.0F + 0.045F,
                  (float)this.rand.nextGaussian() / 16.0F,
                  1.0F,
                  1.0F - this.rand.nextFloat() * 0.1F,
                  0.7F - this.rand.nextFloat() * 0.3F,
                  true,
                  this.rand.nextInt(360),
                  true,
                  1.0F
               );
               part.alphaGlowing = true;
               this.world.spawnEntity(part);
            } else {
               GUNParticle part = new GUNParticle(
                  texture2,
                  0.01F,
                  0.0F,
                  5,
                  240,
                  this.world,
                  pointToLights.x,
                  pointToLights.y,
                  pointToLights.z,
                  0.0F,
                  0.0F,
                  0.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  true,
                  this.rand.nextInt(360)
               );
               part.alphaGlowing = true;
               part.scaleTickAdding = 0.1F + this.rand.nextFloat() * 0.03F;
               if (this.rand.nextFloat() < 0.6F) {
                  part.rotationPitchYaw = new Vec2f(this.rand.nextInt(360), this.rand.nextInt(360));
               }

               this.world.spawnEntity(part);
            }

            SparkleSubparticle sparkl = new SparkleSubparticle(
               pointToLights.x,
               pointToLights.y,
               pointToLights.z,
               0.01F + this.rand.nextFloat() * 0.01F,
               20 + this.rand.nextInt(20),
               (float)(0.01 * this.rand.nextGaussian()),
               (float)(0.02 * this.rand.nextGaussian() + 0.01),
               (float)(0.01 * this.rand.nextGaussian()),
               0.0F
            );
            SparkleSubparticle.particles.add(sparkl);
         } else if (this.ticksExisted % 10 == 0) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(ItemsRegister.ARROWBENGAL);
            float bengal_damage = parameters.get("bengal_damage");

            for (Entity entity : GetMOP.getEntitiesInAABBof(this.world, this, 1.7, this)) {
               if (Team.checkIsOpponent(this.shootingEntity, entity)) {
                  Weapons.dealDamage(
                     new WeaponDamage(null, this.shootingEntity, this, false, false, this, WeaponDamage.fire), bengal_damage, this.shootingEntity, entity, true
                  );
                  entity.hurtResistantTime = 0;
               }
            }
         }
      }
   }

   @Override
   public SoundEvent getHitSound() {
      return Sounds.arrow_bengal;
   }

   protected ItemStack getArrowStack() {
      return new ItemStack(ItemsRegister.ARROWBENGAL);
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 9) {
         this.isSparkling = true;
         Minecraft.getMinecraft().getSoundHandler().playSound(new MovingSoundEntity(this, Sounds.sparkler, this.getSoundCategory(), 1.0F, 1.0F, false));
      } else {
         super.handleStatusUpdate(id);
      }
   }
}
