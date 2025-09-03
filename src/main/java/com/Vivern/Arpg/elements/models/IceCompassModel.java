//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class IceCompassModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer arrow;
   public ModelRenderer shaft;
   public ModelRenderer gear1;
   public ModelRenderer gear2;
   public ModelRenderer gear3;
   public ModelRenderer gear4;
   public ModelRenderer gear5;
   public ModelRenderer gear6;

   public IceCompassModel() {
      this.textureWidth = 32;
      this.textureHeight = 16;
      this.gear5 = new ModelRenderer(this, 6, 0);
      this.gear5.setRotationPoint(-2.25F, 0.5F, -2.25F);
      this.gear5.addBox(-1.0F, 0.0F, -1.0F, 2, 0, 2, 0.0F);
      this.shape1 = new ModelRenderer(this, 3, 6);
      this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1.addBox(-3.5F, 0.0F, -3.5F, 7, 3, 7, 0.0F);
      this.arrow = new ModelRenderer(this, -4, 9);
      this.arrow.setRotationPoint(0.0F, -0.1F, 0.0F);
      this.arrow.addBox(-0.5F, 0.0F, -3.5F, 4, 0, 4, 0.0F);
      this.setRotateAngle(this.arrow, 0.0F, (float) (Math.PI / 4), 0.0F);
      this.shaft = new ModelRenderer(this, 24, 8);
      this.shaft.setRotationPoint(0.0F, -0.25F, 0.0F);
      this.shaft.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
      this.gear2 = new ModelRenderer(this, 8, 0);
      this.gear2.setRotationPoint(3.0F, 1.0F, 0.0F);
      this.gear2.addBox(-2.0F, 0.0F, -2.0F, 4, 0, 4, 0.0F);
      this.gear6 = new ModelRenderer(this, 6, 0);
      this.gear6.setRotationPoint(2.0F, 0.25F, 2.0F);
      this.gear6.addBox(-1.0F, 0.0F, -1.0F, 2, 0, 2, 0.0F);
      this.gear3 = new ModelRenderer(this, -5, 4);
      this.gear3.setRotationPoint(-2.25F, 1.5F, -2.25F);
      this.gear3.addBox(-2.5F, 0.0F, -2.5F, 5, 0, 5, 0.0F);
      this.gear1 = new ModelRenderer(this, 8, 0);
      this.gear1.setRotationPoint(0.0F, 1.0F, 3.0F);
      this.gear1.addBox(-2.0F, 0.0F, -2.0F, 4, 0, 4, 0.0F);
      this.gear4 = new ModelRenderer(this, -4, 0);
      this.gear4.setRotationPoint(0.0F, 2.5F, 0.0F);
      this.gear4.addBox(-2.0F, 0.0F, -2.0F, 4, 0, 4, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.disableCull();
      float angle = f * 0.017453F;
      this.arrow.rotateAngleY = (float) (Math.PI / 4) + angle;
      this.gear2.rotateAngleY = -angle;
      this.gear1.rotateAngleY = angle * 0.75F;
      this.gear3.rotateAngleY = -angle * 0.5F;
      this.gear4.rotateAngleY = angle;
      this.gear5.rotateAngleY = this.gear3.rotateAngleY;
      this.gear6.rotateAngleY = angle * 2.0F;
      this.gear5.render(f5);
      this.shape1.render(f5);
      this.arrow.render(f5);
      this.shaft.render(f5);
      this.gear2.render(f5);
      this.gear6.render(f5);
      this.gear3.render(f5);
      this.gear1.render(f5);
      this.gear4.render(f5);
      GlStateManager.enableCull();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
