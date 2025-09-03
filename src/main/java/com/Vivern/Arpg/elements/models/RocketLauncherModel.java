//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.ColorConverters;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.renders.ModelRendererLimited;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class RocketLauncherModel extends ModelBase {
   public ModelRenderer shapess;
   public ModelRenderer shape1;
   public ModelRenderer shapeammo;
   public ModelRenderer shape2;
   public ModelRenderer shaperr1;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer shape5;
   public ModelRenderer shape6;
   public ModelRenderer shape7;
   public ModelRenderer shape8;
   public ModelRenderer shaperr2;
   public ModelRendererLimited rocket;
   public ModelRenderer shaperr3;
   public ModelRendererLimited rocket2;

   public RocketLauncherModel() {
      this.textureWidth = 64;
      this.textureHeight = 16;
      this.shape5 = new ModelRenderer(this, 0, 9);
      this.shape5.setRotationPoint(0.0F, 0.0F, 6.5F);
      this.shape5.addBox(-1.0F, 1.7F, 0.0F, 2, 1, 4, 0.0F);
      this.setRotateAngle(this.shape5, 0.0F, 0.0F, (float) (Math.PI / 3));
      this.shape3 = new ModelRenderer(this, 0, 9);
      this.shape3.setRotationPoint(0.0F, 0.0F, 6.5F);
      this.shape3.addBox(-1.0F, 1.7F, 0.0F, 2, 1, 4, 0.0F);
      this.setRotateAngle(this.shape3, 0.0F, 0.0F, (float) Math.PI);
      this.shape1 = new ModelRenderer(this, 44, 0);
      this.shape1.setRotationPoint(-1.5F, -1.5F, 3.5F);
      this.shape1.addBox(0.0F, 0.0F, 0.0F, 3, 3, 5, 0.0F);
      this.shape8 = new ModelRenderer(this, 48, 8);
      this.shape8.setRotationPoint(0.0F, 0.0F, 5.7F);
      this.shape8.addBox(-1.0F, 1.7F, 0.0F, 2, 1, 6, 0.0F);
      this.setRotateAngle(this.shape8, 0.0F, 0.0F, (float) (Math.PI * 2.0 / 3.0));
      this.shapess = new ModelRenderer(this, 0, 6);
      this.shapess.setRotationPoint(0.0F, 2.5F, -1.0F);
      this.shapess.addBox(-0.5F, -0.5F, 0.0F, 1, 4, 1, 0.0F);
      this.setRotateAngle(this.shapess, 0.13665928F, 0.0F, 0.0F);
      this.shapeammo = new ModelRenderer(this, 0, 0);
      this.shapeammo.setRotationPoint(-1.5F, -1.5F, 1.4F);
      this.shapeammo.addBox(0.0F, 0.0F, 0.0F, 3, 3, 3, 0.2F);
      this.shaperr2 = new ModelRenderer(this, 16, 0);
      this.shaperr2.setRotationPoint(0.0F, -1.3F, 1.5F);
      this.shaperr2.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 3, 0.0F);
      this.setRotateAngle(this.shaperr2, 2.3675392F, 0.0F, 0.0F);
      this.shaperr1 = new ModelRenderer(this, 0, 0);
      this.shaperr1.setRotationPoint(0.0F, 0.0F, -10.65F);
      this.shaperr1.addBox(-0.5F, -1.75F, 0.0F, 1, 1, 14, 0.1F);
      this.shaperr3 = new ModelRenderer(this, 16, 1);
      this.shaperr3.setRotationPoint(0.0F, 0.0F, 3.5F);
      this.shaperr3.addBox(-1.0F, -1.0F, -6.0F, 2, 2, 11, 0.0F);
      this.setRotateAngle(this.shaperr3, 0.7740535F, 0.0F, 0.0F);
      this.rocket = new ModelRendererLimited(this, 16, 4);
      this.rocket.setRotationPoint(0.0F, -1.25F, 6.5F);
      this.rocket.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 2, 0.0F);
      this.shape4 = new ModelRenderer(this, 0, 9);
      this.shape4.setRotationPoint(0.0F, 0.0F, 6.5F);
      this.shape4.addBox(-1.0F, 1.7F, 0.0F, 2, 1, 4, 0.0F);
      this.setRotateAngle(this.shape4, 0.0F, 0.0F, (float) (-Math.PI / 3));
      this.shape2 = new ModelRenderer(this, 31, 3);
      this.shape2.setRotationPoint(-2.0F, -2.0F, -1.5F);
      this.shape2.addBox(0.0F, 0.0F, 0.0F, 4, 4, 5, 0.0F);
      this.shape6 = new ModelRenderer(this, 48, 8);
      this.shape6.setRotationPoint(0.0F, 0.0F, 5.7F);
      this.shape6.addBox(-1.0F, 1.7F, 0.0F, 2, 1, 6, 0.0F);
      this.shape7 = new ModelRenderer(this, 48, 8);
      this.shape7.setRotationPoint(0.0F, 0.0F, 5.7F);
      this.shape7.addBox(-1.0F, 1.7F, 0.0F, 2, 1, 6, 0.0F);
      this.setRotateAngle(this.shape7, 0.0F, 0.0F, (float) (-Math.PI * 2.0 / 3.0));
      this.rocket2 = new ModelRendererLimited(this, 16, 4);
      this.rocket2.setRotationPoint(0.0F, 0.0F, -6.5F);
      this.rocket2.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 2, 0.0F);
      this.shaperr1.addChild(this.shaperr2);
      this.shaperr2.addChild(this.shaperr3);
      this.shaperr1.addChild(this.rocket);
      this.shaperr3.addChild(this.rocket2);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.shape3.offsetZ = -2.0F * GetMOP.getfromto(f2, 0.5F, 0.7F);
      this.shape4.offsetZ = this.shape3.offsetZ;
      this.shape5.offsetZ = this.shape3.offsetZ;
      this.shape6.offsetZ = -2.0F * GetMOP.getfromto(f1, 0.0F, 0.2F) + 2.0F * GetMOP.getfromto(f1, 0.2F, 0.4F);
      this.shape7.offsetZ = this.shape6.offsetZ;
      this.shape8.offsetZ = this.shape6.offsetZ;
      this.shapeammo.offsetZ = -6.5F * GetMOP.getfromto(f1, 0.0F, 0.3F) + 6.5F * GetMOP.getfromto(f1, 0.5F, 0.8F);
      float ff1 = GetMOP.getfromto(f1, 0.3F, 0.45F);
      float ff2 = GetMOP.getfromto(f1, 0.8F, 0.85F);
      float ff3 = GetMOP.getfromto(f1, 0.45F, 0.6F);
      float ff4 = GetMOP.getfromto(f2, 0.5F, 0.8F);
      float ff5 = GetMOP.getfromto(f1, 0.5F, 0.7F);
      float ff6 = GetMOP.getfromto(f1, 0.8F, 1.0F);
      float ff7 = GetMOP.getfromto(f1, 0.9F, 1.0F);
      float ff8 = GetMOP.getfromto(f2, 0.0F, 0.5F);
      float ff9 = GetMOP.getfromto(f2, 0.8F, 1.0F);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape5.offsetX, this.shape5.offsetY, this.shape5.offsetZ);
      GlStateManager.translate(this.shape5.rotationPointX * f5, this.shape5.rotationPointY * f5, this.shape5.rotationPointZ * f5);
      GlStateManager.scale(0.8, 0.8, 0.8);
      GlStateManager.translate(-this.shape5.offsetX, -this.shape5.offsetY, -this.shape5.offsetZ);
      GlStateManager.translate(-this.shape5.rotationPointX * f5, -this.shape5.rotationPointY * f5, -this.shape5.rotationPointZ * f5);
      this.shape5.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape3.offsetX, this.shape3.offsetY, this.shape3.offsetZ);
      GlStateManager.translate(this.shape3.rotationPointX * f5, this.shape3.rotationPointY * f5, this.shape3.rotationPointZ * f5);
      GlStateManager.scale(0.8, 0.8, 0.8);
      GlStateManager.translate(-this.shape3.offsetX, -this.shape3.offsetY, -this.shape3.offsetZ);
      GlStateManager.translate(-this.shape3.rotationPointX * f5, -this.shape3.rotationPointY * f5, -this.shape3.rotationPointZ * f5);
      this.shape3.render(f5);
      GlStateManager.popMatrix();
      this.shape1.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape8.offsetX, this.shape8.offsetY, this.shape8.offsetZ);
      GlStateManager.translate(this.shape8.rotationPointX * f5, this.shape8.rotationPointY * f5, this.shape8.rotationPointZ * f5);
      GlStateManager.scale(0.8, 0.8, 0.8);
      GlStateManager.translate(-this.shape8.offsetX, -this.shape8.offsetY, -this.shape8.offsetZ);
      GlStateManager.translate(-this.shape8.rotationPointX * f5, -this.shape8.rotationPointY * f5, -this.shape8.rotationPointZ * f5);
      this.shape8.render(f5);
      GlStateManager.popMatrix();
      this.shapess.render(f5);
      this.shapeammo.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape4.offsetX, this.shape4.offsetY, this.shape4.offsetZ);
      GlStateManager.translate(this.shape4.rotationPointX * f5, this.shape4.rotationPointY * f5, this.shape4.rotationPointZ * f5);
      GlStateManager.scale(0.8, 0.8, 0.8);
      GlStateManager.translate(-this.shape4.offsetX, -this.shape4.offsetY, -this.shape4.offsetZ);
      GlStateManager.translate(-this.shape4.rotationPointX * f5, -this.shape4.rotationPointY * f5, -this.shape4.rotationPointZ * f5);
      this.shape4.render(f5);
      GlStateManager.popMatrix();
      this.shape2.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape6.offsetX, this.shape6.offsetY, this.shape6.offsetZ);
      GlStateManager.translate(this.shape6.rotationPointX * f5, this.shape6.rotationPointY * f5, this.shape6.rotationPointZ * f5);
      GlStateManager.scale(0.8, 0.8, 0.8);
      GlStateManager.translate(-this.shape6.offsetX, -this.shape6.offsetY, -this.shape6.offsetZ);
      GlStateManager.translate(-this.shape6.rotationPointX * f5, -this.shape6.rotationPointY * f5, -this.shape6.rotationPointZ * f5);
      this.shape6.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape7.offsetX, this.shape7.offsetY, this.shape7.offsetZ);
      GlStateManager.translate(this.shape7.rotationPointX * f5, this.shape7.rotationPointY * f5, this.shape7.rotationPointZ * f5);
      GlStateManager.scale(0.8, 0.8, 0.8);
      GlStateManager.translate(-this.shape7.offsetX, -this.shape7.offsetY, -this.shape7.offsetZ);
      GlStateManager.translate(-this.shape7.rotationPointX * f5, -this.shape7.rotationPointY * f5, -this.shape7.rotationPointZ * f5);
      this.shape7.render(f5);
      GlStateManager.popMatrix();
      Vec3d vec = ColorConverters.DecimaltoRGB((int)f4);
      this.rocket.setColor((float)vec.x, (float)vec.y, (float)vec.z, 1.0F);
      this.rocket2.setColor((float)vec.x, (float)vec.y, (float)vec.z, 1.0F);

      for (int i = 0; i < 6; i++) {
         this.rocket.offsetY = -0.7F * ff1 + 0.7F * ff2;
         this.rocket.offsetZ = -2.0F * ff3;
         this.shaperr2.rotateAngleX = Math.max(2.36F - 1.59F * ff4 - 1.905F * ff5 + 1.905F * ff6, 0.455F);
         this.shaperr3.offsetZ = -2.0F * ff9;
         this.shaperr3.offsetY = -2.0F * ff9;
         this.shaperr3.rotateAngleX = (float) Math.PI - this.shaperr2.rotateAngleX;
         this.rocket2.offsetZ = (f3 >= i + 1 ? 2.0F : 4.0F) - 2.0F * ff7;
         this.shaperr1.rotateAngleZ = i * 1.047198F + f + 1.047198F + (float) Math.PI * ff8;
         this.shaperr1.render(f5);
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
