//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.blocks.GiantShell;
import com.Vivern.Arpg.dimensions.aquatica.AquaticaChunkGenerator;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorSettings.Factory;

public class AquaticaBeach extends Biome {
   public AquaticaBeach() {
      super(
         new BiomeProperties("Aquatica beach")
            .setBaseHeight(5.5F)
            .setHeightVariation(0.03F)
            .setTemperature(0.95F)
            .setWaterColor(11921407)
            .setRainfall(0.0F)
            .setRainDisabled()
      );
      this.topBlock = Blocks.SAND.getDefaultState();
      this.fillerBlock = Blocks.SAND.getDefaultState();
      this.decorator = new AquaticaBeachDecorator();
   }

   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
      AquaticaChunkGenerator.generateBiomeTerrain(this, worldIn, rand, chunkPrimerIn, x, z, noiseVal);
   }

   class AquaticaBeachDecorator extends BiomeDecorator {
      public void decorate(World world, Random random, Biome biome, BlockPos decpos) {
         if (this.decorating) {
            throw new RuntimeException("Already decorating");
         } else {
            if (random.nextFloat() < 0.2) {
               BlockPos position = GetMOP.getTrueHeight(
                     world, new BlockPos(decpos.getX() + 8 + random.nextInt(16), 215, decpos.getZ() + 8 + random.nextInt(16))
                  )
                  .up();
               if (position.getY() != AquaticaChunkGenerator.sealvl) {
                  boolean w = world.getBlockState(position).getMaterial() == Material.WATER;
                  world.setBlockState(
                     position, BlocksRegister.GIANTSHELL.getDefaultState().withProperty(GiantShell.TYPE, random.nextInt(8)).withProperty(GiantShell.WET, w), 2
                  );
               }
            }

            if (random.nextFloat() < 0.08) {
               BlockPos position = GetMOP.getTrueHeight(
                     world, new BlockPos(decpos.getX() + 8 + random.nextInt(16), 215, decpos.getZ() + 8 + random.nextInt(16))
                  )
                  .up();
               if (position.getY() > AquaticaChunkGenerator.sealvl) {
                  world.setBlockState(position, BlocksRegister.BONESPILE.getDefaultState(), 2);
               }
            }

            this.chunkProviderSettings = Factory.jsonToFactory(world.getWorldInfo().getGeneratorOptions()).build();
            this.chunkPos = decpos;
            this.decorating = false;
         }
      }
   }
}
