package com.vivern.arpg.renders.mobrender;

import com.vivern.arpg.elements.models.SummonedBlazeModel;
import com.vivern.arpg.mobs.SummonedBlaze;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderSummonedBlaze extends RenderLiving<SummonedBlaze> {
   private ResourceLocation mobTexture = new ResourceLocation("arpg:textures/summoned_blaze_model_tex.png");
   public static SummonedBlazeFactory FACTORY = new SummonedBlazeFactory();

   public RenderSummonedBlaze(RenderManager manager) {
      super(manager, new SummonedBlazeModel(), 0.5F);
   }

   @Nonnull
   protected ResourceLocation getEntityTexture(@Nonnull SummonedBlaze entity) {
      return this.mobTexture;
   }

   public static class SummonedBlazeFactory implements IRenderFactory<SummonedBlaze> {
      public Render<? super SummonedBlaze> createRenderFor(RenderManager manager) {
         return new RenderSummonedBlaze(manager);
      }
   }
}
