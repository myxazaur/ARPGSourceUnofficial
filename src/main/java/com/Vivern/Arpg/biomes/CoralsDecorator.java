//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.blocks.GiantShell;
import com.Vivern.Arpg.dimensions.aquatica.AquaticaChunkGenerator;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenCoral;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenMetallicCoral;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenMiniCoral;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenSpread;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.ChunkGeneratorSettings.Factory;
import net.minecraft.world.gen.feature.WorldGenerator;

class CoralsDecorator extends BiomeDecorator {
   public NoiseGeneratorPerlin perlin = null;
   public long perlinseed = 0L;
   public WorldGenSpread seagrass = new WorldGenSpread(BlocksRegister.SEAGRASS, 35, 6, 3, null, true);
   public WorldGenCoral coralGenerator = new WorldGenCoral(0.6F, 0.8F, null);
   public WorldGenMetallicCoral coralOreGenerator = new WorldGenMetallicCoral(16, true);
   public WorldGenMiniCoral[] normalCorals = new WorldGenMiniCoral[]{
      new WorldGenMiniCoral(BlocksRegister.MINICORALBROWN, 33, 4, 3),
      new WorldGenMiniCoral(BlocksRegister.MINICORALPURPLE, 33, 4, 3),
      new WorldGenMiniCoral(BlocksRegister.MINICORALRED, 33, 4, 3),
      new WorldGenMiniCoral(BlocksRegister.MINICORALWHITE, 33, 4, 3),
      new WorldGenMiniCoral(BlocksRegister.MINICORALWHITE2, 33, 4, 3)
   };
   public WorldGenMiniCoral[] faviaCorals = new WorldGenMiniCoral[]{
      new WorldGenMiniCoral(BlocksRegister.MINICORALFAVIABLUE, 24, 3, 3),
      new WorldGenMiniCoral(BlocksRegister.MINICORALFAVIAGREEN, 24, 3, 3),
      new WorldGenMiniCoral(BlocksRegister.MINICORALFAVIARED, 24, 3, 3),
      new WorldGenMiniCoral(BlocksRegister.MINICORALFAVIAYELLOW, 24, 3, 3)
   };
   public WorldGenMiniCoral[] bigCorals = new WorldGenMiniCoral[]{
      new WorldGenMiniCoral(BlocksRegister.MINICORALBROWNBIG, 30, 5, 1),
      new WorldGenMiniCoral(BlocksRegister.MINICORALPURPLEBIG, 30, 5, 1),
      new WorldGenMiniCoral(BlocksRegister.MINICORALREDBIG, 30, 5, 1),
      new WorldGenMiniCoral(BlocksRegister.MINICORALWHITE2BIG, 30, 5, 1),
      new WorldGenMiniCoral(BlocksRegister.MINICORALWHITEBIG, 30, 5, 1)
   };
   public WorldGenMiniCoral[] corallimorphaCorals = new WorldGenMiniCoral[]{
      new WorldGenMiniCoral(BlocksRegister.CORALLIMORPHABLUE, 18, 4, 2).setcorallimorpha(),
      new WorldGenMiniCoral(BlocksRegister.CORALLIMORPHABROWN, 18, 4, 2).setcorallimorpha(),
      new WorldGenMiniCoral(BlocksRegister.CORALLIMORPHAGREEN, 18, 4, 2).setcorallimorpha(),
      new WorldGenMiniCoral(BlocksRegister.CORALLIMORPHALILAC, 18, 4, 2).setcorallimorpha(),
      new WorldGenMiniCoral(BlocksRegister.CORALLIMORPHAPINK, 18, 4, 2).setcorallimorpha(),
      new WorldGenMiniCoral(BlocksRegister.CORALLIMORPHARED, 18, 4, 2).setcorallimorpha(),
      new WorldGenMiniCoral(BlocksRegister.CORALLIMORPHAYELLOW, 18, 4, 2).setcorallimorpha()
   };
   public WorldGenMiniCoral[] glowingCorals = new WorldGenMiniCoral[]{
      new WorldGenMiniCoral(BlocksRegister.ACTINIFORABLUE, 21, 5, 5),
      new WorldGenMiniCoral(BlocksRegister.ACTINIFORARED, 21, 5, 5),
      new WorldGenMiniCoral(BlocksRegister.ACTINIFORAYELLOW, 21, 5, 5),
      new WorldGenMiniCoral(BlocksRegister.ACTINIFORABLUEBIG, 21, 4, 3),
      new WorldGenMiniCoral(BlocksRegister.ACTINIFORAREDBIG, 20, 3, 2)
   };
   public WorldGenMiniCoral[] brainCorals = new WorldGenMiniCoral[]{new WorldGenMiniCoral(BlocksRegister.MINICORALBRAIN, 16, 5, 2)};

