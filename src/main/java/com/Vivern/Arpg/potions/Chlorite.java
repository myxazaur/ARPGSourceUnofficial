package com.Vivern.Arpg.potions;

import baubles.api.BaublesApi;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.ItemsRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class Chlorite extends AdvancedPotion {
   public Chlorite(int index) {
      super(true, 14410575, index, true);
      this.setRegistryName("arpg:chlorite");
      this.setPotionName("Chlorite");
      this.setIconIndex(29, 1);
   }

   public void performEffect(EntityLivingBase entityOnEffect, int amplifier) {
      if (!entityOnEffect.world.isRemote) {
         if (amplifier > 15 && entityOnEffect.ticksExisted % 20 == 0 && this.getThisDuration(entityOnEffect) > 0) {
            entityOnEffect.attackEntityFrom(DamageSource.MAGIC, 5.0F);
         }
      } else if (entityOnEffect == Minecraft.getMinecraft().player && entityOnEffect.ticksExisted % 40 == 0) {
         Booom.drunkTick = 260;
         Booom.drunkPower = Math.min(amplifier / 2.0F, 15.0F);
      }
   }

   public boolean isReady(int duration, int amplifier) {
      return true;
   }

   @Override
   public void onRemoveEffect(EntityLivingBase entityOnEffect, PotionEffect effect, boolean byExpiry) {
      if (entityOnEffect == Minecraft.getMinecraft().player) {
         Booom.drunkTick = 0;
         Booom.drunkPower = 0.0F;
      }

      super.onRemoveEffect(entityOnEffect, effect, byExpiry);
   }

   @Override
   public void onThisDeath(LivingDeathEvent event, PotionEffect effect) {
      if (event.getEntityLiving() == Minecraft.getMinecraft().player) {
         Booom.drunkTick = 0;
         Booom.drunkPower = 0.0F;
      }

      super.onThisDeath(event, effect);
   }

   @Override
   public boolean isPotionApplicable(EntityLivingBase entityOnEffect, PotionEffect effect) {
      if (entityOnEffect instanceof EntityPlayer) {
         int eq = BaublesApi.isBaubleEquipped((EntityPlayer)entityOnEffect, ItemsRegister.GASMASK);
         if (eq > -1) {
            ItemStack st = BaublesApi.getBaublesHandler((EntityPlayer)entityOnEffect).getStackInSlot(eq);
            if (st.getItemDamage() == st.getMaxDamage()) {
               return true;
            }

            if (!entityOnEffect.world.isRemote) {
               st.damageItem(1, entityOnEffect);
            }

            return false;
         }
      }

      return true;
   }
}
