package com.vivern.arpg.weather;

import com.vivern.arpg.main.NoiseGenerator3D;
import com.vivern.arpg.renders.GUNParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class NetherWeather extends WeatherPhenomenon {
   static ResourceLocation tex = new ResourceLocation("arpg:textures/nether_rain.png");

   public NetherWeather() {
      this.sizeX = 140.0;
      this.sizeZ = 140.0;
      this.affectEntitys = true;
      this.changeSpeed = 2000;
      this.noisegenerator3d = new NoiseGenerator3D(666L);
   }

   @Override
   public WeatherRenderList getRenderer() {
      return Weather.RENDERNETHERSMOKE;
   }

   @Override
   public boolean isGoingInDimension(int id) {
      return id == -1;
   }

   @Override
   public boolean isGoingInBiome(Biome biome) {
      return true;
   }

   @Override
   public boolean isGoingOn(double noiseValue) {
      return noiseValue > 0.25;
   }

   @Override
   public void clientUpdate(double x, double z, World world, double value, double playerY) {
      if (world.isRemote && !Minecraft.getMinecraft().isGamePaused() && value < 0.4 && this.rand.nextFloat() < value * 0.013) {
         int randx = this.rand.nextInt(3) - 1;
         int randz = this.rand.nextInt(3) - 1;
         if (world.isAirBlock(new BlockPos(x + randx, Math.min(playerY + 20.0, 100.0), z + randz))) {
            GUNParticle hailp = new GUNParticle(
               tex,
               0.027F + this.rand.nextFloat() / 15.0F,
               0.02F,
               70,
               240,
               world,
               x + randx,
               Math.min(playerY + 20.0, 110.0),
               z + randz,
               (float)(this.rand.nextGaussian() / 30.0),
               -0.2F,
               (float)(this.rand.nextGaussian() / 30.0),
               1.0F,
               1.0F,
               1.0F,
               false,
               0,
               true,
               2.0F
            );
            hailp.scaleTickDropAdding = -0.001F;
            world.spawnEntity(hailp);
         }
      }
   }

   @Override
   public void onAffectEntityLiving(EntityLivingBase entity, World world, double value) {
      if (!world.isRemote && value > 0.45 && entity.ticksExisted % 40 == 0) {
         PotionEffect debaff = new PotionEffect(MobEffects.WITHER, 50);
         entity.addPotionEffect(debaff);
      }
   }
}
