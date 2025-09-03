//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.container;

import com.Vivern.Arpg.renders.RenderFluidHelper;
import com.Vivern.Arpg.tileentity.TileAlchemicLab;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class GUIAlchemicLab extends GuiContainer {
   public static final ResourceLocation GUI_TEXTURES = new ResourceLocation("arpg:textures/gui_alchemic_lab.png");
   private final InventoryPlayer playerInventory;
   private final IInventory tileinv;

   public GUIAlchemicLab(InventoryPlayer playerInv, IInventory iInv) {
      super(new ContainerAlchemicLab(playerInv, iInv));
      this.playerInventory = playerInv;
      this.tileinv = iInv;
      this.xSize = 200;
      this.ySize = 180;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      super.drawScreen(mouseX, mouseY, partialTicks);
      this.renderHoveredToolTip(mouseX, mouseY);
      if (this.tileinv instanceof TileAlchemicLab) {
         TileAlchemicLab alchemLab = (TileAlchemicLab)this.tileinv;
         int max = TileAlchemicLab.fluidMax;
         if (this.isPointInRegion(7, 15, 16, 60, mouseX, mouseY)) {
            FluidStack f1 = alchemLab.tank1.getFluid();
            if (f1 != null && f1.getFluid() != null) {
               this.drawHoveringText(f1.amount + " / " + max + " mB " + f1.getFluid().getLocalizedName(f1), mouseX, mouseY);
            }
         }

         if (this.isPointInRegion(175, 15, 16, 60, mouseX, mouseY)) {
            FluidStack f2 = alchemLab.tank2.getFluid();
            if (f2 != null && f2.getFluid() != null) {
               this.drawHoveringText(f2.amount + " / " + max + " mB " + f2.getFluid().getLocalizedName(f2), mouseX, mouseY);
            }
         }
      }
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
      int progr = this.tileinv.getField(6);
      int progrmax = this.tileinv.getField(9);
      int w = (int)((float)progr / progrmax * 46.0F);
      if (progr > 0) {
         this.drawTexturedModalRect(i + 77, j + 21, 0, 212, w, 32);
      }

      if (this.tileinv.getField(8) == 1) {
         this.drawTexturedModalRect(i + 93, j + 58, 0, 190, 14, 14);
      }

      float mana = this.tileinv.getField(0) / 10.0F;
      int manamax = this.tileinv.getField(1);
      int mw = (int)(mana / manamax * 74.0F);
      if (mana > 0.0F) {
         this.drawTexturedModalRect(i + 63, j + 73, 0, 204, mw, 8);
      }

      if (this.tileinv instanceof TileAlchemicLab) {
         TileAlchemicLab alchemLab = (TileAlchemicLab)this.tileinv;
         FluidStack f1 = alchemLab.tank1.getFluid();
         FluidStack f2 = alchemLab.tank2.getFluid();
         if (f1 != null) {
            this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            int interpamount = Math.round((float)f1.amount / TileAlchemicLab.fluidMax * 60.0F);
            int displace = 76 - interpamount;
            int polygonheight = 76 - interpamount;
            if (interpamount < 16) {
               polygonheight = 76 - displace;
            }

            if (interpamount >= 16) {
               RenderFluidHelper.drawTexturedFluidRect(i + 8, j + 60, 16, 16, 1.0F, f1.getFluid(), f1, 0.0);
               polygonheight = 60 - displace;
            }

            if (interpamount >= 32) {
               RenderFluidHelper.drawTexturedFluidRect(i + 8, j + 44, 16, 16, 1.0F, f1.getFluid(), f1, 0.0);
               polygonheight = 44 - displace;
            }

            if (interpamount >= 48) {
               RenderFluidHelper.drawTexturedFluidRect(i + 8, j + 28, 16, 16, 1.0F, f1.getFluid(), f1, 0.0);
               polygonheight = 28 - displace;
            }

            RenderFluidHelper.drawTexturedFluidRect(i + 8, j + displace, 16, polygonheight, polygonheight / 16.0F, f1.getFluid(), f1, 0.0);
         }

         if (f2 != null) {
            this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            int interpamountx = Math.round((float)f2.amount / TileAlchemicLab.fluidMax * 60.0F);
            int displacex = 76 - interpamountx;
            int polygonheightx = 76 - interpamountx;
            if (interpamountx < 16) {
               polygonheightx = 76 - displacex;
            }

            if (interpamountx >= 16) {
               RenderFluidHelper.drawTexturedFluidRect(i + 176, j + 60, 16, 16, 1.0F, f2.getFluid(), f2, 0.0);
               polygonheightx = 60 - displacex;
            }

            if (interpamountx >= 32) {
               RenderFluidHelper.drawTexturedFluidRect(i + 176, j + 44, 16, 16, 1.0F, f2.getFluid(), f2, 0.0);
               polygonheightx = 44 - displacex;
            }

            if (interpamountx >= 48) {
               RenderFluidHelper.drawTexturedFluidRect(i + 176, j + 28, 16, 16, 1.0F, f2.getFluid(), f2, 0.0);
               polygonheightx = 28 - displacex;
            }

            RenderFluidHelper.drawTexturedFluidRect(i + 176, j + displacex, 16, polygonheightx, polygonheightx / 16.0F, f2.getFluid(), f2, 0.0);
         }
      }
   }
}
