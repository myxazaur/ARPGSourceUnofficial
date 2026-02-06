package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.entity.EntityMagicUI;
import com.Vivern.Arpg.main.IMagicUI;
import com.Vivern.Arpg.main.Main;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.tileentity.TileAlchemicLab;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
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
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AlchemicLab extends Block implements IMagicUI {
   public static final PropertyBool NORTH = PropertyBool.create("north");
   public static final PropertyBool EAST = PropertyBool.create("east");
   public static final PropertyBool SOUTH = PropertyBool.create("south");
   public static final PropertyBool WEST = PropertyBool.create("west");

   public AlchemicLab() {
      super(Material.GLASS);
      this.setRegistryName("alchemic_lab");
      this.setTranslationKey("alchemic_lab");
      this.blockHardness = 2.5F;
      this.blockResistance = 1.0F;
      this.setCreativeTab(CreativeTabs.MISC);
      this.setLightLevel(0.5F);
      this.setSoundType(SoundTypeShards.SHARDS);
      this.setHarvestLevel("pickaxe", 0);
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
      return BlockRenderLayer.TRANSLUCENT;
   }

   public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
      return layer == BlockRenderLayer.TRANSLUCENT || layer == BlockRenderLayer.CUTOUT;
   }

   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      boolean west = worldIn.getTileEntity(pos.west()) != null
         && worldIn.getTileEntity(pos.west()).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.EAST);
      boolean east = worldIn.getTileEntity(pos.east()) != null
         && worldIn.getTileEntity(pos.east()).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.WEST);
      boolean south = worldIn.getTileEntity(pos.south()) != null
         && worldIn.getTileEntity(pos.south()).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.NORTH);
      boolean north = worldIn.getTileEntity(pos.north()) != null
         && worldIn.getTileEntity(pos.north()).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.SOUTH);
      return state.withProperty(WEST, west).withProperty(EAST, east).withProperty(NORTH, north).withProperty(SOUTH, south);
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      return worldIn.isAirBlock(pos.up());
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{NORTH, EAST, SOUTH, WEST});
   }

   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
      return BlockFaceShape.UNDEFINED;
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getActualState(this.getDefaultState(), worldIn, pos);
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (!worldIn.isRemote) {
         TileAlchemicLab tile = this.getTileEntity(worldIn, pos);
         worldIn.playSound((EntityPlayer)null, pos, Sounds.vessel_hit, SoundCategory.PLAYERS, 0.5F, 0.9F + RANDOM.nextFloat() / 5.0F);
         if (tile != null) {
            player.openGui(Main.instance, 2, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
         }
      }

      return true;
   }

   public static void trySendPacketUpdate(World world, BlockPos pos, TileAlchemicLab tile, int range) {
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

   public Class<TileAlchemicLab> getTileEntityClass() {
      return TileAlchemicLab.class;
   }

   public TileAlchemicLab getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileAlchemicLab)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileAlchemicLab createTileEntity(World world, IBlockState blockState) {
      return new TileAlchemicLab();
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public int getMetaFromState(IBlockState state) {
      return 0;
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState();
   }
}
