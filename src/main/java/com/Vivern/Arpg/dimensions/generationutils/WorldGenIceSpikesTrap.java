package com.Vivern.Arpg.dimensions.generationutils;

import com.Vivern.Arpg.blocks.IceSpikes;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenIceSpikesTrap extends WorldGenerator {
   public int size;
   public int depth;
   public boolean snowy;
   public boolean snapToHeight;
   public IBlockState snow = BlocksRegister.LOOSESNOW.getStateFromMeta(1);

   public WorldGenIceSpikesTrap(boolean snowy, int size, int depth, boolean snapToHeight) {
      this.size = size;
      this.depth = depth;
      this.snowy = snowy;
      this.snapToHeight = snapToHeight;
   }

   public void place(World world, EnumFacing direction, EnumFacing offsetSide, BlockPos center, int count, int width, Random rand) {
      for (int i = 0; i < count; i++) {
         for (int w = -width; w <= width; w++) {
            BlockPos pos = this.snapToHeight ? GetMOP.getTrueHeight(world, center.offset(offsetSide, w)) : center.offset(offsetSide, w);
            if (world.getBlockState(pos).getBlock() != BlocksRegister.CLEANICE) {
               world.setBlockState(pos, BlocksRegister.CLEANICE.getDefaultState());
               if (this.snowy) {
                  BlockPos posup = pos.up();
                  if (world.isAirBlock(posup)) {
                     world.setBlockState(posup, this.snow);
                  }
               }

               int deep = this.depth - Math.abs(w / 2) - i + rand.nextInt(4);

               for (int h = 1; h <= deep; h++) {
                  BlockPos posdeep = pos.down(h);
                  if (h == deep && !world.isAirBlock(posdeep.down())) {
                     world.setBlockState(posdeep, BlocksRegister.ICESPIKES.getDefaultState().withProperty(IceSpikes.NOTPERMANENT, false));
                  } else {
                     world.setBlockToAir(posdeep);
                  }
               }
            }
         }

         width -= rand.nextInt(3);
         if (width < 0) {
            return;
         }

         center = center.offset(direction).offset(offsetSide, rand.nextInt(3) - 1);
      }
   }

   public boolean generate(World worldIn, Random rand, BlockPos position) {
      for (EnumFacing facing : EnumFacing.HORIZONTALS) {
         this.place(worldIn, facing, facing.rotateY(), position, rand.nextInt(this.size) + 2, rand.nextInt(this.size) + 4, rand);
      }

      return true;
   }
}
