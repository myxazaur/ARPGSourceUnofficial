package com.Vivern.Arpg.renders.mobrender;

import com.Vivern.Arpg.elements.models.MoonshroomModel;
import com.Vivern.Arpg.mobs.Moonshroom;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMoonshroom extends RenderLiving<Moonshroom> {
   private ResourceLocation mobTexture = new ResourceLocation("arpg:textures/moonshroom_model_tex.png");
   public static MoonshroomFactory FACTORY = new MoonshroomFactory();

   public RenderMoonshroom(RenderManager manager) {
      super(manager, new MoonshroomModel(), 0.3F);
   }

   @Nonnull
   protected ResourceLocation getEntityTexture(@Nonnull Moonshroom entity) {
      return this.mobTexture;
   }

   public static class MoonshroomFactory implements IRenderFactory<Moonshroom> {
      public Render<? super Moonshroom> createRenderFor(RenderManager manager) {
         return new RenderMoonshroom(manager);
      }
   }
}
