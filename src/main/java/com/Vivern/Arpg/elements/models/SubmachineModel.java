package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class SubmachineModel extends ModelBase {
   public ModelRenderer shapess1;
   public ModelRenderer shapess2;
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shape5;

   public SubmachineModel() {
      this.textureWidth = 26;
      this.textureHeight = 20;
      this.shape3 = new ModelRenderer(this, 20, 15);
      this.shape3.setRotationPoint(-0.5F, -1.0F, -3.0F);
      this.shape3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
      this.shape4 = new ModelRenderer(this, 8, 1);
      this.shape4.setRotationPoint(-1.5F, -2.0F, -9.0F);
      this.shape4.addBox(0.0F, 0.0F, 0.0F, 3, 3, 6, 0.0F);
      this.shape5 = new ModelRenderer(this, 0, 0);
      this.shape5.setRotationPoint(-2.0F, -2.5F, -3.5F);
      this.shape5.addBox(0.0F, 0.0F, 0.0F, 4, 4, 3, 0.0F);
      this.shapess1 = new ModelRenderer(this, 22, 0);
      this.shapess1.setRotationPoint(0.0F, 1.0F, 4.5F);
      this.shapess1.addBox(-0.5F, -0.5F, 0.0F, 1, 6, 1, 0.0F);
      this.setRotateAngle(this.shapess1, 0.18203785F, 0.0F, 0.0F);
      this.shape2 = new ModelRenderer(this, 1, 11);
      this.shape2.setRotationPoint(-1.5F, -1.5F, -1.0F);
      this.shape2.addBox(0.0F, 0.0F, 0.0F, 3, 2, 7, 0.0F);
      this.shapess2 = new ModelRenderer(this, 16, 10);
      this.shapess2.setRotationPoint(0.0F, 5.5F, 2.3F);
      this.shapess2.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 4, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 7);
      this.shape1.setRotationPoint(-1.0F, 0.5F, 0.3F);
      this.shape1.addBox(0.0F, 0.0F, 0.0F, 2, 9, 2, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shape5.render(f5);
      this.shape3.render(f5);
      this.shape4.render(f5);
      this.shape2.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapess1.offsetX, this.shapess1.offsetY, this.shapess1.offsetZ);
      GlStateManager.translate(this.shapess1.rotationPointX * f5, this.shapess1.rotationPointY * f5, this.shapess1.rotationPointZ * f5);
      GlStateManager.scale(1.1, 1.0, 1.2);
      GlStateManager.translate(-this.shapess1.offsetX, -this.shapess1.offsetY, -this.shapess1.offsetZ);
      GlStateManager.translate(-this.shapess1.rotationPointX * f5, -this.shapess1.rotationPointY * f5, -this.shapess1.rotationPointZ * f5);
      this.shapess1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapess2.offsetX, this.shapess2.offsetY, this.shapess2.offsetZ);
      GlStateManager.translate(this.shapess2.rotationPointX * f5, this.shapess2.rotationPointY * f5, this.shapess2.rotationPointZ * f5);
      GlStateManager.scale(1.2, 1.0, 1.2);
      GlStateManager.translate(-this.shapess2.offsetX, -this.shapess2.offsetY, -this.shapess2.offsetZ);
      GlStateManager.translate(-this.shapess2.rotationPointX * f5, -this.shapess2.rotationPointY * f5, -this.shapess2.rotationPointZ * f5);
      this.shapess2.render(f5);
      GlStateManager.popMatrix();
      this.shape1.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
