package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.elements.models.AbstractMobModel;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderCustomArrow<T extends EntityArrow> extends Render<T> {
   public ResourceLocation tex;
   public float scale;
   public int light;
   public boolean tilt;
   public boolean horizontalShake;
   public boolean blend;

   public RenderCustomArrow(RenderManager renderManagerIn, ResourceLocation tex, float scale, int light, boolean tilt, boolean horizontalShake, boolean blend) {
      super(renderManagerIn);
      this.tex = tex;
      this.scale = scale;
      this.light = light;
      this.tilt = tilt;
      this.horizontalShake = horizontalShake;
      this.blend = blend;
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

      if (this.blend) {
         AbstractMobModel.alphaGlowDisable();
         GlStateManager.enableBlend();
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
      float f9 = entity.arrowShake - partialTicks;
      if (f9 > 0.0F) {
         float f10 = -MathHelper.sin(f9 * 3.0F) * f9;
         if (this.horizontalShake) {
            GlStateManager.rotate(f10, 0.0F, 1.0F, 0.0F);
         } else {
            GlStateManager.rotate(f10, 0.0F, 0.0F, 1.0F);
         }
      }

      if (this.tilt) {
         GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
      }

      GlStateManager.scale(this.scale, this.scale, this.scale);
      GlStateManager.translate(-4.0F, 0.0F, 0.0F);
      if (this.renderOutlines) {
         GlStateManager.enableColorMaterial();
         GlStateManager.enableOutlineMode(this.getTeamColor(entity));
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
      bufferbuilder.pos(-7.0, 2.0, -2.0).tex(0.0, 0.46875).endVertex();
      bufferbuilder.pos(-7.0, 2.0, 2.0).tex(0.15625, 0.46875).endVertex();
      bufferbuilder.pos(-7.0, -2.0, 2.0).tex(0.15625, 0.625).endVertex();
      bufferbuilder.pos(-7.0, -2.0, -2.0).tex(0.0, 0.625).endVertex();
      tessellator.draw();
      GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.glNormal3f(0.0F, 0.0F, 0.05625F);
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-8.0, -2.0, 0.0).tex(0.0, 0.3125).endVertex();
      bufferbuilder.pos(8.0, -2.0, 0.0).tex(0.5, 0.3125).endVertex();
      bufferbuilder.pos(8.0, 2.0, 0.0).tex(0.5, 0.46875).endVertex();
      bufferbuilder.pos(-8.0, 2.0, 0.0).tex(0.0, 0.46875).endVertex();
      tessellator.draw();
      GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.glNormal3f(0.0F, 0.0F, 0.05625F);
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-8.0, -2.0, 0.0).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(8.0, -2.0, 0.0).tex(0.5, 0.0).endVertex();
      bufferbuilder.pos(8.0, 2.0, 0.0).tex(0.5, 0.15625).endVertex();
      bufferbuilder.pos(-8.0, 2.0, 0.0).tex(0.0, 0.15625).endVertex();
      tessellator.draw();
      GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.glNormal3f(0.0F, 0.0F, 0.05625F);
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-8.0, -2.0, 0.0).tex(0.0, 0.46875).endVertex();
      bufferbuilder.pos(8.0, -2.0, 0.0).tex(0.5, 0.46875).endVertex();
      bufferbuilder.pos(8.0, 2.0, 0.0).tex(0.5, 0.3125).endVertex();
      bufferbuilder.pos(-8.0, 2.0, 0.0).tex(0.0, 0.3125).endVertex();
      tessellator.draw();
      GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.glNormal3f(0.0F, 0.0F, 0.05625F);
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-8.0, -2.0, 0.0).tex(0.0, 0.15625).endVertex();
      bufferbuilder.pos(8.0, -2.0, 0.0).tex(0.5, 0.15625).endVertex();
      bufferbuilder.pos(8.0, 2.0, 0.0).tex(0.5, 0.0).endVertex();
      bufferbuilder.pos(-8.0, 2.0, 0.0).tex(0.0, 0.0).endVertex();
      tessellator.draw();
      if (this.renderOutlines) {
         GlStateManager.disableOutlineMode();
         GlStateManager.disableColorMaterial();
      }

      if (this.light > -1) {
         GL11.glEnable(2896);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbX, lbY);
      }

      if (this.blend) {
         GlStateManager.disableBlend();
      }

      GlStateManager.disableRescaleNormal();
      GlStateManager.enableLighting();
      GlStateManager.popMatrix();
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   protected ResourceLocation getEntityTexture(EntityArrow entity) {
      return this.tex;
   }
}
