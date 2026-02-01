package com.vivern.arpg.dimensions.generationutils;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenHorribleVillage implements IWorldGenerator {
   public static int probablity = 351;

   public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
      if (world.provider.getDimension() == 0 && GenerationHelper.canGenerateDungeonAtChunkPos(chunkX, chunkZ, world.getSeed(), probablity, 8)) {
         int x = chunkX * 16 + random.nextInt(16);
         int z = chunkZ * 16 + random.nextInt(16);
         BlockPos pos = new BlockPos(x, 255, z);
         if (!BiomeDictionary.hasType(world.getBiome(pos), Type.OCEAN)) {
            HorribleVillage village = new HorribleVillage(world, 36 + random.nextInt(44), 200 + random.nextInt(300), 27, random, 5);
            village.generate(pos);
         }
      }
   }
}
