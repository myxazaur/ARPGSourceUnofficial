package com.vivern.arpg.weather;

import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.mobs.HostileProjectiles;
import com.vivern.arpg.renders.GUNParticle;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.util.vector.Vector4f;

public class Hail extends WorldEvent implements IVoxelCloudInfo {
   public static ResourceLocation hail = new ResourceLocation("arpg:textures/hail.png");
   public static ResourceLocation tex = new ResourceLocation("arpg:textures/everfrost_hail.png");
   public Random rand1 = new Random();
   public Random rand2 = new Random();
   long timeSaved;
   public VoxelCloudField voxelCloudField;
   public Vec3d sunVector;
   public Vector4f[] colors;
   public float nightpower;
   public float daypower;

   public Hail(AbstractWorldProvider worldProvider, int index, int livetimeMin, int livetimeMax, float chanceToStart) {
      super(worldProvider, index, livetimeMin, livetimeMax, chanceToStart);
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted % 23 == 0) {
         this.updateAllLoaded(this.rand2, 1.0E-4F, 14);
      }
   }

   @Override
   public void updateLoadedXZ(World world, int posX, int posZ) {
      if (world.isAirBlock(new BlockPos(posX, 230, posZ))) {
         this.spawnHailblast(world, posX, 230.0, posZ);
      }
   }

   public void spawnHailblast(World world, double x, double y, double z) {
      HostileProjectiles.Hailblast entity = new HostileProjectiles.Hailblast(world);
      entity.setPosition(x, y, z);
      double val2 = 1.0;
      entity.wind = new Vec3d(Weather.getWindX(x, z) * val2, Weather.getWindY(x, z) * val2, Weather.getWindZ(x, z) * val2);
      entity.motionX = this.rand2.nextGaussian() / 7.0 + entity.wind.x;
      entity.motionY = this.rand2.nextFloat() * 0.6 - 1.8 + entity.wind.y;
      entity.motionZ = this.rand2.nextGaussian() / 7.0 + entity.wind.z;
      entity.damage = 20.0F;
      entity.spawnFragmentChance = 0.02F;
      entity.damageRadiusAdd = 1.5F;
      world.spawnEntity(entity);
   }

   @Override
   public boolean canOverlapWith(WorldEvent other) {
      return false;
   }

   @Override
   public int getCooldown() {
      return 3000 + this.worldProvider.getWorld().rand.nextInt(6000);
   }

   @Override
   public void spawnSnowRainParticle(BlockPos pos, int allUsedPosesCount, float dist) {
      if (this.rand2.nextFloat() < (2.0F - dist / 4.0F) / allUsedPosesCount) {
         World world = this.worldProvider.getWorld();
         if (this.timeSaved < world.getTotalWorldTime()) {
            this.timeSaved = world.getTotalWorldTime();
            float scl = (0.25F + 0.125F * this.rand2.nextFloat()) * 0.125F;
            int lt = 20 + this.rand2.nextInt(40);
            GUNParticle part = new GUNParticle(
               hail,
               scl,
               0.04F,
               lt,
               -1,
               world,
               pos.getX() + this.rand2.nextFloat(),
               pos.getY() + 0.0625,
               pos.getZ() + this.rand2.nextFloat(),
               (float)this.rand2.nextGaussian() / 20.0F,
               this.rand2.nextFloat() * 0.05F + 0.07F,
               (float)this.rand2.nextGaussian() / 20.0F,
               1.0F,
               1.0F,
               1.0F,
               false,
               this.rand2.nextInt(360),
               true,
               1.5F
            );
            part.scaleTickAdding = -scl / lt * 0.9F;
            world.spawnEntity(part);
         }
      }
   }

   @Override
   public void render(float partialTicks, WorldClient world, Minecraft mc) {
      super.render(partialTicks, world, mc);
      WorldEventsHandler.renderRainSnow(partialTicks, this.rand1, 0.4F * this.showness, tex, this.showness, 0.016F, -0.2F, 0.0F, 0.0F, null, this);
   }

   @Override
   public void renderClouds(float partialTicks, WorldClient world, Minecraft mc) {
      if (this.voxelCloudField == null) {
         this.voxelCloudField = new VoxelCloudField(16, 384, 112, 230.0F);
         this.voxelCloudField.voxelCloudInfo = this;
      }

      this.voxelCloudField.render(partialTicks, world, mc);
   }

   @Override
   public void stopRenderClouds() {
      this.voxelCloudField = null;
   }

   @Override
   public boolean hasVoxelAt(VoxelCloudField field, double x, double y, double z, int inArrayX, int inArrayY, int inArrayZ) {
      double d1 = 40.0F + Debugger.floats[0];
      double d2 = 20.0F + Debugger.floats[1];
      double perlin = field.noiseGeneratorPerlin1.getValue(x / d1, z / d1)
         + Debugger.floats[2]
         + (2.0F - GetMOP.getfromto((float)inArrayY, 0.0F, 7.0F) * 3.0F);
      return (field.noiseGenerator3D.getValue(x / d2, y / d2, z / d2) + 1.2F + Debugger.floats[3]) * perlin > 4.0F + Debugger.floats[4];
   }

   @Override
   public void onPreRender() {
      float celestialAngle = this.worldProvider.calculateCelestialAngle(this.worldProvider.getWorldTime(), 0.0F);
      this.sunVector = calcCelestialVector(celestialAngle);
      this.colors = this.worldProvider.getTimeOfDayProvider().getColors(5, 2, this.worldProvider.getWorld().getWorldTime());
      this.nightpower = this.worldProvider.getTimeOfDayProvider().getPowerOf(3, this.worldProvider.getWorldTime());
      this.daypower = 1.0F - this.nightpower;
   }

   @Override
   public float getLightInFace(VoxelCloudField field, VoxelCloud voxelCloud, int arrayY, EnumFacing facing) {
      if (facing.getAxis() == Axis.Z) {
         return 0.0F;
      } else {
         float normal2dX = facing.getXOffset();
         float normal2dY = facing.getYOffset();
         double scalar = normal2dX * this.sunVector.x + normal2dY * this.sunVector.y;
         double anglebetweenSun = Math.acos(scalar) * 57.2958;
         double anglebetweenMoon = 180.0 - anglebetweenSun;
         double possibilityAngle = 120.0;
         float dark = (float)(Math.min(anglebetweenSun * this.daypower + anglebetweenMoon * this.nightpower, possibilityAngle) / possibilityAngle);
         return 1.0F - dark;
      }
   }

   @Override
   public void writeColorInPoint(
      VoxelCloudField field,
      VoxelCloud voxelCloud,
      float[] colorRGBA,
      double x,
      double y,
      double z,
      int arrayX,
      int arrayY,
      int arrayZ,
      VoxelCloud.EnumCubeVertex vertex
   ) {
      float light = voxelCloud.getMostSurroundingsLightning(field, vertex, arrayX, arrayY, arrayZ);
      float dark = 1.0F - light;
      colorRGBA[0] = this.colors[1].x * light + this.colors[0].x * dark;
      colorRGBA[1] = this.colors[1].y * light + this.colors[0].y * dark;
      colorRGBA[2] = this.colors[1].z * light + this.colors[0].z * dark;
      colorRGBA[3] = 0.7F * this.showness;
   }
}
