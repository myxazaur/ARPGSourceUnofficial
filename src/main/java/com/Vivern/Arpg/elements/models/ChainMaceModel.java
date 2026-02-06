package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ChainMaceModel extends ModelBase {
   public ModelRenderer staf;
   public ModelRenderer chainA;
   public ModelRenderer mace1;
   public ModelRenderer chainB;
   public ModelRenderer chainA_1;
   public ModelRenderer chainB_1;
   public ModelRenderer chainA_2;
   public ModelRenderer chainB_2;
   public ModelRenderer chainA_3;
   public ModelRenderer chainB_3;
   public ModelRenderer chainA_4;
   public ModelRenderer chainB_4;
   public ModelRenderer chainA_5;
   public ModelRenderer chainB_5;
   public ModelRenderer chainA_6;
   public ModelRenderer chainB_6;
   public ModelRenderer chainA_7;
   public ModelRenderer chainB_7;
   public ModelRenderer smace1;
   public ModelRenderer smace2;
   public ModelRenderer smace3;
   public ModelRenderer smace4;
   public ModelRenderer mace2;
   public ModelRenderer mace3;
   public ModelRenderer mace4;

   public ChainMaceModel() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      this.chainA_5 = new ModelRenderer(this, 28, 6);
      this.chainA_5.setRotationPoint(0.0F, -4.0F, 0.0F);
      this.chainA_5.addBox(-1.0F, -4.0F, 0.0F, 2, 4, 0, 0.0F);
      this.setRotateAngle(this.chainA_5, 0.8651597F, 0.0F, 0.0F);
      this.chainB_6 = new ModelRenderer(this, 28, 8);
      this.chainB_6.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.chainB_6.addBox(-1.0F, -4.0F, 0.0F, 2, 4, 0, 0.0F);
      this.setRotateAngle(this.chainB_6, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.chainB_4 = new ModelRenderer(this, 28, 0);
      this.chainB_4.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.chainB_4.addBox(-1.0F, -4.0F, 0.0F, 2, 4, 0, 0.0F);
      this.setRotateAngle(this.chainB_4, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.chainA_3 = new ModelRenderer(this, 28, 14);
      this.chainA_3.setRotationPoint(0.0F, -4.0F, 0.0F);
      this.chainA_3.addBox(-1.0F, -4.0F, 0.0F, 2, 4, 0, 0.0F);
      this.setRotateAngle(this.chainA_3, 1.1383038F, 0.0F, 0.0F);
      this.chainA_2 = new ModelRenderer(this, 28, 10);
      this.chainA_2.setRotationPoint(0.0F, -4.0F, 0.0F);
      this.chainA_2.addBox(-1.0F, -4.0F, 0.0F, 2, 4, 0, 0.0F);
      this.setRotateAngle(this.chainA_2, 1.3203416F, 0.0F, 0.0F);
      this.chainB_3 = new ModelRenderer(this, 28, 12);
      this.chainB_3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.chainB_3.addBox(-1.0F, -4.0F, 0.02F, 2, 4, 0, 0.0F);
      this.setRotateAngle(this.chainB_3, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.chainB_2 = new ModelRenderer(this, 28, 8);
      this.chainB_2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.chainB_2.addBox(-1.0F, -4.0F, 0.0F, 2, 4, 0, 0.0F);
      this.setRotateAngle(this.chainB_2, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.chainB_5 = new ModelRenderer(this, 28, 4);
      this.chainB_5.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.chainB_5.addBox(-1.0F, -4.0F, 0.02F, 2, 4, 0, 0.0F);
      this.setRotateAngle(this.chainB_5, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.mace3 = new ModelRenderer(this, 8, 10);
      this.mace3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.mace3.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 0, 0.0F);
      this.chainA = new ModelRenderer(this, 28, 2);
      this.chainA.setRotationPoint(0.0F, 1.0F, 5.0F);
      this.chainA.addBox(-1.0F, -4.0F, 0.0F, 2, 4, 0, 0.0F);
      this.setRotateAngle(this.chainA, -0.22759093F, 0.0F, 0.0F);
      this.mace2 = new ModelRenderer(this, 8, 2);
      this.mace2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.mace2.addBox(0.0F, -4.0F, -4.0F, 0, 8, 8, 0.0F);
      this.chainB = new ModelRenderer(this, 28, 0);
      this.chainB.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.chainB.addBox(-1.0F, -4.0F, 0.0F, 2, 4, 0, 0.0F);
      this.setRotateAngle(this.chainB, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.mace1 = new ModelRenderer(this, 8, 0);
      this.mace1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.mace1.addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5, 0.0F);
      this.smace4 = new ModelRenderer(this, 0, 10);
      this.smace4.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.smace4.addBox(-4.0F, 0.0F, -4.0F, 8, 0, 8, 0.0F);
      this.chainB_1 = new ModelRenderer(this, 28, 4);
      this.chainB_1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.chainB_1.addBox(-1.0F, -4.0F, 0.02F, 2, 4, 0, 0.0F);
      this.setRotateAngle(this.chainB_1, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.chainA_6 = new ModelRenderer(this, 28, 10);
      this.chainA_6.setRotationPoint(0.0F, -4.0F, 0.0F);
      this.chainA_6.addBox(-1.0F, -4.0F, 0.0F, 2, 4, 0, 0.0F);
      this.setRotateAngle(this.chainA_6, -0.7285004F, 0.0F, 0.0F);
      this.smace3 = new ModelRenderer(this, 8, 10);
      this.smace3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.smace3.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 0, 0.0F);
      this.staf = new ModelRenderer(this, 0, 0);
      this.staf.setRotationPoint(0.0F, 6.0F, 5.0F);
      this.staf.addBox(-1.0F, -5.0F, -1.0F, 2, 16, 2, 0.0F);
      this.smace1 = new ModelRenderer(this, 8, 0);
      this.smace1.setRotationPoint(0.0F, -6.0F, 0.0F);
      this.smace1.addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5, 0.0F);
      this.setRotateAngle(this.smace1, 0.0F, (float) (Math.PI / 4), 0.0F);
      this.smace2 = new ModelRenderer(this, 8, 2);
      this.smace2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.smace2.addBox(0.0F, -4.0F, -4.0F, 0, 8, 8, 0.0F);
      this.chainA_4 = new ModelRenderer(this, 28, 2);
      this.chainA_4.setRotationPoint(0.0F, -4.0F, 0.0F);
      this.chainA_4.addBox(-1.0F, -4.0F, 0.0F, 2, 4, 0, 0.0F);
      this.setRotateAngle(this.chainA_4, (float) (Math.PI / 3), 0.0F, 0.0F);
      this.chainA_7 = new ModelRenderer(this, 28, 10);
      this.chainA_7.setRotationPoint(0.0F, -4.0F, 0.0F);
      this.chainA_7.addBox(-1.0F, -4.0F, 0.0F, 2, 4, 0, 0.0F);
      this.setRotateAngle(this.chainA_7, -0.59184116F, 0.0F, 0.0F);
      this.mace4 = new ModelRenderer(this, 0, 10);
      this.mace4.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.mace4.addBox(-4.0F, 0.0F, -4.0F, 8, 0, 8, 0.0F);
      this.chainB_7 = new ModelRenderer(this, 28, 12);
      this.chainB_7.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.chainB_7.addBox(-1.0F, -4.0F, 0.02F, 2, 4, 0, 0.0F);
      this.setRotateAngle(this.chainB_7, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.chainA_1 = new ModelRenderer(this, 28, 6);
      this.chainA_1.setRotationPoint(0.0F, -4.0F, 0.0F);
      this.chainA_1.addBox(-1.0F, -4.0F, 0.0F, 2, 4, 0, 0.0F);
      this.setRotateAngle(this.chainA_1, -0.3642502F, 0.0F, 0.0F);
      this.chainA_4.addChild(this.chainA_5);
      this.chainA_6.addChild(this.chainB_6);
      this.chainA_4.addChild(this.chainB_4);
      this.chainA_2.addChild(this.chainA_3);
      this.chainA_1.addChild(this.chainA_2);
      this.chainA_3.addChild(this.chainB_3);
      this.chainA_2.addChild(this.chainB_2);
      this.chainA_5.addChild(this.chainB_5);
      this.mace1.addChild(this.mace3);
      this.mace1.addChild(this.mace2);
      this.chainA.addChild(this.chainB);
      this.smace1.addChild(this.smace4);
      this.chainA_1.addChild(this.chainB_1);
      this.chainA_5.addChild(this.chainA_6);
      this.smace1.addChild(this.smace3);
      this.chainA_7.addChild(this.smace1);
      this.smace1.addChild(this.smace2);
      this.chainA_3.addChild(this.chainA_4);
      this.chainA_6.addChild(this.chainA_7);
      this.mace1.addChild(this.mace4);
      this.chainA_7.addChild(this.chainB_7);
      this.chainA.addChild(this.chainA_1);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (f == -2.0F) {
         this.chainA.render(f5);
      } else if (f == -1.0F) {
         this.staf.render(f5);
      } else if (f == 0.0F) {
         this.chainA.render(f5);
         this.staf.render(f5);
      } else {
         this.mace1.render(f5);
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
