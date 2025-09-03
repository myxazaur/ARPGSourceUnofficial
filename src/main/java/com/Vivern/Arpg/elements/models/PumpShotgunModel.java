//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.GetMOP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class PumpShotgunModel extends ModelBase {
   public ModelRenderer shapess1;
   public ModelRenderer shape1;
   public ModelRenderer shape3;
   public ModelRenderer pump;
   public ModelRenderer shapepr1;
   public ModelRenderer shapess1_1;
   public ModelRenderer shapescope;

   public PumpShotgunModel() {
      this.textureWidth = 38;
      this.textureHeight = 20;
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(0.0F, 1.9F, -6.5F);
      this.shape1.addBox(-1.0F, -3.0F, -11.0F, 2, 2, 17, 0.0F);
      this.shapess1 = new ModelRenderer(this, 31, 8);
      this.shapess1.setRotationPoint(0.0F, 1.2F, 4.0F);
      this.shapess1.addBox(-0.5F, -0.5F, -1.0F, 1, 3, 2, 0.0F);
      this.setRotateAngle(this.shapess1, 0.5009095F, 0.0F, 0.0F);
      this.shape3 = new ModelRenderer(this, 21, 0);
      this.shape3.setRotationPoint(0.0F, -0.2F, -0.5F);
      this.shape3.addBox(-1.0F, -1.0F, 0.0F, 2, 3, 4, 0.0F);
      this.shapescope = new ModelRenderer(this, 0, 10);
      this.shapescope.setRotationPoint(0.0F, -3.0F, 0.0F);
      this.shapescope.addBox(-0.5F, -1.0F, -4.0F, 1, 1, 5, 0.0F);
      this.shapepr1 = new ModelRenderer(this, 0, 0);
      this.shapepr1.setRotationPoint(0.0F, 0.0F, 6.3F);
      this.shapepr1.addBox(-0.5F, 0.0F, -0.1F, 1, 3, 7, 0.0F);
      this.pump = new ModelRenderer(this, 21, 9);
      this.pump.setRotationPoint(0.0F, 0.0F, -9.5F);
      this.pump.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 5, 0.0F);
      this.shapess1_1 = new ModelRenderer(this, 10, 0);
      this.shapess1_1.setRotationPoint(0.0F, -1.3F, 3.8F);
      this.shapess1_1.addBox(-0.5F, 0.0F, -2.0F, 1, 4, 2, 0.0F);
      this.setRotateAngle(this.shapess1_1, 1.1838568F, 0.0F, 0.0F);
      this.shape1.addChild(this.shapescope);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.disableCull();
      this.pump.offsetZ = (GetMOP.getfromto(f1, 0.3F, 0.53F) - GetMOP.getfromto(f1, 0.55F, 0.78F)) * 3.5F;
      this.shape1.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapess1.offsetX, this.shapess1.offsetY, this.shapess1.offsetZ);
      GlStateManager.translate(this.shapess1.rotationPointX * f5, this.shapess1.rotationPointY * f5, this.shapess1.rotationPointZ * f5);
      GlStateManager.scale(1.1, 1.0, 1.2);
      GlStateManager.translate(-this.shapess1.offsetX, -this.shapess1.offsetY, -this.shapess1.offsetZ);
      GlStateManager.translate(-this.shapess1.rotationPointX * f5, -this.shapess1.rotationPointY * f5, -this.shapess1.rotationPointZ * f5);
      this.shapess1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shape3.offsetX, this.shape3.offsetY, this.shape3.offsetZ);
      GlStateManager.translate(this.shape3.rotationPointX * f5, this.shape3.rotationPointY * f5, this.shape3.rotationPointZ * f5);
      GlStateManager.scale(1.2, 1.1, 1.1);
      GlStateManager.translate(-this.shape3.offsetX, -this.shape3.offsetY, -this.shape3.offsetZ);
      GlStateManager.translate(-this.shape3.rotationPointX * f5, -this.shape3.rotationPointY * f5, -this.shape3.rotationPointZ * f5);
      this.shape3.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapepr1.offsetX, this.shapepr1.offsetY, this.shapepr1.offsetZ);
      GlStateManager.translate(this.shapepr1.rotationPointX * f5, this.shapepr1.rotationPointY * f5, this.shapepr1.rotationPointZ * f5);
      GlStateManager.scale(1.1, 1.0, 1.0);
      GlStateManager.translate(-this.shapepr1.offsetX, -this.shapepr1.offsetY, -this.shapepr1.offsetZ);
      GlStateManager.translate(-this.shapepr1.rotationPointX * f5, -this.shapepr1.rotationPointY * f5, -this.shapepr1.rotationPointZ * f5);
      this.shapepr1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.pump.offsetX, this.pump.offsetY, this.pump.offsetZ);
      GlStateManager.translate(this.pump.rotationPointX * f5, this.pump.rotationPointY * f5, this.pump.rotationPointZ * f5);
      GlStateManager.scale(1.2, 1.2, 1.2);
      GlStateManager.translate(-this.pump.offsetX, -this.pump.offsetY, -this.pump.offsetZ);
      GlStateManager.translate(-this.pump.rotationPointX * f5, -this.pump.rotationPointY * f5, -this.pump.rotationPointZ * f5);
      this.pump.render(f5);
      GlStateManager.popMatrix();
      this.shapess1_1.render(f5);
      GlStateManager.enableCull();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
