package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.dimensions.generationutils.GenerationHelper;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenArthroheliaTree;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenFluidLake;
import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkGeneratorSettings.Factory;

class ArthroheliaIslandsDecorator extends BiomeDecorator {
   public WorldGenFluidLake lake = new WorldGenFluidLake(Blocks.WATER, BlocksRegister.FULMINIFLORA, null, BlocksRegister.FULMINIFLORA, false, true);
   public WorldGenArthroheliaTree genArthroheliaTree = new WorldGenArthroheliaTree();

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

         BlockPos topposAh = GetMOP.getTopBlock(worldIn, pos.add(random.nextInt(16) + 8, 254, random.nextInt(16) + 8), BlocksRegister.FULMINIFLORA)
            .up();
         if (topposAh.getY() > 5 && GenerationHelper.isReplaceable(worldIn, topposAh)) {
            this.genArthroheliaTree.genStyle = (int)Debugger.floats[0];
            if (random.nextFloat() < 0.5F) {
               this.genArthroheliaTree.growDirection = EnumFacing.UP;
               this.genArthroheliaTree.maxsamples = 30 + random.nextInt(25);
               this.genArthroheliaTree.chances = WorldGenArthroheliaTree.chancesTree;
               this.genArthroheliaTree.chanceReduction = 1.8F;
               this.genArthroheliaTree.startChanceToContinue = 1.0F;
               this.genArthroheliaTree.startChanceToSplit = 0.3F;
               this.genArthroheliaTree.generate(worldIn, random, topposAh);
            } else if (random.nextFloat() < 0.7F) {
               this.genArthroheliaTree.growDirection = EnumFacing.UP;
               this.genArthroheliaTree.maxsamples = 20 + random.nextInt(10);
               this.genArthroheliaTree.chances = WorldGenArthroheliaTree.chancesMini;
               this.genArthroheliaTree.chanceReduction = 1.8F;
               this.genArthroheliaTree.startChanceToContinue = 0.8F;
               this.genArthroheliaTree.startChanceToSplit = 0.3F;
               this.genArthroheliaTree.generate(worldIn, random, topposAh);
            } else {
               this.genArthroheliaTree.growDirection = EnumFacing.UP;
               this.genArthroheliaTree.maxsamples = 30 + random.nextInt(50);
               this.genArthroheliaTree.chances = WorldGenArthroheliaTree.chancesBrush;
               this.genArthroheliaTree.chanceReduction = 1.0F;
               this.genArthroheliaTree.startChanceToContinue = 0.9F;
               this.genArthroheliaTree.startChanceToSplit = 0.2F;
               this.genArthroheliaTree.generate(worldIn, random, topposAh);
            }
         }

         this.decorating = false;
      }
   }
}
