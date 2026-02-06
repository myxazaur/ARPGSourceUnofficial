package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.tileentity.TileNexusNiveolite;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockNiveoliteGame extends BlockBlockHard {
   public BlockNiveoliteGame() {
      super(Material.ROCK, "block_niveolite_game", BlocksRegister.HR_NIVEOUS_HALL, "pickaxe", false);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setOpacity(0);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileNexusNiveolite createTileEntity(World world, IBlockState blockState) {
      return new TileNexusNiveolite();
   }

   public void breakBlock(World world, BlockPos pos, IBlockState state) {
      TileEntity tile = world.getTileEntity(pos);
      if (tile instanceof TileNexusNiveolite) {
         TileNexusNiveolite nexus = (TileNexusNiveolite)tile;
         if (nexus.invasionStarted) {
            nexus.onInvasionEnd(false);
         }
      }

      super.breakBlock(world, pos, state);
   }

   protected boolean canSilkHarvest() {
      return false;
   }

   public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune) {
      TileEntity tile = world.getTileEntity(pos);
      if (tile instanceof TileNexusNiveolite) {
         TileNexusNiveolite nexus = (TileNexusNiveolite)tile;
         if (nexus.invasionStarted) {
            return;
         }
      }

      super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);
   }

   public boolean onBlockActivated(
      World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      player.swingArm(hand);
      if (!world.isRemote
         && hand == EnumHand.MAIN_HAND
         && player.getHeldItemMainhand().getItem() == ItemsRegister.NIVEOLITE
         && player.getHeldItemMainhand().getCount() >= 8) {
         TileEntity tile = world.getTileEntity(pos);
         if (tile instanceof TileNexusNiveolite) {
            TileNexusNiveolite nexus = (TileNexusNiveolite)tile;
            if (!nexus.invasionStarted) {
               nexus.startInvasion(Team.getTeamFor(player));
               world.playSound(null, pos, Sounds.item_misc_d, SoundCategory.BLOCKS, 1.3F, 0.9F + world.rand.nextFloat() / 5.0F);
               player.getHeldItemMainhand().shrink(8);
            }
         }
      }

      return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
   }

   public EnumBlockRenderType getRenderType(IBlockState state) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   @Override
   public boolean isFullCube(IBlockState state) {
      return false;
   }
}
