package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.AnimationTimer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class BlubberModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shape5;
   public ModelRenderer shape6;
   public ModelRenderer shape7;
   public ModelRenderer shape8;
   public ModelRenderer shape9;
   public ModelRenderer shape10;

   public BlubberModel() {
      this.textureWidth = 25;
      this.textureHeight = 21;
      this.shape1 = new ModelRenderer(this, 0, 1);
      this.shape1.setRotationPoint(0.0F, 17.0F, 0.0F);
      this.shape1.addBox(-3.0F, -3.0F, -3.0F, 6, 4, 6, 0.0F);
      this.shape8 = new ModelRenderer(this, 1, 0);
      this.shape8.setRotationPoint(0.0F, 18.0F, -1.8F);
      this.shape8.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
      this.shape9 = new ModelRenderer(this, 2, 0);
      this.shape9.setRotationPoint(-2.0F, 18.0F, -1.8F);
      this.shape9.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
      this.shape10 = new ModelRenderer(this, 0, 11);
      this.shape10.setRotationPoint(0.0F, 17.0F, 0.0F);
      this.shape10.addBox(-3.0F, -3.0F, -3.0F, 6, 4, 6, 0.8F);
      this.shape2 = new ModelRenderer(this, 0, 0);
      this.shape2.setRotationPoint(0.0F, 18.0F, 1.8F);
      this.shape2.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
      this.shape3 = new ModelRenderer(this, 2, 0);
      this.shape3.setRotationPoint(2.0F, 18.0F, 1.8F);
      this.shape3.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
      this.shape4 = new ModelRenderer(this, 2, 0);
      this.shape4.setRotationPoint(-2.0F, 18.0F, 1.8F);
      this.shape4.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
      this.shape5 = new ModelRenderer(this, 0, 0);
      this.shape5.setRotationPoint(2.0F, 18.0F, 0.0F);
      this.shape5.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
      this.shape6 = new ModelRenderer(this, 1, 0);
      this.shape6.setRotationPoint(-2.0F, 18.0F, 0.0F);
      this.shape6.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
      this.shape7 = new ModelRenderer(this, 3, 0);
      this.shape7.setRotationPoint(2.0F, 18.0F, -1.8F);
      this.shape7.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.shape1.render(f5);
      GlStateManager.pushMatrix();
      GL11.glEnable(3042);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      this.shape8.render(f5);
      this.shape9.render(f5);
      this.shape10.render(f5);
      this.shape2.render(f5);
      this.shape3.render(f5);
      this.shape4.render(f5);
      this.shape5.render(f5);
      this.shape6.render(f5);
      this.shape7.render(f5);
      GL11.glDisable(3042);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }

   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
      float anglee = (float)Math.sin(AnimationTimer.tick / 40.0);
      this.shape2.rotateAngleX = (float)Math.sin((AnimationTimer.tick + 15) / 40.0) / 3.0F;
      this.shape3.rotateAngleX = (float)Math.sin((AnimationTimer.tick + 31) / 40.0) / 3.0F;
      this.shape4.rotateAngleX = (float)Math.sin((AnimationTimer.tick + 44) / 40.0) / 3.0F;
      this.shape5.rotateAngleX = (float)Math.sin((AnimationTimer.tick + 57) / 40.0) / 3.0F;
      this.shape6.rotateAngleX = (float)Math.sin((AnimationTimer.tick + 76) / 40.0) / 3.0F;
      this.shape7.rotateAngleX = (float)Math.sin((AnimationTimer.tick + 99) / 40.0) / 3.0F;
      this.shape8.rotateAngleX = (float)Math.sin((AnimationTimer.tick + 116) / 40.0) / 3.0F;
      this.shape9.rotateAngleX = (float)Math.sin((AnimationTimer.tick + 132) / 40.0) / 3.0F;
      this.shape7.rotateAngleZ = -anglee / 5.0F;
      this.shape5.rotateAngleZ = -anglee / 7.0F;
      this.shape3.rotateAngleY = -anglee / 9.0F;
      this.shape9.rotateAngleZ = anglee / 4.0F;
      this.shape6.rotateAngleZ = anglee / 6.0F;
      this.shape4.rotateAngleY = anglee / 8.0F;
   }
}
