package com.Vivern.Arpg.potions;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

public class Stun extends AdvancedPotion {
   protected Stun(int index) {
      super(true, 5919067, index, true);
      this.setRegistryName("arpg:stun");
      this.setPotionName("Stun");
      this.setIconIndex(39, 1);
      this.shouldRender = true;
   }

   public static void stunEntity(Entity entity, float stunPower) {
      if (entity instanceof EntityLivingBase) {
         stunEntity((EntityLivingBase)entity, stunPower);
      }
   }

   public static void stunEntity(EntityLivingBase entity, float stunPower) {
      int stuntime = 0;
      if (entity instanceof EntityPlayer) {
         float entityPower = entity.getMaxHealth() * 0.05F + 2.0F * (float)entity.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue();
         entityPower = Math.max(entityPower, 1.0F);
         stuntime = (int)(20.0F * stunPower + 40.0F / entityPower * stunPower);
      }

      float entityPower = 1.0F + 2.0F * (float)entity.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue();
      if (entity.isNonBoss()) {
         entityPower = Math.max(entityPower, 1.0F);
         stuntime = (int)(40.0F * stunPower + 60.0F / entityPower * stunPower);
      } else {
         entityPower = Math.max(entityPower, 1.0F);
         stuntime = (int)(40.0F * stunPower + 40.0F / entityPower * stunPower);
      }

      if (stuntime > 0) {
         entity.addPotionEffect(new PotionEffect(PotionEffects.STUN, stuntime, 0, false, false));
      }
   }

   public int getUnstunnableTime(EntityLivingBase entity) {
      if (entity instanceof EntityPlayer) {
         float entityPower = entity.getMaxHealth() * 0.05F + 1.25F + (float)entity.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue();
         return (int)(entityPower * 60.0F);
      } else {
         float entityPower = entity.getMaxHealth() * 0.015F
            + (entity.width + entity.height) / 2.6F
            + 4.0F * (float)entity.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue();
         return entity.isNonBoss() ? (int)(entityPower * 60.0F) : (int)(entityPower * 40.0F) + 200;
      }
   }

   @Override
   public void onRemoveEffect(EntityLivingBase entityOnEffect, PotionEffect effect, boolean byExpiry) {
      if (byExpiry && effect.getAmplifier() == 0) {
         entityOnEffect.addPotionEffect(new PotionEffect(this, this.getUnstunnableTime(entityOnEffect), 1));
      }
   }

   public static boolean canImmobilizeEntity(EntityLivingBase entity, @Nullable PotionEffect effect) {
      return effect != null && effect.getAmplifier() == 0;
   }
}
