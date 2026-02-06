package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ManaBottleModel extends ModelBase {
   public ModelRenderer shapeA1;
   public ModelRenderer shapeAcap;
   public ModelRenderer shapeA2;
   public ModelRenderer shapeA3;
   public ModelRenderer shapeB1;
   public ModelRenderer shapeB2;
   public ModelRenderer shapeB3;
   public ModelRenderer shapeBcap;
   public ModelRenderer shapeC1;
   public ModelRenderer shapeC2;
   public ModelRenderer shapeC3;
   public ModelRenderer shapeC4;
   public ModelRenderer shapeC5;
   public ModelRenderer shapeC6;
   public ModelRenderer shapeCcap;

   public ManaBottleModel() {
      this.textureWidth = 16;
      this.textureHeight = 16;
      this.shapeC4 = new ModelRenderer(this, 0, 0);
      this.shapeC4.setRotationPoint(32.0F, 4.0F, 0.0F);
      this.shapeC4.addBox(-4.0F, 0.0F, -4.0F, 8, 5, 8, 0.0F);
      this.shapeB2 = new ModelRenderer(this, 2, 0);
      this.shapeB2.setRotationPoint(16.0F, 5.0F, 0.0F);
      this.shapeB2.addBox(-3.0F, 0.0F, -3.0F, 6, 8, 6, 0.0F);
      this.shapeC5 = new ModelRenderer(this, 0, 0);
      this.shapeC5.setRotationPoint(32.0F, 6.0F, 0.0F);
      this.shapeC5.addBox(-5.0F, 0.0F, -5.0F, 10, 1, 10, 0.0F);
      this.shapeAcap = new ModelRenderer(this, 0, 0);
      this.shapeAcap.setRotationPoint(0.0F, 5.0F, 0.0F);
      this.shapeAcap.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
      this.shapeC1 = new ModelRenderer(this, 0, 0);
      this.shapeC1.setRotationPoint(32.0F, 16.0F, 0.0F);
      this.shapeC1.addBox(-6.0F, -7.0F, -6.0F, 12, 12, 12, 0.0F);
      this.shapeB3 = new ModelRenderer(this, 4, 5);
      this.shapeB3.setRotationPoint(16.0F, 1.0F, 0.0F);
      this.shapeB3.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
      this.shapeA3 = new ModelRenderer(this, 4, 5);
      this.shapeA3.setRotationPoint(0.0F, 8.0F, 0.0F);
      this.shapeA3.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0.0F);
      this.shapeC3 = new ModelRenderer(this, 0, 0);
      this.shapeC3.setRotationPoint(32.0F, 23.0F, 0.0F);
      this.shapeC3.addBox(-5.0F, 0.0F, -5.0F, 10, 1, 10, 0.0F);
      this.shapeB1 = new ModelRenderer(this, 0, 0);
      this.shapeB1.setRotationPoint(16.0F, 16.0F, 0.0F);
      this.shapeB1.addBox(-5.5F, -3.0F, -5.5F, 11, 11, 11, 0.0F);
      this.shapeA1 = new ModelRenderer(this, 0, 0);
      this.shapeA1.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shapeA1.addBox(-5.0F, 0.0F, -5.0F, 10, 8, 10, 0.0F);
      this.shapeC6 = new ModelRenderer(this, 0, 0);
      this.shapeC6.setRotationPoint(32.0F, -3.0F, 0.0F);
      this.shapeC6.addBox(-1.5F, 0.0F, -1.5F, 3, 7, 3, 0.0F);
      this.shapeCcap = new ModelRenderer(this, 0, 0);
      this.shapeCcap.setRotationPoint(32.0F, -6.0F, 0.0F);
      this.shapeCcap.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
      this.shapeA2 = new ModelRenderer(this, 2, 0);
      this.shapeA2.setRotationPoint(0.0F, 13.0F, 0.0F);
      this.shapeA2.addBox(-3.0F, 0.0F, -3.0F, 6, 3, 6, 0.0F);
      this.shapeC2 = new ModelRenderer(this, 0, 0);
      this.shapeC2.setRotationPoint(32.0F, 21.0F, 0.0F);
      this.shapeC2.addBox(-4.0F, 0.0F, -4.0F, 8, 2, 8, 0.0F);
      this.shapeBcap = new ModelRenderer(this, 0, 0);
      this.shapeBcap.setRotationPoint(16.0F, -2.0F, 0.0F);
      this.shapeBcap.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (f == 0.0F) {
         this.shapeA3.render(f5);
         this.shapeA2.render(f5);
         this.shapeA1.render(f5);
         this.shapeAcap.offsetY = f1;
         this.shapeAcap.render(f5);
      }

      if (f == 1.0F) {
         this.shapeB3.render(f5);
         this.shapeB2.render(f5);
         this.shapeB1.render(f5);
         this.shapeBcap.offsetY = f1;
         this.shapeBcap.render(f5);
      }

      if (f == 2.0F) {
         this.shapeC6.render(f5);
         this.shapeC4.render(f5);
         this.shapeC5.render(f5);
         this.shapeC1.render(f5);
         this.shapeC2.render(f5);
         this.shapeC3.render(f5);
         this.shapeCcap.offsetY = f1;
         this.shapeCcap.render(f5);
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
