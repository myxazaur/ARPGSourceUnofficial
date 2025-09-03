//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.entity.EntityGeomanticCrystal;
import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.NBTHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderGeomanticCrystal<T extends EntityGeomanticCrystal> extends Render<T> {
   public final RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();

   public RenderGeomanticCrystal(RenderManager renderManagerIn) {
      super(renderManagerIn);
   }

   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y + 0.1, (float)z);
      GlStateManager.enableRescaleNormal();
      GlStateManager.rotate(AnimationTimer.tick / 5.0F + entity.randomRotat, 0.0F, 1.0F, 0.0F);
      float size = Math.max(NBTHelper.GetNBTfloat(entity.stackIn, "size"), 15.0F) / 85.0F + 0.6F;
      GlStateManager.scale(size, size, size);
      this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
      if (this.renderOutlines) {
         GlStateManager.enableColorMaterial();
         GlStateManager.enableOutlineMode(this.getTeamColor(entity));
      }

      this.itemRenderer.renderItem(entity.stackIn, TransformType.GROUND);
      if (this.renderOutlines) {
         GlStateManager.disableOutlineMode();
         GlStateManager.disableColorMaterial();
      }

      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   protected ResourceLocation getEntityTexture(EntityGeomanticCrystal entity) {
      return TextureMap.LOCATION_BLOCKS_TEXTURE;
   }

   public static class GeomanticCrystalFactory implements IRenderFactory {
      public Render createRenderFor(RenderManager manager) {
         return new RenderGeomanticCrystal(manager);
      }
   }
}
