package com.vivern.arpg.network;

import com.vivern.arpg.main.Weapons;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketAnimationsToClients extends Packet {
   public int animationId = 0;
   public int entityId = 0;
   public EnumHand hand = EnumHand.MAIN_HAND;

   public void write(EnumHand hand, int animationId, int entityId) {
      this.buf().writeBoolean(hand == EnumHand.MAIN_HAND);
      this.buf().writeInt(animationId);
      this.buf().writeInt(entityId);
   }

   @Override
   public void fromBytes(ByteBuf buf) {
      this.hand = buf.readBoolean() ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
      this.animationId = buf.readInt();
      this.entityId = buf.readInt();
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      Entity entity = player.world.getEntityByID(this.entityId);
      if (entity != null && entity instanceof EntityPlayer) {
         Weapons.setPlayerAnimationOnClient((EntityPlayer)entity, this.animationId, this.hand);
      }
   }
}
