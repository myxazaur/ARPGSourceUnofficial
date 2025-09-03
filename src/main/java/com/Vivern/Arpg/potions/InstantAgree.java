//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.potions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;

public class InstantAgree extends Potion {
   protected InstantAgree(boolean isBadEffectIn, int liquidColorIn) {
      super(isBadEffectIn, liquidColorIn);
      this.setRegistryName("arpg:instant_agree");
      this.setPotionName("Instant_Agree");
   }

   public boolean hasStatusIcon() {
      return false;
   }

   public boolean isInstant() {
      return true;
   }

   public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entityLivingBase, int amplifier, double health) {
      if (entityLivingBase instanceof EntityCreature && indirectSource != null && indirectSource instanceof EntityLivingBase) {
         EntityCreature creature = (EntityCreature)entityLivingBase;
         if (creature.getAttackTarget() == null) {
            creature.attackEntityFrom(DamageSource.causeIndirectMagicDamage(indirectSource, source), 0.0F);
            creature.setAttackTarget((EntityLivingBase)indirectSource);
         }
      }
   }
}
