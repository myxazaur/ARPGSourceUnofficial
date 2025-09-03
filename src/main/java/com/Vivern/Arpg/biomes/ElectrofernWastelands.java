//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.biomes;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class ElectrofernWastelands extends Biome {
   public ElectrofernWastelands() {
      super(new BiomeProperties("Electrofern Wastelands").setBaseHeight(0.0F).setHeightVariation(0.0F).setTemperature(0.45F));
      this.topBlock = Blocks.AIR.getDefaultState();
      this.fillerBlock = Blocks.AIR.getDefaultState();
      this.decorator = new ElectrofernWastelandsDecorator();
   }
}
