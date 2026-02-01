package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FrozenVase extends Block {
   protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 1.9375, 0.875);

   public FrozenVase() {
      super(Material.GLASS);
      this.setRegistryName("frozen_vase");
      this.setTranslationKey("frozen_vase");
      this.blockHardness = BlocksRegister.HR_FROZEN_FURNITURE.HARDNESS;
      this.blockResistance = BlocksRegister.HR_FROZEN_FURNITURE.RESISTANCE;
      this.setHarvestLevel("pickaxe", BlocksRegister.HR_FROZEN_FURNITURE.LVL);
      this.setSoundType(SoundTypeShards.SHARDS);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      return !worldIn.isAirBlock(pos.down());
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return AABB;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }
}
