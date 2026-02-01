package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelsCloud {
   public static ModelBase[] cloudsBySize = new ModelBase[]{new Cloud3Model(), new Cloud1Model(), new Cloud2Model()};
   public static int modelsCount = 2;

   public static class Cloud1Model extends ModelBase {
      public ModelRenderer shape1;
      public ModelRenderer shape2;
      public ModelRenderer shape3;

      public Cloud1Model() {
         this.textureWidth = 48;
         this.textureHeight = 32;
         this.shape1 = new ModelRenderer(this, 16, 16);
         this.shape1.setRotationPoint(-3.0F, 0.0F, -2.0F);
         this.shape1.addBox(0.0F, 0.0F, 0.0F, 8, 6, 8, 0.0F);
         this.shape3 = new ModelRenderer(this, 0, 20);
         this.shape3.setRotationPoint(1.0F, 2.0F, -6.0F);
         this.shape3.addBox(0.0F, 0.0F, 0.0F, 6, 6, 6, 0.0F);
         this.shape2 = new ModelRenderer(this, 0, 0);
         this.shape2.setRotationPoint(0.0F, -2.0F, 3.0F);
         this.shape2.addBox(0.0F, 0.0F, 0.0F, 8, 6, 10, 0.0F);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         this.shape1.render(f5);
         this.shape3.render(f5);
         this.shape2.render(f5);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class Cloud2Model extends ModelBase {
      public ModelRenderer shape1;
      public ModelRenderer shape2;
      public ModelRenderer shape3;
      public ModelRenderer shape4;

      public Cloud2Model() {
         this.textureWidth = 48;
         this.textureHeight = 32;
         this.shape3 = new ModelRenderer(this, 0, 17);
         this.shape3.setRotationPoint(-4.0F, 2.0F, 2.0F);
         this.shape3.addBox(0.0F, 0.0F, 0.0F, 9, 3, 12, 0.0F);
         this.shape4 = new ModelRenderer(this, 22, 0);
         this.shape4.setRotationPoint(-7.0F, 1.0F, -9.0F);
         this.shape4.addBox(0.0F, 0.0F, 0.0F, 6, 6, 6, 0.0F);
         this.shape1 = new ModelRenderer(this, 16, 16);
         this.shape1.setRotationPoint(-5.0F, -1.0F, -4.0F);
         this.shape1.addBox(0.0F, 0.0F, 0.0F, 8, 8, 8, 0.0F);
         this.shape2 = new ModelRenderer(this, 0, 9);
         this.shape2.setRotationPoint(-10.0F, 1.0F, 0.0F);
         this.shape2.addBox(0.0F, 0.0F, 0.0F, 8, 5, 10, 0.0F);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         this.shape3.render(f5);
         this.shape4.render(f5);
         this.shape1.render(f5);
         this.shape2.render(f5);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class Cloud3Model extends ModelBase {
      public ModelRenderer shape1;
      public ModelRenderer shape2;
      public ModelRenderer shape3;

      public Cloud3Model() {
         this.textureWidth = 48;
         this.textureHeight = 32;
         this.shape2 = new ModelRenderer(this, 28, 0);
         this.shape2.setRotationPoint(-7.0F, 3.0F, -7.0F);
         this.shape2.addBox(0.0F, 0.0F, 0.0F, 4, 5, 5, 0.0F);
         this.shape3 = new ModelRenderer(this, 23, 18);
         this.shape3.setRotationPoint(-6.0F, 1.0F, -9.0F);
         this.shape3.addBox(0.0F, 0.0F, 0.0F, 5, 4, 3, 0.0F);
         this.shape1 = new ModelRenderer(this, 0, 17);
         this.shape1.setRotationPoint(-5.0F, -1.0F, -4.0F);
         this.shape1.addBox(0.0F, 0.0F, 0.0F, 6, 7, 8, 0.0F);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         this.shape2.render(f5);
         this.shape3.render(f5);
         this.shape1.render(f5);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }
}
