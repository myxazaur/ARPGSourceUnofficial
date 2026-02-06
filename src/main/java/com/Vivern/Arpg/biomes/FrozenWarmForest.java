package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.main.BlocksRegister;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class FrozenWarmForest extends Biome {
   public FrozenWarmForest() {
      super(new BiomeProperties("Frozen warm forest").setBaseHeight(0.5F).setHeightVariation(0.3F).setTemperature(-0.3F).setWaterColor(10804223));
      this.topBlock = Blocks.SNOW.getDefaultState();
      this.fillerBlock = BlocksRegister.SNOWICE.getDefaultState();
      this.decorator = new FrozenWarmForestDecorator();
   }
}
