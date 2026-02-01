package com.vivern.arpg.network;

import com.vivern.arpg.entity.EntityCoin;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketCoinToClient extends Packet {
   public int amount = 0;
   public int entityId = 0;
   public boolean isMessage;

   public void write(int amount, int entityId) {
      this.buf().writeInt(amount);
      this.buf().writeInt(entityId);
      this.isMessage = true;
   }

   @Override
   public void fromBytes(ByteBuf buf) {
      this.amount = buf.readInt();
      this.entityId = buf.readInt();
      this.buf = buf;
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      if (this.amount != 0) {
         Entity entity = player.world.getEntityByID(this.entityId);
         if (entity != null && entity instanceof EntityCoin) {
            EntityCoin coin = (EntityCoin)entity;
            coin.store = this.amount;
         }
      }
   }
}
