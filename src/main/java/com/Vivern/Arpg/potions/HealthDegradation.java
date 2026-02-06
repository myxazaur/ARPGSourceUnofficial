package com.Vivern.Arpg.potions;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.math.MathHelper;

public class HealthDegradation extends AdvancedPotion {
   public HealthDegradation(int index) {
      super(true, 5242880, index, false);
      this.setRegistryName("arpg:health_degradation");
      this.setPotionName("Health_Degradation");
      this.setIconIndex(37, 1);
      this.registerPotionAttributeModifier(SharedMonsterAttributes.MAX_HEALTH, MathHelper.getRandomUUID().toString(), -4.0, 0);
   }
}
