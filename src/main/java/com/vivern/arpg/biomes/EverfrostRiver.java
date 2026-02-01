package com.vivern.arpg.biomes;

import com.vivern.arpg.dimensions.generationutils.WorldGenGroundFoliage;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.GetMOP;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.gen.ChunkGeneratorSettings.Factory;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class EverfrostRiver extends Biome {
   public EverfrostRiver() {
      super(new BiomeProperties("Everfrost river").setBaseHeight(-0.5F).setHeightVariation(0.0F).setTemperature(-1.0F).setWaterColor(10804223));
      this.topBlock = Blocks.ICE.getDefaultState();
      this.fillerBlock = BlocksRegister.GLACIER.getDefaultState();
      this.decorator = new EverfrostRiverDecorator();
   }

   public void decorate(World worldIn, Random rand, BlockPos pos) {
      super.decorate(worldIn, rand, pos);
      if (rand.nextFloat() < 0.04F) {
         BlockPos position = new BlockPos(pos.getX() + 8 + rand.nextInt(16), 62, pos.getZ() + 8 + rand.nextInt(16));
         WorldGenMinable generator = new WorldGenMinable(BlocksRegister.NIVEOLITEBLOCK.getDefaultState(), 7 + rand.nextInt(12), GetMOP.ALL_BLOCKS);
         generator.generate(worldIn, rand, position);
      }
   }

   public static class EverfrostRiverDecorator extends BiomeDecorator {
      public WorldGenGroundFoliage redberry = new WorldGenGroundFoliage(BlocksRegister.CRIMBERRY, 32, 8, 4);
      public WorldGenGroundFoliage willow = new WorldGenGroundFoliage(BlocksRegister.WINTERWILLOW, 30, 7, 3);
      public WorldGenGroundFoliage tallgrass = new WorldGenGroundFoliage(BlocksRegister.FROSTEDWEED, 32, 6, 4);
      public WorldGenGroundFoliage magicflower = new WorldGenGroundFoliage(BlocksRegister.ICEFLOWER, 20, 5, 3);

      public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
         if (this.decorating) {
            throw new RuntimeException("Already decorating");
         } else {
            this.chunkProviderSettings = Factory.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
            this.chunkPos = pos;
            if (random.nextFloat() < 0.2) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.tallgrass.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            if (random.nextFloat() < 0.1) {
               int j = random.nextInt(16) + 8;
               int k = random.nextInt(16) + 8;
               this.redberry.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.willow.generate(worldIn, random, GetMOP.getTrueHeight(worldIn, this.chunkPos.add(j, 0, k)));
            if (random.nextFloat() < 0.2) {
               j = random.nextInt(16) + 8;
               k = random.nextInt(16) + 8;
               this.magicflower.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
            }

            this.decorating = false;
         }
      }
   }
}
