//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class MagicGeneratorModel extends ModelBase {
   public ModelRenderer log;
   public ModelRenderer bottle;
   public ModelRenderer coil;
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer coilInside;
   float[] color = new float[]{0.47F, 0.65F, 1.0F};
   float[] color2 = new float[]{1.0F, 1.0F, 1.0F};

   public MagicGeneratorModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.shape1 = new ModelRenderer(this, 0, 24);
      this.shape1.setRotationPoint(0.0F, 12.0F, 0.0F);
      this.shape1.addBox(-4.0F, 0.0F, -8.0F, 8, 4, 3, 0.0F);
      this.coil = new ModelRenderer(this, 16, 24);
      this.coil.setRotationPoint(0.0F, 10.0F, 0.0F);
      this.coil.addBox(-5.0F, 0.0F, -5.0F, 10, 4, 10, 0.0F);
      this.shape2 = new ModelRenderer(this, 0, 24);
      this.shape2.setRotationPoint(0.0F, 12.0F, 0.0F);
      this.shape2.addBox(-4.0F, 0.0F, -8.0F, 8, 4, 3, 0.0F);
      this.setRotateAngle(this.shape2, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.shape3 = new ModelRenderer(this, 0, 24);
      this.shape3.setRotationPoint(0.0F, 12.0F, 0.0F);
      this.shape3.addBox(-4.0F, 0.0F, -8.0F, 8, 4, 3, 0.0F);
      this.setRotateAngle(this.shape3, 0.0F, (float) Math.PI, 0.0F);
      this.bottle = new ModelRenderer(this, 0, 0);
      this.bottle.setRotationPoint(0.0F, 8.0F, 0.0F);
      this.bottle.addBox(-2.0F, 0.0F, -2.0F, 4, 8, 4, 0.0F);
      this.shape4 = new ModelRenderer(this, 0, 24);
      this.shape4.setRotationPoint(0.0F, 12.0F, 0.0F);
      this.shape4.addBox(-4.0F, 0.0F, -8.0F, 8, 4, 3, 0.0F);
      this.setRotateAngle(this.shape4, 0.0F, (float) (-Math.PI / 2), 0.0F);
      this.log = new ModelRenderer(this, 0, 0);
      this.log.setRotationPoint(-8.0F, 16.0F, -8.0F);
      this.log.addBox(0.0F, 0.0F, 0.0F, 16, 8, 16, 0.0F);
      this.coilInside = new ModelRenderer(this, 48, 0);
      this.coilInside.setRotationPoint(0.0F, 10.0F, 0.0F);
      this.coilInside.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (f == 1.0F) {
         this.shape3.render(f5);
         this.shape4.render(f5);
         this.shape2.render(f5);
         this.log.render(f5);
         this.coil.render(f5);
         this.shape1.render(f5);
      } else if (f == 0.0F) {
         this.bottle.render(f5);
      } else {
         this.shape3.render(f5);
         this.shape4.render(f5);
         this.shape2.render(f5);
         this.log.render(f5);
         this.coil.render(f5);
         this.shape1.render(f5);
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.coilInside.offsetX, this.coilInside.offsetY, this.coilInside.offsetZ);
         GlStateManager.translate(this.coilInside.rotationPointX * f5, this.coilInside.rotationPointY * f5, this.coilInside.rotationPointZ * f5);
         GlStateManager.scale(-1.01, 1.0, -1.01);
         GlStateManager.translate(-this.coilInside.offsetX, -this.coilInside.offsetY, -this.coilInside.offsetZ);
         GlStateManager.translate(-this.coilInside.rotationPointX * f5, -this.coilInside.rotationPointY * f5, -this.coilInside.rotationPointZ * f5);
         this.coilInside.render(f5);
         GlStateManager.popMatrix();
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
