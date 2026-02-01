package com.vivern.arpg.renders;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class SpearRenderFactory implements IRenderFactory {
   public final Item IntegItem;
   public final float scale;
   public final boolean light;

   public SpearRenderFactory(Item InputItem, float scale, boolean light) {
      this.IntegItem = InputItem;
      this.scale = scale;
      this.light = light;
   }

   public Render createRenderFor(RenderManager manager) {
      return new RenderSpear(manager, this.IntegItem, this.scale, this.light);
   }
}
