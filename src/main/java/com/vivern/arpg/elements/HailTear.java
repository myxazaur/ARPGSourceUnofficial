package com.vivern.arpg.elements;

import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.mobs.HostileProjectiles;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HailTear extends ItemWeapon {
   public HailTear() {
      this.setRegistryName("hail_tear");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("hail_tear");
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

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            boolean hascooldown2 = player.getCooldownTracker().hasCooldown(ItemsRegister.EXP);
            if (click && player.getHeldItemMainhand() == itemstack && !hascooldown) {
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.magic_k,
                  SoundCategory.AMBIENT,
                  0.9F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
               player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
               player.addStat(StatList.getObjectUseStats(this));
               Weapons.setPlayerAnimationOnServer(player, 1, EnumHand.MAIN_HAND);
               if (!player.capabilities.isCreativeMode) {
                  itemstack.shrink(1);
               }

               HostileProjectiles.Hailblast projectile = new HostileProjectiles.Hailblast(world, player);
               projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.7F, 3.5F);
               projectile.damage = 10.0F;
               projectile.spawnFragmentChance = 0.1F;
               world.spawnEntity(projectile);
            }

            if (click2 && player.getHeldItemOffhand() == itemstack && !hascooldown2) {
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.magic_k,
                  SoundCategory.AMBIENT,
                  0.9F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
               player.getCooldownTracker().setCooldown(ItemsRegister.EXP, this.getCooldownTime(itemstack));
               player.addStat(StatList.getObjectUseStats(this));
               Weapons.setPlayerAnimationOnServer(player, 1, EnumHand.OFF_HAND);
               if (!player.capabilities.isCreativeMode) {
                  itemstack.shrink(1);
               }

               HostileProjectiles.Hailblast projectile = new HostileProjectiles.Hailblast(world, player);
               projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.7F, 3.5F);
               projectile.damage = 10.0F;
               projectile.spawnFragmentChance = 0.1F;
               world.spawnEntity(projectile);
            }
         }
      }
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.ONE_HANDED;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      return 10;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean hasSpecialDescription() {
      return false;
   }
}
