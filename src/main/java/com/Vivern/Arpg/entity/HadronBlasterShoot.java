package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.IRenderOptions;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HadronBlasterShoot extends EntityThrowable implements IEntitySynchronize {
   public final ItemStack weaponstack;
   static ResourceLocation explode = new ResourceLocation("arpg:textures/sparkle5.png");
   static ResourceLocation cloud = new ResourceLocation("arpg:textures/circle.png");
   static ResourceLocation beam = new ResourceLocation("arpg:textures/ichorstream.png");

   public HadronBlasterShoot(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.HADRONBLASTER);
   }

   public HadronBlasterShoot(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.HADRONBLASTER);
   }

   public HadronBlasterShoot(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.HADRONBLASTER);
   }

   public HadronBlasterShoot(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.3;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.3;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.2;
      }
   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 30) {
         this.setDead();
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
   }

   @Override
   public void onClient(double x, double y, double z, double a, double b, double c) {
      this.world
         .playSound(
            this.posX,
            this.posY,
            this.posZ,
            Sounds.hadron_blaster_impact,
            SoundCategory.AMBIENT,
            0.9F,
            0.9F + this.rand.nextFloat() / 5.0F,
            false
         );

      for (int i = 0; i < 3; i++) {
         GUNParticle bigsmoke1 = new GUNParticle(
            explode,
            0.04F,
            0.0F,
            5,
            240,
            this.world,
            x,
            y,
            z,
            0.0F,
            0.0F,
            0.0F,
            1.0F,
            this.ticksExisted / 20.0F + this.rand.nextFloat() / 4.0F,
            0.1F + this.rand.nextFloat() / 10.0F,
            true,
            this.rand.nextInt(360)
         );
         bigsmoke1.alphaTickAdding = -0.1F;
         bigsmoke1.alphaGlowing = true;
         bigsmoke1.scaleTickAdding = 0.05F + this.rand.nextFloat() / 10.0F + i * 0.06F;
         bigsmoke1.randomDeath = false;
         if (i > 0) {
            bigsmoke1.rotationPitchYaw = new Vec2f(this.rand.nextInt(360), this.rand.nextInt(360));
         }

         this.world.spawnEntity(bigsmoke1);
      }

      Vec3d newvec = new Vec3d(x, y, z);

      for (int i = 0; i < 7; i++) {
         Vec3d vect = newvec.add(this.rand.nextGaussian(), this.rand.nextGaussian(), this.rand.nextGaussian());
         if (vect.lengthSquared() > 1.0E-6 && newvec.lengthSquared() > 1.0E-6) {
            double dto = newvec.distanceTo(vect);
            BetweenParticle laser = new BetweenParticle(
               this.world,
               beam,
               0.07F,
               240,
               1.0F - this.rand.nextFloat() / 10.0F,
               this.ticksExisted / 20.0F,
               0.15F,
               0.13F,
               dto,
               7,
               0.0F,
               5.0F,
               vect,
               newvec
            );
            laser.setPosition(vect.x, vect.y, vect.z);
            laser.alphaGlowing = true;
            this.world.spawnEntity(laser);
         }
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit) && Weapons.canDealDamageTo(result.entityHit)) {
            Vec3d start = this.getPositionVector();
            RayTraceResult result2 = GetMOP.fixedRayTraceBlocks(
               this.world,
               this.thrower,
               0.3,
               0.2,
               true,
               new Vec3d(this.lastTickPosX, this.lastTickPosY, this.lastTickPosZ),
               start.add(this.motionX, this.motionY, this.motionZ),
               false,
               false,
               false
            );
            if (result2 != null && result2.hitVec != null) {
               this.expl(result2.hitVec);
            } else {
               this.expl(start);
            }
         }
      } else if (this.world
            .getBlockState(result.getBlockPos())
            .getBlock()
            .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
         != null) {
         if (result != null && result.hitVec != null) {
            this.expl(result.hitVec);
         } else {
            this.expl(this.getPositionVector());
         }
      }
   }

   public void expl(Vec3d vect) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
      double damageRadius = parameters.getEnchanted("damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
      AxisAlignedBB axisalignedbb = new AxisAlignedBB(
         vect.x - damageRadius,
         vect.y - damageRadius,
         vect.z - damageRadius,
         vect.x + damageRadius,
         vect.y + damageRadius,
         vect.z + damageRadius
      );
      if (!this.world.isRemote) {
         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
         boolean bonusCreated = false;
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               if (Team.checkIsOpponent(this.thrower, entity)) {
                  int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
                  int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
                  boolean ab = Weapons.dealDamage(
                     new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.laser),
                     parameters.getEnchanted("damage", might),
                     this.getThrower(),
                     entity,
                     true,
                     parameters.getEnchanted("knockback", impulse),
                     this.posX,
                     this.posY,
                     this.posZ
                  );
                  entity.hurtResistantTime = 0;
                  if (entity instanceof EntityLivingBase) {
                     EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                     boolean checkKill = entitylivingbase.getHealth() > 0.0F;
                     float reus = parameters.getEnchanted("hadron_chance_mob", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, this.weaponstack));
                     bonusCreated = ab;
                     if (ab && this.rand.nextFloat() < reus) {
                        HadronBlasterBonus bonus = new HadronBlasterBonus(
                           this.world, entitylivingbase, this.thrower
                        );
                        this.world.spawnEntity(bonus);
                     }

                     if (checkKill && entitylivingbase.getHealth() <= 0.0F && ab) {
                        if (this.rand.nextFloat() < 0.2) {
                           DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_FIRE);
                        }

                        if (this.rand.nextFloat() < reus) {
                           HadronBlasterBonus bonus = new HadronBlasterBonus(
                              this.world, entitylivingbase, this.thrower
                           );
                           this.world.spawnEntity(bonus);
                        }
                     }
                  }
               }
            }
         }

         if (!bonusCreated) {
            float reusx = parameters.getEnchanted("hadron_chance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, this.weaponstack));
            if (this.rand.nextFloat() < reusx) {
               HadronBlasterBonus bonus = new HadronBlasterBonus(this.world, this, this.thrower);
               this.world.spawnEntity(bonus);
            }
         }
      }

      if (!this.world.isRemote) {
         IEntitySynchronize.sendSynchronize(this, 64.0, vect.x, vect.y, vect.z, 0.0, 0.0, 0.0);
         this.setDead();
      }
   }

   public void onEntityUpdate() {
      super.onEntityUpdate();
      if (this.world.isRemote) {
         GUNParticle fire2 = new GUNParticle(
            cloud,
            0.2F,
            0.0F,
            10,
            240,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            0.0F,
            0.0F,
            0.0F,
            1.0F,
            this.ticksExisted / 20.0F,
            0.1F + (float)this.rand.nextGaussian() / 10.0F,
            true,
            this.rand.nextInt(360)
         );
         fire2.alphaTickAdding = -0.08F;
         fire2.scaleTickAdding = -0.016F;
         fire2.alphaGlowing = true;
         float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
         float rotationYaww = (float)(MathHelper.atan2(this.motionX, -this.motionZ) * (180.0 / Math.PI));
         float rotationPitchh = (float)(MathHelper.atan2(this.motionY, f) * (180.0 / Math.PI));
         fire2.rotationPitchYaw = new Vec2f(rotationPitchh, rotationYaww);
         this.world.spawnEntity(fire2);
      }
   }

   public static class HadronBlasterBonus extends Entity implements IRenderOptions {
      public static ResourceLocation texture = new ResourceLocation("arpg:textures/ichorstream.png");
      public Entity owner;
      public boolean follow = false;
      public float randR;
      public float randG;
      public float randB;

      public HadronBlasterBonus(World world) {
         super(world);
         this.setSize(0.15F, 0.15F);
         if (world.isRemote) {
            this.randR = this.rand.nextFloat();
            this.randG = this.rand.nextFloat();
            this.randB = 1.0F - this.randG;
         }
      }

      public HadronBlasterBonus(World world, Entity dropper, Entity owner) {
         super(world);
         this.setPosition(dropper.posX, dropper.posY + dropper.height / 2.0F, dropper.posZ);
         this.setSize(0.15F, 0.15F);
         this.motionX = this.rand.nextGaussian() / 7.0 + dropper.motionX / 2.0;
         this.motionY = this.rand.nextGaussian() / 11.0 + 0.54 + dropper.motionY / 2.0;
         this.motionZ = this.rand.nextGaussian() / 7.0 + dropper.motionZ / 2.0;
         this.velocityChanged = true;
         this.owner = owner;
         if (world.isRemote) {
            this.randR = this.rand.nextFloat();
            this.randG = this.rand.nextFloat();
            this.randB = 1.0F - this.randG;
         }
      }

      public double getYOffset() {
         return 0.05;
      }

      protected void entityInit() {
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.follow && this.owner != null) {
            if (!this.world.isRemote) {
               SuperKnockback.applyMove(
                  this, -0.17F, this.owner.posX, this.owner.posY + this.owner.height / 4.0F, this.owner.posZ
               );
               if (this.getDistanceSq(this.owner) < 2.5) {
                  this.setDead();
               }

               this.velocityChanged = true;
            }
         } else {
            if (!this.hasNoGravity()) {
               this.motionY = this.motionY - 0.004999999329447746 * Math.max(30.0F - this.ticksExisted, 0.0F) / 30.0;
            }

            this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0, this.posZ);
         }

         if (this.ticksExisted == 2) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.magic_k,
                  SoundCategory.HOSTILE,
                  0.9F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (this.ticksExisted == 16) {
            this.motionX *= 0.5;
            this.motionY *= 0.5;
            this.motionZ *= 0.5;
         }

         this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
         float f = this.follow ? 0.88F : 0.98F;
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
         this.motionY *= f;
         this.motionZ *= f;
         if (this.onGround) {
            this.motionY *= -0.9F;
         }

         if (!this.world.isRemote && (this.ticksExisted > 850 || this.owner == null)) {
            this.setDead();
         }
      }

      @SideOnly(Side.CLIENT)
      public boolean isInRangeToRenderDist(double distance) {
         double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0;
         if (Double.isNaN(d0)) {
            d0 = 4.0;
         }

         d0 *= 64.0;
         return distance < d0 * d0;
      }

      protected void readEntityFromNBT(NBTTagCompound compound) {
      }

      protected void writeEntityToNBT(NBTTagCompound compound) {
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 0) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.item_misc_d,
                  SoundCategory.PLAYERS,
                  0.9F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }
      }

      @Override
      public void doOptions() {
         GlStateManager.color(this.randR, this.randG, this.randB);
      }

      @Override
      public void undoOptions() {
         GlStateManager.color(1.0F, 1.0F, 1.0F);
      }

      @SideOnly(Side.CLIENT)
      public void onEntityUpdate() {
         if (this.ticksExisted < 50) {
            Vec3d pos1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            Vec3d pos2 = this.getPositionVector();
            if (this.world.isRemote && pos1.lengthSquared() > 1.0E-6 && pos2.lengthSquared() > 1.0E-6) {
               BetweenParticle laser = new BetweenParticle(
                  this.world, texture, 0.18F, 240, this.randR, this.randG, this.randB, 0.1F, pos1.distanceTo(pos2), 5, -0.15F, 9999.0F, pos1, pos2
               );
               laser.alphaGlowing = true;
               laser.texstart = 0.1F;
               laser.offset = -0.1F;
               laser.horizontal = false;
               laser.setPosition(pos1.x, pos1.y, pos1.z);
               this.world.spawnEntity(laser);
            }
         }
      }
   }
}
