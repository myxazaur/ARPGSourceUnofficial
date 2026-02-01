package com.vivern.arpg.biomes;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class WaterCaves extends Biome {
   public WaterCaves() {
      super(new BiomeProperties("Water caves").setBaseHeight(0.0F).setHeightVariation(0.0F).setTemperature(0.5F));
      this.topBlock = Blocks.STONE.getDefaultState();
      this.fillerBlock = Blocks.STONE.getDefaultState();
      this.decorator = new WaterCavesDecorator();
   }
}
