package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ElectricStaffModel extends ModelBase {
   public ModelRenderer staf;
   public ModelRenderer stonee;
   public ModelRenderer partt;
   public ModelRenderer part;
   public ModelRenderer parts;
   public ModelRenderer partl;
   public ModelRenderer stone;

   public ElectricStaffModel() {
      this.textureWidth = 16;
      this.textureHeight = 22;
      this.parts = new ModelRenderer(this, 0, 11);
      this.parts.setRotationPoint(0.0F, -6.5F, 5.0F);
      this.parts.addBox(-1.0F, -2.0F, -6.0F, 2, 5, 2, 0.0F);
      this.setRotateAngle(this.parts, -0.13665928F, 0.0F, 0.0F);
      this.staf = new ModelRenderer(this, 8, 0);
      this.staf.setRotationPoint(0.0F, 6.0F, 5.0F);
      this.staf.addBox(-1.0F, -8.0F, -1.0F, 2, 20, 2, 0.0F);
      this.stone = new ModelRenderer(this, 0, 18);
      this.stone.setRotationPoint(0.0F, -8.0F, 5.0F);
      this.stone.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.stone, 0.0F, 0.7983136F, 0.0F);
      this.partt = new ModelRenderer(this, 0, 0);
      this.partt.setRotationPoint(0.0F, -1.5F, 5.0F);
      this.partt.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
      this.setRotateAngle(this.partt, -0.68294734F, 0.0F, 0.0F);
      this.part = new ModelRenderer(this, 0, 0);
      this.part.setRotationPoint(0.0F, -1.5F, 5.0F);
      this.part.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
      this.setRotateAngle(this.part, 0.68294734F, 0.0F, 0.0F);
      this.partl = new ModelRenderer(this, 0, 11);
      this.partl.setRotationPoint(0.0F, -6.5F, 5.0F);
      this.partl.addBox(-1.0F, -2.0F, 4.0F, 2, 5, 2, 0.0F);
      this.setRotateAngle(this.partl, 0.13665928F, 0.0F, 0.0F);
      this.stonee = new ModelRenderer(this, 0, 18);
      this.stonee.setRotationPoint(0.0F, -8.0F, 5.0F);
      this.stonee.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.stonee, 1.5934856F, 0.7740535F, -0.8196066F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.parts.offsetX, this.parts.offsetY, this.parts.offsetZ);
      GlStateManager.translate(this.parts.rotationPointX * f5, this.parts.rotationPointY * f5, this.parts.rotationPointZ * f5);
      GlStateManager.scale(0.49, 1.0, 0.39);
      GlStateManager.translate(-this.parts.offsetX, -this.parts.offsetY, -this.parts.offsetZ);
      GlStateManager.translate(-this.parts.rotationPointX * f5, -this.parts.rotationPointY * f5, -this.parts.rotationPointZ * f5);
      this.parts.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf.offsetX, this.staf.offsetY, this.staf.offsetZ);
      GlStateManager.translate(this.staf.rotationPointX * f5, this.staf.rotationPointY * f5, this.staf.rotationPointZ * f5);
      GlStateManager.scale(0.7, 1.0, 0.7);
      GlStateManager.translate(-this.staf.offsetX, -this.staf.offsetY, -this.staf.offsetZ);
      GlStateManager.translate(-this.staf.rotationPointX * f5, -this.staf.rotationPointY * f5, -this.staf.rotationPointZ * f5);
      this.staf.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.partt.offsetX, this.partt.offsetY, this.partt.offsetZ);
      GlStateManager.translate(this.partt.rotationPointX * f5, this.partt.rotationPointY * f5, this.partt.rotationPointZ * f5);
      GlStateManager.scale(0.58, 1.0, 0.49);
      GlStateManager.translate(-this.partt.offsetX, -this.partt.offsetY, -this.partt.offsetZ);
      GlStateManager.translate(-this.partt.rotationPointX * f5, -this.partt.rotationPointY * f5, -this.partt.rotationPointZ * f5);
      this.partt.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.part.offsetX, this.part.offsetY, this.part.offsetZ);
      GlStateManager.translate(this.part.rotationPointX * f5, this.part.rotationPointY * f5, this.part.rotationPointZ * f5);
      GlStateManager.scale(0.59, 1.0, 0.49);
      GlStateManager.translate(-this.part.offsetX, -this.part.offsetY, -this.part.offsetZ);
      GlStateManager.translate(-this.part.rotationPointX * f5, -this.part.rotationPointY * f5, -this.part.rotationPointZ * f5);
      this.part.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.partl.offsetX, this.partl.offsetY, this.partl.offsetZ);
      GlStateManager.translate(this.partl.rotationPointX * f5, this.partl.rotationPointY * f5, this.partl.rotationPointZ * f5);
      GlStateManager.scale(0.49, 1.0, 0.39);
      GlStateManager.translate(-this.partl.offsetX, -this.partl.offsetY, -this.partl.offsetZ);
      GlStateManager.translate(-this.partl.rotationPointX * f5, -this.partl.rotationPointY * f5, -this.partl.rotationPointZ * f5);
      this.partl.render(f5);
      GlStateManager.popMatrix();
      AbstractMobModel.light(120, true);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.stonee.offsetX, this.stonee.offsetY, this.stonee.offsetZ);
      GlStateManager.translate(this.stonee.rotationPointX * f5, this.stonee.rotationPointY * f5, this.stonee.rotationPointZ * f5);
      GlStateManager.scale(0.8, 1.6, 1.0);
      GlStateManager.translate(-this.stonee.offsetX, -this.stonee.offsetY, -this.stonee.offsetZ);
      GlStateManager.translate(-this.stonee.rotationPointX * f5, -this.stonee.rotationPointY * f5, -this.stonee.rotationPointZ * f5);
      this.stonee.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.stone.offsetX, this.stone.offsetY, this.stone.offsetZ);
      GlStateManager.translate(this.stone.rotationPointX * f5, this.stone.rotationPointY * f5, this.stone.rotationPointZ * f5);
      GlStateManager.scale(1.0, 1.5, 1.0);
      GlStateManager.translate(-this.stone.offsetX, -this.stone.offsetY, -this.stone.offsetZ);
      GlStateManager.translate(-this.stone.rotationPointX * f5, -this.stone.rotationPointY * f5, -this.stone.rotationPointZ * f5);
      this.stone.render(f5);
      GlStateManager.popMatrix();
      AbstractMobModel.returnlight();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
