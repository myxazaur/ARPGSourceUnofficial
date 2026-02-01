package com.vivern.arpg.blocks;

import com.vivern.arpg.tileentity.TileSieve;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSieve extends Block {
   public static double boundOffset = 0.0;
   public static double wallThickness = 0.0625;
   public static final AxisAlignedBB AABB = new AxisAlignedBB(0.0, 0.4375, 0.0, 1.0, 0.875, 1.0);
   public static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(boundOffset, 0.4375, boundOffset, 1.0 - boundOffset, 0.5, 1.0 - boundOffset);
   protected static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(
      boundOffset, 0.5, boundOffset, 1.0 - boundOffset, 0.875, boundOffset + wallThickness
   );
   protected static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(
      boundOffset, 0.5, 1.0 - boundOffset - wallThickness, 1.0 - boundOffset, 0.875, 1.0 - boundOffset
   );
   protected static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(
      1.0 - boundOffset - wallThickness, 0.5, boundOffset, 1.0 - boundOffset, 0.875, 1.0 - boundOffset
   );
   protected static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(boundOffset, 0.5, boundOffset, boundOffset + wallThickness, 0.875, 1.0 - boundOffset);

   public BlockSieve(Material material, String name) {
      super(material);
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.blockHardness = 2.0F;
      this.blockResistance = 10.0F;
      this.setCreativeTab(CreativeTabs.MISC);
      this.setHarvestLevel("axe", 0);
      this.setSoundType(SoundType.WOOD);
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public void addCollisionBoxToList(
      IBlockState state,
      World worldIn,
      BlockPos pos,
      AxisAlignedBB entityBox,
      List<AxisAlignedBB> collidingBoxes,
      @Nullable Entity entityIn,
      boolean isActualState
   ) {
      addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_LEGS);
      addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
      addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
      addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
      addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (!worldIn.isRemote && hand == EnumHand.MAIN_HAND) {
         TileSieve tile = this.getTileEntity(worldIn, pos);
         if (tile != null) {
            tile.addSievePower(10, 40);
         }
      }

      return true;
   }

   public TileSieve getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileSieve)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileSieve createTileEntity(World world, IBlockState blockState) {
      return new TileSieve();
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public EnumBlockRenderType getRenderType(IBlockState state) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
      super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
      TileSieve tile = this.getTileEntity(worldIn, pos);
      tile.rotated = placer.getHorizontalFacing().getAxis() == Axis.Z;
   }

   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
      return face == EnumFacing.UP ? BlockFaceShape.BOWL : BlockFaceShape.UNDEFINED;
   }
}
