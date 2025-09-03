//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.network;

import com.Vivern.Arpg.mobs.NPCMobsPack;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketTraderClickToServer extends Packet {
   public int id = 0;
   public int mx = 0;
   public int my = 0;
   public int key = 0;
   public boolean isMessage;
   public int page = 0;

   public void writeints(int id, int mouseX, int mouseY, int mouseButton, int page) {
      this.buf().writeInt(id);
      this.buf().writeInt(mouseX);
      this.buf().writeInt(mouseY);
      this.buf().writeInt(mouseButton);
      this.buf().writeInt(page);
      this.isMessage = true;
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.id = buffer.readInt();
      this.mx = buffer.readInt();
      this.my = buffer.readInt();
      this.key = buffer.readInt();
      this.page = buffer.readInt();
   }

   @Override
   public void server(EntityLivingBase player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player));
   }

   void processMessage(EntityLivingBase player) {
      try {
         Entity en = player.world.getEntityByID(this.id);
         if (en != null && en instanceof NPCMobsPack.NpcTrader && player instanceof EntityPlayer) {
            NPCMobsPack.NpcTrader trader = (NPCMobsPack.NpcTrader)en;
            trader.guiclick((EntityPlayer)player, this.mx, this.my, this.key, this.page);
         }
      } catch (ConcurrentModificationException var4) {
         var4.printStackTrace();
      }
   }
}
