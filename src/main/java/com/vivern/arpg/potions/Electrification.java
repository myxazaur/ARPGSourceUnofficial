package com.vivern.arpg.potions;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class Electrification extends Potion {
   protected Electrification(boolean isBadEffectIn, int liquidColorIn) {
      super(isBadEffectIn, liquidColorIn);
      this.setRegistryName("arpg:electrification");
      this.setPotionName("Electrification");
      this.setIconIndex(16, 1);
   }

   public boolean hasStatusIcon() {
      Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("arpg:textures/potions.png"));
      return true;
   }
}
