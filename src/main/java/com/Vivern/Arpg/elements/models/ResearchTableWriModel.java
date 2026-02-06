package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ResearchTableWriModel extends ModelBase {
   public ModelRenderer shape2;
   public ModelRenderer shapeInk;
   public ModelRenderer shapeBlank;
   public ModelRenderer shape9;
   public ModelRenderer shape10;
   public ModelRenderer shape11;
   public ModelRenderer shapeBlank2;
   public ModelRenderer shape12;
   public ModelRenderer shape13;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shape5;
   public ModelRenderer shapeB1;
   public ModelRenderer shapeB2;
   public ModelRenderer shape6;

   public ResearchTableWriModel() {
      this.textureWidth = 48;
      this.textureHeight = 32;
      this.shape2 = new ModelRenderer(this, 0, 16);
      this.shape2.setRotationPoint(5.5F, 5.0F, -3.5F);
      this.shape2.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
      this.setRotateAngle(this.shape2, 0.0F, -0.13665928F, 0.0F);
      this.shapeB2 = new ModelRenderer(this, 0, 0);
      this.shapeB2.setRotationPoint(8.5F, 0.0F, 0.2F);
      this.shapeB2.addBox(-0.5F, -0.5F, -0.5F, 1, 5, 1, 0.0F);
      this.setRotateAngle(this.shapeB2, 0.0F, -0.3642502F, 0.0F);
      this.shape3 = new ModelRenderer(this, 12, 17);
      this.shape3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape3.addBox(-0.5F, -3.0F, -0.5F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shape3, -1.0927507F, -1.2292354F, 1.5025539F);
      this.shape13 = new ModelRenderer(this, 4, 0);
      this.shape13.setRotationPoint(-5.8F, 7.5F, -2.2F);
      this.shape13.addBox(-0.5F, -0.5F, -0.5F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shape13, 2.5041983F, 2.9140017F, 1.4114478F);
      this.shape10 = new ModelRenderer(this, 8, 0);
      this.shape10.setRotationPoint(0.0F, 3.0F, 0.9F);
      this.shape10.addBox(-1.5F, -0.7F, -0.5F, 3, 5, 1, 0.0F);
      this.shape5 = new ModelRenderer(this, 24, 16);
      this.shape5.setRotationPoint(0.0F, -4.0F, 0.0F);
      this.shape5.addBox(-0.5F, -2.7F, -1.5F, 2, 3, 2, 0.0F);
      this.setRotateAngle(this.shape5, -0.13665928F, 0.0F, -0.091106184F);
      this.shape12 = new ModelRenderer(this, 4, 0);
      this.shape12.setRotationPoint(-6.5F, 7.5F, -3.5F);
      this.shape12.addBox(-0.5F, -0.5F, -0.5F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shape12, (float) (Math.PI * 2.0 / 3.0), 2.276433F, 0.63739425F);
      this.shapeInk = new ModelRenderer(this, 0, 0);
      this.shapeInk.setRotationPoint(-8.0F, 7.95F, -8.0F);
      this.shapeInk.addBox(0.0F, 0.0F, 0.0F, 16, 0, 16, 0.0F);
      this.shape9 = new ModelRenderer(this, 30, 21);
      this.shape9.setRotationPoint(-4.0F, 1.0F, 1.0F);
      this.shape9.addBox(0.0F, 0.0F, 0.0F, 8, 3, 1, 0.0F);
      this.setRotateAngle(this.shape9, -0.59184116F, 0.0F, 0.0F);
      this.shape11 = new ModelRenderer(this, 0, 12);
      this.shape11.setRotationPoint(-1.0F, 8.0F, -0.1F);
      this.shape11.addBox(-1.5F, -0.7F, -0.5F, 5, 1, 3, 0.0F);
      this.shapeBlank = new ModelRenderer(this, 0, 7);
      this.shapeBlank.setRotationPoint(-4.0F, 0.0F, 1.0F);
      this.shapeBlank.addBox(0.0F, 0.0F, 0.0F, 8, 4, 0, 0.0F);
      this.setRotateAngle(this.shapeBlank, -0.59184116F, 0.0F, 0.0F);
      this.shapeBlank2 = new ModelRenderer(this, 32, 16);
      this.shapeBlank2.setRotationPoint(2.0F, 7.5F, 7.0F);
      this.shapeBlank2.addBox(0.0F, 0.0F, 0.0F, 5, 3, 0, 0.0F);
      this.setRotateAngle(this.shapeBlank2, -1.6390387F, 0.5009095F, -0.13665928F);
      this.shape4 = new ModelRenderer(this, 16, 16);
      this.shape4.setRotationPoint(0.0F, -3.0F, 0.0F);
      this.shape4.addBox(-0.5F, -3.7F, -1.5F, 2, 4, 2, 0.0F);
      this.setRotateAngle(this.shape4, 0.3642502F, -0.091106184F, 0.3642502F);
      this.shapeB1 = new ModelRenderer(this, 0, 0);
      this.shapeB1.setRotationPoint(-0.5F, 0.0F, 0.2F);
      this.shapeB1.addBox(-0.5F, -0.5F, -0.5F, 1, 5, 1, 0.0F);
      this.setRotateAngle(this.shapeB1, 0.0F, 0.3642502F, 0.0F);
      this.shape6 = new ModelRenderer(this, 4, 0);
      this.shape6.setRotationPoint(5.5F, 0.0F, 0.2F);
      this.shape6.addBox(-0.5F, -0.5F, 0.0F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shape6, 0.0F, -0.59184116F, 0.0F);
      this.shapeBlank.addChild(this.shapeB2);
      this.shape2.addChild(this.shape3);
      this.shape4.addChild(this.shape5);
      this.shape3.addChild(this.shape4);
      this.shapeBlank.addChild(this.shapeB1);
      this.shapeBlank2.addChild(this.shape6);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shape2.render(f5);
      this.shape13.render(f5);
      this.shape10.render(f5);
      this.shape12.render(f5);
      this.shapeInk.render(f5);
      this.shape9.render(f5);
      this.shape11.render(f5);
      this.shapeBlank.render(f5);
      this.shapeBlank2.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
