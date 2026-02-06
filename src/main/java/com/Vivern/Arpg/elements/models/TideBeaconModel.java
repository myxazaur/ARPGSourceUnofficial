package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.AnimationTimer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class TideBeaconModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shapeTbase;
   public ModelRenderer shapeL1;
   public ModelRenderer pearl;
   public ModelRenderer shapeR1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shape5;
   public ModelRenderer shapeT1;
   public ModelRenderer shapeT2;
   public ModelRenderer shapeT3;
   public ModelRenderer shapeL2;
   public ModelRenderer shapeL3;
   public ModelRenderer shapeR2;
   public ModelRenderer shapeR3;
   public boolean isDried = false;

   public TideBeaconModel() {
      this.textureWidth = 64;
      this.textureHeight = 34;
      this.shape5 = new ModelRenderer(this, 39, 0);
      this.shape5.mirror = true;
      this.shape5.setRotationPoint(0.0F, 7.5F, 0.0F);
      this.shape5.addBox(-5.0F, -10.0F, 0.0F, 10, 10, 0, 0.0F);
      this.setRotateAngle(this.shape5, 0.3642502F, -2.3675392F, -0.22759093F);
      this.shapeR2 = new ModelRenderer(this, 60, 10);
      this.shapeR2.setRotationPoint(0.1F, -10.5F, -1.5F);
      this.shapeR2.addBox(-0.5F, -11.0F, -1.0F, 1, 11, 1, 0.0F);
      this.setRotateAngle(this.shapeR2, -0.91053826F, 0.0F, 0.0F);
      this.pearl = new ModelRenderer(this, 48, 18);
      this.pearl.setRotationPoint(0.0F, -8.0F, 0.0F);
      this.pearl.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 4, 0.0F);
      this.shapeR1 = new ModelRenderer(this, 10, 7);
      this.shapeR1.setRotationPoint(0.0F, 16.0F, -3.0F);
      this.shapeR1.addBox(-0.5F, -11.0F, -3.0F, 1, 11, 2, 0.0F);
      this.setRotateAngle(this.shapeR1, 0.7285004F, 0.0F, 0.0F);
      this.shapeL1 = new ModelRenderer(this, 0, 6);
      this.shapeL1.setRotationPoint(0.0F, 16.0F, 3.0F);
      this.shapeL1.addBox(-0.5F, -16.0F, 0.0F, 1, 16, 3, 0.0F);
      this.setRotateAngle(this.shapeL1, -0.4553564F, 0.0F, 0.0F);
      this.shapeR3 = new ModelRenderer(this, 54, 7);
      this.shapeR3.setRotationPoint(0.1F, -10.5F, -0.5F);
      this.shapeR3.addBox(0.0F, 0.0F, 0.0F, 0, 8, 3, 0.0F);
      this.setRotateAngle(this.shapeR3, 2.5953045F, 0.0F, 0.0F);
      this.shapeT2 = new ModelRenderer(this, 15, 0);
      this.shapeT2.setRotationPoint(0.0F, -5.5F, 0.0F);
      this.shapeT2.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
      this.setRotateAngle(this.shapeT2, 0.0F, 0.0F, -0.27314404F);
      this.shape3 = new ModelRenderer(this, 35, 0);
      this.shape3.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shape3.addBox(-7.0F, -10.0F, 0.0F, 14, 10, 0, 0.0F);
      this.setRotateAngle(this.shape3, 0.0F, -0.7740535F, 0.0F);
      this.shape4 = new ModelRenderer(this, 35, 0);
      this.shape4.setRotationPoint(0.0F, 10.0F, 0.0F);
      this.shape4.addBox(-7.0F, -10.0F, 0.0F, 14, 10, 0, 0.0F);
      this.setRotateAngle(this.shape4, 0.3642502F, 0.27314404F, 0.0F);
      this.shapeL2 = new ModelRenderer(this, 10, 7);
      this.shapeL2.setRotationPoint(0.1F, -15.5F, 0.5F);
      this.shapeL2.addBox(-0.5F, -16.0F, 0.0F, 1, 16, 2, 0.0F);
      this.setRotateAngle(this.shapeL2, 0.63739425F, 0.0F, 0.0F);
      this.shape2 = new ModelRenderer(this, 35, 0);
      this.shape2.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shape2.addBox(-7.0F, -10.0F, 0.0F, 14, 10, 0, 0.0F);
      this.setRotateAngle(this.shape2, 0.0F, 0.7740535F, 0.0F);
      this.shapeT3 = new ModelRenderer(this, 11, 0);
      this.shapeT3.setRotationPoint(0.0F, -5.5F, 0.0F);
      this.shapeT3.addBox(-0.5F, -6.0F, -0.5F, 1, 6, 1, 0.0F);
      this.setRotateAngle(this.shapeT3, 0.0F, 0.0F, -0.31869712F);
      this.shapeT1 = new ModelRenderer(this, 23, 0);
      this.shapeT1.setRotationPoint(-6.0F, -2.5F, 0.0F);
      this.shapeT1.addBox(-1.5F, -6.0F, -1.5F, 3, 6, 3, 0.0F);
      this.setRotateAngle(this.shapeT1, 0.0F, 0.0F, (float) (-Math.PI / 3));
      this.shapeTbase = new ModelRenderer(this, 0, 0);
      this.shapeTbase.setRotationPoint(0.0F, 20.0F, 0.0F);
      this.shapeTbase.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 10);
      this.shape1.setRotationPoint(-8.0F, 16.0F, -8.0F);
      this.shape1.addBox(0.0F, 0.0F, 0.0F, 16, 8, 16, 0.0F);
      this.shapeL3 = new ModelRenderer(this, 48, 7);
      this.shapeL3.setRotationPoint(0.1F, -15.5F, 0.5F);
      this.shapeL3.addBox(0.0F, -8.0F, 0.0F, 0, 8, 3, 0.0F);
      this.setRotateAngle(this.shapeL3, 1.6390387F, 0.0F, 0.0F);
      this.shapeR1.addChild(this.shapeR2);
      this.shapeR2.addChild(this.shapeR3);
      this.shapeT1.addChild(this.shapeT2);
      this.shapeL1.addChild(this.shapeL2);
      this.shapeT2.addChild(this.shapeT3);
      this.shapeTbase.addChild(this.shapeT1);
      this.shapeL2.addChild(this.shapeL3);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      float timer = this.isDried ? 0.0F : AnimationTimer.tick;
      this.shapeL1.rotateAngleX = -0.4553564F + MathHelper.sin(timer / 20.0F) * 0.18F - 0.14F;
      this.shapeL2.rotateAngleX = 0.63739425F + MathHelper.sin((timer - 10.0F) / 20.0F) * 0.18F - 0.14F;
      this.shapeL3.rotateAngleX = 1.6390387F + MathHelper.sin((timer - 20.0F) / 20.0F) * 0.28F - 0.2F;
      this.shapeL1.rotateAngleY = f1;
      this.shapeL1.rotateAngleZ = f2;
      this.shapeR1.rotateAngleX = 0.7285004F - MathHelper.sin((timer - 5.0F) / 20.0F) * 0.18F + 0.12F;
      this.shapeR2.rotateAngleX = -0.91053826F - MathHelper.sin((timer - 15.0F) / 20.0F) * 0.18F + 0.12F;
      this.shapeR3.rotateAngleX = 2.5953045F - MathHelper.sin((timer - 25.0F) / 20.0F) * 0.28F + 0.22F;
      this.shapeR1.rotateAngleY = f3;
      this.shapeR1.rotateAngleZ = f4;
      float r1 = AnimationTimer.rainbow1;
      float r2 = AnimationTimer.rainbow2;
      this.setRotateAngle(this.pearl, r1 * 0.17F, timer * 0.05F, r2 * 0.115F);
      this.shape4.rotateAngleX = 0.3642502F + MathHelper.sin(AnimationTimer.tick / 50.0F) * 0.14F;
      this.shape5.rotateAngleX = 0.3642502F + MathHelper.sin((AnimationTimer.tick + 16) / 50.0F) * 0.13F;
      this.shape5.render(f5);
      this.shapeR1.render(f5);
      this.shapeL1.render(f5);
      this.shape3.render(f5);
      this.shape4.render(f5);
      this.shape2.render(f5);
      int tentackles = (int)f;
      float oneangle = (float)((Math.PI * 2) / tentackles);

      for (int i = 0; i < tentackles; i++) {
         float offset = MathHelper.sin(timer / 50.0F + i) * 0.2F;
         float offsetX1 = MathHelper.sin(-timer / 40.0F + i * 1.678F) * 0.25F;
         float offsetX2 = MathHelper.sin((-timer + 30.0F) / 40.0F + i * 1.678F) * 0.26F;
         float offsetX3 = MathHelper.sin((-timer + 60.0F) / 40.0F + i * 1.678F) * 0.27F;
         this.shapeT1.rotateAngleX = offset;
         this.shapeT1.rotateAngleZ = (float) (-Math.PI / 3) + offsetX1;
         this.shapeT2.rotateAngleZ = -0.27314404F + offsetX2;
         this.shapeT3.rotateAngleZ = -0.31869712F + offsetX3;
         this.shapeTbase.rotateAngleY = i * oneangle;
         this.shapeTbase.render(f5);
      }

      this.shape1.render(f5);
      AbstractMobModel.light(90, true);
      this.pearl.render(f5);
      AbstractMobModel.returnlight();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
