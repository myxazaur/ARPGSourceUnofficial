package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class SkullCrasherModel extends ModelBase {
   public ModelRenderer staf;
   public ModelRenderer shape;
   public ModelRenderer shape_1;
   public ModelRenderer shape_2;
   public ModelRenderer shape_3;

   public SkullCrasherModel() {
      this.textureWidth = 36;
      this.textureHeight = 31;
      this.shape = new ModelRenderer(this, 8, 18);
      this.shape.setRotationPoint(-2.5F, -5.0F, 1.0F);
      this.shape.addBox(0.0F, 0.0F, 0.0F, 5, 5, 8, 0.0F);
      this.staf = new ModelRenderer(this, 0, 0);
      this.staf.setRotationPoint(0.0F, 6.0F, 5.0F);
      this.staf.addBox(-1.0F, -12.0F, -1.0F, 2, 29, 2, 0.0F);
      this.shape_3 = new ModelRenderer(this, 8, 0);
      this.shape_3.setRotationPoint(0.0F, 0.0F, 7.0F);
      this.shape_3.addBox(-0.5F, 0.0F, 0.0F, 1, 7, 1, 0.0F);
      this.shape_2 = new ModelRenderer(this, 12, 0);
      this.shape_2.setRotationPoint(0.0F, 6.4F, 7.0F);
      this.shape_2.addBox(-0.5F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shape_2, -0.63739425F, 0.0F, 0.0F);
      this.shape_1 = new ModelRenderer(this, 8, 10);
      this.shape_1.setRotationPoint(-3.0F, -5.5F, 2.0F);
      this.shape_1.addBox(0.0F, 0.0F, 0.0F, 6, 6, 2, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shape.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf.offsetX, this.staf.offsetY, this.staf.offsetZ);
      GlStateManager.translate(this.staf.rotationPointX * f5, this.staf.rotationPointY * f5, this.staf.rotationPointZ * f5);
      GlStateManager.scale(0.7, 1.0, 0.7);
      GlStateManager.translate(-this.staf.offsetX, -this.staf.offsetY, -this.staf.offsetZ);
      GlStateManager.translate(-this.staf.rotationPointX * f5, -this.staf.rotationPointY * f5, -this.staf.rotationPointZ * f5);
      this.staf.render(f5);
      GlStateManager.popMatrix();
      this.shape_3.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape_2.offsetX, this.shape_2.offsetY, this.shape_2.offsetZ);
      GlStateManager.translate(this.shape_2.rotationPointX * f5, this.shape_2.rotationPointY * f5, this.shape_2.rotationPointZ * f5);
      GlStateManager.scale(0.9, 1.0, 0.9);
      GlStateManager.translate(-this.shape_2.offsetX, -this.shape_2.offsetY, -this.shape_2.offsetZ);
      GlStateManager.translate(-this.shape_2.rotationPointX * f5, -this.shape_2.rotationPointY * f5, -this.shape_2.rotationPointZ * f5);
      this.shape_2.render(f5);
      GlStateManager.popMatrix();
      this.shape_1.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
