//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class SlimeShotgunModel extends ModelBase {
   public ModelRenderer shapess;
   public ModelRenderer shape;
   public ModelRenderer shape_1;
   public ModelRenderer shape_2;
   public ModelRenderer shape_3;
   public ModelRenderer shape_4;
   public ModelRenderer shape_5;
   public ModelRenderer shape_6;
   public ModelRenderer shape_7;
   public ModelRenderer shape_8;

   public SlimeShotgunModel() {
      this.textureWidth = 24;
      this.textureHeight = 18;
      this.shape_2 = new ModelRenderer(this, 0, 7);
      this.shape_2.setRotationPoint(0.0F, 1.25F, -2.4F);
      this.shape_2.addBox(-1.0F, -3.1F, -1.0F, 2, 7, 2, 0.0F);
      this.setRotateAngle(this.shape_2, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.shape_8 = new ModelRenderer(this, 12, 7);
      this.shape_8.setRotationPoint(0.0F, 3.65F, 1.2F);
      this.shape_8.addBox(-0.5F, -3.0F, -1.0F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shape_8, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.shape = new ModelRenderer(this, 0, 9);
      this.shape.setRotationPoint(0.0F, 2.2F, 6.0F);
      this.shape.addBox(-1.0F, -1.1F, 0.0F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.shape, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.shape_1 = new ModelRenderer(this, 0, 0);
      this.shape_1.setRotationPoint(0.0F, 2.0F, 4.4F);
      this.shape_1.addBox(-1.5F, -3.0F, -0.5F, 3, 4, 3, 0.0F);
      this.setRotateAngle(this.shape_1, 1.775698F, 0.0F, 0.0F);
      this.shape_6 = new ModelRenderer(this, 12, 0);
      this.shape_6.setRotationPoint(0.0F, 2.0F, -0.3F);
      this.shape_6.addBox(-1.5F, -3.0F, -0.5F, 3, 4, 3, 0.0F);
      this.setRotateAngle(this.shape_6, 1.775698F, 0.0F, 0.0F);
      this.shape_7 = new ModelRenderer(this, 16, 7);
      this.shape_7.setRotationPoint(0.0F, 2.85F, 1.0F);
      this.shape_7.addBox(-1.0F, -3.1F, -1.0F, 2, 6, 2, 0.0F);
      this.setRotateAngle(this.shape_7, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.shape_5 = new ModelRenderer(this, 0, 7);
      this.shape_5.setRotationPoint(0.0F, 2.0F, 7.6F);
      this.shape_5.addBox(-1.0F, -1.0F, 0.0F, 2, 5, 2, 0.0F);
      this.setRotateAngle(this.shape_5, 1.4570009F, 0.0F, 0.0F);
      this.shapess = new ModelRenderer(this, 8, 8);
      this.shapess.setRotationPoint(0.0F, 2.0F, 4.5F);
      this.shapess.addBox(-0.5F, -0.5F, 0.0F, 1, 5, 1, 0.0F);
      this.setRotateAngle(this.shapess, 0.27314404F, 0.0F, 0.0F);
      this.shape_3 = new ModelRenderer(this, 9, 12);
      this.shape_3.setRotationPoint(0.0F, 3.3F, 11.1F);
      this.shape_3.addBox(-1.0F, -1.0F, 0.0F, 2, 1, 3, 0.0F);
      this.setRotateAngle(this.shape_3, 0.95609134F, 0.0F, 0.0F);
      this.shape_4 = new ModelRenderer(this, 9, 12);
      this.shape_4.setRotationPoint(0.0F, 2.6F, 10.2F);
      this.shape_4.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 3, 0.0F);
      this.setRotateAngle(this.shape_4, 1.0927507F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape_2.offsetX, this.shape_2.offsetY, this.shape_2.offsetZ);
      GlStateManager.translate(this.shape_2.rotationPointX * f5, this.shape_2.rotationPointY * f5, this.shape_2.rotationPointZ * f5);
      GlStateManager.scale(0.8, 0.8, 1.0);
      GlStateManager.translate(-this.shape_2.offsetX, -this.shape_2.offsetY, -this.shape_2.offsetZ);
      GlStateManager.translate(-this.shape_2.rotationPointX * f5, -this.shape_2.rotationPointY * f5, -this.shape_2.rotationPointZ * f5);
      this.shape_2.render(f5);
      GlStateManager.popMatrix();
      this.shape_8.render(f5);
      this.shape.render(f5);
      this.shape_1.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape_6.offsetX, this.shape_6.offsetY, this.shape_6.offsetZ);
      GlStateManager.translate(this.shape_6.rotationPointX * f5, this.shape_6.rotationPointY * f5, this.shape_6.rotationPointZ * f5);
      GlStateManager.scale(0.9, 0.9, 0.9);
      GlStateManager.translate(-this.shape_6.offsetX, -this.shape_6.offsetY, -this.shape_6.offsetZ);
      GlStateManager.translate(-this.shape_6.rotationPointX * f5, -this.shape_6.rotationPointY * f5, -this.shape_6.rotationPointZ * f5);
      this.shape_6.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape_7.offsetX, this.shape_7.offsetY, this.shape_7.offsetZ);
      GlStateManager.translate(this.shape_7.rotationPointX * f5, this.shape_7.rotationPointY * f5, this.shape_7.rotationPointZ * f5);
      GlStateManager.scale(0.6, 0.8, 1.0);
      GlStateManager.translate(-this.shape_7.offsetX, -this.shape_7.offsetY, -this.shape_7.offsetZ);
      GlStateManager.translate(-this.shape_7.rotationPointX * f5, -this.shape_7.rotationPointY * f5, -this.shape_7.rotationPointZ * f5);
      this.shape_7.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape_5.offsetX, this.shape_5.offsetY, this.shape_5.offsetZ);
      GlStateManager.translate(this.shape_5.rotationPointX * f5, this.shape_5.rotationPointY * f5, this.shape_5.rotationPointZ * f5);
      GlStateManager.scale(0.6, 0.8, 0.9);
      GlStateManager.translate(-this.shape_5.offsetX, -this.shape_5.offsetY, -this.shape_5.offsetZ);
      GlStateManager.translate(-this.shape_5.rotationPointX * f5, -this.shape_5.rotationPointY * f5, -this.shape_5.rotationPointZ * f5);
      this.shape_5.render(f5);
      GlStateManager.popMatrix();
      this.shapess.render(f5);
      this.shape_3.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape_4.offsetX, this.shape_4.offsetY, this.shape_4.offsetZ);
      GlStateManager.translate(this.shape_4.rotationPointX * f5, this.shape_4.rotationPointY * f5, this.shape_4.rotationPointZ * f5);
      GlStateManager.scale(0.8, 0.8, 0.8);
      GlStateManager.translate(-this.shape_4.offsetX, -this.shape_4.offsetY, -this.shape_4.offsetZ);
      GlStateManager.translate(-this.shape_4.rotationPointX * f5, -this.shape_4.rotationPointY * f5, -this.shape_4.rotationPointZ * f5);
      this.shape_4.render(f5);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
