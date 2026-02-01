package com.vivern.arpg.elements.models;

import com.vivern.arpg.renders.ModelRendererGlow;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class IndustrialMixerModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer rotor1;
   public ModelRenderer rotor3;
   public ModelRenderer shape2;
   public ModelRenderer shapeGlow;
   public ModelRenderer rotor2;
   public ModelRenderer rotor4;

   public IndustrialMixerModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.shape1 = new ModelRenderer(this, 14, 0);
      this.shape1.setRotationPoint(0.0F, 13.0F, 0.0F);
      this.shape1.addBox(-6.0F, 0.0F, -6.0F, 12, 10, 12, 0.0F);
      this.shapeGlow = new ModelRendererGlow(this, 20, 49, 60, true);
      this.shapeGlow.setRotationPoint(0.0F, 8.0F, 0.0F);
      this.shapeGlow.addBox(-3.0F, 2.0F, -8.3F, 6, 3, 1, 0.0F);
      this.shape2 = new ModelRenderer(this, 0, 32);
      this.shape2.setRotationPoint(0.0F, 8.0F, 0.0F);
      this.shape2.addBox(-8.0F, 0.0F, -8.0F, 16, 16, 16, -0.01F);
      this.rotor1 = new ModelRenderer(this, 0, 5);
      this.rotor1.setRotationPoint(0.0F, 11.0F, 0.0F);
      this.rotor1.addBox(-6.0F, 0.0F, -0.5F, 12, 2, 1, 0.0F);
      this.rotor4 = new ModelRenderer(this, 0, 0);
      this.rotor4.setRotationPoint(0.0F, 0.01F, 0.0F);
      this.rotor4.addBox(-6.0F, 0.0F, -0.5F, 12, 2, 1, 0.0F);
      this.setRotateAngle(this.rotor4, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.rotor3 = new ModelRenderer(this, 0, 0);
      this.rotor3.setRotationPoint(0.0F, 9.0F, 0.0F);
      this.rotor3.addBox(-6.0F, 0.0F, -0.5F, 12, 2, 1, 0.0F);
      this.setRotateAngle(this.rotor3, 0.0F, 0.8196066F, 0.0F);
      this.rotor2 = new ModelRenderer(this, 0, 5);
      this.rotor2.setRotationPoint(0.0F, 0.01F, 0.0F);
      this.rotor2.addBox(-6.0F, 0.0F, -0.5F, 12, 2, 1, 0.0F);
      this.setRotateAngle(this.rotor2, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.rotor3.addChild(this.rotor4);
      this.rotor1.addChild(this.rotor2);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.rotor1.rotateAngleY = f;
      this.rotor3.rotateAngleY = -f * 0.8F;
      this.shape1.render(f5);
      this.rotor1.render(f5);
      this.rotor3.render(f5);
      this.shape2.render(f5);
      this.shapeGlow.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
