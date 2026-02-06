package com.Vivern.Arpg.potions;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.UUID;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.nbt.NBTTagCompound;

public class AdvancedMobEffect {
   public boolean deathCure = true;
   public final String name;
   public boolean stackable = false;
   public final int id;
   public boolean hasAttributes = false;

   public AdvancedMobEffect(String name, int id) {
      this.name = name;
      this.id = id;
      AdvancedMobEffects.registerlist.add(this);
   }

   public void tick(EntityLivingBase entity, NBTTagCompound effect) {
   }

   public Multimap<String, AttributeModifier> getAttributeModifiers(EntityLivingBase entity) {
      Multimap<String, AttributeModifier> multimap = HashMultimap.create();
      UUID uuid = UUID.fromString("CC2C5" + this.id + "D3-46" + this.id + "C-4F78-C556-9151A11B1" + this.id + "AF");
      return multimap;
   }

   public void applyAttributes(EntityLivingBase entity) {
      if (!entity.world.isRemote) {
         entity.getAttributeMap().applyAttributeModifiers(this.getAttributeModifiers(entity));
      }
   }

   public void removeAttributes(EntityLivingBase entity) {
      if (!entity.world.isRemote) {
         entity.getAttributeMap().removeAttributeModifiers(this.getAttributeModifiers(entity));
      }
   }

   public void addEffect(EntityLivingBase entity, int dur, int amp) {
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
            effect.setInteger("duration", dur);
            effect.setInteger("amplifier", amp);
            if (this.hasAttributes) {
               this.applyAttributes(entity);
            }
         }
      }
   }

   public void removeEffect(EntityLivingBase entity) {
      NBTTagCompound data = entity.getEntityData();
      if (data != null && data.hasKey("adv_mob_effects")) {
         NBTTagCompound effects = data.getCompoundTag("adv_mob_effects");
         if (effects.hasKey(this.name)) {
            effects.removeTag(this.name);
            if (this.hasAttributes) {
               this.removeAttributes(entity);
            }
         }
      }
   }
}
