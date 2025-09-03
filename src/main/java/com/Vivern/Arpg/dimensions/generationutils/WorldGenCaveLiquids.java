//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.dimensions.generationutils;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCaveLiquids extends WorldGenerator {
   public final Block block;
   public final Block blockstone;

   public WorldGenCaveLiquids(Block blockLiquid, Block blockStone) {
      this.block = blockLiquid;
      this.blockstone = blockStone;
   }

   public boolean generate(World worldIn, Random rand, BlockPos position) {
      if (worldIn.getBlockState(position.up()).getBlock() != this.blockstone) {
         return false;
      } else if (worldIn.getBlockState(position.down()).getBlock() != this.blockstone) {
         return false;
      } else {
         IBlockState iblockstate = worldIn.getBlockState(position);
         if (!iblockstate.getBlock().isAir(iblockstate, worldIn, position) && iblockstate.getBlock() != this.blockstone) {
            return false;
         } else {
            int i = 0;
            if (worldIn.getBlockState(position.west()).getBlock() == this.blockstone) {
               i++;
            }

            if (worldIn.getBlockState(position.east()).getBlock() == this.blockstone) {
               i++;
            }

            if (worldIn.getBlockState(position.north()).getBlock() == this.blockstone) {
               i++;
            }

            if (worldIn.getBlockState(position.south()).getBlock() == this.blockstone) {
               i++;
            }

            int j = 0;
            if (worldIn.isAirBlock(position.west())) {
               j++;
            }

            if (worldIn.isAirBlock(position.east())) {
               j++;
            }

            if (worldIn.isAirBlock(position.north())) {
               j++;
            }

            if (worldIn.isAirBlock(position.south())) {
               j++;
            }

            if (i == 3 && j == 1) {
               IBlockState iblockstate1 = this.block.getDefaultState();
               worldIn.setBlockState(position, iblockstate1, 2);
               worldIn.immediateBlockTick(position, iblockstate1, rand);
            }

            return true;
         }
      }
   }
}
