package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block.EnumOffsetType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FulminiortumBulb extends Block {
   public static AxisAlignedBB AABB = new AxisAlignedBB(0.1, 0.0, 0.1, 0.9, 0.2, 0.9);

   public FulminiortumBulb() {
      super(Material.PLANTS);
      this.setRegistryName("fulminiortum_bulb");
      this.setTranslationKey("fulminiortum_bulb");
      this.blockHardness = 0.1F;
      this.blockResistance = 0.0F;
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setSoundType(SoundType.PLANT);
   }

   @SideOnly(Side.CLIENT)
   public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
      return 15728880;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
      return true;
   }

   public EnumOffsetType getOffsetType() {
      return EnumOffsetType.XZ;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return NULL_AABB;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   public int quantityDropped(Random random) {
      return 0;
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      return canStayAtPos(worldIn, pos);
   }

   public static boolean canStayAtPos(World worldIn, BlockPos pos) {
      Block blockd = worldIn.getBlockState(pos.down()).getBlock();
      return blockd == BlocksRegister.FULMINIFLORA || blockd == Blocks.STONE;
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
