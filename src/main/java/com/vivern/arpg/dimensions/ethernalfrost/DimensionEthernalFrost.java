package com.vivern.arpg.dimensions.ethernalfrost;

import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import com.vivern.arpg.main.DimensionsRegister;
import com.vivern.arpg.mobs.SpawnerTuners;
import com.vivern.arpg.renders.RenderFrozenSky;
import com.vivern.arpg.tileentity.TileMonsterSpawner;
import com.vivern.arpg.weather.Aurora;
import com.vivern.arpg.weather.Hail;
import com.vivern.arpg.weather.Snowfall;
import com.vivern.arpg.weather.TimeOfDayProvider;
import com.vivern.arpg.weather.WorldEvent;
import com.vivern.arpg.weather.WorldEventsHandler;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import org.lwjgl.util.vector.Vector4f;

public class DimensionEthernalFrost extends AbstractWorldProvider {
   public static TimeOfDayProvider timeOfDayProvider = new TimeOfDayProvider()
      .addO(
         21700,
         23000,
         new Vector4f(0.02745098F, 0.1254902F, 0.4117647F, 0.9882353F),
         new Vector4f(0.0F, 0.05490196F, 0.07058824F, 0.99215686F),
         new Vector4f(0.007843138F, 0.84705883F, 1.0F, 0.99607843F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
         new Vector4f(0.06666667F, 0.0627451F, 0.19607843F, 0.27058825F),
         new Vector4f(0.003921569F, 0.49411765F, 1.0F, 0.47843137F)
      )
      .addO(
         23500,
         25000,
         new Vector4f(0.011764706F, 0.21176471F, 0.6431373F, 0.0F),
         new Vector4f(0.7058824F, 1.0F, 1.0F, 0.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
         new Vector4f(0.6039216F, 0.6862745F, 0.77254903F, 0.16862746F),
         new Vector4f(0.8745098F, 0.9372549F, 1.0F, 0.5921569F)
      )
      .addO(
         11500,
         12500,
         new Vector4f(0.015686275F, 0.039215688F, 0.3254902F, 0.0F),
         new Vector4f(0.89411765F, 0.69411767F, 0.80784315F, 0.0F),
         new Vector4f(0.8235294F, 0.27058825F, 0.16078432F, 0.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
         new Vector4f(0.09411765F, 0.12941177F, 0.4117647F, 0.34509805F),
         new Vector4f(0.7137255F, 0.29411766F, 0.5137255F, 0.6862745F)
      )
      .addO(
         12500,
         13500,
         new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
         new Vector4f(0.08627451F, 0.14901961F, 0.2627451F, 0.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
         new Vector4f(0.0F, 0.0F, 0.0F, 0.0F),
         new Vector4f(0.0F, 0.05490196F, 0.105882354F, 0.0F),
         new Vector4f(0.21960784F, 0.28627452F, 0.4F, 0.21176471F),
         new Vector4f(0.07450981F, 0.105882354F, 0.17254902F, 0.015686275F)
      )
      .cycleAll();
   public static RenderFrozenSky skyRender = new RenderFrozenSky(timeOfDayProvider);
   public Snowfall snowfall = new Snowfall(this, 0, 6000, 24000, 0.045F);
   public Hail hail = new Hail(this, 1, 8000, 12000, 0.035F);
   public Aurora aurora = new Aurora(this, 2, 8000, 12000, 0.025F);
   public WorldEventsHandler worldEventsHandler = new WorldEventsHandler(this, this.snowfall, this.hail, this.aurora);

   public void getLightmapColors(float partialTicks, float sunBrightness, float skyLight, float blockLight, float[] colors) {
      timeOfDayProvider.setLightmapColors(6, this.getWorldTime(), partialTicks, sunBrightness, skyLight, blockLight, colors);
   }

   public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
      return null;
   }

   public IRenderHandler getSkyRenderer() {
      return skyRender;
   }

   public IRenderHandler getWeatherRenderer() {
      return this.worldEventsHandler;
   }

   public void updateWeather() {
      this.worldEventsHandler.onUpdate();
   }

   @Override
   public WorldEventsHandler getWorldEventsHandler() {
      return this.worldEventsHandler;
   }

   @Override
   public TimeOfDayProvider getTimeOfDayProvider() {
      return timeOfDayProvider;
   }

   public static boolean isAuroraNow(World world) {
      if (world.provider instanceof AbstractWorldProvider) {
         AbstractWorldProvider worldProvider = (AbstractWorldProvider)world.provider;
         if (worldProvider.getWorldEventsHandler() != null) {
            for (WorldEvent worldEvent : worldProvider.getWorldEventsHandler().events) {
               if (worldEvent instanceof Aurora) {
                  return worldEvent.isStarted;
               }
            }
         }
      }

      return false;
   }

   public static boolean isSnowfallNow(World world) {
      if (world.provider instanceof AbstractWorldProvider) {
         AbstractWorldProvider worldProvider = (AbstractWorldProvider)world.provider;
         if (worldProvider.getWorldEventsHandler() != null) {
            for (WorldEvent worldEvent : worldProvider.getWorldEventsHandler().events) {
               if (worldEvent instanceof Snowfall) {
                  return worldEvent.isStarted;
               }
            }
         }
      }

      return false;
   }

   public DimensionType getDimensionType() {
      return DimensionsRegister.ETHERNAL_FROST;
   }

   public IChunkGenerator createChunkGenerator() {
      return new EthernalFrostChunkGenerator(this.world, this.world.getSeed());
   }

   @Override
   public void init() {
      super.init();
      this.hasSkyLight = true;
      this.biomeProvider = new BiomeProviderFrost(this.world.getSeed());
   }

   public boolean canRespawnHere() {
      return true;
   }

   public boolean canBlockFreeze(BlockPos pos, boolean byWater) {
      return this.canBlockFreezes(this.world, pos, byWater);
   }

   public boolean canBlockFreezes(World world, BlockPos pos, boolean noWaterAdj) {
      Biome biome = world.getBiome(pos);
      float f = biome.getTemperature(pos);
      if (f >= 0.15F) {
         return false;
      } else {
         if (pos.getY() >= 0 && pos.getY() < 256 && world.getLightFor(EnumSkyBlock.BLOCK, pos) < 10) {
            IBlockState iblockstate1 = world.getBlockState(pos);
            Block block = iblockstate1.getBlock();
            if (block == Blocks.WATER || block == Blocks.FLOWING_WATER) {
               if (!noWaterAdj) {
                  return true;
               }

               return true;
            }
         }

         return false;
      }
   }

   public static void setupRandomSpawner(@Nullable World world, @Nullable TileEntity tile, EnumEverfrostSpawner type, Random rand) {
      if (world == null && tile != null) {
         world = tile.getWorld();
      }

      if (tile != null && tile instanceof TileMonsterSpawner && world != null) {
         TileMonsterSpawner spawner = (TileMonsterSpawner)tile;
         if (type == EnumEverfrostSpawner.ICE_CASTLE_PARAPET) {
            SpawnerTuners.ICECASTLEPARAPET.setupSpawner(world, spawner, world.rand);
         }

         if (type == EnumEverfrostSpawner.ICE_CASTLE) {
            SpawnerTuners.ICECASTLE.setupSpawner(world, spawner, world.rand);
         }

         if (type == EnumEverfrostSpawner.MOUND) {
            SpawnerTuners.MOUND.setupSpawner(world, spawner, world.rand);
         }

         if (type == EnumEverfrostSpawner.STRUCTURES) {
            SpawnerTuners.EVERFROST_STRUCTURES.setupSpawner(world, spawner, world.rand);
         }

         if (type == EnumEverfrostSpawner.GRAVE) {
            SpawnerTuners.EVERFROST_GRAVE.setupSpawner(world, spawner, world.rand);
         }

         if (type == EnumEverfrostSpawner.NIVEOUS_HALL) {
            SpawnerTuners.NIVEOUSHALL.setupSpawner(world, spawner, world.rand);
         }
      }
   }

   public static enum EnumEverfrostSpawner {
      ICE_CASTLE,
      ICE_CASTLE_PARAPET,
      MOUND,
      STRUCTURES,
      GRAVE,
      NIVEOUS_HALL;
   }
}
