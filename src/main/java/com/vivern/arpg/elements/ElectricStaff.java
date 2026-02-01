package com.vivern.arpg.elements;

import com.vivern.arpg.entity.EntityElectricBolt;
import com.vivern.arpg.main.EnchantmentInit;
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

public class ElectricStaff extends ItemWeapon {
   public ElectricStaff() {
      this.setRegistryName("electric_staff");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("electric_staff");
      this.setMaxDamage(5000);
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
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            float manacost = parameters.getEnchanted("manacost", sor);
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            EnumHand hand = player.getHeldItemMainhand() == itemstack ? EnumHand.MAIN_HAND : (player.getHeldItemOffhand() == itemstack ? EnumHand.OFF_HAND : null);
            if (hand != null
               && (click && hand == EnumHand.MAIN_HAND || click2 && hand == EnumHand.OFF_HAND)
               && Mana.getMana(player) > manacost
               && !player.getCooldownTracker().hasCooldown(this)) {
               Weapons.setPlayerAnimationOnServer(player, 14, hand);
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.electricstaff,
                  SoundCategory.AMBIENT,
                  0.9F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
               player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
               player.addStat(StatList.getObjectUseStats(this));
               EntityElectricBolt bolt = new EntityElectricBolt(world, player, itemstack, power);
               Weapons.shoot(
                  bolt,
                  EnumHand.MAIN_HAND,
                  player,
                  player.rotationPitch,
                  player.rotationYaw,
                  0.0F,
                  parameters.get("velocity"),
                  parameters.getEnchanted("inaccuracy", acc),
                  -0.15F,
                  0.5F,
                  0.2F
               );
               bolt.livetime = parameters.getEnchantedi("livetime", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
               world.spawnEntity(bolt);
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
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.SEMI_ONE_HANDED;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }
}
