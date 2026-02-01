package com.vivern.arpg.container;

import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.network.PacketTileClickToServer;
import com.vivern.arpg.tileentity.TilePuzzle;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIFrozenPuzzle extends GuiScreen {
   public static final ResourceLocation PUZZLE_GUI_TEXTURES = new ResourceLocation("arpg:textures/gui_puzzle.png");
   public static final ResourceLocation CUAD_TEXTURE = new ResourceLocation("arpg:textures/gui_puzzle_quad.png");
   public static final ResourceLocation ELEMENT_TEXTURE = new ResourceLocation("arpg:textures/gui_puzzle_element.png");
   public TilePuzzle tile;
   public Vec3d[] colors = new Vec3d[]{
      new Vec3d(0.5, 0.8, 1.0),
      new Vec3d(0.3, 0.6, 0.7),
      new Vec3d(0.2, 0.8, 0.9),
      new Vec3d(0.4, 0.9, 0.9),
      new Vec3d(0.3, 0.9, 1.0),
      new Vec3d(0.4, 0.9, 0.7),
      new Vec3d(0.5, 0.4, 0.8),
      new Vec3d(0.3, 0.6, 0.7),
      new Vec3d(0.3, 0.4, 1.0),
      new Vec3d(0.1, 0.4, 0.6),
      new Vec3d(0.1, 0.6, 0.5),
      new Vec3d(0.5, 0.8, 0.8),
      new Vec3d(0.1, 0.7, 0.9),
      new Vec3d(0.1, 0.1, 0.7),
      new Vec3d(0.2, 0.1, 0.5),
      new Vec3d(0.2, 0.6, 0.3),
      new Vec3d(0.3, 0.7, 0.7),
      new Vec3d(0.2, 0.6, 0.9),
      new Vec3d(0.3, 0.9, 0.7),
      new Vec3d(0.6, 0.6, 0.7),
      new Vec3d(0.5, 0.6, 0.5),
      new Vec3d(0.4, 0.2, 1.0),
      new Vec3d(0.8, 0.8, 0.9),
      new Vec3d(0.2, 0.3, 0.4),
      new Vec3d(0.3, 0.6, 0.3),
      new Vec3d(0.1, 1.0, 0.7),
      new Vec3d(0.1, 0.1, 0.1),
      new Vec3d(0.3, 0.1, 0.3),
      new Vec3d(0.2, 0.2, 0.2),
      new Vec3d(0.3, 0.5, 0.5),
      new Vec3d(0.1, 0.3, 0.5),
      new Vec3d(0.4, 0.4, 0.4),
      new Vec3d(0.5, 0.6, 0.7),
      new Vec3d(0.4, 0.4, 0.4),
      new Vec3d(0.3, 0.1, 0.5),
      new Vec3d(0.3, 0.4, 0.1),
      new Vec3d(0.7, 0.7, 0.8),
      new Vec3d(0.5, 0.5, 0.2),
      new Vec3d(0.6, 0.7, 0.7),
      new Vec3d(0.9, 0.9, 0.9)
   };

   public GUIFrozenPuzzle(TilePuzzle tile) {
      this.tile = tile;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      super.drawScreen(mouseX, mouseY, partialTicks);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      int i = (this.width - 144) / 2;
      int j = (this.height - 180) / 2;
      this.mc.getTextureManager().bindTexture(PUZZLE_GUI_TEXTURES);
      this.drawTexturedModalRect(i, j, 0, 0, 144, 180);
      i += 16;
      j += 16;
      int number = 0;

      for (int w = 0; w < 8; w++) {
         for (int h = 0; h < 8; h++) {
            if (w < this.tile.puzzleWidth && h < this.tile.puzzleHeight) {
               if (this.tile.puzzlePlate[w][h].center) {
                  int nn = number / 39;
                  Vec3d vec = this.colors[number - nn * 39];
                  GlStateManager.color((float)vec.x, (float)vec.y, (float)vec.z, 1.0F);

                  for (int[] ints : this.tile.puzzlePlate[w][h].list) {
                     if (ints[0] == 0 && ints[1] == 0) {
                        this.drawTexturedModalRect(i + w * 14, j + h * 14, 144, 14, 14, 14);
                     } else {
                        this.drawTexturedModalRect(i + w * 14 + ints[0] * 14, j + h * 14 + ints[1] * 14, 144, 0, 14, 14);
                     }
                  }
               }
            } else {
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               this.drawTexturedModalRect(i + w * 14, j + h * 14, 144, 28, 14, 14);
            }

            number++;
         }
      }

      if (!this.tile.elements.isEmpty()) {
         for (int[] intsx : this.tile.elements.peek().list) {
            int coorx = 55;
            int coory = 139;
            this.drawTexturedModalRect(i + coorx + intsx[0] * 2, j + coory + intsx[1] * 2, 144, 42, 2, 2);
         }
      }
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      int x = mouseX - ((this.width - 144) / 2 + 16);
      int y = mouseY - ((this.height - 180) / 2 + 16);
      PacketTileClickToServer packet = new PacketTileClickToServer();
      packet.writeints(
         this.tile.getPos().getX(),
         this.tile.getPos().getY(),
         this.tile.getPos().getZ(),
         x / 14,
         y / 14,
         mouseButton
      );
      PacketHandler.NETWORK.sendToServer(packet);
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      if (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode)) {
         this.mc.player.closeScreen();
      }
   }
}
