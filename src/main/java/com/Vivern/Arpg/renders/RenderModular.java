//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import java.util.ArrayList;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderModular<T extends Entity> extends Render<T> {
   public RenderModularFactory factory;

   public RenderModular(RenderManager renderManagerIn, RenderModularFactory factory) {
      super(renderManagerIn);
      this.factory = factory;
   }

   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
      if (entity.ticksExisted >= this.factory.renderStartTick) {
         GlStateManager.pushMatrix();
         GlStateManager.enableRescaleNormal();
         this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
         if (entity instanceof RenderModule.IRenderModuleOverride) {
            RenderModule.IRenderModuleOverride renderModuleOverride = (RenderModule.IRenderModuleOverride)entity;

            for (RenderModule module : this.factory.modules) {
               renderModuleOverride.rewriteModuleParameters(module);
               module.render(entity, x, y, z, entityYaw, partialTicks);
            }
         } else {
            for (RenderModule module : this.factory.modules) {
               module.render(entity, x, y, z, entityYaw, partialTicks);
            }
         }

         GlStateManager.disableRescaleNormal();
         GlStateManager.popMatrix();
         super.doRender(entity, x, y, z, entityYaw, partialTicks);
      }
   }

   protected ResourceLocation getEntityTexture(Entity entity) {
      return TextureMap.LOCATION_BLOCKS_TEXTURE;
   }

   public static class RenderModularFactory implements IRenderFactory {
      public ArrayList<RenderModule> modules = new ArrayList<>();
      public int renderStartTick = 0;

      public RenderModularFactory setDelayedRender(int renderStartTick) {
         this.renderStartTick = renderStartTick;
         return this;
      }

      public RenderModularFactory add(RenderModule module) {
         this.modules.add(module);
         return this;
      }

      public Render createRenderFor(RenderManager manager) {
         return new RenderModular(manager, this);
      }
   }
}
