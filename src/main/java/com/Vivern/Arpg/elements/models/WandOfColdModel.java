package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class WandOfColdModel extends ModelBase {
   public ModelRenderer staf;
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shape5;
   public ModelRenderer gem1;
   public ModelRenderer gem2;
   public ModelRenderer gem3;
   public ModelRenderer icicles;

   public WandOfColdModel() {
      this.textureWidth = 34;
      this.textureHeight = 32;
      this.staf = new ModelRenderer(this, 0, 0);
      this.staf.setRotationPoint(0.0F, -2.0F, 4.7F);
      this.staf.addBox(-1.0F, 0.0F, -1.0F, 2, 25, 2, 0.0F);
      this.icicles = new ModelRenderer(this, 8, 9);
      this.icicles.setRotationPoint(-4.0F, -10.7F, 4.3F);
      this.icicles.addBox(0.0F, 0.0F, 0.0F, 12, 22, 1, 0.0F);
      this.shape2 = new ModelRenderer(this, 0, 0);
      this.shape2.setRotationPoint(-2.7F, -3.0F, 4.7F);
      this.shape2.addBox(-1.0F, 0.0F, -1.1F, 2, 7, 2, 0.0F);
      this.setRotateAngle(this.shape2, 0.0F, 0.0F, 0.3642502F);
      this.shape5 = new ModelRenderer(this, 10, 0);
      this.shape5.setRotationPoint(0.0F, -5.6F, 4.7F);
      this.shape5.addBox(-1.5F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
      this.gem3 = new ModelRenderer(this, 0, 28);
      this.gem3.setRotationPoint(4.0F, -10.6F, 5.0F);
      this.gem3.addBox(-1.5F, -1.5F, -1.5F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.gem3, 1.0927507F, 0.3642502F, 0.68294734F);
      this.shape4 = new ModelRenderer(this, 0, 0);
      this.shape4.setRotationPoint(2.7F, -3.0F, 4.7F);
      this.shape4.addBox(-1.0F, 0.0F, -1.1F, 2, 7, 2, 0.0F);
      this.setRotateAngle(this.shape4, 0.0F, 0.0F, -0.3642502F);
      this.gem1 = new ModelRenderer(this, 22, 0);
      this.gem1.setRotationPoint(0.0F, -9.6F, 4.7F);
      this.gem1.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
      this.setRotateAngle(this.gem1, 1.0927507F, 0.3642502F, 0.68294734F);
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(-0.4F, -5.0F, 4.7F);
      this.shape1.addBox(-1.0F, 0.0F, -1.1F, 2, 7, 2, 0.0F);
      this.setRotateAngle(this.shape1, 0.0F, 0.0F, 0.8651597F);
      this.gem2 = new ModelRenderer(this, 0, 28);
      this.gem2.setRotationPoint(-3.0F, -10.6F, 5.0F);
      this.gem2.addBox(-1.5F, -1.5F, -1.5F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.gem2, 1.0927507F, 0.3642502F, 0.68294734F);
      this.shape3 = new ModelRenderer(this, 0, 0);
      this.shape3.setRotationPoint(0.4F, -5.0F, 4.7F);
      this.shape3.addBox(-1.0F, 0.0F, -1.1F, 2, 7, 2, 0.0F);
      this.setRotateAngle(this.shape3, 0.0F, 0.0F, -0.8651597F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      float lbX = OpenGlHelper.lastBrightnessX;
      float lbY = OpenGlHelper.lastBrightnessY;
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf.offsetX, this.staf.offsetY, this.staf.offsetZ);
      GlStateManager.translate(this.staf.rotationPointX * f5, this.staf.rotationPointY * f5, this.staf.rotationPointZ * f5);
      GlStateManager.scale(0.7, 0.9, 0.7);
      GlStateManager.translate(-this.staf.offsetX, -this.staf.offsetY, -this.staf.offsetZ);
      GlStateManager.translate(-this.staf.rotationPointX * f5, -this.staf.rotationPointY * f5, -this.staf.rotationPointZ * f5);
      this.staf.render(f5);
      GlStateManager.popMatrix();
      if (f1 == 0.0F) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.icicles.offsetX, this.icicles.offsetY, this.icicles.offsetZ);
         GlStateManager.translate(this.icicles.rotationPointX * f5, this.icicles.rotationPointY * f5, this.icicles.rotationPointZ * f5);
         GlStateManager.scale(0.7, 0.7, 0.7);
         GlStateManager.translate(-this.icicles.offsetX, -this.icicles.offsetY, -this.icicles.offsetZ);
         GlStateManager.translate(-this.icicles.rotationPointX * f5, -this.icicles.rotationPointY * f5, -this.icicles.rotationPointZ * f5);
         this.icicles.render(f5);
         GlStateManager.popMatrix();
      }

      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape2.offsetX, this.shape2.offsetY, this.shape2.offsetZ);
      GlStateManager.translate(this.shape2.rotationPointX * f5, this.shape2.rotationPointY * f5, this.shape2.rotationPointZ * f5);
      GlStateManager.scale(0.7, 0.7, 0.6);
      GlStateManager.translate(-this.shape2.offsetX, -this.shape2.offsetY, -this.shape2.offsetZ);
      GlStateManager.translate(-this.shape2.rotationPointX * f5, -this.shape2.rotationPointY * f5, -this.shape2.rotationPointZ * f5);
      this.shape2.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape5.offsetX, this.shape5.offsetY, this.shape5.offsetZ);
      GlStateManager.translate(this.shape5.rotationPointX * f5, this.shape5.rotationPointY * f5, this.shape5.rotationPointZ * f5);
      GlStateManager.scale(0.7, 0.7, 0.7);
      GlStateManager.translate(-this.shape5.offsetX, -this.shape5.offsetY, -this.shape5.offsetZ);
      GlStateManager.translate(-this.shape5.rotationPointX * f5, -this.shape5.rotationPointY * f5, -this.shape5.rotationPointZ * f5);
      this.shape5.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape4.offsetX, this.shape4.offsetY, this.shape4.offsetZ);
      GlStateManager.translate(this.shape4.rotationPointX * f5, this.shape4.rotationPointY * f5, this.shape4.rotationPointZ * f5);
      GlStateManager.scale(0.7, 0.7, 0.6);
      GlStateManager.translate(-this.shape4.offsetX, -this.shape4.offsetY, -this.shape4.offsetZ);
      GlStateManager.translate(-this.shape4.rotationPointX * f5, -this.shape4.rotationPointY * f5, -this.shape4.rotationPointZ * f5);
      this.shape4.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape3.offsetX, this.shape3.offsetY, this.shape3.offsetZ);
      GlStateManager.translate(this.shape3.rotationPointX * f5, this.shape3.rotationPointY * f5, this.shape3.rotationPointZ * f5);
      GlStateManager.scale(0.7, 0.7, 0.7);
      GlStateManager.translate(-this.shape3.offsetX, -this.shape3.offsetY, -this.shape3.offsetZ);
      GlStateManager.translate(-this.shape3.rotationPointX * f5, -this.shape3.rotationPointY * f5, -this.shape3.rotationPointZ * f5);
      this.shape3.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1.offsetX, this.shape1.offsetY, this.shape1.offsetZ);
      GlStateManager.translate(this.shape1.rotationPointX * f5, this.shape1.rotationPointY * f5, this.shape1.rotationPointZ * f5);
      GlStateManager.scale(0.7, 0.7, 0.7);
      GlStateManager.translate(-this.shape1.offsetX, -this.shape1.offsetY, -this.shape1.offsetZ);
      GlStateManager.translate(-this.shape1.rotationPointX * f5, -this.shape1.rotationPointY * f5, -this.shape1.rotationPointZ * f5);
      this.shape1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GL11.glDisable(2896);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 170.0F, 170.0F);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.gem1.offsetX, this.gem1.offsetY, this.gem1.offsetZ);
      GlStateManager.translate(this.gem1.rotationPointX * f5, this.gem1.rotationPointY * f5, this.gem1.rotationPointZ * f5);
      GlStateManager.scale(0.7, 0.7, 0.7);
      GlStateManager.translate(-this.gem1.offsetX, -this.gem1.offsetY, -this.gem1.offsetZ);
      GlStateManager.translate(-this.gem1.rotationPointX * f5, -this.gem1.rotationPointY * f5, -this.gem1.rotationPointZ * f5);
      this.gem1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.gem3.offsetX, this.gem3.offsetY, this.gem3.offsetZ);
      GlStateManager.translate(this.gem3.rotationPointX * f5, this.gem3.rotationPointY * f5, this.gem3.rotationPointZ * f5);
      GlStateManager.scale(0.7, 0.7, 0.7);
      GlStateManager.translate(-this.gem3.offsetX, -this.gem3.offsetY, -this.gem3.offsetZ);
      GlStateManager.translate(-this.gem3.rotationPointX * f5, -this.gem3.rotationPointY * f5, -this.gem3.rotationPointZ * f5);
      this.gem3.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.gem2.offsetX, this.gem2.offsetY, this.gem2.offsetZ);
      GlStateManager.translate(this.gem2.rotationPointX * f5, this.gem2.rotationPointY * f5, this.gem2.rotationPointZ * f5);
      GlStateManager.scale(0.7, 0.7, 0.7);
      GlStateManager.translate(-this.gem2.offsetX, -this.gem2.offsetY, -this.gem2.offsetZ);
      GlStateManager.translate(-this.gem2.rotationPointX * f5, -this.gem2.rotationPointY * f5, -this.gem2.rotationPointZ * f5);
      this.gem2.render(f5);
      GlStateManager.popMatrix();
      GL11.glEnable(2896);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
