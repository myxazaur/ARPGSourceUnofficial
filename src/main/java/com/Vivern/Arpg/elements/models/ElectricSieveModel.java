//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ElectricSieveModel extends ModelBase {
   public ModelRenderer sieve;
   public ModelRenderer leg1;
   public ModelRenderer leg2;
   public ModelRenderer slegs;
   public ModelRenderer wall1;
   public ModelRenderer wall2;
   public ModelRenderer wall3;
   public ModelRenderer wall4;

   public ElectricSieveModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.wall2 = new ModelRenderer(this, 0, 0);
      this.wall2.setRotationPoint(0.0F, -6.0F, 0.0F);
      this.wall2.addBox(-7.0F, 0.0F, -8.0F, 15, 6, 1, 0.0F);
      this.setRotateAngle(this.wall2, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.leg1 = new ModelRenderer(this, 0, 27);
      this.leg1.setRotationPoint(0.0F, 17.0F, 0.0F);
      this.leg1.addBox(-8.0F, 0.0F, -8.0F, 4, 4, 16, 0.01F);
      this.wall1 = new ModelRenderer(this, 0, 0);
      this.wall1.setRotationPoint(0.0F, -6.0F, 0.0F);
      this.wall1.addBox(-7.0F, 0.0F, -8.0F, 15, 6, 1, 0.0F);
      this.leg2 = new ModelRenderer(this, 0, 27);
      this.leg2.setRotationPoint(0.0F, 17.0F, 0.0F);
      this.leg2.addBox(4.0F, 0.0F, -8.0F, 4, 4, 16, 0.01F);
      this.slegs = new ModelRenderer(this, 0, 7);
      this.slegs.setRotationPoint(0.0F, 21.0F, 0.0F);
      this.slegs.addBox(-8.0F, 0.0F, -8.0F, 16, 3, 16, 0.0F);
      this.sieve = new ModelRenderer(this, 0, 47);
      this.sieve.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.sieve.addBox(-8.0F, 0.0F, -8.0F, 16, 1, 16, 0.0F);
      this.wall4 = new ModelRenderer(this, 0, 0);
      this.wall4.setRotationPoint(0.0F, -6.0F, 0.0F);
      this.wall4.addBox(-7.0F, 0.0F, -8.0F, 15, 6, 1, 0.0F);
      this.setRotateAngle(this.wall4, 0.0F, (float) (Math.PI * 3.0 / 2.0), 0.0F);
      this.wall3 = new ModelRenderer(this, 0, 0);
      this.wall3.setRotationPoint(0.0F, -6.0F, 0.0F);
      this.wall3.addBox(-7.0F, 0.0F, -8.0F, 15, 6, 1, 0.0F);
      this.setRotateAngle(this.wall3, 0.0F, (float) Math.PI, 0.0F);
      this.sieve.addChild(this.wall2);
      this.sieve.addChild(this.wall1);
      this.sieve.addChild(this.wall4);
      this.sieve.addChild(this.wall3);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.sieve.offsetZ = f3;
      this.leg1.render(f5);
      this.leg2.render(f5);
      this.slegs.render(f5);
      this.sieve.render(f5);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
