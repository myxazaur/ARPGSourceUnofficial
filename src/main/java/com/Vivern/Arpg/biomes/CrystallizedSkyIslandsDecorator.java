package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.dimensions.generationutils.GenerationHelper;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenFluidLake;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkGeneratorSettings.Factory;

class CrystallizedSkyIslandsDecorator extends BiomeDecorator {
   public WorldGenFluidLake lake = new WorldGenFluidLake(Blocks.WATER, BlocksRegister.FULMINIFLORA, null, BlocksRegister.FULMINIFLORA, false, true);

   public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
      if (this.decorating) {
         throw new RuntimeException("Already decorating");
      } else {
         this.chunkProviderSettings = Factory.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
         this.chunkPos = pos;
         if (random.nextFloat() < 0.4) {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.lake.size = 10;
            this.lake.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
         }

         if (random.nextFloat() < 0.3F) {
            BlockPos toppos = GetMOP.getTopBlock(worldIn, pos.add(random.nextInt(16) + 8, 254, random.nextInt(16) + 8), BlocksRegister.FULMINIFLORA)
               .up();
            if (toppos.getY() > 5 && GenerationHelper.isReplaceable(worldIn, toppos)) {
               worldIn.setBlockState(toppos, BlocksRegister.SHIMMERINGBEASTBLOOM.getDefaultState(), 2);
            }
         }

         this.decorating = false;
      }
   }
}
