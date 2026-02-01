package com.vivern.arpg.renders;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class GUNPFactory implements IRenderFactory {
   public Render createRenderFor(RenderManager manager) {
      return new GUNPRender(manager);
   }
}
