package com.vivern.arpg.entity;

import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.potions.Freezing;
import com.vivern.arpg.potions.PotionEffects;
import com.vivern.arpg.renders.GUNParticle;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityFrostBolt extends EntityThrowable {
   public final ItemStack weaponstack;
   public float magicPower = 1.0F;
   public float damage = 8.0F;
   public float knockback = 0.7F;
   ResourceLocation resou = new ResourceLocation("arpg:textures/ice_cube.png");
   ResourceLocation resoup = new ResourceLocation("arpg:textures/snowflake.png");

   public EntityFrostBolt(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.FROSTBOLT);
   }

   public EntityFrostBolt(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.FROSTBOLT);
   }

   public EntityFrostBolt(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.FROSTBOLT);
   }

   public EntityFrostBolt(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.magicPower = power;
   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 140) {
         this.setDead();
      }

      this.world.setEntityState(this, (byte)2);
      BlockPos blockpos = this.getPosition();
      if (this.world.getBlockState(blockpos).getBlock() == Blocks.WATER) {
         this.world.setBlockToAir(blockpos);
         this.world.setBlockState(blockpos, Blocks.FROSTED_ICE.getDefaultState());
      }

      if (this.world.getBlockState(blockpos).getBlock() == Blocks.LAVA) {
         this.world.setBlockToAir(blockpos);
         this.world.setBlockState(blockpos, Blocks.COBBLESTONE.getDefaultState());
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 5) {
         this.world.playSound(this.posX, this.posY, this.posZ, Sounds.magic_b, SoundCategory.AMBIENT, 1.0F, 1.0F, false);

         for (int ss = 0; ss < 7; ss++) {
            Entity spel = new EntityCubicParticle(
               this.resou,
               0.01F,
               0.0F,
               20 + this.rand.nextInt(4),
               90 + this.rand.nextInt(9),
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 9.0F,
               (float)this.rand.nextGaussian() / 9.0F,
               (float)this.rand.nextGaussian() / 9.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextFloat(),
               this.rand.nextFloat(),
               this.rand.nextFloat(),
               0.05F,
               true,
               0.0F
            );
            this.world.spawnEntity(spel);
            Entity spelll = new GUNParticle(
               this.resoup,
               0.13F,
               0.005F,
               20,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 13.0F,
               (float)this.rand.nextGaussian() / 13.0F,
               (float)this.rand.nextGaussian() / 13.0F,
               0.8F,
               0.8F,
               0.8F + (float)this.rand.nextGaussian() / 6.0F,
               false,
               0
            );
            this.world.spawnEntity(spelll);
         }
      }

      if (id == 2) {
         Entity spel = new EntityCubicParticle(
            this.resou,
            0.016F,
            0.0F,
            12 + this.rand.nextInt(4),
            99,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            (float)this.rand.nextGaussian() / 44.0F,
            (float)this.rand.nextGaussian() / 44.0F,
            (float)this.rand.nextGaussian() / 44.0F,
            1.0F,
            1.0F,
            1.0F,
            true,
            1.0F,
            1.0F,
            1.0F,
            0.08F,
            true,
            -0.0012F
         );
         this.world.spawnEntity(spel);
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, this.weaponstack);
            Weapons.dealDamage(
               new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.frost),
               this.damage * this.magicPower,
               this.getThrower(),
               result.entityHit,
               true,
               this.knockback
            );
            result.entityHit.hurtResistantTime = 0;
            if (result.entityHit instanceof EntityLivingBase) {
               EntityLivingBase entitylivingbase = (EntityLivingBase)result.entityHit;
               int potionValue1 = parameters.getEnchantedi("potion_time_add", witchery);
               int potionValue2 = parameters.getEnchantedi("potion_power_add", witchery);
               PotionEffect lastdebaff = Weapons.mixPotion(
                  entitylivingbase,
                  PotionEffects.FREEZING,
                  (float)Math.round(potionValue1 * this.magicPower),
                  (float)potionValue2,
                  Weapons.EnumPotionMix.GREATEST,
                  Weapons.EnumPotionMix.GREATEST,
                  Weapons.EnumMathOperation.NONE,
                  Weapons.EnumMathOperation.NONE,
                  0,
                  0
               );
               Freezing.tryPlaySound(entitylivingbase, lastdebaff);
               if (entitylivingbase.getHealth() <= 0.0F) {
                  DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_ICING);
               }
            }

            this.world.setEntityState(this, (byte)5);
            this.setDead();
         }
      } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null
         && !this.world.isRemote) {
         this.world.setEntityState(this, (byte)5);
         this.setDead();
      }
   }
}
