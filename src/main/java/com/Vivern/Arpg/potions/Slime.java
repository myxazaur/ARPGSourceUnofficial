//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.potions;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class Slime extends Potion {
   protected Slime(boolean isBadEffectIn, int liquidColorIn) {
      super(isBadEffectIn, liquidColorIn);
      this.setRegistryName("arpg:slime");
      this.setPotionName("Slime");
      this.setIconIndex(7, 1);
   }

   public boolean hasStatusIcon() {
      Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("arpg:textures/potions.png"));
      return true;
   }

   public void performEffect(EntityLivingBase entityLivingBase, int amplifier) {
      int amp = amplifier + 1;
      if (entityLivingBase.collided) {
         entityLivingBase.motionX /= 1.2 * amp;
         entityLivingBase.motionY /= 1.2 * amp;
         entityLivingBase.motionZ /= 1.2 * amp;
      }
   }

   public boolean isReady(int duration, int amplifier) {
      return true;
   }
}
