package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.AnimatedGParticle;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EnigmateTwinsShoot extends EntityThrowable {
   public final ItemStack weaponstack;
   public EntityLivingBase targ = null;
   public float homingPower = -0.1F;
   public float homingPowerAdd = -0.006F;
   public int expireTicks;
   public boolean findNext = true;
   public float damage;
   public int canImpactTick;
   public int impactTickNeed;
   static ResourceLocation explode = new ResourceLocation("arpg:textures/magic_explode4.png");
   static ResourceLocation texture = new ResourceLocation("arpg:textures/hail_trace.png");

   public EnigmateTwinsShoot(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.ENIGMATETWINS);
      this.randomizeHoming();
   }

   public EnigmateTwinsShoot(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.ENIGMATETWINS);
      this.randomizeHoming();
   }

   public EnigmateTwinsShoot(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.ENIGMATETWINS);
      this.randomizeHoming();
   }

   public EnigmateTwinsShoot(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.randomizeHoming();
   }

   public void randomizeHoming() {
      this.homingPower = -0.1F - 0.02F * this.rand.nextFloat();
      this.homingPowerAdd = (-0.006F - 0.0015F * this.rand.nextFloat()) * 20.0F;
      this.impactTickNeed = this.rand.nextFloat() < 0.6 ? -1 : this.rand.nextInt(25);
   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 80) {
         this.setDead();
      }

      if (this.targ == null) {
         if (this.ticksExisted % 5 == 0) {
            EntityLivingBase enemy = GetMOP.findNearestEnemy(
               this.world, this.thrower, this.posX, this.posY, this.posZ, 7.0, true
            );
            if (enemy != null) {
               this.targ = enemy;
            } else {
               this.expireTicks += 5;
               if (this.expireTicks > 20) {
                  this.setDead();
               }
            }
         }
      } else {
         if (!this.targ.isEntityAlive() && this.findNext) {
            EntityLivingBase enemy = GetMOP.findNearestEnemy(
               this.world, this.thrower, this.posX, this.posY, this.posZ, 7.0, true
            );
            if (enemy != null) {
               this.targ = enemy;
            }

            this.findNext = false;
         }

         double friction = 0.95 + Debugger.floats[2];
         this.motionX *= friction;
         this.motionY *= friction;
         this.motionZ *= friction;
         SuperKnockback.applyMove(
            this, this.homingPower, this.targ.posX, this.targ.posY + this.targ.height / 2.0F, this.targ.posZ
         );
         this.homingPower = this.homingPower + this.homingPowerAdd;
      }

      if (this.canImpactTick > 0) {
         this.canImpactTick++;
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 8) {
         AnimatedGParticle anim = new AnimatedGParticle(
            explode,
            0.25F + 0.2F * this.rand.nextFloat(),
            0.0F,
            20,
            240,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            0.0F,
            0.0F,
            0.0F,
            1.0F - 0.1F * this.rand.nextFloat(),
            1.0F - 0.1F * this.rand.nextFloat(),
            1.0F,
            true,
            this.rand.nextInt(360)
         );
         anim.framecount = 8;
         anim.alphaGlowing = true;
         anim.randomDeath = false;
         anim.animDelay = this.rand.nextFloat() < 0.4F ? 3 : 2;
         anim.disableOnEnd = true;
         this.world.spawnEntity(anim);
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (this.canImpactTick == 0) {
            this.canImpactTick++;
            this.homingPower = (-0.1F - 0.02F * this.rand.nextFloat()) * 3.0F;
            this.homingPowerAdd = 0.0F;
            double friction = 0.7;
            this.motionX *= friction;
            this.motionY *= friction;
            this.motionZ *= friction;
         }

         if (this.canImpactTick > this.impactTickNeed && Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
            result.entityHit.hurtResistantTime = 0;
            Weapons.dealDamage(
               new WeaponDamage(this.weaponstack, this.thrower, this, false, true, this, WeaponDamage.portal),
               this.damage * (1.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack) * 0.07F),
               this.thrower,
               result.entityHit,
               true,
               0.1F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack) * 0.04F
            );
            result.entityHit.hurtResistantTime = 0;
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.enigmate_twins_impact,
                  SoundCategory.AMBIENT,
                  0.8F,
                  0.9F + this.rand.nextFloat() / 5.0F
               );
            this.world.setEntityState(this, (byte)8);
            this.setDead();
         }
      } else if (this.canImpactTick <= 0
         && this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
         this.world
            .playSound(
               (EntityPlayer)null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.enigmate_twins_impact,
               SoundCategory.AMBIENT,
               0.8F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)8);
            this.setDead();
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void onEntityUpdate() {
      Vec3d pos1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
      Vec3d pos2 = this.getPositionVector();
      if (this.world.isRemote && pos1.lengthSquared() > 1.0E-6 && pos2.lengthSquared() > 1.0E-6) {
         BetweenParticle laser = new BetweenParticle(
            this.world, texture, 0.04F, 190, 0.85F, 0.2F, 1.0F, 0.1F, pos1.distanceTo(pos2), 5, -0.15F, 9999.0F, pos1, pos2
         );
         laser.alphaGlowing = true;
         laser.texstart = 0.1F;
         laser.offset = 0.1F;
         laser.setPosition(pos1.x, pos1.y, pos1.z);
         this.world.spawnEntity(laser);
      }
   }
}
