package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.entity.AbstractGlyphid;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.mobs.AbstractMob;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class CinderCrawlerModel extends ModelBase {
   public ModelRenderer neck;
   public ModelRenderer head;
   public ModelRenderer shape1;
   public ModelRenderer leg1;
   public ModelRenderer leg3;
   public ModelRenderer leg5;
   public ModelRenderer leg7;
   public ModelRenderer legg1;
   public ModelRenderer legg2;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer leg2;
   public ModelRenderer leg4;
   public ModelRenderer leg6;
   public ModelRenderer leg8;

   public CinderCrawlerModel() {
      this.textureWidth = 64;
      this.textureHeight = 32;
      this.leg5 = new ModelRenderer(this, 18, 0);
      this.leg5.setRotationPoint(3.0F, 16.0F, 4.0F);
      this.leg5.addBox(-1.0F, -1.0F, -1.0F, 9, 2, 2, 0.0F);
      this.setRotateAngle(this.leg5, 0.0F, (float) (-Math.PI / 3), -0.27314404F);
      this.leg2 = new ModelRenderer(this, 28, 22);
      this.leg2.setRotationPoint(8.0F, 0.9F, 0.0F);
      this.leg2.addBox(-1.0F, -1.0F, -1.0F, 16, 3, 2, 0.0F);
      this.setRotateAngle(this.leg2, 0.0F, 0.3642502F, 1.4570009F);
      this.leg8 = new ModelRenderer(this, 28, 22);
      this.leg8.setRotationPoint(8.0F, 0.9F, 0.0F);
      this.leg8.addBox(-1.0F, -1.0F, -1.0F, 16, 3, 2, 0.0F);
      this.setRotateAngle(this.leg8, 0.0F, 0.3642502F, 1.4570009F);
      this.shape2 = new ModelRenderer(this, 1, 12);
      this.shape2.setRotationPoint(0.5F, 0.0F, 8.0F);
      this.shape2.addBox(-2.0F, -2.0F, -1.0F, 3, 3, 9, 0.0F);
      this.setRotateAngle(this.shape2, 0.5009095F, 0.0F, 0.0F);
      this.legg1 = new ModelRenderer(this, 40, 0);
      this.legg1.setRotationPoint(4.0F, 16.0F, 1.0F);
      this.legg1.addBox(-1.0F, -1.0F, -1.0F, 6, 2, 2, 0.0F);
      this.setRotateAngle(this.legg1, 0.0F, 0.22759093F, 0.091106184F);
      this.shape1 = new ModelRenderer(this, 0, 12);
      this.shape1.setRotationPoint(0.0F, 15.0F, 5.0F);
      this.shape1.addBox(-2.0F, -2.0F, -1.0F, 4, 4, 9, 0.0F);
      this.setRotateAngle(this.shape1, 0.5009095F, 0.0F, 0.0F);
      this.leg3 = new ModelRenderer(this, 18, 0);
      this.leg3.setRotationPoint(-4.0F, 16.0F, -1.0F);
      this.leg3.addBox(-1.0F, -1.0F, -1.0F, 9, 2, 2, 0.0F);
      this.setRotateAngle(this.leg3, 0.0F, 2.4586453F, 0.27314404F);
      this.shape3 = new ModelRenderer(this, 2, 13);
      this.shape3.setRotationPoint(0.5F, 0.0F, 8.0F);
      this.shape3.addBox(-2.0F, -2.0F, -1.0F, 2, 2, 9, 0.0F);
      this.setRotateAngle(this.shape3, 1.0016445F, 0.0F, 0.0F);
      this.leg7 = new ModelRenderer(this, 18, 0);
      this.leg7.setRotationPoint(-3.0F, 16.0F, 4.0F);
      this.leg7.addBox(-1.0F, -1.0F, -1.0F, 9, 2, 2, 0.0F);
      this.setRotateAngle(this.leg7, 0.0F, (float) (-Math.PI * 2.0 / 3.0), 0.27314404F);
      this.leg6 = new ModelRenderer(this, 28, 22);
      this.leg6.setRotationPoint(8.0F, 0.9F, 0.0F);
      this.leg6.addBox(-1.0F, -1.0F, -1.0F, 16, 3, 2, 0.0F);
      this.setRotateAngle(this.leg6, 0.0F, -0.3642502F, 1.4570009F);
      this.leg1 = new ModelRenderer(this, 18, 0);
      this.leg1.setRotationPoint(4.0F, 16.0F, -1.0F);
      this.leg1.addBox(-1.0F, -1.0F, -1.0F, 9, 2, 2, 0.0F);
      this.setRotateAngle(this.leg1, 0.0F, 0.68294734F, -0.27314404F);
      this.leg4 = new ModelRenderer(this, 28, 22);
      this.leg4.setRotationPoint(8.0F, 0.9F, 0.0F);
      this.leg4.addBox(-1.0F, -1.0F, -1.0F, 16, 3, 2, 0.0F);
      this.setRotateAngle(this.leg4, 0.0F, -0.3642502F, 1.4570009F);
      this.neck = new ModelRenderer(this, 0, 0);
      this.neck.setRotationPoint(0.0F, 15.0F, 0.0F);
      this.neck.addBox(-3.0F, -3.0F, -1.0F, 6, 6, 6, 0.0F);
      this.head = new ModelRenderer(this, 28, 4);
      this.head.setRotationPoint(0.0F, 15.0F, -3.0F);
      this.head.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 10, 0.0F);
      this.legg2 = new ModelRenderer(this, 40, 0);
      this.legg2.setRotationPoint(-4.0F, 16.0F, 1.0F);
      this.legg2.addBox(-1.0F, -1.0F, -1.0F, 6, 2, 2, 0.0F);
      this.setRotateAngle(this.legg2, 0.0F, 2.9140017F, -0.091106184F);
      this.leg1.addChild(this.leg2);
      this.leg7.addChild(this.leg8);
      this.shape1.addChild(this.shape2);
      this.shape2.addChild(this.shape3);
      this.leg5.addChild(this.leg6);
      this.leg3.addChild(this.leg4);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      float angleX = 0.0F;
      float angleY = 0.0F;
      float angleZ = 0.0F;
      float angleGl = 0.0F;
      float angleVecGl = 0.0F;
      if (entity instanceof AbstractGlyphid) {
         AbstractGlyphid glyp = (AbstractGlyphid)entity;
         float pt = Minecraft.getMinecraft().getRenderPartialTicks();
         angleX = GetMOP.partial(glyp.renderangleX, glyp.prevrenderangleX, pt);
         angleY = GetMOP.partial(glyp.renderangleY, glyp.prevrenderangleY, pt);
         angleZ = GetMOP.partial(glyp.renderangleZ, glyp.prevrenderangleZ, pt);
         angleGl = GetMOP.partial(glyp.renderangle, glyp.prevrenderangle, pt);
         angleVecGl = GetMOP.partial(glyp.renderanglevec, glyp.prevrenderanglevec, pt);
      }

      float angle = 0.0F;
      if (!entity.isRiding()) {
         float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
         AbstractMob entitylb = (AbstractMob)entity;
         float qf = ModelsToxicomaniaMob.interpolateRotation(entitylb.prevRenderYawOffset, entitylb.renderYawOffset, partialTicks);
         angle = 180.0F - qf;
      }

      this.setRotateAngle(this.leg5, 0.0F, (float) (-Math.PI / 3), -0.27314404F);
      this.setRotateAngle(this.leg2, 0.0F, 0.3642502F, 1.4570009F);
      this.setRotateAngle(this.leg8, 0.0F, 0.3642502F, 1.4570009F);
      this.setRotateAngle(this.leg3, 0.0F, 2.4586453F, 0.27314404F);
      this.setRotateAngle(this.leg7, 0.0F, (float) (-Math.PI * 2.0 / 3.0), 0.27314404F);
      this.setRotateAngle(this.leg6, 0.0F, -0.3642502F, 1.4570009F);
      this.setRotateAngle(this.leg1, 0.0F, 0.68294734F, -0.27314404F);
      this.setRotateAngle(this.leg4, 0.0F, -0.3642502F, 1.4570009F);
      float legsAnim = f * 0.65F;
      float legsV = MathHelper.clamp(MathHelper.cos(legsAnim), -0.2F, 1.0F) * 0.45F;
      float legsH = MathHelper.sin(legsAnim) * 0.25F;
      float legsV2 = MathHelper.clamp(MathHelper.cos(legsAnim + (float) Math.PI), -0.2F, 1.0F) * 0.45F;
      float legsH2 = MathHelper.sin(legsAnim + (float) Math.PI) * 0.25F;
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      GlStateManager.pushMatrix();
      GlStateManager.rotate(angle, 0.0F, 1.0F, 0.0F);
      GlStateManager.translate(0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(angleGl, angleX, angleY, angleZ);
      GlStateManager.translate(0.0F, -1.0F, 0.0F);
      GlStateManager.rotate(angleVecGl, 0.0F, 1.0F, 0.0F);
      this.leg1.rotateAngleZ -= legsV;
      this.leg2.rotateAngleZ += legsV;
      this.leg1.rotateAngleX -= legsH;
      this.leg1.rotateAngleY += legsH;
      this.leg2.rotateAngleY += legsH;
      this.leg3.rotateAngleZ += legsV2;
      this.leg4.rotateAngleZ += legsV2;
      this.leg3.rotateAngleX += legsH2;
      this.leg3.rotateAngleY -= legsH2;
      this.leg4.rotateAngleY -= legsH2;
      this.leg5.rotateAngleZ -= legsV2;
      this.leg5.rotateAngleX += legsV2;
      this.leg5.rotateAngleY += legsH2;
      this.leg6.rotateAngleZ += legsV2;
      this.leg6.rotateAngleY += legsH2;
      this.leg6.rotateAngleZ += legsH2;
      this.leg7.rotateAngleZ += legsV;
      this.leg7.rotateAngleX -= legsV;
      this.leg7.rotateAngleY -= legsH;
      this.leg8.rotateAngleZ += legsV;
      this.leg8.rotateAngleY -= legsH;
      this.leg8.rotateAngleZ += legsH;
      this.leg5.render(f5);
      this.legg1.render(f5);
      this.shape1.render(f5);
      this.leg3.render(f5);
      this.leg7.render(f5);
      this.leg1.render(f5);
      this.neck.render(f5);
      this.head.render(f5);
      this.legg2.render(f5);
      GlStateManager.popMatrix();
   }

   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
      this.head.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
      this.head.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      this.legg1.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount + 0.22759093F;
      this.legg2.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount + 2.9140017F;
      this.legg1.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount + 0.22759093F;
      this.legg2.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount + 2.9140017F;
      this.shape1.rotateAngleX = MathHelper.cos(ageInTicks / 30.0F) / 2.0F + 0.5009095F;
      this.shape2.rotateAngleX = MathHelper.cos(ageInTicks / 30.0F) / 3.0F + 0.5009095F;
      this.shape3.rotateAngleX = MathHelper.cos(ageInTicks / 30.0F) / 4.0F + 1.0016445F;
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
