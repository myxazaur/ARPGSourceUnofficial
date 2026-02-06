package com.Vivern.Arpg.network;

import com.Vivern.Arpg.elements.IWeapon;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketWeaponStateToClients extends Packet {
   byte state;
   int entityId;
   int slot;

   public void writeargs(byte state, int entityId, int slot) {
      this.buf().writeByte(state);
      this.buf().writeInt(entityId);
      this.buf().writeInt(slot);
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.state = buffer.readByte();
      this.entityId = buffer.readInt();
      this.slot = buffer.readInt();
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      Entity entity = player.world.getEntityByID(this.entityId);
      if (entity != null && entity instanceof EntityPlayer) {
         if (this.slot < 0) {
            this.slot = 0;
         }

         EntityPlayer playerActivates = (EntityPlayer)entity;
         ItemStack itemStack = playerActivates.inventory.getStackInSlot(this.slot);
         if (!itemStack.isEmpty() && itemStack.getItem() instanceof IWeapon) {
            ((IWeapon)itemStack.getItem()).onStateReceived(playerActivates, itemStack, this.state, this.slot);
         }
      }
   }
}
