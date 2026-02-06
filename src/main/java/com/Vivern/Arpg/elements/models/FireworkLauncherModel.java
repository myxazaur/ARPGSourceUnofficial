package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class FireworkLauncherModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape1_1;
   public ModelRenderer shape1_2;
   public ModelRenderer shape1_3;
   public ModelRenderer shape1_4;
   public ModelRenderer shape1_5;
   public ModelRenderer shape1_6;
   public ModelRenderer shape1_7;
   public ModelRenderer shape1_8;
   public ModelRenderer shape1_9;
   public ModelRenderer shape1_10;
   public ModelRenderer shape1_11;
   public ModelRenderer shape1_12;
   public ModelRenderer round1;
   public ModelRenderer round2;
   public ModelRenderer rocket;
   public ModelRenderer shape1_13;
   public ModelRenderer shape1_14;
   public ModelRenderer shape1_15;
   public ModelRenderer shape1_16;

   public FireworkLauncherModel() {
      this.textureWidth = 64;
      this.textureHeight = 32;
      this.shape1_1 = new ModelRenderer(this, 46, 23);
      this.shape1_1.setRotationPoint(0.0F, -3.5F, 5.0F);
      this.shape1_1.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 5, 0.0F);
      this.shape1_9 = new ModelRenderer(this, 0, 15);
      this.shape1_9.setRotationPoint(0.0F, -3.5F, 4.0F);
      this.shape1_9.addBox(-3.0F, -2.0F, -2.0F, 6, 10, 1, 0.0F);
      this.shape1_14 = new ModelRenderer(this, 46, 7);
      this.shape1_14.setRotationPoint(0.0F, -3.9F, -3.0F);
      this.shape1_14.addBox(-4.3F, -1.3F, 3.0F, 2, 1, 7, 0.0F);
      this.setRotateAngle(this.shape1_14, 0.091106184F, 0.091106184F, 0.0F);
      this.round1 = new ModelRenderer(this, 25, 7);
      this.round1.setRotationPoint(0.0F, -2.0781987E-15F, -2.0F);
      this.round1.addBox(-2.0F, -3.5F, -2.0F, 4, 7, 6, 0.0F);
      this.shape1_6 = new ModelRenderer(this, 13, 5);
      this.shape1_6.setRotationPoint(0.0F, -1.7F, -9.0F);
      this.shape1_6.addBox(-1.5F, -1.0F, -4.0F, 3, 2, 6, 0.0F);
      this.setRotateAngle(this.shape1_6, 0.4553564F, 0.0F, 0.0F);
      this.shape1_13 = new ModelRenderer(this, 46, 7);
      this.shape1_13.setRotationPoint(0.0F, -6.5F, -9.0F);
      this.shape1_13.addBox(-4.3F, -1.3F, 3.0F, 2, 2, 7, 0.0F);
      this.setRotateAngle(this.shape1_13, -0.22759093F, 0.0F, 0.0F);
      this.shape1 = new ModelRenderer(this, 46, 23);
      this.shape1.setRotationPoint(0.0F, -3.5F, -6.8F);
      this.shape1.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 5, 0.0F);
      this.rocket = new ModelRenderer(this, 0, 7);
      this.rocket.setRotationPoint(0.0F, -2.5F, -2.0F);
      this.rocket.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 6, 0.0F);
      this.round2 = new ModelRenderer(this, 22, 20);
      this.round2.setRotationPoint(0.0F, -2.0781987E-15F, -2.0F);
      this.round2.addBox(-3.0F, -2.5F, -2.0F, 6, 5, 6, 0.0F);
      this.shape1_16 = new ModelRenderer(this, 46, 7);
      this.shape1_16.setRotationPoint(0.0F, -3.9F, -3.0F);
      this.shape1_16.addBox(2.3F, -1.3F, 3.0F, 2, 1, 7, 0.0F);
      this.setRotateAngle(this.shape1_16, 0.091106184F, -0.091106184F, 0.0F);
      this.shape1_2 = new ModelRenderer(this, 50, 0);
      this.shape1_2.setRotationPoint(0.0F, -2.5F, -12.0F);
      this.shape1_2.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 5, 0.0F);
      this.shape1_3 = new ModelRenderer(this, 13, 5);
      this.shape1_3.setRotationPoint(0.0F, -5.3F, -9.0F);
      this.shape1_3.addBox(-1.5F, -1.0F, -5.0F, 3, 2, 6, 0.0F);
      this.setRotateAngle(this.shape1_3, -0.22759093F, 0.0F, 0.0F);
      this.shape1_10 = new ModelRenderer(this, 4, 26);
      this.shape1_10.setRotationPoint(0.0F, -3.5F, 24.0F);
      this.shape1_10.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 1, 0.0F);
      this.shape1_15 = new ModelRenderer(this, 46, 7);
      this.shape1_15.setRotationPoint(0.0F, -6.5F, -9.0F);
      this.shape1_15.addBox(2.3F, -1.3F, 3.0F, 2, 2, 7, 0.0F);
      this.setRotateAngle(this.shape1_15, -0.22759093F, 0.0F, 0.0F);
      this.shape1_4 = new ModelRenderer(this, 44, 16);
      this.shape1_4.setRotationPoint(0.0F, -5.5F, -9.0F);
      this.shape1_4.addBox(-3.0F, -2.0F, 1.0F, 6, 3, 4, 0.0F);
      this.setRotateAngle(this.shape1_4, -0.22759093F, 0.0F, 0.0F);
      this.shape1_11 = new ModelRenderer(this, 0, 17);
      this.shape1_11.setRotationPoint(0.0F, -3.5F, 10.0F);
      this.shape1_11.addBox(-2.0F, -2.0F, -2.0F, 4, 1, 14, 0.0F);
      this.shape1_5 = new ModelRenderer(this, 39, 7);
      this.shape1_5.setRotationPoint(0.0F, -5.8F, -8.0F);
      this.shape1_5.addBox(-2.0F, -1.0F, -6.8F, 4, 3, 2, 0.0F);
      this.setRotateAngle(this.shape1_5, -0.22759093F, 0.0F, 0.0F);
      this.shape1_8 = new ModelRenderer(this, 36, 0);
      this.shape1_8.setRotationPoint(0.0F, -1.7F, -9.0F);
      this.shape1_8.addBox(-2.0F, -0.5F, 1.5F, 4, 3, 3, 0.0F);
      this.setRotateAngle(this.shape1_8, 0.4553564F, 0.0F, 0.0F);
      this.shape1_7 = new ModelRenderer(this, 39, 7);
      this.shape1_7.setRotationPoint(0.0F, -1.7F, -9.0F);
      this.shape1_7.addBox(-2.0F, -1.3F, -5.5F, 4, 3, 2, 0.0F);
      this.setRotateAngle(this.shape1_7, 0.4553564F, 0.0F, 0.0F);
      this.shape1_12 = new ModelRenderer(this, 10, 0);
      this.shape1_12.setRotationPoint(0.0F, -0.5F, 7.0F);
      this.shape1_12.addBox(-1.0F, -2.0F, -2.0F, 2, 6, 2, 0.0F);
      this.setRotateAngle(this.shape1_12, 0.4098033F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shape1_1.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1_9.offsetX, this.shape1_9.offsetY, this.shape1_9.offsetZ);
      GlStateManager.translate(this.shape1_9.rotationPointX * f5, this.shape1_9.rotationPointY * f5, this.shape1_9.rotationPointZ * f5);
      GlStateManager.scale(0.8, 1.0, 1.0);
      GlStateManager.translate(-this.shape1_9.offsetX, -this.shape1_9.offsetY, -this.shape1_9.offsetZ);
      GlStateManager.translate(-this.shape1_9.rotationPointX * f5, -this.shape1_9.rotationPointY * f5, -this.shape1_9.rotationPointZ * f5);
      this.shape1_9.render(f5);
      GlStateManager.popMatrix();
      this.shape1_14.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1_6.offsetX, this.shape1_6.offsetY, this.shape1_6.offsetZ);
      GlStateManager.translate(this.shape1_6.rotationPointX * f5, this.shape1_6.rotationPointY * f5, this.shape1_6.rotationPointZ * f5);
      GlStateManager.scale(1.1, 1.0, 1.0);
      GlStateManager.translate(-this.shape1_6.offsetX, -this.shape1_6.offsetY, -this.shape1_6.offsetZ);
      GlStateManager.translate(-this.shape1_6.rotationPointX * f5, -this.shape1_6.rotationPointY * f5, -this.shape1_6.rotationPointZ * f5);
      this.shape1_6.render(f5);
      GlStateManager.popMatrix();
      this.shape1_13.render(f5);
      this.shape1.render(f5);
      if (f1 == -1.0F) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.rocket.offsetX, this.rocket.offsetY, this.rocket.offsetZ);
         GlStateManager.translate(this.rocket.rotationPointX * f5, this.rocket.rotationPointY * f5, this.rocket.rotationPointZ * f5);
         GlStateManager.scale(1.2, 1.2, 1.0);
         GlStateManager.translate(-this.rocket.offsetX, -this.rocket.offsetY, -this.rocket.offsetZ);
         GlStateManager.translate(-this.rocket.rotationPointX * f5, -this.rocket.rotationPointY * f5, -this.rocket.rotationPointZ * f5);
         this.rocket.render(f5);
         GlStateManager.popMatrix();
      }

      if (f1 >= 0.0F) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.round2.offsetX, this.round2.offsetY, this.round2.offsetZ);
         GlStateManager.translate(this.round2.rotationPointX * f5, this.round2.rotationPointY * f5, this.round2.rotationPointZ * f5);
         GlStateManager.scale(1.0, 1.0, 1.01);
         GlStateManager.translate(-this.round2.offsetX, -this.round2.offsetY, -this.round2.offsetZ);
         GlStateManager.translate(-this.round2.rotationPointX * f5, -this.round2.rotationPointY * f5, -this.round2.rotationPointZ * f5);
         GlStateManager.rotate(f1, 0.0F, 0.0F, 1.0F);
         this.round2.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.rotate(f1, 0.0F, 0.0F, 1.0F);
         this.round1.render(f5);
         GlStateManager.popMatrix();
      }

      this.shape1_16.render(f5);
      this.shape1_2.render(f5);
      this.shape1_3.render(f5);
      this.shape1_10.render(f5);
      this.shape1_15.render(f5);
      this.shape1_4.render(f5);
      this.shape1_11.render(f5);
      this.shape1_5.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1_8.offsetX, this.shape1_8.offsetY, this.shape1_8.offsetZ);
      GlStateManager.translate(this.shape1_8.rotationPointX * f5, this.shape1_8.rotationPointY * f5, this.shape1_8.rotationPointZ * f5);
      GlStateManager.scale(1.1, 1.0, 1.0);
      GlStateManager.translate(-this.shape1_8.offsetX, -this.shape1_8.offsetY, -this.shape1_8.offsetZ);
      GlStateManager.translate(-this.shape1_8.rotationPointX * f5, -this.shape1_8.rotationPointY * f5, -this.shape1_8.rotationPointZ * f5);
      this.shape1_8.render(f5);
      GlStateManager.popMatrix();
      this.shape1_7.render(f5);
      this.shape1_12.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
