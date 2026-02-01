package com.vivern.arpg.elements;

import com.vivern.arpg.entity.PlasmaAcceleratorShoot;
import com.vivern.arpg.main.Booom;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlasmaAccelerator extends ItemWeapon implements IEnergyItem {
   public PlasmaAccelerator() {
      this.setRegistryName("plasma_accelerator");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("plasma_accelerator");
      this.setMaxDamage(1000);
      this.setMaxStackSize(1);
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
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int reuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            int cooldowntime = this.getCooldownTime(itemstack);
            int special = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack);
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            int RFtoShoot = parameters.getEnchantedi("rf_to_shoot", reuse);
            int shoots = this.getEnergyStored(itemstack) / RFtoShoot;
            if (player.getHeldItemMainhand() == itemstack) {
               if (click) {
                  NBTHelper.GiveNBTint(itemstack, 0, "charge");
               }

               int charge = NBTHelper.GetNBTint(itemstack, "charge");
               if (player.ticksExisted % 15 == 0 && !hascooldown) {
                  if (charge > -25 && charge < -10) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.plasma_accelerator_loopfirst,
                        SoundCategory.AMBIENT,
                        0.8F,
                        1.0F
                     );
                     Weapons.setPlayerAnimationOnServer(player, 11, EnumHand.MAIN_HAND);
                  }

                  if (charge <= -25) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.plasma_accelerator_loop,
                        SoundCategory.AMBIENT,
                        0.8F,
                        1.0F
                     );
                     Weapons.setPlayerAnimationOnServer(player, 11, EnumHand.MAIN_HAND);
                  }
               }

               int maxChargedShoots = parameters.getEnchantedi("shots", special);
               if (charge <= 0 && click && charge > -(cooldowntime * Math.min(shoots - 1, maxChargedShoots) + 1)) {
                  if (!hascooldown) {
                     if (charge == 0) {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.plasma_accelerator_start,
                           SoundCategory.AMBIENT,
                           0.8F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                        Weapons.setPlayerAnimationOnServer(player, 11, EnumHand.MAIN_HAND);
                     }

                     if (charge == -cooldowntime || charge == -cooldowntime * 2 || special > 0 && charge == -cooldowntime * 3) {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.plasma_accelerator_charge,
                           SoundCategory.AMBIENT,
                           0.8F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                     }

                     NBTHelper.AddNBTint(itemstack, -1, "charge");
                  }
               } else if (charge < 0 && !click) {
                  NBTHelper.SetNBTint(itemstack, -NBTHelper.GetNBTint(itemstack, "charge"), "charge");
               } else if (this.getEnergyStored(itemstack) >= RFtoShoot && charge > 0) {
                  if (!hascooldown) {
                     if (charge < cooldowntime) {
                        NBTHelper.SetNBTint(itemstack, 0, "charge");
                        player.getCooldownTracker().setCooldown(this, cooldowntime);
                     } else {
                        NBTHelper.AddNBTint(itemstack, -cooldowntime, "charge");
                        player.getCooldownTracker().setCooldown(this, cooldowntime / 5);
                     }

                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.plasma_accelerator,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     Weapons.setPlayerAnimationOnServer(player, 3, EnumHand.MAIN_HAND);
                     player.addStat(StatList.getObjectUseStats(this));
                     IWeapon.fireBomEffect(this, player, world, 0);
                     if (!player.capabilities.isCreativeMode) {
                        itemstack.damageItem(1, player);
                        this.extractEnergyFromItem(itemstack, RFtoShoot, false);
                     }

                     PlasmaAcceleratorShoot projectile = new PlasmaAcceleratorShoot(world, player, itemstack);
                     Weapons.shoot(
                        projectile,
                        EnumHand.MAIN_HAND,
                        player,
                        player.rotationPitch,
                        player.rotationYaw,
                        0.0F,
                        parameters.get("velocity"),
                        parameters.getEnchanted("inaccuracy", acc),
                        -0.2F,
                        0.5F,
                        0.3F
                     );
                     projectile.livetime = parameters.getEnchantedi("livetime", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                     world.spawnEntity(projectile);
                  }
               } else if (charge > 0) {
                  NBTHelper.SetNBTint(itemstack, 0, "charge");
               }
            } else {
               NBTHelper.SetNBTint(itemstack, 0, "charge");
            }
         }
      }
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      Booom.lastTick = 15;
      Booom.frequency = 0.2F;
      Booom.x = 1.0F;
      Booom.y = 0.0F;
      Booom.z = 0.0F;
      Booom.power = 0.3F;
      Booom.FOVlastTick = 15;
      Booom.FOVfrequency = -0.2F;
      Booom.FOVpower = 0.4F;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public int getMaxEnergyStored(ItemStack stack) {
      return ItemAccumulator.TOPAZITRON_CAPACITY * 2;
   }

   @Override
   public int getThroughput() {
      return ItemAccumulator.TOPAZITRON_THROUGHPUT;
   }
}
