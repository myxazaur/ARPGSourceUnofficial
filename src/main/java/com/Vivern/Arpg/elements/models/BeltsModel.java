//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class BeltsModel extends ModelBase {
   public ModelRenderer beltBended1;
   public ModelRenderer beltBended2;
   public ModelRenderer beltRibbons;
   public ModelRenderer beltKnotSmall;
   public ModelRenderer beltClassic;
   public ModelRenderer beltBig;
   public ModelRenderer beltKnotBig;
   public ModelRenderer beltPotions1;
   public ModelRenderer beltPotions2;
   public ModelRenderer beltPotions3;
   public ModelRenderer beltBag;
   public ModelRenderer beltKnotRightFront;
   public ModelRenderer beltKnotRightBack;
   public ModelRenderer beltKnotLeftFront;
   public ModelRenderer beltKnotLeftBack;
   public ModelRenderer beltKnotSmallBack;
   public ModelRenderer beltKnotBigBack;
   public ModelRenderer beltBigDiagonal;

   public BeltsModel() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      this.beltKnotRightBack = new ModelRenderer(this, 16, 22);
      this.beltKnotRightBack.setRotationPoint(-3.6F, 9.0F, 1.5F);
      this.beltKnotRightBack.addBox(-1.0F, -1.0F, -1.5F, 2, 2, 1, 0.0F);
      this.setRotateAngle(this.beltKnotRightBack, 0.0F, 2.003289F, 0.0F);
      this.beltKnotLeftFront = new ModelRenderer(this, 16, 22);
      this.beltKnotLeftFront.setRotationPoint(3.6F, 9.0F, -1.5F);
      this.beltKnotLeftFront.addBox(-1.0F, -1.0F, -1.5F, 2, 2, 1, 0.0F);
      this.setRotateAngle(this.beltKnotLeftFront, 0.0F, -1.1383038F, 0.0F);
      this.beltPotions1 = new ModelRenderer(this, 0, 14);
      this.beltPotions1.setRotationPoint(-4.5F, 9.5F, -0.5F);
      this.beltPotions1.addBox(-1.0F, 0.0F, -1.0F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.beltPotions1, 0.091106184F, 0.0F, 0.0F);
      this.beltBended1 = new ModelRenderer(this, 0, 26);
      this.beltBended1.setRotationPoint(0.0F, 9.0F, 0.0F);
      this.beltBended1.addBox(-4.5F, -0.5F, -2.5F, 9, 1, 5, 0.1F);
      this.setRotateAngle(this.beltBended1, 0.0F, 0.0F, -0.13665928F);
      this.beltRibbons = new ModelRenderer(this, 4, 22);
      this.beltRibbons.setRotationPoint(0.0F, 9.5F, -2.5F);
      this.beltRibbons.addBox(-3.0F, 0.0F, 0.0F, 6, 4, 0, 0.0F);
      this.setRotateAngle(this.beltRibbons, -0.091106184F, 0.0F, 0.0F);
      this.beltClassic = new ModelRenderer(this, 0, 26);
      this.beltClassic.setRotationPoint(0.0F, 9.0F, 0.0F);
      this.beltClassic.addBox(-4.5F, -0.5F, -2.5F, 9, 1, 5, 0.09F);
      this.beltPotions3 = new ModelRenderer(this, 0, 22);
      this.beltPotions3.setRotationPoint(-3.2F, 9.5F, -2.5F);
      this.beltPotions3.addBox(-1.0F, 0.0F, -1.0F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.beltPotions3, 0.091106184F, 0.0F, 0.0F);
      this.beltPotions2 = new ModelRenderer(this, 0, 18);
      this.beltPotions2.setRotationPoint(-4.5F, 9.5F, -2.1F);
      this.beltPotions2.addBox(-1.0F, 0.0F, -1.0F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.beltPotions2, 0.091106184F, 0.0F, 0.0F);
      this.beltBag = new ModelRenderer(this, 24, 24);
      this.beltBag.setRotationPoint(4.0F, 9.5F, -1.5F);
      this.beltBag.addBox(0.0F, 0.0F, -1.5F, 1, 4, 3, 0.0F);
      this.beltKnotSmallBack = new ModelRenderer(this, 25, 0);
      this.beltKnotSmallBack.setRotationPoint(0.0F, 9.0F, 1.75F);
      this.beltKnotSmallBack.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 1, 0.0F);
      this.beltKnotRightFront = new ModelRenderer(this, 16, 22);
      this.beltKnotRightFront.setRotationPoint(-3.6F, 9.0F, -1.5F);
      this.beltKnotRightFront.addBox(-1.0F, -1.0F, -1.5F, 2, 2, 1, 0.0F);
      this.setRotateAngle(this.beltKnotRightFront, 0.0F, 1.1383038F, 0.0F);
      this.beltKnotSmall = new ModelRenderer(this, 0, 0);
      this.beltKnotSmall.setRotationPoint(0.0F, 9.0F, -1.75F);
      this.beltKnotSmall.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 1, 0.0F);
      this.beltKnotLeftBack = new ModelRenderer(this, 16, 22);
      this.beltKnotLeftBack.setRotationPoint(3.6F, 9.0F, 1.5F);
      this.beltKnotLeftBack.addBox(-1.0F, -1.0F, -1.5F, 2, 2, 1, 0.0F);
      this.setRotateAngle(this.beltKnotLeftBack, 0.0F, -2.003289F, 0.0F);
      this.beltKnotBigBack = new ModelRenderer(this, 23, 10);
      this.beltKnotBigBack.setRotationPoint(0.0F, 9.0F, 2.0F);
      this.beltKnotBigBack.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 1, 0.0F);
      this.beltBended2 = new ModelRenderer(this, 0, 26);
      this.beltBended2.setRotationPoint(0.0F, 9.0F, 0.0F);
      this.beltBended2.addBox(-4.5F, -0.5F, -2.5F, 9, 1, 5, 0.08F);
      this.setRotateAngle(this.beltBended2, 0.0F, 0.0F, 0.13665928F);
      this.beltKnotBig = new ModelRenderer(this, 0, 10);
      this.beltKnotBig.setRotationPoint(0.0F, 9.0F, -2.0F);
      this.beltKnotBig.addBox(-1.5F, -1.5F, -1.0F, 3, 3, 1, 0.0F);
      this.beltBigDiagonal = new ModelRenderer(this, 4, 10);
      this.beltBigDiagonal.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.beltBigDiagonal.addBox(-6.5F, -0.5F, -2.5F, 7, 7, 5, 0.07F);
      this.setRotateAngle(this.beltBigDiagonal, 0.0F, 0.0F, (float) (-Math.PI / 4));
      this.beltBig = new ModelRenderer(this, 2, 0);
      this.beltBig.setRotationPoint(0.0F, 7.0F, 0.0F);
      this.beltBig.addBox(-4.5F, -0.5F, -2.5F, 9, 5, 5, 0.11F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.beltKnotRightBack.render(f5);
      this.beltKnotLeftFront.render(f5);
      this.beltPotions1.render(f5);
      this.beltBended1.render(f5);
      this.beltRibbons.render(f5);
      this.beltClassic.render(f5);
      this.beltPotions3.render(f5);
      this.beltPotions2.render(f5);
      this.beltBag.render(f5);
      this.beltKnotSmallBack.render(f5);
      this.beltKnotRightFront.render(f5);
      this.beltKnotSmall.render(f5);
      this.beltKnotLeftBack.render(f5);
      this.beltKnotBigBack.render(f5);
      this.beltBended2.render(f5);
      this.beltKnotBig.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.beltBigDiagonal.offsetX, this.beltBigDiagonal.offsetY, this.beltBigDiagonal.offsetZ);
      GlStateManager.translate(this.beltBigDiagonal.rotationPointX * f5, this.beltBigDiagonal.rotationPointY * f5, this.beltBigDiagonal.rotationPointZ * f5);
      GlStateManager.scale(0.98, 1.0, 1.0);
      GlStateManager.translate(-this.beltBigDiagonal.offsetX, -this.beltBigDiagonal.offsetY, -this.beltBigDiagonal.offsetZ);
      GlStateManager.translate(-this.beltBigDiagonal.rotationPointX * f5, -this.beltBigDiagonal.rotationPointY * f5, -this.beltBigDiagonal.rotationPointZ * f5);
      this.beltBigDiagonal.render(f5);
      GlStateManager.popMatrix();
      this.beltBig.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
