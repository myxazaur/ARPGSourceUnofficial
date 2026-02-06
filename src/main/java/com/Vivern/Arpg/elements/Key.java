package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.tileentity.ChestLock;
import com.Vivern.Arpg.tileentity.TileARPGChest;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class Key extends ItemItem {
   public ChestLock lockOpens;
   public boolean consumes;

   public Key(String name, int maxstacksize, ChestLock lockOpens, boolean consumes) {
      super(name, CreativeTabs.TOOLS, 0, maxstacksize);
      this.lockOpens = lockOpens;
      this.consumes = consumes;
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      player.setActiveHand(hand);
      RayTraceResult raytraceresult = this.rayTrace(world, player, false);
      if (raytraceresult == null) {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      } else if (raytraceresult.typeOfHit != Type.BLOCK) {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      } else {
         BlockPos pos = raytraceresult.getBlockPos();
         TileEntity tile = world.getTileEntity(pos);
         if (tile != null && tile instanceof TileARPGChest) {
            TileARPGChest chest = (TileARPGChest)tile;
            if (chest.isLockedWith(this.lockOpens)) {
               world.playSound(null, pos, Sounds.item_misc_b, SoundCategory.BLOCKS, 0.5F, 0.9F + itemRand.nextFloat() / 5.0F);
               if (!world.isRemote) {
                  chest.lockOrUnlockWith(this.lockOpens, false);
                  if (this.consumes) {
                     itemstack.shrink(1);
                  }

                  PacketHandler.trySendPacketUpdate(world, pos, chest, 64.0);
               }

               return new ActionResult(EnumActionResult.SUCCESS, itemstack);
            }
         }

         return new ActionResult(EnumActionResult.PASS, itemstack);
      }
   }
}
