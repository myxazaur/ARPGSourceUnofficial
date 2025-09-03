//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.elements.models.AbstractMobModel;
import com.Vivern.Arpg.entity.BetweenParticle;
import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.potions.AdvancedPotion;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.shader.ShaderMain;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionAddedEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionExpiryEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionRemoveEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;

@EventBusSubscriber(
   modid = "arpg"
)
@SideOnly(Side.CLIENT)
public class PotionBurningEffects {
   static Random rand = new Random();
   static ResourceLocation fiertex = new ResourceLocation("arpg:textures/oildrop.png");
   static ResourceLocation fiertrtex = new ResourceLocation("arpg:textures/oiltrail.png");
   static ResourceLocation firecircle = new ResourceLocation("arpg:textures/fire_circle.png");
   public static List<EntityLivingBase> fieryoil = new ArrayList<>();
   public static List<EntityLivingBase> fireaura = new ArrayList<>();

   @SubscribeEvent
   public static void potionAdded(PotionAddedEvent event) {
      if (event.getPotionEffect().getPotion() == PotionEffects.FIERYOIL) {
         fieryoil.add(event.getEntityLiving());
      }

      if (event.getPotionEffect().getPotion() == PotionEffects.FIRE_AURA) {
         fireaura.add(event.getEntityLiving());
      }
   }

   @SubscribeEvent
   public static void potionRemove(PotionRemoveEvent event) {
      if (event.getPotionEffect() != null) {
         if (event.getPotionEffect().getPotion() == PotionEffects.FIERYOIL) {
            fieryoil.remove(event.getEntityLiving());
         }

         if (event.getPotionEffect().getPotion() == PotionEffects.FIRE_AURA) {
            fireaura.remove(event.getEntityLiving());
         }
      }
   }

   @SubscribeEvent
   public static void potionExpiry(PotionExpiryEvent event) {
      if (event.getPotionEffect() != null) {
         if (event.getPotionEffect().getPotion() == PotionEffects.FIERYOIL) {
            fieryoil.remove(event.getEntityLiving());
         }

         if (event.getPotionEffect().getPotion() == PotionEffects.FIRE_AURA) {
            fireaura.remove(event.getEntityLiving());
         }
      }
   }

   @SubscribeEvent
   public static void onLivingUpdate(LivingUpdateEvent event) {
      EntityLivingBase entityliving = event.getEntityLiving();
      if (fieryoil.contains(entityliving)) {
         BlockPos pos = entityliving.getPosition();
         Vec3d vecpos = entityliving.getPositionVector().add(0.0, entityliving.height / 1.4F, 0.0);
         Vec3d vec = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
         Vec3d vec2 = new Vec3d(pos.getX() + 1, pos.getY(), pos.getZ() + 1);
         BetweenParticle laser = new BetweenParticle(
            entityliving.world, fiertrtex, 0.35F, -1, 1.0F, 1.0F, 1.0F, 0.0F, vecpos.distanceTo(vec), 1, 0.0F, 3.0F, vecpos, vec
         );
         laser.setPosition(entityliving.posX, entityliving.posY + entityliving.height / 1.4, entityliving.posZ);
         entityliving.world.spawnEntity(laser);
         BetweenParticle laser2 = new BetweenParticle(
            entityliving.world, fiertrtex, 0.35F, -1, 1.0F, 1.0F, 1.0F, 0.0F, vecpos.distanceTo(vec2), 1, 0.0F, 3.0F, vecpos, vec2
         );
         laser2.setPosition(entityliving.posX, entityliving.posY + entityliving.height / 1.4, entityliving.posZ);
         entityliving.world.spawnEntity(laser2);
         if (entityliving instanceof EntityPlayer && !entityliving.isPotionActive(PotionEffects.FIERYOIL)) {
            fieryoil.remove(entityliving);
         }
      }
   }

