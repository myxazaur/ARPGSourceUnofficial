//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class FrozenBeach extends Biome {
   public FrozenBeach() {
      super(new BiomeProperties("Frozen beach").setBaseHeight(-0.7F).setHeightVariation(0.05F).setTemperature(-1.0F).setWaterColor(10804223));
      this.topBlock = Blocks.ICE.getDefaultState();
      this.fillerBlock = BlocksRegister.GLACIER.getDefaultState();
      this.decorator = new EverfrostRiver.EverfrostRiverDecorator();
   }

   public void decorate(World worldIn, Random rand, BlockPos pos) {
      if (rand.nextFloat() < 0.07F) {
         BlockPos position = new BlockPos(pos.getX() + 8 + rand.nextInt(16), 63, pos.getZ() + 8 + rand.nextInt(16));
         WorldGenMinable generator = new WorldGenMinable(BlocksRegister.NIVEOLITEBLOCK.getDefaultState(), 8 + rand.nextInt(13), GetMOP.ALL_BLOCKS);
         generator.generate(worldIn, rand, position);
      }
   }
}
