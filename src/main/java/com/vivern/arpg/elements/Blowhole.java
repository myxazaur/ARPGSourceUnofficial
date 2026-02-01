package com.vivern.arpg.elements;

import com.vivern.arpg.entity.BlowholeShoot;
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

public class Blowhole extends ItemWeapon {
   public static int maxammo = 160;

   public Blowhole() {
      this.setRegistryName("blowhole");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("blowhole");
      this.setMaxDamage(3400);
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
      if (param < 8) {
         Booom.lastTick = 8;
         Booom.frequency = -0.395F;
      } else if (param < 16) {
         Booom.lastTick = 12;
         Booom.frequency = -0.262F;
      } else {
         Booom.lastTick = 16;
         Booom.frequency = -0.196F;
      }

      Booom.x = 1.0F;
      Booom.y = 0.0F;
      Booom.z = 0.0F;
      Booom.power = 0.3F * (param / 10.0F);
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
            int reuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
            int special = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            NBTHelper.GiveNBTint(itemstack, 0, "charge");
            int charge = NBTHelper.GetNBTint(itemstack, "charge");
            EnumHand hand = player.getHeldItemMainhand() == itemstack ? EnumHand.MAIN_HAND : (player.getHeldItemOffhand() == itemstack ? EnumHand.OFF_HAND : null);
            boolean click3 = hand == EnumHand.MAIN_HAND ? click : click2;
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            int maxcharge = parameters.geti("max_charge");
            if (hand != null) {
               boolean anotherHandEmpty = player.getHeldItem(hand == EnumHand.MAIN_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND).isEmpty();
               if (click3) {
                  if (ammo > 0 && this.isReloaded(itemstack)) {
                     if (charge == 4) {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.blowhole_charge,
                           SoundCategory.NEUTRAL,
                           0.7F,
                           0.95F + itemRand.nextFloat() / 10.0F
                        );
                     }

                     NBTHelper.AddNBTint(itemstack, parameters.geti("charge_add"), "charge");
                     if (charge > 7 && charge % 9 == 0) {
                        Weapons.setPlayerAnimationOnServer(player, anotherHandEmpty ? 11 : 10, hand);
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.blowhole_loop,
                           SoundCategory.NEUTRAL,
                           0.6F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                     }
                  } else if (this.initiateReload(itemstack, player, ItemsRegister.BLOWHOLEPELLETS, maxammo)) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.blowhole_rel,
                        SoundCategory.NEUTRAL,
                        0.7F,
                        0.95F + itemRand.nextFloat() / 10.0F
                     );
                     Weapons.setPlayerAnimationOnServer(player, 4, hand);
                     NBTHelper.SetNBTint(itemstack, 0, "charge");
                  }
               } else if (charge > 0) {
                  charge = Math.min(charge, maxcharge);
                  if (!player.getCooldownTracker().hasCooldown(this)) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.blowhole,
                        SoundCategory.AMBIENT,
                        0.9F,
                        1.25F + itemRand.nextFloat() / 10.0F - charge / 50.0F
                     );
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     IWeapon.fireBomEffect(this, player, world, charge);
                     Weapons.setPlayerAnimationOnServer(player, anotherHandEmpty ? 3 : 5, hand);
                     BlowholeShoot shoot = new BlowholeShoot(
                        world,
                        player,
                        itemstack,
                        charge * parameters.getEnchanted("bubble_size_multiplier", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack))
                     );
                     Weapons.shoot(
                        shoot,
                        hand,
                        player,
                        player.rotationPitch,
                        player.rotationYaw,
                        0.0F,
                        parameters.getEnchanted("velocity", special) + charge * parameters.getEnchanted("velocity_charge_multiplier", special),
                        parameters.getEnchanted("inaccuracy", acc),
                        -0.2F,
                        0.5F,
                        0.15F
                     );
                     world.spawnEntity(shoot);
                     NBTHelper.SetNBTint(itemstack, 0, "charge");
                     if (!player.capabilities.isCreativeMode) {
                        int ammoadd = 0;
                        if (charge < 8) {
                           ammoadd = parameters.getEnchantedi("ammo_add", reuse);
                        } else {
                           ammoadd = (int)(-charge * parameters.getEnchanted("ammo_add_charged_multiplier", reuse));
                        }

                        this.addAmmo(ammo, itemstack, ammoadd);
                        itemstack.damageItem(1 + charge / 6, player);
                     }
                  }
               }
            } else {
               NBTHelper.SetNBTint(itemstack, MathHelper.clamp(charge - 1, 0, maxcharge), "charge");
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
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.SEMI_ONE_HANDED;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }
}
