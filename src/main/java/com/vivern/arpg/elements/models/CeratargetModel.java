package com.vivern.arpg.elements.models;

import com.vivern.arpg.main.AnimationTimer;
import com.vivern.arpg.main.GetMOP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class CeratargetModel extends ModelBase {
   public ModelRenderer shapess1;
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer pearl;
   public ModelRenderer shapeleft;
   public ModelRenderer shaperight;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shapea;
   public ModelRenderer shapeb;
   public ModelRenderer shapef1;
   public ModelRenderer shapef2;
   public ModelRenderer shapef3;
   public ModelRenderer shapef4;
   public ModelRenderer shapef5;
   public ModelRenderer shapef6;
   public ModelRenderer shapef7;
   public ModelRenderer rocket1;
   public ModelRenderer rocket3;
   public ModelRenderer pearl2;
   public ModelRenderer spike1;
   public ModelRenderer spike2;
   public ModelRenderer rocket2;
   public ModelRenderer rocket4;

   public CeratargetModel() {
      this.textureWidth = 52;
      this.textureHeight = 32;
      this.shapef3 = new ModelRenderer(this, 22, 20);
      this.shapef3.setRotationPoint(1.5F, 0.5F, -5.0F);
      this.shapef3.addBox(-1.0F, -0.5F, 0.0F, 5, 0, 9, 0.0F);
      this.setRotateAngle(this.shapef3, 0.0F, 0.0F, 0.18203785F);
      this.rocket1 = new ModelRenderer(this, 0, 24);
      this.rocket1.setRotationPoint(-2.5F, -1.5F, -11.2F);
      this.rocket1.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 7, 0.0F);
      this.spike1 = new ModelRenderer(this, 35, 23);
      this.spike1.setRotationPoint(5.0F, 3.0F, 3.0F);
      this.spike1.addBox(0.0F, 0.0F, 0.0F, 4, 0, 8, 0.0F);
      this.setRotateAngle(this.spike1, 0.0F, 0.0F, 0.31869712F);
      this.shape4 = new ModelRenderer(this, 25, 9);
      this.shape4.setRotationPoint(-1.0F, 3.0F, -4.5F);
      this.shape4.addBox(0.0F, -0.1F, -0.4F, 2, 11, 1, 0.0F);
      this.setRotateAngle(this.shape4, 1.4570009F, 0.0F, 0.0F);
      this.shapeleft = new ModelRenderer(this, 0, 0);
      this.shapeleft.setRotationPoint(1.5F, -2.0F, -10.0F);
      this.shapeleft.addBox(0.0F, -0.5F, 0.0F, 5, 5, 5, 0.0F);
      this.pearl = new ModelRenderer(this, 41, 4);
      this.pearl.setRotationPoint(0.0F, -1.0F, 4.0F);
      this.pearl.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
      this.shapef7 = new ModelRenderer(this, 7, 23);
      this.shapef7.setRotationPoint(0.0F, -0.5F, 9.0F);
      this.shapef7.addBox(-3.5F, -0.5F, 0.0F, 7, 0, 9, 0.0F);
      this.pearl2 = new ModelRenderer(this, 41, 0);
      this.pearl2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.pearl2.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.pearl2, -0.7740535F, -0.3642502F, 0.7285004F);
      this.shapess1 = new ModelRenderer(this, 41, 8);
      this.shapess1.setRotationPoint(0.0F, 1.0F, 4.0F);
      this.shapess1.addBox(-0.5F, 0.5F, -1.0F, 1, 7, 2, 0.0F);
      this.setRotateAngle(this.shapess1, 0.18203785F, 0.0F, 0.0F);
      this.shapef6 = new ModelRenderer(this, 0, -2);
      this.shapef6.setRotationPoint(-4.0F, -2.0F, -5.0F);
      this.shapef6.addBox(0.0F, -0.5F, 0.0F, 0, 4, 12, 0.0F);
      this.setRotateAngle(this.shapef6, 0.0F, 0.22759093F, 0.0F);
      this.shape3 = new ModelRenderer(this, 0, 14);
      this.shape3.setRotationPoint(-1.5F, -1.0F, -8.0F);
      this.shape3.addBox(0.0F, -0.5F, 0.0F, 3, 3, 5, 0.0F);
      this.setRotateAngle(this.shape3, -0.4553564F, 0.0F, 0.0F);
      this.rocket3 = new ModelRenderer(this, 0, 24);
      this.rocket3.setRotationPoint(2.5F, -1.5F, -11.2F);
      this.rocket3.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 7, 0.0F);
      this.shape1 = new ModelRenderer(this, 33, 23);
      this.shape1.setRotationPoint(0.0F, -1.5F, 7.0F);
      this.shape1.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 8, 0.0F);
      this.spike2 = new ModelRenderer(this, 35, 23);
      this.spike2.mirror = true;
      this.spike2.setRotationPoint(-5.0F, 3.0F, 3.0F);
      this.spike2.addBox(-4.0F, 0.0F, 0.0F, 4, 0, 8, 0.0F);
      this.setRotateAngle(this.spike2, 0.0F, 0.0F, -0.31869712F);
      this.shapef5 = new ModelRenderer(this, 0, -2);
      this.shapef5.setRotationPoint(4.0F, -2.0F, -5.0F);
      this.shapef5.addBox(0.0F, -0.5F, 0.0F, 0, 4, 12, 0.0F);
      this.setRotateAngle(this.shapef5, 0.0F, -0.22759093F, 0.0F);
      this.rocket4 = new ModelRenderer(this, 0, 0);
      this.rocket4.mirror = true;
      this.rocket4.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.rocket4.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.setRotateAngle(this.rocket4, 0.7740535F, -0.63739425F, 0.0F);
      this.shapeb = new ModelRenderer(this, 11, 14);
      this.shapeb.setRotationPoint(-0.5F, 0.0F, -16.0F);
      this.shapeb.addBox(0.0F, -0.5F, 0.0F, 1, 1, 4, 0.0F);
      this.shapef1 = new ModelRenderer(this, 11, 0);
      this.shapef1.setRotationPoint(1.5F, -1.0F, -5.0F);
      this.shapef1.addBox(-1.0F, -0.5F, 0.0F, 5, 0, 9, 0.0F);
      this.setRotateAngle(this.shapef1, 0.0F, 0.0F, -0.091106184F);
      this.shape2 = new ModelRenderer(this, 42, 18);
      this.shape2.setRotationPoint(-1.5F, -2.0F, 6.0F);
      this.shape2.addBox(0.0F, -0.5F, 0.0F, 3, 3, 1, 0.0F);
      this.shapef4 = new ModelRenderer(this, 22, 10);
      this.shapef4.setRotationPoint(-1.5F, 1.0F, -5.0F);
      this.shapef4.addBox(-4.0F, -0.5F, 0.0F, 5, 0, 9, 0.0F);
      this.setRotateAngle(this.shapef4, 0.0F, 0.0F, -0.13665928F);
      this.shaperight = new ModelRenderer(this, 0, 0);
      this.shaperight.mirror = true;
      this.shaperight.setRotationPoint(-1.5F, -2.0F, -10.0F);
      this.shaperight.addBox(-5.0F, -0.5F, 0.0F, 5, 5, 5, 0.0F);
      this.shapea = new ModelRenderer(this, 17, 17);
      this.shapea.setRotationPoint(-1.0F, -0.5F, -12.0F);
      this.shapea.addBox(0.0F, -0.5F, 0.0F, 2, 2, 4, 0.0F);
      this.rocket2 = new ModelRenderer(this, 0, 0);
      this.rocket2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.rocket2.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.setRotateAngle(this.rocket2, 0.7740535F, 0.63739425F, 0.0F);
      this.shapef2 = new ModelRenderer(this, 22, 0);
      this.shapef2.setRotationPoint(-1.5F, -0.5F, -5.0F);
      this.shapef2.addBox(-4.0F, -0.5F, 0.0F, 5, 0, 9, 0.0F);
      this.setRotateAngle(this.shapef2, 0.0F, 0.0F, 0.18203785F);
      this.shapeleft.addChild(this.spike1);
      this.pearl.addChild(this.pearl2);
      this.shaperight.addChild(this.spike2);
      this.rocket3.addChild(this.rocket4);
      this.rocket1.addChild(this.rocket2);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (f3 == 0.0F) {
         float ft1 = (GetMOP.getfromto(f1, 0.0F, 0.04F) - GetMOP.getfromto(f1, 0.92F, 0.96F)) * 1.25F;
         float ft2 = (GetMOP.getfromto(f1, 0.04F, 0.08F) - GetMOP.getfromto(f1, 0.96F, 1.0F)) * 2.0F;
         float ft3 = GetMOP.getfromto(f1, 0.0F, 0.2F) - GetMOP.getfromto(f1, 0.3F, 0.55F) + 1.0F;
         if (f1 == 0.0F) {
            ft3 = 2.0F;
         }

         this.shapeleft.offsetX = -ft1;
         this.shapeleft.offsetZ = ft2;
         this.shaperight.offsetX = ft1;
         this.shaperight.offsetZ = ft2;
         this.spike1.isHidden = f == 1.0F;
         this.spike2.isHidden = f == 1.0F;
         this.shape4.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shapess1.offsetX, this.shapess1.offsetY, this.shapess1.offsetZ);
         GlStateManager.translate(this.shapess1.rotationPointX * f5, this.shapess1.rotationPointY * f5, this.shapess1.rotationPointZ * f5);
         GlStateManager.scale(1.1, 1.0, 1.2);
         GlStateManager.translate(-this.shapess1.offsetX, -this.shapess1.offsetY, -this.shapess1.offsetZ);
         GlStateManager.translate(-this.shapess1.rotationPointX * f5, -this.shapess1.rotationPointY * f5, -this.shapess1.rotationPointZ * f5);
         this.shapess1.render(f5);
         GlStateManager.popMatrix();
         this.shape3.render(f5);
         this.shape1.render(f5);
         this.shapeb.render(f5);
         this.shape2.render(f5);
         this.shapea.render(f5);
         if (f == 0.0F) {
            this.shapef3.render(f5);
            this.shapef2.render(f5);
            this.shapef4.render(f5);
            this.shapef1.render(f5);
            this.shapef7.render(f5);
         }

         this.shapef6.render(f5);
         this.shapef5.render(f5);
         this.shaperight.render(f5);
         this.shapeleft.render(f5);
         float r1 = AnimationTimer.rainbow1;
         float r2 = AnimationTimer.rainbow2;
         int tick = AnimationTimer.tick;
         this.setRotateAngle(this.pearl, r1 * 0.27F, tick * 0.052F, r2 * 0.115F);
         AbstractMobModel.light(100 + (int)(ft3 * 20.0F), true);
         this.pearl.render(f5);
         AbstractMobModel.returnlight();
         AbstractMobModel.light((int)(GetMOP.getfromto(f1, 0.1F, 1.0F) * 130.0F), true);

         for (int i = 0; i < f2; i++) {
            if (i % 2 == 0) {
               this.rocket1.offsetX = ft1 - 1.5F * (i / 2 % 3);
               this.rocket1.offsetY = 1.5F * (i / 2 / 3);
               this.rocket1.offsetZ = ft2;
               this.rocket1.render(f5);
            } else {
               this.rocket3.offsetX = -ft1 + 1.5F * (i / 2 % 3);
               this.rocket3.offsetY = 1.5F * (i / 2 / 3);
               this.rocket3.offsetZ = ft2;
               this.rocket3.render(f5);
            }
         }

         AbstractMobModel.returnlight();
      } else {
         float r1 = AnimationTimer.rainbow1;
         float r2 = AnimationTimer.rainbow2;
         int tick = AnimationTimer.tick;
         this.setRotateAngle(this.pearl, r1 * 0.27F + f3, tick * 0.087F + f3 * 8.4145F, r2 * 0.115F + f3 * 3.7241F);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.pearl.offsetX, this.pearl.offsetY, this.pearl.offsetZ);
         GlStateManager.translate(this.pearl.rotationPointX * f5, this.pearl.rotationPointY * f5, this.pearl.rotationPointZ * f5);
         GlStateManager.scale(f4, f4, f4);
         GlStateManager.translate(-this.pearl.offsetX, -this.pearl.offsetY, -this.pearl.offsetZ);
         GlStateManager.translate(-this.pearl.rotationPointX * f5, -this.pearl.rotationPointY * f5, -this.pearl.rotationPointZ * f5);
         this.pearl.render(f5);
         GlStateManager.popMatrix();
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
