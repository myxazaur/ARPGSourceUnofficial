package com.vivern.arpg.mobs;

import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.SuperKnockback;
import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EntityAIRayLogicJump extends EntityAIBase {
   public EntityCreature entity;
   public Predicate ignoreblocks = new Predicate<IBlockState>() {
      public boolean apply(@Nullable IBlockState block) {
         return false;
      }
   };

   public EntityAIRayLogicJump(EntityCreature entity) {
      this.entity = entity;
   }

   public boolean shouldExecute() {
      return true;
   }

   public void updateTask() {
      if (this.entity.ticksExisted % 20 == 0) {
         Vec3d fromPos = this.entity.getPositionEyes(1.0F);
         Vec3d vec3d1 = this.entity.getLook(1.0F);
         Vec3d lookPos = fromPos.add(vec3d1.x * 1.3, vec3d1.y * 1.3, vec3d1.z * 1.3);
         Vec3d lookPosforjump = fromPos.add(vec3d1.x * 10.0, vec3d1.y * 10.0, vec3d1.z * 10.0);
         Vec3d impact = GetMOP.logicRayTraceIgnoreMobs(this.entity.world, fromPos, lookPos, 1.0F, this.ignoreblocks, false);
         BlockPos pos = new BlockPos(impact);
         BlockPos posd1 = pos.down();
         if (this.entity.world.getBlockState(posd1).getCollisionBoundingBox(this.entity.world, posd1) == null) {
            BlockPos posd2 = posd1.down();
            if (this.entity.world.getBlockState(posd2).getCollisionBoundingBox(this.entity.world, posd2) == null) {
               this.jump(lookPosforjump);
            }
         }
      }
   }

   public void jump(Vec3d lookPos) {
      if (this.entity.onGround) {
         SuperKnockback.applyMove(this.entity, -15.0F, lookPos.x, 0.0, lookPos.z);
         this.entity.motionY = 0.6;
         this.entity.velocityChanged = true;
      }
   }
}
