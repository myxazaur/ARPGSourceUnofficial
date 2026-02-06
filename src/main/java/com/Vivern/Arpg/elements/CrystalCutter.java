package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.EntityCrystalCutter;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.WeaponParameters;
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

public class CrystalCutter extends ItemWeapon {
   public static int maxammo = 7;

   public CrystalCutter() {
      this.setRegistryName("crystal_cutter");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("crystal_cutter");
      this.setMaxDamage(780);
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
      Booom.lastTick = 16;
      Booom.frequency = -0.196F;
      Booom.FOVlastTick = 16;
      Booom.FOVfrequency = -0.196F;
      Booom.x = 1.0F;
      Booom.y = 0.0F;
      Booom.z = 0.0F;
      Booom.power = 0.2F;
      Booom.FOVpower = 0.08F;
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            this.decreaseReload(itemstack, player);
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            int anim = NBTHelper.GetNBTint(itemstack, "animation");
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            NBTHelper.SetNBTint(itemstack, anim, "prevanimation");
            if (ammo < maxammo) {
               NBTHelper.SetNBTint(itemstack, Math.min(anim + 1, (maxammo - ammo) * 10), "animation");
            }

            if (click && player.getHeldItemMainhand() == itemstack) {
               if (ammo > 0 && this.isReloaded(itemstack)) {
                  if (!player.getCooldownTracker().hasCooldown(this)) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.crystal_cutter,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.95F + itemRand.nextFloat() / 10.0F
                     );
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     IWeapon.fireBomEffect(this, player, world, 0);
                     Weapons.setPlayerAnimationOnServer(player, 13, EnumHand.MAIN_HAND);
                     EntityCrystalCutter shoot = new EntityCrystalCutter(world, player, itemstack);
                     Weapons.shoot(
                        shoot,
                        EnumHand.MAIN_HAND,
                        player,
                        player.rotationPitch,
                        player.rotationYaw,
                        0.0F,
                        parameters.get("velocity"),
                        parameters.getEnchanted("inaccuracy", acc),
                        -0.4F,
                        0.4F,
                        0.2F
                     );
                     if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0) {
                        shoot.triple = true;
                     }

                     shoot.cutterSize = parameters.getEnchanted("cutter_size", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                     shoot.livetime = parameters.getEnchantedi("livetime", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                     world.spawnEntity(shoot);
                     if (!player.capabilities.isCreativeMode) {
                        this.addAmmo(ammo, itemstack, -1);
                        itemstack.damageItem(1, player);
                        NBTHelper.GiveNBTint(itemstack, 0, "animation");
                        NBTHelper.GiveNBTint(itemstack, 0, "prevanimation");
                     }
                  }
               } else if (this.initiateReload(itemstack, player, ItemsRegister.CRYSTALCUTTERAMMO, maxammo)) {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.crystal_cutter_rel,
                     SoundCategory.NEUTRAL,
                     0.7F,
                     0.95F + itemRand.nextFloat() / 10.0F
                  );
                  Weapons.setPlayerAnimationOnServer(player, 4, EnumHand.MAIN_HAND);
                  NBTHelper.SetNBTint(itemstack, 0, "animation");
                  NBTHelper.SetNBTint(itemstack, 0, "prevanimation");
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

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }
}
