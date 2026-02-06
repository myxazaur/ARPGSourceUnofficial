package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.entity.AbstractGlyphid;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;

public class EntityAILavaGlyphid extends EntityAIGlyphid {
   public static long recalcLastTime1 = 0L;
   public static long longWayCall1 = 0L;
   public static int longWayCounter1 = 0;

   public EntityAILavaGlyphid(AbstractGlyphid entity) {
      super(entity);
      this.glyphidAiType = 1;
   }

   @Override
   public void updateTask() {
      EntityLivingBase attackTarg = this.entity.getAttackTarget();
      if (attackTarg != null) {
         if (this.path != null) {
            this.moveOnPath(
               this.entity,
               -this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue(),
               this.path.getX() + 0.5,
               this.path.getY() + (1.0 - this.entity.height) / 2.0,
               this.path.getZ() + 0.5
            );
         }

         int reachDist = (int)this.entity.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
         double distsq = attackTarg.getDistanceSq(this.entity);
         if (distsq <= reachDist * reachDist) {
            if (distsq > 289.0) {
               longWayCall1 = this.entity.world.getTotalWorldTime();
            }

            if (this.entity.world.getTotalWorldTime() > recalcLastTime1 + 20L) {
               recalcLastTime1 = this.entity.world.getTotalWorldTime();
               if (this.entity.world.getTotalWorldTime() < longWayCall1 + 10L) {
                  longWayCounter1++;
               }

               if (longWayCounter1 < 6) {
                  this.recalcPathToTarget(this.entity.world, attackTarg, 19, 17);
               } else {
                  this.recalcPathToTarget(this.entity.world, attackTarg, reachDist, 32);
                  longWayCounter1 = 0;
               }
            }
         }
      }
   }
}
