package com.vivern.arpg.entity;

import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class StaffFireballEntity extends EntityThrowable {
   public final ItemStack weaponstack;
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largesmoke.png");
   ResourceLocation flame = new ResourceLocation("arpg:textures/flame_big.png");
   public float magicPower = 1.0F;

   public StaffFireballEntity(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.FIREBALLSTAFF);
   }

   public StaffFireballEntity(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.FIREBALLSTAFF);
   }

   public StaffFireballEntity(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.FIREBALLSTAFF);
   }

   public StaffFireballEntity(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.magicPower = power;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.1;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.1;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.2;
      }
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 60) {
         this.expl();
      }

      this.world.setEntityState(this, (byte)8);
   }

   protected float getGravityVelocity() {
      return 0.004F;
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 5) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               SoundEvents.ENTITY_GENERIC_EXPLODE,
               SoundCategory.AMBIENT,
               2.0F,
               0.95F + this.rand.nextFloat() / 10.0F,
               false
            );

         for (int ss = 0; ss < 10; ss++) {
            GUNParticle bigsmoke = new GUNParticle(
               this.largesmoke,
               0.6F + (float)this.rand.nextGaussian() / 2.0F,
               -0.002F,
               54,
               60,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 16.0F,
               (float)this.rand.nextGaussian() / 20.0F,
               (float)this.rand.nextGaussian() / 16.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            bigsmoke.alphaTickAdding = -0.028F;
            bigsmoke.scaleTickAdding = 0.03F + this.rand.nextFloat() / 20.0F;
            this.world.spawnEntity(bigsmoke);
         }

         for (int ss = 0; ss < 27; ss++) {
            GUNParticle fire = new GUNParticle(
               this.flame,
               0.14F + (float)this.rand.nextGaussian() / 5.0F,
               -0.005F,
               15 + this.rand.nextInt(10),
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 7.0F,
               (float)this.rand.nextGaussian() / 7.0F + 0.05F,
               (float)this.rand.nextGaussian() / 7.0F,
               1.0F,
               0.8F + (float)this.rand.nextGaussian() / 5.0F,
               1.0F,
               true,
               this.rand.nextInt(100) - 50
            );
            fire.alphaTickAdding = -0.04F;
            fire.alphaGlowing = true;
            fire.scaleTickAdding = 0.05F + this.rand.nextFloat() / 10.0F;
            this.world.spawnEntity(fire);
         }
      }

      if (id == 8) {
         GUNParticle fire2 = new GUNParticle(
            this.flame,
            0.13F + (float)this.rand.nextGaussian() / 15.0F,
            -0.009F,
            10 + this.rand.nextInt(5),
            240,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            0.0F,
            0.0F,
            0.0F,
            1.0F,
            0.8F + (float)this.rand.nextGaussian() / 5.0F,
            1.0F,
            true,
            this.rand.nextInt(100) - 50
         );
         fire2.alphaTickAdding = -0.1F;
         fire2.alphaGlowing = true;
         this.world.spawnEntity(fire2);
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit)) {
            this.expl();
         }
      } else if (this.world
            .getBlockState(result.getBlockPos())
            .getBlock()
            .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
         != null) {
         this.expl();
      }
   }

   public void expl() {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
      double damageRadius = parameters.getEnchanted("damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      if (!this.world.isRemote) {
         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this.thrower, axisalignedbb);
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               float friendlyfire = Team.checkIsOpponent(this.thrower, entity) ? 1.0F : parameters.get("friendlyfire");
               int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
               int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
               int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, this.weaponstack);
               Weapons.dealDamage(
                  new WeaponDamage(this.weaponstack, this.getThrower(), this, true, false, this, WeaponDamage.explode),
                  parameters.getEnchanted("damage", might) * this.magicPower * friendlyfire,
                  this.getThrower(),
                  entity,
                  true,
                  parameters.getEnchanted("knockback", impulse) * friendlyfire,
                  this.posX,
                  this.posY,
                  this.posZ
               );
               entity.hurtResistantTime = 0;
               entity.setFire(Math.round(parameters.getEnchanted("fire", witchery) * friendlyfire));
               if (entity instanceof EntityLivingBase) {
                  EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                  if (entitylivingbase.getHealth() <= 0.0F && this.rand.nextFloat() < 0.6 && entitylivingbase.deathTime < 1) {
                     DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_DISMEMBER);
                  }
               }
            }
         }

         if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) > 0) {
            Explosion explosion = new Explosion(
               this.world, null, this.posX, this.posY, this.posZ, parameters.get("special_explosion_size"), false, true
            );
            explosion.doExplosionA();
            explosion.doExplosionB(false);
         }

         this.world.setEntityState(this, (byte)5);
         this.setDead();
      }
   }
}
