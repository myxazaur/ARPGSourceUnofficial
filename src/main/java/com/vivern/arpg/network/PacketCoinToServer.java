package com.vivern.arpg.network;

import com.vivern.arpg.entity.EntityCoin;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketCoinToServer extends Packet {
   public int entityid = 0;
   public boolean isMessage;

   public void writeints(int entityid) {
      this.buf().writeInt(entityid);
      this.isMessage = true;
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.entityid = buffer.readInt();
   }

   @Override
   public void server(EntityLivingBase player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player));
   }

   void processMessage(EntityLivingBase player) {
      try {
         Entity en = player.world.getEntityByID(this.entityid);
         if (en != null && en instanceof EntityCoin && player instanceof EntityPlayerMP) {
            EntityCoin coin = (EntityCoin)en;
            PacketCoinToClient packet = new PacketCoinToClient();
            packet.write(coin.store, this.entityid);
            PacketHandler.sendTo(packet, (EntityPlayerMP)player);
         }
      } catch (ConcurrentModificationException var5) {
         var5.printStackTrace();
      }
   }
}
