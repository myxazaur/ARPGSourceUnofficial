//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.container;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GUIDisenchantmentTable extends GuiContainer {
   public static final ResourceLocation GUI_TEXTURES = new ResourceLocation("arpg:textures/gui_disenchantment_table.png");
   private final InventoryPlayer playerInventory;
   private final IInventory tileinv;

   public GUIDisenchantmentTable(InventoryPlayer playerInv, IInventory iInv) {
      super(new Container1(playerInv, iInv));
      this.playerInventory = playerInv;
      this.tileinv = iInv;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      super.drawScreen(mouseX, mouseY, partialTicks);
      this.renderHoveredToolTip(mouseX, mouseY);
   }

   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
      String s = this.tileinv.getDisplayName().getUnformattedText();
      this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
      this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
      int enchId = this.tileinv.getField(2);
      if (enchId != -1) {
         Enchantment enchantment = Enchantment.getEnchantmentByID(enchId);
         if (enchantment != null) {
            String enchName = enchantment.getTranslatedName(1);
            this.fontRenderer.drawString(enchName, this.xSize / 2 - this.fontRenderer.getStringWidth(enchName) / 2, 18, 4210752);
         }
      }
   }

   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      int i = (this.width - this.xSize) / 2;
      int j = (this.height - this.ySize) / 2;
      this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
      this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
      int progr = this.tileinv.getField(0);
      if (progr > 0) {
         int progrmax = this.tileinv.getField(1);
         int w = (int)((float)progr / progrmax * 40.0F);
         if (w > 26) {
            w += 30;
         }

         this.drawTexturedModalRect(i + 47, j + 34, 178, 2, w, 16);
      }
   }
}
