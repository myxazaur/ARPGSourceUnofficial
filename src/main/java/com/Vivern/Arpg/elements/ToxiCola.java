package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.ColorConverters;
import com.Vivern.Arpg.main.Mana;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ToxiCola extends Item {
   public ToxiCola() {
      this.setRegistryName("toxicola");
      this.setCreativeTab(CreativeTabs.FOOD);
      this.setTranslationKey("toxicola");
   }

   public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
      playerIn.setActiveHand(handIn);
      return new ActionResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
   }

   public EnumAction getItemUseAction(ItemStack stack) {
      return EnumAction.DRINK;
   }

   public int getMaxItemUseDuration(ItemStack stack) {
      return 20;
   }

   public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
      if (entityLiving instanceof EntityPlayerMP) {
         EntityPlayerMP entityplayermp = (EntityPlayerMP)entityLiving;
         CriteriaTriggers.CONSUME_ITEM.trigger(entityplayermp, stack);
         entityplayermp.addStat(StatList.getObjectUseStats(this));
      }

      if (entityLiving.isPotionActive(MobEffects.SLOWNESS)) {
         entityLiving.removePotionEffect(MobEffects.SLOWNESS);
      }

      if (entityLiving.isPotionActive(MobEffects.WEAKNESS)) {
         entityLiving.removePotionEffect(MobEffects.WEAKNESS);
      }

      entityLiving.heal(7.0F);
      if (entityLiving instanceof EntityPlayer) {
         int amount = 15;
         EntityPlayer player = (EntityPlayer)entityLiving;
         Mana.setMana(player, ColorConverters.InBorder(Mana.getMana(player), Mana.getManaMax(player), Mana.getMana(player) + amount));
         Mana.addRad(player, 30 + worldIn.getDifficulty().getId() * 20, false);
         if (!((EntityPlayer)entityLiving).capabilities.isCreativeMode) {
            stack.shrink(1);
            player.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE, 1));
         }
      }

      entityLiving.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 350));
      return stack;
   }
}
