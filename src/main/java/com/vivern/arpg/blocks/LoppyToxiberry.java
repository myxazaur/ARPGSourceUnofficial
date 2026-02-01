package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.ItemsRegister;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LoppyToxiberry extends Block implements IShearable {
   protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.2, 0.0, 0.2, 0.8, 1.0, 0.8);

   public LoppyToxiberry() {
      super(Material.PLANTS);
      this.setRegistryName("loppy_toxiberry");
      this.setTranslationKey("loppy_toxiberry");
      this.setHardness(0.0F);
      this.setResistance(0.0F);
      this.setSoundType(SoundType.PLANT);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setTickRandomly(true);
      this.setLightLevel(0.45F);
   }

   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      return ItemsRegister.GLOWINGTOXIBERRY;
   }

   public boolean isBerry(Block block) {
      return block == BlocksRegister.LOPPYTOXIBERRY || block == BlocksRegister.LOPPYTOXISTEM;
   }

   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      if (worldIn.isAirBlock(pos.down())) {
         if (!this.isBerry(worldIn.getBlockState(pos.up()).getBlock())) {
            this.trygrow(worldIn, pos, rand, 0.2F);
         } else if (!this.isBerry(worldIn.getBlockState(pos.up(2)).getBlock())) {
            this.trygrow(worldIn, pos, rand, 0.3F);
         } else if (!this.isBerry(worldIn.getBlockState(pos.up(3)).getBlock())) {
            this.trygrow(worldIn, pos, rand, 0.4F);
         } else if (!this.isBerry(worldIn.getBlockState(pos.up(4)).getBlock())) {
            this.trygrow(worldIn, pos, rand, 0.5F);
         } else if (!this.isBerry(worldIn.getBlockState(pos.up(5)).getBlock())) {
            this.trygrow(worldIn, pos, rand, 1.0F);
         }
      }
   }

   public void trygrow(World worldIn, BlockPos pos, Random rand, float num) {
      if (rand.nextFloat() < 0.5) {
         if (rand.nextFloat() < num) {
            worldIn.setBlockState(pos.down(), this.getDefaultState());
         } else {
            worldIn.setBlockState(pos.down(), BlocksRegister.LOPPYTOXISTEM.getDefaultState());
         }
      }
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return NULL_AABB;
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      if (worldIn.getBlockState(pos).getMaterial().isLiquid()) {
         return false;
      } else {
         BlockPos posup = pos.up();
         return this.isBerry(worldIn.getBlockState(posup).getBlock()) || worldIn.isSideSolid(posup, EnumFacing.DOWN) || worldIn.isBlockFullCube(posup);
      }
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
         for (int i = pos.getY() + 1; i < 256; i++) {
            BlockPos pos2 = new BlockPos(pos.getX(), i, pos.getZ());
            if (worldIn.isSideSolid(pos2, EnumFacing.DOWN) || worldIn.isBlockFullCube(pos2)) {
               return true;
            }

            if (!this.isBerry(worldIn.getBlockState(pos2).getBlock())) {
               return false;
            }
         }
      }

      return false;
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

   public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
      return true;
   }

   public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
      return NonNullList.withSize(1, new ItemStack(this, 1));
   }
}
