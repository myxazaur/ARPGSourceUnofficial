package com.vivern.arpg.renders;

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
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;

// FIX: change `Render<T>` to `Render<WhipParticle>`
public class RenderWhip<T extends WhipParticle> extends Render<WhipParticle> {
   public RenderWhip(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   public void doRender(WhipParticle entity, double x, double y, double z, float entityYaw, float partialTicks) {
      float mx = (float)(entity.from.x + entity.startmotionX * partialTicks - (entity.to.x + entity.targetmotionX * partialTicks));
      float mz = (float)(entity.from.z + entity.startmotionZ * partialTicks - (entity.to.z + entity.targetmotionZ * partialTicks));
      float my = (float)(entity.to.y + entity.targetmotionY * partialTicks - (entity.from.y + entity.startmotionY * partialTicks));
      float moti_zx = (float)Math.sqrt(mx * mx + mz * mz);
      float moti_zy = (float)Math.sqrt(my * my + mz * mz);
      float cosangle_Yaw = mz / moti_zx;
      float cosangle_Pitch = mz / moti_zy;
      float angle_Yaw = (float)Math.toDegrees(Math.acos(cosangle_Yaw));
      float angle_Pitch = (float)Math.toDegrees(Math.acos(cosangle_Pitch));
      float tanangle = my / moti_zx;
      float angle = (float)Math.toDegrees(Math.atan(tanangle));
      if (mx > 0.0F) {
         angle_Yaw = -angle_Yaw;
      }

      if (my < 0.0F) {
         angle_Pitch = -angle_Pitch;
      }

      entity.rotatYaw = angle_Yaw + 180.0F;
      entity.rotatPitch = -angle;
      double dist = MathHelper.sqrt(mx * mx + my * my + mz * mz);
      GlStateManager.pushMatrix();
      GlStateManager.depthMask(false);
      GlStateManager.translate(
         (float)x + entity.startmotionX * partialTicks, (float)y + entity.startmotionY * partialTicks, (float)z + entity.startmotionZ * partialTicks
      );
      GlStateManager.enableRescaleNormal();
      GlStateManager.rotate(-entity.rotatYaw, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(entity.rotatPitch, 1.0F, 0.0F, 0.0F);
      this.bindTexture(entity.texture);
      if (entity.light >= 0) {
         GL11.glDisable(2896);
      }

      GL11.glEnable(3042);
      GL11.glDisable(2884);
      if (entity.alphaGlowing) {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
      } else {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      }

      if (entity.light >= 0) {
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, entity.light, entity.light);
      }

      GlStateManager.color(entity.Red, entity.Green, entity.Blue, entity.alpha);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-entity.scale, 0.0, 0.0)
         .tex(0.0, entity.texstart + dist / entity.texlengh + entity.ticksExisted * entity.animation + entity.offset)
         .endVertex();
      bufferbuilder.pos(-entity.scale, 0.0, dist).tex(0.0, 0.0F + entity.ticksExisted * entity.animation + entity.offset).endVertex();
      bufferbuilder.pos(entity.scale, 0.0, dist).tex(1.0, 0.0F + entity.ticksExisted * entity.animation + entity.offset).endVertex();
      bufferbuilder.pos(entity.scale, 0.0, 0.0)
         .tex(1.0, entity.texstart + dist / entity.texlengh + entity.ticksExisted * entity.animation + entity.offset)
         .endVertex();
      tessellator.draw();
      GlStateManager.disableRescaleNormal();
      GL11.glDisable(3042);
      if (entity.light >= 0) {
         GL11.glEnable(2896);
      }

      GL11.glEnable(2884);
      GlStateManager.depthMask(true);
      GlStateManager.popMatrix();
      if (entity.horizontal) {
         GlStateManager.pushMatrix();
         GlStateManager.depthMask(false);
         GlStateManager.translate(
            (float)x + entity.startmotionX * partialTicks, (float)y + entity.startmotionY * partialTicks, (float)z + entity.startmotionZ * partialTicks
         );
         GlStateManager.enableRescaleNormal();
         GlStateManager.rotate(-entity.rotatYaw, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(entity.rotatPitch, 1.0F, 0.0F, 0.0F);
         this.bindTexture(entity.texture);
         GL11.glDisable(2896);
         GL11.glEnable(3042);
         GL11.glDisable(2884);
         if (entity.alphaGlowing) {
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         }

         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, entity.light, entity.light);
         GlStateManager.color(entity.Red, entity.Green, entity.Blue, entity.alpha);
         Tessellator tessellator2 = Tessellator.getInstance();
         BufferBuilder bufferbuilder2 = tessellator2.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder2.pos(0.0, -entity.scale, 0.0)
            .tex(0.0, entity.texstart + dist / entity.texlengh + entity.ticksExisted * entity.animation + entity.offset)
            .endVertex();
         bufferbuilder2.pos(0.0, -entity.scale, dist)
            .tex(0.0, 0.0F + entity.ticksExisted * entity.animation + entity.offset)
            .endVertex();
         bufferbuilder2.pos(0.0, entity.scale, dist)
            .tex(1.0, 0.0F + entity.ticksExisted * entity.animation + entity.offset)
            .endVertex();
         bufferbuilder2.pos(0.0, entity.scale, 0.0)
            .tex(1.0, entity.texstart + dist / entity.texlengh + entity.ticksExisted * entity.animation + entity.offset)
            .endVertex();
         tessellator2.draw();
         GlStateManager.disableRescaleNormal();
         GL11.glDisable(3042);
         GL11.glEnable(2896);
         GL11.glEnable(2884);
         GlStateManager.depthMask(true);
         GlStateManager.popMatrix();
      }

      if (entity.alphaGlowing) {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      }

      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   protected ResourceLocation getEntityTexture(WhipParticle entity) {
      return TextureMap.LOCATION_MISSING_TEXTURE;
   }

   public static class RenderWhipFactory implements IRenderFactory {
      public Render createRenderFor(RenderManager manager) {
         return new RenderWhip(manager);
      }
   }
}
