//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.dimensions.ethernalfrost.ChestReplacersFrozen;
import com.Vivern.Arpg.dimensions.generationutils.GenerationHelper;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenGroundFoliage;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkGeneratorSettings.Factory;

class IceHillsDecorator extends BiomeDecorator {
   public WorldGenGroundFoliage magicflower = new WorldGenGroundFoliage(BlocksRegister.ICEFLOWER, 38, 5, 4);

   public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
      if (this.decorating) {
         throw new RuntimeException("Already decorating");
      } else {
         int x = random.nextInt(16);
         int z = random.nextInt(16);
         if (random.nextFloat() < 0.03) {
            BlockPos position = worldIn.getHeight(
               new BlockPos(pos.getX() + 8 + random.nextInt(16), 0, pos.getZ() + 8 + random.nextInt(16))
            );
            GenerationHelper.placeStruct(
               worldIn, position, random, ":frozen_grave_" + (random.nextInt(3) + 1), 4, -1, random.nextInt(4), ChestReplacersFrozen.replacerGrave
            );
         }

         this.chunkProviderSettings = Factory.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
         this.chunkPos = pos;
         if (random.nextFloat() < 0.1) {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            this.magicflower.generate(worldIn, random, GetMOP.getTrueHeight(worldIn, this.chunkPos.add(j, 0, k)));
         }

         this.decorating = false;
      }
   }
}
