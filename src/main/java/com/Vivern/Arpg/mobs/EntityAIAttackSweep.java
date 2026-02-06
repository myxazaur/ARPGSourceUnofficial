package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.Weapons;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAIAttackSweep extends AbstractMob.AbstractAI {
   public boolean enable = true;
   public AbstractMob entity;
   public int maxcooldown = 30;
   public float radius = 0.5F;
   public float length = 2.5F;
   public int maxreflectTime = 10;
   public float endradius = 0.5F;
   public int actualCooldown = 0;
   public int actualReflectTime = 0;
   public boolean attacked = false;
   public boolean canReflect = false;
   public float damageToReflection = 5.0F;
   public float maxdamageBlocked = 10.0F;
   public SoundEvent reflectSound = Sounds.melee_block;
   public float reflectSoundVolume = 0.7F;
   public SoundEvent swoshSound = Sounds.swosh_b;
   public float swoshSoundVolume = 0.7F;
   public SoundEvent hitSound = Sounds.melee_mob_attack;
   public float hitSoundVolume = 0.7F;
   public boolean attackstarted = false;
   public boolean breakBlocks = false;
   public float blockBreakingHardness = 1.0F;
   public boolean triggerOnStart = false;
   public float knockbackOnReflect = 1.0F;
   public boolean avoidDownbreak = true;
   public boolean useYawOffset = false;
   public byte sendId = 0;
   public byte animTriggered = -1;

   public EntityAIAttackSweep(
      AbstractMob entity,
      int maxcooldown,
      float radius,
      float endradius,
      float length,
      int maxreflectTime,
      boolean canReflect,
      float damageToReflection,
      float maxdamageBlocked,
      SoundEvent reflectSound
   ) {
      this.entity = entity;
      this.maxcooldown = maxcooldown;
      this.radius = radius;
      this.length = length;
      this.maxreflectTime = maxreflectTime;
      this.endradius = endradius;
      this.canReflect = canReflect;
      this.damageToReflection = damageToReflection;
      this.reflectSound = reflectSound;
      this.maxdamageBlocked = maxdamageBlocked;
   }

   public EntityAIAttackSweep(AbstractMob entity, int maxcooldown, float radius, float endradius, float length, int maxreflectTime) {
      this.entity = entity;
      this.maxcooldown = maxcooldown;
      this.radius = radius;
      this.length = length;
      this.maxreflectTime = maxreflectTime;
      this.endradius = endradius;
   }

   public EntityAIAttackSweep setknockbackOnReflect(float knockbackOnReflect) {
      this.knockbackOnReflect = knockbackOnReflect;
      return this;
   }

   public EntityAIAttackSweep setBreakBlocks(float blockBreakingHardness) {
      this.breakBlocks = true;
      this.blockBreakingHardness = blockBreakingHardness;
      return this;
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null && this.enable;
   }

   public EntityAIAttackSweep setTriggerOnStart() {
      this.triggerOnStart = true;
      return this;
   }

   @Override
   public float onEntityAttacked(DamageSource source, float amount) {
      if (this.canReflect && this.actualReflectTime > 0 && amount > this.damageToReflection) {
         this.actualReflectTime = 0;
         this.actualCooldown = this.maxcooldown;
         this.attackstarted = false;
         if (this.reflectSound != null) {
            this.entity
               .world
               .playSound(
                  (EntityPlayer)null,
                  this.entity.posX,
                  this.entity.posY,
                  this.entity.posZ,
                  this.reflectSound,
                  SoundCategory.HOSTILE,
                  this.reflectSoundVolume,
                  1.1F + this.entity.getRNG().nextFloat() / 5.0F
               );
         }

         if (source.getTrueSource() != null) {
            SuperKnockback.applyKnockback(
               this.entity,
               this.knockbackOnReflect,
               source.getTrueSource().posX,
               source.getTrueSource().posY,
               source.getTrueSource().posZ
            );
         }

         return Math.max(0.0F, amount - this.maxdamageBlocked);
      } else {
         return amount;
      }
   }

   public void updateTask() {
      if (this.enable) {
         if (this.entity.getAttackTarget() != null) {
            this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 30.0F, 30.0F);
            this.entity.rotationYaw = this.entity.prevRotationYawHead;
            if (this.actualCooldown > 0) {
               this.actualCooldown--;
            }

            if (this.actualCooldown < 1 && this.entity.getDistance(this.entity.getAttackTarget()) <= this.length) {
               this.attackstarted = true;
            }
         }

         if (this.attackstarted) {
            this.actualReflectTime++;
            if (this.actualReflectTime > this.maxreflectTime) {
               this.sweep();
               if (!this.triggerOnStart) {
                  this.entity.triggerAnimation(this.animTriggered);
               }

               this.actualReflectTime = 0;
               this.attackstarted = false;
            }

            if (this.actualReflectTime == 1) {
               if (this.swoshSound != null) {
                  this.entity
                     .world
                     .playSound(
                        (EntityPlayer)null,
                        this.entity.posX,
                        this.entity.posY,
                        this.entity.posZ,
                        this.swoshSound,
                        SoundCategory.HOSTILE,
                        this.swoshSoundVolume,
                        0.8F + this.entity.getRNG().nextFloat() / 5.0F
                     );
               }

               if (this.triggerOnStart) {
                  this.entity.triggerAnimation(this.animTriggered);
               }
            }
         }
      }
   }

   public void sweep() {
      World world = this.entity.world;
      Vec3d vec = this.useYawOffset
         ? GetMOP.RotatedPosRayTrace(this.length, 1.0F, this.entity, this.radius, this.radius, this.entity.rotationPitch, this.entity.renderYawOffset)
         : GetMOP.PosRayTrace(this.length, 1.0F, this.entity, this.radius, this.radius);
      double damageRadius = this.endradius;
      this.actualCooldown = this.actualCooldown + this.maxcooldown;
      AxisAlignedBB aabb = new AxisAlignedBB(
         vec.x - damageRadius,
         vec.y - damageRadius,
         vec.z - damageRadius,
         vec.x + damageRadius,
         vec.y + damageRadius,
         vec.z + damageRadius
      );
      List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
      if (this.hitSound != null) {
         world.playSound(
            (EntityPlayer)null,
            this.entity.posX,
            this.entity.posY,
            this.entity.posZ,
            this.hitSound,
            SoundCategory.HOSTILE,
            this.hitSoundVolume,
            0.9F + this.entity.getRNG().nextFloat() / 5.0F
         );
      }

      if (!list.isEmpty()) {
         for (EntityLivingBase entitylivingbase : list) {
            if (Team.checkIsOpponent(entitylivingbase, this.entity)) {
               this.entity.attackEntityAsMob(entitylivingbase);
            }
         }
      }

      if (this.sendId != 0) {
         world.setEntityState(this.entity, this.sendId);
      }

      if (this.breakBlocks) {
         this.breakBlocks(vec, (int)this.endradius);
      }
   }

   public void breakBlocks(Vec3d center, int radius) {
      if (this.entity
         .world
         .isAreaLoaded(this.entity.getPosition().add(-16, -16, -16), this.entity.getPosition().add(16, 16, 16))) {
         for (BlockPos blockpos : GetMOP.getPosesInsideSphere((int)center.x, (int)center.y, (int)center.z, radius)) {
            if (!this.avoidDownbreak
               || this.entity.getAttackTarget() != null && blockpos.getY() >= this.entity.getAttackTarget().posY + 1.0
               || blockpos.getY() >= this.entity.posY + 0.5) {
               IBlockState state = this.entity.world.getBlockState(blockpos);
               Block block = state.getBlock();
               if (Weapons.easyBreakBlockFor(this.entity.world, this.blockBreakingHardness, blockpos, state)) {
                  if (!state.getBlock().hasTileEntity(state) && !(this.entity.getRNG().nextFloat() < 0.6F)) {
                     EntityFallingBlock entityfallingblock = new EntityFallingBlock(
                        this.entity.world, blockpos.getX() + 0.5, blockpos.getY() + 0.2, blockpos.getZ() + 0.5, state
                     );
                     this.entity.world.spawnEntity(entityfallingblock);
                     SuperKnockback.applyKnockback(
                        entityfallingblock, 1.5F, this.entity.posX, this.entity.posY - 0.4, this.entity.posZ
                     );
                     entityfallingblock.velocityChanged = true;
                  } else {
                     this.entity.world.destroyBlock(blockpos, true);
                  }
               }
            }
         }
      }
   }
}
