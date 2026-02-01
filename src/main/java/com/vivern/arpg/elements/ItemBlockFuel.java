package com.vivern.arpg.elements;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockFuel extends ItemBlock {
   public int burntime;

   public ItemBlockFuel(Block block, int burntime) {
      super(block);
      this.burntime = burntime;
   }

   public int getItemBurnTime(ItemStack itemStack) {
      return this.burntime < 0 ? -1 : this.burntime * 20;
   }
}
