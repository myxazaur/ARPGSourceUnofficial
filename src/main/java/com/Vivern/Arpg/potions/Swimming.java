//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.potions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

public class Swimming extends AdvancedPotion {
   public Swimming(int index) {
      super(false, 30855, index, false);
      this.setRegistryName("arpg:swimming");
      this.setPotionName("Swimming");
      this.setIconIndex(35, 1);
      this.registerPotionAttributeModifier(EntityPlayer.SWIM_SPEED, MathHelper.getRandomUUID().toString(), 0.2F, 0);
   }
}
