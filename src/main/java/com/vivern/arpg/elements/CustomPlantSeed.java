package com.vivern.arpg.elements;

import com.vivern.arpg.blocks.CustomPlant;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CustomPlantSeed extends Item {
   public CustomPlant plant;
   public int burntime = -1;

   public CustomPlantSeed(CustomPlant plant) {
      this.setRegistryName(plant.getRegistryName() + "_seed");
      this.setCreativeTab(CreativeTabs.MISC);
      this.setTranslationKey(plant.getRegistryName() + "_seed");
      this.plant = plant;
   }

   public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
      if (worldIn.isAirBlock(pos.up()) && this.plant.canBlockStay(worldIn, pos.up()) && facing == EnumFacing.UP) {
         player.getHeldItem(hand).shrink(1);
         worldIn.setBlockState(pos.up(), this.plant.getDefaultState().withProperty(CustomPlant.GROWED, false));
         return EnumActionResult.SUCCESS;
      } else {
         return EnumActionResult.FAIL;
      }
   }

   public int getItemBurnTime(ItemStack itemStack) {
      return this.burntime * 20;
   }
}
