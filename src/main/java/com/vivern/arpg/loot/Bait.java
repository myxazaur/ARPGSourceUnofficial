package com.vivern.arpg.loot;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class Bait {
   public Item item;
   public float power;
   public List<Block> liquids;
   public float looseChance;

   public Bait(Item item, float power, List<Block> liquids, float looseChance) {
      this.item = item;
      this.power = power;
      this.liquids = liquids;
      this.looseChance = looseChance;
   }
}
