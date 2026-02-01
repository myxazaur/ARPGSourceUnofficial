package com.vivern.arpg.container;

import com.vivern.arpg.mobs.NPCMobsPack;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.network.PacketTraderClickToServer;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class GUIDialog extends GuiScreen {
   public EntityPlayer player;
   public NPCMobsPack.NpcTrader npc;

   public GUIDialog(EntityPlayer player, NPCMobsPack.NpcTrader npc) {
      this.player = player;
      this.npc = npc;
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      int mx = mouseX - (this.width - 212) / 2;
      int my = mouseY - (this.height - 256) / 2;
      PacketTraderClickToServer packet = new PacketTraderClickToServer();
      packet.writeints(this.npc.getEntityId(), mx, my, mouseButton, -1);
      PacketHandler.NETWORK.sendToServer(packet);
      if (Minecraft.getMinecraft().player != null) {
         this.npc.guiclick(Minecraft.getMinecraft().player, mx, my, mouseButton, -1);
      }
   }

   public boolean doesGuiPauseGame() {
      return false;
   }
}
