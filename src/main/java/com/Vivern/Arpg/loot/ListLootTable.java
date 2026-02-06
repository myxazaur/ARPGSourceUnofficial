package com.Vivern.Arpg.loot;

import com.google.common.collect.Sets;
import java.io.File;
import java.util.Collections;
import java.util.Set;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableManager;

public class ListLootTable {
   private static final Set<ResourceLocation> LOOT_TABLES = Sets.newHashSet();
   private static final Set<ResourceLocation> READ_ONLY_LOOT_TABLES = Collections.unmodifiableSet(LOOT_TABLES);
   public static final ResourceLocation CHESTS_ICE_CASTLE = register("chests/ice_castle");
   public static final ResourceLocation CHESTS_ICE_CASTLE_RICH = register("chests/ice_castle_rich");
   public static final ResourceLocation CHESTS_NIVEOUS_HALL = register("chests/niveous_hall");
   public static final ResourceLocation CHESTS_TOXIC_COMMON = register("chests/toxic_common");
   public static final ResourceLocation PRESENT_BOX = register("chests/present_box");
   public static final ResourceLocation CHESTS_RUSTED_UNDERGROUND = register("chests/rusted_underground");
   public static final ResourceLocation CHESTS_RUSTED_BUNKER = register("chests/rusted_bunker");
   public static final ResourceLocation CHESTS_RUSTED_LAB = register("chests/rusted_lab");
   public static final ResourceLocation CHESTS_MOUND = register("chests/frozen_mound");
   public static final ResourceLocation CHESTS_FROZEN_STRUCTURES = register("chests/frozen_structures");
   public static final ResourceLocation CHESTS_FROZEN_GRAVE = register("chests/frozen_grave");
   public static final ResourceLocation CHESTS_HORRIBLE_VILLAGE = register("chests/horrible_village");
   public static final ResourceLocation CHESTS_DUNGEON_DOLERITE_TROVE = register("chests/dungeon_dolerite_trove");
   public static final ResourceLocation CHESTS_DUNGEON_MINESHAFT = register("chests/dungeon_mineshaft");
   public static final ResourceLocation CHESTS_SUNKEN = register("chests/sunken");
   public static final ResourceLocation CHESTS_JELLYFISH_CAVE = register("chests/jellyfish_cave");
   public static final ResourceLocation CHESTS_JELLYFISH_CAVE_FINAL = register("chests/jellyfish_cave_final");
   public static final ResourceLocation CHESTS_SUNKEN_TOWN = register("chests/sunken_town");
   public static final ResourceLocation CHESTS_SIREN_SANCTUARY = register("chests/siren_sanctuary");
   public static final ResourceLocation CHESTS_ZARPION_INTRICACY = register("chests/zarpion_intricacy");
   public static final ResourceLocation CHESTS_MUSHROOM_TREASURE = register("chests/mushroom_treasure");

   private static ResourceLocation register(String id) {
      return register(new ResourceLocation("arpg", id));
   }

   public static ResourceLocation register(ResourceLocation id) {
      if (LOOT_TABLES.add(id)) {
         return id;
      } else {
         throw new IllegalArgumentException(id + " is already a registered built-in loot table");
      }
   }

   public static Set<ResourceLocation> getAll() {
      return READ_ONLY_LOOT_TABLES;
   }

   public static boolean test() {
      LootTableManager loottablemanager = new LootTableManager((File)null);

      for (ResourceLocation resourcelocation : READ_ONLY_LOOT_TABLES) {
         if (loottablemanager.getLootTableFromLocation(resourcelocation) == LootTable.EMPTY_LOOT_TABLE) {
            return false;
         }
      }

      return true;
   }
}
