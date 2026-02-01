package com.vivern.arpg.elements;

import com.vivern.arpg.entity.EntityIchor;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.Mana;
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

public class IchorShower extends ItemWeapon {
   public IchorShower() {
      this.setRegistryName("ichor_shower");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("ichor_shower");
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
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
            float power = Mana.getMagicPowerMax(player);
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            float manacost = parameters.getEnchanted("manacost", sor);
            if (click && player.getHeldItemMainhand() == itemstack && Mana.getMana(player) > manacost && !player.getCooldownTracker().hasCooldown(this)) {
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.ichorsteam,
                  SoundCategory.AMBIENT,
                  0.8F,
                  0.4F / (itemRand.nextFloat() * 0.4F + 0.8F)
               );
               player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
               player.addStat(StatList.getObjectUseStats(this));
               Weapons.setPlayerAnimationOnServer(player, 11, EnumHand.MAIN_HAND);
               EntityIchor entit = new EntityIchor(world, player, itemstack, power);
               Weapons.shoot(
                  entit,
                  EnumHand.MAIN_HAND,
                  player,
                  player.rotationPitch - 8.0F,
                  player.rotationYaw,
                  0.0F,
                  parameters.get("velocity"),
                  parameters.getEnchanted("inaccuracy", acc),
                  -0.3F,
                  0.4F,
                  0.2F
               );
               world.spawnEntity(entit);
               if (!player.capabilities.isCreativeMode) {
                  Mana.changeMana(player, -manacost);
                  Mana.setManaSpeed(player, 0.001F);
                  itemstack.damageItem(1, player);
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
   public int getCooldownTime(ItemStack itemstack) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(itemstack.getItem());
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return GetMOP.floatToIntWithChance(parameters.getEnchanted("cooldown", rapidity), itemRand);
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
