//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.Weapons;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAIJumpBeastAttack extends EntityAIBase {
   public AbstractMob entity;
   public int attackTimer = 0;
   public int maxCooldown = 30;
   public float radius = 0.0F;
   public float upMovement = 0.2F;
   public float forwardMovement = 0.0F;
   public float damageRadius;
   public int triggeredAnimation;
   public byte sendId = 13;
   public boolean breakBlocks = false;
   public float blockBreakingHardness = 1.0F;
   public boolean avoidDownbreak = true;
   public SoundEvent soundOnJump = null;
   public SoundEvent soundOnFall = null;
   public boolean isAdvanced;
   int currentTicksInFlying;
   boolean startedFlying;
   Vec3d targetPosition;
   boolean onGroundPrev;

   public EntityAIJumpBeastAttack(
      AbstractMob entity,
      int maxCooldown,
      float castRadius,
      float damageRadius,
      float upMovement,
      float forwardMovement,
      int triggeredAnimation,
      float blockBreakingHardness,
      boolean isAdvanced
   ) {
      this.entity = entity;
      this.maxCooldown = maxCooldown;
      this.upMovement = upMovement;
      this.radius = castRadius;
      this.damageRadius = damageRadius;
      this.triggeredAnimation = triggeredAnimation;
      this.forwardMovement = forwardMovement;
      this.breakBlocks = blockBreakingHardness != 0.0F;
      this.blockBreakingHardness = blockBreakingHardness;
      this.isAdvanced = isAdvanced;
   }

   public EntityAIJumpBeastAttack setSounds(SoundEvent soundOnJump, SoundEvent soundOnFall) {
      this.soundOnJump = soundOnJump;
      this.soundOnFall = soundOnFall;
      return this;
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null;
   }

   public boolean shouldContinueExecuting() {
      return this.entity.getAttackTarget() != null && this.entity.getAttackTarget().isEntityAlive();
   }

   public void updateTask() {
      EntityLivingBase attackTarg = this.entity.getAttackTarget();
      this.attackTimer--;
      if (attackTarg != null) {
         if (this.startedFlying) {
            this.entity.motionX /= 0.98;
            this.entity.motionY /= 0.98;
            this.entity.motionZ /= 0.98;
            this.currentTicksInFlying++;
            if (this.entity.motionY < 0.0) {
               SuperKnockback.applyMove(
                  this.entity, -0.015F, this.targetPosition.x, this.targetPosition.y, this.targetPosition.z
               );
            }

            this.entity.fallDistance = 0.0F;
            if (!this.onGroundPrev && this.entity.onGround) {
               this.sweep();
               this.startedFlying = false;
               this.attackTimer = this.maxCooldown;
               this.currentTicksInFlying = 0;
            }

            if (this.currentTicksInFlying > 100) {
               this.startedFlying = false;
               this.attackTimer = this.maxCooldown;
               this.currentTicksInFlying = 0;
            }
         } else if (this.attackTimer <= 0) {
            double dist = this.entity.getDistance(attackTarg);
            if (dist < this.radius) {
               this.startedFlying = true;
               this.targetPosition = attackTarg.getPositionVector();
               SuperKnockback.setMove(
                  this.entity, (float)(-dist * this.forwardMovement), attackTarg.posX, attackTarg.posY, attackTarg.posZ
               );
               this.entity.motionY = this.entity.motionY + this.upMovement;
               this.attackTimer = this.maxCooldown;
               this.entity.triggerAnimation(this.triggeredAnimation);
               if (this.soundOnJump != null) {
                  this.entity.playSound(this.soundOnJump, 1.5F, 0.9F + this.entity.getRNG().nextFloat() / 5.0F);
               }
            }
         }
      }

      this.onGroundPrev = this.entity.onGround;
   }

   public void sweep() {
      World world = this.entity.world;
      Vec3d vec = this.entity.getPositionVector();
      AxisAlignedBB aabb = new AxisAlignedBB(
         vec.x - this.damageRadius,
         vec.y - 1.0,
         vec.z - this.damageRadius,
         vec.x + this.damageRadius,
         vec.y + 0.5,
         vec.z + this.damageRadius
      );
      List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this.entity, aabb);
      if (this.soundOnFall != null) {
         world.playSound(
            (EntityPlayer)null,
            this.entity.posX,
            this.entity.posY,
            this.entity.posZ,
            this.soundOnFall,
            SoundCategory.HOSTILE,
            1.3F,
            0.9F + this.entity.getRNG().nextFloat() / 5.0F
         );
      }

      if (!list.isEmpty()) {
         for (Entity entitylivingbase : list) {
            if (Team.checkIsOpponent(entitylivingbase, this.entity)) {
               this.entity.attackEntityAsMob(entitylivingbase);
            }
         }
      }

      if (this.sendId != 0) {
         world.setEntityState(this.entity, this.sendId);
      }

      if (this.breakBlocks) {
         this.breakBlocks(vec, (int)this.damageRadius);
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
