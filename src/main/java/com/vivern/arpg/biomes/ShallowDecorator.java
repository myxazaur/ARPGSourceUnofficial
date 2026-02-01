package com.vivern.arpg.biomes;

import com.vivern.arpg.blocks.GiantShell;
import com.vivern.arpg.dimensions.aquatica.AquaticaChunkGenerator;
import com.vivern.arpg.dimensions.generationutils.WorldGenCoral;
import com.vivern.arpg.dimensions.generationutils.WorldGenMiniCoral;
import com.vivern.arpg.dimensions.generationutils.WorldGenSpread;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.GetMOP;
import java.util.Random;
import net.minecraft.block.BlockColored;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkGeneratorSettings.Factory;

class ShallowDecorator extends BiomeDecorator {
   public WorldGenMiniCoral[] faviaCorals = new WorldGenMiniCoral[]{
      new WorldGenMiniCoral(BlocksRegister.MINICORALFAVIABLUE, 8, 6, 2),
      new WorldGenMiniCoral(BlocksRegister.MINICORALFAVIAGREEN, 8, 6, 2),
      new WorldGenMiniCoral(BlocksRegister.MINICORALFAVIARED, 8, 6, 2),
      new WorldGenMiniCoral(BlocksRegister.MINICORALFAVIAYELLOW, 8, 6, 2)
   };
   public WorldGenMiniCoral[] corallimorphaCorals = new WorldGenMiniCoral[]{
      new WorldGenMiniCoral(BlocksRegister.CORALLIMORPHABLUE, 8, 7, 1).setcorallimorpha(),
      new WorldGenMiniCoral(BlocksRegister.CORALLIMORPHABROWN, 9, 7, 1).setcorallimorpha(),
      new WorldGenMiniCoral(BlocksRegister.CORALLIMORPHAGREEN, 8, 7, 1).setcorallimorpha(),
      new WorldGenMiniCoral(BlocksRegister.CORALLIMORPHALILAC, 8, 7, 1).setcorallimorpha(),
      new WorldGenMiniCoral(BlocksRegister.CORALLIMORPHAPINK, 9, 7, 1).setcorallimorpha(),
      new WorldGenMiniCoral(BlocksRegister.CORALLIMORPHARED, 11, 7, 1).setcorallimorpha(),
      new WorldGenMiniCoral(BlocksRegister.CORALLIMORPHAYELLOW, 9, 7, 1).setcorallimorpha()
   };
   public WorldGenCoral coralGenerator1 = new WorldGenCoral(0.1F, 0.5F, BlocksRegister.CORALRED);
   public WorldGenCoral coralGenerator2 = new WorldGenCoral(0.1F, 0.4F, BlocksRegister.CORALORANGE);
   public WorldGenSpread seagrass = new WorldGenSpread(BlocksRegister.SEAGRASS, 35, 7, 1, null, true);

   public void decorate(World world, Random random, Biome biome, BlockPos decpos) {
      if (this.decorating) {
         throw new RuntimeException("Already decorating");
      } else {
         if (random.nextFloat() < 0.04) {
            int ix = random.nextInt(16) + 8;
            int lz = random.nextInt(16) + 8;
            BlockPos fpos = world.getTopSolidOrLiquidBlock(decpos.add(ix, 0, lz));
            int structN = random.nextInt(3) + 1;
            int offsetXZ = 0;
            if (structN == 1) {
               offsetXZ = 4;
            }

            if (structN == 2) {
               offsetXZ = 4;
            }

            if (structN == 3) {
               offsetXZ = 3;
            }

            AquaticaChunkGenerator.placeStruct(world, fpos, random, ":sea_subfossil_" + structN, offsetXZ, 0, random.nextInt(4));
         }

         if (random.nextFloat() < 0.027) {
            int count = 1 + random.nextInt(7);

            for (int i = 0; i < count; i++) {
               BlockPos position = GetMOP.getTrueHeight(
                  world, new BlockPos(decpos.getX() + 8 + random.nextInt(16), 183, decpos.getZ() + 8 + random.nextInt(16))
               );
               int radius = Math.max(6 + random.nextInt(3) - i, 1);
               AquaticaChunkGenerator.generateBigSponge(world, position.up(radius / 2), random, radius, BlocksRegister.LIVINGSPONGE.getDefaultState());
            }
         }

         if (random.nextFloat() < 0.025) {
            int count = 1 + random.nextInt(6);

            for (int i = 0; i < count; i++) {
               BlockPos position = GetMOP.getTrueHeight(
                  world, new BlockPos(decpos.getX() + 8 + random.nextInt(16), 180, decpos.getZ() + 8 + random.nextInt(16))
               );
               int radius = 1 + random.nextInt(3);
               AquaticaChunkGenerator.generateBigSponge(
                  world, position.up(radius / 2 + 1), random, radius, BlocksRegister.LIVINGSPONGE.getDefaultState()
               );
            }
         }

         if (random.nextFloat() < 0.085) {
            BlockPos position = GetMOP.getTrueHeight(
                  world, new BlockPos(decpos.getX() + 8 + random.nextInt(16), 185, decpos.getZ() + 8 + random.nextInt(16))
               )
               .up();
            int heightRmv = Math.min(172 - position.getY(), 0);
            int brl = 10 + random.nextInt(15 - heightRmv);
            int count = 20 - heightRmv + (int)(random.nextInt(brl) * 1.5);
            AquaticaChunkGenerator.genRecursiveCoral(
               world,
               position.getX(),
               position.getY(),
               position.getZ(),
               random,
               count,
               brl,
               BlocksRegister.LIVINGSPONGE.getDefaultState(),
               BlocksRegister.LIVINGSPONGE.getStateFromMeta(1),
               193
            );
         }

         if (random.nextFloat() < 0.13) {
            int count = 1 + random.nextInt(5);

            for (int i = 0; i < count; i++) {
               int ixx = random.nextInt(16) + 8;
               int lzx = random.nextInt(16) + 8;
               BlockPos fposx = world.getTopSolidOrLiquidBlock(decpos.add(ixx, 0, lzx));
               IBlockState state1;
               if (random.nextFloat() < 0.3) {
                  state1 = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.RED);
               } else {
                  state1 = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.byDyeDamage(random.nextInt(16)));
               }

               IBlockState state2;
               if (random.nextFloat() < 0.3) {
                  state2 = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.LIGHT_BLUE);
               } else {
                  state2 = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.byDyeDamage(random.nextInt(16)));
               }

