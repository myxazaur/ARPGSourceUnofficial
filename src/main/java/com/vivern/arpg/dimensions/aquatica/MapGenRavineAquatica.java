package com.vivern.arpg.dimensions.aquatica;

import com.vivern.arpg.main.BiomesRegister;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenRavine;

public class MapGenRavineAquatica extends MapGenRavine {
   protected void digBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop) {
      Biome biome = this.world.getBiome(new BlockPos(x + chunkX * 16, 0, z + chunkZ * 16));
      if (biome != BiomesRegister.SEAWEED_BAY) {
         IBlockState state = data.getBlockState(x, y, z);
         IBlockState top = biome.topBlock;
         IBlockState filler = biome.fillerBlock;
         if (state.getBlock() == AquaticaChunkGenerator.STONE.getBlock()
            || state.getBlock() == AquaticaChunkGenerator.STONE2.getBlock()
            || state.getBlock() == top.getBlock()
            || state.getBlock() == filler.getBlock()) {
            if (y - 1 < 7) {
               data.setBlockState(x, y, z, FLOWING_LAVA);
            } else {
               data.setBlockState(x, y, z, AIR);
               if (foundTop && data.getBlockState(x, y - 1, z).getBlock() == filler.getBlock()) {
                  data.setBlockState(x, y - 1, z, top.getBlock().getDefaultState());
               }
            }
         }
      }
   }
}
