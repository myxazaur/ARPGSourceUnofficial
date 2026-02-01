package com.vivern.arpg.dimensions.generationutils;

import com.vivern.arpg.weather.TimeOfDayProvider;
import com.vivern.arpg.weather.WorldEvent;
import com.vivern.arpg.weather.WorldEventsHandler;
import java.lang.reflect.Field;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public abstract class AbstractWorldProvider extends WorldProvider {
   static Field fieldfarPlaneDistance;
   static boolean lastFailed;
   static IRenderHandler pcaleholder = new IRenderHandler() {
      public void render(float partialTicks, WorldClient world, Minecraft mc) {
      }
   };

   public static void disableFarplane() {
      if (fieldfarPlaneDistance != null && !lastFailed) {
         try {
            fieldfarPlaneDistance.setFloat(Minecraft.getMinecraft().entityRenderer, 100000.0F);
         } catch (IllegalArgumentException var2) {
            lastFailed = true;
         } catch (IllegalAccessException var3) {
            lastFailed = true;
         }
      } else {
         try {
            EntityRenderer entityRenderer = Minecraft.getMinecraft().entityRenderer;
            fieldfarPlaneDistance = EntityRenderer.class.getField("farPlaneDistance");
            fieldfarPlaneDistance.setAccessible(true);
            fieldfarPlaneDistance.setFloat(entityRenderer, 100000.0F);
            lastFailed = false;
         } catch (Exception var1) {
         }
      }
   }

   public IRenderHandler getCloudRenderer() {
      return pcaleholder;
   }

   public void onUpdatePlayerOnClient(EntityPlayer player) {
      WorldEventsHandler weh = this.getWorldEventsHandler();
      if (weh != null) {
         weh.onUpdateClient(player);
      }
   }

   public void onWorldSave() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      if (this.getWorldEventsHandler() != null) {
         NBTTagCompound worldEventsTag = new NBTTagCompound();
         this.getWorldEventsHandler().writeToNbt(worldEventsTag);
         nbttagcompound.setTag("worldEventsTag", worldEventsTag);
      }

      this.world.getWorldInfo().setDimensionData(this.world.provider.getDimension(), nbttagcompound);
   }

   public void init() {
      if (this.getWorldEventsHandler() != null) {
         NBTTagCompound nbttagcompound = this.world.getWorldInfo().getDimensionData(this.world.provider.getDimension());
         if (this.world instanceof WorldServer) {
            NBTTagCompound worldEventsTag = nbttagcompound.getCompoundTag("worldEventsTag");
            this.getWorldEventsHandler().readFromNbt(worldEventsTag);
         }
      }
   }

   public void onPlayerJoin(EntityPlayerMP player) {
      WorldEventsHandler worldEventsHandler = this.getWorldEventsHandler();
      if (worldEventsHandler != null) {
         for (int i = 0; i < worldEventsHandler.events.length; i++) {
            WorldEvent event = worldEventsHandler.events[i];
            if (event.isStarted) {
               event.delayedSendPacket();
            }
         }
      }
   }

   public static void onEntityJoinWorld(EntityJoinWorldEvent e) {
      Entity entity = e.getEntity();
      if (entity instanceof EntityPlayerMP) {
         World world = e.getWorld();
         if (world.provider instanceof AbstractWorldProvider) {
            EntityPlayerMP player = (EntityPlayerMP)entity;
            AbstractWorldProvider abstractWorldProvider = (AbstractWorldProvider)world.provider;
            abstractWorldProvider.onPlayerJoin(player);
         }
      }
   }

   public boolean canDoRainSnowIce(Chunk chunk) {
      return false;
   }

   public void calculateInitialWeather() {
   }

   public World getWorld() {
      return this.world;
   }

   @Nullable
   public abstract WorldEventsHandler getWorldEventsHandler();

   @Nullable
   public abstract TimeOfDayProvider getTimeOfDayProvider();

   public boolean isWorldEventStarted(int index) {
      WorldEventsHandler worldEventsHandler = this.getWorldEventsHandler();
      return worldEventsHandler != null && index >= 0 && index < worldEventsHandler.events.length ? worldEventsHandler.events[index].isStarted : false;
   }

   public static int rgba(int decimalColorRgb, float alpha) {
      int a = (int)(alpha * 255.0F);
      return decimalColorRgb | a << 24;
   }
}
