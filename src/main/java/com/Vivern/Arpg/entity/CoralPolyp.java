//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.renders.GUNParticle;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CoralPolyp extends Entity {
   public static ResourceLocation tex = new ResourceLocation("arpg:textures/coral_polyp_body.png");
   public static int segmentCount = 7;
   public Vec3d[] segmentPoses;
   public Vec3d[] segmentPosesPrev;
   public EntityLivingBase thrower;
   public EntityLivingBase enemy;
   public int attackCooldown;
   public float damage;
   public int live;
   public int jump;
   public int attacked;

   public CoralPolyp(World worldIn) {
      super(worldIn);
      this.setSize(0.1F, 0.1F);
      this.live = 100;
   }

   public CoralPolyp(World worldIn, EntityLivingBase thrower, float damage) {
      super(worldIn);
      this.setSize(0.1F, 0.1F);
      this.live = 100;
      this.thrower = thrower;
      this.damage = damage;
   }

   protected void playStepSound(BlockPos pos, Block blockIn) {
   }

   public Vec3d followToVec3d(Vec3d base, Vec3d followTo, double distance) {
      return new Vec3d(
         GetMOP.followNumber(base.x, followTo.x, distance),
         GetMOP.followNumber(base.y, followTo.y, distance),
         GetMOP.followNumber(base.z, followTo.z, distance)
      );
   }

   public Team getTeam() {
      return this.thrower != null ? this.thrower.getTeam() : super.getTeam();
   }

   public boolean canBeCollidedWith() {
      return !this.world.isRemote;
   }

   public boolean attackEntityFrom(DamageSource source, float amount) {
      if (!this.world.isRemote) {
         if (this.attacked >= 1 || amount > 10.0F || source.getTrueSource() instanceof EntityPlayer) {
            this.world.setEntityState(this, (byte)8);
            this.setDead();
            return true;
         }

         this.attacked++;
      }

      return super.attackEntityFrom(source, amount);
   }

   public void handleStatusUpdate(byte id) {
      super.handleStatusUpdate(id);
      if (id == 8 && this.segmentPoses != null) {
         float halfLength = this.segmentPoses.length / 2.0F;
         float scalePrev = 0.05F;
         float scaleAdding = -0.030000001F / halfLength;

         for (int i = 0; i < this.segmentPoses.length - 1; i++) {
            Vec3d pos = this.segmentPoses[i];
            int lt = 30 + this.rand.nextInt(20);
            GUNParticle particle = new GUNParticle(
               tex,
               scalePrev,
               this.isInWater() ? 0.001F : 0.01F,
               lt,
               180,
               this.world,
               pos.x,
               pos.y,
               pos.z,
               (float)this.rand.nextGaussian() / 36.0F,
               (float)this.rand.nextGaussian() / 36.0F,
               (float)this.rand.nextGaussian() / 36.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(360),
               true,
               3.0F
            );
            particle.dropMode = true;
            particle.alphaTickAdding = -1.0F / lt;
            this.world.spawnEntity(particle);
            if (i > halfLength) {
               scalePrev += scaleAdding;
            }
         }
      }
   }

   public void onUpdate() {
      super.onUpdate();
      if (!this.isInWater() && this.jump <= 0 && !this.hasNoGravity()) {
         this.motionY -= 0.03;
      }

      this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0, this.posZ);
      this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
      float f = 0.98F;
      if (this.onGround) {
         BlockPos underPos = new BlockPos(
            MathHelper.floor(this.posX),
            MathHelper.floor(this.getEntityBoundingBox().minY) - 1,
            MathHelper.floor(this.posZ)
         );
         IBlockState underState = this.world.getBlockState(underPos);
         f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.98F;
      }

      this.motionX *= f;
      this.motionY *= 0.98F;
      this.motionZ *= f;
      if (this.onGround) {
         this.motionY *= -0.9F;
      }

      if (this.world.isRemote) {
         if (this.segmentPoses == null) {
            this.segmentPoses = new Vec3d[segmentCount];
            this.segmentPosesPrev = new Vec3d[segmentCount];
            Vec3d vec = GetMOP.entityCenterPos(this);

            for (int i = 0; i < this.segmentPoses.length; i++) {
               this.segmentPoses[i] = vec;
            }
         }

         for (int i = 0; i < this.segmentPoses.length; i++) {
            this.segmentPosesPrev[i] = this.segmentPoses[i];
         }

         Vec3d centerPos = GetMOP.entityCenterPos(this);
         double distTolastPosition = centerPos.distanceTo(this.segmentPoses[0]);

         for (int i = this.segmentPoses.length - 1; i > 0; i--) {
            this.segmentPoses[i] = this.followToVec3d(this.segmentPoses[i], this.segmentPoses[i - 1], distTolastPosition);
         }

         this.segmentPoses[0] = centerPos;
      } else {
         this.live--;
         if (this.live < 0) {
            this.world.setEntityState(this, (byte)8);
            this.setDead();
         }

         if (this.attackCooldown > 0) {
            this.attackCooldown--;
         }

         if (this.jump > 0) {
            this.jump--;
         }

         if (this.ticksExisted % 30 == 0) {
            this.enemy = this.thrower == null
               ? GetMOP.findNearestEntityWithinAABB(this.world, EntityLiving.class, GetMOP.newAABB(this, 5.0), this.getPositionVector())
               : GetMOP.findNearestEnemy(this.world, this.thrower, this.posX, this.posY, this.posZ, 6.0, false);
            if (!this.isInWater() && this.enemy != null && this.rand.nextFloat() < 0.5F) {
               this.jump = 8;
            }
         }

         if (this.enemy != null) {
            SuperKnockback.applyMove(
               this,
               this.jump > 0 ? -0.1F : -0.08F,
               this.enemy.posX,
               this.enemy.posY + this.rand.nextFloat() * this.enemy.height,
               this.enemy.posZ
            );
            if (this.attackCooldown <= 0 && this.enemy.getEntityBoundingBox().contains(this.getPositionVector())) {
               if (this.enemy.attackEntityFrom(new WeaponDamage(null, this.thrower, this, false, false, this, WeaponDamage.bite), this.damage)) {
                  if (this.live < 300) {
                     this.live += 40;
                  }

                  this.enemy.hurtResistantTime = 0;
               }

               this.attackCooldown = 5 + this.rand.nextInt(10);
               if (this.rand.nextFloat() < 0.3) {
                  this.enemy = null;
               }
            }
         } else if (this.ticksExisted % 4 == 0) {
            this.motionX = this.motionX + (this.rand.nextFloat() - 0.5) * 0.14;
            this.motionY = this.motionY + (this.rand.nextFloat() - 0.5) * 0.14;
            this.motionZ = this.motionZ + (this.rand.nextFloat() - 0.5) * 0.14;
         }
      }
   }

   protected void entityInit() {
   }

   protected void readEntityFromNBT(NBTTagCompound compound) {
   }

   protected void writeEntityToNBT(NBTTagCompound compound) {
   }
}
