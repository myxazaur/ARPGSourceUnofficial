package com.vivern.arpg.entity;

import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.renders.GUNParticle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySnowflakeShuriken extends EntityThrowable {
   public final ItemStack weaponstack;
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/scatter.png");
   public float damage = 5.5F;
   public float knockback = 1.5F;

   public EntitySnowflakeShuriken(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.SNOWBALLCANNON);
   }

   public EntitySnowflakeShuriken(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.SNOWBALLCANNON);
   }

   public EntitySnowflakeShuriken(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.SNOWBALLCANNON);
   }

   public EntitySnowflakeShuriken(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.3;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.3;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.3;
      }
   }

   protected float getGravityVelocity() {
      return 0.02F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 100) {
         this.setDead();
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 8) {
         for (int i = 0; i < 6; i++) {
            GUNParticle bigsmoke = new GUNParticle(
               this.largesmoke,
               0.35F + (float)this.rand.nextGaussian() / 20.0F,
               0.0F,
               7,
               140,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 21.0F,
               (float)this.rand.nextGaussian() / 23.0F,
               (float)this.rand.nextGaussian() / 21.0F,
               0.75F,
               0.75F + this.rand.nextFloat() / 4.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            bigsmoke.alphaTickAdding = -0.12F;
            this.world.spawnEntity(bigsmoke);
         }
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
            Weapons.dealDamage(
               new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.shards),
               this.damage,
               this.getThrower(),
               result.entityHit,
               true,
               this.knockback
            );
            result.entityHit.hurtResistantTime = 0;
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.snowflake_impact,
                  SoundCategory.AMBIENT,
                  0.8F,
                  0.9F + this.rand.nextFloat() / 5.0F
               );
            this.world.setEntityState(this, (byte)8);
            this.setDead();
         }
      } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null
         && !this.world.isRemote) {
         this.world
            .playSound(
               (EntityPlayer)null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.snowflake_impact,
               SoundCategory.AMBIENT,
               0.8F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         this.world.setEntityState(this, (byte)8);
         this.setDead();
      }
   }
}
