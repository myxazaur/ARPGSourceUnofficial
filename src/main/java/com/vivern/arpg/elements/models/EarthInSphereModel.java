package com.vivern.arpg.elements.models;

import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.main.AnimationTimer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class EarthInSphereModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shape5;
   public ModelRenderer shape6;
   public ModelRenderer shape7;
   public ModelRenderer shape8;
   public ModelRenderer shape9;

   public EarthInSphereModel() {
      this.textureWidth = 16;
      this.textureHeight = 16;
      this.shape4 = new ModelRenderer(this, 7, 0);
      this.shape4.setRotationPoint(0.0F, 18.0F, 0.0F);
      this.shape4.addBox(-1.0F, -1.25F, -2.0F, 1, 2, 1, 0.0F);
      this.shape1 = new ModelRenderer(this, 1, 5);
      this.shape1.setRotationPoint(0.0F, 18.0F, 0.0F);
      this.shape1.addBox(-1.0F, -2.01F, -1.0F, 2, 3, 2, 0.0F);
      this.shape6 = new ModelRenderer(this, 10, 1);
      this.shape6.setRotationPoint(0.0F, 18.0F, 0.0F);
      this.shape6.addBox(-1.75F, -1.5F, -0.5F, 1, 2, 2, 0.0F);
      this.shape2 = new ModelRenderer(this, 1, 5);
      this.shape2.setRotationPoint(0.0F, 18.0F, 0.0F);
      this.shape2.addBox(-0.25F, -1.0F, -1.5F, 2, 1, 2, 0.0F);
      this.shape7 = new ModelRenderer(this, 10, 5);
      this.shape7.setRotationPoint(0.0F, 18.0F, 0.0F);
      this.shape7.addBox(-2.0F, 0.5F, 0.0F, 2, 1, 1, 0.0F);
      this.shape9 = new ModelRenderer(this, 7, 0);
      this.shape9.setRotationPoint(0.0F, 18.0F, 0.0F);
      this.shape9.addBox(-1.5F, 0.25F, -1.01F, 1, 1, 1, 0.0F);
      this.shape8 = new ModelRenderer(this, 1, 5);
      this.shape8.setRotationPoint(0.0F, 18.0F, 0.0F);
      this.shape8.addBox(-0.5F, 0.75F, -0.5F, 2, 1, 2, 0.0F);
      this.shape3 = new ModelRenderer(this, 0, 0);
      this.shape3.setRotationPoint(0.0F, 18.0F, 0.0F);
      this.shape3.addBox(0.0F, 0.0F, -1.75F, 2, 1, 3, 0.0F);
      this.shape5 = new ModelRenderer(this, 10, 5);
      this.shape5.setRotationPoint(0.0F, 18.0F, 0.0F);
      this.shape5.addBox(-0.75F, -1.25F, 0.75F, 2, 2, 1, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      float time = AnimationTimer.tick / 100.0F;
      float mult = 0.015625F + Debugger.floats[0];
      this.shape1.offsetY = MathHelper.sin(time) * mult;
      this.shape2.offsetZ = MathHelper.sin(time + 15.356113F) * mult;
      this.shape3.offsetX = MathHelper.sin(time + 8.546456F) * mult;
      this.shape4.offsetX = -MathHelper.sin(time + 10.31546F) * mult;
      this.shape5.offsetZ = MathHelper.sin(time + 17.436346F) * mult;
      this.shape6.offsetX = MathHelper.sin(time + 35.32416F) * mult;
      this.shape7.offsetZ = MathHelper.sin(time + 24.15878F) * mult;
      this.shape8.offsetY = -MathHelper.sin(time + 13.135636F) * mult;
      this.shape9.offsetY = MathHelper.sin(time + 3.3545535F) * mult;
      this.shape4.render(f5);
      this.shape1.render(f5);
      this.shape6.render(f5);
      this.shape2.render(f5);
      this.shape7.render(f5);
      this.shape9.render(f5);
      this.shape8.render(f5);
      this.shape3.render(f5);
      this.shape5.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
