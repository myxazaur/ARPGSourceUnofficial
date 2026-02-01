package com.vivern.arpg.network;

import com.vivern.arpg.main.EntityInfluence;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketInfluenceToClients extends Packet {
   int influenceid = 0;
   int entityid = 0;

   public void writeargs(int influenceid, int entityid) {
      this.buf().writeInt(influenceid);
      this.buf().writeInt(entityid);
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.influenceid = buffer.readInt();
      this.entityid = buffer.readInt();
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player.world));
   }

   void processMessage(World world) {
      try {
         Entity entity = world.getEntityByID(this.entityid);
         if (entity != null) {
            EntityInfluence.addEntityInfluence(entity, EntityInfluence.influenceById.get(this.influenceid), 0.0);
         }
      } catch (ConcurrentModificationException var3) {
         var3.printStackTrace();
      }
   }
}
