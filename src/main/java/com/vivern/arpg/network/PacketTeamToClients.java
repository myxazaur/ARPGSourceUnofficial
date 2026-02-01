package com.vivern.arpg.network;

import com.vivern.arpg.mobs.AbstractMob;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketTeamToClients extends Packet {
   String teamm;
   int id = 0;

   public void writeargs(String team, int id) {
      this.buf().writeInt(id);
      ByteBufUtils.writeUTF8String(this.buf(), team);
   }

   public void writeargs(int id, double... args) {
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.id = buffer.readInt();
      this.teamm = ByteBufUtils.readUTF8String(buffer);
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      Entity entity = player.world.getEntityByID(this.id);
      if (entity != null && entity instanceof AbstractMob) {
         AbstractMob entityi = (AbstractMob)entity;
         FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(entityi));
      }
   }

   void processMessage(AbstractMob entityi) {
      try {
         entityi.team = this.teamm;
      } catch (ConcurrentModificationException var3) {
         var3.printStackTrace();
      }
   }
}
