package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.BilebiterHomingShoot;
import com.Vivern.Arpg.entity.BilebiterShoot;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Weapons;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Bilebiter extends ItemWeapon {
   public static int maxammo = 80;

   public Bilebiter() {
      this.setRegistryName("bilebiter");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("bilebiter");
      this.setMaxDamage(6800);
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

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      Booom.lastTick = 8;
      Booom.frequency = 4.0F;
      Booom.x = (float)itemRand.nextGaussian();
      Booom.y = (float)itemRand.nextGaussian();
      Booom.z = (float)itemRand.nextGaussian();
      Booom.power = 0.19F;
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            this.decreaseReload(itemstack, player);
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean clickcec = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int reuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            int for1shootP = 20;
            int maxchargeP = 100;
            if (rapidity == 1) {
               for1shootP = 15;
               maxchargeP = 75;
            }

            if (rapidity > 1) {
               for1shootP = 10;
               maxchargeP = 50;
            }

            int maxchargeS = 200;
            if (rapidity == 1) {
               maxchargeS = 175;
            }

            if (rapidity > 1) {
               maxchargeS = 150;
            }

            NBTHelper.GiveNBTint(itemstack, 0, "charge_primary");
            NBTHelper.GiveNBTint(itemstack, 0, "charge_secondary");
            int chargesec = NBTHelper.GetNBTint(itemstack, "charge_secondary");
            if (NBTHelper.GetNBTint(itemstack, "charge_primary") < maxchargeP) {
               NBTHelper.AddNBTint(itemstack, 1, "charge_primary");
            }

            if (chargesec < 40 && chargesec >= 0 && Mana.getMana(player) > 1.0F) {
               NBTHelper.AddNBTint(itemstack, 1, "charge_secondary");
               if (!player.capabilities.isCreativeMode) {
                  Mana.changeMana(player, -0.3F / (1 + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack)));
                  Mana.setManaSpeed(player, 0.001F);
               }
            }

            if (chargesec < maxchargeS && chargesec >= 40) {
               NBTHelper.AddNBTint(itemstack, 1, "charge_secondary");
            }

            if (player.getHeldItemMainhand() == itemstack
               && click
               && !player.getCooldownTracker().hasCooldown(this)
               && NBTHelper.GetNBTint(itemstack, "charge_primary") >= for1shootP) {
               if (ammo > 0 && this.isReloaded(itemstack)) {
                  if (!player.getCooldownTracker().hasCooldown(this)) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.bb_shoot,
                        SoundCategory.AMBIENT,
                        0.8F,
                        0.95F + itemRand.nextFloat() / 10.0F
                     );
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     NBTHelper.AddNBTint(itemstack, -for1shootP, "charge_primary");
                     Weapons.setPlayerAnimationOnServer(player, 13, EnumHand.MAIN_HAND);
                     BilebiterShoot acid = new BilebiterShoot(world, player, itemstack);
                     acid.shoot(player, player.rotationPitch, player.rotationYaw, 0.5F, 1.4F, 2.9F - acc / 1.15F);
                     world.spawnEntity(acid);
                     if (!player.capabilities.isCreativeMode && itemRand.nextFloat() > reuse / 9) {
                        this.addAmmo(ammo, itemstack, -1);
                        itemstack.damageItem(1, player);
                     }
                  }
               } else if (this.initiateReload(itemstack, player, ItemsRegister.BILEBITERAMMO, maxammo)) {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.bb_reloading,
                     SoundCategory.NEUTRAL,
                     0.7F,
                     0.95F + itemRand.nextFloat() / 10.0F
                  );
                  Weapons.setPlayerAnimationOnServer(player, 4, EnumHand.MAIN_HAND);
               }
            }

            if (player.getHeldItemMainhand() == itemstack
               && clickcec
               && !player.getCooldownTracker().hasCooldown(this)
               && NBTHelper.GetNBTint(itemstack, "charge_secondary") >= maxchargeS
               && ammo >= 10) {
               NBTHelper.SetNBTint(itemstack, -200, "charge_secondary");
               if (!player.capabilities.isCreativeMode) {
                  if (reuse == 0) {
                     this.addAmmo(ammo, itemstack, -10);
                     itemstack.damageItem(10, player);
                  } else {
                     this.addAmmo(ammo, itemstack, 10 - itemRand.nextInt(reuse * 2));
                     itemstack.damageItem(10, player);
                  }
               }
            }

            if (player.getHeldItemMainhand() == itemstack && !player.getCooldownTracker().hasCooldown(this) && NBTHelper.GetNBTint(itemstack, "charge_secondary") < 0) {
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.bb_homing,
                  SoundCategory.AMBIENT,
                  0.8F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
               player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
               player.addStat(StatList.getObjectUseStats(this));
               NBTHelper.AddNBTint(itemstack, 40, "charge_secondary");
               BilebiterHomingShoot acidd = new BilebiterHomingShoot(world, player, itemstack);
               acidd.shoot(player, player.rotationPitch - 15.0F + itemRand.nextInt(25), player.rotationYaw - 70.0F, 0.5F, 1.1F, 3.7F - acc / 3);
               world.spawnEntity(acidd);
               BilebiterHomingShoot aciddd = new BilebiterHomingShoot(world, player, itemstack);
               aciddd.shoot(player, player.rotationPitch - 15.0F + itemRand.nextInt(25), player.rotationYaw + 70.0F, 0.5F, 1.1F, 3.7F - acc / 3);
               world.spawnEntity(aciddd);
               IWeapon.fireBomEffect(this, player, world, 0);
            }
         }
      }
   }

   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      return MathHelper.clamp((float)NBTHelper.GetNBTint(itemstack, "ammo") / maxammo, 0.0F, 1.0F);
   }

   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return true;
   }

   @Override
   public int getItemEnchantability() {
      return 10;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public int getReloadTime(ItemStack itemstack) {
      return 45 - EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RELOADING, itemstack) * 10;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      int rap = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      if (rap == 0) {
         return 3;
      } else {
         return rap == 1 ? 3 - Math.round(itemRand.nextFloat()) : 2;
      }
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }
}
