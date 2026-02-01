package com.vivern.arpg.renders;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

// FIX: change `Render<T>` to `Render<BulletParticle>`
@SideOnly(Side.CLIENT)
public class RenderBulletParticle<T extends BulletParticle> extends Render<BulletParticle> {
   public RenderBulletParticle(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   public void doRender(BulletParticle entity, double x, double y, double z, float entityYaw, float partialTicks) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y, (float)z);
      GlStateManager.rotate(-entity.rotatYaw, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(entity.rotatPitch, 1.0F, 0.0F, 0.0F);
      GlStateManager.disableCull();
      GL11.glEnable(3042);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableTexture2D();
      GlStateManager.shadeModel(7425);
      GlStateManager.disableAlpha();
      GlStateManager.depthMask(false);
      float length2 = entity.length / 2.0F;
      float width = entity.scale / 2.0F;
      float progress = Math.min(entity.ticksExisted + partialTicks, (float)entity.livetime) / entity.livetime;
      float progressUnsafe = (entity.ticksExisted + partialTicks) / entity.livetime;
      float progressHide = MathHelper.clamp((float)entity.ticksExisted - entity.livetime + partialTicks, 0.0F, entity.hidetime) / entity.hidetime;
      float len2lenProgr = -length2 + Math.max(Math.min(entity.length * progressUnsafe - 0.8F, entity.length * progress), 0.0F);
      float mwidth2 = -width / 2.0F;
      float r0 = entity.Red;
      float g0 = entity.Green;
      float b0 = entity.Blue;
      float r1 = entity.smokeRed;
      float g1 = entity.smokeGreen;
      float b1 = entity.smokeBlue;
      float a1 = entity.smokeAlpha * (1.0F - progressHide);
      GlStateManager.pushMatrix();
      GlStateManager.translate(
         (float)entity.smokeDisplace.x * progressHide,
         (float)entity.smokeDisplace.y * progressHide,
         (float)entity.smokeDisplace.z * progressHide
      );
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
      bufferbuilder.pos(-width, mwidth2, -length2).color(r0, g0, b0, 0.0F).endVertex();
      bufferbuilder.pos(-width, mwidth2, len2lenProgr).color(r0, g0, b0, 0.0F).endVertex();
      bufferbuilder.pos(0.0, 0.0, len2lenProgr).color(r1, g1, b1, a1).endVertex();
      bufferbuilder.pos(0.0, 0.0, -length2).color(r0, g0, b0, 0.0F).endVertex();
      bufferbuilder.pos(0.0, 0.0, -length2).color(r0, g0, b0, 0.0F).endVertex();
      bufferbuilder.pos(0.0, 0.0, len2lenProgr).color(r1, g1, b1, a1).endVertex();
      bufferbuilder.pos(width, mwidth2, len2lenProgr).color(r0, g0, b0, 0.0F).endVertex();
      bufferbuilder.pos(width, mwidth2, -length2).color(r0, g0, b0, 0.0F).endVertex();
      bufferbuilder.pos(0.0, 0.0, -length2).color(r0, g0, b0, 0.0F).endVertex();
      bufferbuilder.pos(0.0, 0.0, len2lenProgr).color(r1, g1, b1, a1).endVertex();
      bufferbuilder.pos(0.0, width, len2lenProgr).color(r0, g0, b0, 0.0F).endVertex();
      bufferbuilder.pos(0.0, width, -length2).color(r0, g0, b0, 0.0F).endVertex();
      tessellator.draw();
      GlStateManager.popMatrix();
      if (progress < 1.0F) {
         width = entity.scale / 2.0F * 0.9F;
         float len2lenProgr2 = -length2 + entity.length * progress;
         r0 = Math.min(entity.Red * 1.4F, 1.0F);
         g0 = Math.min(entity.Green * 1.4F, 1.0F);
         b0 = Math.min(entity.Blue * 1.4F, 1.0F);
         r1 = entity.Red;
         g1 = entity.Green;
         b1 = entity.Blue;
         float r2 = entity.Red2;
         float g2 = entity.Green2;
         float b2 = entity.Blue2;
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         float pike = 0.15F;
         Tessellator tessellatorx = Tessellator.getInstance();
         BufferBuilder bufferbuilderx = tessellatorx.getBuffer();
         bufferbuilderx.begin(7, DefaultVertexFormats.POSITION_COLOR);
         bufferbuilderx.pos(-width, mwidth2, len2lenProgr).color(r2, g2, b2, 0.0F).endVertex();
         bufferbuilderx.pos(-width, mwidth2, len2lenProgr2).color(r0, g0, b0, 0.0F).endVertex();
         bufferbuilderx.pos(0.0, 0.0, len2lenProgr2 + pike).color(r1, g1, b1, a1).endVertex();
         bufferbuilderx.pos(0.0, 0.0, len2lenProgr - pike).color(r2, g2, b2, 0.0F).endVertex();
         bufferbuilderx.pos(0.0, 0.0, len2lenProgr - pike).color(r2, g2, b2, 0.0F).endVertex();
         bufferbuilderx.pos(0.0, 0.0, len2lenProgr2 + pike).color(r1, g1, b1, a1).endVertex();
         bufferbuilderx.pos(width, mwidth2, len2lenProgr2).color(r0, g0, b0, 0.0F).endVertex();
         bufferbuilderx.pos(width, mwidth2, len2lenProgr).color(r2, g2, b2, 0.0F).endVertex();
         bufferbuilderx.pos(0.0, 0.0, len2lenProgr - pike).color(r2, g2, b2, 0.0F).endVertex();
         bufferbuilderx.pos(0.0, 0.0, len2lenProgr2 + pike).color(r1, g1, b1, a1).endVertex();
         bufferbuilderx.pos(0.0, width, len2lenProgr2).color(r0, g0, b0, 0.0F).endVertex();
         bufferbuilderx.pos(0.0, width, len2lenProgr).color(r2, g2, b2, 0.0F).endVertex();
         tessellatorx.draw();
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      }

      GlStateManager.enableCull();
      GlStateManager.depthMask(true);
      GlStateManager.shadeModel(7424);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableTexture2D();
      GlStateManager.enableAlpha();
      RenderHelper.enableStandardItemLighting();
      GL11.glDisable(3042);
      GlStateManager.popMatrix();
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   protected ResourceLocation getEntityTexture(BulletParticle entity) {
      return TextureMap.LOCATION_MISSING_TEXTURE;
   }

   public static class BulletParticleFactory implements IRenderFactory {
      public Render createRenderFor(RenderManager manager) {
         return new RenderBulletParticle(manager);
      }
   }
}
