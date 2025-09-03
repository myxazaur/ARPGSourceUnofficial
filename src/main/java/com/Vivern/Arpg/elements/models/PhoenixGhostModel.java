//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class PhoenixGhostModel extends ModelBase {
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape1;
   public ModelRenderer shape1AAAA;
   public ModelRenderer shape2AAAA;
   public ModelRenderer shape4;
   public ModelRenderer shape5;
   public ModelRenderer head;
   public ModelRenderer body;

   public PhoenixGhostModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.shape1AAAA = new ModelRenderer(this, 20, 18);
      this.shape1AAAA.setRotationPoint(5.5F, 0.0F, 1.0F);
      this.shape1AAAA.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 12, 0.0F);
      this.setRotateAngle(this.shape1AAAA, 0.091106184F, 0.22759093F, 0.0F);
      this.shape4 = new ModelRenderer(this, 21, 20);
      this.shape4.setRotationPoint(-2.5F, -5.0F, 3.0F);
      this.shape4.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 8, 0.0F);
      this.setRotateAngle(this.shape4, 0.18203785F, -0.13665928F, 0.0F);
      this.shape3 = new ModelRenderer(this, 18, 19);
      this.shape3.setRotationPoint(-1.5F, -3.0F, 9.0F);
      this.shape3.addBox(3.0F, -1.0F, 0.0F, 2, 2, 11, 0.0F);
      this.setRotateAngle(this.shape3, 0.091106184F, 0.0F, 0.0F);
      this.shape1 = new ModelRenderer(this, 18, 17);
      this.shape1.setRotationPoint(1.0F, 3.0F, 13.0F);
      this.shape1.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 13, 0.0F);
      this.shape5 = new ModelRenderer(this, 10, 21);
      this.shape5.setRotationPoint(2.5F, -5.0F, 3.0F);
      this.shape5.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 7, 0.0F);
      this.setRotateAngle(this.shape5, 0.18203785F, 0.13665928F, 0.0F);
      this.shape2 = new ModelRenderer(this, 10, 19);
      this.shape2.setRotationPoint(-2.5F, 0.0F, 12.0F);
      this.shape2.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 11, 0.0F);
      this.setRotateAngle(this.shape2, 0.045553092F, 0.0F, 0.0F);
      this.body = new ModelRenderer(this, 16, 16);
      this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
      this.setRotateAngle(this.body, 1.3658947F, 0.0F, 0.0F);
      this.shape2AAAA = new ModelRenderer(this, 8, 18);
      this.shape2AAAA.setRotationPoint(-5.5F, 0.0F, 1.0F);
      this.shape2AAAA.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 12, 0.0F);
      this.setRotateAngle(this.shape2AAAA, 0.091106184F, -0.27314404F, 0.0F);
      this.head = new ModelRenderer(this, 0, 0);
      this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.head.addBox(-4.0F, -7.0F, -7.0F, 8, 8, 8, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1AAAA.offsetX, this.shape1AAAA.offsetY, this.shape1AAAA.offsetZ);
      GlStateManager.translate(this.shape1AAAA.rotationPointX * f5, this.shape1AAAA.rotationPointY * f5, this.shape1AAAA.rotationPointZ * f5);
      GlStateManager.scale(1.0, 1.0, f1);
      GlStateManager.translate(-this.shape1AAAA.offsetX, -this.shape1AAAA.offsetY, -this.shape1AAAA.offsetZ);
      GlStateManager.translate(-this.shape1AAAA.rotationPointX * f5, -this.shape1AAAA.rotationPointY * f5, -this.shape1AAAA.rotationPointZ * f5);
      this.shape1AAAA.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape4.offsetX, this.shape4.offsetY, this.shape4.offsetZ);
      GlStateManager.translate(this.shape4.rotationPointX * f5, this.shape4.rotationPointY * f5, this.shape4.rotationPointZ * f5);
      GlStateManager.scale(1.0, 1.0, f1);
      GlStateManager.translate(-this.shape4.offsetX, -this.shape4.offsetY, -this.shape4.offsetZ);
      GlStateManager.translate(-this.shape4.rotationPointX * f5, -this.shape4.rotationPointY * f5, -this.shape4.rotationPointZ * f5);
      this.shape4.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape3.offsetX, this.shape3.offsetY, this.shape3.offsetZ);
      GlStateManager.translate(this.shape3.rotationPointX * f5, this.shape3.rotationPointY * f5, this.shape3.rotationPointZ * f5);
      GlStateManager.scale(1.0, 1.0, f1);
      GlStateManager.translate(-this.shape3.offsetX, -this.shape3.offsetY, -this.shape3.offsetZ);
      GlStateManager.translate(-this.shape3.rotationPointX * f5, -this.shape3.rotationPointY * f5, -this.shape3.rotationPointZ * f5);
      this.shape3.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1.offsetX, this.shape1.offsetY, this.shape1.offsetZ);
      GlStateManager.translate(this.shape1.rotationPointX * f5, this.shape1.rotationPointY * f5, this.shape1.rotationPointZ * f5);
      GlStateManager.scale(1.0, 1.0, f1);
      GlStateManager.translate(-this.shape1.offsetX, -this.shape1.offsetY, -this.shape1.offsetZ);
      GlStateManager.translate(-this.shape1.rotationPointX * f5, -this.shape1.rotationPointY * f5, -this.shape1.rotationPointZ * f5);
      this.shape1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape5.offsetX, this.shape5.offsetY, this.shape5.offsetZ);
      GlStateManager.translate(this.shape5.rotationPointX * f5, this.shape5.rotationPointY * f5, this.shape5.rotationPointZ * f5);
      GlStateManager.scale(1.0, 1.0, f1);
      GlStateManager.translate(-this.shape5.offsetX, -this.shape5.offsetY, -this.shape5.offsetZ);
      GlStateManager.translate(-this.shape5.rotationPointX * f5, -this.shape5.rotationPointY * f5, -this.shape5.rotationPointZ * f5);
      this.shape5.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape2.offsetX, this.shape2.offsetY, this.shape2.offsetZ);
      GlStateManager.translate(this.shape2.rotationPointX * f5, this.shape2.rotationPointY * f5, this.shape2.rotationPointZ * f5);
      GlStateManager.scale(1.0, 1.0, f1);
      GlStateManager.translate(-this.shape2.offsetX, -this.shape2.offsetY, -this.shape2.offsetZ);
      GlStateManager.translate(-this.shape2.rotationPointX * f5, -this.shape2.rotationPointY * f5, -this.shape2.rotationPointZ * f5);
      this.shape2.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.body.offsetX, this.body.offsetY, this.body.offsetZ);
      GlStateManager.translate(this.body.rotationPointX * f5, this.body.rotationPointY * f5, this.body.rotationPointZ * f5);
      GlStateManager.scale(0.99, 1.0, 1.0);
      GlStateManager.translate(-this.body.offsetX, -this.body.offsetY, -this.body.offsetZ);
      GlStateManager.translate(-this.body.rotationPointX * f5, -this.body.rotationPointY * f5, -this.body.rotationPointZ * f5);
      this.body.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape2AAAA.offsetX, this.shape2AAAA.offsetY, this.shape2AAAA.offsetZ);
      GlStateManager.translate(this.shape2AAAA.rotationPointX * f5, this.shape2AAAA.rotationPointY * f5, this.shape2AAAA.rotationPointZ * f5);
      GlStateManager.scale(1.0, 1.0, f1);
      GlStateManager.translate(-this.shape2AAAA.offsetX, -this.shape2AAAA.offsetY, -this.shape2AAAA.offsetZ);
      GlStateManager.translate(-this.shape2AAAA.rotationPointX * f5, -this.shape2AAAA.rotationPointY * f5, -this.shape2AAAA.rotationPointZ * f5);
      this.shape2AAAA.render(f5);
      GlStateManager.popMatrix();
      this.head.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
