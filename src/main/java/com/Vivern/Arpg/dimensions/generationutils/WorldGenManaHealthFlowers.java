//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.dimensions.generationutils;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenManaHealthFlowers implements IWorldGenerator {
   public boolean ishealth;
   public static WorldGenGroundFoliage.WorldGenManaAndHealthFoliage healthFlowers = new WorldGenGroundFoliage.WorldGenManaAndHealthFoliage(
      BlocksRegister.HEALTHFLOWERLEAVES, 8, 2, 1
   );
   public static WorldGenGroundFoliage.WorldGenManaAndHealthFoliage manaFlowers = new WorldGenGroundFoliage.WorldGenManaAndHealthFoliage(
      BlocksRegister.MANAFLOWERLEAVES, 16, 3, 2
   );

   public WorldGenManaHealthFlowers(boolean ishealth) {
      this.ishealth = ishealth;
   }

   public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
      int dim = world.provider.getDimension();
      if (dim == 0) {
         if (this.ishealth) {
            if (random.nextInt(3) == 0) {
               int x = chunkX * 16 + random.nextInt(16);
               int z = chunkZ * 16 + random.nextInt(16);
               BlockPos top = GetMOP.getTopBlock(world, new BlockPos(x, 255, z), Blocks.GRASS);
               int start = top.getY() - 4;
               int end = 11;
               BlockPos startpos = new BlockPos(x, end, z);
               if (GetMOP.SOLID_BLOCKS.apply(world.getBlockState(startpos))) {
                  GetMOP.BlockTraceResult result = GetMOP.blockTrace(world, startpos, EnumFacing.UP, start - end, GetMOP.AIR_BLOCKS);
                  if (result != null) {
                     healthFlowers.generate(world, random, result.pos);
                  }
               }
            }
         } else if (random.nextInt(100) == 0) {
            int x = chunkX * 16 + random.nextInt(16);
            int z = chunkZ * 16 + random.nextInt(16);
            manaFlowers.generate(world, random, GetMOP.getTopBlock(world, new BlockPos(x, 255, z), Blocks.GRASS));
         }
      }
   }
}
