package com.vivern.arpg.elements;

import com.vivern.arpg.blocks.BlockARPGChest;
import com.vivern.arpg.tileentity.EnumChest;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemARPGChest extends ItemBlock {
   public EnumChest chestType;

   public ItemARPGChest(Block block) {
      super(block);
      this.chestType = ((BlockARPGChest)block).chestType;
   }

   public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
      IBlockState iblockstate = worldIn.getBlockState(pos);
      Block block = iblockstate.getBlock();
      if (!block.isReplaceable(worldIn, pos)) {
         pos = pos.offset(facing);
      }

      ItemStack itemstack = player.getHeldItem(hand);
      if (!itemstack.isEmpty() && player.canPlayerEdit(pos, facing, itemstack) && worldIn.mayPlace(this.block, pos, false, facing, player)) {
         int i = this.getMetadata(itemstack.getMetadata());
         IBlockState iblockstate1 = this.block.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, i, player, hand);
         if (this.placeBlockAt(itemstack, player, worldIn, pos, facing, hitX, hitY, hitZ, iblockstate1)) {
            iblockstate1 = worldIn.getBlockState(pos);
            SoundType soundtype = iblockstate1.getBlock().getSoundType(iblockstate1, worldIn, pos, player);
            worldIn.playSound(
               player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F
            );
            itemstack.shrink(1);
         }

         return EnumActionResult.SUCCESS;
      } else {
         return EnumActionResult.FAIL;
      }
   }

   public boolean placeBlockAt(
      ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState
   ) {
      if (!world.setBlockState(pos, newState, 11)) {
         return false;
      } else {
         IBlockState state = world.getBlockState(pos);
         if (state.getBlock() == this.block) {
            setTileEntityNBT(world, player, pos, stack);
            ((BlockARPGChest)this.block).onBlockPlacedBy(world, pos, state, player, stack, side);
            if (player instanceof EntityPlayerMP) {
               CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, stack);
            }
         }

         return true;
      }
   }
}
