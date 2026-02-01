package com.vivern.arpg.elements;

import com.vivern.arpg.blocks.CustomPlant;
import com.vivern.arpg.main.Mana;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CustomPlantSeedEatable extends ItemFood {
   public CustomPlant plant;
   public int burntime = -1;

   public CustomPlantSeedEatable(CustomPlant plant, int amount, Potion potion, int dur, int amp, float effectChance) {
      super(amount, false);
      this.setRegistryName(plant.getRegistryName() + "_seed");
      this.setCreativeTab(CreativeTabs.MISC);
      this.setTranslationKey(plant.getRegistryName() + "_seed");
      this.plant = plant;
      if (potion != null) {
         this.setPotionEffect(new PotionEffect(potion, dur, amp), effectChance);
      }
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

   public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
      if (this.plant.seedRadiation != 0 && entityLiving instanceof EntityPlayer) {
         Mana.addRad((EntityPlayer)entityLiving, this.plant.seedRadiation, false);
      }

      return super.onItemUseFinish(stack, worldIn, entityLiving);
   }

   public int getItemBurnTime(ItemStack itemStack) {
      return this.burntime * 20;
   }
}
