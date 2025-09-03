//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.main;

import com.Vivern.Arpg.elements.ItemBullet;
import com.Vivern.Arpg.elements.ItemRocket;
import com.Vivern.Arpg.elements.SoulStone;
import com.Vivern.Arpg.elements.Wrench;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FindAmmo {
   @Deprecated
   public static int Find(InventoryPlayer inventory, Item item) {
      int count = 0;

      for (int i = 0; i < 36; i++) {
         if (inventory.hasItemStack(new ItemStack(item)) && inventory.getStackInSlot(i).getItem() == item) {
            count += inventory.getStackInSlot(i).getCount();
         }
      }

      return count;
   }

   public static Item FindModulate(InventoryPlayer inventory, List<Item> itemlist) {
      for (int i = 0; i < 36; i++) {
         for (Item variantItem : itemlist) {
            if (inventory.getStackInSlot(i).getItem() == variantItem) {
               return variantItem;
            }
         }
      }

      return null;
   }

   public static ItemStack getStackFor(InventoryPlayer inventory, Item item) {
      for (int i = 0; i < inventory.mainInventory.size(); i++) {
         ItemStack st = (ItemStack)inventory.mainInventory.get(i);
         if (!st.isEmpty() && item == st.getItem()) {
            return st;
         }
      }

      return ItemStack.EMPTY;
   }

   public static int getNonEmptyClipStack(InventoryPlayer inventory, Item item) {
      for (int i = 0; i < inventory.mainInventory.size(); i++) {
         ItemStack st = (ItemStack)inventory.mainInventory.get(i);
         if (!st.isEmpty() && item == st.getItem()) {
            String nbtname = NBTHelper.GetNBTstring(st, "bullet");
            if (!nbtname.isEmpty()) {
               return i;
            }
         }
      }

      return -1;
   }

   public static int getNonEmptyClipStack(InventoryPlayer inventory, Item item, int amount) {
      for (int i = 0; i < inventory.mainInventory.size(); i++) {
         ItemStack st = (ItemStack)inventory.mainInventory.get(i);
         if (!st.isEmpty() && item == st.getItem()) {
            String nbtname = NBTHelper.GetNBTstring(st, "bullet");
            if (!nbtname.isEmpty()) {
               if (st.getCount() >= amount) {
                  return i;
               }

               int count = st.getCount();

               for (int i2 = i + 1; i2 < inventory.mainInventory.size(); i2++) {
                  ItemStack st2 = (ItemStack)inventory.mainInventory.get(i2);
                  if (!st2.isEmpty() && item == st2.getItem()) {
                     String nbtname2 = NBTHelper.GetNBTstring(st2, "bullet");
                     if (nbtname.equals(nbtname2)) {
                        count += st2.getCount();
                     }
                  }
               }

               if (count >= amount) {
                  return i;
               }
            }
         }
      }

      return -1;
   }

   public static int getSlotFor(InventoryPlayer inventory, Item item) {
      for (int i = 0; i < inventory.mainInventory.size(); i++) {
         ItemStack st = (ItemStack)inventory.mainInventory.get(i);
         if (!st.isEmpty() && item == st.getItem()) {
            return i;
         }
      }

      return -1;
   }

   public static int getSlotForWrench(InventoryPlayer inventory) {
      for (int i = 0; i < inventory.mainInventory.size(); i++) {
         ItemStack st = (ItemStack)inventory.mainInventory.get(i);
         if (!st.isEmpty() && st.getItem() instanceof Wrench) {
            return i;
         }
      }

      return -1;
   }

   public static int getSlotForEmptySoulstone(InventoryPlayer inventory) {
      for (int i = 0; i < inventory.mainInventory.size(); i++) {
         ItemStack st = (ItemStack)inventory.mainInventory.get(i);
         if (!st.isEmpty() && ItemsRegister.SOULSTONE == st.getItem() && SoulStone.getSoul(st) == 0) {
            return i;
         }
      }

      return -1;
   }

   public static int getSlotForItemBullet(InventoryPlayer inventory, @Nullable ItemBullet bullet, boolean onlyCurrentType) {
      for (int i = 0; i < inventory.mainInventory.size(); i++) {
         ItemStack st = (ItemStack)inventory.mainInventory.get(i);
         if (!st.isEmpty() && (bullet == null || st.getItem() == bullet)) {
            return i;
         }
      }

      if (!onlyCurrentType) {
         for (int ix = 0; ix < inventory.mainInventory.size(); ix++) {
            ItemStack st = (ItemStack)inventory.mainInventory.get(ix);
            if (!st.isEmpty() && st.getItem() instanceof ItemBullet) {
               return ix;
            }
         }
      }

      return -1;
   }

   @Nullable
   public static List<Integer> getFirstRocket(InventoryPlayer inventory, int countNeed) {
      for (int n = 0; n < 36; n++) {
         int count = 0;
         Item item = inventory.getStackInSlot(n).getItem();
         if (item instanceof ItemRocket) {
            List<Integer> list = new ArrayList<>();
            if (!list.contains(n)) {
               list.add(n);
            }

            for (int i = n; i < 36; i++) {
               if (inventory.getStackInSlot(i).getItem() == item) {
                  if (!list.contains(i)) {
                     list.add(i);
                  }

                  count += inventory.getStackInSlot(i).getCount();
               }
            }

            if (count >= countNeed) {
               return list;
            }
         }
      }

      return null;
   }
}
