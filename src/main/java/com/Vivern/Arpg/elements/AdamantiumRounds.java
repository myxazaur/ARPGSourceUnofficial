//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.ColorConverters;
import com.Vivern.Arpg.main.NBTHelper;
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
