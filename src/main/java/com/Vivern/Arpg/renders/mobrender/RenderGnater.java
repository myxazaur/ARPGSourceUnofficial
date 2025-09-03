package com.Vivern.Arpg.renders.mobrender;

import com.Vivern.Arpg.elements.models.GnaterModel;
import com.Vivern.Arpg.mobs.Gnater;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderGnater extends RenderLiving<Gnater> {
   private ResourceLocation mobTexture = new ResourceLocation("arpg:textures/gnater_model_tex.png");
   public static GnaterFactory FACTORY = new GnaterFactory();

   public RenderGnater(RenderManager manager) {
      super(manager, new GnaterModel(), 0.3F);
   }

   @Nonnull
   protected ResourceLocation getEntityTexture(@Nonnull Gnater entity) {
      return this.mobTexture;
   }

   public static class GnaterFactory implements IRenderFactory<Gnater> {
      public Render<? super Gnater> createRenderFor(RenderManager manager) {
         return new RenderGnater(manager);
      }
   }
}
