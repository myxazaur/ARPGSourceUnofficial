package com.vivern.arpg.blocks;

import com.vivern.arpg.dimensions.ethernalfrost.DimensionEthernalFrost;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.ShardType;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.tileentity.TileNexusWinterAltar;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWinterAltar extends BlockBlockHard {
   public static AxisAlignedBB AABB = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 1.0, 0.9375);

   public BlockWinterAltar() {
      super(Material.ROCK, "winter_altar", BlocksRegister.HR_FROZEN_STONE, "pickaxe", false);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
   }

   @Override
   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   @Override
   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   @Override
   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public Class<TileNexusWinterAltar> getTileEntityClass() {
      return TileNexusWinterAltar.class;
   }

   public TileNexusWinterAltar getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileNexusWinterAltar)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileNexusWinterAltar createTileEntity(World world, IBlockState blockState) {
      return new TileNexusWinterAltar();
   }

   public boolean onBlockActivated(
      World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      player.swingArm(hand);
      if (!world.isRemote && hand == EnumHand.MAIN_HAND) {
         TileEntity tile = world.getTileEntity(pos);
         if (tile instanceof TileNexusWinterAltar) {
            TileNexusWinterAltar nexus = (TileNexusWinterAltar)tile;
            if (pos.getY() >= 80) {
               if (!nexus.invasionStarted && nexus.getElementCount(ShardType.COLD) >= 16.0F && DimensionEthernalFrost.isAuroraNow(world)) {
                  nexus.startInvasion(Team.getTeamFor(player));
                  world.playSound(null, pos, Sounds.phantasm_living, SoundCategory.BLOCKS, 1.3F, 0.9F + world.rand.nextFloat() / 5.0F);
                  nexus.acceptVialElements(ItemStack.EMPTY, ShardType.COLD, -16.0F);
               }
            } else if (player instanceof EntityPlayerMP) {
               player.sendMessage(new TextComponentString("The altar must be placed at 80 height or higher"));
            }
         }
      }

      return player.getHeldItemMainhand().getItem() != ItemsRegister.VIAL;
   }

   public static void trySendPacketUpdate(World world, BlockPos pos, TileNexusWinterAltar tile) {
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

   @Override
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }
}
