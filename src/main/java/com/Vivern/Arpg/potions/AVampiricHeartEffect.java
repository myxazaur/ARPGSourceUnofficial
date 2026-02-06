package com.Vivern.Arpg.potions;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.UUID;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.nbt.NBTTagCompound;

public class AVampiricHeartEffect extends AdvancedMobEffect {
   public AVampiricHeartEffect() {
      super("Vampiric heart effect", 1);
      this.hasAttributes = true;
   }

   @Override
   public void tick(EntityLivingBase entity, NBTTagCompound effect) {
      if (entity.ticksExisted % 150 == 0) {
         AdvancedMobEffects.addToAmplifier(effect, 1);
         int amp = AdvancedMobEffects.deserealizeAmplifier(effect);
         if (amp > 0) {
            this.removeAttributes(entity);
            this.applyAttributes(entity);
         }
      }
   }

   @Override
   public Multimap<String, AttributeModifier> getAttributeModifiers(EntityLivingBase entity) {
      Multimap<String, AttributeModifier> multimap = HashMultimap.create();
      UUID uuid = UUID.fromString("CC2C5" + this.id + "D3-46" + this.id + "C-4F78-C556-9151A11B1" + this.id + "AF");
      int amp = AdvancedMobEffects.deserealizeAmplifier(AdvancedMobEffects.getEffects(entity, this));
      multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(uuid, "max health modifier", -1.0 * amp, 0));
      return multimap;
   }

   public void addEffect(EntityLivingBase entity) {
      NBTTagCompound data = entity.getEntityData();
      if (data != null) {
         boolean has = data.hasKey("adv_mob_effects");
         NBTTagCompound effects = data.getCompoundTag("adv_mob_effects");
         if (!has) {
            data.setTag("adv_mob_effects", effects);
         }

         if (effects != null) {
            if (!effects.hasKey(this.name)) {
               effects.setTag(this.name, new NBTTagCompound());
            }

            NBTTagCompound effect = effects.getCompoundTag(this.name);
            effect.setInteger("amplifier", 0);
            if (this.hasAttributes) {
               this.applyAttributes(entity);
            }
         }
      }
   }
}
