package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.elements.IRepulsable;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import java.lang.reflect.Field;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class CrystalStarShoot extends EntityArrow implements IRepulsable, INailer {
   public final ItemStack weaponstack;
   public boolean prickedToWall = false;
   public Entity prickedEntity = null;
   public BlockPos wallPos = null;
   public boolean damageDealed = false;
   public int detachtime = 0;
   public int removeTime = 0;
   public EntityLivingBase entitydamaged = null;
   public float magicPower = 1.0F;
   public int timeInGround = 0;

   public CrystalStarShoot(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.CRYSTALSTAR);
      this.pickupStatus = PickupStatus.DISALLOWED;
   }

   public CrystalStarShoot(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.CRYSTALSTAR);
      this.pickupStatus = PickupStatus.DISALLOWED;
   }

   public CrystalStarShoot(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.CRYSTALSTAR);
      this.pickupStatus = PickupStatus.DISALLOWED;
   }

   public CrystalStarShoot(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.pickupStatus = PickupStatus.DISALLOWED;
      this.magicPower = power;
   }

   public boolean handleWaterMovement() {
      return false;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.9;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.9;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.5;
      }
   }

   public void playSound(SoundEvent soundIn, float volume, float pitch) {
      if (soundIn == SoundEvents.ENTITY_ARROW_HIT) {
         soundIn = Sounds.bullet;
      }

      super.playSound(soundIn, volume, pitch);
   }

   public static void registerFixesTippedArrow(DataFixer fixer) {
      EntityArrow.registerFixesArrow(fixer, "TippedArrow");
   }

   @Override
   public void setNailedEntity(Entity entity) {
      this.prickedEntity = entity;
   }

   @Override
   public Entity getNailedEntity() {
      return this.prickedEntity;
   }

   @Override
   public boolean isprickedToWall() {
      return this.prickedToWall;
   }

   @Override
   public boolean canPrickParticle() {
      return this.timeInGround < 10;
   }

   protected void onHit(RayTraceResult raytraceResultIn) {
      Entity entity = raytraceResultIn.entityHit;
      if (entity != null) {
         if (!this.repulse(entity) && Team.checkIsOpponent(this.shootingEntity, entity) && !this.world.isRemote) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            if (!this.damageDealed) {
               int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
               int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
               Weapons.dealDamage(
                  new WeaponDamage(this.weaponstack, this.shootingEntity, this, false, true, this, WeaponDamage.pierce),
                  parameters.getEnchanted("damage", might) * this.magicPower,
                  this.shootingEntity,
                  entity,
                  true,
                  parameters.getEnchanted("knockback", impulse),
                  this.posX,
                  this.posY,
                  this.posZ
               );
               entity.hurtResistantTime = 0;
            }

            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.bullet,
                  SoundCategory.AMBIENT,
                  0.8F,
                  0.9F + this.rand.nextFloat() / 5.0F
               );
            this.world.setEntityState(this, (byte)8);
            if (entity instanceof EntityLivingBase) {
               EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
               boolean apricked = NailGunShoot.isEntityPricked(this.world, entitylivingbase);
               boolean healthmin = entitylivingbase.getHealth() <= parameters.get("health_to_prick");
               if (!this.damageDealed) {
                  this.damageDealed = true;
                  if (healthmin && !apricked) {
                     this.prickedEntity = entitylivingbase;
                  }

                  this.entitydamaged = entitylivingbase;
               }

               if (entitylivingbase.getHealth() <= 0.0F && this.rand.nextFloat() < 0.95) {
                  DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_DISMEMBER);
               }
            }
         }
      } else {
         BlockPos blockpos = raytraceResultIn.getBlockPos();
         IBlockState iblockstate = this.world.getBlockState(blockpos);

         try {
            Class thisclass = this.getClass();
            Field fxtile = thisclass.getField("xTile");
            Field fytile = thisclass.getField("yTile");
            Field fztile = thisclass.getField("zTile");
            fxtile.setAccessible(true);
            fytile.setAccessible(true);
            fztile.setAccessible(true);
            fxtile.setInt(this, blockpos.getX());
            fytile.setInt(this, blockpos.getY());
            fztile.setInt(this, blockpos.getZ());
            Field finTile = thisclass.getField("inTile");
            Field finData = thisclass.getField("inData");
            finTile.setAccessible(true);
            finData.setAccessible(true);
            finTile.set(this, iblockstate.getBlock());
            finData.set(this, iblockstate.getBlock().getMetaFromState(iblockstate));
         } catch (Exception var11) {
         }

         this.motionX = (float)(raytraceResultIn.hitVec.x - this.posX);
         this.motionY = (float)(raytraceResultIn.hitVec.y - this.posY);
         this.motionZ = (float)(raytraceResultIn.hitVec.z - this.posZ);
         float f2 = MathHelper.sqrt(
            this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ
         );
         this.posX = this.posX - this.motionX / f2 * 0.05F;
         this.posY = this.posY - this.motionY / f2 * 0.05F;
         this.posZ = this.posZ - this.motionZ / f2 * 0.05F;
         this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
         this.inGround = true;
         this.arrowShake = 7;
         this.prickedToWall = true;
         this.setIsCritical(false);
         if (this.prickedEntity != null) {
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.crystal_star_impact,
                  SoundCategory.AMBIENT,
                  2.2F,
                  0.85F + this.rand.nextFloat() * 0.3F
               );
         }

         if (iblockstate.getMaterial() != Material.AIR) {
            iblockstate.getBlock().onEntityCollision(this.world, blockpos, iblockstate, this);
         }
      }
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.inGround) {
         this.timeInGround++;
      }

      if (this.ticksExisted > 300 && !this.prickedToWall || this.ticksExisted > 300) {
         this.setDead();
      }

      if (this.wallPos == null) {
         this.prickedToWall = false;
      }

      if (this.prickedToWall
         && this.world
               .getBlockState(this.wallPos)
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(this.wallPos), this.world, this.wallPos)
            == null) {
         this.prickedToWall = false;
         this.wallPos = null;
      }

      if (this.entitydamaged != null && !this.world.getEntitiesWithinAABB(Entity.class, this.getEntityBoundingBox()).contains(this.entitydamaged)) {
         this.removeTime++;
      }

      if (this.prickedEntity != null) {
         double dist = this.getDistance(this.prickedEntity);
         if (this.world.getEntitiesWithinAABB(Entity.class, this.getEntityBoundingBox()).contains(this.prickedEntity)) {
            if (this.prickedToWall) {
               this.prickedEntity.setNoGravity(true);
               this.prickedEntity.motionX = 0.0;
               this.prickedEntity.motionY = 0.0;
               this.prickedEntity.motionZ = 0.0;
            } else {
               this.prickedEntity.motionX = this.motionX;
               this.prickedEntity.motionY = this.motionY;
               this.prickedEntity.motionZ = this.motionZ;
            }

            if (this.detachtime > 0) {
               this.detachtime--;
            }
         } else {
            this.detachtime++;
            SuperKnockback.applyKnockback(this.prickedEntity, -0.5F, this.posX, this.posY, this.posZ);
         }

         if (dist > this.prickedEntity.height * 3.0F || this.detachtime > 20) {
            this.prickedEntity = null;
         }

         if (this.removeTime > 2 && !this.inGround) {
            this.setDead();
         }
      }
   }

   public boolean repulse(Entity entityHit) {
      int rep = Weapons.getEntityRepulseType(entityHit);
      if (rep == 0) {
         return false;
      } else if (entityHit instanceof EntityThrowable && ((EntityThrowable)entityHit).getThrower() == this.shootingEntity) {
         return false;
      } else if (entityHit instanceof EntityArrow && ((EntityArrow)entityHit).shootingEntity == this.shootingEntity) {
         return false;
      } else if (rep == 1 || rep == 2) {
         this.prickedEntity = entityHit;
         return false;
      } else if (rep == 3) {
         entityHit.setDead();
         this.world
            .playSound(
               (EntityPlayer)null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.bullet,
               SoundCategory.AMBIENT,
               0.8F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         return true;
      } else if (rep == 4) {
         entityHit.setDead();
         this.world
            .playSound(
               (EntityPlayer)null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.bullet,
               SoundCategory.AMBIENT,
               0.8F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         this.world.setEntityState(this, (byte)8);
         this.setDead();
         return true;
      } else if (rep == 5) {
         entityHit.motionX = entityHit.motionX + this.motionX;
         entityHit.motionY = entityHit.motionY + this.motionY;
         entityHit.motionZ = entityHit.motionZ + this.motionZ;
         Weapons.setAcceleration(entityHit);
         this.world
            .playSound(
               (EntityPlayer)null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.bullet,
               SoundCategory.AMBIENT,
               0.8F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         this.world.setEntityState(this, (byte)8);
         this.setDead();
         return true;
      } else if (rep == 6) {
         int axisnoreflect = this.rand.nextInt(3);
         entityHit.motionX *= axisnoreflect == 0 ? 1.0 : -1.0;
         entityHit.motionY *= axisnoreflect == 1 ? 1.0 : -1.0;
         entityHit.motionZ *= axisnoreflect == 2 ? 1.0 : -1.0;
         this.motionX *= axisnoreflect == 0 ? 1.0 : -1.0;
         this.motionY *= axisnoreflect == 1 ? 1.0 : -1.0;
         this.motionZ *= axisnoreflect == 2 ? 1.0 : -1.0;
         Weapons.setAcceleration(entityHit);
         this.world
            .playSound(
               (EntityPlayer)null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.bullet,
               SoundCategory.AMBIENT,
               0.8F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         return true;
      } else if (rep == 7) {
         this.world
            .playSound(
               (EntityPlayer)null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.bullet,
               SoundCategory.AMBIENT,
               0.8F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         this.world.setEntityState(this, (byte)8);
         this.setDead();
         return true;
      } else if (rep == 8) {
         this.motionX *= -1.0;
         this.motionY *= -1.0;
         this.motionZ *= -1.0;
         this.world
            .playSound(
               (EntityPlayer)null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.bullet,
               SoundCategory.AMBIENT,
               0.8F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         return true;
      } else {
         return false;
      }
   }

   @Override
   public int getRepulseType() {
      return 2;
   }

   protected ItemStack getArrowStack() {
      return ItemStack.EMPTY;
   }
}
