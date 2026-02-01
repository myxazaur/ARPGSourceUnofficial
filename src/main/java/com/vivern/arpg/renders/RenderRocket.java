package com.vivern.arpg.renders;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderRocket<T extends Entity, ModelT extends ModelBase> extends Render<T> {
   Class myClass;
   ModelT MyModel;
   float scale = 1.0F;
   boolean opaq;
   float melting;
   public boolean checkAdvanced;
   protected final ResourceLocation texture;

   public static <ModelT> ModelT createInstance(Class ClassName) throws Exception {
      return (ModelT)ClassName.newInstance();
   }

   public RenderRocket(RenderManager renderManagerIn, ResourceLocation texture, Class myClass, float scale, boolean opaq, float melting, boolean checkAdvanced) {
      super(renderManagerIn);
      this.texture = texture;
      this.scale = scale;
      this.opaq = opaq;
      this.myClass = myClass;
      this.melting = melting;
      this.checkAdvanced = checkAdvanced;

      try {
         this.MyModel = createInstance(this.myClass);
      } catch (Exception var9) {
         System.out.println(var9);
      }
   }

   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
      GlStateManager.pushMatrix();
      GlStateManager.disableCull();
      GlStateManager.translate((float)x, (float)y + entity.getYOffset(), (float)z);
      GlStateManager.enableRescaleNormal();
      GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
      if (this.opaq) {
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
      }

      GlStateManager.rotate(90.0F, 0.0F, -1.0F, 0.0F);
      this.bindTexture(this.texture);
      float ang = entity.ticksExisted;
      if (this.renderOutlines) {
         GlStateManager.enableColorMaterial();
         GlStateManager.enableOutlineMode(this.getTeamColor(entity));
      }

      boolean saveinstanceof = false;
      if (this.checkAdvanced && entity instanceof IRenderOptions) {
         saveinstanceof = true;
         ((IRenderOptions)entity).doOptions();
      }

      GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      float melts = 1.0F - entity.ticksExisted * this.melting;
      GlStateManager.scale(melts, melts, melts);
      this.MyModel.render(entity, 0.0F, 0.0F, 0.0F, 1.0F, ang / 3.0F * partialTicks, 0.05F * this.scale);
      if (this.renderOutlines) {
         GlStateManager.disableOutlineMode();
         GlStateManager.disableColorMaterial();
      }

      if (this.opaq) {
         GL11.glDisable(3042);
      }

      if (saveinstanceof) {
         ((IRenderOptions)entity).undoOptions();
      }

      GlStateManager.popMatrix();
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   protected ResourceLocation getEntityTexture(Entity entity) {
      return this.texture;
   }
}
