package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.EntityVampireKnife;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.FindAmmo;
import com.Vivern.Arpg.main.ItemsRegister;
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

public class VampireKnifes extends Item {
   public VampireKnifes() {
      this.setRegistryName("vampire_knifes");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("vampire_knifes");
      this.setMaxDamage(20);
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
         if (player.getActiveItemStack() == itemstack && click && !player.getCooldownTracker().hasCooldown(itemIn)) {
            if (damage <= itemIn.getMaxDamage() - 1) {
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.knife,
                  SoundCategory.AMBIENT,
                  0.8F,
                  0.4F / (itemRand.nextFloat() * 0.4F + 0.8F)
               );
               player.getCooldownTracker().setCooldown(this, 8 - EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack));
               player.addStat(StatList.getObjectUseStats(this));
            }

            if (itemstack.getItemDamage() > itemIn.getMaxDamage() - 1 && FindAmmo.Find(player.inventory, ItemsRegister.VAMPIREKNIFE) >= 20) {
               player.inventory.clearMatchingItems(new ItemStack(ItemsRegister.VAMPIREKNIFE, 20).getItem(), -1, 20, null);
               itemstack.setItemDamage(0);
               player.getCooldownTracker().setCooldown(this, 45 - EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RELOADING, itemstack) * 5);
               world.playSound(
                  (EntityPlayer)null, player.posX, player.posY, player.posZ, Sounds.vampireknifes, SoundCategory.NEUTRAL, 0.7F, 1.0F
               );
            }

            if (!player.capabilities.isCreativeMode && damage <= itemIn.getMaxDamage() - 1) {
               itemstack.damageItem(1, player);
            }

            if (!world.isRemote && damage <= itemIn.getMaxDamage() - 1) {
               EntityVampireKnife apple = new EntityVampireKnife(world, player, itemstack);
               apple.shoot(
                  player,
                  player.rotationPitch,
                  player.rotationYaw,
                  0.0F,
                  1.1F,
                  0.9F - EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack) / 5
               );
               world.spawnEntity(apple);
            }
         }
      }
   }

   public int getItemEnchantability() {
      return 10;
   }
}
