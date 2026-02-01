package com.vivern.arpg.weather;

import com.vivern.arpg.main.NoiseGenerator3D;
import com.vivern.arpg.renders.GUNParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class SnowfallWeather extends WeatherPhenomenon {
   static ResourceLocation snow = new ResourceLocation("arpg:textures/snowflake4.png");

   public SnowfallWeather() {
      this.sizeX = 140.0;
      this.sizeZ = 140.0;
      this.affectEntitys = false;
      this.changeSpeed = 2400;
      this.noisegenerator3d = new NoiseGenerator3D(531L);
   }

   @Override
   public boolean isGoingInDimension(int id) {
      return id == 100;
   }

   @Override
   public boolean isGoingInBiome(Biome biome) {
      return true;
   }

   @Override
   public boolean isGoingOn(double noiseValue) {
      return noiseValue > 0.3;
   }

   public void onUpdate(double x, double z, World world, double value) {
      if (!world.isRemote && this.rand.nextFloat() < value * 0.1) {
         int xx = this.rand.nextInt(5) - 2;
         int zz = this.rand.nextInt(5) - 2;

         for (int yy = 250; yy > 30; yy--) {
            BlockPos pos = new BlockPos(xx + x, yy, zz + z);
            if (world.isBlockFullCube(pos.down())) {
               if (world.isAirBlock(pos)) {
                  world.setBlockState(pos, Blocks.SNOW_LAYER.getDefaultState());
                  break;
               }

               if (world.getBlockState(pos).getBlock() == Blocks.SNOW_LAYER && Blocks.SNOW_LAYER.getMetaFromState(world.getBlockState(pos)) < 6) {
                  world.setBlockState(pos, Blocks.SNOW_LAYER.getStateFromMeta(Blocks.SNOW_LAYER.getMetaFromState(world.getBlockState(pos)) + 1));
                  break;
               }
            }
         }
      }
   }

   @Override
   public void clientUpdate(double x, double z, World world, double value, double playerY) {
      if (world.isRemote && !Minecraft.getMinecraft().isGamePaused() && this.rand.nextFloat() < value * 0.04) {
         GUNParticle hailp = new GUNParticle(
            snow,
            2.2F + this.rand.nextFloat(),
            0.01F,
            110,
            -1,
            world,
            x + this.rand.nextInt(3),
            playerY + 35.0,
            z + this.rand.nextInt(3),
            (float)(this.rand.nextGaussian() / 20.0),
            -0.1F,
            (float)(this.rand.nextGaussian() / 20.0),
            1.0F,
            1.0F,
            1.0F,
            false,
            this.rand.nextInt(360),
            true,
            2.0F
         );
         hailp.dropMode = true;
         world.spawnEntity(hailp);
      }
   }
}
