//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class SpineSegmentModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;

   public SpineSegmentModel() {
      this.textureWidth = 48;
      this.textureHeight = 30;
      this.shape1 = new ModelRenderer(this, 0, 4);
      this.shape1.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.shape1.addBox(-4.0F, -4.0F, -14.0F, 8, 8, 16, 0.0F);
      this.shape2 = new ModelRenderer(this, 0, 0);
      this.shape2.setRotationPoint(0.0F, -2.0F, -8.0F);
      this.shape2.addBox(-1.5F, -17.4F, -1.5F, 3, 16, 4, 0.0F);
      this.setRotateAngle(this.shape2, -0.5462881F, 0.0F, 0.0F);
      this.shape3 = new ModelRenderer(this, 32, 0);
      this.shape3.setRotationPoint(2.0F, 0.0F, -8.0F);
      this.shape3.addBox(-1.5F, -13.0F, -1.5F, 3, 11, 2, 0.0F);
      this.setRotateAngle(this.shape3, -1.0016445F, 1.4114478F, (float) (Math.PI / 2));
      this.shape4 = new ModelRenderer(this, 32, 0);
      this.shape4.setRotationPoint(-2.0F, 0.0F, -8.0F);
      this.shape4.addBox(-1.5F, -13.0F, -1.5F, 3, 11, 2, 0.0F);
      this.setRotateAngle(this.shape4, -1.0016445F, -1.4114478F, (float) (-Math.PI / 2));
      this.shape1.addChild(this.shape2);
      this.shape1.addChild(this.shape3);
      this.shape1.addChild(this.shape4);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      float lbX = OpenGlHelper.lastBrightnessX;
      float lbY = OpenGlHelper.lastBrightnessY;
      f5 *= 1.4F;
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0, -1.2, 0.0);
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, Math.min(240.0F, 100.0F + lbX), Math.min(240.0F, 100.0F + lbY));
      GL11.glEnable(3042);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      this.shape1.render(f5);
      GL11.glDisable(3042);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbX, lbY);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }

   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
      this.shape1.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      this.shape1.rotateAngleX = headPitch * (float) (Math.PI / 180.0) + 0.17F;
   }
}
