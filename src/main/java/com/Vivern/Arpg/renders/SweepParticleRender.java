package com.Vivern.Arpg.renders;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

// FIX: change `Render<T>` to `Render<SweepParticle>`
@SideOnly(Side.CLIENT)
public class SweepParticleRender<T extends SweepParticle> extends Render<SweepParticle> {
   public SweepParticleRender(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   public void doRender(SweepParticle entity, double x, double y, double z, float entityYaw, float partialTicks) {
      if (entity.ticksExisted >= entity.schedule) {
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y, (float)z);
         GlStateManager.enableRescaleNormal();
         GlStateManager.rotate(-entity.yaw, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(entity.pitch + 60.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(entity.rotation, 0.0F, 1.0F, 0.0F);
         this.bindTexture(entity.texture);
         GlStateManager.scale(entity.scale, entity.scale, entity.scale);
         GL11.glDepthMask(false);
         GL11.glDisable(2884);
         if (entity.light >= 0) {
            GL11.glDisable(2896);
         }

         if (entity.opa) {
            GL11.glEnable(3042);
         }

         if (entity.alphaGlowing) {
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         } else if (entity.acidRender) {
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.SRC_COLOR);
         } else {
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         }

         if (entity.light >= 0) {
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, entity.light, entity.light);
         }

         GlStateManager.color(entity.Red, entity.Green, entity.Blue, entity.alpha);
         float time = entity.ticksExisted - entity.schedule / entity.ticksPerFrame;
         float scaleY = entity.frameCount;
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-1.0, -1.0, 0.0).tex(0.0, 1.0F / scaleY + time / scaleY).endVertex();
         bufferbuilder.pos(-1.0, 1.0, 0.0).tex(0.0, 0.0F + time / scaleY).endVertex();
         bufferbuilder.pos(1.0, 1.0, 0.0).tex(1.0, 0.0F + time / scaleY).endVertex();
         bufferbuilder.pos(1.0, -1.0, 0.0).tex(1.0, 1.0F / scaleY + time / scaleY).endVertex();
         tessellator.draw();
         GlStateManager.disableRescaleNormal();
         if (entity.light >= 0) {
            GL11.glEnable(2896);
         }

         GL11.glDepthMask(true);
         if (entity.opa) {
            GL11.glDisable(3042);
         }

         GL11.glEnable(2884);
         if (entity.alphaGlowing) {
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         }

         GlStateManager.popMatrix();
      }

      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   protected ResourceLocation getEntityTexture(SweepParticle entity) {
      return TextureMap.LOCATION_MISSING_TEXTURE;
   }
}
