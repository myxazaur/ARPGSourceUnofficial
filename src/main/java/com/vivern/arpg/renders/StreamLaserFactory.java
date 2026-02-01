package com.vivern.arpg.renders;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class StreamLaserFactory implements IRenderFactory {
   public Render createRenderFor(RenderManager manager) {
      return new StreamLaserRender(manager);
   }
}
