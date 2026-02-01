package com.vivern.arpg.elements.models;

import com.vivern.arpg.main.AnimationTimer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class GnaterModel extends ModelBase {
   public ModelRenderer Head;
   public ModelRenderer body1;
   public ModelRenderer body2;
   public ModelRenderer overlay;
   public ModelRenderer body3;
   public ModelRenderer wing1;
   public ModelRenderer wing2;
   public ModelRenderer body4;
   public ModelRenderer body5;

   public GnaterModel() {
      this.textureWidth = 64;
      this.textureHeight = 28;
      this.body1 = new ModelRenderer(this, 48, 15);
      this.body1.setRotationPoint(0.0F, 15.0F, 0.0F);
      this.body1.addBox(-2.0F, -2.0F, -3.0F, 4, 4, 4, 0.0F);
      this.body5 = new ModelRenderer(this, 41, 1);
      this.body5.setRotationPoint(0.0F, 0.5F, 7.5F);
      this.body5.addBox(-1.0F, -3.1F, 0.0F, 2, 2, 8, 0.0F);
      this.wing2 = new ModelRenderer(this, 0, 19);
      this.wing2.mirror = true;
      this.wing2.setRotationPoint(-3.0F, 0.2F, 2.0F);
      this.wing2.addBox(-15.8F, -0.5F, -3.0F, 16, 1, 8, 0.0F);
      this.setRotateAngle(this.wing2, -0.13665928F, 0.0F, 0.0F);
      this.overlay = new ModelRenderer(this, 18, 0);
      this.overlay.setRotationPoint(0.0F, 15.0F, -3.0F);
      this.overlay.addBox(-3.0F, -2.0F, -8.01F, 6, 1, 1, 0.0F);
      this.body2 = new ModelRenderer(this, 0, 0);
      this.body2.setRotationPoint(0.0F, 15.0F, 0.0F);
      this.body2.addBox(-3.0F, -2.5F, 0.0F, 6, 6, 6, 0.0F);
      this.wing1 = new ModelRenderer(this, 0, 19);
      this.wing1.setRotationPoint(3.0F, 0.2F, 2.0F);
      this.wing1.addBox(-0.2F, -0.5F, -3.0F, 16, 1, 8, 0.0F);
      this.setRotateAngle(this.wing1, -0.13665928F, 0.0F, 0.0F);
      this.body4 = new ModelRenderer(this, 40, 0);
      this.body4.setRotationPoint(0.0F, 0.5F, 7.5F);
      this.body4.addBox(-1.5F, -2.8F, 0.0F, 3, 3, 8, 0.0F);
      this.body3 = new ModelRenderer(this, 40, 0);
      this.body3.setRotationPoint(0.0F, 0.5F, 5.5F);
      this.body3.addBox(-2.0F, -2.5F, 0.0F, 4, 4, 8, 0.0F);
      this.Head = new ModelRenderer(this, 16, 4);
      this.Head.setRotationPoint(0.0F, 15.0F, -3.0F);
      this.Head.addBox(-4.0F, -4.0F, -8.0F, 8, 7, 8, 0.0F);
      this.body4.addChild(this.body5);
      this.body2.addChild(this.wing2);
      this.body2.addChild(this.wing1);
      this.body3.addChild(this.body4);
      this.body2.addChild(this.body3);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.body1.render(f5);
      this.body2.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.Head.offsetX, this.Head.offsetY, this.Head.offsetZ);
      GlStateManager.translate(this.Head.rotationPointX * f5, this.Head.rotationPointY * f5, this.Head.rotationPointZ * f5);
      GlStateManager.scale(0.8, 0.8, 0.8);
      GlStateManager.translate(-this.Head.offsetX, -this.Head.offsetY, -this.Head.offsetZ);
      GlStateManager.translate(-this.Head.rotationPointX * f5, -this.Head.rotationPointY * f5, -this.Head.rotationPointZ * f5);
      this.Head.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GL11.glDisable(2896);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 200.0F, 200.0F);
      GlStateManager.translate(this.overlay.offsetX, this.overlay.offsetY, this.overlay.offsetZ);
      GlStateManager.translate(this.overlay.rotationPointX * f5, this.overlay.rotationPointY * f5, this.overlay.rotationPointZ * f5);
      GlStateManager.scale(0.8, 0.8, 0.8);
      GlStateManager.translate(-this.overlay.offsetX, -this.overlay.offsetY, -this.overlay.offsetZ);
      GlStateManager.translate(-this.overlay.rotationPointX * f5, -this.overlay.rotationPointY * f5, -this.overlay.rotationPointZ * f5);
      this.overlay.render(f5);
      GL11.glEnable(2896);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }

   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
      this.Head.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      this.Head.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
      this.overlay.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      this.overlay.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
      this.wing1.rotateAngleZ = (float)Math.sin(AnimationTimer.tick / 7.0);
      this.wing2.rotateAngleZ = (float)(-Math.sin(AnimationTimer.tick / 7.0));
      float anglee = (float)Math.sin(AnimationTimer.tick / 30.0) * 0.25F;
      this.body3.rotateAngleY = anglee;
      this.body4.rotateAngleY = anglee;
      this.body5.rotateAngleY = anglee;
   }
}
