package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.tileentity.IManaBuffer;
import com.Vivern.Arpg.tileentity.TileManaBottle;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ManaBottle extends Block {
   public static final AxisAlignedBB ALL_AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.75, 0.875);

   public ManaBottle() {
      super(IManaBuffer.MAGIC_BLOCK);
      this.setRegistryName("mana_bottle");
      this.setTranslationKey("mana_bottle");
      this.blockHardness = 5.5F;
      this.blockResistance = 15.0F;
      this.setCreativeTab(CreativeTabs.MISC);
      this.setLightLevel(0.3F);
      this.setSoundType(SoundTypeShards.SHARDS);
      this.setHarvestLevel("pickaxe", 1);
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

   public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
      if (!world.isRemote) {
         ItemStack stack = new ItemStack(Item.getItemFromBlock(this));
         TileEntity tile = this.getTileEntity(world, pos);
         if (tile != null && tile instanceof TileManaBottle) {
            TileManaBottle manabottle = (TileManaBottle)tile;
            NBTTagCompound compound = new NBTTagCompound();
            compound.setFloat("max", manabottle.getManaBuffer().getManaStorageSize());
            compound.setFloat("manaStored", manabottle.getManaBuffer().getManaStored());
            NBTHelper.GiveNBTtag(stack, compound, "BlockEntityTag");
            NBTHelper.SetNBTtag(stack, compound, "BlockEntityTag");
         }

         spawnAsEntity(world, pos, stack);
      }
   }

   public int quantityDropped(IBlockState state, int fortune, Random random) {
      return 0;
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      boolean flag1 = false;
      boolean powered = worldIn.isBlockPowered(pos);
      if (blockIn != this && (powered || blockIn.getDefaultState().canProvidePower())) {
         TileEntity tile = this.getTileEntity(worldIn, pos);
         if (tile != null && tile instanceof TileManaBottle) {
            TileManaBottle manabottle = (TileManaBottle)tile;
            manabottle.opened = powered;
         }
      }
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      TileEntity tile = this.getTileEntity(worldIn, pos);
      if (tile != null && tile instanceof TileManaBottle) {
         TileManaBottle manabottle = (TileManaBottle)tile;
         manabottle.opened = !manabottle.opened;
         return true;
      } else {
         return false;
      }
   }

   public static void trySendPacketUpdate(World world, BlockPos pos, TileManaBottle tile) {
      int range = 64;

      for (EntityPlayerMP playerIn : world.getEntitiesWithinAABB(
         EntityPlayerMP.class,
         new AxisAlignedBB(
            pos.getX() + 64,
            pos.getY() + 64,
            pos.getZ() + 64,
            pos.getX() - 64,
            pos.getY() - 64,
            pos.getZ() - 64
         )
      )) {
         SPacketUpdateTileEntity spacketupdatetileentity = tile.getUpdatePacket();
         if (spacketupdatetileentity != null) {
            playerIn.connection.sendPacket(spacketupdatetileentity);
         }
      }
   }

   public Class<TileManaBottle> getTileEntityClass() {
      return TileManaBottle.class;
   }

   public TileManaBottle getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileManaBottle)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileManaBottle createTileEntity(World world, IBlockState blockState) {
      return new TileManaBottle();
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
