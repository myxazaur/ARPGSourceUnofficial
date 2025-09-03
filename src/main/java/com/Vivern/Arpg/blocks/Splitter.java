//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.tileentity.TileSplitter;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Splitter extends Block {
   public static double boundOffset = 0.125;
   public static double wallThickness = 0.0625;
   public static final AxisAlignedBB AABB = new AxisAlignedBB(boundOffset, 0.0, boundOffset, 1.0 - boundOffset, 1.0, 1.0 - boundOffset);
   public static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(boundOffset, 0.0, boundOffset, 1.0 - boundOffset, 0.3125, 1.0 - boundOffset);
   protected static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(boundOffset, 0.0, boundOffset, 1.0 - boundOffset, 1.0, boundOffset + wallThickness);
   protected static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(
      boundOffset, 0.0, 1.0 - boundOffset - wallThickness, 1.0 - boundOffset, 1.0, 1.0 - boundOffset
   );
   protected static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(
      1.0 - boundOffset - wallThickness, 0.0, boundOffset, 1.0 - boundOffset, 1.0, 1.0 - boundOffset
   );
   protected static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(boundOffset, 0.0, boundOffset, boundOffset + wallThickness, 1.0, 1.0 - boundOffset);

   public Splitter() {
      super(Material.IRON);
      this.setRegistryName("splitter");
      this.setTranslationKey("splitter");
      this.blockHardness = 4.0F;
      this.blockResistance = 40.0F;
      this.setSoundType(SoundTypeShards.ANVIL);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
   }

   public boolean onBlockActivated(
      World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      ItemStack stack = playerIn.getHeldItem(hand);
      if (stack.getItem() == ItemsRegister.BEAKER) {
         TileEntity tileEntity = world.getTileEntity(pos);
         if (tileEntity != null && tileEntity instanceof TileSplitter) {
            ((TileSplitter)tileEntity).onPlayerUseBeaker(stack, playerIn);
            return true;
         }
      }

      return false;
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileSplitter createTileEntity(World world, IBlockState blockState) {
      return new TileSplitter();
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      return worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP);
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

   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
      return true;
   }

   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
      return face == EnumFacing.UP ? BlockFaceShape.BOWL : BlockFaceShape.UNDEFINED;
   }
}
