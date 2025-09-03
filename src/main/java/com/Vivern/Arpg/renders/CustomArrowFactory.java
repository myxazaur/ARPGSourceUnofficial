package com.Vivern.Arpg.renders;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class CustomArrowFactory implements IRenderFactory {
   public ResourceLocation tex;
   public float scale = 0.05625F;
   public int light = -1;
   public boolean tilt = true;
   public boolean horizontalShake = false;
   public boolean blend = false;

   public CustomArrowFactory(ResourceLocation tex) {
      this.tex = tex;
   }

   public CustomArrowFactory(ResourceLocation tex, float scale, int light) {
      this.tex = tex;
      this.scale = scale;
      this.light = light;
   }

   public CustomArrowFactory setTilt(boolean tilt) {
      this.tilt = tilt;
      return this;
   }

   public CustomArrowFactory setHorizontalShake(boolean horizontalShake) {
      this.horizontalShake = horizontalShake;
      return this;
   }

   public CustomArrowFactory setBlend(boolean blend) {
      this.blend = blend;
      return this;
   }

   public Render createRenderFor(RenderManager manager) {
      return new RenderCustomArrow(manager, this.tex, this.scale, this.light, this.tilt, this.horizontalShake, this.blend);
   }
}
