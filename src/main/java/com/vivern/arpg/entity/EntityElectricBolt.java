package com.vivern.arpg.entity;

import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.potions.PotionEffects;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.renders.ParticleTracker;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityElectricBolt extends EntityThrowable implements IEntitySynchronize {
   public final ItemStack weaponstack;
   public float magicPower = 1.0F;
   public Vec3d pos1 = this.getPositionVector();
   public Vec3d pos2 = this.getPositionVector();
   public Vec3d pos3 = this.getPositionVector();
   public Vec3d pos4 = this.getPositionVector();
   public Vec3d pos5 = this.getPositionVector();
   public int livetime = 100;
   ResourceLocation texture = new ResourceLocation("arpg:textures/shock.png");
   ResourceLocation texturexpl = new ResourceLocation("arpg:textures/blueexplode3.png");
   public static ParticleTracker.TrackerSmoothShowHide trackerssh = new ParticleTracker.TrackerSmoothShowHide(
      null, new Vec3d[]{new Vec3d(0.0, 3.0, 0.3), new Vec3d(2.0, 5.0, -0.3)}
   );

   public EntityElectricBolt(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.ELECTRICSTAFF);
   }

   public EntityElectricBolt(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.ELECTRICSTAFF);
   }

   public EntityElectricBolt(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.ELECTRICSTAFF);
   }

   public EntityElectricBolt(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
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
         this.motionY = this.motionY + entityThrower.motionY * 0.1;
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

      if (this.pos4 != Vec3d.ZERO) {
         this.pos5 = this.pos4;
      } else {
         this.pos5 = this.getPositionVector();
      }

      if (this.pos3 != Vec3d.ZERO) {
         this.pos4 = this.pos3;
      } else {
         this.pos4 = this.getPositionVector();
      }

      if (this.pos2 != Vec3d.ZERO) {
         this.pos3 = this.pos2;
      } else {
         this.pos3 = this.getPositionVector();
      }

      if (this.pos1 != Vec3d.ZERO) {
         this.pos2 = this.pos1;
      } else {
         this.pos2 = this.getPositionVector();
      }

      if (this.getPositionVector() != Vec3d.ZERO) {
         this.pos1 = this.getPositionVector();
      } else {
         this.pos1 = this.thrower.getPositionVector();
      }
   }

   @Override
   public void onClient(double x, double y, double z, double a, double b, double c) {
      GUNParticle sp = new GUNParticle(
         this.texturexpl,
         0.1F + this.rand.nextFloat() / 4.0F,
         0.0F,
         4,
         240,
         this.world,
         x,
         y,
         z,
         0.0F,
         0.0F,
         0.0F,
         1.0F,
         1.0F - this.rand.nextFloat() / 20.0F,
         1.0F,
         true,
         this.rand.nextInt(360)
      );
      sp.alphaGlowing = true;
      sp.randomDeath = false;
      sp.tracker = trackerssh;
      this.world.spawnEntity(sp);
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
            int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
            int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, this.weaponstack);
            Weapons.dealDamage(
               new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.electric),
               parameters.getEnchanted("damage", might) * this.magicPower,
               this.getThrower(),
               result.entityHit,
               true,
               parameters.getEnchanted("knockback", impulse),
               this.posX,
               this.posY,
               this.posZ
            );
            result.entityHit.hurtResistantTime = 0;
            if (result.entityHit instanceof EntityLivingBase) {
               EntityLivingBase entitylivingbase = (EntityLivingBase)result.entityHit;
               PotionEffect baff = parameters.getPotion("shock", PotionEffects.SHOCK, witchery);
               entitylivingbase.addPotionEffect(baff);
               if (entitylivingbase.getHealth() <= 0.0F && this.rand.nextFloat() > 0.25) {
                  DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_ELECTRIC);
               }
            }

            IEntitySynchronize.sendSynchronize(this, 64.0, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.electricstaffimpact,
                  SoundCategory.AMBIENT,
                  0.6F,
                  0.95F + this.rand.nextFloat() / 10.0F,
                  false
               );
            this.setDead();
         }
      } else if (this.world
            .getBlockState(result.getBlockPos())
            .getBlock()
            .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
         != null) {
         if (result.hitVec != null) {
            IEntitySynchronize.sendSynchronize(
               this, 64.0, result.hitVec.x, result.hitVec.y, result.hitVec.z, 0.0, 0.0, 0.0
            );
         }

         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.electricstaffimpact,
               SoundCategory.AMBIENT,
               0.6F,
               0.95F + this.rand.nextFloat() / 10.0F,
               false
            );
         this.setDead();
      }
   }

   @SideOnly(Side.CLIENT)
   public void onEntityUpdate() {
      Vec3d subtraction = this.pos1.subtract(this.pos2);
      if (this.ticksExisted > 2 && this.world.isRemote && this.pos1.lengthSquared() > 1.0E-6 && this.pos2.lengthSquared() > 1.0E-6) {
         BetweenParticle laser = new BetweenParticle(
            this.world,
            this.texture,
            0.2F,
            240,
            1.0F,
            1.0F,
            1.0F,
            0.17F,
            this.pos1.distanceTo(this.pos2),
            Math.min(4, this.ticksExisted - 2),
            0.3F,
            2.0F,
            this.pos1,
            this.pos2
         );
         laser.setPosition(this.posX, this.posY, this.posZ);
         laser.alphaGlowing = true;
         this.world.spawnEntity(laser);
      }
   }
}
