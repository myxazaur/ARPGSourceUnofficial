//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Garland extends Block {
   public static final PropertyBool NORTH = PropertyBool.create("north");
   public static final PropertyBool EAST = PropertyBool.create("east");
   public static final PropertyBool SOUTH = PropertyBool.create("south");
   public static final PropertyBool WEST = PropertyBool.create("west");
   public static final PropertyBool VERTICAL = PropertyBool.create("vertical");
   protected static final AxisAlignedBB SN_AABB = new AxisAlignedBB(0.375, 0.375, 0.0, 0.625, 1.0, 1.0);
   protected static final AxisAlignedBB WE_AABB = new AxisAlignedBB(0.0, 0.375, 0.375, 1.0, 1.0, 0.625);
   protected static final AxisAlignedBB UPPER_AABB = new AxisAlignedBB(0.375, 0.375, 0.375, 0.625, 1.0, 0.625);
   protected static final AxisAlignedBB UPPERLARGE_AABB = new AxisAlignedBB(0.175, 0.625, 0.175, 0.825, 1.0, 0.825);
   protected static final AxisAlignedBB S_half_AABB = new AxisAlignedBB(0.0, 0.25, 0.825, 1.0, 0.875, 1.0);
   protected static final AxisAlignedBB W_half_AABB = new AxisAlignedBB(0.0, 0.25, 0.0, 0.175, 0.875, 1.0);
   protected static final AxisAlignedBB N_half_AABB = new AxisAlignedBB(0.0, 0.25, 0.0, 1.0, 0.875, 0.175);
   protected static final AxisAlignedBB E_half_AABB = new AxisAlignedBB(0.825, 0.25, 0.0, 1.0, 0.875, 1.0);

   public Garland() {
      super(Material.CLOTH);
      this.setRegistryName("garland");
      this.setTranslationKey("garland");
      this.blockHardness = 0.0F;
      this.blockResistance = 0.0F;
      this.setSoundType(SoundType.CLOTH);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setDefaultState(this.getDefaultState().withProperty(VERTICAL, false));
      this.setLightLevel(0.2F);
   }

   @SideOnly(Side.CLIENT)
   public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
      return 15728880;
   }

   public AxisAlignedBB getAABB(IBlockState state) {
      boolean n = (Boolean)state.getValue(NORTH);
      boolean e = (Boolean)state.getValue(EAST);
      boolean s = (Boolean)state.getValue(SOUTH);
      boolean w = (Boolean)state.getValue(WEST);
      boolean vertical = (Boolean)state.getValue(VERTICAL);
      if (vertical) {
         if (n) {
            return e ? UPPERLARGE_AABB : SN_AABB;
         } else {
            return e ? WE_AABB : UPPER_AABB;
         }
      } else if (n && !s) {
         return N_half_AABB;
      } else if (e && !w) {
         return E_half_AABB;
      } else if (s && !n) {
         return S_half_AABB;
      } else {
         return w && !e ? W_half_AABB : FULL_BLOCK_AABB;
      }
   }

   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      if ((Boolean)state.getValue(VERTICAL)) {
         boolean west = worldIn.getBlockState(pos.west()).getBlock() == this;
         boolean east = west || worldIn.getBlockState(pos.east()).getBlock() == this;
         boolean south = worldIn.getBlockState(pos.south()).getBlock() == this;
         boolean north = south || worldIn.getBlockState(pos.north()).getBlock() == this;
         return state.withProperty(WEST, east).withProperty(EAST, east).withProperty(NORTH, north).withProperty(SOUTH, north);
      } else {
         boolean west = this.isBlocksolid(worldIn, pos.west());
         boolean east = this.isBlocksolid(worldIn, pos.east());
         boolean south = this.isBlocksolid(worldIn, pos.south());
         boolean north = this.isBlocksolid(worldIn, pos.north());
         return state.withProperty(WEST, west).withProperty(EAST, east).withProperty(NORTH, north).withProperty(SOUTH, south);
      }
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      boolean west = this.isBlocksolid(worldIn, pos.west());
      boolean east = this.isBlocksolid(worldIn, pos.east());
      boolean south = this.isBlocksolid(worldIn, pos.south());
      boolean north = this.isBlocksolid(worldIn, pos.north());
      boolean up = this.isBlocksolid(worldIn, pos.up());
      return east || north || south || west || up;
   }

   public boolean isBlocksolid(IBlockAccess worldIn, BlockPos pos) {
      return worldIn.getBlockState(pos).getCollisionBoundingBox(worldIn, pos) != Block.NULL_AABB;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return NULL_AABB;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return this.getAABB(this.getActualState(state, source, pos));
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

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(VERTICAL, meta > 0);
   }

   public int getMetaFromState(IBlockState state) {
      return state.getValue(VERTICAL) ? 1 : 0;
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{NORTH, EAST, SOUTH, WEST, VERTICAL});
   }

   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
      return BlockFaceShape.UNDEFINED;
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      boolean west = this.isBlocksolid(worldIn, pos.west());
      boolean east = this.isBlocksolid(worldIn, pos.east());
      boolean south = this.isBlocksolid(worldIn, pos.south());
      boolean north = this.isBlocksolid(worldIn, pos.north());
      boolean up = this.isBlocksolid(worldIn, pos.up());
      return this.getDefaultState()
         .withProperty(VERTICAL, placer.isSneaking() || !west && !east && !south && !north && up)
         .withProperty(WEST, west)
         .withProperty(EAST, east)
         .withProperty(NORTH, north)
         .withProperty(SOUTH, south);
   }
}
