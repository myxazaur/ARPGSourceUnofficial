//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.weather;

import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.NoiseGenerator3D;
import java.util.ArrayList;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import org.lwjgl.opengl.GL11;

public class VoxelCloudField {
   public VoxelCloudArray voxels;
   public int radiusXZInVoxels;
   public int heightInVoxels;
   public int sizeXZInVoxels;
   public int voxelSize;
   public int radiusXZInBlocks;
   public ArrayList<int[]> positionsRenderList = new ArrayList<>();
   public float fieldRenderY;
   public NoiseGenerator3D noiseGenerator3D;
   public NoiseGeneratorPerlin noiseGeneratorPerlin1;
   public NoiseGeneratorPerlin noiseGeneratorPerlin2;
   public float noiseScale = 10.0F;
   public float noiseOffset = 0.5F;
   public IVoxelCloudInfo voxelCloudInfo;
   public int lightGettingDelay = 50;
   public int lightGettingTimer;
   double prevFieldx;
   double prevFieldy;
   double prevFieldz;

   public VoxelCloudField(int voxelSize, int radiusXZInBlocks, int heightInBlocks, float fieldRenderY) {
      this.voxelSize = voxelSize;
      this.radiusXZInBlocks = radiusXZInBlocks;
      this.radiusXZInVoxels = radiusXZInBlocks / voxelSize;
      this.sizeXZInVoxels = this.radiusXZInVoxels * 2 + 1;
      this.heightInVoxels = heightInBlocks / voxelSize;
      this.voxels = new VoxelCloudArray(this.sizeXZInVoxels, this.heightInVoxels, this.sizeXZInVoxels);
      this.noiseGenerator3D = new NoiseGenerator3D(1L);
      this.noiseGeneratorPerlin1 = new NoiseGeneratorPerlin(new Random(2L), 4);
      this.noiseGeneratorPerlin2 = new NoiseGeneratorPerlin(new Random(3L), 4);
      this.fieldRenderY = fieldRenderY;
      int halfHeightVoxels = this.heightInVoxels / 2;

      for (int tradius = 0; tradius <= this.radiusXZInVoxels; tradius++) {
         for (int x = -tradius; x <= tradius; x++) {
            for (int y = -tradius; y <= tradius; y++) {
               int yTo = y + halfHeightVoxels;
               if (yTo >= 0 && yTo < this.heightInVoxels) {
                  for (int z = -tradius; z <= tradius; z++) {
                     if (x == tradius || x == -tradius || y == tradius || y == -tradius || z == tradius || z == -tradius) {
                        this.positionsRenderList.add(new int[]{x + this.radiusXZInVoxels, yTo, z + this.radiusXZInVoxels});
                     }
                  }
               }
            }
         }
      }
   }

   public void render(double fieldx, double fieldy, double fieldz, double cameraX, double cameraY, double cameraZ) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(-cameraX, -cameraY, -cameraZ);
      boolean tex2dEnabled = GL11.glIsEnabled(3553);
      if (Debugger.floats[2] == 0.0F && tex2dEnabled) {
         GL11.glDisable(3553);
      }

      if (Debugger.floats[3] == 0.0F) {
         GlStateManager.shadeModel(7425);
      }

      if (Debugger.floats[4] == 0.0F) {
         GlStateManager.enableBlend();
      }

      if (Debugger.floats[5] == 0.0F) {
         GlStateManager.disableAlpha();
      }

      if (Debugger.floats[6] == 0.0F) {
         GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      }

