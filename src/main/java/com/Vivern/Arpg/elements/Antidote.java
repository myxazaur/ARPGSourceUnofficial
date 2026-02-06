package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.potions.PotionEffects;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class Antidote extends Item {
   public Antidote() {
      this.setRegistryName("antidote");
      this.setCreativeTab(CreativeTabs.FOOD);
      this.setTranslationKey("antidote");
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      if (player.isPotionActive(MobEffects.POISON)) {
         player.removePotionEffect(MobEffects.POISON);
      }

      if (player.isPotionActive(PotionEffects.TOXIN)) {
         player.removePotionEffect(PotionEffects.TOXIN);
      }

      if (player.isPotionActive(MobEffects.WITHER)) {
         player.removePotionEffect(MobEffects.WITHER);
      }

      if (player.isPotionActive(PotionEffects.CHLORITE)) {
         player.removePotionEffect(PotionEffects.CHLORITE);
      }

      itemstack.shrink(1);
      world.playSound(
         (EntityPlayer)null,
         player.posX,
         player.posY,
         player.posZ,
         Sounds.injector,
         SoundCategory.PLAYERS,
         0.8F,
         0.9F + itemRand.nextFloat() / 5.0F
      );
      return new ActionResult(EnumActionResult.SUCCESS, itemstack);
   }
}
