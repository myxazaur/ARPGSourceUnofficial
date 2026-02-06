package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.ColorConverters;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.renders.ModelRendererLimited;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class GrenadeLauncherModel extends ModelBase {
   public ModelRenderer shapess1;
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shapepr1;
   public ModelRenderer shapepr2;
   public ModelRenderer shapepr3;
   public ModelRenderer shapescope;
   public ModelRendererLimited ammo;

   public GrenadeLauncherModel() {
      this.textureWidth = 48;
      this.textureHeight = 20;
      this.shapescope = new ModelRenderer(this, 0, 9);
      this.shapescope.setRotationPoint(0.0F, -3.0F, -6.0F);
      this.shapescope.addBox(-1.5F, -3.0F, -4.0F, 3, 3, 5, 0.0F);
      this.shapepr2 = new ModelRenderer(this, 4, 1);
      this.shapepr2.setRotationPoint(0.0F, 0.9F, 9.0F);
      this.shapepr2.addBox(-0.5F, -3.0F, 0.0F, 1, 3, 5, 0.0F);
      this.shape2 = new ModelRenderer(this, 32, 0);
      this.shape2.setRotationPoint(0.0F, 1.9F, -4.0F);
      this.shape2.addBox(-1.5F, 0.0F, 0.0F, 3, 10, 1, 0.0F);
      this.setRotateAngle(this.shape2, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(0.0F, 0.9F, -2.0F);
      this.shape1.addBox(-1.5F, -3.0F, -11.0F, 3, 3, 17, 0.0F);
      this.shape3 = new ModelRenderer(this, 30, 12);
      this.shape3.setRotationPoint(0.0F, -1.3F, 4.0F);
      this.shape3.addBox(-1.5F, -1.0F, 0.0F, 3, 3, 2, 0.0F);
      this.shapess1 = new ModelRenderer(this, 24, 0);
      this.shapess1.setRotationPoint(0.0F, 1.0F, 4.0F);
      this.shapess1.addBox(-1.0F, 0.5F, -1.0F, 2, 5, 2, 0.0F);
      this.setRotateAngle(this.shapess1, 0.27314404F, 0.0F, 0.0F);
      this.shapepr1 = new ModelRenderer(this, 0, 0);
      this.shapepr1.setRotationPoint(0.0F, 1.4F, 6.0F);
      this.shapepr1.addBox(-0.5F, -3.0F, 0.0F, 1, 3, 3, 0.0F);
      this.shapepr3 = new ModelRenderer(this, 23, 7);
      this.shapepr3.setRotationPoint(0.0F, 1.4F, 14.0F);
      this.shapepr3.addBox(-0.5F, -3.0F, 0.0F, 1, 4, 3, 0.0F);
      this.ammo = new ModelRendererLimited(this, 38, 9);
      this.ammo.setRotationPoint(3.0F, 5.5F, 5.0F);
      this.ammo.addBox(-4.0F, -8.0F, -1.5F, 2, 2, 3, 0.0F);
      this.shape1.addChild(this.shapescope);
      this.shape1.addChild(this.ammo);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.disableCull();
      this.shape1.rotateAngleX = (GetMOP.getfromto(f1, 0.085F, 0.185F) - GetMOP.getfromto(f1, 0.75F, 0.9F)) * 0.78F;
      float ft1 = 1.0F - GetMOP.getfromto(f1, 0.23F, 0.5F);
      this.ammo.rotateAngleX = -2.18F * ft1;
      this.ammo.rotateAngleY = 1.0F * ft1;
      this.shapescope.isHidden = f == 1.0F;
      this.ammo.isHidden = f2 == 0.0F || f1 < 0.15F;
      Vec3d vec = ColorConverters.DecimaltoRGB((int)f3);
      this.ammo.setColor((float)vec.x, (float)vec.y, (float)vec.z, 1.0F);
      this.shapepr2.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape2.offsetX, this.shape2.offsetY, this.shape2.offsetZ);
      GlStateManager.translate(this.shape2.rotationPointX * f5, this.shape2.rotationPointY * f5, this.shape2.rotationPointZ * f5);
      GlStateManager.scale(1.1, 1.1, 1.0);
      GlStateManager.translate(-this.shape2.offsetX, -this.shape2.offsetY, -this.shape2.offsetZ);
      GlStateManager.translate(-this.shape2.rotationPointX * f5, -this.shape2.rotationPointY * f5, -this.shape2.rotationPointZ * f5);
      this.shape2.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape3.offsetX, this.shape3.offsetY, this.shape3.offsetZ);
      GlStateManager.translate(this.shape3.rotationPointX * f5, this.shape3.rotationPointY * f5, this.shape3.rotationPointZ * f5);
      GlStateManager.scale(1.2, 1.1, 1.0);
      GlStateManager.translate(-this.shape3.offsetX, -this.shape3.offsetY, -this.shape3.offsetZ);
      GlStateManager.translate(-this.shape3.rotationPointX * f5, -this.shape3.rotationPointY * f5, -this.shape3.rotationPointZ * f5);
      this.shape3.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapess1.offsetX, this.shapess1.offsetY, this.shapess1.offsetZ);
      GlStateManager.translate(this.shapess1.rotationPointX * f5, this.shapess1.rotationPointY * f5, this.shapess1.rotationPointZ * f5);
      GlStateManager.scale(1.1, 1.0, 1.2);
      GlStateManager.translate(-this.shapess1.offsetX, -this.shapess1.offsetY, -this.shapess1.offsetZ);
      GlStateManager.translate(-this.shapess1.rotationPointX * f5, -this.shapess1.rotationPointY * f5, -this.shapess1.rotationPointZ * f5);
      this.shapess1.render(f5);
      GlStateManager.popMatrix();
      this.shapepr1.render(f5);
      this.shapepr3.render(f5);
      this.shape1.render(f5);
      GlStateManager.enableCull();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
