package com.vivern.arpg.elements;

import com.vivern.arpg.main.PropertiesRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ManaExpansionPotion extends ItemEatable {
   public ManaExpansionPotion() {
      super("mana_expansion_potion", 0, 16, 0, 0.0F, false, 32, null, null, true, 0);
      this.ench = true;
      this.setAlwaysEdible();
      this.setStackToReturn(new ItemStack(Items.GLASS_BOTTLE));
   }

   @Override
   protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
      if (!world.isRemote) {
         PropertiesRegistry.PermanentAttributes attributes = PropertiesRegistry.getPlayerPermanentAttributes(player);
         if (attributes.manaBoosters[0] < PropertiesRegistry.PermanentAttributes.getMaximumManaBoosts(0)) {
            attributes.manaBoosters[0]++;
         }

         PropertiesRegistry.setPlayerPermanentAttributes(player, attributes);
      }
   }
}
