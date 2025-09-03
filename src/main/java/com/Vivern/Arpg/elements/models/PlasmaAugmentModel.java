//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.renders.ModelRendererGlow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class PlasmaAugmentModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer manA;
   public ModelRenderer shape2;
   public ModelRenderer manB;
   public ModelRenderer manC;
   public ModelRenderer manD;
   public ModelRenderer tool;
   public ModelRenderer tip;
   public ModelRenderer wire1;
   public ModelRenderer wire2;
   public ModelRenderer plasma1;
   public ModelRenderer plasma2;

   public PlasmaAugmentModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.shape1 = new ModelRenderer(this, 0, 34);
      this.shape1.setRotationPoint(0.0F, 10.0F, 0.0F);
      this.shape1.addBox(-8.0F, 0.0F, -8.0F, 16, 14, 16, -0.01F);
      this.plasma1 = new ModelRendererGlow(this, 0, 0, 240, false);
      this.plasma1.setRotationPoint(0.0F, 4.0F, 0.0F);
      this.plasma1.addBox(-1.5F, 0.0F, 0.0F, 3, 4, 0, 0.0F);
      this.setRotateAngle(this.plasma1, 0.0F, (float) (Math.PI / 4), 0.0F);
      this.manD = new ModelRenderer(this, 0, 8);
      this.manD.setRotationPoint(0.0F, -2.0F, -7.0F);
      this.manD.addBox(-2.0F, -1.0F, -2.0F, 4, 4, 4, 0.0F);
      this.tip = new ModelRendererGlow(this, 17, 0, 60, true);
      this.tip.setRotationPoint(0.0F, 4.0F, 0.0F);
      this.tip.addBox(-1.5F, -7.0F, -1.5F, 3, 2, 3, 0.0F);
      this.shape2 = new ModelRenderer(this, 0, 16);
      this.shape2.setRotationPoint(0.0F, 10.0F, 0.0F);
      this.shape2.addBox(-6.0F, 0.0F, -6.0F, 12, 4, 11, -0.01F);
      this.wire1 = new ModelRendererGlow(this, 9, 1, 60, true);
      this.wire1.setRotationPoint(0.0F, 3.0F, 0.0F);
      this.wire1.addBox(-0.5F, -5.5F, 1.5F, 1, 1, 5, 0.0F);
      this.tool = new ModelRenderer(this, 17, 11);
      this.tool.setRotationPoint(0.0F, 4.0F, 0.0F);
      this.tool.addBox(-1.5F, -1.0F, -1.5F, 3, 1, 3, 0.0F);
      this.wire2 = new ModelRendererGlow(this, 9, 2, 60, true);
      this.wire2.setRotationPoint(0.0F, 3.0F, 0.0F);
      this.wire2.addBox(-0.5F, -4.5F, 5.5F, 1, 2, 1, 0.0F);
      this.manC = new ModelRenderer(this, 36, 8);
      this.manC.setRotationPoint(0.0F, -2.0F, -4.0F);
      this.manC.addBox(-1.0F, -2.0F, -5.0F, 2, 2, 6, 0.0F);
      this.setRotateAngle(this.manC, 0.0F, (float) -Math.PI, 0.0F);
      this.plasma2 = new ModelRendererGlow(this, 0, 0, 240, false);
      this.plasma2.setRotationPoint(0.0F, 4.0F, 0.0F);
      this.plasma2.addBox(-1.5F, 0.0F, 0.0F, 3, 4, 0, 0.0F);
      this.setRotateAngle(this.plasma2, 0.0F, (float) (-Math.PI / 4), 0.0F);
      this.manB = new ModelRenderer(this, 35, 0);
      this.manB.setRotationPoint(0.0F, -2.0F, 7.0F);
      this.manB.addBox(-1.5F, -2.0F, -5.0F, 3, 1, 6, 0.0F);
      this.setRotateAngle(this.manB, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.manA = new ModelRenderer(this, 36, 17);
      this.manA.setRotationPoint(0.0F, 10.0F, -0.03F);
      this.manA.addBox(-2.0F, -3.0F, 5.0F, 4, 5, 4, 0.0F);
      this.manD.addChild(this.plasma1);
      this.manC.addChild(this.manD);
      this.manD.addChild(this.tip);
      this.manD.addChild(this.wire1);
      this.manD.addChild(this.tool);
      this.manD.addChild(this.wire2);
      this.manB.addChild(this.manC);
      this.manD.addChild(this.plasma2);
      this.manA.addChild(this.manB);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (Minecraft.getMinecraft().gameSettings.particleSetting == 2) {
         this.plasma1.isHidden = f < 1.0F;
         this.plasma2.isHidden = this.plasma1.isHidden;
         this.shape2.render(f5);
         this.shape1.render(f5);
         GlStateManager.enableBlend();
         this.manA.render(f5);
         GlStateManager.disableBlend();
      } else {
         this.plasma1.isHidden = true;
         this.plasma2.isHidden = true;
         this.shape2.render(f5);
         this.shape1.render(f5);
         this.manA.render(f5);
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
