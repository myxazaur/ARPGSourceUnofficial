package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.FluidsRegister;
import com.vivern.arpg.main.Sounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockFluidHydrothermal extends BlockFluidClassic {
   public BlockFluidHydrothermal() {
      super(FluidsRegister.HYDROTHERMAL, Material.WATER);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setTranslationKey("fluid_hydrothermal_block");
      this.setRegistryName("fluid_hydrothermal_block");
   }

   public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
      super.onBlockAdded(world, pos, state);
      this.mergerFluids(pos, world);
   }

   public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighbourPos) {
      super.neighborChanged(state, world, pos, neighborBlock, neighbourPos);
      this.mergerFluids(pos, world);
   }

   private void mergerFluids(BlockPos pos, World world) {
      if (!world.isRemote) {
         for (EnumFacing facing : EnumFacing.values()) {
            BlockPos frompos = pos.offset(facing);
            Block block = world.getBlockState(frompos).getBlock();
            if (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA) {
               if (frompos.getY() > pos.getY()) {
                  world.setBlockState(pos, Blocks.SAND.getDefaultState());
               } else {
                  world.setBlockState(frompos, Blocks.SAND.getDefaultState());
               }

               world.playSound(null, frompos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 0.85F + world.rand.nextFloat() / 4.0F);
            }

            if (block == BlocksRegister.FLUIDCRYON) {
               world.setBlockState(frompos, Blocks.ICE.getDefaultState());
               world.playSound(null, frompos, Sounds.fluid_freezing, SoundCategory.BLOCKS, 1.0F, 0.85F + world.rand.nextFloat() / 4.0F);
            }
         }
      }
   }
}
