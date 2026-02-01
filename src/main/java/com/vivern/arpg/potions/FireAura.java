package com.vivern.arpg.potions;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class FireAura extends Potion {
   protected FireAura(boolean isBadEffectIn, int liquidColorIn) {
      super(isBadEffectIn, liquidColorIn);
      this.setRegistryName("arpg:fire_aura");
      this.setPotionName("Fire_Aura");
      this.setIconIndex(12, 1);
   }

   public boolean hasStatusIcon() {
      Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("arpg:textures/potions.png"));
      return true;
   }

   public void performEffect(EntityLivingBase entityLivingBase, int amplifier) {
      World world = entityLivingBase.world;
      double damageRadius = 3.0 + amplifier;
      AxisAlignedBB axisalignedbb = entityLivingBase.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
      if (!list.isEmpty()) {
         for (EntityLivingBase entitylivingb : list) {
            if (entitylivingb != entityLivingBase) {
               IAttributeInstance entityAttribute = entitylivingb.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
               double baseValue = entityAttribute.getBaseValue();
               entityAttribute.setBaseValue(1.0);
               if (entityLivingBase instanceof EntityPlayer) {
                  entitylivingb.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)entityLivingBase), 2 + amplifier);
               } else {
                  entitylivingb.attackEntityFrom(DamageSource.causeMobDamage(entityLivingBase), 2 + amplifier);
               }

               entitylivingb.hurtResistantTime = 0;
               entityAttribute.setBaseValue(baseValue);
            }
         }
      }
   }

   public boolean isReady(int duration, int amplifier) {
      return duration % Math.round((float)(10 / (amplifier + 1) + 5)) == 0;
   }
}
