//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.container;

import com.Vivern.Arpg.renders.RenderFluidHelper;
import com.Vivern.Arpg.tileentity.TileIndustrialMixer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class GUIIndustrialMixer extends GuiContainer {
   public static final ResourceLocation GUI_TEXTURES = new ResourceLocation("arpg:textures/gui_industrial_mixer.png");
   private final InventoryPlayer playerInventory;
   private final IInventory tileinv;

   public GUIIndustrialMixer(InventoryPlayer playerInv, IInventory iInv) {
      super(new ContainerIndustrialMixer(playerInv, iInv));
      this.playerInventory = playerInv;
      this.tileinv = iInv;
      this.xSize = 176;
      this.ySize = 192;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      super.drawScreen(mouseX, mouseY, partialTicks);
      this.renderHoveredToolTip(mouseX, mouseY);
      if (this.isPointInRegion(80, 38, 16, 34, mouseX, mouseY)) {
         this.drawHoveringText(this.tileinv.getField(0) + "/10000 RF", mouseX, mouseY);
      }

      if (this.tileinv instanceof TileIndustrialMixer) {
         TileIndustrialMixer mixer = (TileIndustrialMixer)this.tileinv;
         int max = TileIndustrialMixer.fluidMax;
         if (this.isPointInRegion(8, 35, 16, 60, mouseX, mouseY)) {
            FluidStack f1 = mixer.tank1.getFluid();
            if (f1 != null && f1.getFluid() != null) {
               this.drawHoveringText(f1.amount + " / " + max + " mB " + f1.getFluid().getLocalizedName(f1), mouseX, mouseY);
            }
         }

         if (this.isPointInRegion(30, 35, 16, 60, mouseX, mouseY)) {
            FluidStack f1 = mixer.tank2.getFluid();
            if (f1 != null && f1.getFluid() != null) {
               this.drawHoveringText(f1.amount + " / " + max + " mB " + f1.getFluid().getLocalizedName(f1), mouseX, mouseY);
            }
         }

         if (this.isPointInRegion(130, 35, 16, 60, mouseX, mouseY)) {
            FluidStack f1 = mixer.tank3.getFluid();
            if (f1 != null && f1.getFluid() != null) {
               this.drawHoveringText(f1.amount + " / " + max + " mB " + f1.getFluid().getLocalizedName(f1), mouseX, mouseY);
            }
         }

         if (this.isPointInRegion(152, 35, 16, 60, mouseX, mouseY)) {
            FluidStack f1 = mixer.tank4.getFluid();
            if (f1 != null && f1.getFluid() != null) {
               this.drawHoveringText(f1.amount + " / " + max + " mB " + f1.getFluid().getLocalizedName(f1), mouseX, mouseY);
            }
         }
      }
   }

   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
      String s = this.tileinv.getDisplayName().getUnformattedText();
      this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 5, 4210752);
      this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 3, 4210752);
   }

   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      int i = (this.width - this.xSize) / 2;
      int j = (this.height - this.ySize) / 2;
      this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
      this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
      int progr = this.tileinv.getField(6);
      int progrmax = this.tileinv.getField(9);
      if (progr > 0) {
         int vertical = (int)((float)progr / progrmax * 44.0F);
         this.drawTexturedModalRect(i + 64, j + 33, 176, 35, 48, vertical);
      }

      float rf = this.tileinv.getField(0);
      int rfmax = this.tileinv.getField(1);
      int rfw = (int)(rf / rfmax * 34.0F);
      if (rf > 0.0F) {
         int elctX = 80;
         int elctY = 38;
         int elctTexX = 176;
         int elctTexY = 0;
         this.drawTexturedModalRect(i + elctX, j + elctY + 34 - rfw, elctTexX, elctTexY + 34 - rfw, 16, rfw);
      }

      if (this.tileinv instanceof TileIndustrialMixer) {
         TileIndustrialMixer mixer = (TileIndustrialMixer)this.tileinv;
         if (mixer.tank1 != null) {
            FluidStack f1 = mixer.tank1.getFluid();
            if (f1 != null) {
               int wi = 16;
               int hmin = 95;
               int xPoss = 8;
               this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
               int interpamount = Math.round((float)f1.amount / TileIndustrialMixer.fluidMax * 60.0F);
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

         if (mixer.tank2 != null) {
            FluidStack f1 = mixer.tank2.getFluid();
            if (f1 != null) {
               int wix = 16;
               int hminx = 95;
               int xPossx = 30;
               this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
               int interpamountx = Math.round((float)f1.amount / TileIndustrialMixer.fluidMax * 60.0F);
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

         if (mixer.tank3 != null) {
            FluidStack f1 = mixer.tank3.getFluid();
            if (f1 != null) {
               int wixx = 16;
               int hminxx = 95;
               int xPossxx = 130;
               this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
               int interpamountxx = Math.round((float)f1.amount / TileIndustrialMixer.fluidMax * 60.0F);
               int displacexx = hminxx - interpamountxx;
               int polygonheightxx = hminxx - interpamountxx;
               if (interpamountxx < 16) {
                  polygonheightxx = hminxx - displacexx;
               }

               if (interpamountxx >= 16) {
                  RenderFluidHelper.drawTexturedFluidRect(i + xPossxx, j + (hminxx - 16), wixx, 16, 1.0F, f1.getFluid(), f1, 0.0);
                  polygonheightxx = hminxx - 16 - displacexx;
               }

               if (interpamountxx >= 32) {
                  RenderFluidHelper.drawTexturedFluidRect(i + xPossxx, j + (hminxx - 32), wixx, 16, 1.0F, f1.getFluid(), f1, 0.0);
                  polygonheightxx = hminxx - 32 - displacexx;
               }

               if (interpamountxx >= 48) {
                  RenderFluidHelper.drawTexturedFluidRect(i + xPossxx, j + (hminxx - 48), wixx, 16, 1.0F, f1.getFluid(), f1, 0.0);
                  polygonheightxx = hminxx - 48 - displacexx;
               }

               RenderFluidHelper.drawTexturedFluidRect(i + xPossxx, j + displacexx, wixx, polygonheightxx, polygonheightxx / 16.0F, f1.getFluid(), f1, 0.0);
            }
         }

         if (mixer.tank4 != null) {
            FluidStack f1 = mixer.tank4.getFluid();
            if (f1 != null) {
               int wixxx = 16;
               int hminxxx = 95;
               int xPossxxx = 152;
               this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
               int interpamountxxx = Math.round((float)f1.amount / TileIndustrialMixer.fluidMax * 60.0F);
               int displacexxx = hminxxx - interpamountxxx;
               int polygonheightxxx = hminxxx - interpamountxxx;
               if (interpamountxxx < 16) {
                  polygonheightxxx = hminxxx - displacexxx;
               }

               if (interpamountxxx >= 16) {
                  RenderFluidHelper.drawTexturedFluidRect(i + xPossxxx, j + (hminxxx - 16), wixxx, 16, 1.0F, f1.getFluid(), f1, 0.0);
                  polygonheightxxx = hminxxx - 16 - displacexxx;
               }

               if (interpamountxxx >= 32) {
                  RenderFluidHelper.drawTexturedFluidRect(i + xPossxxx, j + (hminxxx - 32), wixxx, 16, 1.0F, f1.getFluid(), f1, 0.0);
                  polygonheightxxx = hminxxx - 32 - displacexxx;
               }

               if (interpamountxxx >= 48) {
                  RenderFluidHelper.drawTexturedFluidRect(i + xPossxxx, j + (hminxxx - 48), wixxx, 16, 1.0F, f1.getFluid(), f1, 0.0);
                  polygonheightxxx = hminxxx - 48 - displacexxx;
               }

               RenderFluidHelper.drawTexturedFluidRect(i + xPossxxx, j + displacexxx, wixxx, polygonheightxxx, polygonheightxxx / 16.0F, f1.getFluid(), f1, 0.0);
            }
         }
      }
   }
}
