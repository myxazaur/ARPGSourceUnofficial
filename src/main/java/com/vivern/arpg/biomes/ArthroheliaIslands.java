package com.vivern.arpg.biomes;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class ArthroheliaIslands extends Biome {
   public ArthroheliaIslands() {
      super(new BiomeProperties("Arthrohelia Islands").setBaseHeight(0.0F).setHeightVariation(0.0F).setTemperature(0.35F));
      this.topBlock = Blocks.AIR.getDefaultState();
      this.fillerBlock = Blocks.AIR.getDefaultState();
      this.decorator = new ArthroheliaIslandsDecorator();
   }
}
