package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class AzureOreStaffModel extends ModelBase {
   public ModelRenderer staf1;
   public ModelRenderer staf2;
   public ModelRenderer ore1;
   public ModelRenderer ore2;
   public ModelRenderer tip1;
   public ModelRenderer tip2;
   public ModelRenderer tip3;
   public ModelRenderer dtip1;
   public ModelRenderer dtip2;
   public ModelRenderer dtip3;
   public ModelRenderer staf3;

   public AzureOreStaffModel() {
      this.textureWidth = 32;
      this.textureHeight = 16;
      this.ore1 = new ModelRenderer(this, 8, 0);
      this.ore1.setRotationPoint(0.0F, -9.0F, 5.0F);
      this.ore1.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.ore1, 0.68294734F, 0.27314404F, -0.68294734F);
      this.staf3 = new ModelRenderer(this, 0, 0);
      this.staf3.setRotationPoint(0.0F, 11.8F, 0.0F);
      this.staf3.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, 0.0F);
      this.setRotateAngle(this.staf3, -0.18203785F, 0.0F, 0.18203785F);
      this.ore2 = new ModelRenderer(this, 8, 8);
      this.ore2.setRotationPoint(0.0F, -9.0F, 5.0F);
      this.ore2.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 4, -0.95F);
      this.setRotateAngle(this.ore2, 0.68294734F, 0.27314404F, -0.68294734F);
      this.staf1 = new ModelRenderer(this, 0, 0);
      this.staf1.setRotationPoint(0.0F, -5.0F, 5.0F);
      this.staf1.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
      this.tip3 = new ModelRenderer(this, 17, 1);
      this.tip3.setRotationPoint(0.0F, -6.0F, 5.0F);
      this.tip3.addBox(-0.5F, -5.0F, -2.6F, 1, 5, 1, 0.0F);
      this.setRotateAngle(this.tip3, 0.0F, (float) Math.PI, 0.0F);
      this.dtip1 = new ModelRenderer(this, 17, 2);
      this.dtip1.setRotationPoint(0.0F, -2.0F, 5.0F);
      this.dtip1.addBox(-0.5F, -5.0F, -1.6F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.dtip1, 0.0F, 1.0016445F, 0.0F);
      this.dtip3 = new ModelRenderer(this, 17, 2);
      this.dtip3.setRotationPoint(0.0F, -2.0F, 5.0F);
      this.dtip3.addBox(-0.5F, -5.0F, -1.6F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.dtip3, 0.0F, (float) Math.PI, 0.0F);
      this.tip2 = new ModelRenderer(this, 17, 1);
      this.tip2.setRotationPoint(0.0F, -6.0F, 5.0F);
      this.tip2.addBox(-0.5F, -5.0F, -2.6F, 1, 5, 1, 0.0F);
      this.setRotateAngle(this.tip2, 0.0F, -0.7740535F, 0.0F);
      this.staf2 = new ModelRenderer(this, 24, 0);
      this.staf2.setRotationPoint(0.0F, 3.8F, 5.0F);
      this.staf2.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, 0.0F);
      this.setRotateAngle(this.staf2, 0.091106184F, 0.0F, -0.091106184F);
      this.tip1 = new ModelRenderer(this, 17, 1);
      this.tip1.setRotationPoint(0.0F, -6.0F, 5.0F);
      this.tip1.addBox(-0.5F, -5.0F, -2.6F, 1, 5, 1, 0.0F);
      this.setRotateAngle(this.tip1, 0.0F, 1.0016445F, 0.0F);
      this.dtip2 = new ModelRenderer(this, 17, 1);
      this.dtip2.setRotationPoint(0.0F, -2.0F, 5.0F);
      this.dtip2.addBox(-0.5F, -5.0F, -1.6F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.dtip2, 0.0F, -0.7740535F, 0.0F);
      this.staf2.addChild(this.staf3);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (f == 1.0F) {
         this.ore2.render(f5);
      } else {
         this.ore1.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.staf1.offsetX, this.staf1.offsetY, this.staf1.offsetZ);
         GlStateManager.translate(this.staf1.rotationPointX * f5, this.staf1.rotationPointY * f5, this.staf1.rotationPointZ * f5);
         GlStateManager.scale(0.7, 0.9, 0.7);
         GlStateManager.translate(-this.staf1.offsetX, -this.staf1.offsetY, -this.staf1.offsetZ);
         GlStateManager.translate(-this.staf1.rotationPointX * f5, -this.staf1.rotationPointY * f5, -this.staf1.rotationPointZ * f5);
         this.staf1.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.tip3.offsetX, this.tip3.offsetY, this.tip3.offsetZ);
         GlStateManager.translate(this.tip3.rotationPointX * f5, this.tip3.rotationPointY * f5, this.tip3.rotationPointZ * f5);
         GlStateManager.scale(0.9, 0.9, 0.9);
         GlStateManager.translate(-this.tip3.offsetX, -this.tip3.offsetY, -this.tip3.offsetZ);
         GlStateManager.translate(-this.tip3.rotationPointX * f5, -this.tip3.rotationPointY * f5, -this.tip3.rotationPointZ * f5);
         this.tip3.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.dtip1.offsetX, this.dtip1.offsetY, this.dtip1.offsetZ);
         GlStateManager.translate(this.dtip1.rotationPointX * f5, this.dtip1.rotationPointY * f5, this.dtip1.rotationPointZ * f5);
         GlStateManager.scale(0.9, 0.9, 0.9);
         GlStateManager.translate(-this.dtip1.offsetX, -this.dtip1.offsetY, -this.dtip1.offsetZ);
         GlStateManager.translate(-this.dtip1.rotationPointX * f5, -this.dtip1.rotationPointY * f5, -this.dtip1.rotationPointZ * f5);
         this.dtip1.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.dtip3.offsetX, this.dtip3.offsetY, this.dtip3.offsetZ);
         GlStateManager.translate(this.dtip3.rotationPointX * f5, this.dtip3.rotationPointY * f5, this.dtip3.rotationPointZ * f5);
         GlStateManager.scale(0.9, 0.9, 0.9);
         GlStateManager.translate(-this.dtip3.offsetX, -this.dtip3.offsetY, -this.dtip3.offsetZ);
         GlStateManager.translate(-this.dtip3.rotationPointX * f5, -this.dtip3.rotationPointY * f5, -this.dtip3.rotationPointZ * f5);
         this.dtip3.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.tip2.offsetX, this.tip2.offsetY, this.tip2.offsetZ);
         GlStateManager.translate(this.tip2.rotationPointX * f5, this.tip2.rotationPointY * f5, this.tip2.rotationPointZ * f5);
         GlStateManager.scale(0.9, 0.9, 0.9);
         GlStateManager.translate(-this.tip2.offsetX, -this.tip2.offsetY, -this.tip2.offsetZ);
         GlStateManager.translate(-this.tip2.rotationPointX * f5, -this.tip2.rotationPointY * f5, -this.tip2.rotationPointZ * f5);
         this.tip2.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.staf2.offsetX, this.staf2.offsetY, this.staf2.offsetZ);
         GlStateManager.translate(this.staf2.rotationPointX * f5, this.staf2.rotationPointY * f5, this.staf2.rotationPointZ * f5);
         GlStateManager.scale(0.7, 0.9, 0.7);
         GlStateManager.translate(-this.staf2.offsetX, -this.staf2.offsetY, -this.staf2.offsetZ);
         GlStateManager.translate(-this.staf2.rotationPointX * f5, -this.staf2.rotationPointY * f5, -this.staf2.rotationPointZ * f5);
         this.staf2.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.tip1.offsetX, this.tip1.offsetY, this.tip1.offsetZ);
         GlStateManager.translate(this.tip1.rotationPointX * f5, this.tip1.rotationPointY * f5, this.tip1.rotationPointZ * f5);
         GlStateManager.scale(0.9, 0.9, 0.9);
         GlStateManager.translate(-this.tip1.offsetX, -this.tip1.offsetY, -this.tip1.offsetZ);
         GlStateManager.translate(-this.tip1.rotationPointX * f5, -this.tip1.rotationPointY * f5, -this.tip1.rotationPointZ * f5);
         this.tip1.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.dtip2.offsetX, this.dtip2.offsetY, this.dtip2.offsetZ);
         GlStateManager.translate(this.dtip2.rotationPointX * f5, this.dtip2.rotationPointY * f5, this.dtip2.rotationPointZ * f5);
         GlStateManager.scale(0.9, 0.9, 0.9);
         GlStateManager.translate(-this.dtip2.offsetX, -this.dtip2.offsetY, -this.dtip2.offsetZ);
         GlStateManager.translate(-this.dtip2.rotationPointX * f5, -this.dtip2.rotationPointY * f5, -this.dtip2.rotationPointZ * f5);
         this.dtip2.render(f5);
         GlStateManager.popMatrix();
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
