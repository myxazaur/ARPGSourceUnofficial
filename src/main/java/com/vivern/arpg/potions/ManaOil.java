package com.vivern.arpg.potions;

import com.vivern.arpg.main.Mana;
import com.vivern.arpg.main.PropertiesRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

public class ManaOil extends AdvancedPotion {
   public ManaOil(int index) {
      super(false, 4603238, index, false);
      this.setRegistryName("arpg:mana_oil");
      this.setPotionName("Mana_Oil");
      this.setIconIndex(36, 1);
      this.registerPotionAttributeModifier(PropertiesRegistry.MAGIC_POWER_MAX, MathHelper.getRandomUUID().toString(), -0.9F, 1);
      this.registerPotionAttributeModifier(PropertiesRegistry.MANASPEED_MAX, MathHelper.getRandomUUID().toString(), 0.5, 0);
      this.registerPotionAttributeModifier(PropertiesRegistry.ENTITY_COLOR_RED_MAX, MathHelper.getRandomUUID().toString(), -0.22000003F, 1);
      this.registerPotionAttributeModifier(PropertiesRegistry.ENTITY_COLOR_GREEN_MAX, MathHelper.getRandomUUID().toString(), -0.33999997F, 1);
      this.registerPotionAttributeModifier(PropertiesRegistry.ENTITY_COLOR_BLUE_MAX, MathHelper.getRandomUUID().toString(), -0.100000024F, 1);
   }

   public void performEffect(EntityLivingBase entityOnEffect, int amplifier) {
      if (!entityOnEffect.world.isRemote
         && entityOnEffect instanceof EntityPlayer
         && entityOnEffect.ticksExisted % (int)(100.0F / (amplifier + 5)) == 0) {
         Mana.changeMana((EntityPlayer)entityOnEffect, 3.0F);
      }
   }

   public boolean isReady(int duration, int amplifier) {
      return true;
   }
}
