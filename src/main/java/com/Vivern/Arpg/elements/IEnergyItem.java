//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.NBTHelper;
import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IEnergyItem {
   default int addEnergyToItem(ItemStack stack, int maxAdd, boolean simulate) {
      int energyReceived = Math.min(this.getMaxEnergyStored(stack) - this.getEnergyStored(stack), Math.min(this.getThroughput(), maxAdd));
      if (!simulate) {
         NBTHelper.GiveNBTint(stack, 0, "rf");
         NBTHelper.AddNBTint(stack, energyReceived, "rf");
         this.onEnergyChanged(stack, energyReceived);
      }

      return energyReceived;
   }

   default int extractEnergyFromItem(ItemStack stack, int maxExtract, boolean simulate) {
      int energyExtracted = Math.min(this.getEnergyStored(stack), Math.min(this.getThroughput(), maxExtract));
      if (!simulate) {
         NBTHelper.AddNBTint(stack, -energyExtracted, "rf");
         this.onEnergyChanged(stack, -energyExtracted);
      }

      return energyExtracted;
   }

   default int getEnergyStored(ItemStack stack) {
      return NBTHelper.GetNBTint(stack, "rf");
   }

   int getMaxEnergyStored(ItemStack var1);

   int getThroughput();

   default void onEnergyChanged(ItemStack stack, int energy) {
   }

   static ItemStack getFullcharged(Item item, int count) {
      ItemStack stack = new ItemStack(item, count);
      if (item instanceof IEnergyItem) {
         IEnergyItem iEnergyItem = (IEnergyItem)item;
         int max = iEnergyItem.getMaxEnergyStored(stack);
         NBTHelper.GiveNBTint(stack, 0, "rf");
         NBTHelper.SetNBTint(stack, max, "rf");
         iEnergyItem.onEnergyChanged(stack, max);
      }

      return stack;
   }

   @SideOnly(Side.CLIENT)
   static void addRFInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
      if (stack.getItem() instanceof IEnergyItem) {
         IEnergyItem energyItem = (IEnergyItem)stack.getItem();
         int stored = energyItem.getEnergyStored(stack);
         int maxstored = energyItem.getMaxEnergyStored(stack);
         tooltip.add(TextFormatting.RED + "RF: " + stored + "/" + maxstored);
         ItemWeapon.addTooltipBar((float)stored / maxstored, 9689, TextFormatting.RED, TextFormatting.DARK_RED, tooltip);
      }
   }
}
