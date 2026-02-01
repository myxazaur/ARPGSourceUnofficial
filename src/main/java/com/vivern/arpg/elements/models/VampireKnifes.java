package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class VampireKnifes extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape2_1;
   public ModelRenderer shape2_2;

   public VampireKnifes() {
      this.textureWidth = 16;
      this.textureHeight = 16;
      this.shape2_2 = new ModelRenderer(this, 0, 4);
      this.shape2_2.setRotationPoint(-0.5F, 0.05F, -3.5F);
      this.shape2_2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
      this.shape2 = new ModelRenderer(this, 5, 0);
      this.shape2.setRotationPoint(-1.5F, -0.2F, 0.5F);
      this.shape2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 2, 0.0F);
      this.shape2_1 = new ModelRenderer(this, 6, 3);
      this.shape2_1.setRotationPoint(-1.0F, -0.1F, -1.5F);
      this.shape2_1.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(-0.5F, -0.2F, 2.5F);
      this.shape1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape2_2.offsetX, this.shape2_2.offsetY, this.shape2_2.offsetZ);
      GlStateManager.translate(this.shape2_2.rotationPointX * f5, this.shape2_2.rotationPointY * f5, this.shape2_2.rotationPointZ * f5);
      GlStateManager.scale(1.0, 0.5, 1.0);
      GlStateManager.translate(-this.shape2_2.offsetX, -this.shape2_2.offsetY, -this.shape2_2.offsetZ);
      GlStateManager.translate(-this.shape2_2.rotationPointX * f5, -this.shape2_2.rotationPointY * f5, -this.shape2_2.rotationPointZ * f5);
      this.shape2_2.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape2.offsetX, this.shape2.offsetY, this.shape2.offsetZ);
      GlStateManager.translate(this.shape2.rotationPointX * f5, this.shape2.rotationPointY * f5, this.shape2.rotationPointZ * f5);
      GlStateManager.scale(1.0, 1.1, 1.0);
      GlStateManager.translate(-this.shape2.offsetX, -this.shape2.offsetY, -this.shape2.offsetZ);
      GlStateManager.translate(-this.shape2.rotationPointX * f5, -this.shape2.rotationPointY * f5, -this.shape2.rotationPointZ * f5);
      this.shape2.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape2_1.offsetX, this.shape2_1.offsetY, this.shape2_1.offsetZ);
      GlStateManager.translate(this.shape2_1.rotationPointX * f5, this.shape2_1.rotationPointY * f5, this.shape2_1.rotationPointZ * f5);
      GlStateManager.scale(1.0, 0.8, 1.0);
      GlStateManager.translate(-this.shape2_1.offsetX, -this.shape2_1.offsetY, -this.shape2_1.offsetZ);
      GlStateManager.translate(-this.shape2_1.rotationPointX * f5, -this.shape2_1.rotationPointY * f5, -this.shape2_1.rotationPointZ * f5);
      this.shape2_1.render(f5);
      GlStateManager.popMatrix();
      this.shape1.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
