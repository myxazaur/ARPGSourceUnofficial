//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.mobs.WhiteSlime;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class SlimeModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;
   public ModelRenderer shape3;

   public SlimeModel() {
      this.textureWidth = 48;
      this.textureHeight = 24;
      this.shape1 = new ModelRenderer(this, 0, 0);
      this.shape1.setRotationPoint(0.0F, 23.0F, 0.0F);
      this.shape1.addBox(-6.0F, -11.0F, -6.0F, 12, 12, 12, 0.0F);
      this.shape2 = new ModelRenderer(this, 0, 0);
      this.shape2.setRotationPoint(0.0F, 23.0F, 0.0F);
      this.shape2.addBox(-4.0F, -6.0F, -5.0F, 2, 2, 2, 0.0F);
      this.shape3 = new ModelRenderer(this, 0, 4);
      this.shape3.setRotationPoint(0.0F, 23.0F, 0.0F);
      this.shape3.addBox(2.0F, -6.0F, -5.0F, 2, 2, 2, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (entity instanceof WhiteSlime) {
         float jumpscale = ((WhiteSlime)entity).jumpscale;
         float size = ((WhiteSlime)entity).slimesize;
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape2.offsetX, this.shape2.offsetY, this.shape2.offsetZ);
         GlStateManager.translate(this.shape2.rotationPointX * f5, this.shape2.rotationPointY * f5, this.shape2.rotationPointZ * f5);
         GlStateManager.scale(size, size, size);
         GlStateManager.translate(-this.shape2.offsetX, -this.shape2.offsetY, -this.shape2.offsetZ);
         GlStateManager.translate(-this.shape2.rotationPointX * f5, -this.shape2.rotationPointY * f5, -this.shape2.rotationPointZ * f5);
         this.shape2.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.translate(this.shape3.offsetX, this.shape3.offsetY, this.shape3.offsetZ);
         GlStateManager.translate(this.shape3.rotationPointX * f5, this.shape3.rotationPointY * f5, this.shape3.rotationPointZ * f5);
         GlStateManager.scale(size, size, size);
         GlStateManager.translate(-this.shape3.offsetX, -this.shape3.offsetY, -this.shape3.offsetZ);
         GlStateManager.translate(-this.shape3.rotationPointX * f5, -this.shape3.rotationPointY * f5, -this.shape3.rotationPointZ * f5);
         this.shape3.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GL11.glEnable(3042);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.translate(this.shape1.offsetX, this.shape1.offsetY, this.shape1.offsetZ);
         GlStateManager.translate(this.shape1.rotationPointX * f5, this.shape1.rotationPointY * f5, this.shape1.rotationPointZ * f5);
         GlStateManager.scale(size, size, size);
         GlStateManager.scale(1.0 - jumpscale / 3.0F, 1.0 + jumpscale, 1.0 - jumpscale / 3.0F);
         GlStateManager.translate(-this.shape1.offsetX, -this.shape1.offsetY, -this.shape1.offsetZ);
         GlStateManager.translate(-this.shape1.rotationPointX * f5, -this.shape1.rotationPointY * f5, -this.shape1.rotationPointZ * f5);
         this.shape1.render(f5);
         GL11.glDisable(3042);
         GlStateManager.popMatrix();
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
