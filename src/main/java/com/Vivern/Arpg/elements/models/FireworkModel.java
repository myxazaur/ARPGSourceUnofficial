package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class FireworkModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape1_1;
   public ModelRenderer shape1_2;
   public ModelRenderer shape1_3;

   public FireworkModel() {
      this.textureWidth = 20;
      this.textureHeight = 10;
      this.shape1_2 = new ModelRenderer(this, 0, 4);
      this.shape1_2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1_2.addBox(-1.0F, -1.0F, -4.2F, 2, 2, 1, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 8, 0.0F);
      this.shape1_1 = new ModelRenderer(this, 14, 0);
      this.shape1_1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1_1.addBox(-1.0F, -1.0F, -3.0F, 2, 2, 1, 0.0F);
      this.shape1_3 = new ModelRenderer(this, 0, 0);
      this.shape1_3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1_3.addBox(-1.0F, -1.0F, -5.6F, 2, 2, 1, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1_2.offsetX, this.shape1_2.offsetY, this.shape1_2.offsetZ);
      GlStateManager.translate(this.shape1_2.rotationPointX * f5, this.shape1_2.rotationPointY * f5, this.shape1_2.rotationPointZ * f5);
      GlStateManager.scale(1.0, 1.0, 0.9);
      GlStateManager.translate(-this.shape1_2.offsetX, -this.shape1_2.offsetY, -this.shape1_2.offsetZ);
      GlStateManager.translate(-this.shape1_2.rotationPointX * f5, -this.shape1_2.rotationPointY * f5, -this.shape1_2.rotationPointZ * f5);
      this.shape1_2.render(f5);
      GlStateManager.popMatrix();
      this.shape1.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1_1.offsetX, this.shape1_1.offsetY, this.shape1_1.offsetZ);
      GlStateManager.translate(this.shape1_1.rotationPointX * f5, this.shape1_1.rotationPointY * f5, this.shape1_1.rotationPointZ * f5);
      GlStateManager.scale(1.4, 1.4, 1.0);
      GlStateManager.translate(-this.shape1_1.offsetX, -this.shape1_1.offsetY, -this.shape1_1.offsetZ);
      GlStateManager.translate(-this.shape1_1.rotationPointX * f5, -this.shape1_1.rotationPointY * f5, -this.shape1_1.rotationPointZ * f5);
      this.shape1_1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1_3.offsetX, this.shape1_3.offsetY, this.shape1_3.offsetZ);
      GlStateManager.translate(this.shape1_3.rotationPointX * f5, this.shape1_3.rotationPointY * f5, this.shape1_3.rotationPointZ * f5);
      GlStateManager.scale(0.5, 0.5, 0.8);
      GlStateManager.translate(-this.shape1_3.offsetX, -this.shape1_3.offsetY, -this.shape1_3.offsetZ);
      GlStateManager.translate(-this.shape1_3.rotationPointX * f5, -this.shape1_3.rotationPointY * f5, -this.shape1_3.rotationPointZ * f5);
      this.shape1_3.render(f5);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
