package com.Vivern.Arpg.weather;

import com.Vivern.Arpg.main.NoiseGenerator3D;
import com.Vivern.Arpg.mobs.HostileProjectiles;
import com.Vivern.Arpg.renders.CloudSubparticle;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.ParticleTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class HailWeather extends WeatherPhenomenon {
   static ResourceLocation snowflake = new ResourceLocation("arpg:textures/snowflake5.png");
   public static int height = 230;

   public HailWeather() {
      this.sizeX = 440.0;
      this.sizeZ = 440.0;
      this.affectEntitys = false;
      this.changeSpeed = 2160;
      this.noisegenerator3d = new NoiseGenerator3D(12345L);
      this.hasClientChunkUpdate = true;
      this.hasClientUpdate = true;
   }

   @Override
   public WeatherRenderList getRenderer() {
      return Weather.RENDERHAILWEATHER;
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
      return noiseValue > 0.2;
   }

   public void onUpdate(double x, double z, World world, double value) {
      if (!world.isRemote && value > 0.4 && this.rand.nextFloat() < value * 2.0E-4) {
         HostileProjectiles.Hailblast entity = new HostileProjectiles.Hailblast(world);
         entity.setPosition(x, height - this.rand.nextFloat() * 8.0F, z);
         double val2 = value * 4.0;
         entity.wind = new Vec3d(Weather.getWindX(x, z) * val2, Weather.getWindY(x, z) * val2, Weather.getWindZ(x, z) * val2);
         entity.motionX = this.rand.nextGaussian() / 7.0 + entity.wind.x;
         entity.motionY = this.rand.nextFloat() * 0.6 - 1.8 + entity.wind.y;
         entity.motionZ = this.rand.nextGaussian() / 7.0 + entity.wind.z;
         entity.damage = 20.0F;
         entity.spawnFragmentChance = 0.02F;
         entity.damageRadiusAdd = 1.5F;
         world.spawnEntity(entity);
      }
   }

   @Override
   public void clientUpdate(double x, double z, World world, double value, double playerY) {
      if (world.isRemote && !Minecraft.getMinecraft().isGamePaused() && this.rand.nextFloat() < value * 0.03) {
         float f = world.getCelestialAngle(1.0F);
         float f1 = MathHelper.cos(f * (float) (Math.PI * 2)) * 2.0F + 0.25F;
         f1 = MathHelper.clamp(f1, 0.53F, 1.0F);
         int light = (int)(f1 * 225.0F) + this.rand.nextInt(16);
         GUNParticle hailp = new GUNParticle(
            snowflake,
            0.023F + this.rand.nextFloat() / 10.0F,
            0.01F + (this.rand.nextFloat() - 0.5F) * 0.016F,
            100,
            light,
            world,
            x + this.rand.nextInt(4),
            Math.min(playerY + 30.0, (double)height),
            z + this.rand.nextInt(4),
            (float)(this.rand.nextGaussian() / 30.0),
            -0.15F,
            (float)(this.rand.nextGaussian() / 30.0),
            0.2F + f1 * 0.8F,
            0.4F + f1 * 0.6F,
            1.0F,
            false,
            this.rand.nextInt(360),
            true,
            5.0F
         );
         hailp.scaleTickDropAdding = -0.002F;
         hailp.tracker = new ParticleTracker.TrackerMotionVector(
            Weather.getWindX(x, z), Weather.getWindY(x, z), Weather.getWindZ(x, z), (float)(value / 3.0), (float)(value / 10.0), 100
         );
         world.spawnEntity(hailp);
      }
   }

   @Override
   public void clientChunkUpdate(double x, double z, World world, double value, double playerY) {
      if (world.isRemote && !Minecraft.getMinecraft().isGamePaused() && this.rand.nextFloat() < value * 5.0E-4) {
         CloudSubparticle part = new CloudSubparticle(
            CloudSubparticle.standartTex,
            this.rand.nextInt(3),
            x + this.rand.nextFloat() * 16.0F,
            height + this.rand.nextGaussian() * 5.0,
            z + this.rand.nextFloat() * 16.0F,
            2.0F + this.rand.nextFloat() * 2.0F,
            2.0F + this.rand.nextFloat() * 2.0F,
            2.0F + this.rand.nextFloat() * 2.0F,
            800 + this.rand.nextInt(1500),
            250 + this.rand.nextInt(150)
         );
         part.rotateY = 1.0F;
         part.rotateAngle = this.rand.nextInt(4) * 90;
         CloudSubparticle.particles.add(part);
      }
   }
}
