//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.network;

import com.Vivern.Arpg.mobs.NPCMobsPack;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketTradesToClient extends Packet {
   public NBTTagCompound compound = null;
   public int id = 0;
   public boolean isMessage;

   public void write(NBTTagCompound tag, int id) {
      ByteBufUtils.writeTag(this.buf(), tag);
      this.buf().writeInt(id);
      this.isMessage = true;
   }

   @Override
   public void fromBytes(ByteBuf buf) {
      this.compound = ByteBufUtils.readTag(buf);
      this.id = buf.readInt();
      this.buf = buf;
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player));
   }

   void processMessage(EntityPlayer player) {
      try {
         if (this.compound != null) {
            Entity entity = player.world.getEntityByID(this.id);
            if (entity != null && entity instanceof NPCMobsPack.NpcTrader) {
               NPCMobsPack.NpcTrader trader = (NPCMobsPack.NpcTrader)entity;
               trader.readFromNBT(this.compound);
            }
         }
      } catch (ConcurrentModificationException var4) {
         var4.printStackTrace();
      }
   }
}
