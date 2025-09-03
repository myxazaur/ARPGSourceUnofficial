//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.elements.CryonedBlock;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.ParticleFastSummon;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.Freezing;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CryoDestroyerSpray extends EntityThrowable implements IEntitySynchronize {
   public final ItemStack weaponstack;
   public static ResourceLocation frozen_circle = new ResourceLocation("arpg:textures/frozen_circle.png");
   public static ResourceLocation largecloud = new ResourceLocation("arpg:textures/largecloud.png");

   public CryoDestroyerSpray(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.CRYODESTROYER);
   }

   public CryoDestroyerSpray(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.CRYODESTROYER);
   }

   public CryoDestroyerSpray(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.CRYODESTROYER);
   }

   public CryoDestroyerSpray(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.5;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.5;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.2;
      }
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 7) {
         this.setDead();
      }

      if (!this.world.isRemote) {
         BlockPos thispos = this.getPosition();
         if (Weapons.freezeBlock(this.world, thispos)) {
            this.setDead();
         }
      }
   }

   protected float getGravityVelocity() {
      return 0.01F;
   }

   @Override
   public void onClient(double... args) {
      if (args.length == 4) {
         Vec3d pos = new Vec3d(args[0], args[1], args[2]);
         GUNParticle part = ParticleFastSummon.round2(
            this.world, frozen_circle, pos, 0.1F, 1.1F, 8 + this.rand.nextInt(4), 170, 1.0F, 1.0F, 1.0F, 0
         );
         part.snapToFace(EnumFacing.byIndex((int)args[3]));

         for (int ss = 0; ss < 2; ss++) {
            GUNParticle bigsmoke = new GUNParticle(
               largecloud,
               0.4F + (float)this.rand.nextGaussian() / 8.0F,
               -0.003F,
               15 + this.rand.nextInt(10),
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 20.0F,
               (float)this.rand.nextGaussian() / 20.0F,
               (float)this.rand.nextGaussian() / 20.0F,
               0.75F + this.rand.nextFloat() / 5.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            bigsmoke.alphaGlowing = true;
            bigsmoke.alphaTickAdding = -0.04F;
            this.world.spawnEntity(bigsmoke);
         }
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (!this.world.isRemote) {
         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit)) {
               Weapons.dealDamage(
                  new WeaponDamage(this.weaponstack, this.thrower, this, false, false, this, WeaponDamage.frost),
                  3.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack) * 0.5F,
                  this.thrower,
                  result.entityHit,
                  true,
                  0.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack) * 0.2F,
                  this.posX,
                  this.posY,
                  this.posZ
               );
               result.entityHit.hurtResistantTime = 0;
               if (result.entityHit instanceof EntityLivingBase) {
                  EntityLivingBase entitylivingbase = (EntityLivingBase)result.entityHit;
                  PotionEffect lastdebaff = Weapons.mixPotion(
                     entitylivingbase,
                     PotionEffects.FREEZING,
                     20.0F,
                     1.0F,
                     Weapons.EnumPotionMix.WITH_MAXIMUM,
                     Weapons.EnumPotionMix.WITH_MAXIMUM,
                     Weapons.EnumMathOperation.PLUS,
                     Weapons.EnumMathOperation.PLUS,
                     80,
                     13
                  );
                  Freezing.tryPlaySound(entitylivingbase, lastdebaff);
                  if (entitylivingbase.getHealth() <= 0.0F && this.rand.nextFloat() < 0.8 && entitylivingbase.deathTime < 1) {
                     DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_ICING);
                  }
               }

               this.setDead();
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            if (!Weapons.freezeBlock(this.world, result.getBlockPos())) {
               List<CryonedBlock> list = this.world.getEntitiesWithinAABB(CryonedBlock.class, new AxisAlignedBB(result.getBlockPos()).shrink(0.1));
               if (list.isEmpty()) {
                  CryonedBlock cryonedBlock = new CryonedBlock(this.world, result.getBlockPos());
                  cryonedBlock.timeLast = 100;
                  this.world.spawnEntity(cryonedBlock);
               } else {
                  CryonedBlock cryonedBlock = list.get(0);
                  cryonedBlock.timeLast = MathHelper.clamp(cryonedBlock.timeLast + 40, 100, 300);
               }
            }

            if (result.hitVec != null && result.sideHit != null) {
               result = GetMOP.normalizeRayTraceResult(result, 0.06);
               IEntitySynchronize.sendSynchronize(
                  this,
                  32.0,
                  result.hitVec.x,
                  result.hitVec.y,
                  result.hitVec.z,
                  result.sideHit.getIndex()
               );
            }

            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.cryo_gun_impact,
                  SoundCategory.AMBIENT,
                  0.7F,
                  1.1F + this.rand.nextFloat() / 5.0F,
                  false
               );
            this.setDead();
         }
      }
   }
}
