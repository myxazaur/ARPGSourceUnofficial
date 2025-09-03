//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.GetMOP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class MagicScrollModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shape5;
   public ModelRenderer shape6;

   public MagicScrollModel() {
      this.textureWidth = 24;
      this.textureHeight = 16;
      this.shape6 = new ModelRenderer(this, 0, 13);
      this.shape6.setRotationPoint(0.0F, 3.0F, 0.0F);
      this.shape6.addBox(-4.0F, 0.0F, 0.0F, 8, 3, 0, 0.0F);
      this.setRotateAngle(this.shape6, 0.4553564F, 0.0F, 0.0F);
      this.shape4 = new ModelRenderer(this, 0, 7);
      this.shape4.setRotationPoint(0.0F, 3.0F, 0.0F);
      this.shape4.addBox(-4.0F, 0.0F, 0.0F, 8, 3, 0, 0.0F);
      this.setRotateAngle(this.shape4, -0.5462881F, 0.0F, 0.0F);
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(0.0F, 1.0F, 4.5F);
      this.shape1.addBox(-5.0F, -0.5F, -1.0F, 10, 2, 2, 0.0F);
      this.setRotateAngle(this.shape1, 0.31869712F, 0.0F, 0.0F);
      this.shape5 = new ModelRenderer(this, 0, 10);
      this.shape5.setRotationPoint(0.0F, 3.0F, 0.0F);
      this.shape5.addBox(-4.0F, 0.0F, 0.0F, 8, 3, 0, 0.0F);
      this.setRotateAngle(this.shape5, -0.3642502F, 0.0F, 0.0F);
      this.shape2 = new ModelRenderer(this, 0, 0);
      this.shape2.setRotationPoint(0.0F, 13.0F, 4.5F);
      this.shape2.addBox(-5.0F, 0.0F, -1.0F, 10, 2, 2, 0.0F);
      this.setRotateAngle(this.shape2, -0.31869712F, 0.0F, 0.0F);
      this.shape3 = new ModelRenderer(this, 0, 4);
      this.shape3.setRotationPoint(0.0F, 2.2F, 4.8F);
      this.shape3.addBox(-4.0F, 0.0F, 0.0F, 8, 3, 0, 0.0F);
      this.setRotateAngle(this.shape3, 0.4553564F, 0.0F, 0.0F);
      this.shape5.addChild(this.shape6);
      this.shape3.addChild(this.shape4);
      this.shape4.addChild(this.shape5);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, f * 4.0F, 0.0F);
      float ft1 = GetMOP.getfromto(f, 0.0F, 0.9F);
      this.shape2.offsetY = -10.0F * ft1;
      this.shape2.offsetZ = 0.5F * ft1;
      this.shape1.render(f5);
      this.shape2.render(f5);
      if (f < 1.0F) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape3.offsetX, this.shape3.offsetY, this.shape3.offsetZ);
         GlStateManager.translate(this.shape3.rotationPointX * f5, this.shape3.rotationPointY * f5, this.shape3.rotationPointZ * f5);
         GlStateManager.scale(1.0, 1.0F - f, 1.0);
         GlStateManager.translate(-this.shape3.offsetX, -this.shape3.offsetY, -this.shape3.offsetZ);
         GlStateManager.translate(-this.shape3.rotationPointX * f5, -this.shape3.rotationPointY * f5, -this.shape3.rotationPointZ * f5);
         this.shape3.render(f5);
         GlStateManager.popMatrix();
      }

      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
