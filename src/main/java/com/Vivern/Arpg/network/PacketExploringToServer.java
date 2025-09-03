//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.network;

import com.Vivern.Arpg.recipes.ExploringField;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketExploringToServer extends Packet {
   public int nbtKey;
   public int nbtBitshift;

   public void writeints(int nbtKey, int nbtBitshift) {
      this.buf().writeInt(nbtKey);
      this.buf().writeInt(nbtBitshift);
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.nbtKey = buffer.readInt();
      this.nbtBitshift = buffer.readInt();
   }

   @Override
   public void server(EntityLivingBase player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player));
   }

   void processMessage(EntityLivingBase player) {
      try {
         if (this.nbtBitshift >= 0 && this.nbtBitshift < 32 && player instanceof EntityPlayerMP) {
            ExploringField.setExploringNow(ExploringField.getExploringTagCompound((EntityPlayerMP)player), this.nbtKey, this.nbtBitshift);
         }
      } catch (ConcurrentModificationException var3) {
         var3.printStackTrace();
      }
   }
}
