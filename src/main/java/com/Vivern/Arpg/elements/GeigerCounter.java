//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.Mana;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class GeigerCounter extends Item {
   public GeigerCounter() {
      this.setRegistryName("geiger_counter");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("geiger_counter");
   }

   public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (Minecraft.getMinecraft().player == entityIn
         && (Minecraft.getMinecraft().player.getHeldItemMainhand() == stack || Minecraft.getMinecraft().player.getHeldItemOffhand() == stack)) {
         Minecraft.getMinecraft()
            .ingameGUI
            .setOverlayMessage(TextFormatting.GREEN + "Your radiation: " + Mana.getRad(Minecraft.getMinecraft().player), false);
      }
   }

   public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
      if (Minecraft.getMinecraft().player != null) {
         tooltip.add(TextFormatting.GREEN + "Your radiation: " + Mana.getRad(Minecraft.getMinecraft().player));
      }
   }
}
