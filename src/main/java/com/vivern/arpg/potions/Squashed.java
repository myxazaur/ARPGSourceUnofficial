package com.vivern.arpg.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class Squashed extends AdvancedPotion {
   public Squashed(int index) {
      super(true, 10395039, index, false);
      this.setRegistryName("arpg:squashed");
      this.setPotionName("Squashed");
      this.setIconIndex(39, 1);
   }

   @Override
   public float onHurtThis(EntityLivingBase entityOnEffect, DamageSource source, float damage, PotionEffect effect) {
      return source == DamageSource.IN_WALL ? damage * Math.max(effect.getAmplifier(), 1) : damage;
   }
}
