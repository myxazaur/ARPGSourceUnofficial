//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;

public class RenderLikeArrow<T extends Entity> extends Render<T> {
   public ResourceLocation tex;
   public float scale;
   public int light;
   public boolean checkAdvanced;

   public RenderLikeArrow(RenderManager renderManagerIn, ResourceLocation tex, float scale, int light, boolean checkAdvanced) {
      super(renderManagerIn);
      this.tex = tex;
      this.scale = scale;
      this.light = light;
      this.checkAdvanced = checkAdvanced;
   }

   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
      this.bindEntityTexture(entity);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.pushMatrix();
      GlStateManager.disableLighting();
      GlStateManager.translate((float)x, (float)y, (float)z);
      GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
      float lbX = OpenGlHelper.lastBrightnessX;
      float lbY = OpenGlHelper.lastBrightnessY;
      if (this.light > -1) {
         GL11.glDisable(2896);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, this.light, this.light);
      }

      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      int i = 0;
      float f = 0.0F;
      float f1 = 0.5F;
      float f2 = 0.0F;
      float f3 = 0.15625F;
      float f4 = 0.0F;
      float f5 = 0.15625F;
      float f6 = 0.15625F;
      float f7 = 0.3125F;
      float f8 = 0.05625F;
      GlStateManager.enableRescaleNormal();
      GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.scale(this.scale, this.scale, this.scale);
      GlStateManager.translate(-4.0F, 0.0F, 0.0F);
      if (this.renderOutlines) {
         GlStateManager.enableColorMaterial();
         GlStateManager.enableOutlineMode(this.getTeamColor(entity));
      }

      boolean saveinstanceof = false;
      if (this.checkAdvanced && entity instanceof IRenderOptions) {
         saveinstanceof = true;
         ((IRenderOptions)entity).doOptions();
      }

      GlStateManager.glNormal3f(0.05625F, 0.0F, 0.0F);
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-7.0, -2.0, -2.0).tex(0.0, 0.15625).endVertex();
      bufferbuilder.pos(-7.0, -2.0, 2.0).tex(0.15625, 0.15625).endVertex();
      bufferbuilder.pos(-7.0, 2.0, 2.0).tex(0.15625, 0.3125).endVertex();
      bufferbuilder.pos(-7.0, 2.0, -2.0).tex(0.0, 0.3125).endVertex();
      tessellator.draw();
      GlStateManager.glNormal3f(-0.05625F, 0.0F, 0.0F);
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-7.0, 2.0, -2.0).tex(0.0, 0.15625).endVertex();
      bufferbuilder.pos(-7.0, 2.0, 2.0).tex(0.15625, 0.15625).endVertex();
      bufferbuilder.pos(-7.0, -2.0, 2.0).tex(0.15625, 0.3125).endVertex();
      bufferbuilder.pos(-7.0, -2.0, -2.0).tex(0.0, 0.3125).endVertex();
      tessellator.draw();

      for (int j = 0; j < 4; j++) {
         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.glNormal3f(0.0F, 0.0F, 0.05625F);
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-8.0, -2.0, 0.0).tex(0.0, 0.0).endVertex();
         bufferbuilder.pos(8.0, -2.0, 0.0).tex(0.5, 0.0).endVertex();
         bufferbuilder.pos(8.0, 2.0, 0.0).tex(0.5, 0.15625).endVertex();
         bufferbuilder.pos(-8.0, 2.0, 0.0).tex(0.0, 0.15625).endVertex();
         tessellator.draw();
      }

      if (this.renderOutlines) {
         GlStateManager.disableOutlineMode();
         GlStateManager.disableColorMaterial();
      }

      if (this.light > -1) {
         GL11.glEnable(2896);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbX, lbY);
      }

      GlStateManager.disableRescaleNormal();
      GlStateManager.enableLighting();
      if (saveinstanceof) {
         ((IRenderOptions)entity).undoOptions();
      }

      GlStateManager.popMatrix();
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   protected ResourceLocation getEntityTexture(Entity entity) {
      return this.tex;
   }

   public static class RenderLikeArrowFactory implements IRenderFactory {
      public ResourceLocation tex;
      public float scale = 0.05625F;
      public int light = -1;
      public boolean checkAdvanced = false;

      public RenderLikeArrowFactory(ResourceLocation tex) {
         this.tex = tex;
      }

      public RenderLikeArrowFactory(ResourceLocation tex, float scale, int light, boolean checkAdvanced) {
         this.tex = tex;
         this.scale = scale;
         this.light = light;
         this.checkAdvanced = checkAdvanced;
      }

      public Render createRenderFor(RenderManager manager) {
         return new RenderLikeArrow(manager, this.tex, this.scale, this.light, this.checkAdvanced);
      }
   }
}