   public static int getNBcount2Dquad(boolean[][] matrix, int x, int z, int addition16X2) {
      int count = 0;
      if (x + 1 < addition16X2 && matrix[x + 1][z]) {
         count++;
      }

      if (x + 1 < addition16X2 && z + 1 < addition16X2 && matrix[x + 1][z + 1]) {
         count++;
      }

      if (z + 1 < addition16X2 && matrix[x][z + 1]) {
         count++;
      }

      if (x - 1 >= 0 && matrix[x - 1][z]) {
         count++;
      }

      if (x - 1 >= 0 && z - 1 >= 0 && matrix[x - 1][z - 1]) {
         count++;
      }

      if (z - 1 >= 0 && matrix[x][z - 1]) {
         count++;
      }

      if (x + 1 < addition16X2 && z - 1 >= 0 && matrix[x + 1][z - 1]) {
         count++;
      }

      if (z + 1 < addition16X2 && x - 1 >= 0 && matrix[x - 1][z + 1]) {
         count++;
      }

      return count;
   }

   public void decorate(World world, Random random, Biome biome, BlockPos decpos) {
      if (this.decorating) {
         throw new RuntimeException("Already decorating");
      } else {
         if (this.perlin == null || this.perlinseed != world.getSeed()) {
            this.perlin = new NoiseGeneratorPerlin(new Random(world.getSeed()), 2);
            this.perlinseed = world.getSeed();
         }

         Block coralBlock = BlocksRegister.STROMATOLITE;
         Block seastoneBlock = BlocksRegister.SEASTONE;
         int iters = 27;
         int addition = 4;
         int addition16X2 = 16 + addition * 2;
         BlockPos offsetpos = decpos.add(8, 0, 8);
         boolean[][][] matrix = new boolean[iters][addition16X2][addition16X2];

         for (int ix = 0; ix < addition16X2; ix++) {
            for (int iz = 0; iz < addition16X2; iz++) {
               matrix[0][ix][iz] = this.perlin.getValue(offsetpos.getX() + ix, offsetpos.getZ() + iz) < 0.0;
            }
         }

         for (int it = 1; it < iters; it++) {
            int addRule = 5;
            int removeRule = 3;
            if (it == 6) {
               addRule = 3;
            }

            if (it == 7) {
               removeRule = 5;
            }

            if (it == 8) {
               removeRule = 5;
            }

            if (it == 10) {
               removeRule = 5;
            }

            if (it == 14) {
               addRule = 3;
            }

            if (it == 15) {
               addRule = 3;
            }

            if (it == 16) {
               removeRule = 5;
            }

            if (it == 17) {
               removeRule = 5;
            }

            if (it == 20) {
               addRule = 3;
            }

            if (it == 21) {
               addRule = 2;
            }

            if (it == 22) {
               removeRule = 6;
            }

            if (it == 23) {
               removeRule = 5;
            }

            if (it == 25) {
               addRule = 3;
               removeRule = 5;
            }

            for (int ix = 0; ix < addition16X2; ix++) {
               for (int iz = 0; iz < addition16X2; iz++) {
                  if (!matrix[it - 1][ix][iz]) {
                     if (getNBcount2Dquad(matrix[it - 1], ix, iz, addition16X2) >= addRule) {
                        matrix[it][ix][iz] = true;
                     } else {
                        matrix[it][ix][iz] = false;
                     }
                  } else if (getNBcount2Dquad(matrix[it - 1], ix, iz, addition16X2) <= removeRule) {
                     matrix[it][ix][iz] = false;
                  } else {
                     matrix[it][ix][iz] = true;
                  }
               }
            }
         }

         for (int ix = 0; ix < addition16X2; ix++) {
            for (int izx = 0; izx < addition16X2; izx++) {
               BlockPos posxz = new BlockPos(offsetpos.getX() + ix - addition, 150, offsetpos.getZ() + izx - addition);
               posxz = getTopBlock(world, posxz);

               for (int it = 0; it < iters; it++) {
                  if (matrix[it][ix][izx]) {
                     IBlockState stb;
                     if (it <= random.nextInt(4)) {
                        stb = seastoneBlock.getDefaultState();
                     } else {
                        stb = coralBlock.getDefaultState();
                     }

                     world.setBlockState(posxz.add(0, it - 2, 0), stb, 2);
                  }
               }
            }
         }

         if (random.nextFloat() < 0.75) {
            BlockPos position = GetMOP.getTrueHeight(
               world, new BlockPos(decpos.getX() + 8 + random.nextInt(16), 130, decpos.getZ() + 8 + random.nextInt(16))
            );
            this.coralGenerator.generate(world, random, position.up());
         }

         for (int isw = 0; isw < 12; isw++) {
            int offsetX = random.nextInt(8) - random.nextInt(8);
            int offsetZ = random.nextInt(8) - random.nextInt(8);
            EnumFacing facing = EnumFacing.VALUES[random.nextInt(6)];
            int dist = 8;
            if (facing == EnumFacing.EAST) {
               dist -= offsetX;
            } else if (facing == EnumFacing.WEST) {
               dist += offsetX;
            } else if (facing == EnumFacing.SOUTH) {
               dist -= offsetZ;
            } else if (facing == EnumFacing.EAST) {
               dist += offsetZ;
            }

            GetMOP.BlockTraceResult res = GetMOP.blockTrace(
               world, new BlockPos(decpos.getX() + 16 + offsetX, 85 + isw * 2, decpos.getZ() + 16 + offsetZ), facing, dist, null
            );
            if (res != null) {
               if (res.impactState.getBlock() == Blocks.WATER || res.impactState.getBlock() == Blocks.FLOWING_WATER) {
                  this.coralGenerator.generate(world, random, res.pos);
               } else if (res.prevState.getBlock() == Blocks.WATER || res.prevState.getBlock() == Blocks.FLOWING_WATER) {
                  this.coralGenerator.generate(world, random, res.prevPos);
               }
            }
         }

         if (random.nextFloat() < 0.16) {
            int height = 4 + random.nextInt(7);
            BlockPos position = GetMOP.getTrueHeight(
               world, new BlockPos(decpos.getX() + 8 + random.nextInt(16), 130, decpos.getZ() + 8 + random.nextInt(16))
            );
            AquaticaChunkGenerator.genCellularCoral(
               world,
               position.getX(),
               position.getY(),
               position.getZ(),
               random,
               height * 2,
               height,
               AquaticaChunkGenerator.corals[random.nextInt(5)].getDefaultState()
            );
         }

         int offsetXx = random.nextInt(8) - random.nextInt(8);
         int offsetZx = random.nextInt(8) - random.nextInt(8);
         EnumFacing facingx = EnumFacing.VALUES[random.nextInt(6)];
         int distx = 8;
         if (facingx == EnumFacing.EAST) {
            distx -= offsetXx;
         } else if (facingx == EnumFacing.WEST) {
            distx += offsetXx;
         } else if (facingx == EnumFacing.SOUTH) {
            distx -= offsetZx;
         } else if (facingx == EnumFacing.EAST) {
            distx += offsetZx;
         }

         GetMOP.BlockTraceResult res = GetMOP.blockTrace(
            world, new BlockPos(decpos.getX() + 16 + offsetXx, 85 + random.nextInt(25), decpos.getZ() + 16 + offsetZx), facingx, distx, null
         );
         if (res != null) {
            if ((res.impactState.getBlock() == Blocks.WATER || res.impactState.getBlock() == Blocks.FLOWING_WATER)
               && res.prevState.getBlock() == coralBlock) {
               this.coralOreGenerator.size = 8 + random.nextInt(20);
               this.coralOreGenerator.generate(world, random, res.pos);
            } else if ((res.prevState.getBlock() == Blocks.WATER || res.prevState.getBlock() == Blocks.FLOWING_WATER)
               && res.impactState.getBlock() == coralBlock) {
               this.coralOreGenerator.size = 8 + random.nextInt(20);
               this.coralOreGenerator.generate(world, random, res.prevPos);
            }
         }

         if (random.nextFloat() < 0.25) {
            BlockPos position = GetMOP.getTrueHeight(
               world, new BlockPos(decpos.getX() + 8 + random.nextInt(16), 140, decpos.getZ() + 8 + random.nextInt(16))
            );
            AquaticaChunkGenerator.genSeaweed(
               world,
               position.getX(),
               position.getY() + 1,
               position.getZ(),
               4 + random.nextInt(8),
               BlocksRegister.SEAWEEDBLOCK.getDefaultState()
            );
         }

         if (random.nextFloat() < 0.55) {
            BlockPos position = GetMOP.getTrueHeight(
               world, new BlockPos(decpos.getX() + 8 + random.nextInt(16), 90, decpos.getZ() + 8 + random.nextInt(16))
            );
            AquaticaChunkGenerator.genSeaweed(
               world,
               position.getX(),
               position.getY() + 1,
               position.getZ(),
               5 + random.nextInt(8),
               BlocksRegister.SEAWEEDBLOCK.getDefaultState()
            );
         }

         if (random.nextFloat() < 0.7) {
            offsetXx = random.nextInt(16) + 8;
            offsetZx = random.nextInt(16) + 8;
            this.seagrass.generate(world, random, world.getTopSolidOrLiquidBlock(decpos.add(offsetXx, 0, offsetZx)));
         }

         if (random.nextFloat() < 0.4) {
            BlockPos position = GetMOP.getTrueHeight(
                  world, new BlockPos(decpos.getX() + 8 + random.nextInt(16), 130, decpos.getZ() + 8 + random.nextInt(16))
               )
               .up();
            if (position.getY() != AquaticaChunkGenerator.sealvl) {
               boolean w = world.getBlockState(position).getMaterial() == Material.WATER;
               world.setBlockState(
                  position, BlocksRegister.GIANTSHELL.getDefaultState().withProperty(GiantShell.TYPE, random.nextInt(8)).withProperty(GiantShell.WET, w), 2
               );
            }
         }

         for (int isw = 0; isw < 15; isw++) {
            offsetZx = random.nextInt(8) - random.nextInt(8);
            int offsetZxx = random.nextInt(8) - random.nextInt(8);
            EnumFacing facingxx = EnumFacing.VALUES[random.nextInt(6)];
            int distxx = 8;
            if (facingxx == EnumFacing.EAST) {
               distxx -= offsetZx;
            } else if (facingxx == EnumFacing.WEST) {
               distxx += offsetZx;
            } else if (facingxx == EnumFacing.SOUTH) {
               distxx -= offsetZxx;
            } else if (facingxx == EnumFacing.EAST) {
               distxx += offsetZxx;
            }

            GetMOP.BlockTraceResult resx = GetMOP.blockTrace(
               world, new BlockPos(decpos.getX() + 16 + offsetZx, 83 + isw * 2, decpos.getZ() + 16 + offsetZxx), facingxx, distxx, null
            );
            if (resx != null) {
               WorldGenerator[] generators = null;
               int r = random.nextInt(100);
               WorldGenMiniCoral[] var59;
               if (r < 8) {
                  var59 = this.brainCorals;
               } else if (r < 22) {
                  var59 = this.faviaCorals;
               } else if (r < 34) {
                  var59 = this.corallimorphaCorals;
               } else if (r < 55) {
                  var59 = this.bigCorals;
               } else if (r < 70) {
                  var59 = this.glowingCorals;
               } else {
                  var59 = this.normalCorals;
               }

               if (resx.impactState.getBlock() == Blocks.WATER || resx.impactState.getBlock() == Blocks.FLOWING_WATER) {
                  var59[random.nextInt(var59.length)].generate(world, random, resx.pos);
               } else if (resx.prevState.getBlock() == Blocks.WATER || resx.prevState.getBlock() == Blocks.FLOWING_WATER) {
                  var59[random.nextInt(var59.length)].generate(world, random, resx.prevPos);
               }
            }
         }

         for (int isw = 0; isw < 6; isw++) {
            offsetZx = random.nextInt(8) - random.nextInt(8);
            int offsetZxxx = random.nextInt(8) - random.nextInt(8);
            EnumFacing facingxxx = EnumFacing.VALUES[random.nextInt(6)];
            int distxxx = 8;
            if (facingxxx == EnumFacing.EAST) {
               distxxx -= offsetZx;
            } else if (facingxxx == EnumFacing.WEST) {
               distxxx += offsetZx;
            } else if (facingxxx == EnumFacing.SOUTH) {
               distxxx -= offsetZxxx;
            } else if (facingxxx == EnumFacing.EAST) {
               distxxx += offsetZxxx;
            }

            GetMOP.BlockTraceResult resx = GetMOP.blockTrace(
               world, new BlockPos(decpos.getX() + 16 + offsetZx, 85 + isw * 4, decpos.getZ() + 16 + offsetZxxx), facingxxx, distxxx, null
            );
            if (resx != null) {
               if (resx.impactState.getBlock() == Blocks.WATER || resx.impactState.getBlock() == Blocks.FLOWING_WATER) {
                  this.seagrass.generate(world, random, resx.pos);
               } else if (resx.prevState.getBlock() == Blocks.WATER || resx.prevState.getBlock() == Blocks.FLOWING_WATER) {
                  this.seagrass.generate(world, random, resx.prevPos);
               }
            }
         }

         this.chunkProviderSettings = Factory.jsonToFactory(world.getWorldInfo().getGeneratorOptions()).build();
         this.chunkPos = decpos;
         this.decorating = false;
      }
   }

   public static BlockPos getTopBlock(World world, BlockPos pos) {
      Chunk chunk = world.getChunk(pos);

      for (int y = pos.getY(); y > 0; y--) {
         Block block = chunk.getBlockState(pos.getX(), y, pos.getZ()).getBlock();
         if (block == Blocks.SAND || block == BlocksRegister.SHELLROCK) {
            return new BlockPos(pos.getX(), y, pos.getZ());
         }
      }

      return new BlockPos(pos.getX(), 76, pos.getZ());
   }
}
