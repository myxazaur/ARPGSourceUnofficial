//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class GasmaskModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape8;
   public ModelRenderer shape9;
   public ModelRenderer shape4;
   public ModelRenderer shape6;
   public ModelRenderer shape5;
   public ModelRenderer shape7;

   public GasmaskModel() {
      this.textureWidth = 32;
      this.textureHeight = 16;
      this.shape1 = new ModelRenderer(this, 24, 10);
      this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1.addBox(-3.0F, -5.0F, -7.0F, 2, 2, 2, 0.0F);
      this.shape5 = new ModelRenderer(this, 6, 3);
      this.shape5.setRotationPoint(0.5F, 2.3F, -2.5F);
      this.shape5.addBox(0.0F, 0.0F, 0.0F, 3, 2, 3, 0.0F);
      this.setRotateAngle(this.shape5, 0.27314404F, 0.0F, 0.18203785F);
      this.shape2 = new ModelRenderer(this, 24, 10);
      this.shape2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape2.addBox(1.0F, -5.0F, -7.0F, 2, 2, 2, 0.0F);
      this.shape8 = new ModelRenderer(this, 8, 0);
      this.shape8.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape8.addBox(-1.5F, 0.0F, -5.0F, 3, 1, 2, 0.0F);
      this.shape7 = new ModelRenderer(this, 6, 3);
      this.shape7.setRotationPoint(-0.5F, 2.3F, -2.5F);
      this.shape7.addBox(-3.0F, 0.0F, 0.0F, 3, 2, 3, 0.0F);
      this.setRotateAngle(this.shape7, 0.27314404F, 0.0F, -0.18203785F);
      this.shape3 = new ModelRenderer(this, 18, 0);
      this.shape3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape3.addBox(-3.0F, -7.0F, -5.0F, 6, 7, 1, 0.0F);
      this.shape9 = new ModelRenderer(this, 0, 8);
      this.shape9.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape9.addBox(-4.5F, -5.0F, -4.5F, 9, 2, 6, 0.0F);
      this.shape4 = new ModelRenderer(this, 0, 0);
      this.shape4.setRotationPoint(0.0F, 0.0F, -5.0F);
      this.shape4.addBox(1.0F, -0.4F, -2.0F, 2, 3, 2, 0.0F);
      this.setRotateAngle(this.shape4, -1.0927507F, -0.59184116F, -0.27314404F);
      this.shape6 = new ModelRenderer(this, 0, 0);
      this.shape6.setRotationPoint(0.0F, 0.0F, -5.0F);
      this.shape6.addBox(-3.0F, -0.4F, -2.0F, 2, 3, 2, 0.0F);
      this.setRotateAngle(this.shape6, -1.0927507F, 0.59184116F, 0.27314404F);
      this.shape4.addChild(this.shape5);
      this.shape6.addChild(this.shape7);
      this.shape3.addChild(this.shape4);
      this.shape3.addChild(this.shape6);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shape1.render(f5);
      this.shape2.render(f5);
      this.shape8.render(f5);
      this.shape3.render(f5);
      this.shape9.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
