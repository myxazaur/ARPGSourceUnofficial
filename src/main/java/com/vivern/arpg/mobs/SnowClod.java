package com.vivern.arpg.mobs;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class SnowClod extends EntityMob {
   public SnowClod(World worldIn) {
      super(worldIn);
      this.setSize(0.85F, 0.85F);
   }

   public void fall(float distance, float damageMultiplier) {
      super.fall(distance, damageMultiplier);
   }

   public void onUpdate() {
      super.onUpdate();
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0);
      this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0);
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
   }

   protected void initEntityAI() {
      this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0, false));
      this.tasks.addTask(2, new EntityAIRayLogicJump(this));
      this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
      this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
   }
}
