package com.vivern.arpg.potions;

import com.vivern.arpg.main.Booom;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.mobs.AbstractMob;
import com.vivern.arpg.renders.LayerIce;
import com.vivern.arpg.renders.PotionBurningEffects;
import gloomyfolkenvivern.arpghooklib.example.AnnotationHooks;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionAddedEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Freezing extends AdvancedPotion {
   public static final ResourceLocation cold = new ResourceLocation("arpg:textures/cold.png");
   public static final ResourceLocation textur = new ResourceLocation("arpg:textures/freezing.png");
   public static PotionEffect forTestSound = new PotionEffect(PotionEffects.FREEZING);

   protected Freezing(boolean isBadEffectIn, int liquidColorIn, int index) {
      super(isBadEffectIn, liquidColorIn, index, true);
      this.setRegistryName("arpg:freezing");
      this.setPotionName("Freezing");
      this.setIconIndex(2, 1);
      this.shouldRender = true;
   }

   @Override
   public boolean isPotionApplicable(EntityLivingBase entityOnEffect, PotionEffect effect) {
      return !entityOnEffect.isPotionActive(PotionEffects.FREEZE_IMMUNITY);
   }

   public static boolean canImmobilizeEntity(EntityLivingBase entity, @Nullable PotionEffect effect) {
      if (effect == null || effect.getDuration() <= 1) {
         return false;
      } else if (entity instanceof AbstractMob) {
         return ((AbstractMob)entity).canFreezingImmobilizeMob(effect);
      } else if (entity instanceof EntityPlayer) {
         int effectPower = effect.getAmplifier() + 1;
         float entityPower = entity.getMaxHealth() * 0.05F + 1.25F + (float)entity.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue();
         return effectPower >= entityPower;
      } else {
         int effectPower = effect.getAmplifier() + 1;
         float entityPower = entity.getMaxHealth() * 0.025F
            + (entity.width + entity.height) / 2.6F
            + (float)entity.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue();
         if (entity.isImmuneToFire()) {
            entityPower--;
         }

         return effectPower >= entityPower;
      }
   }

   public static boolean canImmobilizeEntity(Entity entity) {
      if (entity instanceof EntityLivingBase) {
         EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
         return canImmobilizeEntity(entityLivingBase, entityLivingBase.getActivePotionEffect(PotionEffects.FREEZING));
      } else {
         return false;
      }
   }

   @Override
   public void onApplyEffect(PotionAddedEvent event) {
      if (event.getEntityLiving() instanceof AbstractHorse) {
         event.getEntityLiving().setAIMoveSpeed(0.0F);
      }

      super.onApplyEffect(event);
   }

   @Override
   public void onRemoveEffect(EntityLivingBase entityOnEffect, PotionEffect effect, boolean byExpiry) {
      if (entityOnEffect instanceof AbstractHorse) {
         entityOnEffect.setAIMoveSpeed(1.0F);
      }

      super.onRemoveEffect(entityOnEffect, effect, byExpiry);
   }

   public static boolean tryPlaySound(EntityLivingBase entity, PotionEffect lasteffect) {
      PotionEffect neweff = entity.getActivePotionEffect(PotionEffects.FREEZING);
      if (!canImmobilizeEntity(entity, lasteffect) && canImmobilizeEntity(entity, neweff) && entity.isPotionApplicable(neweff)) {
         entity.world
            .playSound(
               (EntityPlayer)null,
               entity.posX,
               entity.posY,
               entity.posZ,
               Sounds.freezing,
               SoundCategory.HOSTILE,
               1.2F,
               0.9F + entity.getRNG().nextFloat() / 5.0F
            );
         return true;
      } else {
         return false;
      }
   }

   public static boolean tryPlaySound(EntityLivingBase entity) {
      return tryPlaySound(entity, forTestSound);
   }

   public static boolean tryPlaySound(Entity entity) {
      return entity instanceof EntityLivingBase ? tryPlaySound((EntityLivingBase)entity, forTestSound) : false;
   }

   public static boolean tryPlaySound(Entity entity, PotionEffect lasteffect) {
      return entity instanceof EntityLivingBase ? tryPlaySound((EntityLivingBase)entity, lasteffect) : false;
   }

   public static boolean onKeysChange(EntityLivingBase player, int nextKeys) {
      if (canImmobilizeEntity(player)) {
         if (!player.world.isRemote) {
            PotionEffect effect = Weapons.mixPotion(
               player,
               PotionEffects.FREEZING,
               3.0F,
               0.0F,
               Weapons.EnumPotionMix.WITH_MINIMUM,
               Weapons.EnumPotionMix.ABSOLUTE,
               Weapons.EnumMathOperation.MINUS,
               Weapons.EnumMathOperation.PLUS,
               0,
               0
            );
            if (effect.getDuration() <= 4) {
               player.addPotionEffect(new PotionEffect(PotionEffects.FREEZE_IMMUNITY, 105 - player.world.getDifficulty().getId() * 15));
            }
         } else {
            bom();
         }

         return false;
      } else {
         return true;
      }
   }

   @SideOnly(Side.CLIENT)
   public static void bom() {
      Booom.lastTick = 6;
      Booom.frequency = 0.525F * (rand.nextFloat() < 0.5 ? 1 : -1);
      Booom.x = 0.0F;
      Booom.y = 0.0F;
      Booom.z = 1.0F;
      Booom.power = 0.45F;
   }

   @Override
   public boolean hasStatusIcon() {
      Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("arpg:textures/potions.png"));
      return true;
   }

   public void performEffect(EntityLivingBase entityLivingBase, int amplifier) {
      entityLivingBase.motionY -= 0.02;
   }

   public boolean isReady(int duration, int amplifier) {
      return true;
   }

   @Override
   public void render(EntityLivingBase entityOnEffect, double x, double y, double z, float yaw, float partialTicks, PotionEffect effect, Render entityRenderer) {
      if (canImmobilizeEntity(entityOnEffect, effect)) {
         GlStateManager.pushMatrix();
         GlStateManager.depthMask(false);
         GlStateManager.enableBlend();
         GlStateManager.matrixMode(5888);
         AnnotationHooks.bindEnotherTexture = textur;
         entityRenderer.doRender(entityOnEffect, x, y, z, yaw, partialTicks);
         GlStateManager.disableBlend();
         GlStateManager.depthMask(true);
         GlStateManager.popMatrix();
         AnnotationHooks.bindEnotherTexture = null;
      }
   }

   @Override
   public void renderFirstperson(EntityPlayer player, PotionEffect effect, RenderHandEvent event) {
      float add = canImmobilizeEntity(player, effect) ? 0.3F : 0.0F;
      Minecraft.getMinecraft().getTextureManager().bindTexture(textur);
      PotionBurningEffects.renderPolygonInFirstPerson(DemonicBurn.fire1, 0.1F + 0.3F * Math.min(effect.getDuration(), 60) / 60.0F + add, -0.4F);
   }

   public static void initIceLayers() {
      for (Entry<Class<? extends Entity>, Render<? extends Entity>> entry : Minecraft.getMinecraft().getRenderManager().entityRenderMap.entrySet()) {
         if (entry.getKey() != null
            && EntityLivingBase.class.isAssignableFrom(entry.getKey())
            && entry.getValue() != null
            && entry.getValue() instanceof RenderLivingBase) {
            RenderLivingBase render = (RenderLivingBase)entry.getValue();
            LayerIce layer = new LayerIce(render);
            render.addLayer(layer);
         }
      }

      LayerIce layer1 = new LayerIce((RenderLivingBase<?>)Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default"));
      LayerIce layer2 = new LayerIce((RenderLivingBase<?>)Minecraft.getMinecraft().getRenderManager().getSkinMap().get("slim"));
      ((RenderPlayer)Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default")).addLayer(layer1);
      ((RenderPlayer)Minecraft.getMinecraft().getRenderManager().getSkinMap().get("slim")).addLayer(layer2);
   }
}
