package com.vivern.arpg.weather;

import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import com.vivern.arpg.main.AnimationTimer;
import com.vivern.arpg.main.GetMOP;
import com.google.common.base.Predicate;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.IRenderHandler;

public class WorldEventsHandler extends IRenderHandler {
   public static float[] rainXCoords;
   public static float[] rainYCoords;
   public static float timeOverclock = 1.0F;
   public static int startCheckDelay = (int) (1200.0F * timeOverclock);
   public AbstractWorldProvider worldProvider;
   public WorldEvent[] events;

   public WorldEventsHandler(AbstractWorldProvider worldProvider, WorldEvent... events) {
      this.events = events;
      this.worldProvider = worldProvider;
   }

   public void writeToNbt(NBTTagCompound compound) {
      for (int i = 0; i < this.events.length; i++) {
         WorldEvent event = this.events[i];
         NBTTagCompound eventTag = new NBTTagCompound();
         event.writeToNbt(eventTag);
         compound.setTag("" + event.index, eventTag);
      }
   }

   public void readFromNbt(NBTTagCompound compound) {
      for (int i = 0; i < this.events.length; i++) {
         WorldEvent event = this.events[i];
         if (compound.hasKey("" + event.index)) {
            NBTTagCompound eventTag = compound.getCompoundTag("" + event.index);
            event.readFromNbt(eventTag);
         }
      }
   }

   public void startOrStopEvent(int index, byte start) {
      if (index >= 0 && index < this.events.length) {
         if (start == 0) {
            this.events[index].stop();
         } else if (start == 1) {
            this.events[index].start();
         } else if (start == 2) {
            this.events[index].stop();
            this.events[index].showness = 0.0F;
         } else if (start == 3) {
            this.events[index].start();
            this.events[index].showness = 1.0F;
         }
      }
   }

   public void render(float partialTicks, WorldClient world, Minecraft mc) {
      for (int i = 0; i < this.events.length; i++) {
         WorldEvent event = this.events[i];
         if (event.isStarted || event.showness > 0.0F) {
            event.render(partialTicks, world, mc);
         }
      }
   }

   public void renderClouds(float partialTicks, WorldClient world, Minecraft mc) {
      for (int i = 0; i < this.events.length; i++) {
         WorldEvent event = this.events[i];
         if (event.isStarted || event.showness > 0.0F) {
            event.renderClouds(partialTicks, world, mc);
         }
      }
   }

   public void onUpdate() {
      for (int i = 0; i < this.events.length; i++) {
         WorldEvent event = this.events[i];
         if (event.isStarted) {
            event.onUpdate();
         } else if (event.currentCooldown > 0) {
            event.currentCooldown--;
         }
      }

      if (this.worldProvider.getWorldTime() % startCheckDelay == 0L) {
         int k = 0;
         if (this.events.length > 0) {
            for (int ix = this.worldProvider.getWorld().rand
                  .nextInt(this.events.length); k < this.events.length; ix = GetMOP.next(ix, 1, this.events.length)) {
               WorldEvent event = this.events[ix];
               if (!event.isStarted
                     && event.canStart()
                     && event.currentCooldown <= 0
                     && this.worldProvider.getWorld().rand.nextFloat() < event.chanceToStart) {
                  boolean can = true;

                  for (int j = 0; j < this.events.length; j++) {
                     WorldEvent event2 = this.events[j];
                     if (event2.isStarted && !event.canOverlapWith(event2)) {
                        can = false;
                        break;
                     }
                  }

                  if (can) {
                     event.start();
                  }
               }

               k++;
            }
         }
      }
   }

   public void onUpdateClient(EntityPlayer player) {
      for (int i = 0; i < this.events.length; i++) {
         WorldEvent event = this.events[i];
         if (event.isStarted || event.showness > 0.0F) {
            event.onUpdateClient(player);
         }
      }
   }

   public static void initRainCoords() {
      rainXCoords = new float[1024];
      rainYCoords = new float[1024];

      for (int i = 0; i < 32; i++) {
         for (int j = 0; j < 32; j++) {
            float f = j - 16;
            float f1 = i - 16;
            float f2 = MathHelper.sqrt(f * f + f1 * f1);
            rainXCoords[i << 5 | j] = -f1 / f2;
            rainYCoords[i << 5 | j] = f / f2;
         }
      }
   }

   public static void enableLightmap() {
      Minecraft.getMinecraft().entityRenderer.enableLightmap();
   }

   public static void disableLightmap() {
      Minecraft.getMinecraft().entityRenderer.disableLightmap();
   }

