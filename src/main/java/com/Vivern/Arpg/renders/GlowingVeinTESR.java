//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.tileentity.TileGlowingVein;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GlowingVeinTESR extends TileEntitySpecialRenderer<TileGlowingVein> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("arpg:textures/te_glowing_vein.png");

   public void render(TileGlowingVein te, double xx, double yy, double zz, float partialTicks, int destroyStage, float alpha) {
      float lbx = OpenGlHelper.lastBrightnessX;
      float lby = OpenGlHelper.lastBrightnessY;
      GlStateManager.pushMatrix();
      GlStateManager.depthMask(false);
      GL11.glDisable(2896);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
      this.bindTexture(TEXTURE);
      double x = xx - 0.01;
      double y = yy - 0.01;
      double z = zz - 0.01;
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(x, y, z + 1.02).tex(0.0, 1.0).endVertex();
      bufferbuilder.pos(x + 1.02, y, z + 1.02).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(x + 1.02, y + 1.02, z + 1.02).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(x, y + 1.02, z + 1.02).tex(1.0, 1.0).endVertex();
      bufferbuilder.pos(x, y + 1.02, z).tex(0.0, 1.0).endVertex();
      bufferbuilder.pos(x + 1.02, y + 1.02, z).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(x + 1.02, y, z).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(x, y, z).tex(1.0, 1.0).endVertex();
      bufferbuilder.pos(x + 1.02, y + 1.02, z).tex(0.0, 1.0).endVertex();
      bufferbuilder.pos(x + 1.02, y + 1.02, z + 1.0).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(x + 1.02, y, z + 1.02).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(x + 1.02, y, z).tex(1.0, 1.0).endVertex();
      bufferbuilder.pos(x, y, z).tex(0.0, 1.0).endVertex();
      bufferbuilder.pos(x, y, z + 1.02).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(x, y + 1.02, z + 1.02).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(x, y + 1.02, z).tex(1.0, 1.0).endVertex();
      bufferbuilder.pos(x, y, z).tex(0.0, 1.0).endVertex();
      bufferbuilder.pos(x + 1.02, y, z).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(x + 1.02, y, z + 1.02).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(x, y, z + 1.02).tex(1.0, 1.0).endVertex();
      bufferbuilder.pos(x, y + 1.02, z + 1.02).tex(0.0, 1.0).endVertex();
      bufferbuilder.pos(x + 1.02, y + 1.02, z + 1.02).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(x + 1.02, y + 1.02, z).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(x, y + 1.02, z).tex(1.0, 1.0).endVertex();
      tessellator.draw();
      GlStateManager.depthMask(true);
      GL11.glEnable(2896);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbx, lby);
      GlStateManager.popMatrix();
   }
}
