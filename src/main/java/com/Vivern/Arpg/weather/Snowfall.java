//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.weather;

import com.Vivern.Arpg.dimensions.generationutils.AbstractWorldProvider;
import com.Vivern.Arpg.events.Debugger;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.util.vector.Vector4f;

public class Snowfall extends WorldEvent implements IVoxelCloudInfo {
   public static ResourceLocation tex = new ResourceLocation("arpg:textures/everfrost_snow.png");
   public Random rand = new Random();
   public VoxelCloudField voxelCloudField;
   public Vec3d sunVector = Vec3d.ZERO;
   public Vector4f[] colors;
   public float nightpower;
   public float daypower;

   public Snowfall(AbstractWorldProvider worldProvider, int index, int livetimeMin, int livetimeMax, float chanceToStart) {
      super(worldProvider, index, livetimeMin, livetimeMax, chanceToStart);
   }

   @Override
   public boolean canOverlapWith(WorldEvent other) {
      return false;
   }

   @Override
   public int getCooldown() {
      return 5000 + this.worldProvider.getWorld().rand.nextInt(5000);
   }

   @Override
   public void render(float partialTicks, WorldClient world, Minecraft mc) {
      super.render(partialTicks, world, mc);
      WorldEventsHandler.renderRainSnow(partialTicks, this.rand, 1.0F, tex, this.showness, 0.001F, 0.7F, 0.002F, 2.0F, null, null);
   }

   @Override
   public void renderClouds(float partialTicks, WorldClient world, Minecraft mc) {
      if (this.voxelCloudField == null) {
         this.voxelCloudField = new VoxelCloudField(16, 512, 48, 180.0F);
         this.voxelCloudField.voxelCloudInfo = this;
      }

      this.voxelCloudField.render(partialTicks, world, mc);
   }

   @Override
   public void stopRenderClouds() {
      this.voxelCloudField = null;
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted % 10 == 0) {
         this.updateAllLoaded(this.rand, 0.01F, 1);
      }
   }

   @Override
   public void updateLoadedXZ(World world, int posX, int posZ) {
      BlockPos pos = new BlockPos(posX, 0, posZ);
      pos = world.getPrecipitationHeight(pos);
      if (Blocks.SNOW_LAYER.canPlaceBlockAt(world, pos) && world.isAirBlock(pos)) {
         world.setBlockState(pos, Blocks.SNOW_LAYER.getDefaultState(), 2);
      }
   }

   @Override
   public boolean hasVoxelAt(VoxelCloudField field, double x, double y, double z, int inArrayX, int inArrayY, int inArrayZ) {
      if (inArrayY == 0) {
         double d1 = 100.0F + Debugger.floats[3];
         return field.noiseGenerator3D.getValue(x / d1, y / d1, z / d1) > Debugger.floats[4];
      } else if (inArrayY == 2) {
         double d1 = 70.0F + Debugger.floats[5];
         return field.noiseGenerator3D.getValue(x / d1, y / d1, z / d1) > Debugger.floats[6];
      } else {
         return false;
      }
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
      colorRGBA[3] = (this.colors[1].w * light + this.colors[0].w * dark) * this.showness;
   }
}
