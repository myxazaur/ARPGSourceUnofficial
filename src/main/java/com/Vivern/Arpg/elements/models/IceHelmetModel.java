//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class IceHelmetModel extends ModelBiped {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shape5;
   public ModelRenderer shape6;

   public IceHelmetModel() {
      this.textureWidth = 64;
      this.textureHeight = 32;
      this.shape4 = new ModelRenderer(this, 56, 22);
      this.shape4.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape4.addBox(-3.6F, 1.6F, 1.0F, 2, 7, 2, 0.0F);
      this.setRotateAngle(this.shape4, 1.6390387F, -0.5462881F, 0.0F);
      this.shape1 = new ModelRenderer(this, 56, 20);
      this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1.addBox(2.2F, 1.0F, 3.7F, 2, 10, 2, 0.0F);
      this.setRotateAngle(this.shape1, 1.7301449F, 0.4098033F, 0.0F);
      this.shape5 = new ModelRenderer(this, 56, 20);
      this.shape5.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape5.addBox(2.8F, 2.6F, 6.4F, 1, 7, 1, 0.0F);
      this.setRotateAngle(this.shape5, 1.821251F, 0.18203785F, 0.0F);
      this.shape3 = new ModelRenderer(this, 56, 22);
      this.shape3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape3.addBox(1.6F, 1.6F, 1.0F, 2, 7, 2, 0.0F);
      this.setRotateAngle(this.shape3, 1.6390387F, 0.5462881F, 0.0F);
      this.shape6 = new ModelRenderer(this, 56, 20);
      this.shape6.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape6.addBox(-3.8F, 2.6F, 6.4F, 1, 7, 1, 0.0F);
      this.setRotateAngle(this.shape6, 1.821251F, -0.18203785F, 0.0F);
      this.shape2 = new ModelRenderer(this, 56, 20);
      this.shape2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape2.addBox(-4.2F, 1.0F, 3.7F, 2, 10, 2, 0.0F);
      this.setRotateAngle(this.shape2, 1.7301449F, -0.4098033F, 0.0F);
      this.bipedHead.addChild(this.shape1);
      this.bipedHead.addChild(this.shape2);
      this.bipedHead.addChild(this.shape3);
      this.bipedHead.addChild(this.shape4);
      this.bipedHead.addChild(this.shape5);
      this.bipedHead.addChild(this.shape6);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5 * 1.13F);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
