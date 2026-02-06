package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.tileentity.TileTeamBanner;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTeamBanner extends Block {
   public static final AxisAlignedBB ALL_AABB = GetMOP.newAABB(4, 26, 0);

   public BlockTeamBanner() {
      super(Material.WOOD);
      this.setRegistryName("team_banner");
      this.setTranslationKey("team_banner");
      this.blockHardness = 2.0F;
      this.blockResistance = 20.0F;
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setSoundType(SoundType.WOOD);
      this.setHarvestLevel("axe", 0);
   }

   public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
      TileEntity tileEntity = world.getTileEntity(pos);
      if (tileEntity != null && tileEntity instanceof TileTeamBanner && placer != null && placer instanceof EntityPlayer) {
         TileTeamBanner tilebanner = (TileTeamBanner)tileEntity;
         EntityPlayer player = (EntityPlayer)placer;
         tilebanner.playername = player.getName();
         tilebanner.teamname = Team.nameOfPersonalTeam(tilebanner.playername);
         tilebanner.rotation = (short)MathHelper.wrapDegrees(player.rotationYaw + 180.0F);
         if (player.getGameProfile() != null && player.getGameProfile().getId() != null) {
            tilebanner.playerUUID = player.getGameProfile().getId();
         } else {
            tilebanner.playerUUID = player.getUniqueID();
         }
      }
   }

   public boolean onBlockActivated(
      World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (!world.isRemote && hand == EnumHand.MAIN_HAND) {
         TileEntity tileEntity = world.getTileEntity(pos);
         if (tileEntity != null && tileEntity instanceof TileTeamBanner) {
            TileTeamBanner tilebanner = (TileTeamBanner)tileEntity;
            tilebanner.joinNewPlayer(player);
         }
      }

      return true;
   }

   public EnumBlockRenderType getRenderType(IBlockState state) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
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
   public TileTeamBanner createTileEntity(World world, IBlockState blockState) {
      return new TileTeamBanner();
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }
}
