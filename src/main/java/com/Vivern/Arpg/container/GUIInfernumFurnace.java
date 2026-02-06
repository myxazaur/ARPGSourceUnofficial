package com.Vivern.Arpg.container;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GUIInfernumFurnace extends GuiContainer {
   public static final ResourceLocation FURNACE_GUI_TEXTURES = new ResourceLocation("arpg:textures/gui_infernum_furnace.png");
   private final InventoryPlayer playerInventory;
   private final IInventory tileFurnace;

   public GUIInfernumFurnace(InventoryPlayer playerInv, IInventory furnaceInv) {
      super(new ContainerInfernumFurnace(playerInv, furnaceInv));
      this.playerInventory = playerInv;
      this.tileFurnace = furnaceInv;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      super.drawScreen(mouseX, mouseY, partialTicks);
      this.renderHoveredToolTip(mouseX, mouseY);
   }

   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
      String s = this.tileFurnace.getDisplayName().getUnformattedText();
      this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
      this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
   }

   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(FURNACE_GUI_TEXTURES);
      int i = (this.width - this.xSize) / 2;
      int j = (this.height - this.ySize) / 2;
      this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
      int progr = this.tileFurnace.getField(3);
      int progrmax = this.tileFurnace.getField(5);
      int w = (int)((float)progr / progrmax * 24.0F);
      if (progr > 0) {
         this.drawTexturedModalRect(i + 79, j + 34, 176, 14, w, 17);
         this.drawTexturedModalRect(i + 57, j + 55, 176, 0, 14, 14);
      }
   }
}
