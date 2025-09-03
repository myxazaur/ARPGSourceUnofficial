//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ShadowWingsModel extends ModelBase {
   public ModelRenderer shape1a;
   public ModelRenderer shape2a;
   public ModelRenderer shape9a;
   public ModelRenderer shapeXa1;
   public ModelRenderer shapeXa2;
   public ModelRenderer shapeXa3;
   public ModelRenderer shapeXa4;
   public ModelRenderer shape4a;
   public ModelRenderer shapeWa1;
   public ModelRenderer shapeFa1;
   public ModelRenderer shapeWa2;
   public ModelRenderer shapeFa2;
   public ModelRenderer shapeWa3;
   public ModelRenderer shapeFa3;
   public ModelRenderer shapeWa4;
   public ModelRenderer shapeFa4;

   public ShadowWingsModel() {
      this.textureWidth = 16;
      this.textureHeight = 32;
      this.shapeWa2 = new ModelRenderer(this, 10, 22);
      this.shapeWa2.setRotationPoint(0.5F, 8.8F, 0.5F);
      this.shapeWa2.addBox(-0.5F, 0.0F, -0.52F, 1, 9, 1, 0.0F);
      this.setRotateAngle(this.shapeWa2, -0.05235988F, 0.0F, 0.27314404F);
      this.shapeXa2 = new ModelRenderer(this, 10, 16);
      this.shapeXa2.setRotationPoint(0.0F, 8.0F, -0.08F);
      this.shapeXa2.addBox(0.0F, 0.0F, 0.0F, 1, 9, 1, 0.0F);
      this.setRotateAngle(this.shapeXa2, 0.0F, 0.0F, 2.4586453F);
      this.shape4a = new ModelRenderer(this, 0, 10);
      this.shape4a.mirror = true;
      this.shape4a.setRotationPoint(0.0F, 9.0F, 0.5F);
      this.shape4a.addBox(0.0F, 0.0F, 0.0F, 3, 2, 0, 0.0F);
      this.shapeWa4 = new ModelRenderer(this, 10, 22);
      this.shapeWa4.setRotationPoint(0.5F, 8.8F, 0.5F);
      this.shapeWa4.addBox(-0.5F, 0.0F, -0.52F, 1, 9, 1, 0.0F);
      this.setRotateAngle(this.shapeWa4, -0.05235988F, 0.0F, 0.27314404F);
      this.shapeWa3 = new ModelRenderer(this, 10, 22);
      this.shapeWa3.setRotationPoint(0.5F, 8.8F, 0.5F);
      this.shapeWa3.addBox(-0.5F, 0.0F, -0.52F, 1, 9, 1, 0.0F);
      this.setRotateAngle(this.shapeWa3, -0.05235988F, 0.0F, 0.27314404F);
      this.shapeFa4 = new ModelRenderer(this, 0, 16);
      this.shapeFa4.setRotationPoint(0.0F, 0.5F, 0.5F);
      this.shapeFa4.addBox(-4.1F, 0.0F, 0.0F, 5, 16, 0, 0.0F);
      this.setRotateAngle(this.shapeFa4, 0.0F, 0.0F, 0.091106184F);
      this.shapeXa3 = new ModelRenderer(this, 10, 16);
      this.shapeXa3.setRotationPoint(0.0F, 8.0F, -0.06F);
      this.shapeXa3.addBox(0.0F, 0.0F, 0.0F, 1, 9, 1, 0.0F);
      this.setRotateAngle(this.shapeXa3, 0.0F, 0.0F, 2.5953045F);
      this.shape1a = new ModelRenderer(this, 0, 0);
      this.shape1a.setRotationPoint(1.5F, 1.7F, 2.0F);
      this.shape1a.addBox(-1.0F, 0.0F, -1.0F, 2, 7, 2, 0.0F);
      this.setRotateAngle(this.shape1a, 0.27314404F, 0.0F, -0.13665928F);
      this.shapeFa2 = new ModelRenderer(this, 0, 16);
      this.shapeFa2.setRotationPoint(0.0F, 0.5F, 0.5F);
      this.shapeFa2.addBox(-4.1F, 0.0F, 0.0F, 5, 16, 0, 0.0F);
      this.setRotateAngle(this.shapeFa2, 0.0F, 0.0F, 0.091106184F);
      this.shape2a = new ModelRenderer(this, 8, 0);
      this.shape2a.setRotationPoint(0.0F, 7.0F, -0.5F);
      this.shape2a.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 1, 0.0F);
      this.setRotateAngle(this.shape2a, 0.0F, 0.0F, -2.276433F);
      this.shapeWa1 = new ModelRenderer(this, 10, 22);
      this.shapeWa1.setRotationPoint(0.5F, 8.8F, 0.5F);
      this.shapeWa1.addBox(-0.5F, 0.0F, -0.52F, 1, 9, 1, 0.0F);
      this.setRotateAngle(this.shapeWa1, -0.05235988F, 0.0F, 0.27314404F);
      this.shapeXa1 = new ModelRenderer(this, 10, 16);
      this.shapeXa1.setRotationPoint(0.0F, 8.0F, -0.1F);
      this.shapeXa1.addBox(0.0F, 0.0F, 0.0F, 1, 9, 1, 0.0F);
      this.setRotateAngle(this.shapeXa1, 0.0F, 0.0F, 2.321986F);
      this.shapeFa3 = new ModelRenderer(this, 0, 16);
      this.shapeFa3.setRotationPoint(0.0F, 0.5F, 0.5F);
      this.shapeFa3.addBox(-4.1F, 0.0F, 0.0F, 5, 16, 0, 0.0F);
      this.setRotateAngle(this.shapeFa3, 0.0F, 0.0F, 0.091106184F);
      this.shape9a = new ModelRenderer(this, 8, 10);
      this.shape9a.mirror = true;
      this.shape9a.setRotationPoint(-1.0F, 6.7F, 0.3F);
      this.shape9a.addBox(-2.0F, -2.0F, 0.0F, 4, 6, 0, 0.0F);
      this.setRotateAngle(this.shape9a, 0.0F, 0.0F, (float) Math.PI);
      this.shapeXa4 = new ModelRenderer(this, 10, 16);
      this.shapeXa4.setRotationPoint(0.0F, 8.0F, -0.04F);
      this.shapeXa4.addBox(0.0F, 0.0F, 0.0F, 1, 9, 1, 0.0F);
      this.setRotateAngle(this.shapeXa4, 0.0F, 0.0F, 2.7317894F);
      this.shapeFa1 = new ModelRenderer(this, 0, 16);
      this.shapeFa1.setRotationPoint(0.0F, 0.5F, 0.5F);
      this.shapeFa1.addBox(-4.1F, 0.0F, 0.0F, 5, 16, 0, 0.0F);
      this.setRotateAngle(this.shapeFa1, 0.0F, 0.0F, 0.091106184F);
      this.shapeXa2.addChild(this.shapeWa2);
      this.shape2a.addChild(this.shapeXa2);
      this.shape2a.addChild(this.shape4a);
      this.shapeXa4.addChild(this.shapeWa4);
      this.shapeXa3.addChild(this.shapeWa3);
      this.shapeXa4.addChild(this.shapeFa4);
      this.shape2a.addChild(this.shapeXa3);
      this.shapeXa2.addChild(this.shapeFa2);
      this.shape1a.addChild(this.shape2a);
      this.shapeXa1.addChild(this.shapeWa1);
      this.shape2a.addChild(this.shapeXa1);
      this.shapeXa3.addChild(this.shapeFa3);
      this.shape1a.addChild(this.shape9a);
      this.shape2a.addChild(this.shapeXa4);
      this.shapeXa1.addChild(this.shapeFa1);
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
      this.shapeXa1.rotateAngleZ = 2.321986F * unexpanded + expanded * 1.183857F;
      this.shapeXa2.rotateAngleZ = 2.4586453F * unexpanded + expanded * 1.639039F;
      this.shapeXa3.rotateAngleZ = 2.5953045F * unexpanded + expanded * 2.094395F;
      this.shapeXa4.rotateAngleZ = 2.7317894F * unexpanded + expanded * 2.602111F;
      this.shapeXa1.rotateAngleX = expanded * -0.18203785F + 0.35F * upwardTipsBend * upward - 0.31F * gliding;
      this.shapeXa2.rotateAngleX = this.shapeXa1.rotateAngleX * 0.8F;
      this.shapeXa3.rotateAngleX = this.shapeXa1.rotateAngleX * 0.6F;
      this.shapeXa4.rotateAngleX = this.shapeXa1.rotateAngleX * 0.4F;
      float featherBend = upwardTipsBend * upward + gliding;
      this.shapeXa2.rotateAngleX += 0.17F * featherBend;
      this.shapeXa3.rotateAngleX += 0.26F * featherBend;
      this.shapeXa4.rotateAngleX += 0.22F * featherBend;
      this.shapeFa1.rotateAngleY = 0.48F * featherBend;
      this.shapeFa2.rotateAngleY = 0.35F * featherBend;
      this.shapeFa4.rotateAngleY = -0.34F * featherBend;
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
