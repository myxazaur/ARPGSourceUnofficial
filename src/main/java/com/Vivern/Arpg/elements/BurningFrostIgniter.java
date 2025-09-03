//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.BlocksRegister;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BurningFrostIgniter extends Item {
   public BurningFrostIgniter() {
      this.setRegistryName("burning_frost_igniter");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("burning_frost_igniter");
      this.setMaxDamage(40);
      this.setMaxStackSize(1);
   }

   public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
      pos = pos.offset(facing);
      ItemStack itemstack = player.getHeldItem(hand);
      if (!player.canPlayerEdit(pos, facing, itemstack)) {
         return EnumActionResult.FAIL;
      } else {
         if (worldIn.isAirBlock(pos)) {
            worldIn.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
            worldIn.setBlockState(pos, BlocksRegister.BURNINGFROST.getDefaultState(), 11);
         }

         if (player instanceof EntityPlayerMP) {
            CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, itemstack);
         }

         itemstack.damageItem(1, player);
         return EnumActionResult.SUCCESS;
      }
   }
}
