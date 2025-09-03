//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.renders.ModelRendererLimited;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class WhipModel extends ModelBase {
   public ModelRenderer shapeMAIN;
   public ModelRenderer[] shapeWhips;
   public ModelRenderer shapeUp;
   public ModelRenderer shapeDown;
   public ModelRenderer shapeOverlay;
   public ModelRenderer shapeGem;
   public ModelRenderer shapeProtect;
   public ModelRenderer shapeRings1;
   public ModelRenderer shapeRings2;
   public ModelRenderer shapeRings3;
   public ModelRenderer shapeRings4;

   public WhipModel() {
      this.textureWidth = 492;
      this.textureHeight = 48;
      this.shapeOverlay = new ModelRenderer(this, 4, 0);
      this.shapeOverlay.setRotationPoint(0.0F, 3.5F, 5.0F);
      this.shapeOverlay.addBox(-0.5F, -4.49F, -0.5F, 1, 12, 1, 0.0F);
      this.shapeRings3 = new ModelRendererLimited(this, 3, 42, false, true, false, false, false, false);
      this.shapeRings3.setRotationPoint(0.0F, 2.0F, 5.0F);
      this.shapeRings3.addBox(0.0F, -3.0F, -3.25F, 0, 3, 3, 0.0F);
      this.setRotateAngle(this.shapeRings3, 0.0F, (float) (Math.PI * 3.0 / 4.0), 0.0F);
      this.shapeProtect = new ModelRenderer(this, 0, 34);
      this.shapeProtect.setRotationPoint(0.0F, 3.5F, 4.1F);
      this.shapeProtect.addBox(-0.5F, -1.0F, -3.0F, 1, 8, 3, 0.0F);
      this.shapeMAIN = new ModelRenderer(this, 0, 0);
      this.shapeMAIN.setRotationPoint(0.0F, 3.5F, 5.0F);
      this.shapeMAIN.addBox(-0.5F, -4.5F, -0.5F, 1, 12, 1, 0.0F);
      this.shapeGem = new ModelRenderer(this, 0, 30);
      this.shapeGem.setRotationPoint(0.0F, 11.5F, 5.0F);
      this.shapeGem.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, -0.2F);
      this.setRotateAngle(this.shapeGem, 0.0F, (float) (Math.PI / 4), -0.95609134F);
      this.shapeRings4 = new ModelRendererLimited(this, 6, 42, false, true, false, false, false, false);
      this.shapeRings4.setRotationPoint(0.0F, 2.0F, 5.0F);
      this.shapeRings4.addBox(0.0F, -3.0F, -3.25F, 0, 3, 3, 0.0F);
      this.setRotateAngle(this.shapeRings4, 0.0F, (float) (-Math.PI * 3.0 / 4.0), 0.0F);
      this.shapeDown = new ModelRenderer(this, 0, 26);
      this.shapeDown.setRotationPoint(0.0F, 3.5F, 5.0F);
      this.shapeDown.addBox(-1.0F, 6.0F, -1.0F, 2, 1, 2, 0.0F);
      this.shapeRings1 = new ModelRendererLimited(this, 0, 42, false, true, false, false, false, false);
      this.shapeRings1.setRotationPoint(0.0F, 2.0F, 5.0F);
      this.shapeRings1.addBox(0.0F, -3.0F, -3.25F, 0, 3, 3, 0.0F);
      this.setRotateAngle(this.shapeRings1, 0.0F, (float) (Math.PI / 4), 0.0F);
      this.shapeRings2 = new ModelRendererLimited(this, 9, 42, false, true, false, false, false, false);
      this.shapeRings2.setRotationPoint(0.0F, 2.0F, 5.0F);
      this.shapeRings2.addBox(0.0F, -3.0F, -3.25F, 0, 3, 3, 0.0F);
      this.setRotateAngle(this.shapeRings2, 0.0F, (float) (-Math.PI / 4), 0.0F);
      this.shapeUp = new ModelRenderer(this, 0, 22);
      this.shapeUp.setRotationPoint(0.0F, 3.5F, 5.0F);
      this.shapeUp.addBox(-1.0F, -1.0F, -1.0F, 2, 1, 2, 0.0F);
      this.shapeWhips = new ModelRenderer[24];

      for (int i = 0; i < 24; i++) {
         this.shapeWhips[i] = new ModelRendererLimited(this, 12 + i * 20, -20, false, true, false, false, false, false);
         this.shapeWhips[i].setRotationPoint(0.0F, -1.0F, 5.0F);
         this.shapeWhips[i].addBox(0.0F, -32.0F, -10.0F, 0, 48, 20, 0.0F);
      }
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.disableCull();
      GlStateManager.enableBlend();
      GlStateManager.pushMatrix();
      GlStateManager.translate(this.shapeOverlay.offsetX, this.shapeOverlay.offsetY, this.shapeOverlay.offsetZ);
      GlStateManager.translate(this.shapeOverlay.rotationPointX * f5, this.shapeOverlay.rotationPointY * f5, this.shapeOverlay.rotationPointZ * f5);
      GlStateManager.scale(1.2, 1.01, 1.2);
      GlStateManager.translate(-this.shapeOverlay.offsetX, -this.shapeOverlay.offsetY, -this.shapeOverlay.offsetZ);
      GlStateManager.translate(-this.shapeOverlay.rotationPointX * f5, -this.shapeOverlay.rotationPointY * f5, -this.shapeOverlay.rotationPointZ * f5);
      this.shapeOverlay.render(f5);
      GlStateManager.popMatrix();
      this.shapeGem.render(f5);
      this.shapeMAIN.render(f5);
      this.shapeDown.render(f5);
      this.shapeUp.render(f5);
      if (f == 0.0F) {
         int frame = MathHelper.clamp(Math.round(f1), 0, 23);
         int frameNext = MathHelper.clamp(Math.round(f1 + 1.0F), 0, 23);
         float ft1 = GetMOP.getfromto(f1, (float)frame, (float)frameNext);
         if (f2 > 0.0F) {
            AbstractMobModel.light((int)f2, true);
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F - ft1);
         ModelRenderer shapeWhip = this.shapeWhips[frame];
         GlStateManager.pushMatrix();
         GlStateManager.translate(shapeWhip.offsetX, shapeWhip.offsetY, shapeWhip.offsetZ);
         GlStateManager.translate(shapeWhip.rotationPointX * f5, shapeWhip.rotationPointY * f5, shapeWhip.rotationPointZ * f5);
         GlStateManager.scale(1.0, 1.01, 1.0);
         GlStateManager.translate(-shapeWhip.offsetX, -shapeWhip.offsetY, -shapeWhip.offsetZ);
         GlStateManager.translate(-shapeWhip.rotationPointX * f5, -shapeWhip.rotationPointY * f5, -shapeWhip.rotationPointZ * f5);
         shapeWhip.render(f5);
         GlStateManager.popMatrix();
         GlStateManager.color(1.0F, 1.0F, 1.0F, ft1);
         shapeWhip = this.shapeWhips[frameNext];
         GlStateManager.pushMatrix();
         GlStateManager.translate(shapeWhip.offsetX, shapeWhip.offsetY, shapeWhip.offsetZ);
         GlStateManager.translate(shapeWhip.rotationPointX * f5, shapeWhip.rotationPointY * f5, shapeWhip.rotationPointZ * f5);
         GlStateManager.scale(1.0, 1.01, 1.0);
         GlStateManager.translate(-shapeWhip.offsetX, -shapeWhip.offsetY, -shapeWhip.offsetZ);
         GlStateManager.translate(-shapeWhip.rotationPointX * f5, -shapeWhip.rotationPointY * f5, -shapeWhip.rotationPointZ * f5);
         shapeWhip.render(f5);
         GlStateManager.popMatrix();
         if (f2 > 0.0F) {
            AbstractMobModel.returnlight();
         }

         this.shapeRings4.render(f5);
         this.shapeRings3.render(f5);
         this.shapeRings2.render(f5);
         this.shapeRings1.render(f5);
         this.shapeProtect.render(f5);
      }

      GlStateManager.enableCull();
      GlStateManager.disableBlend();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
