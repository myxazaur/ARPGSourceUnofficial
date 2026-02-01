package com.vivern.arpg.biomes;

import com.vivern.arpg.dimensions.aquatica.AquaticaChunkGenerator;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSand.EnumType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.world.biome.Biome.BiomeProperties;

public class Shallow extends BiomeControlled {
   public Shallow() {
      super(
         new BiomeProperties("Shallow")
            .setBaseHeight(4.0F)
            .setHeightVariation(0.032F)
            .setTemperature(0.9F)
            .setWaterColor(11921407)
            .setRainfall(0.0F)
            .setRainDisabled()
      );
      this.topBlock = Blocks.RED_SANDSTONE.getDefaultState();
      this.fillerBlock = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.SILVER);
      this.decorator = new ShallowDecorator();
   }

   @Override
   public IBlockState[] controlSurface(IBlockState top, IBlockState filler, double noiseValue) {
      if (noiseValue > 2.5) {
         top = AquaticaChunkGenerator.SAND;
         filler = AquaticaChunkGenerator.SANDSTONE;
      } else if (noiseValue > 1.0) {
         top = Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, EnumType.RED_SAND);
         filler = Blocks.STAINED_HARDENED_CLAY.getDefaultState();
      }

      return new IBlockState[]{top, filler};
   }
}
