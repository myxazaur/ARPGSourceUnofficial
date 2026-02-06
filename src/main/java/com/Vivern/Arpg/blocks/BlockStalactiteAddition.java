package com.Vivern.Arpg.blocks;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStalactiteAddition extends Block {
   public static AxisAlignedBB AABB = new AxisAlignedBB(0.35, 0.0, 0.35, 0.65, 1.0, 0.65);

   public BlockStalactiteAddition(String name, Material material) {
      super(material);
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.blockHardness = 1.0F;
      this.blockResistance = 0.8F;
      this.setCreativeTab(CreativeTabs.DECORATIONS);
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
      return side == EnumFacing.DOWN || side == EnumFacing.UP;
   }

   public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
      return false;
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      if (worldIn.isAirBlock(pos.up()) && worldIn.isAirBlock(pos.down())) {
         worldIn.destroyBlock(pos, true);
      }
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return NULL_AABB;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   public int quantityDropped(Random random) {
      return 0;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }
}
