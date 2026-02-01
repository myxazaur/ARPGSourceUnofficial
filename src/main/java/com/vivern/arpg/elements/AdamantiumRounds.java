package com.vivern.arpg.elements;

import com.vivern.arpg.main.ColorConverters;
import com.vivern.arpg.main.NBTHelper;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AdamantiumRounds extends Item implements IItemColor {
   public AdamantiumRounds() {
      this.setRegistryName("adamantium_rounds");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("adamantium_rounds");
   }

   public int colorMultiplier(ItemStack stack, int tintIndex) {
      ItemBullet bullet = ItemBullet.getItemBulletFromString(NBTHelper.GetNBTstring(stack, "bullet"));
      return tintIndex == 1 && bullet != null ? ColorConverters.RGBtoDecimal(bullet.colorR, bullet.colorG, bullet.colorB) : 16777215;
   }
}
