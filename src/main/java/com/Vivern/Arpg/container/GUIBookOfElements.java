//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.container;

import com.Vivern.Arpg.main.ItemsElements;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.renders.ManaBar;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIBookOfElements extends GuiScreen {
   public static final ResourceLocation[] GUI_TEXTURES = new ResourceLocation[]{
      new ResourceLocation("arpg:textures/gui_book_of_elements_1.png"),
      new ResourceLocation("arpg:textures/gui_book_of_elements_2.png"),
      new ResourceLocation("arpg:textures/gui_book_of_elements_3.png")
   };
   public static final ResourceLocation EnergyRunes = new ResourceLocation("arpg:textures/gui_main_runes.png");
   public int bookMaxPage = 0;
   public int currentPage = 0;
   public ArrayList<ItemStack> stacksInBook = new ArrayList<>();

   public GUIBookOfElements(ItemStack book) {
      this.bookMaxPage = -1;
      NBTTagList tagList = NBTHelper.GetNbtTagList(book, "pages", 10);
      if (!tagList.isEmpty()) {
         for (NBTBase base : tagList) {
            if (base instanceof NBTTagCompound) {
               NBTTagCompound tagCompound = (NBTTagCompound)base;
               if (tagCompound.hasKey("item") && tagCompound.hasKey("metadata")) {
                  Item item = Item.getByNameOrId(tagCompound.getString("item"));
                  if (item != null) {
                     this.stacksInBook.add(new ItemStack(item, 1, tagCompound.getInteger("metadata")));
                     this.bookMaxPage++;
                  }
               }
            }
         }
      }

      this.playsound(Sounds.paper);
   }

   public GUIBookOfElements(NonNullList<ItemStack> books) {
      this.bookMaxPage = -1;

      for (ItemStack book : books) {
         if (!book.isEmpty()) {
            NBTTagList tagList = NBTHelper.GetNbtTagList(book, "pages", 10);
            if (!tagList.isEmpty()) {
               for (NBTBase base : tagList) {
                  if (base instanceof NBTTagCompound) {
                     NBTTagCompound tagCompound = (NBTTagCompound)base;
                     if (tagCompound.hasKey("item") && tagCompound.hasKey("metadata")) {
                        Item item = Item.getByNameOrId(tagCompound.getString("item"));
                        if (item != null) {
                           this.stacksInBook.add(new ItemStack(item, 1, tagCompound.getInteger("metadata")));
                           this.bookMaxPage++;
                        }
                     }
                  }
               }
            }
         }
      }

      this.playsound(Sounds.paper);
   }

   public void onGuiClosed() {
      super.onGuiClosed();
      this.playsound(Sounds.book_close);
   }

   public void playsound(SoundEvent soundEvent) {
      EntityPlayer player = Minecraft.getMinecraft().player;
      if (player != null) {
         player.world
            .playSound(
               player.posX,
               player.posY,
               player.posZ,
               soundEvent,
               SoundCategory.PLAYERS,
               0.8F,
               0.9F + player.getRNG().nextFloat() / 5.0F,
               false
            );
      }
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      super.mouseClicked(mouseX, mouseY, mouseButton);
      mouseX -= (this.width - 186) / 2;
      mouseY -= (this.height - 186) / 2;
      if (mouseX > 38 && mouseY > 156 && mouseX <= 61 && mouseY <= 169 && this.currentPage > 0) {
         this.currentPage--;
         this.playsound(Sounds.paper);
      }

      if (mouseX > 120 && mouseY > 156 && mouseX <= 143 && mouseY <= 169 && this.currentPage < this.bookMaxPage) {
         this.currentPage++;
         this.playsound(Sounds.paper);
      }
   }

   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      if (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode)) {
         this.mc.player.closeScreen();
      }
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      super.drawScreen(mouseX, mouseY, partialTicks);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      int i = (this.width - 186) / 2;
      int j = (this.height - 186) / 2;
      this.mc.getTextureManager().bindTexture(GUI_TEXTURES[this.currentPage % 3]);
      this.drawTexturedModalRect(i, j, 0, 0, 186, 186);
      mouseX -= (this.width - 186) / 2;
      mouseY -= (this.height - 186) / 2;
      if (this.currentPage > 0) {
         this.drawButton(i + 38, j + 156, false, mouseX > 38 && mouseY > 156 && mouseX <= 61 && mouseY <= 169);
      }

      if (this.currentPage < this.bookMaxPage) {
         this.drawButton(i + 120, j + 156, true, mouseX > 120 && mouseY > 156 && mouseX <= 143 && mouseY <= 169);
      }

      if (this.currentPage >= 0 && this.currentPage < this.stacksInBook.size()) {
         ItemStack itemstack = this.stacksInBook.get(this.currentPage);
         ItemsElements.ElementsPack elementsPack = ItemsElements.getAllElements(itemstack);
         String strName = itemstack.getDisplayName();
         int swidth = this.mc.fontRenderer.getStringWidth(strName);
         this.mc.fontRenderer.drawString(strName, i + 93 - swidth / 2, j + 11, 3026737);
         ItemsElements.EnumPurity purity = elementsPack.getPurity();
         String strPurity = purity.getDisplayName();
         int swidth2 = this.mc.fontRenderer.getStringWidth(strPurity);
         this.mc.fontRenderer.drawString(strPurity, i + 93 - swidth2 / 2, j + 20, purity.color);
         GlStateManager.pushMatrix();
         GlStateManager.translate(77 + i, 30 + j, 0.0F);
         GlStateManager.scale(2.0F, 2.0F, 2.0F);
         this.itemRender.renderItemAndEffectIntoGUI(this.mc.player, itemstack, 0, 0);
         GlStateManager.popMatrix();
         this.renderElements(i + 51, j + 79, elementsPack);
      }
   }

   public void drawButton(int i, int j, boolean isForward, boolean active) {
      int texi = 0;
      int texj = 192;
      if (active) {
         texi += 23;
      }

      if (!isForward) {
         texj += 13;
      }

      this.drawTexturedModalRect(i, j, texi, texj, 23, 13);
   }

   public void renderElements(int i, int j, ItemsElements.ElementsPack elementsPack) {
      int oneLength = 12;
      int space = 12;
      int horizontalIcons = 4;
      int oneLengthAndSpace = oneLength + space;
      int currentRendered = 0;

      for (int s = 1; s < 13; s++) {
         float amount = elementsPack.elementsAmount[s - 1];
         if (amount > 0.0F) {
            int iadd = oneLengthAndSpace * (currentRendered % horizontalIcons);
            int jadd = oneLengthAndSpace * (currentRendered / horizontalIcons);
            this.mc.getTextureManager().bindTexture(EnergyRunes);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            drawModalRectWithCustomSizedTexture(i + iadd, j + jadd, 0.0F, s * 12, 12, 12, 12.0F, 156.0F);
            String str = ManaBar.asString(amount);
            int swidth = this.mc.fontRenderer.getStringWidth(str);
            this.mc.fontRenderer.drawString(str, i + iadd + (oneLength - swidth) / 2, j + jadd + oneLength + 1, 3026737);
            currentRendered++;
         }
      }
   }
}
