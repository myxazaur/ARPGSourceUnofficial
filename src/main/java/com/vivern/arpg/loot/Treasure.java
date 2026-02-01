package com.vivern.arpg.loot;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;

public class Treasure {
   public Item item;
   public float chance;
   public int damage;
   public int mincount;
   public int maxcount;
   public NBTTagCompound nbtTag;

   public Treasure(Item item, float chance, int damage, int mincount, int maxcount) {
      this.item = item;
      this.chance = chance;
      this.damage = damage;
      this.mincount = mincount;
      this.maxcount = maxcount;
   }

   public Treasure setNbt(NBTTagCompound nbtTag) {
      this.nbtTag = nbtTag;
      return this;
   }
}
