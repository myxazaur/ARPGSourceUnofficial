package com.Vivern.Arpg.potions;

import com.Vivern.Arpg.main.Mana;
import java.util.UUID;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

public class ABloodDrain extends AdvancedMobEffect {
   public ABloodDrain() {
      super("Blood drain", 0);
   }

   @Override
   public void tick(EntityLivingBase entity, NBTTagCompound effect) {
      int dur = AdvancedMobEffects.deserealizeDuration(effect);
      AdvancedMobEffects.decreaseDuration(effect);
      if (dur % 20 == 0) {
         int amp = AdvancedMobEffects.deserealizeAmplifier(effect);
         entity.attackEntityFrom(DamageSource.MAGIC, 3 + amp);
         if (effect.hasKey("owner")) {
            EntityPlayer owner = entity.world.getPlayerEntityByUUID(UUID.fromString(effect.getString("owner")));
            if (owner != null) {
               Mana.giveMana(owner, 1.0F + amp / 2.0F);
            }
         }
      }

      if (dur <= 0) {
         this.removeEffect(entity);
      }
   }

   public void addEffect(EntityLivingBase entity, int dur, int amp, EntityLivingBase owner) {
      NBTTagCompound data = entity.getEntityData();
      if (data != null) {
         boolean has = data.hasKey("adv_mob_effects");
         NBTTagCompound effects = data.getCompoundTag("adv_mob_effects");
         if (!has) {
            data.setTag("adv_mob_effects", effects);
         }

         if (effects != null && owner != null) {
            if (!effects.hasKey(this.name)) {
               effects.setTag(this.name, new NBTTagCompound());
            }

            NBTTagCompound effect = effects.getCompoundTag(this.name);
            effect.setInteger("duration", dur);
            effect.setInteger("amplifier", amp);
            effect.setString("owner", owner.getCachedUniqueIdString());
         }
      }
   }
}
