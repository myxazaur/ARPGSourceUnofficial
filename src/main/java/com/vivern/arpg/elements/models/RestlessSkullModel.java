package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;

public class RestlessSkullModel extends ModelBase {
   public ModelRenderer shapessh;
   public ModelRenderer shape1;
   public ModelRenderer skull1;
   public ModelRenderer skull2;
   public ModelRenderer skull3;
   public ModelRenderer skull4;
   public ModelRenderer shape1_1;
   public ModelRenderer shap1;
   public ModelRenderer shap2;
   public ModelRenderer magic1;
   public ModelRenderer magic2;
   public ModelRenderer interfbl;
   public ModelRenderer interfen;
   public ModelRenderer interffl;

   public RestlessSkullModel() {
      this.textureWidth = 16;
      this.textureHeight = 16;
      this.interfbl = new ModelRenderer(this, 0, 0);
      this.interfbl.setRotationPoint(2.0F, 2.1F, -1.5F);
      this.interfbl.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.setRotateAngle(this.interfbl, -1.1383038F, 0.0F, 0.0F);
      this.skull2 = new ModelRenderer(this, 4, 7);
      this.skull2.setRotationPoint(0.0F, 3.4F, -2.1F);
      this.skull2.addBox(-1.0F, 0.0F, -0.5F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.skull2, -0.4553564F, 0.0F, 0.0F);
      this.magic2 = new ModelRenderer(this, 12, 2);
      this.magic2.setRotationPoint(-1.0F, 3.3F, -1.8F);
      this.magic2.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 7);
      this.shape1.setRotationPoint(0.0F, 0.0F, 2.0F);
      this.shape1.addBox(-0.5F, -1.5F, -1.5F, 1, 5, 1, 0.0F);
      this.setRotateAngle(this.shape1, 1.4114478F, 0.0F, 0.0F);
      this.shapessh = new ModelRenderer(this, 12, 7);
      this.shapessh.setRotationPoint(0.0F, 2.0F, 4.6F);
      this.shapessh.addBox(-0.5F, -1.0F, 0.0F, 1, 5, 1, 0.0F);
      this.setRotateAngle(this.shapessh, 0.13665928F, 0.0F, 0.0F);
      this.magic1 = new ModelRenderer(this, 12, 2);
      this.magic1.setRotationPoint(1.0F, 3.3F, -1.8F);
      this.magic1.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.interfen = new ModelRenderer(this, 0, 2);
      this.interfen.setRotationPoint(2.1F, 3.25F, -0.9F);
      this.interfen.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.setRotateAngle(this.interfen, -0.63739425F, 0.0F, 0.0F);
      this.interffl = new ModelRenderer(this, 12, 2);
      this.interffl.setRotationPoint(1.9F, 4.2F, 0.2F);
      this.interffl.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.setRotateAngle(this.interffl, -0.22759093F, 0.0F, 0.0F);
      this.skull3 = new ModelRenderer(this, 1, 11);
      this.skull3.setRotationPoint(0.0F, 4.9F, -2.7F);
      this.skull3.addBox(-1.5F, 0.0F, -0.6F, 3, 2, 3, 0.0F);
      this.setRotateAngle(this.skull3, 0.3642502F, 0.0F, 0.0F);
      this.skull4 = new ModelRenderer(this, 12, 0);
      this.skull4.setRotationPoint(0.0F, 3.1F, -2.1F);
      this.skull4.addBox(-0.5F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
      this.setRotateAngle(this.skull4, 0.091106184F, 0.0F, 0.0F);
      this.skull1 = new ModelRenderer(this, 0, 0);
      this.skull1.setRotationPoint(0.0F, 1.4F, -2.0F);
      this.skull1.addBox(-2.0F, 0.0F, -0.5F, 4, 3, 4, 0.0F);
      this.setRotateAngle(this.skull1, -0.13665928F, 0.0F, 0.0F);
      this.shape1_1 = new ModelRenderer(this, 0, 7);
      this.shape1_1.setRotationPoint(0.0F, 0.0F, 1.0F);
      this.shape1_1.addBox(-0.5F, -2.5F, -1.5F, 1, 2, 1, 0.0F);
      this.setRotateAngle(this.shape1_1, 1.8668041F, 0.0F, 0.0F);
      this.shap1 = new ModelRenderer(this, 0, 7);
      this.shap1.setRotationPoint(0.0F, 4.5F, 0.9F);
      this.shap1.addBox(-0.5F, 0.0F, -1.5F, 1, 2, 1, 0.0F);
      this.setRotateAngle(this.shap1, 0.18203785F, 0.0F, 0.0F);
      this.shap2 = new ModelRenderer(this, 0, 10);
      this.shap2.setRotationPoint(0.0F, 6.5F, 0.9F);
      this.shap2.addBox(-0.5F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
      this.setRotateAngle(this.shap2, 0.4098033F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.skull2.render(f5);
      this.shape1.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapessh.offsetX, this.shapessh.offsetY, this.shapessh.offsetZ);
      GlStateManager.translate(this.shapessh.rotationPointX * f5, this.shapessh.rotationPointY * f5, this.shapessh.rotationPointZ * f5);
      GlStateManager.scale(1.05, 1.0, 1.0);
      GlStateManager.translate(-this.shapessh.offsetX, -this.shapessh.offsetY, -this.shapessh.offsetZ);
      GlStateManager.translate(-this.shapessh.rotationPointX * f5, -this.shapessh.rotationPointY * f5, -this.shapessh.rotationPointZ * f5);
      this.shapessh.render(f5);
      GlStateManager.popMatrix();
      this.skull3.render(f5);
      this.skull4.render(f5);
      this.skull1.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1_1.offsetX, this.shape1_1.offsetY, this.shape1_1.offsetZ);
      GlStateManager.translate(this.shape1_1.rotationPointX * f5, this.shape1_1.rotationPointY * f5, this.shape1_1.rotationPointZ * f5);
      GlStateManager.scale(0.95, 1.0, 1.0);
      GlStateManager.translate(-this.shape1_1.offsetX, -this.shape1_1.offsetY, -this.shape1_1.offsetZ);
      GlStateManager.translate(-this.shape1_1.rotationPointX * f5, -this.shape1_1.rotationPointY * f5, -this.shape1_1.rotationPointZ * f5);
      this.shape1_1.render(f5);
      GlStateManager.popMatrix();
      this.shap1.render(f5);
      this.shap2.render(f5);
      AbstractMobModel.light(220, false);
      this.magic2.render(f5);
      this.magic1.render(f5);
      if (f > 0.0F) {
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
      } else {
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 10.0F, 10.0F);
      }

