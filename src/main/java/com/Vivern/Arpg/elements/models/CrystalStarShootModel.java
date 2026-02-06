package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class CrystalStarShootModel extends ModelBase {
   public ModelRenderer shape;

   public CrystalStarShootModel() {
      this.textureWidth = 12;
      this.textureHeight = 6;
      this.shape = new ModelRenderer(this, 0, 0);
      this.shape.setRotationPoint(0.0F, 0.0F, 1.7F);
      this.shape.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
      this.setRotateAngle(this.shape, (float) (Math.PI / 4), (float) (Math.PI / 4), 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      float lbX = OpenGlHelper.lastBrightnessX;
      float lbY = OpenGlHelper.lastBrightnessY;
      GlStateManager.pushMatrix();
      GL11.glDisable(2896);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, Math.min(240.0F, 80.0F + lbX), Math.min(240.0F, 80.0F + lbY));
      GlStateManager.translate(this.shape.offsetX, this.shape.offsetY, this.shape.offsetZ);
      GlStateManager.translate(this.shape.rotationPointX * f5, this.shape.rotationPointY * f5, this.shape.rotationPointZ * f5);
      GlStateManager.scale(0.25, 0.25, 2.2);
      GlStateManager.translate(-this.shape.offsetX, -this.shape.offsetY, -this.shape.offsetZ);
      GlStateManager.translate(-this.shape.rotationPointX * f5, -this.shape.rotationPointY * f5, -this.shape.rotationPointZ * f5);
      this.shape.render(f5);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbX, lbY);
      GL11.glEnable(2896);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