   public static void renderRainSnow(
         float partialTicks,
         Random random,
         float chance,
         ResourceLocation rainTexture,
         float alpha,
         float rainSpeed,
         float rainSpeedRandomize,
         float horizontalSpeed,
         float horizontalSpeedRandomize,
         @Nullable Predicate<Biome> biomeFilter,
         @Nullable WorldEvent worldevent) {
      if (rainXCoords == null) {
         initRainCoords();
      }

      Minecraft mc = Minecraft.getMinecraft();
      enableLightmap();
      Entity entity = mc.getRenderViewEntity();
      if (entity != null) {
         World world = mc.world;
         int entityPosX = MathHelper.floor(entity.posX);
         int entityPosY = MathHelper.floor(entity.posY);
         int entityPosZ = MathHelper.floor(entity.posZ);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         GlStateManager.disableCull();
         GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE,
               DestFactor.ZERO);
         GlStateManager.alphaFunc(516, 0.1F);
         double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
         double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
         double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
         int l = MathHelper.floor(d1);
         int range = 5;
         if (mc.gameSettings.fancyGraphics) {
            range = 10;
         }

         int j1 = -1;
         float time = AnimationTimer.tick * 1.5F;
         bufferbuilder.setTranslation(-d0, -d1, -d2);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         MutableBlockPos blockpos$mutableblockpos = new MutableBlockPos();
         int heightAdd = 64;
         int allUsedPosesCount = (int) ((range * 2 + 1) * (range * 2 + 1) * chance);
         boolean nochance = chance >= 1.0F;

         for (int k1 = entityPosZ - range; k1 <= entityPosZ + range; k1++) {
            for (int l1 = entityPosX - range; l1 <= entityPosX + range; l1++) {
               int i2 = (k1 - entityPosZ + 16) * 32 + l1 - entityPosX + 16;
               double d3 = rainXCoords[i2] * 0.5;
               double d4 = rainYCoords[i2] * 0.5;
               blockpos$mutableblockpos.setPos(l1, 0, k1);
               if ((nochance || random.nextFloat() < chance)
                     && (biomeFilter == null || biomeFilter.apply(world.getBiome(blockpos$mutableblockpos)))) {
                  int precipHeight = world.getPrecipitationHeight(blockpos$mutableblockpos).getY();
                  int lowestPosY = entityPosY - range;
                  if (lowestPosY < precipHeight) {
                     lowestPosY = precipHeight;
                  }

                  int highstPosY = lowestPosY + range + heightAdd;
                  int i3 = precipHeight;
                  if (precipHeight < l) {
                     i3 = l;
                  }

                  if (lowestPosY != highstPosY) {
                     random.setSeed(l1 * l1 * 3121 + l1 * 45238971 ^ k1 * k1 * 418711 + k1 * 13761);
                     blockpos$mutableblockpos.setPos(l1, lowestPosY, k1);
                     if (j1 != 1) {
                        if (j1 >= 0) {
                           tessellator.draw();
                        }

                        j1 = 1;
                        mc.getTextureManager().bindTexture(rainTexture);
                        bufferbuilder.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                     }

                     double d8 = -time * rainSpeed * (1.0 - random.nextDouble() * rainSpeedRandomize);
                     double texDisplaceX = random.nextDouble()
                           + time * horizontalSpeed * (1.0 - random.nextDouble() * horizontalSpeedRandomize);
                     double texDisplaceY = random.nextDouble() + lowestPosY * -0.25;
                     double d11 = l1 + 0.5F - entity.posX;
                     double d12 = k1 + 0.5F - entity.posZ;
                     float dist = MathHelper.sqrt(d11 * d11 + d12 * d12);
                     float f6 = dist / range;
                     float f5 = ((1.0F - f6 * f6) * 0.3F + 0.5F) * alpha;
                     blockpos$mutableblockpos.setPos(l1, i3, k1);
                     int i4 = (world.getCombinedLight(blockpos$mutableblockpos, 0) * 3 + 15728880) / 4;
                     int j4 = i4 >> 16 & 65535;
                     int k4 = i4 & 65535;
                     bufferbuilder.pos(l1 - d3 + 0.5, highstPosY, k1 - d4 + 0.5)
                           .tex(0.0 + texDisplaceX, d8 + texDisplaceY)
                           .color(1.0F, 1.0F, 1.0F, f5)
                           .lightmap(j4, k4)
                           .endVertex();
                     bufferbuilder.pos(l1 + d3 + 0.5, highstPosY, k1 + d4 + 0.5)
                           .tex(1.0 + texDisplaceX, d8 + texDisplaceY)
                           .color(1.0F, 1.0F, 1.0F, f5)
                           .lightmap(j4, k4)
                           .endVertex();
                     bufferbuilder.pos(l1 + d3 + 0.5, lowestPosY, k1 + d4 + 0.5)
                           .tex(1.0 + texDisplaceX, 16.0 + d8 + texDisplaceY)
                           .color(1.0F, 1.0F, 1.0F, f5)
                           .lightmap(j4, k4)
                           .endVertex();
                     bufferbuilder.pos(l1 - d3 + 0.5, lowestPosY, k1 - d4 + 0.5)
                           .tex(0.0 + texDisplaceX, 16.0 + d8 + texDisplaceY)
                           .color(1.0F, 1.0F, 1.0F, f5)
                           .lightmap(j4, k4)
                           .endVertex();
                     if (worldevent != null) {
                        worldevent.spawnSnowRainParticle(blockpos$mutableblockpos, allUsedPosesCount, dist);
                     }
                  }
               }
            }
         }

         if (j1 >= 0) {
            tessellator.draw();
         }

         bufferbuilder.setTranslation(0.0, 0.0, 0.0);
         GlStateManager.enableCull();
         GlStateManager.disableBlend();
         GlStateManager.alphaFunc(516, 0.1F);
         disableLightmap();
      }
   }
}
