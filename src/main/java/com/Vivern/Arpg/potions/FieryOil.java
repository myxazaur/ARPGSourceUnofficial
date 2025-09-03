//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.potions;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

public class FieryOil extends Potion {
   protected FieryOil(boolean isBadEffectIn, int liquidColorIn) {
      super(isBadEffectIn, liquidColorIn);
      this.setRegistryName("arpg:fiery_oil");
      this.setPotionName("Fiery_Oil");
      this.setIconIndex(8, 1);
   }

   public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
      return 1.0F / ((amplifier + 0.5F) / 2.0F + 0.75F) - 1.0F;
   }

   public boolean hasStatusIcon() {
      Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("arpg:textures/potions.png"));
      return true;
   }

   public void performEffect(EntityLivingBase entityLivingBase, int amplifier) {
      double damageRadius = 4.0 + amplifier;
      AxisAlignedBB axisalignedbb = entityLivingBase.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      List<EntityLivingBase> list = entityLivingBase.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
      if (!list.isEmpty()) {
         for (EntityLivingBase base : list) {
            if (base.isBurning()) {
               entityLivingBase.setFire(1);
            }
         }
      }
   }

   public boolean isReady(int duration, int amplifier) {
      return true;
   }
}
