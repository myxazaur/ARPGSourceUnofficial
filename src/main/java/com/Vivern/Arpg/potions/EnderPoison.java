//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.potions;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import com.Vivern.Arpg.elements.BaubleAntipotion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.ArrayUtils;

public class EnderPoison extends AdvancedPotion {
   public EnderPoison(boolean isBadEffectIn, int liquidColorIn, int index) {
      super(isBadEffectIn, liquidColorIn, index, false);
      this.setRegistryName("arpg:ender_poison");
      this.setPotionName("Ender_Poison");
      this.setIconIndex(13, 1);
   }

   public void performEffect(EntityLivingBase entityOnEffect, int amplifier) {
      if (!entityOnEffect.world.isRemote && entityOnEffect.ticksExisted % Math.max(20 - amplifier, 2) == 0) {
         double d0 = entityOnEffect.posX;
         double d1 = entityOnEffect.posY;
         double d2 = entityOnEffect.posZ;

         for (int i = 0; i < 16; i++) {
            double d3 = entityOnEffect.posX + (entityOnEffect.getRNG().nextDouble() - 0.5) * 16.0;
            double d4 = MathHelper.clamp(
               entityOnEffect.posY + (entityOnEffect.getRNG().nextInt(16) - 8), 0.0, entityOnEffect.world.getActualHeight() - 1
            );
            double d5 = entityOnEffect.posZ + (entityOnEffect.getRNG().nextDouble() - 0.5) * 16.0;
            if (entityOnEffect.isRiding()) {
               entityOnEffect.dismountRidingEntity();
            }

            if (entityOnEffect.attemptTeleport(d3, d4, d5)) {
               entityOnEffect.world.playSound((EntityPlayer)null, d0, d1, d2, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
               entityOnEffect.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
               break;
            }
         }
      }
   }

   @Override
   public boolean isPotionApplicable(EntityLivingBase entityOnEffect, PotionEffect effect) {
      if (entityOnEffect instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entityOnEffect;
         IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);

         for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stack = handler.getStackInSlot(i);
            if (stack.getItem() instanceof BaubleAntipotion) {
               BaubleAntipotion baubleAntipotion = (BaubleAntipotion)stack.getItem();
               return !ArrayUtils.contains(baubleAntipotion.potion, this);
            }
         }
      }

      return super.isPotionApplicable(entityOnEffect, effect);
   }

   public boolean isReady(int duration, int amplifier) {
      return true;
   }
}
