package com.vivern.arpg.biomes;

import com.vivern.arpg.dimensions.aquatica.AquaticaChunkGenerator;
import com.vivern.arpg.dimensions.generationutils.WorldGenCoral;
import com.vivern.arpg.dimensions.generationutils.WorldGenMiniCoral;
import com.vivern.arpg.dimensions.generationutils.WorldGenSpread;
import com.vivern.arpg.main.BiomesRegister;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.GetMOP;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.ChunkGeneratorSettings.Factory;
import net.minecraft.world.gen.feature.WorldGenClay;
import net.minecraft.world.gen.feature.WorldGenerator;

class SeaweedBayDecorator extends BiomeDecorator {
   public NoiseGeneratorPerlin perlin = null;
   public NoiseGeneratorPerlin perlin2 = null;
   public NoiseGeneratorPerlin perlin3 = null;
   public long perlinseed = 0L;
   public WorldGenMiniCoral[] normalCorals = new WorldGenMiniCoral[]{
      new WorldGenMiniCoral(BlocksRegister.MINICORALBROWN, 23, 5, 3),
      new WorldGenMiniCoral(BlocksRegister.MINICORALPURPLE, 23, 5, 3),
      new WorldGenMiniCoral(BlocksRegister.MINICORALRED, 23, 5, 3),
      new WorldGenMiniCoral(BlocksRegister.MINICORALWHITE, 23, 5, 3),
      new WorldGenMiniCoral(BlocksRegister.MINICORALWHITE2, 23, 5, 3)
   };
   public WorldGenMiniCoral[] faviaCorals = new WorldGenMiniCoral[]{
      new WorldGenMiniCoral(BlocksRegister.MINICORALFAVIABLUE, 4, 3, 3),
      new WorldGenMiniCoral(BlocksRegister.MINICORALFAVIAGREEN, 4, 3, 3),
      new WorldGenMiniCoral(BlocksRegister.MINICORALFAVIARED, 4, 3, 3),
      new WorldGenMiniCoral(BlocksRegister.MINICORALFAVIAYELLOW, 4, 3, 3)
   };
   public WorldGenMiniCoral[] bigCorals = new WorldGenMiniCoral[]{
      new WorldGenMiniCoral(BlocksRegister.MINICORALBROWNBIG, 14, 5, 2),
      new WorldGenMiniCoral(BlocksRegister.MINICORALPURPLEBIG, 14, 5, 2),
      new WorldGenMiniCoral(BlocksRegister.MINICORALREDBIG, 14, 5, 2),
      new WorldGenMiniCoral(BlocksRegister.MINICORALWHITE2BIG, 14, 5, 2),
      new WorldGenMiniCoral(BlocksRegister.MINICORALWHITEBIG, 14, 5, 2)
   };
   public WorldGenMiniCoral[] corallimorphaCorals = new WorldGenMiniCoral[]{
      new WorldGenMiniCoral(BlocksRegister.CORALLIMORPHABLUE, 5, 5, 2).setcorallimorpha(),
      new WorldGenMiniCoral(BlocksRegister.CORALLIMORPHABROWN, 5, 5, 2).setcorallimorpha(),
      new WorldGenMiniCoral(BlocksRegister.CORALLIMORPHAGREEN, 5, 5, 2).setcorallimorpha(),
      new WorldGenMiniCoral(BlocksRegister.CORALLIMORPHALILAC, 5, 5, 2).setcorallimorpha(),
      new WorldGenMiniCoral(BlocksRegister.CORALLIMORPHAPINK, 5, 5, 2).setcorallimorpha(),
      new WorldGenMiniCoral(BlocksRegister.CORALLIMORPHARED, 5, 5, 2).setcorallimorpha(),
      new WorldGenMiniCoral(BlocksRegister.CORALLIMORPHAYELLOW, 5, 5, 2).setcorallimorpha()
   };
   public WorldGenMiniCoral[] glowingCorals = new WorldGenMiniCoral[]{
      new WorldGenMiniCoral(BlocksRegister.ACTINIFORABLUE, 11, 7, 5),
      new WorldGenMiniCoral(BlocksRegister.ACTINIFORARED, 11, 7, 5),
      new WorldGenMiniCoral(BlocksRegister.ACTINIFORAYELLOW, 11, 7, 5),
      new WorldGenMiniCoral(BlocksRegister.ACTINIFORABLUEBIG, 11, 5, 3),
      new WorldGenMiniCoral(BlocksRegister.ACTINIFORAREDBIG, 10, 4, 2)
   };
   public WorldGenMiniCoral[] brainCorals = new WorldGenMiniCoral[]{new WorldGenMiniCoral(BlocksRegister.MINICORALBRAIN, 4, 5, 2)};
   public WorldGenCoral coralGenerator = new WorldGenCoral(1.0F, 1.0F, null);
   public WorldGenerator clayGen = new WorldGenClay(4);
   public WorldGenSpread seagrass = new WorldGenSpread(BlocksRegister.SEAGRASS, 60, 7, 1, null, true);
   public WorldGenSpread seagrass2 = new WorldGenSpread(BlocksRegister.SEAGRASS, 30, 5, 1, null, true);

