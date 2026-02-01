package com.vivern.arpg.elements;

import com.vivern.arpg.entity.EntitySnowflakeShuriken;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SnowflakeShuriken extends ItemWeapon {
   public SnowflakeShuriken() {
      this.setRegistryName("snowflake_shuriken");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("snowflake_shuriken");
      this.setMaxStackSize(64);
   }

   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      return true;
   }

   @Override
   public boolean canAttackMelee(ItemStack itemstack, EntityPlayer player) {
      return false;
   }

   public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
      return false;
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return slotChanged;
   }

   public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(stack, entityIn);
         if (IWeapon.canShoot(stack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            EnumHand hand = null;
            if (Keys.isKeyPressed(player, Keys.PRIMARYATTACK) && player.getHeldItemMainhand() == stack && !player.getCooldownTracker().hasCooldown(this)) {
               hand = EnumHand.MAIN_HAND;
            } else if (Keys.isKeyPressed(player, Keys.SECONDARYATTACK)
               && player.getHeldItemOffhand() == stack
               && !player.getCooldownTracker().hasCooldown(ItemsRegister.EXP)) {
               hand = EnumHand.OFF_HAND;
            }

            if (hand != null) {
               WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
               Weapons.setPlayerAnimationOnServer(player, 1, hand);
               player.getCooldownTracker().setCooldown((Item)(hand == EnumHand.MAIN_HAND ? this : ItemsRegister.EXP), this.getCooldownTime(stack));
               player.addStat(StatList.getObjectUseStats(this));
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.swosh_a,
                  SoundCategory.AMBIENT,
                  0.8F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
               EntitySnowflakeShuriken projectile = new EntitySnowflakeShuriken(world, player, stack);
               projectile.damage = parameters.get("damage");
               projectile.knockback = parameters.get("knockback");
               Weapons.shoot(
                  projectile,
                  hand,
                  player,
                  player.rotationPitch,
                  player.rotationYaw,
                  0.3F,
                  parameters.get("velocity"),
                  parameters.get("inaccuracy"),
                  -0.15F,
                  0.5F,
                  0.1F
               );
               world.spawnEntity(projectile);
               if (!player.capabilities.isCreativeMode) {
                  stack.shrink(1);
               }
            }
         }
      }
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.ONE_HANDED;
   }

   @Override
   public boolean hasSpecialDescription() {
      return false;
   }
}
