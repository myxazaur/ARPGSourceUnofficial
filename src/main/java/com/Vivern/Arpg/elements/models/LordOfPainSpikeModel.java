//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class LordOfPainSpikeModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shape5;
   public ModelRenderer shape6;
   public ModelRenderer shape7;
   public ModelRenderer shape8;
   public ModelRenderer shape9;
   public ModelRenderer shape10;

   public LordOfPainSpikeModel() {
      this.textureWidth = 4;
      this.textureHeight = 8;
      this.shape2 = new ModelRenderer(this, 0, 2);
      this.shape2.setRotationPoint(0.0F, 2.0F, 0.0F);
      this.shape2.addBox(-0.5F, -0.5F, -0.5F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.shape2, 0.4098033F, -0.31869712F, 0.4553564F);
      this.shape7 = new ModelRenderer(this, 0, 0);
      this.shape7.setRotationPoint(-0.1F, 10.5F, 3.6F);
      this.shape7.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.setRotateAngle(this.shape7, -0.5009095F, -0.63739425F, -1.5934856F);
      this.shape10 = new ModelRenderer(this, 0, 0);
      this.shape10.setRotationPoint(-0.1F, 22.5F, -0.1F);
      this.shape10.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.setRotateAngle(this.shape10, -0.4098033F, -0.68294734F, -2.1855013F);
      this.shape8 = new ModelRenderer(this, 0, 0);
      this.shape8.setRotationPoint(-0.6F, 5.5F, -0.9F);
      this.shape8.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.setRotateAngle(this.shape8, -0.5009095F, -0.63739425F, -1.5934856F);
      this.shape5 = new ModelRenderer(this, 0, 2);
      this.shape5.setRotationPoint(1.5F, 16.8F, 1.2F);
      this.shape5.addBox(-0.5F, -0.5F, -0.5F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shape5, -0.63739425F, 0.045553092F, -0.091106184F);
      this.shape4 = new ModelRenderer(this, 0, 2);
      this.shape4.setRotationPoint(0.0F, 11.2F, 2.1F);
      this.shape4.addBox(-0.5F, -0.5F, -0.5F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shape4, -0.27314404F, 0.0F, -0.63739425F);
      this.shape6 = new ModelRenderer(this, 0, 2);
      this.shape6.setRotationPoint(1.6F, 21.7F, -0.4F);
      this.shape6.addBox(-0.5F, -0.5F, -0.5F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shape6, 0.27314404F, -0.091106184F, 0.27314404F);
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.setRotateAngle(this.shape1, -0.7740535F, -0.7285004F, -1.5934856F);
      this.shape3 = new ModelRenderer(this, 0, 2);
      this.shape3.setRotationPoint(-0.6F, 6.0F, 0.7F);
      this.shape3.addBox(-0.5F, -0.5F, -0.5F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shape3, 0.68294734F, 0.0F, -0.22759093F);
      this.shape9 = new ModelRenderer(this, 0, 0);
      this.shape9.setRotationPoint(2.4F, 15.4F, 1.4F);
      this.shape9.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.setRotateAngle(this.shape9, -0.68294734F, -0.68294734F, -2.1855013F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape2.offsetX, this.shape2.offsetY, this.shape2.offsetZ);
      GlStateManager.translate(this.shape2.rotationPointX * f5, this.shape2.rotationPointY * f5, this.shape2.rotationPointZ * f5);
      GlStateManager.scale(0.6, 2.0, 0.6);
      GlStateManager.translate(-this.shape2.offsetX, -this.shape2.offsetY, -this.shape2.offsetZ);
      GlStateManager.translate(-this.shape2.rotationPointX * f5, -this.shape2.rotationPointY * f5, -this.shape2.rotationPointZ * f5);
      this.shape2.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape7.offsetX, this.shape7.offsetY, this.shape7.offsetZ);
      GlStateManager.translate(this.shape7.rotationPointX * f5, this.shape7.rotationPointY * f5, this.shape7.rotationPointZ * f5);
      GlStateManager.scale(0.3, 0.3, 2.0);
      GlStateManager.translate(-this.shape7.offsetX, -this.shape7.offsetY, -this.shape7.offsetZ);
      GlStateManager.translate(-this.shape7.rotationPointX * f5, -this.shape7.rotationPointY * f5, -this.shape7.rotationPointZ * f5);
      this.shape7.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape10.offsetX, this.shape10.offsetY, this.shape10.offsetZ);
      GlStateManager.translate(this.shape10.rotationPointX * f5, this.shape10.rotationPointY * f5, this.shape10.rotationPointZ * f5);
      GlStateManager.scale(1.8, 0.4, 0.4);
      GlStateManager.translate(-this.shape10.offsetX, -this.shape10.offsetY, -this.shape10.offsetZ);
      GlStateManager.translate(-this.shape10.rotationPointX * f5, -this.shape10.rotationPointY * f5, -this.shape10.rotationPointZ * f5);
      this.shape10.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape8.offsetX, this.shape8.offsetY, this.shape8.offsetZ);
      GlStateManager.translate(this.shape8.rotationPointX * f5, this.shape8.rotationPointY * f5, this.shape8.rotationPointZ * f5);
      GlStateManager.scale(0.3, 0.3, 1.8);
      GlStateManager.translate(-this.shape8.offsetX, -this.shape8.offsetY, -this.shape8.offsetZ);
      GlStateManager.translate(-this.shape8.rotationPointX * f5, -this.shape8.rotationPointY * f5, -this.shape8.rotationPointZ * f5);
      this.shape8.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape5.offsetX, this.shape5.offsetY, this.shape5.offsetZ);
      GlStateManager.translate(this.shape5.rotationPointX * f5, this.shape5.rotationPointY * f5, this.shape5.rotationPointZ * f5);
      GlStateManager.scale(0.9, 1.7, 0.9);
      GlStateManager.translate(-this.shape5.offsetX, -this.shape5.offsetY, -this.shape5.offsetZ);
      GlStateManager.translate(-this.shape5.rotationPointX * f5, -this.shape5.rotationPointY * f5, -this.shape5.rotationPointZ * f5);
      this.shape5.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape4.offsetX, this.shape4.offsetY, this.shape4.offsetZ);
      GlStateManager.translate(this.shape4.rotationPointX * f5, this.shape4.rotationPointY * f5, this.shape4.rotationPointZ * f5);
      GlStateManager.scale(0.8, 1.8, 0.8);
      GlStateManager.translate(-this.shape4.offsetX, -this.shape4.offsetY, -this.shape4.offsetZ);
      GlStateManager.translate(-this.shape4.rotationPointX * f5, -this.shape4.rotationPointY * f5, -this.shape4.rotationPointZ * f5);
      this.shape4.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape6.offsetX, this.shape6.offsetY, this.shape6.offsetZ);
      GlStateManager.translate(this.shape6.rotationPointX * f5, this.shape6.rotationPointY * f5, this.shape6.rotationPointZ * f5);
      GlStateManager.scale(1.0, 1.3, 1.0);
      GlStateManager.translate(-this.shape6.offsetX, -this.shape6.offsetY, -this.shape6.offsetZ);
      GlStateManager.translate(-this.shape6.rotationPointX * f5, -this.shape6.rotationPointY * f5, -this.shape6.rotationPointZ * f5);
      this.shape6.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape1.offsetX, this.shape1.offsetY, this.shape1.offsetZ);
      GlStateManager.translate(this.shape1.rotationPointX * f5, this.shape1.rotationPointY * f5, this.shape1.rotationPointZ * f5);
      GlStateManager.scale(0.5, 3.5, 0.5);
      GlStateManager.translate(-this.shape1.offsetX, -this.shape1.offsetY, -this.shape1.offsetZ);
      GlStateManager.translate(-this.shape1.rotationPointX * f5, -this.shape1.rotationPointY * f5, -this.shape1.rotationPointZ * f5);
      this.shape1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape3.offsetX, this.shape3.offsetY, this.shape3.offsetZ);
      GlStateManager.translate(this.shape3.rotationPointX * f5, this.shape3.rotationPointY * f5, this.shape3.rotationPointZ * f5);
      GlStateManager.scale(0.7, 1.8, 0.7);
      GlStateManager.translate(-this.shape3.offsetX, -this.shape3.offsetY, -this.shape3.offsetZ);
      GlStateManager.translate(-this.shape3.rotationPointX * f5, -this.shape3.rotationPointY * f5, -this.shape3.rotationPointZ * f5);
      this.shape3.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape9.offsetX, this.shape9.offsetY, this.shape9.offsetZ);
      GlStateManager.translate(this.shape9.rotationPointX * f5, this.shape9.rotationPointY * f5, this.shape9.rotationPointZ * f5);
      GlStateManager.scale(1.5, 0.3, 0.3);
      GlStateManager.translate(-this.shape9.offsetX, -this.shape9.offsetY, -this.shape9.offsetZ);
      GlStateManager.translate(-this.shape9.rotationPointX * f5, -this.shape9.rotationPointY * f5, -this.shape9.rotationPointZ * f5);
      this.shape9.render(f5);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
