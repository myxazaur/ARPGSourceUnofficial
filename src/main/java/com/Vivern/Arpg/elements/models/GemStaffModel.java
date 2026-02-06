package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class GemStaffModel extends ModelBase {
   public ModelRenderer staf;
   public ModelRenderer gem;
   public ModelRenderer shape1;

   public GemStaffModel() {
      this.textureWidth = 20;
      this.textureHeight = 20;
      this.staf = new ModelRenderer(this, 0, 0);
      this.staf.setRotationPoint(0.0F, -2.0F, 5.0F);
      this.staf.addBox(-1.0F, 0.0F, -1.0F, 2, 18, 2, 0.0F);
      this.shape1 = new ModelRenderer(this, 8, 0);
      this.shape1.setRotationPoint(0.0F, -2.8F, 5.0F);
      this.shape1.addBox(-1.5F, 0.0F, -1.5F, 3, 1, 3, 0.0F);
      this.gem = new ModelRenderer(this, 8, 4);
      this.gem.setRotationPoint(0.0F, -7.0F, 5.0F);
      this.gem.addBox(-1.9F, 0.8F, -0.5F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.gem, -0.5462881F, 0.5462881F, -0.5009095F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf.offsetX, this.staf.offsetY, this.staf.offsetZ);
      GlStateManager.translate(this.staf.rotationPointX * f5, this.staf.rotationPointY * f5, this.staf.rotationPointZ * f5);
      GlStateManager.scale(0.7, 0.9, 0.7);
      GlStateManager.translate(-this.staf.offsetX, -this.staf.offsetY, -this.staf.offsetZ);
      GlStateManager.translate(-this.staf.rotationPointX * f5, -this.staf.rotationPointY * f5, -this.staf.rotationPointZ * f5);
      this.staf.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1.offsetX, this.shape1.offsetY, this.shape1.offsetZ);
      GlStateManager.translate(this.shape1.rotationPointX * f5, this.shape1.rotationPointY * f5, this.shape1.rotationPointZ * f5);
      GlStateManager.scale(0.8, 0.9, 0.8);
      GlStateManager.translate(-this.shape1.offsetX, -this.shape1.offsetY, -this.shape1.offsetZ);
      GlStateManager.translate(-this.shape1.rotationPointX * f5, -this.shape1.rotationPointY * f5, -this.shape1.rotationPointZ * f5);
      this.shape1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.gem.offsetX, this.gem.offsetY, this.gem.offsetZ);
      GlStateManager.translate(this.gem.rotationPointX * f5, this.gem.rotationPointY * f5, this.gem.rotationPointZ * f5);
      GlStateManager.scale(0.85, 1.4, 0.85);
      GlStateManager.translate(-this.gem.offsetX, -this.gem.offsetY, -this.gem.offsetZ);
      GlStateManager.translate(-this.gem.rotationPointX * f5, -this.gem.rotationPointY * f5, -this.gem.rotationPointZ * f5);
      this.gem.render(f5);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
