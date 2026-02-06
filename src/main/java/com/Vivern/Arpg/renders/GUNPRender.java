package com.Vivern.Arpg.renders;

import net.minecraft.client.Minecraft;
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
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

// FIX: change `Render<T>` to `Render<GUNParticle>`
@SideOnly(Side.CLIENT)
public class GUNPRender<T extends GUNParticle> extends Render<GUNParticle> {
   public GUNPRender(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   public void doRender(GUNParticle entity, double x, double y, double z, float entityYaw, float partialTicks) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y, (float)z);
      GlStateManager.enableRescaleNormal();
      if (entity.rotationPitchYaw != null) {
         GlStateManager.rotate(-entity.rotationPitchYaw.y, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(entity.rotationPitchYaw.x, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(entity.rotation, 0.0F, 0.0F, 1.0F);
      } else if (!entity.dropped) {
         GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate((this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(entity.rotation, 0.0F, 0.0F, 1.0F);
      } else {
         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(entity.rotation, 0.0F, 0.0F, 1.0F);
      }

      this.bindTexture(entity.texture);
      float finalscale = entity.scale + entity.scaleTickAdding * partialTicks;
      GlStateManager.scale(finalscale, finalscale, finalscale);
      GL11.glDepthMask(false);
      if (entity.rotation != 0) {
         GL11.glDisable(2884);
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

      if (entity.disableLightning) {
         GL11.glDisable(2896);
      }

      if (entity.light >= 0) {
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, entity.light, entity.light);
      } else {
         Minecraft.getMinecraft().entityRenderer.enableLightmap();
      }

      GlStateManager.color(entity.Red, entity.Green, entity.Blue, entity.alpha);
      if (entity.tracker != null) {
         entity.tracker.render((T)entity, x, y, z, entityYaw, partialTicks);
      }

      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-1.0, -1.0, 0.0).tex(0.0, 1.0).endVertex();
      bufferbuilder.pos(-1.0, 1.0, 0.0).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(1.0, 1.0, 0.0).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(1.0, -1.0, 0.0).tex(1.0, 1.0).endVertex();
      tessellator.draw();
      if (entity.light < 0) {
         Minecraft.getMinecraft().entityRenderer.disableLightmap();
      }

      GlStateManager.disableRescaleNormal();
      if (entity.disableLightning) {
         GL11.glEnable(2896);
      }

      GL11.glDepthMask(true);
      if (entity.opa) {
         GL11.glDisable(3042);
      }

      if (entity.rotation != 0) {
         GL11.glEnable(2884);
      }

      if (entity.alphaGlowing || entity.acidRender) {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      }

      GlStateManager.popMatrix();
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   protected ResourceLocation getEntityTexture(GUNParticle entity) {
      return TextureMap.LOCATION_MISSING_TEXTURE;
   }

   public static int getBrightnessForRender(Entity entity) {
      BlockPos blockpos = new BlockPos(entity.posX, entity.posY, entity.posZ);
      int i = entity.world.isBlockLoaded(blockpos) ? entity.world.getCombinedLight(blockpos, 0) : 0;
      int j = i >> 16 & 65535;
      int k = i & 65535;
      return Math.max(j, k);
   }
}