      if (Debugger.floats[7] == 0.0F) {
         GlStateManager.disableFog();
      }

      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
      double voxelRadius = this.voxelSize / 2.0;
      if (Debugger.floats[9] == 0.0F) {
         this.lightGettingTimer++;
         if (this.lightGettingTimer > this.lightGettingDelay) {
            if (Debugger.floats[10] == 0.0F) {
               this.voxelCloudInfo.onPreRender();
            }

            for (int[] pos : this.positionsRenderList) {
               VoxelCloud voxel = this.voxels.get(pos[0], pos[1], pos[2]);
               if (Debugger.floats[11] == 0.0F && voxel != null) {
                  voxel.setFacesLight(this, pos[1]);
               }
            }

            this.lightGettingTimer = 0;
         }

         for (int[] posx : this.positionsRenderList) {
            double xpos = posx[0] * this.voxelSize + fieldx - this.radiusXZInBlocks - voxelRadius;
            double ypos = posx[1] * this.voxelSize + fieldy - voxelRadius;
            double zpos = posx[2] * this.voxelSize + fieldz - this.radiusXZInBlocks - voxelRadius;
            VoxelCloud voxel = this.voxels.get(posx[0], posx[1], posx[2]);
            if (Debugger.floats[12] == 0.0F && voxel != null) {
               voxel.render(this, bufferbuilder, this.voxelSize, xpos, ypos, zpos, cameraX, cameraY, cameraZ, posx[0], posx[1], posx[2]);
            }
         }
      }

