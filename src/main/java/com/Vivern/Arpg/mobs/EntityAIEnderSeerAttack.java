package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.entity.EnderSeerProjectile;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIEnderSeerAttack extends EntityAIBase {
   public EntityCreature entity;
   public int delay = 5;
   public int bigDelay = 50;
   public int count = 3;

   public EntityAIEnderSeerAttack(EntityCreature entity) {
      this.entity = entity;
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null && this.entity.getDistance(this.entity.getAttackTarget()) < 80.0F;
   }

   public void updateTask() {
      this.bigDelay--;
      if (this.bigDelay < 1) {
         this.bigDelay = 100;
         this.count = 3;
         this.entity.world.setEntityState(this.entity, (byte)9);
      }

      if (this.count > 0 && this.entity.ticksExisted % 5 == 0) {
         if (!this.entity.world.isRemote) {
            EnderSeerProjectile bolt = new EnderSeerProjectile(this.entity.world, this.entity, this.entity.getAttackTarget());
            bolt.shoot(this.entity, this.entity.rotationPitch, this.entity.rotationYaw, 0.0F, 1.4F, 13.8F);
            this.entity.world.spawnEntity(bolt);
         }

         this.count--;
      }
   }
}
