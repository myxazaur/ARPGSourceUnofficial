//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.network;

import com.Vivern.Arpg.elements.IWeapon;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketItemBoomToClient extends Packet {
   public int param = 0;
   public int id = 0;

   public void write(int itemId, int parameter) {
      this.buf().writeInt(parameter);
      this.buf().writeInt(itemId);
   }

   @Override
   public void fromBytes(ByteBuf buf) {
      this.param = buf.readInt();
      this.id = buf.readInt();
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      Item item = Item.getItemById(this.id);
      if (item != null && item instanceof IWeapon) {
         ((IWeapon)item).bom(this.param);
      }
   }
}