   @SubscribeEvent
   public static void onRenderHand(RenderWorldLastEvent event) {
      if (fireaura.contains(Minecraft.getMinecraft().player)
         && Minecraft.getMinecraft().getRenderManager().options != null
         && Minecraft.getMinecraft().getRenderManager().options.thirdPersonView == 0) {
         float time = AnimationTimer.tick;
         ShaderMain.MoltenShader.start();
         ARBShaderObjects.glUniform1fARB(ShaderMain.MoltenShader.getUniform("time"), time / 20.0F);
         GlStateManager.pushMatrix();
         GL11.glDisable(2884);
         GL11.glDisable(2896);
         GL11.glEnable(3042);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
         GlStateManager.translate(0.0, 0.3, 0.0);
         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(time / 2.0F, 0.0F, 0.0F, 1.0F);
         float scaleY = 5.0F;
         GlStateManager.scale(1.8, 1.8, 1.8);
         Minecraft.getMinecraft().renderEngine.bindTexture(firecircle);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-1.0, -1.0, 0.0).tex(0.0, 1.0).endVertex();
         bufferbuilder.pos(-1.0, 1.0, 0.0).tex(0.0, 0.0).endVertex();
         bufferbuilder.pos(1.0, 1.0, 0.0).tex(1.0, 0.0).endVertex();
         bufferbuilder.pos(1.0, -1.0, 0.0).tex(1.0, 1.0).endVertex();
         tessellator.draw();
         GL11.glEnable(2896);
         GL11.glDisable(3042);
         GL11.glEnable(2884);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.popMatrix();
         ShaderMain.MoltenShader.stop();
         if (!Minecraft.getMinecraft().player.isPotionActive(PotionEffects.FIRE_AURA)) {
            fireaura.remove(Minecraft.getMinecraft().player);
         }
      }
   }

   public static void onTickRender(RenderTickEvent event) {
      if (Minecraft.getMinecraft().world != null && Minecraft.getMinecraft().world.getLoadedEntityList() != null) {
         float pt = Minecraft.getMinecraft().getRenderPartialTicks();
         double cameraX = 0.0;
         double cameraY = 0.0;
         double cameraZ = 0.0;
         Entity rvEntity = Minecraft.getMinecraft().getRenderViewEntity();
         if (rvEntity != null) {
            cameraX = rvEntity.prevPosX + (rvEntity.posX - rvEntity.prevPosX) * pt;
            cameraY = rvEntity.prevPosY + (rvEntity.posY - rvEntity.prevPosY) * pt;
            cameraZ = rvEntity.prevPosZ + (rvEntity.posZ - rvEntity.prevPosZ) * pt;
         }

         for (Entity entity : Minecraft.getMinecraft().world.getLoadedEntityList()) {
            if (entity instanceof EntityLivingBase) {
               EntityLivingBase entitylb = (EntityLivingBase)entity;
               double posx = entitylb.prevPosX + (entitylb.posX - entitylb.prevPosX) * pt - cameraX;
               double posy = entitylb.prevPosY + (entitylb.posY - entitylb.prevPosY) * pt - cameraY;
               double posz = entitylb.prevPosZ + (entitylb.posZ - entitylb.prevPosZ) * pt - cameraZ;
               if (fireaura.contains(entitylb)) {
                  float time = AnimationTimer.tick;
                  ShaderMain.MoltenShader.start();
                  ARBShaderObjects.glUniform1fARB(ShaderMain.MoltenShader.getUniform("time"), time / 20.0F);
                  GlStateManager.pushMatrix();
                  GL11.glDisable(2884);
                  GL11.glDisable(2896);
                  GL11.glEnable(3042);
                  GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
                  OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
                  GlStateManager.translate(posx, posy + 0.3, posz);
                  GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                  GlStateManager.rotate(time / 2.0F, 0.0F, 0.0F, 1.0F);
                  float scaleY = 5.0F;
                  GlStateManager.scale(1.8, 1.8, 1.8);
                  Minecraft.getMinecraft().renderEngine.bindTexture(firecircle);
                  Tessellator tessellator = Tessellator.getInstance();
                  BufferBuilder bufferbuilder = tessellator.getBuffer();
                  bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
                  bufferbuilder.pos(-1.0, -1.0, 0.0).tex(0.0, 1.0).endVertex();
                  bufferbuilder.pos(-1.0, 1.0, 0.0).tex(0.0, 0.0).endVertex();
                  bufferbuilder.pos(1.0, 1.0, 0.0).tex(1.0, 0.0).endVertex();
                  bufferbuilder.pos(1.0, -1.0, 0.0).tex(1.0, 1.0).endVertex();
                  tessellator.draw();
                  GL11.glEnable(2896);
                  GL11.glDisable(3042);
                  GL11.glEnable(2884);
                  GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
                  GlStateManager.popMatrix();
                  ShaderMain.MoltenShader.stop();
                  if (entitylb instanceof EntityPlayer && !entitylb.isPotionActive(PotionEffects.FIRE_AURA)) {
                     fireaura.remove(entitylb);
                  }
               }
            }
         }
      }
   }

   public static void renderEntityOnFire(Entity entity, double x, double y, double z, float partialTicks, String firelayer0, String firelayer1, int addLight) {
      GlStateManager.disableLighting();
      TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
      TextureAtlasSprite textureatlassprite = texturemap.getAtlasSprite(firelayer0);
      TextureAtlasSprite textureatlassprite1 = texturemap.getAtlasSprite(firelayer1);
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y, (float)z);
      AbstractMobModel.light(addLight, true);
      float f = entity.width * 1.4F;
      GlStateManager.scale(f, f, f);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      float f1 = 0.5F;
      float f2 = 0.0F;
      float f3 = entity.height / f;
      float f4 = (float)(entity.posY - entity.getEntityBoundingBox().minY);
      GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
      GlStateManager.translate(0.0F, 0.0F, -0.3F + (int)f3 * 0.02F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      float f5 = 0.0F;
      int i = 0;
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);

      while (f3 > 0.0F) {
         TextureAtlasSprite textureatlassprite2 = i % 2 == 0 ? textureatlassprite : textureatlassprite1;
         Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
         float f6 = textureatlassprite2.getMinU();
         float f7 = textureatlassprite2.getMinV();
         float f8 = textureatlassprite2.getMaxU();
         float f9 = textureatlassprite2.getMaxV();
         if (i / 2 % 2 == 0) {
            float f10 = f8;
            f8 = f6;
            f6 = f10;
         }

         bufferbuilder.pos(f1 - 0.0F, 0.0F - f4, f5).tex(f8, f9).endVertex();
         bufferbuilder.pos(-f1 - 0.0F, 0.0F - f4, f5).tex(f6, f9).endVertex();
         bufferbuilder.pos(-f1 - 0.0F, 1.4F - f4, f5).tex(f6, f7).endVertex();
         bufferbuilder.pos(f1 - 0.0F, 1.4F - f4, f5).tex(f8, f7).endVertex();
         f3 -= 0.45F;
         f4 -= 0.45F;
         f1 *= 0.9F;
         f5 += 0.03F;
         i++;
      }

      tessellator.draw();
      GlStateManager.popMatrix();
      AbstractMobModel.returnlight();
      GlStateManager.enableLighting();
   }

   @SubscribeEvent
   @SideOnly(Side.CLIENT)
   public static void onRenderHand(RenderHandEvent event) {
      if (Minecraft.getMinecraft().getRenderViewEntity() != null
         && Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityPlayer
         && Minecraft.getMinecraft().getRenderManager() != null
         && Minecraft.getMinecraft().getRenderManager().options != null
         && Minecraft.getMinecraft().getRenderManager().options.thirdPersonView == 0) {
         EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().getRenderViewEntity();

         for (PotionEffect potioneffect : player.getActivePotionEffects()) {
            if (potioneffect.getPotion() instanceof AdvancedPotion) {
               ((AdvancedPotion)potioneffect.getPotion()).renderFirstperson(player, potioneffect, event);
            }
         }
      }
   }

   public static void renderFireInFirstPerson(String firelayer1, float alpha, float yoffset) {
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
      GlStateManager.depthFunc(519);
      GlStateManager.depthMask(false);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      float f = 1.0F;

      for (int i = 0; i < 2; i++) {
         GlStateManager.pushMatrix();
         TextureAtlasSprite textureatlassprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(firelayer1);
         Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
         float f1 = textureatlassprite.getMinU();
         float f2 = textureatlassprite.getMaxU();
         float f3 = textureatlassprite.getMinV();
         float f4 = textureatlassprite.getMaxV();
         float f5 = -0.5F;
         float f6 = 0.5F;
         float f7 = -0.5F;
         float f8 = 0.5F;
         float f9 = -0.5F;
         GlStateManager.translate(-(i * 2 - 1) * 0.24F, -0.3F, 0.0F);
         GlStateManager.rotate((i * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-0.5, 0.0F + yoffset, -0.5).tex(f2, f4).endVertex();
         bufferbuilder.pos(0.5, 0.0F + yoffset, -0.5).tex(f1, f4).endVertex();
         bufferbuilder.pos(0.5, 0.5 + yoffset, -0.5).tex(f1, f3).endVertex();
         bufferbuilder.pos(-0.5, 0.5 + yoffset, -0.5).tex(f2, f3).endVertex();
         tessellator.draw();
         GlStateManager.popMatrix();
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableBlend();
      GlStateManager.depthMask(true);
      GlStateManager.depthFunc(515);
   }

   public static void renderPolygonInFirstPerson(String firelayer1, float alpha, float yoffset) {
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
      GlStateManager.depthFunc(519);
      GlStateManager.depthMask(false);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      float f = 1.0F;
      GlStateManager.pushMatrix();
      float f5 = -0.5F;
      float f6 = 0.5F;
      float f7 = -0.5F;
      float f8 = 0.5F;
      float f9 = -0.5F;
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-1.0, 0.0F + yoffset, -0.5).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(1.0, 0.0F + yoffset, -0.5).tex(0.0, 1.0).endVertex();
      bufferbuilder.pos(1.0, 1.5 + yoffset, -0.5).tex(1.0, 1.0).endVertex();
      bufferbuilder.pos(-1.0, 1.5 + yoffset, -0.5).tex(1.0, 0.0).endVertex();
      tessellator.draw();
      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableBlend();
      GlStateManager.depthMask(true);
      GlStateManager.depthFunc(515);
   }
}
