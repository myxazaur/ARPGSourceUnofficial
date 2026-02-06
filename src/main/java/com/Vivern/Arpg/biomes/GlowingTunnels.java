package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.main.BlocksRegister;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class GlowingTunnels extends Biome {
   public GlowingTunnels() {
      super(new BiomeProperties("Glowing tonnels").setBaseHeight(0.0F).setHeightVariation(0.0F).setTemperature(0.0F));
      this.topBlock = BlocksRegister.DOLERITE.getDefaultState();
      this.fillerBlock = BlocksRegister.DOLERITE.getDefaultState();
      this.decorator = new GlowingTunnelsDecorator();
   }
}
