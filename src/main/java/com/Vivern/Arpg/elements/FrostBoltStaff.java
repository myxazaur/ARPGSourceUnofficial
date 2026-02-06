package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.EntityFrostBolt;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.Sounds;
import java.util.Random;
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

public class FrostBoltStaff extends Item {
   Random rand = new Random();

   public FrostBoltStaff() {
      this.setRegistryName("frost_bolt");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("frost_bolt");
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
         float mana = Mana.getMana(player);
         float spee = Mana.getManaSpeed(player);
         float power = Mana.getMagicPowerMax(player);
         int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
         int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
         int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
         if (player.getActiveItemStack() == itemstack && mana > 5.5F - sor * 0.8F && click && !player.getCooldownTracker().hasCooldown(itemIn)) {
            world.playSound(
               (EntityPlayer)null,
               player.posX,
               player.posY,
               player.posZ,
               Sounds.magic_c,
               SoundCategory.AMBIENT,
               0.8F,
               0.8F / (itemRand.nextFloat() * 0.4F + 0.8F)
            );
            player.getCooldownTracker().setCooldown(this, 22 - rapidity * 3);
            player.addStat(StatList.getObjectUseStats(this));
            if (!player.capabilities.isCreativeMode) {
               Mana.changeMana(player, -5.5F + sor * 0.8F);
               Mana.setManaSpeed(player, 0.001F);
            }

            if (!world.isRemote) {
               EntityFrostBolt bolt = new EntityFrostBolt(world, player, itemstack, power);
               bolt.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.7F, 1.4F / (acc * 2 + 1));
               world.spawnEntity(bolt);
            }
         }
      }
   }
}
