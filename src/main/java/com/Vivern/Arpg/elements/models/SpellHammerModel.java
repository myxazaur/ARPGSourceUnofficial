package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class SpellHammerModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer staf1;
   public ModelRenderer staf2;
   public ModelRenderer staf3;
   public ModelRenderer staf4;
   public ModelRenderer staf5;
   public ModelRenderer shape4;
   public ModelRenderer shape5;
   public ModelRenderer shape7;
   public ModelRenderer shape3;
   public ModelRenderer shape6;

   public SpellHammerModel() {
      this.textureWidth = 22;
      this.textureHeight = 22;
      this.shape3 = new ModelRenderer(this, 8, 7);
      this.shape3.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.shape3.addBox(-0.5F, -0.5F, 0.0F, 4, 4, 1, 0.0F);
      this.shape1 = new ModelRenderer(this, 1, 12);
      this.shape1.setRotationPoint(-1.5F, -4.0F, 1.5F);
      this.shape1.addBox(0.0F, 0.0F, 0.0F, 3, 3, 7, 0.0F);
      this.shape2 = new ModelRenderer(this, 8, 7);
      this.shape2.setRotationPoint(-1.5F, -4.0F, 2.5F);
      this.shape2.addBox(-0.5F, -0.5F, 0.0F, 4, 4, 1, 0.0F);
      this.shape7 = new ModelRenderer(this, 8, 0);
      this.shape7.mirror = true;
      this.shape7.setRotationPoint(0.0F, -2.5F, 7.5F);
      this.shape7.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 1, 0.0F);
      this.shape6 = new ModelRenderer(this, 8, 7);
      this.shape6.setRotationPoint(0.0F, 0.0F, -2.0F);
      this.shape6.addBox(-0.5F, -0.5F, 0.0F, 4, 4, 1, 0.0F);
      this.shape4 = new ModelRenderer(this, 8, 0);
      this.shape4.setRotationPoint(0.0F, -2.5F, 1.5F);
      this.shape4.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 1, 0.0F);
      this.staf2 = new ModelRenderer(this, 18, 11);
      this.staf2.setRotationPoint(0.0F, 1.0F, 5.0F);
      this.staf2.addBox(-0.5F, 0.0F, -0.5F, 1, 7, 1, 0.0F);
      this.staf3 = new ModelRenderer(this, 0, 0);
      this.staf3.setRotationPoint(0.0F, 8.0F, 5.0F);
      this.staf3.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
      this.shape5 = new ModelRenderer(this, 8, 7);
      this.shape5.setRotationPoint(-1.5F, -4.0F, 8.5F);
      this.shape5.addBox(-0.5F, -0.5F, 0.0F, 4, 4, 1, 0.0F);
      this.staf1 = new ModelRenderer(this, 0, 15);
      this.staf1.setRotationPoint(0.0F, -1.0F, 5.0F);
      this.staf1.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
      this.staf4 = new ModelRenderer(this, 0, 12);
      this.staf4.setRotationPoint(0.0F, 16.5F, 5.0F);
      this.staf4.addBox(-1.0F, 0.0F, -1.0F, 2, 1, 2, 0.0F);
      this.staf5 = new ModelRenderer(this, 0, 12);
      this.staf5.setRotationPoint(0.0F, 8.5F, 5.0F);
      this.staf5.addBox(-1.0F, 0.0F, -1.0F, 2, 1, 2, 0.0F);
      this.shape2.addChild(this.shape3);
      this.shape5.addChild(this.shape6);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shape1.render(f5);
      this.shape2.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape7.offsetX, this.shape7.offsetY, this.shape7.offsetZ);
      GlStateManager.translate(this.shape7.rotationPointX * f5, this.shape7.rotationPointY * f5, this.shape7.rotationPointZ * f5);
      GlStateManager.scale(0.9, 0.9, 1.0);
      GlStateManager.translate(-this.shape7.offsetX, -this.shape7.offsetY, -this.shape7.offsetZ);
      GlStateManager.translate(-this.shape7.rotationPointX * f5, -this.shape7.rotationPointY * f5, -this.shape7.rotationPointZ * f5);
      this.shape7.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape4.offsetX, this.shape4.offsetY, this.shape4.offsetZ);
      GlStateManager.translate(this.shape4.rotationPointX * f5, this.shape4.rotationPointY * f5, this.shape4.rotationPointZ * f5);
      GlStateManager.scale(0.9, 0.9, 1.0);
      GlStateManager.translate(-this.shape4.offsetX, -this.shape4.offsetY, -this.shape4.offsetZ);
      GlStateManager.translate(-this.shape4.rotationPointX * f5, -this.shape4.rotationPointY * f5, -this.shape4.rotationPointZ * f5);
      this.shape4.render(f5);
      GlStateManager.popMatrix();
      this.staf2.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf3.offsetX, this.staf3.offsetY, this.staf3.offsetZ);
      GlStateManager.translate(this.staf3.rotationPointX * f5, this.staf3.rotationPointY * f5, this.staf3.rotationPointZ * f5);
      GlStateManager.scale(0.8, 1.0, 0.8);
      GlStateManager.translate(-this.staf3.offsetX, -this.staf3.offsetY, -this.staf3.offsetZ);
      GlStateManager.translate(-this.staf3.rotationPointX * f5, -this.staf3.rotationPointY * f5, -this.staf3.rotationPointZ * f5);
      this.staf3.render(f5);
      GlStateManager.popMatrix();
      this.shape5.render(f5);
      this.staf1.render(f5);
      this.staf4.render(f5);
      this.staf5.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
