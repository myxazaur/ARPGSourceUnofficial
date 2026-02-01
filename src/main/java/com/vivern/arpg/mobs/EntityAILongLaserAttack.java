package com.vivern.arpg.mobs;

import com.vivern.arpg.main.GetMOP;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;

public class EntityAILongLaserAttack extends EntityAIBase {
   public EntityCreature entity;
   public int attackTimer = 40;
   public int attackDuration = 0;
   public int maxCooldown = 30;
   public double dist = 15.0;
   public double sizeANDstep = 0.05;
   public int maxDuration = 40;
   public boolean checkWalls = true;

   public EntityAILongLaserAttack(EntityCreature entity, int maxCooldown, double dist, double sizeANDstep, int maxDuration, boolean checkWalls) {
      this.entity = entity;
      this.maxCooldown = maxCooldown;
      this.dist = dist;
      this.sizeANDstep = sizeANDstep;
      this.maxDuration = maxDuration;
      this.checkWalls = checkWalls;
   }

   public boolean shouldExecute() {
      if (this.checkWalls) {
         Vec3d vec3d = this.entity.getPositionEyes(1.0F);
         Vec3d vec3d1 = this.entity.getLook(1.0F);
         Vec3d vec3d2 = vec3d.add(vec3d1.x * this.dist, vec3d1.y * this.dist, vec3d1.z * this.dist);
         RayTraceResult raytraceresult = this.entity.world.rayTraceBlocks(vec3d, vec3d2, false, true, false);
         return raytraceresult == null ? false : raytraceresult.typeOfHit != Type.BLOCK && this.entity.getAttackTarget() != null;
      } else {
         return this.entity.getAttackTarget() != null
            && this.entity.getDistance(this.entity.getAttackTarget()) < this.entity.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
      }
   }

   public void updateTask() {
      if (!this.entity.world.isRemote) {
         EntityLivingBase attackTarg = this.entity.getAttackTarget();
         if (this.attackDuration <= 0) {
            this.attackTimer--;
         }

         if (attackTarg != null && this.attackDuration > 0) {
            this.attackDuration--;
            Vec3d vec = GetMOP.PosRayTrace(this.dist, 1.0F, this.entity, this.sizeANDstep, this.sizeANDstep);
            if (this.entity instanceof ILongLaserAttackMob) {
               ((ILongLaserAttackMob)this.entity).onLaserTick(vec, this.attackDuration);
            }

            this.attackTimer = this.maxCooldown;
         }

         if (this.attackTimer <= 0) {
            this.attackDuration = this.maxDuration;
         }
      }
   }
}
