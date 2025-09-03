//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.renders.ModelRendererLimited;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class RollModel extends ModelBase {
   public ModelRenderer staf1;
   public ModelRenderer staf2;
   public ModelRenderer paper;

   public RollModel(int textureHeight) {
      this.textureWidth = 32;
      this.textureHeight = textureHeight;
      this.staf2 = new ModelRenderer(this, 9, 0);
      this.staf2.setRotationPoint(16.0F, 0.5F, 5.0F);
      this.staf2.addBox(-1.0F, -4.0F, -1.0F, 2, 8, 2, 0.0F);
      this.setRotateAngle(this.staf2, 0.18203785F, 0.0F, 0.0F);
      this.staf1 = new ModelRenderer(this, 0, 0);
      this.staf1.setRotationPoint(0.0F, 0.5F, 5.0F);
      this.staf1.addBox(-1.0F, -4.0F, -1.0F, 2, 8, 2, 0.0F);
      this.setRotateAngle(this.staf1, 0.18203785F, 0.0F, 0.0F);
      this.paper = new ModelRendererLimited(this, -18, 10, false, false, false, false, false, true);
      this.paper.setRotationPoint(0.8F, 0.5F, 5.0F);
      this.paper.addBox(0.0F, -3.0F, 0.0F, 18, 6, 0, 0.0F);
      this.setRotateAngle(this.paper, 0.18203785F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.staf2.offsetX = -12.8F + 12.8F * f;
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf2.offsetX, this.staf2.offsetY, this.staf2.offsetZ);
      GlStateManager.translate(this.staf2.rotationPointX * f5, this.staf2.rotationPointY * f5, this.staf2.rotationPointZ * f5);
      GlStateManager.scale(0.8, 0.8, 0.8);
      GlStateManager.translate(-this.staf2.offsetX, -this.staf2.offsetY, -this.staf2.offsetZ);
      GlStateManager.translate(-this.staf2.rotationPointX * f5, -this.staf2.rotationPointY * f5, -this.staf2.rotationPointZ * f5);
      this.staf2.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.staf1.offsetX, this.staf1.offsetY, this.staf1.offsetZ);
      GlStateManager.translate(this.staf1.rotationPointX * f5, this.staf1.rotationPointY * f5, this.staf1.rotationPointZ * f5);
      GlStateManager.scale(0.8, 0.8, 0.8);
      GlStateManager.translate(-this.staf1.offsetX, -this.staf1.offsetY, -this.staf1.offsetZ);
      GlStateManager.translate(-this.staf1.rotationPointX * f5, -this.staf1.rotationPointY * f5, -this.staf1.rotationPointZ * f5);
      this.staf1.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.disableCull();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.paper.offsetX, this.paper.offsetY, this.paper.offsetZ);
      GlStateManager.translate(this.paper.rotationPointX * f5, this.paper.rotationPointY * f5, this.paper.rotationPointZ * f5);
      GlStateManager.scale(0.15 + 0.65 * f, 0.8, 0.8);
      GlStateManager.translate(-this.paper.offsetX, -this.paper.offsetY, -this.paper.offsetZ);
      GlStateManager.translate(-this.paper.rotationPointX * f5, -this.paper.rotationPointY * f5, -this.paper.rotationPointZ * f5);
      this.paper.render(f5);
      GlStateManager.popMatrix();
      GlStateManager.enableCull();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
