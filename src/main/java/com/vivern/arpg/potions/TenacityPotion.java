package com.vivern.arpg.potions;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class TenacityPotion extends Potion {
   protected TenacityPotion(boolean isBadEffectIn, int liquidColorIn) {
      super(isBadEffectIn, liquidColorIn);
      this.setRegistryName("arpg:tenacity");
      this.setPotionName("Tenacity");
      this.setIconIndex(32, 1);
      this.registerPotionAttributeModifier(SharedMonsterAttributes.KNOCKBACK_RESISTANCE, MathHelper.getRandomUUID().toString(), 0.2, 0);
   }

   public boolean hasStatusIcon() {
      Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("arpg:textures/potions.png"));
      return true;
   }
}
