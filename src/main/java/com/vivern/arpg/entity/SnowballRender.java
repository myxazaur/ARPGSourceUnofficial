package com.vivern.arpg.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class SnowballRender implements IRenderFactory {
   public final Item IntegItem;

   public SnowballRender(Item InputItem) {
      this.IntegItem = InputItem;
   }

   public Render createRenderFor(RenderManager manager) {
      return new RenderSnowball(manager, this.IntegItem, Minecraft.getMinecraft().getRenderItem());
   }
}
