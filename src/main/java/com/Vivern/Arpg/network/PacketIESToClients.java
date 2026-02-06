package com.Vivern.Arpg.network;

import com.Vivern.Arpg.entity.IEntitySynchronize;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketIESToClients extends Packet {
   double[] args;
   int id = 0;

   public void writeargs(double x, double y, double z, double a, double b, double c, int id) {
      this.writeargs(id, x, y, z, a, b, c);
   }

   public void writeargs(int id, double... args) {
      this.buf().writeInt(id);

      for (int i = 0; i < args.length; i++) {
         this.buf().writeDouble(args[i]);
      }
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      int count = (buffer.capacity() - 4) / 8;
      this.id = buffer.readInt();
      this.args = new double[Math.max(count, 1)];

      for (int i = 0; i < count; i++) {
         this.args[i] = buffer.readDouble();
      }
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      Entity entity = player.world.getEntityByID(this.id);
      if (entity != null && entity instanceof IEntitySynchronize) {
         IEntitySynchronize entityi = (IEntitySynchronize)entity;
         FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(entityi));
      }
   }

   void processMessage(IEntitySynchronize entityi) {
      try {
         entityi.onClient(this.args);
      } catch (ConcurrentModificationException var3) {
         var3.printStackTrace();
      }
   }
}
