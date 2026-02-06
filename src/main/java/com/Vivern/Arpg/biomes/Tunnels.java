package com.Vivern.Arpg.biomes;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class Tunnels extends Biome {
   public Tunnels() {
      super(new BiomeProperties("Tonnels").setBaseHeight(0.0F).setHeightVariation(0.0F).setTemperature(0.0F));
      this.topBlock = Blocks.STONE.getDefaultState();
      this.fillerBlock = Blocks.STONE.getDefaultState();
      this.decorator = new TunnelsDecorator();
   }
}
