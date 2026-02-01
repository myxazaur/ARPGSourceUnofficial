package com.vivern.arpg.elements.models;

import com.vivern.arpg.main.GetMOP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class WeldAugmentModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer manA;
   public ModelRenderer manB;
   public ModelRenderer manC;
   public ModelRenderer manD;
   public ModelRenderer manE;
   public ModelRenderer wire2;
   public ModelRenderer tool1;
   public ModelRenderer tool2;
   public ModelRenderer wire1;
   public ModelRenderer tool1a;

   public WeldAugmentModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.shape2 = new ModelRenderer(this, 0, 16);
      this.shape2.setRotationPoint(0.0F, 10.0F, 0.0F);
      this.shape2.addBox(-7.0F, 0.0F, -7.0F, 14, 4, 14, -0.01F);
      this.manD = new ModelRenderer(this, 10, 20);
      this.manD.setRotationPoint(0.0F, -2.0F, -1.0F);
      this.manD.addBox(-0.5F, -7.0F, -0.5F, 1, 7, 1, 0.0F);
      this.setRotateAngle(this.manD, 0.7285004F, 0.0F, 0.0F);
      this.tool2 = new ModelRenderer(this, 26, 0);
      this.tool2.setRotationPoint(0.0F, -3.5F, 0.5F);
      this.tool2.addBox(-1.5F, 0.0F, -1.5F, 3, 2, 3, 0.0F);
      this.wire1 = new ModelRenderer(this, 6, 4);
      this.wire1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.wire1.addBox(0.0F, -3.5F, 1.5F, 0, 6, 2, 0.0F);
      this.wire2 = new ModelRenderer(this, 0, 3);
      this.wire2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.wire2.addBox(0.0F, -8.0F, 0.5F, 0, 9, 3, 0.0F);
      this.setRotateAngle(this.wire2, 0.0F, -0.68294734F, 0.0F);
      this.manA = new ModelRenderer(this, 42, 16);
      this.manA.setRotationPoint(0.0F, 10.0F, -0.01F);
      this.manA.addBox(-3.0F, -2.0F, 5.0F, 6, 8, 4, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 34);
      this.shape1.setRotationPoint(0.0F, 10.0F, 0.0F);
      this.shape1.addBox(-8.0F, 0.0F, -8.0F, 16, 14, 16, -0.01F);
      this.manC = new ModelRenderer(this, 10, 20);
      this.manC.setRotationPoint(0.0F, -3.5F, 0.0F);
      this.manC.addBox(-0.5F, -7.0F, -0.5F, 1, 7, 1, 0.0F);
      this.setRotateAngle(this.manC, 0.7285004F, 0.0F, 0.0F);
      this.tool1a = new ModelRenderer(this, 4, 0);
      this.tool1a.setRotationPoint(0.0F, -1.5F, 0.0F);
      this.tool1a.addBox(-0.5F, -0.5F, -0.5F, 1, 2, 1, 0.0F);
      this.setRotateAngle(this.tool1a, 0.0F, (float) (Math.PI / 4), 0.0F);
      this.manE = new ModelRenderer(this, 17, 0);
      this.manE.setRotationPoint(0.0F, -6.5F, 0.0F);
      this.manE.addBox(-1.0F, -4.5F, -0.5F, 2, 7, 2, 0.0F);
      this.setRotateAngle(this.manE, 1.9577358F, 0.0F, 0.0F);
      this.manB = new ModelRenderer(this, 0, 20);
      this.manB.setRotationPoint(0.0F, -1.5F, 7.0F);
      this.manB.addBox(-1.0F, -4.0F, -1.5F, 2, 5, 3, 0.0F);
      this.setRotateAngle(this.manB, 0.3642502F, 0.0F, 0.0F);
      this.tool1 = new ModelRenderer(this, 0, 0);
      this.tool1.setRotationPoint(0.0F, -4.5F, 0.5F);
      this.tool1.addBox(-0.5F, -4.0F, -0.5F, 1, 4, 1, 0.0F);
      this.manB.addChild(this.manD);
      this.manE.addChild(this.tool2);
      this.manE.addChild(this.wire1);
      this.manC.addChild(this.wire2);
      this.manB.addChild(this.manC);
      this.tool1.addChild(this.tool1a);
      this.manC.addChild(this.manE);
      this.manA.addChild(this.manB);
      this.manE.addChild(this.tool1);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shape2.render(f5);
      this.manA.render(f5);
      this.shape1.render(f5);
   }

   public void setManipulatorAngle(float progress, float toolAngle) {
      this.manB.rotateAngleX = GetMOP.partial(0.4F, -0.48F, progress);
      this.manC.rotateAngleX = GetMOP.partial(1.08F, 1.22F, progress);
      this.manD.rotateAngleX = this.manC.rotateAngleX;
      this.manE.rotateAngleX = GetMOP.partial(toolAngle, 1.85F, progress);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
