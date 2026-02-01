package com.vivern.arpg.elements.models;

import com.vivern.arpg.main.AnimationTimer;
import com.vivern.arpg.renders.ModelRendererGlow;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class TritonHearthModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer tent1;
   public ModelRenderer shape5;
   public ModelRenderer tent2;
   public ModelRenderer tent3;

   public TritonHearthModel() {
      this.textureWidth = 64;
      this.textureHeight = 32;
      this.shape1 = new ModelRenderer(this, 0, 18);
      this.shape1.setRotationPoint(-6.0F, 22.0F, -6.0F);
      this.shape1.addBox(0.0F, 0.0F, 0.0F, 12, 2, 12, 0.0F);
      this.tent2 = new ModelRenderer(this, 0, 3);
      this.tent2.setRotationPoint(8.0F, 0.0F, 0.0F);
      this.tent2.addBox(0.0F, -2.0F, 0.0F, 6, 2, 0, 0.0F);
      this.setRotateAngle(this.tent2, 0.0F, 0.0F, -0.8651597F);
      this.tent1 = new ModelRenderer(this, 0, 6);
      this.tent1.setRotationPoint(0.0F, 12.1F, 0.0F);
      this.tent1.addBox(5.0F, -2.0F, -0.5F, 3, 2, 1, 0.0F);
      this.shape4 = new ModelRenderer(this, 4, 20);
      this.shape4.setRotationPoint(-5.0F, 10.0F, -5.0F);
      this.shape4.addBox(0.0F, 0.0F, 0.0F, 10, 2, 10, 0.0F);
      this.shape3 = new ModelRenderer(this, 32, 0);
      this.shape3.setRotationPoint(-4.0F, 12.0F, -4.0F);
      this.shape3.addBox(0.0F, 0.0F, 0.0F, 8, 10, 8, 0.0F);
      this.shape2 = new ModelRenderer(this, 8, 0);
      this.shape2.setRotationPoint(-3.0F, 11.0F, -3.0F);
      this.shape2.addBox(0.0F, 0.0F, 0.0F, 6, 12, 6, 0.0F);
      this.shape5 = new ModelRendererGlow(this, 36, 18, 200, false);
      this.shape5.setRotationPoint(-2.0F, 8.0F, -2.0F);
      this.shape5.addBox(0.0F, 0.0F, 0.0F, 4, 2, 4, 0.0F);
      this.tent3 = new ModelRenderer(this, 0, 0);
      this.tent3.setRotationPoint(6.0F, -1.0F, 0.01F);
      this.tent3.addBox(0.0F, -2.0F, 0.0F, 6, 2, 0, 0.0F);
      this.setRotateAngle(this.tent3, 0.0F, 0.0F, -1.1383038F);
      this.tent1.addChild(this.tent2);
      this.tent2.addChild(this.tent3);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shape1.render(f5);
      this.shape3.render(f5);
      this.shape2.render(f5);
      this.shape5.render(f5);
      this.shape4.render(f5);

      for (int i = 0; i < 12; i++) {
         this.tent1.rotateAngleY = 0.523599F * i;
         float offset1 = MathHelper.sin(-AnimationTimer.tick / 40.0F + i * 1.678F) * 0.22F;
         float offset2 = MathHelper.sin((-AnimationTimer.tick + 30) / 40.0F + i * 1.678F) * 0.23F;
         float offset3 = MathHelper.sin(-AnimationTimer.tick / 57.0F + i * 1.678F) * 0.17F;
         this.tent2.rotateAngleZ = -0.8651597F + offset1;
         this.tent3.rotateAngleZ = -1.1383038F + offset2;
         this.tent1.rotateAngleX = offset3;
         this.tent1.render(f5);
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
