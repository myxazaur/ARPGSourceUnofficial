//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.SuperKnockback;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class EntityAIEasyFlyingAttack extends EntityAIBase {
   public EntityCreature entity;
   public float speed = 0.15F;
   public float friction = 1.0F;

   public EntityAIEasyFlyingAttack(EntityCreature entity, float speed) {
      this.entity = entity;
      this.speed = speed;
   }

   public EntityAIEasyFlyingAttack(EntityCreature entity, float speed, float friction) {
      this.entity = entity;
      this.speed = speed;
      this.friction = friction;
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null;
   }

   public void updateTask() {
      if (this.entity.getAttackTarget() != null) {
         Vec3d vec = this.entity.getAttackTarget().getPositionVector().add(0.0, this.entity.getAttackTarget().height / 2.0F, 0.0);
         this.entity.motionX = this.entity.motionX / this.friction;
         this.entity.motionY = this.entity.motionY / this.friction;
         this.entity.motionZ = this.entity.motionZ / this.friction;
         SuperKnockback.applyMove(this.entity, -this.speed, vec.x, vec.y, vec.z);
         this.entity.getLookHelper().setLookPosition(vec.x, vec.y, vec.z, 20.0F, 20.0F);
         this.entity.rotationYaw = (float)(MathHelper.atan2(this.entity.motionX, this.entity.motionZ) * (180.0 / Math.PI));
      }
   }
}
