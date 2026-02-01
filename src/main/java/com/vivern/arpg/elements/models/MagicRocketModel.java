package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class MagicRocketModel extends ModelBase {
   public ModelRenderer staf;
   public ModelRenderer staf_1;
   public ModelRenderer gem;
   public ModelRenderer staf_2;

   public MagicRocketModel() {
      this.textureWidth = 16;
      this.textureHeight = 32;
      this.staf_1 = new ModelRenderer(this, 8, 0);
      this.staf_1.setRotationPoint(0.0F, -5.3F, 7.0F);
      this.staf_1.addBox(-0.5F, 0.0F, -1.0F, 1, 5, 2, 0.0F);
      this.setRotateAngle(this.staf_1, -0.7740535F, 0.0F, 0.0F);
      this.staf_2 = new ModelRenderer(this, 8, 7);
      this.staf_2.setRotationPoint(0.0F, -6.1F, 3.1F);
      this.staf_2.addBox(-0.5F, 0.0F, -1.0F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.staf_2, 0.7740535F, 0.0F, 0.0F);
      this.gem = new ModelRenderer(this, 0, 20);
      this.gem.setRotationPoint(0.0F, -6.6F, 5.0F);
      this.gem.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
      this.setRotateAngle(this.gem, -0.7740535F, 0.0F, 0.0F);
      this.staf = new ModelRenderer(this, 0, 0);
      this.staf.setRotationPoint(0.0F, -2.0F, 5.0F);
      this.staf.addBox(-1.0F, 0.0F, -1.0F, 2, 18, 2, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf_1.offsetX, this.staf_1.offsetY, this.staf_1.offsetZ);
      GlStateManager.translate(this.staf_1.rotationPointX * f5, this.staf_1.rotationPointY * f5, this.staf_1.rotationPointZ * f5);
      GlStateManager.scale(0.7, 1.0, 0.7);
      GlStateManager.translate(-this.staf_1.offsetX, -this.staf_1.offsetY, -this.staf_1.offsetZ);
      GlStateManager.translate(-this.staf_1.rotationPointX * f5, -this.staf_1.rotationPointY * f5, -this.staf_1.rotationPointZ * f5);
      this.staf_1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf_2.offsetX, this.staf_2.offsetY, this.staf_2.offsetZ);
      GlStateManager.translate(this.staf_2.rotationPointX * f5, this.staf_2.rotationPointY * f5, this.staf_2.rotationPointZ * f5);
      GlStateManager.scale(0.7, 1.0, 0.7);
      GlStateManager.translate(-this.staf_2.offsetX, -this.staf_2.offsetY, -this.staf_2.offsetZ);
      GlStateManager.translate(-this.staf_2.rotationPointX * f5, -this.staf_2.rotationPointY * f5, -this.staf_2.rotationPointZ * f5);
      this.staf_2.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.gem.offsetX, this.gem.offsetY, this.gem.offsetZ);
      GlStateManager.translate(this.gem.rotationPointX * f5, this.gem.rotationPointY * f5, this.gem.rotationPointZ * f5);
      GlStateManager.scale(0.7, 1.0, 0.7);
      GlStateManager.translate(-this.gem.offsetX, -this.gem.offsetY, -this.gem.offsetZ);
      GlStateManager.translate(-this.gem.rotationPointX * f5, -this.gem.rotationPointY * f5, -this.gem.rotationPointZ * f5);
      this.gem.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf.offsetX, this.staf.offsetY, this.staf.offsetZ);
      GlStateManager.translate(this.staf.rotationPointX * f5, this.staf.rotationPointY * f5, this.staf.rotationPointZ * f5);
      GlStateManager.scale(0.7, 0.9, 0.7);
      GlStateManager.translate(-this.staf.offsetX, -this.staf.offsetY, -this.staf.offsetZ);
      GlStateManager.translate(-this.staf.rotationPointX * f5, -this.staf.rotationPointY * f5, -this.staf.rotationPointZ * f5);
      this.staf.render(f5);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
