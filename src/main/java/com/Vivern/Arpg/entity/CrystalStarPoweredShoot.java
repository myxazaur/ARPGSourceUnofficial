//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.events.Debugger;
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
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CrystalStarPoweredShoot extends StandardBullet implements INailer {
   public final ItemStack weaponstack;
   public EntityLivingBase targ = null;
   public float magicPower = 1.0F;
   public float homingPower = -0.37F;
   public float homingPowerMax = -0.37F;
   public boolean prickedToWall = false;
   public Entity prickedEntity = null;
   public int timeInGround = 0;
   public boolean damageDealed = false;
   public int detachtime = 0;
   static ResourceLocation texture = new ResourceLocation("arpg:textures/generic_beam5.png");

   public CrystalStarPoweredShoot(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.CRYSTALSTAR);
      this.setHomingPower();
   }

   public CrystalStarPoweredShoot(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.CRYSTALSTAR);
      this.setHomingPower();
   }

   public CrystalStarPoweredShoot(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.CRYSTALSTAR);
      this.setHomingPower();
   }

   public CrystalStarPoweredShoot(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.magicPower = power;
      this.setHomingPower();
   }

   @Override
   protected void entityInit() {
   }

   public void setHomingPower() {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
      float homingpower = parameters.get("homing_power") + this.rand.nextFloat() * parameters.get("homing_power_random");
      this.homingPower = -homingpower;
      this.homingPowerMax = -parameters.get("homing_power_max");
   }

   @Override
   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
   }

   @Override
   public float getGravityVelocity() {
      return 0.0F;
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 60 && !this.world.isRemote) {
         this.expl(GetMOP.entityCenterPos(this));
      }

      if (this.targ == null) {
         if (this.ticksExisted % 5 == 0) {
            double max = Double.MAX_VALUE;
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            double damageRadius = parameters.getEnchanted("find_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
               .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
               .offset(-damageRadius, -damageRadius, -damageRadius);
            List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
            if (!list.isEmpty()) {
               for (EntityLivingBase entitylivingbase : list) {
                  if (entitylivingbase.isCreatureType(EnumCreatureType.MONSTER, false)
                     && Team.checkIsOpponent(this.thrower, entitylivingbase)
                     && entitylivingbase != this.getThrower()
                     && EntitySelectors.NOT_SPECTATING.apply(entitylivingbase)
                     && entitylivingbase.getHealth() > 0.0F
                     && GetMOP.thereIsNoBlockCollidesBetween(
                        this.world, GetMOP.entityCenterPos(this), GetMOP.entityCenterPos(entitylivingbase), 1.0F, null, false
                     )) {
                     double dist = entitylivingbase.getDistance(this);
                     if (dist < max) {
                        max = dist;
                        this.targ = entitylivingbase;
                     }
                  }
               }

               if (this.targ == null) {
                  for (EntityLivingBase entitylivingbasex : list) {
                     if (Team.checkIsOpponent(this.thrower, entitylivingbasex)
                        && entitylivingbasex != this.getThrower()
                        && EntitySelectors.NOT_SPECTATING.apply(entitylivingbasex)
                        && entitylivingbasex.getHealth() > 0.0F
                        && GetMOP.thereIsNoBlockCollidesBetween(
                           this.world, GetMOP.entityCenterPos(this), GetMOP.entityCenterPos(entitylivingbasex), 1.0F, null, false
                        )) {
                        double dist = entitylivingbasex.getDistance(this);
                        if (dist < max) {
                           max = dist;
                           this.targ = entitylivingbasex;
                        }
                     }
                  }
               }
            }

            if (this.targ == null) {
               this.motionX = this.motionX + this.rand.nextGaussian() / 15.0;
               this.motionY = this.motionY + this.rand.nextGaussian() / 15.0;
               this.motionZ = this.motionZ + this.rand.nextGaussian() / 15.0;
            } else {
               this.motionX *= 0.5;
               this.motionY *= 0.5;
               this.motionZ *= 0.5;
            }
         }
      } else {
         this.motionX *= 0.9;
         this.motionY *= 0.9;
         this.motionZ *= 0.9;
         SuperKnockback.applyMove(
            this, this.homingPower, this.targ.posX, this.targ.posY + this.targ.height / 2.0F, this.targ.posZ
         );
         if (this.homingPower > this.homingPowerMax) {
            this.homingPower -= 0.017F;
         }
      }

      if (this.prickedEntity != null) {
         Vec3d mot = new Vec3d(this.motionX, this.motionY, this.motionZ).normalize().scale(0.4F);
         Vec3d location = new Vec3d(this.posX + mot.x, this.posY + mot.y, this.posZ + mot.z);
         Vec3d to = location.subtract(this.prickedEntity.getPositionVector());
         this.prickedEntity.move(MoverType.SELF, to.x, to.y, to.z);
      }
   }

   @Override
   public void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            if (!this.damageDealed) {
               int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
               int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
               Weapons.dealDamage(
                  new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.pierce),
                  parameters.getEnchanted("damage_powered", might) * this.magicPower,
                  this.getThrower(),
                  result.entityHit,
                  true,
                  parameters.getEnchanted("knockback", impulse),
                  this.prevPosX,
                  this.prevPosY,
                  this.prevPosZ
               );
               result.entityHit.hurtResistantTime = 0;
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
            if (result.entityHit instanceof EntityLivingBase) {
               EntityLivingBase entitylivingbase = (EntityLivingBase)result.entityHit;
               if (entitylivingbase.getHealth() <= 0.0F) {
                  if (this.rand.nextFloat() < 0.95) {
                     DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_DISMEMBER);
                  }
               } else {
                  boolean apricked = NailGunShoot.isEntityPricked(this.world, entitylivingbase);
                  boolean healthmin = entitylivingbase.getHealth() <= parameters.get("health_to_prick_powered");
                  if (!this.damageDealed) {
                     this.damageDealed = true;
                     if (healthmin && !apricked) {
                        this.prickedEntity = entitylivingbase;
                     }
                  }
               }
            }
         }
      } else if (this.world
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
               Sounds.crystal_star_impact,
               SoundCategory.AMBIENT,
               2.4F,
               0.85F + this.rand.nextFloat() * 0.3F
            );
         if (!this.world.isRemote) {
            this.expl(GetMOP.normalizeRayTraceResult(result, 0.5).hitVec);
         }
      }
   }

   public void expl(Vec3d pos) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
      int nn = parameters.geti("shots");
      if (this.getThrower() != null) {
         float radius = parameters.get("explode_find_radius");
         List<EntityLivingBase> list = GetMOP.getHostilesInAABBto(this.world, pos, radius, radius, this.getThrower(), this.prickedEntity);
         float randchance = list.isEmpty() ? 2.0F : 0.5F - list.size() * 0.08F;
         boolean hasspecial = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) > 0;
         if (!list.isEmpty() && hasspecial) {
            randchance = 0.0F;
         }

         for (int i = 0; i < nn; i++) {
            CrystalStarShoot projectile = new CrystalStarShoot(this.world, this.getThrower(), this.weaponstack, this.magicPower);
            projectile.setPosition(pos.x, pos.y, pos.z);
            if (this.rand.nextFloat() < randchance) {
               projectile.motionX = this.rand.nextGaussian() / 2.0;
               projectile.motionY = this.rand.nextGaussian() / 2.0;
               projectile.motionZ = this.rand.nextGaussian() / 2.0;
            } else {
               EntityLivingBase target = null;
               if (hasspecial) {
                  for (int j = 0; j < 4; j++) {
                     target = list.get(this.rand.nextInt(list.size()));
                     if (GetMOP.thereIsNoBlockCollidesBetween(this.world, pos, GetMOP.entityCenterPos(target), 1.0F, null, false)) {
                        break;
                     }
                  }
               } else {
                  target = list.get(this.rand.nextInt(list.size()));
               }

               SuperKnockback.setMove(
                  projectile,
                  -parameters.get("velocity"),
                  target.posX + (this.rand.nextFloat() - 0.5F) * 0.18F,
                  target.posY + target.height / 2.0F + (this.rand.nextFloat() - 0.5F) * 0.18F,
                  target.posZ + (this.rand.nextFloat() - 0.5F) * 0.18F
               );
            }

            ProjectileHelper.rotateTowardsMovement(projectile, Debugger.floats[0]);
            this.world.spawnEntity(projectile);
         }
      }

      if (this.prickedEntity != null) {
         this.prickedEntity.motionX = this.motionX;
         this.prickedEntity.motionY = this.motionY;
         this.prickedEntity.motionZ = this.motionZ;
         int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
         Weapons.dealDamage(
            new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.dismember),
            parameters.getEnchanted("damage_powered", might) * this.magicPower,
            this.getThrower(),
            this.prickedEntity,
            true
         );
         this.prickedEntity.hurtResistantTime = 0;
         DeathEffects.applyDeathEffect(this.prickedEntity, DeathEffects.DE_DISMEMBER, 0.95F);
      }

      this.setDead();
   }

   @SideOnly(Side.CLIENT)
   public void onEntityUpdate() {
      Vec3d pos1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
      Vec3d pos2 = this.getPositionVector();
      if (this.world.isRemote && pos1.lengthSquared() > 1.0E-6 && pos2.lengthSquared() > 1.0E-6) {
         BetweenParticle laser = new BetweenParticle(
            this.world, texture, 0.14F, 190, 0.83F, 0.91F, 0.78F, 0.1F, pos1.distanceTo(pos2), 8, -0.15F, 9999.0F, pos1, pos2
         );
         laser.alphaGlowing = true;
         laser.texstart = 0.1F;
         laser.offset = 0.1F;
         laser.setPosition(pos1.x, pos1.y, pos1.z);
         this.world.spawnEntity(laser);
      }
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
}
