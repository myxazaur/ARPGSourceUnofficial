//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.Sounds;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class Gnater extends AbstractMob {
   public Gnater(World worldIn) {
      super(worldIn, 0.8F, 0.8F);
      this.hurtSound = Sounds.gnater_hurt;
      this.deathSound = Sounds.gnater_dead;
      this.setattributes(15.0, 28.0, 3.0, 0.07, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0);
      this.setNoGravity(true);
      this.setDropMoney(-2, 2, 1.0F);
      this.experienceValue = 3;
   }

   public float getCollisionBorderSize() {
      return 0.2F;
   }

   public void fall(float distance, float damageMultiplier) {
   }

   protected void initEntityAI() {
      this.tasks.addTask(1, new EntityAIRayLogicFly(this));
      this.tasks.addTask(2, new EntityAIAABBAttack(this, 30, 0.2F));
      this.tasks.addTask(4, new EntityAICorrupterIdle(this));
      this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
      this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
      this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
   }
}
