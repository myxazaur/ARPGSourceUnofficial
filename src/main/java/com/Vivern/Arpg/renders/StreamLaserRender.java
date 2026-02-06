package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.entity.EntityStreamLaserP;
import com.Vivern.Arpg.main.GetMOP;
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
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

// FIX: change `Render<T>` to `Render<EntityStreamLaserP>`
@SideOnly(Side.CLIENT)
public class StreamLaserRender<T extends EntityStreamLaserP> extends Render<EntityStreamLaserP> {
   public StreamLaserRender(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   public void doRender(EntityStreamLaserP entity, double x, double y, double z, float entityYaw, float partialTicks) {
      if (entity.useModel) {
         entity.resetPlayerArmPitchYaw(partialTicks);
         float rp = entity.player != null
            ? (this.renderManager.options.thirdPersonView == 2 ? 1 : 1) * entity.player.rotationPitch
            : GetMOP.partial(entity.rotatPitch, entity.prevrotatPitch, partialTicks);
         float rw = entity.player != null
            ? GetMOP.partial(entity.player.rotationYaw, entity.player.prevRotationYaw, partialTicks)
            : GetMOP.partial(entity.rotatYaw, entity.prevrotatYaw, partialTicks);
         rp = -rp;
         rw = -rw + 180.0F;
         float anglefix1 = (float)Math.atan(entity.shoulders / entity.distance) * (float) (180.0 / Math.PI);
         float anglefix2 = (float)Math.atan(entity.yoffset / entity.distance) * (float) (180.0 / Math.PI);
         if (entity.player != null
            && (entity.hand != EnumHand.MAIN_HAND || entity.player.getPrimaryHand() != EnumHandSide.RIGHT)
            && (entity.hand != EnumHand.OFF_HAND || entity.player.getPrimaryHand() != EnumHandSide.LEFT)) {
            anglefix1 = -anglefix1;
         }

         Vec3d rvePos = MyRenderHelper.getRenderViewEntityPosition(partialTicks);
         Vec3d entityPos = entity.getPositionEyes(partialTicks);
         entity.model
            .renderLaserModel(
               entity,
               entityPos,
               rp - anglefix2,
               rw + anglefix1,
               entity.distance,
               entity.horizOffset,
               entityPos.x - rvePos.x,
               entityPos.y - rvePos.y,
               entityPos.z - rvePos.z,
               partialTicks
            );
      } else {
         double dist = entity.distance;
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y, (float)z);
         GlStateManager.enableRescaleNormal();
         if (entity.player != null) {
            GlStateManager.rotate(-entity.player.rotationYaw, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate((this.renderManager.options.thirdPersonView == 2 ? 1 : 1) * entity.player.rotationPitch, 1.0F, 0.0F, 0.0F);
         } else {
            GlStateManager.rotate(-GetMOP.partial(entity.rotatYaw, entity.prevrotatYaw, partialTicks), 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(GetMOP.partial(entity.rotatPitch, entity.prevrotatPitch, partialTicks), 1.0F, 0.0F, 0.0F);
         }

         this.bindTexture(entity.texture);
         GL11.glDisable(2896);
         GL11.glEnable(3042);
         GL11.glDisable(2884);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, entity.light, entity.light);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         GlStateManager.color(entity.Red, entity.Green, entity.Blue, entity.alpha);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-entity.scale - entity.horizOffset, -0.1, entity.distanceStart)
            .tex(0.0, 1.0 + dist / entity.lengh + entity.Tickpl * entity.animation)
            .endVertex();
         bufferbuilder.pos(-entity.scale, 0.0, dist + 0.08).tex(0.0, 0.0F + entity.Tickpl * entity.animation).endVertex();
         bufferbuilder.pos(entity.scale, 0.0, dist + 0.08).tex(1.0, 0.0F + entity.Tickpl * entity.animation).endVertex();
         bufferbuilder.pos(entity.scale - entity.horizOffset, -0.1, entity.distanceStart)
            .tex(1.0, 1.0 + dist / entity.lengh + entity.Tickpl * entity.animation)
            .endVertex();
         tessellator.draw();
         GlStateManager.disableRescaleNormal();
         GL11.glDisable(3042);
         GL11.glEnable(2896);
         GL11.glEnable(2884);
         GlStateManager.popMatrix();
         if (entity.horizontal) {
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)x, (float)y, (float)z);
            GlStateManager.enableRescaleNormal();
            if (entity.player != null) {
               GlStateManager.rotate(-entity.player.rotationYaw, 0.0F, 1.0F, 0.0F);
               GlStateManager.rotate((this.renderManager.options.thirdPersonView == 2 ? 1 : 1) * entity.player.rotationPitch, 1.0F, 0.0F, 0.0F);
            } else {
               GlStateManager.rotate(-GetMOP.partial(entity.rotatYaw, entity.prevrotatYaw, partialTicks), 0.0F, 1.0F, 0.0F);
               GlStateManager.rotate(GetMOP.partial(entity.rotatPitch, entity.prevrotatPitch, partialTicks), 1.0F, 0.0F, 0.0F);
            }

            this.bindTexture(entity.texture);
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            GL11.glDisable(2884);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, entity.light, entity.light);
            GlStateManager.color(entity.Red, entity.Green, entity.Blue, entity.alpha);
            Tessellator tessellator2 = Tessellator.getInstance();
            BufferBuilder bufferbuilder2 = tessellator2.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder2.pos(-entity.horizOffset, -entity.scale - 0.1, entity.distanceStart)
               .tex(0.0, 1.0 + dist / entity.lengh + entity.Tickpl * entity.animation)
               .endVertex();
            bufferbuilder2.pos(0.0, -entity.scale, dist + 0.08).tex(0.0, 0.0F + entity.Tickpl * entity.animation).endVertex();
            bufferbuilder2.pos(0.0, entity.scale, dist + 0.08).tex(1.0, 0.0F + entity.Tickpl * entity.animation).endVertex();
            bufferbuilder2.pos(-entity.horizOffset, entity.scale - 0.1, entity.distanceStart)
               .tex(1.0, 1.0 + dist / entity.lengh + entity.Tickpl * entity.animation)
               .endVertex();
            tessellator2.draw();
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.disableRescaleNormal();
            GL11.glDisable(3042);
            GL11.glEnable(2896);
            GL11.glEnable(2884);
            GlStateManager.popMatrix();
         }

         super.doRender(entity, x, y, z, entityYaw, partialTicks);
      }
   }

   protected ResourceLocation getEntityTexture(EntityStreamLaserP entity) {
      return TextureMap.LOCATION_MISSING_TEXTURE;
   }
}
