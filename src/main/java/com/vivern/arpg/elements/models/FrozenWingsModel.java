package com.vivern.arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class FrozenWingsModel extends ModelBase {
   public ModelRenderer shape1a;
   public ModelRenderer shape2a;
   public ModelRenderer shape9a;
   public ModelRenderer shape3a;
   public ModelRenderer shape8a;
   public ModelRenderer shapeF1;
   public ModelRenderer shapeF2;
   public ModelRenderer shapeF3;
   public ModelRenderer shapeF4;
   public ModelRenderer shapeF5;
   public ModelRenderer shapeF6;
   public ModelRenderer shapeF7;

   public FrozenWingsModel() {
      this.textureWidth = 14;
      this.textureHeight = 26;
      this.shape1a = new ModelRenderer(this, 0, 0);
      this.shape1a.setRotationPoint(1.5F, 1.7F, 2.0F);
      this.shape1a.addBox(-1.0F, 0.0F, -1.0F, 2, 7, 2, 0.0F);
      this.setRotateAngle(this.shape1a, 0.27314404F, 0.0F, -0.13665928F);
      this.shape3a = new ModelRenderer(this, 4, 9);
      this.shape3a.setRotationPoint(0.0F, 8.0F, -0.1F);
      this.shape3a.addBox(0.0F, 0.0F, 0.0F, 1, 16, 1, 0.0F);
      this.setRotateAngle(this.shape3a, 0.0F, 0.0F, 2.1399481F);
      this.shapeF6 = new ModelRenderer(this, 0, 14);
      this.shapeF6.setRotationPoint(-1.1F, 0.7F, 0.25F);
      this.shapeF6.addBox(0.0F, 0.0F, 0.0F, 2, 11, 0, 0.0F);
      this.setRotateAngle(this.shapeF6, 0.0F, 0.0F, 0.8196066F);
      this.shapeF3 = new ModelRenderer(this, 0, 11);
      this.shapeF3.setRotationPoint(-1.1F, 0.7F, 0.4F);
      this.shapeF3.addBox(0.0F, 0.0F, 0.0F, 2, 14, 0, 0.0F);
      this.setRotateAngle(this.shapeF3, 0.0F, 0.0F, 0.29670596F);
      this.shape8a = new ModelRenderer(this, 8, 10);
      this.shape8a.mirror = true;
      this.shape8a.setRotationPoint(-1.1F, 4.7F, 0.05F);
      this.shape8a.addBox(0.0F, 0.0F, 0.0F, 3, 6, 0, 0.0F);
      this.setRotateAngle(this.shape8a, 0.0F, 0.0F, -2.8228955F);
      this.shapeF5 = new ModelRenderer(this, 0, 13);
      this.shapeF5.setRotationPoint(-1.1F, 0.7F, 0.3F);
      this.shapeF5.addBox(0.0F, 0.0F, 0.0F, 2, 12, 0, 0.0F);
      this.setRotateAngle(this.shapeF5, 0.0F, 0.0F, 0.63739425F);
      this.shapeF2 = new ModelRenderer(this, 0, 10);
      this.shapeF2.setRotationPoint(-1.1F, 0.7F, 0.45F);
      this.shapeF2.addBox(0.0F, 0.0F, 0.0F, 2, 15, 0, 0.0F);
      this.setRotateAngle(this.shapeF2, 0.0F, 0.0F, 0.13665928F);
      this.shapeF1 = new ModelRenderer(this, 0, 9);
      this.shapeF1.setRotationPoint(-1.1F, 0.7F, 0.5F);
      this.shapeF1.addBox(0.0F, 0.0F, 0.0F, 2, 16, 0, 0.0F);
      this.shapeF7 = new ModelRenderer(this, 0, 15);
      this.shapeF7.setRotationPoint(-1.1F, 0.7F, 0.2F);
      this.shapeF7.addBox(-0.4F, -0.4F, 0.0F, 2, 10, 0, 0.0F);
      this.setRotateAngle(this.shapeF7, 0.0F, 0.0F, 0.95609134F);
      this.shape2a = new ModelRenderer(this, 8, 0);
      this.shape2a.setRotationPoint(0.0F, 7.0F, -0.5F);
      this.shape2a.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 1, 0.0F);
      this.setRotateAngle(this.shape2a, 0.0F, 0.0F, -2.276433F);
      this.shape9a = new ModelRenderer(this, 8, 16);
      this.shape9a.mirror = true;
      this.shape9a.setRotationPoint(-1.0F, 6.7F, 0.3F);
      this.shape9a.addBox(0.0F, 0.0F, 0.0F, 1, 6, 0, 0.0F);
      this.setRotateAngle(this.shape9a, 0.0F, 0.0F, (float) Math.PI);
      this.shapeF4 = new ModelRenderer(this, 0, 12);
      this.shapeF4.setRotationPoint(-1.1F, 0.7F, 0.35F);
      this.shapeF4.addBox(0.0F, 0.0F, 0.0F, 2, 13, 0, 0.0F);
      this.setRotateAngle(this.shapeF4, 0.0F, 0.0F, 0.4553564F);
      this.shape2a.addChild(this.shape3a);
      this.shape3a.addChild(this.shapeF6);
      this.shape3a.addChild(this.shapeF3);
      this.shape2a.addChild(this.shape8a);
      this.shape3a.addChild(this.shapeF5);
      this.shape3a.addChild(this.shapeF2);
      this.shape3a.addChild(this.shapeF1);
      this.shape3a.addChild(this.shapeF7);
      this.shape1a.addChild(this.shape2a);
      this.shape1a.addChild(this.shape9a);
      this.shape3a.addChild(this.shapeF4);
   }

   public void renderWings(
      Entity entity, float expanded, float nofly, float upward, float upwardProgress, float gliding, float limbSwing, float limbSwingAmount, float f5
   ) {
      float swingMult = (1.0F - Math.max(upward, gliding * 0.8F)) * 0.3F * nofly;
      float angl1L = MathHelper.cos(-limbSwing * 0.6662F) * -0.3F * limbSwingAmount * swingMult;
      float angl1R = MathHelper.cos(-limbSwing * 0.6662F + (float) Math.PI) * -0.3F * limbSwingAmount * swingMult;
      float upwardDelta = MathHelper.sin(upwardProgress) * upward;
      float upwardTipsBend = -MathHelper.cos(upwardProgress);
      expanded = (upwardTipsBend + 0.6F) / 1.6F * upward + expanded * (1.0F - upward);
      float unexpanded = 1.0F - expanded;
      this.shape1a.rotateAngleX = 0.27314404F + 0.96F * upward + 0.35F * upwardDelta + 0.15F * gliding;
      this.shape1a.rotateAngleY = -0.35F * nofly + 0.58F * upward + 1.2F * gliding;
      this.shape1a.rotateAngleZ = -0.13665928F * unexpanded + expanded * -1.3203416F;
      this.shape2a.rotateAngleX = -0.12F * upwardTipsBend * upward + 0.26F * gliding;
      this.shape2a.rotateAngleY = 0.68F * gliding;
      this.shape2a.rotateAngleZ = -2.276433F * unexpanded + expanded * -0.8651597F;
      this.shape3a.rotateAngleZ = 2.1399481F * unexpanded + expanded * 0.91053826F;
      this.shape3a.rotateAngleX = expanded * -0.18203785F + 0.35F * upwardTipsBend * upward - 0.31F * gliding;
      this.shapeF1.rotateAngleZ = expanded * 0.045553092F;
      this.shapeF2.rotateAngleZ = 0.13665928F * unexpanded + expanded * 0.31869712F;
      this.shapeF3.rotateAngleZ = 0.29670596F * unexpanded + expanded * 0.63739425F;
      this.shapeF4.rotateAngleZ = 0.4553564F * unexpanded + expanded * 1.0016445F;
      this.shapeF5.rotateAngleZ = 0.63739425F * unexpanded + expanded * 1.3658947F;
      this.shapeF6.rotateAngleZ = 0.8196066F * unexpanded + expanded * 1.775698F;
      this.shapeF7.rotateAngleZ = 0.95609134F * unexpanded + expanded * 2.1855013F;
      float featherBend = upwardTipsBend * upward + gliding;
      this.shapeF2.rotateAngleX = 0.09F * featherBend;
      this.shapeF3.rotateAngleX = 0.17F * featherBend;
      this.shapeF4.rotateAngleX = 0.26F * featherBend;
      this.shapeF5.rotateAngleX = this.shapeF3.rotateAngleX;
      this.shapeF6.rotateAngleX = this.shapeF2.rotateAngleX;
      this.shape2a.rotateAngleY += angl1L;
      this.shape1a.rotateAngleY -= angl1L;
      this.shape1a.render(f5);
      this.shape2a.rotateAngleY -= angl1L;
      this.shape1a.rotateAngleY += angl1L;
      this.shape2a.rotateAngleY += angl1R;
      this.shape1a.rotateAngleY -= angl1R;
      GlStateManager.pushMatrix();
      GlStateManager.scale(-1.0F, 1.0F, 1.0F);
      this.shape1a.render(f5);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