      GlStateManager.pushMatrix();
      GlStateManager.translate(this.interfbl.offsetX, this.interfbl.offsetY, this.interfbl.offsetZ);
      GlStateManager.translate(this.interfbl.rotationPointX * f5, this.interfbl.rotationPointY * f5, this.interfbl.rotationPointZ * f5);
      GlStateManager.scale(0.7, 0.7, 0.7);
      GlStateManager.translate(-this.interfbl.offsetX, -this.interfbl.offsetY, -this.interfbl.offsetZ);
      GlStateManager.translate(-this.interfbl.rotationPointX * f5, -this.interfbl.rotationPointY * f5, -this.interfbl.rotationPointZ * f5);
      this.interfbl.render(f5);
      GlStateManager.popMatrix();
      if (f1 > 0.0F) {
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
      } else {
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 10.0F, 10.0F);
      }

      GlStateManager.pushMatrix();
      GlStateManager.translate(this.interfen.offsetX, this.interfen.offsetY, this.interfen.offsetZ);
      GlStateManager.translate(this.interfen.rotationPointX * f5, this.interfen.rotationPointY * f5, this.interfen.rotationPointZ * f5);
      GlStateManager.scale(0.7, 0.7, 0.7);
      GlStateManager.translate(-this.interfen.offsetX, -this.interfen.offsetY, -this.interfen.offsetZ);
      GlStateManager.translate(-this.interfen.rotationPointX * f5, -this.interfen.rotationPointY * f5, -this.interfen.rotationPointZ * f5);
      this.interfen.render(f5);
      GlStateManager.popMatrix();
      if (f2 > 0.0F) {
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
      } else {
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 10.0F, 10.0F);
      }

      GlStateManager.pushMatrix();
      GlStateManager.translate(this.interffl.offsetX, this.interffl.offsetY, this.interffl.offsetZ);
      GlStateManager.translate(this.interffl.rotationPointX * f5, this.interffl.rotationPointY * f5, this.interffl.rotationPointZ * f5);
      GlStateManager.scale(0.7, 0.7, 0.7);
      GlStateManager.translate(-this.interffl.offsetX, -this.interffl.offsetY, -this.interffl.offsetZ);
      GlStateManager.translate(-this.interffl.rotationPointX * f5, -this.interffl.rotationPointY * f5, -this.interffl.rotationPointZ * f5);
      this.interffl.render(f5);
      GlStateManager.popMatrix();
      AbstractMobModel.returnlight();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
