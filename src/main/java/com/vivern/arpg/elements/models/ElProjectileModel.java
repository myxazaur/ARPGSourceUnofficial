package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ElProjectileModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape1_1;
   public ModelRenderer shape1_2;

   public ElProjectileModel() {
      this.textureWidth = 16;
      this.textureHeight = 5;
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
      this.shape1_1 = new ModelRenderer(this, 8, 0);
      this.shape1_1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1_1.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 2, 0.0F);
      this.shape1_2 = new ModelRenderer(this, 6, 0);
      this.shape1_2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1_2.addBox(-0.5F, -0.5F, 1.0F, 1, 1, 1, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1.offsetX, this.shape1.offsetY, this.shape1.offsetZ);
      GlStateManager.translate(this.shape1.rotationPointX * f5, this.shape1.rotationPointY * f5, this.shape1.rotationPointZ * f5);
      GlStateManager.scale(0.7, 0.7, 1.0);
      GlStateManager.translate(-this.shape1.offsetX, -this.shape1.offsetY, -this.shape1.offsetZ);
      GlStateManager.translate(-this.shape1.rotationPointX * f5, -this.shape1.rotationPointY * f5, -this.shape1.rotationPointZ * f5);
      this.shape1.render(f5);
      GlStateManager.popMatrix();
      this.shape1_1.render(f5);
      this.shape1_2.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
