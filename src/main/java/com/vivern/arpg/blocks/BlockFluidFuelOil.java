package com.vivern.arpg.blocks;

import com.vivern.arpg.main.FluidsRegister;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockFluidFuelOil extends BlockFluidClassic {
   public BlockFluidFuelOil() {
      super(FluidsRegister.FUELOIL, Material.WATER);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setTranslationKey("fluid_fueloil_block");
      this.setRegistryName("fluid_fueloil_block");
   }

   public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
      super.onBlockAdded(world, pos, state);
      this.mergerFluids(pos, world);
   }

   public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighbourPos) {
      super.neighborChanged(state, world, pos, neighborBlock, neighbourPos);
      this.mergerFluids(pos, world);
   }

   public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
      super.onEntityCollision(worldIn, pos, state, entityIn);
      entityIn.motionX *= 0.5;
      entityIn.motionY *= 0.5;
      entityIn.motionZ *= 0.5;
   }

   public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
      return true;
   }

   public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
      return 20;
   }

   private void mergerFluids(BlockPos pos, World world) {
   }
}
