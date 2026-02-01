package com.vivern.arpg.renders;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBossLoot extends RenderEntityItem {
   public RenderBossLoot(RenderManager renderManagerIn, RenderItem p_i46167_2_) {
      super(renderManagerIn, p_i46167_2_);
   }

   public static class BossLootFactory implements IRenderFactory {
      public Render createRenderFor(RenderManager manager) {
         return new RenderBossLoot(manager, Minecraft.getMinecraft().getRenderItem());
      }
   }
}
