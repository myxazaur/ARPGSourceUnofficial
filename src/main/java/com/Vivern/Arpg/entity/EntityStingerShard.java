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
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityStingerShard extends EntityThrowable {
   public final ItemStack weaponstack;
   ResourceLocation smoketex = new ResourceLocation("arpg:textures/smoke.png");
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largesmoke.png");
   ResourceLocation sparkle = new ResourceLocation("arpg:textures/sparkle.png");
   final double ssx;
   final double ssy;
   final double ssz;

   public EntityStingerShard(World world) {
      super(world);
      this.ssx = 0.02F;
      this.ssy = 0.02F;
      this.ssz = 0.02F;
      this.weaponstack = new ItemStack(ItemsRegister.STINGER);
   }

   public EntityStingerShard(World world, EntityLivingBase thrower, double ssx, double ssy, double ssz, StingerBoltEntity bolt) {
      super(world, bolt.posX, bolt.posY, bolt.posZ);
      this.ssx = ssx;
      this.ssy = ssy;
      this.ssz = ssz;
      this.thrower = thrower;
      this.weaponstack = bolt.weaponstack;
   }

   public EntityStingerShard(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.ssx = 0.02F;
      this.ssy = 0.02F;
      this.ssz = 0.02F;
      this.weaponstack = new ItemStack(ItemsRegister.STINGER);
   }

   public EntityStingerShard(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.ssx = 0.02F;
      this.ssy = 0.02F;
      this.ssz = 0.02F;
      this.weaponstack = new ItemStack(ItemsRegister.STINGER);
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 40) {
         this.setDead();
      }

      if (this.ticksExisted == 1) {
         this.setVelocity(this.ssx, this.ssy, this.ssz);
      }

      this.world.setEntityState(this, (byte)7);
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 6) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.explode_stinger,
               SoundCategory.AMBIENT,
               0.9F,
               0.95F + this.rand.nextFloat() / 10.0F,
               false
            );

         for (int ss = 0; ss < 10; ss++) {
            Entity bigsmoke = new GUNParticle(
               this.largesmoke,
               0.4F,
               0.0F,
               12,
               80,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 23.0F,
               (float)this.rand.nextGaussian() / 27.0F,
               (float)this.rand.nextGaussian() / 23.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               0
            );
            this.world.spawnEntity(bigsmoke);
         }

         for (int ss = 0; ss < 8; ss++) {
            Entity fire = new GUNParticle(
               this.sparkle,
               0.07F + (float)this.rand.nextGaussian() / 30.0F,
               0.01F,
               10,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 14.0F,
               (float)this.rand.nextGaussian() / 14.0F + 0.15F,
               (float)this.rand.nextGaussian() / 14.0F,
               1.0F,
               0.8F + (float)this.rand.nextGaussian() / 5.0F,
               1.0F,
               true,
               0
            );
            this.world.spawnEntity(fire);
         }
      }

      if (id == 7) {
         Entity smoke = new GUNParticle(
            this.smoketex,
            0.16F,
            0.0F,
            7,
            80,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            0.0F,
            0.0F,
            0.0F,
            1.0F,
            1.0F,
            1.0F,
            true,
            0
         );
         this.world.spawnEntity(smoke);
      }
   }

   protected float getGravityVelocity() {
      return 0.05F;
   }

   protected void onImpact(RayTraceResult result) {
      if (!this.world.isRemote) {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
         double damageRadius = parameters.getEnchanted("damage_radius_shard", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               if (Team.checkIsOpponent(this.thrower, entity)) {
                  int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
                  int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
                  Weapons.dealDamage(
                     new WeaponDamage(this.weaponstack, this.getThrower(), this, true, false, this, WeaponDamage.explode),
                     parameters.getEnchanted("damage_shard", might),
                     this.getThrower(),
                     entity,
                     true,
                     parameters.getEnchanted("knockback_shard", impulse),
                     this.posX,
                     this.posY - 0.1,
                     this.posZ
                  );
                  entity.hurtResistantTime = 0;
               }
            }
         }

         this.world.setEntityState(this, (byte)6);
         this.setDead();
      }
   }
}
