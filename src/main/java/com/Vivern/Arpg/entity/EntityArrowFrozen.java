package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.Freezing;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.GUNParticle;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityArrowFrozen extends AbstractArrow {
   public static ResourceLocation snow = new ResourceLocation("arpg:textures/snowflake5.png");

   public EntityArrowFrozen(World worldIn) {
      super(worldIn);
   }

   public EntityArrowFrozen(World worldIn, double x, double y, double z) {
      super(worldIn, x, y, z);
   }

   public EntityArrowFrozen(World worldIn, EntityLivingBase shooter) {
      super(worldIn, shooter);
   }

   @Override
   public void arrowHit(EntityLivingBase living) {
      super.arrowHit(living);
      if (this.pickupStatus == PickupStatus.ALLOWED) {
         PotionEffect lastdebaff = Weapons.mixPotion(
            living,
            PotionEffects.FREEZING,
            48.0F,
            2.0F,
            Weapons.EnumPotionMix.WITH_MAXIMUM,
            Weapons.EnumPotionMix.WITH_MAXIMUM,
            Weapons.EnumMathOperation.PLUS,
            Weapons.EnumMathOperation.PLUS,
            100,
            16
         );
         Freezing.tryPlaySound(living, lastdebaff);
         if (living.getHealth() <= 0.0F && this.rand.nextFloat() < 0.3 && living.deathTime < 1) {
            DeathEffects.applyDeathEffect(living, DeathEffects.DE_ICING);
         }
      }
   }

   @Override
   public SoundEvent getHitSound() {
      return Sounds.arrow_frozen;
   }

   protected ItemStack getArrowStack() {
      return new ItemStack(ItemsRegister.ARROWFROZEN);
   }

   @Override
   public void spawnArrowParticles(int particleCount) {
      for (int i = 0; i < particleCount; i++) {
         GUNParticle snow2 = new GUNParticle(
            snow,
            0.07F + this.rand.nextFloat() * 0.04F,
            0.008F,
            15 + this.rand.nextInt(10),
            170,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            (float)this.rand.nextGaussian() / 50.0F,
            (float)this.rand.nextGaussian() / 50.0F,
            (float)this.rand.nextGaussian() / 50.0F,
            1.0F,
            1.0F,
            1.0F,
            false,
            this.rand.nextInt(360)
         );
         snow2.scaleTickAdding = -0.0046F;
         this.world.spawnEntity(snow2);
      }
   }
}
