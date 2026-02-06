package com.Vivern.Arpg.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Table extends Block {
   protected static final AxisAlignedBB AABB_FULL = new AxisAlignedBB(0.0, 0.1, 0.0, 1.0, 1.0, 1.0);
   protected static final AxisAlignedBB AABB_FLAT = new AxisAlignedBB(0.0, 0.75, 0.0, 1.0, 1.0, 1.0);
   public static final PropertyBool FLAT = PropertyBool.create("flat");

   public Table(Material mater, String name, float hard, float resi, SoundType stype, String tool, int harvestlvl) {
      super(mater);
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.blockHardness = hard;
      this.blockResistance = resi;
      this.setSoundType(stype);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setHarvestLevel(tool, harvestlvl);
   }

   public boolean isTopSolid(IBlockState state) {
      return true;
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return !facing.equals(EnumFacing.UP) && !facing.equals(EnumFacing.DOWN) ? this.getStateFromMeta(1) : this.getStateFromMeta(0);
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return state.getValue(FLAT) ? AABB_FLAT : AABB_FULL;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return blockState.getValue(FLAT) ? AABB_FLAT : AABB_FULL;
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

   public IBlockState getStateFromMeta(int meta) {
      return meta == 1 ? this.getDefaultState().withProperty(FLAT, true) : this.getDefaultState().withProperty(FLAT, false);
   }

   public int getMetaFromState(IBlockState state) {
      return state.getValue(FLAT) ? 1 : 0;
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{FLAT});
   }
}
