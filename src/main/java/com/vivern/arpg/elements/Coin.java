package com.vivern.arpg.elements;

import com.vivern.arpg.main.Coins;
import com.vivern.arpg.main.Sounds;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class Coin extends Item {
   public Coin() {
      this.setRegistryName("coin");
      this.setCreativeTab(CreativeTabs.MISC);
      this.setTranslationKey("coin");
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      int count = player.isSneaking() ? itemstack.getCount() : 1;
      itemstack.shrink(count);
      Coins.addCoins(player, count);
      world.playSound(
         player,
         player.posX,
         player.posY,
         player.posZ,
         Sounds.grap_money,
         SoundCategory.PLAYERS,
         0.8F,
         0.9F + itemRand.nextFloat() / 5.0F
      );
      return new ActionResult(EnumActionResult.SUCCESS, itemstack);
   }
}
