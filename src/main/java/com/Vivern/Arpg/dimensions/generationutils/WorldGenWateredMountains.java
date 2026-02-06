package com.Vivern.Arpg.dimensions.generationutils;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenWateredMountains extends WorldGenerator {
   public IBlockState stone;
   public IBlockState biomeDirt;
   public IBlockState biomeGrass;
   public NoiseGeneratorPerlin noise;
   public int height = 100;
   public int maxradius = 13;
   public int startradius = 8;
   public float mult1 = 2.0F;
   public float mult2 = 2.0F;

   public WorldGenWateredMountains(Block stone, Block biomeDirt, Block biomeGrass, NoiseGeneratorPerlin noise) {
      this.stone = stone.getDefaultState();
      this.biomeDirt = biomeDirt.getDefaultState();
      this.biomeGrass = biomeGrass.getDefaultState();
      this.noise = noise;
   }

   public WorldGenWateredMountains(IBlockState stone, IBlockState biomeDirt, IBlockState biomeGrass, NoiseGeneratorPerlin noise) {
      this.stone = stone;
      this.biomeDirt = biomeDirt;
      this.biomeGrass = biomeGrass;
      this.noise = noise;
   }

   public boolean generate(World worldIn, Random rand, BlockPos position) {
      int allheight = position.getY() + this.height + this.height / 6;
      int currentradius = this.startradius;
      float radiusAddChance = 1.0F / this.height * (this.maxradius - this.startradius);
      float radiusRemoveChance = 1.0F / (this.height / 6) * (this.maxradius - this.startradius);
      int displace = 0;

      for (int i = position.getY(); i < allheight; i++) {
         for (int xx = -currentradius; xx <= currentradius; xx++) {
            for (int zz = -currentradius; zz <= currentradius; zz++) {
               double dist = Math.sqrt(xx * xx + zz * zz);
               double nvalue = this.noise.getValue((position.getX() + xx + displace) / 3.0, (position.getZ() + zz + displace) / 3.0);
               if (nvalue * this.mult1 + 5.0 > (dist - currentradius / 2) * this.mult2) {
                  worldIn.setBlockState(new BlockPos(position.getX() + xx, i, position.getZ() + zz), this.stone);
               }
            }
         }

         if (i < position.getY() + this.height) {
            if (rand.nextFloat() < radiusAddChance && currentradius < this.maxradius) {
               currentradius++;
            }
         } else if (rand.nextFloat() < radiusRemoveChance && currentradius > 2) {
            currentradius--;
         }

         if (rand.nextFloat() < 0.1) {
            displace++;
         }
      }

      int yupmin = position.getY() + this.height;
      int yup = yupmin + this.height / 6 + 1;

      for (int xx = -this.maxradius; xx <= this.maxradius; xx++) {
         for (int zzx = -this.maxradius; zzx <= this.maxradius; zzx++) {
            for (int yy = yup; yy > yupmin; yy--) {
               BlockPos pos = new BlockPos(position.getX() + xx, yy, position.getZ() + zzx);
               if (!worldIn.isAirBlock(pos)) {
                  worldIn.setBlockState(pos, this.biomeDirt);
                  worldIn.setBlockState(pos.up(), this.biomeDirt);
                  worldIn.setBlockState(pos.up(2), this.biomeGrass);
                  break;
               }
            }
         }
      }

      return true;
   }
}
