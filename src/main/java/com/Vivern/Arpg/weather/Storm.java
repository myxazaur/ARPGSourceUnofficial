//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.weather;

import com.Vivern.Arpg.dimensions.generationutils.AbstractWorldProvider;
import com.Vivern.Arpg.entity.BigLightningStrike;
import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.GetMOP;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.util.vector.Vector4f;

public class Storm extends WorldEvent implements IVoxelCloudInfo {
   public Random rand = new Random();
   public VoxelCloudField voxelCloudField;
   static ArrayList<int[]> listlightningposes = new ArrayList<>();
   public Vec3d sunVector;
   public Vector4f[] colors;
   public float nightpower;
   public float daypower;

   public Storm(AbstractWorldProvider worldProvider, int index, int livetimeMin, int livetimeMax, float chanceToStart) {
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
   }

   @Override
   public void renderClouds(float partialTicks, WorldClient world, Minecraft mc) {
      if (this.voxelCloudField == null) {
         this.voxelCloudField = new VoxelCloudField(32, 1024, 96, 256.0F);
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
      if (this.ticksExisted % 80 == 0) {
         this.updateAllLoaded(this.rand, 0.005F, 1);
      }
   }

   @Override
   public void updateLoadedXZ(World world, int posX, int posZ) {
      if (this.rand.nextFloat() < 0.005) {
         this.spawnLightning(world, posX, posZ);
      }
   }

   public void spawnLightning(World world, int posX, int posZ) {
      if (world.isAnyPlayerWithinRangeAt(posX, 256.0, posZ, 256.0)) {
         if (listlightningposes.isEmpty()) {
            for (int xx = -3; xx <= 3; xx++) {
               for (int zz = -3; zz <= 3; zz++) {
                  listlightningposes.add(new int[]{xx, zz});
               }
            }
         }

         Collections.shuffle(listlightningposes);
         GetMOP.BlockTraceResult result = null;

         for (int[] ints : listlightningposes) {
            result = GetMOP.blockTrace(world, new BlockPos(posX, 256, posZ), EnumFacing.DOWN, 180, null);
            if (result != null) {
               break;
            }
         }

         if (result == null) {
            new Vec3d(posX + 0.5 + this.rand.nextGaussian() * 2.0, 76 + this.rand.nextInt(100), posZ + 0.5 + this.rand.nextGaussian() * 2.0);
         } else {
            new Vec3d(result.pos.getX() + 0.5, result.pos.getY() + 0.5, result.pos.getZ() + 0.5);
         }

         BigLightningStrike bolt = new BigLightningStrike(world);
         bolt.setPosition(posX, 254.0, posZ);
         bolt.setSegmentParams(16.0F, 2.0F);
         world.spawnEntity(bolt);
      }
   }

   @Override
   public boolean hasVoxelAt(VoxelCloudField field, double x, double y, double z, int inArrayX, int inArrayY, int inArrayZ) {
      double d1 = 40.0F + Debugger.floats[0];
      double d2 = 20.0F + Debugger.floats[1];
      double perlin = field.noiseGeneratorPerlin1.getValue(x / d1, z / d1)
         + Debugger.floats[2]
         + (2.0F - GetMOP.getfromto((float)inArrayY, 0.0F, 7.0F) * 3.0F);
      return (field.noiseGenerator3D.getValue(x / d2, y / d2, z / d2) + 1.2F + Debugger.floats[3]) * perlin > 11.0F + Debugger.floats[4];
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
         double possibilityAngle = 110.0;
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
