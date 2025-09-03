//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ConiferRodModel extends ModelBase {
   public ModelRenderer con1;
   public ModelRenderer staf2;
   public ModelRenderer con2;
   public ModelRenderer con3;
   public ModelRenderer con0;
   public ModelRenderer con4;
   public ModelRenderer con5;
   public ModelRenderer ball;

   public ConiferRodModel() {
      this.textureWidth = 16;
      this.textureHeight = 27;
      this.con4 = new ModelRenderer(this, 0, 6);
      this.con4.setRotationPoint(0.4F, 2.0F, 5.0F);
      this.con4.addBox(-1.8F, 0.0F, -2.5F, 2, 8, 2, 0.0F);
      this.setRotateAngle(this.con4, 2.321986F, -0.091106184F, -0.13665928F);
      this.con5 = new ModelRenderer(this, 0, 3);
      this.con5.setRotationPoint(-0.4F, -1.0F, 7.0F);
      this.con5.addBox(-1.4F, 0.0F, -2.5F, 2, 8, 2, 0.0F);
      this.setRotateAngle(this.con5, 1.8668041F, -0.27314404F, -0.13665928F);
      this.con0 = new ModelRenderer(this, 0, 0);
      this.con0.setRotationPoint(0.5F, 0.0F, 5.0F);
      this.con0.addBox(-1.55F, -2.7F, -2.4F, 2, 8, 2, 0.0F);
      this.setRotateAngle(this.con0, -3.0504866F, -0.045553092F, 0.18203785F);
      this.con1 = new ModelRenderer(this, 0, 9);
      this.con1.setRotationPoint(0.5F, -2.0F, 5.0F);
      this.con1.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
      this.setRotateAngle(this.con1, -2.5953045F, -0.091106184F, 0.0F);
      this.ball = new ModelRenderer(this, 0, 23);
      this.ball.setRotationPoint(-0.4F, -4.0F, 5.0F);
      this.ball.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.ball, -0.8196066F, -0.091106184F, -0.8196066F);
      this.con3 = new ModelRenderer(this, 0, 13);
      this.con3.setRotationPoint(0.5F, -3.1F, 3.9F);
      this.con3.addBox(-0.9F, 4.0F, -1.7F, 2, 8, 2, 0.0F);
      this.setRotateAngle(this.con3, -1.7301449F, -0.3642502F, 0.0F);
      this.staf2 = new ModelRenderer(this, 8, 0);
      this.staf2.setRotationPoint(0.0F, 4.0F, 5.0F);
      this.staf2.addBox(-1.0F, -9.0F, -1.0F, 2, 25, 2, 0.0F);
      this.con2 = new ModelRenderer(this, 0, 10);
      this.con2.setRotationPoint(0.5F, -3.1F, 3.9F);
      this.con2.addBox(-0.5F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
      this.setRotateAngle(this.con2, (float) (-Math.PI * 2.0 / 3.0), -0.22759093F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.con4.offsetX, this.con4.offsetY, this.con4.offsetZ);
      GlStateManager.translate(this.con4.rotationPointX * f5, this.con4.rotationPointY * f5, this.con4.rotationPointZ * f5);
      GlStateManager.scale(0.5, 0.65, 0.35);
      GlStateManager.translate(-this.con4.offsetX, -this.con4.offsetY, -this.con4.offsetZ);
      GlStateManager.translate(-this.con4.rotationPointX * f5, -this.con4.rotationPointY * f5, -this.con4.rotationPointZ * f5);
      this.con4.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.con5.offsetX, this.con5.offsetY, this.con5.offsetZ);
      GlStateManager.translate(this.con5.rotationPointX * f5, this.con5.rotationPointY * f5, this.con5.rotationPointZ * f5);
      GlStateManager.scale(0.4, 0.65, 0.35);
      GlStateManager.translate(-this.con5.offsetX, -this.con5.offsetY, -this.con5.offsetZ);
      GlStateManager.translate(-this.con5.rotationPointX * f5, -this.con5.rotationPointY * f5, -this.con5.rotationPointZ * f5);
      this.con5.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.con0.offsetX, this.con0.offsetY, this.con0.offsetZ);
      GlStateManager.translate(this.con0.rotationPointX * f5, this.con0.rotationPointY * f5, this.con0.rotationPointZ * f5);
      GlStateManager.scale(0.5, 0.65, 0.45);
      GlStateManager.translate(-this.con0.offsetX, -this.con0.offsetY, -this.con0.offsetZ);
      GlStateManager.translate(-this.con0.rotationPointX * f5, -this.con0.rotationPointY * f5, -this.con0.rotationPointZ * f5);
      this.con0.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.con1.offsetX, this.con1.offsetY, this.con1.offsetZ);
      GlStateManager.translate(this.con1.rotationPointX * f5, this.con1.rotationPointY * f5, this.con1.rotationPointZ * f5);
      GlStateManager.scale(0.5, 0.45, 0.55);
      GlStateManager.translate(-this.con1.offsetX, -this.con1.offsetY, -this.con1.offsetZ);
      GlStateManager.translate(-this.con1.rotationPointX * f5, -this.con1.rotationPointY * f5, -this.con1.rotationPointZ * f5);
      this.con1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.ball.offsetX, this.ball.offsetY, this.ball.offsetZ);
      GlStateManager.translate(this.ball.rotationPointX * f5, this.ball.rotationPointY * f5, this.ball.rotationPointZ * f5);
      GlStateManager.scale(0.85, 0.85, 0.85);
      GlStateManager.translate(-this.ball.offsetX, -this.ball.offsetY, -this.ball.offsetZ);
      GlStateManager.translate(-this.ball.rotationPointX * f5, -this.ball.rotationPointY * f5, -this.ball.rotationPointZ * f5);
      this.ball.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.con3.offsetX, this.con3.offsetY, this.con3.offsetZ);
      GlStateManager.translate(this.con3.rotationPointX * f5, this.con3.rotationPointY * f5, this.con3.rotationPointZ * f5);
      GlStateManager.scale(0.25, 0.35, 0.65);
      GlStateManager.translate(-this.con3.offsetX, -this.con3.offsetY, -this.con3.offsetZ);
      GlStateManager.translate(-this.con3.rotationPointX * f5, -this.con3.rotationPointY * f5, -this.con3.rotationPointZ * f5);
      this.con3.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf2.offsetX, this.staf2.offsetY, this.staf2.offsetZ);
      GlStateManager.translate(this.staf2.rotationPointX * f5, this.staf2.rotationPointY * f5, this.staf2.rotationPointZ * f5);
      GlStateManager.scale(0.65, 0.75, 0.65);
      GlStateManager.translate(-this.staf2.offsetX, -this.staf2.offsetY, -this.staf2.offsetZ);
      GlStateManager.translate(-this.staf2.rotationPointX * f5, -this.staf2.rotationPointY * f5, -this.staf2.rotationPointZ * f5);
      this.staf2.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.con2.offsetX, this.con2.offsetY, this.con2.offsetZ);
      GlStateManager.translate(this.con2.rotationPointX * f5, this.con2.rotationPointY * f5, this.con2.rotationPointZ * f5);
      GlStateManager.scale(0.3, 0.45, 0.75);
      GlStateManager.translate(-this.con2.offsetX, -this.con2.offsetY, -this.con2.offsetZ);
      GlStateManager.translate(-this.con2.rotationPointX * f5, -this.con2.rotationPointY * f5, -this.con2.rotationPointZ * f5);
      this.con2.render(f5);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
