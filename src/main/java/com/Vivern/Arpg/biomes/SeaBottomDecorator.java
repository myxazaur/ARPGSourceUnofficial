//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.dimensions.aquatica.AquaticaChunkGenerator;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenSpread;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkGeneratorSettings.Factory;

class SeaBottomDecorator extends BiomeDecorator {
   public WorldGenSpread seagrass = new WorldGenSpread(BlocksRegister.SEAGRASS, 25, 7, 1, null, true);
   public WorldGenSpread urchins = new WorldGenSpread(BlocksRegister.SEAURCHIN, 8, 5, 2, Blocks.GRAVEL, true);

   public void decorate(World world, Random random, Biome biome, BlockPos decpos) {
      if (this.decorating) {
         throw new RuntimeException("Already decorating");
      } else {
         if (random.nextFloat() < 0.21) {
            int ix = random.nextInt(16) + 8;
            int lz = random.nextInt(16) + 8;
            BlockPos fpos = world.getTopSolidOrLiquidBlock(decpos.add(ix, 0, lz));

            for (BlockPos poss : GetMOP.getPosesInsideSphere(fpos.getX(), fpos.getY(), fpos.getZ(), 1 + random.nextInt(3))) {
               world.setBlockState(poss, BlocksRegister.SEASTONE.getDefaultState(), 2);
            }
         }

         if (random.nextFloat() < 0.23) {
            int count = 1 + random.nextInt(6);

            for (int i = 0; i < count; i++) {
               int ix = random.nextInt(16) + 8;
               int lz = random.nextInt(16) + 8;
               BlockPos fpos = world.getTopSolidOrLiquidBlock(decpos.add(ix, 0, lz));
               IBlockState state1 = BlocksRegister.LIVINGSPONGE.getDefaultState();
               IBlockState state2 = BlocksRegister.LIVINGSPONGE.getStateFromMeta(1);
               if (random.nextFloat() < 0.5) {
                  world.setBlockState(fpos, state1, 2);
                  if (random.nextFloat() < 0.5) {
                     world.setBlockState(fpos.up(), state1, 2);
                     if (random.nextFloat() < 0.5) {
                        world.setBlockState(fpos.up(2), state1, 2);
                        if (random.nextFloat() < 0.5) {
                           world.setBlockState(fpos.up(3), state1, 2);
                           if (random.nextFloat() < 0.5) {
                              world.setBlockState(fpos.up(4), state2, 2);
                           }
                        } else {
                           world.setBlockState(fpos.up(3), state2, 2);
                        }
                     } else {
                        world.setBlockState(fpos.up(2), state2, 2);
                     }
                  } else {
                     world.setBlockState(fpos.up(), state2, 2);
                  }
               } else {
                  world.setBlockState(fpos, state2, 2);
               }
            }
         }

         if (random.nextFloat() < 0.25) {
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

         if (random.nextFloat() < 0.7) {
            int l1 = random.nextInt(16) + 8;
            int i6 = random.nextInt(16) + 8;
            this.seagrass.generate(world, random, world.getTopSolidOrLiquidBlock(decpos.add(l1, 0, i6)));
         }

         if (random.nextFloat() < 0.2) {
            int l1 = random.nextInt(16) + 8;
            int i6 = random.nextInt(16) + 8;
            this.urchins.generate(world, random, world.getTopSolidOrLiquidBlock(decpos.add(l1, 0, i6)));
         }

         this.chunkProviderSettings = Factory.jsonToFactory(world.getWorldInfo().getGeneratorOptions()).build();
         this.chunkPos = decpos;
         this.decorating = false;
      }
   }
}
