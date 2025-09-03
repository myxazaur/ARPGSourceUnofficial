//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.renders.ModelRendererLimited;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class TeamBannerModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shapeFace;
   public ModelRenderer shapeLeft;
   public ModelRenderer shapeUp;
   public ModelRenderer shapeRight;

   public TeamBannerModel() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      this.shape3 = new ModelRendererLimited(this, 8, 10, false, false, false, false, true, false);
      this.shape3.setRotationPoint(0.0F, -2.0F, -1.0F);
      this.shape3.addBox(-7.0F, 0.0F, 0.0F, 14, 22, 0, 0.01F);
      this.setRotateAngle(this.shape3, (float) (-Math.PI / 180.0), 0.0F, 0.0F);
      this.shapeLeft = new ModelRenderer(this, 10, 10);
      this.shapeLeft.setRotationPoint(0.0F, -2.0F, -1.0F);
      this.shapeLeft.addBox(-3.0F, 2.0F, 0.02F, 1, 6, 0, 0.01F);
      this.setRotateAngle(this.shapeLeft, (float) (-Math.PI / 180.0), 0.0F, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 6);
      this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1.addBox(-1.0F, 0.0F, -1.0F, 2, 24, 2, 0.0F);
      this.shapeUp = new ModelRenderer(this, 2, 10);
      this.shapeUp.setRotationPoint(0.0F, -2.0F, -1.0F);
      this.shapeUp.addBox(-1.0F, 0.5F, -0.02F, 2, 2, 0, 0.01F);
      this.setRotateAngle(this.shapeUp, (float) (-Math.PI / 180.0), 0.0F, 0.0F);
      this.shapeFace = new ModelRenderer(this, 4, 4);
      this.shapeFace.setRotationPoint(0.0F, -2.0F, -1.0F);
      this.shapeFace.addBox(-2.0F, 3.0F, -0.02F, 4, 4, 0, 0.01F);
      this.setRotateAngle(this.shapeFace, (float) (-Math.PI / 180.0), 0.0F, 0.0F);
      this.shape2 = new ModelRenderer(this, 0, 0);
      this.shape2.setRotationPoint(0.0F, -2.0F, 0.0F);
      this.shape2.addBox(-7.0F, 0.0F, -1.0F, 14, 2, 2, 0.0F);
      this.shapeRight = new ModelRenderer(this, 13, 10);
      this.shapeRight.setRotationPoint(0.0F, -2.0F, -1.0F);
      this.shapeRight.addBox(2.0F, 2.0F, 0.02F, 1, 6, 0, 0.01F);
      this.setRotateAngle(this.shapeRight, (float) (-Math.PI / 180.0), 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (f == 0.0F) {
         this.shape1.render(f5);
         this.shape2.render(f5);
         GlStateManager.disableCull();
         this.shape3.render(f5);
         GlStateManager.enableCull();
      }

      if (f == 1.0F) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shapeRight.offsetX, this.shapeRight.offsetY, this.shapeRight.offsetZ);
         GlStateManager.translate(this.shapeRight.rotationPointX * f5, this.shapeRight.rotationPointY * f5, this.shapeRight.rotationPointZ * f5);
         GlStateManager.scale(2.0, 2.0, 2.0);
         GlStateManager.translate(-this.shapeRight.offsetX, -this.shapeRight.offsetY, -this.shapeRight.offsetZ);
         GlStateManager.translate(-this.shapeRight.rotationPointX * f5, -this.shapeRight.rotationPointY * f5, -this.shapeRight.rotationPointZ * f5);
         this.shapeRight.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shapeLeft.offsetX, this.shapeLeft.offsetY, this.shapeLeft.offsetZ);
         GlStateManager.translate(this.shapeLeft.rotationPointX * f5, this.shapeLeft.rotationPointY * f5, this.shapeLeft.rotationPointZ * f5);
         GlStateManager.scale(2.0, 2.0, 2.0);
         GlStateManager.translate(-this.shapeLeft.offsetX, -this.shapeLeft.offsetY, -this.shapeLeft.offsetZ);
         GlStateManager.translate(-this.shapeLeft.rotationPointX * f5, -this.shapeLeft.rotationPointY * f5, -this.shapeLeft.rotationPointZ * f5);
         this.shapeLeft.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shapeUp.offsetX, this.shapeUp.offsetY, this.shapeUp.offsetZ);
         GlStateManager.translate(this.shapeUp.rotationPointX * f5, this.shapeUp.rotationPointY * f5, this.shapeUp.rotationPointZ * f5);
         GlStateManager.scale(2.0, 2.0, 2.0);
         GlStateManager.translate(-this.shapeUp.offsetX, -this.shapeUp.offsetY, -this.shapeUp.offsetZ);
         GlStateManager.translate(-this.shapeUp.rotationPointX * f5, -this.shapeUp.rotationPointY * f5, -this.shapeUp.rotationPointZ * f5);
         this.shapeUp.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shapeFace.offsetX, this.shapeFace.offsetY, this.shapeFace.offsetZ);
         GlStateManager.translate(this.shapeFace.rotationPointX * f5, this.shapeFace.rotationPointY * f5, this.shapeFace.rotationPointZ * f5);
         GlStateManager.scale(2.0, 2.0, 2.0);
         GlStateManager.translate(-this.shapeFace.offsetX, -this.shapeFace.offsetY, -this.shapeFace.offsetZ);
         GlStateManager.translate(-this.shapeFace.rotationPointX * f5, -this.shapeFace.rotationPointY * f5, -this.shapeFace.rotationPointZ * f5);
         this.shapeFace.render(f5);
         GlStateManager.popMatrix();
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
