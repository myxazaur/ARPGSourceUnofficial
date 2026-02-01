package com.vivern.arpg.container;

import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.network.PacketIWeaponGuiClickToServer;
import com.vivern.arpg.network.PacketIWeaponStringToServer;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ResourceLocation;

public class GUISummon extends GuiScreen {
   public static final int[] LEGAL_KEY_CODES = new int[]{203, 205, 14, 211, 199, 207};
   public static final ResourceLocation GUI_TEXTURES = new ResourceLocation("arpg:textures/gui_summon.png");
   public EntityPlayer player;
   public ItemStack wand;
   public int xSize = 62;
   public int ySize = 62;
   public int modeSelected = 0;
   public GuiTextField filterMinionEdit;
   public GuiTextField filterHostilEdit;
   public ArrayList<FilterDraw> listMinion = new ArrayList<>();
   public ArrayList<FilterDraw> listHostil = new ArrayList<>();
   public int filterModeSelecMinion = 1;
   public int filterModeSelecHostil = 1;
   public int filterSelecMinion = 0;
   public int filterSelecHostil = 0;
   public boolean filterModeInvertedMinion = false;
   public boolean filterModeInvertedHostil = false;

   public GUISummon(EntityPlayer player, ItemStack wand) {
      this.player = player;
      this.wand = wand;
      this.modeSelected = NBTHelper.GetNBTint(wand, "mode");
      this.filterSelecMinion = NBTHelper.GetNBTint(wand, "selecFilterMinion");
      this.filterSelecHostil = NBTHelper.GetNBTint(wand, "selecFilterHostil");
      String filterType = "filters";
      if (wand.hasTagCompound() && wand.getTagCompound().hasKey(filterType)) {
         NBTTagCompound filtersTAG = wand.getTagCompound().getCompoundTag(filterType);

         for (int i = 0; i < 99; i++) {
            if (filtersTAG.hasKey("" + i)) {
               NBTTagCompound filter = filtersTAG.getCompoundTag("" + i);
               if (filter.hasKey("mode") && filter.hasKey("data") && filter.hasKey("inverted")) {
                  FilterDraw draw = new FilterDraw(
                     filter.getInteger("mode"), i, filter.getString("data"), false, filter.getBoolean("inverted")
                  );
                  this.listMinion.add(draw);
               }
            }
         }
      }

      filterType = "filtershostile";
      if (wand.hasTagCompound() && wand.getTagCompound().hasKey(filterType)) {
         NBTTagCompound filtersTAG = wand.getTagCompound().getCompoundTag(filterType);

         for (int ix = 0; ix < 99; ix++) {
            if (filtersTAG.hasKey("" + ix)) {
               NBTTagCompound filter = filtersTAG.getCompoundTag("" + ix);
               if (filter.hasKey("mode") && filter.hasKey("data") && filter.hasKey("inverted")) {
                  FilterDraw draw = new FilterDraw(
                     filter.getInteger("mode"), ix, filter.getString("data"), true, filter.getBoolean("inverted")
                  );
                  this.listHostil.add(draw);
               }
            }
         }
      }
   }

   public void initGui() {
      int i = (this.width - this.xSize) / 2;
      int j = (this.height - this.ySize) / 2;
      this.filterMinionEdit = new GuiTextField(0, this.fontRenderer, i + 97, j - 60, 118, 18);
      this.filterMinionEdit.setMaxStringLength(64);
      this.filterHostilEdit = new GuiTextField(0, this.fontRenderer, i + 97 - 252, j - 60, 118, 18);
      this.filterHostilEdit.setMaxStringLength(64);
   }

