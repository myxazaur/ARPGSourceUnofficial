//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.container;

import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.network.PacketTileClickToServer;
import java.io.IOException;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GUIAssemblyTable extends GuiContainer {
   public static final ResourceLocation GUI_TEXTURES = new ResourceLocation("arpg:textures/gui_assembly_table.png");
   private final InventoryPlayer playerInventory;
   private final IInventory tileinv;

   public GUIAssemblyTable(InventoryPlayer playerInv, IInventory iInv) {
      super(new ContainerAssemblyTable(playerInv, iInv));
      this.playerInventory = playerInv;
      this.tileinv = iInv;
      this.xSize = 192;
      this.ySize = 233;
   }

   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      if (Character.isDigit(typedChar) || keyCode == 14) {
         PacketTileClickToServer packet = new PacketTileClickToServer();
         packet.writeints(
            this.tileinv.getField(10),
            this.tileinv.getField(11),
            this.tileinv.getField(12),
            keyCode == 14 ? 0 : Integer.parseInt(typedChar + ""),
            keyCode == 14 ? 1 : 0,
            10
         );
         PacketHandler.NETWORK.sendToServer(packet);
      } else if (typedChar == 'j') {
         PacketTileClickToServer packet = new PacketTileClickToServer();
         packet.writeints(this.tileinv.getField(10), this.tileinv.getField(11), this.tileinv.getField(12), 0, 2, 10);
         PacketHandler.NETWORK.sendToServer(packet);
      } else {
         super.keyTyped(typedChar, keyCode);
      }
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      super.drawScreen(mouseX, mouseY, partialTicks);
      this.renderHoveredToolTip(mouseX, mouseY);
      if (this.isPointInRegion(22, 93, 16, 34, mouseX, mouseY)) {
         this.drawHoveringText(this.tileinv.getField(0) + "/10000 RF", mouseX, mouseY);
      }

      for (int sl = 1; sl < 6; sl++) {
         if (this.isPointInRegion(27 * sl, 38, 14, 14, mouseX, mouseY)) {
            int idSlot = this.tileinv.getField(sl + 3);
            if (idSlot == 1) {
               this.drawHoveringText("Turning", mouseX, mouseY);
            } else if (idSlot == 2) {
               this.drawHoveringText("Pressing", mouseX, mouseY);
            } else if (idSlot == 3) {
               this.drawHoveringText("Weld", mouseX, mouseY);
            } else if (idSlot == 4) {
               this.drawHoveringText("Plasma Spray", mouseX, mouseY);
            } else if (idSlot == 5) {
               this.drawHoveringText("Molecular Print", mouseX, mouseY);
            }
         }
      }
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      super.mouseClicked(mouseX, mouseY, mouseButton);
      int i = (this.width - this.xSize) / 2;
      int j = (this.height - this.ySize) / 2;
      PacketTileClickToServer packet = new PacketTileClickToServer();
      packet.writeints(this.tileinv.getField(10), this.tileinv.getField(11), this.tileinv.getField(12), mouseX - i, mouseY - j, mouseButton);
      PacketHandler.NETWORK.sendToServer(packet);
   }

   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
      String s = this.tileinv.getDisplayName().getUnformattedText();
      this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
      this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
   }

   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      int i = (this.width - this.xSize) / 2;
      int j = (this.height - this.ySize) / 2;
      this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
      this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
      int progr = this.tileinv.getField(2);
      int progrmax = this.tileinv.getField(1);
      int slotsUsed = this.tileinv.getField(9);
      if (progr > 0) {
         int w = (int)((float)progr / progrmax * 24.0F);
         this.drawTexturedModalRect(i + 114, j + 92, 192, 14, w, 17);
         if ((slotsUsed & 1) != 0) {
            int w2 = (int)((float)progr / progrmax * 35.0F);
            this.drawTexturedModalRect(i + 33, j + 54, 192, 77, 16, w2);
         }

         if ((slotsUsed & 2) != 0) {
            int w2 = (int)((float)progr / progrmax * 18.0F);
            this.drawTexturedModalRect(i + 60, j + 54, 208, 77, 7, w2);
         }

         if ((slotsUsed & 4) != 0) {
            int w2 = (int)((float)progr / progrmax * 18.0F);
            this.drawTexturedModalRect(i + 87, j + 54, 215, 77, 2, w2);
         }

         if ((slotsUsed & 8) != 0) {
            int w2 = (int)((float)progr / progrmax * 23.0F);
            this.drawTexturedModalRect(i + 107, j + 54, 224, 77, 9, w2);
         }

         if ((slotsUsed & 16) != 0) {
            int w2 = (int)((float)progr / progrmax * 44.0F);
            this.drawTexturedModalRect(i + 107, j + 54, 192, 112, 36, w2);
         }
      }

      float rf = this.tileinv.getField(0);
      int rfmax = this.tileinv.getField(13);
      int rfw = (int)(rf / rfmax * 34.0F);
      if (rf > 0.0F) {
         int elctX = 22;
         int elctY = 93;
         int elctTexX = 192;
         int elctTexY = 43;
         this.drawTexturedModalRect(i + elctX, j + elctY + 34 - rfw, elctTexX, elctTexY + 34 - rfw, 16, rfw);
      }

      for (int sl = 0; sl < 5; sl++) {
         int idSlot = this.tileinv.getField(sl + 4);
         if (idSlot > 0) {
            this.drawTexturedModalRect(i + 28 + sl * 27, j + 39, 180 + idSlot * 12, 31, 12, 12);
         }
      }
   }
}
