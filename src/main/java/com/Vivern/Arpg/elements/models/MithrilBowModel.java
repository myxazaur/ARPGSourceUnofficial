package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.GetMOP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class MithrilBowModel extends ModelBase {
   public ModelRenderer shapeMAIN;
   public ModelRenderer shapeUP1;
   public ModelRenderer shapeDOWN1;
   public ModelRenderer shapeUP2;
   public ModelRenderer ropeUP;
   public ModelRenderer shapeDOWN2;
   public ModelRenderer ropeDOWN;

   public MithrilBowModel() {
      this.textureWidth = 16;
      this.textureHeight = 16;
      this.shapeMAIN = new ModelRenderer(this, 2, 0);
      this.shapeMAIN.setRotationPoint(0.0F, 3.5F, 5.0F);
      this.shapeMAIN.addBox(-1.0F, -4.0F, -1.1F, 2, 8, 2, 0.0F);
      this.shapeDOWN2 = new ModelRenderer(this, 12, 1);
      this.shapeDOWN2.setRotationPoint(0.0F, -5.5F, 0.0F);
      this.shapeDOWN2.addBox(-0.5F, -8.0F, -0.5F, 1, 8, 1, 0.0F);
      this.setRotateAngle(this.shapeDOWN2, -0.7740535F, 0.0F, 0.0F);
      this.ropeDOWN = new ModelRenderer(this, 0, -1);
      this.ropeDOWN.setRotationPoint(0.0F, -7.5F, 0.0F);
      this.ropeDOWN.addBox(0.0F, 0.0F, -0.5F, 0, 16, 1, 0.0F);
      this.setRotateAngle(this.ropeDOWN, 0.59184116F, 0.0F, 0.0F);
      this.ropeUP = new ModelRenderer(this, 0, -1);
      this.ropeUP.setRotationPoint(0.0F, -7.5F, 0.0F);
      this.ropeUP.addBox(0.0F, 0.0F, -0.5F, 0, 16, 1, 0.0F);
      this.setRotateAngle(this.ropeUP, 0.59184116F, 0.0F, 0.0F);
      this.shapeUP1 = new ModelRenderer(this, 8, 8);
      this.shapeUP1.setRotationPoint(0.0F, 0.0F, 5.0F);
      this.shapeUP1.addBox(-0.5F, -6.0F, -1.0F, 1, 6, 2, 0.05F);
      this.setRotateAngle(this.shapeUP1, 0.18203785F, 0.0F, 0.0F);
      this.shapeUP2 = new ModelRenderer(this, 12, 1);
      this.shapeUP2.setRotationPoint(0.0F, -5.5F, 0.0F);
      this.shapeUP2.addBox(-0.5F, -8.0F, -0.5F, 1, 8, 1, 0.0F);
      this.setRotateAngle(this.shapeUP2, -0.7740535F, 0.0F, 0.0F);
      this.shapeDOWN1 = new ModelRenderer(this, 8, 8);
      this.shapeDOWN1.setRotationPoint(0.0F, 7.0F, 5.0F);
      this.shapeDOWN1.addBox(-0.5F, -6.0F, -1.0F, 1, 6, 2, 0.05F);
      this.setRotateAngle(this.shapeDOWN1, 0.18203785F, 0.0F, (float) Math.PI);
      this.shapeDOWN1.addChild(this.shapeDOWN2);
      this.shapeDOWN2.addChild(this.ropeDOWN);
      this.shapeUP2.addChild(this.ropeUP);
      this.shapeUP1.addChild(this.shapeUP2);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shapeUP1.rotateAngleX = GetMOP.partial(0.045553092F, 0.18203785F, f);
      this.shapeUP2.rotateAngleX = GetMOP.partial(-0.8651597F, -0.7740535F, f);
      this.ropeUP.rotateAngleX = GetMOP.partial(1.2292354F, 0.59184116F, f);
      this.ropeDOWN.rotateAngleX = this.ropeUP.rotateAngleX;
      this.shapeDOWN1.rotateAngleX = this.shapeUP1.rotateAngleX;
      this.shapeDOWN2.rotateAngleX = this.shapeUP2.rotateAngleX;
      this.ropeUP.isHidden = f1 > 0.0F;
      this.ropeDOWN.isHidden = f1 > 0.0F;
      this.shapeMAIN.render(f5);
      this.shapeUP1.render(f5);
      this.shapeDOWN1.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
