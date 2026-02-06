package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class StaffOfWitherdryModel extends ModelBase {
   public ModelRenderer staf;
   public ModelRenderer fac1;
   public ModelRenderer fac2;
   public ModelRenderer fac3;
   public ModelRenderer light1;
   public ModelRenderer light2;
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer lantern1;
   public ModelRenderer lantern2;
   public ModelRenderer light3;
   public ModelRenderer light4;
   public ModelRenderer light5;
   public ModelRenderer light6;

   public StaffOfWitherdryModel() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      this.light6 = new ModelRenderer(this, 4, 20);
      this.light6.setRotationPoint(0.0F, -14.5F, 5.0F);
      this.light6.addBox(-1.0F, -2.0F, -4.0F, 2, 2, 8, 0.0F);
      this.setRotateAngle(this.light6, 0.0F, (float) (Math.PI * 3.0 / 4.0), 0.0F);
      this.fac3 = new ModelRenderer(this, 4, 10);
      this.fac3.setRotationPoint(0.0F, -10.0F, 5.0F);
      this.fac3.addBox(-1.5F, -2.0F, -2.6F, 3, 4, 1, 0.0F);
      this.setRotateAngle(this.fac3, 0.0F, (float) (Math.PI * 2.0 / 3.0), 0.0F);
      this.shape2 = new ModelRenderer(this, 12, 0);
      this.shape2.setRotationPoint(0.0F, -8.0F, 5.0F);
      this.shape2.addBox(0.0F, 0.0F, -2.1F, 0, 18, 2, 0.0F);
      this.setRotateAngle(this.shape2, 0.0F, (float) (-Math.PI * 2.0 / 3.0), 0.0F);
      this.light2 = new ModelRenderer(this, 4, 20);
      this.light2.setRotationPoint(0.0F, -11.0F, 5.0F);
      this.light2.addBox(-1.0F, -2.0F, -4.0F, 2, 4, 8, 0.0F);
      this.setRotateAngle(this.light2, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.lantern1 = new ModelRenderer(this, 4, 15);
      this.lantern1.setRotationPoint(0.0F, -13.0F, 5.0F);
      this.lantern1.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
      this.shape3 = new ModelRenderer(this, 12, 0);
      this.shape3.setRotationPoint(0.0F, -8.0F, 5.0F);
      this.shape3.addBox(0.0F, 0.0F, -2.1F, 0, 18, 2, 0.0F);
      this.setRotateAngle(this.shape3, 0.0F, (float) (Math.PI * 2.0 / 3.0), 0.0F);
      this.fac2 = new ModelRenderer(this, 4, 5);
      this.fac2.setRotationPoint(0.0F, -10.0F, 5.0F);
      this.fac2.addBox(-1.5F, -2.0F, -2.6F, 3, 4, 1, 0.0F);
      this.setRotateAngle(this.fac2, 0.0F, (float) (-Math.PI * 2.0 / 3.0), 0.0F);
      this.shape1 = new ModelRenderer(this, 12, 0);
      this.shape1.setRotationPoint(0.0F, -8.0F, 5.0F);
      this.shape1.addBox(0.0F, 0.0F, -2.1F, 0, 18, 2, 0.0F);
      this.fac1 = new ModelRenderer(this, 4, 0);
      this.fac1.setRotationPoint(0.0F, -10.0F, 5.0F);
      this.fac1.addBox(-1.5F, -2.0F, -2.6F, 3, 4, 1, 0.0F);
      this.light1 = new ModelRenderer(this, 4, 20);
      this.light1.setRotationPoint(0.0F, -11.0F, 5.0F);
      this.light1.addBox(-1.0F, -2.0F, -4.0F, 2, 4, 8, 0.0F);
      this.light4 = new ModelRenderer(this, 4, 21);
      this.light4.setRotationPoint(0.0F, -16.0F, 5.0F);
      this.light4.addBox(-1.0F, -5.0F, -1.0F, 2, 5, 2, 0.0F);
      this.setRotateAngle(this.light4, 0.0F, (float) (Math.PI / 4), 0.0F);
      this.light5 = new ModelRenderer(this, 4, 20);
      this.light5.setRotationPoint(0.0F, -14.5F, 5.0F);
      this.light5.addBox(-1.0F, -2.0F, -4.0F, 2, 2, 8, 0.0F);
      this.setRotateAngle(this.light5, 0.0F, (float) (Math.PI / 4), 0.0F);
      this.staf = new ModelRenderer(this, 0, -1);
      this.staf.setRotationPoint(0.0F, -9.0F, 5.0F);
      this.staf.addBox(-0.5F, 0.0F, -0.5F, 1, 32, 1, 0.0F);
      this.lantern2 = new ModelRenderer(this, 16, 15);
      this.lantern2.setRotationPoint(0.0F, -16.0F, 5.0F);
      this.lantern2.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.lantern2, 0.0F, (float) (Math.PI / 4), 0.0F);
      this.light3 = new ModelRenderer(this, 16, 21);
      this.light3.setRotationPoint(0.0F, -13.0F, 5.0F);
      this.light3.addBox(-1.0F, -5.0F, -1.0F, 2, 5, 2, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf.offsetX, this.staf.offsetY, this.staf.offsetZ);
      GlStateManager.translate(this.staf.rotationPointX * f5, this.staf.rotationPointY * f5, this.staf.rotationPointZ * f5);
      GlStateManager.scale(1.1, 1.1, 1.1);
      GlStateManager.translate(-this.staf.offsetX, -this.staf.offsetY, -this.staf.offsetZ);
      GlStateManager.translate(-this.staf.rotationPointX * f5, -this.staf.rotationPointY * f5, -this.staf.rotationPointZ * f5);
      this.staf.render(f5);
      GlStateManager.popMatrix();
      if (f == 0.0F) {
         this.shape2.render(f5);
         this.shape3.render(f5);
         this.shape1.render(f5);
      }

      AbstractMobModel.light(100, true);
      this.fac1.render(f5);
      this.fac2.render(f5);
      this.fac3.render(f5);
      AbstractMobModel.returnlight();
      AbstractMobModel.light(240, true);
      this.lantern2.render(f5);
      this.lantern1.render(f5);
      if (f == 0.0F) {
         AbstractMobModel.alphaGlow();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.light1.offsetX, this.light1.offsetY, this.light1.offsetZ);
         GlStateManager.translate(this.light1.rotationPointX * f5, this.light1.rotationPointY * f5, this.light1.rotationPointZ * f5);
         GlStateManager.scale(0.5, 0.75, 0.45);
         GlStateManager.translate(-this.light1.offsetX, -this.light1.offsetY, -this.light1.offsetZ);
         GlStateManager.translate(-this.light1.rotationPointX * f5, -this.light1.rotationPointY * f5, -this.light1.rotationPointZ * f5);
         this.light1.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.light4.offsetX, this.light4.offsetY, this.light4.offsetZ);
         GlStateManager.translate(this.light4.rotationPointX * f5, this.light4.rotationPointY * f5, this.light4.rotationPointZ * f5);
         GlStateManager.scale(0.5, 0.45, 0.5);
         GlStateManager.translate(-this.light4.offsetX, -this.light4.offsetY, -this.light4.offsetZ);
         GlStateManager.translate(-this.light4.rotationPointX * f5, -this.light4.rotationPointY * f5, -this.light4.rotationPointZ * f5);
         this.light4.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.light5.offsetX, this.light5.offsetY, this.light5.offsetZ);
         GlStateManager.translate(this.light5.rotationPointX * f5, this.light5.rotationPointY * f5, this.light5.rotationPointZ * f5);
         GlStateManager.scale(0.5, 0.5, 0.45);
         GlStateManager.translate(-this.light5.offsetX, -this.light5.offsetY, -this.light5.offsetZ);
         GlStateManager.translate(-this.light5.rotationPointX * f5, -this.light5.rotationPointY * f5, -this.light5.rotationPointZ * f5);
         this.light5.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.light3.offsetX, this.light3.offsetY, this.light3.offsetZ);
         GlStateManager.translate(this.light3.rotationPointX * f5, this.light3.rotationPointY * f5, this.light3.rotationPointZ * f5);
         GlStateManager.scale(0.5, 0.45, 0.5);
         GlStateManager.translate(-this.light3.offsetX, -this.light3.offsetY, -this.light3.offsetZ);
         GlStateManager.translate(-this.light3.rotationPointX * f5, -this.light3.rotationPointY * f5, -this.light3.rotationPointZ * f5);
         this.light3.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.light6.offsetX, this.light6.offsetY, this.light6.offsetZ);
         GlStateManager.translate(this.light6.rotationPointX * f5, this.light6.rotationPointY * f5, this.light6.rotationPointZ * f5);
         GlStateManager.scale(0.45, 0.5, 0.5);
         GlStateManager.translate(-this.light6.offsetX, -this.light6.offsetY, -this.light6.offsetZ);
         GlStateManager.translate(-this.light6.rotationPointX * f5, -this.light6.rotationPointY * f5, -this.light6.rotationPointZ * f5);
         this.light6.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.light2.offsetX, this.light2.offsetY, this.light2.offsetZ);
         GlStateManager.translate(this.light2.rotationPointX * f5, this.light2.rotationPointY * f5, this.light2.rotationPointZ * f5);
         GlStateManager.scale(0.45, 0.75, 0.5);
         GlStateManager.translate(-this.light2.offsetX, -this.light2.offsetY, -this.light2.offsetZ);
         GlStateManager.translate(-this.light2.rotationPointX * f5, -this.light2.rotationPointY * f5, -this.light2.rotationPointZ * f5);
         this.light2.render(f5);
         GlStateManager.popMatrix();
         AbstractMobModel.alphaGlowDisable();
      }

      AbstractMobModel.returnlight();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
