//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.potions;

import com.Vivern.Arpg.main.Team;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class Insane extends Potion {
   protected Insane(boolean isBadEffectIn, int liquidColorIn) {
      super(isBadEffectIn, liquidColorIn);
      this.setRegistryName("arpg:insane");
      this.setPotionName("Insane");
      this.setIconIndex(21, 1);
   }

   public boolean hasStatusIcon() {
      Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("arpg:textures/potions.png"));
      return true;
   }

   public void performEffect(EntityLivingBase entityLivingBase, int amplifier) {
      if (entityLivingBase instanceof EntityCreature) {
         EntityCreature creature = (EntityCreature)entityLivingBase;
         PotionEffect eff = entityLivingBase.getActivePotionEffect(this);
         if (eff != null && eff.getDuration() < 15) {
            creature.setAttackTarget(null);
         }

         if (creature.getAttackTarget() == null || creature.getAttackTarget().isDead || amplifier > 0) {
            World world = entityLivingBase.world;
            double damageRadius = entityLivingBase.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
            AxisAlignedBB axisalignedbb = entityLivingBase.getEntityBoundingBox()
               .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
               .offset(-damageRadius, -damageRadius, -damageRadius);
            List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
            double maxdist = Double.MAX_VALUE;
            EntityLivingBase finalentity = null;
            if (!list.isEmpty()) {
               for (EntityLivingBase entitylivingb : list) {
                  if ((amplifier != 2 || entitylivingb.getClass() != entityLivingBase.getClass())
                     && Team.checkIsOpponent(entityLivingBase, entitylivingb)
                     && creature.getEntitySenses().canSee(entitylivingb)) {
                     double dist = entitylivingb.getDistance(entityLivingBase);
                     if (dist < maxdist) {
                        maxdist = dist;
                        finalentity = entitylivingb;
                     }
                  }
               }
            }

            if (finalentity != null) {
               creature.setAttackTarget(finalentity);
            }
         }
      }
   }

   public boolean isReady(int duration, int amplifier) {
      return duration % 10 == 0;
   }
}
