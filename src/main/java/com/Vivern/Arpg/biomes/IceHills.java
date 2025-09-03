//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.main.BlocksRegister;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class IceHills extends Biome {
   public IceHills() {
      super(new BiomeProperties("Ice hills").setBaseHeight(0.5F).setHeightVariation(0.5F).setTemperature(-1.0F).setWaterColor(10804223));
      this.topBlock = Blocks.SNOW.getDefaultState();
      this.fillerBlock = BlocksRegister.FROZENSTONE.getDefaultState();
      this.decorator = new IceHillsDecorator();
   }
}
