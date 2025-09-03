//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.main.BlocksRegister;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.gen.ChunkGeneratorSettings.Factory;

public class MagicCrystalCaves extends Biome {
   public MagicCrystalCaves() {
      super(new BiomeProperties("Magic crystal caves").setBaseHeight(0.0F).setHeightVariation(0.0F).setTemperature(0.4F));
      this.topBlock = Blocks.STONE.getDefaultState();
      this.fillerBlock = Blocks.STONE.getDefaultState();
      this.decorator = new MagicCrystalCavesDecorator();
   }

   class MagicCrystalCavesDecorator extends BiomeDecorator {
      public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
         if (this.decorating) {
            throw new RuntimeException("Already decorating");
         } else {
            for (int ss = 0; ss < 90; ss++) {
               BlockPos postogenerate = new BlockPos(
                  pos.getX() + random.nextInt(16) + 8, random.nextInt(254) + 1, pos.getZ() + random.nextInt(16) + 8
               );
               if (worldIn.getBlockState(postogenerate).getMaterial() == Material.ROCK) {
                  CrystalCaves.blockPosCrystalTrace2(
                     worldIn,
                     random,
                     postogenerate,
                     pos.getX() + 3,
                     pos.getX() + 31,
                     pos.getZ() + 1,
                     pos.getZ() + 29,
                     BlocksRegister.MAGICSTONE
                  );
               }
            }

            this.chunkProviderSettings = Factory.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
            this.chunkPos = pos;
            this.decorating = false;
         }
      }
   }
}
