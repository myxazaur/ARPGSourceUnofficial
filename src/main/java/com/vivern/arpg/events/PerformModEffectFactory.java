package com.vivern.arpg.events;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.MinecraftForge;

public class PerformModEffectFactory {
   public static void onPerformModEffect(EntityLivingBase livingBase, int amplifier, Potion potion) {
      MinecraftForge.EVENT_BUS.post(new PerformModEffectEvent(livingBase, amplifier, potion));
   }
}
