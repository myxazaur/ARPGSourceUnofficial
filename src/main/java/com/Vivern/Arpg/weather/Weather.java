package com.Vivern.Arpg.weather;

import com.Vivern.Arpg.renders.CloudSubparticle;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Deprecated
public class Weather {
   public static List<WeatherPhenomenon> weatherlist = new ArrayList<>();
   public static List<WeatherRenderList> weatherrenderlist = new ArrayList<>();
   public static HailWeather HAILWEATHER = new HailWeather();
   public static SnowfallWeather SNOWFALLWEATHER = new SnowfallWeather();
   public static NetherWeather NETHERWEATHER = new NetherWeather();
   public static RenderHail RENDERHAILWEATHER = new RenderHail(HAILWEATHER);
   public static RenderNetherSmoke RENDERNETHERSMOKE = new RenderNetherSmoke(NETHERWEATHER);

   public static boolean isWeatherGoingAt(World world, WeatherPhenomenon weather, BlockPos pos) {
      if (weather.isGoingInDimension(world.provider.getDimension()) && (weather.anyBiome || weather.isGoingInBiome(world.getBiome(pos)))) {
         double value = weather.noisegenerator3d
            .getValue(pos.getX() / weather.sizeX, (double)world.getTotalWorldTime() / weather.changeSpeed, pos.getZ() / weather.sizeZ);
         if (weather.isGoingOn(value)) {
            return true;
         }
      }

      return false;
   }

   public static void updateEntity(EntityLivingBase entity) {
      World world = entity.getEntityWorld();

      for (WeatherPhenomenon weather : weatherlist) {
         if (weather.affectEntitys
            && weather.isGoingInDimension(entity.dimension)
            && (weather.anyBiome || weather.isGoingInBiome(world.getBiome(entity.getPosition())))) {
            double value = weather.noisegenerator3d
               .getValue(entity.posX / weather.sizeX, (double)world.getTotalWorldTime() / weather.changeSpeed, entity.posZ / weather.sizeZ);
            if (weather.isGoingOn(value)) {
               weather.onAffectEntityLiving(entity, world, value);
            }
         }
      }
   }

   @SubscribeEvent
   public static void TravelDimension(PlayerChangedDimensionEvent event) {
      for (WeatherRenderList renderlist : weatherrenderlist) {
         renderlist.clearAll();
      }
   }

   @SideOnly(Side.CLIENT)
   @SubscribeEvent
   public static void RenderWorld(RenderWorldLastEvent event) {
      EntityPlayer player = Minecraft.getMinecraft().player;
      if (player != null) {
         for (WeatherRenderList renderlist : weatherrenderlist) {
            renderlist.render(
               player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch, event.getPartialTicks()
            );
         }
      }
   }

