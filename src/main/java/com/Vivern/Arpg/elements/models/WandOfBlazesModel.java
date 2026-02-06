package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.AnimationTimer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;

public class WandOfBlazesModel extends ModelBase {
   public ModelRenderer staf1;
   public ModelRenderer staf2;
   public ModelRenderer staf3;
   public ModelRenderer staf4;
   public ModelRenderer staf5;
   public ModelRenderer staf6;
   public ModelRenderer staf7;
   public ModelRenderer core;

   public WandOfBlazesModel() {
      this.textureWidth = 16;
      this.textureHeight = 32;
      this.staf3 = new ModelRenderer(this, 8, 0);
      this.staf3.setRotationPoint(0.0F, -7.0F, 5.0F);
      this.staf3.addBox(-1.0F, -7.0F, -1.0F, 2, 7, 2, 0.0F);
      this.setRotateAngle(this.staf3, -0.7740535F, (float) (-Math.PI * 2.0 / 3.0), 0.0F);
      this.staf6 = new ModelRenderer(this, 8, 20);
      this.staf6.setRotationPoint(2.0F, -8.0F, 7.0F);
      this.staf6.addBox(-1.0F, -5.0F, -1.0F, 2, 5, 2, 0.0F);
      this.setRotateAngle(this.staf6, -0.8196066F, -0.3642502F, -0.5009095F);
      this.core = new ModelRenderer(this, 8, 28);
      this.core.setRotationPoint(0.0F, -10.5F, 5.0F);
      this.core.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.core, -1.2292354F, -2.4130921F, 2.5497515F);
      this.staf1 = new ModelRenderer(this, 8, 0);
      this.staf1.setRotationPoint(0.0F, -7.0F, 5.0F);
      this.staf1.addBox(-1.0F, -6.5F, -1.0F, 2, 7, 2, 0.0F);
      this.setRotateAngle(this.staf1, 1.1383038F, (float) (-Math.PI * 2.0 / 3.0), 0.0F);
      this.staf4 = new ModelRenderer(this, 8, 20);
      this.staf4.setRotationPoint(-2.3F, -12.8F, 4.7F);
      this.staf4.addBox(-1.0F, -5.0F, -1.0F, 2, 5, 2, 0.0F);
      this.setRotateAngle(this.staf4, -1.2292354F, -2.4130921F, 2.5497515F);
      this.staf5 = new ModelRenderer(this, 8, 9);
      this.staf5.setRotationPoint(-3.0F, -11.0F, 4.0F);
      this.staf5.addBox(-1.0F, -7.0F, -1.0F, 2, 8, 2, 0.0F);
      this.setRotateAngle(this.staf5, 0.63739425F, -2.5953045F, 0.0F);
      this.staf2 = new ModelRenderer(this, 0, 0);
      this.staf2.setRotationPoint(0.0F, 0.0F, 5.0F);
      this.staf2.addBox(-1.0F, -9.0F, -1.0F, 2, 30, 2, 0.0F);
      this.staf7 = new ModelRenderer(this, 8, 9);
      this.staf7.setRotationPoint(0.7F, -13.5F, 2.7F);
      this.staf7.addBox(-1.0F, -2.5F, -0.1F, 2, 9, 2, 0.0F);
      this.setRotateAngle(this.staf7, 0.091106184F, -2.959555F, -0.13665928F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotateAngle(this.core, AnimationTimer.rainbow1 * 0.28F, AnimationTimer.tick * 0.089F, AnimationTimer.rainbow2 * 0.11F);
      float lbX = OpenGlHelper.lastBrightnessX;
      float lbY = OpenGlHelper.lastBrightnessY;
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf2.offsetX, this.staf2.offsetY, this.staf2.offsetZ);
      GlStateManager.translate(this.staf2.rotationPointX * f5, this.staf2.rotationPointY * f5, this.staf2.rotationPointZ * f5);
      GlStateManager.scale(0.7, 0.75, 0.7);
      GlStateManager.translate(-this.staf2.offsetX, -this.staf2.offsetY, -this.staf2.offsetZ);
      GlStateManager.translate(-this.staf2.rotationPointX * f5, -this.staf2.rotationPointY * f5, -this.staf2.rotationPointZ * f5);
      this.staf2.render(f5);
      GlStateManager.popMatrix();
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, Math.min(240.0F, 40.0F + lbX), Math.min(240.0F, 40.0F + lbY));
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf3.offsetX, this.staf3.offsetY, this.staf3.offsetZ);
      GlStateManager.translate(this.staf3.rotationPointX * f5, this.staf3.rotationPointY * f5, this.staf3.rotationPointZ * f5);
      GlStateManager.scale(0.7, 0.7, 0.7);
      GlStateManager.translate(-this.staf3.offsetX, -this.staf3.offsetY, -this.staf3.offsetZ);
      GlStateManager.translate(-this.staf3.rotationPointX * f5, -this.staf3.rotationPointY * f5, -this.staf3.rotationPointZ * f5);
      this.staf3.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf1.offsetX, this.staf1.offsetY, this.staf1.offsetZ);
      GlStateManager.translate(this.staf1.rotationPointX * f5, this.staf1.rotationPointY * f5, this.staf1.rotationPointZ * f5);
      GlStateManager.scale(0.7, 0.7, 0.7);
      GlStateManager.translate(-this.staf1.offsetX, -this.staf1.offsetY, -this.staf1.offsetZ);
      GlStateManager.translate(-this.staf1.rotationPointX * f5, -this.staf1.rotationPointY * f5, -this.staf1.rotationPointZ * f5);
      this.staf1.render(f5);
      GlStateManager.popMatrix();
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, Math.min(240.0F, 100.0F + lbX), Math.min(240.0F, 100.0F + lbY));
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf4.offsetX, this.staf4.offsetY, this.staf4.offsetZ);
      GlStateManager.translate(this.staf4.rotationPointX * f5, this.staf4.rotationPointY * f5, this.staf4.rotationPointZ * f5);
      GlStateManager.scale(0.7, 0.7, 0.7);
      GlStateManager.translate(-this.staf4.offsetX, -this.staf4.offsetY, -this.staf4.offsetZ);
      GlStateManager.translate(-this.staf4.rotationPointX * f5, -this.staf4.rotationPointY * f5, -this.staf4.rotationPointZ * f5);
      this.staf4.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf5.offsetX, this.staf5.offsetY, this.staf5.offsetZ);
      GlStateManager.translate(this.staf5.rotationPointX * f5, this.staf5.rotationPointY * f5, this.staf5.rotationPointZ * f5);
      GlStateManager.scale(0.7, 0.7, 0.7);
      GlStateManager.translate(-this.staf5.offsetX, -this.staf5.offsetY, -this.staf5.offsetZ);
      GlStateManager.translate(-this.staf5.rotationPointX * f5, -this.staf5.rotationPointY * f5, -this.staf5.rotationPointZ * f5);
      this.staf5.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf7.offsetX, this.staf7.offsetY, this.staf7.offsetZ);
      GlStateManager.translate(this.staf7.rotationPointX * f5, this.staf7.rotationPointY * f5, this.staf7.rotationPointZ * f5);
      GlStateManager.scale(0.7, 0.7, 0.7);
      GlStateManager.translate(-this.staf7.offsetX, -this.staf7.offsetY, -this.staf7.offsetZ);
      GlStateManager.translate(-this.staf7.rotationPointX * f5, -this.staf7.rotationPointY * f5, -this.staf7.rotationPointZ * f5);
      this.staf7.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf6.offsetX, this.staf6.offsetY, this.staf6.offsetZ);
      GlStateManager.translate(this.staf6.rotationPointX * f5, this.staf6.rotationPointY * f5, this.staf6.rotationPointZ * f5);
      GlStateManager.scale(0.7, 0.7, 0.7);
      GlStateManager.translate(-this.staf6.offsetX, -this.staf6.offsetY, -this.staf6.offsetZ);
      GlStateManager.translate(-this.staf6.rotationPointX * f5, -this.staf6.rotationPointY * f5, -this.staf6.rotationPointZ * f5);
      this.staf6.render(f5);
      GlStateManager.popMatrix();
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, Math.min(240.0F, 190.0F + lbX), Math.min(240.0F, 190.0F + lbY));
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.core.offsetX, this.core.offsetY, this.core.offsetZ);
      GlStateManager.translate(this.core.rotationPointX * f5, this.core.rotationPointY * f5, this.core.rotationPointZ * f5);
      GlStateManager.scale(0.9, 0.9, 0.9);
      GlStateManager.translate(-this.core.offsetX, -this.core.offsetY, -this.core.offsetZ);
      GlStateManager.translate(-this.core.rotationPointX * f5, -this.core.rotationPointY * f5, -this.core.rotationPointZ * f5);
      this.core.render(f5);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
