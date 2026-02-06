package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.elements.ItemBullet;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.ParticleTracker;
import com.Vivern.Arpg.renders.RenderModule;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CoralRifleBullet extends StandardBullet implements IEntitySynchronize, RenderModule.IRenderModuleMulticolored {
   public final ItemStack weaponstack;
   public boolean bulletCollided = false;
   public ItemBullet bullet;
   public int livetime = 20;
   static ResourceLocation star = new ResourceLocation("arpg:textures/magic_rocket.png");
   static ResourceLocation shard1 = new ResourceLocation("arpg:textures/minisand1.png");
   static ResourceLocation shard2 = new ResourceLocation("arpg:textures/minisand2.png");
   static ResourceLocation shard3 = new ResourceLocation("arpg:textures/minisand3.png");
   public static ResourceLocation[] shards = new ResourceLocation[]{shard1, shard2, shard3};

   public CoralRifleBullet(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.CORALRIFLE);
   }

   public CoralRifleBullet(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.CORALRIFLE);
   }

   public CoralRifleBullet(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.CORALRIFLE);
   }

   public CoralRifleBullet(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
   }

   @Override
   public Vec3d getColor(int index) {
      return new Vec3d(this.getRED(), this.getGREEN(), this.getBLUE());
   }

   @Override
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

   @Override
   public float getGravityVelocity() {
      return 0.0F;
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      ItemBullet bullet = ItemBullet.getItemBulletFromString(NBTHelper.GetNBTstring(this.weaponstack, "bullet"));
      if (bullet != null) {
         bullet.onProjectileUpdate(this);
      }

      if (this.ticksExisted > this.livetime) {
         this.setDead();
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 8) {
      }
   }

   @Override
   public void onClient(double x, double y, double z, double a, double b, double c) {
      for (int ss = 0; ss < 3; ss++) {
         float delScl = 10 + this.rand.nextInt(5);
         float delSpd = 25.0F - delScl;
         float scl = 0.13F + (float)this.rand.nextGaussian() / delScl;
         GUNParticle bigsmoke = new GUNParticle(
            star,
            scl,
            0.0F,
            20,
            240,
            this.world,
            x,
            y,
            z,
            (float)this.rand.nextGaussian() / delSpd,
            (float)this.rand.nextGaussian() / delSpd,
            (float)this.rand.nextGaussian() / delSpd,
            this.getRED(),
            this.getGREEN(),
            this.getBLUE(),
            true,
            this.rand.nextInt(360)
         );
         bigsmoke.scaleTickAdding = -scl / 20.0F;
         bigsmoke.alphaGlowing = true;
         this.world.spawnEntity(bigsmoke);
      }

      for (int ss = 0; ss < this.rand.nextInt(3) + 2; ss++) {
         GUNParticle part = new GUNParticle(
            shards[this.rand.nextInt(3)],
            0.03F + this.rand.nextFloat() / 35.0F,
            0.08F,
            30 + this.rand.nextInt(20),
            -1,
            this.world,
            x,
            y,
            z,
            (float)this.rand.nextGaussian() / 13.0F,
            (float)this.rand.nextGaussian() / 13.0F + 0.1F,
            (float)this.rand.nextGaussian() / 13.0F,
            this.getRED(),
            this.getGREEN(),
            this.getBLUE(),
            false,
            this.rand.nextInt(360),
            true,
            4.0F
         );
         part.dropMode = true;
         part.rotationPitchYaw = new Vec2f(this.rand.nextInt(360), this.rand.nextInt(360));
         part.tracker = new ParticleTracker.TrackerGlassShard(
            (float)this.rand.nextGaussian() * 2.0F, (float)this.rand.nextGaussian() * 2.0F, (int)this.rand.nextGaussian() * 2, true
         );
         this.world.spawnEntity(part);
      }
   }

   @Override
   public void onImpact(RayTraceResult result) {
      if (this.bullet != null && !this.bulletCollided) {
         this.bulletCollided = this.bullet
            .onImpact(this.world, (EntityPlayer)this.getThrower(), this.posX, this.posY, this.posZ, result, this);
      }

      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            float bdamage = parameters.getEnchanted("damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack));
            float bknockback = parameters.getEnchanted("knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack));
            if (this.bullet != null) {
               bdamage += this.bullet.damage * parameters.get("bullet_damage");
               bknockback += this.bullet.knockback * parameters.get("bullet_knockback");
            }

            Weapons.dealDamage(
               new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.bullet),
               bdamage,
               this.getThrower(),
               result.entityHit,
               true,
               bknockback
            );
            result.entityHit.hurtResistantTime = 0;
            if (result.entityHit instanceof EntityLivingBase) {
               EntityLivingBase entitylivingbase = (EntityLivingBase)result.entityHit;
               if (this.bullet != null) {
                  this.bullet.onDamageCause(this.world, entitylivingbase, (EntityPlayer)this.getThrower(), this);
               }

               if (entitylivingbase.getHealth() <= 0.0F && this.rand.nextFloat() < 0.1) {
                  DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_DISMEMBER);
               }
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
            IEntitySynchronize.sendSynchronize(
               this, 64.0, result.hitVec.x, result.hitVec.y, result.hitVec.z, -1.0
            );
            this.setDead();
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
               Sounds.bullet,
               SoundCategory.AMBIENT,
               0.8F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         if (!this.world.isRemote) {
            if (result.hitVec != null) {
               IEntitySynchronize.sendSynchronize(
                  this, 64.0, result.hitVec.x, result.hitVec.y, result.hitVec.z, 0.0, 0.0, 0.0
               );
            }

            this.setDead();
         }
      }
   }

   public boolean handleWaterMovement() {
      return false;
   }
}
