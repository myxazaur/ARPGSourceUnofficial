package com.Vivern.Arpg.container;

import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.ColorConverters;
import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.util.vector.Vector4f;

public class GUIDebugColorBlock extends GuiScreen {
   public static final ResourceLocation pixel = new ResourceLocation("arpg:textures/pixel.png");
   public static final ResourceLocation gui_color_debug = new ResourceLocation("arpg:textures/gui_color_debug.png");
   public BlockPos pos;

   public GUIDebugColorBlock(BlockPos pos) {
      this.pos = pos;
   }

   public String getName() {
      World world = Minecraft.getMinecraft().world;
      if (world != null) {
         TileEntity e = world.getTileEntity(this.pos.up());
         if (e != null && e instanceof TileEntitySign) {
            TileEntitySign tes = (TileEntitySign)e;
            return tes.signText[0].getUnformattedComponentText();
         }
      }

      return null;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      super.drawScreen(mouseX, mouseY, partialTicks);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      int i = this.width / 2 - 128;
      int j = this.height / 2 + 128;
      this.mc.getTextureManager().bindTexture(pixel);
      String name = this.getName();
      if (name != null) {
         Vector4f color = Debugger.getDebugColor(name);
         float[] hsb = Color.RGBtoHSB((int)(color.x * 255.0F), (int)(color.y * 255.0F), (int)(color.z * 255.0F), new float[3]);
         int alpha = (int)MathHelper.clamp(color.w * 255.0F, 0.0F, 255.0F);
         int hue = (int)(hsb[0] * 255.0F);

         for (int sat = 0; sat < 256; sat++) {
            for (int bri = 0; bri < 256; bri++) {
               int intColor = Color.HSBtoRGB(hue / 255.0F, sat / 255.0F, bri / 255.0F);
               intColor &= 16777215;
               intColor |= alpha << 24;
               drawRect(i + sat, j - bri, i + sat + 1, j - bri + 1, intColor);
            }
         }

         i += 276;

         for (int hu = 0; hu < 256; hu++) {
            int intColor = Color.HSBtoRGB(hu / 255.0F, 1.0F, 1.0F);
            intColor |= -16777216;
            drawRect(i, j - hu, i + 10, j - hu + 1, intColor);
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         i -= 276;
         int sat = (int)(hsb[1] * 255.0F);
         int bri = (int)(hsb[2] * 255.0F);
         this.mc.getTextureManager().bindTexture(gui_color_debug);
         drawScaledCustomSizeModalRect(i + sat - 4, j - bri - 4, 0.0F, 0.0F, 9, 9, 9, 9, 16.0F, 16.0F);
         i += 276;
         drawScaledCustomSizeModalRect(i - 3, j - hue - 4, 9.0F, 0.0F, 7, 9, 7, 9, 16.0F, 16.0F);
         i += 20;

         for (int alp = 0; alp < 256; alp++) {
            int intColor = ColorConverters.RGBAtoDecimal(color.x, color.y, color.z, alp / 255.0F);
            drawRect(i, j - alp, i + 10, j - alp + 1, intColor);
         }

         drawScaledCustomSizeModalRect(i - 3, j - alpha - 4, 9.0F, 0.0F, 7, 9, 7, 9, 16.0F, 16.0F);
      }
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      String name = this.getName();
      if (name != null) {
         int x = mouseX - (this.width / 2 - 128);
         int y = mouseY - (this.height / 2 - 128) - 2;
         if (x < 265) {
            int sat = MathHelper.clamp(x, 0, 255);
            int bri = 255 - MathHelper.clamp(y, 0, 255);
            Vector4f color = Debugger.getDebugColor(name);
            float[] hsb = Color.RGBtoHSB((int)(color.x * 255.0F), (int)(color.y * 255.0F), (int)(color.z * 255.0F), new float[3]);
            int rgb = Color.HSBtoRGB(hsb[0], sat / 255.0F, bri / 255.0F);
            Vec3d rgbvec = ColorConverters.DecimaltoRGB(rgb);
            Debugger.debugColors.put(name, new Vector4f((float)rgbvec.x, (float)rgbvec.y, (float)rgbvec.z, color.w));
         } else if (x > 265 && x < 295) {
            int hue = 255 - MathHelper.clamp(y, 0, 255);
            Vector4f color = Debugger.getDebugColor(name);
            float[] hsb = Color.RGBtoHSB((int)(color.x * 255.0F), (int)(color.y * 255.0F), (int)(color.z * 255.0F), new float[3]);
            int rgb = Color.HSBtoRGB(hue / 255.0F, hsb[1], hsb[2]);
            Vec3d rgbvec = ColorConverters.DecimaltoRGB(rgb);
            Debugger.debugColors.put(name, new Vector4f((float)rgbvec.x, (float)rgbvec.y, (float)rgbvec.z, color.w));
         } else if (x > 295) {
            int alp = 255 - MathHelper.clamp(y, 0, 255);
            Vector4f color = Debugger.getDebugColor(name);
            Debugger.debugColors.put(name, new Vector4f(color.x, color.y, color.z, alp / 255.0F));
         }
      }
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
