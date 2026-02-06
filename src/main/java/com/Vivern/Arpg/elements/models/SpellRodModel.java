package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class SpellRodModel extends ModelBase {
   public ModelRenderer staf3;
   public ModelRenderer staf5;
   public ModelRenderer staf4;
   public ModelRenderer staf2;
   public ModelRenderer mid1;
   public ModelRenderer mid2;
   public ModelRenderer gem;
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;

   public SpellRodModel() {
      this.textureWidth = 22;
      this.textureHeight = 22;
      this.staf2 = new ModelRenderer(this, 18, 11);
      this.staf2.setRotationPoint(0.0F, 1.0F, 5.0F);
      this.staf2.addBox(-0.5F, 0.0F, -0.5F, 1, 7, 1, 0.0F);
      this.mid1 = new ModelRenderer(this, 8, 19);
      this.mid1.setRotationPoint(0.0F, 0.0F, 5.0F);
      this.mid1.addBox(-1.0F, 0.0F, -1.0F, 2, 1, 2, 0.0F);
      this.shape3 = new ModelRenderer(this, 8, 5);
      this.shape3.setRotationPoint(0.0F, -2.5F, 5.0F);
      this.shape3.addBox(1.0F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
      this.shape1 = new ModelRenderer(this, 8, 0);
      this.shape1.setRotationPoint(0.0F, -2.5F, 5.0F);
      this.shape1.addBox(-0.5F, 0.0F, -2.0F, 1, 3, 1, 0.0F);
      this.gem = new ModelRenderer(this, 8, 11);
      this.gem.setRotationPoint(0.0F, -2.5F, 5.0F);
      this.gem.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, -0.01F);
      this.staf4 = new ModelRenderer(this, 0, 12);
      this.staf4.setRotationPoint(0.0F, 16.5F, 5.0F);
      this.staf4.addBox(-1.0F, 0.0F, -1.0F, 2, 1, 2, 0.0F);
      this.shape4 = new ModelRenderer(this, 13, 5);
      this.shape4.setRotationPoint(0.0F, -2.5F, 5.0F);
      this.shape4.addBox(-2.0F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
      this.shape2 = new ModelRenderer(this, 13, 0);
      this.shape2.setRotationPoint(0.0F, -2.5F, 5.0F);
      this.shape2.addBox(-0.5F, 0.0F, 1.0F, 1, 3, 1, 0.0F);
      this.mid2 = new ModelRenderer(this, 6, 15);
      this.mid2.setRotationPoint(0.0F, -1.0F, 5.0F);
      this.mid2.addBox(-1.5F, 0.0F, -1.5F, 3, 1, 3, 0.0F);
      this.staf3 = new ModelRenderer(this, 0, 0);
      this.staf3.setRotationPoint(0.0F, 8.0F, 5.0F);
      this.staf3.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
      this.staf5 = new ModelRenderer(this, 0, 12);
      this.staf5.setRotationPoint(0.0F, 8.5F, 5.0F);
      this.staf5.addBox(-1.0F, 0.0F, -1.0F, 2, 1, 2, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.staf2.render(f5);
      this.mid1.render(f5);
      this.shape3.render(f5);
      this.shape1.render(f5);
      this.gem.render(f5);
      this.staf4.render(f5);
      this.shape4.render(f5);
      this.shape2.render(f5);
      this.mid2.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf3.offsetX, this.staf3.offsetY, this.staf3.offsetZ);
      GlStateManager.translate(this.staf3.rotationPointX * f5, this.staf3.rotationPointY * f5, this.staf3.rotationPointZ * f5);
      GlStateManager.scale(0.8, 1.0, 0.8);
      GlStateManager.translate(-this.staf3.offsetX, -this.staf3.offsetY, -this.staf3.offsetZ);
      GlStateManager.translate(-this.staf3.rotationPointX * f5, -this.staf3.rotationPointY * f5, -this.staf3.rotationPointZ * f5);
      this.staf3.render(f5);
      GlStateManager.popMatrix();
      this.staf5.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
