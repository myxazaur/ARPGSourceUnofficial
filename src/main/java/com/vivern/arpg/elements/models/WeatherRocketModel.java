package com.vivern.arpg.elements.models;

import com.vivern.arpg.renders.ModelRendererLimited;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class WeatherRocketModel extends ModelBase {
   public ModelRenderer shape1;
   public ModelRenderer shape2;

   public WeatherRocketModel() {
      this.textureWidth = 16;
      this.textureHeight = 16;
      this.shape1 = new ModelRendererLimited(this, 0, -16, true, false, false, false, false, false);
      this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape1.addBox(0.0F, -8.0F, -8.0F, 0, 16, 16, 0.01F);
      this.setRotateAngle(this.shape1, (float) (Math.PI / 4), 0.0F, 0.0F);
      this.shape2 = new ModelRendererLimited(this, 0, -16, true, false, false, false, false, false);
      this.shape2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shape2.addBox(0.0F, -8.0F, -8.0F, 0, 16, 16, 0.01F);
      this.setRotateAngle(this.shape2, (float) (Math.PI / 4), 0.0F, (float) (-Math.PI / 2));
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      GlStateManager.disableCull();
      this.shape2.render(f5);
      this.shape1.render(f5);
      GlStateManager.enableCull();
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
