package com.Vivern.Arpg.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.EncoderException;
import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Packet implements IMessage, IMessageHandler<Packet, Packet> {
   public ByteBuf buf;

   public Packet onMessage(Packet sp, MessageContext ctx) {
      if (ctx.side.isServer()) {
         sp.server(ctx.getServerHandler().player, sp, ctx);
      } else {
         sp.client(this.clientPlayer(), sp, ctx);
      }

      return null;
   }

   protected ByteBuf buf() {
      return this.buf != null ? this.buf : (this.buf = Unpooled.buffer());
   }

   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
   }

   public void server(EntityLivingBase player, Packet sp, MessageContext ctx) {
   }

   public void fromBytes(ByteBuf buf) {
      this.buf = buf;
   }

   public void toBytes(ByteBuf buf) {
      if (buf != null) {
         buf.writeBytes(this.buf);
      }
   }

   @SideOnly(Side.CLIENT)
   public EntityPlayer clientPlayer() {
      return Minecraft.getMinecraft().player;
   }

   public static ByteBuf writeCompoundTag(@Nullable NBTTagCompound nbt, ByteBuf buffer) {
      if (nbt == null) {
         buffer.writeByte(0);
      } else {
         try {
            CompressedStreamTools.write(nbt, new ByteBufOutputStream(buffer));
         } catch (IOException var3) {
            throw new EncoderException(var3);
         }
      }

      return buffer;
   }

   @Nullable
   public static NBTTagCompound readCompoundTag(ByteBuf buffer) throws IOException {
      int i = buffer.readerIndex();
      byte b0 = buffer.readByte();
      if (b0 == 0) {
         return null;
      } else {
         buffer.readerIndex(i);

         try {
            return CompressedStreamTools.read(new ByteBufInputStream(buffer), new NBTSizeTracker(2097152L));
         } catch (IOException var4) {
            throw new EncoderException(var4);
         }
      }
   }
}
