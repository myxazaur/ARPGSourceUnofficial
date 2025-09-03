//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class NailGunModel extends ModelBase {
   public ModelRenderer shapess;
   public ModelRenderer shape;
   public ModelRenderer shape_1;
   public ModelRenderer shape_2;
   public ModelRenderer shape_3;
   public ModelRenderer shape_4;
   public ModelRenderer shape_5;
   public ModelRenderer shape_6;
   public ModelRenderer shape_7;
   public ModelRenderer shape_8;
   public ModelRenderer scope;

   public NailGunModel() {
      this.textureWidth = 36;
      this.textureHeight = 20;
      this.shape_2 = new ModelRenderer(this, 22, 0);
      this.shape_2.setRotationPoint(-0.5F, 0.0F, 12.0F);
      this.shape_2.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
      this.shape_6 = new ModelRenderer(this, 20, 15);
      this.shape_6.setRotationPoint(-1.5F, 0.0F, -0.9F);
      this.shape_6.addBox(0.0F, 0.0F, 0.0F, 3, 1, 4, 0.0F);
      this.shape_8 = new ModelRenderer(this, 22, 8);
      this.shape_8.mirror = true;
      this.shape_8.setRotationPoint(-3.5F, 2.0F, -0.9F);
      this.shape_8.addBox(0.0F, 0.0F, 0.0F, 1, 3, 4, 0.0F);
      this.scope = new ModelRenderer(this, 28, 8);
      this.scope.setRotationPoint(-1.5F, -3.3F, -6.9F);
      this.scope.addBox(0.0F, 0.0F, 0.0F, 3, 3, 1, 0.0F);
      this.shape_3 = new ModelRenderer(this, 18, 0);
      this.shape_3.setRotationPoint(-0.5F, 0.0F, -10.5F);
      this.shape_3.addBox(0.0F, 0.0F, 0.0F, 1, 13, 1, 0.0F);
      this.setRotateAngle(this.shape_3, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.shape = new ModelRenderer(this, 0, 1);
      this.shape.setRotationPoint(0.0F, -1.3F, 2.5F);
      this.shape.addBox(-1.5F, 0.0F, 0.0F, 3, 3, 6, 0.0F);
      this.shape_5 = new ModelRenderer(this, 20, 15);
      this.shape_5.mirror = true;
      this.shape_5.setRotationPoint(1.5F, 7.0F, -0.9F);
      this.shape_5.addBox(0.0F, 0.0F, 0.0F, 3, 1, 4, 0.0F);
      this.setRotateAngle(this.shape_5, 0.0F, 0.0F, (float) Math.PI);
      this.shape_7 = new ModelRenderer(this, 22, 8);
      this.shape_7.setRotationPoint(2.5F, 2.0F, -0.9F);
      this.shape_7.addBox(0.0F, 0.0F, 0.0F, 1, 3, 4, 0.0F);
      this.shapess = new ModelRenderer(this, 0, 0);
      this.shapess.setRotationPoint(0.0F, 2.0F, 4.5F);
      this.shapess.addBox(-0.5F, -0.5F, 0.0F, 1, 6, 1, 0.0F);
      this.setRotateAngle(this.shapess, 0.27314404F, 0.0F, 0.0F);
      this.shape_1 = new ModelRenderer(this, 22, 0);
      this.shape_1.setRotationPoint(-0.5F, 0.0F, 7.0F);
      this.shape_1.addBox(0.0F, 0.0F, 0.0F, 1, 3, 5, 0.0F);
      this.shape_4 = new ModelRenderer(this, 0, 11);
      this.shape_4.setRotationPoint(-2.5F, 1.0F, -0.9F);
      this.shape_4.addBox(0.0F, 0.0F, 0.0F, 5, 5, 4, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shape_2.render(f5);
      this.shape_6.render(f5);
      this.shape_8.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.scope.offsetX, this.scope.offsetY, this.scope.offsetZ);
      GlStateManager.translate(this.scope.rotationPointX * f5, this.scope.rotationPointY * f5, this.scope.rotationPointZ * f5);
      GlStateManager.scale(1.0, 1.0, 0.1);
      GlStateManager.translate(-this.scope.offsetX, -this.scope.offsetY, -this.scope.offsetZ);
      GlStateManager.translate(-this.scope.rotationPointX * f5, -this.scope.rotationPointY * f5, -this.scope.rotationPointZ * f5);
      this.scope.render(f5);
      GlStateManager.popMatrix();
      this.shape_3.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape.offsetX, this.shape.offsetY, this.shape.offsetZ);
      GlStateManager.translate(this.shape.rotationPointX * f5, this.shape.rotationPointY * f5, this.shape.rotationPointZ * f5);
      GlStateManager.scale(0.95, 1.0, 1.0);
      GlStateManager.translate(-this.shape.offsetX, -this.shape.offsetY, -this.shape.offsetZ);
      GlStateManager.translate(-this.shape.rotationPointX * f5, -this.shape.rotationPointY * f5, -this.shape.rotationPointZ * f5);
      this.shape.render(f5);
      GlStateManager.popMatrix();
      this.shape_5.render(f5);
      this.shape_7.render(f5);
      this.shapess.render(f5);
      this.shape_1.render(f5);
      this.shape_4.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
