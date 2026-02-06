package com.Vivern.Arpg.renders.mobrender;

import com.Vivern.Arpg.elements.models.AbstractMobModel;
import com.Vivern.Arpg.mobs.AbstractMob;
import com.Vivern.Arpg.renders.IRender;
import com.Vivern.Arpg.renders.LayerRandomItem;
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

public class RenderUniversal extends RenderLiving<EntityLiving> {
   private ResourceLocation mobTexture;
   public boolean noinverseLayers = true;
   public boolean useIRender;
   public AbstractMob.RenderAbstractMobEntry entry;
   public boolean lightLayerMode = false;

   public void doRenderInvertedLayers(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTicks) {
      if (!MinecraftForge.EVENT_BUS.post(new Pre(entity, this, partialTicks, x, y, z))) {
         GlStateManager.pushMatrix();
         GlStateManager.disableCull();
         this.mainModel.swingProgress = this.getSwingProgress(entity, partialTicks);
         boolean shouldSit = entity.isRiding() && entity.getRidingEntity() != null && entity.getRidingEntity().shouldRiderSit();
         this.mainModel.isRiding = shouldSit;
         this.mainModel.isChild = entity.isChild();

         try {
            float f = this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
            float f1 = this.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
            float f2 = f1 - f;
            if (shouldSit && entity.getRidingEntity() instanceof EntityLivingBase) {
               EntityLivingBase entitylivingbase = (EntityLivingBase)entity.getRidingEntity();
               f = this.interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
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

            float f7 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
            this.renderLivingAt(entity, x, y, z);
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
               this.renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, f4);
               if (!this.renderMarker) {
                  this.renderModel(entity, f6, f5, f8, f2, f7, f4);
               }

               GlStateManager.disableOutlineMode();
               GlStateManager.disableColorMaterial();
               if (flag1) {
                  this.unsetScoreTeamColor();
               }
            } else {
               this.renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, f4);
               boolean flag = this.setDoRenderBrightness(entity, partialTicks);
               this.renderModel(entity, f6, f5, f8, f2, f7, f4);
               if (flag) {
                  this.unsetBrightness();
               }

               GlStateManager.depthMask(true);
            }

            GlStateManager.disableRescaleNormal();
         } catch (Exception var20) {
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
      }
   }

   public RenderUniversal(
      RenderManager manager,
      ModelBase model,
      ResourceLocation mobTexture,
      float shadowSize,
      boolean layerHeldItem,
      boolean layerArmor,
      LayerRandomItem layerrandItem,
      boolean useIRender,
      AbstractMob.RenderAbstractMobEntry entry
   ) {
      super(manager, model, shadowSize);
      this.mobTexture = mobTexture;
      this.useIRender = useIRender;
      this.entry = entry;
      if (layerHeldItem) {
         this.addLayer(new LayerHeldItem(this));
      }

      if (layerArmor) {
         this.addLayer(new LayerBipedArmor(this));
      }

      if (layerrandItem != null) {
         layerrandItem.renderer = this;
         this.noinverseLayers = !layerrandItem.inverseLayers;
         this.addLayer(layerrandItem);
      }
   }

   public void doRender(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTicks) {
      if (this.noinverseLayers) {
         super.doRender(entity, x, y, z, entityYaw, partialTicks);
         if (this.entry.lightLayer) {
            this.lightLayerMode = true;
            AbstractMobModel.light(240, false);
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
            AbstractMobModel.returnlight();
            this.lightLayerMode = false;
         }

         if (this.useIRender) {
            ((IRender)entity).renderPost(entity, x, y, z, entityYaw, partialTicks);
         }
      } else {
         this.doRenderInvertedLayers(entity, x, y, z, entityYaw, partialTicks);
         if (this.entry.lightLayer) {
            this.lightLayerMode = true;
            AbstractMobModel.light(240, false);
            this.doRenderInvertedLayers(entity, x, y, z, entityYaw, partialTicks);
            AbstractMobModel.returnlight();
            this.lightLayerMode = false;
         }

         if (this.useIRender) {
            ((IRender)entity).renderPost(entity, x, y, z, entityYaw, partialTicks);
         }

         if (!this.renderOutlines) {
            this.renderLeash(entity, x, y, z, entityYaw, partialTicks);
         }
      }
   }

   public static UniversalFactory getFACTORY(
      ModelBase model, ResourceLocation mobTexture, float shadowSize, boolean useIRender, AbstractMob.RenderAbstractMobEntry entry
   ) {
      return new UniversalFactory(model, mobTexture, shadowSize, useIRender, entry);
   }

   @Nonnull
   protected ResourceLocation getEntityTexture(@Nonnull EntityLiving entity) {
      return this.lightLayerMode ? this.entry.lightLayerTexture : this.mobTexture;
   }

   public static class UniversalFactory implements IRenderFactory<EntityLiving> {
      private ModelBase model;
      private ResourceLocation mobTexture;
      private float shadowSize;
      private boolean layerHeldItem = false;
      private boolean layerArmor = false;
      LayerRandomItem layerrandItem;
      private boolean useIRender = false;
      public AbstractMob.RenderAbstractMobEntry entry;

      public UniversalFactory(ModelBase model, ResourceLocation mobTexture, float shadowSize, boolean useIRender, AbstractMob.RenderAbstractMobEntry entry) {
         this.model = model;
         this.mobTexture = mobTexture;
         this.shadowSize = shadowSize;
         this.useIRender = useIRender;
         this.entry = entry;
      }

      public UniversalFactory setLayerHeldItem(boolean layerHeldItem) {
         this.layerHeldItem = layerHeldItem;
         return this;
      }

      public UniversalFactory setlayerArmor(boolean layerArmor) {
         this.layerArmor = layerArmor;
         return this;
      }

      public UniversalFactory setLayerRandomItem(LayerRandomItem layerrandItem) {
         this.layerrandItem = layerrandItem;
         return this;
      }

      public Render<? super EntityLiving> createRenderFor(RenderManager manager) {
         return new RenderUniversal(
            manager, this.model, this.mobTexture, this.shadowSize, this.layerHeldItem, this.layerArmor, this.layerrandItem, this.useIRender, this.entry
         );
      }
   }
}
