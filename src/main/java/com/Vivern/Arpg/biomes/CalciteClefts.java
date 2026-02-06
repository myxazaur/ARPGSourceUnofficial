package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.main.BlocksRegister;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class CalciteClefts extends Biome {
   public CalciteClefts() {
      super(new BiomeProperties("Calcite clefts").setBaseHeight(0.0F).setHeightVariation(0.0F).setTemperature(0.5F));
      this.topBlock = BlocksRegister.CALCITE.getDefaultState();
      this.fillerBlock = BlocksRegister.CALCITE.getDefaultState();
      this.decorator = new CalciteCleftsDecorator();
   }
}
