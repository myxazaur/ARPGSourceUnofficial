package com.vivern.arpg.network;

import com.vivern.arpg.renders.ManaBar;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSmallSomethingToClients extends Packet {
   double x = 0.0;
   int id = 0;

   public void writeargs(int id, double x) {
      this.buf().writeDouble(x);
      this.buf().writeInt(id);
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.x = buffer.readDouble();
      this.id = buffer.readInt();
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player.world));
   }

   void processMessage(World world) {
      try {
         if (this.id == 1) {
            ManaBar.energy_bars_enable = true;
         }

         if (this.id == 2) {
            ManaBar.leadershipUsed = (int)this.x;
         }
      } catch (ConcurrentModificationException var3) {
         var3.printStackTrace();
      }
   }
}
