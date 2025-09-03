package com.Vivern.Arpg.renders;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderBoomerangFactory implements IRenderFactory {
   public final Item IntegItem;

   public RenderBoomerangFactory(Item InputItem) {
      this.IntegItem = InputItem;
   }

   public Render createRenderFor(RenderManager manager) {
      return new RenderBoomerang(manager, this.IntegItem);
   }
}
