package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.network.PacketTileEntityToClients;
import com.Vivern.Arpg.network.PacketTileEntityToServer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;

public interface ITileEntitySynchronize {
   void onClient(double... var1);

   default void onServer(double... args) {
   }

   default void onServer(EntityLivingBase playerSends, double... args) {
      this.onServer(args);
   }

   static void sendSynchronize(TileEntity thisentity, double distance, double... args) {
      if (!thisentity.getWorld().isRemote) {
         PacketTileEntityToClients packet = new PacketTileEntityToClients();
         packet.writeargs(
            thisentity.getPos().getX(), thisentity.getPos().getY(), thisentity.getPos().getZ(), args
         );
         PacketHandler.sendToAllAround(
            packet,
            thisentity.getWorld(),
            thisentity.getPos().getX(),
            thisentity.getPos().getY(),
            thisentity.getPos().getZ(),
            distance
         );
      }
   }

   static void sendDataToServer(int posx, int posy, int posz, double... args) {
      PacketTileEntityToServer packet = new PacketTileEntityToServer();
      packet.writeargs(posx, posy, posz, args);
      PacketHandler.NETWORK.sendToServer(packet);
   }
}
