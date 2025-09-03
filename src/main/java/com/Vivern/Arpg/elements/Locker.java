//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.tileentity.ChestLock;
import com.Vivern.Arpg.tileentity.TileARPGChest;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class Locker extends Item {
   public Locker() {
      this.setRegistryName("locker");
      this.setCreativeTab(CreativeTabs.MISC);
      this.setTranslationKey("locker");
      this.setMaxDamage(100);
      this.setMaxStackSize(1);
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      player.setActiveHand(hand);
      RayTraceResult raytraceresult = this.rayTrace(world, player, false);
      if (raytraceresult == null) {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      } else if (raytraceresult.typeOfHit != Type.BLOCK) {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      } else if (!world.isRemote) {
         BlockPos pos = raytraceresult.getBlockPos();
         TileEntity tile = world.getTileEntity(pos);
         if (tile != null && tile instanceof TileARPGChest) {
            TileARPGChest chest = (TileARPGChest)tile;
            if ("rusted_key".equals(itemstack.getDisplayName())) {
               chest.lockOrUnlockWith(ChestLock.RUSTED_KEY, true);
            }

            if ("winter_curse".equals(itemstack.getDisplayName())) {
               chest.lockOrUnlockWith(ChestLock.WINTER_CURSE, true);
            }

            if ("puzzle".equals(itemstack.getDisplayName())) {
               chest.lockOrUnlockWith(ChestLock.PUZZLE, true);
            }

            PacketHandler.trySendPacketUpdate(world, pos, chest, 64.0);
         }

         return new ActionResult(EnumActionResult.SUCCESS, itemstack);
      } else {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      }
   }
}
