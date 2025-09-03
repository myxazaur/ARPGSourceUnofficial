package com.Vivern.Arpg.biomes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;

public abstract class BiomeControlled extends Biome {
   public BiomeControlled(BiomeProperties properties) {
      super(properties);
   }

   public abstract IBlockState[] controlSurface(IBlockState var1, IBlockState var2, double var3);

   public void onPlayer60ticksInBiome(BiomeControlled biome, EntityPlayer player) {
   }
}
