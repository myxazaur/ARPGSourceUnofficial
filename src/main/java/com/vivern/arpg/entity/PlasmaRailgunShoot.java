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
import java.util.ArrayList;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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

public class PlasmaRailgunShoot extends StandardBullet implements IEntitySynchronize {
   static ResourceLocation explode = new ResourceLocation("arpg:textures/blueexplode5.png");
   static ResourceLocation cloud = new ResourceLocation("arpg:textures/largecloud.png");
   static ResourceLocation blueexplode = new ResourceLocation("arpg:textures/blueexplode.png");
   public final ItemStack weaponstack;
   public Vec3d pos1 = this.getPositionVector();
   public Vec3d pos2 = this.getPositionVector();
   public Vec3d pos3 = this.getPositionVector();
   public Vec3d pos4 = this.getPositionVector();
   public Vec3d pos5 = this.getPositionVector();
   public int pierces = 0;
   public List<Entity> attacked = new ArrayList<>();
   public int livetime = 14;
   ResourceLocation texture = new ResourceLocation("arpg:textures/generic_beam.png");
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largesmoke.png");

   public PlasmaRailgunShoot(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.PLASMARAILGUN);
   }

   public PlasmaRailgunShoot(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.PLASMARAILGUN);
   }

   public PlasmaRailgunShoot(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.PLASMARAILGUN);
   }

   public PlasmaRailgunShoot(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
   }

   @Override
   protected void entityInit() {
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
      for (int ss = 0; ss < 5; ss++) {
         GUNParticle bigsmoke = new GUNParticle(
            cloud,
            0.2F + this.rand.nextFloat() / 8.0F,
            0.0F,
            30,
            220,
            this.world,
            x,
            y,
            z,
            (float)this.rand.nextGaussian() / 14.0F,
            (float)this.rand.nextGaussian() / 14.0F,
            (float)this.rand.nextGaussian() / 14.0F,
            0.75F + (float)this.rand.nextGaussian() / 10.0F,
            0.45F,
            1.0F,
            true,
            this.rand.nextInt(360)
         );
         bigsmoke.alphaTickAdding = -0.025F;
         bigsmoke.alphaGlowing = true;
         this.world.spawnEntity(bigsmoke);
      }

      GUNParticle bigsmoke1 = new GUNParticle(
         explode, 0.1F, 0.0F, 11, 240, this.world, x, y, z, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, true, this.rand.nextInt(360)
      );
      bigsmoke1.alphaTickAdding = -0.095F;
      bigsmoke1.alphaGlowing = true;
      bigsmoke1.scaleTickAdding = 0.1F;
      this.world.spawnEntity(bigsmoke1);
   }

   @Override
   public void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.attacked.contains(result.entityHit) && !this.world.isRemote) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
            int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
            boolean deal = Weapons.dealDamage(
               new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.plasma),
               parameters.getEnchanted("damage", might) + this.pierces * parameters.getEnchanted("damage_per_pierces", might),
               this.getThrower(),
               result.entityHit,
               true,
               parameters.getEnchanted("knockback", impulse)
            );
            result.entityHit.hurtResistantTime = 0;
            if (result.entityHit instanceof EntityLivingBase) {
               EntityLivingBase entitylivingbase = (EntityLivingBase)result.entityHit;
               PotionEffect baff = parameters.getPotion(
                  "shock", PotionEffects.SHOCK, EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, this.weaponstack)
               );
               entitylivingbase.addPotionEffect(baff);
               if (entitylivingbase.getHealth() <= 0.0F && this.rand.nextFloat() > 0.65) {
                  DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_ELECTRIC);
               }
            }

            if (deal) {
               this.attacked.add(result.entityHit);
               this.pierces++;
               if (this.pierces > parameters.getEnchanted("max_pierces", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack))) {
                  this.setDead();
               }
            }

            IEntitySynchronize.sendSynchronize(this, 64.0, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.plasma_railgun_impact,
                  SoundCategory.AMBIENT,
                  0.8F,
                  0.9F + this.rand.nextFloat() / 5.0F
               );
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
               Sounds.plasma_railgun_impact,
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

   @SideOnly(Side.CLIENT)
   public void onEntityUpdate() {
      Vec3d subtraction = this.pos1.subtract(this.pos2);
      if (this.world.isRemote) {
         if (this.ticksExisted > 2 && this.pos1.lengthSquared() > 1.0E-6 && this.pos2.lengthSquared() > 1.0E-6) {
            BetweenParticle laser = new BetweenParticle(
               this.world, this.texture, 0.05F, 240, 0.92F, 0.8F, 1.0F, 0.09F, this.pos1.distanceTo(this.pos2), 10, 0.3F, 2.0F, this.pos1, this.pos2
            );
            laser.setPosition(this.posX, this.posY, this.posZ);
            laser.alphaGlowing = true;
            this.world.spawnEntity(laser);
         }

         if (this.ticksExisted == 2) {
            GUNParticle bigsmoke1 = new GUNParticle(
               blueexplode,
               0.04F,
               0.0F,
               9,
               240,
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
               this.rand.nextInt(360)
            );
            bigsmoke1.alphaTickAdding = -0.11F;
            bigsmoke1.alphaGlowing = true;
            bigsmoke1.scaleTickAdding = 0.1F;
            this.world.spawnEntity(bigsmoke1);
         }
      }
   }
}
