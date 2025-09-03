//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.SuperKnockback;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class BossAISpine extends EntityAIBase {
   public EntityCreature entity;
   public Vec3d vec;
   public int delay = 30;
   public int frequency = 90;
   public float radius = 9.0F;
   public float speed = 0.25F;
   public float radiusVar = 5.0F;
   public float yOffset = 0.0F;
   public float resetDistance = 1.5F;
   public float attackChance = 0.4F;
   public float friction = 1.15F;
   public boolean checkInBlock = false;

   public BossAISpine(
      EntityCreature entity,
      int frequency,
      float radius,
      float speed,
      float radiusVar,
      float yOffset,
      float resetDistance,
      float attackChance,
      float friction,
      boolean checkInBlock
   ) {
      this.entity = entity;
      this.vec = entity.getPositionVector();
      this.frequency = frequency;
      this.radius = radius;
      this.speed = speed;
      this.radiusVar = radiusVar;
      this.yOffset = yOffset;
      this.resetDistance = resetDistance;
      this.delay = frequency;
      this.attackChance = attackChance;
      this.friction = friction;
      this.checkInBlock = checkInBlock;
   }

   public BossAISpine(EntityCreature entity) {
      this.entity = entity;
      this.vec = entity.getPositionVector();
   }

   public boolean shouldExecute() {
      return true;
   }

   public void updateTask() {
      if (this.entity.ticksExisted % 20 == 0) {
         double agrr = 50.0;
         AxisAlignedBB agraabb = new AxisAlignedBB(
            this.entity.posX - agrr,
            this.entity.posY - agrr,
            this.entity.posZ - agrr,
            this.entity.posX + agrr,
            this.entity.posY + agrr,
            this.entity.posZ + agrr
         );
         EntityLivingBase targpl = (EntityLivingBase)this.entity.world.findNearestEntityWithinAABB(EntityPlayer.class, agraabb, this.entity);
         this.entity.setAttackTarget(targpl);
      }

      if (this.entity.getAttackTarget() != null) {
         if (this.delay <= 0) {
            if (this.entity.getRNG().nextFloat() > this.attackChance) {
               if (this.yOffset == 0.0F) {
                  this.vec = GetMOP.findRandPosNearEntity(this.entity.getAttackTarget(), this.radius + this.entity.getRNG().nextFloat() * this.radiusVar);
                  if (this.checkInBlock) {
                     double radr = 0.1;
                     AxisAlignedBB aabb = new AxisAlignedBB(
                        this.vec.x - radr,
                        this.vec.y - radr,
                        this.vec.z - radr,
                        this.vec.x + radr,
                        this.vec.y + radr,
                        this.vec.z + radr
                     );
                     if (this.entity.world.collidesWithAnyBlock(aabb)) {
                        this.delay = 0;
                     }
                  }
               } else {
                  this.vec = GetMOP.findRandPosNearPoint(
                     this.entity.getAttackTarget().posX,
                     this.entity.getAttackTarget().posY + this.yOffset,
                     this.entity.getAttackTarget().posZ,
                     this.radius + this.entity.getRNG().nextFloat() * this.radiusVar
                  );
                  if (this.checkInBlock) {
                     double radr = 0.1;
                     AxisAlignedBB aabb = new AxisAlignedBB(
                        this.vec.x - radr,
                        this.vec.y - radr,
                        this.vec.z - radr,
                        this.vec.x + radr,
                        this.vec.y + radr,
                        this.vec.z + radr
                     );
                     if (this.entity.world.collidesWithAnyBlock(aabb)) {
                        this.delay = 0;
                     }
                  }
               }
            } else {
               this.vec = this.entity.getAttackTarget().getPositionVector();
            }

            this.delay = this.frequency;
         }

         this.delay--;
         if (this.entity.getDistance(this.vec.x, this.vec.y, this.vec.z) < this.resetDistance) {
            this.delay = 0;
         }

         this.entity.motionX = this.entity.motionX / this.friction;
         this.entity.motionY = this.entity.motionY / this.friction;
         this.entity.motionZ = this.entity.motionZ / this.friction;
         SuperKnockback.applyMove(
            this.entity, -this.speed, this.vec.x, this.vec.y + this.entity.getAttackTarget().height / 2.0F, this.vec.z
         );
      }
   }
}
