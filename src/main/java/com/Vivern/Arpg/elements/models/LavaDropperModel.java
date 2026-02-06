package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class LavaDropperModel extends ModelBase {
   public ModelRenderer staf;
   public ModelRenderer core;
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shape5;
   public ModelRenderer shape6;

   public LavaDropperModel() {
      this.textureWidth = 20;
      this.textureHeight = 22;
      this.staf = new ModelRenderer(this, 0, 0);
      this.staf.setRotationPoint(0.0F, 6.0F, 5.0F);
      this.staf.addBox(-1.0F, -8.0F, -1.0F, 2, 19, 2, 0.0F);
      this.shape6 = new ModelRenderer(this, 8, 4);
      this.shape6.setRotationPoint(0.0F, 13.5F, 10.5F);
      this.shape6.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shape6, (float) (-Math.PI / 2), 0.0F, 0.0F);
      this.shape5 = new ModelRenderer(this, 8, 0);
      this.shape5.setRotationPoint(0.0F, 13.5F, 6.0F);
      this.shape5.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
      this.setRotateAngle(this.shape5, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.shape3 = new ModelRenderer(this, 8, 0);
      this.shape3.setRotationPoint(0.0F, 13.5F, 4.0F);
      this.shape3.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
      this.setRotateAngle(this.shape3, (float) (-Math.PI / 2), 0.0F, 0.0F);
      this.shape2 = new ModelRenderer(this, 4, 17);
      this.shape2.setRotationPoint(0.0F, -7.75F, 5.0F);
      this.shape2.addBox(-2.0F, 0.0F, -2.0F, 4, 1, 4, 0.0F);
      this.shape4 = new ModelRenderer(this, 8, 4);
      this.shape4.setRotationPoint(0.0F, 13.5F, -0.5F);
      this.shape4.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shape4, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.core = new ModelRenderer(this, 8, 10);
      this.core.setRotationPoint(0.0F, -7.0F, 5.0F);
      this.core.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
      this.shape1 = new ModelRenderer(this, 4, 17);
      this.shape1.setRotationPoint(0.0F, -2.75F, 5.0F);
      this.shape1.addBox(-2.0F, 0.0F, -2.0F, 4, 1, 4, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.staf.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape6.offsetX, this.shape6.offsetY, this.shape6.offsetZ);
      GlStateManager.translate(this.shape6.rotationPointX * f5, this.shape6.rotationPointY * f5, this.shape6.rotationPointZ * f5);
      GlStateManager.scale(0.6, 0.7, 0.6);
      GlStateManager.translate(-this.shape6.offsetX, -this.shape6.offsetY, -this.shape6.offsetZ);
      GlStateManager.translate(-this.shape6.rotationPointX * f5, -this.shape6.rotationPointY * f5, -this.shape6.rotationPointZ * f5);
      this.shape6.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape5.offsetX, this.shape5.offsetY, this.shape5.offsetZ);
      GlStateManager.translate(this.shape5.rotationPointX * f5, this.shape5.rotationPointY * f5, this.shape5.rotationPointZ * f5);
      GlStateManager.scale(1.1, 1.0, 1.1);
      GlStateManager.translate(-this.shape5.offsetX, -this.shape5.offsetY, -this.shape5.offsetZ);
      GlStateManager.translate(-this.shape5.rotationPointX * f5, -this.shape5.rotationPointY * f5, -this.shape5.rotationPointZ * f5);
      this.shape5.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape3.offsetX, this.shape3.offsetY, this.shape3.offsetZ);
      GlStateManager.translate(this.shape3.rotationPointX * f5, this.shape3.rotationPointY * f5, this.shape3.rotationPointZ * f5);
      GlStateManager.scale(1.1, 1.0, 1.1);
      GlStateManager.translate(-this.shape3.offsetX, -this.shape3.offsetY, -this.shape3.offsetZ);
      GlStateManager.translate(-this.shape3.rotationPointX * f5, -this.shape3.rotationPointY * f5, -this.shape3.rotationPointZ * f5);
      this.shape3.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape2.offsetX, this.shape2.offsetY, this.shape2.offsetZ);
      GlStateManager.translate(this.shape2.rotationPointX * f5, this.shape2.rotationPointY * f5, this.shape2.rotationPointZ * f5);
      GlStateManager.scale(0.9, 0.9, 0.9);
      GlStateManager.translate(-this.shape2.offsetX, -this.shape2.offsetY, -this.shape2.offsetZ);
      GlStateManager.translate(-this.shape2.rotationPointX * f5, -this.shape2.rotationPointY * f5, -this.shape2.rotationPointZ * f5);
      this.shape2.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape4.offsetX, this.shape4.offsetY, this.shape4.offsetZ);
      GlStateManager.translate(this.shape4.rotationPointX * f5, this.shape4.rotationPointY * f5, this.shape4.rotationPointZ * f5);
      GlStateManager.scale(0.6, 0.7, 0.6);
      GlStateManager.translate(-this.shape4.offsetX, -this.shape4.offsetY, -this.shape4.offsetZ);
      GlStateManager.translate(-this.shape4.rotationPointX * f5, -this.shape4.rotationPointY * f5, -this.shape4.rotationPointZ * f5);
      this.shape4.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1.offsetX, this.shape1.offsetY, this.shape1.offsetZ);
      GlStateManager.translate(this.shape1.rotationPointX * f5, this.shape1.rotationPointY * f5, this.shape1.rotationPointZ * f5);
      GlStateManager.scale(1.0, 0.9, 1.0);
      GlStateManager.translate(-this.shape1.offsetX, -this.shape1.offsetY, -this.shape1.offsetZ);
      GlStateManager.translate(-this.shape1.rotationPointX * f5, -this.shape1.rotationPointY * f5, -this.shape1.rotationPointZ * f5);
      this.shape1.render(f5);
      GlStateManager.popMatrix();
      float lbX = OpenGlHelper.lastBrightnessX;
      float lbY = OpenGlHelper.lastBrightnessY;
      GlStateManager.pushMatrix();
      GL11.glDisable(2896);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 150.0F, 150.0F);
      GlStateManager.translate(this.core.offsetX, this.core.offsetY, this.core.offsetZ);
      GlStateManager.translate(this.core.rotationPointX * f5, this.core.rotationPointY * f5, this.core.rotationPointZ * f5);
      GlStateManager.scale(1.1, 1.1, 1.1);
      GlStateManager.translate(-this.core.offsetX, -this.core.offsetY, -this.core.offsetZ);
      GlStateManager.translate(-this.core.rotationPointX * f5, -this.core.rotationPointY * f5, -this.core.rotationPointZ * f5);
      this.core.render(f5);
      GL11.glEnable(2896);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbX, lbY);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
