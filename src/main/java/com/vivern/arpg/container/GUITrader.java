package com.vivern.arpg.container;

import com.vivern.arpg.main.Coins;
import com.vivern.arpg.mobs.NPCMobsPack;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.network.PacketTraderClickToServer;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;

public class GUITrader extends GuiContainer {
   public static final ResourceLocation GUI_TEXTURES = new ResourceLocation("arpg:textures/gui_trader.png");
   public NPCMobsPack.NpcTrader npc;
   public int page = 0;
   public int maxpage = 0;
   int lastPressedX;
   int lastPressedY;
   int lastPressedMouse;
   boolean isPressedNow = false;
   int fastBuyCooldown = 0;
   int ticksFastBuy = 0;

   public GUITrader(NPCMobsPack.NpcTrader npc, EntityPlayer player) {
      super(new ContainerTrader(player.inventory));
      this.npc = npc;
      this.maxpage = MathHelper.ceil(npc.trades.size() / 5.0F);
      this.xSize = 212;
      this.ySize = 256;
   }

   public void normalizeColor() {
      GlStateManager.color(1.0F, 1.0F, 1.0F);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
      GlStateManager.disableLighting();
   }

   public void drawStringAndMoney(String string, int x, int y, boolean sell) {
      int stringwidth1 = this.fontRenderer.getStringWidth(string);
      if (stringwidth1 > 30) {
         int stringwidth2 = stringwidth1 / 2;
         this.normalizeColor();
         this.drawTexturedModalRect(x + 102, y + 49, 230, 0, 7, 7);
         this.normalizeColor();
         this.fontRenderer.drawString(string, x - stringwidth2 + 106, y + 41, 0);
      } else {
         if (sell) {
            x += 112;
         } else {
            x += 99;
         }

         y += 45;
         int stringwidth2 = (stringwidth1 + 8) / 2;
         this.normalizeColor();
         int xx = x - stringwidth2;
         this.drawTexturedModalRect(xx + stringwidth1 + 1, y, 230, 0, 7, 7);
         this.normalizeColor();
         this.fontRenderer.drawString(string, xx, y, 0);
      }
   }

   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
      GlStateManager.color(1.0F, 1.0F, 1.0F);
      int i = (this.width - 212) / 2;
      int j = (this.height - 256) / 2;
      this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
      this.drawTexturedModalRect(i, j, 0, 0, 212, 256);
      if (Minecraft.getMinecraft().player != null) {
         String plname = Minecraft.getMinecraft().player.getDisplayNameString();
         String s2 = Coins.getCoins(Minecraft.getMinecraft().player) + "";
         this.normalizeColor();
         this.drawTexturedModalRect(i + 36 + this.fontRenderer.getStringWidth(plname) + this.fontRenderer.getStringWidth(s2), j + 163, 230, 0, 7, 7);
         this.normalizeColor();
         this.fontRenderer.drawString(plname, i + 28, j + 163, 4210752);
         this.normalizeColor();
         this.fontRenderer.drawString(s2, i + 34 + this.fontRenderer.getStringWidth(plname), j + 163, 0);
      }

