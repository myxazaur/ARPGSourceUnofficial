//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.dimensions.generationutils;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.NoiseGenerator3D;
import com.google.common.base.Predicate;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenPetroleum implements IWorldGenerator {
   public static Predicate<IBlockState> defaultBlocksToReplace = apply -> apply.getBlock() != Blocks.BEDROCK
      && apply.getBlock() != Blocks.BARRIER
      && apply.getBlock() != Blocks.END_PORTAL_FRAME;
   public IBlockState petrolState;
   @Nullable
   public IBlockState gasState;
   public IBlockState wallState;
   public int dimension;
   public Predicate<IBlockState> blocksToReplace;
   public int rarity;
   public float gasChance;
   public NoiseGenerator3D noisegenerator3d;
   public long lastUsedSeed;
   public int radiusMin;
   public int radiusMax;
   public boolean hasGeyser;
   public double noiseScaleFrom;
   public double noiseScaleAdd;
   public double boundFrom;
   public double boundAdd;
   public double randMultFrom;
   public double randMultAdd;
   public double noiseMultFrom;
   public double noiseMultAdd;
   public double flattingFrom;
   public double flattingAdd;

   public WorldGenPetroleum(
      IBlockState petrolState,
      IBlockState wallState,
      @Nullable IBlockState gasState,
      int dimension,
      Predicate<IBlockState> blocksToReplace,
      int rarity,
      int radiusMin,
      int radiusMax,
      float gasChance,
      boolean hasGeyser
   ) {
      this.petrolState = petrolState;
      this.wallState = wallState;
      this.gasState = gasState;
      this.dimension = dimension;
      this.blocksToReplace = blocksToReplace;
      this.rarity = rarity;
      this.radiusMin = radiusMin;
      this.radiusMax = radiusMax;
      this.gasChance = gasChance;
      this.hasGeyser = hasGeyser;
   }

   public WorldGenPetroleum setOptions(
      double noiseScaleFrom,
      double noiseScaleAdd,
      double boundFrom,
      double boundAdd,
      double randMultFrom,
      double randMultAdd,
      double noiseMultFrom,
      double noiseMultAdd,
      double flattingFrom,
      double flattingAdd
   ) {
      this.noiseScaleFrom = noiseScaleFrom;
      this.noiseScaleAdd = noiseScaleAdd;
      this.boundFrom = boundFrom;
      this.boundAdd = boundAdd;
      this.randMultFrom = randMultFrom;
      this.randMultAdd = randMultAdd;
      this.noiseMultFrom = noiseMultFrom;
      this.noiseMultAdd = noiseMultAdd;
      this.flattingFrom = flattingFrom;
      this.flattingAdd = flattingAdd;
      return this;
   }

   public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
      int dim = world.provider.getDimension();
      if (this.dimension == dim && GenerationHelper.canGenerateDungeonAtChunkPos(chunkX, chunkZ, world.getSeed(), this.rarity, 6)) {
         if (this.lastUsedSeed != world.getSeed() || this.noisegenerator3d == null) {
            this.noisegenerator3d = new NoiseGenerator3D(world.getSeed());
            this.lastUsedSeed = world.getSeed();
         }

         int x = chunkX * 16 + random.nextInt(16);
         int z = chunkZ * 16 + random.nextInt(16);
         int height = GetMOP.getTrueHeight(world, new BlockPos(x, 255, z)).getY();
         int radius = this.radiusMin + random.nextInt(this.radiusMax - this.radiusMin + 1);
         double finalHeight = radius > height - radius ? height / 2 : GetMOP.partial((float)(height - radius), (float)radius, random.nextFloat());
         BlockPos poss = new BlockPos(x, finalHeight, z);
         IBlockState centerState = world.getBlockState(poss);
         if (!centerState.getMaterial().isLiquid() && centerState.getMaterial() != Material.AIR) {
            double noiseScale = this.noiseScaleFrom + random.nextDouble() * this.noiseScaleAdd;
            double bound = this.boundFrom + random.nextDouble() * this.boundAdd;
            double randMult = this.randMultFrom + random.nextDouble() * this.randMultAdd;
            double noiseMult = this.noiseMultFrom + random.nextDouble() * this.noiseMultAdd;
            double flatting = this.flattingFrom + random.nextDouble() * this.flattingAdd;
            int cracks = random.nextInt(5);
            boolean hasGas = false;
            int gasHeight = 255;
            if (this.gasState != null) {
               hasGas = random.nextFloat() < this.gasChance;
               if (hasGas) {
                  if (random.nextFloat() < 0.5F) {
                     gasHeight = (int)(poss.getY() + (height - poss.getY()) * random.nextDouble());
                  } else {
                     gasHeight = (int)(poss.getY() + radius * random.nextDouble());
                  }
               }
            }

            runGenerator(
               this.petrolState,
               this.wallState,
               hasGas ? this.gasState : this.petrolState,
               noiseScale,
               bound,
               randMult,
               noiseMult,
               flatting,
               this.blocksToReplace,
               world,
               random,
               poss.getX(),
               poss.getY(),
               poss.getZ(),
               radius,
               this.noisegenerator3d,
               cracks,
               gasHeight,
               this.hasGeyser
            );
         }
      }
   }

   public static void runGenerator(
      IBlockState blockToGen,
      IBlockState wall,
      IBlockState gas,
      double noiseScale,
      double bound,
      double randMult,
      double noiseMult,
      double flatting,
      Predicate<IBlockState> blockToReplace,
      World world,
      Random rand,
      int x,
      int y,
      int z,
      int radius,
      NoiseGenerator3D generator3d,
      int cracks,
      int gasHeight,
      boolean hasGeyser
   ) {
      for (int xx = -radius; xx <= radius; xx++) {
         for (int yy = -radius; yy <= radius; yy++) {
            for (int zz = -radius; zz <= radius; zz++) {
               BlockPos pos = new BlockPos(xx + x, yy + y, zz + z);
               double dist = Math.sqrt(xx * xx + yy * yy / flatting + zz * zz);
               double weight0 = radius / bound - dist / bound;
               double weight1 = Math.max(weight0, 0.0);
               double weight2 = generator3d.getValue(pos.getX() * noiseScale, pos.getY() * noiseScale, pos.getZ() * noiseScale)
                  * noiseMult;
               double weight3 = (weight2 + weight0 + weight2 * weight1) / 2.0;
               if (rand.nextFloat() * randMult < weight3 && blockToReplace.apply(world.getBlockState(pos))) {
                  world.setBlockState(pos, pos.getY() > gasHeight ? gas : blockToGen, 2);
               }
            }
         }
      }

      for (int xx = -radius; xx <= radius; xx++) {
         for (int yy = -radius; yy <= radius; yy++) {
            for (int zzx = -radius; zzx <= radius; zzx++) {
               BlockPos pos = new BlockPos(xx + x, yy + y, zzx + z);
               IBlockState state = world.getBlockState(pos);
               if ((state.getBlock() == blockToGen.getBlock() || state.getBlock() == gas.getBlock())
                  && collidesWithSoftBlock(world, pos, blockToGen.getBlock(), gas.getBlock())) {
                  world.setBlockState(pos, wall, 2);
               }
            }
         }
      }

      int maxRadiusCracksSq = (radius + 5) * (radius + 5);
      if (cracks > 0) {
         for (int i = 0; i < cracks; i++) {
            Vec3d vec = new Vec3d(x + (rand.nextDouble() - 0.5) * radius, y, z + (rand.nextDouble() - 0.5) * radius);
            Vec3d direction = new Vec3d(rand.nextDouble() - 0.5, 1.0 + rand.nextDouble(), rand.nextDouble() - 0.5).normalize();

            for (int j = 0; j < 250; j++) {
               BlockPos pos = new BlockPos(vec);
               IBlockState blockState = world.getBlockState(pos);
               if (isSoft(blockState, blockToGen.getBlock(), gas.getBlock())) {
                  if (!hasGeyser) {
                     break;
                  }

                  IBlockState toPlaceGeyser = pos.getY() > gasHeight ? gas : blockToGen;
                  int lmax = rand.nextInt(3) + 1;

                  for (int k = 0; k < 255; k++) {
                     world.setBlockState(pos, toPlaceGeyser, 2);
                     pos = pos.up();
                     if (!world.getBlockState(pos).getMaterial().isLiquid()) {
                        if (--lmax <= 0) {
                           break;
                        }
                     }
                  }
                  break;
               }

               if (blockToReplace.apply(blockState)) {
                  world.setBlockState(pos, pos.getY() > gasHeight ? gas : blockToGen, 2);
               }

               vec = vec.add(direction);
               if (rand.nextFloat() < 0.1F) {
                  direction = new Vec3d(rand.nextDouble() - 0.5, 0.4 + rand.nextDouble(), rand.nextDouble() - 0.5).normalize();
               }
            }
         }
      }
   }

   public static boolean collidesWithSoftBlock(World world, BlockPos pos, Block toGen, Block gas) {
      return isSoft(world.getBlockState(pos.up()), toGen, gas)
         || isSoft(world.getBlockState(pos.down()), toGen, gas)
         || isSoft(world.getBlockState(pos.west()), toGen, gas)
         || isSoft(world.getBlockState(pos.south()), toGen, gas)
         || isSoft(world.getBlockState(pos.north()), toGen, gas)
         || isSoft(world.getBlockState(pos.east()), toGen, gas);
   }

   public static boolean isSoft(IBlockState state, Block toGen, Block gas) {
      return state.getBlock() != toGen && state.getBlock() != gas ? !state.isFullCube() : false;
   }
}
