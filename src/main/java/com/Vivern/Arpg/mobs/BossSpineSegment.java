//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BossSpineSegment extends EntityMob {
   public int number;
   public BossSpine owner;

   public BossSpineSegment(World worldIn) {
      super(worldIn);
      this.setSize(1.0F, 1.0F);
      this.setNoGravity(true);
      this.noClip = true;
      this.number = 0;
      this.owner = null;
      this.isImmuneToFire = true;
   }

   public BossSpineSegment(World worldIn, int number, BossSpine owner) {
      super(worldIn);
      this.setSize(1.0F, 1.0F);
      this.setNoGravity(true);
      this.noClip = true;
      this.number = number;
      this.owner = owner;
      this.isImmuneToFire = true;
   }

   public void onDeath(DamageSource cause) {
      if (this.owner != null) {
         this.owner.deads++;
      }

      super.onDeath(cause);
   }

   public void fall(float distance, float damageMultiplier) {
   }

   public boolean attackEntityFrom(DamageSource source, float amount) {
      if (source == DamageSource.IN_WALL || source == DamageSource.CRAMMING || source == DamageSource.CACTUS) {
         return false;
      } else if (this.owner != null && this.owner.deads > 20) {
         this.world.setEntityState(this, (byte)2);
         boolean att = this.owner.attackEntityFrom(source, amount);
         if (this.owner.getHealth() <= 0.0F) {
            super.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
         }

         return att;
      } else {
         return this.isEntityInvulnerable(source) ? false : super.attackEntityFrom(source, amount);
      }
   }

   public boolean canBreatheUnderwater() {
      return true;
   }

   public void onUpdate() {
      super.onUpdate();
      if (!this.world.isRemote) {
         if (this.owner != null) {
            if (this.owner.poslist.size() > this.number) {
               Vec3d vec = this.owner.poslist.get(this.number);
               if (vec != null) {
                  this.motionX /= 2.0;
                  this.motionY /= 2.0;
                  this.motionZ /= 2.0;
                  float power = (float)this.getDistance(vec.x, vec.y, vec.z);
                  SuperKnockback.applyMove(this, -power, vec.x, vec.y, vec.z);
               }
            }

            if (this.owner.getHealth() <= 0.0F) {
               this.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
            }
         } else {
            this.setDead();
         }
      }

      if (this.owner != null) {
         if (this.owner.poslist.size() > this.number) {
            if (this.number > 0) {
               Vec3d pitchYaw = GetMOP.Vec3dToPitchYaw(this.getPositionVector().subtract(this.owner.poslist.get(this.number - 1)));
               this.rotationPitch = (float)MathHelper.wrapDegrees(pitchYaw.x);
               this.rotationYaw = (float)MathHelper.wrapDegrees(pitchYaw.y);
            }

            if (this.number == 0) {
               Vec3d pitchYaw = GetMOP.Vec3dToPitchYaw(this.getPositionVector().subtract(this.owner.getPositionVector()));
               this.rotationPitch = (float)MathHelper.wrapDegrees(pitchYaw.x);
               this.rotationYaw = (float)MathHelper.wrapDegrees(pitchYaw.y);
            }
         }

         if (this.owner.getAttackTarget() != null) {
            this.setAttackTarget(this.owner.getAttackTarget());
         }
      }
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0);
      this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(17.0);
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0);
      this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4.0);
      this.getEntityAttribute(PropertiesRegistry.ARMOR_PROTECTION).setBaseValue(3.0);
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return Sounds.spine_hurt;
   }

   protected SoundEvent getDeathSound() {
      return Sounds.spine_segment_dead;
   }

   protected void initEntityAI() {
   }
}
