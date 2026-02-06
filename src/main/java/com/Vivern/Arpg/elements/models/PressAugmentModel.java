package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class PressAugmentModel extends ModelBase {
   public ModelRenderer shape5;
   public ModelRenderer shape2;
   public ModelRenderer shape4;
   public ModelRenderer shape1;
   public ModelRenderer shapeRotate1;
   public ModelRenderer shapeRotate2;
   public ModelRenderer shapeS4;
   public ModelRenderer shapeS3;
   public ModelRenderer shapeS2;
   public ModelRenderer shapeS1;
   public ModelRenderer shape3;

   public PressAugmentModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.shape4 = new ModelRenderer(this, 32, 22);
      this.shape4.setRotationPoint(0.0F, 10.0F, 0.0F);
      this.shape4.addBox(-4.0F, -4.01F, -4.0F, 8, 4, 8, 0.0F);
      this.shapeS2 = new ModelRenderer(this, 0, 0);
      this.shapeS2.setRotationPoint(0.0F, 12.0F, 0.0F);
      this.shapeS2.addBox(3.0F, 0.0F, -5.0F, 2, 8, 2, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 34);
      this.shape1.setRotationPoint(0.0F, 10.0F, 0.0F);
      this.shape1.addBox(-8.0F, 0.0F, -8.0F, 16, 14, 16, -0.01F);
      this.shapeRotate2 = new ModelRenderer(this, 0, 18);
      this.shapeRotate2.setRotationPoint(-5.01F, 10.0F, 0.0F);
      this.shapeRotate2.addBox(-1.5F, -1.5F, -2.0F, 3, 3, 4, 0.0F);
      this.shape5 = new ModelRenderer(this, -16, 34);
      this.shape5.setRotationPoint(0.0F, 20.0F, 0.0F);
      this.shape5.addBox(-8.0F, 0.0F, -8.0F, 16, 0, 16, -0.02F);
      this.shape2 = new ModelRenderer(this, 40, 0);
      this.shape2.setRotationPoint(0.0F, 6.0F, 0.0F);
      this.shape2.addBox(-3.0F, 0.0F, -3.0F, 6, 8, 6, 0.0F);
      this.shapeS3 = new ModelRenderer(this, 0, 0);
      this.shapeS3.setRotationPoint(0.0F, 12.0F, 0.0F);
      this.shapeS3.addBox(3.0F, 0.0F, 3.0F, 2, 8, 2, 0.0F);
      this.shape3 = new ModelRenderer(this, 0, 2);
      this.shape3.setRotationPoint(0.0F, 8.0F, 0.0F);
      this.shape3.addBox(-6.0F, 0.0F, -6.0F, 12, 2, 12, 0.0F);
      this.shapeRotate1 = new ModelRenderer(this, 0, 18);
      this.shapeRotate1.setRotationPoint(5.01F, 10.0F, 0.0F);
      this.shapeRotate1.addBox(-1.5F, -1.5F, -2.0F, 3, 3, 4, 0.0F);
      this.shapeS1 = new ModelRenderer(this, 0, 0);
      this.shapeS1.setRotationPoint(0.0F, 12.0F, 0.0F);
      this.shapeS1.addBox(-5.0F, 0.0F, -5.0F, 2, 8, 2, 0.0F);
      this.shapeS4 = new ModelRenderer(this, 0, 0);
      this.shapeS4.setRotationPoint(0.0F, 12.0F, 0.0F);
      this.shapeS4.addBox(-5.0F, 0.0F, 3.0F, 2, 8, 2, 0.0F);
      this.shape2.addChild(this.shape3);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shape4.render(f5);
      this.shapeS2.render(f5);
      this.shape1.render(f5);
      this.shapeRotate2.render(f5);
      this.shape5.render(f5);
      this.shape2.render(f5);
      this.shapeS3.render(f5);
      this.shapeRotate1.render(f5);
      this.shapeS1.render(f5);
      this.shapeS4.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
