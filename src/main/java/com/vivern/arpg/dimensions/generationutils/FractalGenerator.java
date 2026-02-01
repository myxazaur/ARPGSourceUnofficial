package com.vivern.arpg.dimensions.generationutils;

import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class FractalGenerator {
   public int checkRadius;
   public NoiseGeneratorPerlin perlin;

   public FractalGenerator(int checkRadius, NoiseGeneratorPerlin perlin) {
      this.checkRadius = checkRadius;
      this.perlin = perlin;
   }

   public boolean checkAndGenerate(World world, int chunkX, int chunkZ) {
      return false;
   }
}