      this.fontRenderer.drawString(this.page + 1 + "/" + (this.maxpage + 1), i + 160, j + 163, 4210752);
      GlStateManager.disableLighting();
      RenderHelper.enableGUIStandardItemLighting();
      if (!this.npc.trades.isEmpty()) {
         for (int t = 0; t < 5; t++) {
            int n = t * 25;
            if (this.npc.trades.size() - 1 < t + this.page * 5 || t + this.page * 5 < 0) {
               break;
            }

            NPCMobsPack.Trade trade = this.npc.trades.get(t + this.page * 5);
            this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
            if (trade.coinsBuy > 0) {
               String b = "" + trade.coinsBuy;
               this.normalizeColor();
               this.drawTexturedModalRect(i + 119, j + 44 + n, 218, 8, 12, 10);
               this.drawStringAndMoney(b, i, j + n, false);
            } else if (trade.coinsSell > 0) {
               String s = "" + trade.coinsSell;
               this.normalizeColor();
               this.drawTexturedModalRect(i + 81, j + 44 + n, 218, 8, 12, 10);
               this.drawStringAndMoney(s, i, j + n, true);
            } else {
               this.normalizeColor();
               this.drawTexturedModalRect(i + 100, j + 44 + n, 218, 8, 12, 10);
            }

            if (trade.buy[0] != null) {
               this.drawStack(i + 62, j + 41 + n, trade.buy[0], mouseX, mouseY);
            }

            if (trade.buy[1] != null) {
               this.drawStack(i + 44, j + 41 + n, trade.buy[1], mouseX, mouseY);
            }

            if (trade.buy[2] != null) {
               this.drawStack(i + 26, j + 41 + n, trade.buy[2], mouseX, mouseY);
            }

            if (trade.buy[3] != null) {
               this.drawStack(i + 8, j + 41 + n, trade.buy[3], mouseX, mouseY);
            }

            if (trade.sell[0] != null) {
               this.drawStack(i + 134, j + 41 + n, trade.sell[0], mouseX, mouseY);
            }

            if (trade.sell[1] != null) {
               this.drawStack(i + 152, j + 41 + n, trade.sell[1], mouseX, mouseY);
            }

            if (trade.sell[2] != null) {
               this.drawStack(i + 170, j + 41 + n, trade.sell[2], mouseX, mouseY);
            }

            if (trade.sell[3] != null) {
               this.drawStack(i + 188, j + 41 + n, trade.sell[3], mouseX, mouseY);
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
      int i = (this.width - 212) / 2;
      int j = (this.height - 256) / 2;
      GlStateManager.translate(0.0F, 0.0F, 32.0F);
      this.zLevel = 200.0F;
      this.itemRender.zLevel = 200.0F;
      FontRenderer font = stack.getItem().getFontRenderer(stack);
      if (font == null) {
         font = this.fontRenderer;
      }

      this.normalizeColor();
      this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
      this.normalizeColor();
      this.itemRender.renderItemOverlayIntoGUI(font, stack, x, y, null);
      if (this.isPointInRegion(x - i, y - j, 16, 16, mouseX, mouseY)) {
         this.normalizeColor();
         this.renderToolTip(stack, mouseX, mouseY);
      }

      this.zLevel = 0.0F;
      this.itemRender.zLevel = 0.0F;
      this.normalizeColor();
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      super.mouseClicked(mouseX, mouseY, mouseButton);
      int mx = mouseX - (this.width - 212) / 2;
      int my = mouseY - (this.height - 256) / 2;
      if (my > 173 && my < 198) {
         boolean play = false;
         if (mx > 7 && mx < 20 && this.page > 0) {
            this.page--;
            play = true;
         }

         if (mx > 192 && mx < 205 && this.page < this.maxpage) {
            this.page++;
            play = true;
         }

         if (play && Minecraft.getMinecraft().player != null) {
            this.npc
               .world
               .playSound(
                  Minecraft.getMinecraft().player,
                  Minecraft.getMinecraft().player.posX,
                  Minecraft.getMinecraft().player.posY,
                  Minecraft.getMinecraft().player.posZ,
                  SoundEvents.UI_BUTTON_CLICK,
                  SoundCategory.AMBIENT,
                  1.0F,
                  1.0F
               );
         }
      }

      if (isShiftKeyDown()) {
         mouseButton = 6;
      }

      this.sendTradeToServer(mx, my, mouseButton);
      this.lastPressedX = mx;
      this.lastPressedY = my;
      this.lastPressedMouse = mouseButton;
      this.isPressedNow = true;
   }

   public void sendTradeToServer(int mx, int my, int mouseButton) {
      PacketTraderClickToServer packet = new PacketTraderClickToServer();
      packet.writeints(this.npc.getEntityId(), mx, my, mouseButton, this.page);
      PacketHandler.NETWORK.sendToServer(packet);
      if (Minecraft.getMinecraft().player != null) {
         this.npc.guiclick(Minecraft.getMinecraft().player, mx, my, mouseButton, this.page);
      }
   }

   public void updateScreen() {
      if (this.isPressedNow) {
         this.ticksFastBuy++;
         if (this.fastBuyCooldown <= 0) {
            this.sendTradeToServer(this.lastPressedX, this.lastPressedY, this.lastPressedMouse);
            this.resetFastBuyCooldown();
         } else {
            this.fastBuyCooldown--;
         }
      }

      super.updateScreen();
   }

   public void resetFastBuyCooldown() {
      this.fastBuyCooldown = Math.max(Math.round(7.0F - this.ticksFastBuy / 25.0F * 6.0F), 0);
   }

   protected void mouseReleased(int mouseX, int mouseY, int state) {
      this.isPressedNow = false;
      this.ticksFastBuy = 0;
      this.resetFastBuyCooldown();
      super.mouseReleased(mouseX, mouseY, state);
   }

   public boolean doesGuiPauseGame() {
      return false;
   }
}
