package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.elements.models.CubikModel;
import com.Vivern.Arpg.entity.EntityCubicParticle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderCubikParticle<T extends EntityCubicParticle> extends Render<T> {
   private final CubikModel model = new CubikModel();

   public RenderCubikParticle(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
      float rrotateX = 0.0F;
      float rrotateY = 0.0F;
      float rrotateZ = 0.0F;
      if (entity.rotateRand) {
         rrotateX = entity.rotateX + (entity.ticksExisted + partialTicks) * entity.rotateSpeed;
         rrotateY = entity.rotateY + (entity.ticksExisted + partialTicks) * entity.rotateSpeed;
         rrotateZ = entity.rotateZ + (entity.ticksExisted + partialTicks) * entity.rotateSpeed;
      } else {
         rrotateX = entity.rotateX;
         rrotateY = entity.rotateY;
         rrotateZ = entity.rotateZ;
      }

      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y, (float)z);
      GlStateManager.enableRescaleNormal();
      GlStateManager.disableLighting();
      this.bindTexture(entity.texture);
      GlStateManager.scale(entity.scale, entity.scale, entity.scale);
      if (entity.light >= 0) {
         GL11.glDisable(2896);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, entity.light, entity.light);
      } else {
         int lightt = GUNPRender.getBrightnessForRender(entity);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightt, lightt);
      }

      if (entity.opa) {
         GL11.glEnable(3042);
      }

      GlStateManager.color(entity.Red, entity.Green, entity.Blue, 1.0F);
      if (entity.alphaGlowing) {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
      } else {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      }

      if (this.renderOutlines) {
         GlStateManager.enableColorMaterial();
         GlStateManager.enableOutlineMode(this.getTeamColor(entity));
      }

      this.model.setRotateAngle(this.model.shape1, rrotateX, rrotateY, rrotateZ);
      this.model.render(entity, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      if (this.renderOutlines) {
         GlStateManager.disableOutlineMode();
         GlStateManager.disableColorMaterial();
      }

      if (entity.opa) {
         GL11.glDisable(3042);
      }

      if (entity.light >= 0) {
         GL11.glEnable(2896);
      }

      if (entity.alphaGlowing) {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      }

      GlStateManager.enableLighting();
      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   protected ResourceLocation getEntityTexture(EntityCubicParticle entity) {
      return TextureMap.LOCATION_MISSING_TEXTURE;
   }
}
