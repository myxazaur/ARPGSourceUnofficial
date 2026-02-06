package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.Main;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.tileentity.TileAlchemicVaporizer;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AlchemicVaporizer extends Block {
   public static final AxisAlignedBB ALL_AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 1.0, 0.875);

   public AlchemicVaporizer() {
      super(Material.IRON);
      this.setRegistryName("alchemic_vaporizer");
      this.setTranslationKey("alchemic_vaporizer");
      this.blockHardness = 8.5F;
      this.blockResistance = 15.0F;
      this.setCreativeTab(CreativeTabs.MISC);
      this.setSoundType(SoundTypeShards.METAL);
      this.setHarvestLevel("pickaxe", 1);
   }

   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
      TileEntity tileentity = worldIn.getTileEntity(pos);
      if (tileentity instanceof IInventory) {
         InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
         worldIn.updateComparatorOutputLevel(pos, this);
      }

      super.breakBlock(worldIn, pos, state);
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
      return layer == BlockRenderLayer.TRANSLUCENT || layer == BlockRenderLayer.CUTOUT;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return ALL_AABB;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return ALL_AABB;
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (!worldIn.isRemote) {
         TileAlchemicVaporizer tile = this.getTileEntity(worldIn, pos);
         worldIn.playSound((EntityPlayer)null, pos, Sounds.vessel_hit, SoundCategory.PLAYERS, 0.5F, 0.9F + RANDOM.nextFloat() / 5.0F);
         if (tile != null) {
            player.openGui(Main.instance, 3, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
         }
      }

      return false;
   }

   public Class<TileAlchemicVaporizer> getTileEntityClass() {
      return TileAlchemicVaporizer.class;
   }

   public TileAlchemicVaporizer getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileAlchemicVaporizer)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileAlchemicVaporizer createTileEntity(World world, IBlockState blockState) {
      return new TileAlchemicVaporizer();
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }
}
