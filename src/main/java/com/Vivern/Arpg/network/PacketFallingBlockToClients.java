package com.Vivern.Arpg.network;

import com.Vivern.Arpg.entity.AdvancedFallingBlock;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketFallingBlockToClients extends Packet {
   public String block = "";
   public int entityId = 0;
   public int blockMeta = 0;

   public void write(String block, int entityId, int blockMeta) {
      ByteBufUtils.writeUTF8String(this.buf(), block);
      this.buf().writeInt(entityId);
      this.buf().writeInt(blockMeta);
   }

   @Override
   public void fromBytes(ByteBuf buf) {
      this.block = ByteBufUtils.readUTF8String(buf);
      this.entityId = buf.readInt();
      this.blockMeta = buf.readInt();
      this.buf = buf;
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      if (!this.block.isEmpty()) {
         Entity entity = player.world.getEntityByID(this.entityId);
         if (entity != null && entity instanceof AdvancedFallingBlock) {
            AdvancedFallingBlock fallingb = (AdvancedFallingBlock)entity;
            fallingb.fallTile = Block.getBlockFromName(this.block).getStateFromMeta(this.blockMeta);
         }
      }
   }
}
