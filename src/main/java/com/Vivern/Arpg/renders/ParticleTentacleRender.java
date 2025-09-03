//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.elements.models.AbstractMobModel;
import com.Vivern.Arpg.renders.mobrender.RenderTentacles;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;

// FIX: change `Render<T>` to `Render<ParticleTentacle>`
public class ParticleTentacleRender<T extends ParticleTentacle> extends Render<ParticleTentacle> {
   public ParticleTentacleRender(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   public void doRender(ParticleTentacle entity, double x, double y, double z, float entityYaw, float partialTicks) {
      GlStateManager.pushMatrix();
      this.bindTexture(entity.texture);
      GL11.glDepthMask(false);
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

      Vec3d pos1 = entity.start.getPosition(partialTicks);
      Vec3d pos2 = entity.end.getPosition(partialTicks);
      int lightStart;
      int lightEnd;
      if (entity.light >= 0) {
         lightStart = entity.light;
         lightEnd = entity.light;
      } else {
         int[] lightt1 = AbstractMobModel.getBrightnessForRender(entity.world, new BlockPos(pos1));
         lightStart = Math.max(lightt1[0], lightt1[1]);
         int[] lightt2 = AbstractMobModel.getBrightnessForRender(entity.world, new BlockPos(pos2));
         lightEnd = Math.max(lightt2[0], lightt2[1]);
      }

      GlStateManager.color(entity.Red, entity.Green, entity.Blue, entity.alpha);
      if (entity.tracker != null) {
         entity.tracker.render((T)entity, x, y, z, entityYaw, partialTicks);
      }

      Vec3d[] segmentsPoses = RenderTentacles.getTentacleSegmenstPositions(
         pos1,
         pos2,
         entity.start.getNormalizedRotationVector(partialTicks),
         entity.end.getNormalizedRotationVector(partialTicks),
         entity.rotationVecLength,
         entity.segments + 1
      );
      RenderTentacles.renderTentacleAnimFulltexture(
         segmentsPoses,
         entity.overlap,
         (entity.ticksExisted + partialTicks) / entity.livetime,
         partialTicks,
         entity.scale,
         entity.scaleEnd,
         lightStart,
         lightEnd
      );
      if (entity.disableLightning) {
         GL11.glEnable(2896);
      }

      if (entity.opa) {
         GL11.glDisable(3042);
      }

      if (entity.alphaGlowing || entity.acidRender) {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      }

      GL11.glDepthMask(true);
      GlStateManager.popMatrix();
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   protected ResourceLocation getEntityTexture(ParticleTentacle entity) {
      return TextureMap.LOCATION_MISSING_TEXTURE;
   }

   public static class ParticleTentacleFactory implements IRenderFactory {
      public Render createRenderFor(RenderManager manager) {
         return new ParticleTentacleRender(manager);
      }
   }
}
