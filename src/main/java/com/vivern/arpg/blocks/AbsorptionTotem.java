package com.vivern.arpg.blocks;

import com.vivern.arpg.tileentity.IManaBuffer;
import com.vivern.arpg.tileentity.TileAbsorptionTotem;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AbsorptionTotem extends Block {
   public static final AxisAlignedBB ALL_AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.75, 0.875);

   public AbsorptionTotem() {
      super(IManaBuffer.MAGIC_BLOCK);
      this.setRegistryName("absorption_totem");
      this.setTranslationKey("absorption_totem");
      this.blockHardness = 5.5F;
      this.blockResistance = 15.0F;
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setSoundType(SoundType.WOOD);
      this.setHarvestLevel("axe", 1);
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return ALL_AABB;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return ALL_AABB;
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileAbsorptionTotem createTileEntity(World world, IBlockState blockState) {
      return new TileAbsorptionTotem();
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }
}
