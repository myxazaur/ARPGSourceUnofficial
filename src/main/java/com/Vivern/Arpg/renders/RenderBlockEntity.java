package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.elements.models.CubikModel;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderBlockEntity<T extends Entity> extends Render<T> {
   private final CubikModel model = new CubikModel();
   public float scaleX;
   public float scaleY;
   public float scaleZ;
   public int light;
   public ResourceLocation texture;
   public float Red;
   public float Green;
   public float Blue;
   public boolean opa;
   public float rotateX;
   public float rotateY;
   public float rotateZ;
   public float rotateSpeed;
   public boolean rotateRand;
   public final float rand1;
   public final float rand2;
   public final float rand3;

   public RenderBlockEntity(
      RenderManager renderManagerIn,
      ResourceLocation texture,
      float scaleX,
      float scaleY,
      float scaleZ,
      int light,
      float Red,
      float Green,
      float Blue,
      boolean opa,
      float rotateX,
      float rotateY,
      float rotateZ,
      float rotateSpeed,
      boolean rotateRand
   ) {
      super(renderManagerIn);
      this.scaleX = scaleX;
      this.scaleY = scaleY;
      this.scaleZ = scaleZ;
      this.light = light;
      this.texture = texture;
      this.Red = Red;
      this.Green = Green;
      this.Blue = Blue;
      this.opa = opa;
      this.rotateSpeed = rotateSpeed;
      this.rotateRand = rotateRand;
      this.rand1 = (float)Math.random();
      this.rand2 = (float)Math.random();
      this.rand3 = (float)Math.random();
      this.rotateX = rotateX;
      this.rotateY = rotateY;
      this.rotateZ = rotateZ;
   }

   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
      float rrotateX = 0.0F;
      float rrotateY = 0.0F;
      float rrotateZ = 0.0F;
      if (!this.rotateRand) {
         rrotateX = this.rotateX;
         rrotateY = this.rotateY;
         rrotateZ = this.rotateZ;
      }

      if (this.rotateRand) {
         rrotateX = this.rand1 + entity.ticksExisted * this.rotateSpeed;
         rrotateY = this.rand2 + entity.ticksExisted * this.rotateSpeed;
         rrotateZ = this.rand3 + entity.ticksExisted * this.rotateSpeed;
      }

      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y, (float)z);
      GlStateManager.enableRescaleNormal();
      GlStateManager.disableLighting();
      this.bindTexture(this.texture);
      GlStateManager.scale(this.scaleX, this.scaleY, this.scaleZ);
      if (this.light > 100) {
         GL11.glDisable(2896);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, this.light, this.light);
      }

      if (this.opa) {
         GL11.glEnable(3042);
      }

      GlStateManager.color(this.Red, this.Green, this.Blue, 1.0F);
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

      if (this.opa) {
         GL11.glDisable(3042);
      }

      if (this.light > 100) {
         GL11.glEnable(2896);
      }

      GlStateManager.enableLighting();
      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   protected ResourceLocation getEntityTexture(Entity entity) {
      return TextureMap.LOCATION_MISSING_TEXTURE;
   }
}
