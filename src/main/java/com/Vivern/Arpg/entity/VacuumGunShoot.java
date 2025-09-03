//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

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
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class VacuumGunShoot extends StandardBullet {
   public final ItemStack weaponstack;
   static ResourceLocation explode = new ResourceLocation("arpg:textures/void_explode.png");
   static ResourceLocation cloud = new ResourceLocation("arpg:textures/largecloud.png");
   static ResourceLocation stick = new ResourceLocation("arpg:textures/stick_particle.png");
   static ResourceLocation largesmoke = new ResourceLocation("arpg:textures/purple_smoke.png");
   public int ticksVacuum = 0;
   public boolean vacuum = false;
   public boolean exploded = false;
   public int livetime = 35;
   public boolean special = false;

   public VacuumGunShoot(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.VACUUMGUN);
   }

   public VacuumGunShoot(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.VACUUMGUN);
   }

   public VacuumGunShoot(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.VACUUMGUN);
   }

   public VacuumGunShoot(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
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
      if (!this.world.isRemote) {
         if (this.ticksExisted > this.livetime) {
            this.vacuum = true;
         }

         if (this.vacuum) {
            if (this.ticksVacuum == 0) {
               this.world.setEntityState(this, (byte)9);
            }

            if (this.ticksVacuum % 5 == 0) {
               this.world.setEntityState(this, (byte)8);
            }

            this.expl2();
            this.ticksVacuum++;
            if (this.ticksVacuum > 20) {
               this.expl();
            }
         } else {
            this.world.setEntityState(this, (byte)5);
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 15) {
         for (int ss = 0; ss < 10; ss++) {
            GUNParticle bigsmoke = new GUNParticle(
               largesmoke,
               0.1F + (float)this.rand.nextGaussian() / 20.0F,
               0.0F,
               8,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 20.0F,
               (float)this.rand.nextGaussian() / 20.0F,
               (float)this.rand.nextGaussian() / 20.0F,
               0.7F,
               0.6F + (float)this.rand.nextGaussian() / 5.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            bigsmoke.alphaTickAdding = -0.11F;
            bigsmoke.alphaGlowing = true;
            bigsmoke.scaleTickAdding = 0.1F;
            this.world.spawnEntity(bigsmoke);
         }
      }

      if (id == 8) {
         float red = 0.7F;
         float green = 0.5F;
         float blue = 1.0F;
         int countOfParticles = 5;
         float R = 0.088F + (float)this.rand.nextGaussian() / 30.0F;

         for (int i = 0; i < countOfParticles; i++) {
            float rand1 = this.rand.nextFloat() * 2.0F - 1.0F;
            float rand2 = this.rand.nextFloat() * 2.0F - 1.0F;
            float X = rand1 * R;
            float new_R = (float)Math.sqrt(R * R - X * X);
            float Y = rand2 * new_R;
            float Z = (float)Math.sqrt(new_R * new_R - Y * Y);
            if (this.rand.nextBoolean()) {
               Z *= -1.0F;
            }

            GUNParticle particle = new GUNParticle(
               stick,
               0.07F + (float)this.rand.nextGaussian() / 30.0F,
               0.0F,
               20,
               200,
               this.world,
               this.posX + X * 15.0F,
               this.posY + Y * 15.0F,
               this.posZ + Z * 15.0F,
               -X,
               -Y,
               -Z,
               red - (float)this.rand.nextGaussian() / 10.0F,
               green - (float)this.rand.nextGaussian() / 10.0F,
               blue - (float)this.rand.nextGaussian() / 10.0F,
               false,
               this.rand.nextInt(360)
            );
            particle.scaleTickAdding = -0.001F;
            this.world.spawnEntity(particle);
         }
      }

      if (id == 9) {
         this.world
            .playSound(this.posX, this.posY, this.posZ, Sounds.vacuum_gun_impact, SoundCategory.AMBIENT, 1.8F, 1.0F, false);
         GUNParticle bigsmoke1 = new GUNParticle(
            explode,
            2.0F,
            0.0F,
            20,
            240,
            this.world,
            this.lastTickPosX,
            this.lastTickPosY,
            this.lastTickPosZ,
            0.0F,
            0.0F,
            0.0F,
            0.9F + this.rand.nextFloat() / 10.0F,
            0.9F + this.rand.nextFloat() / 10.0F,
            1.0F,
            true,
            this.rand.nextInt(360)
         );
         bigsmoke1.alphaGlowing = true;
         bigsmoke1.scaleTickAdding = -0.1F;
         bigsmoke1.randomDeath = false;
         this.world.spawnEntity(bigsmoke1);
      }

      if (id == 5) {
         GUNParticle fire2 = new GUNParticle(
            cloud,
            0.17F,
            0.0F,
            10,
            240,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            0.0F,
            0.0F,
            0.0F,
            0.7F + (float)this.rand.nextGaussian() / 7.0F,
            0.5F + (float)this.rand.nextGaussian() / 7.0F,
            1.0F,
            true,
            this.rand.nextInt(360)
         );
         fire2.alphaTickAdding = -0.09F;
         fire2.scaleTickAdding = -0.016F;
         fire2.alphaGlowing = true;
         this.world.spawnEntity(fire2);
      }
   }

   @Override
   public void onImpact(RayTraceResult result) {
      if (!this.world.isRemote) {
         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit)) {
               this.vacuum = true;
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            this.vacuum = true;
         }
      }
   }

   public void expl2() {
      boolean spec = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) > 0;
      this.motionX = 0.0;
      this.motionY = 0.0;
      this.motionZ = 0.0;
      this.velocityChanged = true;
      this.setNoGravity(true);
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
      double damageRadius = parameters.getEnchanted("attract_damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      if (spec) {
         for (Entity entity : GetMOP.getEntitiesInAABBof(this.world, this, damageRadius, this)) {
            if (Team.checkIsOpponent(this.getThrower(), entity)) {
               int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
               SuperKnockback.applyKnockback(
                  entity, parameters.getEnchanted("attract_knockback", impulse), this.posX, this.posY, this.posZ
               );
            }
         }
      } else {
         List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
         if (!list.isEmpty()) {
            int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);

            for (EntityLivingBase entitylivingbase : list) {
               SuperKnockback.applyKnockback(
                  entitylivingbase, parameters.getEnchanted("attract_knockback", impulse), this.posX, this.posY, this.posZ
               );
            }
         }
      }

      this.world.setEntityState(this, (byte)8);
   }

   public void expl() {
      this.motionX = 0.0;
      this.motionY = 0.0;
      this.motionZ = 0.0;
      this.velocityChanged = true;
      this.setNoGravity(true);
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
      double damageRadius = parameters.getEnchanted("damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
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
                  new WeaponDamage(this.weaponstack, this.getThrower(), this, false, false, this, WeaponDamage.portal),
                  parameters.getEnchanted("damage", might),
                  this.getThrower(),
                  entity,
                  true,
                  parameters.getEnchanted("knockback", impulse),
                  this.posX,
                  this.posY,
                  this.posZ
               );
               entity.hurtResistantTime = 0;
               DeathEffects.applyDeathEffect(entity, DeathEffects.DE_DISMEMBER, 0.45F);
            }
         }
      }

      this.world.setEntityState(this, (byte)15);
      this.setDead();
   }
}
