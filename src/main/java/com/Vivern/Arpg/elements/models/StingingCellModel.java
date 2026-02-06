package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class StingingCellModel extends ModelBase {
   public ModelRenderer staf;
   public ModelRenderer shape;
   public ModelRenderer shape_1;
   public ModelRenderer tents;
   public ModelRenderer tentsss;
   public ModelRenderer tentsss_1;
   public ModelRenderer polip;
   public ModelRenderer tentsss_2;
   public ModelRenderer tentsss_3;
   public static Vec3d[] colors = new Vec3d[]{
      new Vec3d(0.9, 0.1, 0.3),
      new Vec3d(0.9, 0.5, 0.1),
      new Vec3d(0.9, 0.9, 0.0),
      new Vec3d(0.3, 0.9, 0.2),
      new Vec3d(0.2, 0.7, 1.0),
      new Vec3d(0.3, 0.3, 1.0),
      new Vec3d(0.5, 0.0, 0.9),
      new Vec3d(0.7, 0.1, 0.8),
      new Vec3d(0.8, 0.1, 0.5),
      new Vec3d(0.9, 0.1, 0.3),
      new Vec3d(0.9, 0.5, 0.1)
   };

   public StingingCellModel() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      this.tents = new ModelRenderer(this, 12, 27);
      this.tents.setRotationPoint(0.0F, -2.0F, 5.0F);
      this.tents.addBox(-1.5F, 0.0F, -1.5F, 3, 2, 3, 0.0F);
      this.tentsss_2 = new ModelRenderer(this, 0, 8);
      this.tentsss_2.setRotationPoint(0.0F, 15.0F, 5.0F);
      this.tentsss_2.addBox(-2.5F, 0.0F, 0.0F, 5, 6, 0, 0.0F);
      this.setRotateAngle(this.tentsss_2, 0.0F, (float) (-Math.PI / 4), 0.0F);
      this.tentsss = new ModelRenderer(this, 0, 0);
      this.tentsss.setRotationPoint(0.0F, -4.0F, 5.0F);
      this.tentsss.addBox(-2.5F, 0.0F, 0.0F, 5, 6, 0, 0.0F);
      this.setRotateAngle(this.tentsss, 0.0F, (float) (-Math.PI / 4), 0.0F);
      this.polip = new ModelRenderer(this, 0, 6);
      this.polip.setRotationPoint(0.0F, -1.0F, 5.0F);
      this.polip.addBox(0.8F, 0.0F, -0.5F, 1, 1, 1, 0.0F);
      this.setRotateAngle(this.polip, 0.0F, -2.321986F, 0.0F);
      this.staf = new ModelRenderer(this, 10, 0);
      this.staf.setRotationPoint(0.0F, -2.0F, 5.0F);
      this.staf.addBox(-1.0F, 0.0F, -1.0F, 2, 23, 2, 0.0F);
      this.tentsss_1 = new ModelRenderer(this, 0, 0);
      this.tentsss_1.setRotationPoint(0.0F, -4.0F, 5.0F);
      this.tentsss_1.addBox(-2.5F, 0.0F, 0.0F, 5, 6, 0, 0.0F);
      this.setRotateAngle(this.tentsss_1, 0.0F, (float) (Math.PI / 4), 0.0F);
      this.shape = new ModelRenderer(this, 0, 27);
      this.shape.setRotationPoint(0.0F, -4.0F, 5.0F);
      this.shape.addBox(-1.5F, 0.0F, -1.5F, 3, 2, 3, 0.0F);
      this.shape_1 = new ModelRenderer(this, 0, 24);
      this.shape_1.setRotationPoint(0.0F, -5.0F, 5.0F);
      this.shape_1.addBox(-1.0F, 0.0F, -1.0F, 2, 1, 2, 0.0F);
      this.tentsss_3 = new ModelRenderer(this, 0, 8);
      this.tentsss_3.setRotationPoint(0.0F, 15.0F, 5.0F);
      this.tentsss_3.addBox(-2.5F, 0.0F, 0.0F, 5, 6, 0, 0.0F);
      this.setRotateAngle(this.tentsss_3, 0.0F, (float) (Math.PI / 4), 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (f1 == 0.0F) {
         this.tents.render(f5);
         this.tentsss_2.render(f5);
         this.tentsss.render(f5);
         this.tentsss_3.render(f5);
         this.tentsss_1.render(f5);
      }

      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf.offsetX, this.staf.offsetY, this.staf.offsetZ);
      GlStateManager.translate(this.staf.rotationPointX * f5, this.staf.rotationPointY * f5, this.staf.rotationPointZ * f5);
      GlStateManager.scale(0.7, 0.9, 0.7);
      GlStateManager.translate(-this.staf.offsetX, -this.staf.offsetY, -this.staf.offsetZ);
      GlStateManager.translate(-this.staf.rotationPointX * f5, -this.staf.rotationPointY * f5, -this.staf.rotationPointZ * f5);
      this.staf.render(f5);
      GlStateManager.popMatrix();
      this.shape.render(f5);
      this.shape_1.render(f5);
      AbstractMobModel.light(240, false);
      GlStateManager.pushMatrix();

      for (int i = 0; i < 11; i++) {
         GlStateManager.color((float)colors[i].x, (float)colors[i].y, (float)colors[i].z);
         this.polip.rotateAngleY = -2.321986F + i;
         float scl = 1.0F - i / 20.0F;
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.staf.offsetX, this.staf.offsetY, this.staf.offsetZ);
         GlStateManager.translate(this.staf.rotationPointX * f5, this.staf.rotationPointY * f5, this.staf.rotationPointZ * f5);
         GlStateManager.scale(scl, scl, scl);
         GlStateManager.translate(-this.staf.offsetX, -this.staf.offsetY, -this.staf.offsetZ);
         GlStateManager.translate(-this.staf.rotationPointX * f5, -this.staf.rotationPointY * f5, -this.staf.rotationPointZ * f5);
         this.polip.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.translate(0.0F, 1.5F, 0.0F);
      }

      GlStateManager.popMatrix();
      AbstractMobModel.returnlight();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
