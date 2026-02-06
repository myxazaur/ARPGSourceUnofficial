package com.Vivern.Arpg.potions;

import com.Vivern.Arpg.main.ColorConverters;
import com.Vivern.Arpg.main.Mana;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class InstantMana extends Potion {
   protected InstantMana(boolean isBadEffectIn, int liquidColorIn) {
      super(isBadEffectIn, liquidColorIn);
      this.setRegistryName("arpg:instant_mana");
      this.setPotionName("Instant_Mana");
      this.setIconIndex(10, 1);
   }

   public boolean hasStatusIcon() {
      Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("arpg:textures/potions.png"));
      return true;
   }

   public boolean isInstant() {
      return true;
   }

   public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, EntityLivingBase entityLivingBaseIn, int amplifier, double health) {
      if (entityLivingBaseIn instanceof EntityPlayer) {
         int amount = (amplifier + 1) * 10;
         EntityPlayer player = (EntityPlayer)entityLivingBaseIn;
         Mana.setMana(player, ColorConverters.InBorder(Mana.getMana(player), Mana.getManaMax(player), Mana.getMana(player) + amount));
      }
   }
}
