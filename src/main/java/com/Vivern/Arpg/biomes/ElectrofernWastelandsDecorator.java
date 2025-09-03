//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.biomes;

import com.Vivern.Arpg.dimensions.generationutils.WorldGenElectrofern;
import com.Vivern.Arpg.main.BlocksRegister;
import java.util.Random;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkGeneratorSettings.Factory;

class ElectrofernWastelandsDecorator extends BiomeDecorator {
   public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
      if (this.decorating) {
         throw new RuntimeException("Already decorating");
      } else {
         this.chunkProviderSettings = Factory.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
         this.chunkPos = pos;
         if (random.nextFloat() < 0.7F) {
            BlockPos randomPos = pos.add(random.nextInt(16) + 8, 40 + random.nextInt(200), random.nextInt(16) + 8);
            if (!worldIn.collidesWithAnyBlock(new AxisAlignedBB(randomPos).grow(6.0))) {
               WorldGenElectrofern gen = new WorldGenElectrofern();
               gen.maxLength = 5 + random.nextInt(30);
               worldIn.setBlockState(randomPos, BlocksRegister.ELECTROFERNSTEM.getDefaultState(), 2);
               boolean rotate = random.nextFloat() < 0.5F;

               for (EnumFacing facing : EnumFacing.VALUES) {
                  gen.generateDirection = facing;
                  if (!rotate) {
                     gen.generateRotated = false;
                     if (facing.getAxis() != Axis.X) {
                        worldIn.setBlockState(randomPos.offset(facing), BlocksRegister.ELECTROFERNSTEM.getDefaultState(), 2);
                        gen.generate(worldIn, random, randomPos.offset(facing));
                     }
                  } else {
                     gen.generateRotated = facing.getAxis() == Axis.Y;
                     if (facing.getAxis() != Axis.Z) {
                        worldIn.setBlockState(randomPos.offset(facing), BlocksRegister.ELECTROFERNSTEM.getDefaultState(), 2);
                        gen.generate(worldIn, random, randomPos.offset(facing));
                     }
                  }
               }
            }
         }

         this.decorating = false;
      }
   }
}
