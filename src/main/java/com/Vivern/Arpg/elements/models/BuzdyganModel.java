package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.renders.ModelRendererGlow;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class BuzdyganModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer staf2;
   public ModelRenderer shapeMain;
   public ModelRenderer rune3;
   public ModelRenderer rune2;
   public ModelRenderer rune1;
   public ModelRenderer staf1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer feather1;
   public ModelRenderer feather2;
   public ModelRenderer feather3;
   public ModelRenderer feather4;

   public BuzdyganModel() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      this.feather3 = new ModelRenderer(this, 0, 0);
      this.feather3.setRotationPoint(0.0F, -11.0F, 0.0F);
      this.feather3.addBox(-4.0F, 0.0F, 0.0F, 8, 16, 0, 0.0F);
      this.setRotateAngle(this.feather3, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.staf1 = new ModelRenderer(this, 0, 16);
      this.staf1.setRotationPoint(0.0F, 10.0F, 5.0F);
      this.staf1.addBox(-1.0F, -8.0F, -1.0F, 2, 14, 2, 0.0F);
      this.feather1 = new ModelRenderer(this, 0, 0);
      this.feather1.setRotationPoint(0.0F, -11.0F, 0.0F);
      this.feather1.addBox(-4.0F, 0.0F, 0.0F, 8, 16, 0, 0.0F);
      this.shape1 = new ModelRenderer(this, 16, 0);
      this.shape1.setRotationPoint(0.0F, -5.0F, 5.0F);
      this.shape1.addBox(-1.5F, 0.0F, -1.5F, 3, 7, 3, 0.0F);
      this.rune3 = new ModelRendererGlow(this, 20, 14, 240, false);
      this.rune3.setRotationPoint(0.0F, -4.5F, 5.0F);
      this.rune3.addBox(-1.5F, 0.0F, -1.5F, 3, 2, 3, 0.0F);
      this.shape2 = new ModelRenderer(this, 16, 0);
      this.shape2.setRotationPoint(0.0F, -7.0F, 0.0F);
      this.shape2.addBox(-1.5F, 0.0F, -1.5F, 3, 7, 3, 0.0F);
      this.setRotateAngle(this.shape2, 0.0F, (float) (Math.PI / 4), 0.0F);
      this.shapeMain = new ModelRenderer(this, 8, 26);
      this.shapeMain.setRotationPoint(0.0F, -7.0F, 5.0F);
      this.shapeMain.addBox(-2.0F, 0.0F, -2.0F, 4, 2, 4, 0.0F);
      this.rune2 = new ModelRendererGlow(this, 20, 19, 240, false);
      this.rune2.setRotationPoint(0.0F, -2.5F, 5.0F);
      this.rune2.addBox(-1.5F, 0.0F, -1.5F, 3, 2, 3, 0.0F);
      this.rune1 = new ModelRendererGlow(this, 20, 24, 240, false);
      this.rune1.setRotationPoint(0.0F, -0.5F, 5.0F);
      this.rune1.addBox(-1.5F, 0.0F, -1.5F, 3, 2, 3, 0.0F);
      this.feather4 = new ModelRenderer(this, 0, 0);
      this.feather4.setRotationPoint(0.0F, -11.0F, 0.0F);
      this.feather4.addBox(-4.0F, 0.0F, 0.0F, 8, 16, 0, 0.0F);
      this.setRotateAngle(this.feather4, 0.0F, (float) (-Math.PI / 4), 0.0F);
      this.staf2 = new ModelRenderer(this, 8, 16);
      this.staf2.setRotationPoint(0.0F, 16.0F, 5.0F);
      this.staf2.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
      this.feather2 = new ModelRenderer(this, 0, 0);
      this.feather2.setRotationPoint(0.0F, -11.0F, 0.0F);
      this.feather2.addBox(-4.0F, 0.0F, 0.0F, 8, 16, 0, 0.0F);
      this.setRotateAngle(this.feather2, 0.0F, (float) (Math.PI / 4), 0.0F);
      this.shape3 = new ModelRenderer(this, 8, 26);
      this.shape3.setRotationPoint(0.0F, -9.0F, 0.0F);
      this.shape3.addBox(-2.0F, 0.0F, -2.0F, 4, 2, 4, 0.0F);
      this.shapeMain.addChild(this.feather3);
      this.shapeMain.addChild(this.feather1);
      this.shapeMain.addChild(this.shape2);
      this.shapeMain.addChild(this.feather4);
      this.shapeMain.addChild(this.feather2);
      this.shapeMain.addChild(this.shape3);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.disableCull();
      if (f3 == 0.0F) {
         this.shapeMain.rotateAngleY = -f2 * (float) (Math.PI / 180.0);
         this.shape2.rotateAngleY = (float) (Math.PI / 4) + f2 * 0.034906585F;
      } else {
         this.shapeMain.rotateAngleY = f2 * (float) (Math.PI / 180.0);
         this.shape2.rotateAngleY = (float) (Math.PI / 4);
      }

      this.staf1.render(f5);
      this.shape1.render(f5);
      this.shapeMain.render(f5);
      this.staf2.render(f5);
      if (f >= 1.0F) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.rune1.offsetX, this.rune1.offsetY, this.rune1.offsetZ);
         GlStateManager.translate(this.rune1.rotationPointX * f5, this.rune1.rotationPointY * f5, this.rune1.rotationPointZ * f5);
         GlStateManager.scale(1.02, 1.0, 1.02);
         GlStateManager.translate(-this.rune1.offsetX, -this.rune1.offsetY, -this.rune1.offsetZ);
         GlStateManager.translate(-this.rune1.rotationPointX * f5, -this.rune1.rotationPointY * f5, -this.rune1.rotationPointZ * f5);
         this.rune1.render(f5);
         GlStateManager.popMatrix();
      }

      if (f >= 2.0F) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.rune2.offsetX, this.rune2.offsetY, this.rune2.offsetZ);
         GlStateManager.translate(this.rune2.rotationPointX * f5, this.rune2.rotationPointY * f5, this.rune2.rotationPointZ * f5);
         GlStateManager.scale(1.02, 1.0, 1.02);
         GlStateManager.translate(-this.rune2.offsetX, -this.rune2.offsetY, -this.rune2.offsetZ);
         GlStateManager.translate(-this.rune2.rotationPointX * f5, -this.rune2.rotationPointY * f5, -this.rune2.rotationPointZ * f5);
         this.rune2.render(f5);
         GlStateManager.popMatrix();
      }

      if (f >= 3.0F) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.rune3.offsetX, this.rune3.offsetY, this.rune3.offsetZ);
         GlStateManager.translate(this.rune3.rotationPointX * f5, this.rune3.rotationPointY * f5, this.rune3.rotationPointZ * f5);
         GlStateManager.scale(1.02, 1.0, 1.02);
         GlStateManager.translate(-this.rune3.offsetX, -this.rune3.offsetY, -this.rune3.offsetZ);
         GlStateManager.translate(-this.rune3.rotationPointX * f5, -this.rune3.rotationPointY * f5, -this.rune3.rotationPointZ * f5);
         this.rune3.render(f5);
         GlStateManager.popMatrix();
      }

      GlStateManager.enableCull();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
