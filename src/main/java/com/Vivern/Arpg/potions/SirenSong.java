//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.potions;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;

public class SirenSong extends AdvancedPotion {
   public static int clientPotionPower = 0;

   public SirenSong(int index) {
      super(true, 3495164, index, false);
      this.setRegistryName("arpg:siren_song");
      this.setPotionName("Siren_Song");
      this.setIconIndex(34, 1);
   }

   public void performEffect(EntityLivingBase entityOnEffect, int amplifier) {
      if (entityOnEffect == Minecraft.getMinecraft().player) {
         int dur = this.getThisDuration(entityOnEffect);
         if (dur > 40) {
            if (clientPotionPower < 40) {
               clientPotionPower++;
            }
         } else {
            clientPotionPower = dur;
         }
      }
   }

   public boolean isReady(int duration, int amplifier) {
      return true;
   }
}
