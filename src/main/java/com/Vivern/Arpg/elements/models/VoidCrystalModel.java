package com.Vivern.Arpg.elements.models;

import java.util.Random;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class VoidCrystalModel extends ModelBase {
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

   public VoidCrystalModel() {
      this.textureWidth = 16;
      this.textureHeight = 16;
      this.shape10 = new ModelRenderer(this, 0, 0);
      this.shape10.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shape10.addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F);
      this.shape7 = new ModelRenderer(this, 6, 0);
      this.shape7.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shape7.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
      this.shape5 = new ModelRenderer(this, 4, 0);
      this.shape5.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shape5.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
      this.shape9 = new ModelRenderer(this, 8, 0);
      this.shape9.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shape9.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
      this.shape4 = new ModelRenderer(this, 3, 0);
      this.shape4.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shape4.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, 0.0F);
      this.shape8 = new ModelRenderer(this, 7, 0);
      this.shape8.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shape8.addBox(-1.0F, 0.0F, -1.0F, 2, 7, 2, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shape1.addBox(-1.0F, 0.0F, -1.0F, 2, 14, 2, 0.0F);
      this.shape3 = new ModelRenderer(this, 2, 0);
      this.shape3.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shape3.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, 0.0F);
      this.shape6 = new ModelRenderer(this, 5, 0);
      this.shape6.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shape6.addBox(-1.0F, 0.0F, -1.0F, 2, 9, 2, 0.0F);
      this.shape2 = new ModelRenderer(this, 1, 0);
      this.shape2.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shape2.addBox(-1.0F, 0.0F, -1.0F, 2, 13, 2, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      AbstractMobModel.light(180, false);
      Random rand = new Random((long)f);

      for (int i = 0; i < 2; i++) {
         this.setRotateAngle(this.shape1, rand.nextFloat() * 6.283185F, rand.nextFloat() * 6.283185F, rand.nextFloat() * 6.283185F);
         this.setRotateAngle(this.shape2, rand.nextFloat() * 6.283185F, rand.nextFloat() * 6.283185F, rand.nextFloat() * 6.283185F);
         this.setRotateAngle(this.shape3, rand.nextFloat() * 6.283185F, rand.nextFloat() * 6.283185F, rand.nextFloat() * 6.283185F);
         this.setRotateAngle(this.shape4, rand.nextFloat() * 6.283185F, rand.nextFloat() * 6.283185F, rand.nextFloat() * 6.283185F);
         this.setRotateAngle(this.shape5, rand.nextFloat() * 6.283185F, rand.nextFloat() * 6.283185F, rand.nextFloat() * 6.283185F);
         this.setRotateAngle(this.shape6, rand.nextFloat() * 6.283185F, rand.nextFloat() * 6.283185F, rand.nextFloat() * 6.283185F);
         this.setRotateAngle(this.shape7, rand.nextFloat() * 6.283185F, rand.nextFloat() * 6.283185F, rand.nextFloat() * 6.283185F);
         this.setRotateAngle(this.shape8, rand.nextFloat() * 6.283185F, rand.nextFloat() * 6.283185F, rand.nextFloat() * 6.283185F);
         this.setRotateAngle(this.shape9, rand.nextFloat() * 6.283185F, rand.nextFloat() * 6.283185F, rand.nextFloat() * 6.283185F);
         this.setRotateAngle(this.shape10, rand.nextFloat() * 6.283185F, rand.nextFloat() * 6.283185F, rand.nextFloat() * 6.283185F);
         this.shape10.render(f5);
         this.shape7.render(f5);
         this.shape5.render(f5);
         this.shape9.render(f5);
         this.shape4.render(f5);
         this.shape8.render(f5);
         this.shape1.render(f5);
         this.shape3.render(f5);
         this.shape6.render(f5);
         this.shape2.render(f5);
      }

      AbstractMobModel.returnlight();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