   public void updateScreen() {
      this.filterMinionEdit.updateCursorCounter();
      this.filterHostilEdit.updateCursorCounter();
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      int i = (this.width - this.xSize) / 2;
      int j = (this.height - this.ySize) / 2;
      this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
      this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
      if (this.modeSelected == 0) {
         this.drawTexturedModalRect(i + 24, j + 1, 62, 54, 15, 15);
      } else if (this.modeSelected == 1) {
         this.drawTexturedModalRect(i + 40, j + 9, 62, 54, 15, 15);
      } else if (this.modeSelected == 2) {
         this.drawTexturedModalRect(i + 46, j + 26, 62, 54, 15, 15);
      } else if (this.modeSelected == 3) {
         this.drawTexturedModalRect(i + 34, j + 42, 62, 54, 15, 15);
      } else if (this.modeSelected == 4) {
         this.drawTexturedModalRect(i + 14, j + 42, 62, 54, 15, 15);
      } else if (this.modeSelected == 5) {
         this.drawTexturedModalRect(i + 1, j + 27, 62, 54, 15, 15);
      } else if (this.modeSelected == 6) {
         this.drawTexturedModalRect(i + 6, j + 9, 62, 54, 15, 15);
      }

      int addW = 42 + i;
      int addH = -100 + j;

      for (FilterDraw draw : this.listMinion) {
         this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
         this.drawTexturedModalRect(55 + addW, addH + 60 + draw.number * 20, 62, this.filterSelecMinion == draw.number ? 36 : 18, 118, 18);
         this.drawTexturedModalRect(175 + addW, addH + 63 + draw.number * 20, 89, 54, 12, 12);
         this.drawTexturedModalRect(41 + addW, addH + 63 + draw.number * 20, 89 + draw.mode * 12, draw.inverted ? 66 : 54, 12, 12);
         this.fontRenderer.drawString(draw.text, 59 + addW, addH + 66 + draw.number * 20, 16777215);
      }

      this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
      this.drawTexturedModalRect(175 + addW, addH + 43, 77, 54, 12, 12);
      this.drawTexturedModalRect(41 + addW, addH + 43, 89 + this.filterModeSelecMinion * 12, this.filterModeInvertedMinion ? 66 : 54, 12, 12);
      addW = 42 + i - 252;

      for (FilterDraw draw : this.listHostil) {
         this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
         this.drawTexturedModalRect(55 + addW, addH + 60 + draw.number * 20, 62, this.filterSelecHostil == draw.number ? 36 : 18, 118, 18);
         this.drawTexturedModalRect(175 + addW, addH + 63 + draw.number * 20, 89, 54, 12, 12);
         this.drawTexturedModalRect(41 + addW, addH + 63 + draw.number * 20, 89 + draw.mode * 12, draw.inverted ? 66 : 54, 12, 12);
         this.fontRenderer.drawString(draw.text, 59 + addW, addH + 66 + draw.number * 20, 16777215);
      }

      this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
      this.drawTexturedModalRect(175 + addW, addH + 43, 77, 54, 12, 12);
      this.drawTexturedModalRect(41 + addW, addH + 43, 89 + this.filterModeSelecHostil * 12, this.filterModeInvertedHostil ? 66 : 54, 12, 12);
      this.filterMinionEdit.drawTextBox();
      this.filterHostilEdit.drawTextBox();
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      int mx = mouseX - (this.width - this.xSize) / 2;
      int my = mouseY - (this.height - this.ySize) / 2;
      PacketIWeaponGuiClickToServer packet = new PacketIWeaponGuiClickToServer();
      packet.writeints(mx, my, mouseButton);
      PacketHandler.NETWORK.sendToServer(packet);
      if (this.filterMinionEdit.getVisible()) {
         this.filterMinionEdit.mouseClicked(mouseX, mouseY, mouseButton);
      }

      if (this.filterHostilEdit.getVisible()) {
         this.filterHostilEdit.mouseClicked(mouseX, mouseY, mouseButton);
      }

      if (my > 0 && my <= 16 && mx > 23 && mx <= 39) {
         this.modeSelected = 0;
      } else if (my > 8 && my <= 24 && mx > 39 && mx <= 55) {
         if (mouseButton == 0) {
            this.modeSelected = 1;
         }
      } else if (my > 25 && my <= 41 && mx > 45 && mx <= 61) {
         if (mouseButton == 0) {
            this.modeSelected = 2;
         }
      } else if (my > 41 && my <= 57 && mx > 32 && mx <= 48) {
         if (mouseButton == 0) {
            this.modeSelected = 3;
         }
      } else if (my > 41 && my <= 57 && mx > 13 && mx <= 29) {
         if (mouseButton == 0) {
            this.modeSelected = 4;
         }
      } else if (my > 26 && my <= 42 && mx > 1 && mx <= 17) {
         if (mouseButton == 0) {
            this.modeSelected = 5;
         }
      } else if (my > 8 && my <= 24 && mx > 6 && mx <= 22) {
         if (mouseButton == 0) {
            this.modeSelected = 6;
         }
      } else if (mx >= 83 && mx <= 94 && my >= -57 && my <= -46) {
         if (mouseButton == 0) {
            this.filterModeSelecMinion++;
            if (this.filterModeSelecMinion > 5) {
               this.filterModeSelecMinion = 1;
            }
         } else {
            this.filterModeInvertedMinion = !this.filterModeInvertedMinion;
         }
      } else if (mx >= -169 && mx <= -158 && my >= -57 && my <= -46) {
         if (mouseButton == 0) {
            this.filterModeSelecHostil++;
            if (this.filterModeSelecHostil > 5) {
               this.filterModeSelecHostil = 1;
            }
         } else {
            this.filterModeInvertedHostil = !this.filterModeInvertedHostil;
         }
      } else if (mx >= 217 && mx <= 228 && my >= -57 && my <= -46) {
         int max = 0;
         if (!this.listMinion.isEmpty()) {
            for (FilterDraw draw : this.listMinion) {
               if (draw.number > max) {
                  max = draw.number + 1;
               }
            }
         }

         if (max < 9) {
            PacketIWeaponStringToServer packet2 = new PacketIWeaponStringToServer();
            String inv = this.filterModeInvertedMinion ? "1" : "0";
            packet2.write(this.filterModeSelecMinion + "0" + inv + this.filterMinionEdit.getText());
            PacketHandler.NETWORK.sendToServer(packet2);
            this.listMinion
               .add(new FilterDraw(this.filterModeSelecMinion, max, this.filterMinionEdit.getText(), false, this.filterModeInvertedMinion));
         }
      } else if (mx >= -35 && mx <= -24 && my >= -57 && my <= -46) {
         int maxx = 0;
         if (!this.listHostil.isEmpty()) {
            for (FilterDraw drawx : this.listHostil) {
               if (drawx.number > maxx) {
                  maxx = drawx.number + 1;
               }
            }
         }

         if (maxx < 9) {
            PacketIWeaponStringToServer packet2 = new PacketIWeaponStringToServer();
            String inv = this.filterModeInvertedHostil ? "1" : "0";
            packet2.write(this.filterModeSelecHostil + "1" + inv + this.filterHostilEdit.getText());
            PacketHandler.NETWORK.sendToServer(packet2);
            this.listHostil
               .add(new FilterDraw(this.filterModeSelecHostil, maxx, this.filterHostilEdit.getText(), true, this.filterModeInvertedHostil));
         }
      } else if (mx >= 97 && mx <= 215) {
         for (FilterDraw drawxx : this.listMinion) {
            int startY = -40 + drawxx.number * 20;
            if (my >= startY && my <= startY + 18) {
               if (this.filterSelecMinion == drawxx.number) {
                  this.filterSelecMinion = -1;
               } else {
                  this.filterSelecMinion = drawxx.number;
               }
               break;
            }
         }
      } else if (mx >= -155 && mx <= -38) {
         for (FilterDraw drawxxx : this.listHostil) {
            int startY = -40 + drawxxx.number * 20;
            if (my >= startY && my <= startY + 18) {
               if (this.filterSelecHostil == drawxxx.number) {
                  this.filterSelecHostil = -1;
               } else {
                  this.filterSelecHostil = drawxxx.number;
               }
               break;
            }
         }
      } else if (mx >= 217 && mx <= 228) {
         FilterDraw removing = null;

         for (FilterDraw drawxxxx : this.listMinion) {
            int startY = -37 + drawxxxx.number * 20;
            if (my >= startY && my <= startY + 12) {
               removing = drawxxxx;
               break;
            }
         }

         if (removing != null) {
            for (FilterDraw drawxxxxx : this.listMinion) {
               if (drawxxxxx.number > removing.number) {
                  drawxxxxx.number--;
               }
            }

            this.listMinion.remove(removing);
         }
      } else if (mx >= -35 && mx <= -24) {
         FilterDraw removing = null;

         for (FilterDraw drawxxxxxx : this.listHostil) {
            int startY = -37 + drawxxxxxx.number * 20;
            if (my >= startY && my <= startY + 12) {
               removing = drawxxxxxx;
               break;
            }
         }

         if (removing != null) {
            for (FilterDraw drawxxxxxxx : this.listHostil) {
               if (drawxxxxxxx.number > removing.number) {
                  drawxxxxxxx.number--;
               }
            }

            this.listHostil.remove(removing);
         }
      }
   }

   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      if (!this.filterMinionEdit.isFocused()
         && !this.filterHostilEdit.isFocused()
         && (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode))) {
         this.mc.player.closeScreen();
      }

      if (isValidCharacterForName(typedChar, keyCode)) {
         if (this.filterMinionEdit.getVisible()) {
            this.filterMinionEdit.textboxKeyTyped(typedChar, keyCode);
         }

         if (this.filterHostilEdit.getVisible()) {
            this.filterHostilEdit.textboxKeyTyped(typedChar, keyCode);
         }
      }
   }

   private static boolean isValidCharacterForName(char p_190301_0_, int p_190301_1_) {
      boolean flag = true;

      for (int i : LEGAL_KEY_CODES) {
         if (i == p_190301_1_) {
            return true;
         }
      }

      for (char c0 : ChatAllowedCharacters.ILLEGAL_STRUCTURE_CHARACTERS) {
         if (c0 == p_190301_0_) {
            flag = false;
            break;
         }
      }

      return flag;
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public class FilterDraw {
      public int mode;
      public int number;
      public String text;
      public boolean hostile;
      public boolean inverted;

      public FilterDraw(int mode, int number, String text, boolean hostile, boolean inverted) {
         this.mode = mode;
         this.number = number;
         this.text = text;
         this.hostile = hostile;
         this.inverted = inverted;
      }
   }
}
