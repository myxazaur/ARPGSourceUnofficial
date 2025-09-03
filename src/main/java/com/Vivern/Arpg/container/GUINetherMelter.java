//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.container;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GUINetherMelter extends GuiContainer {
   public static final ResourceLocation FURNACE_GUI_TEXTURES = new ResourceLocation("arpg:textures/gui_nether_melter.png");
   private final InventoryPlayer playerInventory;
   private final IInventory tileFurnace;

   public GUINetherMelter(InventoryPlayer playerInv, IInventory furnaceInv) {
      super(new ContainerNetherMelter(playerInv, furnaceInv));
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
      this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 100, this.ySize - 96 + 2, 4210752);
   }

   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(FURNACE_GUI_TEXTURES);
      int i = (this.width - this.xSize) / 2;
      int j = (this.height - this.ySize) / 2;
      this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
      int progr = this.tileFurnace.getField(3);
      int progrmax = this.tileFurnace.getField(6);
      int w = (int)((float)progr / progrmax * 41.0F);
      if (progr > 0) {
         this.drawTexturedModalRect(i + 104, j + 19, 176, 14, w, 16);
      }

      if (this.tileFurnace.getField(5) == 1) {
         this.drawTexturedModalRect(i + 45, j + 37, 176, 0, 14, 14);
      }

      float mana = this.tileFurnace.getField(0) / 10.0F;
      int manamax = this.tileFurnace.getField(1);
      int mw = (int)(mana / manamax * 74.0F);
      if (mana > 0.0F) {
         this.drawTexturedModalRect(i + 15, j + 52, 176, 30, mw, 8);
      }
   }
}
