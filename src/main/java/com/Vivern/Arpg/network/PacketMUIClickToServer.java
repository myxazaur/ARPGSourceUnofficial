//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.network;

import com.Vivern.Arpg.entity.EntityMagicUI;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketMUIClickToServer extends Packet {
   public int id = 0;

   public void writeint(int id) {
      this.buf().writeInt(id);
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.id = buffer.readInt();
   }

   @SideOnly(Side.CLIENT)
   public static void send(int id) {
      PacketMUIClickToServer packet = new PacketMUIClickToServer();
      packet.writeint(id);
      PacketHandler.NETWORK.sendToServer(packet);
   }

   @Override
   public void server(EntityLivingBase player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player));
   }

   void processMessage(EntityLivingBase player) {
      try {
         Entity en = player.world.getEntityByID(this.id);
         if (en != null && en instanceof EntityMagicUI && player instanceof EntityPlayerMP && en.getDistanceSq(player) <= 64.0) {
            ((EntityMagicUI)en).onPressTick((EntityPlayer)player);
         }
      } catch (ConcurrentModificationException var3) {
         var3.printStackTrace();
      }
   }
}
