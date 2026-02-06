package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.PropertiesRegistry;
import com.Vivern.Arpg.main.Sounds;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class HealthfulCapsule extends Item {
   public HealthfulCapsule() {
      this.setRegistryName("healthful_capsule");
      this.setCreativeTab(CreativeTabs.FOOD);
      this.setTranslationKey("healthful_capsule");
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      if (!world.isRemote) {
         PropertiesRegistry.PermanentAttributes attributes = PropertiesRegistry.getPlayerPermanentAttributes(player);
         if (attributes.liveBoosters[1] < PropertiesRegistry.PermanentAttributes.getMaximumLiveBoosts(1)) {
            attributes.liveBoosters[1]++;
         }

         PropertiesRegistry.setPlayerPermanentAttributes(player, attributes);
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
