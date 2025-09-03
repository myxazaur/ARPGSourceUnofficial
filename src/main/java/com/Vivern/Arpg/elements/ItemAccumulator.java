//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAccumulator extends Item implements IEnergyItem {
   public static int REDSTONE_ION_CAPACITY = 150000;
   public static int REDSTONE_ION_THROUGHPUT = 1000;
   public static int LEAD_ACID_CAPACITY = 500000;
   public static int LEAD_ACID_THROUGHPUT = 2000;
   public static int LI_ION_CAPACITY = 2000000;
   public static int LI_ION_THROUGHPUT = 4000;
   public static int ANCIENT_CAPACITY = 6000000;
   public static int ANCIENT_THROUGHPUT = 8000;
   public static int AQUATRONIC_CAPACITY = 16000000;
   public static int AQUATRONIC_THROUGHPUT = 16000;
   public static int TOPAZITRON_CAPACITY = 30000000;
   public static int TOPAZITRON_THROUGHPUT = 32000;
   public int maxRF;
   public int throughput;

   public ItemAccumulator(String name, int maxRF, int throughput) {
      this.setRegistryName(name);
      this.setCreativeTab(CreativeTabs.MISC);
      this.setTranslationKey(name);
      this.setMaxDamage(1);
      this.maxRF = maxRF;
      this.throughput = throughput;
   }

   public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
      IEnergyItem.addRFInformation(stack, worldIn, tooltip, flagIn);
   }

   @Override
   public int getMaxEnergyStored(ItemStack stack) {
      return this.maxRF;
   }

   @Override
   public int getThroughput() {
      return this.throughput;
   }

   public double getDurabilityForDisplay(ItemStack stack) {
      return 1.0 - (double)this.getEnergyStored(stack) / this.getMaxEnergyStored(stack);
   }

   public boolean showDurabilityBar(ItemStack stack) {
      return this.getEnergyStored(stack) != 0;
   }

   @Override
   public void onEnergyChanged(ItemStack stack, int energy) {
      if (this.getEnergyStored(stack) == this.getMaxEnergyStored(stack)) {
         stack.setItemDamage(1);
      } else {
         stack.setItemDamage(0);
      }
   }
}
