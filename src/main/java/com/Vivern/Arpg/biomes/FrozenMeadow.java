//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.main.BlocksRegister;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class FrozenMeadow extends Biome {
   public FrozenMeadow() {
      super(new BiomeProperties("Frozen meadow").setBaseHeight(0.46F).setHeightVariation(0.15F).setTemperature(-0.3F).setWaterColor(10804223));
      this.topBlock = Blocks.SNOW.getDefaultState();
      this.fillerBlock = BlocksRegister.SNOWICE.getDefaultState();
      this.decorator = new FrozenMeadowDecorator();
   }
}
