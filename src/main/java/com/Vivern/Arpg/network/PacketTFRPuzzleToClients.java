//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.network;

import com.Vivern.Arpg.container.GUIResearchTable;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketTFRPuzzleToClients extends Packet {
   public NBTTagCompound tagCompound;

   public void write(NBTTagCompound tag) {
      ByteBufUtils.writeTag(this.buf(), tag);
   }

   @Override
   public void fromBytes(ByteBuf buf) {
      this.tagCompound = ByteBufUtils.readTag(buf);
      this.buf = buf;
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(this.tagCompound));
   }

   void processMessage(NBTTagCompound tagCompound) {
      try {
         GUIResearchTable.setPuzzleFromTag(tagCompound);
      } catch (ConcurrentModificationException var3) {
         var3.printStackTrace();
      } catch (NullPointerException var4) {
         var4.printStackTrace();
      }
   }
}
