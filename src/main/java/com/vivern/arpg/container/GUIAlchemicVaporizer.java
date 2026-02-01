package com.vivern.arpg.container;

import com.vivern.arpg.renders.RenderFluidHelper;
import com.vivern.arpg.tileentity.TileAlchemicVaporizer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class GUIAlchemicVaporizer extends GuiContainer {
   public static final ResourceLocation GUI_TEXTURES = new ResourceLocation("arpg:textures/gui_alchemic_vaporizer.png");
   private final InventoryPlayer playerInventory;
   private final IInventory tileinv;

   public GUIAlchemicVaporizer(InventoryPlayer playerInv, IInventory iInv) {
      super(new ContainerAlchemicVaporizer(playerInv, iInv));
      this.playerInventory = playerInv;
      this.tileinv = iInv;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      super.drawScreen(mouseX, mouseY, partialTicks);
      this.renderHoveredToolTip(mouseX, mouseY);
      if (this.tileinv instanceof TileAlchemicVaporizer) {
         TileAlchemicVaporizer alchem = (TileAlchemicVaporizer)this.tileinv;
         int max = alchem.fluidMax;
         if (this.isPointInRegion(8, 62, 16, 60, mouseX, mouseY)) {
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
         this.drawTexturedModalRect(i + 66, j + 14, 176, 38, w, 32);
      }

      if (this.tileinv.getField(8) == 1) {
         this.drawTexturedModalRect(i + 81, j + 50, 176, 0, 14, 14);
      }

      float mana = this.tileinv.getField(0) / 10.0F;
      int manamax = this.tileinv.getField(1);
      int mw = (int)(mana / manamax * 74.0F);
      if (mana > 0.0F) {
         this.drawTexturedModalRect(i + 51, j + 65, 176, 30, mw, 8);
      }

      if (this.tileinv instanceof TileAlchemicVaporizer) {
         TileAlchemicVaporizer alchem = (TileAlchemicVaporizer)this.tileinv;
         FluidStack f1 = alchem.tank1.getFluid();
         if (f1 != null) {
            this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            int interpamount = Math.round((float)f1.amount / alchem.fluidMax * 60.0F);
            int displace = 68 - interpamount;
            int polygonheight = 68 - interpamount;
            if (interpamount < 16) {
               polygonheight = 68 - displace;
            }

            if (interpamount >= 16) {
               RenderFluidHelper.drawTexturedFluidRect(i + 62, j + 52, 16, 16, 1.0F, f1.getFluid(), f1, 0.0);
               polygonheight = 52 - displace;
            }

            if (interpamount >= 32) {
               RenderFluidHelper.drawTexturedFluidRect(i + 62, j + 36, 16, 16, 1.0F, f1.getFluid(), f1, 0.0);
               polygonheight = 36 - displace;
            }

            if (interpamount >= 48) {
               RenderFluidHelper.drawTexturedFluidRect(i + 62, j + 20, 16, 16, 1.0F, f1.getFluid(), f1, 0.0);
               polygonheight = 20 - displace;
            }

            RenderFluidHelper.drawTexturedFluidRect(i + 62, j + displace, 16, polygonheight, polygonheight / 16.0F, f1.getFluid(), f1, 0.0);
         }
      }
   }
}
