package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class TurningAugmentModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shapeS1;
   public ModelRenderer shapeS2;
   public ModelRenderer shapeS3;
   public ModelRenderer shapeS4;
   public ModelRenderer shapeS5;
   public ModelRenderer shapeS6;
   public ModelRenderer shapeS7;
   public ModelRenderer shapeS8;
   public ModelRenderer shapeMove1;
   public ModelRenderer shapeMove2;
   public ModelRenderer engine1;
   public ModelRenderer rotate;
   public ModelRenderer engine2;

   public TurningAugmentModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.engine2 = new ModelRenderer(this, 15, 0);
      this.engine2.setRotationPoint(0.0F, -1.0F, 0.0F);
      this.engine2.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
      this.engine1 = new ModelRenderer(this, 26, 0);
      this.engine1.setRotationPoint(0.0F, 10.0F, 0.0F);
      this.engine1.addBox(-1.5F, -5.0F, -1.5F, 3, 4, 3, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 34);
      this.shape1.setRotationPoint(0.0F, 10.0F, 0.0F);
      this.shape1.addBox(-8.0F, 0.0F, -8.0F, 16, 14, 16, -0.01F);
      this.shapeS4 = new ModelRenderer(this, 0, 0);
      this.shapeS4.setRotationPoint(0.0F, 10.0F, 0.0F);
      this.shapeS4.addBox(-7.0F, -4.0F, 5.0F, 2, 4, 2, 0.0F);
      this.shapeS8 = new ModelRenderer(this, 0, 0);
      this.shapeS8.setRotationPoint(0.0F, 10.0F, 0.0F);
      this.shapeS8.addBox(5.0F, -4.0F, -5.0F, 2, 1, 10, 0.0F);
      this.shapeMove2 = new ModelRenderer(this, 14, 2);
      this.shapeMove2.setRotationPoint(0.0F, 10.0F, 0.0F);
      this.shapeMove2.addBox(-0.5F, -4.0F, -5.0F, 1, 1, 10, 0.0F);
      this.setRotateAngle(this.shapeMove2, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.shapeS7 = new ModelRenderer(this, 0, 0);
      this.shapeS7.setRotationPoint(0.0F, 10.0F, 0.0F);
      this.shapeS7.addBox(-7.0F, -4.0F, -5.0F, 2, 1, 10, 0.0F);
      this.setRotateAngle(this.shapeS7, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.rotate = new ModelRenderer(this, 38, 0);
      this.rotate.setRotationPoint(0.0F, 18.0F, 0.0F);
      this.rotate.addBox(-2.5F, -2.5F, -4.0F, 5, 5, 8, 0.0F);
      this.shapeS1 = new ModelRenderer(this, 0, 0);
      this.shapeS1.setRotationPoint(0.0F, 10.0F, 0.0F);
      this.shapeS1.addBox(-7.0F, -4.0F, -7.0F, 2, 4, 2, 0.0F);
      this.shapeS2 = new ModelRenderer(this, 0, 0);
      this.shapeS2.setRotationPoint(0.0F, 10.0F, 0.0F);
      this.shapeS2.addBox(5.0F, -4.0F, -7.0F, 2, 4, 2, 0.0F);
      this.shapeS5 = new ModelRenderer(this, 0, 0);
      this.shapeS5.setRotationPoint(0.0F, 10.0F, 0.0F);
      this.shapeS5.addBox(-7.0F, -4.0F, -5.0F, 2, 1, 10, 0.0F);
      this.shapeMove1 = new ModelRenderer(this, 12, 0);
      this.shapeMove1.setRotationPoint(0.0F, 10.0F, 0.0F);
      this.shapeMove1.addBox(-0.5F, -3.0F, -7.0F, 1, 1, 12, 0.0F);
      this.shape2 = new ModelRenderer(this, 0, 16);
      this.shape2.setRotationPoint(0.0F, 10.0F, 0.0F);
      this.shape2.addBox(-5.0F, 0.0F, -5.0F, 10, 6, 10, -0.01F);
      this.shapeS6 = new ModelRenderer(this, 0, 0);
      this.shapeS6.setRotationPoint(0.0F, 10.0F, 0.0F);
      this.shapeS6.addBox(5.0F, -4.0F, -5.0F, 2, 1, 10, 0.0F);
      this.setRotateAngle(this.shapeS6, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.shapeS3 = new ModelRenderer(this, 0, 0);
      this.shapeS3.setRotationPoint(0.0F, 10.0F, 0.0F);
      this.shapeS3.addBox(5.0F, -4.0F, 5.0F, 2, 4, 2, 0.0F);
      this.engine1.addChild(this.engine2);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.engine1.render(f5);
      this.shape1.render(f5);
      this.shapeS4.render(f5);
      this.shapeS8.render(f5);
      this.shapeMove2.render(f5);
      this.shapeS7.render(f5);
      this.rotate.render(f5);
      this.shapeS1.render(f5);
      this.shapeS2.render(f5);
      this.shapeS5.render(f5);
      this.shapeMove1.render(f5);
      this.shape2.render(f5);
      this.shapeS6.render(f5);
      this.shapeS3.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
