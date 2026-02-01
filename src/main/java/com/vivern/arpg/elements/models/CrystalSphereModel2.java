package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class CrystalSphereModel2 extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape1_1;
   public ModelRenderer shape1_2;
   public ModelRenderer shape1_3;
   public ModelRenderer shape1_4;
   public ModelRenderer shape1_5;
   public ModelRenderer shape1_6;
   public ModelRenderer shape1_7;
   public ModelRenderer shape1_8;
   public ModelRenderer shape1_9;
   public ModelRenderer shape1_10;
   public ModelRenderer shape1_11;
   public ModelRenderer shapeMain;
   public ModelRenderer shapeMain_1;
   public ModelRenderer shapeMain_2;
   public ModelRenderer shapeMain_3;
   public ModelRenderer shapeMain_4;
   public ModelRenderer shapeMain_5;
   public ModelRenderer shapeOver;
   public ModelRenderer shapeOver_1;
   public ModelRenderer shapeOver_2;
   public ModelRenderer shapeOver_3;
   public ModelRenderer shapeOver_4;
   public ModelRenderer shapeOver_5;
   public ModelRenderer shapeIn;

   public CrystalSphereModel2() {
      this.textureWidth = 64;
      this.textureHeight = 32;
      this.shapeOver_5 = new ModelRenderer(this, 10, 4);
      this.shapeOver_5.setRotationPoint(0.0F, 15.0F, 0.0F);
      this.shapeOver_5.addBox(-5.0F, -4.0F, -4.0F, 1, 8, 8, 0.1F);
      this.shape1_3 = new ModelRenderer(this, 7, 0);
      this.shape1_3.setRotationPoint(-5.0F, 23.0F, -0.5F);
      this.shape1_3.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
      this.shape1_4 = new ModelRenderer(this, 9, 0);
      this.shape1_4.setRotationPoint(-3.0F, 21.0F, -0.5F);
      this.shape1_4.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
      this.shapeOver_3 = new ModelRenderer(this, 0, 20);
      this.shapeOver_3.setRotationPoint(0.0F, 15.0F, 0.0F);
      this.shapeOver_3.addBox(-4.0F, 4.0F, -4.0F, 8, 1, 8, 0.1F);
      this.shape1_10 = new ModelRenderer(this, 11, 0);
      this.shape1_10.setRotationPoint(-0.5F, 20.0F, 3.0F);
      this.shape1_10.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
      this.shape1_7 = new ModelRenderer(this, 1, 0);
      this.shape1_7.setRotationPoint(-0.5F, 23.0F, -5.0F);
      this.shape1_7.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
      this.shapeIn = new ModelRenderer(this, 28, 0);
      this.shapeIn.setRotationPoint(0.0F, 19.0F, 0.0F);
      this.shapeIn.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
      this.shape1_9 = new ModelRenderer(this, 6, 0);
      this.shape1_9.setRotationPoint(-0.5F, 21.0F, 2.0F);
      this.shape1_9.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
      this.shapeMain_2 = new ModelRenderer(this, 0, 3);
      this.shapeMain_2.setRotationPoint(0.0F, 15.0F, 0.0F);
      this.shapeMain_2.addBox(-4.0F, -4.0F, -5.0F, 8, 8, 1, 0.0F);
      this.shape1_8 = new ModelRenderer(this, 0, 0);
      this.shape1_8.setRotationPoint(-0.5F, 20.0F, -4.0F);
      this.shape1_8.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
      this.shapeOver_4 = new ModelRenderer(this, 0, 3);
      this.shapeOver_4.setRotationPoint(0.0F, 15.0F, 0.0F);
      this.shapeOver_4.addBox(-4.0F, -4.0F, 4.0F, 8, 8, 1, 0.1F);
      this.shape1_1 = new ModelRenderer(this, 13, 0);
      this.shape1_1.setRotationPoint(2.0F, 21.0F, -0.5F);
      this.shape1_1.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
      this.shape1_2 = new ModelRenderer(this, 4, 0);
      this.shape1_2.setRotationPoint(3.0F, 20.0F, -0.5F);
      this.shape1_2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
      this.shapeMain_3 = new ModelRenderer(this, 10, 4);
      this.shapeMain_3.setRotationPoint(0.0F, 15.0F, 0.0F);
      this.shapeMain_3.addBox(-5.0F, -4.0F, -4.0F, 1, 8, 8, 0.0F);
      this.shape1_11 = new ModelRenderer(this, 0, 0);
      this.shape1_11.setRotationPoint(-0.5F, 23.0F, 3.0F);
      this.shape1_11.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
      this.shapeMain_4 = new ModelRenderer(this, 10, 4);
      this.shapeMain_4.setRotationPoint(0.0F, 15.0F, 0.0F);
      this.shapeMain_4.addBox(4.0F, -4.0F, -4.0F, 1, 8, 8, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(3.0F, 23.0F, -0.5F);
      this.shape1.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
      this.shapeMain_1 = new ModelRenderer(this, 0, 3);
      this.shapeMain_1.setRotationPoint(0.0F, 15.0F, 0.0F);
      this.shapeMain_1.addBox(-4.0F, -4.0F, 4.0F, 8, 8, 1, 0.0F);
      this.shapeMain_5 = new ModelRenderer(this, 0, 20);
      this.shapeMain_5.setRotationPoint(0.0F, 15.0F, 0.0F);
      this.shapeMain_5.addBox(-4.0F, 4.0F, -4.0F, 8, 1, 8, 0.0F);
      this.shapeOver_2 = new ModelRenderer(this, 0, 20);
      this.shapeOver_2.setRotationPoint(0.0F, 15.0F, 0.0F);
      this.shapeOver_2.addBox(-4.0F, -5.0F, -4.0F, 8, 1, 8, 0.1F);
      this.shape1_6 = new ModelRenderer(this, 5, 0);
      this.shape1_6.setRotationPoint(-0.5F, 21.0F, -3.0F);
      this.shape1_6.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
      this.shapeMain = new ModelRenderer(this, 0, 20);
      this.shapeMain.setRotationPoint(0.0F, 15.0F, 0.0F);
      this.shapeMain.addBox(-4.0F, -5.0F, -4.0F, 8, 1, 8, 0.0F);
      this.shapeOver = new ModelRenderer(this, 0, 3);
      this.shapeOver.setRotationPoint(0.0F, 15.0F, 0.0F);
      this.shapeOver.addBox(-4.0F, -4.0F, -5.0F, 8, 8, 1, 0.1F);
      this.shape1_5 = new ModelRenderer(this, 0, 0);
      this.shape1_5.setRotationPoint(-4.0F, 20.0F, -0.5F);
      this.shape1_5.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
      this.shapeOver_1 = new ModelRenderer(this, 10, 4);
      this.shapeOver_1.setRotationPoint(0.0F, 15.0F, 0.0F);
      this.shapeOver_1.addBox(4.0F, -4.0F, -4.0F, 1, 8, 8, 0.1F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (f == 0.0F) {
         this.shape1_3.render(f5);
         this.shape1_4.render(f5);
         this.shape1_10.render(f5);
         this.shape1_7.render(f5);
         this.shape1_9.render(f5);
         this.shape1_8.render(f5);
         this.shape1_1.render(f5);
         this.shape1_2.render(f5);
         this.shape1_11.render(f5);
         this.shape1.render(f5);
         this.shape1_6.render(f5);
         this.shape1_5.render(f5);
         GlStateManager.enableBlend();
         this.shapeMain_4.render(f5);
         this.shapeMain_5.render(f5);
         this.shapeMain.render(f5);
         this.shapeMain_1.render(f5);
         this.shapeMain_2.render(f5);
         this.shapeMain_3.render(f5);
         GlStateManager.disableBlend();
      } else if (f == 1.0F) {
         this.shapeOver_4.render(f5);
         this.shapeOver_2.render(f5);
         this.shapeOver.render(f5);
         this.shapeOver_1.render(f5);
         this.shapeOver_5.render(f5);
         this.shapeOver_3.render(f5);
      } else {
         this.shapeIn.render(f5);
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
