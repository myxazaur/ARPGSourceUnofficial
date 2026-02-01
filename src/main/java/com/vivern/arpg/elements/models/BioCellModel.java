package com.vivern.arpg.elements.models;

import com.vivern.arpg.main.AnimationTimer;
import java.util.Random;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class BioCellModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer cell;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer pipe;
   public ModelRenderer pipe_1;
   public ModelRenderer pipe_2;
   public ModelRenderer pipe_3;
   public ModelRenderer pipe_4;
   public ModelRenderer pipe_5;
   public ModelRenderer pipe_6;
   public ModelRenderer pipe_7;
   public ModelRenderer fluid;
   public ModelRenderer mutate1;
   public ModelRenderer mutate2;
   public ModelRenderer mutate3;
   public ModelRenderer mutate4;

   public BioCellModel() {
      this.textureWidth = 128;
      this.textureHeight = 64;
      this.mutate3 = new ModelRenderer(this, 33, 0);
      this.mutate3.setRotationPoint(0.0F, 4.0F, 0.0F);
      this.mutate3.addBox(-0.5F, -3.5F, -0.5F, 1, 4, 1, 0.0F);
      this.mutate2 = new ModelRenderer(this, 30, 11);
      this.mutate2.setRotationPoint(0.0F, 4.0F, 0.0F);
      this.mutate2.addBox(-1.0F, -5.0F, -1.0F, 2, 6, 2, 0.0F);
      this.mutate4 = new ModelRenderer(this, 28, 0);
      this.mutate4.setRotationPoint(0.0F, -3.5F, 0.0F);
      this.mutate4.addBox(-0.5F, -4.0F, -0.5F, 1, 4, 1, 0.0F);
      this.shape1 = new ModelRenderer(this, 39, 0);
      this.shape1.setRotationPoint(0.0F, 22.0F, 0.0F);
      this.shape1.addBox(-8.0F, 0.0F, -8.0F, 16, 2, 16, 0.0F);
      this.cell = new ModelRenderer(this, 0, 20);
      this.cell.setRotationPoint(0.0F, -8.0F, 0.0F);
      this.cell.addBox(-7.0F, 0.0F, -7.0F, 14, 30, 14, 0.0F);
      this.shape2 = new ModelRenderer(this, 87, 0);
      this.shape2.setRotationPoint(0.0F, -10.0F, 0.0F);
      this.shape2.addBox(-5.0F, 0.0F, -5.0F, 10, 2, 10, 0.0F);
      this.pipe_4 = new ModelRenderer(this, 0, 0);
      this.pipe_4.setRotationPoint(0.0F, 20.0F, 0.0F);
      this.pipe_4.addBox(8.0F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
      this.shape3 = new ModelRenderer(this, 43, 20);
      this.shape3.setRotationPoint(0.0F, -11.0F, 0.0F);
      this.shape3.addBox(-3.0F, 0.0F, -3.0F, 6, 1, 6, 0.0F);
      this.pipe_5 = new ModelRenderer(this, 13, 5);
      this.pipe_5.setRotationPoint(0.0F, 17.0F, 0.0F);
      this.pipe_5.addBox(-11.0F, 0.0F, -1.5F, 6, 3, 3, 0.0F);
      this.fluid = new ModelRenderer(this, 56, 22);
      this.fluid.setRotationPoint(0.0F, -7.5F, 0.0F);
      this.fluid.addBox(-6.5F, 0.0F, -6.5F, 13, 29, 13, 0.0F);
      this.pipe_1 = new ModelRenderer(this, 0, 0);
      this.pipe_1.setRotationPoint(0.0F, 20.0F, 0.0F);
      this.pipe_1.addBox(-1.5F, 0.0F, -11.0F, 3, 4, 3, 0.0F);
      this.pipe_2 = new ModelRenderer(this, 0, 10);
      this.pipe_2.setRotationPoint(0.0F, 17.0F, 0.0F);
      this.pipe_2.addBox(-1.5F, 0.0F, -11.0F, 3, 3, 6, 0.0F);
      this.pipe = new ModelRenderer(this, 109, 34);
      this.pipe.setRotationPoint(0.0F, 17.0F, 0.0F);
      this.pipe.addBox(-1.5F, 0.0F, 5.0F, 3, 3, 6, 0.0F);
      this.mutate1 = new ModelRenderer(this, 38, 0);
      this.mutate1.setRotationPoint(0.0F, 4.0F, 0.0F);
      this.mutate1.addBox(-2.0F, -3.0F, -2.0F, 4, 4, 4, 0.0F);
      this.pipe_6 = new ModelRenderer(this, 0, 0);
      this.pipe_6.setRotationPoint(0.0F, 20.0F, 0.0F);
      this.pipe_6.addBox(-11.0F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
      this.pipe_3 = new ModelRenderer(this, 0, 0);
      this.pipe_3.setRotationPoint(0.0F, 20.0F, 0.0F);
      this.pipe_3.addBox(-1.5F, 0.0F, 8.0F, 3, 4, 3, 0.0F);
      this.shape4 = new ModelRenderer(this, 95, 23);
      this.shape4.setRotationPoint(0.0F, 19.0F, 0.0F);
      this.shape4.addBox(-4.0F, 0.0F, -4.0F, 8, 3, 8, 0.0F);
      this.pipe_7 = new ModelRenderer(this, 109, 44);
      this.pipe_7.setRotationPoint(0.0F, 17.0F, 0.0F);
      this.pipe_7.addBox(5.0F, 0.0F, -1.5F, 6, 3, 3, 0.0F);
      this.mutate3.addChild(this.mutate4);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      Random rand = new Random((long)f);
      int count1 = rand.nextInt(4);
      int count2 = rand.nextInt(5);
      int count3 = rand.nextInt(4);
      float scl = rand.nextFloat() * 0.4F + 0.5F;
      GlStateManager.pushMatrix();
      GlStateManager.scale(scl, scl, scl);
      GlStateManager.translate(0.0F, MathHelper.sin((AnimationTimer.tick + f) / 70.0F) / 4.0F, 0.0F);

      for (int i = 0; i < count1; i++) {
         this.mutate1.rotateAngleX = rand.nextFloat() * 6.0F;
         this.mutate1.rotateAngleY = rand.nextFloat() * 6.0F;
         this.mutate1.rotateAngleZ = rand.nextFloat() * 6.0F;
         this.mutate1.offsetX = rand.nextFloat() / 10.0F;
         this.mutate1.offsetY = rand.nextFloat() / 10.0F;
         this.mutate1.offsetZ = rand.nextFloat() / 10.0F;
         this.mutate1.render(f5);
      }

      for (int i = 0; i < count2; i++) {
         this.mutate2.rotateAngleX = rand.nextFloat() * 6.0F;
         this.mutate2.rotateAngleY = rand.nextFloat() * 6.0F;
         this.mutate2.rotateAngleZ = rand.nextFloat() * 6.0F;
         this.mutate2.render(f5);
      }

      for (int i = 0; i < count3; i++) {
         this.mutate3.rotateAngleX = rand.nextFloat() * 6.0F;
         this.mutate3.rotateAngleY = rand.nextFloat() * 6.0F;
         this.mutate3.rotateAngleZ = rand.nextFloat() * 6.0F;
         this.mutate4.rotateAngleX = rand.nextFloat() / 2.0F + 0.4F;
         this.mutate4.rotateAngleY = rand.nextFloat() * 6.0F;
         this.mutate3.render(f5);
      }

      GlStateManager.popMatrix();
      this.shape1.render(f5);
      this.shape2.render(f5);
      this.pipe_4.render(f5);
      this.shape3.render(f5);
      this.pipe_5.render(f5);
      this.pipe_1.render(f5);
      this.pipe_2.render(f5);
      this.pipe.render(f5);
      this.pipe_6.render(f5);
      this.pipe_3.render(f5);
      this.shape4.render(f5);
      this.pipe_7.render(f5);
      AbstractMobModel.light(180, true);
      this.fluid.render(f5);
      AbstractMobModel.returnlight();
      this.cell.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
