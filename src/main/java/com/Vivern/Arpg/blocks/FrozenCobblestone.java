package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;

public class FrozenCobblestone extends BlockBlockHard {
   public FrozenCobblestone() {
      super(
         Material.ROCK,
         "frozen_cobblestone",
         BlocksRegister.HR_FROZEN_COBBLESTONE.HARDNESS,
         BlocksRegister.HR_FROZEN_COBBLESTONE.RESISTANCE,
         BlocksRegister.HR_FROZEN_COBBLESTONE.SLOW,
         BlocksRegister.HR_FROZEN_COBBLESTONE.FAST,
         BlocksRegister.HR_FROZEN_COBBLESTONE.LVL,
         "pickaxe",
         true
      );
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
   }

   @Override
   public boolean isFullCube(IBlockState state) {
      return true;
   }
}
