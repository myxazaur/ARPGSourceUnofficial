package com.Vivern.Arpg.main;

import com.Vivern.Arpg.renders.IRenderOptions;
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
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
@EventBusSubscriber({Side.CLIENT})
public class Render2D<T extends Entity> extends Render<T> {
   protected final ResourceLocation texture;
   private float scaleX;
   private float scaleY;
   private float scaleZ;
   private int time = -1;
   private float rotationx;
   private float rotationy;
   private float rotationz;
   private boolean opaq;
   private int light = -1;
   private DestFactor dstFactor;
   private boolean checkAdvanced;
   public float red;
   public float green;
   public float blue;
   private boolean useColor;

   public Render2D(
      RenderManager renderManagerIn,
      ResourceLocation texture,
      float scaleX,
      float scaleY,
      float scaleZ,
      float rotationx,
      float rotationy,
      float rotationz,
      boolean opaq,
      int light,
      DestFactor dstFactor,
      boolean checkAdvanced,
      float r,
      float g,
      float b
   ) {
      super(renderManagerIn);
      this.texture = texture;
      this.scaleX = scaleX;
      this.scaleY = scaleY;
      this.scaleZ = scaleZ;
      this.rotationx = rotationx;
      this.rotationy = rotationy;
      this.rotationz = rotationz;
      this.opaq = opaq;
      this.light = light;
      this.dstFactor = dstFactor;
      this.checkAdvanced = checkAdvanced;
      this.red = r;
      this.green = g;
      this.blue = b;
      this.useColor = r != 1.0F || g != 1.0F || b != 1.0F;
   }

   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y + entity.getYOffset(), (float)z);
      GlStateManager.enableRescaleNormal();
      GlStateManager.disableLighting();
      GL11.glDepthMask(false);
      GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate((this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
      if (this.opaq) {
         GL11.glEnable(3042);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, this.dstFactor);
      }

      if (this.light >= 0) {
         GL11.glDisable(2896);
      }

      if (this.rotationx + this.rotationy + this.rotationz != 0.0F) {
         GlStateManager.rotate(90.0F, this.rotationx, this.rotationy, this.rotationz);
      }

      this.bindTexture(this.texture);
      GlStateManager.scale(this.scaleX, this.scaleZ, this.scaleZ);
      if (this.light >= 0) {
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, this.light, this.light);
      }

      if (this.renderOutlines) {
         GlStateManager.enableColorMaterial();
         GlStateManager.enableOutlineMode(this.getTeamColor(entity));
      }

      boolean saveinstanceof = false;
      if (this.checkAdvanced && entity instanceof IRenderOptions) {
         saveinstanceof = true;
         ((IRenderOptions)entity).doOptions();
      }

      if (this.useColor) {
         GlStateManager.color(this.red, this.green, this.blue);
      }

      this.time = entity.ticksExisted;
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-1.0, -1.0, 0.0).tex(0.0, 1.0F / this.scaleY + this.time / this.scaleY).endVertex();
      bufferbuilder.pos(-1.0, 1.0, 0.0).tex(0.0, 0.0F + this.time / this.scaleY).endVertex();
      bufferbuilder.pos(1.0, 1.0, 0.0).tex(1.0, 0.0F + this.time / this.scaleY).endVertex();
      bufferbuilder.pos(1.0, -1.0, 0.0).tex(1.0, 1.0F / this.scaleY + this.time / this.scaleY).endVertex();
      tessellator.draw();
      if (this.renderOutlines) {
         GlStateManager.disableOutlineMode();
         GlStateManager.disableColorMaterial();
      }

      GlStateManager.enableLighting();
      GlStateManager.disableRescaleNormal();
      if (this.opaq) {
         GL11.glDisable(3042);
      }

      if (this.light >= 0) {
         GL11.glEnable(2896);
      }

      GL11.glDepthMask(true);
      if (saveinstanceof) {
         ((IRenderOptions)entity).undoOptions();
      }

      if (this.useColor) {
         GlStateManager.color(1.0F, 1.0F, 1.0F);
      }

      GlStateManager.popMatrix();
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   protected ResourceLocation getEntityTexture(Entity entity) {
      return TextureMap.LOCATION_MISSING_TEXTURE;
   }
}
