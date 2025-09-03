//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.network.PacketIESToClients;
import net.minecraft.entity.Entity;

public interface IEntitySynchronize {
   default void onClient(double x, double y, double z, double a, double b, double c) {
   }

   default void onClient(double... args) {
      this.onClient(
         args[0],
         args.length > 1 ? args[1] : 0.0,
         args.length > 2 ? args[2] : 0.0,
         args.length > 3 ? args[3] : 0.0,
         args.length > 4 ? args[4] : 0.0,
         args.length > 5 ? args[5] : 0.0
      );
   }

   static void sendSynchronize(Entity thisentity, double distance, double... args) {
      if (!thisentity.world.isRemote) {
         PacketIESToClients packet = new PacketIESToClients();
         packet.writeargs(thisentity.getEntityId(), args);
         PacketHandler.sendToAllAround(packet, thisentity.world, thisentity.posX, thisentity.posY, thisentity.posZ, distance);
      }
   }
}
