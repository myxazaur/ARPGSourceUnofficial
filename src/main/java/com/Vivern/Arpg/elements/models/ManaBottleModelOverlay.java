//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.models;

import com.Vivern.Arpg.renders.ModelRendererLimited;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ManaBottleModelOverlay extends ModelBase {
   public ModelRenderer shapeA1;
   public ModelRenderer shapeAcap;
   public ModelRenderer shapeA3;
   public ModelRenderer shapeAshaft;
   public ModelRenderer shapeB1;
   public ModelRenderer shapeB3;
   public ModelRenderer shapeBcap;
   public ModelRenderer shapeB4x;
   public ModelRenderer shapeB4y;
   public ModelRenderer shapeB4z;
   public ModelRenderer shapeB4w;
   public ModelRenderer shapeC1;
   public ModelRenderer shapeC2;
   public ModelRenderer shapeC3;
   public ModelRenderer shapeC5;
   public ModelRenderer shapeCcap;
   public ModelRenderer shapeC4x;
   public ModelRenderer shapeC4y;
   public ModelRenderer shapeC4z;
   public ModelRenderer shapeC4w;
   public ModelRenderer shapeBshaft;
   public ModelRenderer shapeCshaft;

   public ManaBottleModelOverlay() {
      this.textureWidth = 64;
      this.textureHeight = 32;
      this.shapeA1 = new ModelRendererLimited(this, 0, -10, true, true, false, false, true, true);
      this.shapeA1.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.shapeA1.addBox(-5.0F, 4.0F, -5.0F, 10, 4, 10, 0.1F);
      this.shapeB4x = new ModelRendererLimited(this, 40, 0, true, true, false, false, true, true);
      this.shapeB4x.setRotationPoint(16.0F, 3.0F, 0.0F);
      this.shapeB4x.addBox(1.5F, 0.0F, 0.0F, 5, 10, 0, 0.0F);
      this.shapeBcap = new ModelRendererLimited(this, 0, 4, true, true, false, false, true, true);
      this.shapeBcap.setRotationPoint(16.0F, -2.0F, 0.0F);
      this.shapeBcap.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.1F);
      this.shapeB4w = new ModelRendererLimited(this, 40, 0, true, true, false, false, true, true);
      this.shapeB4w.mirror = true;
      this.shapeB4w.setRotationPoint(16.0F, 3.0F, 0.0F);
      this.shapeB4w.addBox(-6.5F, 0.0F, 0.0F, 5, 10, 0, 0.0F);
      this.setRotateAngle(this.shapeB4w, 0.0F, (float) (-Math.PI / 2), 0.0F);
      this.shapeAshaft = new ModelRendererLimited(this, 12, 2, true, true, false, false, true, true);
      this.shapeAshaft.setRotationPoint(0.0F, 8.0F, 0.0F);
      this.shapeAshaft.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, -0.1F);
      this.shapeB3 = new ModelRendererLimited(this, 0, 4, true, true, false, false, true, true);
      this.shapeB3.setRotationPoint(16.0F, 4.0F, 0.0F);
      this.shapeB3.addBox(-1.5F, 0.0F, -1.5F, 3, 1, 3, 0.1F);
      this.shapeC5 = new ModelRendererLimited(this, 52, -3, true, true, false, false, true, true);
      this.shapeC5.setRotationPoint(32.0F, -3.0F, 0.0F);
      this.shapeC5.addBox(-1.5F, 0.0F, -1.5F, 3, 7, 3, 0.1F);
      this.shapeAcap = new ModelRendererLimited(this, 0, 1, true, true, false, false, true, true);
      this.shapeAcap.setRotationPoint(0.0F, 5.0F, 0.0F);
      this.shapeAcap.addBox(-1.5F, 0.0F, -1.5F, 3, 1, 3, 0.1F);
      this.shapeA3 = new ModelRendererLimited(this, 0, 1, true, true, false, false, true, true);
      this.shapeA3.setRotationPoint(0.0F, 8.0F, 0.0F);
      this.shapeA3.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.1F);
      this.shapeC1 = new ModelRendererLimited(this, 0, 2, true, true, false, false, true, true);
      this.shapeC1.setRotationPoint(32.0F, 16.0F, 0.0F);
      this.shapeC1.addBox(-6.0F, -4.0F, -6.0F, 12, 9, 12, 0.1F);
      this.shapeCcap = new ModelRenderer(this, 44, 26);
      this.shapeCcap.setRotationPoint(32.0F, -5.0F, 0.0F);
      this.shapeCcap.addBox(-2.5F, 0.0F, -2.5F, 5, 1, 5, 0.1F);
      this.shapeCshaft = new ModelRendererLimited(this, 12, 2, true, true, false, false, true, true);
      this.shapeCshaft.setRotationPoint(32.0F, -3.0F, 0.0F);
      this.shapeCshaft.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, -0.1F);
      this.shapeC4x = new ModelRendererLimited(this, 52, 9, true, true, false, false, true, true);
      this.shapeC4x.setRotationPoint(32.0F, -5.0F, 0.0F);
      this.shapeC4x.addBox(2.0F, 0.0F, 0.0F, 6, 17, 0, 0.0F);
      this.shapeC4w = new ModelRendererLimited(this, 52, 9, true, true, false, false, true, true);
      this.shapeC4w.mirror = true;
      this.shapeC4w.setRotationPoint(32.0F, -5.0F, 0.0F);
      this.shapeC4w.addBox(-8.0F, 0.0F, 0.0F, 6, 17, 0, 0.0F);
      this.setRotateAngle(this.shapeC4w, 0.0F, (float) (-Math.PI / 2), 0.0F);
      this.shapeC4z = new ModelRendererLimited(this, 52, 9, true, true, false, false, true, true);
      this.shapeC4z.setRotationPoint(32.0F, -5.0F, 0.0F);
      this.shapeC4z.addBox(2.0F, 0.0F, 0.0F, 6, 17, 0, 0.0F);
      this.setRotateAngle(this.shapeC4z, 0.0F, (float) (-Math.PI / 2), 0.0F);
      this.shapeB1 = new ModelRendererLimited(this, 0, 12, true, true, false, false, true, true);
      this.shapeB1.setRotationPoint(16.0F, 18.0F, 0.0F);
      this.shapeB1.addBox(-5.5F, -3.0F, -5.5F, 11, 9, 11, 0.1F);
      this.shapeB4z = new ModelRendererLimited(this, 40, 0, true, true, false, false, true, true);
      this.shapeB4z.setRotationPoint(16.0F, 3.0F, 0.0F);
      this.shapeB4z.addBox(1.5F, 0.0F, 0.0F, 5, 10, 0, 0.0F);
      this.setRotateAngle(this.shapeB4z, 0.0F, (float) (-Math.PI / 2), 0.0F);
      this.shapeC2 = new ModelRendererLimited(this, 0, 4, true, true, false, false, true, true);
      this.shapeC2.setRotationPoint(32.0F, 21.0F, 0.0F);
      this.shapeC2.addBox(-4.0F, 0.0F, -4.0F, 8, 2, 8, 0.1F);
      this.shapeC4y = new ModelRendererLimited(this, 52, 9, true, true, false, false, true, true);
      this.shapeC4y.mirror = true;
      this.shapeC4y.setRotationPoint(32.0F, -5.0F, 0.0F);
      this.shapeC4y.addBox(-8.0F, 0.0F, 0.0F, 6, 17, 0, 0.0F);
      this.shapeBshaft = new ModelRendererLimited(this, 12, 2, true, true, false, false, true, true);
      this.shapeBshaft.setRotationPoint(16.0F, 1.0F, 0.0F);
      this.shapeBshaft.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, -0.1F);
      this.shapeB4y = new ModelRendererLimited(this, 40, 0, true, true, false, false, true, true);
      this.shapeB4y.mirror = true;
      this.shapeB4y.setRotationPoint(16.0F, 3.0F, 0.0F);
      this.shapeB4y.addBox(-6.5F, 0.0F, 0.0F, 5, 10, 0, 0.0F);
      this.shapeC3 = new ModelRendererLimited(this, 0, 1, true, true, false, false, true, true);
      this.shapeC3.setRotationPoint(32.0F, 23.0F, 0.0F);
      this.shapeC3.addBox(-5.0F, 0.0F, -5.0F, 10, 1, 10, 0.1F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      if (f == 0.0F) {
         this.shapeA1.render(f5);
         this.shapeAcap.offsetY = f1;
         this.shapeAcap.render(f5);
         this.shapeA3.render(f5);
         this.shapeAshaft.offsetY = f1;
         this.shapeAshaft.render(f5);
      }

      if (f == 1.0F) {
         this.shapeB4x.render(f5);
         this.shapeBcap.offsetY = f1;
         this.shapeBcap.render(f5);
         this.shapeB4w.render(f5);
         this.shapeB3.render(f5);
         this.shapeB1.render(f5);
         this.shapeB4z.render(f5);
         this.shapeBshaft.offsetY = f1;
         this.shapeBshaft.render(f5);
         this.shapeB4y.render(f5);
      }

      if (f == 2.0F) {
         this.shapeC5.render(f5);
         this.shapeC1.render(f5);
         this.shapeCcap.offsetY = f1;
         this.shapeCcap.render(f5);
         this.shapeCshaft.offsetY = f1;
         this.shapeCshaft.render(f5);
         this.shapeC4x.render(f5);
         this.shapeC4w.render(f5);
         this.shapeC4z.render(f5);
         this.shapeC2.render(f5);
         this.shapeC4y.render(f5);
         this.shapeC3.render(f5);
      }
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
