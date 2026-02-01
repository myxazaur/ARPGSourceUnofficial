package com.vivern.arpg.potions;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class IchorCurse extends Potion {
   protected IchorCurse(boolean isBadEffectIn, int liquidColorIn) {
      super(isBadEffectIn, liquidColorIn);
      this.setRegistryName("arpg:ichor_curse");
      this.setPotionName("Ichor_Curse");
      this.setIconIndex(1, 1);
   }

   public boolean hasStatusIcon() {
      Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("arpg:textures/potions.png"));
      return true;
   }
}
