package com.vivern.arpg.elements.models;

import com.vivern.arpg.renders.ModelRendererGlow;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBiped.ArmPose;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class MerchantModel extends ModelBiped {
   public ModelRenderer bipedRightArm2;
   public ModelRenderer bipedBody2;
   public ModelRenderer bipedPack1;
   public ModelRenderer bipedPack2;
   public ModelRenderer bipedItem1;
   public ModelRenderer bipedItem2;
   public ModelRenderer bipedItem3;
   public ModelRenderer bipedHead2;
   public ModelRenderer bipedHead3;
   public ModelRenderer bipedEye1;
   public ModelRenderer bipedEye2;
   public ModelRenderer monocl;
   public ModelRenderer stick1;
   public ModelRenderer stick2;
   public ModelRenderer glow;
   public ModelRenderer bipedLeftLeg2;
   public ModelRenderer bipedLeftLeg3;
   public ModelRenderer bipedRightLeg2;
   public ModelRenderer bipedRightLeg3;
   public ModelRenderer bipedLeftArm2;

   public MerchantModel() {
      this.boxList.clear();
      this.leftArmPose = ArmPose.EMPTY;
      this.rightArmPose = ArmPose.EMPTY;
      this.textureWidth = 64;
      this.textureHeight = 70;
      this.bipedRightLeg = new ModelRenderer(this, 32, 0);
      this.bipedRightLeg.setRotationPoint(-5.0F, 9.0F, 0.0F);
      this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 8, 5, 0.0F);
      this.setRotateAngle(this.bipedRightLeg, -0.22759093F, 0.0F, 0.0F);
      this.bipedPack1 = new ModelRenderer(this, 28, 45);
      this.bipedPack1.setRotationPoint(0.0F, -3.0F, 8.0F);
      this.bipedPack1.addBox(-5.5F, -0.5F, -3.5F, 11, 8, 7, 0.0F);
      this.setRotateAngle(this.bipedPack1, -0.31869712F, 0.0F, 0.0F);
      this.bipedLeftLeg3 = new ModelRenderer(this, 21, 63);
      this.bipedLeftLeg3.setRotationPoint(-0.1F, 7.0F, 0.0F);
      this.bipedLeftLeg3.addBox(-3.0F, 0.0F, -6.9F, 5, 1, 6, 0.0F);
      this.setRotateAngle(this.bipedLeftLeg3, -0.091106184F, 0.0F, 0.0F);
      this.bipedLeftLeg = new ModelRenderer(this, 32, 0);
      this.bipedLeftLeg.setRotationPoint(5.0F, 9.0F, 0.0F);
      this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 8, 5, 0.0F);
      this.setRotateAngle(this.bipedLeftLeg, -0.22759093F, 0.0F, 0.0F);
      this.bipedItem2 = new ModelRenderer(this, 0, 64);
      this.bipedItem2.setRotationPoint(1.0F, 0.2F, 4.5F);
      this.bipedItem2.addBox(-2.0F, -2.0F, -2.0F, 3, 3, 3, 0.0F);
      this.setRotateAngle(this.bipedItem2, 0.5009095F, 0.091106184F, 0.5009095F);
      this.bipedHead3 = new ModelRenderer(this, 0, 33);
      this.bipedHead3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.bipedHead3.addBox(-3.0F, -4.5F, -4.3F, 6, 6, 6, 0.0F);
      this.setRotateAngle(this.bipedHead3, -0.18203785F, 0.0F, 0.0F);
      this.bipedEye2 = new ModelRenderer(this, 8, 0);
      this.bipedEye2.setRotationPoint(0.0F, 1.0F, -3.1F);
      this.bipedEye2.addBox(0.3F, -9.2F, 0.0F, 3, 2, 3, 0.0F);
      this.setRotateAngle(this.bipedEye2, 0.13665928F, 0.0F, 0.045553092F);
      this.bipedBody2 = new ModelRenderer(this, 28, 13);
      this.bipedBody2.setRotationPoint(0.0F, 6.0F, 0.0F);
      this.bipedBody2.addBox(-5.0F, -0.5F, -3.5F, 10, 8, 8, 0.0F);
      this.setRotateAngle(this.bipedBody2, -0.8196066F, 0.0F, 0.0F);
      this.bipedLeftLeg2 = new ModelRenderer(this, 20, 0);
      this.bipedLeftLeg2.setRotationPoint(-0.9F, 7.0F, 0.0F);
      this.bipedLeftLeg2.addBox(-1.0F, 0.0F, -1.5F, 2, 8, 4, 0.0F);
      this.setRotateAngle(this.bipedLeftLeg2, 0.3642502F, 0.0F, 0.0F);
      this.stick2 = new ModelRenderer(this, 32, 0);
      this.stick2.setRotationPoint(0.05F, -3.8F, 0.0F);
      this.stick2.addBox(-0.5F, -4.0F, -0.5F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.stick2, 0.68294734F, 0.091106184F, -0.045553092F);
      this.stick1 = new ModelRenderer(this, 32, 0);
      this.stick1.setRotationPoint(0.0F, -7.0F, 0.9F);
      this.stick1.addBox(-0.5F, -4.0F, -0.5F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.stick1, 0.63739425F, -0.091106184F, -0.045553092F);
      this.bipedItem3 = new ModelRenderer(this, 0, 20);
      this.bipedItem3.setRotationPoint(3.3F, -0.7F, 3.1F);
      this.bipedItem3.addBox(-1.0F, -5.0F, -1.0F, 2, 5, 2, 0.0F);
      this.setRotateAngle(this.bipedItem3, -0.5009095F, -0.13665928F, 0.3642502F);
      this.bipedRightArm2 = new ModelRenderer(this, 56, 10);
      this.bipedRightArm2.setRotationPoint(-0.8F, 4.0F, 0.5F);
      this.bipedRightArm2.addBox(-1.0F, -0.6F, -1.0F, 2, 7, 2, 0.0F);
      this.setRotateAngle(this.bipedRightArm2, -0.91053826F, -0.27314404F, 0.0F);
      this.bipedLeftArm = new ModelRenderer(this, 50, 0);
      this.bipedLeftArm.setRotationPoint(5.0F, 2.4F, 0.0F);
      this.bipedLeftArm.addBox(-1.0F, -2.0F, -1.5F, 3, 6, 4, 0.0F);
      this.setRotateAngle(this.bipedLeftArm, -0.4098033F, 0.0F, -0.27314404F);
      this.bipedHead2 = new ModelRenderer(this, 0, 21);
      this.bipedHead2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.bipedHead2.addBox(-4.5F, -6.0F, -4.0F, 9, 4, 8, 0.0F);
      this.setRotateAngle(this.bipedHead2, -0.91053826F, 0.0F, 0.0F);
      this.bipedLeftArm2 = new ModelRenderer(this, 56, 10);
      this.bipedLeftArm2.setRotationPoint(0.8F, 4.0F, 0.5F);
      this.bipedLeftArm2.addBox(-1.0F, -0.6F, -1.0F, 2, 7, 2, 0.0F);
      this.setRotateAngle(this.bipedLeftArm2, -0.91053826F, 0.27314404F, 0.0F);
      this.bipedHead = new ModelRenderer(this, 0, 7);
      this.bipedHead.setRotationPoint(0.0F, 1.0F, -2.0F);
      this.bipedHead.addBox(-3.5F, -7.0F, -4.0F, 7, 3, 6, 0.0F);
      this.bipedRightLeg2 = new ModelRenderer(this, 20, 0);
      this.bipedRightLeg2.setRotationPoint(-0.1F, 7.0F, 0.0F);
      this.bipedRightLeg2.addBox(0.0F, 0.0F, -1.5F, 2, 8, 4, 0.0F);
      this.setRotateAngle(this.bipedRightLeg2, 0.3642502F, 0.0F, 0.0F);
      this.monocl = new ModelRenderer(this, 0, 0);
      this.monocl.setRotationPoint(0.0F, 1.0F, -3.1F);
      this.monocl.addBox(-4.0F, -10.2F, -0.5F, 4, 7, 0, 0.0F);
      this.setRotateAngle(this.monocl, 0.13665928F, 0.0F, -0.045553092F);
      this.glow = new ModelRendererGlow(this, 46, 0, 100, true);
      this.glow.setRotationPoint(0.05F, -3.8F, 0.0F);
      this.glow.addBox(-1.0F, -2.0F, -1.5F, 2, 2, 2, 0.0F);
      this.setRotateAngle(this.glow, 0.5009095F, -0.045553092F, -0.045553092F);
      this.bipedPack2 = new ModelRenderer(this, 30, 29);
      this.bipedPack2.setRotationPoint(0.0F, 7.0F, 1.0F);
      this.bipedPack2.addBox(-4.5F, -0.5F, -3.4F, 9, 8, 8, 0.0F);
      this.setRotateAngle(this.bipedPack2, 0.22759093F, 0.0F, 0.0F);
      this.bipedRightLeg3 = new ModelRenderer(this, 21, 63);
      this.bipedRightLeg3.setRotationPoint(-0.1F, 7.0F, 0.0F);
      this.bipedRightLeg3.addBox(-1.0F, 0.0F, -6.9F, 5, 1, 6, 0.0F);
      this.setRotateAngle(this.bipedRightLeg3, -0.091106184F, 0.0F, 0.0F);
      this.bipedBody = new ModelRenderer(this, 0, 51);
      this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 8, 5, 0.0F);
      this.setRotateAngle(this.bipedBody, 0.18203785F, 0.0F, 0.0F);
      this.bipedRightArm = new ModelRenderer(this, 50, 0);
      this.bipedRightArm.setRotationPoint(-5.0F, 2.4F, 0.0F);
      this.bipedRightArm.addBox(-2.0F, -2.0F, -1.5F, 3, 6, 4, 0.0F);
      this.setRotateAngle(this.bipedRightArm, -0.4098033F, 0.0F, 0.27314404F);
      this.bipedItem1 = new ModelRenderer(this, 46, 61);
      this.bipedItem1.setRotationPoint(-3.0F, -2.0F, 4.0F);
      this.bipedItem1.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 4, 0.0F);
      this.setRotateAngle(this.bipedItem1, 0.3642502F, 0.0F, -0.22759093F);
      this.bipedEye1 = new ModelRenderer(this, 8, 0);
      this.bipedEye1.setRotationPoint(0.0F, 1.0F, -3.1F);
      this.bipedEye1.addBox(-3.3F, -9.2F, 0.0F, 3, 2, 3, 0.0F);
      this.setRotateAngle(this.bipedEye1, 0.13665928F, 0.0F, -0.045553092F);
      this.bipedBody.addChild(this.bipedPack1);
      this.bipedLeftLeg2.addChild(this.bipedLeftLeg3);
      this.bipedPack2.addChild(this.bipedItem2);
      this.bipedHead.addChild(this.bipedHead3);
      this.bipedHead.addChild(this.bipedEye2);
      this.bipedBody.addChild(this.bipedBody2);
      this.bipedLeftLeg.addChild(this.bipedLeftLeg2);
      this.stick1.addChild(this.stick2);
      this.bipedHead.addChild(this.stick1);
      this.bipedPack2.addChild(this.bipedItem3);
      this.bipedRightArm.addChild(this.bipedRightArm2);
      this.bipedHead.addChild(this.bipedHead2);
      this.bipedLeftArm.addChild(this.bipedLeftArm2);
      this.bipedRightLeg.addChild(this.bipedRightLeg2);
      this.bipedHead.addChild(this.monocl);
      this.stick2.addChild(this.glow);
      this.bipedPack1.addChild(this.bipedPack2);
      this.bipedRightLeg2.addChild(this.bipedRightLeg3);
      this.bipedPack2.addChild(this.bipedItem1);
      this.bipedHead.addChild(this.bipedEye1);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.bipedRightLeg.render(f5);
      this.bipedLeftLeg.render(f5);
      this.bipedLeftArm.render(f5);
      this.bipedHead.render(f5);
      this.bipedBody.render(f5);
      this.bipedRightArm.render(f5);
   }

   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
      boolean flag = entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).getTicksElytraFlying() > 4;
      this.bipedHead.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      if (flag) {
         this.bipedHead.rotateAngleX = (float) (-Math.PI / 4);
      } else {
         this.bipedHead.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
      }

      this.bipedBody.rotateAngleY = 0.0F;
      float f = 1.0F;
      if (flag) {
         f = (float)(
            entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY + entityIn.motionZ * entityIn.motionZ
         );
         f /= 0.2F;
         f = f * f * f;
      }

      if (f < 1.0F) {
         f = 1.0F;
      }

      this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
      this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
      this.bipedRightArm.rotateAngleZ = 0.0F;
      this.bipedLeftArm.rotateAngleZ = 0.0F;
      this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
      this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / f;
      this.bipedRightLeg.rotateAngleY = 0.0F;
      this.bipedLeftLeg.rotateAngleY = 0.0F;
      this.bipedRightLeg.rotateAngleZ = 0.0F;
      this.bipedLeftLeg.rotateAngleZ = 0.0F;
      this.bipedRightLeg2.rotateAngleX = 0.3642502F;
      this.bipedLeftLeg2.rotateAngleX = 0.3642502F;
      if (this.isRiding) {
         this.bipedRightArm.rotateAngleX += (float) (-Math.PI / 5);
         this.bipedLeftArm.rotateAngleX += (float) (-Math.PI / 5);
         this.bipedRightLeg.rotateAngleX = -1.4137167F;
         this.bipedRightLeg.rotateAngleY = (float) (Math.PI / 10);
         this.bipedRightLeg.rotateAngleZ = 0.07853982F;
         this.bipedLeftLeg.rotateAngleX = -1.4137167F;
         this.bipedLeftLeg.rotateAngleY = (float) (-Math.PI / 10);
         this.bipedLeftLeg.rotateAngleZ = -0.07853982F;
         this.bipedRightLeg2.rotateAngleX += 0.9242502F;
         this.bipedLeftLeg2.rotateAngleX += 0.9242502F;
      }

      this.bipedRightArm.rotateAngleY = 0.0F;
      this.bipedRightArm.rotateAngleZ = 0.0F;
      switch (this.leftArmPose) {
         case EMPTY:
            this.bipedLeftArm.rotateAngleY = 0.0F;
            break;
         case BLOCK:
            this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - 0.9424779F;
            this.bipedLeftArm.rotateAngleY = (float) (Math.PI / 6);
            break;
         case ITEM:
            this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - (float) (Math.PI / 10);
            this.bipedLeftArm.rotateAngleY = 0.0F;
      }

      switch (this.rightArmPose) {
         case EMPTY:
            this.bipedRightArm.rotateAngleY = 0.0F;
            break;
         case BLOCK:
            this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - 0.9424779F;
            this.bipedRightArm.rotateAngleY = (float) (-Math.PI / 6);
            break;
         case ITEM:
            this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - (float) (Math.PI / 10);
            this.bipedRightArm.rotateAngleY = 0.0F;
      }

      if (this.swingProgress > 0.0F) {
         EnumHandSide enumhandside = this.getMainHand(entityIn);
         ModelRenderer modelrenderer = this.getArmForSide(enumhandside);
         float f1 = this.swingProgress;
         this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * (float) (Math.PI * 2)) * 0.2F;
         if (enumhandside == EnumHandSide.LEFT) {
            this.bipedBody.rotateAngleY *= -1.0F;
         }

         this.bipedRightArm.rotateAngleY = this.bipedRightArm.rotateAngleY + this.bipedBody.rotateAngleY;
         this.bipedLeftArm.rotateAngleY = this.bipedLeftArm.rotateAngleY + this.bipedBody.rotateAngleY;
         this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX + this.bipedBody.rotateAngleY;
         f1 = 1.0F - this.swingProgress;
         f1 *= f1;
         f1 *= f1;
         f1 = 1.0F - f1;
         float f2 = MathHelper.sin(f1 * (float) Math.PI);
         float f3 = MathHelper.sin(this.swingProgress * (float) Math.PI) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
         modelrenderer.rotateAngleX = (float)(modelrenderer.rotateAngleX - (f2 * 1.2 + f3));
         modelrenderer.rotateAngleY = modelrenderer.rotateAngleY + this.bipedBody.rotateAngleY * 2.0F;
         modelrenderer.rotateAngleZ = modelrenderer.rotateAngleZ + MathHelper.sin(this.swingProgress * (float) Math.PI) * -0.4F;
      }

      if (this.isSneak) {
         this.bipedBody.rotateAngleX = 0.5F;
         this.bipedRightArm.rotateAngleX += 0.4F;
         this.bipedLeftArm.rotateAngleX += 0.4F;
      } else {
         this.bipedBody.rotateAngleX = 0.0F;
      }

      this.bipedRightArm.rotateAngleZ = this.bipedRightArm.rotateAngleZ + (MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F);
      this.bipedLeftArm.rotateAngleZ = this.bipedLeftArm.rotateAngleZ - (MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F);
      this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX + MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
      this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX - MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
      if (this.rightArmPose == ArmPose.BOW_AND_ARROW) {
         this.bipedRightArm.rotateAngleY = -0.1F + this.bipedHead.rotateAngleY;
         this.bipedLeftArm.rotateAngleY = 0.1F + this.bipedHead.rotateAngleY + 0.4F;
         this.bipedRightArm.rotateAngleX = (float) (-Math.PI / 2) + this.bipedHead.rotateAngleX;
         this.bipedLeftArm.rotateAngleX = (float) (-Math.PI / 2) + this.bipedHead.rotateAngleX;
      } else if (this.leftArmPose == ArmPose.BOW_AND_ARROW) {
         this.bipedRightArm.rotateAngleY = -0.1F + this.bipedHead.rotateAngleY - 0.4F;
         this.bipedLeftArm.rotateAngleY = 0.1F + this.bipedHead.rotateAngleY;
         this.bipedRightArm.rotateAngleX = (float) (-Math.PI / 2) + this.bipedHead.rotateAngleX;
         this.bipedLeftArm.rotateAngleX = (float) (-Math.PI / 2) + this.bipedHead.rotateAngleX;
      }

      this.bipedRightLeg.rotateAngleX += -0.22759093F;
      this.bipedLeftLeg.rotateAngleX += -0.22759093F;
      this.bipedBody.rotateAngleX += 0.18203785F;
      this.bipedRightArm.rotateAngleX += -0.4098033F;
      this.bipedLeftArm.rotateAngleX += -0.4098033F;
      this.bipedRightArm.rotateAngleZ += 0.27314404F;
      this.bipedLeftArm.rotateAngleZ += -0.27314404F;
      copyModelAngles(this.bipedHead, this.bipedHeadwear);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
