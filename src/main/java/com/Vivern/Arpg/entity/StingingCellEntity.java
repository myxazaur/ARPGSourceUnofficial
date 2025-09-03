//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class StingingCellEntity extends EntityThrowable {
   public final ItemStack weaponstack;
   public float magicPower = 1.0F;
   public float moveX;
   public float moveY;
   public float moveZ;
   public boolean throwed;
   public float scale = 1.0F;
   public boolean scalereduct = false;
   public float scaleadd = 0.0F;
   public int livetime = 400;
   ResourceLocation slime = new ResourceLocation("arpg:textures/slimesplash.png");
   ResourceLocation texturbubble = new ResourceLocation("arpg:textures/blob.png");

   public StingingCellEntity(World world) {
      super(world);
      this.setSize(0.35F, 0.35F);
      this.weaponstack = new ItemStack(ItemsRegister.STINGINGCELL);
      this.moveX = (this.rand.nextFloat() - 0.5F) / 100.0F;
      this.moveY = (this.rand.nextFloat() - 0.5F) / 100.0F;
      this.moveZ = (this.rand.nextFloat() - 0.5F) / 100.0F;
   }

   public StingingCellEntity(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.setSize(0.35F, 0.35F);
      this.weaponstack = new ItemStack(ItemsRegister.STINGINGCELL);
      this.moveX = (this.rand.nextFloat() - 0.5F) / 100.0F;
      this.moveY = (this.rand.nextFloat() - 0.5F) / 100.0F;
      this.moveZ = (this.rand.nextFloat() - 0.5F) / 100.0F;
   }

   public StingingCellEntity(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.setSize(0.35F, 0.35F);
      this.weaponstack = new ItemStack(ItemsRegister.STINGINGCELL);
      this.moveX = (this.rand.nextFloat() - 0.5F) / 100.0F;
      this.moveY = (this.rand.nextFloat() - 0.5F) / 100.0F;
      this.moveZ = (this.rand.nextFloat() - 0.5F) / 100.0F;
   }

   public StingingCellEntity(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
      super(world, thrower);
      this.setSize(0.35F, 0.35F);
      this.weaponstack = itemstack;
      this.magicPower = power;
   }

   public void setupMove() {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
      float movespeed = parameters.get("move_speed");
      this.moveX = (this.rand.nextFloat() - 0.5F) * movespeed;
      this.moveY = (this.rand.nextFloat() - 0.5F) * movespeed;
      this.moveZ = (this.rand.nextFloat() - 0.5F) * movespeed;
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

   public boolean canBeCollidedWith() {
      return true;
   }

   public float getCollisionBorderSize() {
      return 0.3F;
   }

   public boolean handleWaterMovement() {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 5) {
         for (int ss = 0; ss < 4; ss++) {
            GUNParticle bubble = new GUNParticle(
               this.slime,
               0.4F + this.rand.nextFloat() / 3.0F,
               -0.001F,
               10 + this.rand.nextInt(10),
               -1,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 19.0F,
               (float)this.rand.nextGaussian() / 22.0F + 0.05F,
               (float)this.rand.nextGaussian() / 19.0F,
               0.6F + (float)this.rand.nextGaussian() / 10.0F,
               0.85F + (float)this.rand.nextGaussian() / 10.0F,
               0.95F + (float)this.rand.nextGaussian() / 10.0F,
               true,
               this.rand.nextInt(180)
            );
            bubble.alphaTickAdding = -0.04F;
            bubble.scaleTickAdding = -0.02F;
            bubble.alphaGlowing = true;
            this.world.spawnEntity(bubble);
         }

         for (int s = 0; s < 7; s++) {
            GUNParticle bubble2 = new GUNParticle(
               this.texturbubble,
               0.08F + this.rand.nextFloat() / 20.0F,
               -0.004F,
               50 + this.rand.nextInt(20),
               200,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 16.0F,
               (float)this.rand.nextGaussian() / 16.0F - 0.07F,
               (float)this.rand.nextGaussian() / 16.0F,
               0.6F,
               0.8F + (float)this.rand.nextGaussian() / 5.0F,
               1.0F,
               true,
               this.rand.nextInt(180)
            );
            bubble2.isPushedByLiquids = false;
            this.world.spawnEntity(bubble2);
         }
      }

      if (id == 6) {
         this.scalereduct = true;
      }

      if (id == 7) {
         this.scale = 0.8F;
      }

      if (id == 8) {
         this.scaleadd = 0.4F;
      }
   }

   public void onUpdate() {
      super.onUpdate();
      if (!this.world.isRemote) {
         if (this.ticksExisted % 2 == 0) {
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox());
            if (!list.isEmpty()) {
               for (Entity base : list) {
                  if (Team.checkIsOpponent(this.thrower, base) && Weapons.canDealDamageTo(base) && !(base instanceof StingingCellEntity)) {
                     this.hitEntity(base);
                  }
               }
            }
         }

         if (this.ticksExisted > this.livetime - 20) {
            this.world.setEntityState(this, (byte)6);
         }

         if (this.ticksExisted < 2 && EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) > 0) {
            this.world.setEntityState(this, (byte)7);
         }

         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
         if (this.ticksExisted == parameters.geti("special_grow_time") && EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) > 0) {
            this.world.setEntityState(this, (byte)8);
         }

         if (this.ticksExisted > this.livetime) {
            this.setDead();
         }
      }

      if (this.ticksExisted % 5 == 0) {
         this.motionX *= 0.75;
         this.motionY *= 0.75;
         this.motionZ *= 0.75;
         this.motionX = this.motionX + this.moveX;
         this.motionY = this.motionY + this.moveY;
         this.motionZ = this.motionZ + this.moveZ;
         this.velocityChanged = true;
      }

      if (this.scalereduct) {
         this.scale -= 0.05F;
      }

      if (this.scaleadd > 0.0F) {
         this.scaleadd -= 0.1F;
         this.scale += 0.1F;
      }
   }

   public void hitEntity(Entity entity) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
      int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
      int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
      float damage = 0.0F;
      if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) > 0 && this.ticksExisted > parameters.geti("special_grow_time")) {
         damage = parameters.getEnchanted("damage_growed", might);
      } else {
         damage = parameters.getEnchanted("damage", might);
      }

      Weapons.dealDamage(
         new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.water),
         damage * this.magicPower,
         this.getThrower(),
         entity,
         true,
         parameters.getEnchanted("knockback", impulse),
         this.posX,
         this.posY,
         this.posZ
      );
      entity.hurtResistantTime = 0;
      this.world
         .playSound(
            (EntityPlayer)null,
            this.posX,
            this.posY,
            this.posZ,
            Sounds.stinging_cell_impact,
            SoundCategory.AMBIENT,
            0.8F,
            0.9F + this.rand.nextFloat() / 5.0F
         );
      this.world.setEntityState(this, (byte)5);
      this.setDead();
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit)
            && !this.world.isRemote
            && Weapons.canDealDamageTo(result.entityHit)
            && !(result.entityHit instanceof StingingCellEntity)) {
            this.hitEntity(result.entityHit);
         }
      } else if (result != null
         && result.getBlockPos() != null
         && this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
         if (this.throwed) {
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.stinging_cell_impact,
                  SoundCategory.AMBIENT,
                  0.8F,
                  0.9F + this.rand.nextFloat() / 5.0F
               );
            this.world.setEntityState(this, (byte)5);
            this.setDead();
            return;
         }

         if (result.sideHit == EnumFacing.UP || result.sideHit == EnumFacing.DOWN) {
            this.motionY = -this.motionY;
            this.moveY = -this.moveY;
         }

         if (result.sideHit == EnumFacing.NORTH || result.sideHit == EnumFacing.SOUTH) {
            this.motionZ = -this.motionZ;
            this.moveZ = -this.moveZ;
         }

         if (result.sideHit == EnumFacing.EAST || result.sideHit == EnumFacing.WEST) {
            this.motionX = -this.motionX;
            this.moveX = -this.moveX;
         }

         this.velocityChanged = true;
      }
   }

   public boolean attackEntityFrom(DamageSource source, float amount) {
      if (this.isEntityInvulnerable(source)) {
         return false;
      } else {
         this.markVelocityChanged();
         if (source.getTrueSource() != null) {
            if (amount > 3.0F) {
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.stinging_cell_impact,
                     SoundCategory.AMBIENT,
                     0.8F,
                     0.9F + this.rand.nextFloat() / 5.0F
                  );
               this.world.setEntityState(this, (byte)5);
               this.setDead();
               return true;
            } else {
               Vec3d vec3d = source.getTrueSource().getLookVec();
               if (vec3d != null) {
                  this.motionX = vec3d.x;
                  this.motionY = vec3d.y;
                  this.motionZ = vec3d.z;
                  this.moveX = (float)this.motionX * 0.1F;
                  this.moveY = (float)this.motionY * 0.1F;
                  this.moveZ = (float)this.motionZ * 0.1F;
                  this.throwed = true;
               }

               return true;
            }
         } else {
            return false;
         }
      }
   }
}
