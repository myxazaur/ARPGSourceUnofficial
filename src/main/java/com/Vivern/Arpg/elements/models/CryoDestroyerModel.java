package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.renders.ModelRendererGlow;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class CryoDestroyerModel extends ModelBase {
   public ModelRenderer shapess1;
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shaft;
   public ModelRenderer barometer;
   public ModelRenderer injector;
   public ModelRenderer camera;
   public ModelRenderer shape5;
   public ModelRenderer shape6;
   public ModelRenderer shape7;
   public ModelRenderer ammo;
   public ModelRenderer noglow;
   public ModelRenderer head0;
   public ModelRenderer head1;
   public ModelRenderer head2;
   public ModelRenderer head3;
   public ModelRenderer head4;
   public ModelRenderer arrow;
   public ModelRenderer pipe1;
   public ModelRenderer pipe2;

   public CryoDestroyerModel() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      this.head2 = new ModelRenderer(this, 20, 28);
      this.head2.setRotationPoint(0.0F, 0.5F, -2.0F);
      this.head2.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
      this.setRotateAngle(this.head2, 0.4098033F, 0.0F, (float) Math.PI);
      this.camera = new ModelRenderer(this, 22, 18);
      this.camera.setRotationPoint(0.0F, 1.0F, -7.0F);
      this.camera.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.camera, 0.0F, 0.0F, -0.31869712F);
      this.shape1 = new ModelRenderer(this, 0, 10);
      this.shape1.setRotationPoint(0.0F, 2.0F, -1.0F);
      this.shape1.addBox(-1.5F, 0.0F, 0.0F, 3, 8, 1, 0.0F);
      this.setRotateAngle(this.shape1, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.head4 = new ModelRenderer(this, 20, 28);
      this.head4.setRotationPoint(-0.5F, 0.0F, -2.0F);
      this.head4.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
      this.setRotateAngle(this.head4, 0.4098033F, 0.0F, (float) (-Math.PI / 2));
      this.head1 = new ModelRenderer(this, 20, 28);
      this.head1.setRotationPoint(0.0F, -0.5F, -2.0F);
      this.head1.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
      this.setRotateAngle(this.head1, 0.4098033F, 0.0F, 0.0F);
      this.barometer = new ModelRenderer(this, 22, 0);
      this.barometer.setRotationPoint(2.0F, 2.5F, -3.0F);
      this.barometer.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
      this.setRotateAngle(this.barometer, 0.2443461F, 0.0F, 0.0F);
      this.ammo = new ModelRendererGlow(this, 16, 23, 170, false);
      this.ammo.setRotationPoint(9.5F, 9.5F, 0.5F);
      this.ammo.addBox(-8.5F, -3.5F, -4.0F, 1, 1, 4, 0.0F);
      this.pipe2 = new ModelRenderer(this, 16, 4);
      this.pipe2.setRotationPoint(0.0F, 4.0F, 1.0F);
      this.pipe2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
      this.shaft = new ModelRenderer(this, 1, 23);
      this.shaft.setRotationPoint(0.0F, 4.0F, -10.0F);
      this.shaft.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 7, 0.0F);
      this.shapess1 = new ModelRenderer(this, 0, 22);
      this.shapess1.setRotationPoint(0.0F, 1.0F, 4.0F);
      this.shapess1.addBox(-1.0F, 0.5F, -1.0F, 2, 5, 2, 0.0F);
      this.setRotateAngle(this.shapess1, 0.18203785F, 0.0F, 0.0F);
      this.shape6 = new ModelRenderer(this, 0, 0);
      this.shape6.setRotationPoint(0.0F, 4.0F, 0.5F);
      this.shape6.addBox(0.0F, 0.0F, 0.0F, 3, 4, 1, 0.0F);
      this.injector = new ModelRenderer(this, 16, 18);
      this.injector.setRotationPoint(0.0F, 1.0F, -11.0F);
      this.injector.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 4, 0.0F);
      this.head3 = new ModelRenderer(this, 20, 28);
      this.head3.setRotationPoint(0.5F, 0.0F, -2.0F);
      this.head3.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
      this.setRotateAngle(this.head3, 0.4098033F, 0.0F, (float) (Math.PI / 2));
      this.shape3 = new ModelRenderer(this, 2, 0);
      this.shape3.setRotationPoint(0.0F, 2.0F, -4.0F);
      this.shape3.addBox(-2.0F, 0.0F, 0.0F, 4, 4, 6, 0.0F);
      this.arrow = new ModelRenderer(this, 26, 4);
      this.arrow.setRotationPoint(1.0F, 1.0F, 1.1F);
      this.arrow.addBox(-0.25F, -1.0F, 0.0F, 1, 1, 0, 0.0F);
      this.setRotateAngle(this.arrow, 0.0F, 0.0F, 2.2310543F);
      this.head0 = new ModelRenderer(this, 20, 28);
      this.head0.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.head0.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 3, 0.0F);
      this.noglow = new ModelRenderer(this, 8, 1);
      this.noglow.setRotationPoint(0.0F, 0.5F, -11.0F);
      this.noglow.addBox(0.0F, 0.0F, -1.0F, 0, 2, 11, 0.0F);
      this.shape2 = new ModelRenderer(this, 0, 10);
      this.shape2.setRotationPoint(0.0F, 7.0F, 1.5F);
      this.shape2.addBox(-1.5F, 0.0F, 0.0F, 3, 8, 1, 0.0F);
      this.setRotateAngle(this.shape2, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.shape5 = new ModelRenderer(this, 0, 0);
      this.shape5.setRotationPoint(0.0F, 4.0F, -1.0F);
      this.shape5.addBox(0.0F, 0.0F, 0.0F, 3, 4, 1, 0.0F);
      this.shape4 = new ModelRenderer(this, 3, 14);
      this.shape4.setRotationPoint(0.0F, 2.5F, -9.0F);
      this.shape4.addBox(-1.5F, 0.0F, 0.0F, 3, 3, 5, 0.0F);
      this.shape7 = new ModelRenderer(this, 19, 14);
      this.shape7.setRotationPoint(0.0F, 6.0F, -4.0F);
      this.shape7.addBox(-1.0F, 0.0F, 0.0F, 2, 1, 3, 0.0F);
      this.pipe1 = new ModelRenderer(this, 17, 0);
      this.pipe1.setRotationPoint(0.5F, 0.0F, 0.5F);
      this.pipe1.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1, 0.0F);
      this.shaft.addChild(this.head2);
      this.shaft.addChild(this.head4);
      this.shaft.addChild(this.head1);
      this.pipe1.addChild(this.pipe2);
      this.shaft.addChild(this.head3);
      this.barometer.addChild(this.arrow);
      this.shaft.addChild(this.head0);
      this.camera.addChild(this.pipe1);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      float ft1 = GetMOP.getfromto(f1, 0.05F, 0.1F) - GetMOP.softfromto(f1, 0.2F, 1.0F);
      this.shaft.offsetZ = -6.0F * ft1;
      GlStateManager.disableCull();
      float ft2 = GetMOP.getfromto(f2, 0.0F, 0.4F) - GetMOP.softfromto(f2, 0.6F, 1.0F);
      this.ammo.rotateAngleX = 1.954736F * ft2;
      this.ammo.rotateAngleY = -0.715573F * ft2;
      this.ammo.rotateAngleZ = -0.855197F * ft2;
      if (f2 <= 0.4F || f2 >= 0.6F) {
         this.ammo.render(f5);
      }

      this.arrow.isHidden = f != 0.0F;
      this.arrow.rotateAngleZ = -f3 * 0.017453F;
      this.camera.render(f5);
      this.shape1.render(f5);
      this.barometer.render(f5);
      this.shaft.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapess1.offsetX, this.shapess1.offsetY, this.shapess1.offsetZ);
      GlStateManager.translate(this.shapess1.rotationPointX * f5, this.shapess1.rotationPointY * f5, this.shapess1.rotationPointZ * f5);
      GlStateManager.scale(1.1, 1.0, 1.2);
      GlStateManager.translate(-this.shapess1.offsetX, -this.shapess1.offsetY, -this.shapess1.offsetZ);
      GlStateManager.translate(-this.shapess1.rotationPointX * f5, -this.shapess1.rotationPointY * f5, -this.shapess1.rotationPointZ * f5);
      this.shapess1.render(f5);
      GlStateManager.popMatrix();
      this.shape6.render(f5);
      this.injector.render(f5);
      this.shape3.render(f5);
      if (f == 0.0F) {
         this.noglow.render(f5);
      }

      this.shape2.render(f5);
      this.shape5.render(f5);
      this.shape4.render(f5);
      this.shape7.render(f5);
      GlStateManager.enableCull();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
