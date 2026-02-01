package com.vivern.arpg.elements.models;

import com.vivern.arpg.renders.ModelRendererGlow;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class CrystalCutterModel extends ModelBase {
   public ModelRenderer shapess1;
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape5;
   public ModelRenderer shape6;
   public ModelRenderer shape7;
   public ModelRenderer shape8;
   public ModelRenderer shapeglow1;
   public ModelRenderer ammo;
   public ModelRenderer shapetr;
   public ModelRenderer shapess3;
   public ModelRenderer shapess2;
   public ModelRenderer shape4;
   public ModelRenderer shape6_1;
   public ModelRenderer shape7_1;
   public ModelRenderer shape8_1;
   public ModelRenderer shapeglow1_1;
   public ModelRenderer ammo1;
   public ModelRenderer ammo2;
   public ModelRenderer ammo3;

   public CrystalCutterModel() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      this.shape8_1 = new ModelRenderer(this, 13, 15);
      this.shape8_1.setRotationPoint(-4.0F, 11.0F, -5.0F);
      this.shape8_1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
      this.shape5 = new ModelRenderer(this, 0, 0);
      this.shape5.setRotationPoint(1.0F, 9.0F, -8.0F);
      this.shape5.addBox(0.0F, 0.0F, -1.0F, 2, 12, 1, 0.0F);
      this.setRotateAngle(this.shape5, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.ammo1 = new ModelRendererGlow(this, 20, 0, 180, false);
      this.ammo1.setRotationPoint(2.2F, 0.5F, 0.5F);
      this.ammo1.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.setRotateAngle(this.ammo1, -0.3642502F, 0.4553564F, -0.95609134F);
      this.shape1 = new ModelRenderer(this, 0, 21);
      this.shape1.setRotationPoint(-2.0F, 7.0F, 3.0F);
      this.shape1.addBox(0.0F, 0.0F, 0.0F, 4, 3, 6, 0.0F);
      this.shape7_1 = new ModelRenderer(this, 18, 15);
      this.shape7_1.mirror = true;
      this.shape7_1.setRotationPoint(-3.0F, 11.0F, -8.0F);
      this.shape7_1.addBox(0.0F, 0.0F, 0.0F, 2, 1, 5, 0.0F);
      this.shape8 = new ModelRenderer(this, 13, 15);
      this.shape8.setRotationPoint(3.0F, 11.0F, -5.0F);
      this.shape8.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
      this.shapess3 = new ModelRenderer(this, 0, 15);
      this.shapess3.setRotationPoint(0.0F, 2.0F, 1.95F);
      this.shapess3.addBox(-0.5F, 0.0F, -1.0F, 1, 1, 5, 0.0F);
      this.shape2 = new ModelRenderer(this, 13, 23);
      this.shape2.mirror = true;
      this.shape2.setRotationPoint(-5.0F, 7.5F, -1.0F);
      this.shape2.addBox(0.0F, 0.0F, 0.0F, 3, 2, 7, 0.0F);
      this.shape6 = new ModelRenderer(this, 7, 15);
      this.shape6.setRotationPoint(1.0F, 10.0F, -8.0F);
      this.shape6.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
      this.shapeglow1 = new ModelRenderer(this, 6, 0);
      this.shapeglow1.setRotationPoint(4.0F, 10.5F, -5.5F);
      this.shapeglow1.addBox(0.0F, 0.0F, 0.0F, 1, 2, 3, 0.0F);
      this.ammo2 = new ModelRenderer(this, 14, 2);
      this.ammo2.setRotationPoint(5.0F, 0.0F, 0.0F);
      this.ammo2.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
      this.shape3 = new ModelRenderer(this, 13, 23);
      this.shape3.setRotationPoint(2.0F, 7.5F, -1.0F);
      this.shape3.addBox(0.0F, 0.0F, 0.0F, 3, 2, 7, 0.0F);
      this.ammo = new ModelRenderer(this, 14, 0);
      this.ammo.setRotationPoint(-3.5F, 8.0F, 2.0F);
      this.ammo.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
      this.shape7 = new ModelRenderer(this, 18, 15);
      this.shape7.setRotationPoint(1.0F, 11.0F, -8.0F);
      this.shape7.addBox(0.0F, 0.0F, 0.0F, 2, 1, 5, 0.0F);
      this.shape4 = new ModelRenderer(this, 0, 0);
      this.shape4.mirror = true;
      this.shape4.setRotationPoint(-3.0F, 9.0F, -8.0F);
      this.shape4.addBox(0.0F, 0.0F, -1.0F, 2, 12, 1, 0.0F);
      this.setRotateAngle(this.shape4, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.shape6_1 = new ModelRenderer(this, 7, 15);
      this.shape6_1.mirror = true;
      this.shape6_1.setRotationPoint(-3.0F, 10.0F, -8.0F);
      this.shape6_1.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
      this.ammo3 = new ModelRendererGlow(this, 20, 2, 180, false);
      this.ammo3.setRotationPoint(-0.2F, 0.5F, 0.5F);
      this.ammo3.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.setRotateAngle(this.ammo3, 1.9123572F, -0.7740535F, -0.8651597F);
      this.shapess1 = new ModelRenderer(this, 24, 0);
      this.shapess1.setRotationPoint(0.0F, 1.0F, 4.5F);
      this.shapess1.addBox(-0.5F, 1.3F, -1.0F, 1, 5, 2, 0.0F);
      this.setRotateAngle(this.shapess1, 0.18203785F, 0.0F, 0.0F);
      this.shapess2 = new ModelRenderer(this, 14, 19);
      this.shapess2.setRotationPoint(0.0F, 1.0F, 2.4F);
      this.shapess2.addBox(-0.5F, 1.3F, -1.0F, 1, 6, 1, 0.0F);
      this.setRotateAngle(this.shapess2, -0.18203785F, 0.0F, 0.0F);
      this.shapetr = new ModelRenderer(this, 0, 5);
      this.shapetr.setRotationPoint(0.0F, 7.48F, -6.5F);
      this.shapetr.addBox(-4.0F, 0.0F, 0.0F, 8, 2, 8, 0.0F);
      this.shapeglow1_1 = new ModelRenderer(this, 6, 0);
      this.shapeglow1_1.setRotationPoint(-5.0F, 10.5F, -5.5F);
      this.shapeglow1_1.addBox(0.0F, 0.0F, 0.0F, 1, 2, 3, 0.0F);
      this.ammo.addChild(this.ammo1);
      this.ammo.addChild(this.ammo2);
      this.ammo2.addChild(this.ammo3);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.disableCull();
      this.shape8_1.render(f5);
      this.shape5.render(f5);
      this.shape1.render(f5);
      this.shape7_1.render(f5);
      this.shape8.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapess3.offsetX, this.shapess3.offsetY, this.shapess3.offsetZ);
      GlStateManager.translate(this.shapess3.rotationPointX * f5, this.shapess3.rotationPointY * f5, this.shapess3.rotationPointZ * f5);
      GlStateManager.scale(1.2, 1.0, 1.0);
      GlStateManager.translate(-this.shapess3.offsetX, -this.shapess3.offsetY, -this.shapess3.offsetZ);
      GlStateManager.translate(-this.shapess3.rotationPointX * f5, -this.shapess3.rotationPointY * f5, -this.shapess3.rotationPointZ * f5);
      this.shapess3.render(f5);
      GlStateManager.popMatrix();
      this.shape2.render(f5);
      this.shape6.render(f5);
      this.shape3.render(f5);

      for (int i = 0; i < f1; i++) {
         this.ammo.offsetZ = -1.5F * i - 1.5F * f2;
         this.ammo.render(f5);
      }

      this.shape7.render(f5);
      this.shape4.render(f5);
      this.shape6_1.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapess1.offsetX, this.shapess1.offsetY, this.shapess1.offsetZ);
      GlStateManager.translate(this.shapess1.rotationPointX * f5, this.shapess1.rotationPointY * f5, this.shapess1.rotationPointZ * f5);
      GlStateManager.scale(1.1, 1.0, 1.2);
      GlStateManager.translate(-this.shapess1.offsetX, -this.shapess1.offsetY, -this.shapess1.offsetZ);
      GlStateManager.translate(-this.shapess1.rotationPointX * f5, -this.shapess1.rotationPointY * f5, -this.shapess1.rotationPointZ * f5);
      this.shapess1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapess2.offsetX, this.shapess2.offsetY, this.shapess2.offsetZ);
      GlStateManager.translate(this.shapess2.rotationPointX * f5, this.shapess2.rotationPointY * f5, this.shapess2.rotationPointZ * f5);
      GlStateManager.scale(1.1, 1.0, 1.2);
      GlStateManager.translate(-this.shapess2.offsetX, -this.shapess2.offsetY, -this.shapess2.offsetZ);
      GlStateManager.translate(-this.shapess2.rotationPointX * f5, -this.shapess2.rotationPointY * f5, -this.shapess2.rotationPointZ * f5);
      this.shapess2.render(f5);
      GlStateManager.popMatrix();
      AbstractMobModel.light(180, false);
      if (f == 0.0F) {
         this.shapetr.render(f5);
      }

      this.shapeglow1.render(f5);
      this.shapeglow1_1.render(f5);
      AbstractMobModel.returnlight();
      GlStateManager.enableCull();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
