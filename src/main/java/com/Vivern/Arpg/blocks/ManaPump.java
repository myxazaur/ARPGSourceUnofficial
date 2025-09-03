//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.tileentity.IManaBuffer;
import com.Vivern.Arpg.tileentity.TileManaPump;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ManaPump extends Block {
   public static final AxisAlignedBB AABB1 = new AxisAlignedBB(0.0, 0.15625, 0.34375, 1.0, 0.46875, 0.65625);
   public static final AxisAlignedBB AABB2 = new AxisAlignedBB(0.34375, 0.15625, 0.0, 0.65625, 0.46875, 1.0);

   public ManaPump() {
      super(IManaBuffer.MAGIC_BLOCK);
      this.setRegistryName("mana_pump");
      this.setTranslationKey("mana_pump");
      this.blockHardness = 5.5F;
      this.blockResistance = 15.0F;
      this.setCreativeTab(CreativeTabs.MISC);
      this.setSoundType(SoundTypeShards.SHARDS);
      this.setHarvestLevel("pickaxe", 1);
   }

   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
      super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
      TileEntity tile = worldIn.getTileEntity(pos);
      if (tile != null && tile instanceof TileManaPump) {
         TileManaPump manap = (TileManaPump)tile;
         manap.facing = placer.getHorizontalFacing();
      }
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      TileEntity tile = source.getTileEntity(pos);
      if (tile != null && tile instanceof TileManaPump) {
         TileManaPump manap = (TileManaPump)tile;
         if (manap.facing != null) {
            return manap.facing.getAxis() == Axis.X ? AABB1 : AABB2;
         }
      }

      return AABB1;
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileManaPump createTileEntity(World world, IBlockState blockState) {
      return new TileManaPump();
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public EnumBlockRenderType getRenderType(IBlockState state) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }
}
