package com.Vivern.Arpg.renders.mobrender;

import com.Vivern.Arpg.elements.models.EnderSeerModel;
import com.Vivern.Arpg.mobs.EnderSeer;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderEnderSeer extends RenderLiving<EnderSeer> {
   private ResourceLocation mobTexture = new ResourceLocation("arpg:textures/ender_seer_model_tex.png");
   public static EnderSeerFactory FACTORY = new EnderSeerFactory();

   public RenderEnderSeer(RenderManager manager) {
      super(manager, new EnderSeerModel(), 0.3F);
   }

   @Nonnull
   protected ResourceLocation getEntityTexture(@Nonnull EnderSeer entity) {
      return this.mobTexture;
   }

   public static class EnderSeerFactory implements IRenderFactory<EnderSeer> {
      public Render<? super EnderSeer> createRenderFor(RenderManager manager) {
         return new RenderEnderSeer(manager);
      }
   }
}
