//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.SuperKnockback;
import java.util.Random;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;

public class EntityAICorrupterIdle extends EntityAIBase {
   public final AbstractMob entity;
   public final Random rand;
   public double idlePosX = 0.0;
   public double idlePosY = 0.0;
   public double idlePosZ = 0.0;
   public boolean posesSetted = false;
   public float savedSpeed = 0.0F;
   private double lookX;
   private double lookZ;
   private int idleTime;

   public EntityAICorrupterIdle(AbstractMob mob) {
      this.entity = mob;
      this.rand = mob.getRNG();
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() == null;
   }

   public boolean shouldContinueExecuting() {
      return this.shouldExecute();
   }

   public void resetTask() {
      super.resetTask();
      this.entity.resetActiveHand();
   }

   public void updateTask() {
      EntityLivingBase entitylivingbase = this.entity.getAttackTarget();
      if (entitylivingbase == null) {
         if (this.rand.nextFloat() < 0.005) {
            this.findPos();
            BlockPos pos = new BlockPos(this.idlePosX, this.idlePosY, this.idlePosZ);
            if (this.entity.world.isBlockLoaded(pos) && this.entity.world.isAirBlock(pos)) {
               this.posesSetted = true;
               this.savedSpeed = (float)this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() / 4.0F;
            }
         }

         if (this.posesSetted) {
            SuperKnockback.applyMove(this.entity, -this.savedSpeed, this.idlePosX, this.idlePosY, this.idlePosZ);
            if (this.entity.getDistance(this.idlePosX, this.idlePosY, this.idlePosZ) < 0.5) {
               this.posesSetted = false;
            }
         }

         if (this.idleTime >= 0) {
            this.idleTime--;
            this.entity
               .getLookHelper()
               .setLookPosition(
                  this.entity.posX + this.lookX,
                  this.entity.posY + this.entity.getEyeHeight(),
                  this.entity.posZ + this.lookZ,
                  this.entity.getHorizontalFaceSpeed(),
                  this.entity.getVerticalFaceSpeed()
               );
         }

         if (this.rand.nextFloat() < 0.02F) {
            double d0 = (Math.PI * 2) * this.rand.nextDouble();
            this.lookX = Math.cos(d0);
            this.lookZ = Math.sin(d0);
            this.idleTime = 20 + this.rand.nextInt(20);
         }
      }
   }

   public void findPos() {
      this.idlePosX = this.entity.posX + this.rand.nextGaussian() * 6.0;
      this.idlePosZ = this.entity.posZ + this.rand.nextGaussian() * 6.0;
      if (this.entity.world.isBlockLoaded(new BlockPos(this.idlePosX, this.entity.posY, this.idlePosZ))) {
         for (int i = (int)(this.entity.posY + 8.0); i > this.entity.posY - 8.0; i--) {
            BlockPos pos = new BlockPos(this.idlePosX, i, this.idlePosZ);
            if (this.entity.world.isAirBlock(pos) && !this.entity.world.isAirBlock(pos.down(3))) {
               this.idlePosY = i;
               return;
            }
         }
      }

      this.idlePosY = this.entity.posY - 8.0;
   }
}
