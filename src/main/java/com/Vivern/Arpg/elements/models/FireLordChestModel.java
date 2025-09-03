//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class FireLordChestModel extends ModelBiped {
   public ModelRenderer cobe1;
   public ModelRenderer cubearm1;
   public ModelRenderer cubearm2;
   public ModelRenderer cubearm3;
   public ModelRenderer cubearm4;
   public ModelRenderer cubearm5;
   public ModelRenderer cubearm6;

   public FireLordChestModel() {
      this.bipedBody.showModel = false;
      this.bipedHead.showModel = false;
      this.bipedHeadwear.showModel = false;
      this.bipedLeftArm.showModel = false;
      this.bipedRightArm.showModel = false;
      this.bipedLeftLeg.showModel = false;
      this.bipedRightLeg.showModel = false;
      this.textureWidth = 64;
      this.textureHeight = 32;
      this.cubearm1 = new ModelRenderer(this, 40, 16);
      this.cubearm1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.cubearm1.addBox(-1.0F, -3.0F, -2.5F, 5, 10, 5, 0.0F);
      this.cobe1 = new ModelRenderer(this, 16, 0);
      this.cobe1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.cobe1.addBox(-4.5F, -0.5F, -2.5F, 9, 13, 5, 0.0F);
      this.cubearm5 = new ModelRenderer(this, 44, 0);
      this.cubearm5.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.cubearm5.addBox(-2.4F, -6.0F, -1.5F, 3, 3, 3, 0.0F);
      this.setRotateAngle(this.cubearm5, 0.0F, 0.0F, -0.7285004F);
      this.cubearm6 = new ModelRenderer(this, 56, 16);
      this.cubearm6.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.cubearm6.addBox(-2.5F, -8.5F, -1.0F, 2, 3, 2, 0.0F);
      this.setRotateAngle(this.cubearm6, 0.0F, 0.0F, -0.63739425F);
      this.cubearm2 = new ModelRenderer(this, 40, 16);
      this.cubearm2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.cubearm2.addBox(-4.0F, -3.0F, -2.5F, 5, 10, 5, 0.0F);
      this.cubearm3 = new ModelRenderer(this, 0, 0);
      this.cubearm3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.cubearm3.addBox(-0.6F, -6.0F, -1.5F, 3, 3, 3, 0.0F);
      this.setRotateAngle(this.cubearm3, 0.0F, 0.0F, 0.7285004F);
      this.cubearm4 = new ModelRenderer(this, 56, 0);
      this.cubearm4.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.cubearm4.addBox(0.5F, -8.5F, -1.0F, 2, 3, 2, 0.0F);
      this.setRotateAngle(this.cubearm4, 0.0F, 0.0F, 0.63739425F);
      this.bipedBody.addChild(this.cobe1);
      this.bipedLeftArm.addChild(this.cubearm1);
      this.bipedLeftArm.addChild(this.cubearm3);
      this.bipedLeftArm.addChild(this.cubearm4);
      this.bipedRightArm.addChild(this.cubearm2);
      this.bipedRightArm.addChild(this.cubearm5);
      this.bipedRightArm.addChild(this.cubearm6);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
