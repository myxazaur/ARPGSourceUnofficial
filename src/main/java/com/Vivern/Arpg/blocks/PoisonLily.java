package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PoisonLily extends Block {
   protected static final AxisAlignedBB LILY_PAD_AABB = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.09375, 0.9375);

   public PoisonLily() {
      super(Material.PLANTS);
      this.setRegistryName("poisonlily");
      this.setTranslationKey("poisonlily");
      this.setHardness(0.0F);
      this.setResistance(0.0F);
      this.setSoundType(SoundType.PLANT);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
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
      if (!(entityIn instanceof EntityBoat)) {
         addCollisionBoxToList(pos, entityBox, collidingBoxes, LILY_PAD_AABB);
      }
   }

   public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
      super.onEntityCollision(worldIn, pos, state, entityIn);
      if (entityIn instanceof EntityBoat) {
         worldIn.destroyBlock(new BlockPos(pos), true);
      }
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return LILY_PAD_AABB;
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      return worldIn.getBlockState(pos).getMaterial().isLiquid() ? false : this.canBlockStay(worldIn, pos);
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
      this.checkAndDropBlock(worldIn, pos, state);
   }

   protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
      if (!this.canBlockStay(worldIn, pos)) {
         this.dropBlockAsItem(worldIn, pos, state, 0);
         worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
      }
   }

   public boolean canBlockStay(World worldIn, BlockPos pos) {
      if (pos.getY() >= 0 && pos.getY() < 256) {
         IBlockState iblockstate = worldIn.getBlockState(pos.down());
         Material material = iblockstate.getMaterial();
         return iblockstate.getBlock() == BlocksRegister.FLUIDPOISON && (Integer)iblockstate.getValue(BlockLiquid.LEVEL) == 0;
      } else {
         return false;
      }
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
      return BlockFaceShape.UNDEFINED;
   }

   public int getMetaFromState(IBlockState state) {
      return 0;
   }
}
