package com.vivern.arpg.network;

import com.vivern.arpg.entity.EntityMagicUI;
import com.vivern.arpg.main.IMagicUI;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketMUIOpenToServer extends Packet {
   public int x = 0;
   public int y = 0;
   public int z = 0;

   public void write(BlockPos pos) {
      this.buf().writeInt(pos.getX());
      this.buf().writeInt(pos.getY());
      this.buf().writeInt(pos.getZ());
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.x = buffer.readInt();
      this.y = buffer.readInt();
      this.z = buffer.readInt();
   }

   @SideOnly(Side.CLIENT)
   public static void send(BlockPos pos) {
      PacketMUIOpenToServer packet = new PacketMUIOpenToServer();
      packet.write(pos);
      PacketHandler.NETWORK.sendToServer(packet);
   }

   @Override
   public void server(EntityLivingBase player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player));
   }

   void processMessage(EntityLivingBase player) {
      try {
         if (player instanceof EntityPlayer) {
            BlockPos pos = new BlockPos(this.x, this.y, this.z);
            IMagicUI mui = EntityMagicUI.getMUIinPos(player.world, pos);
            if (mui != null) {
               mui.open(player.world, (EntityPlayer)player, pos, null);
            }
         }
      } catch (ConcurrentModificationException var4) {
         var4.printStackTrace();
      }
   }
}
