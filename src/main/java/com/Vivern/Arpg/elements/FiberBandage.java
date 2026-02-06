package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.PotionEffects;
import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class FiberBandage extends Item {
   public FiberBandage() {
      this.setRegistryName("fiber_bandage");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("fiber_bandage");
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      Weapons.setPotionIfEntityLB(player, PotionEffects.FIBER_BANDAGING, 12000, 0);
      itemstack.shrink(1);
      world.playSound(
         (EntityPlayer)null,
         player.posX,
         player.posY,
         player.posZ,
         Sounds.sea_effloresce_impact,
         SoundCategory.PLAYERS,
         0.8F,
         1.45F + itemRand.nextFloat() / 5.0F
      );
      return new ActionResult(EnumActionResult.SUCCESS, itemstack);
   }

   public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
      tooltip.add(
         "For a while, it slightly reduces the radiation that affects you from the outside and reduces the chance of getting dirty in liquids: poisons, toxins and mucus"
      );
   }
}
