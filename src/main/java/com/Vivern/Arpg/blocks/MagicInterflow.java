package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.tileentity.TileEntityMagicInterflow;
import com.google.common.base.Predicate;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MagicInterflow extends Block {
   public MagicInterflow() {
      super(Material.PORTAL);
      this.setRegistryName("magic_interflow");
      this.setTranslationKey("magic_interflow");
      this.setBlockUnbreakable();
      this.blockResistance = 4.0F;
      this.setCreativeTab(CreativeTabs.MISC);
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      ItemStack stack = playerIn.getHeldItem(hand);
      if (stack == ItemStack.EMPTY) {
         return false;
      } else if (worldIn.getTileEntity(pos) instanceof TileEntityMagicInterflow) {
         TileEntityMagicInterflow tile = (TileEntityMagicInterflow)worldIn.getTileEntity(pos);
         int type = tile.type;
         int power = tile.power;
         NBTHelper.GiveNBTint(stack, type, "storedelement");
         NBTHelper.SetNBTint(stack, type, "storedelement");
         NBTHelper.GiveNBTint(stack, type, "storedelementpower");
         NBTHelper.SetNBTint(stack, type, "storedelementpower");
         return true;
      } else {
         return false;
      }
   }

   public EnumBlockRenderType getRenderType(IBlockState state) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   @Nullable
   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return NULL_AABB;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public int quantityDropped(IBlockState state, int fortune, Random random) {
      return 0;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos, Predicate<IBlockState> target) {
      return true;
   }

   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
      return BlockFaceShape.UNDEFINED;
   }
}
