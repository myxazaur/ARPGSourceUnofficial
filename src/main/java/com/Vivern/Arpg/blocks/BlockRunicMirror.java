package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.tileentity.TileRunicMirror;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRunicMirror extends Block {
   public static final AxisAlignedBB ALL_AABB1 = GetMOP.newAABB(10, 2, 0);
   public static final AxisAlignedBB ALL_AABB2 = GetMOP.newAABB(4, 7, 2);
   public static final AxisAlignedBB ALL_AABB3 = ALL_AABB1.union(ALL_AABB2);
   public static final AxisAlignedBB MIRROR_AABB = GetMOP.newAABB(10, 10, 11);

   public BlockRunicMirror() {
      super(Material.GLASS);
      this.setRegistryName("runic_mirror");
      this.setTranslationKey("runic_mirror");
      this.blockHardness = 5.5F;
      this.blockResistance = 15.0F;
      this.setCreativeTab(CreativeTabs.MISC);
      this.setLightLevel(0.4F);
      this.setSoundType(SoundType.METAL);
      this.setHarvestLevel("pickaxe", 1);
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return ALL_AABB3;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return ALL_AABB3;
   }

   public void addCollisionBoxToList(
      IBlockState state,
      World world,
      BlockPos pos,
      AxisAlignedBB entityBox,
      List<AxisAlignedBB> collidingBoxes,
      @Nullable Entity entityIn,
      boolean isActualState
   ) {
      addCollisionBoxToList(pos, entityBox, collidingBoxes, ALL_AABB1);
      addCollisionBoxToList(pos, entityBox, collidingBoxes, ALL_AABB2);
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (!worldIn.isRemote && hand == EnumHand.MAIN_HAND && player.getHeldItemMainhand().isEmpty() && player.isSneaking()) {
         TileRunicMirror tile = this.getTileEntity(worldIn, pos);
         tile.rotateMirror(player.rotationPitch, player.rotationYaw);
         return true;
      } else {
         return false;
      }
   }

   public Class<TileRunicMirror> getTileEntityClass() {
      return TileRunicMirror.class;
   }

   public TileRunicMirror getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileRunicMirror)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileRunicMirror createTileEntity(World world, IBlockState blockState) {
      return new TileRunicMirror();
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

   public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
      return false;
   }

   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
      return BlockFaceShape.UNDEFINED;
   }
}
