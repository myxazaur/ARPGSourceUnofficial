package com.Vivern.Arpg.events;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PerformModEffectEvent extends Event {
   private final EntityLivingBase livingBase;
   private final Potion potion;
   private final int amplifier;

   public PerformModEffectEvent(EntityLivingBase livingBase, int amplifier, Potion potion) {
      this.livingBase = livingBase;
      this.amplifier = amplifier;
      this.potion = potion;
   }

   public EntityLivingBase getEntityLiving() {
      return this.livingBase;
   }

   public int getAmplifier() {
      return this.amplifier;
   }

   public Potion getPotion() {
      return this.potion;
   }
}
