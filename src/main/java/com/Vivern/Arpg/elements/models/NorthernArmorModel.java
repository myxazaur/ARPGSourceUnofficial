//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class NorthernArmorModel extends ModelBiped {
   public ModelRenderer chest;
   public ModelRenderer leftarm;
   public ModelRenderer rightarm;
   public ModelRenderer helm;
   public ModelRenderer leftleg;
   public ModelRenderer rightleg;
   public ModelRenderer rightboot;
   public ModelRenderer leftboot;
   public ModelRenderer chest_1;
   public ModelRenderer chest_2;
   public ModelRenderer leftarm_1;
   public ModelRenderer leftarm_2;
   public ModelRenderer leftarmhorh;
   public ModelRenderer leftarm_3;
   public ModelRenderer leftarm_4;
   public ModelRenderer helm_1;
   public ModelRenderer helm_2;
   public ModelRenderer horn;
   public ModelRenderer horn_1;
   public ModelRenderer horn_2;
   public ModelRenderer horn_3;
   public ModelRenderer horn_4;
   public ModelRenderer horn_5;
   public ModelRenderer rightleg_1;

   public NorthernArmorModel() {
      this.textureWidth = 64;
      this.textureHeight = 48;
      this.leftboot = new ModelRenderer(this, 24, 0);
      this.leftboot.setRotationPoint(1.9F, 12.0F, 0.0F);
      this.leftboot.addBox(-1.8F, 8.0F, -3.01F, 4, 4, 5, 0.4F);
      this.leftarm_4 = new ModelRenderer(this, 40, 17);
      this.leftarm_4.mirror = true;
      this.leftarm_4.setRotationPoint(0.0F, 6.0F, 0.0F);
      this.leftarm_4.addBox(-3.4F, -1.0F, -2.5F, 4, 3, 5, 0.0F);
      this.helm_1 = new ModelRenderer(this, 0, 37);
      this.helm_1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.helm_1.addBox(-4.5F, -6.5F, -4.5F, 9, 2, 9, 0.0F);
      this.chest = new ModelRenderer(this, 16, 17);
      this.chest.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.chest.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.5F);
      this.rightboot = new ModelRenderer(this, 24, 0);
      this.rightboot.mirror = true;
      this.rightboot.setRotationPoint(-1.9F, 12.0F, 0.0F);
      this.rightboot.addBox(-2.2F, 8.0F, -3.0F, 4, 4, 5, 0.4F);
      this.helm_2 = new ModelRenderer(this, 31, 26);
      this.helm_2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.helm_2.addBox(-1.0F, -8.5F, -4.5F, 2, 2, 9, 0.0F);
      this.chest_2 = new ModelRenderer(this, 14, 17);
      this.chest_2.setRotationPoint(0.0F, 7.3F, -2.5F);
      this.chest_2.addBox(-1.5F, -1.5F, -0.5F, 2, 2, 1, 0.0F);
      this.setRotateAngle(this.chest_2, 0.0F, 0.0F, (float) (Math.PI / 4));
      this.leftleg = new ModelRenderer(this, 0, 17);
      this.leftleg.setRotationPoint(1.9F, 12.0F, 0.0F);
      this.leftleg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.2F);
      this.horn_1 = new ModelRenderer(this, 32, 9);
      this.horn_1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.horn_1.addBox(-8.0F, -7.5F, -1.5F, 4, 3, 3, 0.0F);
      this.horn = new ModelRenderer(this, 32, 9);
      this.horn.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.horn.addBox(4.0F, -7.5F, -1.5F, 4, 3, 3, 0.0F);
      this.leftarm = new ModelRenderer(this, 42, 0);
      this.leftarm.setRotationPoint(5.0F, 2.0F, 0.0F);
      this.leftarm.addBox(-0.5F, -2.0F, -2.0F, 4, 4, 4, 0.4F);
      this.horn_5 = new ModelRenderer(this, 0, 0);
      this.horn_5.setRotationPoint(0.0F, 1.0F, -3.0F);
      this.horn_5.addBox(0.0F, 0.0F, -1.5F, 1, 1, 3, 0.0F);
      this.leftarm_3 = new ModelRenderer(this, 40, 9);
      this.leftarm_3.setRotationPoint(0.0F, 3.0F, 0.0F);
      this.leftarm_3.addBox(-4.5F, -0.6F, -3.0F, 5, 2, 6, 0.0F);
      this.horn_3 = new ModelRenderer(this, 0, 0);
      this.horn_3.setRotationPoint(0.0F, 1.0F, -3.0F);
      this.horn_3.addBox(-1.0F, 0.0F, -1.5F, 1, 1, 3, 0.0F);
      this.rightleg = new ModelRenderer(this, 0, 17);
      this.rightleg.mirror = true;
      this.rightleg.setRotationPoint(-1.9F, 12.0F, 0.0F);
      this.rightleg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.2F);
      this.chest_1 = new ModelRenderer(this, 27, 37);
      this.chest_1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.chest_1.addBox(-5.0F, -0.6F, -3.5F, 10, 2, 7, 0.0F);
      this.leftarmhorh = new ModelRenderer(this, 0, 33);
      this.leftarmhorh.setRotationPoint(4.0F, -2.0F, 0.0F);
      this.leftarmhorh.addBox(0.0F, -0.5F, -1.0F, 1, 4, 2, 0.0F);
      this.leftarm_1 = new ModelRenderer(this, 40, 9);
      this.leftarm_1.setRotationPoint(0.0F, 3.0F, 0.0F);
      this.leftarm_1.addBox(-0.5F, -0.6F, -3.0F, 5, 2, 6, 0.0F);
      this.helm = new ModelRenderer(this, 0, 1);
      this.helm.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.helm.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.2F);
      this.rightleg_1 = new ModelRenderer(this, 45, 25);
      this.rightleg_1.setRotationPoint(-0.9F, -2.0F, -1.0F);
      this.rightleg_1.addBox(-2.0F, 0.0F, -2.0F, 5, 6, 4, 0.0F);
      this.rightarm = new ModelRenderer(this, 42, 0);
      this.rightarm.mirror = true;
      this.rightarm.setRotationPoint(-5.0F, 2.0F, 0.0F);
      this.rightarm.addBox(-3.5F, -2.0F, -2.0F, 4, 4, 4, 0.4F);
      this.leftarm_2 = new ModelRenderer(this, 40, 17);
      this.leftarm_2.setRotationPoint(0.0F, 6.0F, 0.0F);
      this.leftarm_2.addBox(-0.6F, -1.0F, -2.5F, 4, 3, 5, 0.0F);
      this.horn_4 = new ModelRenderer(this, 53, 17);
      this.horn_4.mirror = true;
      this.horn_4.setRotationPoint(-7.0F, -4.5F, 0.0F);
      this.horn_4.addBox(-1.0F, 0.0F, -1.5F, 2, 2, 3, 0.0F);
      this.horn_2 = new ModelRenderer(this, 53, 17);
      this.horn_2.setRotationPoint(7.0F, -4.5F, 0.0F);
      this.horn_2.addBox(-1.0F, 0.0F, -1.5F, 2, 2, 3, 0.0F);
      this.rightarm.addChild(this.leftarm_4);
      this.helm.addChild(this.helm_1);
      this.helm.addChild(this.helm_2);
      this.chest.addChild(this.chest_2);
      this.helm.addChild(this.horn_1);
      this.helm.addChild(this.horn);
      this.horn_4.addChild(this.horn_5);
      this.rightarm.addChild(this.leftarm_3);
      this.horn_2.addChild(this.horn_3);
      this.chest.addChild(this.chest_1);
      this.leftarm.addChild(this.leftarmhorh);
      this.leftarm.addChild(this.leftarm_1);
      this.rightleg.addChild(this.rightleg_1);
      this.leftarm.addChild(this.leftarm_2);
      this.horn_1.addChild(this.horn_4);
      this.horn.addChild(this.horn_2);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.leftarm.render(f5);
      this.helm.render(f5);
      this.rightleg.render(f5);
      this.rightarm.render(f5);
      this.chest.render(f5);
      this.leftboot.render(f5);
      this.rightboot.render(f5);
      this.leftleg.render(f5);
   }

   public void setModelAttributes(ModelBase model) {
      super.setModelAttributes(model);
      if (model instanceof ModelBiped) {
         ModelBiped modelbiped = (ModelBiped)model;
         this.rightarm.rotateAngleX = modelbiped.bipedRightArm.rotateAngleX;
         this.rightarm.rotateAngleY = modelbiped.bipedRightArm.rotateAngleY;
         this.rightarm.rotateAngleZ = modelbiped.bipedRightArm.rotateAngleZ;
         this.leftarm.rotateAngleX = modelbiped.bipedLeftArm.rotateAngleX;
         this.leftarm.rotateAngleY = modelbiped.bipedLeftArm.rotateAngleY;
         this.leftarm.rotateAngleZ = modelbiped.bipedLeftArm.rotateAngleZ;
         this.helm.rotateAngleX = modelbiped.bipedHead.rotateAngleX;
         this.helm.rotateAngleY = modelbiped.bipedHead.rotateAngleY;
         this.helm.rotateAngleZ = modelbiped.bipedHead.rotateAngleZ;
         this.chest.rotateAngleX = modelbiped.bipedBody.rotateAngleX;
         this.chest.rotateAngleY = modelbiped.bipedBody.rotateAngleY;
         this.chest.rotateAngleZ = modelbiped.bipedBody.rotateAngleZ;
         this.rightleg.rotateAngleX = modelbiped.bipedRightLeg.rotateAngleX;
         this.rightleg.rotateAngleY = modelbiped.bipedRightLeg.rotateAngleY;
         this.rightleg.rotateAngleZ = modelbiped.bipedRightLeg.rotateAngleZ;
         this.leftleg.rotateAngleX = modelbiped.bipedLeftLeg.rotateAngleX;
         this.leftleg.rotateAngleY = modelbiped.bipedLeftLeg.rotateAngleY;
         this.leftleg.rotateAngleZ = modelbiped.bipedLeftLeg.rotateAngleZ;
         this.rightboot.rotateAngleX = modelbiped.bipedRightLeg.rotateAngleX;
         this.rightboot.rotateAngleY = modelbiped.bipedRightLeg.rotateAngleY;
         this.rightboot.rotateAngleZ = modelbiped.bipedRightLeg.rotateAngleZ;
         this.leftboot.rotateAngleX = modelbiped.bipedLeftLeg.rotateAngleX;
         this.leftboot.rotateAngleY = modelbiped.bipedLeftLeg.rotateAngleY;
         this.leftboot.rotateAngleZ = modelbiped.bipedLeftLeg.rotateAngleZ;
         this.rightarm.rotationPointX = modelbiped.bipedRightArm.rotationPointX;
         this.rightarm.rotationPointY = modelbiped.bipedRightArm.rotationPointY;
         this.rightarm.rotationPointZ = modelbiped.bipedRightArm.rotationPointZ;
         this.leftarm.rotationPointX = modelbiped.bipedLeftArm.rotationPointX;
         this.leftarm.rotationPointY = modelbiped.bipedLeftArm.rotationPointY;
         this.leftarm.rotationPointZ = modelbiped.bipedLeftArm.rotationPointZ;
         this.helm.rotationPointX = modelbiped.bipedHead.rotationPointX;
         this.helm.rotationPointY = modelbiped.bipedHead.rotationPointY;
         this.helm.rotationPointZ = modelbiped.bipedHead.rotationPointZ;
         this.chest.rotationPointX = modelbiped.bipedBody.rotationPointX;
         this.chest.rotationPointY = modelbiped.bipedBody.rotationPointY;
         this.chest.rotationPointZ = modelbiped.bipedBody.rotationPointZ;
         this.rightleg.rotationPointX = modelbiped.bipedRightLeg.rotationPointX;
         this.rightleg.rotationPointY = modelbiped.bipedRightLeg.rotationPointY;
         this.rightleg.rotationPointZ = modelbiped.bipedRightLeg.rotationPointZ;
         this.leftleg.rotationPointX = modelbiped.bipedLeftLeg.rotationPointX;
         this.leftleg.rotationPointY = modelbiped.bipedLeftLeg.rotationPointY;
         this.leftleg.rotationPointZ = modelbiped.bipedLeftLeg.rotationPointZ;
         this.rightboot.rotationPointX = modelbiped.bipedRightLeg.rotationPointX;
         this.rightboot.rotationPointY = modelbiped.bipedRightLeg.rotationPointY;
         this.rightboot.rotationPointZ = modelbiped.bipedRightLeg.rotationPointZ;
         this.leftboot.rotationPointX = modelbiped.bipedLeftLeg.rotationPointX;
         this.leftboot.rotationPointY = modelbiped.bipedLeftLeg.rotationPointY;
         this.leftboot.rotationPointZ = modelbiped.bipedLeftLeg.rotationPointZ;
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
