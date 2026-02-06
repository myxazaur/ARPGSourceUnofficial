package com.Vivern.Arpg.container;

import com.Vivern.Arpg.main.FluidsRegister;
import com.Vivern.Arpg.network.PacketNeedTileSendToServer;
import com.Vivern.Arpg.renders.RenderFluidHelper;
import com.Vivern.Arpg.tileentity.TileElectromagnet;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class GUIElectromagnet extends GuiScreen {
   public static final ResourceLocation GUI_TEXTURES = new ResourceLocation("arpg:textures/gui_electromagnet.png");
   public TileElectromagnet tile;
   public int guiLeft;
   public int guiTop;
   protected int xSize = 176;
   protected int ySize = 96;

   public GUIElectromagnet(TileElectromagnet tile) {
      this.tile = tile;
   }

   public void updateScreen() {
      super.updateScreen();
      if (this.tile != null) {
         PacketNeedTileSendToServer.send(this.tile.getPos());
      }
   }

   public void initGui() {
      super.initGui();
      this.guiLeft = (this.width - this.xSize) / 2;
      this.guiTop = (this.height - this.ySize) / 2;
   }

   public boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY) {
      int i = this.guiLeft;
      int j = this.guiTop;
      pointX -= i;
      pointY -= j;
      return pointX >= rectX - 1 && pointX < rectX + rectWidth + 1 && pointY >= rectY - 1 && pointY < rectY + rectHeight + 1;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      super.drawScreen(mouseX, mouseY, partialTicks);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      int i = (this.width - this.xSize) / 2;
      int j = (this.height - this.ySize) / 2;
      this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
      this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
      int rf = this.tile.electricStorage.getEnergyStored();
      int rfmax = 2000;
      int rfw = (int)((float)rf / rfmax * 34.0F);
      if (rf > 0) {
         int elctX = 80;
         int elctY = 48;
         int elctTexX = 176;
         int elctTexY = 0;
         this.drawTexturedModalRect(i + elctX, j + elctY + 34 - rfw, elctTexX, elctTexY + 34 - rfw, 16, rfw);
      }

      if (this.tile.type == 1) {
         this.drawTexturedModalRect(i + 27, j + 13, 0, 96, 122, 31);
      }

      if (this.tile.type == 2) {
         this.drawTexturedModalRect(i + 27, j + 13, 0, 127, 122, 31);
      }

      int max = this.tile.fluidMax;
      if (this.tile.tank1 != null && this.isPointInRegion(9, 21, 16, 60, mouseX, mouseY)) {
         FluidStack f1 = this.tile.tank1.getFluid();
         if (f1 != null && f1.getFluid() != null) {
            this.drawHoveringText(f1.amount + " / " + max + " mB " + f1.getFluid().getLocalizedName(f1), mouseX, mouseY);
         }
      }

      if (this.tile.tank2 != null && this.isPointInRegion(149, 21, 16, 60, mouseX, mouseY)) {
         FluidStack f2 = this.tile.tank2.getFluid();
         if (f2 != null && f2.getFluid() != null) {
            this.drawHoveringText(f2.amount + " / " + max + " mB " + f2.getFluid().getLocalizedName(f2), mouseX, mouseY);
         }
      }

      String s = this.tile.type == 0 ? "Copper Electromagnet" : (this.tile.type == 1 ? "Silver Electromagnet" : "Stormsteel Electromagnet");
      this.fontRenderer.drawString(s, i + this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, j + 3, 4210752);
      this.fontRenderer.drawString("Heat", i + 33, j + 49, 4210752);
      this.fontRenderer.drawString("" + this.tile.heat, i + 33, j + 57, 4210752);
      this.fontRenderer.drawString("Biome t", i + 33, j + 66, 4210752);
      this.fontRenderer
         .drawString(
            ""
               + FluidsRegister.biomeToLiquidTemperature(
                  this.tile.getWorld().getBiome(this.tile.getPos()).getTemperature(this.tile.getPos())
               ),
            i + 33,
            j + 74,
            4210752
         );
      this.fontRenderer.drawString("Resist.", i + 103, j + 49, 4210752);
      this.fontRenderer.drawString("" + this.tile.getResistance(), i + 103, j + 57, 4210752);
      if (this.isPointInRegion(80, 48, 16, 34, mouseX, mouseY)) {
         this.drawHoveringText(rf + "/2000 RF", mouseX, mouseY);
      }

      if (this.tile.tank1 != null) {
         FluidStack f1 = this.tile.tank1.getFluid();
         if (f1 != null) {
            int wi = 16;
            int hmin = 82;
            int xPoss = 10;
            this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            int interpamount = Math.round((float)f1.amount / this.tile.fluidMax * 60.0F);
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

      if (this.tile.tank2 != null) {
         FluidStack f1 = this.tile.tank2.getFluid();
         if (f1 != null) {
            int wix = 16;
            int hminx = 82;
            int xPossx = 150;
            this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            int interpamountx = Math.round((float)f1.amount / this.tile.fluidMax * 60.0F);
            int displacex = hminx - interpamountx;
            int polygonheightx = hminx - interpamountx;
            if (interpamountx < 16) {
               polygonheightx = hminx - displacex;
            }

            if (interpamountx >= 16) {
               RenderFluidHelper.drawTexturedFluidRect(i + xPossx, j + (hminx - 16), wix, 16, 1.0F, f1.getFluid(), f1, 0.0);
               polygonheightx = hminx - 16 - displacex;
            }

            if (interpamountx >= 32) {
               RenderFluidHelper.drawTexturedFluidRect(i + xPossx, j + (hminx - 32), wix, 16, 1.0F, f1.getFluid(), f1, 0.0);
               polygonheightx = hminx - 32 - displacex;
            }

            if (interpamountx >= 48) {
               RenderFluidHelper.drawTexturedFluidRect(i + xPossx, j + (hminx - 48), wix, 16, 1.0F, f1.getFluid(), f1, 0.0);
               polygonheightx = hminx - 48 - displacex;
            }

            RenderFluidHelper.drawTexturedFluidRect(i + xPossx, j + displacex, wix, polygonheightx, polygonheightx / 16.0F, f1.getFluid(), f1, 0.0);
         }
      }
   }

   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      if (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode)) {
         this.mc.player.closeScreen();
      }
   }

   public boolean doesGuiPauseGame() {
      return false;
   }
}
