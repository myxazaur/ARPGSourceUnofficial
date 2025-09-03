//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.tileentity.IManaBuffer;
import com.Vivern.Arpg.tileentity.TileTopazCrystal;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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

public class SapphireEye extends Item implements IBauble {
   public SapphireEye() {
      this.setRegistryName("sapphire_eye");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("sapphire_eye");
      this.setMaxStackSize(1);
   }

   public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
      ItemStack itemstack = playerIn.getHeldItem(handIn);
      RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, false);
      if (raytraceresult == null) {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      } else {
         if (raytraceresult.typeOfHit == Type.BLOCK) {
            BlockPos blockpos = raytraceresult.getBlockPos();
            if (!worldIn.isBlockModifiable(playerIn, blockpos)
               || !playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack)) {
               return new ActionResult(EnumActionResult.PASS, itemstack);
            }

            TileEntity tentity = worldIn.getTileEntity(blockpos);
            if (tentity != null) {
               if (tentity instanceof IManaBuffer) {
                  if (playerIn.isSneaking()) {
                     NBTHelper.SetNBTboolean(itemstack, false, "topaz");
                     NBTHelper.SetNBTboolean(itemstack, false, "firstclicked");
                  }

                  boolean firstclicked = NBTHelper.GetNBTboolean(itemstack, "firstclicked");
                  if (firstclicked) {
                     boolean topaz = NBTHelper.GetNBTboolean(itemstack, "topaz");
                     BlockPos from = NBTHelper.GetNBTBlockPos(itemstack, "from");
                     if (from == blockpos) {
                        return new ActionResult(EnumActionResult.PASS, itemstack);
                     }

                     if (from != null && topaz) {
                        TileEntity tiletopazfrom = worldIn.getTileEntity(from);
                        if (tiletopazfrom != null && tiletopazfrom instanceof TileTopazCrystal) {
                           ((TileTopazCrystal)tiletopazfrom).setPosToGive(blockpos);
                        }

                        NBTHelper.SetNBTboolean(itemstack, false, "topaz");
                     }

                     NBTHelper.SetNBTboolean(itemstack, false, "firstclicked");
                     worldIn.playSound(
                        playerIn,
                        playerIn.posX,
                        playerIn.posY,
                        playerIn.posZ,
                        Sounds.item_misc_d,
                        SoundCategory.BLOCKS,
                        0.5F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                  } else {
                     NBTHelper.GiveNBTboolean(itemstack, true, "firstclicked");
                     NBTHelper.SetNBTboolean(itemstack, true, "firstclicked");
                     NBTHelper.GiveNBTBlockPos(itemstack, blockpos, "from");
                     NBTHelper.SetNBTBlockPos(itemstack, blockpos, "from");
                     if (tentity instanceof TileTopazCrystal) {
                        NBTHelper.GiveNBTboolean(itemstack, true, "topaz");
                        NBTHelper.SetNBTboolean(itemstack, true, "topaz");
                     }

                     worldIn.playSound(
                        playerIn,
                        playerIn.posX,
                        playerIn.posY,
                        playerIn.posZ,
                        Sounds.item_misc_b,
                        SoundCategory.BLOCKS,
                        0.5F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                  }

                  return new ActionResult(EnumActionResult.SUCCESS, itemstack);
               }
            } else if (playerIn.isSneaking()) {
               NBTHelper.SetNBTboolean(itemstack, false, "topaz");
               NBTHelper.SetNBTboolean(itemstack, false, "firstclicked");
            }
         } else if (playerIn.isSneaking()) {
            NBTHelper.SetNBTboolean(itemstack, false, "topaz");
            NBTHelper.SetNBTboolean(itemstack, false, "firstclicked");
         }

         return new ActionResult(EnumActionResult.PASS, itemstack);
      }
   }

   @Override
   public BaubleType getBaubleType(ItemStack itemstack) {
      return BaubleType.TRINKET;
   }
}
