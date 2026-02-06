package com.Vivern.Arpg.renders.mobrender;

import javax.annotation.Nonnull;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMobMultitexture extends RenderLiving<EntityLiving> {
   public RenderMobMultitexture(RenderManager manager, ModelBase model, float shadowSize, boolean layerHeldItem, boolean layerArmor) {
      super(manager, model, shadowSize);
      if (layerHeldItem) {
         this.addLayer(new LayerHeldItem(this));
      }

      if (layerArmor) {
         this.addLayer(new LayerBipedArmor(this));
      }
   }

   public static RenderMobMultitexFactory getFACTORY(ModelBase model, float shadowSize) {
      return new RenderMobMultitexFactory(model, shadowSize);
   }

   @Nonnull
   protected ResourceLocation getEntityTexture(@Nonnull EntityLiving entity) {
      return ((IMultitexture)entity).getMultitexture();
   }

   public static class RenderMobMultitexFactory implements IRenderFactory<EntityLiving> {
      private ModelBase model;
      private float shadowSize;
      private boolean layerHeldItem = false;
      private boolean layerArmor = false;

      public RenderMobMultitexFactory(ModelBase model, float shadowSize) {
         this.model = model;
         this.shadowSize = shadowSize;
      }

      public RenderMobMultitexFactory setLayerHeldItem(boolean layerHeldItem) {
         this.layerHeldItem = layerHeldItem;
         return this;
      }

      public RenderMobMultitexFactory setlayerArmor(boolean layerArmor) {
         this.layerArmor = layerArmor;
         return this;
      }

      public Render<? super EntityLiving> createRenderFor(RenderManager manager) {
         return new RenderMobMultitexture(manager, this.model, this.shadowSize, this.layerHeldItem, this.layerArmor);
      }
   }
}
