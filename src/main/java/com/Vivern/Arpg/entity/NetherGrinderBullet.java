//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.elements.IRepulsable;
import com.Vivern.Arpg.elements.ItemBullet;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.ParticleFastSummon;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.RenderModule;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class NetherGrinderBullet extends StandardBullet implements IRepulsable, IEntitySynchronize, RenderModule.IRenderModuleMulticolored {
   public final ItemStack weaponstack;
   public ItemBullet bullet;
   public int inwalltime = 0;
   public int livetime = 20;
   public boolean bulletCollided = false;
   ResourceLocation texture = new ResourceLocation("arpg:textures/bullet_trace.png");
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largesmoke.png");

   public NetherGrinderBullet(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.NETHERGRINDER);
   }

   public NetherGrinderBullet(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.NETHERGRINDER);
   }

   public NetherGrinderBullet(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.NETHERGRINDER);
   }

   public NetherGrinderBullet(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
   }

   @Override
   public Vec3d getColor(int index) {
      return index == 1
         ? new Vec3d(this.getRED(), this.getGREEN(), this.getBLUE())
         : new Vec3d(Math.min(this.getRED() + 0.2F, 1.0F), Math.min(this.getGREEN() + 0.2F, 1.0F), Math.min(this.getBLUE() + 0.2F, 1.0F));
   }

   @Override
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

   @Override
   public float getGravityVelocity() {
      return 0.0F;
   }

   @Override
   public void onClient(double... args) {
      if (args.length == 4) {
         Vec3d to = new Vec3d(args[0], args[1], args[2]);
         Vec3d from = to.subtract(this.motionX, this.motionY, this.motionZ);
         ParticleFastSummon.bulletImpact(
            this.world, 2, 0.12F, this.getRED(), this.getGREEN(), this.getBLUE(), from, to, 0.32F, 0.11F, 0.17F, 5, 8, 3, (int)args[3]
         );
      }
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

   public boolean repulse(Entity entityHit) {
      int rep = Weapons.getEntityRepulseType(entityHit);
      if (rep == 0) {
         return false;
      } else if (entityHit instanceof EntityThrowable && ((EntityThrowable)entityHit).getThrower() == this.getThrower()) {
         return false;
      } else if (rep == 1 || rep == 5) {
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
      } else if (rep == 2) {
         entityHit.motionX = entityHit.motionX + this.motionX / 2.0;
         entityHit.motionY = entityHit.motionY + this.motionY / 2.0;
         entityHit.motionZ = entityHit.motionZ + this.motionZ / 2.0;
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
      } else if (rep == 3 || rep == 4) {
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
      } else if (rep != 7 && rep != 8) {
         return false;
      } else {
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
      }
   }

   @Override
   public void onImpact(RayTraceResult result) {
      if (this.bullet != null && !this.bulletCollided) {
         this.bulletCollided = this.bullet
            .onImpact(this.world, (EntityPlayer)this.getThrower(), this.posX, this.posY, this.posZ, result, this);
      }

      if (result.entityHit != null) {
         if (!this.repulse(result.entityHit) && Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            float bdamage = parameters.getEnchanted("damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack));
            float bknockback = parameters.getEnchanted("knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack));
            if (this.bullet != null) {
               bdamage += this.bullet.damage * parameters.get("bullet_damage");
               bknockback += this.bullet.knockback * parameters.get("bullet_knockback");
            }

            if (result.entityHit.isBurning()) {
               bdamage += parameters.get("fire_bonus");
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

               if (entitylivingbase.getHealth() <= 0.0F && this.rand.nextFloat() < 0.15) {
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
            if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) > 0) {
               this.inwalltime++;
               if (this.inwalltime > 1) {
                  IEntitySynchronize.sendSynchronize(
                     this,
                     64.0,
                     result.hitVec.x,
                     result.hitVec.y,
                     result.hitVec.z,
                     Block.getStateId(this.world.getBlockState(result.getBlockPos()))
                  );
                  this.setDead();
               }
            } else {
               IEntitySynchronize.sendSynchronize(
                  this,
                  64.0,
                  result.hitVec.x,
                  result.hitVec.y,
                  result.hitVec.z,
                  Block.getStateId(this.world.getBlockState(result.getBlockPos()))
               );
               this.setDead();
            }
         }
      }
   }

   @Override
   public int getRepulseType() {
      return 0;
   }
}
