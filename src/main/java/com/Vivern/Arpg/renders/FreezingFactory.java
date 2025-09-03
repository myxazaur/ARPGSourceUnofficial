package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.elements.models.PhoenixGhostCapeModel;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class FreezingFactory implements IRenderFactory {
   private final PhoenixGhostCapeModel elfocus = new PhoenixGhostCapeModel();

   public Render createRenderFor(RenderManager manager) {
      return new RenderFreezing(manager, this.elfocus);
   }
}
