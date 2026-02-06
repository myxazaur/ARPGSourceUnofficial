package com.Vivern.Arpg.container;

import com.Vivern.Arpg.elements.ItemAccumulator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GUICharger extends GuiContainer {
   public static final ResourceLocation GUI_TEXTURES = new ResourceLocation("arpg:textures/gui_charger.png");
   private final InventoryPlayer playerInventory;
   private final IInventory tileinv;

   public GUICharger(InventoryPlayer playerInv, IInventory iInv) {
      super(new ContainerCharger(playerInv, iInv));
      this.playerInventory = playerInv;
      this.tileinv = iInv;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      super.drawScreen(mouseX, mouseY, partialTicks);
      this.renderHoveredToolTip(mouseX, mouseY);
      if (this.isPointInRegion(35, 25, 16, 34, mouseX, mouseY)) {
         this.drawHoveringText(this.tileinv.getField(0) + "/" + ItemAccumulator.LEAD_ACID_CAPACITY + " RF", mouseX, mouseY);
      }
   }

   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
      String s = this.tileinv.getDisplayName().getUnformattedText();
      this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
      this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
      this.fontRenderer.drawString(this.tileinv.getField(1) + "%", 116, 38, 3663171);
   }

   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      int i = (this.width - this.xSize) / 2;
      int j = (this.height - this.ySize) / 2;
      this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
      this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
      float rf = this.tileinv.getField(0);
      int rfmax = ItemAccumulator.LEAD_ACID_CAPACITY;
      int rfw = (int)(rf / rfmax * 34.0F);
      if (rf > 0.0F) {
         int elctX = 35;
         int elctY = 25;
         int elctTexX = 176;
         int elctTexY = 0;
         this.drawTexturedModalRect(i + elctX, j + elctY + 34 - rfw, elctTexX, elctTexY + 34 - rfw, 16, rfw);
      }
   }
}
