package com.vivern.arpg.renders.mobrender;

import com.vivern.arpg.elements.models.BlubberModel;
import com.vivern.arpg.mobs.Blubber;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderBlubber extends RenderLiving<Blubber> {
   private ResourceLocation mobTexture = new ResourceLocation("arpg:textures/blubber_model_tex.png");
   public static BlubberFactory FACTORY = new BlubberFactory();

   public RenderBlubber(RenderManager manager) {
      super(manager, new BlubberModel(), 0.3F);
   }

   @Nonnull
   protected ResourceLocation getEntityTexture(@Nonnull Blubber entity) {
      return this.mobTexture;
   }

   public static class BlubberFactory implements IRenderFactory<Blubber> {
      public Render<? super Blubber> createRenderFor(RenderManager manager) {
         return new RenderBlubber(manager);
      }
   }
}
