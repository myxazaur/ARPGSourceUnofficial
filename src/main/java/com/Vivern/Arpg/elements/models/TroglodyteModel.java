package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class TroglodyteModel extends ModelBase {
   public ModelRenderer head1;
   public ModelRenderer head2;
   public ModelRenderer head3;
   public ModelRenderer body1;
   public ModelRenderer legaaa1;
   public ModelRenderer legbbb1;
   public ModelRenderer hand1;
   public ModelRenderer hand2;
   public ModelRenderer head4;
   public ModelRenderer light;
   public ModelRenderer body2;
   public ModelRenderer body3;
   public ModelRenderer legaaa2;
   public ModelRenderer legaaa3;
   public ModelRenderer legbbb2;
   public ModelRenderer legbbb3;
   public ModelRenderer head5;

   public TroglodyteModel() {
      this.textureWidth = 56;
      this.textureHeight = 32;
      this.legbbb1 = new ModelRenderer(this, 0, 18);
      this.legbbb1.setRotationPoint(-3.0F, 4.0F, 3.0F);
      this.legbbb1.addBox(-2.0F, -1.5F, -1.5F, 2, 7, 3, 0.0F);
      this.setRotateAngle(this.legbbb1, -0.31869712F, 0.31869712F, 0.045553092F);
      this.legbbb3 = new ModelRenderer(this, 26, 14);
      this.legbbb3.setRotationPoint(0.0F, 14.5F, 0.4F);
      this.legbbb3.addBox(-1.5F, 0.5F, -6.0F, 3, 1, 6, 0.0F);
      this.hand2 = new ModelRenderer(this, 0, 0);
      this.hand2.setRotationPoint(-2.5F, 4.2F, -1.0F);
      this.hand2.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.hand2, -0.22759093F, 0.0F, -0.4098033F);
      this.head2 = new ModelRenderer(this, 0, 20);
      this.head2.setRotationPoint(0.0F, 3.0F, -2.0F);
      this.head2.addBox(-4.0F, -6.0F, -3.0F, 8, 2, 10, 0.0F);
      this.setRotateAngle(this.head2, -0.3642502F, 0.0F, 0.0F);
      this.hand1 = new ModelRenderer(this, 4, 0);
      this.hand1.setRotationPoint(2.5F, 4.2F, -1.0F);
      this.hand1.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
      this.setRotateAngle(this.hand1, -0.22759093F, 0.0F, 0.4098033F);
      this.legaaa2 = new ModelRenderer(this, 50, 14);
      this.legaaa2.setRotationPoint(1.0F, 5.0F, -0.5F);
      this.legaaa2.addBox(-0.5F, 0.0F, -1.0F, 1, 16, 2, 0.0F);
      this.setRotateAngle(this.legaaa2, 0.3642502F, 0.0F, 0.0F);
      this.head3 = new ModelRenderer(this, 8, 14);
      this.head3.setRotationPoint(0.0F, 3.0F, -2.0F);
      this.head3.addBox(-3.0F, -6.42F, -4.5F, 6, 2, 3, 0.0F);
      this.setRotateAngle(this.head3, -0.13665928F, 0.0F, 0.0F);
      this.head1 = new ModelRenderer(this, 0, 0);
      this.head1.setRotationPoint(0.0F, 3.0F, -2.0F);
      this.head1.addBox(-4.0F, -5.0F, -6.0F, 8, 6, 8, 0.0F);
      this.legaaa3 = new ModelRenderer(this, 26, 14);
      this.legaaa3.setRotationPoint(0.0F, 14.5F, 0.4F);
      this.legaaa3.addBox(-1.5F, 0.5F, -6.0F, 3, 1, 6, 0.0F);
      this.legaaa1 = new ModelRenderer(this, 0, 18);
      this.legaaa1.setRotationPoint(3.0F, 4.0F, 3.0F);
      this.legaaa1.addBox(0.0F, -1.5F, -1.5F, 2, 7, 3, 0.0F);
      this.setRotateAngle(this.legaaa1, -0.31869712F, -0.31869712F, -0.045553092F);
      this.body1 = new ModelRenderer(this, 32, 0);
      this.body1.setRotationPoint(0.0F, 3.0F, -2.0F);
      this.body1.addBox(-3.0F, 0.0F, -1.0F, 6, 8, 6, 0.0F);
      this.setRotateAngle(this.body1, (float) (Math.PI / 3), 0.0F, 0.0F);
      this.light = new ModelRenderer(this, 0, 14);
      this.light.setRotationPoint(0.0F, 3.0F, -2.0F);
      this.light.addBox(-1.0F, -6.1F, -11.4F, 2, 2, 2, 0.0F);
      this.body2 = new ModelRenderer(this, 26, 21);
      this.body2.setRotationPoint(0.0F, 6.0F, 0.0F);
      this.body2.addBox(-2.0F, 2.0F, 0.0F, 4, 5, 4, 0.0F);
      this.setRotateAngle(this.body2, 0.27314404F, 0.0F, 0.0F);
      this.body3 = new ModelRenderer(this, 24, 0);
      this.body3.setRotationPoint(0.0F, 6.0F, 0.0F);
      this.body3.addBox(-1.0F, 1.2F, 1.0F, 2, 3, 2, 0.0F);
      this.setRotateAngle(this.body3, 0.27314404F, 0.0F, 0.0F);
      this.head4 = new ModelRenderer(this, 28, 14);
      this.head4.setRotationPoint(0.0F, 3.0F, -2.0F);
      this.head4.addBox(-0.5F, -11.0F, 0.8F, 1, 5, 1, 0.0F);
      this.setRotateAngle(this.head4, 0.63739425F, 0.0F, 0.0F);
      this.head5 = new ModelRenderer(this, 28, 14);
      this.head5.setRotationPoint(0.0F, -10.0F, 1.0F);
      this.head5.addBox(-0.5F, -4.2F, 0.5F, 1, 5, 1, 0.0F);
      this.setRotateAngle(this.head5, 1.8668041F, 0.0F, 0.0F);
      this.legbbb2 = new ModelRenderer(this, 44, 14);
      this.legbbb2.setRotationPoint(-1.0F, 5.0F, -0.5F);
      this.legbbb2.addBox(-0.5F, 0.0F, -1.0F, 1, 16, 2, 0.0F);
      this.setRotateAngle(this.legbbb2, 0.3642502F, 0.0F, 0.0F);
      this.legbbb2.addChild(this.legbbb3);
      this.legaaa1.addChild(this.legaaa2);
      this.legaaa2.addChild(this.legaaa3);
      this.body1.addChild(this.body2);
      this.body2.addChild(this.body3);
      this.head4.addChild(this.head5);
      this.legbbb1.addChild(this.legbbb2);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.legbbb1.render(f5);
      this.hand2.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.head2.offsetX, this.head2.offsetY, this.head2.offsetZ);
      GlStateManager.translate(this.head2.rotationPointX * f5, this.head2.rotationPointY * f5, this.head2.rotationPointZ * f5);
      GlStateManager.scale(0.9, 1.0, 1.0);
      GlStateManager.translate(-this.head2.offsetX, -this.head2.offsetY, -this.head2.offsetZ);
      GlStateManager.translate(-this.head2.rotationPointX * f5, -this.head2.rotationPointY * f5, -this.head2.rotationPointZ * f5);
      this.head2.render(f5);
      GlStateManager.popMatrix();
      this.hand1.render(f5);
      this.head3.render(f5);
      this.head1.render(f5);
      this.legaaa1.render(f5);
      this.body1.render(f5);
      GlStateManager.pushMatrix();
      GL11.glDisable(2896);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 200.0F, 200.0F);
      GlStateManager.translate(this.light.offsetX, this.light.offsetY, this.light.offsetZ);
      GlStateManager.translate(this.light.rotationPointX * f5, this.light.rotationPointY * f5, this.light.rotationPointZ * f5);
      GlStateManager.scale(0.8, 1.0, 0.8);
      GlStateManager.translate(-this.light.offsetX, -this.light.offsetY, -this.light.offsetZ);
      GlStateManager.translate(-this.light.rotationPointX * f5, -this.light.rotationPointY * f5, -this.light.rotationPointZ * f5);
      this.light.render(f5);
      GL11.glEnable(2896);
      GlStateManager.popMatrix();
      this.head4.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }

   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
      this.head1.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      this.head1.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
      this.head2.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      this.head2.rotateAngleX = headPitch * (float) (Math.PI / 180.0) - 0.3642502F;
      this.head3.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      this.head3.rotateAngleX = headPitch * (float) (Math.PI / 180.0) - 0.13665928F;
      this.head4.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      this.head4.rotateAngleX = headPitch * (float) (Math.PI / 180.0) + 0.63739425F;
      this.light.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      this.light.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
      this.legaaa1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount - 0.31869712F;
      this.legbbb1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount - 0.31869712F;
   }
}
