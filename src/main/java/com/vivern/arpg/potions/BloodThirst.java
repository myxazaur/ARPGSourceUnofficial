package com.vivern.arpg.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;

public class BloodThirst extends AdvancedPotion {
   public BloodThirst() {
      super(true, 15678523, 2, false);
      this.setRegistryName("arpg:blood_thirst");
      this.setPotionName("Blood_Thirst");
      this.setIconIndex(25, 1);
      this.registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, MathHelper.getRandomUUID().toString(), 2.0, 0);
   }

   public void performEffect(EntityLivingBase entityOnEffect, int amplifier) {
      if (!entityOnEffect.world.isRemote && entityOnEffect.ticksExisted % 25 == 0 && this.getThisDuration(entityOnEffect) > 0) {
         double motion = entityOnEffect.motionY;
         entityOnEffect.attackEntityFrom(DamageSource.MAGIC, 2 * (amplifier + 1));
         entityOnEffect.motionY = motion;
      }
   }

   public boolean isReady(int duration, int amplifier) {
      return true;
   }

   @Override
   public void onThisKills(EntityLivingBase entityOnEffect, EntityLivingBase target, DamageSource source, PotionEffect effect) {
      entityOnEffect.removeActivePotionEffect(this);
   }
}
