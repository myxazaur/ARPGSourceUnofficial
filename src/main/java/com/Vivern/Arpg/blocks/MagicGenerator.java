package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.tileentity.TileMagicGenerator;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MagicGenerator extends Block {
   public MagicGenerator() {
      super(Material.IRON);
      this.setRegistryName("magic_generator");
      this.setTranslationKey("magic_generator");
      this.blockHardness = 6.5F;
      this.blockResistance = 10.0F;
      this.setCreativeTab(CreativeTabs.MISC);
      this.setSoundType(SoundType.WOOD);
      this.setHarvestLevel("pickaxe", 1);
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   public EnumBlockRenderType getRenderType(IBlockState state) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileMagicGenerator createTileEntity(World world, IBlockState blockState) {
      return new TileMagicGenerator();
   }
}
