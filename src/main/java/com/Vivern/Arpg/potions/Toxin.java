package com.Vivern.Arpg.potions;

import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.WeaponDamage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class Toxin extends Potion {
   protected Toxin(boolean isBadEffectIn, int liquidColorIn) {
      super(isBadEffectIn, liquidColorIn);
      this.setRegistryName("arpg:toxin");
      this.setPotionName("Toxin");
      this.setIconIndex(15, 1);
   }

   public boolean hasStatusIcon() {
      Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("arpg:textures/potions.png"));
      return true;
   }

   public void performEffect(EntityLivingBase entityLivingBase, int amplifier) {
      if (entityLivingBase.ticksExisted % 12 == 0) {
         IAttributeInstance entityAttribute = entityLivingBase.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
         double baseValue = entityAttribute.getBaseValue();
         entityAttribute.setBaseValue(1.0);
         entityLivingBase.attackEntityFrom(new WeaponDamage(null, null, null, false, false, null, WeaponDamage.toxin), 3 * (amplifier + 1));
         entityLivingBase.hurtResistantTime = 0;
         entityAttribute.setBaseValue(baseValue);
         if (entityLivingBase.getHealth() <= 0.0F) {
            DeathEffects.applyDeathEffect(entityLivingBase, DeathEffects.DE_COLOREDACID);
         }
      }
   }

   public boolean isReady(int duration, int amplifier) {
      return true;
   }
}
