package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.main.BlocksRegister;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class FrozenForest extends Biome {
   public FrozenForest() {
      super(new BiomeProperties("Frozen forest").setBaseHeight(0.3F).setTemperature(-3.0F).setSnowEnabled().setWaterColor(10804223));
      this.topBlock = Blocks.SNOW.getDefaultState();
      this.fillerBlock = BlocksRegister.SNOWICE.getDefaultState();
      this.decorator = new FrozenForestDecorator();
   }
}
