package com.vivern.arpg.biomes;

import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.GetMOP;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class FrozenLake extends Biome {
   public FrozenLake() {
      super(new BiomeProperties("Frozen lake").setBaseHeight(-1.4F).setHeightVariation(0.1F).setTemperature(-1.0F).setWaterColor(10804223));
      this.topBlock = Blocks.ICE.getDefaultState();
      this.fillerBlock = BlocksRegister.GLACIER.getDefaultState();
   }

   public void decorate(World worldIn, Random rand, BlockPos pos) {
      if (rand.nextFloat() < 0.075F) {
         BlockPos position = new BlockPos(pos.getX() + 8 + rand.nextInt(16), 63, pos.getZ() + 8 + rand.nextInt(16));
         WorldGenMinable generator = new WorldGenMinable(BlocksRegister.NIVEOLITEBLOCK.getDefaultState(), 8 + rand.nextInt(13), GetMOP.ALL_BLOCKS);
         generator.generate(worldIn, rand, position);
      }
   }
}
