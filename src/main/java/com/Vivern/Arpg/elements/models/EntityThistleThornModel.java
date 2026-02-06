package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.entity.EntityThistleThorn;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class EntityThistleThornModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;

   public EntityThistleThornModel() {
      this.textureWidth = 26;
      this.textureHeight = 12;
      this.shape2 = new ModelRenderer(this, 0, 1);
      this.shape2.setRotationPoint(0.0F, 0.05F, 0.0F);
      this.shape2.addBox(-0.5F, 0.0F, -2.0F, 1, 1, 9, 0.0F);
      this.setRotateAngle(this.shape2, 0.0F, (float) Math.PI, 0.0F);
      this.shape1 = new ModelRenderer(this, 2, 0);
      this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1.addBox(-1.5F, 0.0F, -4.0F, 3, 1, 9, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      float lbX = OpenGlHelper.lastBrightnessX;
      float lbY = OpenGlHelper.lastBrightnessY;
      int add = 240 - entity.ticksExisted * 5;
      GL11.glDisable(2896);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, MathHelper.clamp(add, 0, 240), MathHelper.clamp(add, 0, 240));
      if (entity instanceof EntityThistleThorn) {
         if (((EntityThistleThorn)entity).charged) {
            GlStateManager.color(0.8F, 0.27F, 0.95F);
         } else {
            GlStateManager.color(0.95F, 1.0F, 0.25F);
         }
      }

      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape2.offsetX, this.shape2.offsetY, this.shape2.offsetZ);
      GlStateManager.translate(this.shape2.rotationPointX * f5, this.shape2.rotationPointY * f5, this.shape2.rotationPointZ * f5);
      GlStateManager.scale(0.5, 1.0, 1.0);
      GlStateManager.translate(-this.shape2.offsetX, -this.shape2.offsetY, -this.shape2.offsetZ);
      GlStateManager.translate(-this.shape2.rotationPointX * f5, -this.shape2.rotationPointY * f5, -this.shape2.rotationPointZ * f5);
      this.shape2.render(f5);
      GlStateManager.popMatrix();
      this.shape1.render(f5);
      GlStateManager.color(1.0F, 1.0F, 1.0F);
      GL11.glEnable(2896);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbX, lbY);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