               if (random.nextFloat() < 0.7) {
                  world.setBlockState(fposx, state2, 2);
                  world.setBlockState(fposx.west(), state1, 2);
                  world.setBlockState(fposx.east(), state1, 2);
                  world.setBlockState(fposx.south(), state1, 2);
                  world.setBlockState(fposx.north(), state1, 2);
               } else if (random.nextFloat() < 0.7) {
                  world.setBlockState(fposx, state2, 2);
                  world.setBlockState(fposx.add(1, 0, 1), state1, 2);
                  world.setBlockState(fposx.add(-1, 0, 1), state1, 2);
                  world.setBlockState(fposx.add(-1, 0, -1), state1, 2);
                  world.setBlockState(fposx.add(1, 0, -1), state1, 2);
               } else {
                  int yof = random.nextFloat() < 0.35 ? 1 : 0;
                  world.setBlockState(fposx, state2, 2);
                  world.setBlockState(fposx.add(1, yof, 1), state1, 2);
                  world.setBlockState(fposx.add(-1, yof, 1), state1, 2);
                  world.setBlockState(fposx.add(-1, yof, -1), state1, 2);
                  world.setBlockState(fposx.add(1, yof, -1), state1, 2);
                  world.setBlockState(fposx.west(2), state1, 2);
                  world.setBlockState(fposx.east(2), state1, 2);
                  world.setBlockState(fposx.south(2), state1, 2);
                  world.setBlockState(fposx.north(2), state1, 2);
               }
            }
         }

         if (random.nextFloat() < 0.6) {
            BlockPos position = GetMOP.getTrueHeight(
               world, new BlockPos(decpos.getX() + 8 + random.nextInt(16), 182, decpos.getZ() + 8 + random.nextInt(16))
            );
            if (random.nextFloat() < 0.4) {
               this.coralGenerator1.generate(world, random, position.up());
            } else {
               this.coralGenerator2.generate(world, random, position.up());
            }
         }

         if (random.nextFloat() < 0.1) {
            int ixxx = random.nextInt(16) + 8;
            int lzxx = random.nextInt(16) + 8;
            BlockPos fposxx = world.getTopSolidOrLiquidBlock(decpos.add(ixxx, 0, lzxx));
            this.faviaCorals[random.nextInt(this.faviaCorals.length)].generate(world, random, fposxx);
         }

         if (random.nextFloat() < 0.16) {
            int ixxx = random.nextInt(16) + 8;
            int lzxx = random.nextInt(16) + 8;
            BlockPos fposxx = world.getTopSolidOrLiquidBlock(decpos.add(ixxx, 0, lzxx));
            this.corallimorphaCorals[random.nextInt(this.corallimorphaCorals.length)].generate(world, random, fposxx);
         }

         if (random.nextFloat() < 0.45) {
            int l1 = random.nextInt(16) + 8;
            int i6 = random.nextInt(16) + 8;
            this.seagrass.generate(world, random, world.getTopSolidOrLiquidBlock(decpos.add(l1, 0, i6)));
         }

         if (random.nextFloat() < 0.4) {
            BlockPos position = GetMOP.getTrueHeight(
                  world, new BlockPos(decpos.getX() + 8 + random.nextInt(16), 195, decpos.getZ() + 8 + random.nextInt(16))
               )
               .up();
            if (position.getY() != AquaticaChunkGenerator.sealvl) {
               boolean w = world.getBlockState(position).getMaterial() == Material.WATER;
               world.setBlockState(
                  position, BlocksRegister.GIANTSHELL.getDefaultState().withProperty(GiantShell.TYPE, random.nextInt(8)).withProperty(GiantShell.WET, w), 2
               );
            }
         }

         this.chunkProviderSettings = Factory.jsonToFactory(world.getWorldInfo().getGeneratorOptions()).build();
         this.chunkPos = decpos;
         this.decorating = false;
      }
   }
}
