package com.vivern.arpg.elements;

import com.vivern.arpg.entity.EntityLaunchedRocket;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RocketLauncher extends ItemWeapon {
   public static int maxammo = 6;

   public RocketLauncher() {
      this.setRegistryName("rocket_launcher");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("rocket_launcher");
      this.setMaxDamage(850);
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
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            this.decreaseReload(itemstack, player);
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            NBTHelper.GiveNBTfloat(itemstack, 0.0F, "cooldown");
            NBTHelper.GiveNBTint(itemstack, 0, "firemode");
            NBTHelper.GiveNBTfloat(itemstack, 0.0F, "angle");
            NBTHelper.GiveNBTfloat(itemstack, 0.0F, "needangle");
            NBTHelper.GiveNBTfloat(itemstack, 0.0F, "prevangle");
            NBTHelper.GiveNBTint(itemstack, 0, "prevfiremode");
            int cooldownTime = this.getCooldownTime(itemstack);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            NBTHelper.SetNBTfloat(itemstack, player.getCooldownTracker().getCooldown(this, 0.0F), "cooldown");
            int firemode = NBTHelper.GetNBTint(itemstack, "firemode");
            int needfiremodeTick = cooldownTime * 3;
            if (itemstack.hasTagCompound()) {
               NBTHelper.SetNBTfloat(itemstack, NBTHelper.GetNBTfloat(itemstack, "angle"), "prevangle");
               NBTHelper.SetNBTint(itemstack, NBTHelper.GetNBTint(itemstack, "firemode"), "prevfiremode");
            }

            if (firemode < 0) {
               if (firemode >= -needfiremodeTick / 2.0F - 1.0F) {
                  NBTHelper.SetNBTint(itemstack, 0, "firemode");
                  if (!player.capabilities.isCreativeMode) {
                     NBTHelper.AddNBTfloat(itemstack, (float) Math.PI, "needangle");
                     NBTHelper.AddNBTfloat(itemstack, (float) Math.PI, "angle");
                     NBTHelper.SetNBTfloat(itemstack, NBTHelper.GetNBTfloat(itemstack, "needangle"), "prevangle");
                  }
               } else {
                  NBTHelper.AddNBTint(itemstack, 1, "firemode");
               }
            }

            if (player.getHeldItemMainhand() == itemstack) {
               float angleunit = 1.347198F / cooldownTime;
               float angle = NBTHelper.GetNBTfloat(itemstack, "angle");
               float angleneed = NBTHelper.GetNBTfloat(itemstack, "needangle");
               float add = Math.min(angleunit, angleneed - angle);
               if (add != 0.0F) {
                  NBTHelper.AddNBTfloat(itemstack, add, "angle");
               }

               if (firemode > 0 && firemode < needfiremodeTick) {
                  NBTHelper.AddNBTint(itemstack, 1, "firemode");
               } else if (click2 && firemode < 1 && firemode > -2 && ammo >= 3) {
                  NBTHelper.SetNBTint(itemstack, 1, "firemode");
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.rocket_launcher_mode,
                     SoundCategory.AMBIENT,
                     0.9F,
                     0.97F + itemRand.nextFloat() / 15.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack) * 0.2F
                  );
               }

               if (click && firemode <= 0) {
                  if (ammo > 0 && this.isReloaded(itemstack)) {
                     if (!hascooldown) {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.rocket_launcher,
                           SoundCategory.AMBIENT,
                           0.9F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                        player.getCooldownTracker().setCooldown(this, cooldownTime);
                        player.addStat(StatList.getObjectUseStats(this));
                        IWeapon.fireBomEffect(this, player, world, 0);
                        if (!player.capabilities.isCreativeMode) {
                           NBTHelper.AddNBTfloat(itemstack, 1.047198F, "needangle");
                           itemstack.damageItem(1, player);
                           this.addAmmo(ammo, itemstack, -1);
                        }

                        EntityLaunchedRocket projectile = new EntityLaunchedRocket(world, player, itemstack);
                        projectile.damage = parameters.getEnchanted("damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack));
                        projectile.knockback = parameters.getEnchanted("knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack));
                        Weapons.shoot(
                           projectile,
                           EnumHand.MAIN_HAND,
                           player,
                           player.rotationPitch,
                           player.rotationYaw,
                           0.0F,
                           parameters.get("velocity"),
                           parameters.getEnchanted("inaccuracy", acc),
                           -0.05F,
                           0.5F,
                           0.7F
                        );
                        world.spawnEntity(projectile);
                        Weapons.setPlayerAnimationOnServer(player, 13, EnumHand.MAIN_HAND);
                     }
                  } else if (this.initiateRocketReload(itemstack, player, maxammo)) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.rocket_launcher_rel,
                        SoundCategory.AMBIENT,
                        0.8F,
                        1.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RELOADING, itemstack) * 0.2F
                     );
                     NBTHelper.SetNBTfloat(itemstack, 0.0F, "needangle");
                     NBTHelper.SetNBTfloat(itemstack, 0.0F, "angle");
                  }
               }

               if (click && firemode >= needfiremodeTick) {
                  if (ammo < 3 || !this.isReloaded(itemstack)) {
                     NBTHelper.SetNBTint(itemstack, -needfiremodeTick, "firemode");
                  } else if (!hascooldown) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.rocket_launcher,
                        SoundCategory.AMBIENT,
                        1.1F,
                        0.8F + itemRand.nextFloat() / 5.0F
                     );
                     player.getCooldownTracker().setCooldown(this, cooldownTime);
                     player.addStat(StatList.getObjectUseStats(this));
                     IWeapon.fireBomEffect(this, player, world, 0);
                     if (!player.capabilities.isCreativeMode) {
                        itemstack.damageItem(3, player);
                        this.addAmmo(ammo, itemstack, -3);
                     }

                     for (int i = 0; i < 3; i++) {
                        EntityLaunchedRocket projectile = new EntityLaunchedRocket(world, player, itemstack);
                        projectile.damage = parameters.getEnchanted("damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack));
                        projectile.knockback = parameters.getEnchanted("knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack));
                        Weapons.shoot(
                           projectile,
                           EnumHand.MAIN_HAND,
                           player,
                           player.rotationPitch,
                           player.rotationYaw + (i - 1) * 5,
                           0.0F,
                           parameters.get("velocity"),
                           parameters.getEnchanted("inaccuracy", acc),
                           -0.05F,
                           0.5F,
                           0.7F
                        );
                        projectile.grenade = true;
                        world.spawnEntity(projectile);
                     }

                     Weapons.setPlayerAnimationOnServer(player, 3, EnumHand.MAIN_HAND);
                     NBTHelper.SetNBTint(itemstack, -needfiremodeTick, "firemode");
                  }
               }
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

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      Booom.lastTick = 14;
      Booom.frequency = -0.225F;
      Booom.x = 1.0F;
      Booom.y = 0.0F;
      Booom.z = 0.0F;
      Booom.power = 0.5F;
      Booom.FOVlastTick = 8;
      Booom.FOVfrequency = -0.395F;
      Booom.FOVpower = 0.022F;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return true;
   }
}