      tessellator.draw();
      if (Debugger.floats[8] == 0.0F) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      }

      if (Debugger.floats[7] == 0.0F) {
         GlStateManager.enableFog();
      }

      if (Debugger.floats[6] == 0.0F) {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      }

      if (Debugger.floats[5] == 0.0F) {
         GlStateManager.enableAlpha();
      }

      if (Debugger.floats[4] == 0.0F) {
         GlStateManager.disableBlend();
      }

      if (Debugger.floats[2] == 0.0F && tex2dEnabled) {
         GL11.glEnable(3553);
      }

      GlStateManager.popMatrix();
   }

   public void render(float partialTicks, WorldClient world, Minecraft mc) {
      double cameraX = 0.0;
      double cameraY = 0.0;
      double cameraZ = 0.0;
      Entity rvEntity = Minecraft.getMinecraft().getRenderViewEntity();
      if (rvEntity != null) {
         cameraX = rvEntity.prevPosX + (rvEntity.posX - rvEntity.prevPosX) * partialTicks;
         cameraY = rvEntity.prevPosY + (rvEntity.posY - rvEntity.prevPosY) * partialTicks;
         cameraZ = rvEntity.prevPosZ + (rvEntity.posZ - rvEntity.prevPosZ) * partialTicks;
      }

      double fieldX = (int)cameraX / this.voxelSize * this.voxelSize;
      double fieldY = this.fieldRenderY;
      double fieldZ = (int)cameraZ / this.voxelSize * this.voxelSize;
      if (Debugger.floats[3] == 0.0F && this.isFieldPosChanged(fieldX, fieldY, fieldZ)) {
         this.generateVoxels(fieldX, fieldY, fieldZ);
         this.bakeVoxels();
         this.lightGettingTimer = this.lightGettingDelay;
      }

      this.render(fieldX, fieldY, fieldZ, cameraX, cameraY, cameraZ);
   }

   public boolean hasVoxelAt(double x, double y, double z, int inArrayX, int inArrayY, int inArrayZ) {
      if (this.voxelCloudInfo != null) {
         return this.voxelCloudInfo.hasVoxelAt(this, x, y, z, inArrayX, inArrayY, inArrayZ);
      } else {
         double d1 = this.noiseScale;
         return this.noiseGenerator3D.getValue(x / d1, y / d1, z / d1) > this.noiseOffset;
      }
   }

   public void generateVoxels(double fieldx, double fieldy, double fieldz) {
      for (int x = 0; x < this.sizeXZInVoxels; x++) {
         double xpos = x * this.voxelSize + fieldx - this.radiusXZInBlocks;

         for (int y = 0; y < this.heightInVoxels; y++) {
            double ypos = y * this.voxelSize + fieldy;

            for (int z = 0; z < this.sizeXZInVoxels; z++) {
               double zpos = z * this.voxelSize + fieldz - this.radiusXZInBlocks;
               if (this.hasVoxelAt(xpos, ypos, zpos, x, y, z)) {
                  this.voxels.set(x, y, z, new VoxelCloud());
               } else {
                  this.voxels.set(x, y, z, null);
               }
            }
         }
      }

      this.prevFieldx = fieldx;
      this.prevFieldy = fieldy;
      this.prevFieldz = fieldz;
   }

   @Nullable
   public VoxelCloud getVoxelAt(double x, double y, double z) {
      Vec3d fieldXYZ = this.getFieldXYZ();
      return this.getVoxelAt(x, y, z, fieldXYZ.x, fieldXYZ.y, fieldXYZ.z);
   }

   @Nullable
   public VoxelCloud getVoxelAt(double x, double y, double z, double fieldx, double fieldy, double fieldz) {
      Vec3i arr = this.worldCoordsToVoxelArrayCoords(x, y, z, fieldx, fieldy, fieldz);
      return this.getVoxel(arr.getX(), arr.getY(), arr.getZ());
   }

   @Nullable
   public VoxelCloud getVoxel(int arrayx, int arrayy, int arrayz) {
      return arrayy >= 0 && arrayy < this.heightInVoxels ? this.voxels.get(arrayx, arrayy, arrayz) : null;
   }

   public Vec3i worldCoordsToVoxelArrayCoords(double x, double y, double z, double fieldx, double fieldy, double fieldz) {
      double add = this.voxelSize / 2.0;
      int arrx = MathHelper.floor((x + add + this.radiusXZInBlocks - fieldx) / this.voxelSize);
      int arry = MathHelper.floor((y + add - fieldy) / this.voxelSize);
      int arrz = MathHelper.floor((z + add + this.radiusXZInBlocks - fieldz) / this.voxelSize);
      return new Vec3i(arrx, arry, arrz);
   }

   public Vec3d voxelArrayCoordsToWorldCoords(int x, int y, int z, double fieldx, double fieldy, double fieldz) {
      double voxelRadius = this.voxelSize / 2.0;
      double xpos = x * this.voxelSize + fieldx - this.radiusXZInBlocks - voxelRadius;
      double ypos = y * this.voxelSize + fieldy - voxelRadius;
      double zpos = z * this.voxelSize + fieldz - this.radiusXZInBlocks - voxelRadius;
      return new Vec3d(xpos, ypos, zpos);
   }

   public Vec3d getFieldXYZ() {
      double cameraX = 0.0;
      double cameraY = 0.0;
      double cameraZ = 0.0;
      Entity rvEntity = Minecraft.getMinecraft().getRenderViewEntity();
      float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
      if (rvEntity != null) {
         cameraX = rvEntity.prevPosX + (rvEntity.posX - rvEntity.prevPosX) * partialTicks;
         cameraY = rvEntity.prevPosY + (rvEntity.posY - rvEntity.prevPosY) * partialTicks;
         cameraZ = rvEntity.prevPosZ + (rvEntity.posZ - rvEntity.prevPosZ) * partialTicks;
      }

      double fieldX = (int)cameraX / this.voxelSize * this.voxelSize;
      double fieldY = this.fieldRenderY;
      double fieldZ = (int)cameraZ / this.voxelSize * this.voxelSize;
      return new Vec3d(fieldX, fieldY, fieldZ);
   }

   public boolean isFieldPosChanged(double fieldx, double fieldy, double fieldz) {
      return fieldx != this.prevFieldx || fieldy != this.prevFieldy || fieldz != this.prevFieldz;
   }

   public void bakeVoxels() {
      int heightInVoxelsM1 = this.heightInVoxels - 1;

      for (int x = 0; x < this.sizeXZInVoxels; x++) {
         for (int z = 0; z < this.sizeXZInVoxels; z++) {
            int bottom = 0;
            int top = 0;
            boolean tracingColumn = false;

            for (int y = 0; y < this.heightInVoxels; y++) {
               VoxelCloud voxelCenter = this.voxels.get(x, y, z);
               if (voxelCenter != null) {
                  VoxelCloud voxelUp = y == heightInVoxelsM1 ? null : this.voxels.get(x, y + 1, z);
                  VoxelCloud voxelDown = y == 0 ? null : this.voxels.get(x, y - 1, z);
                  VoxelCloud voxelWest = this.voxels.get(x - 1, y, z);
                  VoxelCloud voxelEast = this.voxels.get(x + 1, y, z);
                  VoxelCloud voxelNorth = this.voxels.get(x, y, z - 1);
                  VoxelCloud voxelSouth = this.voxels.get(x, y, z + 1);
                  voxelCenter.up = voxelUp == null;
                  voxelCenter.down = voxelDown == null;
                  voxelCenter.south = voxelSouth == null;
                  voxelCenter.north = voxelNorth == null;
                  voxelCenter.west = voxelWest == null;
                  voxelCenter.east = voxelEast == null;
                  if (!tracingColumn) {
                     bottom = y;
                     tracingColumn = true;
                  }
               } else if (tracingColumn) {
                  top = y;
                  tracingColumn = false;

                  for (int i = bottom; i < top; i++) {
                     VoxelCloud voxelInColumn = this.voxels.get(x, i, z);
                     voxelInColumn.columnBottom = bottom;
                     voxelInColumn.columnTop = top;
                  }
               }
            }
         }
      }
   }

   public void writeColorInPoint(
      VoxelCloud voxelCloud, float[] colorRGBA, double x, double y, double z, int arrayX, int arrayY, int arrayZ, VoxelCloud.EnumCubeVertex vertex
   ) {
      if (this.voxelCloudInfo != null) {
         this.voxelCloudInfo.writeColorInPoint(this, voxelCloud, colorRGBA, x, y, z, arrayX, arrayY, arrayZ, vertex);
      } else {
         float watered = 1.0F
            - Math.min(
               GetMOP.getfromto(VoxelCloud.isDownVertex(vertex.id) ? arrayY : arrayY + 1, (float)voxelCloud.columnBottom, (float)voxelCloud.columnTop) * 2.0F,
               1.0F
            );
         watered = 1.0F - watered * watered;
         float noise = (float)MathHelper.clamp(
            this.noiseGeneratorPerlin2.getValue(x / 17.0 + AnimationTimer.tick / 50.0, z / 17.0) / 20.0 + 0.5, 0.0, 1.0
         );
         noise = 1.0F - noise * noise;
         float noisecolorG = 0.5F + 0.5F * noise;
         float noisecolorB = 0.2F + 0.8F * noise;
         colorRGBA[0] = 0.07F + 0.93F * watered;
         colorRGBA[1] = (0.11F + 0.89F * watered) * noisecolorG;
         colorRGBA[2] = (0.31F + 0.69F * watered) * noisecolorB;
         colorRGBA[3] = 0.4F;
      }
   }

   public class VoxelCloudArray {
      public int arraySizeX;
      public int arraySizeY;
      public int arraySizeZ;
      public VoxelCloud[][][] voxels;
      public int startPointX;
      public int startPointY;
      public int startPointZ;

      public VoxelCloudArray(int xsize, int ysize, int zsize) {
         this.arraySizeX = xsize;
         this.arraySizeY = ysize;
         this.arraySizeZ = zsize;
         this.voxels = new VoxelCloud[this.arraySizeX][this.arraySizeY][this.arraySizeZ];
      }

      public VoxelCloud get(int x, int y, int z) {
         return this.voxels[GetMOP.cycle(this.startPointX + x, this.arraySizeX)][GetMOP.cycle(this.startPointY + y, this.arraySizeY)][GetMOP.cycle(
            this.startPointZ + z, this.arraySizeZ
         )];
      }

      public void set(int x, int y, int z, VoxelCloud voxel) {
         this.voxels[GetMOP.cycle(this.startPointX + x, this.arraySizeX)][GetMOP.cycle(this.startPointY + y, this.arraySizeY)][GetMOP.cycle(
            this.startPointZ + z, this.arraySizeZ
         )] = voxel;
      }
   }
}
