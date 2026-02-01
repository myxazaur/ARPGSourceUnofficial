package com.vivern.arpg.renders.mobrender;

import com.vivern.arpg.elements.models.TroglodyteModel;
import com.vivern.arpg.mobs.Troglodyte;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderTroglodyte extends RenderLiving<Troglodyte> {
   private ResourceLocation mobTexture = new ResourceLocation("arpg:textures/troglodyte_model_tex.png");
   public static TroglodyteFactory FACTORY = new TroglodyteFactory();

   public RenderTroglodyte(RenderManager manager) {
      super(manager, new TroglodyteModel(), 0.3F);
   }

   @Nonnull
   protected ResourceLocation getEntityTexture(@Nonnull Troglodyte entity) {
      return this.mobTexture;
   }

   public static class TroglodyteFactory implements IRenderFactory<Troglodyte> {
      public Render<? super Troglodyte> createRenderFor(RenderManager manager) {
         return new RenderTroglodyte(manager);
      }
   }
}
