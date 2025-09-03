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

public class HolyShotgunModel extends ModelBase {
   public ModelRenderer shapess1;
   public ModelRenderer shape1;
   public ModelRenderer sh1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;
   public ModelRenderer shape4;
   public ModelRenderer sh1a;
   public ModelRenderer sh2;
   public ModelRendererLimited ammo1;
   public ModelRenderer sh3;
   public ModelRenderer sh4;
   public ModelRenderer sh2a;
   public ModelRendererLimited ammo1a;
   public ModelRenderer sh3a;
   public ModelRenderer sh4a;

   public HolyShotgunModel() {
      this.textureWidth = 42;
      this.textureHeight = 18;
      this.ammo1 = new ModelRendererLimited(this, 28, 9);
      this.ammo1.setRotationPoint(-6.0F, 3.5F, 6.0F);
      this.ammo1.addBox(-4.0F, -8.0F, -1.5F, 1, 1, 2, 0.0F);
      this.setRotateAngle(this.ammo1, 0.0F, 0.0F, (float) (Math.PI / 2));
      this.sh2 = new ModelRenderer(this, 0, 0);
      this.sh2.setRotationPoint(2.5F, 0.5F, -10.0F);
      this.sh2.addBox(0.0F, 0.0F, 0.0F, 1, 2, 16, 0.0F);
      this.setRotateAngle(this.sh2, 0.0F, 0.0F, (float) (Math.PI / 2));
      this.sh3 = new ModelRenderer(this, 0, 0);
      this.sh3.setRotationPoint(0.5F, -0.5F, -10.0F);
      this.sh3.addBox(0.0F, 0.0F, 0.0F, 1, 2, 16, 0.0F);
      this.setRotateAngle(this.sh3, 0.0F, 0.0F, (float) (-Math.PI / 2));
      this.sh3a = new ModelRenderer(this, 0, 0);
      this.sh3a.setRotationPoint(0.5F, -0.5F, -10.0F);
      this.sh3a.addBox(0.0F, 0.0F, 0.0F, 1, 2, 16, 0.0F);
      this.setRotateAngle(this.sh3a, 0.0F, 0.0F, (float) (-Math.PI / 2));
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(0.0F, 2.0F, -6.0F);
      this.shape1.addBox(-1.5F, 0.0F, 0.0F, 3, 12, 2, 0.0F);
      this.setRotateAngle(this.shape1, (float) (Math.PI / 2), 0.0F, 0.0F);
      this.sh2a = new ModelRenderer(this, 0, 0);
      this.sh2a.setRotationPoint(2.5F, 0.5F, -10.0F);
      this.sh2a.addBox(0.0F, 0.0F, 0.0F, 1, 2, 16, 0.0F);
      this.setRotateAngle(this.sh2a, 0.0F, 0.0F, (float) (Math.PI / 2));
      this.sh4a = new ModelRenderer(this, 0, 0);
      this.sh4a.setRotationPoint(2.0F, -1.0F, -10.0F);
      this.sh4a.addBox(0.0F, 0.0F, 0.0F, 1, 2, 16, 0.0F);
      this.sh4 = new ModelRenderer(this, 0, 0);
      this.sh4.setRotationPoint(2.0F, -1.0F, -10.0F);
      this.sh4.addBox(0.0F, 0.0F, 0.0F, 1, 2, 16, 0.0F);
      this.sh1 = new ModelRenderer(this, 0, 0);
      this.sh1.mirror = true;
      this.sh1.setRotationPoint(-1.55F, 1.0F, -5.0F);
      this.sh1.addBox(0.0F, -1.0F, -10.0F, 1, 2, 16, 0.0F);
      this.setRotateAngle(this.sh1, 0.0F, 0.0F, (float) (-Math.PI / 2));
      this.sh1a = new ModelRenderer(this, 0, 0);
      this.sh1a.mirror = true;
      this.sh1a.setRotationPoint(1.55F, 1.0F, -5.0F);
      this.sh1a.addBox(0.0F, -1.0F, -10.0F, 1, 2, 16, 0.0F);
      this.setRotateAngle(this.sh1a, 0.0F, 0.0F, (float) (-Math.PI / 2));
      this.shape3 = new ModelRenderer(this, 25, 12);
      this.shape3.setRotationPoint(0.0F, -2.0F, 1.0F);
      this.shape3.addBox(-2.5F, 0.0F, 0.0F, 5, 1, 3, 0.0F);
      this.ammo1a = new ModelRendererLimited(this, 28, 9);
      this.ammo1a.setRotationPoint(-6.0F, 3.5F, 6.0F);
      this.ammo1a.addBox(-4.0F, -8.0F, -1.5F, 1, 1, 2, 0.0F);
      this.setRotateAngle(this.ammo1a, 0.0F, 0.0F, (float) (Math.PI / 2));
      this.shapess1 = new ModelRenderer(this, 20, 6);
      this.shapess1.setRotationPoint(0.0F, 1.0F, 4.0F);
      this.shapess1.addBox(-1.0F, 0.5F, -1.0F, 2, 5, 2, 0.0F);
      this.setRotateAngle(this.shapess1, 0.4098033F, 0.0F, 0.0F);
      this.shape2 = new ModelRenderer(this, 20, 0);
      this.shape2.setRotationPoint(0.0F, -1.5F, 1.0F);
      this.shape2.addBox(-3.0F, 0.0F, 0.0F, 6, 2, 4, 0.0F);
      this.shape4 = new ModelRenderer(this, 34, 6);
      this.shape4.setRotationPoint(0.0F, -1.5F, 0.5F);
      this.shape4.addBox(-0.5F, -1.0F, 0.0F, 1, 1, 2, 0.0F);
      this.sh1.addChild(this.ammo1);
      this.sh1.addChild(this.sh2);
      this.sh1.addChild(this.sh3);
      this.sh1a.addChild(this.sh3a);
      this.sh1a.addChild(this.sh2a);
      this.sh1a.addChild(this.sh4a);
      this.sh1.addChild(this.sh4);
      this.sh1a.addChild(this.ammo1a);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.disableCull();
      this.sh1.rotateAngleY = (GetMOP.getfromto(f1, 0.085F, 0.185F) - GetMOP.getfromto(f1, 0.65F, 0.8F)) * 0.48F;
      this.sh1a.rotateAngleY = this.sh1.rotateAngleY;
      float ft1 = 1.0F - GetMOP.getfromto(f1, 0.33F, 0.5F);
      this.ammo1.rotateAngleX = -2.18F * ft1;
      this.ammo1.rotateAngleY = 1.0F * ft1;
      this.ammo1a.rotateAngleX = -2.09F * ft1;
      this.ammo1a.rotateAngleY = 1.1F * ft1;
      Vec3d vec = ColorConverters.DecimaltoRGB((int)f3);
      this.ammo1.setColor((float)vec.x, (float)vec.y, (float)vec.z, 1.0F);
      this.ammo1a.setColor((float)vec.x, (float)vec.y, (float)vec.z, 1.0F);
      this.shape1.render(f5);
      this.sh1.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.sh1a.offsetX, this.sh1a.offsetY, this.sh1a.offsetZ);
      GlStateManager.translate(this.sh1a.rotationPointX * f5, this.sh1a.rotationPointY * f5, this.sh1a.rotationPointZ * f5);
      GlStateManager.scale(-1.0, 1.0, 1.0);
      GlStateManager.translate(-this.sh1a.offsetX, -this.sh1a.offsetY, -this.sh1a.offsetZ);
      GlStateManager.translate(-this.sh1a.rotationPointX * f5, -this.sh1a.rotationPointY * f5, -this.sh1a.rotationPointZ * f5);
      this.sh1a.render(f5);
      GlStateManager.popMatrix();
      this.shape3.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapess1.offsetX, this.shapess1.offsetY, this.shapess1.offsetZ);
      GlStateManager.translate(this.shapess1.rotationPointX * f5, this.shapess1.rotationPointY * f5, this.shapess1.rotationPointZ * f5);
      GlStateManager.scale(1.0, 1.0, 1.1);
      GlStateManager.translate(-this.shapess1.offsetX, -this.shapess1.offsetY, -this.shapess1.offsetZ);
      GlStateManager.translate(-this.shapess1.rotationPointX * f5, -this.shapess1.rotationPointY * f5, -this.shapess1.rotationPointZ * f5);
      this.shapess1.render(f5);
      GlStateManager.popMatrix();
      this.shape2.render(f5);
      this.shape4.render(f5);
      GlStateManager.enableCull();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
