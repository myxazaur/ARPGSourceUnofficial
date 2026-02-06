package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.Team;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.AxisAlignedBB;

public class EntityAISegmAABBAttack extends EntityAIBase {
   public AbstractMob entity;
   public int maxCooldown = 30;
   public float grow = 0.0F;

   public EntityAISegmAABBAttack(AbstractMob entity, int maxCooldown, float grow) {
      this.entity = entity;
      this.maxCooldown = maxCooldown;
      this.grow = grow;
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null;
   }

   public void updateTask() {
      AbstractMob owner = this.entity.getOwnerIfSegment();
      if (owner != null) {
         EntityLivingBase attackTarg = owner.getAttackTarget();
         if (attackTarg != null) {
            owner.var1--;
            if (owner.var1 <= 0 && this.entity.ticksExisted % 2 == 0) {
               AxisAlignedBB axisalignedbb = this.entity
                  .getEntityBoundingBox()
                  .expand(this.grow * 2.0F, this.grow * 2.0F, this.grow * 2.0F)
                  .offset(-this.grow, -this.grow, -this.grow);
               List<EntityLivingBase> list = owner.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
               if (!list.isEmpty()) {
                  boolean suc = false;

                  for (EntityLivingBase entitylivingbase : list) {
                     if (Team.checkIsOpponent(owner, entitylivingbase)) {
                        suc = true;
                        owner.attackEntityAsMob(entitylivingbase);
                     }
                  }

                  if (suc) {
                     owner.var1 = this.maxCooldown;
                  }
               }
            }
         }
      }
   }
}
