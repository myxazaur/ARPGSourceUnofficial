//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.SuperKnockback;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class EntityAIEasyRunaway extends EntityAIBase {
   public boolean enable = true;
   public EntityCreature entity;
   public float speed = 0.15F;
   public float hpProportion = 0.5F;

   public EntityAIEasyRunaway(EntityCreature entity, float speed, float hpProportion) {
      this.entity = entity;
      this.speed = speed;
      this.hpProportion = hpProportion;
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null && this.entity.getHealth() < this.entity.getMaxHealth() * this.hpProportion && this.enable;
   }

   public void updateTask() {
      Vec3d vec = this.entity.getAttackTarget().getPositionVector().add(0.0, this.entity.getAttackTarget().height / 2.0F, 0.0);
      SuperKnockback.applyMove(this.entity, this.speed, vec.x, vec.y, vec.z);
      this.entity.getLookHelper().setLookPosition(vec.x, vec.y, vec.z, 20.0F, 20.0F);
      this.entity.rotationYaw = (float)(MathHelper.atan2(this.entity.motionX, this.entity.motionZ) * (180.0 / Math.PI));
   }
}
