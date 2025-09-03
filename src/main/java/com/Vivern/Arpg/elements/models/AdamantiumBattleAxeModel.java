//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class AdamantiumBattleAxeModel extends ModelBase {
   public ModelRenderer staf;
   public ModelRenderer base;
   public ModelRenderer base_1;
   public ModelRenderer base_2;
   public ModelRenderer base_3;
   public ModelRenderer base_4;
   public ModelRenderer base_5;
   public ModelRenderer staf_1;
   public ModelRenderer staf_2;

   public AdamantiumBattleAxeModel() {
      this.textureWidth = 32;
      this.textureHeight = 16;
      this.base_1 = new ModelRenderer(this, 14, 0);
      this.base_1.setRotationPoint(0.0F, -3.0F, 5.0F);
      this.base_1.addBox(-0.5F, -5.0F, -6.0F, 1, 10, 2, 0.0F);
      this.staf_2 = new ModelRenderer(this, 24, 0);
      this.staf_2.setRotationPoint(0.0F, 6.0F, 5.0F);
      this.staf_2.addBox(-1.0F, 14.0F, -1.0F, 2, 4, 2, 0.0F);
      this.base_3 = new ModelRenderer(this, 24, 9);
      this.base_3.setRotationPoint(0.0F, -3.0F, 5.0F);
      this.base_3.addBox(-0.5F, -2.0F, -4.5F, 1, 4, 3, 0.0F);
      this.base_4 = new ModelRenderer(this, 9, 9);
      this.base_4.setRotationPoint(0.0F, -3.0F, 5.0F);
      this.base_4.addBox(-0.5F, -5.0F, -4.0F, 1, 2, 3, 0.0F);
      this.base_2 = new ModelRenderer(this, 0, 8);
      this.base_2.setRotationPoint(0.0F, -3.0F, 5.0F);
      this.base_2.addBox(-0.5F, 3.0F, -5.0F, 1, 2, 6, 0.0F);
      this.base_5 = new ModelRenderer(this, 0, 10);
      this.base_5.setRotationPoint(0.0F, -3.0F, 5.0F);
      this.base_5.addBox(-0.5F, -1.0F, 2.0F, 1, 2, 2, 0.0F);
      this.base = new ModelRenderer(this, 0, 0);
      this.base.setRotationPoint(0.0F, 6.0F, 5.0F);
      this.base.addBox(-1.0F, -11.0F, -2.0F, 2, 4, 4, 0.0F);
      this.staf = new ModelRenderer(this, 24, 0);
      this.staf.setRotationPoint(0.0F, 6.0F, 5.0F);
      this.staf.addBox(-1.0F, -7.0F, -1.0F, 2, 7, 2, 0.0F);
      this.staf_1 = new ModelRenderer(this, 20, 0);
      this.staf_1.setRotationPoint(0.0F, 6.0F, 5.0F);
      this.staf_1.addBox(-0.5F, 0.0F, -0.5F, 1, 14, 1, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.base_1.offsetX, this.base_1.offsetY, this.base_1.offsetZ);
      GlStateManager.translate(this.base_1.rotationPointX * f5, this.base_1.rotationPointY * f5, this.base_1.rotationPointZ * f5);
      GlStateManager.scale(0.5, 1.0, 1.0);
      GlStateManager.translate(-this.base_1.offsetX, -this.base_1.offsetY, -this.base_1.offsetZ);
      GlStateManager.translate(-this.base_1.rotationPointX * f5, -this.base_1.rotationPointY * f5, -this.base_1.rotationPointZ * f5);
      this.base_1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf_2.offsetX, this.staf_2.offsetY, this.staf_2.offsetZ);
      GlStateManager.translate(this.staf_2.rotationPointX * f5, this.staf_2.rotationPointY * f5, this.staf_2.rotationPointZ * f5);
      GlStateManager.scale(0.8, 1.0, 0.8);
      GlStateManager.translate(-this.staf_2.offsetX, -this.staf_2.offsetY, -this.staf_2.offsetZ);
      GlStateManager.translate(-this.staf_2.rotationPointX * f5, -this.staf_2.rotationPointY * f5, -this.staf_2.rotationPointZ * f5);
      this.staf_2.render(f5);
      GlStateManager.popMatrix();
      this.base_3.render(f5);
      this.base_4.render(f5);
      this.base_2.render(f5);
      this.base_5.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.base.offsetX, this.base.offsetY, this.base.offsetZ);
      GlStateManager.translate(this.base.rotationPointX * f5, this.base.rotationPointY * f5, this.base.rotationPointZ * f5);
      GlStateManager.scale(1.1, 1.0, 1.0);
      GlStateManager.translate(-this.base.offsetX, -this.base.offsetY, -this.base.offsetZ);
      GlStateManager.translate(-this.base.rotationPointX * f5, -this.base.rotationPointY * f5, -this.base.rotationPointZ * f5);
      this.base.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf.offsetX, this.staf.offsetY, this.staf.offsetZ);
      GlStateManager.translate(this.staf.rotationPointX * f5, this.staf.rotationPointY * f5, this.staf.rotationPointZ * f5);
      GlStateManager.scale(0.8, 1.0, 0.8);
      GlStateManager.translate(-this.staf.offsetX, -this.staf.offsetY, -this.staf.offsetZ);
      GlStateManager.translate(-this.staf.rotationPointX * f5, -this.staf.rotationPointY * f5, -this.staf.rotationPointZ * f5);
      this.staf.render(f5);
      GlStateManager.popMatrix();
      this.staf_1.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
