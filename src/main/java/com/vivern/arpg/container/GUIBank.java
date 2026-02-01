package com.vivern.arpg.container;

import com.vivern.arpg.main.Coins;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.network.PacketTileClickToServer;
import com.vivern.arpg.tileentity.TileBank;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

public class GUIBank extends GuiScreen {
   public static final ResourceLocation GUI_TEXTURES = new ResourceLocation("arpg:textures/gui_bank.png");
   public TileBank tile;
   public EntityPlayer player;
   public int coinsMoved;
   public int delay = 0;
   public boolean isclicked = false;
   public int clickedx = 0;
   public int clickedy = 0;
   public int clickedTimer = 0;

   public GUIBank(TileBank tile, EntityPlayer player) {
      this.tile = tile;
      this.player = player;
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

   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
      String s = this.tile.getDisplayName().getUnformattedText();
      this.fontRenderer.drawString(s, 88 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      if (this.mc != null) {
         this.drawDefaultBackground();
         super.drawScreen(mouseX, mouseY, partialTicks);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         int i = (this.width - 176) / 2;
         int j = (this.height - 109) / 2;
         this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
         this.drawTexturedModalRect(i, j, 0, 0, 176, 109);
         this.drawStringAndMoney("" + (this.tile.coins + (this.isclicked ? this.coinsMoved : 0)), i + 88, j + 28);
         this.drawStringAndMoney("" + (Coins.getCoins(this.player) - (this.isclicked ? this.coinsMoved : 0)), i + 88, j + 70);
         if (Minecraft.getMinecraft().player != null) {
            String plname = Minecraft.getMinecraft().player.getDisplayNameString();
            this.normalizeColor();
            this.fontRenderer.drawString(plname, i + 9, j + 86, 4210752);
            this.fontRenderer.drawString(this.tile.getBlockType().getLocalizedName(), i + 9, j + 44, 4210752);
         }
      }
   }

   protected void mousePressedTick(int mouseX, int mouseY, int timeSinceLastClick) {
      if (this.delay <= 0) {
         int x = mouseX - (this.width - 176) / 2;
         int y = mouseY - (this.height - 109) / 2;
         if (y > 40 && y < 58 && x > 91 && x < 115) {
            this.coinsMoved -= timeSinceLastClick > 30 ? 2 : 1;
            this.delay = (int)Math.round(Math.max(10.0 - timeSinceLastClick / 2.0, 0.0));
            this.playsoundCoin();
         }

         if (y > 82 && y < 100 && x > 91 && x < 115) {
            this.coinsMoved += timeSinceLastClick > 30 ? 2 : 1;
            this.delay = (int)Math.round(Math.max(10.0 - timeSinceLastClick / 2.0, 0.0));
            this.playsoundCoin();
         }
      } else {
         this.delay--;
      }
   }

   protected void mouseReleased(int mouseX, int mouseY, int state) {
      super.mouseReleased(mouseX, mouseY, state);
      this.sendCoinsMoved();
      this.delay = 0;
      this.clickedTimer = 0;
      this.isclicked = false;
   }

   public void updateScreen() {
      super.updateScreen();
      if (this.isclicked) {
         this.mousePressedTick(this.clickedx, this.clickedy, this.clickedTimer);
         this.clickedTimer++;
      }
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      int x = mouseX - (this.width - 176) / 2;
      int y = mouseY - (this.height - 109) / 2;
      this.clickedx = mouseX;
      this.clickedy = mouseY;
      this.isclicked = true;
      if (y > 40 && y < 58) {
         if (x > 118 && x < 142) {
            this.coinsMoved = -this.tile.coins;
            this.sendCoinsMoved();
            this.playsoundCoin();
         }

         if (x > 145 && x < 169) {
            this.coinsMoved = (int)(-this.tile.coins * 0.1);
            this.sendCoinsMoved();
            this.playsoundCoin();
         }
      }

      if (y > 82 && y < 100) {
         if (x > 118 && x < 142) {
            this.coinsMoved = Coins.getCoins(this.player);
            this.sendCoinsMoved();
            this.playsoundCoin();
         }

         if (x > 145 && x < 169) {
            this.coinsMoved = (int)(Coins.getCoins(this.player) * 0.1);
            this.sendCoinsMoved();
            this.playsoundCoin();
         }
      }
   }

   public void onGuiClosed() {
      super.onGuiClosed();
      this.sendCoinsMoved();
   }

   public void sendCoinsMoved() {
      if (this.coinsMoved != 0) {
         PacketTileClickToServer packet = new PacketTileClickToServer();
         packet.writeints(
            this.tile.getPos().getX(),
            this.tile.getPos().getY(),
            this.tile.getPos().getZ(),
            this.coinsMoved,
            0,
            0
         );
         PacketHandler.NETWORK.sendToServer(packet);
         this.coinsMoved = 0;
      }
   }

   public void playsoundCoin() {
      EntityPlayer player = Minecraft.getMinecraft().player;
      if (player != null) {
         player.world
            .playSound(
               player.posX,
               player.posY,
               player.posZ,
               Sounds.grap_money,
               SoundCategory.PLAYERS,
               0.3F,
               0.9F + player.getRNG().nextFloat() / 5.0F,
               false
            );
      }
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      if (this.mc != null && (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode))) {
         this.mc.player.closeScreen();
      }
   }
}
