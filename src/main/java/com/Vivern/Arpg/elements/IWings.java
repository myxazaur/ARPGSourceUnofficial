//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IWings {
   String[] itemsNoCompatibleNames = new String[]{"minecraft:elytra"};

   int getMaxFlyTime(ItemStack var1);

   static boolean isEquippableWithChestplate(Item chestplate) {
      String itemname = chestplate.getRegistryName().toString();

      for (String name : itemsNoCompatibleNames) {
         if (name.equals(itemname)) {
            return false;
         }
      }

      return true;
   }

   static void chestplateReturnToInv(EntityPlayer player) {
      if (!isEquippableWithChestplate(((ItemStack)player.inventory.armorInventory.get(2)).getItem())) {
         ItemStack transpstack = (ItemStack)player.inventory.armorInventory.get(2);
         int empty = player.inventory.getFirstEmptyStack();
         player.inventory.armorInventory.set(2, ItemStack.EMPTY);
         if (empty != -1) {
            player.inventory.setInventorySlotContents(empty, transpstack);
         } else if (!player.world.isRemote) {
            player.world
               .spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, transpstack));
         }
      }
   }
}
