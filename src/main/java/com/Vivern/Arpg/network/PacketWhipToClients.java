//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.network;

import com.Vivern.Arpg.elements.FireWhip;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketWhipToClients extends Packet {
   double x = 0.0;
   double y = 0.0;
   double z = 0.0;
   double a = 0.0;
   double b = 0.0;
   double c = 0.0;
   double d1 = 0.0;
   double d2 = 0.0;
   double d3 = 0.0;
   double v1 = 0.0;
   double v2 = 0.0;
   double v3 = 0.0;
   int id = 0;
   String name = "";

   public void writeargs(double x, double y, double z, double a, double b, double c, double d1, double d2, double d3, double v1, double v2, double v3, int id) {
      this.buf().writeDouble(x);
      this.buf().writeDouble(y);
      this.buf().writeDouble(z);
      this.buf().writeDouble(a);
      this.buf().writeDouble(b);
      this.buf().writeDouble(c);
      this.buf().writeDouble(d1);
      this.buf().writeDouble(d2);
      this.buf().writeDouble(d3);
      this.buf().writeDouble(v1);
      this.buf().writeDouble(v2);
      this.buf().writeDouble(v3);
      this.buf().writeInt(id);
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.x = buffer.readDouble();
      this.y = buffer.readDouble();
      this.z = buffer.readDouble();
      this.a = buffer.readDouble();
      this.b = buffer.readDouble();
      this.c = buffer.readDouble();
      this.d1 = buffer.readDouble();
      this.d2 = buffer.readDouble();
      this.d3 = buffer.readDouble();
      this.v1 = buffer.readDouble();
      this.v2 = buffer.readDouble();
      this.v3 = buffer.readDouble();
      this.id = buffer.readInt();
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      Item item = Item.getItemById(this.id);
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(item));
   }

   void processMessage(Item item) {
      try {
         ((FireWhip)item)
            .effectt(
               this.clientPlayer(),
               this.clientPlayer().world,
               this.x,
               this.y,
               this.z,
               this.a,
               this.b,
               this.c,
               this.d1,
               this.d2,
               this.d3,
               this.v1,
               this.v2,
               this.v3
            );
      } catch (ConcurrentModificationException var3) {
         var3.printStackTrace();
      }
   }
}
