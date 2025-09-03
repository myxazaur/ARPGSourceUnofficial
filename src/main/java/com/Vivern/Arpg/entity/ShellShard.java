//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponParameters;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ShellShard extends AbstractArrow {
   public ShellShard(World worldIn) {
      super(worldIn);
      this.pickupStatus = PickupStatus.DISALLOWED;
   }

   public ShellShard(World worldIn, double x, double y, double z) {
      super(worldIn, x, y, z);
      this.pickupStatus = PickupStatus.DISALLOWED;
   }

   public ShellShard(World worldIn, EntityLivingBase shooter) {
      super(worldIn, shooter);
      this.pickupStatus = PickupStatus.DISALLOWED;
   }

   @Override
   public void onHit(RayTraceResult raytraceResultIn) {
      if (raytraceResultIn.entityHit == null || this.ticksExisted > 3) {
         super.onHit(raytraceResultIn);
      }
   }

   @Override
   public boolean doWaterMoveHook() {
      return true;
   }

   @Override
   public int waterParticlesHookAdding() {
      return 0;
   }

   @Override
   public void modifySpeedInWater() {
      double multiplier = 0.97;
      this.motionX *= multiplier;
      this.motionY *= multiplier;
      this.motionZ *= multiplier;
      this.motionY -= 0.004;
   }

   public boolean hasNoGravity() {
      return this.inWater || super.hasNoGravity();
   }

   @Override
   public float calculateDamage(RayTraceResult raytraceResultIn, DamageSource damagesource) {
      double finalDamagePerSpeed = this.getItemArrowDamage(this.world) + this.bowDamage;
      if (Team.checkIsOpponent(this.shootingEntity, raytraceResultIn.entityHit)) {
         float f = Math.max(
               MathHelper.sqrt(
                  this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ
               ),
               1.0F
            )
            + 1.0F;
         int i = MathHelper.ceil(f * finalDamagePerSpeed);
         if (this.getIsCritical()) {
            i += this.rand.nextInt(i / 2 + 2);
         }

         return i;
      } else {
         return 0.0F;
      }
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (!this.world.isRemote) {
         if (this.timeInGround > 60) {
            this.removeArrowEntity(null);
         }

         if (this.ticksExisted < 5) {
            this.velocityChanged = true;
         }
      }
   }

   @Override
   public double getItemArrowDamage(World worldIn) {
      return WeaponParameters.getWeaponParameters(ItemsRegister.ARROWSHELL).get("shard_damage");
   }

   @Override
   public SoundEvent getHitSound() {
      return Sounds.seashell;
   }

   protected ItemStack getArrowStack() {
      return ItemStack.EMPTY;
   }
}
