package com.Vivern.Arpg.renders.mobrender;

import com.Vivern.Arpg.elements.models.AbstractMobModel;
import com.Vivern.Arpg.mobs.AbstractMob;
import javax.annotation.Nonnull;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderLivingEvent.Post;
import net.minecraftforge.client.event.RenderLivingEvent.Pre;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderRocketMob extends RenderLiving<EntityLiving> {
   private ResourceLocation mobTexture;
   public boolean useMultitex = false;
   public boolean vertical = true;
   public AbstractMob.RenderAbstractMobEntry entry;
   public boolean lightLayerMode = false;

   public RenderRocketMob(
      RenderManager manager,
      ModelBase model,
      ResourceLocation mobTexture,
      float shadowSize,
      boolean layerHeldItem,
      boolean layerArmor,
      boolean vertical,
      AbstractMob.RenderAbstractMobEntry entry
   ) {
      super(manager, model, shadowSize);
      this.mobTexture = mobTexture;
      this.useMultitex = mobTexture == null;
      this.vertical = vertical;
      this.entry = entry;
      if (layerHeldItem) {
         this.addLayer(new LayerHeldItem(this));
      }

      if (layerArmor) {
         this.addLayer(new LayerBipedArmor(this));
      }
   }

   public void doRender(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTicks) {
      if (!MinecraftForge.EVENT_BUS.post(new Pre(entity, this, partialTicks, x, y, z))) {
         GlStateManager.pushMatrix();
         GlStateManager.disableCull();
         this.mainModel.swingProgress = this.getSwingProgress(entity, partialTicks);
         boolean shouldSit = entity.isRiding() && entity.getRidingEntity() != null && entity.getRidingEntity().shouldRiderSit();
         this.mainModel.isRiding = shouldSit;
         this.mainModel.isChild = entity.isChild();

         try {
            float f = 0.0F;
            float f1 = 0.0F;
            float f2 = f1 - f;
            if (shouldSit && entity.getRidingEntity() instanceof EntityLivingBase) {
               EntityLivingBase entitylivingbase = (EntityLivingBase)entity.getRidingEntity();
               f = 0.0F;
               f2 = f1 - f;
               float f3 = MathHelper.wrapDegrees(f2);
               if (f3 < -85.0F) {
                  f3 = -85.0F;
               }

               if (f3 >= 85.0F) {
                  f3 = 85.0F;
               }

               f = f1 - f3;
               if (f3 * f3 > 2500.0F) {
                  f += f3 * 0.2F;
               }

               f2 = f1 - f;
            }

            float f7 = 0.0F;
            this.renderLivingAt(entity, x, y, z);
            float ehd2 = entity.height / 2.0F;
            if (this.vertical) {
               GlStateManager.translate(0.0F, ehd2, 0.0F);
            }

            GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
            float pitchr = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
            GlStateManager.rotate(pitchr, 0.0F, 0.0F, 1.0F);
            if (this.vertical) {
               GlStateManager.rotate(90.0F, 0.0F, 0.0F, -1.0F);
            } else {
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            }

            if (this.vertical) {
               GlStateManager.translate(0.0F, -ehd2, 0.0F);
            }

            float f8 = this.handleRotationFloat(entity, partialTicks);
            this.applyRotations(entity, f8, f, partialTicks);
            float f4 = this.prepareScale(entity, partialTicks);
            float f5 = 0.0F;
            float f6 = 0.0F;
            if (!entity.isRiding()) {
               f5 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialTicks;
               f6 = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);
               if (entity.isChild()) {
                  f6 *= 3.0F;
               }

               if (f5 > 1.0F) {
                  f5 = 1.0F;
               }

               f2 = f1 - f;
            }

            GlStateManager.enableAlpha();
            this.mainModel.setLivingAnimations(entity, f6, f5, partialTicks);
            this.mainModel.setRotationAngles(f6, f5, f8, f2, f7, f4, entity);
            if (this.renderOutlines) {
               boolean flag1 = this.setScoreTeamColor(entity);
               GlStateManager.enableColorMaterial();
               GlStateManager.enableOutlineMode(this.getTeamColor(entity));
               if (!this.renderMarker) {
                  this.renderModel(entity, f6, f5, f8, f2, f7, f4);
                  if (this.entry.lightLayer) {
                     this.lightLayerMode = true;
                     AbstractMobModel.light(240, false);
                     this.renderModel(entity, f6, f5, f8, f2, f7, f4);
                     AbstractMobModel.returnlight();
                     this.lightLayerMode = false;
                  }
               }

               this.renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, f4);
               GlStateManager.disableOutlineMode();
               GlStateManager.disableColorMaterial();
               if (flag1) {
                  this.unsetScoreTeamColor();
               }
            } else {
               boolean flag = this.setDoRenderBrightness(entity, partialTicks);
               this.renderModel(entity, f6, f5, f8, f2, f7, f4);
               if (this.entry.lightLayer) {
                  this.lightLayerMode = true;
                  AbstractMobModel.light(240, false);
                  this.renderModel(entity, f6, f5, f8, f2, f7, f4);
                  AbstractMobModel.returnlight();
                  this.lightLayerMode = false;
               }

               if (flag) {
                  this.unsetBrightness();
               }

               GlStateManager.depthMask(true);
               this.renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, f4);
            }

            GlStateManager.disableRescaleNormal();
         } catch (Exception var22) {
         }

         GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
         GlStateManager.enableTexture2D();
         GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
         GlStateManager.enableCull();
         GlStateManager.popMatrix();
         if (!this.renderOutlines) {
            this.renderName(entity, x, y, z);
         }

         MinecraftForge.EVENT_BUS.post(new Post(entity, this, partialTicks, x, y, z));
         if (!this.renderOutlines) {
            this.renderLeash(entity, x, y, z, entityYaw, partialTicks);
         }
      }
   }

   public static RocketMobFactory getFACTORY(
      ModelBase model, ResourceLocation mobTexture, float shadowSize, boolean vertical, AbstractMob.RenderAbstractMobEntry entry
   ) {
      return new RocketMobFactory(model, mobTexture, shadowSize, vertical, entry);
   }

   @Nonnull
   protected ResourceLocation getEntityTexture(@Nonnull EntityLiving entity) {
      if (this.lightLayerMode) {
         return this.entry.lightLayerTexture;
      } else {
         return this.useMultitex ? ((AbstractMob)entity).getMultitexture() : this.mobTexture;
      }
   }

   public static class RocketMobFactory implements IRenderFactory<EntityLiving> {
      private ModelBase model;
      private ResourceLocation mobTexture;
      private float shadowSize;
      private boolean layerHeldItem = false;
      private boolean layerArmor = false;
      boolean vertical;
      public AbstractMob.RenderAbstractMobEntry entry;

      public RocketMobFactory(ModelBase model, ResourceLocation mobTexture, float shadowSize, boolean vertical, AbstractMob.RenderAbstractMobEntry entry) {
         this.model = model;
         this.mobTexture = mobTexture;
         this.shadowSize = shadowSize;
         this.vertical = vertical;
         this.entry = entry;
      }

      public RocketMobFactory setLayerHeldItem(boolean layerHeldItem) {
         this.layerHeldItem = layerHeldItem;
         return this;
      }

      public RocketMobFactory setlayerArmor(boolean layerArmor) {
         this.layerArmor = layerArmor;
         return this;
      }

      public Render<? super EntityLiving> createRenderFor(RenderManager manager) {
         return new RenderRocketMob(manager, this.model, this.mobTexture, this.shadowSize, this.layerHeldItem, this.layerArmor, this.vertical, this.entry);
      }
   }
}
