package com.vivern.arpg.renders.mobrender;

import com.vivern.arpg.elements.models.ModelsEverfrostMob;
import com.vivern.arpg.mobs.BossWinterFury;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderWinterFury extends RenderLiving<BossWinterFury> {
   public ResourceLocation mobTexture1 = new ResourceLocation("arpg:textures/winter_fury_model_tex1.png");
   public ResourceLocation mobTexture2 = new ResourceLocation("arpg:textures/winter_fury_model_tex2.png");
   public static RenderWinterFuryFactory FACTORY = new RenderWinterFuryFactory();

   public RenderWinterFury(RenderManager manager) {
      super(manager, new ModelsEverfrostMob.WinterFuryModel(), 1.0F);
   }

   @Nonnull
   protected ResourceLocation getEntityTexture(@Nonnull BossWinterFury entity) {
      return entity.noClip ? this.mobTexture2 : this.mobTexture1;
   }

   public static class RenderWinterFuryFactory implements IRenderFactory<BossWinterFury> {
      public Render<? super BossWinterFury> createRenderFor(RenderManager manager) {
         return new RenderWinterFury(manager);
      }
   }
}
