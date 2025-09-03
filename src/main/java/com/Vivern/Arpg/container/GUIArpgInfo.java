//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.container;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.apache.logging.log4j.LogManager;

public class GUIArpgInfo extends GuiScreen {
   private static final ResourceLocation GUI_TEXTURES = new ResourceLocation("arpg:textures/arpg_info.png");
   private EntityPlayer player;
   private static final ArrayList<ArpgInfoString> infos = new ArrayList<>();
   private static int displX = 37;
   public boolean updated = false;
   public static int kk = 0;

   public GUIArpgInfo(EntityPlayer player) {
      this.player = player;
   }

   public void updateScreen() {
      super.updateScreen();
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      if (this.mc != null) {
         int mX = mouseX - (this.width - 256) / 2;
         int mY = mouseY - (this.height - 256) / 2;
         this.drawDefaultBackground();
         super.drawScreen(mouseX, mouseY, partialTicks);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         int i = (this.width - 256) / 2;
         int j = (this.height - 256) / 2;
         this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
         this.drawTexturedModalRect(i, j, 0, 0, 256, 256);

         for (int ii = 0; ii < infos.size(); ii++) {
            ArpgInfoString info = infos.get(ii);
            this.fontRenderer
               .drawString(info.getFormattedText(), i + displX, j + 25 + 12 * ii, info.isLink && info.isInRange(mX, mY, ii) ? 8364777 : info.color);
         }

         if (!this.updated && kk < 10) {
            Minecraft.getMinecraft().mouseHelper.ungrabMouseCursor();
            this.updated = true;
            kk++;
         }
      }
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      super.mouseClicked(mouseX, mouseY, mouseButton);
      int mX = mouseX - (this.width - 256) / 2;
      int mY = mouseY - (this.height - 256) / 2;

      for (int ii = 0; ii < infos.size(); ii++) {
         ArpgInfoString info = infos.get(ii);
         if (info.isInRange(mX, mY, ii)) {
            info.onClick();
         }
      }
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      super.keyTyped(typedChar, keyCode);
      if (this.mc != null && (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode))) {
         this.mc.player.closeScreen();
      }
   }

   static {
      infos.add(new ArpgInfoString("Abstract RPG v1.33 Beta", 5111912));
      infos.add(new ArpgInfoString("Created by Vivern (VivernBar)", 657930));
      infos.add(new ArpgInfoString("Mod Wiki", 4214832).setOpenLink("https://abstractrpg.fandom.com/wiki/AbstractRPG_Wiki"));
      infos.add(new ArpgInfoString("Discord", 5661430).setOpenLink("https://discord.gg/ucdCkGRrRa"));
      infos.add(new ArpgInfoString("YouTube", 16711680).setOpenLink("https://www.youtube.com/@vivernbar"));
      infos.add(new ArpgInfoString("", 0));
      infos.add(new ArpgInfoString("Highly recommended to use:", 657930));
      infos.add(new ArpgInfoString("Just Enough Items,", 657930));
      infos.add(new ArpgInfoString("Any mods that adds Copper, Tin", 657930));
      infos.add(new ArpgInfoString("Steel, Lead and RF energy source", 657930));
      infos.add(new ArpgInfoString("(ThermalExpansion add it all at once)", 657930));
      infos.add(new ArpgInfoString("Any mods that adds liquid pipes", 657930));
      infos.add(new ArpgInfoString("", 0));
      infos.add(new ArpgInfoString("Not recommended:", 7211530));
      infos.add(new ArpgInfoString("Mods that adds unbalanced: weapons,", 7211530));
      infos.add(new ArpgInfoString("armor, features or unlimited flying", 7211530));
      infos.add(new ArpgInfoString("(jetpacks may break combat gameplay)", 7211530));
      infos.add(new ArpgInfoString("", 0));
      infos.add(new ArpgInfoString("May contain bugs", 3684408));
   }

   private static class ArpgInfoString {
      private String unlocalizedText;
      private int color;
      private boolean isLink;
      private String link;

      private ArpgInfoString(String unlocalizedText, int color) {
         this.unlocalizedText = unlocalizedText;
         this.color = color;
      }

      private ArpgInfoString setOpenLink(String link) {
         this.isLink = true;
         this.link = link;
         return this;
      }

      private String getFormattedText() {
         return this.isLink ? TextFormatting.UNDERLINE + this.unlocalizedText : this.unlocalizedText;
      }

      private boolean isInRange(int mX, int mY, int index) {
         int diapY1 = 25 + index * 12;
         int diapY2 = 25 + index * 12 + 12;
         return mX >= GUIArpgInfo.displX && mX <= 207 && mY >= diapY1 && mY < diapY2;
      }

      private void onClick() {
         if (this.isLink) {
            try {
               URI uri = new URI(this.link);
               this.openWebLink(uri);
            } catch (URISyntaxException var2) {
               LogManager.getLogger().error("Can't open url for {}", this.link, var2);
            }
         }
      }

      private void openWebLink(URI url) {
         try {
            Class<?> oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop").invoke(null);
            oclass.getMethod("browse", URI.class).invoke(object, url);
         } catch (Throwable var4) {
            Throwable throwable = var4.getCause();
            LogManager.getLogger().error("Couldn't open link: {}", throwable == null ? "<UNKNOWN>" : throwable.getMessage());
         }
      }
   }
}
