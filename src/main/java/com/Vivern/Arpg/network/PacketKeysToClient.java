//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.network;

import com.Vivern.Arpg.main.PropertiesRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketKeysToClient extends Packet {
   public int k = 0;
   public boolean isMessage;

   public void writeint(int keys) {
      this.buf().writeInt(keys);
      this.isMessage = true;
   }

   @Override
   public void fromBytes(ByteBuf buf) {
      this.k = buf.readInt();
      System.out.println("fromBytes buff:    " + buf);
      System.out.println("fromBytes k:    " + this.k);
      this.buf = buf;
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      int keys = this.k;
      if (keys != 0) {
         player.getDataManager().set(PropertiesRegistry.KEYS_PRESSED, keys);
         System.out.println(keys);
      }
   }
}
