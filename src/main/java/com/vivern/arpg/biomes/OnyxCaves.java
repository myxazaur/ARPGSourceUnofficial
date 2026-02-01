package com.vivern.arpg.biomes;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class OnyxCaves extends Biome {
   public OnyxCaves() {
      super(new BiomeProperties("Onyx caves").setBaseHeight(0.0F).setHeightVariation(0.0F).setTemperature(0.0F));
      this.topBlock = Blocks.STONE.getDefaultState();
      this.fillerBlock = Blocks.STONE.getDefaultState();
      this.decorator = new OnyxCavesDecorator();
   }
}
