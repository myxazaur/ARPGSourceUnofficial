//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.dimensions.stormledge;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public class GenLayerFixStormledge {
   public static GenLayer[] createWorld(long seed) {
      GenLayer biomes = new GenLayerBiomeStormledge(1L);
      biomes = new GenLayerZoom(1000L, biomes);
      biomes = new GenLayerZoom(1001L, biomes);
      biomes = new GenLayerZoom(1002L, biomes);
      biomes = new GenLayerZoom(1003L, biomes);
      biomes = new GenLayerZoom(1004L, biomes);
      biomes = new GenLayerZoom(1005L, biomes);
      GenLayer genlayervoronoizoom = new GenLayerVoronoiZoom(10L, biomes);
      biomes.initWorldGenSeed(seed);
      genlayervoronoizoom.initWorldGenSeed(seed);
      return new GenLayer[]{biomes, genlayervoronoizoom};
   }
}
