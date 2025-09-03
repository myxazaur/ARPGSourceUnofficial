//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.Team;
import java.util.List;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.AxisAlignedBB;

public class EntityAIAABBAttack extends EntityAIBase {
   public EntityCreature entity;
   public int attackTimer = 0;
   public int maxCooldown = 30;
   public float grow = 0.0F;

   public EntityAIAABBAttack(EntityCreature entity, int maxCooldown, float grow) {
      this.entity = entity;
      this.maxCooldown = maxCooldown;
      this.grow = grow;
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null;
   }

   public void updateTask() {
      EntityLivingBase attackTarg = this.entity.getAttackTarget();
      this.attackTimer--;
      if (attackTarg != null && !this.entity.world.isRemote && this.attackTimer <= 0 && this.entity.ticksExisted % 2 == 0) {
         AxisAlignedBB axisalignedbb = this.entity
            .getEntityBoundingBox()
            .expand(this.grow * 2.0F, this.grow * 2.0F, this.grow * 2.0F)
            .offset(-this.grow, -this.grow, -this.grow);
         List<EntityLivingBase> list = this.entity.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
         if (!list.isEmpty()) {
            boolean suc = false;

            for (EntityLivingBase entitylivingbase : list) {
               if (Team.checkIsOpponent(this.entity, entitylivingbase)) {
                  suc = true;
                  this.entity.attackEntityAsMob(entitylivingbase);
               }
            }

            if (suc) {
               this.attackTimer = this.maxCooldown;
            }
         }
      }
   }
}
