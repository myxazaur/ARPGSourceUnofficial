package com.Vivern.Arpg.renders.mobrender;

import com.Vivern.Arpg.elements.models.SlimeModel;
import com.Vivern.Arpg.mobs.WhiteSlime;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderWhiteSlime extends RenderLiving<WhiteSlime> {
   private ResourceLocation mobTexture = new ResourceLocation("arpg:textures/white_slime_tex.png");
   public static WhiteSlimeFactory FACTORY = new WhiteSlimeFactory();

   public RenderWhiteSlime(RenderManager manager) {
      super(manager, new SlimeModel(), 0.4F);
   }

   @Nonnull
   protected ResourceLocation getEntityTexture(@Nonnull WhiteSlime entity) {
      return this.mobTexture;
   }

   public static class WhiteSlimeFactory implements IRenderFactory<WhiteSlime> {
      public Render<? super WhiteSlime> createRenderFor(RenderManager manager) {
         return new RenderWhiteSlime(manager);
      }
   }
}
