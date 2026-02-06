package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.PropertiesRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class HealthFruit extends ItemEatable {
   public HealthFruit() {
      super("health_fruit", 0, 4, 6, 1.0F, false, 32, null, null, false, 0);
      this.ench = true;
      this.setAlwaysEdible();
   }

   @Override
   protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
      if (!world.isRemote) {
         PropertiesRegistry.PermanentAttributes attributes = PropertiesRegistry.getPlayerPermanentAttributes(player);
         if (attributes.liveBoosters[0] < PropertiesRegistry.PermanentAttributes.getMaximumLiveBoosts(0)) {
            attributes.liveBoosters[0]++;
         }

         PropertiesRegistry.setPlayerPermanentAttributes(player, attributes);
      }
   }
}
