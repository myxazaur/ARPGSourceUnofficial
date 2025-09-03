//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.potions.PotionEffects;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SnowstormEntity extends EntityThrowable {
   public final ItemStack weaponstack;
   ResourceLocation icecube = new ResourceLocation("arpg:textures/ice_cube.png");
   boolean impacted = false;

   public SnowstormEntity(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.FIREBALLSTAFF);
   }

   public SnowstormEntity(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.FIREBALLSTAFF);
   }

   public SnowstormEntity(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.FIREBALLSTAFF);
   }

   public SnowstormEntity(World world, EntityLivingBase thrower, ItemStack itemstack) {
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
         this.motionY = this.motionY + entityThrower.motionY * 0.5;
      }
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 10) {
         this.setDead();
      }
   }

   protected float getGravityVelocity() {
      return 0.004F;
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 5) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               SoundEvents.BLOCK_GLASS_HIT,
               SoundCategory.AMBIENT,
               0.3F,
               1.15F + this.rand.nextFloat() / 10.0F,
               false
            );
         EntityCubicParticle spel = new EntityCubicParticle(
            this.icecube,
            0.006F,
            0.0F,
            32 + this.rand.nextInt(14),
            99,
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
            1.0F,
            1.0F,
            1.0F,
            0.0F,
            true,
            0.0022F
         );
         spel.maxsize = 0.04F;
         this.world.spawnEntity(spel);
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.typeOfHit == Type.BLOCK && !this.world.isRemote) {
         this.world.setEntityState(this, (byte)5);
         this.setDead();
      }

      if (result.entityHit != null && result.entityHit instanceof EntityLivingBase) {
         EntityLivingBase entitylivingbase = (EntityLivingBase)result.entityHit;
         if (entitylivingbase != this.getThrower() && !this.impacted) {
            if (!this.world.isRemote) {
               PotionEffect baff = new PotionEffect(PotionEffects.FREEZING, 100, 0);
               entitylivingbase.addPotionEffect(baff);
            }

            if (!this.world.isRemote) {
               SuperKnockback.applyKnockback(entitylivingbase, 0.3F, this.posX, this.posY, this.posZ);
            }

            IAttributeInstance entityAttribute = entitylivingbase.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
            double baseValue = entityAttribute.getBaseValue();
            entityAttribute.setBaseValue(1.0);
            entitylivingbase.attackEntityFrom(
               DamageSource.causePlayerDamage((EntityPlayer)this.getThrower()),
               2.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack) / 2.0F
            );
            entitylivingbase.hurtResistantTime = 0;
            entityAttribute.setBaseValue(baseValue);
            if (entitylivingbase.getHealth() <= 0.0F) {
               DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_ICING);
            }

            this.impacted = true;
         }
      }
   }
}
