package com.vivern.arpg.entity;

import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.potions.PotionEffects;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.renders.ParticleTracker;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityRestlessSkull extends EntityThrowable implements IEntitySynchronize {
   public final ItemStack weaponstack;
   public float magicPower = 1.0F;
   public int ticksNoCollide = 0;
   public int impacts = 0;
   public boolean powered = false;
   public boolean active = false;
   public boolean flaming = false;
   public boolean collMob = false;
   public boolean collBlock = false;
   public int attackDelay = 0;
   public int attacks = 0;
   static ResourceLocation texture = new ResourceLocation("arpg:textures/ghostly_shoot.png");
   static ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largecloud.png");
   static ResourceLocation smmsh = new ResourceLocation("arpg:textures/simple_magic_shoot.png");
   public static ParticleTracker.TrackerRandomMotion trRandMotionUp = new ParticleTracker.TrackerRandomMotion(new Vec3d(0.0, 0.1, 0.0), 0.1F, 5);

   public EntityRestlessSkull(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.RESTLESSSKULL);
   }

   public EntityRestlessSkull(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.RESTLESSSKULL);
   }

   public EntityRestlessSkull(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.RESTLESSSKULL);
   }

   public EntityRestlessSkull(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.magicPower = power;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.2;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.2;
   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (!this.powered) {
         this.ticksNoCollide++;
      }

      if (this.ticksExisted > 200 || this.ticksNoCollide > 60) {
         this.setDead();
      }

      if (!this.world.isRemote) {
         this.world.setEntityState(this, (byte)5);
      }

      if (this.ticksExisted < 2 && this.powered) {
         this.world.setEntityState(this, (byte)8);
      }

      if (this.powered && this.ticksExisted % 3 == 0) {
         if (this.attackDelay > 0) {
            this.attackDelay -= 3;
         }

         double max = Double.MAX_VALUE;
         EntityLivingBase targ = null;
         double damageRadius = 4.0;
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
         if (!list.isEmpty()) {
            for (EntityLivingBase entitylivingbase : list) {
               if (Team.checkIsOpponent(this.thrower, entitylivingbase)
                  && entitylivingbase != this.getThrower()
                  && EntitySelectors.NOT_SPECTATING.apply(entitylivingbase)) {
                  double dist = entitylivingbase.getDistance(this);
                  if (dist < max) {
                     max = dist;
                     targ = entitylivingbase;
                  }
               }
            }
         }

         if (targ != null) {
            this.motionX *= 0.93;
            this.motionY *= 0.93;
            this.motionZ *= 0.93;
            SuperKnockback.applyKnockback(this, -0.2F, targ.posX, targ.posY + targ.height / 2.0F, targ.posZ);
            this.velocityChanged = true;
         }
      }
   }

   @Override
   public void onClient(double x, double y, double z, double a, double b, double c) {
      for (int ss = 0; ss < 8; ss++) {
         GUNParticle bigsmoke = new GUNParticle(
            largesmoke,
            0.3F + (float)this.rand.nextGaussian() / 20.0F,
            -0.03F,
            14,
            240,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            (float)this.rand.nextGaussian() / 20.0F,
            (float)this.rand.nextGaussian() / 25.0F,
            (float)this.rand.nextGaussian() / 20.0F,
            0.5F,
            0.7F + (float)this.rand.nextGaussian() / 5.0F,
            1.0F,
            true,
            this.rand.nextInt(360)
         );
         bigsmoke.alphaTickAdding = -0.065F;
         bigsmoke.scaleTickAdding = -0.015F;
         bigsmoke.alphaGlowing = true;
         this.world.spawnEntity(bigsmoke);
      }

      for (int ss = 0; ss < 3; ss++) {
         TrailParticle trailpart = new TrailParticle(
            smmsh,
            0.08F,
            -0.02F,
            25,
            220,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            (float)this.rand.nextGaussian() / 8.0F,
            (float)this.rand.nextGaussian() / 8.0F + 0.15F,
            (float)this.rand.nextGaussian() / 8.0F,
            1.0F,
            1.0F,
            1.0F,
            true,
            this.rand.nextInt(180),
            false,
            1.0F,
            texture,
            0.08F,
            220,
            1.0F,
            1.0F,
            1.0F,
            0.1F,
            5,
            -0.15F,
            9999.0F
         );
         trailpart.Ttexstart = 0.1F;
         trailpart.Toffset = -0.1F;
         trailpart.TalphaGlowing = true;
         trailpart.alphaGlowing = true;
         trailpart.tracker = trRandMotionUp;
         this.world.spawnEntity(trailpart);
      }
   }

   public void handleStatusUpdate(byte id) {
      super.handleStatusUpdate(id);
      if (id == 8) {
         this.powered = true;
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit)) {
            if (this.active) {
               if (this.attackDelay <= 0 && !this.world.isRemote) {
                  float basekn = this.powered ? 0.8F : 1.7F;
                  Weapons.dealDamage(
                     null,
                     (9.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack)) * this.magicPower,
                     this.thrower,
                     result.entityHit,
                     true,
                     basekn + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack) / 3.0F,
                     this.thrower.posX,
                     this.thrower.posY,
                     this.thrower.posZ
                  );
                  result.entityHit.hurtResistantTime = 0;
                  if (result.entityHit instanceof EntityLivingBase) {
                     EntityLivingBase entitylivingbase = (EntityLivingBase)result.entityHit;
                     if (this.flaming) {
                        PotionEffect debaff = new PotionEffect(PotionEffects.INCORPOREITY, 150);
                        entitylivingbase.addPotionEffect(debaff);
                     }

                     if (this.powered) {
                        this.attacks++;
                        this.attackDelay = 15;
                        if (this.attacks >= 2) {
                           this.world
                              .playSound(
                                 (EntityPlayer)null,
                                 this.posX,
                                 this.posY,
                                 this.posZ,
                                 Sounds.normal_projectile,
                                 SoundCategory.AMBIENT,
                                 0.7F,
                                 0.9F + this.rand.nextFloat() / 5.0F
                              );
                           IEntitySynchronize.sendSynchronize(this, 64.0, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
                           this.setDead();
                        }
                     } else {
                        if (entitylivingbase.getHealth() <= 0.0F && this.collBlock) {
                           NBTHelper.SetNBTboolean(this.weaponstack, true, "challengeblock");
                        }

                        if (this.collMob) {
                           NBTHelper.SetNBTboolean(this.weaponstack, true, "challengemob");
                        }
                     }
                  }

                  if (!this.powered) {
                     this.world
                        .playSound(
                           (EntityPlayer)null,
                           this.posX,
                           this.posY,
                           this.posZ,
                           Sounds.normal_projectile,
                           SoundCategory.AMBIENT,
                           0.7F,
                           0.9F + this.rand.nextFloat() / 5.0F
                        );
                     IEntitySynchronize.sendSynchronize(this, 64.0, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
                     this.setDead();
                  }
               }
            } else {
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.normal_projectile,
                     SoundCategory.AMBIENT,
                     0.7F,
                     0.9F + this.rand.nextFloat() / 5.0F
                  );
               this.ticksNoCollide = 0;
               this.impacts++;
               this.active = true;
               this.collMob = true;
               EnumFacing ef = getSideForCollide(result.entityHit, this);
               if (ef == EnumFacing.UP || ef == EnumFacing.DOWN) {
                  this.motionY = -this.motionY;
               }

               if (ef == EnumFacing.NORTH || ef == EnumFacing.SOUTH) {
                  this.motionZ = -this.motionZ;
               }

               if (ef == EnumFacing.EAST || ef == EnumFacing.WEST) {
                  this.motionX = -this.motionX;
               }

               this.velocityChanged = true;
            }
         }
      } else if (result != null
         && result.getBlockPos() != null
         && this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
         this.world
            .playSound(
               (EntityPlayer)null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.normal_projectile,
               SoundCategory.AMBIENT,
               0.7F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         this.ticksNoCollide = 0;
         this.impacts++;
         this.active = true;
         if (result.sideHit == EnumFacing.UP || result.sideHit == EnumFacing.DOWN) {
            this.motionY = -this.motionY;
         }

         if (result.sideHit == EnumFacing.NORTH || result.sideHit == EnumFacing.SOUTH) {
            this.motionZ = -this.motionZ;
         }

         if (result.sideHit == EnumFacing.EAST || result.sideHit == EnumFacing.WEST) {
            this.motionX = -this.motionX;
         }

         this.collBlock = true;
         if (!this.powered
            && !this.world.isRemote
            && this.impacts > 1 + 2 * EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack)) {
            if (result.hitVec != null) {
               IEntitySynchronize.sendSynchronize(
                  this, 64.0, result.hitVec.x, result.hitVec.y, result.hitVec.z, 0.0, 0.0, 0.0
               );
            }

            this.setDead();
         }
      }
   }

   public static EnumFacing getSideForCollide(Entity mob, Entity shoot) {
      double xdispl = shoot.posX - mob.posX;
      double zdispl = shoot.posZ - mob.posZ;
      if (shoot.posY > mob.posY + mob.height) {
         return EnumFacing.UP;
      } else if (shoot.posY < mob.posY) {
         return EnumFacing.DOWN;
      } else if (zdispl <= 0.0) {
         if (xdispl > -zdispl) {
            return EnumFacing.EAST;
         } else {
            return xdispl < zdispl ? EnumFacing.WEST : EnumFacing.NORTH;
         }
      } else if (zdispl > 0.0) {
         if (xdispl > zdispl) {
            return EnumFacing.EAST;
         } else {
            return xdispl < -zdispl ? EnumFacing.WEST : EnumFacing.SOUTH;
         }
      } else {
         return EnumFacing.SOUTH;
      }
   }

   @SideOnly(Side.CLIENT)
   public void onEntityUpdate() {
      Vec3d pos1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
      Vec3d pos2 = this.getPositionVector();
      if (this.world.isRemote && pos1.lengthSquared() > 1.0E-6 && pos2.lengthSquared() > 1.0E-6) {
         BetweenParticle laser = new BetweenParticle(
            this.world, texture, 0.14F, 240, 1.0F, 1.0F, 1.0F, 0.1F, pos1.distanceTo(pos2), 5, -0.15F, 9999.0F, pos1, pos2
         );
         laser.alphaGlowing = true;
         laser.texstart = 0.1F;
         laser.offset = -0.1F;
         laser.setPosition(pos1.x, pos1.y, pos1.z);
         this.world.spawnEntity(laser);
      }
   }
}
