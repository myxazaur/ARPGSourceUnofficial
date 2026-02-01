package com.vivern.arpg.blocks;

import com.vivern.arpg.tileentity.TileWeaponSpawner;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWeaponSpawner extends Block {
   public BlockWeaponSpawner() {
      super(Material.ROCK);
      this.setRegistryName("weapon_spawner");
      this.setTranslationKey("weapon_spawner");
      this.blockHardness = -1.0F;
      this.blockResistance = 99999.0F;
      this.setCreativeTab(CreativeTabs.REDSTONE);
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.up());
      if (worldIn.getTileEntity(pos) instanceof TileWeaponSpawner) {
         TileWeaponSpawner tileentity = (TileWeaponSpawner)worldIn.getTileEntity(pos);
         if (flag) {
            tileentity.redstone++;
         }

         if (!flag) {
            tileentity.redstone = 0;
         }
      }
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (worldIn.isRemote) {
         return true;
      } else {
         TileEntity tileentity = worldIn.getTileEntity(pos);
         if (tileentity instanceof TileWeaponSpawner) {
            playerIn.displayGUIChest((TileWeaponSpawner)tileentity);
         }

         return true;
      }
   }

   public Class<TileWeaponSpawner> getTileEntityClass() {
      return TileWeaponSpawner.class;
   }

   public TileWeaponSpawner getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileWeaponSpawner)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileWeaponSpawner createTileEntity(World world, IBlockState blockState) {
      return new TileWeaponSpawner();
   }

   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
      TileEntity tileentity = worldIn.getTileEntity(pos);
      if (tileentity instanceof TileWeaponSpawner) {
         InventoryHelper.dropInventoryItems(worldIn, pos, (TileWeaponSpawner)tileentity);
         worldIn.updateComparatorOutputLevel(pos, this);
      }

      super.breakBlock(worldIn, pos, state);
   }

   public boolean hasComparatorInputOverride(IBlockState state) {
      return true;
   }

   public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
      return Container.calcRedstone(worldIn.getTileEntity(pos));
   }
}
