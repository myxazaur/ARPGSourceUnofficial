package com.vivern.arpg.blocks;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block.EnumOffsetType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SlimeBlob extends Block {
   protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.45, 0.75);

   public SlimeBlob() {
      super(Material.CLAY);
      this.setRegistryName("slime_blob");
      this.setTranslationKey("slime_blob");
      this.blockHardness = 0.0F;
      this.blockResistance = 0.0F;
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setSoundType(SoundType.SLIME);
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      return Items.SLIME_BALL;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   public EnumOffsetType getOffsetType() {
      return EnumOffsetType.XZ;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return NULL_AABB;
   }

   public boolean canPlaceBlockAt(World world, BlockPos pos) {
      return world.isSideSolid(pos.down(), EnumFacing.UP) ? super.canPlaceBlockAt(world, pos) : false;
   }

   public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
      return false;
   }
}
