package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class EntityCrystalCutterModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;

   public EntityCrystalCutterModel() {
      this.textureWidth = 16;
      this.textureHeight = 8;
      this.shape4 = new ModelRenderer(this, 10, 6);
      this.shape4.setRotationPoint(3.0F, 0.0F, 0.0F);
      this.shape4.addBox(0.0F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
      this.shape2 = new ModelRenderer(this, 8, 0);
      this.shape2.setRotationPoint(-0.2F, 0.0F, 0.0F);
      this.shape2.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.shape2, 0.8196066F, 1.0016445F, 0.0F);
      this.shape3 = new ModelRenderer(this, 0, 0);
      this.shape3.setRotationPoint(-0.2F, 0.0F, 0.0F);
      this.shape3.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.shape3, -0.8196066F, -1.0016445F, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 4);
      this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1.addBox(0.0F, -1.0F, -1.0F, 3, 2, 2, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape4.offsetX, this.shape4.offsetY, this.shape4.offsetZ);
      GlStateManager.translate(this.shape4.rotationPointX * f5, this.shape4.rotationPointY * f5, this.shape4.rotationPointZ * f5);
      GlStateManager.scale(0.8, 1.2, 1.2);
      GlStateManager.translate(-this.shape4.offsetX, -this.shape4.offsetY, -this.shape4.offsetZ);
      GlStateManager.translate(-this.shape4.rotationPointX * f5, -this.shape4.rotationPointY * f5, -this.shape4.rotationPointZ * f5);
      this.shape4.render(f5);
      GlStateManager.popMatrix();
      AbstractMobModel.light(180, false);
      this.shape2.render(f5);
      this.shape3.render(f5);
      AbstractMobModel.returnlight();
      this.shape1.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
