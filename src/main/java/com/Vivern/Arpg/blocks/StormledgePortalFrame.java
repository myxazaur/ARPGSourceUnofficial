package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.DimensionsRegister;
import com.Vivern.Arpg.main.ItemsRegister;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StormledgePortalFrame extends BlockBlock {
   public StormledgePortalFrame(Material mater, String name, float hard, float resi) {
      super(mater, name, hard, resi);
   }

   public boolean onBlockActivated(
      World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (player.getHeldItemMainhand().getItem() == ItemsRegister.PIRATESEXTANT) {
         if (!DimensionsRegister.teleporterSTORMLEDGE.tryActivate(world, pos.add(0, 2, 0), null, null)
            && !DimensionsRegister.teleporterSTORMLEDGE.tryActivate(world, pos.add(1, 2, 0), null, null)
            && !DimensionsRegister.teleporterSTORMLEDGE.tryActivate(world, pos.add(0, 2, 1), null, null)
            && !DimensionsRegister.teleporterSTORMLEDGE.tryActivate(world, pos.add(-1, 2, 0), null, null)
            && DimensionsRegister.teleporterSTORMLEDGE.tryActivate(world, pos.add(0, 2, -1), null, null)) {
         }

         return true;
      } else {
         return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
      }
   }
}
