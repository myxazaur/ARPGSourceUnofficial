//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.SuperKnockback;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class EntityAIFlying extends EntityAIBase {
   public EntityCreature entity;
   public Vec3d vec;
   public int frequency = 30;
   public float radius = 5.0F;
   public float speed = 0.15F;
   public boolean pitch = false;

   public EntityAIFlying(EntityCreature entity, int frequency, float radius, float speed, boolean pitch) {
      this.entity = entity;
      this.vec = entity.getPositionVector();
      this.frequency = frequency;
      this.radius = radius;
      this.speed = speed;
      this.pitch = pitch;
   }

   public EntityAIFlying(EntityCreature entity) {
      this.entity = entity;
      this.vec = entity.getPositionVector();
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null
         ? this.entity.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue() <= this.entity.getDistance(this.entity.getAttackTarget())
         : true;
   }

   public void updateTask() {
      if (this.entity.ticksExisted % this.frequency == 0) {
         this.vec = GetMOP.findRandPosNearEntity(this.entity, this.radius);
      }

      if (this.entity.getDistance(this.vec.x, this.vec.y, this.vec.z) > 0.3) {
         SuperKnockback.applyMove(this.entity, -this.speed, this.vec.x, this.vec.y, this.vec.z);
      }

      this.entity.getLookHelper().setLookPosition(this.vec.x, this.vec.y, this.vec.z, 20.0F, 20.0F);
      Vec3d motion = new Vec3d(-this.entity.motionX, -this.entity.motionY, -this.entity.motionZ);
      Vec3d pitchYaw = GetMOP.Vec3dToPitchYaw(motion);
      this.entity.rotationYaw = (float)MathHelper.wrapDegrees(pitchYaw.y);
      if (this.pitch) {
         this.entity.rotationPitch = (float)MathHelper.wrapDegrees(pitchYaw.x);
      }
   }
}
