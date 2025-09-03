//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DecorativeChain extends Block {
   public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 3);
   public static final AxisAlignedBB AABB = new AxisAlignedBB(0.4375, 0.0, 0.4375, 0.5625, 1.0, 0.5625);
   public static final AxisAlignedBB AABB_SELECT = new AxisAlignedBB(0.375, 0.0, 0.375, 0.625, 1.0, 0.625);

   public DecorativeChain() {
      super(Material.IRON);
      this.setRegistryName("decorative_chain");
      this.setTranslationKey("decorative_chain");
      this.blockHardness = 1.5F;
      this.blockResistance = 20.0F;
      this.setSoundType(SoundType.METAL);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setLightOpacity(0);
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return AABB;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB_SELECT;
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(TYPE, meta);
   }

   public int getMetaFromState(IBlockState state) {
      return (Integer)state.getValue(TYPE);
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{TYPE});
   }

   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      Block blockup = worldIn.getBlockState(pos.up()).getBlock();
      Block blockdown = worldIn.getBlockState(pos.down()).getBlock();
      boolean up = blockup != this;
      boolean down = blockdown != this;
      if (up) {
         return down ? this.getDefaultState().withProperty(TYPE, 1) : this.getDefaultState().withProperty(TYPE, 3);
      } else {
         return down ? this.getDefaultState().withProperty(TYPE, 2) : this.getDefaultState().withProperty(TYPE, 0);
      }
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      Block blockup = worldIn.getBlockState(pos.up()).getBlock();
      Block blockdown = worldIn.getBlockState(pos.down()).getBlock();
      boolean up = blockup != this;
      boolean down = blockdown != this;
      if (up) {
         return down ? this.getDefaultState().withProperty(TYPE, 1) : this.getDefaultState().withProperty(TYPE, 3);
      } else {
         return down ? this.getDefaultState().withProperty(TYPE, 2) : this.getDefaultState().withProperty(TYPE, 0);
      }
   }
}
