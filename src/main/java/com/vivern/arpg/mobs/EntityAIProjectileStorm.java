package com.vivern.arpg.mobs;

import com.vivern.arpg.main.Team;
import java.util.HashMap;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EntityAIProjectileStorm extends EntityAIBase {
   public AbstractMob entity;
   public int maxCooldown = 30;
   public float radius = 0.0F;
   public boolean gaussianRadius = false;
   public int minCount;
   public int addCount;
   public int height;
   public boolean selfCast;
   public int maxDelay;
   public byte sendId;
   public byte specialAttackId = 1;
   public double distanceSq = 0.0;
   public int heightRandAdd;
   public int attackTimer = 0;
   public int delay = 0;
   public Vec3d vector = null;

   public EntityAIProjectileStorm(
      AbstractMob entity,
      double distance,
      int maxCooldown,
      int maxDelay,
      float radius,
      int minCount,
      int maxCount,
      int height,
      int heightRandAdd,
      boolean selfCast,
      byte specialAttackId,
      byte sendId
   ) {
      this.entity = entity;
      this.maxCooldown = maxCooldown;
      this.radius = radius;
      this.specialAttackId = specialAttackId;
      this.minCount = minCount;
      this.addCount = maxCount - minCount + 1;
      this.height = height;
      this.height = height;
      this.selfCast = selfCast;
      this.maxDelay = maxDelay;
      this.sendId = sendId;
      this.distanceSq = distance * distance;
      this.heightRandAdd = heightRandAdd;
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null;
   }

   public void updateTask() {
      EntityLivingBase attackTarg = this.entity.getAttackTarget();
      this.attackTimer--;
      if (attackTarg != null) {
         Random rand = this.entity.getRNG();
         if (this.delay > 0) {
            this.delay++;
         }

         if (this.delay > this.maxDelay && this.vector != null) {
            int count = this.minCount + rand.nextInt(this.addCount);
            int heightmin = attackTarg.world.canSeeSky(attackTarg.getPosition()) ? this.height : 0;

            for (int c = 0; c < count; c++) {
               BlockPos randpos = new BlockPos(
                  this.vector.x + (rand.nextFloat() - 0.5F) * this.radius * 2.0F,
                  this.vector.y,
                  this.vector.z + (rand.nextFloat() - 0.5F) * this.radius * 2.0F
               );
               int hh = this.height + rand.nextInt(this.heightRandAdd);

               for (int yy = 0; yy <= hh; yy++) {
                  randpos = randpos.up();
                  if (yy >= heightmin
                     && (yy == hh || this.entity.world.getBlockState(randpos).getCollisionBoundingBox(this.entity.world, randpos) != null)) {
                     this.entity
                        .specialAttack(randpos.getX() + 0.5, randpos.getY() - 0.5, randpos.getZ() + 0.5, this.specialAttackId);
                     break;
                  }
               }
            }

            this.vector = null;
            this.delay = 0;
         }

         if (this.attackTimer <= 0) {
            double distSq = this.entity.getDistanceSq(attackTarg);
            if (distSq <= this.distanceSq) {
               HashMap<Vec3d, Integer> map = new HashMap<>();
               int maximum = 0;

               for (int i = 0; i < 5; i++) {
                  Vec3d pos = this.selfCast ? this.entity.getPositionVector() : attackTarg.getPositionVector();
                  if (i != 0) {
                     pos = pos.add((rand.nextFloat() - 0.5F) * this.radius * 2.0F, 0.0, (rand.nextFloat() - 0.5F) * this.radius * 2.0F);
                  }

                  int counter = 0;
                  AxisAlignedBB aabb = new AxisAlignedBB(
                     pos.x - this.radius,
                     pos.y - this.radius,
                     pos.z - this.radius,
                     pos.x + this.radius,
                     pos.y + this.radius,
                     pos.z + this.radius
                  );

                  for (EntityLivingBase base : this.entity.world.getEntitiesWithinAABB(EntityLivingBase.class, aabb)) {
                     if (base == attackTarg) {
                        counter += 2;
                     } else if (Team.checkIsOpponent(this.entity, base)) {
                        counter++;
                     }
                  }

                  maximum = Math.max(maximum, counter);
                  map.put(pos, counter);
               }

               for (Entry<Vec3d, Integer> entry : map.entrySet()) {
                  if (entry.getValue() == maximum) {
                     this.vector = entry.getKey();
                     this.delay = 1;
                     this.attackTimer = this.maxCooldown;
                     this.entity.world.setEntityState(this.entity, this.sendId);
                     break;
                  }
               }
            }
         }
      }
   }
}