   @SideOnly(Side.CLIENT)
   @SubscribeEvent
   public static void TickClient(ClientTickEvent event) {
      EntityPlayer player = Minecraft.getMinecraft().player;
      if (player != null && player.world != null) {
         for (WeatherPhenomenon weather : weatherlist) {
            if (weather.isGoingInDimension(player.world.provider.getDimension())) {
               if (player.world.getTotalWorldTime() % 5L == 0L) {
                  for (WeatherRenderList renderlist : weatherrenderlist) {
                     if (renderlist.weather == weather) {
                        renderlist.clearAll();
                     }
                  }

                  for (int bb = -320; bb < 336; bb += 16) {
                     for (int nn = -320; nn < 336; nn += 16) {
                        double x = player.posX - player.posX % 16.0 + bb;
                        double z = player.posZ - player.posZ % 16.0 + nn;
                        if (weather.anyBiome || weather.isGoingInBiome(player.world.getBiome(new BlockPos(x, 0.0, z)))) {
                           double value = weather.noisegenerator3d
                              .getValue(x / weather.sizeX, (double)player.world.getTotalWorldTime() / weather.changeSpeed, z / weather.sizeZ);
                           if (weather.isGoingOn(value)) {
                              for (WeatherRenderList renderlistx : weatherrenderlist) {
                                 if (renderlistx.weather == weather) {
                                    renderlistx.listX.add(x);
                                    renderlistx.listY.add(value);
                                    renderlistx.listZ.add(z);
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               if (weather.hasClientUpdate) {
                  for (int bb = -weather.clientBlockRange; bb <= weather.clientBlockRange; bb += 4) {
                     for (int nnx = -weather.clientBlockRange; nnx <= weather.clientBlockRange; nnx += 4) {
                        double x = player.posX + bb;
                        double z = player.posZ + nnx;
                        if (weather.anyBiome || weather.isGoingInBiome(player.world.getBiome(new BlockPos(x, 0.0, z)))) {
                           double value = weather.noisegenerator3d
                              .getValue(x / weather.sizeX, (double)player.world.getTotalWorldTime() / weather.changeSpeed, z / weather.sizeZ);
                           if (weather.isGoingOn(value)) {
                              weather.clientUpdate(x, z, player.world, value, player.posY);
                           }
                        }
                     }
                  }
               }

               if (weather.hasClientChunkUpdate) {
                  for (int bb = -weather.clientChunkRange; bb <= weather.clientChunkRange; bb += 16) {
                     for (int nnxx = -weather.clientChunkRange; nnxx <= weather.clientChunkRange; nnxx += 16) {
                        double x = player.posX - player.posX % 16.0 + bb;
                        double z = player.posZ - player.posZ % 16.0 + nnxx;
                        if (weather.anyBiome || weather.isGoingInBiome(player.world.getBiome(new BlockPos(x, 0.0, z)))) {
                           double value = weather.noisegenerator3d
                              .getValue(x / weather.sizeX, (double)player.world.getTotalWorldTime() / weather.changeSpeed, z / weather.sizeZ);
                           if (weather.isGoingOn(value)) {
                              weather.clientChunkUpdate(x, z, player.world, value, player.posY);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void TickWorld(WorldTickEvent event) {
      World world = event.world;
      if (world != null) {
         for (WeatherPhenomenon weather : weatherlist) {
            if (weather.isGoingInDimension(world.provider.getDimension())) {
               weather.onUpdate(world);
               if (weather.invokeOnUpdateAt && world.getChunkProvider() instanceof ChunkProviderServer) {
                  ChunkProviderServer chunkProvider = (ChunkProviderServer)world.getChunkProvider();

                  for (Chunk chunk : chunkProvider.getLoadedChunks()) {
                     for (int bb = 2; bb < 16; bb += 4) {
                        for (int nn = 2; nn < 16; nn += 4) {
                           double x = chunk.x * 16 + bb;
                           double z = chunk.z * 16 + nn;
                           double value = weather.noisegenerator3d
                              .getValue(x / weather.sizeX, (double)world.getTotalWorldTime() / weather.changeSpeed, z / weather.sizeZ);
                           if (weather.isGoingInBiome(world.getBiome(new BlockPos(x, 0.0, z))) && weather.isGoingOn(value)) {
                              weather.onUpdateAt(x, z, world, value);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static void registerWeatherPhenomenon(WeatherPhenomenon weather) {
      weatherlist.add(weather);
   }

   public static void registerWeatherRender(WeatherRenderList render) {
      weatherrenderlist.add(render);
   }

   public static float getBrightnessFromTime(long totalworldtime) {
      int time = (int)(totalworldtime % 24000L);
      if (time >= 0 && time < 12000) {
         return 1.0F;
      } else if (time >= 12000 && time < 13800) {
         return 1.0F - (time - 12000) / 1800.0F;
      } else if (time >= 13800 && time < 22550) {
         return 0.0F;
      } else {
         return time >= 22550 && time < 24000 ? (time - 22550) / 1450.0F : 0.0F;
      }
   }

   public static float getWindX(World world) {
      return CloudSubparticle.windX;
   }

   public static float getWindY(World world) {
      return CloudSubparticle.windY;
   }

   public static float getWindZ(World world) {
      return CloudSubparticle.windZ;
   }

   public static float getWindX(double posX, double posZ) {
      return CloudSubparticle.windX;
   }

   public static float getWindY(double posX, double posZ) {
      return CloudSubparticle.windY;
   }

   public static float getWindZ(double posX, double posZ) {
      return CloudSubparticle.windZ;
   }
}
