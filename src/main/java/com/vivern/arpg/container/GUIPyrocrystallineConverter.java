package com.vivern.arpg.container;

import com.vivern.arpg.main.AnimationTimer;
import com.vivern.arpg.renders.RenderFluidHelper;
import com.vivern.arpg.tileentity.TilePyrocrystallineConverter;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class GUIPyrocrystallineConverter extends GuiContainer {
   public static final ResourceLocation GUI_TEXTURES = new ResourceLocation("arpg:textures/gui_pyrocrystalline_converter.png");
   private final InventoryPlayer playerInventory;
   private final IInventory tileinv;

   public GUIPyrocrystallineConverter(InventoryPlayer playerInv, IInventory iInv) {
      super(new ContainerPyrocrystalline(playerInv, iInv));
      this.playerInventory = playerInv;
      this.tileinv = iInv;
      this.xSize = 176;
      this.ySize = 203;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      super.drawScreen(mouseX, mouseY, partialTicks);
      this.renderHoveredToolTip(mouseX, mouseY);
      if (this.tileinv instanceof TilePyrocrystallineConverter) {
         TilePyrocrystallineConverter alchem = (TilePyrocrystallineConverter)this.tileinv;
         int max = alchem.fluidMax;
         if (this.isPointInRegion(152, 18, 16, 60, mouseX, mouseY)) {
            FluidStack f1 = alchem.tank1.getFluid();
            if (f1 != null && f1.getFluid() != null) {
               this.drawHoveringText(f1.amount + " / " + max + " mB " + f1.getFluid().getLocalizedName(f1), mouseX, mouseY);
            }
         } else if (this.isPointInRegion(72, 70, 32, 32, mouseX, mouseY)) {
            if (!alchem.melt.isEmpty()) {
               this.drawHoveringText(alchem.meltCount + " Pyrocrystalline Melt: " + alchem.melt, mouseX, mouseY);
            } else {
               this.drawHoveringText("Empty", mouseX, mouseY);
            }
         } else if (this.isPointInRegion(9, 13, 8, 74, mouseX, mouseY)) {
            this.drawHoveringText("Mana: " + alchem.manaStored, mouseX, mouseY);
         } else if (this.isPointInRegion(21, 13, 8, 74, mouseX, mouseY)) {
            this.drawHoveringText("Geomantic Energy: " + alchem.geomanticEnergy, mouseX, mouseY);
         } else if (this.isPointInRegion(116, 78, 16, 16, mouseX, mouseY)) {
            this.drawHoveringText("Progress: " + alchem.progress, mouseX, mouseY);
         }
      }
   }

   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
      String s = this.tileinv.getDisplayName().getUnformattedText();
      this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 5, 4210752);
      this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
   }

   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      int i = (this.width - this.xSize) / 2;
      int j = (this.height - this.ySize) / 2;
      this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
      this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
      float mana = this.tileinv.getField(0) / 10.0F;
      int manamax = this.tileinv.getField(1);
      int mw = (int)(mana / manamax * 74.0F);
      if (mana > 0.0F) {
         this.drawTexturedModalRect(i + 9, j + 87 - mw, 176, 74 - mw, 8, mw);
      }

      float en = this.tileinv.getField(5) / 10.0F;
      int enmax = this.tileinv.getField(6);
      int enmw = (int)(en / enmax * 74.0F);
      if (en > 0.0F) {
         this.drawTexturedModalRect(i + 21, j + 87 - enmw, 184, 74 - enmw, 8, enmw);
      }

      int frame = AnimationTimer.normaltick / 2 % 16;
      int xoff;
      if (frame < 8) {
         xoff = 224;
      } else {
         xoff = 240;
         frame -= 8;
      }

      frame *= 16;
      this.drawTexturedModalRect(i + 72, j + 70, xoff, frame, 16, 16);
      this.drawTexturedModalRect(i + 88, j + 70, xoff, frame, 16, 16);
      this.drawTexturedModalRect(i + 72, j + 86, xoff, frame, 16, 16);
      this.drawTexturedModalRect(i + 88, j + 86, xoff, frame, 16, 16);
      if (this.tileinv instanceof TilePyrocrystallineConverter) {
         TilePyrocrystallineConverter alchem = (TilePyrocrystallineConverter)this.tileinv;
         FluidStack f1 = alchem.tank1.getFluid();
         if (f1 != null) {
            int wi = 16;
            int hmin = 78;
            int xPoss = 152;
            this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            int interpamount = Math.round((float)f1.amount / alchem.fluidMax * 60.0F);
            int displace = hmin - interpamount;
            int polygonheight = hmin - interpamount;
            if (interpamount < 16) {
               polygonheight = hmin - displace;
            }

            if (interpamount >= 16) {
               RenderFluidHelper.drawTexturedFluidRect(i + xPoss, j + (hmin - 16), wi, 16, 1.0F, f1.getFluid(), f1, 0.0);
               polygonheight = hmin - 16 - displace;
            }

            if (interpamount >= 32) {
               RenderFluidHelper.drawTexturedFluidRect(i + xPoss, j + (hmin - 32), wi, 16, 1.0F, f1.getFluid(), f1, 0.0);
               polygonheight = hmin - 32 - displace;
            }

            if (interpamount >= 48) {
               RenderFluidHelper.drawTexturedFluidRect(i + xPoss, j + (hmin - 48), wi, 16, 1.0F, f1.getFluid(), f1, 0.0);
               polygonheight = hmin - 48 - displace;
            }

            RenderFluidHelper.drawTexturedFluidRect(i + xPoss, j + displace, wi, polygonheight, polygonheight / 16.0F, f1.getFluid(), f1, 0.0);
         }
      }
   }
}
