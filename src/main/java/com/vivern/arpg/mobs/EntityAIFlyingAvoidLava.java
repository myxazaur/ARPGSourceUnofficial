package com.vivern.arpg.mobs;

import com.vivern.arpg.main.SuperKnockback;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class EntityAIFlyingAvoidLava extends EntityAIBase {
   public EntityCreature entity;
   public float speed = 0.3F;

   public EntityAIFlyingAvoidLava(EntityCreature entity) {
      this.entity = entity;
   }

   public boolean shouldExecute() {
      return true;
   }

   public void updateTask() {
      for (int ii = 0; ii < 3; ii++) {
         for (int rr = 0; rr < 3; rr++) {
            for (int zz = 0; zz < 3; zz++) {
               BlockPos pos1 = this.entity.getPosition().add(-1, -1, -1).add(ii, rr, zz);
               if (this.entity.world.getBlockState(pos1).getBlock() == Blocks.FLOWING_LAVA
                  || this.entity.world.getBlockState(pos1).getBlock() == Blocks.LAVA) {
                  SuperKnockback.applyMove(this.entity, this.speed, pos1.getX() + 0.5, pos1.getY() + 0.5, pos1.getZ() + 0.5);
               }
            }
         }
      }
   }
}
