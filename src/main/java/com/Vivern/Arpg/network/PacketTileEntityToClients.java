package com.Vivern.Arpg.network;

import com.Vivern.Arpg.tileentity.ITileEntitySynchronize;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketTileEntityToClients extends Packet {
   double[] args;
   int posx = 0;
   int posy = 0;
   int posz = 0;

   public void writeargs(int posx, int posy, int posz, double... args) {
      this.buf().writeInt(posx);
      this.buf().writeInt(posy);
      this.buf().writeInt(posz);

      for (int i = 0; i < args.length; i++) {
         this.buf().writeDouble(args[i]);
      }
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      int count = (buffer.capacity() - 12) / 8;
      this.posx = buffer.readInt();
      this.posy = buffer.readInt();
      this.posz = buffer.readInt();
      this.args = new double[Math.max(count, 1)];

      for (int i = 0; i < count; i++) {
         this.args[i] = buffer.readDouble();
      }
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      TileEntity tileEntity = player.world.getTileEntity(new BlockPos(this.posx, this.posy, this.posz));
      if (tileEntity != null && tileEntity instanceof ITileEntitySynchronize) {
         ITileEntitySynchronize entityi = (ITileEntitySynchronize)tileEntity;
         FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(entityi));
      }
   }

   void processMessage(ITileEntitySynchronize entityi) {
      try {
         entityi.onClient(this.args);
      } catch (ConcurrentModificationException var3) {
         var3.printStackTrace();
      }
   }
}
