//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class MagicCandleModel extends ModelBase {
   public ModelRenderer shapeA1;
   public ModelRenderer shapeA2;
   public ModelRenderer shapeB1;
   public ModelRenderer shapeB2;
   public ModelRenderer shapeC1;
   public ModelRenderer shapeC2;
   public ModelRenderer shapeD1;
   public ModelRenderer shapeD2;
   public ModelRenderer shapeE1;
   public ModelRenderer shapeE2;
   public ModelRenderer shapeB4;
   public ModelRenderer shapeB5;
   public ModelRenderer shapeC3;
   public ModelRenderer shapeC4;
   public ModelRenderer shapeC5;
   public ModelRenderer shapeD3;
   public ModelRenderer shapeD4;
   public ModelRenderer shapeD5;
   public ModelRenderer shapeE3;
   public ModelRenderer shapeE4;
   public ModelRenderer shapeE5;

   public MagicCandleModel() {
      this.textureWidth = 8;
      this.textureHeight = 16;
      this.shapeE2 = new ModelRenderer(this, 0, 0);
      this.shapeE2.setRotationPoint(0.0F, 15.0F, 0.0F);
      this.shapeE2.addBox(-0.5F, -2.0F, 0.0F, 1, 2, 0, 0.0F);
      this.setRotateAngle(this.shapeE2, 0.0F, (float) (-Math.PI / 4), 0.0F);
      this.shapeD2 = new ModelRenderer(this, 0, 0);
      this.shapeD2.setRotationPoint(0.0F, 17.0F, 0.0F);
      this.shapeD2.addBox(-0.5F, -2.0F, 0.0F, 1, 2, 0, 0.0F);
      this.setRotateAngle(this.shapeD2, 0.0F, (float) (-Math.PI / 4), 0.0F);
      this.shapeC1 = new ModelRenderer(this, 0, 0);
      this.shapeC1.setRotationPoint(0.0F, 14.0F, 0.0F);
      this.shapeC1.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
      this.shapeB5 = new ModelRenderer(this, 1, 10);
      this.shapeB5.setRotationPoint(-1.0F, 7.0F, 0.0F);
      this.shapeB5.addBox(-1.0F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
      this.shapeC2 = new ModelRenderer(this, 0, 0);
      this.shapeC2.setRotationPoint(0.0F, 14.0F, 0.0F);
      this.shapeC2.addBox(-0.5F, -2.0F, 0.0F, 1, 2, 0, 0.0F);
      this.setRotateAngle(this.shapeC2, 0.0F, (float) (-Math.PI / 4), 0.0F);
      this.shapeD4 = new ModelRenderer(this, 4, 10);
      this.shapeD4.setRotationPoint(1.0F, 6.0F, -1.0F);
      this.shapeD4.addBox(-1.0F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
      this.shapeE4 = new ModelRenderer(this, 1, 8);
      this.shapeE4.setRotationPoint(0.0F, 6.0F, -1.0F);
      this.shapeE4.addBox(-1.0F, 0.0F, -1.0F, 1, 3, 1, 0.0F);
      this.shapeE5 = new ModelRenderer(this, 5, 9);
      this.shapeE5.setRotationPoint(0.0F, 7.0F, 2.0F);
      this.shapeE5.addBox(-1.0F, 0.0F, -1.0F, 1, 2, 1, 0.0F);
      this.shapeD3 = new ModelRenderer(this, 3, 4);
      this.shapeD3.setRotationPoint(-1.0F, 0.0F, 1.0F);
      this.shapeD3.addBox(-1.0F, 0.0F, -1.0F, 1, 2, 1, 0.0F);
      this.shapeA1 = new ModelRenderer(this, 0, 1);
      this.shapeA1.setRotationPoint(0.0F, 15.0F, 0.0F);
      this.shapeA1.addBox(-1.0F, 0.0F, -1.0F, 2, 9, 2, 0.0F);
      this.shapeB4 = new ModelRenderer(this, 2, 9);
      this.shapeB4.setRotationPoint(0.0F, 6.0F, -1.0F);
      this.shapeB4.addBox(-1.0F, 0.0F, -1.0F, 1, 2, 1, 0.0F);
      this.shapeE3 = new ModelRenderer(this, 0, 9);
      this.shapeE3.setRotationPoint(-1.0F, 8.0F, 0.0F);
      this.shapeE3.addBox(-1.0F, 0.0F, -1.0F, 1, 1, 2, 0.0F);
      this.shapeE1 = new ModelRenderer(this, 0, 1);
      this.shapeE1.setRotationPoint(0.0F, 15.0F, 0.0F);
      this.shapeE1.addBox(-1.0F, 0.0F, -1.0F, 2, 9, 2, 0.0F);
      this.shapeC3 = new ModelRenderer(this, 2, 1);
      this.shapeC3.setRotationPoint(0.0F, 0.0F, -1.0F);
      this.shapeC3.addBox(-1.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F);
      this.shapeC4 = new ModelRenderer(this, 0, 9);
      this.shapeC4.setRotationPoint(0.0F, 8.0F, -1.0F);
      this.shapeC4.addBox(-1.0F, 0.0F, -1.0F, 1, 2, 1, 0.0F);
      this.shapeD1 = new ModelRenderer(this, 3, 2);
      this.shapeD1.setRotationPoint(-0.5F, 17.0F, -0.5F);
      this.shapeD1.addBox(-1.0F, 0.0F, -1.0F, 3, 7, 3, 0.0F);
      this.shapeA2 = new ModelRenderer(this, 0, 0);
      this.shapeA2.setRotationPoint(0.0F, 15.0F, 0.0F);
      this.shapeA2.addBox(-0.5F, -2.0F, 0.0F, 1, 2, 0, 0.0F);
      this.setRotateAngle(this.shapeA2, 0.0F, (float) (-Math.PI / 4), 0.0F);
      this.shapeC5 = new ModelRenderer(this, 4, 8);
      this.shapeC5.setRotationPoint(-1.0F, 7.0F, 0.0F);
      this.shapeC5.addBox(-1.0F, 0.0F, -1.0F, 1, 3, 1, 0.0F);
      this.shapeD5 = new ModelRenderer(this, 0, 9);
      this.shapeD5.setRotationPoint(-1.0F, 5.0F, 1.0F);
      this.shapeD5.addBox(-1.0F, 0.0F, -1.0F, 1, 2, 1, 0.0F);
      this.shapeB1 = new ModelRenderer(this, 0, 2);
      this.shapeB1.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shapeB1.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
      this.shapeB2 = new ModelRenderer(this, 0, 0);
      this.shapeB2.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shapeB2.addBox(-0.5F, -2.0F, 0.0F, 1, 2, 0, 0.0F);
      this.setRotateAngle(this.shapeB2, 0.0F, (float) (-Math.PI / 4), 0.0F);
      this.shapeB1.addChild(this.shapeB5);
      this.shapeD1.addChild(this.shapeD4);
      this.shapeE1.addChild(this.shapeE4);
      this.shapeE1.addChild(this.shapeE5);
      this.shapeD1.addChild(this.shapeD3);
      this.shapeB1.addChild(this.shapeB4);
      this.shapeE1.addChild(this.shapeE3);
      this.shapeC1.addChild(this.shapeC3);
      this.shapeC1.addChild(this.shapeC4);
      this.shapeC1.addChild(this.shapeC5);
      this.shapeD1.addChild(this.shapeD5);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      int rotation = (int)f1 / 5;
      int type = (int)f1 % 5;
      GlStateManager.pushMatrix();
      GlStateManager.rotate(rotation * 90, 0.0F, 1.0F, 0.0F);
      if (type == 0) {
         this.shapeA1.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shapeA2.offsetX, this.shapeA2.offsetY, this.shapeA2.offsetZ);
         GlStateManager.translate(this.shapeA2.rotationPointX * f5, this.shapeA2.rotationPointY * f5, this.shapeA2.rotationPointZ * f5);
         GlStateManager.scale(0.5, 0.5, 0.5);
         GlStateManager.translate(-this.shapeA2.offsetX, -this.shapeA2.offsetY, -this.shapeA2.offsetZ);
         GlStateManager.translate(-this.shapeA2.rotationPointX * f5, -this.shapeA2.rotationPointY * f5, -this.shapeA2.rotationPointZ * f5);
         this.shapeA2.render(f5);
         GlStateManager.popMatrix();
      }

      if (type == 1) {
         this.shapeB1.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shapeB2.offsetX, this.shapeB2.offsetY, this.shapeB2.offsetZ);
         GlStateManager.translate(this.shapeB2.rotationPointX * f5, this.shapeB2.rotationPointY * f5, this.shapeB2.rotationPointZ * f5);
         GlStateManager.scale(0.5, 0.5, 0.5);
         GlStateManager.translate(-this.shapeB2.offsetX, -this.shapeB2.offsetY, -this.shapeB2.offsetZ);
         GlStateManager.translate(-this.shapeB2.rotationPointX * f5, -this.shapeB2.rotationPointY * f5, -this.shapeB2.rotationPointZ * f5);
         this.shapeB2.render(f5);
         GlStateManager.popMatrix();
      }

      if (type == 2) {
         this.shapeC1.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shapeC2.offsetX, this.shapeC2.offsetY, this.shapeC2.offsetZ);
         GlStateManager.translate(this.shapeC2.rotationPointX * f5, this.shapeC2.rotationPointY * f5, this.shapeC2.rotationPointZ * f5);
         GlStateManager.scale(0.5, 0.5, 0.5);
         GlStateManager.translate(-this.shapeC2.offsetX, -this.shapeC2.offsetY, -this.shapeC2.offsetZ);
         GlStateManager.translate(-this.shapeC2.rotationPointX * f5, -this.shapeC2.rotationPointY * f5, -this.shapeC2.rotationPointZ * f5);
         this.shapeC2.render(f5);
         GlStateManager.popMatrix();
      }

      if (type == 3) {
         this.shapeD1.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shapeD2.offsetX, this.shapeD2.offsetY, this.shapeD2.offsetZ);
         GlStateManager.translate(this.shapeD2.rotationPointX * f5, this.shapeD2.rotationPointY * f5, this.shapeD2.rotationPointZ * f5);
         GlStateManager.scale(0.5, 0.5, 0.5);
         GlStateManager.translate(-this.shapeD2.offsetX, -this.shapeD2.offsetY, -this.shapeD2.offsetZ);
         GlStateManager.translate(-this.shapeD2.rotationPointX * f5, -this.shapeD2.rotationPointY * f5, -this.shapeD2.rotationPointZ * f5);
         this.shapeD2.render(f5);
         GlStateManager.popMatrix();
      }

      if (type == 4) {
         this.shapeE1.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shapeE2.offsetX, this.shapeE2.offsetY, this.shapeE2.offsetZ);
         GlStateManager.translate(this.shapeE2.rotationPointX * f5, this.shapeE2.rotationPointY * f5, this.shapeE2.rotationPointZ * f5);
         GlStateManager.scale(0.5, 0.5, 0.5);
         GlStateManager.translate(-this.shapeE2.offsetX, -this.shapeE2.offsetY, -this.shapeE2.offsetZ);
         GlStateManager.translate(-this.shapeE2.rotationPointX * f5, -this.shapeE2.rotationPointY * f5, -this.shapeE2.rotationPointZ * f5);
         this.shapeE2.render(f5);
         GlStateManager.popMatrix();
      }

      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
