package com.Vivern.Arpg.container;

import com.Vivern.Arpg.arpgamemodes.SurvivorGamestyleWatcher;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.Vec2i;
import com.Vivern.Arpg.network.PacketDoSomethingToServer;
import com.Vivern.Arpg.network.PacketHandler;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

public class GUISurvivorEnchant extends GuiScreen {
   public static final ResourceLocation GUI_TEXTURES = new ResourceLocation("arpg:textures/gui_survivor_enchant.png");
   public EntityPlayer player;
   public Enchantment[] enchantments;
   public int[] enchantmentsCost;
   public int xSize = 256;
   public int ySize = 256;

   public GUISurvivorEnchant(EntityPlayer player, Enchantment[] enchantments) {
      this.player = player;
      this.enchantments = enchantments;
      this.enchantmentsCost = new int[enchantments.length];

      for (int i = 0; i < enchantments.length; i++) {
         this.enchantmentsCost[i] = SurvivorGamestyleWatcher.howMuchLevelsNeedToEnchant(player.getHeldItemMainhand(), enchantments[i]);
      }
   }

   public static Vec2i enchantCoordinates(Enchantment enchantment) {
      int psize = 48;
      if (enchantment == EnchantmentInit.MIGHT) {
         return new Vec2i(0, 0);
      } else if (enchantment == EnchantmentInit.IMPULSE) {
         return new Vec2i(1 * psize, 0);
      } else if (enchantment == EnchantmentInit.RANGE) {
         return new Vec2i(2 * psize, 0);
      } else if (enchantment == EnchantmentInit.ACCURACY) {
         return new Vec2i(3 * psize, 0);
      } else if (enchantment == EnchantmentInit.RAPIDITY) {
         return new Vec2i(4 * psize, 0);
      } else if (enchantment == EnchantmentInit.RELOADING) {
         return new Vec2i(0, 1 * psize);
      } else if (enchantment == EnchantmentInit.REUSE) {
         return new Vec2i(1 * psize, 1 * psize);
      } else if (enchantment == EnchantmentInit.SPECIAL) {
         return new Vec2i(2 * psize, 1 * psize);
      } else if (enchantment == EnchantmentInit.SORCERY) {
         return new Vec2i(3 * psize, 1 * psize);
      } else {
         return enchantment == EnchantmentInit.WITCHERY ? new Vec2i(4 * psize, 1 * psize) : new Vec2i(0, 2 * psize);
      }
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      int i = this.width / 2;
      int j = this.height / 2;
      int oneLength = 48;
      int space = 24;
      int fullLength = oneLength * this.enchantments.length + space * (this.enchantments.length - 1);
      int halfLength = fullLength / 2;
      int currentTranslation = -halfLength;

      for (int j2 = 0; j2 < this.enchantments.length; j2++) {
         this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
         Enchantment enchantment = this.enchantments[j2];
         Vec2i vec = enchantCoordinates(enchantment);
         this.drawTexturedModalRect(i + currentTranslation, j - space, vec.x, vec.y, oneLength, oneLength);
         if (this.enchantmentsCost[j2] >= 0) {
            boolean green = this.player.experienceLevel >= this.enchantmentsCost[j2];
            this.drawTexturedModalRect(i + currentTranslation + 14, j - space + 51, 240, green ? 0 : 13, 13, 13);
            String text = "" + this.enchantmentsCost[j2];
            int strX = i + currentTranslation + 25;
            int strY = j - space + 54;
            this.fontRenderer.drawString(text, strX - 1, strY, 2957570);
            this.fontRenderer.drawString(text, strX + 1, strY, 2957570);
            this.fontRenderer.drawString(text, strX, strY - 1, 2957570);
            this.fontRenderer.drawString(text, strX, strY + 1, 2957570);
            this.fontRenderer.drawString(text, strX, strY, green ? 13172623 : 10827601);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         }

         currentTranslation += oneLength + space;
      }

      this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
      this.drawTexturedModalRect(this.width - 64, 32, 224, 224, 32, 32);
      if (this.player.getCooldownTracker().hasCooldown(Items.GOLD_INGOT)) {
         float cooldown = this.player.getCooldownTracker().getCooldown(Items.GOLD_INGOT, partialTicks);
         int pixels = Math.round(32.0F * cooldown);
         this.drawTexturedModalRect(this.width - 64, 64 - pixels, 192, 256 - pixels, 32, pixels);
      }
   }

   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      super.mouseClicked(mouseX, mouseY, mouseButton);
      int i = this.width / 2;
      int j = this.height / 2;
      int oneLength = 48;
      int space = 24;
      int fullLength = oneLength * this.enchantments.length + space * (this.enchantments.length - 1);
      int halfLength = fullLength / 2;
      int currentTranslation = -halfLength;

      for (int j2 = 0; j2 < this.enchantments.length; j2++) {
         Enchantment enchantment = this.enchantments[j2];
         Vec2i vec = enchantCoordinates(enchantment);
         if (mouseX >= i + currentTranslation && mouseX < i + currentTranslation + oneLength && mouseY >= j - space && mouseY < j - space + oneLength) {
            PacketDoSomethingToServer packet = new PacketDoSomethingToServer();
            packet.writeargs(j2, 0.0, 0.0, 0.0, 0.0, 0.0, 9);
            PacketHandler.NETWORK.sendToServer(packet);
            this.mc.displayGuiScreen((GuiScreen)null);
            if (this.mc.currentScreen == null) {
               this.mc.setIngameFocus();
            }

            return;
         }

         currentTranslation += oneLength + space;
      }

      if (mouseX >= this.width - 64 && mouseX < this.width - 64 + 32 && mouseY >= 32 && mouseY < 64) {
         PacketDoSomethingToServer packet = new PacketDoSomethingToServer();
         packet.writeargs(0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 9);
         PacketHandler.NETWORK.sendToServer(packet);
         this.mc.displayGuiScreen((GuiScreen)null);
         if (this.mc.currentScreen == null) {
            this.mc.setIngameFocus();
         }
      }
   }
}
