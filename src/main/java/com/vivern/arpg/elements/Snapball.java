package com.vivern.arpg.elements;

import com.vivern.arpg.entity.EntitySnapball;
import com.vivern.arpg.main.Booom;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.ItemsRegister;
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

public class Snapball extends ItemWeapon implements IEnergyItem {
   public static int maxammo = 1;

   public Snapball() {
      this.setRegistryName("snapball");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("snapball");
      this.setMaxDamage(3500);
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
            this.decreaseReload(itemstack, player);
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            if ((click || click2) && player.getHeldItemMainhand() == itemstack) {
               if (ammo > 0 && this.isReloaded(itemstack)) {
                  if (!player.getCooldownTracker().hasCooldown(this)) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.snapball,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     Weapons.setPlayerAnimationOnServer(player, 3, EnumHand.MAIN_HAND);
                     IWeapon.fireBomEffect(this, player, world, 0);
                     if (!player.capabilities.isCreativeMode) {
                        this.addAmmo(ammo, itemstack, -1);
                        itemstack.damageItem(1, player);
                     }

                     NBTHelper.GiveNBTint(itemstack, 0, "charge");
                     int charge = NBTHelper.GetNBTint(itemstack, "charge");
                     boolean isPowered = charge > parameters.geti("charge_to_powered");
                     if (click) {
                        EntitySnapball projectile = new EntitySnapball(world, player, itemstack);
                        Weapons.shoot(
                           projectile,
                           EnumHand.MAIN_HAND,
                           player,
                           player.rotationPitch,
                           player.rotationYaw,
                           0.0F,
                           isPowered ? parameters.get("velocity_charged") : parameters.get("velocity"),
                           parameters.getEnchanted("inaccuracy", acc),
                           -0.15F,
                           0.5F,
                           0.4F
                        );
                        world.spawnEntity(projectile);
                        if (isPowered) {
                           world.playSound(
                              (EntityPlayer)null,
                              player.posX,
                              player.posY,
                              player.posZ,
                              Sounds.snapball_explode,
                              SoundCategory.AMBIENT,
                              0.7F,
                              1.2F + itemRand.nextFloat() / 5.0F
                           );
                           projectile.powered = true;
                           NBTHelper.SetNBTint(itemstack, 0, "charge");
                        }
                     } else if (click2) {
                        EntitySnapball projectile = new EntitySnapball(world, player, itemstack);
                        projectile.enablePhysics = true;
                        projectile.exploding = true;
                        Weapons.shoot(
                           projectile,
                           EnumHand.MAIN_HAND,
                           player,
                           player.rotationPitch,
                           player.rotationYaw,
                           0.0F,
                           parameters.get("velocity_grenade"),
                           parameters.getEnchanted("inaccuracy", acc),
                           -0.15F,
                           0.5F,
                           0.4F
                        );
                        world.spawnEntity(projectile);
                        if (isPowered) {
                           world.playSound(
                              (EntityPlayer)null,
                              player.posX,
                              player.posY,
                              player.posZ,
                              Sounds.snapball_explode,
                              SoundCategory.AMBIENT,
                              0.7F,
                              1.2F + itemRand.nextFloat() / 5.0F
                           );
                           projectile.powered = true;
                           NBTHelper.SetNBTint(itemstack, 0, "charge");
                        }
                     }
                  }
               } else {
                  int reuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
                  int RFtoReload = parameters.getEnchantedi("rf_to_reload", reuse);
                  int RF = this.getEnergyStored(itemstack);
                  if (RF >= RFtoReload && this.initiateReload(itemstack, player, ItemsRegister.SNAPBALLAMMO, maxammo)) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.snapball_rel,
                        SoundCategory.AMBIENT,
                        0.7F,
                        0.95F + (itemRand.nextFloat() + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RELOADING, itemstack)) / 10.0F
                     );
                     this.extractEnergyFromItem(itemstack, RFtoReload, false);
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return true;
   }

   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
      int charge = NBTHelper.GetNBTint(itemstack, "charge");
      int chtp = parameters.geti("charge_to_powered") + 1;
      return MathHelper.clamp((float)charge / chtp, 0.0F, 1.0F);
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      Booom.lastTick = 13;
      Booom.frequency = -0.245F;
      Booom.x = 1.0F;
      Booom.y = 0.0F;
      Booom.z = 0.0F;
      Booom.power = 0.5F;
      Booom.FOVlastTick = 10;
      Booom.FOVfrequency = -0.32F;
      Booom.FOVpower = 0.025F;
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
      return ItemAccumulator.LI_ION_CAPACITY * 2;
   }

   @Override
   public int getThroughput() {
      return ItemAccumulator.LI_ION_THROUGHPUT;
   }
}
