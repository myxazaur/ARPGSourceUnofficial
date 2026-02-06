package com.Vivern.Arpg.dimensions.mortuorus;

import com.Vivern.Arpg.main.BiomesRegister;
import com.Vivern.Arpg.main.DimensionsRegister;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;

public class DimensionMortuorus extends WorldProvider {
   public DimensionType getDimensionType() {
      return DimensionsRegister.MORTUORUS;
   }

   public IChunkGenerator createChunkGenerator() {
      return new MortuorusChunkGenerator(this.world, this.world.getSeed());
   }

   protected void init() {
      this.hasSkyLight = true;
      this.biomeProvider = new BiomeProviderSingle(BiomesRegister.DEAD_LANDS);
   }

   public boolean canRespawnHere() {
      return true;
   }
}
