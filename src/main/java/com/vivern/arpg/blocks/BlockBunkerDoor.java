package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.tileentity.TileBunkerDoor;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBunkerDoor extends BlockBlockHard {
   public static final PropertyDirection FACING = BlockDirectional.FACING;

   public BlockBunkerDoor() {
      super(Material.IRON, "block_bunker_door", BlocksRegister.HR_BUNKER, "pickaxe", false);
      this.setCreativeTab(CreativeTabs.REDSTONE);
      this.setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH));
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.up());
      if (worldIn.getTileEntity(pos) instanceof TileBunkerDoor) {
         TileBunkerDoor tileentity = (TileBunkerDoor)worldIn.getTileEntity(pos);
         if (flag && tileentity.keyCard.isEmpty()) {
            if (!tileentity.started) {
               tileentity.resetAndStart(false);
               worldIn.playSound(null, pos, Sounds.keycard_open, SoundCategory.BLOCKS, 0.7F, 0.8F + RANDOM.nextFloat() / 5.0F);
            } else {
               tileentity.started = false;
            }
         }
      }
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (worldIn.isRemote) {
         return true;
      } else {
         TileEntity tileentity = worldIn.getTileEntity(pos);
         if (tileentity != null && tileentity instanceof TileBunkerDoor) {
            TileBunkerDoor door = (TileBunkerDoor)tileentity;
            if (door.keyCard.isEmpty()) {
               if (player.isCreative() || player.getHeldItem(hand).getItem() == ItemsRegister.BUNKERKEYCARD) {
                  door.keyCard = player.getHeldItem(hand).copy();
                  worldIn.playSound(null, pos, Sounds.item_misc_b, SoundCategory.BLOCKS, 0.7F, 0.8F + RANDOM.nextFloat() / 5.0F);
               }
            } else if (!door.started && ItemStack.areItemStacksEqual(player.getHeldItem(hand), door.keyCard)) {
               door.resetAndStart(false);
               worldIn.playSound(null, pos, Sounds.keycard_open, SoundCategory.BLOCKS, 0.7F, 0.8F + RANDOM.nextFloat() / 5.0F);
            }
         }

         return true;
      }
   }

   public Class<TileBunkerDoor> getTileEntityClass() {
      return TileBunkerDoor.class;
   }

   public TileBunkerDoor getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileBunkerDoor)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileBunkerDoor createTileEntity(World world, IBlockState blockState) {
      return new TileBunkerDoor();
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer).getOpposite());
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
