//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.dimensions.mortuorus;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.NoiseGenerator3D;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class GraveCombGenerator {
   public int genFromX;
   public int genFromZ;
   public int genToX;
   public int genToZ;
   public ChunkPrimer chunk;
   public Random rand;
   public double maxYoffset = 0.5;
   public double XZoffset = 0.18F;
   public NoiseGenerator3D noise3d;

   public GraveCombGenerator(ChunkPrimer chunk, Random rand) {
      this.chunk = chunk;
      this.rand = rand;
   }

   public GraveCombGenerator setGenRange(ChunkPos chunk) {
      this.genFromX = chunk.getXStart();
      this.genFromZ = chunk.getZStart();
      this.genToX = chunk.getXEnd();
      this.genToZ = chunk.getZEnd();
      return this;
   }

   public static void onGenerateChunk(World world, int chunkX, int chunkZ, ChunkPrimer chunkprimer) {
      int checkRadius = 1;

      for (int xx = -checkRadius; xx <= checkRadius; xx++) {
         for (int zz = -checkRadius; zz <= checkRadius; zz++) {
            int cX = chunkX + xx;
            int cZ = chunkZ + zz;
            Random rand = new Random((cX | (long)cZ << 32) ^ world.getSeed());
            if (rand.nextFloat() < 0.2F) {
               GraveCombGenerator gen = new GraveCombGenerator(chunkprimer, rand);
               int i = cX * 16 + rand.nextInt(16);
               int j = cZ * 16 + rand.nextInt(16);
               gen.setGenRange(new ChunkPos(chunkX, chunkZ));
               gen.generate(new BlockPos(i, 5 + rand.nextInt(44), j), 5 + rand.nextInt(7), 5 + rand.nextInt(15));
            }
         }
      }
   }

   public void generate(BlockPos pos, int radius, int height) {
      int smallradius = radius - 1;
      int smallradiusX2 = smallradius * 2;
      boolean[][] array1 = new boolean[radius * 2 + 1][radius * 2 + 1];

      for (int x = 0; x <= radius * 2; x++) {
         for (int z = 0; z <= radius * 2; z++) {
            array1[x][z] = this.rand.nextFloat() < 0.4F;
         }
      }

      for (int i = 0; i < 20; i++) {
         boolean[][] array2 = new boolean[radius * 2 + 1][radius * 2 + 1];

         for (int x = 1; x <= smallradiusX2; x++) {
            for (int z = 1; z <= smallradiusX2; z++) {
               int SUM_4 = (array1[x + 1][z] ? 1 : 0) + (array1[x - 1][z] ? 1 : 0) + (array1[x][z + 1] ? 1 : 0) + (array1[x][z - 1] ? 1 : 0);
               int SUM_42 = (array1[x + 1][z + 1] ? 1 : 0) + (array1[x - 1][z + 1] ? 1 : 0) + (array1[x + 1][z - 1] ? 1 : 0) + (array1[x - 1][z - 1] ? 1 : 0);
               int SUM_9 = SUM_4 + SUM_42;
               if (SUM_9 > 3) {
                  array2[x][z] = false;
               } else if (SUM_42 == 0 && SUM_4 != 3) {
                  array2[x][z] = !array1[x][z];
               } else {
                  array2[x][z] = array1[x][z];
               }
            }
         }

         array1 = array2;
      }

      for (int i = 0; i < height; i++) {
         boolean[][] array2 = new boolean[radius * 2 + 1][radius * 2 + 1];

         for (int x = 1; x <= smallradiusX2; x++) {
            for (int zx = 1; zx <= smallradiusX2; zx++) {
               int SUM_4 = (array1[x + 1][zx] ? 1 : 0) + (array1[x - 1][zx] ? 1 : 0) + (array1[x][zx + 1] ? 1 : 0) + (array1[x][zx - 1] ? 1 : 0);
               int SUM_42 = (array1[x + 1][zx + 1] ? 1 : 0)
                  + (array1[x - 1][zx + 1] ? 1 : 0)
                  + (array1[x + 1][zx - 1] ? 1 : 0)
                  + (array1[x - 1][zx - 1] ? 1 : 0);
               int SUM_9 = SUM_4 + SUM_42;
               if (SUM_9 > 3) {
                  array2[x][zx] = false;
               } else if (SUM_42 == 0 && SUM_4 != 3) {
                  array2[x][zx] = !array1[x][zx];
               } else {
                  array2[x][zx] = array1[x][zx];
               }

               BlockPos poss = pos.add(x - smallradius, i, zx - smallradius);
               if (this.isInGenRange(poss)) {
                  this.setBlockState(poss, array2[x][zx] ? Blocks.END_BRICKS.getDefaultState() : BlocksRegister.BONESBLOCK.getDefaultState());
               }
            }
         }

         array1 = array2;
      }
   }

   public boolean isInGenRange(BlockPos pos) {
      return this.isInGenRange(pos.getX(), pos.getZ());
   }

   public boolean isInGenRange(int X, int Z) {
      return X >= this.genFromX && X <= this.genToX && Z >= this.genFromZ && Z <= this.genToZ;
   }

   public void setBlockState(BlockPos pos, IBlockState state) {
      int i = pos.getX() & 15;
      int k = pos.getZ() & 15;
      this.chunk.setBlockState(i, MathHelper.clamp(pos.getY(), 0, 255), k, state);
   }

   public IBlockState getBlockState(BlockPos pos) {
      int i = pos.getX() & 15;
      int k = pos.getZ() & 15;
      return this.chunk.getBlockState(i, MathHelper.clamp(pos.getY(), 0, 255), k);
   }
}
