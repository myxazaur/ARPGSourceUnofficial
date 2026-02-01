package com.vivern.arpg.biomes;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class UndergroundLake extends Biome {
   public UndergroundLake() {
      super(new BiomeProperties("Underground lake").setBaseHeight(0.0F).setHeightVariation(0.0F).setTemperature(0.5F));
      this.topBlock = Blocks.STONE.getDefaultState();
      this.fillerBlock = Blocks.STONE.getDefaultState();
      this.decorator = new UndergroundLakeDecorator();
   }
}
