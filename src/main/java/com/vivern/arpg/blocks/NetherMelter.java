package com.vivern.arpg.blocks;

import com.vivern.arpg.entity.EntityMagicUI;
import com.vivern.arpg.main.IMagicUI;
import com.vivern.arpg.AbstractRPG;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.tileentity.TileNetherMelter;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NetherMelter extends Block implements IMagicUI {
   public static final PropertyDirection FACING = PropertyDirection.create("facing");

   public NetherMelter() {
      super(Material.ROCK);
      this.setRegistryName("nether_melter");
      this.setTranslationKey("nether_melter");
      this.blockHardness = 8.5F;
      this.blockResistance = 18.0F;
      this.setCreativeTab(CreativeTabs.MISC);
      this.setLightLevel(0.3F);
   }

   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
      TileEntity tileentity = worldIn.getTileEntity(pos);
      if (tileentity instanceof IInventory) {
         InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
         worldIn.updateComparatorOutputLevel(pos, this);
      }

      super.breakBlock(worldIn, pos, state);
   }

   @Override
   public void open(World world, EntityPlayer player, BlockPos pos, Entity entity) {
      if (!world.isRemote && IMagicUI.checkNoNearOpened(world, pos, null, 2)) {
         world.playSound(
            (EntityPlayer)null,
            pos.getX(),
            pos.getY(),
            pos.getZ(),
            Sounds.mui_open,
            IMagicUI.soundc,
            0.8F,
            0.85F + RANDOM.nextFloat() * 0.3F
         );
         EntityMagicUI.EntityMUIManaBuffer mui1 = new EntityMagicUI.EntityMUIManaBuffer(world, pos);
         mui1.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());
         world.spawnEntity(mui1);
      }
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT_MIPPED;
   }

   @SideOnly(Side.CLIENT)
   public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
      return 13107400;
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (!worldIn.isRemote) {
         TileNetherMelter tile = this.getTileEntity(worldIn, pos);
         if (tile != null) {
            player.openGui(AbstractRPG.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
         }
      }

      return false;
   }

   public static void trySendPacketUpdate(World world, BlockPos pos, TileNetherMelter tile) {
      int range = 8;

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

   public Class<TileNetherMelter> getTileEntityClass() {
      return TileNetherMelter.class;
   }

   public TileNetherMelter getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileNetherMelter)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileNetherMelter createTileEntity(World world, IBlockState blockState) {
      return new TileNetherMelter();
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
   }

   public IBlockState getStateFromMeta(int meta) {
      EnumFacing enumfacing = EnumFacing.byIndex(meta);
      if (enumfacing.getAxis() == Axis.Y) {
         enumfacing = EnumFacing.NORTH;
      }

      return this.getDefaultState().withProperty(FACING, enumfacing);
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
