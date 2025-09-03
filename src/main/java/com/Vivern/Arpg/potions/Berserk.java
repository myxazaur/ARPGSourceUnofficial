//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.potions;

import com.Vivern.Arpg.main.PropertiesRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;

public class Berserk extends AdvancedPotion {
   public Berserk() {
      super(false, 16730431, 1, false);
      this.setRegistryName("arpg:berserk");
      this.setPotionName("Berserk");
      this.setIconIndex(24, 1);
      this.registerPotionAttributeModifier(PropertiesRegistry.JUMP_HEIGHT, MathHelper.getRandomUUID().toString(), 0.1F, 0);
      this.registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED, MathHelper.getRandomUUID().toString(), 0.8F, 0);
      this.registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, MathHelper.getRandomUUID().toString(), 1.5, 2);
      this.registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, MathHelper.getRandomUUID().toString(), 0.04F, 0);
   }

   @Override
   public float onHurtThis(EntityLivingBase entityOnEffect, DamageSource source, float damage, PotionEffect effect) {
      return damage * (2 + effect.getAmplifier());
   }
}
