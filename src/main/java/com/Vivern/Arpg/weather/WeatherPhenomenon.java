package com.Vivern.Arpg.weather;

import com.Vivern.Arpg.main.NoiseGenerator3D;
import java.util.Random;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class WeatherPhenomenon {
   public int changeSpeed = 20;
   public double sizeX = 3.0;
   public double sizeZ = 3.0;
   public boolean affectEntitys;
   public boolean invokeOnUpdateAt;
   public NoiseGenerator3D noisegenerator3d;
   Random rand = new Random();
   public boolean hasClientUpdate = false;
   public boolean hasClientChunkUpdate = false;
   public int clientBlockRange = 64;
   public int clientChunkRange = 320;
   public boolean anyBiome = true;
   public int dimension;
   public double prevWindedX;
   public double prevWindedZ;
   public double windedPosX;
   public double windedPosZ;
   public int ticksExisted;

   public WeatherPhenomenon(double sizeX, double sizeZ, boolean affectEntitys, int changeSpeed, long seed) {
      this.sizeX = sizeX;
      this.sizeZ = sizeZ;
      this.affectEntitys = affectEntitys;
      this.noisegenerator3d = new NoiseGenerator3D(seed);
   }

   public WeatherPhenomenon() {
   }

   public void loadFromNbt(NBTTagCompound compound) {
      if (compound.hasKey("ticksExisted")) {
         this.ticksExisted = compound.getInteger("ticksExisted");
      }

      if (compound.hasKey("windedPosX")) {
         this.windedPosX = compound.getDouble("windedPosX");
      }

      if (compound.hasKey("windedPosZ")) {
         this.windedPosZ = compound.getDouble("windedPosZ");
      }
   }

   public void saveToNbt(NBTTagCompound compound) {
      compound.setInteger("ticksExisted", this.ticksExisted);
      compound.setDouble("windedPosX", this.windedPosX);
      compound.setDouble("windedPosZ", this.windedPosZ);
   }

   public boolean isGoingInDimension(int id) {
      return id == this.dimension;
   }

   public boolean isGoingInBiome(Biome biome) {
      return true;
   }

   public boolean isGoingOn(double noiseValue) {
      return noiseValue > 0.2;
   }

   public void onUpdate(World world) {
      this.prevWindedX = this.windedPosX;
      this.prevWindedZ = this.windedPosZ;
      this.ticksExisted++;
      this.windedPosX = this.windedPosX + Weather.getWindX(world);
      this.windedPosZ = this.windedPosZ + Weather.getWindZ(world);
   }

   public void onUpdateAt(double x, double z, World world, double value) {
   }

   public void onAffectEntityLiving(EntityLivingBase entity, World world, double value) {
   }

   public void clientUpdate(double x, double z, World world, double value, double playerY) {
   }

   public void clientChunkUpdate(double x, double z, World world, double value, double playerY) {
   }

   public WeatherRenderList getRenderer() {
      return null;
   }
}
