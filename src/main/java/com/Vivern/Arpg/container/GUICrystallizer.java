//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.container;

import com.Vivern.Arpg.main.ColorConverters;
import com.Vivern.Arpg.main.OreDicHelper;
import com.Vivern.Arpg.renders.RenderFluidHelper;
import com.Vivern.Arpg.tileentity.TileCrystallizer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fluids.FluidStack;

public class GUICrystallizer extends GuiContainer {
   public static final ResourceLocation GUI_TEXTURES = new ResourceLocation("arpg:textures/gui_crystallizer.png");
   public static final ResourceLocation GUI_OVER = new ResourceLocation("arpg:textures/gui_crystallizer_overlay.png");
   private final InventoryPlayer playerInventory;
   private final IInventory tileinv;

   public GUICrystallizer(InventoryPlayer playerInv, IInventory iInv) {
      super(new ContainerCrystallizer(playerInv, iInv));
      this.playerInventory = playerInv;
      this.tileinv = iInv;
      this.xSize = 176;
      this.ySize = 204;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      super.drawScreen(mouseX, mouseY, partialTicks);
      this.renderHoveredToolTip(mouseX, mouseY);
      if (this.tileinv instanceof TileCrystallizer) {
         TileCrystallizer alchem = (TileCrystallizer)this.tileinv;
         int max = alchem.fluidMax;
         if (this.isPointInRegion(59, 47, 60, 60, mouseX, mouseY)) {
            FluidStack f1 = alchem.tank1.getFluid();
            if (f1 != null && f1.getFluid() != null) {
               this.drawHoveringText(f1.amount + " / " + max + " mB " + f1.getFluid().getLocalizedName(f1), mouseX, mouseY);
            }
         }
      }
   }

   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
      String s = this.tileinv.getDisplayName().getUnformattedText();
      this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
      this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
      if (this.tileinv instanceof TileCrystallizer) {
         TileCrystallizer alchem = (TileCrystallizer)this.tileinv;
         int xoff1 = 5;
         int xoff2 = 145;
         int yoff1 = 16;
         int yadd1 = 8;
         this.fontRenderer.drawString("Si " + alchem.getMaterialCount(OreDicHelper.DUSTQUARTZ), xoff1, yoff1, 4210752);
         this.fontRenderer.drawString("C " + alchem.getMaterialCount(OreDicHelper.DUSTCOAL), xoff2, yoff1, 4210752);
         yoff1 += yadd1;
         this.fontRenderer.drawString("Al " + alchem.getMaterialCount(OreDicHelper.DUSTALUMINIUM), xoff1, yoff1, 4210752);
         this.fontRenderer.drawString("Cu " + alchem.getMaterialCount(OreDicHelper.DUSTCOPPER), xoff2, yoff1, 4210752);
         yoff1 += yadd1;
         this.fontRenderer.drawString("Fe " + alchem.getMaterialCount(OreDicHelper.DUSTIRON), xoff1, yoff1, 4210752);
         this.fontRenderer.drawString("Pb " + alchem.getMaterialCount(OreDicHelper.DUSTLEAD), xoff2, yoff1, 4210752);
         yoff1 += yadd1;
         this.fontRenderer.drawString("Cr " + alchem.getMaterialCount(OreDicHelper.DUSTCHROMIUM), xoff1, yoff1, 4210752);
         this.fontRenderer.drawString("Sn " + alchem.getMaterialCount(OreDicHelper.DUSTTIN), xoff2, yoff1, 4210752);
         yoff1 += yadd1;
         this.fontRenderer.drawString("Be " + alchem.getMaterialCount(OreDicHelper.DUSTBERYLLIUM), xoff1, yoff1, 4210752);
         this.fontRenderer.drawString("Ag " + alchem.getMaterialCount(OreDicHelper.DUSTSILVER), xoff2, yoff1, 4210752);
         yoff1 += yadd1;
         this.fontRenderer.drawString("Ti " + alchem.getMaterialCount(OreDicHelper.DUSTTITANIUM), xoff1, yoff1, 4210752);
         this.fontRenderer.drawString("Au " + alchem.getMaterialCount(OreDicHelper.DUSTGOLD), xoff2, yoff1, 4210752);
         yoff1 += yadd1;
         this.fontRenderer.drawString("S " + alchem.getMaterialCount(OreDicHelper.DUSTSULFUR), xoff1, yoff1, 4210752);
         this.fontRenderer.drawString("Pt " + alchem.getMaterialCount(OreDicHelper.DUSTPLATINUM), xoff2, yoff1, 4210752);
         yoff1 += yadd1;
         this.fontRenderer.drawString("Mn " + alchem.getMaterialCount(OreDicHelper.DUSTMANGANESE), xoff1, yoff1, 4210752);
         this.fontRenderer.drawString("Ni " + alchem.getMaterialCount(OreDicHelper.DUSTNICKEL), xoff2, yoff1, 4210752);
         yoff1 += yadd1;
         this.fontRenderer.drawString("Magic " + alchem.getMaterialCount("magic"), xoff2, yoff1, 4210752);
      }
   }

   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      int i = (this.width - this.xSize) / 2;
      int j = (this.height - this.ySize) / 2;
      this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
      this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
      if (this.tileinv instanceof TileCrystallizer) {
         TileCrystallizer alchem = (TileCrystallizer)this.tileinv;
         FluidStack f1 = alchem.tank1.getFluid();
         if (f1 != null) {
            Vec3d vec = ColorConverters.DecimaltoRGB(alchem.solutionPrimColor);

            for (int ii = 0; ii < 64; ii += 16) {
               int wi = ii == 48 ? 12 : 16;
               int hmin = 107;
               int xPoss = 59 + ii;
               this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
               int interpamount = Math.round((float)f1.amount / alchem.fluidMax * 60.0F);
               int displace = hmin - interpamount;
               int polygonheight = hmin - interpamount;
               if (interpamount < 16) {
                  polygonheight = hmin - displace;
               }

               if (interpamount >= 16) {
                  RenderFluidHelper.drawTexturedFluidRect(
                     i + xPoss,
                     j + (hmin - 16),
                     wi,
                     16,
                     1.0F,
                     f1.getFluid(),
                     f1,
                     0.0,
                     (float)vec.x,
                     (float)vec.y,
                     (float)vec.z
                  );
                  polygonheight = hmin - 16 - displace;
               }

               if (interpamount >= 32) {
                  RenderFluidHelper.drawTexturedFluidRect(
                     i + xPoss,
                     j + (hmin - 32),
                     wi,
                     16,
                     1.0F,
                     f1.getFluid(),
                     f1,
                     0.0,
                     (float)vec.x,
                     (float)vec.y,
                     (float)vec.z
                  );
                  polygonheight = hmin - 32 - displace;
               }

               if (interpamount >= 48) {
                  RenderFluidHelper.drawTexturedFluidRect(
                     i + xPoss,
                     j + (hmin - 48),
                     wi,
                     16,
                     1.0F,
                     f1.getFluid(),
                     f1,
                     0.0,
                     (float)vec.x,
                     (float)vec.y,
                     (float)vec.z
                  );
                  polygonheight = hmin - 48 - displace;
               }

               RenderFluidHelper.drawTexturedFluidRect(
                  i + xPoss,
                  j + displace,
                  wi,
                  polygonheight,
                  polygonheight / 16.0F,
                  f1.getFluid(),
                  f1,
                  0.0,
                  (float)vec.x,
                  (float)vec.y,
                  (float)vec.z
               );
            }
         }

         Vec3d vec = ColorConverters.DecimaltoRGB(alchem.solutionSecnColor);
         GlStateManager.color((float)vec.x, (float)vec.y, (float)vec.z, 1.0F);
         this.mc.getTextureManager().bindTexture(GUI_OVER);
         this.drawTexturedModalRect(i + 36, j + 39, 0, 0, this.xSize, this.ySize);
      }
   }
}
