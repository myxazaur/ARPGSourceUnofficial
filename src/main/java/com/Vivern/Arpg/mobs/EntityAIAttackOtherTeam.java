//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.Team;
import com.google.common.base.Predicate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;

public class EntityAIAttackOtherTeam<T extends EntityLivingBase> extends EntityAITarget {
   protected final Class<T> targetClass;
   private final int targetChance;
   protected final net.minecraft.entity.ai.EntityAINearestAttackableTarget.Sorter sorter;
   protected final Predicate<? super T> targetEntitySelector;
   protected T targetEntity;

   public EntityAIAttackOtherTeam(EntityCreature creature, Class<T> classTarget, boolean checkSight) {
      this(creature, classTarget, checkSight, false);
   }

   public EntityAIAttackOtherTeam(EntityCreature creature, Class<T> classTarget, boolean checkSight, boolean onlyNearby) {
      this(creature, classTarget, 10, checkSight, onlyNearby, (Predicate<? super T>)null);
   }

   public EntityAIAttackOtherTeam(EntityCreature creature, Class<T> classTarget, int chance, boolean checkSight) {
      this(creature, classTarget, chance, checkSight, false, (Predicate<? super T>)null);
   }

   public EntityAIAttackOtherTeam(
      EntityCreature creature, Class<T> classTarget, int chance, boolean checkSight, boolean onlyNearby, @Nullable final Predicate<? super T> targetSelector
   ) {
      super(creature, checkSight, onlyNearby);
      this.targetClass = classTarget;
      this.targetChance = chance;
      this.sorter = new net.minecraft.entity.ai.EntityAINearestAttackableTarget.Sorter(creature);
      this.setMutexBits(1);
      this.targetEntitySelector = new Predicate<T>() {
         public boolean apply(@Nullable T p_apply_1_) {
            if (p_apply_1_ == null) {
               return false;
            } else if (targetSelector != null && !targetSelector.apply(p_apply_1_)) {
               return false;
            } else {
               return !EntitySelectors.NOT_SPECTATING.apply(p_apply_1_) ? false : EntityAIAttackOtherTeam.this.isSuitableTarget(p_apply_1_, false);
            }
         }
      };
   }

   protected boolean isSuitableTarget(EntityLivingBase target, boolean includeInvincibles) {
      return super.isSuitableTarget(target, includeInvincibles) && !Team.isOnSameTeam(target, this.taskOwner);
   }

   public boolean shouldExecute() {
      if (this.taskOwner instanceof AbstractMob) {
         AbstractMob amob = (AbstractMob)this.taskOwner;
         if (!amob.isAgressive) {
            return false;
         }

         if (amob.ownerName != null && amob.owner == null) {
            return false;
         }
      }

      if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0) {
         return false;
      } else {
         List<T> list = this.taskOwner
            .world
            .getEntitiesWithinAABB(this.targetClass, this.getTargetableArea(this.getTargetDistance()), this.targetEntitySelector);
         if (list.isEmpty()) {
            return false;
         } else {
            Collections.sort(list, this.sorter);
            this.targetEntity = list.get(0);
            return true;
         }
      }
   }

   protected AxisAlignedBB getTargetableArea(double targetDistance) {
      return this.taskOwner.getEntityBoundingBox().grow(targetDistance, targetDistance, targetDistance);
   }

   public void startExecuting() {
      this.taskOwner.setAttackTarget(this.targetEntity);
      super.startExecuting();
   }

   public static class Sorter implements Comparator<Entity> {
      private final Entity entity;

      public Sorter(Entity entityIn) {
         this.entity = entityIn;
      }

      public int compare(Entity p_compare_1_, Entity p_compare_2_) {
         double d0 = this.entity.getDistanceSq(p_compare_1_);
         double d1 = this.entity.getDistanceSq(p_compare_2_);
         if (d0 < d1) {
            return -1;
         } else {
            return d0 > d1 ? 1 : 0;
         }
      }
   }
}
