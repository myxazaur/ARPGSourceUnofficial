package com.vivern.arpg.dimensions.generationutils;

import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ChunkBuilder implements IBlockAccess {
   public ChunkPrimer chunkprimer;
   public int chunkX;
   public int chunkZ;
   public long worldSeed;
   public int minPosX;
   public int minPosZ;
   public int maxPosX;
   public int maxPosZ;
   public boolean advancedMode = false;
   public int memoryChunksRadius;
   public ChunkPrimer[][] memoryChunks;

   public ChunkBuilder(ChunkPrimer chunkprimer, int chunkX, int chunkZ, long worldSeed) {
      this.chunkprimer = chunkprimer;
      this.chunkX = chunkX;
      this.chunkZ = chunkZ;
      this.worldSeed = worldSeed;
      this.minPosX = chunkX * 16;
      this.maxPosX = this.minPosX + 15;
      this.minPosZ = chunkZ * 16;
      this.maxPosZ = this.minPosZ + 15;
   }

   public int getAdvancedModeMinX() {
      int chunkXmin = this.chunkX - this.memoryChunksRadius;
      return chunkXmin * 16;
   }

   public int getAdvancedModeMaxX() {
      int chunkXmax = this.chunkX + this.memoryChunksRadius;
      return chunkXmax * 16 + 15;
   }

   public int getAdvancedModeMinZ() {
      int chunkZmin = this.chunkZ - this.memoryChunksRadius;
      return chunkZmin * 16;
   }

   public int getAdvancedModeMaxZ() {
      int chunkZmax = this.chunkZ + this.memoryChunksRadius;
      return chunkZmax * 16 + 15;
   }

   public void setBlockState(BlockPos pos, IBlockState newState) {
      if (pos.getY() >= 0 && pos.getY() <= 255) {
         if (pos.getX() >= this.minPosX
            && pos.getX() <= this.maxPosX
            && pos.getZ() >= this.minPosZ
            && pos.getZ() <= this.maxPosZ) {
            int inChunkX = pos.getX() - this.minPosX;
            int inChunkZ = pos.getZ() - this.minPosZ;
            this.chunkprimer.setBlockState(inChunkX, pos.getY(), inChunkZ, newState);
         } else if (this.advancedMode) {
            ChunkPos chunkPos = new ChunkPos(pos);
            int abschunk0X = this.chunkX - this.memoryChunksRadius;
            int abschunk0Z = this.chunkZ - this.memoryChunksRadius;
            int relativeTargX = chunkPos.x - abschunk0X;
            int relativeTargZ = chunkPos.z - abschunk0Z;
            int arraysize = this.memoryChunksRadius * 2 + 1;
            if (relativeTargX >= 0 && relativeTargX < arraysize && relativeTargZ >= 0 && relativeTargZ < arraysize) {
               ChunkPrimer primerTo = this.memoryChunks[relativeTargX][relativeTargZ];
               int inChunkX = pos.getX() - chunkPos.getXStart();
               int inChunkZ = pos.getZ() - chunkPos.getZStart();
               primerTo.setBlockState(inChunkX, pos.getY(), inChunkZ, newState);
            }
         }
      }
   }

   public void setBlockState(BlockPos pos, IBlockState newState, int flag) {
      this.setBlockState(pos, newState);
   }

   public IBlockState getBlockState(BlockPos pos) {
      if (pos.getY() >= 0 && pos.getY() <= 255) {
         if (pos.getX() >= this.minPosX
            && pos.getX() <= this.maxPosX
            && pos.getZ() >= this.minPosZ
            && pos.getZ() <= this.maxPosZ) {
            int inChunkX = pos.getX() - this.minPosX;
            int inChunkZ = pos.getZ() - this.minPosZ;
            return this.chunkprimer.getBlockState(inChunkX, pos.getY(), inChunkZ);
         }

         if (this.advancedMode) {
            ChunkPos chunkPos = new ChunkPos(pos);
            int abschunk0X = this.chunkX - this.memoryChunksRadius;
            int abschunk0Z = this.chunkZ - this.memoryChunksRadius;
            int relativeTargX = chunkPos.x - abschunk0X;
            int relativeTargZ = chunkPos.z - abschunk0Z;
            int arraysize = this.memoryChunksRadius * 2 + 1;
            if (relativeTargX >= 0 && relativeTargX < arraysize && relativeTargZ >= 0 && relativeTargZ < arraysize) {
               ChunkPrimer primerTo = this.memoryChunks[relativeTargX][relativeTargZ];
               int inChunkX = pos.getX() - chunkPos.getXStart();
               int inChunkZ = pos.getZ() - chunkPos.getZStart();
               return primerTo.getBlockState(
                  MathHelper.clamp(inChunkX, 0, 15), MathHelper.clamp(pos.getY(), 0, 255), MathHelper.clamp(inChunkZ, 0, 15)
               );
            }
         }
      }

      return Blocks.AIR.getDefaultState();
   }

   public boolean isAirBlock(BlockPos pos) {
      return this.getBlockState(pos).getMaterial() == Material.AIR;
   }

   public void enableAdvancedMode(int memoryChunksRadius) {
      this.advancedMode = true;
      this.memoryChunksRadius = memoryChunksRadius;
      int arraysize = memoryChunksRadius * 2 + 1;
      this.memoryChunks = new ChunkPrimer[arraysize][arraysize];

      for (int x = 0; x < arraysize; x++) {
         for (int z = 0; z < arraysize; z++) {
            if (x == memoryChunksRadius && z == memoryChunksRadius) {
               this.memoryChunks[x][z] = this.chunkprimer;
            } else {
               this.memoryChunks[x][z] = new ChunkPrimer();
            }
         }
      }
   }

   @Nullable
   public TileEntity getTileEntity(BlockPos pos) {
      return null;
   }

   @SideOnly(Side.CLIENT)
   public int getCombinedLight(BlockPos pos, int lightValue) {
      return 0;
   }

   @SideOnly(Side.CLIENT)
   public Biome getBiome(BlockPos pos) {
      return Biomes.VOID;
   }

   public int getStrongPower(BlockPos pos, EnumFacing direction) {
      return this.getBlockState(pos).getStrongPower(this, pos, direction);
   }

   @SideOnly(Side.CLIENT)
   public WorldType getWorldType() {
      return WorldType.DEFAULT;
   }

   public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default) {
      return this.getBlockState(pos).isSideSolid(this, pos, side);
   }
}
