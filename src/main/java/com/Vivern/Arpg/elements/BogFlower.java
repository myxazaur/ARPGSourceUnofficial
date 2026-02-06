package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.EntityBogFlower;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;

public class BogFlower extends ItemWeapon {
   public BogFlower() {
      this.setRegistryName("bog_flower");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("bog_flower");
      this.setMaxDamage(4);
      this.setMaxStackSize(1);
   }

   public int getMaxItemUseDuration(ItemStack itemstack) {
      return 72000;
   }

   public EnumAction getItemUseAction(ItemStack stack) {
      return EnumAction.BOW;
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      player.setActiveHand(hand);
      return new ActionResult(EnumActionResult.PASS, itemstack);
   }

   public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
      return true;
   }

   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!worldIn.isRemote && entityIn instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entityIn;
         int damage = itemstack.getItemDamage();
         World world = player.getEntityWorld();
         Item itemIn = itemstack.getItem();
         EnumHand hand = player.getActiveHand();
         boolean click = Mouse.isButtonDown(1);
         float mana = Mana.getMana(player);
         float spee = Mana.getManaSpeed(player);
         int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
         int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
         float power = Mana.getMagicPowerMax(player);
         NBTHelper.GiveNBTint(itemstack, 0, "charges");
         int charges = NBTHelper.GetNBTint(itemstack, "charges");
         int rel = this.getReloadTime(itemstack);
         if (charges < rel * (3 + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) * 2)) {
            NBTHelper.AddNBTint(itemstack, 1, "charges");
            itemstack.setItemDamage(4 - Math.round((float)(charges / rel)));
         }

         if (!player.getCooldownTracker().hasCooldown(itemIn) && player.getActiveItemStack() == itemstack && mana > 10.0F - sor * 1.2F && click && charges >= rel) {
            NBTHelper.AddNBTint(itemstack, -rel, "charges");
            world.playSound(
               (EntityPlayer)null,
               player.posX,
               player.posY,
               player.posZ,
               Sounds.bog_flower,
               SoundCategory.AMBIENT,
               0.8F,
               0.9F + itemRand.nextFloat() / 5.0F
            );
            player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
            player.addStat(StatList.getObjectUseStats(this));
            if (!player.capabilities.isCreativeMode) {
               Mana.changeMana(player, -10.0F + sor * 1.2F);
               Mana.setManaSpeed(player, 0.001F);
            }

            EntityBogFlower projectile = new EntityBogFlower(world, player, itemstack, power);
            projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.5F, 3.0F - acc);
            world.spawnEntity(projectile);
         }
      }
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.ONE_HANDED;
   }

   @Override
   public boolean autoReload(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return true;
   }

   @Override
   public boolean hasZoom(ItemStack itemstack) {
      return false;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      return 10 - EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack) * 3;
   }

   @Override
   public int getReloadTime(ItemStack itemstack) {
      return 110 - EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RELOADING, itemstack) * 25;
   }

   @Override
   public float getZoom(ItemStack itemstack, EntityPlayer player) {
      return 0.0F;
   }
}
