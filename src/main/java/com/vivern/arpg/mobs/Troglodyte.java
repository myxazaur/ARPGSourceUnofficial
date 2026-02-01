package com.vivern.arpg.mobs;

import com.vivern.arpg.main.Sounds;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class Troglodyte extends AbstractMob {
   public Troglodyte(World worldIn) {
      super(worldIn, 0.7F, 1.8F);
      this.hurtSound = Sounds.troglodyte_hurt;
      this.deathSound = Sounds.troglodyte_dead;
      this.livingSound = Sounds.troglodyte_living;
      this.setattributes(25.0, 22.0, 5.0, 0.3, 3.0, 0.0, 0.0, 0.0, 0.0, 0.0);
      this.setDropMoney(-2, 5, 0.8F);
   }

   protected void initEntityAI() {
      this.tasks.addTask(1, new EntityAISwimming(this));
      this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0, false));
      this.tasks.addTask(3, new EntityAIWanderAvoidWater(this, 0.8));
      this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(4, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
      this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
   }
}
