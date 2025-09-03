//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.weather;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderHail extends WeatherRenderList {
   static ResourceLocation texture = new ResourceLocation("arpg:textures/clouds2.png");

   public RenderHail(WeatherPhenomenon weather) {
      super(weather);
   }

   @Override
   public void render(double x, double y, double z, float rotationY, float rotationX, float partialTicks) {
      if (!this.isListsEmpty()) {
         for (int i = 0; i < this.listX.size(); i++) {
            this.doRender(this.listX.get(i) - x, HailWeather.height - y, this.listZ.get(i) - z, partialTicks, this.listY.get(i), rotationY, rotationX);
         }
      }
   }

   @Override
   public void doRender(double x, double y, double z, float partialTicks, double noisevalue, float rotationY, float rotationX) {
      RenderManager rmanager = Minecraft.getMinecraft().getRenderManager();
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y, (float)z);
      GlStateManager.enableRescaleNormal();
      GL11.glEnable(3042);
      GlStateManager.depthMask(false);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.rotate(-rotationY, 0.0F, 1.0F, 0.0F);
      if (rmanager.options != null) {
         GlStateManager.rotate((rmanager.options.thirdPersonView == 2 ? -1 : 1) * rotationX, 1.0F, 0.0F, 0.0F);
      }

      GlStateManager.rotate((float)(Math.sin(noisevalue) * 880.0), 0.0F, 0.0F, 1.0F);
      Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
      float scale = (float)(8.5 + noisevalue * 110.0);
      GlStateManager.scale(scale, scale, scale);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-1.0, -1.0, 0.0).tex(0.0, 1.0).endVertex();
      bufferbuilder.pos(-1.0, 1.0, 0.0).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(1.0, 1.0, 0.0).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(1.0, -1.0, 0.0).tex(1.0, 1.0).endVertex();
      tessellator.draw();
      GlStateManager.depthMask(true);
      GL11.glDisable(3042);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.enableLighting();
      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
   }
}
