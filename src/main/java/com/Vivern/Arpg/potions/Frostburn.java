package com.Vivern.Arpg.potions;

import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.PotionBurningEffects;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Frostburn extends AdvancedPotion {
   public static String fire0 = "arpg:fires/burning_frost_layer_0";
   public static String fire1 = "arpg:fires/burning_frost_layer_1";

   public Frostburn(int index) {
      super(true, 8186111, index, true);
      this.setRegistryName("arpg:frostburn");
      this.setPotionName("FrostBurn");
      this.setIconIndex(27, 1);
      this.shouldRender = true;
   }

   public void performEffect(EntityLivingBase entityOnEffect, int amplifier) {
      if (!entityOnEffect.world.isRemote && entityOnEffect.ticksExisted % 20 == 0) {
         entityOnEffect.attackEntityFrom(DamageSource.MAGIC, 2 + amplifier);
         if (this.isEntityInsideWater(entityOnEffect.world, entityOnEffect)) {
            freezeWhileFrostburning(entityOnEffect);
         }

         if (entityOnEffect.isBurning()) {
            entityOnEffect.extinguish();
            entityOnEffect.removePotionEffect(this);
         }

         if (rand.nextFloat() < 0.25 && entityOnEffect.world.isRainingAt(entityOnEffect.getPosition())) {
            freezeWhileFrostburning(entityOnEffect);
         }
      }
   }

   public static void freezeWhileFrostburning(EntityLivingBase entityOnEffect) {
      if (entityOnEffect.isPotionActive(PotionEffects.FREEZING)) {
         PotionEffect lastdebaff = Weapons.mixPotion(
            entityOnEffect,
            PotionEffects.FREEZING,
            30.0F,
            rand.nextFloat() < 0.5 ? 0.0F : 1.0F,
            Weapons.EnumPotionMix.WITH_MAXIMUM,
            Weapons.EnumPotionMix.ABSOLUTE,
            Weapons.EnumMathOperation.PLUS,
            Weapons.EnumMathOperation.PLUS,
            100,
            0
         );
         Freezing.tryPlaySound(entityOnEffect, lastdebaff);
      } else {
         PotionEffect effect = entityOnEffect.getActivePotionEffect(PotionEffects.FROSTBURN);
         if (effect != null) {
            PotionEffect lastdebaff = Weapons.mixPotion(
               entityOnEffect,
               PotionEffects.FREEZING,
               30.0F,
               (float)(effect.getAmplifier() + 1),
               Weapons.EnumPotionMix.WITH_MAXIMUM,
               Weapons.EnumPotionMix.ABSOLUTE,
               Weapons.EnumMathOperation.PLUS,
               Weapons.EnumMathOperation.PLUS,
               100,
               0
            );
            Freezing.tryPlaySound(entityOnEffect, lastdebaff);
         }
      }
   }

   public boolean isEntityInsideWater(World world, Entity entity) {
      AxisAlignedBB bb = entity.getEntityBoundingBox();
      int j2 = MathHelper.floor(bb.minX);
      int k2 = MathHelper.ceil(bb.maxX);
      int l2 = MathHelper.floor(bb.minY);
      int i3 = MathHelper.ceil(bb.maxY);
      int j3 = MathHelper.floor(bb.minZ);
      int k3 = MathHelper.ceil(bb.maxZ);
      PooledMutableBlockPos blockpos$pooledmutableblockpos = PooledMutableBlockPos.retain();

      for (int l3 = j2; l3 < k2; l3++) {
         for (int i4 = l2; i4 < i3; i4++) {
            for (int j4 = j3; j4 < k3; j4++) {
               IBlockState iblockstate1 = world.getBlockState(blockpos$pooledmutableblockpos.setPos(l3, i4, j4));
               if (Weapons.isBlockConsideredWater(world, iblockstate1.getBlock())) {
                  blockpos$pooledmutableblockpos.release();
                  return true;
               }
            }
         }
      }

      blockpos$pooledmutableblockpos.release();
      return false;
   }

   public boolean isReady(int duration, int amplifier) {
      return true;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void render(EntityLivingBase entityOnEffect, double x, double y, double z, float yaw, float partialTicks, PotionEffect effect, Render entityRenderer) {
      PotionBurningEffects.renderEntityOnFire(entityOnEffect, x, y + 0.15, z, partialTicks, fire0, fire1, 180);
   }

   @Override
   public void renderFirstperson(EntityPlayer player, PotionEffect effect, RenderHandEvent event) {
      PotionBurningEffects.renderFireInFirstPerson(fire1, 0.6F, -0.3F + 0.1F * Math.min(effect.getAmplifier(), 2));
   }
}
