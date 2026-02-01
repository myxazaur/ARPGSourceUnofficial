package com.vivern.arpg.entity;

import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlasmaAcceleratorShoot extends EntityThrowable {
   public final ItemStack weaponstack;
   static ResourceLocation trail = new ResourceLocation("arpg:textures/generic_beam.png");
   static ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largesmoke.png");
   static ResourceLocation flame = new ResourceLocation("arpg:textures/plasma_cloud.png");
   static ResourceLocation cloud = new ResourceLocation("arpg:textures/plasma_accelerator_shoot.png");
   public int livetime = 40;

   public PlasmaAcceleratorShoot(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.PLASMAPISTOL);
   }

   public PlasmaAcceleratorShoot(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.PLASMAPISTOL);
   }

   public PlasmaAcceleratorShoot(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.PLASMAPISTOL);
   }

   public PlasmaAcceleratorShoot(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.2;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.2;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.2;
      }
   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (!this.world.isRemote && this.ticksExisted > this.livetime) {
         this.setDead();
      }

      this.world.setEntityState(this, (byte)5);
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 8) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.plasma_accelerator_impact,
               SoundCategory.AMBIENT,
               1.5F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );

         for (int ss = 0; ss < 10; ss++) {
            GUNParticle bigsmoke = new GUNParticle(
               largesmoke,
               0.5F + this.rand.nextFloat() / 2.0F,
               -0.001F,
               40,
               60,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 20.0F,
               (float)this.rand.nextGaussian() / 25.0F,
               (float)this.rand.nextGaussian() / 20.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            bigsmoke.alphaTickAdding = -0.01F;
            this.world.spawnEntity(bigsmoke);
         }

         float rb = this.rand.nextFloat() / 2.0F + 0.4F;
         float g = 0.7F + (float)this.rand.nextGaussian() / 4.0F;

         for (int ss = 0; ss < 25; ss++) {
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
               rb,
               g,
               rb + (float)this.rand.nextGaussian() / 7.0F,
               true,
               this.rand.nextInt(100) - 50
            );
            fire.alphaTickAdding = -0.026F;
            fire.alphaGlowing = true;
            fire.scaleTickAdding = -0.015F;
            this.world.spawnEntity(fire);
         }

         for (int ss = 0; ss < 3 + this.rand.nextInt(3); ss++) {
            TrailParticle trailpart = new TrailParticle(
               cloud,
               0.2F,
               0.04F,
               25,
               240,
               this.world,
               this.lastTickPosX,
               this.lastTickPosY,
               this.lastTickPosZ,
               (float)this.rand.nextGaussian() / 5.0F,
               (float)this.rand.nextGaussian() / 5.0F + 0.15F,
               (float)this.rand.nextGaussian() / 5.0F,
               rb,
               g,
               rb + (float)this.rand.nextGaussian() / 10.0F,
               true,
               this.rand.nextInt(180),
               true,
               1.1F,
               trail,
               0.14F,
               240,
               rb,
               g,
               rb + (float)this.rand.nextGaussian() / 10.0F,
               0.15F,
               5,
               0.3F,
               1.0F
            );
            trailpart.alphaGlowing = true;
            this.world.spawnEntity(trailpart);
         }
      }

      if (id == 5) {
         GUNParticle fire2 = new GUNParticle(
            cloud,
            0.25F,
            0.0F,
            15,
            240,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            0.0F,
            0.0F,
            0.0F,
            1.0F,
            0.8F + this.rand.nextFloat() / 5.0F,
            0.8F + this.rand.nextFloat() / 5.0F,
            true,
            this.rand.nextInt(360)
         );
         fire2.alphaTickAdding = -0.06F;
         fire2.alphaGlowing = true;
         this.world.spawnEntity(fire2);
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit) && Weapons.canDealDamageTo(result.entityHit)) {
            this.expl(result);
         }
      } else if (this.world
            .getBlockState(result.getBlockPos())
            .getBlock()
            .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
         != null) {
         this.expl(result);
      }
   }

   public void expl(RayTraceResult result) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
      double damageRadius = parameters.getEnchanted("damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
      int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
      int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      if (!this.world.isRemote && result.hitVec != null) {
         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               if (Team.checkIsOpponent(this.thrower, entity)) {
                  float damage = parameters.getEnchanted("damage", might)
                     * Math.max(
                        parameters.get("damage_multiplier")
                           - (float)Weapons.getDistanceToMobHitbox(
                              entity.width,
                              entity.height,
                              entity.posX,
                              entity.posY,
                              entity.posZ,
                              result.hitVec.x,
                              result.hitVec.y,
                              result.hitVec.z
                           ),
                        0.1F
                     );
                  entity.hurtResistantTime = 0;
                  Weapons.dealDamage(
                     new WeaponDamage(this.weaponstack, this.getThrower(), this, true, false, this, WeaponDamage.explode),
                     damage,
                     this.getThrower(),
                     entity,
                     true,
                     parameters.getEnchanted("knockback", impulse),
                     this.posX,
                     this.posY,
                     this.posZ
                  );
                  entity.hurtResistantTime = 0;
                  DeathEffects.applyDeathEffect(entity, DeathEffects.DE_DISMEMBER, 0.55F);
               } else {
                  float damage = parameters.getEnchanted("friendlyfire_damage", might)
                     * Math.max(
                        parameters.get("damage_multiplier")
                           - (float)Weapons.getDistanceToMobHitbox(
                              entity.width,
                              entity.height,
                              entity.posX,
                              entity.posY,
                              entity.posZ,
                              result.hitVec.x,
                              result.hitVec.y,
                              result.hitVec.z
                           ),
                        0.1F
                     )
                     * parameters.get("friendlyfire");
                  entity.hurtResistantTime = 0;
                  Weapons.dealDamage(
                     new WeaponDamage(this.weaponstack, this.getThrower(), this, true, false, this, WeaponDamage.explode),
                     damage,
                     this.getThrower(),
                     entity,
                     true,
                     parameters.getEnchanted("knockback", impulse),
                     this.posX,
                     this.posY,
                     this.posZ
                  );
                  DeathEffects.applyDeathEffect(entity, DeathEffects.DE_DISMEMBER, 0.55F);
               }
            }
         }

         if (this.world.isAreaLoaded(this.getPosition().add(-16, -16, -16), this.getPosition().add(16, 16, 16))) {
            for (BlockPos blockpos : GetMOP.getPosesInsideSphere(
               (int)this.posX,
               (int)this.posY,
               (int)this.posZ,
               parameters.getEnchantedi("radius_blocks", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack))
            )) {
               IBlockState state = this.world.getBlockState(blockpos);
               if (Weapons.easyBreakBlockFor(this.world, parameters.getEnchanted("hardness_breaks", might), blockpos, state)) {
                  if (!state.getBlock().hasTileEntity(state) && !(this.rand.nextFloat() < 0.3F)) {
                     EntityFallingBlock entityfallingblock = new EntityFallingBlock(
                        this.world, blockpos.getX() + 0.5, blockpos.getY() + 0.2, blockpos.getZ() + 0.5, state
                     );
                     this.world.spawnEntity(entityfallingblock);
                     SuperKnockback.applyKnockback(
                        entityfallingblock,
                        parameters.getEnchanted("knockback_to_blocks", impulse),
                        this.posX,
                        this.posY - 0.2,
                        this.posZ
                     );
                     entityfallingblock.velocityChanged = true;
                  } else {
                     this.world.destroyBlock(blockpos, true);
                  }
               }
            }
         }

         this.world.setEntityState(this, (byte)8);
         this.setDead();
      }
   }
}
