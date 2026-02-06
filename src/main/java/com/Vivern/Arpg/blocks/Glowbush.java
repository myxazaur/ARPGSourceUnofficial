package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block.EnumOffsetType;
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

public class Glowbush extends Block {
   public static AxisAlignedBB AABB = new AxisAlignedBB(0.15, 0.0, 0.15, 0.85, 0.7, 0.85);

   public Glowbush() {
      super(Material.PLANTS);
      this.setRegistryName("glowbush");
      this.setTranslationKey("glowbush");
      this.blockHardness = 0.3F;
      this.blockResistance = 0.3F;
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setSoundType(SoundType.PLANT);
      this.lightValue = 4;
   }

   @SideOnly(Side.CLIENT)
   public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
      return 15728880;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public EnumOffsetType getOffsetType() {
      return EnumOffsetType.XYZ;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return AABB;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   public int quantityDropped(Random random) {
      return 0;
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      return super.canPlaceBlockAt(worldIn, pos) && canStayAtPos(worldIn, pos);
   }

   public static boolean canStayAtPos(World worldIn, BlockPos pos) {
      Block blockd = worldIn.getBlockState(pos.down()).getBlock();
      return blockd == BlocksRegister.FULMINIFLORA;
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      if (!canStayAtPos(worldIn, pos)) {
         worldIn.destroyBlock(pos, true);
      }
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }
}
