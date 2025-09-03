//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ToxicNuclearCannonModel extends ModelBase {
   public ModelRenderer shapessh;
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shapegrid1;
   public ModelRenderer shape3;
   public ModelRenderer shapegrid2;
   public ModelRenderer shape4;

   public ToxicNuclearCannonModel() {
      this.textureWidth = 60;
      this.textureHeight = 10;
      this.shapegrid2 = new ModelRenderer(this, 39, 0);
      this.shapegrid2.setRotationPoint(0.0F, -0.8F, 7.0F);
      this.shapegrid2.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 4, 0.0F);
      this.shape4 = new ModelRenderer(this, 45, 0);
      this.shape4.setRotationPoint(0.5F, -0.2F, -3.0F);
      this.shape4.addBox(-1.0F, -1.0F, -0.5F, 1, 1, 6, 0.0F);
      this.shape3 = new ModelRenderer(this, 29, 0);
      this.shape3.setRotationPoint(0.5F, 0.2F, 8.0F);
      this.shape3.addBox(-1.5F, -1.5F, -1.5F, 2, 2, 6, 0.0F);
      this.shape2 = new ModelRenderer(this, 11, 0);
      this.shape2.setRotationPoint(0.5F, 0.9F, -3.0F);
      this.shape2.addBox(-1.5F, -1.5F, -1.5F, 2, 2, 7, 0.0F);
      this.shape1 = new ModelRenderer(this, 4, 0);
      this.shape1.setRotationPoint(0.0F, 0.0F, 4.0F);
      this.shape1.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 4, 0.0F);
      this.shapessh = new ModelRenderer(this, 0, 0);
      this.shapessh.setRotationPoint(0.0F, 2.0F, 4.5F);
      this.shapessh.addBox(-0.5F, -0.5F, 0.0F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shapessh, 0.18203785F, 0.0F, 0.0F);
      this.shapegrid1 = new ModelRenderer(this, 22, 0);
      this.shapegrid1.setRotationPoint(0.0F, 0.4F, -1.7F);
      this.shapegrid1.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 4, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapegrid2.offsetX, this.shapegrid2.offsetY, this.shapegrid2.offsetZ);
      GlStateManager.translate(this.shapegrid2.rotationPointX * f5, this.shapegrid2.rotationPointY * f5, this.shapegrid2.rotationPointZ * f5);
      GlStateManager.scale(1.2, 1.2, 1.2);
      GlStateManager.translate(-this.shapegrid2.offsetX, -this.shapegrid2.offsetY, -this.shapegrid2.offsetZ);
      GlStateManager.translate(-this.shapegrid2.rotationPointX * f5, -this.shapegrid2.rotationPointY * f5, -this.shapegrid2.rotationPointZ * f5);
      this.shapegrid2.render(f5);
      GlStateManager.popMatrix();
      this.shape4.render(f5);
      this.shape3.render(f5);
      this.shape2.render(f5);
      this.shape1.render(f5);
      this.shapessh.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapegrid1.offsetX, this.shapegrid1.offsetY, this.shapegrid1.offsetZ);
      GlStateManager.translate(this.shapegrid1.rotationPointX * f5, this.shapegrid1.rotationPointY * f5, this.shapegrid1.rotationPointZ * f5);
      GlStateManager.scale(1.2, 1.2, 1.2);
      GlStateManager.translate(-this.shapegrid1.offsetX, -this.shapegrid1.offsetY, -this.shapegrid1.offsetZ);
      GlStateManager.translate(-this.shapegrid1.rotationPointX * f5, -this.shapegrid1.rotationPointY * f5, -this.shapegrid1.rotationPointZ * f5);
      this.shapegrid1.render(f5);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
