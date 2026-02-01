package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class SpineMinionModel extends ModelBase {
   public ModelRenderer Head;
   public ModelRenderer shape;
   public ModelRenderer shape_1;
   public ModelRenderer shape_2;
   public ModelRenderer shape_3;
   public ModelRenderer shape_4;
   public ModelRenderer shape_5;
   public ModelRenderer shape_6;
   public ModelRenderer ribs;

   public SpineMinionModel() {
      this.textureWidth = 34;
      this.textureHeight = 28;
      this.shape_6 = new ModelRenderer(this, 0, 0);
      this.shape_6.setRotationPoint(0.0F, 25.2F, 4.1F);
      this.shape_6.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
      this.setRotateAngle(this.shape_6, 0.59184116F, 0.0F, 0.0F);
      this.Head = new ModelRenderer(this, 0, 0);
      this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.Head.addBox(-4.0F, -4.0F, -6.0F, 8, 7, 9, 0.0F);
      this.shape_3 = new ModelRenderer(this, 0, 0);
      this.shape_3.setRotationPoint(0.0F, 15.9F, -2.0F);
      this.shape_3.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
      this.setRotateAngle(this.shape_3, -0.18203785F, 0.0F, 0.0F);
      this.shape_4 = new ModelRenderer(this, 0, 0);
      this.shape_4.setRotationPoint(0.0F, 19.3F, -0.9F);
      this.shape_4.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
      this.setRotateAngle(this.shape_4, 0.31869712F, 0.0F, 0.0F);
      this.ribs = new ModelRenderer(this, 0, 16);
      this.ribs.setRotationPoint(0.0F, 7.4F, -0.8F);
      this.ribs.addBox(-3.0F, -4.0F, -3.0F, 6, 7, 4, 0.0F);
      this.setRotateAngle(this.ribs, 0.045553092F, 0.0F, 0.0F);
      this.shape = new ModelRenderer(this, 0, 0);
      this.shape.setRotationPoint(0.0F, 4.0F, 0.0F);
      this.shape.addBox(-1.0F, -4.0F, -1.0F, 2, 5, 2, 0.0F);
      this.setRotateAngle(this.shape, 0.18203785F, 0.0F, 0.0F);
      this.shape_1 = new ModelRenderer(this, 0, 0);
      this.shape_1.setRotationPoint(0.0F, 8.6F, 0.0F);
      this.shape_1.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
      this.setRotateAngle(this.shape_1, -0.045553092F, 0.0F, 0.0F);
      this.shape_2 = new ModelRenderer(this, 0, 0);
      this.shape_2.setRotationPoint(0.0F, 12.2F, -1.0F);
      this.shape_2.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
      this.setRotateAngle(this.shape_2, -0.22759093F, 0.0F, 0.0F);
      this.shape_5 = new ModelRenderer(this, 0, 0);
      this.shape_5.setRotationPoint(0.0F, 22.3F, 2.1F);
      this.shape_5.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
      this.setRotateAngle(this.shape_5, 0.8196066F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      f5 *= 1.1F;
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape_6.offsetX, this.shape_6.offsetY, this.shape_6.offsetZ);
      GlStateManager.translate(this.shape_6.rotationPointX * f5, this.shape_6.rotationPointY * f5, this.shape_6.rotationPointZ * f5);
      GlStateManager.scale(0.62, 0.62, 0.62);
      GlStateManager.translate(-this.shape_6.offsetX, -this.shape_6.offsetY, -this.shape_6.offsetZ);
      GlStateManager.translate(-this.shape_6.rotationPointX * f5, -this.shape_6.rotationPointY * f5, -this.shape_6.rotationPointZ * f5);
      this.shape_6.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape_3.offsetX, this.shape_3.offsetY, this.shape_3.offsetZ);
      GlStateManager.translate(this.shape_3.rotationPointX * f5, this.shape_3.rotationPointY * f5, this.shape_3.rotationPointZ * f5);
      GlStateManager.scale(0.92, 0.92, 0.92);
      GlStateManager.translate(-this.shape_3.offsetX, -this.shape_3.offsetY, -this.shape_3.offsetZ);
      GlStateManager.translate(-this.shape_3.rotationPointX * f5, -this.shape_3.rotationPointY * f5, -this.shape_3.rotationPointZ * f5);
      this.shape_3.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape_4.offsetX, this.shape_4.offsetY, this.shape_4.offsetZ);
      GlStateManager.translate(this.shape_4.rotationPointX * f5, this.shape_4.rotationPointY * f5, this.shape_4.rotationPointZ * f5);
      GlStateManager.scale(0.82, 0.82, 0.82);
      GlStateManager.translate(-this.shape_4.offsetX, -this.shape_4.offsetY, -this.shape_4.offsetZ);
      GlStateManager.translate(-this.shape_4.rotationPointX * f5, -this.shape_4.rotationPointY * f5, -this.shape_4.rotationPointZ * f5);
      this.shape_4.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape.offsetX, this.shape.offsetY, this.shape.offsetZ);
      GlStateManager.translate(this.shape.rotationPointX * f5, this.shape.rotationPointY * f5, this.shape.rotationPointZ * f5);
      GlStateManager.scale(0.8, 0.8, 0.8);
      GlStateManager.translate(-this.shape.offsetX, -this.shape.offsetY, -this.shape.offsetZ);
      GlStateManager.translate(-this.shape.rotationPointX * f5, -this.shape.rotationPointY * f5, -this.shape.rotationPointZ * f5);
      this.shape.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape_1.offsetX, this.shape_1.offsetY, this.shape_1.offsetZ);
      GlStateManager.translate(this.shape_1.rotationPointX * f5, this.shape_1.rotationPointY * f5, this.shape_1.rotationPointZ * f5);
      GlStateManager.scale(0.9, 0.9, 0.9);
      GlStateManager.translate(-this.shape_1.offsetX, -this.shape_1.offsetY, -this.shape_1.offsetZ);
      GlStateManager.translate(-this.shape_1.rotationPointX * f5, -this.shape_1.rotationPointY * f5, -this.shape_1.rotationPointZ * f5);
      this.shape_1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape_2.offsetX, this.shape_2.offsetY, this.shape_2.offsetZ);
      GlStateManager.translate(this.shape_2.rotationPointX * f5, this.shape_2.rotationPointY * f5, this.shape_2.rotationPointZ * f5);
      GlStateManager.scale(0.92, 0.92, 0.92);
      GlStateManager.translate(-this.shape_2.offsetX, -this.shape_2.offsetY, -this.shape_2.offsetZ);
      GlStateManager.translate(-this.shape_2.rotationPointX * f5, -this.shape_2.rotationPointY * f5, -this.shape_2.rotationPointZ * f5);
      this.shape_2.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape_5.offsetX, this.shape_5.offsetY, this.shape_5.offsetZ);
      GlStateManager.translate(this.shape_5.rotationPointX * f5, this.shape_5.rotationPointY * f5, this.shape_5.rotationPointZ * f5);
      GlStateManager.scale(0.72, 0.72, 0.72);
      GlStateManager.translate(-this.shape_5.offsetX, -this.shape_5.offsetY, -this.shape_5.offsetZ);
      GlStateManager.translate(-this.shape_5.rotationPointX * f5, -this.shape_5.rotationPointY * f5, -this.shape_5.rotationPointZ * f5);
      this.shape_5.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.ribs.offsetX, this.ribs.offsetY, this.ribs.offsetZ);
      GlStateManager.translate(this.ribs.rotationPointX * f5, this.ribs.rotationPointY * f5, this.ribs.rotationPointZ * f5);
      GlStateManager.scale(0.9, 0.9, 0.9);
      GlStateManager.translate(-this.ribs.offsetX, -this.ribs.offsetY, -this.ribs.offsetZ);
      GlStateManager.translate(-this.ribs.rotationPointX * f5, -this.ribs.rotationPointY * f5, -this.ribs.rotationPointZ * f5);
      this.ribs.render(f5);
      GlStateManager.popMatrix();
      float lbX = OpenGlHelper.lastBrightnessX;
      float lbY = OpenGlHelper.lastBrightnessY;
      GlStateManager.pushMatrix();
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, Math.min(240.0F, 100.0F + lbX), Math.min(240.0F, 100.0F + lbY));
      GL11.glEnable(3042);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.translate(this.Head.offsetX, this.Head.offsetY, this.Head.offsetZ);
      GlStateManager.translate(this.Head.rotationPointX * f5, this.Head.rotationPointY * f5, this.Head.rotationPointZ * f5);
      GlStateManager.scale(0.8, 0.8, 0.8);
      GlStateManager.translate(-this.Head.offsetX, -this.Head.offsetY, -this.Head.offsetZ);
      GlStateManager.translate(-this.Head.rotationPointX * f5, -this.Head.rotationPointY * f5, -this.Head.rotationPointZ * f5);
      this.Head.render(f5);
      GL11.glDisable(3042);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbX, lbY);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }

   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
      this.Head.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      this.Head.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
   }
}
