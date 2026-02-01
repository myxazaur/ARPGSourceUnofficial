package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;

public class VortexInABottleModel extends ModelBase {
   public ModelRenderer shape15;
   public ModelRenderer shape15_1;
   public ModelRenderer shape15_2;
   public ModelRenderer shape15_3;
   public ModelRenderer shape15_4;
   public ModelRenderer shape15_5;
   public ModelRenderer shape15_6;
   public ModelRenderer shape15_7;

   public VortexInABottleModel() {
      this.textureWidth = 8;
      this.textureHeight = 8;
      this.shape15 = new ModelRenderer(this, 0, 3);
      this.shape15.setRotationPoint(0.0F, 6.0F, -3.0F);
      this.shape15.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
      this.shape15_7 = new ModelRenderer(this, 0, 1);
      this.shape15_7.setRotationPoint(-4.0F, 0.0F, 2.0F);
      this.shape15_7.addBox(0.0F, 0.0F, 0.0F, 8, 1, 1, 0.0F);
      this.shape15_1 = new ModelRenderer(this, -3, 1);
      this.shape15_1.setRotationPoint(-4.0F, 0.0F, -3.0F);
      this.shape15_1.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
      this.shape15_4 = new ModelRenderer(this, 0, 0);
      this.shape15_4.setRotationPoint(-1.0F, 3.0F, -4.0F);
      this.shape15_4.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2, 0.0F);
      this.shape15_3 = new ModelRenderer(this, -2, 1);
      this.shape15_3.setRotationPoint(-2.0F, 1.0F, -3.0F);
      this.shape15_3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
      this.shape15_6 = new ModelRenderer(this, -1, 1);
      this.shape15_6.setRotationPoint(0.5F, 1.0F, -3.0F);
      this.shape15_6.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
      this.shape15_5 = new ModelRenderer(this, 5, 1);
      this.shape15_5.setRotationPoint(2.0F, 0.0F, -3.0F);
      this.shape15_5.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
      this.shape15_2 = new ModelRenderer(this, 0, 0);
      this.shape15_2.setRotationPoint(-0.5F, 2.0F, -3.0F);
      this.shape15_2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (f3 == 1.0F) {
         GlStateManager.disableLighting();
         GlStateManager.enableBlend();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape15.offsetX, this.shape15.offsetY, this.shape15.offsetZ);
         GlStateManager.translate(this.shape15.rotationPointX * f5, this.shape15.rotationPointY * f5, this.shape15.rotationPointZ * f5);
         GlStateManager.scale(0.9, 0.95, 0.9);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
         GlStateManager.translate(-this.shape15.offsetX, -this.shape15.offsetY, -this.shape15.offsetZ);
         GlStateManager.translate(-this.shape15.rotationPointX * f5, -this.shape15.rotationPointY * f5, -this.shape15.rotationPointZ * f5);
         GlStateManager.color(1.0F, f1, f2, 1.0F);
         this.shape15.render(f5);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 90.0F, 90.0F);
         GlStateManager.popMatrix();
         GlStateManager.enableLighting();
         GlStateManager.disableBlend();
      }

      if (f3 == 2.0F) {
         this.shape15_7.render(f5);
         this.shape15_1.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape15_4.offsetX, this.shape15_4.offsetY, this.shape15_4.offsetZ);
         GlStateManager.translate(this.shape15_4.rotationPointX * f5, this.shape15_4.rotationPointY * f5, this.shape15_4.rotationPointZ * f5);
         GlStateManager.scale(1.0, 0.5, 1.0);
         GlStateManager.translate(-this.shape15_4.offsetX, -this.shape15_4.offsetY, -this.shape15_4.offsetZ);
         GlStateManager.translate(-this.shape15_4.rotationPointX * f5, -this.shape15_4.rotationPointY * f5, -this.shape15_4.rotationPointZ * f5);
         this.shape15_4.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape15_3.offsetX, this.shape15_3.offsetY, this.shape15_3.offsetZ);
         GlStateManager.translate(this.shape15_3.rotationPointX * f5, this.shape15_3.rotationPointY * f5, this.shape15_3.rotationPointZ * f5);
         GlStateManager.scale(1.5, 1.0, 1.0);
         GlStateManager.translate(-this.shape15_3.offsetX, -this.shape15_3.offsetY, -this.shape15_3.offsetZ);
         GlStateManager.translate(-this.shape15_3.rotationPointX * f5, -this.shape15_3.rotationPointY * f5, -this.shape15_3.rotationPointZ * f5);
         this.shape15_3.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape15_6.offsetX, this.shape15_6.offsetY, this.shape15_6.offsetZ);
         GlStateManager.translate(this.shape15_6.rotationPointX * f5, this.shape15_6.rotationPointY * f5, this.shape15_6.rotationPointZ * f5);
         GlStateManager.scale(1.5, 1.0, 1.0);
         GlStateManager.translate(-this.shape15_6.offsetX, -this.shape15_6.offsetY, -this.shape15_6.offsetZ);
         GlStateManager.translate(-this.shape15_6.rotationPointX * f5, -this.shape15_6.rotationPointY * f5, -this.shape15_6.rotationPointZ * f5);
         this.shape15_6.render(f5);
         GlStateManager.popMatrix();
         this.shape15_5.render(f5);
         this.shape15_2.render(f5);
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
