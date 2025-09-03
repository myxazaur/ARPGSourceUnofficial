//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.potions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "arpg"
)
public class AdvancedMobEffects {
   public static List<AdvancedMobEffect> registerlist = new ArrayList<>();
   public static ABloodDrain BLOOD_DRAIN = new ABloodDrain();
   public static AVampiricHeartEffect VAMPIRIC_HEART_EFFECT = new AVampiricHeartEffect();

   public static void init() {
      registerlist.add(BLOOD_DRAIN);
      registerlist.add(VAMPIRIC_HEART_EFFECT);
   }

   public static void updateEffects(EntityLivingBase entity) {
      NBTTagCompound data = entity.getEntityData();
      if (data != null) {
         NBTTagCompound effects = data.getCompoundTag("adv_mob_effects");
         if (effects != null) {
            for (AdvancedMobEffect adveffect : registerlist) {
               if (effects.hasKey(adveffect.name) && !adveffect.stackable) {
                  adveffect.tick(entity, effects.getCompoundTag(adveffect.name));
               }
            }
         }
      }
   }

   public static boolean hasEffect(EntityLivingBase entity, AdvancedMobEffect checkeffect) {
      NBTTagCompound data = entity.getEntityData();
      if (data != null) {
         NBTTagCompound effects = data.getCompoundTag("adv_mob_effects");
         if (effects != null) {
            return effects.hasKey(checkeffect.name);
         }
      }

      return false;
   }

   public static NBTTagCompound getEffects(EntityLivingBase entity, AdvancedMobEffect effect) {
      NBTTagCompound data = entity.getEntityData();
      if (data != null) {
         NBTTagCompound effects = data.getCompoundTag("adv_mob_effects");
         if (effects != null) {
            return effects.getCompoundTag(effect.name);
         }
      }

      return null;
   }

   public static int deserealizeDuration(NBTTagCompound effect) {
      return effect.hasKey("duration") ? effect.getInteger("duration") : 0;
   }

   public static int deserealizeAmplifier(NBTTagCompound effect) {
      return effect.hasKey("amplifier") ? effect.getInteger("amplifier") : 1;
   }

   public static Entity getEntityByid(World world, UUID id) {
      for (Entity e : world.loadedEntityList) {
         if (e.getUniqueID().equals(id)) {
            return e;
         }
      }

      return null;
   }

   public static void decreaseDuration(NBTTagCompound effect) {
      if (effect.hasKey("duration")) {
         effect.setInteger("duration", effect.getInteger("duration") - 1);
      }
   }

   public static void addToAmplifier(NBTTagCompound effect, int value) {
      if (effect.hasKey("amplifier")) {
         effect.setInteger("amplifier", effect.getInteger("amplifier") + value);
      }
   }
}
