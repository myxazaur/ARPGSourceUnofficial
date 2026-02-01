package com.vivern.arpg.elements;

import com.vivern.arpg.entity.SharkRocket;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Sounds;
import net.minecraft.creativetab.CreativeTabs;
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

public class SharkCannon extends Item {
   public SharkCannon() {
      this.setRegistryName("shark_cannon");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("shark_cannon");
      this.setMaxDamage(3);
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
                  Sounds.sharkfire,
                  SoundCategory.AMBIENT,
                  0.9F,
                  0.9F / (itemRand.nextFloat() * 0.4F + 0.8F)
               );
               player.getCooldownTracker().setCooldown(this, 15);
               player.addStat(StatList.getObjectUseStats(this));
            }

            if (itemstack.getItemDamage() > itemIn.getMaxDamage() - 1 && player.inventory.hasItemStack(new ItemStack(ItemsRegister.SHARKAMMO, 1))) {
               player.inventory.clearMatchingItems(new ItemStack(ItemsRegister.SHARKAMMO, 1).getItem(), -1, 1, null);
               itemstack.setItemDamage(0);
               player.getCooldownTracker().setCooldown(this, 80);
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.sharkreload,
                  SoundCategory.AMBIENT,
                  0.7F,
                  1.0F / (itemRand.nextFloat() * 0.4F + 0.8F)
               );
            }

            if (!player.capabilities.isCreativeMode && damage <= itemIn.getMaxDamage() - 1) {
               itemstack.damageItem(1, player);
            }

            if (!world.isRemote && damage <= itemIn.getMaxDamage() - 1) {
               SharkRocket rocket = new SharkRocket(world, player);
               rocket.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.6F, 1.0F);
               world.spawnEntity(rocket);
            }
         }
      }
   }
}
