package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class MinigunIcicleModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;

   public MinigunIcicleModel() {
      this.textureWidth = 12;
      this.textureHeight = 6;
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1.addBox(-1.0F, -1.0F, -1.0F, 2, 4, 2, 0.0F);
      this.setRotateAngle(this.shape1, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.shape2 = new ModelRenderer(this, 8, 0);
      this.shape2.setRotationPoint(0.0F, 0.0F, -4.0F);
      this.shape2.addBox(-0.5F, -2.0F, -0.5F, 1, 5, 1, 0.0F);
      this.setRotateAngle(this.shape2, (float) (Math.PI / 2), 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1.offsetX, this.shape1.offsetY, this.shape1.offsetZ);
      GlStateManager.translate(this.shape1.rotationPointX * f5, this.shape1.rotationPointY * f5, this.shape1.rotationPointZ * f5);
      GlStateManager.scale(0.9, 0.9, 1.0);
      GlStateManager.translate(-this.shape1.offsetX, -this.shape1.offsetY, -this.shape1.offsetZ);
      GlStateManager.translate(-this.shape1.rotationPointX * f5, -this.shape1.rotationPointY * f5, -this.shape1.rotationPointZ * f5);
      this.shape1.render(f5);
      GlStateManager.popMatrix();
      this.shape2.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
