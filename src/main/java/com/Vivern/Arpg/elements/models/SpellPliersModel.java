package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class SpellPliersModel extends ModelBase {
   public ModelRenderer shape8;
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shape5;
   public ModelRenderer shape6;

   public SpellPliersModel() {
      this.textureWidth = 16;
      this.textureHeight = 16;
      this.shape1 = new ModelRenderer(this, 6, 0);
      this.shape1.setRotationPoint(0.0F, -1.5F, -1.5F);
      this.shape1.addBox(0.0F, -3.0F, -0.5F, 1, 7, 1, 0.0F);
      this.setRotateAngle(this.shape1, 1.1383038F, 0.0F, 0.0F);
      this.shape5 = new ModelRenderer(this, 0, 0);
      this.shape5.mirror = true;
      this.shape5.setRotationPoint(0.0F, 3.8F, 0.0F);
      this.shape5.addBox(-1.02F, 0.0F, -0.5F, 2, 12, 1, 0.0F);
      this.setRotateAngle(this.shape5, 0.4098033F, 0.0F, 0.0F);
      this.shape4 = new ModelRenderer(this, 6, 0);
      this.shape4.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape4.addBox(-1.0F, -3.0F, -0.5F, 1, 7, 1, 0.0F);
      this.setRotateAngle(this.shape4, -0.8196066F, 0.0F, 0.0F);
      this.shape2 = new ModelRenderer(this, 6, 9);
      this.shape2.setRotationPoint(0.0F, -3.0F, 0.0F);
      this.shape2.addBox(-0.98F, -2.8F, -0.5F, 2, 3, 1, 0.0F);
      this.setRotateAngle(this.shape2, -0.4553564F, 0.0F, 0.0F);
      this.shape8 = new ModelRenderer(this, 10, 0);
      this.shape8.setRotationPoint(0.0F, -1.5F, -1.5F);
      this.shape8.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.shape6 = new ModelRenderer(this, 10, 4);
      this.shape6.setRotationPoint(0.0F, -3.0F, 0.0F);
      this.shape6.addBox(-1.02F, -2.8F, -0.5F, 2, 3, 1, 0.0F);
      this.setRotateAngle(this.shape6, 0.4553564F, 0.0F, 0.0F);
      this.shape3 = new ModelRenderer(this, 0, 0);
      this.shape3.setRotationPoint(0.0F, 3.8F, 0.0F);
      this.shape3.addBox(-0.98F, 0.0F, -0.5F, 2, 12, 1, 0.0F);
      this.setRotateAngle(this.shape3, -0.4098033F, 0.0F, 0.0F);
      this.shape4.addChild(this.shape5);
      this.shape1.addChild(this.shape4);
      this.shape1.addChild(this.shape2);
      this.shape4.addChild(this.shape6);
      this.shape1.addChild(this.shape3);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shape4.rotateAngleX = -0.8196066F + f1;
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1.offsetX, this.shape1.offsetY, this.shape1.offsetZ);
      GlStateManager.translate(this.shape1.rotationPointX * f5, this.shape1.rotationPointY * f5, this.shape1.rotationPointZ * f5);
      GlStateManager.scale(0.8, 1.0, 1.0);
      GlStateManager.translate(-this.shape1.offsetX, -this.shape1.offsetY, -this.shape1.offsetZ);
      GlStateManager.translate(-this.shape1.rotationPointX * f5, -this.shape1.rotationPointY * f5, -this.shape1.rotationPointZ * f5);
      this.shape1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape8.offsetX, this.shape8.offsetY, this.shape8.offsetZ);
      GlStateManager.translate(this.shape8.rotationPointX * f5, this.shape8.rotationPointY * f5, this.shape8.rotationPointZ * f5);
      GlStateManager.scale(2.0, 0.5, 0.5);
      GlStateManager.translate(-this.shape8.offsetX, -this.shape8.offsetY, -this.shape8.offsetZ);
      GlStateManager.translate(-this.shape8.rotationPointX * f5, -this.shape8.rotationPointY * f5, -this.shape8.rotationPointZ * f5);
      this.shape8.render(f5);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
