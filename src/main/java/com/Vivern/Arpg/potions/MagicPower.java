//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.potions;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class MagicPower extends Potion {
   protected MagicPower(boolean isBadEffectIn, int liquidColorIn) {
      super(isBadEffectIn, liquidColorIn);
      this.setRegistryName("arpg:magic_power");
      this.setPotionName("Magic_Power");
      this.setIconIndex(11, 1);
   }

   public boolean hasStatusIcon() {
      Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("arpg:textures/potions.png"));
      return true;
   }
}
