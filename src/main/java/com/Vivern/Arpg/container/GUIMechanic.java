package com.Vivern.Arpg.container;

import com.Vivern.Arpg.main.Coins;
import com.Vivern.Arpg.mobs.NPCMobsPack;
import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.network.PacketTraderClickToServer;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUIMechanic extends GuiContainer {
   public static final ResourceLocation GUI_TEXTURES = new ResourceLocation("arpg:textures/gui_mechanic.png");
   public NPCMobsPack.NpcMechanic npc;

   public GUIMechanic(NPCMobsPack.NpcMechanic npc, EntityPlayer player) {
      super(new ContainerMechanic(player.inventory, npc));
      this.npc = npc;
      this.xSize = 176;
      this.ySize = 166;
   }

   public void normalizeColor() {
      GlStateManager.color(1.0F, 1.0F, 1.0F);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
      GlStateManager.disableLighting();
   }

   public void drawStringAndMoney(String string, int x, int y) {
      int stringwidth1 = this.fontRenderer.getStringWidth(string);
      int stringwidth2 = (stringwidth1 + 8) / 2;
      int xx = x - stringwidth2;
      this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
      this.normalizeColor();
      this.drawTexturedModalRect(xx + stringwidth1 + 1, y, 230, 0, 7, 7);
      this.normalizeColor();
      this.fontRenderer.drawString(string, xx, y, 0);
   }

   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
      GlStateManager.color(1.0F, 1.0F, 1.0F);
      int i = (this.width - this.xSize) / 2;
      int j = (this.height - this.ySize) / 2;
      this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
      this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
      if (Minecraft.getMinecraft().player != null) {
         String plname = Minecraft.getMinecraft().player.getDisplayNameString();
         String s2 = Coins.getCoins(Minecraft.getMinecraft().player) + "";
         this.normalizeColor();
         this.drawTexturedModalRect(i + 18 + this.fontRenderer.getStringWidth(plname) + this.fontRenderer.getStringWidth(s2), j + 73, 230, 0, 7, 7);
         this.normalizeColor();
         this.fontRenderer.drawString(plname, i + 10, j + 73, 4210752);
         this.normalizeColor();
         this.fontRenderer.drawString(s2, i + 16 + this.fontRenderer.getStringWidth(plname), j + 73, 0);
      }

      this.fontRenderer.drawString("Comfotability: " + this.npc.getComfortability(), i + 5, j + 5, 4210752);
      GlStateManager.disableLighting();
      RenderHelper.enableGUIStandardItemLighting();
      if (!this.npc.containedItemStack.isEmpty()) {
         int fullprice = this.npc.getRepairFullCost();
         this.drawStringAndMoney("" + fullprice, i + 79, j + 19);
         this.drawStringAndMoney("1", i + 79, j + 38);
         int mx = mouseX - i;
         int my = mouseY - j;
         if (mx > 43 && mx < 68) {
            if (my > 14 && my < 31) {
               String st = "+" + this.npc.containedItemStack.getItemDamage();
               this.normalizeColor();
               this.fontRenderer.drawString(st, i + 103, j + 57, 7986254);
            } else if (my > 33 && my < 50) {
               String st = "+" + Math.min(this.npc.containedItemStack.getItemDamage(), this.npc.getRepairPerCoin());
               this.normalizeColor();
               this.fontRenderer.drawString(st, i + 103, j + 57, 7986254);
            }
         }
      }

      GlStateManager.enableLighting();
      RenderHelper.enableStandardItemLighting();
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      super.drawScreen(mouseX, mouseY, partialTicks);
      this.renderHoveredToolTip(mouseX, mouseY);
   }

   public void drawStack(int x, int y, ItemStack stack, int mouseX, int mouseY) {
      int i = (this.width - this.xSize) / 2;
      int j = (this.height - this.ySize) / 2;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
      GlStateManager.translate(0.0F, 0.0F, 32.0F);
      this.zLevel = 200.0F;
      this.itemRender.zLevel = 200.0F;
      FontRenderer font = stack.getItem().getFontRenderer(stack);
      if (font == null) {
         font = this.fontRenderer;
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F);
      this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
      GlStateManager.color(1.0F, 1.0F, 1.0F);
      this.itemRender.renderItemOverlayIntoGUI(font, stack, x, y, null);
      if (this.isPointInRegion(x - i, y - j, 16, 16, mouseX, mouseY)) {
         this.renderToolTip(stack, mouseX, mouseY);
      }

      this.zLevel = 0.0F;
      this.itemRender.zLevel = 0.0F;
      GlStateManager.color(1.0F, 1.0F, 1.0F);
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      super.mouseClicked(mouseX, mouseY, mouseButton);
      int mx = mouseX - (this.width - this.xSize) / 2;
      int my = mouseY - (this.height - this.ySize) / 2;
      PacketTraderClickToServer packet = new PacketTraderClickToServer();
      packet.writeints(this.npc.getEntityId(), mx, my, mouseButton, 0);
      PacketHandler.NETWORK.sendToServer(packet);
      if (Minecraft.getMinecraft().player != null) {
         this.npc.guiclick(Minecraft.getMinecraft().player, mx, my, mouseButton, 0);
      }
   }

   public boolean doesGuiPauseGame() {
      return false;
   }
}
