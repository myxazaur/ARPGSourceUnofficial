package com.Vivern.Arpg.renders;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class ShardRenderFactory implements IRenderFactory {
   public Render createRenderFor(RenderManager manager) {
      return new RenderShard(manager);
   }
}
