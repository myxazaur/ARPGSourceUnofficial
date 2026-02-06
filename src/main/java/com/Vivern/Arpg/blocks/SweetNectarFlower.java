package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BiomesRegister;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.tileentity.TileNexusFlower;
import javax.annotation.Nullable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SweetNectarFlower extends BlockBlockHard {
   protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.1875, 0.0, 0.1875, 0.8125, 0.8, 0.8125);

   public SweetNectarFlower() {
      super(Material.PLANTS, "sweet_nectar_flower", BlocksRegister.HR_CHLORINE_BELCHER, "axe", false);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setSoundType(SoundType.PLANT);
   }

   public Class<TileNexusFlower> getTileEntityClass() {
      return TileNexusFlower.class;
   }

   public TileNexusFlower getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileNexusFlower)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileNexusFlower createTileEntity(World world, IBlockState blockState) {
      return new TileNexusFlower();
   }

   public void breakBlock(World world, BlockPos pos, IBlockState state) {
      TileEntity tile = world.getTileEntity(pos);
      if (tile instanceof TileNexusFlower) {
         TileNexusFlower nexus = (TileNexusFlower)tile;
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
      if (tile instanceof TileNexusFlower) {
         TileNexusFlower nexus = (TileNexusFlower)tile;
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
      if (!world.isRemote) {
         TileEntity tile = world.getTileEntity(pos);
         if (tile instanceof TileNexusFlower) {
            TileNexusFlower nexus = (TileNexusFlower)tile;
            if (!nexus.invasionStarted) {
               if (world.getBiome(pos) == BiomesRegister.TOXIC_SLIME_LAND
                  && world.getBiome(pos.west(16)) == BiomesRegister.TOXIC_SLIME_LAND
                  && world.getBiome(pos.east(16)) == BiomesRegister.TOXIC_SLIME_LAND
                  && world.getBiome(pos.north(16)) == BiomesRegister.TOXIC_SLIME_LAND
                  && world.getBiome(pos.south(16)) == BiomesRegister.TOXIC_SLIME_LAND) {
                  nexus.startInvasion(Team.getTeamFor(player));
                  world.playSound(null, pos, Sounds.chlorine_belcher, SoundCategory.BLOCKS, 1.3F, 0.9F + world.rand.nextFloat() / 5.0F);
               } else if (player instanceof EntityPlayerMP) {
                  player.sendMessage(new TextComponentString("The flower must be deep in Slime Lands biome"));
               }
            }
         }
      }

      return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
   }

   @Override
   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   @Override
   public boolean isFullCube(IBlockState state) {
      return false;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return null;
   }
}
