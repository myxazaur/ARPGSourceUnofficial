package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.potions.PotionEffects;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class AntiRadInjector extends Item {
   public AntiRadInjector() {
      this.setRegistryName("anti_rad_injector");
      this.setCreativeTab(CreativeTabs.FOOD);
      this.setTranslationKey("anti_rad_injector");
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      Mana.addRad(player, -100, false);
      PotionEffect eff = player.getActivePotionEffect(PotionEffects.RAD_REDUCTION);
      player.addPotionEffect(new PotionEffect(PotionEffects.RAD_REDUCTION, 500, eff == null ? 0 : eff.getAmplifier() + 1));
      if (eff != null && eff.getAmplifier() > 4) {
         player.attackEntityFrom(DamageSource.WITHER, 5 * (eff.getAmplifier() - 4));
      }

      itemstack.shrink(1);
      world.playSound(
         (EntityPlayer)null,
         player.posX,
         player.posY,
         player.posZ,
         Sounds.injector,
         SoundCategory.PLAYERS,
         0.8F,
         0.9F + itemRand.nextFloat() / 5.0F
      );
      return new ActionResult(EnumActionResult.SUCCESS, itemstack);
   }
}
