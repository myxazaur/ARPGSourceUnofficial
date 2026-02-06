package com.Vivern.Arpg.network;

import com.Vivern.Arpg.dimensions.generationutils.AbstractWorldProvider;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketWorldEventToClients extends Packet {
   byte index;
   byte start;

   public void writeargs(byte index, byte start) {
      this.buf().writeByte(index);
      this.buf().writeByte(start);
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.index = buffer.readByte();
      this.start = buffer.readByte();
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player));
   }

   void processMessage(EntityPlayer player) {
      try {
         if (player.world.provider instanceof AbstractWorldProvider) {
            AbstractWorldProvider worldProvider = (AbstractWorldProvider)player.world.provider;
            if (worldProvider.getWorldEventsHandler() != null) {
               worldProvider.getWorldEventsHandler().startOrStopEvent(this.index, this.start);
            }
         }
      } catch (ConcurrentModificationException var3) {
         var3.printStackTrace();
      }
   }
}
