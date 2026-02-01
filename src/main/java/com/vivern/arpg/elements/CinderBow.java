package com.vivern.arpg.elements;

import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.EntityInfluence;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.Mana;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.WeaponParameters;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class CinderBow extends AbstractBow {
   public static EntityInfluence cinderArrowInfluence = new EntityInfluence(true) {
      @Override
      public void onUpdate(Entity entity) {
      }

      @Override
      public void onImpact(Entity entity, RayTraceResult result) {
         if (entity.world.isRemote && result != null && entity.ticksExisted < 20 && GetMOP.getSpeed(entity) > 2.0) {
            for (int i = 0; i < 8; i++) {
               entity.world
                  .spawnParticle(EnumParticleTypes.LAVA, entity.posX, entity.posY, entity.posZ, 0.0, 0.0, 0.0, new int[0]);
            }
         }
      }

      @Override
      public void clientTick(Entity entity) {
         if (entity.world.isRemote && entity.ticksExisted < 20 && GetMOP.getSpeed(entity) > 2.0) {
            entity.world
               .spawnParticle(EnumParticleTypes.LAVA, entity.posX, entity.posY, entity.posZ, 0.0, 0.0, 0.0, new int[0]);
         }
      }
   };

   public CinderBow() {
      super("cinder_bow", 1000, 3.2F, 1.4F, 55, 3, 2.0F, 0.5F);
   }

   @Override
   public SoundEvent getShootSound() {
      return Sounds.fire_b;
   }

   @Override
   public void setDamageToArrow(
      EntityArrow entityarrow, ItemStack ammo, World world, EntityPlayer player, ItemStack bow, int pulling, float arrowvelocity, boolean isArrowUnlimit
   ) {
      int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, bow);
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
      if (pulling >= 55) {
         entityarrow.setDamage(entityarrow.getDamage() + parameters.getEnchanted("fullcharged_damage", might));
      } else {
         entityarrow.setDamage(entityarrow.getDamage() + parameters.getEnchanted("damage", might));
      }
   }

   @Override
   public float getArrowVelocity(int charge, ItemStack bow, EntityPlayer player) {
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, bow);
      float mult = 1.0F - 0.2F * rapidity;
      int ct = Math.round(25.0F * mult);
      float f = (float)charge / ct;
      f = (f * f + f * 2.0F) / 3.0F;
      if (f > 1.0F) {
         f = 1.0F;
      }

      return f;
   }

   @Override
   public boolean inUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected, boolean[] removePull) {
      if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0) {
         EntityPlayer player = (EntityPlayer)entityIn;
         if (player.getHeldItemMainhand() == itemstack && Keys.isKeyPressed(player, Keys.PRIMARYATTACK) && Keys.isKeyPressed(player, Keys.SECONDARYATTACK)) {
            int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
            float manacost = WeaponParameters.getWeaponParameters(this).getEnchanted("manacost", sor);
            if (Mana.getMana(player) > manacost) {
               int pulling = NBTHelper.GetNBTint(itemstack, "pulling");
               int coolTime = this.getCooldownTime(itemstack);
               if (pulling < coolTime) {
                  NBTHelper.SetNBTint(itemstack, Math.min(NBTHelper.GetNBTint(itemstack, "pulling") + 3, coolTime), "pulling");
                  if (!player.capabilities.isCreativeMode) {
                     Mana.changeMana(player, -manacost);
                     Mana.setManaSpeed(player, 0.001F);
                  }
               }
            }
         }
      }

      return true;
   }

   @Override
   public void customizeArrow(
      EntityArrow arrow, ItemStack ammo, World world, EntityPlayer player, ItemStack bow, int pulling, float arrowvelocity, boolean isArrowUnlimit
   ) {
      if (pulling >= 55) {
         EntityInfluence.addEntityInfluence(arrow, cinderArrowInfluence, 64.0);
         if (!arrow.isBurning()) {
            arrow.setFire(15 + EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, bow) * 3);
         }

         int k = GetMOP.floatToIntWithChance(
            WeaponParameters.getWeaponParameters(this).getEnchanted("fullcharged_knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, bow)),
            itemRand
         );
         arrow.setKnockbackStrength(k);
      } else if (!arrow.isBurning()) {
         arrow.setFire(10 + EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, bow) * 2);
      }
   }
}
