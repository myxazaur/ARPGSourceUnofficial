package com.vivern.arpg.entity;

import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.renders.AnimatedGParticle;
import com.vivern.arpg.renders.GUNParticle;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
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

public class EntitySnapball extends EntityThrowable implements IEntitySynchronize {
   public final ItemStack weaponstack;
   public boolean powered = false;
   public boolean exploding = false;
   public boolean enablePhysics = false;
   public boolean pickable = false;
   public boolean picked = false;
   public int lastBounces = 3;
   public int attackCooldown = 0;
   public List<Entity> ignore = new ArrayList<>();
   public float renderscale = 1.0F;
   public double settedX = this.posX;
   public double settedY = this.posY;
   public double settedZ = this.posZ;
   static ResourceLocation texture = new ResourceLocation("arpg:textures/generic_beam4.png");
   static ResourceLocation flame = new ResourceLocation("arpg:textures/purple_smoke.png");
   static ResourceLocation blast = new ResourceLocation("arpg:textures/hadron_blast.png");

   public EntitySnapball(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.SNAPBALL);
      this.setSize(0.4F, 0.4F);
      this.setRenderYawOffset(0.0F);
   }

   public EntitySnapball(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.SNAPBALL);
      this.setSize(0.4F, 0.4F);
      this.setRenderYawOffset(0.0F);
   }

   public EntitySnapball(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.SNAPBALL);
      this.setSize(0.4F, 0.4F);
      this.setRenderYawOffset(0.0F);
   }

   public EntitySnapball(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.setSize(0.4F, 0.4F);
      this.setRenderYawOffset(0.0F);
   }

   public double getYOffset() {
      return 0.0;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      double mot = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) > 0 ? 0.2 : 0.6;
      this.motionX = this.motionX + entityThrower.motionX * mot;
      this.motionZ = this.motionZ + entityThrower.motionZ * mot;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * mot;
      }
   }

   protected float getGravityVelocity() {
      return this.enablePhysics ? 0.05F : 0.0F;
   }

   @Override
   public void onClient(double x, double y, double z, double a, double b, double c) {
      if (y == 0.0 && z == 0.0) {
         Entity e = this.world.getEntityByID((int)x);
         if (e instanceof EntityLivingBase) {
            this.thrower = (EntityLivingBase)e;
         }
      } else {
         double prevX = this.settedX;
         double prevY = this.settedY;
         double prevZ = this.settedZ;
         this.settedX = x;
         this.settedY = y;
         this.settedZ = z;
         this.setPositionAndUpdate(x, y, z);
         this.prevPosX = prevX;
         this.lastTickPosX = prevX;
         this.prevPosY = prevY;
         this.lastTickPosY = prevY;
         this.prevPosZ = prevZ;
         this.lastTickPosZ = prevZ;
         this.setVelocity(a, b, c);
      }
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 440) {
         this.setDead();
      }

      if (this.thrower != null
         && !this.world.isRemote
         && !this.powered
         && !this.picked
         && this.pickable
         && this.getDistanceSq(this.thrower) < 2.0) {
         this.picked = true;
         if (this.thrower.getHeldItemMainhand().getItem() == ItemsRegister.SNAPBALL) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            NBTHelper.AddNBTint(this.thrower.getHeldItemMainhand(), 3 - this.lastBounces, "charge");
            if (this.rand.nextFloat()
               < parameters.getEnchanted("reuse_chance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, this.weaponstack))) {
               this.world
                  .spawnEntity(
                     new EntityItem(this.world, this.posX, this.posY, this.posZ, new ItemStack(ItemsRegister.SNAPBALLAMMO))
                  );
            }

            boolean isPowered = NBTHelper.GetNBTint(this.thrower.getHeldItemMainhand(), "charge") > parameters.geti("charge_to_powered");
            if (isPowered) {
               this.world.setEntityState(this, (byte)11);
            }
         }

         this.world
            .playSound(
               (EntityPlayer)null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.snapball_pick,
               SoundCategory.AMBIENT,
               0.8F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         this.world.setEntityState(this, (byte)8);
      }

      if (this.picked && this.renderscale > 0.0F) {
         this.renderscale = (float)(this.renderscale - 0.1);
         if (!this.world.isRemote && this.renderscale <= 0.0F) {
            this.setDead();
         }
      }

      if (!this.world.isRemote && this.powered) {
         if (this.ticksExisted <= 1) {
            if (this.thrower != null) {
               IEntitySynchronize.sendSynchronize(this, 64.0, this.thrower.getEntityId());
            }

            this.world.setEntityState(this, (byte)9);
         }

         if (this.ticksExisted > 80) {
            this.expl(false);
         } else if (this.ticksExisted % 4 == 0) {
            WeaponParameters parametersx = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            double damageRadius = parametersx.getEnchanted("damage_radius_powered", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
               .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
               .offset(-damageRadius, -damageRadius, -damageRadius);
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
            if (!list.isEmpty()) {
               int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
               int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);

               for (Entity entity : list) {
                  if (Team.checkIsOpponent(this.thrower, entity)
                     && GetMOP.thereIsNoBlockCollidesBetween(
                        this.world, GetMOP.entityCenterPos(this), GetMOP.entityCenterPos(entity), 1.0F, null, false
                     )) {
                     Weapons.dealDamage(
                        new WeaponDamage(this.weaponstack, this.getThrower(), this, false, false, this, WeaponDamage.plasma),
                        parametersx.getEnchanted("damage_powered", might),
                        this.getThrower(),
                        entity,
                        true
                     );
                     entity.hurtResistantTime = 0;
                     DeathEffects.applyDeathEffect(entity, DeathEffects.DE_FIRE, 0.5F);
                  }
               }
            }
         }
      }

      this.attackCooldown--;
      if (this.enablePhysics && !this.world.isRemote) {
         double d0 = this.motionX;
         double d1 = this.motionY;
         double d2 = this.motionZ;
         double speed = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
         if (speed > 0.6) {
            this.motionX *= 0.7;
            this.motionY *= 0.7;
            this.motionZ *= 0.7;
         }

         double radiusSphere = 0.2F + speed;
         Vec3d point = new Vec3d(this.posX, this.posY + this.height / 2.0F, this.posZ);

         for (AxisAlignedBB aabb : this.world.getCollisionBoxes(this, this.getEntityBoundingBox().grow(5.0))) {
            if (aabb != null) {
               Vec3d collisionpoint = GetMOP.getNearestPointInAABBtoPoint(point, aabb);
               double dist = point.distanceTo(collisionpoint);
               if (dist <= 0.0) {
                  collisionpoint = aabb.getCenter();
               }

               if (dist <= radiusSphere) {
                  float absdist = (float)Math.abs(dist - radiusSphere);
                  SuperKnockback.applyMove(
                     this, absdist, collisionpoint.x, collisionpoint.y - this.height / 2.0F, collisionpoint.z
                  );
               }
            }
         }

         double a0 = this.motionX;
         double a1 = this.motionY;
         double a2 = this.motionZ;
         double speed2 = MathHelper.sqrt(a0 * a0 + a1 * a1 + a2 * a2);
         if (speed2 > speed) {
            double dl = speed2 / speed;
            this.motionX /= dl;
            this.motionY /= dl;
            this.motionZ /= dl;
         }

         if (this.exploding && EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) > 0) {
            WeaponParameters parametersx = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            double damageRadius = parametersx.getEnchanted("damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));

            for (Entity entityx : GetMOP.getEntitiesInAABBof(this.world, this, damageRadius * 0.6F, this)) {
               if (Team.checkIsOpponent(this.getThrower(), entityx) && (entityx instanceof EntityLivingBase || entityx instanceof EntitySnapball)) {
                  this.expl(false);
                  break;
               }
            }
         }
      }

      if (!this.world.isRemote) {
         IEntitySynchronize.sendSynchronize(
            this, 64.0, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ
         );
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 8) {
         this.picked = true;
      }

      if (id == 9) {
         this.powered = true;
      }

      if (id == 10) {
         for (int ss = 0; ss < 15; ss++) {
            GUNParticle fire = new GUNParticle(
               flame,
               0.5F + (float)this.rand.nextGaussian() / 3.0F,
               -0.003F,
               23 + this.rand.nextInt(15),
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 13.0F,
               (float)this.rand.nextGaussian() / 13.0F,
               (float)this.rand.nextGaussian() / 13.0F,
               0.96F,
               1.0F,
               0.3F + this.rand.nextFloat() * 0.2F,
               true,
               this.rand.nextInt(360)
            );
            fire.alphaTickAdding = -0.026F;
            fire.alphaGlowing = true;
            fire.scaleTickAdding = -0.015F;
            this.world.spawnEntity(fire);
         }

         AnimatedGParticle anim = new AnimatedGParticle(
            blast,
            1.7F,
            0.0F,
            13,
            240,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            0.0F,
            0.0F,
            0.0F,
            0.9F,
            1.0F,
            0.5F + this.rand.nextFloat() / 10.0F,
            true,
            this.rand.nextInt(360)
         );
         anim.framecount = 13;
         anim.alphaGlowing = true;
         anim.scaleTickAdding = 0.1F;
         anim.randomDeath = false;
         this.world.spawnEntity(anim);
      }

      if (id == 11) {
         this.world
            .playSound(
               (EntityPlayer)null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.alert,
               SoundCategory.AMBIENT,
               0.8F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (this.exploding && result != null && result.entityHit != null && Team.checkIsOpponent(this.thrower, result.entityHit)) {
         this.expl(false);
      }

      if (!this.enablePhysics && result != null) {
         if (result.entityHit != null && this.attackCooldown <= 0) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
               WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
               int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
               int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
               Weapons.dealDamage(
                  new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.bullet),
                  parameters.getEnchanted("damage", might),
                  this.getThrower(),
                  result.entityHit,
                  true,
                  parameters.getEnchanted("knockback", impulse),
                  this.thrower.posX,
                  this.thrower.posY,
                  this.thrower.posZ
               );
               result.entityHit.hurtResistantTime = 0;
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.snapball_impact,
                     SoundCategory.AMBIENT,
                     0.8F,
                     0.9F + this.rand.nextFloat() / 5.0F
                  );
               Vec3d vec = new Vec3d(result.entityHit.posX, this.posY, result.entityHit.posZ);
               AxisAlignedBB bb = this.getEntityBoundingBox()
                  .offset(vec.x - this.posX, vec.y - this.posY, vec.z - this.posZ);
               if (this.world.getCollisionBoxes(this, bb).isEmpty()) {
                  this.setPositionAndUpdate(vec.x, vec.y, vec.z);
               }

               this.ignore.add(result.entityHit);
               this.changeDirection(false, result);
            }
         } else if (result.getBlockPos() != null
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
                  Sounds.snapball_impact,
                  SoundCategory.AMBIENT,
                  0.8F,
                  0.9F + this.rand.nextFloat() / 5.0F
               );
            if (!this.world.isRemote) {
               this.changeDirection(true, result);
            }
         }
      }
   }

   public void changeDirection(boolean onBlock, RayTraceResult result) {
      if (this.powered) {
         this.expl(true);
      } else if (this.lastBounces <= 0) {
         this.enablePhysics = true;
         this.motionX /= 2.0;
         this.motionY /= 2.0;
         this.motionZ /= 2.0;
         this.pickable = true;
      } else {
         this.attackCooldown = 3;
         this.lastBounces--;
         double max = Double.MAX_VALUE;
         EntityLivingBase targ = null;
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
         double damageRadius = parameters.getEnchanted("bounce_find_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
         if (!list.isEmpty()) {
            for (EntityLivingBase entitylivingbase : list) {
               if (entitylivingbase.isCreatureType(EnumCreatureType.MONSTER, false)
                  && Team.checkIsOpponent(this.thrower, entitylivingbase)
                  && !this.ignore.contains(entitylivingbase)
                  && entitylivingbase != this.getThrower()
                  && EntitySelectors.NOT_SPECTATING.apply(entitylivingbase)
                  && entitylivingbase.getHealth() > 0.0F
                  && GetMOP.thereIsNoBlockCollidesBetween(
                     this.world, GetMOP.entityCenterPos(this), GetMOP.entityCenterPos(entitylivingbase), 1.0F, null, false
                  )) {
                  double dist = entitylivingbase.getDistanceSq(this);
                  if (dist < max) {
                     max = dist;
                     targ = entitylivingbase;
                  }
               }
            }

            if (targ == null) {
               for (EntityLivingBase entitylivingbasex : list) {
                  if (Team.checkIsOpponent(this.thrower, entitylivingbasex)
                     && !this.ignore.contains(entitylivingbasex)
                     && entitylivingbasex != this.getThrower()
                     && EntitySelectors.NOT_SPECTATING.apply(entitylivingbasex)
                     && entitylivingbasex.getHealth() > 0.0F
                     && GetMOP.thereIsNoBlockCollidesBetween(
                        this.world, GetMOP.entityCenterPos(this), GetMOP.entityCenterPos(entitylivingbasex), 1.0F, null, false
                     )) {
                     double dist = entitylivingbasex.getDistanceSq(this);
                     if (dist < max) {
                        max = dist;
                        targ = entitylivingbasex;
                     }
                  }
               }
            }
         }

         if (targ != null) {
            SuperKnockback.setMove(this, -1.0F, targ.posX, targ.posY + targ.height / 2.0F, targ.posZ);
         } else {
            if (onBlock && result != null && result.sideHit != null) {
               if (result.sideHit == EnumFacing.UP || result.sideHit == EnumFacing.DOWN) {
                  this.motionY *= -0.9;
               }

               if (result.sideHit == EnumFacing.NORTH || result.sideHit == EnumFacing.SOUTH) {
                  this.motionZ = -this.motionZ;
               }

               if (result.sideHit == EnumFacing.EAST || result.sideHit == EnumFacing.WEST) {
                  this.motionX = -this.motionX;
               }
            }

            this.motionX /= 3.0;
            this.motionY /= 3.0;
            this.motionZ /= 3.0;
            this.enablePhysics = true;
            this.pickable = true;
         }
      }
   }

   public void expl(boolean inChangeDerection) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
      if (this.powered
         && EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) > 0
         && this.ticksExisted < parameters.geti("minimal_special_livetime")) {
         if (inChangeDerection) {
            this.motionX = -this.motionX;
            this.motionY = -this.motionY;
            this.motionZ = -this.motionZ;
         }
      } else {
         double damageRadius = parameters.getEnchanted("damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         if (!this.world.isRemote) {
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
            if (!list.isEmpty()) {
               int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
               int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);

               for (Entity entity : list) {
                  if (Team.checkIsOpponent(this.thrower, entity)) {
                     Weapons.dealDamage(
                        new WeaponDamage(this.weaponstack, this.getThrower(), this, true, false, this, WeaponDamage.explode),
                        parameters.getEnchanted("damage_explode", might),
                        this.thrower,
                        entity,
                        true,
                        parameters.getEnchanted("knockback_explode", impulse),
                        this.posX,
                        this.posY - 0.3,
                        this.posZ
                     );
                     entity.hurtResistantTime = 0;
                     if (entity instanceof EntityLivingBase) {
                        EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                        if (entitylivingbase.getHealth() <= 0.0F && this.rand.nextFloat() < 0.3 && entitylivingbase.deathTime < 1) {
                           DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_FIRE);
                        }
                     }
                  }
               }
            }
         }

         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)10);
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.snapball_explode,
                  SoundCategory.AMBIENT,
                  1.75F,
                  0.9F + this.rand.nextFloat() / 5.0F
               );
            this.setDead();
         }
      }
   }

   public void onEntityUpdate() {
      super.onEntityUpdate();
      if (this.world.isRemote && this.powered && this.ticksExisted % 4 == 0) {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
         double damageRadius = parameters.getEnchanted("damage_radius_powered", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
         Vec3d pos1 = new Vec3d(
            this.posX + this.motionX,
            this.posY + this.height / 2.0F + this.motionY,
            this.posZ + this.motionZ
         );
         if (!list.isEmpty()) {
            for (EntityLivingBase entity : list) {
               if (Team.checkIsOpponent(this.thrower, entity)) {
                  Vec3d pos2 = GetMOP.entityCenterPos(entity);
                  if (GetMOP.thereIsNoBlockCollidesBetween(this.world, pos1, pos2, 1.0F, null, false)) {
                     BetweenParticle laser = new BetweenParticle(
                        this.world, texture, 0.55F, 240, 1.0F, 0.96F, 0.3F, 0.0F, pos1.distanceTo(pos2), 4, 0.16F, 6.0F, pos1, pos2
                     );
                     laser.setPosition(pos1.x, pos1.y, pos1.z);
                     laser.alphaGlowing = true;
                     laser.softAnimation = true;
                     this.world.spawnEntity(laser);
                  }
               }
            }
         }
      }
   }
}
