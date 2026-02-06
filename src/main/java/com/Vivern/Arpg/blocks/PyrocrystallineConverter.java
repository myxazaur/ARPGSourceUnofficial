package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.Main;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.tileentity.TilePyrocrystallineConverter;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PyrocrystallineConverter extends Block {
   public static final PropertyDirection FACING = BlockDirectional.FACING;

   public PyrocrystallineConverter() {
      super(Material.GLASS);
      this.setRegistryName("pyrocrystalline_converter");
      this.setTranslationKey("pyrocrystalline_converter");
      this.blockHardness = 4.5F;
      this.blockResistance = 16.0F;
      this.setCreativeTab(CreativeTabs.MISC);
      this.setSoundType(SoundType.STONE);
      this.setHarvestLevel("pickaxe", 0);
      this.setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH));
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
      return BlockFaceShape.UNDEFINED;
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (!worldIn.isRemote) {
         TilePyrocrystallineConverter tile = this.getTileEntity(worldIn, pos);
         worldIn.playSound((EntityPlayer)null, pos, Sounds.vessel_hit, SoundCategory.PLAYERS, 0.5F, 0.9F + RANDOM.nextFloat() / 5.0F);
         if (tile != null) {
            player.openGui(Main.instance, 8, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
         }
      }

      return false;
   }

   public static void trySendPacketUpdate(World world, BlockPos pos, TilePyrocrystallineConverter tile, int range) {
      if (!world.isRemote) {
         for (EntityPlayerMP playerIn : world.getEntitiesWithinAABB(
            EntityPlayerMP.class,
            new AxisAlignedBB(
               pos.getX() + range,
               pos.getY() + range,
               pos.getZ() + range,
               pos.getX() - range,
               pos.getY() - range,
               pos.getZ() - range
            )
         )) {
            SPacketUpdateTileEntity spacketupdatetileentity = tile.getUpdatePacket();
            if (spacketupdatetileentity != null) {
               playerIn.connection.sendPacket(spacketupdatetileentity);
            }
         }
      }
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.up());
      if (worldIn.getTileEntity(pos) instanceof TilePyrocrystallineConverter) {
         TilePyrocrystallineConverter tileentity = (TilePyrocrystallineConverter)worldIn.getTileEntity(pos);
         if (flag) {
            tileentity.redstone++;
         }

         if (!flag) {
            tileentity.redstone = 0;
         }
      }
   }

   public Class<TilePyrocrystallineConverter> getTileEntityClass() {
      return TilePyrocrystallineConverter.class;
   }

   public TilePyrocrystallineConverter getTileEntity(IBlockAccess world, BlockPos position) {
      return (TilePyrocrystallineConverter)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TilePyrocrystallineConverter createTileEntity(World world, IBlockState blockState) {
      return new TilePyrocrystallineConverter();
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public static BlockPos getOffsetBlock(World world, BlockPos pos) {
      return pos.offset((EnumFacing)world.getBlockState(pos).getValue(FACING), 2);
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta));
   }

   public int getMetaFromState(IBlockState state) {
      return ((EnumFacing)state.getValue(FACING)).getIndex();
   }

   public IBlockState withRotation(IBlockState state, Rotation rot) {
      return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
   }

   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
      return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{FACING});
   }
}
