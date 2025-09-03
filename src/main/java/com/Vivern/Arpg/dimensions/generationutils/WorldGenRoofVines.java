//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.dimensions.generationutils;

import com.Vivern.Arpg.main.GetMOP;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenRoofVines implements IWorldGenerator {
   public IBlockState vineState;
   public int chance;
   public int dimension;
   public int minStartY;
   public int maxStartY;
   public int minVineLength;
   public int maxVineLength;
   public int attempts;
   public int attemptsXZSize;

   public WorldGenRoofVines(
      IBlockState vineState, int chance, int dimension, int minStartY, int maxStartY, int minVineLength, int maxVineLength, int attempts, int attemptsXZSize
   ) {
      this.vineState = vineState;
      this.chance = chance;
      this.dimension = dimension;
      this.minStartY = minStartY;
      this.maxStartY = maxStartY;
      this.minVineLength = minVineLength;
      this.maxVineLength = maxVineLength;
      this.attempts = attempts;
      this.attemptsXZSize = attemptsXZSize;
   }

   public void placeVine(World world, BlockPos pos, int length) {
      if (this.vineState.getBlock().canPlaceBlockAt(world, pos)) {
         for (int i = 0; i < length && world.isAirBlock(pos); i++) {
            world.setBlockState(pos, this.vineState, 2);
            pos = pos.down();
         }
      }
   }

   public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
      int dim = world.provider.getDimension();
      if (dim == this.dimension && random.nextInt(this.chance) == 0) {
         int startY = this.minStartY + random.nextInt(this.maxStartY - this.minStartY + 1);
         int x = chunkX * 16 + random.nextInt(16);
         int z = chunkZ * 16 + random.nextInt(16);
         BlockPos start = new BlockPos(x, startY, z);
         if (GetMOP.AIR_BLOCKS.apply(world.getBlockState(start))) {
            GetMOP.BlockTraceResult result1 = GetMOP.blockTrace(world, start, EnumFacing.DOWN, startY, GetMOP.SOLID_NON_PLANTS_BLOCKS);
            if (result1 == null) {
               return;
            }

            start = result1.pos;
         }

         GetMOP.BlockTraceResult topresult = GetMOP.blockTrace(world, start, EnumFacing.DOWN, start.getY(), GetMOP.AIR_BLOCKS);
         if (topresult != null) {
            this.placeVine(world, topresult.pos, this.minVineLength + random.nextInt(this.maxVineLength - this.minVineLength + 1));
            if (this.attempts > 1) {
               for (int i = 0; i < this.attempts; i++) {
                  GetMOP.BlockTraceResult result2 = GetMOP.blockTrace(
                     world,
                     topresult.pos.add(random.nextGaussian() * this.attemptsXZSize, 2.0, random.nextGaussian() * this.attemptsXZSize),
                     EnumFacing.DOWN,
                     4,
                     GetMOP.AIR_BLOCKS
                  );
                  if (result2 != null) {
                     this.placeVine(world, result2.pos, this.minVineLength + random.nextInt(this.maxVineLength - this.minVineLength));
                  }
               }
            }
         }
      }
   }
}
