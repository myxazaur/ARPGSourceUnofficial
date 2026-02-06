package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.main.BlocksRegister;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkGeneratorSettings.Factory;
import net.minecraft.world.gen.feature.WorldGenMinable;

class WaterCavesDecorator extends BiomeDecorator {
   public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
      if (this.decorating) {
         throw new RuntimeException("Already decorating");
      } else {
         this.chunkProviderSettings = Factory.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
         this.chunkPos = pos;
         this.dirtGen = new WorldGenMinable(BlocksRegister.SNOWICE.getDefaultState(), this.chunkProviderSettings.dirtSize);
         this.gravelOreGen = new WorldGenMinable(BlocksRegister.FROZENSTONE.getDefaultState(), this.chunkProviderSettings.gravelSize);
         this.graniteGen = new WorldGenMinable(BlocksRegister.GLACIER.getDefaultState(), this.chunkProviderSettings.graniteSize);
         this.dioriteGen = new WorldGenMinable(Blocks.ICE.getDefaultState(), this.chunkProviderSettings.dioriteSize);
         this.andesiteGen = new WorldGenMinable(BlocksRegister.FROZENCOBBLE.getDefaultState(), this.chunkProviderSettings.andesiteSize);
         this.coalGen = new WorldGenMinable(BlocksRegister.SNOWICE.getDefaultState(), this.chunkProviderSettings.coalSize);
         this.ironGen = new WorldGenMinable(Blocks.ICE.getDefaultState(), this.chunkProviderSettings.ironSize);
         this.goldGen = new WorldGenMinable(Blocks.GOLD_ORE.getDefaultState(), this.chunkProviderSettings.goldSize);
         this.redstoneGen = new WorldGenMinable(BlocksRegister.GLACIER.getDefaultState(), this.chunkProviderSettings.redstoneSize);
         this.diamondGen = new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), this.chunkProviderSettings.diamondSize);
         this.lapisGen = new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), this.chunkProviderSettings.lapisSize);
         this.decorating = false;
      }
   }
}
