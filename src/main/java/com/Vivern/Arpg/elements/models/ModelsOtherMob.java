package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.mobs.AbstractMob;
import com.Vivern.Arpg.mobs.OtherMobsPack;
import com.Vivern.Arpg.renders.ModelRendererGlow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBiped.ArmPose;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelsOtherMob {
   public static class BristlingModel extends ModelBiped {
      public ModelRenderer bipedTail1;
      public ModelRenderer bipedBodyWear;
      public ModelRenderer bipedRightLegWear;
      public ModelRenderer bipedLeftLegWear;
      public ModelRenderer bipedRightArmWear;
      public ModelRenderer bipedLeftArmWear;
      public ModelRenderer bipedHead2;
      public ModelRenderer bipedEar1;
      public ModelRenderer bipedEar2;
      public ModelRenderer bipedEarFur2;
      public ModelRenderer bipedEarFur1;
      public ModelRenderer bipedVibriss2;
      public ModelRenderer bipedEyes;
      public ModelRenderer bipedFur1;
      public ModelRenderer bipedVibriss1;
      public ModelRenderer bipedFur2;
      public ModelRenderer bipedTail2;
      public ModelRenderer bipedTail3;

      public BristlingModel() {
         this.textureWidth = 64;
         this.textureHeight = 48;
         this.bipedLeftLeg = new ModelRenderer(this, 48, 12);
         this.bipedLeftLeg.setRotationPoint(1.9F, 10.0F, 0.0F);
         this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 14, 4, 0.0F);
         this.bipedEarFur1 = new ModelRenderer(this, 38, 0);
         this.bipedEarFur1.mirror = true;
         this.bipedEarFur1.setRotationPoint(1.0F, -8.0F, 1.0F);
         this.bipedEarFur1.addBox(-0.5F, -4.0F, -0.5F, 5, 5, 0, 0.0F);
         this.setRotateAngle(this.bipedEarFur1, 0.18203785F, 0.0F, 0.13665928F);
         this.bipedVibriss1 = new ModelRenderer(this, 38, 6);
         this.bipedVibriss1.mirror = true;
         this.bipedVibriss1.setRotationPoint(1.0F, -1.7F, -4.9F);
         this.bipedVibriss1.addBox(0.0F, 0.0F, 0.0F, 5, 5, 0, 0.0F);
         this.setRotateAngle(this.bipedVibriss1, -0.31869712F, 0.0F, -0.4098033F);
         this.bipedHead = new ModelRenderer(this, 0, 0);
         this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
         this.bipedFur1 = new ModelRenderer(this, 0, 0);
         this.bipedFur1.mirror = true;
         this.bipedFur1.setRotationPoint(4.0F, -3.3F, 1.7F);
         this.bipedFur1.addBox(0.0F, -3.0F, 0.0F, 4, 6, 0, 0.0F);
         this.setRotateAngle(this.bipedFur1, -0.5462881F, -0.63739425F, 0.3642502F);
         this.bipedEyes = new ModelRendererGlow(this, 24, 7, 80, true);
         this.bipedEyes.setRotationPoint(0.0F, -6.0F, -4.05F);
         this.bipedEyes.addBox(-3.0F, 0.0F, 0.0F, 6, 1, 0, 0.0F);
         this.bipedBodyWear = new ModelRenderer(this, 0, 33);
         this.bipedBodyWear.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.bipedBodyWear.addBox(-4.0F, 0.0F, -2.0F, 8, 10, 5, 0.3F);
         this.bipedBody = new ModelRenderer(this, 0, 16);
         this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 10, 5, 0.0F);
         this.bipedRightArm = new ModelRenderer(this, 26, 16);
         this.bipedRightArm.mirror = true;
         this.bipedRightArm.setRotationPoint(-4.0F, 2.0F, 0.0F);
         this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 3, 12, 4, 0.0F);
         this.bipedRightLeg = new ModelRenderer(this, 48, 12);
         this.bipedRightLeg.mirror = true;
         this.bipedRightLeg.setRotationPoint(-1.9F, 10.0F, 0.0F);
         this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 14, 4, 0.0F);
         this.bipedFur2 = new ModelRenderer(this, 0, 0);
         this.bipedFur2.setRotationPoint(-4.0F, -3.3F, 1.7F);
         this.bipedFur2.addBox(-4.0F, -3.0F, 0.0F, 4, 6, 0, 0.0F);
         this.setRotateAngle(this.bipedFur2, -0.5462881F, 0.63739425F, -0.3642502F);
         this.bipedLeftLegWear = new ModelRenderer(this, 47, 30);
         this.bipedLeftLegWear.setRotationPoint(1.9F, 10.0F, 0.01F);
         this.bipedLeftLegWear.addBox(-2.0F, 0.0F, -2.0F, 4, 14, 4, 0.2F);
         this.bipedEar1 = new ModelRenderer(this, 22, 17);
         this.bipedEar1.setRotationPoint(1.0F, -8.0F, 1.0F);
         this.bipedEar1.addBox(1.0F, -2.0F, -1.0F, 2, 2, 1, 0.0F);
         this.setRotateAngle(this.bipedEar1, 0.18203785F, 0.0F, 0.13665928F);
         this.bipedTail1 = new ModelRenderer(this, 40, 24);
         this.bipedTail1.setRotationPoint(0.0F, 9.0F, 2.0F);
         this.bipedTail1.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
         this.setRotateAngle(this.bipedTail1, 0.4098033F, 0.0F, 0.0F);
         this.bipedRightArmWear = new ModelRenderer(this, 26, 32);
         this.bipedRightArmWear.mirror = true;
         this.bipedRightArmWear.setRotationPoint(-4.0F, 2.0F, 0.0F);
         this.bipedRightArmWear.addBox(-3.0F, -2.0F, -2.0F, 3, 12, 4, 0.28F);
         this.bipedLeftArmWear = new ModelRenderer(this, 26, 32);
         this.bipedLeftArmWear.setRotationPoint(4.0F, 2.0F, 0.0F);
         this.bipedLeftArmWear.addBox(0.0F, -2.0F, -2.0F, 3, 12, 4, 0.28F);
         this.bipedTail3 = new ModelRenderer(this, 48, 0);
         this.bipedTail3.setRotationPoint(0.0F, 5.0F, 0.0F);
         this.bipedTail3.addBox(-2.0F, 0.0F, -2.7F, 4, 6, 4, 0.0F);
         this.setRotateAngle(this.bipedTail3, 0.4098033F, 0.0F, 0.0F);
         this.bipedLeftArm = new ModelRenderer(this, 26, 16);
         this.bipedLeftArm.setRotationPoint(4.0F, 2.0F, 0.0F);
         this.bipedLeftArm.addBox(0.0F, -2.0F, -2.0F, 3, 12, 4, 0.0F);
         this.bipedHead2 = new ModelRenderer(this, 24, 0);
         this.bipedHead2.setRotationPoint(0.0F, -5.0F, -4.0F);
         this.bipedHead2.addBox(-1.5F, 0.0F, -4.0F, 3, 3, 4, 0.0F);
         this.setRotateAngle(this.bipedHead2, 0.91053826F, 0.0F, 0.0F);
         this.bipedEarFur2 = new ModelRenderer(this, 38, 0);
         this.bipedEarFur2.setRotationPoint(-1.0F, -8.0F, 1.0F);
         this.bipedEarFur2.addBox(-4.5F, -4.0F, -0.5F, 5, 5, 0, 0.0F);
         this.setRotateAngle(this.bipedEarFur2, 0.18203785F, 0.0F, -0.13665928F);
         this.bipedEar2 = new ModelRenderer(this, 22, 17);
         this.bipedEar2.mirror = true;
         this.bipedEar2.setRotationPoint(-1.0F, -8.0F, 1.0F);
         this.bipedEar2.addBox(-3.0F, -2.0F, -1.0F, 2, 2, 1, 0.0F);
         this.setRotateAngle(this.bipedEar2, 0.18203785F, 0.0F, -0.13665928F);
         this.bipedTail2 = new ModelRenderer(this, 36, 11);
         this.bipedTail2.setRotationPoint(0.0F, 5.5F, 0.0F);
         this.bipedTail2.addBox(-1.5F, 0.0F, -1.8F, 3, 6, 3, 0.0F);
         this.setRotateAngle(this.bipedTail2, 0.4098033F, 0.0F, 0.0F);
         this.bipedRightLegWear = new ModelRenderer(this, 47, 30);
         this.bipedRightLegWear.mirror = true;
         this.bipedRightLegWear.setRotationPoint(-1.9F, 10.0F, 0.0F);
         this.bipedRightLegWear.addBox(-2.0F, 0.0F, -2.0F, 4, 14, 4, 0.2F);
         this.bipedVibriss2 = new ModelRenderer(this, 38, 6);
         this.bipedVibriss2.setRotationPoint(-1.0F, -1.7F, -4.9F);
         this.bipedVibriss2.addBox(-5.0F, 0.0F, 0.0F, 5, 5, 0, 0.0F);
         this.setRotateAngle(this.bipedVibriss2, -0.31869712F, 0.0F, 0.4098033F);
         this.bipedHead.addChild(this.bipedEarFur1);
         this.bipedHead.addChild(this.bipedVibriss1);
         this.bipedHead.addChild(this.bipedFur1);
         this.bipedHead.addChild(this.bipedEyes);
         this.bipedHead.addChild(this.bipedFur2);
         this.bipedHead.addChild(this.bipedEar1);
         this.bipedTail2.addChild(this.bipedTail3);
         this.bipedHead.addChild(this.bipedHead2);
         this.bipedHead.addChild(this.bipedEarFur2);
         this.bipedHead.addChild(this.bipedEar2);
         this.bipedTail1.addChild(this.bipedTail2);
         this.bipedHead.addChild(this.bipedVibriss2);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
         copyModelAngles(this.bipedBody, this.bipedBodyWear);
         copyModelAngles(this.bipedRightArm, this.bipedRightArmWear);
         copyModelAngles(this.bipedLeftArm, this.bipedLeftArmWear);
         copyModelAngles(this.bipedLeftLeg, this.bipedLeftLegWear);
         copyModelAngles(this.bipedRightLeg, this.bipedRightLegWear);
         this.bipedBody.render(f5);
         this.bipedRightLeg.render(f5);
         this.bipedTail1.render(f5);
         this.bipedRightLegWear.render(f5);
         this.bipedLeftArmWear.render(f5);
         this.bipedRightArmWear.render(f5);
         this.bipedRightArm.render(f5);
         this.bipedLeftArm.render(f5);
         this.bipedBodyWear.render(f5);
         this.bipedLeftLegWear.render(f5);
         this.bipedLeftLeg.render(f5);
         this.bipedHead.render(f5);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
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
               entityIn.motionX * entityIn.motionX
                  + entityIn.motionY * entityIn.motionY
                  + entityIn.motionZ * entityIn.motionZ
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
         this.bipedTail1.rotateAngleZ = -this.bipedRightLeg.rotateAngleX / 3.0F;
         this.bipedTail2.rotateAngleZ = this.bipedTail1.rotateAngleY;
         this.bipedTail3.rotateAngleZ = this.bipedTail1.rotateAngleY;
         if (this.isRiding) {
            this.bipedRightArm.rotateAngleX += (float) (-Math.PI / 5);
            this.bipedLeftArm.rotateAngleX += (float) (-Math.PI / 5);
            this.bipedRightLeg.rotateAngleX = -1.4137167F;
            this.bipedRightLeg.rotateAngleY = (float) (Math.PI / 10);
            this.bipedRightLeg.rotateAngleZ = 0.07853982F;
            this.bipedLeftLeg.rotateAngleX = -1.4137167F;
            this.bipedLeftLeg.rotateAngleY = (float) (-Math.PI / 10);
            this.bipedLeftLeg.rotateAngleZ = -0.07853982F;
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
         float tail = MathHelper.sin(ageInTicks * 0.064F) * 0.05F;
         this.bipedTail1.rotateAngleX = 0.4098033F + tail;
         this.bipedTail2.rotateAngleX = 0.4098033F + tail;
         this.bipedTail3.rotateAngleX = 0.4098033F + tail;
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
      }
   }

   public static class EquestrianModel extends AbstractMobModel {
      public ModelRenderer silverfishBodyParts4;
      public ModelRenderer silverfishBodyParts3;
      public ModelRenderer silverfishBodyParts2;
      public ModelRenderer silverfishBodyParts1;
      public ModelRenderer silverfishBodyParts7;
      public ModelRenderer silverfishBodyParts5;
      public ModelRenderer bipedBody;
      public ModelRenderer lents1;
      public ModelRenderer lents2;
      public ModelRenderer silverfishWings2;
      public ModelRenderer silverfishWings4;
      public ModelRenderer silverfishWings1;
      public ModelRenderer silverfishWings3;
      public ModelRenderer bipedLeftArm;
      public ModelRenderer bipedRightArm;
      public ModelRenderer bipedHead;
      public ModelRenderer leftWing;
      public ModelRenderer rightWing;
      public ModelRenderer crossbowStart;
      public ModelRenderer crossbowCenter;
      public ModelRenderer crossbow1;
      public ModelRenderer crossbow2;
      public ModelRenderer crossbowArrow;
      public ModelRenderer crossbowString1;
      public ModelRenderer crossbowString2;

      public EquestrianModel() {
         this.textureWidth = 64;
         this.textureHeight = 64;
         this.rightWing = new ModelRenderer(this, 0, 32);
         this.rightWing.setRotationPoint(0.0F, 1.0F, 2.0F);
         this.rightWing.addBox(-20.0F, 0.0F, 0.0F, 20, 12, 1, 0.0F);
         this.setRotateAngle(this.rightWing, 0.47123894F, (float) (Math.PI / 5), 0.47123894F);
         this.bipedRightArm = new ModelRenderer(this, 0, 16);
         this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
         this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
         this.setRotateAngle(this.bipedRightArm, -0.4553564F, -0.13665928F, 0.091106184F);
         this.bipedLeftArm = new ModelRenderer(this, 0, 16);
         this.bipedLeftArm.mirror = true;
         this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
         this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
         this.setRotateAngle(this.bipedLeftArm, -1.5934856F, 0.13665928F, -0.091106184F);
         this.crossbowString2 = new ModelRenderer(this, 40, 31);
         this.crossbowString2.setRotationPoint(4.0F, 0.01F, 0.5F);
         this.crossbowString2.addBox(-6.0F, -0.5F, 0.0F, 6, 0, 1, 0.0F);
         this.setRotateAngle(this.crossbowString2, 0.0F, 0.7285004F, 0.0F);
         this.crossbow1 = new ModelRenderer(this, 40, 27);
         this.crossbow1.setRotationPoint(-1.8F, -0.98F, -3.5F);
         this.crossbow1.addBox(-4.0F, -1.0F, 0.0F, 4, 1, 1, 0.0F);
         this.setRotateAngle(this.crossbow1, 0.0F, 0.3642502F, 0.0F);
         this.crossbow2 = new ModelRenderer(this, 40, 27);
         this.crossbow2.setRotationPoint(1.8F, -0.98F, -3.5F);
         this.crossbow2.addBox(0.0F, -1.0F, 0.0F, 4, 1, 1, 0.0F);
         this.setRotateAngle(this.crossbow2, 0.0F, -0.3642502F, 0.0F);
         this.crossbowStart = new ModelRenderer(this, 50, 24);
         this.crossbowStart.setRotationPoint(1.0F, 8.0F, -1.5F);
         this.crossbowStart.addBox(-1.0F, -1.0F, -3.5F, 2, 1, 5, 0.0F);
         this.setRotateAngle(this.crossbowStart, (float) (Math.PI / 2), 0.0F, 0.0F);
         this.bipedBody = new ModelRenderer(this, 16, 16);
         this.bipedBody.setRotationPoint(0.0F, 11.2F, 1.0F);
         this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
         this.silverfishBodyParts2 = new ModelRenderer(this, 0, 52);
         this.silverfishBodyParts2.setRotationPoint(0.0F, 20.0F, -0.5F);
         this.silverfishBodyParts2.addBox(-4.0F, -2.0F, -6.0F, 8, 6, 6, 0.0F);
         this.bipedHead = new ModelRenderer(this, 0, 0);
         this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
         this.leftWing = new ModelRenderer(this, 0, 32);
         this.leftWing.mirror = true;
         this.leftWing.setRotationPoint(0.0F, 1.0F, 2.0F);
         this.leftWing.addBox(0.0F, 0.0F, 0.0F, 20, 12, 1, 0.0F);
         this.setRotateAngle(this.leftWing, 0.47123894F, (float) (-Math.PI / 5), -0.47123894F);
         this.silverfishWings1 = new ModelRenderer(this, 40, 16);
         this.silverfishWings1.setRotationPoint(0.0F, -4.0F, -4.0F);
         this.silverfishWings1.addBox(-6.0F, 0.0F, 0.0F, 12, 8, 0, 0.0F);
         this.silverfishBodyParts1 = new ModelRenderer(this, 28, 56);
         this.silverfishBodyParts1.setRotationPoint(0.0F, 2.0F, -6.0F);
         this.silverfishBodyParts1.addBox(-3.0F, -2.0F, -4.0F, 6, 4, 4, 0.0F);
         this.silverfishWings4 = new ModelRenderer(this, 24, 0);
         this.silverfishWings4.setRotationPoint(0.0F, -2.0F, 3.5F);
         this.silverfishWings4.addBox(-4.0F, 0.0F, 0.0F, 8, 6, 0, 0.0F);
         this.crossbowArrow = new ModelRenderer(this, 33, 7);
         this.crossbowArrow.setRotationPoint(0.0F, -1.0F, -5.6F);
         this.crossbowArrow.addBox(-0.5F, 0.0F, -0.5F, 1, 7, 1, 0.0F);
         this.setRotateAngle(this.crossbowArrow, (float) (Math.PI / 2), 0.0F, 0.0F);
         this.silverfishBodyParts5 = new ModelRenderer(this, 44, 43);
         this.silverfishBodyParts5.setRotationPoint(0.0F, 1.0F, 2.0F);
         this.silverfishBodyParts5.addBox(-2.0F, 0.0F, 0.5F, 4, 3, 6, 0.0F);
         this.lents2 = new ModelRenderer(this, 44, 8);
         this.lents2.setRotationPoint(0.0F, 20.0F, 1.0F);
         this.lents2.addBox(-3.0F, -2.0F, -2.0F, 6, 4, 4, 0.0F);
         this.setRotateAngle(this.lents2, (float) (-Math.PI / 4), (float) (Math.PI / 2), 0.0F);
         this.silverfishBodyParts3 = new ModelRenderer(this, 22, 46);
         this.silverfishBodyParts3.setRotationPoint(0.0F, 19.0F, 1.0F);
         this.silverfishBodyParts3.addBox(-3.0F, 0.0F, -1.5F, 6, 5, 5, 0.0F);
         this.lents1 = new ModelRenderer(this, 40, 0);
         this.lents1.setRotationPoint(0.0F, 18.0F, -0.3F);
         this.lents1.addBox(-4.0F, -2.0F, -2.0F, 8, 4, 4, 0.0F);
         this.setRotateAngle(this.lents1, (float) (-Math.PI / 4), 0.0F, 0.0F);
         this.silverfishWings2 = new ModelRenderer(this, 24, 0);
         this.silverfishWings2.setRotationPoint(0.0F, -2.0F, 2.5F);
         this.silverfishWings2.addBox(-4.0F, 0.0F, 0.0F, 8, 6, 0, 0.0F);
         this.crossbowCenter = new ModelRenderer(this, 40, 24);
         this.crossbowCenter.setRotationPoint(0.0F, -1.0F, -3.5F);
         this.crossbowCenter.addBox(-2.0F, -1.0F, -1.0F, 4, 1, 2, 0.0F);
         this.silverfishBodyParts7 = new ModelRenderer(this, 0, 46);
         this.silverfishBodyParts7.setRotationPoint(0.0F, 2.0F, 6.5F);
         this.silverfishBodyParts7.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 4, 0.0F);
         this.silverfishBodyParts4 = new ModelRenderer(this, 44, 52);
         this.silverfishBodyParts4.setRotationPoint(0.0F, 20.0F, 6.0F);
         this.silverfishBodyParts4.addBox(-2.5F, 0.0F, -1.5F, 5, 4, 4, 0.0F);
         this.silverfishWings3 = new ModelRenderer(this, 44, 32);
         this.silverfishWings3.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.silverfishWings3.addBox(-5.0F, -4.0F, -1.5F, 10, 6, 0, 0.0F);
         this.crossbowString1 = new ModelRenderer(this, 40, 31);
         this.crossbowString1.setRotationPoint(-4.0F, 0.0F, 0.5F);
         this.crossbowString1.addBox(0.0F, -0.5F, 0.0F, 6, 0, 1, 0.0F);
         this.setRotateAngle(this.crossbowString1, 0.0F, -0.7285004F, 0.0F);
         this.bipedBody.addChild(this.rightWing);
         this.bipedBody.addChild(this.bipedRightArm);
         this.bipedBody.addChild(this.bipedLeftArm);
         this.crossbow2.addChild(this.crossbowString2);
         this.crossbowStart.addChild(this.crossbow1);
         this.crossbowStart.addChild(this.crossbow2);
         this.bipedLeftArm.addChild(this.crossbowStart);
         this.bipedBody.addChild(this.bipedHead);
         this.bipedBody.addChild(this.leftWing);
         this.silverfishBodyParts2.addChild(this.silverfishWings1);
         this.silverfishBodyParts2.addChild(this.silverfishBodyParts1);
         this.silverfishBodyParts3.addChild(this.silverfishWings4);
         this.crossbowStart.addChild(this.crossbowArrow);
         this.silverfishBodyParts4.addChild(this.silverfishBodyParts5);
         this.silverfishBodyParts4.addChild(this.silverfishWings2);
         this.crossbowStart.addChild(this.crossbowCenter);
         this.silverfishBodyParts5.addChild(this.silverfishBodyParts7);
         this.silverfishBodyParts1.addChild(this.silverfishWings3);
         this.crossbow1.addChild(this.crossbowString1);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         f5 = (float)(f5 * 1.3);
         this.bipedHead.rotateAngleY = f3 * (float) (Math.PI / 180.0);
         this.bipedHead.rotateAngleX = f4 * (float) (Math.PI / 180.0);
         this.silverfishBodyParts1.rotateAngleY = f3 * (float) (Math.PI / 180.0);
         float fly = entity.hasNoGravity() ? 1.0F : 0.0F;
         this.silverfishBodyParts4.rotateAngleX = -0.45F * fly;
         this.silverfishBodyParts5.rotateAngleX = -0.45F * fly;
         this.silverfishBodyParts7.rotateAngleX = -0.45F * fly;
         this.silverfishBodyParts1.rotateAngleX = 0.26F * fly;
         this.silverfishBodyParts2.rotateAngleX = 0.26F * fly;
         float wingsAnim = -f2 / 3.0F;
         this.leftWing.rotateAngleX = 0.47123894F + (MathHelper.cos(wingsAnim) + 0.4F) * fly * 0.6F;
         this.leftWing.rotateAngleY = (float) (-Math.PI / 5) - MathHelper.sin(wingsAnim) * fly * 0.3F;
         this.leftWing.rotateAngleZ = -0.47123894F - MathHelper.sin(wingsAnim) * fly * 0.45F;
         this.rightWing.rotateAngleX = this.leftWing.rotateAngleX;
         this.rightWing.rotateAngleY = -this.leftWing.rotateAngleY;
         this.rightWing.rotateAngleZ = -this.leftWing.rotateAngleZ;
         float partsAnim = f * 1.2F;
         this.silverfishBodyParts4.rotateAngleY = MathHelper.cos(partsAnim * 0.9F + 1.8849558F) * (float) Math.PI * 0.05F * (1 + Math.abs(2));
         this.silverfishBodyParts5.rotateAngleY = MathHelper.cos(partsAnim * 0.9F + (float) (Math.PI * 3.0 / 4.0))
            * (float) Math.PI
            * 0.05F
            * (1 + Math.abs(3));
         this.silverfishBodyParts7.rotateAngleY = MathHelper.cos(partsAnim * 0.9F + 2.8274336F) * (float) Math.PI * 0.05F * (1 + Math.abs(4));
         float anim1 = 100.0F - an1 + Minecraft.getMinecraft().getRenderPartialTicks();
         float anim3 = 100.0F - an3 + Minecraft.getMinecraft().getRenderPartialTicks();
         float ft1 = GetMOP.getfromto(anim1, 2.0F, OtherMobsPack.Equestrian.shootCooldown * 0.2F)
            - GetMOP.getfromto(anim1, OtherMobsPack.Equestrian.shootCooldown * 0.8F, (float)OtherMobsPack.Equestrian.shootCooldown);
         float ft2 = GetMOP.getfromto(anim3, 0.0F, 10.0F) - GetMOP.getfromto(anim3, 20.0F, 30.0F);
         this.bipedLeftArm.rotateAngleY = 0.13665928F + 0.64F * ft1;
         this.bipedLeftArm.rotateAngleX = -1.5934856F + 0.82F * (1.0F - ft2);
         this.bipedLeftArm.rotateAngleY += f3 * 0.014453292F * ft2;
         this.bipedLeftArm.rotateAngleX += f4 * 0.014453292F * ft2;
         this.crossbowArrow.isHidden = anim1 < OtherMobsPack.Equestrian.shootCooldown;
         if (this.crossbowArrow.isHidden) {
            this.crossbow1.rotateAngleY = 0.17F;
            this.crossbowString1.rotateAngleY = -0.35F;
            this.crossbow2.rotateAngleY = -0.17F;
            this.crossbowString2.rotateAngleY = 0.35F;
         } else {
            this.crossbow1.rotateAngleY = 0.3642502F;
            this.crossbowString1.rotateAngleY = -0.7285004F;
            this.crossbow2.rotateAngleY = -0.3642502F;
            this.crossbowString2.rotateAngleY = 0.7285004F;
         }

         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, -0.45F, 0.0F);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.bipedBody.offsetX, this.bipedBody.offsetY, this.bipedBody.offsetZ);
         GlStateManager.translate(this.bipedBody.rotationPointX * f5, this.bipedBody.rotationPointY * f5, this.bipedBody.rotationPointZ * f5);
         GlStateManager.scale(0.65, 0.65, 0.65);
         GlStateManager.translate(-this.bipedBody.offsetX, -this.bipedBody.offsetY, -this.bipedBody.offsetZ);
         GlStateManager.translate(-this.bipedBody.rotationPointX * f5, -this.bipedBody.rotationPointY * f5, -this.bipedBody.rotationPointZ * f5);
         this.bipedBody.render(f5);
         GlStateManager.popMatrix();
         this.silverfishBodyParts2.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.lents2.offsetX, this.lents2.offsetY, this.lents2.offsetZ);
         GlStateManager.translate(this.lents2.rotationPointX * f5, this.lents2.rotationPointY * f5, this.lents2.rotationPointZ * f5);
         GlStateManager.scale(1.4, 1.4, 0.65);
         GlStateManager.translate(-this.lents2.offsetX, -this.lents2.offsetY, -this.lents2.offsetZ);
         GlStateManager.translate(-this.lents2.rotationPointX * f5, -this.lents2.rotationPointY * f5, -this.lents2.rotationPointZ * f5);
         this.lents2.render(f5);
         GlStateManager.popMatrix();
         this.silverfishBodyParts3.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.lents1.offsetX, this.lents1.offsetY, this.lents1.offsetZ);
         GlStateManager.translate(this.lents1.rotationPointX * f5, this.lents1.rotationPointY * f5, this.lents1.rotationPointZ * f5);
         GlStateManager.scale(0.65, 2.0, 1.0);
         GlStateManager.translate(-this.lents1.offsetX, -this.lents1.offsetY, -this.lents1.offsetZ);
         GlStateManager.translate(-this.lents1.rotationPointX * f5, -this.lents1.rotationPointY * f5, -this.lents1.rotationPointZ * f5);
         this.lents1.render(f5);
         GlStateManager.popMatrix();
         this.silverfishBodyParts4.render(f5);
         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class HellhoundModel extends AbstractMobModel {
      public ModelRenderer wolfBody;
      public ModelRenderer wolfLeg4;
      public ModelRenderer wolfLeg2;
      public ModelRenderer wolfLeg3;
      public ModelRenderer wolfLeg1;
      public ModelRenderer wolfnek;
      public ModelRenderer wolfBody1;
      public ModelRenderer wolfBodytail1;
      public ModelRenderer wolfHeadMain0;
      public ModelRenderer wolfnose;
      public ModelRenderer wolfe1;
      public ModelRenderer wolfe2;
      public ModelRenderer wolfBodytail2;
      public ModelRenderer wolfBodytail3;

      public HellhoundModel() {
         this.textureWidth = 64;
         this.textureHeight = 32;
         this.wolfnose = new ModelRenderer(this, 35, 0);
         this.wolfnose.setRotationPoint(0.0F, 0.7F, -4.5F);
         this.wolfnose.addBox(-1.5F, -2.0F, -2.5F, 3, 3, 3, 0.0F);
         this.wolfLeg4 = new ModelRenderer(this, 50, 18);
         this.wolfLeg4.setRotationPoint(0.5F, 18.0F, -4.0F);
         this.wolfLeg4.addBox(0.0F, 0.0F, -1.0F, 2, 6, 3, 0.0F);
         this.wolfBodytail3 = new ModelRenderer(this, 18, 12);
         this.wolfBodytail3.setRotationPoint(0.0F, -0.1F, 6.0F);
         this.wolfBodytail3.addBox(0.0F, -1.0F, 0.0F, 0, 4, 6, 0.0F);
         this.setRotateAngle(this.wolfBodytail3, 0.18203785F, 0.0F, 0.0F);
         this.wolfHeadMain0 = new ModelRenderer(this, 0, 0);
         this.wolfHeadMain0.setRotationPoint(0.0F, -0.5F, -7.0F);
         this.wolfHeadMain0.addBox(-2.5F, -3.0F, -4.0F, 5, 5, 5, 0.0F);
         this.setRotateAngle(this.wolfHeadMain0, 0.3642502F, 0.0F, 0.0F);
         this.wolfBody1 = new ModelRenderer(this, 31, 21);
         this.wolfBody1.setRotationPoint(0.0F, 14.9F, 3.9F);
         this.wolfBody1.addBox(-2.5F, -3.0F, -3.0F, 5, 4, 7, 0.0F);
         this.wolfBodytail2 = new ModelRenderer(this, 18, 17);
         this.wolfBodytail2.setRotationPoint(0.0F, -0.1F, 6.0F);
         this.wolfBodytail2.addBox(0.0F, -1.0F, 0.0F, 0, 4, 6, 0.0F);
         this.setRotateAngle(this.wolfBodytail2, 0.18203785F, 0.0F, 0.0F);
         this.wolfnek = new ModelRenderer(this, 44, 1);
         this.wolfnek.setRotationPoint(0.0F, 16.0F, -3.9F);
         this.wolfnek.addBox(-2.0F, -3.0F, -7.1F, 4, 5, 6, 0.0F);
         this.setRotateAngle(this.wolfnek, -0.3642502F, 0.0F, 0.0F);
         this.wolfLeg1 = new ModelRenderer(this, 0, 18);
         this.wolfLeg1.setRotationPoint(-1.5F, 15.0F, 5.0F);
         this.wolfLeg1.addBox(-2.0F, 0.0F, -1.0F, 2, 9, 3, 0.0F);
         this.wolfLeg2 = new ModelRenderer(this, 0, 18);
         this.wolfLeg2.setRotationPoint(1.5F, 15.0F, 5.0F);
         this.wolfLeg2.addBox(0.0F, 0.0F, -1.0F, 2, 9, 3, 0.0F);
         this.wolfLeg3 = new ModelRenderer(this, 50, 18);
         this.wolfLeg3.setRotationPoint(-2.5F, 18.0F, -4.0F);
         this.wolfLeg3.addBox(0.0F, 0.0F, -1.0F, 2, 6, 3, 0.0F);
         this.wolfBody = new ModelRenderer(this, 13, 3);
         this.wolfBody.setRotationPoint(0.0F, 14.0F, -3.0F);
         this.wolfBody.addBox(-3.0F, -2.0F, -3.0F, 6, 6, 7, 0.0F);
         this.setRotateAngle(this.wolfBody, 0.045553092F, 0.0F, 0.0F);
         this.wolfBodytail1 = new ModelRenderer(this, 18, 22);
         this.wolfBodytail1.setRotationPoint(0.0F, 12.9F, 7.9F);
         this.wolfBodytail1.addBox(0.0F, -1.0F, 0.0F, 0, 4, 6, 0.0F);
         this.setRotateAngle(this.wolfBodytail1, -0.63739425F, 0.0F, 0.0F);
         this.wolfe1 = new ModelRenderer(this, 31, 14);
         this.wolfe1.setRotationPoint(2.5F, -3.0F, -1.5F);
         this.wolfe1.addBox(0.0F, 0.0F, -1.0F, 0, 6, 3, 0.0F);
         this.setRotateAngle(this.wolfe1, 0.0F, 0.0F, -0.13665928F);
         this.wolfe2 = new ModelRenderer(this, 31, 14);
         this.wolfe2.setRotationPoint(-2.5F, -3.0F, -1.5F);
         this.wolfe2.addBox(0.0F, 0.0F, -1.0F, 0, 6, 3, 0.0F);
         this.setRotateAngle(this.wolfe2, 0.0F, 0.0F, 0.13665928F);
         this.wolfHeadMain0.addChild(this.wolfnose);
         this.wolfBodytail2.addChild(this.wolfBodytail3);
         this.wolfnek.addChild(this.wolfHeadMain0);
         this.wolfBodytail1.addChild(this.wolfBodytail2);
         this.wolfHeadMain0.addChild(this.wolfe1);
         this.wolfHeadMain0.addChild(this.wolfe2);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         float pt = Minecraft.getMinecraft().getRenderPartialTicks();
         f5 *= 1.2F;
         this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
         int animlength = OtherMobsPack.Hellhound.fireTimeMax + 15;
         float anim1 = 100.0F - an2 + pt;
         int delay = OtherMobsPack.Hellhound.fireTimeMax - OtherMobsPack.Hellhound.fireTimeDamageTime;
         float value = GetMOP.getfromto(anim1, 0.0F, (float)delay) - GetMOP.getfromto(anim1, (float)OtherMobsPack.Hellhound.fireTimeMax, (float)animlength);
         float valueNo = 1.0F - value;
         this.wolfLeg3.rotateAngleX = this.wolfLeg3.rotateAngleX * valueNo + -0.35F * value;
         this.wolfLeg4.rotateAngleX = this.wolfLeg4.rotateAngleX * valueNo + -0.35F * value;
         this.wolfBodytail1.rotateAngleX = -0.63739425F * valueNo + value;
         this.wolfBodytail2.rotateAngleX = 0.18203785F * valueNo + value;
         this.wolfBodytail3.rotateAngleX = 0.18203785F * valueNo + value;
         this.wolfe1.rotateAngleX = value;
         this.wolfe2.rotateAngleX = value;
         this.wolfe1.rotateAngleY = 0.31F * value;
         this.wolfe2.rotateAngleY = -0.31F * value;
         this.wolfe1.rotateAngleZ = -0.13665928F * valueNo + -1.4F * value;
         this.wolfe2.rotateAngleZ = 0.13665928F * valueNo + 1.4F * value;
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, -0.3F, 0.0F);
         this.wolfLeg4.render(f5);
         this.wolfBody1.render(f5);
         this.wolfnek.render(f5);
         this.wolfLeg1.render(f5);
         this.wolfLeg2.render(f5);
         this.wolfLeg3.render(f5);
         this.wolfBody.render(f5);
         this.wolfBodytail1.render(f5);
         GlStateManager.popMatrix();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }

      public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
         this.wolfHeadMain0.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
         this.wolfHeadMain0.rotateAngleX = 0.3642502F + headPitch * (float) (Math.PI / 180.0);
         this.wolfLeg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
         this.wolfLeg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
         this.wolfLeg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
         this.wolfLeg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
         this.wolfBodytail1.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 0.35F * limbSwingAmount;
         this.wolfBodytail2.rotateAngleY = this.wolfBodytail1.rotateAngleY;
         this.wolfBodytail3.rotateAngleY = this.wolfBodytail1.rotateAngleY;
      }
   }

   public static class HorribleEvokerModel extends ModelBase {
      public ModelRenderer head;
      public ModelRenderer leg1;
      public ModelRenderer leg2;
      public ModelRenderer leg3;
      public ModelRenderer leg4;
      public ModelRenderer headChild;
      public ModelRenderer legA1;
      public ModelRenderer legA2;
      public ModelRenderer legA3;
      public ModelRenderer legA4;

      public HorribleEvokerModel() {
         this.textureWidth = 64;
         this.textureHeight = 32;
         this.legA3 = new ModelRenderer(this, 0, 6);
         this.legA3.setRotationPoint(0.0F, 2.65F, -0.01F);
         this.legA3.addBox(-1.5F, -0.8F, -1.0F, 2, 5, 2, 0.0F);
         this.setRotateAngle(this.legA3, 0.0F, 0.0F, -1.0016445F);
         this.legA2 = new ModelRenderer(this, 0, 6);
         this.legA2.setRotationPoint(0.0F, 2.65F, -0.01F);
         this.legA2.addBox(-0.5F, -0.8F, -1.0F, 2, 5, 2, 0.0F);
         this.setRotateAngle(this.legA2, 0.0F, 0.0F, 1.0016445F);
         this.legA1 = new ModelRenderer(this, 0, 6);
         this.legA1.setRotationPoint(0.0F, 2.65F, -0.01F);
         this.legA1.addBox(-0.5F, -0.8F, -1.0F, 2, 5, 2, 0.0F);
         this.setRotateAngle(this.legA1, 0.0F, 0.0F, 1.0016445F);
         this.head = new ModelRenderer(this, 0, 0);
         this.head.setRotationPoint(0.0F, 17.5F, 0.0F);
         this.head.addBox(-8.0F, -10.0F, -8.0F, 16, 11, 16, 0.0F);
         this.headChild = new ModelRenderer(this, 48, 0);
         this.headChild.setRotationPoint(0.0F, -3.0F, -8.0F);
         this.headChild.addBox(-2.0F, -1.0F, -4.0F, 4, 8, 4, 0.0F);
         this.leg2 = new ModelRenderer(this, 0, 0);
         this.leg2.setRotationPoint(7.5F, 18.5F, 6.0F);
         this.leg2.addBox(-0.5F, -0.5F, -1.0F, 2, 4, 2, 0.0F);
         this.setRotateAngle(this.leg2, 0.0F, 0.0F, -1.0016445F);
         this.leg4 = new ModelRenderer(this, 0, 0);
         this.leg4.setRotationPoint(-7.5F, 18.5F, 6.0F);
         this.leg4.addBox(-1.5F, -0.5F, -1.0F, 2, 4, 2, 0.0F);
         this.setRotateAngle(this.leg4, 0.0F, 0.0F, 1.0016445F);
         this.leg1 = new ModelRenderer(this, 0, 0);
         this.leg1.setRotationPoint(7.5F, 18.5F, -6.0F);
         this.leg1.addBox(-0.5F, -0.5F, -1.0F, 2, 4, 2, 0.0F);
         this.setRotateAngle(this.leg1, 0.0F, 0.0F, -1.0016445F);
         this.leg3 = new ModelRenderer(this, 0, 0);
         this.leg3.setRotationPoint(-7.5F, 18.5F, -6.0F);
         this.leg3.addBox(-1.5F, -0.5F, -1.0F, 2, 4, 2, 0.0F);
         this.setRotateAngle(this.leg3, 0.0F, 0.0F, 1.0016445F);
         this.legA4 = new ModelRenderer(this, 0, 6);
         this.legA4.setRotationPoint(0.0F, 2.65F, -0.01F);
         this.legA4.addBox(-1.5F, -0.8F, -1.0F, 2, 5, 2, 0.0F);
         this.setRotateAngle(this.legA4, 0.0F, 0.0F, -1.0016445F);
         this.leg3.addChild(this.legA3);
         this.leg2.addChild(this.legA2);
         this.leg1.addChild(this.legA1);
         this.head.addChild(this.headChild);
         this.leg4.addChild(this.legA4);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         this.setRotateAngle(this.leg1, 0.0F, 0.0F, -1.0016445F);
         this.setRotateAngle(this.leg2, 0.0F, 0.0F, -1.0016445F);
         this.setRotateAngle(this.leg3, 0.0F, 0.0F, 1.0016445F);
         this.setRotateAngle(this.leg4, 0.0F, 0.0F, 1.0016445F);
         float legsAnim = f * 1.2F;
         float legsV = MathHelper.clamp(MathHelper.cos(legsAnim), -0.2F, 1.0F) * 0.3F;
         float legsH = MathHelper.sin(legsAnim) * 0.3F;
         float legsV2 = MathHelper.clamp(MathHelper.cos(legsAnim + (float) Math.PI), -0.2F, 1.0F) * 0.3F;
         float legsH2 = MathHelper.sin(legsAnim + (float) Math.PI) * 0.3F;
         this.leg1.rotateAngleZ -= legsV;
         this.leg1.rotateAngleY -= legsV;
         this.leg1.rotateAngleX -= legsH;
         this.leg1.rotateAngleY += legsH;
         this.leg2.rotateAngleZ -= legsV2;
         this.leg2.rotateAngleX -= legsH2;
         this.leg2.rotateAngleY += legsH2;
         this.leg3.rotateAngleZ += legsV2;
         this.leg3.rotateAngleY += legsV2;
         this.leg3.rotateAngleX += legsH2;
         this.leg3.rotateAngleY -= legsH2;
         this.leg4.rotateAngleZ += legsV;
         this.leg4.rotateAngleX += legsH;
         this.leg4.rotateAngleY -= legsH;
         this.head.render(f5);
         this.leg2.render(f5);
         this.leg4.render(f5);
         this.leg1.render(f5);
         this.leg3.render(f5);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class HorribleVindicatorModel extends AbstractMobModel {
      public ModelRenderer head;
      public ModelRenderer body0;
      public ModelRenderer body1;
      public ModelRenderer leg1;
      public ModelRenderer leg0;
      public ModelRenderer leftArm;
      public ModelRenderer rightArm;
      public ModelRenderer cape1;
      public ModelRenderer headChild;
      public ModelRenderer leftBlade;
      public ModelRenderer rightBlade;
      public ModelRenderer cape2;
      public ModelRenderer cape3;

      public HorribleVindicatorModel() {
         this.textureWidth = 64;
         this.textureHeight = 48;
         this.cape2 = new ModelRenderer(this, 39, 21);
         this.cape2.setRotationPoint(0.0F, 10.0F, 0.0F);
         this.cape2.addBox(-5.5F, 0.0F, 0.0F, 11, 10, 0, 0.0F);
         this.setRotateAngle(this.cape2, 0.18203785F, 0.0F, 0.0F);
         this.leg0 = new ModelRenderer(this, 0, 17);
         this.leg0.setRotationPoint(-2.0F, 10.0F, 1.0F);
         this.leg0.addBox(-2.0F, 0.0F, -1.5F, 3, 14, 3, 0.0F);
         this.setRotateAngle(this.leg0, 0.0F, 0.18203785F, 0.0F);
         this.leftArm = new ModelRenderer(this, 24, 32);
         this.leftArm.mirror = true;
         this.leftArm.setRotationPoint(5.5F, -2.0F, 0.0F);
         this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, 0.0F);
         this.leg1 = new ModelRenderer(this, 0, 17);
         this.leg1.mirror = true;
         this.leg1.setRotationPoint(2.0F, 10.0F, 1.0F);
         this.leg1.addBox(-1.0F, 0.0F, -1.5F, 3, 14, 3, 0.0F);
         this.setRotateAngle(this.leg1, 0.0F, -0.18203785F, 0.0F);
         this.leftBlade = new ModelRenderer(this, 14, 12);
         this.leftBlade.mirror = true;
         this.leftBlade.setRotationPoint(0.0F, 9.0F, 0.0F);
         this.leftBlade.addBox(0.0F, -12.0F, -5.0F, 0, 16, 5, 0.0F);
         this.setRotateAngle(this.leftBlade, 1.3203416F, -0.13665928F, 0.091106184F);
         this.body1 = new ModelRenderer(this, 0, 35);
         this.body1.setRotationPoint(0.0F, 0.8F, -0.1F);
         this.body1.addBox(-3.5F, 0.0F, -2.0F, 7, 9, 4, 0.0F);
         this.setRotateAngle(this.body1, 0.18203785F, 0.0F, 0.0F);
         this.rightArm = new ModelRenderer(this, 24, 32);
         this.rightArm.mirror = true;
         this.rightArm.setRotationPoint(-5.5F, -2.0F, 0.0F);
         this.rightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, 0.0F);
         this.headChild = new ModelRenderer(this, 0, 0);
         this.headChild.setRotationPoint(0.0F, -3.0F, -4.0F);
         this.headChild.addBox(-1.0F, -1.0F, -2.1F, 2, 4, 2, 0.0F);
         this.setRotateAngle(this.headChild, -0.091106184F, 0.0F, 0.0F);
         this.body0 = new ModelRenderer(this, 32, 0);
         this.body0.setRotationPoint(0.0F, -4.0F, 0.0F);
         this.body0.addBox(-4.5F, 0.0F, -3.0F, 9, 5, 6, 0.0F);
         this.setRotateAngle(this.body0, 0.091106184F, 0.0F, 0.0F);
         this.cape3 = new ModelRenderer(this, 38, 31);
         this.cape3.setRotationPoint(0.0F, 10.0F, 0.0F);
         this.cape3.addBox(-6.5F, 0.0F, 0.0F, 13, 10, 0, 0.0F);
         this.setRotateAngle(this.cape3, 0.18203785F, 0.0F, 0.0F);
         this.rightBlade = new ModelRenderer(this, 14, 12);
         this.rightBlade.mirror = true;
         this.rightBlade.setRotationPoint(0.0F, 9.0F, 0.0F);
         this.rightBlade.addBox(0.0F, -12.0F, -5.0F, 0, 16, 5, 0.0F);
         this.setRotateAngle(this.rightBlade, 1.3203416F, 0.13665928F, -0.091106184F);
         this.cape1 = new ModelRenderer(this, 40, 11);
         this.cape1.setRotationPoint(0.0F, -4.0F, 3.0F);
         this.cape1.addBox(-4.5F, 0.0F, 0.0F, 9, 10, 0, 0.0F);
         this.setRotateAngle(this.cape1, 0.18203785F, 0.0F, 0.0F);
         this.head = new ModelRenderer(this, 0, 0);
         this.head.setRotationPoint(0.0F, -4.0F, 0.0F);
         this.head.addBox(-4.0F, -9.0F, -4.0F, 8, 9, 8, 0.0F);
         this.cape1.addChild(this.cape2);
         this.leftArm.addChild(this.leftBlade);
         this.head.addChild(this.headChild);
         this.cape2.addChild(this.cape3);
         this.rightArm.addChild(this.rightBlade);
      }

      @Override
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int an1, int an2, int an3, int an4, boolean isAbstractMob) {
         this.head.rotateAngleY = f3 * (float) (Math.PI / 180.0);
         this.head.rotateAngleX = f4 * (float) (Math.PI / 180.0);
         this.leg0.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
         this.leg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
         this.rightArm.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1 * 0.7F;
         this.leftArm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * f1 * 0.7F;
         float an = 100 - an1 + Minecraft.getMinecraft().getRenderPartialTicks();
         float ft1 = GetMOP.getfromto(an, 0.0F, 4.0F) - GetMOP.getfromto(an, 5.0F, 8.0F);
         float ft2 = GetMOP.getfromto(an, 3.0F, 5.0F) - GetMOP.getfromto(an, 6.0F, 10.0F);
         this.rightArm.rotateAngleX += -2.0F * ft1;
         this.leftArm.rotateAngleX += -2.0F * ft1;
         this.rightArm.rotateAngleZ = -0.35F * ft2;
         this.leftArm.rotateAngleZ = 0.35F * ft2;
         this.leg0.render(f5);
         this.leftArm.render(f5);
         this.leg1.render(f5);
         this.body1.render(f5);
         this.rightArm.render(f5);
         this.body0.render(f5);
         this.cape1.render(f5);
         this.head.render(f5);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class LavaSprinklerModel extends ModelBase {
      public ModelRenderer head;
      public ModelRenderer shape1;
      public ModelRenderer shape2;
      public ModelRenderer rib1;
      public ModelRenderer rib2;
      public ModelRenderer rib3;
      public ModelRenderer spike1;
      public ModelRenderer tent1;
      public ModelRenderer tent2;
      public ModelRenderer tent3;
      public ModelRenderer tent4;
      public ModelRenderer tent5;
      public ModelRenderer tent6;
      public ModelRenderer rib1a;
      public ModelRenderer rib2a;
      public ModelRenderer rib3a;
      public ModelRenderer spike2;

      public LavaSprinklerModel() {
         this.textureWidth = 80;
         this.textureHeight = 64;
         this.spike2 = new ModelRenderer(this, 32, -8);
         this.spike2.mirror = true;
         this.spike2.setRotationPoint(0.0F, 12.0F, -7.5F);
         this.spike2.addBox(3.5F, -11.0F, -6.6F, 0, 19, 8, 0.0F);
         this.setRotateAngle(this.spike2, -0.27314404F, -0.3642502F, 0.045553092F);
         this.rib2 = new ModelRenderer(this, 0, 16);
         this.rib2.setRotationPoint(0.0F, 4.0F, 0.0F);
         this.rib2.addBox(5.0F, -1.0F, -7.5F, 4, 2, 15, 0.0F);
         this.setRotateAngle(this.rib2, -0.18203785F, -0.27314404F, 0.0F);
         this.head = new ModelRenderer(this, 0, 0);
         this.head.setRotationPoint(0.0F, 15.0F, -2.0F);
         this.head.addBox(-4.0F, -2.0F, -4.0F, 8, 8, 8, 0.0F);
         this.rib1a = new ModelRenderer(this, 0, 16);
         this.rib1a.mirror = true;
         this.rib1a.setRotationPoint(0.0F, 4.0F, 0.0F);
         this.rib1a.addBox(-8.0F, 4.0F, -8.0F, 4, 2, 15, 0.0F);
         this.setRotateAngle(this.rib1a, 0.13665928F, 0.091106184F, 0.0F);
         this.shape2 = new ModelRenderer(this, 23, 19);
         this.shape2.setRotationPoint(0.0F, 4.0F, 0.0F);
         this.shape2.addBox(-3.0F, -11.0F, -2.0F, 6, 3, 6, 0.0F);
         this.setRotateAngle(this.shape2, -0.27314404F, 0.0F, 0.0F);
         this.spike1 = new ModelRenderer(this, 32, -8);
         this.spike1.setRotationPoint(0.0F, 12.0F, -7.5F);
         this.spike1.addBox(-3.5F, -11.0F, -6.6F, 0, 19, 8, 0.0F);
         this.setRotateAngle(this.spike1, -0.27314404F, 0.3642502F, -0.045553092F);
         this.rib1 = new ModelRenderer(this, 0, 16);
         this.rib1.setRotationPoint(0.0F, 4.0F, 0.0F);
         this.rib1.addBox(4.0F, 4.0F, -8.0F, 4, 2, 15, 0.0F);
         this.setRotateAngle(this.rib1, 0.13665928F, -0.091106184F, 0.0F);
         this.shape1 = new ModelRenderer(this, 0, 34);
         this.shape1.setRotationPoint(0.0F, 4.0F, 0.0F);
         this.shape1.addBox(-6.0F, -8.0F, -4.0F, 12, 18, 12, 0.0F);
         this.setRotateAngle(this.shape1, -0.27314404F, 0.0F, 0.0F);
         this.rib3 = new ModelRenderer(this, 0, 16);
         this.rib3.setRotationPoint(0.0F, 4.0F, 0.0F);
         this.rib3.addBox(6.0F, -6.0F, -9.0F, 4, 2, 15, 0.0F);
         this.setRotateAngle(this.rib3, -0.4553564F, -0.3642502F, 0.0F);
         this.rib3a = new ModelRenderer(this, 0, 16);
         this.rib3a.mirror = true;
         this.rib3a.setRotationPoint(0.0F, 4.0F, 0.0F);
         this.rib3a.addBox(-10.0F, -6.0F, -9.0F, 4, 2, 15, 0.0F);
         this.setRotateAngle(this.rib3a, -0.4553564F, 0.3642502F, 0.0F);
         this.rib2a = new ModelRenderer(this, 0, 16);
         this.rib2a.mirror = true;
         this.rib2a.setRotationPoint(0.0F, 4.0F, 0.0F);
         this.rib2a.addBox(-9.0F, -1.0F, -7.5F, 4, 2, 15, 0.0F);
         this.setRotateAngle(this.rib2a, -0.18203785F, 0.27314404F, 0.0F);
         this.tent1 = new ModelRenderer(this, 50, -15);
         this.tent1.setRotationPoint(0.0F, 14.0F, -2.0F);
         this.tent1.addBox(-3.0F, -13.0F, 5.0F, 0, 20, 15, 0.0F);
         this.setRotateAngle(this.tent1, -0.22759093F, -0.4098033F, 0.0F);
         this.tent2 = new ModelRenderer(this, 50, -15);
         this.tent2.mirror = true;
         this.tent2.setRotationPoint(0.0F, 14.0F, -2.0F);
         this.tent2.addBox(3.0F, -13.0F, 5.0F, 0, 20, 15, 0.0F);
         this.setRotateAngle(this.tent2, -0.22759093F, 0.4098033F, 0.0F);
         this.tent3 = new ModelRenderer(this, 50, 5);
         this.tent3.setRotationPoint(0.0F, 14.0F, -2.0F);
         this.tent3.addBox(-3.0F, -13.0F, 5.0F, 0, 20, 15, 0.0F);
         this.setRotateAngle(this.tent3, -0.22759093F, -0.4098033F, 0.0F);
         this.tent4 = new ModelRenderer(this, 50, 5);
         this.tent4.mirror = true;
         this.tent4.setRotationPoint(0.0F, 14.0F, -2.0F);
         this.tent4.addBox(3.0F, -13.0F, 5.0F, 0, 20, 15, 0.0F);
         this.setRotateAngle(this.tent4, -0.22759093F, 0.4098033F, 0.0F);
         this.tent5 = new ModelRenderer(this, 50, 25);
         this.tent5.setRotationPoint(0.0F, 14.0F, -2.0F);
         this.tent5.addBox(-3.0F, -13.0F, 5.0F, 0, 20, 15, 0.0F);
         this.setRotateAngle(this.tent5, -0.22759093F, -0.4098033F, 0.0F);
         this.tent6 = new ModelRenderer(this, 50, 25);
         this.tent6.mirror = true;
         this.tent6.setRotationPoint(0.0F, 14.0F, -2.0F);
         this.tent6.addBox(3.0F, -13.0F, 5.0F, 0, 20, 15, 0.0F);
         this.setRotateAngle(this.tent6, -0.22759093F, 0.4098033F, 0.0F);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         AbstractMobModel.light(60, true);
         this.shape1.render(f5);
         this.shape2.render(f5);
         int anim = entity.ticksExisted / 3 % 3;
         if (anim == 0) {
            this.tent1.render(f5);
            this.tent2.render(f5);
         }

         if (anim == 1) {
            this.tent3.render(f5);
            this.tent4.render(f5);
         }

         if (anim == 2) {
            this.tent5.render(f5);
            this.tent6.render(f5);
         }

         this.head.render(f5);
         AbstractMobModel.returnlight();
         this.spike2.render(f5);
         this.rib2.render(f5);
         this.rib1a.render(f5);
         this.spike1.render(f5);
         this.rib1.render(f5);
         this.rib3.render(f5);
         this.rib3a.render(f5);
         this.rib2a.render(f5);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }

      public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
         this.head.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
         this.head.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
      }
   }

   public static class MechanicModel extends ModelBiped {
      public ModelRenderer bipedBelt;
      public ModelRenderer bipedBelt1;
      public ModelRenderer bipedBelt2;
      public ModelRenderer bipedBackpack;
      public ModelRenderer bipedGoogles1;
      public ModelRenderer bipedGoogles2;
      public ModelRenderer bipedGoogles3;
      public ModelRenderer bipedHammer1;
      public ModelRenderer bipedHammer2;
      public ModelRenderer bipedTool;
      public ModelRenderer clawA1;
      public ModelRenderer clawA2;
      public ModelRenderer clawB1;
      public ModelRenderer clawB2;

      public MechanicModel() {
         this.textureWidth = 64;
         this.textureHeight = 48;
         this.bipedBody = new ModelRenderer(this, 16, 16);
         this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
         this.bipedLeftArm = new ModelRenderer(this, 40, 16);
         this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
         this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
         this.setRotateAngle(this.bipedLeftArm, 0.0F, 0.0F, -0.10000736F);
         this.bipedBelt2 = new ModelRenderer(this, 58, 1);
         this.bipedBelt2.setRotationPoint(0.0F, 8.0F, 0.0F);
         this.bipedBelt2.addBox(-1.5F, 0.0F, -3.5F, 2, 4, 1, 0.0F);
         this.bipedBelt = new ModelRenderer(this, 35, 1);
         this.bipedBelt.setRotationPoint(0.0F, 8.0F, 0.0F);
         this.bipedBelt.addBox(-4.5F, 0.0F, -2.5F, 9, 3, 5, 0.0F);
         this.bipedGoogles1 = new ModelRenderer(this, 24, -9);
         this.bipedGoogles1.setRotationPoint(0.0F, 0.5F, -1.0F);
         this.bipedGoogles1.addBox(-4.5F, -7.0F, -4.5F, 9, 1, 9, 0.0F);
         this.setRotateAngle(this.bipedGoogles1, -0.13665928F, 0.0F, 0.0F);
         this.clawB2 = new ModelRenderer(this, 32, 45);
         this.clawB2.setRotationPoint(0.0F, 4.5F, -2.0F);
         this.clawB2.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
         this.setRotateAngle(this.clawB2, (float) (Math.PI / 2), 0.0F, 0.0F);
         this.bipedTool = new ModelRenderer(this, 38, 33);
         this.bipedTool.setRotationPoint(-1.0F, 5.1F, -2.0F);
         this.bipedTool.addBox(-0.5F, 0.0F, -0.5F, 5, 5, 5, 0.0F);
         this.bipedHead = new ModelRenderer(this, 0, 0);
         this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
         this.clawA1 = new ModelRenderer(this, 32, 38);
         this.clawA1.setRotationPoint(2.0F, 4.5F, 2.0F);
         this.clawA1.addBox(-0.5F, 0.0F, -3.0F, 1, 5, 1, 0.0F);
         this.setRotateAngle(this.clawA1, -0.27314404F, (float) (-Math.PI / 4), 0.0F);
         this.bipedGoogles3 = new ModelRenderer(this, 0, 4);
         this.bipedGoogles3.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.bipedGoogles3.addBox(1.0F, -7.5F, -6.0F, 2, 2, 2, 0.0F);
         this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
         this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.1F);
         this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
         this.bipedHammer2 = new ModelRenderer(this, 0, 32);
         this.bipedHammer2.setRotationPoint(0.5F, -6.5F, 0.5F);
         this.bipedHammer2.addBox(-1.5F, 0.0F, -2.5F, 3, 3, 5, 0.0F);
         this.bipedRightArm = new ModelRenderer(this, 40, 16);
         this.bipedRightArm.mirror = true;
         this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
         this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
         this.setRotateAngle(this.bipedRightArm, 0.0F, 0.0F, 0.10000736F);
         this.bipedHammer1 = new ModelRenderer(this, 60, 18);
         this.bipedHammer1.setRotationPoint(-1.5F, 9.0F, -1.0F);
         this.bipedHammer1.addBox(0.0F, -7.0F, 0.0F, 1, 13, 1, 0.0F);
         this.setRotateAngle(this.bipedHammer1, 1.7301449F, -0.091106184F, 0.0F);
         this.clawB1 = new ModelRenderer(this, 32, 45);
         this.clawB1.setRotationPoint(0.0F, 4.5F, -2.0F);
         this.clawB1.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
         this.setRotateAngle(this.clawB1, (float) (Math.PI / 2), 0.0F, 0.0F);
         this.clawA2 = new ModelRenderer(this, 32, 38);
         this.clawA2.setRotationPoint(2.0F, 4.5F, 2.0F);
         this.clawA2.addBox(-0.5F, 0.0F, -3.0F, 1, 5, 1, 0.0F);
         this.setRotateAngle(this.clawA2, -0.27314404F, (float) (Math.PI * 3.0 / 4.0), 0.0F);
         this.bipedRightLeg = new ModelRenderer(this, 0, 16);
         this.bipedRightLeg.mirror = true;
         this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.1F);
         this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
         this.bipedBelt1 = new ModelRenderer(this, 58, 1);
         this.bipedBelt1.setRotationPoint(0.0F, 8.0F, 0.0F);
         this.bipedBelt1.addBox(1.5F, 0.0F, -3.5F, 2, 4, 1, 0.0F);
         this.bipedBackpack = new ModelRenderer(this, 13, 37);
         this.bipedBackpack.setRotationPoint(0.0F, 1.0F, 0.0F);
         this.bipedBackpack.addBox(-3.0F, 0.0F, 2.0F, 6, 8, 3, 0.0F);
         this.bipedGoogles2 = new ModelRenderer(this, 0, 0);
         this.bipedGoogles2.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.bipedGoogles2.addBox(-3.0F, -7.5F, -6.0F, 2, 2, 2, 0.0F);
         this.bipedBody.addChild(this.bipedBelt2);
         this.bipedBody.addChild(this.bipedBelt);
         this.bipedHead.addChild(this.bipedGoogles1);
         this.clawA2.addChild(this.clawB2);
         this.bipedLeftArm.addChild(this.bipedTool);
         this.bipedTool.addChild(this.clawA1);
         this.bipedGoogles1.addChild(this.bipedGoogles3);
         this.bipedHammer1.addChild(this.bipedHammer2);
         this.bipedRightArm.addChild(this.bipedHammer1);
         this.clawA1.addChild(this.clawB1);
         this.bipedTool.addChild(this.clawA2);
         this.bipedBody.addChild(this.bipedBelt1);
         this.bipedBody.addChild(this.bipedBackpack);
         this.bipedGoogles1.addChild(this.bipedGoogles2);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
         this.bipedBody.render(f5);
         this.bipedLeftArm.render(f5);
         this.bipedHead.render(f5);
         this.bipedLeftLeg.render(f5);
         this.bipedRightArm.render(f5);
         this.bipedRightLeg.render(f5);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class MechanicalWatcherModel extends ModelBase {
      public ModelRenderer shapeColb;
      public ModelRenderer shapeGun;
      public ModelRenderer shapeEye;
      public ModelRenderer shape2;
      public ModelRenderer shape3;
      public ModelRenderer shape4;
      public ModelRenderer shape5;
      public ModelRenderer shape6;
      public ModelRenderer shape1;
      public ModelRenderer shapeEngine1;
      public ModelRenderer shapeWingA1;
      public ModelRenderer shapeWingA2;
      public ModelRenderer shapeWingA3;
      public ModelRenderer shapeTube;
      public ModelRenderer shapeEngine2;
      public ModelRenderer shapeWingB1;
      public ModelRenderer shapeWingB2;
      public ModelRenderer shapeWingB3;
      public ModelRenderer shapeGunUp;
      public ModelRenderer shapeGunDown;

      public MechanicalWatcherModel() {
         this.textureWidth = 64;
         this.textureHeight = 32;
         this.shapeWingB1 = new ModelRenderer(this, 0, 0);
         this.shapeWingB1.mirror = true;
         this.shapeWingB1.setRotationPoint(-3.5F, 13.0F, 4.0F);
         this.shapeWingB1.addBox(-16.0F, 0.0F, -1.0F, 16, 0, 2, 0.0F);
         this.setRotateAngle(this.shapeWingB1, 0.0F, 0.18203785F, 0.0F);
         this.shape3 = new ModelRenderer(this, 48, 8);
         this.shape3.setRotationPoint(0.0F, 13.4F, 6.6F);
         this.shape3.addBox(-1.5F, -2.0F, -2.0F, 3, 3, 4, 0.0F);
         this.setRotateAngle(this.shape3, -1.2292354F, 0.0F, 0.0F);
         this.shapeGun = new ModelRenderer(this, 38, 24);
         this.shapeGun.setRotationPoint(0.0F, 22.0F, 1.0F);
         this.shapeGun.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
         this.setRotateAngle(this.shapeGun, 0.7740535F, 0.0F, 0.0F);
         this.shapeWingB3 = new ModelRenderer(this, 0, 4);
         this.shapeWingB3.mirror = true;
         this.shapeWingB3.setRotationPoint(-3.5F, 13.0F, 0.0F);
         this.shapeWingB3.addBox(-4.0F, 0.0F, -1.0F, 4, 0, 2, 0.0F);
         this.setRotateAngle(this.shapeWingB3, 0.0F, -0.18203785F, 0.0F);
         this.shapeGunDown = new ModelRenderer(this, 44, 23);
         this.shapeGunDown.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shapeGunDown.addBox(-1.0F, 0.5F, -8.5F, 2, 1, 8, 0.0F);
         this.setRotateAngle(this.shapeGunDown, (float) (-Math.PI / 4), 0.0F, 0.0F);
         this.shape5 = new ModelRenderer(this, 48, 15);
         this.shape5.setRotationPoint(0.0F, 18.6F, 5.7F);
         this.shape5.addBox(-1.0F, -2.0F, -2.0F, 2, 3, 4, 0.0F);
         this.setRotateAngle(this.shape5, -2.1399481F, 0.0F, 0.0F);
         this.shape6 = new ModelRenderer(this, 40, 18);
         this.shape6.setRotationPoint(0.0F, 19.9F, 5.0F);
         this.shape6.addBox(-1.0F, -1.0F, -3.5F, 2, 2, 4, 0.0F);
         this.setRotateAngle(this.shape6, 0.5009095F, 0.0F, 0.0F);
         this.shapeWingA1 = new ModelRenderer(this, 0, 0);
         this.shapeWingA1.setRotationPoint(3.5F, 13.0F, 4.0F);
         this.shapeWingA1.addBox(0.0F, 0.0F, -1.0F, 16, 0, 2, 0.0F);
         this.setRotateAngle(this.shapeWingA1, 0.0F, -0.18203785F, 0.0F);
         this.shapeEye = new ModelRenderer(this, 0, 8);
         this.shapeEye.setRotationPoint(0.0F, 9.5F, 0.0F);
         this.shapeEye.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
         this.setRotateAngle(this.shapeEye, 0.0F, 0.7740535F, 0.0F);
         this.shape2 = new ModelRenderer(this, 34, 4);
         this.shape2.setRotationPoint(0.0F, 10.8F, 5.4F);
         this.shape2.addBox(-2.5F, -2.0F, -2.0F, 5, 3, 4, 0.0F);
         this.setRotateAngle(this.shape2, -0.63739425F, 0.0F, 0.0F);
         this.shapeEngine1 = new ModelRenderer(this, 0, 12);
         this.shapeEngine1.setRotationPoint(2.5F, 13.0F, 0.0F);
         this.shapeEngine1.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 6, 0.0F);
         this.setRotateAngle(this.shapeEngine1, 0.0F, 0.0F, (float) (Math.PI / 4));
         this.shapeTube = new ModelRenderer(this, 16, 17);
         this.shapeTube.setRotationPoint(0.0F, 15.0F, 1.0F);
         this.shapeTube.addBox(0.0F, 0.0F, -4.0F, 0, 6, 5, 0.0F);
         this.shapeEngine2 = new ModelRenderer(this, 0, 12);
         this.shapeEngine2.mirror = true;
         this.shapeEngine2.setRotationPoint(-2.5F, 13.0F, 0.0F);
         this.shapeEngine2.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 6, 0.0F);
         this.setRotateAngle(this.shapeEngine2, 0.0F, 0.0F, (float) (-Math.PI / 4));
         this.shapeWingB2 = new ModelRenderer(this, 0, 2);
         this.shapeWingB2.mirror = true;
         this.shapeWingB2.setRotationPoint(-3.5F, 13.0F, 2.0F);
         this.shapeWingB2.addBox(-8.0F, 0.0F, -1.0F, 8, 0, 2, 0.0F);
         this.shapeWingA2 = new ModelRenderer(this, 0, 2);
         this.shapeWingA2.setRotationPoint(3.5F, 13.0F, 2.0F);
         this.shapeWingA2.addBox(0.0F, 0.0F, -1.0F, 8, 0, 2, 0.0F);
         this.shape1 = new ModelRenderer(this, 48, 0);
         this.shape1.setRotationPoint(0.0F, 10.0F, 2.0F);
         this.shape1.addBox(-1.5F, -2.0F, -2.0F, 3, 3, 5, 0.0F);
         this.shape4 = new ModelRenderer(this, 40, 11);
         this.shape4.setRotationPoint(0.0F, 16.3F, 6.7F);
         this.shape4.addBox(-1.0F, -2.0F, -2.0F, 2, 3, 4, 0.0F);
         this.setRotateAngle(this.shape4, -1.6390387F, 0.0F, 0.0F);
         this.shapeGunUp = new ModelRenderer(this, 24, 23);
         this.shapeGunUp.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.shapeGunUp.addBox(-1.0F, -1.5F, -8.5F, 2, 1, 8, 0.0F);
         this.setRotateAngle(this.shapeGunUp, (float) (-Math.PI / 4), 0.0F, 0.0F);
         this.shapeColb = new ModelRenderer(this, 0, 20);
         this.shapeColb.setRotationPoint(0.0F, 13.0F, 0.0F);
         this.shapeColb.addBox(-2.0F, -2.0F, -4.0F, 4, 4, 8, 0.0F);
         this.shapeWingA3 = new ModelRenderer(this, 0, 4);
         this.shapeWingA3.setRotationPoint(3.5F, 13.0F, 0.0F);
         this.shapeWingA3.addBox(0.0F, 0.0F, -1.0F, 4, 0, 2, 0.0F);
         this.setRotateAngle(this.shapeWingA3, 0.0F, 0.18203785F, 0.0F);
         this.shapeGun.addChild(this.shapeGunDown);
         this.shapeGun.addChild(this.shapeGunUp);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         this.shapeWingA1.rotateAngleZ = 0.4F * MathHelper.sin(AnimationTimer.tick / 20.0F);
         this.shapeWingA2.rotateAngleZ = 0.4F * MathHelper.sin(AnimationTimer.tick / 20.0F + 0.3F);
         this.shapeWingA3.rotateAngleZ = 0.4F * MathHelper.sin(AnimationTimer.tick / 20.0F + 0.6F);
         this.shapeWingB1.rotateAngleZ = -this.shapeWingA1.rotateAngleZ;
         this.shapeWingB2.rotateAngleZ = -this.shapeWingA2.rotateAngleZ;
         this.shapeWingB3.rotateAngleZ = -this.shapeWingA3.rotateAngleZ;
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape3.offsetX, this.shape3.offsetY, this.shape3.offsetZ);
         GlStateManager.translate(this.shape3.rotationPointX * f5, this.shape3.rotationPointY * f5, this.shape3.rotationPointZ * f5);
         GlStateManager.scale(0.8, 1.0, 1.0);
         GlStateManager.translate(-this.shape3.offsetX, -this.shape3.offsetY, -this.shape3.offsetZ);
         GlStateManager.translate(-this.shape3.rotationPointX * f5, -this.shape3.rotationPointY * f5, -this.shape3.rotationPointZ * f5);
         this.shape3.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape5.offsetX, this.shape5.offsetY, this.shape5.offsetZ);
         GlStateManager.translate(this.shape5.rotationPointX * f5, this.shape5.rotationPointY * f5, this.shape5.rotationPointZ * f5);
         GlStateManager.scale(0.9, 0.8, 0.8);
         GlStateManager.translate(-this.shape5.offsetX, -this.shape5.offsetY, -this.shape5.offsetZ);
         GlStateManager.translate(-this.shape5.rotationPointX * f5, -this.shape5.rotationPointY * f5, -this.shape5.rotationPointZ * f5);
         this.shape5.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape6.offsetX, this.shape6.offsetY, this.shape6.offsetZ);
         GlStateManager.translate(this.shape6.rotationPointX * f5, this.shape6.rotationPointY * f5, this.shape6.rotationPointZ * f5);
         GlStateManager.scale(0.8, 0.8, 1.0);
         GlStateManager.translate(-this.shape6.offsetX, -this.shape6.offsetY, -this.shape6.offsetZ);
         GlStateManager.translate(-this.shape6.rotationPointX * f5, -this.shape6.rotationPointY * f5, -this.shape6.rotationPointZ * f5);
         this.shape6.render(f5);
         GlStateManager.popMatrix();
         this.shape2.render(f5);
         this.shapeEngine1.render(f5);
         this.shapeTube.render(f5);
         this.shapeEngine2.render(f5);
         this.shape1.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape4.offsetX, this.shape4.offsetY, this.shape4.offsetZ);
         GlStateManager.translate(this.shape4.rotationPointX * f5, this.shape4.rotationPointY * f5, this.shape4.rotationPointZ * f5);
         GlStateManager.scale(1.0, 0.9, 0.9);
         GlStateManager.translate(-this.shape4.offsetX, -this.shape4.offsetY, -this.shape4.offsetZ);
         GlStateManager.translate(-this.shape4.rotationPointX * f5, -this.shape4.rotationPointY * f5, -this.shape4.rotationPointZ * f5);
         this.shape4.render(f5);
         GlStateManager.popMatrix();
         AbstractMobModel.light(200, false);
         this.shapeGun.render(f5);
         this.shapeEye.render(f5);
         this.shapeColb.render(f5);
         GlStateManager.enableBlend();
         this.shapeWingB1.render(f5);
         this.shapeWingB3.render(f5);
         this.shapeWingA1.render(f5);
         this.shapeWingB2.render(f5);
         this.shapeWingA2.render(f5);
         this.shapeWingA3.render(f5);
         GlStateManager.disableBlend();
         AbstractMobModel.returnlight();
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }

      public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
         this.shapeGun.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
         this.shapeGun.rotateAngleX = 0.785398F + headPitch * (float) (Math.PI / 180.0);
         this.shapeEye.rotateAngleY = 0.785398F + netHeadYaw * (float) (Math.PI / 180.0);
         this.shapeTube.rotateAngleY = this.shapeGun.rotateAngleY / 2.0F;
      }
   }

   public static class VarmintModel extends AbstractMobModel {
      public ModelRenderer shape;
      public ModelRenderer bipedHead;
      public ModelRenderer bipedLeftLeg1;
      public ModelRenderer shapew1;
      public ModelRenderer shapew2;
      public ModelRenderer bipedRightLeg1;
      public ModelRenderer bipedskull;
      public ModelRenderer shapecrown;
      public ModelRenderer bipedLeftLeg2;
      public ModelRenderer bipedLeftLeg3;
      public ModelRenderer bipedRightLeg2;
      public ModelRenderer bipedRightLeg3;

      public VarmintModel() {
         this.textureWidth = 64;
         this.textureHeight = 32;
         this.bipedRightLeg3 = new ModelRenderer(this, 30, 0);
         this.bipedRightLeg3.mirror = true;
         this.bipedRightLeg3.setRotationPoint(0.0F, 9.0F, 0.0F);
         this.bipedRightLeg3.addBox(-1.8F, 0.0F, -1.0F, 3, 9, 4, 0.0F);
         this.setRotateAngle(this.bipedRightLeg3, -2.4130921F, 0.13665928F, -0.22759093F);
         this.shapecrown = new ModelRenderer(this, 28, 16);
         this.shapecrown.setRotationPoint(0.0F, -6.0F, 1.0F);
         this.shapecrown.addBox(-5.0F, 0.0F, -3.0F, 10, 0, 16, 0.0F);
         this.setRotateAngle(this.shapecrown, 0.5462881F, 0.0F, 0.0F);
         this.shapew1 = new ModelRenderer(this, 28, 0);
         this.shapew1.setRotationPoint(3.0F, 9.0F, -2.0F);
         this.shapew1.addBox(-2.0F, 0.0F, -16.0F, 10, 0, 16, 0.0F);
         this.setRotateAngle(this.shapew1, 1.2292354F, 0.0F, 0.0F);
         this.shapew2 = new ModelRenderer(this, 28, 0);
         this.shapew2.mirror = true;
         this.shapew2.setRotationPoint(-3.0F, 9.0F, -2.0F);
         this.shapew2.addBox(-8.0F, 0.0F, -16.0F, 10, 0, 16, 0.0F);
         this.setRotateAngle(this.shapew2, 1.2292354F, 0.0F, 0.0F);
         this.bipedLeftLeg2 = new ModelRenderer(this, 28, 11);
         this.bipedLeftLeg2.setRotationPoint(0.0F, 9.0F, 0.0F);
         this.bipedLeftLeg2.addBox(-0.5F, 0.0F, -1.0F, 1, 9, 2, 0.0F);
         this.setRotateAngle(this.bipedLeftLeg2, 2.6406832F, 0.0F, 0.27314404F);
         this.shape = new ModelRenderer(this, 0, 18);
         this.shape.setRotationPoint(0.0F, 14.0F, 3.0F);
         this.shape.addBox(-3.0F, -8.0F, -4.0F, 6, 8, 6, 0.0F);
         this.setRotateAngle(this.shape, 0.5009095F, 0.0F, 0.0F);
         this.bipedskull = new ModelRenderer(this, 24, 22);
         this.bipedskull.setRotationPoint(0.0F, 8.0F, -1.0F);
         this.bipedskull.addBox(-2.5F, -6.5F, -2.5F, 5, 5, 5, 0.2F);
         this.bipedHead = new ModelRenderer(this, 0, 0);
         this.bipedHead.setRotationPoint(0.0F, 8.0F, -1.0F);
         this.bipedHead.addBox(-3.5F, -7.5F, -3.5F, 7, 7, 7, 0.0F);
         this.bipedRightLeg2 = new ModelRenderer(this, 28, 11);
         this.bipedRightLeg2.setRotationPoint(0.0F, 9.0F, 0.0F);
         this.bipedRightLeg2.addBox(-0.5F, 0.0F, -1.0F, 1, 9, 2, 0.0F);
         this.setRotateAngle(this.bipedRightLeg2, 2.6406832F, 0.0F, -0.27314404F);
         this.bipedLeftLeg1 = new ModelRenderer(this, 34, 10);
         this.bipedLeftLeg1.setRotationPoint(2.9F, 12.0F, 3.0F);
         this.bipedLeftLeg1.addBox(-1.0F, 0.0F, -2.0F, 2, 9, 3, 0.0F);
         this.setRotateAngle(this.bipedLeftLeg1, -0.5009095F, 0.0F, 0.0F);
         this.bipedRightLeg1 = new ModelRenderer(this, 34, 10);
         this.bipedRightLeg1.setRotationPoint(-2.9F, 12.0F, 3.0F);
         this.bipedRightLeg1.addBox(-1.0F, 0.0F, -2.0F, 2, 9, 3, 0.0F);
         this.setRotateAngle(this.bipedRightLeg1, -0.5009095F, 0.0F, 0.0F);
         this.bipedLeftLeg3 = new ModelRenderer(this, 30, 0);
         this.bipedLeftLeg3.setRotationPoint(0.0F, 9.0F, 0.0F);
         this.bipedLeftLeg3.addBox(-1.2F, 0.0F, -1.0F, 3, 9, 4, 0.0F);
         this.setRotateAngle(this.bipedLeftLeg3, -2.4130921F, -0.13665928F, 0.22759093F);
         this.bipedRightLeg2.addChild(this.bipedRightLeg3);
         this.bipedHead.addChild(this.shapecrown);
         this.bipedLeftLeg1.addChild(this.bipedLeftLeg2);
         this.bipedRightLeg1.addChild(this.bipedRightLeg2);
         this.bipedLeftLeg2.addChild(this.bipedLeftLeg3);
      }

      @Override
      public void render(
         Entity entityIn,
         float limbSwing,
         float limbSwingAmount,
         float ageInTicks,
         float netHeadYaw,
         float headPitch,
         float scale,
         int an1,
         int an2,
         int an3,
         int an4,
         boolean isAbstractMob
      ) {
         this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
         if (isAbstractMob && ((AbstractMob)entityIn).var2 > 0.0F) {
            this.shapecrown.isHidden = false;
         } else {
            this.shapecrown.isHidden = true;
         }

         this.shapew1.render(scale);
         this.shapew2.render(scale);
         this.shape.render(scale);
         this.bipedskull.render(scale);
         this.bipedHead.render(scale);
         this.bipedLeftLeg1.render(scale);
         this.bipedRightLeg1.render(scale);
      }

      public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
         this.bipedHead.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
         this.bipedHead.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
         this.bipedskull.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
         this.bipedskull.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
         this.bipedLeftLeg1.rotateAngleX = -0.5009095F;
         this.bipedLeftLeg2.rotateAngleX = 2.6406832F;
         this.bipedLeftLeg3.rotateAngleX = -2.4130921F;
         this.bipedRightLeg1.rotateAngleX = -0.5009095F;
         this.bipedRightLeg2.rotateAngleX = 2.6406832F;
         this.bipedRightLeg3.rotateAngleX = -2.4130921F;
         this.shapew1.rotateAngleX = 1.2292354F;
         this.shapew2.rotateAngleX = 1.2292354F;
         float angl1L = MathHelper.cos(-limbSwing * 0.6662F) * -0.4F * limbSwingAmount;
         float angl2L = MathHelper.sin(-limbSwing * 0.6662F) * 1.2F * limbSwingAmount;
         float angl1R = MathHelper.cos(-limbSwing * 0.6662F + (float) Math.PI) * -0.4F * limbSwingAmount;
         float angl2R = MathHelper.sin(-limbSwing * 0.6662F + (float) Math.PI) * 1.2F * limbSwingAmount;
         this.bipedLeftLeg1.rotateAngleX -= angl2L;
         this.bipedLeftLeg2.rotateAngleX += angl1L;
         this.bipedLeftLeg3.rotateAngleX -= angl1L;
         this.bipedRightLeg1.rotateAngleX -= angl2R;
         this.bipedRightLeg2.rotateAngleX += angl1R;
         this.bipedRightLeg3.rotateAngleX -= angl1R;
         this.shapew1.rotateAngleZ = Math.min(angl1R * 0.4F, 0.0F);
         this.shapew1.rotateAngleX += angl2L * 0.4F;
         this.shapew1.rotateAngleY = -angl2L * 0.6F;
         this.shapew2.rotateAngleZ = Math.max(-angl1L * 0.4F, 0.0F);
         this.shapew2.rotateAngleX += angl2R * 0.4F;
         this.shapew2.rotateAngleY = angl2R * 0.6F;
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }

   public static class WizardModel extends ModelBiped {
      public ModelRenderer bipedBodyWear;
      public ModelRenderer bipedLeftArmWear;
      public ModelRenderer bipedRightArmWear;
      public ModelRenderer bipedCape;
      public ModelRenderer bipedHat1;
      public ModelRenderer bipedHat2;
      public ModelRenderer bipedHat3;
      public ModelRenderer bipedEyes;
      public ModelRenderer bipedHat4;
      public ModelRenderer bipedHat5;
      public ModelRenderer bipedBeard;

      public WizardModel() {
         this.textureWidth = 64;
         this.textureHeight = 64;
         this.bipedHat2 = new ModelRenderer(this, 32, 40);
         this.bipedHat2.setRotationPoint(0.0F, 1.0F, 0.3F);
         this.bipedHat2.addBox(-4.0F, -11.0F, -4.0F, 8, 2, 8, 0.0F);
         this.bipedLeftArm = new ModelRenderer(this, 40, 16);
         this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
         this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
         this.setRotateAngle(this.bipedLeftArm, 0.0F, 0.0F, -0.10000736F);
         this.bipedLeftArmWear = new ModelRenderer(this, 32, 0);
         this.bipedLeftArmWear.setRotationPoint(5.0F, 2.0F, 0.0F);
         this.bipedLeftArmWear.addBox(-1.0F, -2.0F, -2.0F, 4, 6, 4, 0.2F);
         this.setRotateAngle(this.bipedLeftArmWear, 0.0F, 0.0F, -0.10000736F);
         this.bipedCape = new ModelRenderer(this, 48, 0);
         this.bipedCape.setRotationPoint(0.0F, 0.0F, 2.0F);
         this.bipedCape.addBox(-4.0F, 0.0F, 0.0F, 8, 16, 0, 0.31F);
         this.setRotateAngle(this.bipedCape, 0.13665928F, 0.0F, 0.0F);
         this.bipedBody = new ModelRenderer(this, 16, 16);
         this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
         this.bipedRightArmWear = new ModelRenderer(this, 32, 0);
         this.bipedRightArmWear.mirror = true;
         this.bipedRightArmWear.setRotationPoint(-5.0F, 2.0F, 0.0F);
         this.bipedRightArmWear.addBox(-3.0F, -2.0F, -2.0F, 4, 6, 4, 0.2F);
         this.setRotateAngle(this.bipedRightArmWear, 0.0F, 0.0F, 0.10000736F);
         this.bipedRightLeg = new ModelRenderer(this, 0, 16);
         this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.1F);
         this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
         this.bipedRightArm = new ModelRenderer(this, 40, 16);
         this.bipedRightArm.mirror = true;
         this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
         this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
         this.setRotateAngle(this.bipedRightArm, 0.0F, 0.0F, 0.10000736F);
         this.bipedBodyWear = new ModelRenderer(this, 0, 32);
         this.bipedBodyWear.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.bipedBodyWear.addBox(-4.0F, 0.0F, -2.0F, 8, 16, 4, 0.3F);
         this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
         this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.1F);
         this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
         this.bipedHat5 = new ModelRenderer(this, 0, 0);
         this.bipedHat5.setRotationPoint(0.0F, -4.0F, 0.0F);
         this.bipedHat5.addBox(-1.0F, -5.0F, -1.0F, 2, 5, 2, 0.0F);
         this.setRotateAngle(this.bipedHat5, -0.59184116F, 0.0F, 0.0F);
         this.bipedHat1 = new ModelRenderer(this, 16, 50);
         this.bipedHat1.setRotationPoint(0.0F, -2.3F, 0.4F);
         this.bipedHat1.addBox(-6.0F, -6.0F, -6.0F, 12, 2, 12, 0.0F);
         this.setRotateAngle(this.bipedHat1, 0.045553092F, 0.0F, 0.0F);
         this.bipedHat3 = new ModelRenderer(this, 0, 52);
         this.bipedHat3.setRotationPoint(0.0F, -9.3F, 0.6F);
         this.bipedHat3.addBox(-3.0F, -4.0F, -3.0F, 6, 4, 6, 0.0F);
         this.setRotateAngle(this.bipedHat3, -0.18203785F, 0.0F, 0.0F);
         this.bipedEyes = new ModelRendererGlow(this, 32, 12, 80, true);
         this.bipedEyes.setRotationPoint(0.0F, 0.0F, 0.98F);
         this.bipedEyes.addBox(-3.0F, -4.0F, -5.0F, 6, 1, 0, 0.0F);
         this.bipedHead = new ModelRenderer(this, 0, 0);
         this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
         this.bipedHat4 = new ModelRenderer(this, 24, 39);
         this.bipedHat4.setRotationPoint(0.0F, -3.0F, 0.0F);
         this.bipedHat4.addBox(-2.0F, -5.0F, -2.0F, 4, 5, 4, 0.0F);
         this.setRotateAngle(this.bipedHat4, -0.4553564F, 0.0F, 0.0F);
         this.bipedBeard = new ModelRenderer(this, 40, 32);
         this.bipedBeard.setRotationPoint(0.0F, 0.0F, -4.0F);
         this.bipedBeard.addBox(-3.0F, 0.0F, 0.0F, 6, 8, 0, 0.0F);
         this.bipedHead.addChild(this.bipedHat2);
         this.bipedHat4.addChild(this.bipedHat5);
         this.bipedHead.addChild(this.bipedHat1);
         this.bipedHead.addChild(this.bipedHat3);
         this.bipedHead.addChild(this.bipedEyes);
         this.bipedHat3.addChild(this.bipedHat4);
         this.bipedHead.addChild(this.bipedBeard);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
         this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
         this.bipedLeftArm.render(f5);
         this.bipedLeftArmWear.render(f5);
         this.bipedCape.render(f5);
         this.bipedBody.render(f5);
         this.bipedRightArmWear.render(f5);
         this.bipedRightLeg.render(f5);
         this.bipedRightArm.render(f5);
         this.bipedBodyWear.render(f5);
         this.bipedLeftLeg.render(f5);
         this.bipedHead.render(f5);
      }

      public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
         modelRenderer.rotateAngleX = x;
         modelRenderer.rotateAngleY = y;
         modelRenderer.rotateAngleZ = z;
      }
   }
}
