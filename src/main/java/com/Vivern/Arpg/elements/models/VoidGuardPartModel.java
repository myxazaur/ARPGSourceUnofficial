package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class VoidGuardPartModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shape5;

   public VoidGuardPartModel() {
      this.textureWidth = 32;
      this.textureHeight = 16;
      this.shape2 = new ModelRenderer(this, 0, 0);
      this.shape2.setRotationPoint(0.0F, -2.0F, -8.0F);
      this.shape2.addBox(-0.5F, -7.0F, -0.5F, 1, 8, 1, 0.0F);
      this.setRotateAngle(this.shape2, -1.0016445F, 0.0F, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(0.0F, 22.0F, 0.0F);
      this.shape1.addBox(-2.0F, -2.0F, -10.0F, 4, 4, 12, 0.1F);
      this.shape4 = new ModelRenderer(this, 20, 0);
      this.shape4.setRotationPoint(-2.0F, 0.0F, -8.0F);
      this.shape4.addBox(-0.5F, -7.0F, -0.5F, 1, 8, 1, 0.0F);
      this.setRotateAngle(this.shape4, -1.0016445F, 0.0F, (float) (-Math.PI / 2));
      this.shape5 = new ModelRenderer(this, 24, 0);
      this.shape5.setRotationPoint(0.0F, 2.0F, -8.0F);
      this.shape5.addBox(-0.5F, -7.0F, -0.5F, 1, 8, 1, 0.0F);
      this.setRotateAngle(this.shape5, -2.1399481F, 0.0F, 0.0F);
      this.shape3 = new ModelRenderer(this, 4, 0);
      this.shape3.setRotationPoint(2.0F, 0.0F, -8.0F);
      this.shape3.addBox(-0.5F, -7.0F, -0.5F, 1, 8, 1, 0.0F);
      this.setRotateAngle(this.shape3, -1.0016445F, 0.0F, (float) (Math.PI / 2));
      this.shape1.addChild(this.shape2);
      this.shape1.addChild(this.shape4);
      this.shape1.addChild(this.shape5);
      this.shape1.addChild(this.shape3);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      GL11.glEnable(3042);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      this.shape1.render(f5);
      GL11.glDisable(3042);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }

   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
      this.shape1.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      this.shape1.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
   }
}
