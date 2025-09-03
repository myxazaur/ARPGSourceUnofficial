//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.network;

import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketNeedTileSendToServer extends Packet {
   public int x = 0;
   public int y = 0;
   public int z = 0;
   public boolean isMessage;

   public void writepos(BlockPos pos) {
      this.buf().writeInt(pos.getX());
      this.buf().writeInt(pos.getY());
      this.buf().writeInt(pos.getZ());
      this.isMessage = true;
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.x = buffer.readInt();
      this.y = buffer.readInt();
      this.z = buffer.readInt();
   }

   @SideOnly(Side.CLIENT)
   public static void send(BlockPos pos) {
      PacketNeedTileSendToServer packet = new PacketNeedTileSendToServer();
      packet.writepos(pos);
      PacketHandler.NETWORK.sendToServer(packet);
   }

   @Override
   public void server(EntityLivingBase player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player));
   }

   void processMessage(EntityLivingBase player) {
      try {
         TileEntity tile = player.world.getTileEntity(new BlockPos(this.x, this.y, this.z));
         if (tile != null && player instanceof EntityPlayerMP) {
            SPacketUpdateTileEntity spacketupdatetileentity = tile.getUpdatePacket();
            if (spacketupdatetileentity != null) {
               ((EntityPlayerMP)player).connection.sendPacket(spacketupdatetileentity);
            }
         }
      } catch (ConcurrentModificationException var4) {
         var4.printStackTrace();
      }
   }
}
