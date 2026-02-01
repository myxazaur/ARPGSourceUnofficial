package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class HeadShooterModelOverlay extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;

   public HeadShooterModelOverlay() {
      this.textureWidth = 12;
      this.textureHeight = 6;
      this.shape2 = new ModelRenderer(this, 0, 0);
      this.shape2.setRotationPoint(0.0F, 3.3F, -2.5F);
      this.shape2.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 5, 0.0F);
      this.shape3 = new ModelRenderer(this, 0, 0);
      this.shape3.setRotationPoint(0.0F, 1.3F, -3.4F);
      this.shape3.addBox(-0.5F, -0.5F, 0.0F, 1, 3, 1, 0.0F);
      this.shape1 = new ModelRenderer(this, 8, 0);
      this.shape1.setRotationPoint(0.0F, 1.3F, 2.0F);
      this.shape1.addBox(-0.5F, -0.5F, 0.0F, 1, 3, 1, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape2.offsetX, this.shape2.offsetY, this.shape2.offsetZ);
      GlStateManager.translate(this.shape2.rotationPointX * f5, this.shape2.rotationPointY * f5, this.shape2.rotationPointZ * f5);
      GlStateManager.scale(0.9, 1.0, 0.9);
      GlStateManager.translate(-this.shape2.offsetX, -this.shape2.offsetY, -this.shape2.offsetZ);
      GlStateManager.translate(-this.shape2.rotationPointX * f5, -this.shape2.rotationPointY * f5, -this.shape2.rotationPointZ * f5);
      this.shape2.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape3.offsetX, this.shape3.offsetY, this.shape3.offsetZ);
      GlStateManager.translate(this.shape3.rotationPointX * f5, this.shape3.rotationPointY * f5, this.shape3.rotationPointZ * f5);
      GlStateManager.scale(0.9, 1.0, 0.9);
      GlStateManager.translate(-this.shape3.offsetX, -this.shape3.offsetY, -this.shape3.offsetZ);
      GlStateManager.translate(-this.shape3.rotationPointX * f5, -this.shape3.rotationPointY * f5, -this.shape3.rotationPointZ * f5);
      this.shape3.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1.offsetX, this.shape1.offsetY, this.shape1.offsetZ);
      GlStateManager.translate(this.shape1.rotationPointX * f5, this.shape1.rotationPointY * f5, this.shape1.rotationPointZ * f5);
      GlStateManager.scale(0.9, 1.0, 0.9);
      GlStateManager.translate(-this.shape1.offsetX, -this.shape1.offsetY, -this.shape1.offsetZ);
      GlStateManager.translate(-this.shape1.rotationPointX * f5, -this.shape1.rotationPointY * f5, -this.shape1.rotationPointZ * f5);
      this.shape1.render(f5);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
