package com.Vivern.Arpg.container;

import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.network.PacketTileClickToServer;
import com.Vivern.Arpg.tileentity.TileCombinationLock;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GUICombinationLock extends GuiScreen {
   public static final ResourceLocation GUI_TEXTURES = new ResourceLocation("arpg:textures/gui_combination_lock.png");
   public static final ResourceLocation ELEMENTS_TEXTURES = new ResourceLocation("arpg:textures/gui_elements.png");
   public TileCombinationLock tile;

   public GUICombinationLock(TileCombinationLock tile) {
      this.tile = tile;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      super.drawScreen(mouseX, mouseY, partialTicks);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      int i = (this.width - 256) / 2;
      int j = (this.height - 212) / 2;
      this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
      this.drawTexturedModalRect(i, j, 0, 0, 256, 212);
      this.mc.getTextureManager().bindTexture(ELEMENTS_TEXTURES);
      GL11.glEnable(3042);
      if (this.tile.question != null) {
         this.drawTexturedModalRect(i + 112, j + 6, this.tile.question.texX, this.tile.question.texY, 32, 32);
      }

      if (this.tile.selected1 != null) {
         this.drawTexturedModalRect(i + 80, j + 52, this.tile.selected1.texX, this.tile.selected1.texY, 32, 32);
      }

      if (this.tile.selected2 != null) {
         this.drawTexturedModalRect(i + 144, j + 52, this.tile.selected2.texX, this.tile.selected2.texY, 32, 32);
      }

      if (this.tile.result != null) {
         this.drawTexturedModalRect(i + 112, j + 94, this.tile.result.texX, this.tile.result.texY, 32, 32);
      }

      for (int ii = 0; ii < 7; ii++) {
         int framenumber = ii + this.tile.firstFrame;
         if (framenumber < this.tile.unlocked.size()) {
            TileCombinationLock.SeaElement el = this.tile.unlocked.get(framenumber);
            this.drawTexturedModalRect(i + 16 + 32 * ii, j + 132, el.texX, el.texY, 32, 32);
         }
      }

      GL11.glDisable(3042);
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      int i = (this.width - 256) / 2;
      int j = (this.height - 212) / 2;
      PacketTileClickToServer packet = new PacketTileClickToServer();
      packet.writeints(
         this.tile.getPos().getX(),
         this.tile.getPos().getY(),
         this.tile.getPos().getZ(),
         mouseX - i,
         mouseY - j,
         mouseButton
      );
      PacketHandler.NETWORK.sendToServer(packet);
   }

   public boolean doesGuiPauseGame() {
      return false;
   }
}
