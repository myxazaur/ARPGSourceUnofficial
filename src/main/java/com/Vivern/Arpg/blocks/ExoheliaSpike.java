//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ExoheliaSpike extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing");
   public static final PropertyBool ROTATED = PropertyBool.create("rotated");
   protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);

   public ExoheliaSpike() {
      super(Material.ROCK);
      this.setRegistryName("exohelia_spike");
      this.setTranslationKey("exohelia_spike");
      this.blockHardness = 0.2F;
      this.blockResistance = 0.1F;
      this.setSoundType(SoundTypeCrunchy.CRUNCHY);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      return super.canPlaceBlockAt(worldIn, pos);
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return AABB;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public int getMetaFromState(IBlockState state) {
      EnumFacing facing = (EnumFacing)state.getValue(FACING);
      boolean rotated = (Boolean)state.getValue(ROTATED);
      return facing.getIndex() + (rotated ? 6 : 0);
   }

   public IBlockState getStateFromMeta(int meta) {
      boolean rotated = false;
      if (meta >= 6) {
         rotated = true;
         meta -= 6;
      }

      return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta)).withProperty(ROTATED, rotated);
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{FACING, ROTATED});
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      boolean rotated = false;
      if (facing != EnumFacing.UP && facing != EnumFacing.DOWN) {
         rotated = placer.isSneaking();
      } else {
         rotated = placer.getHorizontalFacing().getAxis() == Axis.X;
      }

      return this.getDefaultState().withProperty(FACING, facing).withProperty(ROTATED, rotated);
   }
}
