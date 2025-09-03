//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class BooksModel extends ModelBase {
   public ModelRenderer shapeA1;
   public ModelRenderer shapeA2;
   public ModelRenderer shapeA3;
   public ModelRenderer shapeB3;
   public ModelRenderer shapeB2;
   public ModelRenderer shapeB1;
   public ModelRenderer shapeC1;
   public ModelRenderer shapeC2;
   public ModelRenderer shapeC3;
   public ModelRenderer shapeD1;
   public ModelRenderer shapeD2;
   public ModelRenderer shapeD3;
   public ModelRenderer shapeE3;
   public ModelRenderer shapeE2;
   public ModelRenderer shapeE1;
   public ModelRenderer shapeJ2;
   public ModelRenderer shapeJ3;
   public ModelRenderer shapeJ1;
   public ModelRenderer shapeK2;
   public ModelRenderer shapeK1;
   public ModelRenderer shapeK3;

   public BooksModel() {
      this.textureWidth = 32;
      this.textureHeight = 44;
      this.shapeE3 = new ModelRenderer(this, 0, 0);
      this.shapeE3.setRotationPoint(-3.0F, 22.0F, -4.0F);
      this.shapeE3.addBox(0.0F, -11.0F, 0.0F, 10, 11, 3, 0.0F);
      this.setRotateAngle(this.shapeE3, 0.27314404F, 0.0F, 0.0F);
      this.shapeE1 = new ModelRenderer(this, 0, 0);
      this.shapeE1.setRotationPoint(-4.0F, 11.0F, 3.5F);
      this.shapeE1.addBox(0.0F, 0.0F, 0.0F, 10, 11, 3, 0.0F);
      this.shapeJ2 = new ModelRenderer(this, 0, 0);
      this.shapeJ2.setRotationPoint(-4.0F, 22.0F, -6.0F);
      this.shapeJ2.addBox(0.0F, -12.0F, 0.0F, 10, 12, 3, 0.0F);
      this.shapeJ3 = new ModelRenderer(this, 0, 32);
      this.shapeJ3.setRotationPoint(-5.0F, 16.0F, 6.0F);
      this.shapeJ3.addBox(0.0F, 0.0F, 0.0F, 10, 9, 3, 0.0F);
      this.setRotateAngle(this.shapeJ3, (float) (-Math.PI / 2), 0.0F, 0.0F);
      this.shapeC3 = new ModelRenderer(this, 0, 32);
      this.shapeC3.setRotationPoint(-6.0F, 13.2F, 1.2F);
      this.shapeC3.addBox(0.0F, 0.0F, 0.0F, 10, 9, 3, 0.0F);
      this.setRotateAngle(this.shapeC3, 0.22759093F, 0.0F, 0.0F);
      this.shapeA2 = new ModelRenderer(this, 0, 16);
      this.shapeA2.setRotationPoint(-4.0F, 10.0F, -2.0F);
      this.shapeA2.addBox(0.0F, 0.0F, 0.0F, 10, 12, 4, 0.0F);
      this.shapeK1 = new ModelRenderer(this, 0, 0);
      this.shapeK1.setRotationPoint(-5.0F, 22.0F, -2.8F);
      this.shapeK1.addBox(0.0F, -12.0F, 0.0F, 10, 12, 3, 0.0F);
      this.shapeB1 = new ModelRenderer(this, 0, 16);
      this.shapeB1.setRotationPoint(-3.5F, 12.0F, 2.0F);
      this.shapeB1.addBox(0.0F, 0.0F, 0.0F, 10, 10, 4, 0.0F);
      this.setRotateAngle(this.shapeB1, 0.0F, -0.045553092F, 0.0F);
      this.shapeB2 = new ModelRenderer(this, 0, 0);
      this.shapeB2.setRotationPoint(-5.0F, 10.0F, -2.0F);
      this.shapeB2.addBox(0.0F, 0.0F, 0.0F, 10, 12, 3, 0.0F);
      this.shapeE2 = new ModelRenderer(this, 0, 16);
      this.shapeE2.setRotationPoint(-5.0F, 11.0F, -1.0F);
      this.shapeE2.addBox(0.0F, 0.0F, 0.0F, 10, 11, 4, 0.0F);
      this.shapeC2 = new ModelRenderer(this, 0, 0);
      this.shapeC2.setRotationPoint(-5.0F, 11.1F, -2.0F);
      this.shapeC2.addBox(0.0F, 0.0F, 0.0F, 10, 11, 3, 0.0F);
      this.setRotateAngle(this.shapeC2, 0.091106184F, 0.0F, 0.0F);
      this.shapeA3 = new ModelRenderer(this, 0, 32);
      this.shapeA3.setRotationPoint(-6.0F, 13.0F, 3.0F);
      this.shapeA3.addBox(0.0F, 0.0F, 0.0F, 10, 9, 3, 0.0F);
      this.shapeA1 = new ModelRenderer(this, 0, 0);
      this.shapeA1.setRotationPoint(-5.0F, 22.0F, -6.0F);
      this.shapeA1.addBox(0.0F, -12.0F, 0.0F, 10, 12, 3, 0.0F);
      this.shapeB3 = new ModelRenderer(this, 0, 0);
      this.shapeB3.setRotationPoint(-4.0F, 22.0F, -4.0F);
      this.shapeB3.addBox(0.0F, -11.0F, -3.0F, 10, 11, 3, 0.0F);
      this.setRotateAngle(this.shapeB3, -0.18203785F, 0.0F, 0.0F);
      this.shapeK3 = new ModelRenderer(this, 0, 16);
      this.shapeK3.setRotationPoint(-3.0F, 22.0F, 4.1F);
      this.shapeK3.addBox(0.0F, -11.0F, -4.0F, 10, 11, 4, 0.0F);
      this.setRotateAngle(this.shapeK3, -0.27314404F, 0.0F, 0.0F);
      this.shapeC1 = new ModelRenderer(this, 0, 16);
      this.shapeC1.setRotationPoint(-4.0F, 22.0F, -6.0F);
      this.shapeC1.addBox(0.0F, -12.0F, 0.0F, 10, 12, 4, 0.0F);
      this.shapeD2 = new ModelRenderer(this, 0, 0);
      this.shapeD2.setRotationPoint(-5.0F, 10.0F, -1.5F);
      this.shapeD2.addBox(0.0F, 0.0F, 0.0F, 10, 12, 3, 0.0F);
      this.shapeJ1 = new ModelRenderer(this, 0, 32);
      this.shapeJ1.setRotationPoint(-4.0F, 19.0F, 7.0F);
      this.shapeJ1.addBox(0.0F, 0.0F, 0.0F, 10, 9, 3, 0.0F);
      this.setRotateAngle(this.shapeJ1, (float) (-Math.PI / 2), 0.0F, 0.0F);
      this.shapeD3 = new ModelRenderer(this, 0, 0);
      this.shapeD3.setRotationPoint(-4.0F, 10.0F, 2.5F);
      this.shapeD3.addBox(0.0F, 0.0F, 0.0F, 10, 12, 3, 0.0F);
      this.shapeD1 = new ModelRenderer(this, 0, 0);
      this.shapeD1.setRotationPoint(-4.0F, 23.0F, -5.5F);
      this.shapeD1.addBox(0.0F, -12.0F, 0.0F, 10, 11, 3, 0.0F);
      this.shapeK2 = new ModelRenderer(this, 0, 32);
      this.shapeK2.setRotationPoint(-3.0F, 14.0F, -6.2F);
      this.shapeK2.addBox(0.0F, 0.0F, 0.0F, 10, 8, 3, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      switch ((int)f) {
         case 1:
            if (f1 == 1.0F) {
               this.shapeA1.render(f5);
            }

            if (f1 == 2.0F) {
               this.shapeA2.render(f5);
            }

            if (f1 == 3.0F) {
               this.shapeA3.render(f5);
            }
            break;
         case 2:
            if (f1 == 1.0F) {
               this.shapeB1.render(f5);
            }

            if (f1 == 2.0F) {
               this.shapeB2.render(f5);
            }

            if (f1 == 3.0F) {
               this.shapeB3.render(f5);
            }
            break;
         case 3:
            if (f1 == 1.0F) {
               this.shapeC1.render(f5);
            }

            if (f1 == 2.0F) {
               this.shapeC2.render(f5);
            }

            if (f1 == 3.0F) {
               this.shapeC3.render(f5);
            }
            break;
         case 4:
            if (f1 == 1.0F) {
               this.shapeD1.render(f5);
            }

            if (f1 == 2.0F) {
               this.shapeD2.render(f5);
            }

            if (f1 == 3.0F) {
               this.shapeD3.render(f5);
            }
            break;
         case 5:
            if (f1 == 1.0F) {
               this.shapeE1.render(f5);
            }

            if (f1 == 2.0F) {
               this.shapeE2.render(f5);
            }

            if (f1 == 3.0F) {
               this.shapeE3.render(f5);
            }
            break;
         case 6:
            if (f1 == 1.0F) {
               this.shapeJ1.render(f5);
            }

            if (f1 == 2.0F) {
               this.shapeJ2.render(f5);
            }

            if (f1 == 3.0F) {
               this.shapeJ3.render(f5);
            }
            break;
         case 7:
            if (f1 == 1.0F) {
               this.shapeK1.render(f5);
            }

            if (f1 == 2.0F) {
               this.shapeK2.render(f5);
            }

            if (f1 == 3.0F) {
               this.shapeK3.render(f5);
            }
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
