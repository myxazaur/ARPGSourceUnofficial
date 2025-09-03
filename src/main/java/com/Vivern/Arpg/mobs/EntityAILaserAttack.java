//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.GetMOP;
import java.util.List;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.DamageSource;

public class EntityAILaserAttack extends EntityAIBase {
   public EntityCreature entity;
   public int delay;
   public int delayRandom;
   public int maxDelay;
   public float damage;
   public float attackDist;

   public EntityAILaserAttack(EntityCreature entity, int maxDelay, int delayRandom, float damage, float dist) {
      this.entity = entity;
      this.setMutexBits(8);
      this.maxDelay = maxDelay;
      this.damage = damage;
      this.delayRandom = delayRandom;
      this.attackDist = dist;
   }

   public boolean shouldExecute() {
      EntityLivingBase entitylivingbase = this.entity.getAttackTarget();
      return entitylivingbase != null && entitylivingbase.isEntityAlive() && this.entity.getDistance(entitylivingbase) <= this.attackDist;
   }

   public void startExecuting() {
   }

   public boolean shouldContinueExecuting() {
      return this.shouldExecute();
   }

   public void resetTask() {
      this.delay = this.maxDelay;
   }

   public void updateTask() {
      this.delay--;
      if (this.delay < 1) {
         EntityLivingBase target = this.entity.getAttackTarget();
         this.entity.getLookHelper().setLookPositionWithEntity(target, 10.0F, 10.0F);
         this.delay = this.maxDelay + this.entity.getRNG().nextInt(this.delayRandom);
         List<EntityLivingBase> list = GetMOP.MopRayTrace(this.attackDist, 1.0F, this.entity, 0.01, 0.01);
         if (!list.isEmpty()) {
            for (EntityLivingBase entitylivingbase : list) {
               if (entitylivingbase != this.entity) {
                  entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage(this.entity), this.damage);
                  entitylivingbase.hurtResistantTime = 0;
               }
            }
         }
      }
   }
}
