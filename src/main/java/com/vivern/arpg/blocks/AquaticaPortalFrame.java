package com.vivern.arpg.blocks;

import com.vivern.arpg.main.DimensionsRegister;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AquaticaPortalFrame extends BlockBlock {
   public AquaticaPortalFrame(Material mater, String name, float hard, float resi) {
      super(mater, name, hard, resi);
   }

   public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
      IBlockState statewater = world.getBlockState(fromPos);
      if (statewater.getBlock() == Blocks.WATER || statewater.getBlock() == Blocks.FLOWING_WATER) {
         if (DimensionsRegister.teleporterAQUATICA.tryActivate(world, fromPos.add(0, 2, 0), statewater, null)) {
            return;
         }

         if (DimensionsRegister.teleporterAQUATICA.tryActivate(world, fromPos.add(2, 0, 0), statewater, Axis.X)) {
            return;
         }

         if (DimensionsRegister.teleporterAQUATICA.tryActivate(world, fromPos.add(-2, 0, 0), statewater, Axis.X)) {
            return;
         }

         if (DimensionsRegister.teleporterAQUATICA.tryActivate(world, fromPos.add(1, 1, 0), statewater, Axis.X)) {
            return;
         }

         if (DimensionsRegister.teleporterAQUATICA.tryActivate(world, fromPos.add(-1, 1, 0), statewater, Axis.X)) {
            return;
         }

         if (DimensionsRegister.teleporterAQUATICA.tryActivate(world, fromPos.add(0, 0, 2), statewater, Axis.Z)) {
            return;
         }

         if (DimensionsRegister.teleporterAQUATICA.tryActivate(world, fromPos.add(0, 0, -2), statewater, Axis.Z)) {
            return;
         }

         if (DimensionsRegister.teleporterAQUATICA.tryActivate(world, fromPos.add(0, 1, 1), statewater, Axis.Z)) {
            return;
         }

         if (DimensionsRegister.teleporterAQUATICA.tryActivate(world, fromPos.add(0, 1, -1), statewater, Axis.Z)) {
            return;
         }
      }
   }
}
