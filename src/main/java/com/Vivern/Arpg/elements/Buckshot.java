//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.ColorConverters;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.NBTHelper;
import java.util.List;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Buckshot extends Item implements IItemColor {
   public Buckshot() {
      this.setRegistryName("buckshot");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("buckshot");
   }

   public int colorMultiplier(ItemStack stack, int tintIndex) {
      ItemBullet bullet = ItemBullet.getItemBulletFromString(NBTHelper.GetNBTstring(stack, "bullet"));
      return tintIndex == 1 && bullet != null ? ColorConverters.RGBtoDecimal(bullet.colorR, bullet.colorG, bullet.colorB) : 16777215;
   }

   public static ItemStack getBuckshotStack(String bullet, int amount) {
      ItemStack stack = new ItemStack(ItemsRegister.BUCKSHOT, amount);
      NBTHelper.GiveNBTstring(stack, bullet, "bullet");
      NBTHelper.SetNBTstring(stack, bullet, "bullet");
      NBTHelper.SetNBTstring(stack, bullet, "bullet");
      return stack;
   }

   public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
      tooltip.add("Ammo: " + NBTHelper.GetNBTstring(stack, "bullet"));
   }
}
