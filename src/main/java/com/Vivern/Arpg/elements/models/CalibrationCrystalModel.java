//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.renders.ModelRendererGlow;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class CalibrationCrystalModel extends ModelBase {
   public ModelRenderer big;
   public ModelRenderer medium;
   public ModelRenderer small;
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shape5;
   public ModelRenderer shape6;
   public ModelRenderer shape7;

   public CalibrationCrystalModel() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      this.shape2 = new ModelRenderer(this, 7, 0);
      this.shape2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape2.addBox(-1.0F, -2.0F, 2.0F, 2, 4, 1, 0.0F);
      this.medium = new ModelRenderer(this, 0, 18);
      this.medium.setRotationPoint(0.0F, 20.0F, 0.0F);
      this.medium.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4, 0.0F);
      this.small = new ModelRenderer(this, 17, 18);
      this.small.setRotationPoint(0.0F, 20.0F, 0.0F);
      this.small.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
      this.shape1 = new ModelRenderer(this, 7, 0);
      this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1.addBox(-1.0F, -2.0F, -3.0F, 2, 4, 1, 0.0F);
      this.shape6 = new ModelRendererGlow(this, 18, 9, 100, true);
      this.shape6.setRotationPoint(0.0F, -3.0F, 0.0F);
      this.shape6.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
      this.shape5 = new ModelRendererGlow(this, 0, 9, 100, true);
      this.shape5.setRotationPoint(0.0F, -5.0F, 0.0F);
      this.shape5.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4, 0.0F);
      this.shape3 = new ModelRenderer(this, 0, 0);
      this.shape3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape3.addBox(-1.0F, -2.0F, -3.0F, 2, 4, 1, 0.0F);
      this.setRotateAngle(this.shape3, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.big = new ModelRenderer(this, 8, 1);
      this.big.setRotationPoint(0.0F, 20.0F, 0.0F);
      this.big.addBox(-3.0F, 2.0F, -3.0F, 6, 2, 6, 0.0F);
      this.shape7 = new ModelRendererGlow(this, 12, 9, 100, true);
      this.shape7.setRotationPoint(0.0F, -1.0F, 0.0F);
      this.shape7.addBox(-1.0F, 0.0F, -1.0F, 2, 1, 2, 0.0F);
      this.shape4 = new ModelRenderer(this, 0, 0);
      this.shape4.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape4.addBox(-1.0F, -2.0F, -3.0F, 2, 4, 1, 0.0F);
      this.setRotateAngle(this.shape4, 0.0F, (float) (-Math.PI / 2), 0.0F);
      this.big.addChild(this.shape2);
      this.big.addChild(this.shape1);
      this.medium.addChild(this.shape6);
      this.big.addChild(this.shape5);
      this.big.addChild(this.shape3);
      this.small.addChild(this.shape7);
      this.big.addChild(this.shape4);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (f == 1.0F) {
         this.small.render(f5);
      } else if (f == 2.0F) {
         this.medium.render(f5);
      } else if (f == 3.0F) {
         this.big.render(f5);
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
