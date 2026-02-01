package com.vivern.arpg.potions;

import com.vivern.arpg.main.Mana;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class RadReduction extends AdvancedPotion {
   public RadReduction(int index) {
      super(false, 14410575, index, false);
      this.setRegistryName("arpg:rad_reduction");
      this.setPotionName("Rad_Reduction");
      this.setIconIndex(30, 1);
   }

   public void performEffect(EntityLivingBase entityOnEffect, int amplifier) {
      if (!entityOnEffect.world.isRemote
         && entityOnEffect instanceof EntityPlayer
         && entityOnEffect.ticksExisted % 10 == 0
         && this.getThisDuration(entityOnEffect) > 0) {
         Mana.addRad((EntityPlayer)entityOnEffect, -2 * (amplifier + 1), false);
      }
   }

   public boolean isReady(int duration, int amplifier) {
      return true;
   }
}
