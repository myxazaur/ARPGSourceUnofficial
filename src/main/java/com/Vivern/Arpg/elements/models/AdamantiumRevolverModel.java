package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class AdamantiumRevolverModel extends ModelBase {
   public ModelRenderer shape4;
   public ModelRenderer shape2;
   public ModelRenderer shapess1;
   public ModelRenderer shape2_1;
   public ModelRenderer shape2_2;
   public ModelRenderer shape2_3;
   public ModelRenderer shape2_4;
   public ModelRenderer shape4_1;
   public ModelRenderer shape2_5;
   public ModelRenderer shape2_6;

   public AdamantiumRevolverModel() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      this.shapess1 = new ModelRenderer(this, 16, 0);
      this.shapess1.setRotationPoint(0.0F, 1.0F, 4.0F);
      this.shapess1.addBox(-0.5F, 0.5F, -1.0F, 1, 6, 2, 0.0F);
      this.setRotateAngle(this.shapess1, 0.18203785F, 0.0F, 0.0F);
      this.shape2_6 = new ModelRenderer(this, 17, 15);
      this.shape2_6.setRotationPoint(0.0F, 0.0F, -4.0F);
      this.shape2_6.addBox(-1.5F, -4.0F, 0.0F, 3, 1, 4, 0.0F);
      this.shape2_2 = new ModelRenderer(this, 10, 26);
      this.shape2_2.setRotationPoint(-1.5F, -0.5F, 5.5F);
      this.shape2_2.addBox(0.5F, -0.25F, 0.0F, 2, 1, 2, 0.0F);
      this.setRotateAngle(this.shape2_2, 0.68294734F, 0.0F, 0.0F);
      this.shape2_3 = new ModelRenderer(this, 0, 26);
      this.shape2_3.setRotationPoint(-1.5F, 1.5F, 1.0F);
      this.shape2_3.addBox(0.5F, -0.25F, 0.0F, 2, 1, 5, 0.0F);
      this.shape4_1 = new ModelRenderer(this, 20, 8);
      this.shape4_1.setRotationPoint(-1.0F, 0.0F, -9.0F);
      this.shape4_1.addBox(0.5F, 0.5F, 0.0F, 1, 2, 5, 0.0F);
      this.shape4 = new ModelRenderer(this, 0, 0);
      this.shape4.setRotationPoint(-1.5F, -2.0F, -9.0F);
      this.shape4.addBox(0.5F, 0.5F, 0.0F, 2, 2, 6, 0.0F);
      this.shape2_1 = new ModelRenderer(this, 14, 25);
      this.shape2_1.setRotationPoint(0.0F, 2.0F, 1.0F);
      this.shape2_1.addBox(-2.5F, -3.0F, -4.0F, 5, 3, 4, 0.0F);
      this.shape2 = new ModelRenderer(this, 0, 13);
      this.shape2.setRotationPoint(-1.5F, -1.5F, 1.0F);
      this.shape2.addBox(0.0F, -0.25F, 0.0F, 3, 3, 5, 0.0F);
      this.shape2_4 = new ModelRenderer(this, 0, 21);
      this.shape2_4.setRotationPoint(-1.5F, 7.5F, 2.8F);
      this.shape2_4.addBox(0.5F, -0.25F, 0.0F, 2, 1, 4, 0.0F);
      this.setRotateAngle(this.shape2_4, 0.13665928F, 0.0F, 0.0F);
      this.shape2_5 = new ModelRenderer(this, 17, 20);
      this.shape2_5.setRotationPoint(0.0F, 0.0F, -4.0F);
      this.shape2_5.addBox(-1.5F, 0.0F, 0.0F, 3, 1, 4, 0.0F);
      this.shape2_1.addChild(this.shape2_6);
      this.shape2_1.addChild(this.shape2_5);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapess1.offsetX, this.shapess1.offsetY, this.shapess1.offsetZ);
      GlStateManager.translate(this.shapess1.rotationPointX * f5, this.shapess1.rotationPointY * f5, this.shapess1.rotationPointZ * f5);
      GlStateManager.scale(1.1, 1.0, 1.2);
      GlStateManager.translate(-this.shapess1.offsetX, -this.shapess1.offsetY, -this.shapess1.offsetZ);
      GlStateManager.translate(-this.shapess1.rotationPointX * f5, -this.shapess1.rotationPointY * f5, -this.shapess1.rotationPointZ * f5);
      this.shapess1.render(f5);
      GlStateManager.popMatrix();
      this.shape2_2.render(f5);
      this.shape2_3.render(f5);
      this.shape4_1.render(f5);
      this.shape4.render(f5);
      this.shape2_1.render(f5);
      this.shape2.render(f5);
      this.shape2_4.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
