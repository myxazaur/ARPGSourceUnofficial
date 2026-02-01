package com.vivern.arpg.blocks;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CaveMagicStone extends Block {
   public CaveMagicStone() {
      super(Material.ROCK);
      this.setRegistryName("magic_stone");
      this.setTranslationKey("magic_stone");
      this.blockHardness = 10.0F;
      this.blockResistance = 25.0F;
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setLightLevel(0.5F);
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos, Predicate<IBlockState> target) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return true;
   }
}
