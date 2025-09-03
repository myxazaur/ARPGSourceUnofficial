//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.dimensions.dungeon;

import com.Vivern.Arpg.main.BiomesRegister;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerBiomeDungeon extends GenLayer {
   private Biome[] allowedBiomes = new Biome[]{
      BiomesRegister.CRYSTAL_CAVES,
      BiomesRegister.MAGIC_CRYSTAL_CAVES,
      BiomesRegister.CALCITE_CLEFTS,
      BiomesRegister.TUNNELS,
      BiomesRegister.ONYX_CAVES,
      BiomesRegister.GLOWING_TUNNELS
   };

   public GenLayerBiomeDungeon(long seed) {
      super(seed);
   }

   public int[] getInts(int x, int z, int width, int depth) {
      int[] dest = IntCache.getIntCache(width * depth);

      for (int dz = 0; dz < depth; dz++) {
         for (int dx = 0; dx < width; dx++) {
            this.initChunkSeed(dx + x, dz + z);
            dest[dx + dz * width] = Biome.getIdForBiome(this.allowedBiomes[this.nextInt(this.allowedBiomes.length)]);
         }
      }

      return dest;
   }
}
