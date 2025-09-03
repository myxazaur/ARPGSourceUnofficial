//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package baubles.api;

import baubles.api.cap.BaublesCapabilities;
import baubles.api.cap.IBaublesItemHandler;
import baubles.api.inv.BaublesInventoryWrapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;

public class BaublesApi {
   public static IBaublesItemHandler getBaublesHandler(EntityPlayer player) {
      IBaublesItemHandler handler = (IBaublesItemHandler)player.getCapability(BaublesCapabilities.CAPABILITY_BAUBLES, null);
      handler.setPlayer(player);
      return handler;
   }

   @Deprecated
   public static IInventory getBaubles(EntityPlayer player) {
      IBaublesItemHandler handler = (IBaublesItemHandler)player.getCapability(BaublesCapabilities.CAPABILITY_BAUBLES, null);
      handler.setPlayer(player);
      return new BaublesInventoryWrapper(handler, player);
   }

   public static int isBaubleEquipped(EntityPlayer player, Item bauble) {
      IBaublesItemHandler handler = getBaublesHandler(player);

      for (int a = 0; a < handler.getSlots(); a++) {
         if (!handler.getStackInSlot(a).isEmpty() && handler.getStackInSlot(a).getItem() == bauble) {
            return a;
         }
      }

      return -1;
   }
}
