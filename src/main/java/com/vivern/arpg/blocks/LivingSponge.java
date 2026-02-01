package com.vivern.arpg.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LivingSponge extends Block {
   public static final PropertyBool HOLE = PropertyBool.create("hole");

   public LivingSponge() {
      super(Material.SPONGE);
      this.setRegistryName("living_sponge");
      this.setTranslationKey("living_sponge");
      this.blockHardness = 5.0F;
      this.blockResistance = 2.0F;
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setHarvestLevel("shears", 0);
      this.setSoundType(SoundType.PLANT);
      this.setDefaultState(this.getDefaultState().withProperty(HOLE, false));
   }

   public boolean isFullCube(IBlockState state) {
      return true;
   }

   public IBlockState getStateFromMeta(int meta) {
      return meta == 1 ? this.getDefaultState().withProperty(HOLE, true) : this.getDefaultState().withProperty(HOLE, false);
   }

   public int getMetaFromState(IBlockState state) {
      return state.getValue(HOLE) ? 1 : 0;
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{HOLE});
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return placer.isSneaking() ? this.getStateFromMeta(1) : this.getStateFromMeta(0);
   }
}
