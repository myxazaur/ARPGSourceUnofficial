//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.biomes;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class DeadLands extends Biome {
   public DeadLands() {
      super(new BiomeProperties("Dead lands").setBaseHeight(0.46F).setHeightVariation(0.18F).setTemperature(0.3F).setWaterColor(10804223));
      this.topBlock = Blocks.GRAVEL.getDefaultState();
      this.fillerBlock = Blocks.STONE.getDefaultState();
   }
}
