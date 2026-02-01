package com.vivern.arpg.loot;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;

public class FishingTreasure {
   public Item item;
   public Biome biome;
   public float chance;
   public float depth;
   public Block liquid;
   public float vecChance;
   public float speed;
   public int fishTime;
   public int damage;

   public FishingTreasure(Item item, Biome biome, float chance, float depth, Block liquid, float vecChance, float speed, int fishTime, int damage) {
      this.item = item;
      this.biome = biome;
      this.chance = chance;
      this.depth = depth;
      this.liquid = liquid;
      this.vecChance = vecChance;
      this.speed = speed;
      this.fishTime = fishTime;
      this.damage = damage;
   }
}
