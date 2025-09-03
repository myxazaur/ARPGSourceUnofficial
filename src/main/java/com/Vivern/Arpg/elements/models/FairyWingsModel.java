//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class FairyWingsModel extends ModelBase {
   public ModelRenderer shape1a;
   public ModelRenderer shape1b;

   public FairyWingsModel() {
      this.textureWidth = 22;
      this.textureHeight = 18;
      this.shape1b = new ModelRenderer(this, 12, 0);
      this.shape1b.setRotationPoint(1.5F, 3.5F, 2.0F);
      this.shape1b.addBox(-1.5F, 0.0F, -0.5F, 4, 16, 1, 0.0F);
      this.setRotateAngle(this.shape1b, 0.18203785F, 0.045553092F, -0.091106184F);
      this.shape1a = new ModelRenderer(this, 0, 0);
      this.shape1a.setRotationPoint(1.5F, 1.7F, 2.0F);
      this.shape1a.addBox(-1.5F, 0.0F, -0.5F, 5, 17, 1, 0.0F);
      this.setRotateAngle(this.shape1a, 0.22759093F, 0.18203785F, -0.31869712F);
   }

   public void render(Entity entity, float sweep, float flapnormal, float ticksforward, float nofly, float sneaking, float f5) {
      float fly = 1.0F - nofly;
      float flapforw1 = (float)Math.sin(ticksforward * 0.4F) * ticksforward;
      float flapforw2 = (float)Math.sin(ticksforward * 0.4F);
      this.shape1a.rotateAngleZ = -0.31869712F - sweep / 1.5F - fly + nofly / 5.0F;
      this.shape1a.rotateAngleY = 0.18203785F - sweep / 3.0F + flapforw2;
      this.shape1a.rotateAngleX = 0.22759093F - sweep / 3.0F + fly / 2.0F - flapnormal / 3.5F - flapforw1 / 5.0F + nofly / 3.5F;
      this.shape1a.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.scale(-1.0F, 1.0F, 1.0F);
      this.shape1a.render(f5);
      GlStateManager.popMatrix();
      this.shape1b.rotateAngleZ = 0.011106187F - sweep / 1.7F - fly + flapforw1 / 6.0F;
      this.shape1b.rotateAngleY = 0.045553092F - sweep / 3.0F - flapnormal / 5.0F + flapforw2 / 2.0F;
      this.shape1b.rotateAngleX = 0.18203785F - sweep / 3.0F + fly / 3.0F - flapnormal / 4.0F + flapforw2 / 2.0F + nofly / 2.5F;
      this.shape1b.render(f5);
      GlStateManager.pushMatrix();
      GlStateManager.scale(-1.0F, 1.0F, 1.0F);
      this.shape1b.render(f5);
      GlStateManager.popMatrix();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
