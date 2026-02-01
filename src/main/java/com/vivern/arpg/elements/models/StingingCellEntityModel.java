package com.vivern.arpg.elements.models;

import com.vivern.arpg.main.AnimationTimer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;

public class StingingCellEntityModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer glow1;
   public ModelRenderer glow2;
   public ModelRenderer glow3;
   public ModelRenderer glow4;
   public ModelRenderer glowdn1;
   public ModelRenderer glowdn2;
   public ModelRenderer glowdn3;
   public ModelRenderer glowdn4;
   public ModelRenderer shape2;
   public ModelRenderer shape3;

   public StingingCellEntityModel() {
      this.textureWidth = 16;
      this.textureHeight = 16;
      this.glow3 = new ModelRenderer(this, 2, 0);
      this.glow3.setRotationPoint(0.0F, -2.5F, 0.0F);
      this.glow3.addBox(-3.1F, -2.5F, 0.0F, 1, 5, 0, 0.0F);
      this.glow2 = new ModelRenderer(this, 4, 0);
      this.glow2.setRotationPoint(0.0F, -2.5F, 0.0F);
      this.glow2.addBox(2.1F, -2.5F, 0.0F, 1, 5, 0, 0.0F);
      this.setRotateAngle(this.glow2, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.glow1 = new ModelRenderer(this, 2, 0);
      this.glow1.setRotationPoint(0.0F, -2.5F, 0.0F);
      this.glow1.addBox(2.1F, -2.5F, 0.0F, 1, 5, 0, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 8);
      this.shape1.setRotationPoint(0.0F, -2.5F, 0.0F);
      this.shape1.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 4, 0.0F);
      this.glowdn4 = new ModelRenderer(this, 0, 0);
      this.glowdn4.setRotationPoint(0.0F, -2.5F, 0.0F);
      this.glowdn4.addBox(2.9F, -2.5F, 0.0F, 1, 5, 0, 0.0F);
      this.setRotateAngle(this.glowdn4, 0.0F, (float) (-Math.PI * 3.0 / 4.0), 0.0F);
      this.glowdn3 = new ModelRenderer(this, 6, 0);
      this.glowdn3.setRotationPoint(0.0F, -2.5F, 0.0F);
      this.glowdn3.addBox(2.9F, -2.5F, 0.0F, 1, 5, 0, 0.0F);
      this.setRotateAngle(this.glowdn3, 0.0F, (float) (Math.PI * 3.0 / 4.0), 0.0F);
      this.glowdn2 = new ModelRenderer(this, 6, 0);
      this.glowdn2.setRotationPoint(0.0F, -2.5F, 0.0F);
      this.glowdn2.addBox(2.9F, -2.5F, 0.0F, 1, 5, 0, 0.0F);
      this.setRotateAngle(this.glowdn2, 0.0F, (float) (-Math.PI / 4), 0.0F);
      this.shape2 = new ModelRenderer(this, 8, 3);
      this.shape2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape2.addBox(-1.0F, 2.0F, -1.0F, 2, 1, 2, 0.0F);
      this.shape3 = new ModelRenderer(this, 8, 0);
      this.shape3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape3.addBox(-1.0F, -3.0F, -1.0F, 2, 1, 2, 0.0F);
      this.glowdn1 = new ModelRenderer(this, 0, 0);
      this.glowdn1.setRotationPoint(0.0F, -2.5F, 0.0F);
      this.glowdn1.addBox(2.9F, -2.5F, 0.0F, 1, 5, 0, 0.0F);
      this.setRotateAngle(this.glowdn1, 0.0F, (float) (Math.PI / 4), 0.0F);
      this.glow4 = new ModelRenderer(this, 4, 0);
      this.glow4.setRotationPoint(0.0F, -2.5F, 0.0F);
      this.glow4.addBox(-3.1F, -2.5F, 0.0F, 1, 5, 0, 0.0F);
      this.setRotateAngle(this.glow4, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.shape1.addChild(this.shape2);
      this.shape1.addChild(this.shape3);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (f == 0.0F) {
         this.shape1.render(f5);
      } else {
         AbstractMobModel.light(240, false);
         GlStateManager.pushMatrix();
         GlStateManager.depthMask(false);
         GlStateManager.matrixMode(5890);
         GlStateManager.loadIdentity();
         GlStateManager.translate(0.0F, AnimationTimer.normaltick / 4 / 16.0F, 0.0F);
         GlStateManager.matrixMode(5888);
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.glow3.offsetX, this.glow3.offsetY, this.glow3.offsetZ);
         GlStateManager.translate(this.glow3.rotationPointX * f5, this.glow3.rotationPointY * f5, this.glow3.rotationPointZ * f5);
         GlStateManager.scale(0.8, 0.94, 1.0);
         GlStateManager.translate(-this.glow3.offsetX, -this.glow3.offsetY, -this.glow3.offsetZ);
         GlStateManager.translate(-this.glow3.rotationPointX * f5, -this.glow3.rotationPointY * f5, -this.glow3.rotationPointZ * f5);
         this.glow3.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.glow2.offsetX, this.glow2.offsetY, this.glow2.offsetZ);
         GlStateManager.translate(this.glow2.rotationPointX * f5, this.glow2.rotationPointY * f5, this.glow2.rotationPointZ * f5);
         GlStateManager.scale(1.0, 0.94, 0.8);
         GlStateManager.translate(-this.glow2.offsetX, -this.glow2.offsetY, -this.glow2.offsetZ);
         GlStateManager.translate(-this.glow2.rotationPointX * f5, -this.glow2.rotationPointY * f5, -this.glow2.rotationPointZ * f5);
         this.glow2.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.glow1.offsetX, this.glow1.offsetY, this.glow1.offsetZ);
         GlStateManager.translate(this.glow1.rotationPointX * f5, this.glow1.rotationPointY * f5, this.glow1.rotationPointZ * f5);
         GlStateManager.scale(0.8, 0.94, 1.0);
         GlStateManager.translate(-this.glow1.offsetX, -this.glow1.offsetY, -this.glow1.offsetZ);
         GlStateManager.translate(-this.glow1.rotationPointX * f5, -this.glow1.rotationPointY * f5, -this.glow1.rotationPointZ * f5);
         this.glow1.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.glowdn4.offsetX, this.glowdn4.offsetY, this.glowdn4.offsetZ);
         GlStateManager.translate(this.glowdn4.rotationPointX * f5, this.glowdn4.rotationPointY * f5, this.glowdn4.rotationPointZ * f5);
         GlStateManager.scale(0.8, 0.94, 0.8);
         GlStateManager.translate(-this.glowdn4.offsetX, -this.glowdn4.offsetY, -this.glowdn4.offsetZ);
         GlStateManager.translate(-this.glowdn4.rotationPointX * f5, -this.glowdn4.rotationPointY * f5, -this.glowdn4.rotationPointZ * f5);
         this.glowdn4.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.glowdn3.offsetX, this.glowdn3.offsetY, this.glowdn3.offsetZ);
         GlStateManager.translate(this.glowdn3.rotationPointX * f5, this.glowdn3.rotationPointY * f5, this.glowdn3.rotationPointZ * f5);
         GlStateManager.scale(0.8, 0.94, 0.8);
         GlStateManager.translate(-this.glowdn3.offsetX, -this.glowdn3.offsetY, -this.glowdn3.offsetZ);
         GlStateManager.translate(-this.glowdn3.rotationPointX * f5, -this.glowdn3.rotationPointY * f5, -this.glowdn3.rotationPointZ * f5);
         this.glowdn3.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.glowdn2.offsetX, this.glowdn2.offsetY, this.glowdn2.offsetZ);
         GlStateManager.translate(this.glowdn2.rotationPointX * f5, this.glowdn2.rotationPointY * f5, this.glowdn2.rotationPointZ * f5);
         GlStateManager.scale(0.8, 0.94, 0.8);
         GlStateManager.translate(-this.glowdn2.offsetX, -this.glowdn2.offsetY, -this.glowdn2.offsetZ);
         GlStateManager.translate(-this.glowdn2.rotationPointX * f5, -this.glowdn2.rotationPointY * f5, -this.glowdn2.rotationPointZ * f5);
         this.glowdn2.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.glowdn1.offsetX, this.glowdn1.offsetY, this.glowdn1.offsetZ);
         GlStateManager.translate(this.glowdn1.rotationPointX * f5, this.glowdn1.rotationPointY * f5, this.glowdn1.rotationPointZ * f5);
         GlStateManager.scale(0.8, 0.94, 0.8);
         GlStateManager.translate(-this.glowdn1.offsetX, -this.glowdn1.offsetY, -this.glowdn1.offsetZ);
         GlStateManager.translate(-this.glowdn1.rotationPointX * f5, -this.glowdn1.rotationPointY * f5, -this.glowdn1.rotationPointZ * f5);
         this.glowdn1.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.glow4.offsetX, this.glow4.offsetY, this.glow4.offsetZ);
         GlStateManager.translate(this.glow4.rotationPointX * f5, this.glow4.rotationPointY * f5, this.glow4.rotationPointZ * f5);
         GlStateManager.scale(1.0, 0.94, 0.8);
         GlStateManager.translate(-this.glow4.offsetX, -this.glow4.offsetY, -this.glow4.offsetZ);
         GlStateManager.translate(-this.glow4.rotationPointX * f5, -this.glow4.rotationPointY * f5, -this.glow4.rotationPointZ * f5);
         this.glow4.render(f5);
         GlStateManager.popMatrix();
         AbstractMobModel.returnlight();
         GlStateManager.matrixMode(5890);
         GlStateManager.loadIdentity();
         GlStateManager.matrixMode(5888);
         GlStateManager.disableBlend();
         GlStateManager.depthMask(true);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.popMatrix();
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
