//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ToxicWingsModel extends ModelBase {
   public ModelRenderer shape1a;
   public ModelRenderer shape1b;
   public ModelRenderer shape2a;
   public ModelRenderer shape3a;
   public ModelRenderer shape8a;
   public ModelRenderer shape5a;
   public ModelRenderer shape6a;
   public ModelRenderer shape4a;
   public ModelRenderer shape7a;
   public ModelRenderer shape2b;
   public ModelRenderer shape3b;
   public ModelRenderer shape5b;
   public ModelRenderer shape4b;

   public ToxicWingsModel() {
      this.textureWidth = 26;
      this.textureHeight = 32;
      this.shape1a = new ModelRenderer(this, 0, 0);
      this.shape1a.setRotationPoint(1.5F, 1.7F, 2.0F);
      this.shape1a.addBox(-1.0F, 0.0F, -1.0F, 2, 7, 2, 0.0F);
      this.setRotateAngle(this.shape1a, 0.27314404F, -0.091106184F, -0.31869712F);
      this.shape4b = new ModelRenderer(this, 16, 0);
      this.shape4b.setRotationPoint(1.0F, 0.7F, 0.65F);
      this.shape4b.addBox(-5.0F, 0.0F, 0.0F, 4, 8, 0, 0.0F);
      this.shape3b = new ModelRenderer(this, 12, 0);
      this.shape3b.setRotationPoint(-0.6F, 7.0F, -0.1F);
      this.shape3b.addBox(0.0F, 0.0F, 0.0F, 1, 9, 1, 0.0F);
      this.setRotateAngle(this.shape3b, 0.0F, 0.0F, 1.6390387F);
      this.shape2a = new ModelRenderer(this, 8, 0);
      this.shape2a.setRotationPoint(0.0F, 7.0F, -0.5F);
      this.shape2a.addBox(-1.0F, 0.0F, 0.0F, 1, 11, 1, 0.0F);
      this.setRotateAngle(this.shape2a, 0.0F, 0.13665928F, -2.321986F);
      this.shape2b = new ModelRenderer(this, 8, 0);
      this.shape2b.setRotationPoint(-0.7F, 6.5F, -0.6F);
      this.shape2b.addBox(-1.0F, 0.0F, 0.0F, 1, 8, 1, 0.0F);
      this.setRotateAngle(this.shape2b, 0.0F, 0.0F, -1.548107F);
      this.shape1b = new ModelRenderer(this, 8, 0);
      this.shape1b.setRotationPoint(1.5F, 5.7F, 2.0F);
      this.shape1b.addBox(-1.0F, 0.0F, -1.0F, 1, 7, 1, 0.0F);
      this.setRotateAngle(this.shape1b, 0.27314404F, -0.4098033F, -0.31869712F);
      this.shape6a = new ModelRenderer(this, 10, 21);
      this.shape6a.setRotationPoint(1.9F, 10.7F, 0.55F);
      this.shape6a.addBox(0.0F, 0.0F, 0.0F, 2, 8, 0, 0.0F);
      this.setRotateAngle(this.shape6a, 0.0F, 0.0F, (float) -Math.PI);
      this.shape5b = new ModelRenderer(this, 16, 9);
      this.shape5b.mirror = true;
      this.shape5b.setRotationPoint(-1.0F, 7.7F, 0.45F);
      this.shape5b.addBox(0.0F, 0.0F, 0.0F, 4, 7, 0, 0.0F);
      this.setRotateAngle(this.shape5b, 0.0F, 0.0F, (float) Math.PI);
      this.shape4a = new ModelRenderer(this, 0, 18);
      this.shape4a.mirror = true;
      this.shape4a.setRotationPoint(0.0F, 0.7F, 0.65F);
      this.shape4a.addBox(-5.0F, 0.0F, 0.0F, 5, 11, 0, 0.0F);
      this.shape7a = new ModelRenderer(this, 10, 13);
      this.shape7a.setRotationPoint(2.9F, 8.7F, 0.55F);
      this.shape7a.addBox(0.0F, 0.0F, 0.0F, 2, 8, 0, 0.0F);
      this.setRotateAngle(this.shape7a, 0.0F, 0.0F, (float) -Math.PI);
      this.shape8a = new ModelRenderer(this, 0, 9);
      this.shape8a.mirror = true;
      this.shape8a.setRotationPoint(-1.1F, 4.7F, 0.05F);
      this.shape8a.addBox(0.0F, 0.0F, 0.0F, 3, 6, 0, 0.0F);
      this.setRotateAngle(this.shape8a, 0.0F, 0.0F, -2.8684487F);
      this.shape3a = new ModelRenderer(this, 12, 0);
      this.shape3a.setRotationPoint(0.0F, 10.0F, -0.1F);
      this.shape3a.addBox(0.0F, 0.0F, 0.0F, 1, 12, 1, 0.0F);
      this.setRotateAngle(this.shape3a, 0.0F, 0.0F, 1.821251F);
      this.shape5a = new ModelRenderer(this, 14, 20);
      this.shape5a.mirror = true;
      this.shape5a.setRotationPoint(-1.0F, 10.7F, 0.45F);
      this.shape5a.addBox(0.0F, 0.0F, 0.0F, 5, 9, 0, 0.0F);
      this.setRotateAngle(this.shape5a, 0.0F, 0.0F, (float) Math.PI);
      this.shape3b.addChild(this.shape4b);
      this.shape2b.addChild(this.shape3b);
      this.shape1a.addChild(this.shape2a);
      this.shape1b.addChild(this.shape2b);
      this.shape2a.addChild(this.shape6a);
      this.shape2b.addChild(this.shape5b);
      this.shape3a.addChild(this.shape4a);
      this.shape3a.addChild(this.shape7a);
      this.shape2a.addChild(this.shape8a);
      this.shape2a.addChild(this.shape3a);
      this.shape2a.addChild(this.shape5a);
   }

   public void renderWings(
      Entity entity, float expanded0, float nofly, float upward, float upwardProgress, float gliding, float limbSwing, float limbSwingAmount, float f5
   ) {
      float swingMult = (1.0F - Math.max(upward, gliding * 0.8F)) * 0.3F * nofly;
      float angl1L = MathHelper.cos(-limbSwing * 0.6662F) * -0.3F * limbSwingAmount * swingMult;
      float angl1R = MathHelper.cos(-limbSwing * 0.6662F + (float) Math.PI) * -0.3F * limbSwingAmount * swingMult;
      float upwardDelta = MathHelper.sin(upwardProgress) * upward;
      float upwardTipsBend = -MathHelper.cos(upwardProgress);
      float expanded = (upwardTipsBend + 0.6F) / 1.6F * upward + expanded0 * (1.0F - upward);
      float unexpanded = 1.0F - expanded;
      this.shape1a.rotateAngleX = 0.27314404F + 0.96F * upward + 0.35F * upwardDelta + 0.15F * gliding;
      this.shape1a.rotateAngleY = -0.091106184F + -0.35F * nofly + 0.58F * upward + 1.2F * gliding;
      this.shape1a.rotateAngleZ = -0.31869712F * unexpanded + expanded * -1.3203416F;
      this.shape2a.rotateAngleX = -0.12F * upwardTipsBend * upward + 0.26F * gliding;
      this.shape2a.rotateAngleY = 0.13665928F + 0.68F * gliding;
      this.shape2a.rotateAngleZ = -2.321986F * unexpanded + expanded * -0.8651597F;
      this.shape3a.rotateAngleZ = 2.021251F * unexpanded + expanded * 0.8105383F;
      this.shape3a.rotateAngleX = expanded * -0.18203785F + 0.35F * upwardTipsBend * upward - 0.1F * gliding;
      float featherBend = upwardTipsBend * upward + gliding;
      this.shape4a.rotateAngleY = 0.45F * featherBend;
      this.shape5a.rotateAngleY = -0.45F * featherBend;
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
      float part2add = (float) -Math.PI;
      float upwardDelta2 = MathHelper.sin(upwardProgress + part2add) * upward;
      float upwardTipsBend2 = -MathHelper.cos(upwardProgress + part2add);
      expanded = (upwardTipsBend2 + 0.6F) / 1.6F * upward + expanded0 * (1.0F - upward);
      unexpanded = 1.0F - expanded;
      this.shape1b.rotateAngleX = 0.27314404F + 0.96F * upward + 0.35F * upwardDelta2 + 0.15F * gliding;
      this.shape1b.rotateAngleY = -0.4098033F + -0.35F * nofly + 0.97F * upward + 1.2F * gliding;
      this.shape1b.rotateAngleZ = -0.31869712F * unexpanded + expanded * -1.3203416F + 0.31F * upward;
      this.shape2b.rotateAngleX = -0.12F * upwardTipsBend2 * upward + 0.26F * gliding;
      this.shape2b.rotateAngleY = 0.68F * gliding;
      this.shape2b.rotateAngleZ = -1.548107F * unexpanded + expanded * -0.8651597F;
      this.shape3b.rotateAngleZ = 1.6390387F * unexpanded + expanded * 0.8105383F;
      this.shape3b.rotateAngleX = expanded * -0.18203785F + 0.35F * upwardTipsBend2 * upward - 0.1F * gliding;
      float featherBend2 = upwardTipsBend2 * upward + gliding;
      this.shape4b.rotateAngleY = 0.45F * featherBend2;
      this.shape5b.rotateAngleY = -0.45F * featherBend2;
      this.shape2b.rotateAngleY += angl1L;
      this.shape1b.rotateAngleY -= angl1L;
      this.shape1b.render(f5);
      this.shape2b.rotateAngleY -= angl1L;
      this.shape1b.rotateAngleY += angl1L;
      this.shape2b.rotateAngleY += angl1R;
      this.shape1b.rotateAngleY -= angl1R;
      GlStateManager.pushMatrix();
      GlStateManager.scale(-1.0F, 1.0F, 1.0F);
      this.shape1b.render(f5);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
