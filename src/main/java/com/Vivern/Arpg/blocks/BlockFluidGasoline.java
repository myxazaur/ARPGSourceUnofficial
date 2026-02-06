package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.FluidsRegister;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockFluidGasoline extends BlockFluidClassic {
   public BlockFluidGasoline() {
      super(FluidsRegister.GASOLINE, Material.WATER);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setTranslationKey("fluid_gasoline_block");
      this.setRegistryName("fluid_gasoline_block");
   }

   public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
      super.onBlockAdded(world, pos, state);
      this.mergerFluids(pos, world);
   }

   public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighbourPos) {
      super.neighborChanged(state, world, pos, neighborBlock, neighbourPos);
      this.mergerFluids(pos, world);
   }

   public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
      return true;
   }

   public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
      return 100;
   }

   private void mergerFluids(BlockPos pos, World world) {
   }
}