   public void decorate(World world, Random random, Biome biome, BlockPos decpos) {
      if (this.decorating) {
         throw new RuntimeException("Already decorating");
      } else {
         if (this.perlin == null || this.perlin2 == null || this.perlin3 == null || this.perlinseed != world.getSeed()) {
            this.perlin = new NoiseGeneratorPerlin(new Random(world.getSeed()), 2);
            this.perlin2 = new NoiseGeneratorPerlin(new Random(world.getSeed() + 50L), 2);
            this.perlin3 = new NoiseGeneratorPerlin(new Random(world.getSeed() - 50L), 2);
            this.perlinseed = world.getSeed();
         }

         int range = 16;
         int rangeY = 36;
         double sizeFlat = 12.0;
         double sizeVert = 2.0;
         double sizeOver = 60.0;

         for (int ix = 0; ix < range; ix++) {
            for (int iz = 0; iz < range; iz++) {
               BlockPos pos = GetMOP.getTrueHeight(world, new BlockPos(decpos.getX() + ix + 8, 120, decpos.getZ() + iz + 8));
               if (world.getBiome(pos) == BiomesRegister.SEAWEED_BAY) {
                  for (int iy = 0; iy < rangeY; iy++) {
                     BlockPos finalpos = new BlockPos(pos.getX(), pos.getY() - rangeY + iy + 2, pos.getZ());
                     double d = this.perlin.getValue(finalpos.getX() / sizeFlat, finalpos.getZ() / sizeFlat);
                     double d2 = this.perlin2.getValue(finalpos.getX() / sizeFlat, finalpos.getY() / sizeVert);
                     double noise = d + d2;
                     if (noise
                           > -0.4 + Math.max(this.perlin3.getValue(finalpos.getX() / sizeOver, finalpos.getZ() / sizeOver) + 1.2, 0.0)
                        && !AquaticaChunkGenerator.isCoral(world.getBlockState(finalpos))) {
                        world.setBlockState(finalpos, Blocks.WATER.getDefaultState(), 2);
                     }
                  }
               }
            }
         }

         for (int isw = 0; isw < 2; isw++) {
            int l1 = random.nextInt(16) + 8;
            int i6 = random.nextInt(16) + 8;
            this.seagrass.generate(world, random, world.getTopSolidOrLiquidBlock(decpos.add(l1, 0, i6)));
         }

         int l1 = random.nextInt(16) + 8;
         int i6 = random.nextInt(16) + 8;
         this.clayGen.generate(world, random, world.getTopSolidOrLiquidBlock(decpos.add(l1, 0, i6)));
         if (random.nextFloat() < 0.95) {
            BlockPos position = GetMOP.getTrueHeight(
               world, new BlockPos(decpos.getX() + 8 + random.nextInt(16), 130, decpos.getZ() + 8 + random.nextInt(16))
            );
            this.coralGenerator.generate(world, random, position.up());
         }

         for (int isw = 0; isw < 19; isw++) {
            i6 = random.nextInt(8) - random.nextInt(8);
            int offsetZ = random.nextInt(8) - random.nextInt(8);
            EnumFacing facing = EnumFacing.VALUES[random.nextInt(6)];
            int dist = 8;
            if (facing == EnumFacing.EAST) {
               dist -= i6;
            } else if (facing == EnumFacing.WEST) {
               dist += i6;
            } else if (facing == EnumFacing.SOUTH) {
               dist -= offsetZ;
            } else if (facing == EnumFacing.EAST) {
               dist += offsetZ;
            }

            GetMOP.BlockTraceResult res = GetMOP.blockTrace(
               world, new BlockPos(decpos.getX() + 16 + i6, 42 + isw * 2, decpos.getZ() + 16 + offsetZ), facing, dist, null
            );
            if (res != null) {
               if (res.impactState.getBlock() == Blocks.WATER || res.impactState.getBlock() == Blocks.FLOWING_WATER) {
                  this.coralGenerator.generate(world, random, res.pos);
               } else if (res.prevState.getBlock() == Blocks.WATER || res.prevState.getBlock() == Blocks.FLOWING_WATER) {
                  this.coralGenerator.generate(world, random, res.prevPos);
               }
            }
         }

         if (random.nextFloat() < 0.18) {
            l1 = 4 + random.nextInt(17);
            BlockPos position = GetMOP.getTrueHeight(
               world, new BlockPos(decpos.getX() + 8 + random.nextInt(16), 130, decpos.getZ() + 8 + random.nextInt(16))
            );
            AquaticaChunkGenerator.genCellularCoral(
               world,
               position.getX(),
               position.getY(),
               position.getZ(),
               random,
               l1 * 2,
               l1,
               AquaticaChunkGenerator.corals[random.nextInt(5)].getDefaultState()
            );
         }

         if (random.nextFloat() < 0.165) {
            l1 = 1 + random.nextInt(4);
            i6 = Math.min(l1 - 1, random.nextInt(3));
            BlockPos position = GetMOP.getTrueHeight(
               world, new BlockPos(decpos.getX() + 8 + random.nextInt(16), 130, decpos.getZ() + 8 + random.nextInt(16))
            );
            AquaticaChunkGenerator.genFlatCoral(
               world,
               position.getX(),
               position.getY(),
               position.getZ(),
               random,
               10 + random.nextInt(3),
               l1,
               i6,
               2,
               0.6F + random.nextFloat() / 10.0F,
               4,
               7 + random.nextInt(5)
            );
         }

         if (random.nextFloat() < 0.12) {
            l1 = 1 + random.nextInt(4);
            i6 = Math.min(l1 - 1, random.nextInt(3));
            BlockPos position = GetMOP.getTrueHeight(
               world, new BlockPos(decpos.getX() + 8 + random.nextInt(16), 130, decpos.getZ() + 8 + random.nextInt(16))
            );
            AquaticaChunkGenerator.genFlatCoral(
               world, position.getX(), position.getY(), position.getZ(), random, 11, l1, i6, 2, 0.6F, 4, 9
            );
         }

         if (random.nextFloat() < 0.025) {
            l1 = 1 + random.nextInt(5);
            i6 = Math.min(l1 - 1, random.nextInt(3));
            BlockPos position = GetMOP.getTrueHeight(
               world, new BlockPos(decpos.getX() + 8 + random.nextInt(16), 130, decpos.getZ() + 8 + random.nextInt(16))
            );
            float sizeNoise = random.nextFloat();
            AquaticaChunkGenerator.genFlatCoral(
               world,
               position.getX(),
               position.getY(),
               position.getZ(),
               random,
               7 + (int)(random.nextInt(10) * sizeNoise),
               l1,
               i6,
               2 + random.nextInt(2),
               0.6F + sizeNoise / 5.0F,
               4,
               7 + random.nextInt(5)
            );
         }

         if (random.nextFloat() < 0.225) {
            BlockPos position = GetMOP.getTrueHeight(
               world, new BlockPos(decpos.getX() + 8 + random.nextInt(16), 130, decpos.getZ() + 8 + random.nextInt(16))
            );
            i6 = Math.min(100 - position.getY(), 0);
            int brl = 10 + random.nextInt(30 - i6);
            int count = 50 + random.nextInt(brl) * 2;
            IBlockState coralstate;
            if (random.nextFloat() < 0.8) {
               coralstate = BlocksRegister.CORALWHITE.getDefaultState();
            } else if (random.nextFloat() < 0.5) {
               coralstate = BlocksRegister.CORALRED.getDefaultState();
            } else {
               coralstate = AquaticaChunkGenerator.corals[random.nextInt(4) + 1].getDefaultState();
            }

            AquaticaChunkGenerator.genRecursiveCoral(
               world, position.getX(), position.getY(), position.getZ(), random, count, brl, coralstate
            );
         }

         if (random.nextFloat() < 0.1) {
            BlockPos position = GetMOP.getTrueHeight(
               world, new BlockPos(decpos.getX() + 8 + random.nextInt(16), 130, decpos.getZ() + 8 + random.nextInt(16))
            );
            i6 = Math.min(100 - position.getY(), 0);
            int brl = 10 + random.nextInt(40 - i6);
            int count = 40 + random.nextInt(brl) * 2;
            IBlockState coralstate;
            if (random.nextFloat() < 0.8) {
               coralstate = BlocksRegister.CORALWHITE.getDefaultState();
            } else if (random.nextFloat() < 0.5) {
               coralstate = BlocksRegister.CORALRED.getDefaultState();
            } else if (random.nextFloat() < 0.9) {
               coralstate = AquaticaChunkGenerator.corals[random.nextInt(4) + 1].getDefaultState();
            } else {
               coralstate = BlocksRegister.CORALYELLOW.getDefaultState();
            }

            AquaticaChunkGenerator.genRecursiveCoral(
               world, position.getX(), position.getY(), position.getZ(), random, count, brl, coralstate
            );
         }

         for (int isw = 0; isw < 2; isw++) {
            BlockPos position = GetMOP.getTrueHeight(
               world, new BlockPos(decpos.getX() + 8 + random.nextInt(16), 140, decpos.getZ() + 8 + random.nextInt(16))
            );
            this.seagrass2.generate(world, random, position);
         }

         double dp2 = this.perlin2.getValue(decpos.getX() / 18.0, decpos.getZ() / 18.0);
         double dpCount = Math.max(4.0 + dp2, 0.0);
         if (dpCount > 0.0) {
            for (int isw = 0; isw < dpCount; isw++) {
               BlockPos position = GetMOP.getTrueHeight(
                  world, new BlockPos(decpos.getX() + 8 + random.nextInt(16), 150, decpos.getZ() + 8 + random.nextInt(16))
               );
               AquaticaChunkGenerator.genSeaweed(
                  world,
                  position.getX(),
                  position.getY() + 1,
                  position.getZ(),
                  3 + random.nextInt(50),
                  BlocksRegister.SEAWEEDBLOCK.getDefaultState()
               );
            }
         }

         for (int isw = 0; isw < 5; isw++) {
            BlockPos position = GetMOP.getTrueHeight(
               world, new BlockPos(decpos.getX() + 8 + random.nextInt(16), 104, decpos.getZ() + 8 + random.nextInt(16))
            );
            AquaticaChunkGenerator.genSeaweed(
               world,
               position.getX(),
               position.getY() + 1,
               position.getZ(),
               4 + random.nextInt(7),
               BlocksRegister.SEAWEEDBLOCK.getDefaultState()
            );
         }

         for (int isw = 0; isw < 4; isw++) {
            BlockPos position = GetMOP.getTrueHeight(
               world, new BlockPos(decpos.getX() + 8 + random.nextInt(16), 90 + random.nextInt(40), decpos.getZ() + 8 + random.nextInt(16))
            );
            WorldGenerator[] generators = null;
            int r = random.nextInt(100);
            WorldGenMiniCoral[] var68;
            if (r < 8) {
               var68 = this.brainCorals;
            } else if (r < 22) {
               var68 = this.faviaCorals;
            } else if (r < 34) {
               var68 = this.corallimorphaCorals;
            } else if (r < 55) {
               var68 = this.bigCorals;
            } else if (r < 70) {
               var68 = this.glowingCorals;
            } else {
               var68 = this.normalCorals;
            }

            var68[random.nextInt(var68.length)].generate(world, random, position);
         }

         this.chunkProviderSettings = Factory.jsonToFactory(world.getWorldInfo().getGeneratorOptions()).build();
         this.chunkPos = decpos;
         this.decorating = false;
      }
   }
}
