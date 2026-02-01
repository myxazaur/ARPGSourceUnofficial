package com.vivern.arpg.biomes;

import com.vivern.arpg.dimensions.generationutils.GenerationHelper;
import com.vivern.arpg.dimensions.generationutils.WorldGenArthroheliaTree;
import com.vivern.arpg.dimensions.generationutils.WorldGenElectrofern;
import com.vivern.arpg.dimensions.generationutils.WorldGenFluidLake;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.GetMOP;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.ChunkGeneratorSettings.Factory;

class ElectrofernGroveDecorator extends BiomeDecorator {
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

         if (random.nextFloat() < 0.1F) {
            BlockPos topposAh = GetMOP.getTopBlock(worldIn, pos.add(random.nextInt(16) + 8, 254, random.nextInt(16) + 8), BlocksRegister.FULMINIFLORA)
               .up();
            if (topposAh.getY() > 5 && GenerationHelper.isReplaceable(worldIn, topposAh)) {
               this.genArthroheliaTree.growDirection = EnumFacing.UP;
               this.genArthroheliaTree.maxsamples = 30 + random.nextInt(25);
               this.genArthroheliaTree.chances = WorldGenArthroheliaTree.chancesTree;
               this.genArthroheliaTree.chanceReduction = 1.8F;
               this.genArthroheliaTree.startChanceToContinue = 1.0F;
               this.genArthroheliaTree.startChanceToSplit = 0.3F;
               this.genArthroheliaTree.generate(worldIn, random, topposAh);
            }
         }

         if (random.nextFloat() < 0.7F) {
            BlockPos topposAh = GetMOP.getTopBlock(worldIn, pos.add(random.nextInt(16) + 8, 254, random.nextInt(16) + 8), BlocksRegister.FULMINIFLORA);
            if (topposAh.getY() > 5) {
               WorldGenElectrofern gen = new WorldGenElectrofern();
               gen.generateFernBush(worldIn, random, topposAh, 1 + random.nextInt(6), 1 + random.nextInt(8), 1 + random.nextInt(4), EnumFacing.UP);
            }
         }

         if (random.nextFloat() < 0.92F) {
            BlockPos topposAh = GetMOP.getTopBlock(worldIn, pos.add(random.nextInt(16) + 8, 254, random.nextInt(16) + 8), BlocksRegister.FULMINIFLORA)
               .up();
            if (topposAh.getY() > 5 && GenerationHelper.isReplaceable(worldIn, topposAh)) {
               NoiseGeneratorPerlin perlin1 = new NoiseGeneratorPerlin(new Random(worldIn.getSeed()), 2);
               NoiseGeneratorPerlin perlin2 = new NoiseGeneratorPerlin(new Random(worldIn.getSeed() + 3L), 2);
               WorldGenElectrofern gen = new WorldGenElectrofern();
               int noisePerlin1 = (int)Math.max(perlin1.getValue(topposAh.getX() / 50.0, topposAh.getZ() / 50.0), -12.0);
               int noisePerlin2 = (int)Math.max(perlin2.getValue(topposAh.getX() / 50.0, topposAh.getZ() / 50.0), -4.0);
               gen.generateOldFernTree(
                  worldIn,
                  random,
                  topposAh,
                  13 + random.nextInt(10) + noisePerlin1,
                  5 + random.nextInt(6) + noisePerlin2,
                  BlocksRegister.FULMINIFLORA,
                  BlocksRegister.ARTHROSTELECHALOGBRASS
               );
            }
         }

         if (random.nextFloat() < 0.04F) {
            BlockPos randomPos = pos.add(random.nextInt(16) + 8, 40 + random.nextInt(200), random.nextInt(16) + 8);
            if (!worldIn.collidesWithAnyBlock(new AxisAlignedBB(randomPos).grow(6.0))) {
               WorldGenElectrofern gen = new WorldGenElectrofern();
               gen.maxLength = 5 + random.nextInt(30);
               worldIn.setBlockState(randomPos, BlocksRegister.ELECTROFERNSTEM.getDefaultState(), 2);
               boolean rotate = random.nextFloat() < 0.5F;

               for (EnumFacing facing : EnumFacing.VALUES) {
                  gen.generateDirection = facing;
                  if (!rotate) {
                     gen.generateRotated = false;
                     if (facing.getAxis() != Axis.X) {
                        worldIn.setBlockState(randomPos.offset(facing), BlocksRegister.ELECTROFERNSTEM.getDefaultState(), 2);
                        gen.generate(worldIn, random, randomPos.offset(facing));
                     }
                  } else {
                     gen.generateRotated = facing.getAxis() == Axis.Y;
                     if (facing.getAxis() != Axis.Z) {
                        worldIn.setBlockState(randomPos.offset(facing), BlocksRegister.ELECTROFERNSTEM.getDefaultState(), 2);
                        gen.generate(worldIn, random, randomPos.offset(facing));
                     }
                  }
               }
            }
         }

         this.decorating = false;
      }
   }
}
